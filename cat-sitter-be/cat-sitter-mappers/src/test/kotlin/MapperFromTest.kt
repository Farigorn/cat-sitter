import com.otus.sy.catsitter.api.model.*
import models.MkplWorkMode
import stubs.MkplStubs
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class MapperFromTest {

    @Test
    fun fromCreateTransport() {
        val req = BookingCreateRequest(
            debug = BookingDebug(
                mode = BookingRequestDebugMode.STUB,
                stub = BookingRequestDebugStubs.SUCCESS
            ),
            ownerId = UUID.fromString("2d521cf7-8630-4fcd-858e-e7ff2d7fc432"),
            sitterId = UUID.fromString("50f61642-b715-422b-a70a-dfd405100173"),
            startDate = "startDate",
            endDate = "endDate",
            notes = "notes"
        )

        val context = MkplContext()
        context.fromTransport(req)
        assertEquals(MkplStubs.SUCCESS, context.stubCase)
        assertEquals(MkplWorkMode.STUB, context.workMode)
        assertEquals(UUID.fromString("2d521cf7-8630-4fcd-858e-e7ff2d7fc432"), context.bookingRequest.ownerId.asUUID())
        assertEquals(UUID.fromString("50f61642-b715-422b-a70a-dfd405100173"), context.bookingRequest.sitterId.asUUID())

        assertEquals("startDate", context.bookingRequest.startDate)
        assertEquals("endDate", context.bookingRequest.endDate)
        assertEquals("notes", context.bookingRequest.notes)
    }

    @Test
    fun fromReadTransport() {
        val req = BookingReadRequest(
            debug = BookingDebug(
                mode = BookingRequestDebugMode.STUB,
                stub = BookingRequestDebugStubs.SUCCESS
            ),
            booking = BookingReadObject(
                id = UUID.fromString("2d521cf7-8630-4fcd-858e-e7ff2d7fc432")
            )
        )
        val context = MkplContext()
        context.fromTransport(req)
        assertEquals(MkplStubs.SUCCESS, context.stubCase)
        assertEquals(MkplWorkMode.STUB, context.workMode)
        assertEquals(UUID.fromString("2d521cf7-8630-4fcd-858e-e7ff2d7fc432"), context.bookingRequest.id.asUUID())
    }


    @Test
    fun fromDeleteTransport() {
        val req = BookingDeleteRequest(
            debug = BookingDebug(
                mode = BookingRequestDebugMode.STUB,
                stub = BookingRequestDebugStubs.SUCCESS
            ),
            booking = BookingDeleteObject(
                id = UUID.fromString("2d521cf7-8630-4fcd-858e-e7ff2d7fc432")
            )
        )
        val context = MkplContext()
        context.fromTransport(req)
        assertEquals(MkplStubs.SUCCESS, context.stubCase)
        assertEquals(MkplWorkMode.STUB, context.workMode)
        assertEquals(UUID.fromString("2d521cf7-8630-4fcd-858e-e7ff2d7fc432"), context.bookingRequest.id.asUUID())
    }

    @Test
    fun fromUpdateTransport() {
        val req = BookingUpdateRequest(
            debug = BookingDebug(
                mode = BookingRequestDebugMode.STUB,
                stub = BookingRequestDebugStubs.SUCCESS
            ),
            id = UUID.fromString("8c4e1b2a-a9e6-4de5-8c42-d9e2d7b08238"),
            ownerId = UUID.fromString("2d521cf7-8630-4fcd-858e-e7ff2d7fc432"),
            sitterId = UUID.fromString("50f61642-b715-422b-a70a-dfd405100173"),
            startDate = "startDate",
            endDate = "endDate",
            notes = "notes"
        )
        val context = MkplContext()
        context.fromTransport(req)
        assertEquals(MkplStubs.SUCCESS, context.stubCase)
        assertEquals(MkplWorkMode.STUB, context.workMode)
        assertEquals(UUID.fromString("8c4e1b2a-a9e6-4de5-8c42-d9e2d7b08238"), context.bookingRequest.id.asUUID())
        assertEquals(UUID.fromString("2d521cf7-8630-4fcd-858e-e7ff2d7fc432"), context.bookingRequest.ownerId.asUUID())
        assertEquals(UUID.fromString("50f61642-b715-422b-a70a-dfd405100173"), context.bookingRequest.sitterId.asUUID())
        assertEquals("startDate", context.bookingRequest.startDate)
        assertEquals("endDate", context.bookingRequest.endDate)
        assertEquals("notes", context.bookingRequest.notes)
    }

}