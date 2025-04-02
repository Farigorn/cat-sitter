package ru.catsitter.biz

import MkplContext
import MkplCorSettings
import catsitter.cor.rootChain
import catsitter.cor.worker
import models.MkplBookingId
import models.MkplBookingLock
import models.MkplCommand
import models.MkplState
import ru.catsitter.biz.general.operation
import ru.catsitter.biz.stubs.*
import ru.catsitter.biz.validate.*
import ru.otus.otuskotlin.marketplace.biz.stubs.stubDbError
import ru.otus.otuskotlin.marketplace.biz.stubs.stubValidationBadId
import ru.otus.otuskotlin.marketplace.biz.stubs.stubValidationBadOwnerId

@SuppressWarnings("unused")
class MkplBookingProcessor(private val corSettings: MkplCorSettings = MkplCorSettings.NONE) {

    suspend fun exec(ctx: MkplContext) = businessChain.exec(ctx.also { it.corSettings = corSettings })

    private val businessChain = rootChain<MkplContext> {
        worker("Установим статус") {
            state = MkplState.RUNNING
        }
        operation("Создание бронирования", MkplCommand.CREATE) {
            stubs("Обработка стабов") {
                stubCreateSuccess("Имитируем успешную обработку", corSettings)
                stubValidationBadOwnerId("Имитация ошибки валидации ownerId")
                stubValidationBadSitterId("Имитация ошибки валидации sitterId")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в adValidating") { bookingValidating = bookingRequest.deepCopy() }
                worker("Очистка id") { bookingValidating.id = MkplBookingId.NONE }
                validateOwnerIdIsNotEmpty("Проверка на пустой ownerId")
                validateSitterIdIsNotEmpty("Проверка на пустой sitterId")
            }
        }

        operation("Получить бронирование", MkplCommand.READ) {
            stubs("Обработка стабов") {
                stubReadSuccess("Имитируем успешную обработку", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в adValidating") { bookingValidating = bookingRequest.deepCopy() }
                worker("Очистка id") { bookingValidating.id = MkplBookingId.NONE }
                validateIdNotEmpty("Проверка на пустой id")
            }
        }
        operation("Обновить бронирование", MkplCommand.UPDATE) {
            stubs("Обработка стабов") {
                stubUpdateSuccess("Имитируем успешную обработку", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в adValidating") { bookingValidating = bookingRequest.deepCopy() }
                worker("Очистка lock") { bookingValidating.lock = MkplBookingLock(bookingValidating.lock.asString().trim()) }
                validateIdNotEmpty("Проверка на непустой id")
                validateLockNotEmpty("Проверка на непустой lock")
                validateLockProperFormat("Проверка формата lock")
            }
        }
        operation("Удалить бронирование", MkplCommand.DELETE) {
            stubs("Обработка стабов") {
                stubDeleteSuccess("Имитируем успешную обработку", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в adValidating") { bookingValidating = bookingRequest.deepCopy() }
                worker("Очистка lock") { bookingValidating.lock = MkplBookingLock(bookingValidating.lock.asString().trim()) }
                validateIdNotEmpty("Проверка на непустой id")
                validateLockNotEmpty("Проверка на непустой lock")
                validateLockProperFormat("Проверка формата lock")
            }
        }
    }.build()
}