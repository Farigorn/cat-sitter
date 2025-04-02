package validation

import MkplContext
import kotlinx.coroutines.test.runTest
import models.*
import ru.catsitter.biz.MkplBookingProcessor
import java.util.*
import kotlin.test.assertContains

fun validationBookingIdIsEmpty(command: MkplCommand, processor: MkplBookingProcessor) = runTest {
    val ctx = MkplContext(
        command = command,
        state = MkplState.NONE,
        workMode = MkplWorkMode.TEST,
        bookingRequest = MkplBooking(
            id = MkplBookingId.NONE,
            ownerId = MkplOwnerId(UUID.randomUUID()),
            sitterId = MkplSitterId(UUID.randomUUID()),
            startDate = "startDate",
            endDate = "endDate",
            lock = MkplBookingLock("text"),
        ),)
    processor.exec(ctx)
    kotlin.test.assertEquals(1, ctx.errors.size)
    kotlin.test.assertEquals(MkplState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    kotlin.test.assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}