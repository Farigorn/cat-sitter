import ru.catsitter.biz.MkplBookingProcessor

data class MkplAppSetting (
    var appUrls: List<String> = emptyList(),
    override val corSettings: MkplCorSettings = MkplCorSettings(),
    override val processor: MkplBookingProcessor = MkplBookingProcessor(corSettings)

): IMkplAppSettings