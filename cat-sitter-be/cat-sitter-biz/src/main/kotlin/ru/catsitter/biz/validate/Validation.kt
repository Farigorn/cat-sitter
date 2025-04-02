package ru.catsitter.biz.validate

import MkplContext
import catsitter.cor.ICorChainDsl
import catsitter.cor.chain
import models.MkplState

fun ICorChainDsl<MkplContext>.validation(block: ICorChainDsl<MkplContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == MkplState.RUNNING }
}
