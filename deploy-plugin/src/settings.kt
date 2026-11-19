package apistandards.deploy

import org.jetbrains.amper.plugins.Configurable

@Configurable
interface DeploySettings {
    /** The Google Cloud project to deploy to. */
    val gcpProject: String

    /** The Cloud Run service name. Also used as the image name. */
    val service: String

    /** The region of both the Cloud Run service and the Artifact Registry repository. */
    val region: String get() = "europe-west9"

    /** The Artifact Registry Docker repository. */
    val repository: String

    /** The base image to put the application on top of. */
    val baseImage: String get() = "eclipse-temurin:25-jre"

    /** The port the application listens on. */
    val port: Int get() = 8080
}
