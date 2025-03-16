package request

import apiMapper
import com.otus.sy.catsitter.api.model.*
import org.junit.Assert.assertEquals
import java.util.*
import kotlin.test.Test
import kotlin.test.assertContains

class ReadBookingRequestSerialization {

    val request = BookingReadRequest(
        debug = BookingDebug(
            mode = BookingRequestDebugMode.STUB,
            stub = BookingRequestDebugStubs.SUCCESS
        ),
        booking = BookingReadObject(
            id = UUID.fromString("2d521cf7-8630-4fcd-858e-e7ff2d7fc432"),
        )
    )

    @Test
    fun serialize() {
        val json = apiMapper.writeValueAsString(request)
        assertContains(json, Regex("\"requestType\":\"read\""))
        assertContains(json, Regex("\"mode\":\"stub\""))
        assertContains(json, Regex("\"stub\":\"success\""))
    }

    @Test
    fun deserialize() {
        val json = apiMapper.writeValueAsString(request)
        val obj = apiMapper.readValue(json, BookingReadRequest::class.java)
        assertEquals(request, obj)
    }
}