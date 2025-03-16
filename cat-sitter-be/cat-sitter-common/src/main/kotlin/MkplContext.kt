import kotlinx.datetime.Instant
import models.*
import stubs.MkplStubs

data class MkplContext(
    var command: MkplCommand = MkplCommand.NONE,
    var state: MkplState = MkplState.NONE,
    val errors: MutableList<MkplError> = mutableListOf(),
    var workMode: MkplWorkMode = MkplWorkMode.PROD,
    var stubCase: MkplStubs = MkplStubs.NONE,

    var requestId: MkplRequestId = MkplRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var bookingRequest: MkplBooking = MkplBooking(),

    var bookingResponse: MkplBooking = MkplBooking(),
    var bookingResponses: MutableList<MkplBooking> = mutableListOf(),

    )
