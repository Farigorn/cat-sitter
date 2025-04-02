import com.otus.sy.catsitter.api.model.IRequest
import com.otus.sy.catsitter.api.model.IResponse
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlin.reflect.KClass

suspend inline fun <reified T : IRequest, @Suppress("unused") reified R : IResponse> ApplicationCall.process(
    appSetting: MkplAppSetting,
    clazz: KClass<*>,
    logId: String,
) = appSetting.controllerHelper(
    { fromTransport(receive<T>()) },
    {
        respond(toTransport())
    },
    clazz,
    logId
)