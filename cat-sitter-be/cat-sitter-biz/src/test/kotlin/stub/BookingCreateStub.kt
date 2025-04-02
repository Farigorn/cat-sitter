package stub

import MkplContext
import kotlinx.coroutines.test.runTest
import models.*
import ru.catsitter.biz.MkplBookingProcessor
import stubs.MkplStubs
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class BookingCreateStub {

    private val processor = MkplBookingProcessor()

    val id = MkplBookingId(UUID.fromString("00000000-0000-0000-0000-000000000003"))
    val note = "test"
    val ownerId = MkplOwnerId(UUID.randomUUID())
    val sitterId = MkplSitterId(UUID.randomUUID())


    @Test
    fun create() = runTest {
        val ctx = MkplContext(
            command = MkplCommand.CREATE,
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
    fun databaseError() = runTest {
        val ctx = MkplContext(
            command = MkplCommand.CREATE,
            state = MkplState.NONE,
            workMode = MkplWorkMode.STUB,
            stubCase = MkplStubs.DB_ERROR,
            bookingRequest = MkplBooking(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(MkplBooking(), ctx.bookingResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badOwnerId() = runTest {
        val ctx = MkplContext(
            command = MkplCommand.CREATE,
            state = MkplState.NONE,
            workMode = MkplWorkMode.STUB,
            stubCase = MkplStubs.BAD_OWNER_ID,
            bookingRequest = MkplBooking(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(MkplBooking(), ctx.bookingResponse)
        assertEquals("ownerId", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }


    @Test
    fun badNoCase() = runTest {
        val ctx = MkplContext(
            command = MkplCommand.CREATE,
            state = MkplState.NONE,
            workMode = MkplWorkMode.STUB,
            stubCase = MkplStubs.BAD_ID,
            bookingRequest = MkplBooking(
                id = id,
                ownerId = ownerId,
                sitterId = sitterId,
            ),
        )
        processor.exec(ctx)
        assertEquals(MkplBooking(), ctx.bookingResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

}