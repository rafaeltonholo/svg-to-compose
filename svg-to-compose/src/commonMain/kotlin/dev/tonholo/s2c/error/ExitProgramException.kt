package dev.tonholo.s2c.error

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
        val name = this::class.qualifiedName
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

inline fun ExitProgramException(
    errorCode: ErrorCode,
    message: String,
    throwable: Throwable? = null,
): ExitProgramException = ExitProgramException(
    errorCode,
    message,
    causes = if (throwable == null) arrayOf() else arrayOf(throwable),
)
