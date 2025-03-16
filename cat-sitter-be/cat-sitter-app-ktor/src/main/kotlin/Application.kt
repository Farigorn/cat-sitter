import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.routing.*
import org.slf4j.event.Level
import plugins.initAppSetting

@Suppress("unused")
fun Application.module(
    appSetting: MkplAppSetting = initAppSetting()
) {
    install(CachingHeaders)
    install(DefaultHeaders)
    install(AutoHeadResponse)
    install(CallLogging) {
        level = Level.INFO
    }
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowHeader("MyCustomHeader")
        allowCredentials = true
        /* TODO
            Это временное решение, оно опасно.
            В боевом приложении здесь должны быть конкретные настройки
        */
        anyHost()
    }

    routing {
        route("/") {
            if (application.pluginOrNull(ContentNegotiation) == null) {
                install(ContentNegotiation) {
                    jackson {
                        setConfig(apiMapper.serializationConfig)
                        setConfig(apiMapper.deserializationConfig)
                    }
                }
            }

            booking(appSetting)
//            webSocket("/ws") {
//                wsHandlerV1(appSettings)
//            }
        }
    }
}