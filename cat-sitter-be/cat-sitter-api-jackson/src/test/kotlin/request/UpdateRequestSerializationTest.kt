package request

import apiMapper
import com.otus.sy.catsitter.api.model.BookingDebug
import com.otus.sy.catsitter.api.model.BookingRequestDebugMode
import com.otus.sy.catsitter.api.model.BookingRequestDebugStubs
import com.otus.sy.catsitter.api.model.BookingUpdateRequest
import org.junit.Assert.assertEquals
import java.util.*
import kotlin.test.Test
import kotlin.test.assertContains

class UpdateRequestSerializationTest {

    val request = BookingUpdateRequest(
        debug = BookingDebug(
            mode = BookingRequestDebugMode.STUB,
            stub = BookingRequestDebugStubs.SUCCESS
        ), id = UUID.fromString("8c4e1b2a-a9e6-4de5-8c42-d9e2d7b08238"),
        ownerId = UUID.fromString("2d521cf7-8630-4fcd-858e-e7ff2d7fc432"),
        sitterId = UUID.fromString("50f61642-b715-422b-a70a-dfd405100173"),
        startDate = "startDate",
        endDate = "endDate",
        notes = "notes"
    )

    @Test
    fun serialize() {
        val json = apiMapper.writeValueAsString(request)
        assertContains(json, Regex("\"requestType\":\"update\""))
        assertContains(json, Regex("\"mode\":\"stub\""))
        assertContains(json, Regex("\"stub\":\"success\""))
        assertContains(json, Regex("\"id\":\"8c4e1b2a-a9e6-4de5-8c42-d9e2d7b08238\""))
        assertContains(json, Regex("\"ownerId\":\"2d521cf7-8630-4fcd-858e-e7ff2d7fc432\""))
        assertContains(json, Regex("\"sitterId\":\"50f61642-b715-422b-a70a-dfd405100173\""))
        assertContains(json, Regex("\"startDate\":\"startDate\""))
        assertContains(json, Regex("\"endDate\":\"endDate\""))
        assertContains(json, Regex("\"notes\":\"notes\""))
    }

    @Test
    fun deserialize() {
        val json = apiMapper.writeValueAsString(request)
        val obj = apiMapper.readValue(json, BookingUpdateRequest::class.java)
        assertEquals(request, obj)
    }
}