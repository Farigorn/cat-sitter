package models

import java.util.UUID

@JvmInline
value class MkplOwnerId(private val id: UUID) {

    fun asUUID() = id

    companion object {
        val NONE = MkplOwnerId(UUID.fromString("00000000-0000-0000-0000-000000000000"))
    }
}