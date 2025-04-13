package validation

import models.MkplCommand
import kotlin.test.Test

class BizValidationDeleteTest() : BaseBizValidationTest() {
    override val command: MkplCommand = MkplCommand.DELETE

    @Test
    fun validationBookingIdIsEmpty() = validationBookingIdIsEmpty(command, processor)

    @Test
    fun correctLock() = validationLockCorrect(command, processor)

    @Test
    fun trimLock() = validationLockTrim(command, processor)

    @Test
    fun emptyLock() = validationLockEmpty(command, processor)

    @Test
    fun badFormatLock() = validationLockFormat(command, processor)
}