package dev.tonholo.s2c.error

/**
 * Exception type that signals the program should terminate with a specific error code.
 *
 * This exception is designed to handle critical errors that require program termination,
 * associating them with a specific error code for proper exit status reporting. It supports
 * multiple causes, where the first cause is set as the primary cause via the standard
 * Exception mechanism, and additional causes are added as suppressed exceptions.
 *
 * The exception provides enhanced string representation that lists all non-null causes
 * when multiple causes are present, making it easier to debug complex error scenarios.
 *
 * @property errorCode The error code associated with this exception, indicating the type
 *  of error that occurred.
 * @property causes A variable number of throwable causes that led to this exception.
 *  Multiple causes are supported, with the first being set as the primary cause and
 *  subsequent ones added as suppressed exceptions.
 * @param message A descriptive message explaining why the program needs to exit.
 */
open class ExitProgramException(
    val errorCode: ErrorCode,
    message: String,
    private vararg val causes: Throwable?,
) : Exception(message, causes.singleOrNull()) {
    init {
        causes
            .filterNotNull()
            .takeIf { it.size > 1 }
            ?.forEach { cause ->
                addSuppressed(cause)
            }
    }

    override fun toString(): String {
        val name = this::class.simpleName ?: "ExitProgramException"
        return buildString {
            val causes = causes.filterNotNull()
            if (causes.isNotEmpty()) {
                appendLine("Causes:")
                appendLine(message?.let { "$name: $it" } ?: name)
                causes.forEach {
                    appendLine(it.toString())
                }
            } else {
                append(super.toString())
            }
        }
    }
}

/**
 * Factory function to create an [ExitProgramException] with an optional single cause.
 *
 * This is a convenience function that simplifies the creation of an [ExitProgramException]
 * when dealing with zero or one throwable cause. It converts the nullable throwable parameter
 * into the vararg causes array expected by the [ExitProgramException] constructor.
 *
 * @param errorCode The error code that identifies the type of error and determines the program exit status.
 * @param message The detailed error message describing what went wrong.
 * @param throwable The optional underlying cause of this exception. If null, the exception will have no causes.
 * @return A new [ExitProgramException] instance configured with the provided parameters.
 */
fun ExitProgramException(
    errorCode: ErrorCode,
    message: String,
    throwable: Throwable? = null,
): ExitProgramException = ExitProgramException(
    errorCode,
    message,
    causes = if (throwable == null) arrayOf() else arrayOf(throwable),
)
