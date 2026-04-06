package dev.tonholo.s2c.logger

/**
 * A [Logger] implementation that silently discards all log messages.
 *
 * Used as a default logger for deprecated `materialize()` methods
 * and other contexts where logging is not needed.
 */
internal object NoOpLogger : Logger {
    override fun debug(message: Any) = Unit
    override fun <T> debugSection(title: String, block: () -> T): T = block()
    override fun <T> verboseSection(title: String, block: () -> T): T = block()
    override fun verbose(message: String) = Unit
    override fun warn(message: String, throwable: Throwable?) = Unit
    override fun info(message: String) = Unit
    override fun output(message: String) = Unit
    override fun error(message: String, throwable: Throwable?) = Unit
    override fun printEmpty() = Unit
}
