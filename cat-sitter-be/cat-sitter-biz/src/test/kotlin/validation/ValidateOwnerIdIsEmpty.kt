package validation

import MkplContext
import kotlinx.coroutines.test.runTest
import models.*
import ru.catsitter.biz.MkplBookingProcessor
import java.util.*
import kotlin.test.assertContains

fun validationOwnerIdIsEmpty(command: MkplCommand, processor: MkplBookingProcessor) = runTest {
    val ctx = MkplContext(
        command = command,
        state = MkplState.NONE,
        workMode = MkplWorkMode.TEST,
        bookingRequest = MkplBooking(
            id = MkplBookingId(UUID.randomUUID()),
            ownerId = MkplOwnerId.NONE,
            sitterId = MkplSitterId(UUID.randomUUID()),
            startDate = "startDate",
            endDate = "endDate",
    ),)
    processor.exec(ctx)
    kotlin.test.assertEquals(1, ctx.errors.size)
    kotlin.test.assertEquals(MkplState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    kotlin.test.assertEquals("ownerId", error?.field)
    assertContains(error?.message ?: "", "ownerId")
}