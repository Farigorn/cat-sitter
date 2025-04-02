package ru.catsitter.biz.validate

import MkplContext
import catsitter.cor.ICorChainDsl
import catsitter.cor.worker
import helpers.errorValidation
import helpers.fail
import models.MkplBookingId

fun ICorChainDsl<MkplContext>.validateIdNotEmpty(title: String) = worker {
    this.title = title
    on { bookingValidating.id == MkplBookingId.NONE }
    handle {
        fail(
            errorValidation(
                field = "id",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}
