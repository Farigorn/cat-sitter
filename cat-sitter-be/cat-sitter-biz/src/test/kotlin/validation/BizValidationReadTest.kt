package validation

import models.MkplCommand
import org.junit.Test

class BizValidationReadTest() : BaseBizValidationTest() {
    override val command: MkplCommand = MkplCommand.READ

    @Test
    fun validationBookingIdIsEmpty() = validationBookingIdIsEmpty(command, processor)
}