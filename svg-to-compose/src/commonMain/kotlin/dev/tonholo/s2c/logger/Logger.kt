package dev.tonholo.s2c.logger

import com.rsicarelli.fakt.Fake

/**
 * Abstraction for logging operations.
 */
@Fake
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

    /**
     * Prints an empty line unless output is silenced.
     */
    fun printEmpty()
}
