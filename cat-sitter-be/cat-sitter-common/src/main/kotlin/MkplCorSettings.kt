import ru.catsitter.logging.common.MpLoggerProvider

data class MkplCorSettings(
    val loggerProvider: MpLoggerProvider = MpLoggerProvider()
) {
    companion object {
        val NONE = MkplCorSettings()
    }
}
