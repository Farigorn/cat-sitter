package request

import apiMapper
import com.otus.sy.catsitter.api.model.*
import org.junit.Assert.assertEquals
import java.util.*
import kotlin.test.Test
import kotlin.test.assertContains

class DeleteRequestSerializationTest {
    val request: BookingDeleteRequest = BookingDeleteRequest(
        debug = BookingDebug(
            mode = BookingRequestDebugMode.STUB,
            stub = BookingRequestDebugStubs.SUCCESS
        ),
        booking = BookingDeleteObject(
            id = UUID.fromString("2d521cf7-8630-4fcd-858e-e7ff2d7fc432")
        )
    )


    @Test
    fun serialize() {
        val json = apiMapper.writeValueAsString(request)
        assertContains(json, Regex("\"requestType\":\"delete\""))
        assertContains(json, Regex("\"mode\":\"stub\""))
        assertContains(json, Regex("\"stub\":\"success\""))
        assertContains(json, Regex("\"id\":\"2d521cf7-8630-4fcd-858e-e7ff2d7fc432\""))
    }

    @Test
    fun deserialize() {
        val json = apiMapper.writeValueAsString(request)
        val obj = apiMapper.readValue(json, BookingDeleteRequest::class.java)
        assertEquals(request, obj)
    }
}