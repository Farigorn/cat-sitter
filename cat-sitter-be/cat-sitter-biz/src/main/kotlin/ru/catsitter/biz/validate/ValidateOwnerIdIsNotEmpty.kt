package ru.catsitter.biz.validate

import MkplContext
import catsitter.cor.ICorChainDsl
import catsitter.cor.worker
import helpers.errorValidation
import helpers.fail
import models.MkplOwnerId


fun ICorChainDsl<MkplContext>.validateOwnerIdIsNotEmpty(title: String) = worker {
    this.title = title
    on { bookingValidating.ownerId == MkplOwnerId.NONE }
    handle {
        fail(
            errorValidation(
                field = "ownerId",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}