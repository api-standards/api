package apistandards

import galak.ktor.galakModule
import io.ktor.http.ContentType
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080
    embeddedServer(
        Netty,
        configure = {
            connector { this.port = port }
            // Accept cleartext HTTP/2 (h2c), which Cloud Run sends when the container port is named "h2c"
            enableH2c = true
        },
        module = Application::apiStandardsModule,
    ).start(wait = true)
}

fun Application.apiStandardsModule() {
    // POST/GET /graphql
    galakModule(ServiceExecutableSchemaBuilder().build())

    // GraphiQL 6 at /
    val graphiql = Query::class.java.getResource("/graphiql.html")!!.readText()
    routing {
        get("/") {
            call.respondText(graphiql, ContentType.Text.Html)
        }
    }
}
