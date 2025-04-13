import models.*
import java.util.*

object MkplBookingStub {

    fun prepareResult(block: MkplBooking.() -> Unit): MkplBooking = get().apply(block)

    fun get() = MkplBooking(
        id = MkplBookingId(UUID.fromString("00000000-0000-0000-0000-000000000003")),
        ownerId = MkplOwnerId(UUID.fromString("00000000-0000-0000-0000-000000000001")),
        sitterId = MkplSitterId(UUID.fromString("00000000-0000-0000-0000-000000000002")),
        startDate = "startDate",
        endDate = "endDate",
        notes = "не пьет не курит, ласковый",
        status = MkplBookingStatus.ACCEPTED
    )
}