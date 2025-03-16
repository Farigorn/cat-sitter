package models

import java.util.UUID
@JvmInline
value class MkplSitterId(private val id: UUID) {

    fun asUUID() = id

    companion object {
        val NONE = MkplSitterId(UUID.fromString("00000000-0000-0000-0000-000000000000"))
    }
}