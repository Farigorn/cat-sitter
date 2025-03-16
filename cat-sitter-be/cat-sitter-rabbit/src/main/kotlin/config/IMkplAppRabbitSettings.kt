package config

import IMkplAppSettings

interface IMkplAppRabbitSettings : IMkplAppSettings {
    val rabbit: RabbitConfig
    val controllerConfig: RabbitExchangeConfiguration
}