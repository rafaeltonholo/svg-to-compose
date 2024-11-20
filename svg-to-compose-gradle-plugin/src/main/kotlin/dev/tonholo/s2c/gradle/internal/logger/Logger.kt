package dev.tonholo.s2c.gradle.internal.logger

import dev.tonholo.s2c.logger.Logger
import org.gradle.api.logging.Logger as GradleLogger

internal fun Logger(
    logger: GradleLogger,
): Logger = object : Logger {
    override fun debug(message: Any) {
        logger.debug(message.toString())
    }

    override fun <T> debugSection(title: String, block: () -> T): T {
        logger.debug("STARTING: $title")
        return block().also {
            logger.debug("ENDING: $title")
        }
    }

    override fun <T> verboseSection(title: String, block: () -> T): T = debugSection(title, block)

    override fun verbose(message: String) {
        logger.debug(message)
    }

    override fun warn(message: String) {
        logger.warn(message)
    }

    override fun output(message: String) {
        logger.info(message)
    }

    override fun error(message: String, throwable: Throwable) {
        logger.error(message, throwable)
    }
}
