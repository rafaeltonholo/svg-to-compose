package dev.tonholo.s2c.cli.output.report

/**
 * The command line the user executed, reconstructed from the raw process
 * arguments at startup. Carried through injection so bug reports show the
 * exact invocation instead of a lossy re-serialization of parsed flags.
 */
internal data class InvocationCommand(val value: String)

/**
 * Joins [args] into a copy-pasteable `s2c` command. Arguments containing
 * anything shell-sensitive are wrapped in double quotes with the characters
 * that stay active inside POSIX double quotes (`\`, `"`, `$`, and backtick)
 * escaped, so the command survives pasting into a shell unchanged.
 */
internal fun formatCommandLine(args: List<String>): String =
    (listOf(BINARY_NAME) + args.map(::quoteIfNeeded)).joinToString(separator = " ")

private fun quoteIfNeeded(arg: String): String {
    if (arg.isNotEmpty() && arg.all(::isShellSafe)) return arg
    val escaped = buildString(capacity = arg.length) {
        for (char in arg) {
            if (char in ESCAPED_IN_QUOTES) append('\\')
            append(char)
        }
    }
    return "\"$escaped\""
}

private fun isShellSafe(char: Char): Boolean = char.isLetterOrDigit() || char in SAFE_PUNCTUATION

private const val BINARY_NAME = "s2c"
private const val SAFE_PUNCTUATION = "-_./=:@,+%"
private val ESCAPED_IN_QUOTES = setOf('\\', '"', '$', '`')
