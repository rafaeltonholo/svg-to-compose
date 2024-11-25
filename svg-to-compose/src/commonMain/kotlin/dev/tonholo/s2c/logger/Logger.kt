package dev.tonholo.s2c.logger

/**
 * Abstraction for logging operations.
 */
interface Logger {
    /**
     * Logs a debug message.
     *
     * @param message The message to log.
     */
    fun debug(message: Any)

    /**
     * Executes a block of code within a debug section.
     *
     * @param title The title of the debug section.
     * @param block The code to execute within the section.
     * @return The result of the block execution.
     */
    fun <T> debugSection(title: String, block: () -> T): T

    /**
     * Executes a block of code within a verbose section.
     *
     * @param title The title of the verbose section.
     * @param block The code to execute within the section.
     * @return The result of the block execution.
     */
    fun <T> verboseSection(title: String, block: () -> T): T

    /**
     * Logs a verbose message.
     *
     * @param message The message to log.
     */
    fun verbose(message: String)

    /**
     * Logs a warning message.
     *
     * @param message The message to log.
     * @param throwable An optional throwable associated with the warning.
     */
    fun warn(message: String, throwable: Throwable? = null)

    /**
     * Logs an info message.
     *
     * @param message The message to log.
     */
    fun info(message: String)

    /**
     * Logs an output message.
     *
     * @param message The message to log.
     */
    fun output(message: String)

    /**
     * Logs an error message.
     *
     * @param message The message to log.
     * @param throwable An optional throwable associated with the error.
     */
    fun error(message: String, throwable: Throwable? = null)
}

// TODO(https://github.com/rafaeltonholo/svg-to-compose/issues/85): Remove after passing logger via dependency.
private val logger = CommonLogger()

@Deprecated(
    "Use logger instead.",
    replaceWith = ReplaceWith(
        expression = "logger.debug(message)",
    ),
)
internal fun debug(message: Any) = logger.debug(message)

@Deprecated(
    "Use logger instead.",
    replaceWith = ReplaceWith(
        expression = "logger.debugSection(title, block)",
    ),
)
internal fun <T> debugSection(title: String, block: () -> T): T =
    logger.debugSection(title, block)

@Deprecated(
    "Use logger instead.",
    replaceWith = ReplaceWith(
        expression = "logger.verboseSection(title, block)",
    ),
)
internal fun <T> verboseSection(title: String, block: () -> T) =
    logger.verboseSection(title, block)

@Deprecated(
    "Use logger instead.",
    replaceWith = ReplaceWith(
        expression = "logger.verbose(message)",
    ),
)
internal fun verbose(message: String) = logger.verbose(message)

@Suppress("ForbiddenComment")
@Deprecated(
    "Use logger instead.",
    replaceWith = ReplaceWith(
        expression = "logger.warn(message)",
    ),
)
internal fun warn(message: String, throwable: Throwable? = null) = logger.warn(message, throwable)

@Deprecated(
    "Use logger instead.",
    replaceWith = ReplaceWith(
        expression = "logger.output(message)",
    ),
)
internal fun output(message: String) = logger.output(message)

internal fun printEmpty() {
    if (!AppConfig.silent) {
        println()
    }
}
