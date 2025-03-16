package models

data class MkplBooking(
    var id: MkplBookingId = MkplBookingId.NONE,
    var ownerId: MkplOwnerId = MkplOwnerId.NONE,
    var sitterId: MkplSitterId = MkplSitterId.NONE,
    var startDate: String = "",
    var endDate: String = "",
    var notes: String = "",
    var status: MkplBookingStatus = MkplBookingStatus.NONE,
    var lock: MkplBookingLock = MkplBookingLock.NONE,
)



