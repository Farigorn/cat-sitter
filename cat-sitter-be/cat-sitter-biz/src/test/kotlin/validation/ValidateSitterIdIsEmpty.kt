package validation

import MkplContext
import kotlinx.coroutines.test.runTest
import models.*
import ru.catsitter.biz.MkplBookingProcessor
import java.util.*
import kotlin.test.assertContains

fun validationSitterIdIsEmpty(command: MkplCommand, processor: MkplBookingProcessor) = runTest {
    val ctx = MkplContext(
        command = command,
        state = MkplState.NONE,
        workMode = MkplWorkMode.TEST,
        bookingRequest = MkplBooking(
            id = MkplBookingId(UUID.randomUUID()),
            ownerId = MkplOwnerId(UUID.randomUUID()),
            sitterId = MkplSitterId.NONE,
            startDate = "startDate",
            endDate = "endDate",
        ),)
    processor.exec(ctx)
    kotlin.test.assertEquals(1, ctx.errors.size)
    kotlin.test.assertEquals(MkplState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    kotlin.test.assertEquals("sitterId", error?.field)
    assertContains(error?.message ?: "", "sitterId")
}