package stub

import MkplAppSetting
import MkplCorSettings
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.otus.sy.catsitter.api.model.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import module
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

class BookingStubApiTest {

    @Test
    fun create() = testApplication(
        "create",
        request = BookingCreateRequest(
            ownerId = UUID.randomUUID(),
            sitterId = UUID.randomUUID(),
            notes = "notes",
            debug = BookingDebug(
                mode = BookingRequestDebugMode.STUB,
                stub = BookingRequestDebugStubs.SUCCESS
            )
        ),

        ) { response ->
        val responseObj = response.body<BookingCreateResponse>()
        assertEquals(200, response.status.value)
        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000003"), responseObj.booking?.id)
        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000001"), responseObj.booking?.ownerId)
        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000002"), responseObj.booking?.sitterId)
    }

    @Test
    fun read() = testApplication(
        func = "read",
        request = BookingReadRequest(
            booking = BookingReadObject(
                id = UUID.randomUUID(),
            ),
            debug = BookingDebug(
                mode = BookingRequestDebugMode.STUB,
                stub = BookingRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<BookingReadResponse>()
        assertEquals(200, response.status.value)
        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000003"), responseObj.booking?.id)
        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000001"), responseObj.booking?.ownerId)
        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000002"), responseObj.booking?.sitterId)
    }

    @Test
    fun update() = testApplication(
        func = "update",
        request = BookingUpdateRequest(
            id = UUID.randomUUID(),
            ownerId = UUID.randomUUID(),
            sitterId = UUID.randomUUID(),
            notes = "notes",
            debug = BookingDebug(
                mode = BookingRequestDebugMode.STUB,
                stub = BookingRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<BookingUpdateResponse>()
        assertEquals(200, response.status.value)
        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000003"), responseObj.booking?.id)
        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000001"), responseObj.booking?.ownerId)
        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000002"), responseObj.booking?.sitterId)
    }


    @Test
    fun delete() = testApplication(
        func = "delete",
        request = BookingDeleteRequest(
            booking = BookingDeleteObject(
                id = UUID.randomUUID(),
            ),
            debug = BookingDebug(
                mode = BookingRequestDebugMode.STUB,
                stub = BookingRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<BookingDeleteResponse>()
        assertEquals(200, response.status.value)
        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000003"), responseObj.booking?.id)
        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000001"), responseObj.booking?.ownerId)
        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000002"), responseObj.booking?.sitterId)
    }


    private fun testApplication(
        func: String,
        request: IRequest,
        function: suspend (HttpResponse) -> Unit,
    ): Unit = testApplication {
        application { module(MkplAppSetting(corSettings = MkplCorSettings())) }
        val client = createClient {
            install(ContentNegotiation) {
                jackson {
                    disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    enable(SerializationFeature.INDENT_OUTPUT)
                    writerWithDefaultPrettyPrinter()
                }
            }
        }
        val response = client.post("booking/$func") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        function(response)
    }

}
