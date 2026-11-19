package apistandards.deploy

import com.google.api.gax.core.FixedCredentialsProvider
import com.google.api.gax.rpc.NotFoundException
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.run.v2.Container
import com.google.cloud.run.v2.ContainerPort
import com.google.cloud.run.v2.LocationName
import com.google.cloud.run.v2.RevisionTemplate
import com.google.cloud.run.v2.Service
import com.google.cloud.run.v2.ServiceName
import com.google.cloud.run.v2.ServicesClient
import com.google.cloud.run.v2.ServicesSettings
import com.google.cloud.tools.jib.api.Containerizer
import com.google.cloud.tools.jib.api.JavaContainerBuilder
import com.google.cloud.tools.jib.api.LogEvent
import com.google.cloud.tools.jib.api.RegistryImage
import com.google.cloud.tools.jib.api.buildplan.Port
import org.jetbrains.amper.plugins.Classpath
import org.jetbrains.amper.plugins.CompilationArtifact
import org.jetbrains.amper.plugins.ExecutionAvoidance
import org.jetbrains.amper.plugins.Input
import org.jetbrains.amper.plugins.TaskAction
import java.nio.file.Path
import java.util.concurrent.TimeUnit

/**
 * Builds a container image of the module with Jib, pushes it to Artifact Registry and deploys it to Cloud Run.
 *
 * Authentication uses Application Default Credentials for both the registry and Cloud Run:
 * locally `gcloud auth application-default login`, on CI `google-github-actions/auth`.
 *
 * The image tag defaults to the short git commit hash and can be overridden with the `TAG` environment variable.
 */
@TaskAction(executionAvoidance = ExecutionAvoidance.Disabled)
fun deploy(
    @Input jar: CompilationArtifact,
    @Input runtimeClasspath: Classpath,
    @Input(inferTaskDependency = false) projectDir: Path,
    mainClass: String?,
    settings: DeploySettings,
) {
    checkNotNull(mainClass) { "settings.jvm.mainClass must be set to deploy the module" }

    val credentials = GoogleCredentials.getApplicationDefault()
        .createScoped("https://www.googleapis.com/auth/cloud-platform")
    credentials.refreshIfExpired()
    val accessToken = checkNotNull(credentials.accessToken) { "Cannot get a Google Cloud access token" }.tokenValue

    val tag = System.getenv("TAG")?.takeIf { it.isNotBlank() } ?: gitShortHash(projectDir)
    val image = "${settings.region}-docker.pkg.dev/${settings.gcpProject}/${settings.repository}/${settings.service}"
    val imageWithTag = "$image:$tag"

    // Build and push the image
    val dependencies = runtimeClasspath.resolvedFiles.filter { it != jar.artifact }
    println("Building $imageWithTag")
    val container = JavaContainerBuilder.from(RegistryImage.named(settings.baseImage))
        .addDependencies(dependencies)
        .addProjectDependencies(listOf(jar.artifact))
        .setMainClass(mainClass)
        .toContainerBuilder()
        .setExposedPorts(setOf(Port.tcp(settings.port)))
        .containerize(
            Containerizer.to(
                RegistryImage.named(imageWithTag)
                    .addCredential("oauth2accesstoken", accessToken)
            )
                .withAdditionalTag("latest")
                .setToolName("amper-deploy-plugin")
                .addEventHandler(LogEvent::class.java) { event ->
                    if (event.level <= LogEvent.Level.LIFECYCLE) println(event.message)
                }
        )
    println("Pushed $imageWithTag (${container.digest})")

    // Deploy to Cloud Run
    val servicesSettings = ServicesSettings.newBuilder()
        .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
        .build()
    // The port name tells Cloud Run which protocol to use towards the container
    val containerPort = ContainerPort.newBuilder()
        .setName(if (settings.http2) "h2c" else "http1")
        .setContainerPort(settings.port)
        .build()
    ServicesClient.create(servicesSettings).use { client ->
        val name = ServiceName.of(settings.gcpProject, settings.region, settings.service)
        val existing = try {
            client.getService(name)
        } catch (_: NotFoundException) {
            null
        }

        val deployed = if (existing == null) {
            println("Creating Cloud Run service ${settings.service}")
            val service = Service.newBuilder()
                .setTemplate(
                    RevisionTemplate.newBuilder().addContainers(
                        Container.newBuilder()
                            .setImage(imageWithTag)
                            .addPorts(containerPort)
                    )
                )
                .build()
            client.createServiceAsync(LocationName.of(settings.gcpProject, settings.region), service, settings.service).get()
        } else {
            println("Updating Cloud Run service ${settings.service}")
            val template = existing.template.toBuilder()
                // A revision name, if any, must be unique, so let Cloud Run generate a new one
                .clearRevision()
            template.setContainers(
                0,
                template.getContainers(0).toBuilder()
                    .setImage(imageWithTag)
                    // Cloud Run allows a single port, so this replaces the existing one
                    .clearPorts()
                    .addPorts(containerPort)
            )
            client.updateServiceAsync(existing.toBuilder().setTemplate(template).build()).get()
        }
        println("Deployed ${deployed.latestReadyRevision} at ${deployed.uri}")
    }
}

private fun gitShortHash(projectDir: Path): String {
    val process = ProcessBuilder("git", "rev-parse", "--short", "HEAD")
        .directory(projectDir.toFile())
        .redirectErrorStream(true)
        .start()
    val output = process.inputStream.bufferedReader().readText().trim()
    check(process.waitFor(30, TimeUnit.SECONDS) && process.exitValue() == 0) {
        "Cannot compute the image tag from git, set the TAG environment variable instead: $output"
    }
    return output
}
