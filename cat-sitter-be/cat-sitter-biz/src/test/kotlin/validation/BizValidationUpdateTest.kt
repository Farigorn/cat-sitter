package validation

import models.MkplCommand
import org.junit.Test

class BizValidationUpdateTest() : BaseBizValidationTest() {
    override val command: MkplCommand = MkplCommand.UPDATE

    @Test
    fun validationBookingIdIsEmpty() = validationBookingIdIsEmpty(command, processor)

    @Test fun correctLock() = validationLockCorrect(command, processor)
    @Test fun trimLock() = validationLockTrim(command, processor)
    @Test fun emptyLock() = validationLockEmpty(command, processor)
    @Test fun badFormatLock() = validationLockFormat(command, processor)
}
