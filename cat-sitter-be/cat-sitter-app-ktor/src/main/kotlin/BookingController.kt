import com.otus.sy.catsitter.api.model.*
import io.ktor.server.application.*
import kotlin.reflect.KClass

val clCreate: KClass<*> = ApplicationCall::createBooking::class
suspend fun ApplicationCall.createBooking(appSetting: MkplAppSetting) {
    process<BookingCreateRequest, BookingCreateResponse>(appSetting, clCreate, "create")
}

val clRead: KClass<*> = ApplicationCall::readBooking::class
suspend fun ApplicationCall.readBooking(appSetting: MkplAppSetting) {
    process<BookingReadRequest, BookingReadResponse>(appSetting, clRead, "read")
}

val clUpdate: KClass<*> = ApplicationCall::updateBooking::class
suspend fun ApplicationCall.updateBooking(appSetting: MkplAppSetting) {
    process<BookingUpdateRequest, BookingUpdateResponse>(appSetting, clUpdate, "update")
}

val clDelete: KClass<*> = ApplicationCall::deleteBooking::class
suspend fun ApplicationCall.deleteBooking(appSetting: MkplAppSetting) {
    process<BookingDeleteRequest, BookingDeleteResponse>(appSetting, clDelete, "delete")
}