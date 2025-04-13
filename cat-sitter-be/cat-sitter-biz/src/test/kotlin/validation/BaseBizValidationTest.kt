package validation

import MkplCorSettings
import models.MkplCommand
import ru.catsitter.biz.MkplBookingProcessor

abstract class BaseBizValidationTest {
    protected abstract val command: MkplCommand
    private val settings by lazy { MkplCorSettings() }
    protected val processor by lazy { MkplBookingProcessor(settings) }
}