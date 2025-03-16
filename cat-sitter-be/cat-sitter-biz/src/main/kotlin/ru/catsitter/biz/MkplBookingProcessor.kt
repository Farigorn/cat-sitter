package ru.catsitter.biz

import MkplBookingStub
import MkplContext
import MkplCorSettings
import models.MkplState
@SuppressWarnings("unused")
class MkplBookingProcessor(val MkplCorSettings: MkplCorSettings) {
    suspend fun exec(ctx: MkplContext) {
        ctx.bookingResponse = MkplBookingStub.get()
        ctx.state = MkplState.RUNNING
    }
}