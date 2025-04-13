package ru.catsitter.biz.stubs

import MkplContext
import catsitter.cor.ICorChainDsl
import catsitter.cor.worker
import helpers.fail
import models.MkplError
import models.MkplState

fun ICorChainDsl<MkplContext>.stubNoCase(title: String) = worker {
    this.title = title
    this.description = """
        Валидируем ситуацию, когда запрошен кейс, который не поддерживается в стабах
    """.trimIndent()
    on { state == MkplState.RUNNING }
    handle {
        fail(
            MkplError(
                code = "validation",
                field = "stub",
                group = "validation",
                message = "Wrong stub case is requested: ${stubCase.name}"
            )
        )
    }
}