package controllers

import MkplContext
import apiMapper
import com.otus.sy.catsitter.api.model.IRequest
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Delivery
import config.MkplAppSettings
import controllerHelper
import fromTransport
import helpers.asMkplError
import models.MkplState
import toTransport

class RabbitDirectController(
    private val appSettings: MkplAppSettings
) : RabbitProcessorBase(
    rabbitConfig = appSettings.rabbit,
    exchangeConfig = appSettings.controllerConfig,
    loggerProvider = appSettings.corSettings.loggerProvider,
) {
    override suspend fun Channel.processMessage(message: Delivery) {
        appSettings.controllerHelper(
            {
                val req = apiMapper.readValue(message.body, IRequest::class.java)
                fromTransport(req)
            },
            {
                val res = toTransport()
                apiMapper.writeValueAsBytes(res).also {
                    basicPublish(exchangeConfig.exchange, exchangeConfig.keyOut, null, it)
                }
            },
            this@RabbitDirectController::class,
            "rabbitmq-processor"
        )
    }

    override fun Channel.onError(e: Throwable, delivery: Delivery) {
        val context = MkplContext()
        e.printStackTrace()
        context.state = MkplState.FAILING
        context.errors.add(e.asMkplError())
        val response = context.toTransport()
        apiMapper.writeValueAsBytes(response).also {
            basicPublish(exchangeConfig.exchange, exchangeConfig.keyOut, null, it)
        }


    }
}