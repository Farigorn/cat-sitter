import com.otus.sy.catsitter.api.model.*
import models.*
import ru.otus.otuskotlin.marketplace.common.exceptions.UnknownMkplCommand

fun MkplContext.toTransport(): IResponse = when (val cmd = command) {
    MkplCommand.CREATE -> toTransportCreate()
    MkplCommand.READ -> toTransportRead()
    MkplCommand.UPDATE -> toTransportUpdate()
    MkplCommand.DELETE -> toTransportDelete()
    MkplCommand.NONE -> throw UnknownMkplCommand(cmd)
}

fun MkplContext.toTransportCreate() = BookingCreateResponse(
    result = state.toResult(),
    errors = errors.map { it.toTransportAd() },
    booking = bookingResponse.toTransportBooking()
)

fun MkplContext.toTransportRead() = BookingReadResponse(
    result = state.toResult(),
    errors = errors.map { it.toTransportAd() },
    booking = bookingResponse.toTransportBooking()
)

fun MkplContext.toTransportUpdate() = BookingUpdateResponse(
    result = state.toResult(),
    errors = errors.map { it.toTransportAd() },
    booking = bookingResponse.toTransportBooking()
)

fun MkplContext.toTransportDelete() = BookingDeleteResponse(
    result = state.toResult(),
    errors = errors.map { it.toTransportAd() },
    booking = bookingResponse.toTransportBooking()
)

private fun MkplBooking.toTransportBooking(): BookingResponseObject = BookingResponseObject(
    id = id.takeIf { it != MkplBookingId.NONE }?.asUUID(),
    ownerId = ownerId.takeIf { it != MkplOwnerId.NONE }?.asUUID(),
    sitterId = sitterId.takeIf { it != MkplSitterId.NONE }?.asUUID(),
    startDate = startDate.takeIf { it.isNotBlank() },
    endDate = endDate.takeIf { it.isNotBlank() },
    notes = notes.takeIf { it.isNotBlank() },
    status = status.toStatus()
)

private fun MkplError.toTransportAd() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)

private fun MkplBookingStatus.toStatus(): BookingStatus = when (this) {
    MkplBookingStatus.NEW -> BookingStatus.NEW
    MkplBookingStatus.CANCELLED -> BookingStatus.CANCELLED
    MkplBookingStatus.REJECTED -> BookingStatus.REJECTED
    MkplBookingStatus.ACCEPTED -> BookingStatus.ACCEPTED
    MkplBookingStatus.NONE -> BookingStatus.CANCELLED
}

private fun MkplState.toResult(): ResponseResult? = when (this) {
    MkplState.RUNNING -> ResponseResult.SUCCESS
    MkplState.FAILING -> ResponseResult.ERROR
    MkplState.FINISHING -> ResponseResult.SUCCESS
    MkplState.NONE -> null
}