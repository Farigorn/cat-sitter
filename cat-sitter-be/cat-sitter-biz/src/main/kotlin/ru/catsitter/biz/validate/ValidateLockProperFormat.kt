package ru.catsitter.biz.validate

import MkplContext
import catsitter.cor.ICorChainDsl
import catsitter.cor.worker
import helpers.errorValidation
import helpers.fail
import models.MkplBookingLock


fun ICorChainDsl<MkplContext>.validateLockProperFormat(title: String) = worker {
    this.title = title

    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { bookingValidating.lock != MkplBookingLock.NONE && !bookingValidating.lock.asString().matches(regExp) }
    handle {
        val encodedId = bookingValidating.lock.asString()
        fail(
            errorValidation(
                field = "lock",
                violationCode = "badFormat",
                description = "value $encodedId must contain only"
            )
        )
    }
}
