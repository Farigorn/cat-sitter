package models

import java.util.UUID

@JvmInline
value class MkplBookingId(private val id: UUID) {
    fun asUUID() = id

    companion object {
        val NONE = MkplBookingId(UUID.fromString("00000000-0000-0000-0000-000000000000"))
    }
}