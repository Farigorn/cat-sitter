package ru.catsitter.biz.stubs

import MkplContext
import catsitter.cor.ICorChainDsl
import catsitter.cor.chain
import models.MkplState
import models.MkplWorkMode


fun ICorChainDsl<MkplContext>.stubs(title: String, block: ICorChainDsl<MkplContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == MkplWorkMode.STUB && state == MkplState.RUNNING }
}
