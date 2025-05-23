package ru.catsitter.biz.stubs

import MkplContext
import MkplCorSettings
import catsitter.cor.ICorChainDsl
import catsitter.cor.worker
import models.MkplBookingStatus
import models.MkplState
import ru.catsitter.logging.common.LogLevel
import stubs.MkplStubs

fun ICorChainDsl<MkplContext>.stubReadSuccess(title: String, corSettings: MkplCorSettings) = worker {
    this.title = title
    this.description = """
        Кейс успеха для чтения бронирования
    """.trimIndent()
    on { stubCase == MkplStubs.SUCCESS && state == MkplState.RUNNING }
    val logger = corSettings.loggerProvider.logger("stubReadSuccess")
    handle {
        logger.doWithLogging(id = this.requestId.asString(), LogLevel.DEBUG) {
            state = MkplState.FINISHING
            val stub = MkplBookingStub.prepareResult {
                bookingRequest.notes.takeIf { it.isNotBlank() }?.also { this.notes = it }
                ownerId = bookingRequest.ownerId
                sitterId = bookingRequest.sitterId
                status = MkplBookingStatus.ACCEPTED
            }
            bookingResponse = stub
        }
    }
}