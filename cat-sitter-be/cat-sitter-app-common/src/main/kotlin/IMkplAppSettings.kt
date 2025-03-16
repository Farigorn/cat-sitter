import ru.catsitter.biz.MkplBookingProcessor


interface IMkplAppSettings {
    val processor: MkplBookingProcessor
    val corSettings: MkplCorSettings
}
