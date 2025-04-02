package ru.catsitter.biz.stubs

import MkplBookingStub
import MkplContext
import MkplCorSettings
import catsitter.cor.ICorChainDsl
import catsitter.cor.worker
import models.MkplBookingStatus
import models.MkplState
import ru.catsitter.logging.common.LogLevel
import stubs.MkplStubs

fun ICorChainDsl<MkplContext>.stubDeleteSuccess(title: String, corSettings: MkplCorSettings) = worker {
    this.title = title
    this.description = """
        Кейс успеха для удаления бронирования
    """.trimIndent()
    on { stubCase == MkplStubs.SUCCESS && state == MkplState.RUNNING }
    val logger = corSettings.loggerProvider.logger("stubDeleteSuccess")
    handle {
        logger.doWithLogging(id = this.requestId.asString(), LogLevel.DEBUG) {
            state = MkplState.FINISHING
            val stub = MkplBookingStub.prepareResult {
                bookingRequest.notes.takeIf { it.isNotBlank() }?.also { this.notes = it }
                bookingRequest.ownerId = ownerId
                bookingRequest.sitterId = sitterId
                status = MkplBookingStatus.REJECTED
            }
            bookingResponse = stub
        }
    }
}