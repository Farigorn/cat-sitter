package ru.otus.otuskotlin.marketplace.biz.stubs

import MkplContext
import catsitter.cor.ICorChainDsl
import catsitter.cor.worker
import helpers.fail
import models.MkplError
import models.MkplState
import stubs.MkplStubs


fun ICorChainDsl<MkplContext>.stubValidationBadOwnerId(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки валидации для owner_id
    """.trimIndent()

    on { stubCase == MkplStubs.BAD_OWNER_ID && state == MkplState.RUNNING }
    handle {
        fail(
            MkplError(
                group = "validation",
                code = "validation-title",
                field = "owner_id",
                message = "Wrong owner_id field"
            )
        )
    }
}
