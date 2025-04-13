package validation

import MkplContext
import kotlinx.coroutines.test.runTest
import models.*
import ru.catsitter.biz.MkplBookingProcessor
import java.util.*
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun validationLockCorrect(command: MkplCommand, processor: MkplBookingProcessor) = runTest {
    val ctx = MkplContext(
        command = command,
        state = MkplState.NONE,
        workMode = MkplWorkMode.TEST,
        bookingRequest = MkplBooking(
            id = MkplBookingId(UUID.randomUUID()),
            ownerId = MkplOwnerId(UUID.randomUUID()),
            sitterId = MkplSitterId(UUID.randomUUID()),
            lock = MkplBookingLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(MkplState.FAILING, ctx.state)
}

fun validationLockTrim(command: MkplCommand, processor: MkplBookingProcessor) = runTest {
    val ctx = MkplContext(
        command = command,
        state = MkplState.NONE,
        workMode = MkplWorkMode.TEST,
        bookingRequest = MkplBooking(
            id = MkplBookingId(UUID.randomUUID()),
            ownerId = MkplOwnerId(UUID.randomUUID()),
            sitterId = MkplSitterId(UUID.randomUUID()),
            lock = MkplBookingLock(" \n\t 123-234-abc-ABC \n\t "),
        )
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(MkplState.FAILING, ctx.state)
}

fun validationLockEmpty(command: MkplCommand, processor: MkplBookingProcessor) = runTest {
    val ctx = MkplContext(
        command = command,
        state = MkplState.NONE,
        workMode = MkplWorkMode.TEST,
        bookingRequest = MkplBooking(
            id = MkplBookingId(UUID.randomUUID()),
            ownerId = MkplOwnerId(UUID.randomUUID()),
            sitterId = MkplSitterId(UUID.randomUUID()),
            lock = MkplBookingLock(""),
        )
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(MkplState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("lock", error?.field)
    assertContains(error?.message ?: "", "id")
}

fun validationLockFormat(command: MkplCommand, processor: MkplBookingProcessor) = runTest {
    val ctx = MkplContext(
        command = command,
        state = MkplState.NONE,
        workMode = MkplWorkMode.TEST,
        bookingRequest = MkplBooking(
            id = MkplBookingId(UUID.randomUUID()),
            ownerId = MkplOwnerId(UUID.randomUUID()),
            sitterId = MkplSitterId(UUID.randomUUID()),
            lock = MkplBookingLock("!@#\$%^&*(),.{}"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(MkplState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("lock", error?.field)
    assertContains(error?.message ?: "", "id")
}