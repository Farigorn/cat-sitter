package stub

import MkplContext
import kotlinx.coroutines.test.runTest
import models.*
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.catsitter.biz.MkplBookingProcessor
import stubs.MkplStubs
import java.util.*

class BookingUpdateStub {
    private val processor = MkplBookingProcessor()

    val id = MkplBookingId(UUID.fromString("00000000-0000-0000-0000-000000000003"))
    val note = "test"
    val ownerId = MkplOwnerId(UUID.randomUUID())
    val sitterId = MkplSitterId(UUID.randomUUID())

    @Test
    fun update() = runTest {
        val ctx = MkplContext(
            command = MkplCommand.UPDATE,
            state = MkplState.RUNNING,
            workMode = MkplWorkMode.STUB,
            stubCase = MkplStubs.SUCCESS,
            bookingRequest = MkplBooking(
                id = id,
                ownerId = ownerId,
                sitterId = sitterId,
                startDate = "startDate",
                endDate = "endDate",
                notes = note
            )
        )
        processor.exec(ctx)
        assertEquals(id, ctx.bookingResponse.id)
        assertEquals(ownerId, ctx.bookingResponse.ownerId)
        assertEquals(sitterId, ctx.bookingResponse.sitterId)
        assertEquals(note, ctx.bookingResponse.notes)
    }

    @Test
    fun badId() = runTest {
        val ctx = MkplContext(
            command = MkplCommand.UPDATE,
            state = MkplState.NONE,
            workMode = MkplWorkMode.STUB,
            stubCase = MkplStubs.BAD_ID,
            bookingRequest = MkplBooking(),
        )
        processor.exec(ctx)
        kotlin.test.assertEquals(MkplBooking(), ctx.bookingResponse)
        kotlin.test.assertEquals("id", ctx.errors.firstOrNull()?.field)
        kotlin.test.assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = MkplContext(
            command = MkplCommand.UPDATE,
            state = MkplState.NONE,
            workMode = MkplWorkMode.STUB,
            stubCase = MkplStubs.DB_ERROR,
            bookingRequest = MkplBooking(
                id = id,
            ),
        )
        processor.exec(ctx)
        kotlin.test.assertEquals(MkplBooking(), ctx.bookingResponse)
        kotlin.test.assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = MkplContext(
            command = MkplCommand.UPDATE,
            state = MkplState.NONE,
            workMode = MkplWorkMode.STUB,
            stubCase = MkplStubs.BAD_SEARCH_STRING,
            bookingRequest = MkplBooking(
                id = id,
                ownerId = ownerId,
                sitterId = sitterId,
            ),
        )
        processor.exec(ctx)
        kotlin.test.assertEquals(MkplBooking(), ctx.bookingResponse)
        kotlin.test.assertEquals("stub", ctx.errors.firstOrNull()?.field)
        kotlin.test.assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }


}