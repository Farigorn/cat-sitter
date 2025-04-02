package config

import IMkplAppSettings
import MkplCorSettings
import ru.catsitter.biz.MkplBookingProcessor

data class MkplAppSettings(
    override val corSettings: MkplCorSettings = MkplCorSettings(),
    override val processor: MkplBookingProcessor = MkplBookingProcessor(corSettings),
    override val rabbit: RabbitConfig = RabbitConfig(),
    override val controllerConfig: RabbitExchangeConfiguration = RabbitExchangeConfiguration.NONE
) : IMkplAppSettings, IMkplAppRabbitSettings