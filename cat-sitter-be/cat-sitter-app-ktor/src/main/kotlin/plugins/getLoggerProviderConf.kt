package plugins

import io.ktor.server.application.*
import ru.catsitter.logging.common.MpLoggerProvider
import ru.catsitter.logging.jvm.mpLoggerLogback

fun Application.getLoggerProviderConf(): MpLoggerProvider =
    when (val mode = environment.config.propertyOrNull("ktor.logger")?.getString()) {
        "logback", null -> MpLoggerProvider { mpLoggerLogback(it) }
        else -> throw Exception("Logger $mode is not allowed. Additted values are kmp, socket and logback (default)")
    }