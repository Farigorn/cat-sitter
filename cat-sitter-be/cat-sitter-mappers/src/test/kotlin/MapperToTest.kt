import models.*
import org.junit.Test
import java.util.UUID
import kotlin.test.assertEquals

class MapperToTest {

    @Test
    fun toCreateTransport() {
        val context = MkplContext(
            command = MkplCommand.CREATE,
            state = MkplState.RUNNING,
            bookingResponse = MkplBooking(
                id = MkplBookingId(UUID.fromString("8c4e1b2a-a9e6-4de5-8c42-d9e2d7b08238")),
                ownerId = MkplOwnerId(UUID.fromString("2d521cf7-8630-4fcd-858e-e7ff2d7fc432")),
                sitterId = MkplSitterId(UUID.fromString("50f61642-b715-422b-a70a-dfd405100173")),
                startDate = "startDate",
                endDate = "endDate",
                notes = "notes",
                status = MkplBookingStatus.NEW
            ),
            errors = mutableListOf(
                MkplError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            )
        )
        val req = context.toTransportCreate()

        assertEquals(UUID.fromString("8c4e1b2a-a9e6-4de5-8c42-d9e2d7b08238"), req.booking?.id)
        assertEquals(UUID.fromString("2d521cf7-8630-4fcd-858e-e7ff2d7fc432"), req.booking?.ownerId)
        assertEquals(UUID.fromString("50f61642-b715-422b-a70a-dfd405100173"), req.booking?.sitterId)
        assertEquals("startDate", req.booking?.startDate)
        assertEquals("endDate", req.booking?.endDate)
        assertEquals("notes", req.booking?.notes)
        assertEquals("err", req.errors?.get(0)?.code)
        assertEquals("request", req.errors?.get(0)?.group)
        assertEquals("title", req.errors?.get(0)?.field)
        assertEquals("wrong title", req.errors?.get(0)?.message)
    }

    @Test
    fun toReadTransport() {
        val context = MkplContext(
            command = MkplCommand.READ,
            state = MkplState.RUNNING,
            bookingResponse = MkplBooking(
                id = MkplBookingId(UUID.fromString("8c4e1b2a-a9e6-4de5-8c42-d9e2d7b08238")),
                ownerId = MkplOwnerId(UUID.fromString("2d521cf7-8630-4fcd-858e-e7ff2d7fc432")),
                sitterId = MkplSitterId(UUID.fromString("50f61642-b715-422b-a70a-dfd405100173")),
                startDate = "startDate",
                endDate = "endDate",
                notes = "notes",
                status = MkplBookingStatus.NEW
            ),
            errors = mutableListOf(
                MkplError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                ),
            )
        )
        val req = context.toTransportRead()
        assertEquals(UUID.fromString("8c4e1b2a-a9e6-4de5-8c42-d9e2d7b08238"), req.booking?.id)
        assertEquals(UUID.fromString("2d521cf7-8630-4fcd-858e-e7ff2d7fc432"), req.booking?.ownerId)
        assertEquals(UUID.fromString("50f61642-b715-422b-a70a-dfd405100173"), req.booking?.sitterId)
        assertEquals("startDate", req.booking?.startDate)
        assertEquals("endDate", req.booking?.endDate)
        assertEquals("notes", req.booking?.notes)
        assertEquals("err", req.errors?.get(0)?.code)
        assertEquals("request", req.errors?.get(0)?.group)
        assertEquals("title", req.errors?.get(0)?.field)
        assertEquals("wrong title", req.errors?.get(0)?.message)
    }

    @Test
    fun toUpdateTransport() {
        val context = MkplContext(
            command = MkplCommand.UPDATE,
            state = MkplState.RUNNING,
            bookingResponse = MkplBooking(
                id = MkplBookingId(UUID.fromString("8c4e1b2a-a9e6-4de5-8c42-d9e2d7b08238")),
                ownerId = MkplOwnerId(UUID.fromString("2d521cf7-8630-4fcd-858e-e7ff2d7fc432")),
                sitterId = MkplSitterId(UUID.fromString("50f61642-b715-422b-a70a-dfd405100173")),
                startDate = "startDate",
                endDate = "endDate",
                notes = "notes",
                status = MkplBookingStatus.NEW
            ),
            errors = mutableListOf(
                MkplError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                ),
            )
        )
        val req = context.toTransportUpdate()
        assertEquals(UUID.fromString("8c4e1b2a-a9e6-4de5-8c42-d9e2d7b08238"), req.booking?.id)
        assertEquals(UUID.fromString("2d521cf7-8630-4fcd-858e-e7ff2d7fc432"), req.booking?.ownerId)
        assertEquals(UUID.fromString("50f61642-b715-422b-a70a-dfd405100173"), req.booking?.sitterId)
        assertEquals("startDate", req.booking?.startDate)
        assertEquals("endDate", req.booking?.endDate)
        assertEquals("notes", req.booking?.notes)
        assertEquals("err", req.errors?.get(0)?.code)
        assertEquals("request", req.errors?.get(0)?.group)
        assertEquals("title", req.errors?.get(0)?.field)
        assertEquals("wrong title", req.errors?.get(0)?.message)
    }

    @Test
    fun toDeleteTransport() {
        val context = MkplContext(
            command = MkplCommand.DELETE,
            state = MkplState.RUNNING,
            bookingResponse = MkplBooking(
                id = MkplBookingId(UUID.fromString("8c4e1b2a-a9e6-4de5-8c42-d9e2d7b08238")),
                ownerId = MkplOwnerId(UUID.fromString("2d521cf7-8630-4fcd-858e-e7ff2d7fc432")),
                sitterId = MkplSitterId(UUID.fromString("50f61642-b715-422b-a70a-dfd405100173")),
                startDate = "startDate",
                endDate = "endDate",
                notes = "notes",
                status = MkplBookingStatus.NEW
            ),
            errors = mutableListOf(
                MkplError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                ),
            )
        )
        val req = context.toTransportDelete()
        assertEquals(UUID.fromString("8c4e1b2a-a9e6-4de5-8c42-d9e2d7b08238"), req.booking?.id)
        assertEquals(UUID.fromString("2d521cf7-8630-4fcd-858e-e7ff2d7fc432"), req.booking?.ownerId)
        assertEquals(UUID.fromString("50f61642-b715-422b-a70a-dfd405100173"), req.booking?.sitterId)
        assertEquals("startDate", req.booking?.startDate)
        assertEquals("endDate", req.booking?.endDate)
        assertEquals("notes", req.booking?.notes)
        assertEquals("err", req.errors?.get(0)?.code)
        assertEquals("request", req.errors?.get(0)?.group)
        assertEquals("title", req.errors?.get(0)?.field)
        assertEquals("wrong title", req.errors?.get(0)?.message)
    }
}