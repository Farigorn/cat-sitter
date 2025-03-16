import helpers.asMkplError
import kotlinx.datetime.Clock
import models.MkplCommand
import models.MkplState
import kotlin.reflect.KClass

suspend inline fun <T> IMkplAppSettings.controllerHelper(
    crossinline getRequest: suspend MkplContext.() -> Unit,
    crossinline toResponse: suspend MkplContext.() -> T,
    clazz: KClass<*>,
    logId: String,
): T {
    val logger = corSettings.loggerProvider.logger(clazz)
    val ctx = MkplContext(
        timeStart = Clock.System.now(),
        command = when (logId) {
            "create" -> MkplCommand.CREATE
            "update" -> MkplCommand.UPDATE
            "delete" -> MkplCommand.DELETE
            "read" -> MkplCommand.READ
            else -> MkplCommand.NONE
        }
    )
    return try {
        ctx.getRequest()
        logger.info(
            msg = "Request $logId started for ${clazz.simpleName}",
            marker = "BIZ",
            data = ctx.toLog(logId)
        )
        processor.exec(ctx)
        logger.info(
            msg = "Request $logId processed for ${clazz.simpleName}",
            marker = "BIZ",
            data = ctx.toLog(logId)
        )
        ctx.toResponse()
    } catch (e: Throwable) {
        logger.error(
            msg = "Request $logId failed for ${clazz.simpleName}",
            marker = "BIZ",
            data = ctx.toLog(logId),
            e = e,
        )
        ctx.state = MkplState.FAILING
        ctx.errors.add(e.asMkplError())
        processor.exec(ctx)
        if (ctx.command == MkplCommand.NONE) {
            ctx.command = MkplCommand.READ
        }
        ctx.toResponse()
    }
}