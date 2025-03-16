package response

import apiMapper
import com.otus.sy.catsitter.api.model.BookingReadResponse
import com.otus.sy.catsitter.api.model.BookingResponseObject
import com.otus.sy.catsitter.api.model.BookingStatus
import com.otus.sy.catsitter.api.model.ResponseResult
import java.util.*
import kotlin.test.Test
import kotlin.test.assertContains

class ReadResponseSerializationTest {

    private val response = BookingReadResponse(
        result = ResponseResult.SUCCESS,
        booking = BookingResponseObject(
            id = UUID.fromString("2d521cf7-8630-4fcd-858e-e7ff2d7fc432"),
            ownerId = UUID.fromString("50f61642-b715-422b-a70a-dfd405100173"),
            sitterId = UUID.fromString("50f61642-b715-422b-a70a-dfd405100173"),
            startDate = "2021-12-12",
            endDate = "2021-12-12",
            notes = "notes",
            status = BookingStatus.NEW
        )
    )

    @Test
    fun serialization() {
        val json = apiMapper.writeValueAsString(response)
        assertContains(json, Regex("\"result\":\"success\""))
        assertContains(json, Regex("\"booking\":\\{"))
        assertContains(json, Regex("\"id\":\"2d521cf7-8630-4fcd-858e-e7ff2d7fc432\""))
        assertContains(json, Regex("\"ownerId\":\"50f61642-b715-422b-a70a-dfd405100173\""))
        assertContains(json, Regex("\"sitterId\":\"50f61642-b715-422b-a70a-dfd405100173\""))
        assertContains(json, Regex("\"startDate\":\"2021-12-12\""))
        assertContains(json, Regex("\"endDate\":\"2021-12-12\""))
        assertContains(json, Regex("\"notes\":\"notes\""))
        assertContains(json, Regex("\"status\":\"NEW\""))
    }

    @Test
    fun deserialization() {
        val json = apiMapper.writeValueAsString(response)
        val deserialized = apiMapper.readValue(json, BookingReadResponse::class.java)
        assert(deserialized == response)
    }
}