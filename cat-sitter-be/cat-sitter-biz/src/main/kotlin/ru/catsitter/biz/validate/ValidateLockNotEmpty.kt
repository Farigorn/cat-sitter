package ru.catsitter.biz.validate

import MkplContext
import catsitter.cor.ICorChainDsl
import catsitter.cor.worker
import helpers.errorValidation
import helpers.fail

fun ICorChainDsl<MkplContext>.validateLockNotEmpty(title: String) = worker {
    this.title = title
    on { bookingValidating.lock.asString().isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "lock",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}
