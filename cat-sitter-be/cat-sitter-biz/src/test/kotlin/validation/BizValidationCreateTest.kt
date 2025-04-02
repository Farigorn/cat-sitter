package validation

import models.MkplCommand
import kotlin.test.Test

class BizValidationCreateTest : BaseBizValidationTest() {
    override val command: MkplCommand = MkplCommand.CREATE
    @Test
    fun ownerIdIsNotEmpty() = validationOwnerIdIsEmpty(command, processor)

    @Test
    fun sitterIdIsNotEmpty() = validationSitterIdIsEmpty(command, processor)
}