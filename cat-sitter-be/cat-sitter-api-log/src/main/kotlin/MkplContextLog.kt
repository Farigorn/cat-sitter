import com.otus.sy.catsitter.api.model.BookingLog
import com.otus.sy.catsitter.api.model.CommonLogModel
import com.otus.sy.catsitter.api.model.ErrorLogModel
import com.otus.sy.catsitter.api.model.MkplLogModel
import kotlinx.datetime.Clock
import models.*

fun MkplContext.toLog(logId: String) = CommonLogModel(
    messageTime = Clock.System.now().toString(),
    logId = logId,
    source = "cat-sitter",
    log = toMkplLog(),
    errors = errors.map { it.toLog() },
)

private fun MkplContext.toMkplLog(): MkplLogModel? {
    val bookingNone = MkplBooking()
    return MkplLogModel(
        requestId = requestId.takeIf { it != MkplRequestId.NONE }?.asString(),
        request = bookingRequest.takeIf { it != bookingNone }?.toLog(),
        response = bookingResponse.takeIf { it != bookingNone }?.toLog(),
//        responseAds = adsResponse.takeIf { it.isNotEmpty() }?.filter { it != adNone }?.map { it.toLog() },
//        requestFilter = adFilterRequest.takeIf { it != MkplAdFilter() }?.toLog(),
    ).takeIf { it != MkplLogModel() }
}

private fun MkplError.toLog() = ErrorLogModel(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    code = code.takeIf { it.isNotBlank() },
    level = level.name,
)

private fun MkplBooking.toLog() = BookingLog(
    id = id.takeIf { it != MkplBookingId.NONE }.toString(),
    ownerId = ownerId.takeIf { it != MkplOwnerId.NONE }?.asUUID(),
    sitterId = sitterId.takeIf { it != MkplSitterId.NONE }?.asUUID(),
    notes = notes.takeIf { it.isNotBlank() },
)