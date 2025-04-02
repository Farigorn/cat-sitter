package ru.catsitter.biz.validate

import MkplContext
import catsitter.cor.ICorChainDsl
import catsitter.cor.worker
import helpers.errorValidation
import helpers.fail
import models.MkplSitterId

fun ICorChainDsl<MkplContext>.validateSitterIdIsNotEmpty(title: String) = worker {
    this.title = title
    on { bookingValidating.sitterId == MkplSitterId.NONE }
    handle {
        fail(
           errorValidation(
               field = "sitterId",
               violationCode = "empty",
               description = "field must not be empty"
           )
        )
    }
}