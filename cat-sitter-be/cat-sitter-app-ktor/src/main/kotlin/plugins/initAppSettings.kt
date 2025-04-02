package plugins

import MkplAppSetting
import MkplCorSettings
import io.ktor.server.application.*
import ru.catsitter.biz.MkplBookingProcessor

fun Application.initAppSetting(): MkplAppSetting {
    val  corSetting = MkplCorSettings(loggerProvider = getLoggerProviderConf())
    return MkplAppSetting(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
        corSettings = corSetting,
        processor = MkplBookingProcessor(corSetting)

    )
}