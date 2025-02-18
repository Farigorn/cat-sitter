import com.otus.sy.catsitter.api.model.*
import exceptions.UnknownRequestClass
import models.*
import stubs.MkplStubs
import java.util.UUID


fun MkplContext.fromTransport(request: IRequest) = when (request) {
    is BookingCreateRequest -> fromTransport(request)
    is BookingReadRequest -> fromTransport(request)
    is BookingUpdateRequest -> fromTransport(request)
    is BookingDeleteRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun UUID?.toBookingId() = this?.let { MkplBookingId(it) } ?: MkplBookingId.NONE
private fun UUID?.toSitterId() = this?.let { MkplSitterId(it) } ?: MkplSitterId.NONE
private fun UUID?.toOwnerId() = this?.let { MkplOwnerId(it) } ?: MkplOwnerId.NONE
private fun String?.toBookingLock() = this?.let { MkplBookingLock(it) } ?: MkplBookingLock.NONE


private fun BookingDebug?.transportToWorkMode(): MkplWorkMode = when (this?.mode) {
    BookingRequestDebugMode.PROD -> MkplWorkMode.PROD
    BookingRequestDebugMode.TEST -> MkplWorkMode.TEST
    BookingRequestDebugMode.STUB -> MkplWorkMode.STUB
    null -> MkplWorkMode.PROD
}

private fun BookingDebug?.transportToStubCase(): MkplStubs = when (this?.stub) {
    BookingRequestDebugStubs.SUCCESS -> MkplStubs.SUCCESS
    BookingRequestDebugStubs.NOT_FOUND -> MkplStubs.NOT_FOUND
    BookingRequestDebugStubs.BAD_ID -> MkplStubs.BAD_ID
    BookingRequestDebugStubs.BAD_TITLE -> MkplStubs.BAD_TITLE
    BookingRequestDebugStubs.BAD_DESCRIPTION -> MkplStubs.BAD_DESCRIPTION
    BookingRequestDebugStubs.BAD_VISIBILITY -> MkplStubs.BAD_VISIBILITY
    BookingRequestDebugStubs.CANNOT_DELETE -> MkplStubs.CANNOT_DELETE
    BookingRequestDebugStubs.BAD_SEARCH_STRING -> MkplStubs.BAD_SEARCH_STRING
    null -> MkplStubs.NONE
}

fun MkplContext.fromTransport(request: BookingCreateRequest) {
    command = MkplCommand.CREATE
    bookingRequest = request.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun MkplContext.fromTransport(request: BookingReadRequest) {
    command = MkplCommand.READ
    bookingRequest = request.booking.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun MkplContext.fromTransport(request: BookingUpdateRequest) {
    command = MkplCommand.UPDATE
    bookingRequest = request.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun MkplContext.fromTransport(request: BookingDeleteRequest) {
    command = MkplCommand.DELETE
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
    bookingRequest = request.booking.toInternal()
}

private fun BookingReadObject?.toInternal(): MkplBooking = if (this != null) {
    MkplBooking(id = id.toBookingId())
} else {
    MkplBooking()
}


private fun BookingDeleteObject?.toInternal(): MkplBooking = if (this != null) {
    MkplBooking(
        id = id.toBookingId(),
        lock = lock.toBookingLock(),
    )
} else {
    MkplBooking()
}

private fun BookingCreateRequest.toInternal(): MkplBooking = MkplBooking(
    ownerId = this.ownerId.toOwnerId(),
    sitterId = this.sitterId.toSitterId(),
    startDate = this.startDate ?: "",
    endDate = this.endDate ?: "",
    notes = this.notes ?: ""
)

private fun BookingUpdateRequest.toInternal(): MkplBooking = MkplBooking(
    id = this.id.toBookingId(),
    ownerId = this.ownerId.toOwnerId(),
    sitterId = this.sitterId.toSitterId(),
    startDate = this.startDate ?: "",
    endDate = this.endDate ?: "",
    notes = this.notes ?: ""
)