package ru.otus.otuskotlin.marketplace.biz.stubs

import MkplContext
import catsitter.cor.ICorChainDsl
import catsitter.cor.worker
import helpers.fail
import models.MkplError
import models.MkplState
import stubs.MkplStubs


fun ICorChainDsl<MkplContext>.stubValidationBadId(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки валидации для идентификатора бронирования
    """.trimIndent()
    on { stubCase == MkplStubs.BAD_ID && state == MkplState.RUNNING }
    handle {
        fail(
            MkplError(
                group = "validation",
                code = "validation-id",
                field = "id",
                message = "Wrong id field"
            )
        )
    }
}
