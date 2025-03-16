package models
@JvmInline
value class MkplBookingLock(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = MkplBookingLock("")
    }
}
