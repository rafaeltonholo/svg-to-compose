package dev.tonholo.s2c.gradle.internal.logger

import dev.tonholo.s2c.logger.Logger
import org.gradle.api.logging.LogLevel
import org.gradle.api.logging.Logger as GradleLogger

private lateinit var logLevel: LogLevel
internal fun Logger(
    logger: GradleLogger,
): Logger = object : Logger {

    init {
        if (::logLevel.isInitialized.not()) {
            logLevel = when {
                logger.isErrorEnabled -> LogLevel.ERROR
                logger.isQuietEnabled -> LogLevel.QUIET
                logger.isWarnEnabled -> LogLevel.WARN
                logger.isInfoEnabled -> LogLevel.INFO
                else -> LogLevel.DEBUG
            }
        }
    }

    override fun debug(message: Any) {
        logger.debug(message.toString())
    }

    override fun <T> debugSection(title: String, block: () -> T): T {
        logger.debug("STARTING: $title")
        return block().also {
            logger.debug("ENDING: $title")
        }
    }

    override fun <T> verboseSection(title: String, block: () -> T): T {
        logger.trace("STARTING: $title")
        return block().also {
            logger.trace("ENDING: $title")
        }
    }

    override fun verbose(message: String) {
        logger.trace(message)
    }

    override fun warn(message: String) {
        logger.warn(message)
    }

    override fun info(message: String) {
        logger.info(message)
    }

    override fun output(message: String) {
        if (logLevel != LogLevel.QUIET) {
            logger.lifecycle(message)
        }
    }

    override fun error(message: String, throwable: Throwable?) {
        logger.error(message, throwable)
    }
}

@Suppress("UnusedReceiverParameter")
internal fun Logger.setLogLevel(level: LogLevel) {
    logLevel = level
}
