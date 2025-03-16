package response

import apiMapper
import com.otus.sy.catsitter.api.model.BookingDeleteResponse
import com.otus.sy.catsitter.api.model.Error
import com.otus.sy.catsitter.api.model.ResponseResult
import org.junit.Assert.assertEquals
import kotlin.test.Test
import kotlin.test.assertContains

class DeleteResponseSerializationTest {

    private val response = BookingDeleteResponse(
        result = ResponseResult.ERROR,
        errors = listOf(
            Error(
                code = "code",
                group = "group",
                field = "field",
                message = "message"
            )
        ),
    )

    @Test
    fun serialization() {
        val json = apiMapper.writeValueAsString(response)
        assertContains(json, Regex("\"result\":\"error\""))
        assertContains(json, Regex("\"errors\":\\["))
        assertContains(json, Regex("\"code\":\"code\""))
        assertContains(json, Regex("\"group\":\"group\""))
        assertContains(json, Regex("\"field\":\"field\""))
        assertContains(json, Regex("\"message\":\"message\""))
    }


    @Test
    fun deserialization() {
        val json = apiMapper.writeValueAsString(response)
        val obj = apiMapper.readValue(json, BookingDeleteResponse::class.java)
        assertEquals(response, obj)
    }
}