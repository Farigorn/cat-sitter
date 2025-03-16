package request

import apiMapper
import com.otus.sy.catsitter.api.model.*
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class CreateRequestSerializationTest {

    private val request: BookingCreateRequest = BookingCreateRequest(
        requestType = "create",
        debug = BookingDebug(
            mode = BookingRequestDebugMode.STUB,
            stub = BookingRequestDebugStubs.SUCCESS
        ),
        ownerId = UUID.randomUUID(),
        sitterId = UUID.randomUUID(),
        startDate = "startDate",
        endDate = "endDate",
        notes = "notes"
    )


    @Test
    fun serialize() {
        val json = apiMapper.writeValueAsString(request)
        assertContains(json, Regex("\"requestType\":\"create\""))
        assertContains(json, Regex("\"mode\":\"stub\""))
        assertContains(json, Regex("\"stub\":\"success\""))
        assertContains(json, Regex("\"ownerId\":\".*\""))
        assertContains(json, Regex("\"sitterId\":\".*\""))
        assertContains(json, Regex("\"startDate\":\"startDate\""))
        assertContains(json, Regex("\"endDate\":\"endDate\""))
        assertContains(json, Regex("\"notes\":\"notes\""))
    }


    @Test
    fun deserialize() {
        val json = apiMapper.writeValueAsString(request)
        val obj = apiMapper.readValue(json, IRequest::class.java) as BookingCreateRequest

        assertEquals(request, obj)
    }


}