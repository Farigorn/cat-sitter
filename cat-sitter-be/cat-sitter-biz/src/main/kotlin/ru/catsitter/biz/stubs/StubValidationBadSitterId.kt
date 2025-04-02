package ru.catsitter.biz.stubs

import MkplContext
import catsitter.cor.ICorChainDsl
import catsitter.cor.worker
import helpers.fail
import models.MkplError
import models.MkplState
import stubs.MkplStubs

fun ICorChainDsl<MkplContext>.stubValidationBadSitterId(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки валидации для sitter_id
    """.trimIndent()

    on { stubCase == MkplStubs.BAD_SITTER_ID && state == MkplState.RUNNING }
    handle {
        fail(
            MkplError(
                group = "validation",
                code = "validation-title",
                field = "sitter_id",
                message = "Wrong sitter_id field"
            )
        )
    }
}