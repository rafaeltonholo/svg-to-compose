package dev.tonholo.s2c.cli.output.report

/**
 * The command line the user executed, reconstructed from the raw process
 * arguments at startup. Carried through injection so bug reports show the
 * exact invocation instead of a lossy re-serialization of parsed flags.
 */
internal data class InvocationCommand(val value: String)

/**
 * Joins [args] into a copy-pasteable `s2c` command. Arguments containing
 * whitespace or double quotes are wrapped in double quotes with embedded
 * quotes escaped.
 */
internal fun formatCommandLine(args: List<String>): String =
    (listOf(BINARY_NAME) + args.map(::quoteIfNeeded)).joinToString(separator = " ")

private fun quoteIfNeeded(arg: String): String {
    val needsQuoting = arg.isEmpty() || arg.contains('"') || arg.any { it.isWhitespace() }
    if (!needsQuoting) return arg
    val escaped = arg.replace(oldValue = "\"", newValue = "\\\"")
    return "\"$escaped\""
}

private const val BINARY_NAME = "s2c"
