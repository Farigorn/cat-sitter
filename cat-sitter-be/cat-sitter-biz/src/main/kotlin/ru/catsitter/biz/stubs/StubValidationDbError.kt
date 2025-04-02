package ru.otus.otuskotlin.marketplace.biz.stubs

import MkplContext
import catsitter.cor.ICorChainDsl
import catsitter.cor.worker
import helpers.fail
import models.MkplError
import models.MkplState
import stubs.MkplStubs


fun ICorChainDsl<MkplContext>.stubDbError(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки базы данных
    """.trimIndent()
    on { stubCase == MkplStubs.DB_ERROR && state == MkplState.RUNNING }
    handle {
        fail(
            MkplError(
                group = "internal",
                code = "internal-db",
                message = "Internal error"
            )
        )
    }
}
