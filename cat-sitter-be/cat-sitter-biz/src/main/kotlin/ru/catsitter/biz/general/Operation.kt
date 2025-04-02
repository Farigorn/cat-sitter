package ru.catsitter.biz.general

import MkplContext
import catsitter.cor.ICorChainDsl
import catsitter.cor.chain
import models.MkplCommand
import models.MkplState

fun ICorChainDsl<MkplContext>.operation(
    title: String,
    command: MkplCommand,
    block: ICorChainDsl<MkplContext>.() -> Unit
) = chain {
    block()
    this.title = title
    on { this.command == command && state == MkplState.RUNNING }
}