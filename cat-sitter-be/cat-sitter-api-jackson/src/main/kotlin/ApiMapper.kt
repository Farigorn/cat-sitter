import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.otus.sy.catsitter.api.model.IRequest
import com.otus.sy.catsitter.api.model.IResponse

val apiMapper = JsonMapper.builder().run {
    enable(MapperFeature.USE_BASE_TYPE_AS_DEFAULT_IMPL)
    build()
}

@Suppress("unused")
fun apiRequestSerialize(request: IRequest): String = apiMapper.writeValueAsString(request)

@Suppress("UNCHECKED_CAST", "unused")
fun <T : IRequest> apiV1RequestDeserialize(json: String): T =
    apiMapper.readValue(json, IRequest::class.java) as T

@Suppress("unused")
fun apiV1ResponseSerialize(response: IResponse): String = apiMapper.writeValueAsString(response)

@Suppress("UNCHECKED_CAST", "unused")
fun <T : IResponse> apiV1ResponseDeserialize(json: String): T =
    apiMapper.readValue(json, IResponse::class.java) as T

