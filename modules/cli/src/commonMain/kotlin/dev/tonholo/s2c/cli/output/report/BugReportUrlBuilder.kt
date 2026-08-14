package dev.tonholo.s2c.cli.output.report

import dev.tonholo.s2c.error.ErrorCode

/**
 * A single failed conversion included in a bug report.
 *
 * @property fileName the file that failed.
 * @property errorCode the [ErrorCode] assigned to the failure.
 * @property message the first-line error message.
 * @property stackTrace the optional exception stack trace captured by the
 * processor. Only emitted into the saved error report file when
 * `--stacktrace` is enabled; it is never included in the bug report URL
 * body because stack traces are routinely too large to fit.
 */
internal data class BugReportFailure(
    val fileName: String,
    val errorCode: ErrorCode,
    val message: String,
    val stackTrace: String? = null,
)

/**
 * Builds a pre-filled "New Issue" URL on the svg-to-compose GitHub repository
 * so users can report conversion failures without copying terminal output.
 *
 * The repository uses a YAML issue form (`.github/ISSUE_TEMPLATE/bug_report.yml`),
 * which ignores the classic `body` query parameter; each form field is
 * pre-filled through a query parameter named after its field id, mapped by
 * the field's intent:
 * - `bug-description`: a short failure summary (counts and error codes).
 * - `reproduction-steps`: the command line the user actually executed.
 * - `environment`: OS and CLI version.
 * - `input-output`: the per-file error output plus a request to attach a
 *   reproducing input file.
 *
 * To avoid exceeding browser / GitHub URL length limits, the per-file output
 * is dropped and the description points the reporter at the locally saved
 * error report when the full URL would exceed [maxUrlLength].
 *
 * @param maxUrlLength the maximum allowed URL length before the truncated
 * variant is produced.
 */
internal class BugReportUrlBuilder(private val maxUrlLength: Int = DEFAULT_MAX_URL_LENGTH) {
    fun build(
        version: String,
        platform: String,
        commandLine: String,
        totalFiles: Int,
        succeeded: Int,
        failedFiles: List<BugReportFailure>,
    ): String {
        val title = buildTitle(failedFiles = failedFiles)
        val reproductionSteps = buildReproductionSteps(commandLine = commandLine)
        val environment = buildEnvironment(version = version, platform = platform)
        val full = assemble(
            title = title,
            description = buildDescription(
                totalFiles = totalFiles,
                succeeded = succeeded,
                failedFiles = failedFiles,
                fileListOmitted = false,
            ),
            reproductionSteps = reproductionSteps,
            environment = environment,
            inputOutput = buildInputOutput(failedFiles = failedFiles),
        )
        if (full.length <= maxUrlLength) return full

        return assemble(
            title = title,
            description = buildDescription(
                totalFiles = totalFiles,
                succeeded = succeeded,
                failedFiles = failedFiles,
                fileListOmitted = true,
            ),
            reproductionSteps = reproductionSteps,
            environment = environment,
            inputOutput = null,
        )
    }

    private fun assemble(
        title: String,
        description: String,
        reproductionSteps: String,
        environment: String,
        inputOutput: String?,
    ): String = buildString {
        append(BASE_URL)
        appendQueryParam(name = "template", value = ISSUE_FORM_FILE, first = true)
        appendQueryParam(name = "title", value = title)
        appendQueryParam(name = FIELD_BUG_DESCRIPTION, value = description)
        appendQueryParam(name = FIELD_REPRODUCTION_STEPS, value = reproductionSteps)
        appendQueryParam(name = FIELD_ENVIRONMENT, value = environment)
        if (inputOutput != null) {
            appendQueryParam(name = FIELD_INPUT_OUTPUT, value = inputOutput)
        }
    }

    private fun StringBuilder.appendQueryParam(name: String, value: String, first: Boolean = false) {
        append(if (first) '?' else '&')
        append(name)
        append('=')
        append(percentEncode(value = value))
    }

    private fun buildTitle(failedFiles: List<BugReportFailure>): String {
        val joined = failedFiles.distinctCodes().joinToString(separator = ", ")
        return "$TITLE_PREFIX$joined"
    }

    private fun buildDescription(
        totalFiles: Int,
        succeeded: Int,
        failedFiles: List<BugReportFailure>,
        fileListOmitted: Boolean,
    ): String = buildString {
        append("The CLI failed to convert ")
        append(failedFiles.size)
        append(" of ")
        append(totalFiles)
        append(" files (")
        append(succeeded)
        appendLine(" succeeded).")
        append("Error codes: ")
        appendLine(failedFiles.distinctCodes().joinToString(separator = ", "))
        if (fileListOmitted) {
            appendLine()
            appendLine("File list omitted for URL length; paste it here from the saved error report.")
        }
    }

    private fun buildReproductionSteps(commandLine: String): String = buildString {
        append("1. Run `")
        append(commandLine)
        appendLine("`")
        appendLine("2. The conversion fails with the output in the Input and Output section.")
    }

    private fun buildEnvironment(version: String, platform: String): String = buildString {
        append("- OS: ")
        appendLine(platform)
        append("- svg-to-compose version: ")
        appendLine(version)
    }

    private fun buildInputOutput(failedFiles: List<BugReportFailure>): String = buildString {
        appendLine("Error output:")
        for (entry in failedFiles) {
            append("- ")
            append(entry.errorCode.name)
            append(": ")
            append(entry.fileName)
            val firstLine = entry.message.lineSequence().firstOrNull().orEmpty()
            if (firstLine.isNotEmpty()) {
                append(" - ")
                append(firstLine)
            }
            appendLine()
        }
        appendLine()
        appendLine(
            "Please attach a minimal reproducing SVG if possible. " +
                "Do not paste proprietary SVG content.",
        )
    }

    private fun List<BugReportFailure>.distinctCodes(): List<String> =
        map { it.errorCode.name }.distinct()

    companion object {
        private const val BASE_URL =
            "https://github.com/rafaeltonholo/svg-to-compose/issues/new"
        private const val ISSUE_FORM_FILE = "bug_report.yml"
        private const val FIELD_BUG_DESCRIPTION = "bug-description"
        private const val FIELD_REPRODUCTION_STEPS = "reproduction-steps"
        private const val FIELD_ENVIRONMENT = "environment"
        private const val FIELD_INPUT_OUTPUT = "input-output"
        private const val TITLE_PREFIX = "[Bug]: Conversion failed: "

        /**
         * GitHub accepts long URLs but browsers and shells vary; 2000 chars
         * is the de facto safe limit (IE/MSDN guidance) still quoted by
         * hosting providers today. Anything beyond that can silently drop
         * the pre-filled body on the receiving end.
         */
        private const val DEFAULT_MAX_URL_LENGTH = 2000
    }
}

/**
 * Percent-encodes [value] according to the RFC 3986 `pct-encoded` / `unreserved`
 * rules for use inside a query string. Space is encoded as `%20`, not `+`.
 */
internal fun percentEncode(value: String): String {
    val bytes = value.encodeToByteArray()
    return buildString(capacity = bytes.size) {
        for (byte in bytes) {
            val intValue = byte.toInt() and HEX_MASK_BYTE
            val char = intValue.toChar()
            if (isUnreserved(char = char)) {
                append(char)
            } else {
                append('%')
                append(HEX_DIGITS[intValue shr HEX_NIBBLE_SHIFT])
                append(HEX_DIGITS[intValue and HEX_MASK_NIBBLE])
            }
        }
    }
}

private fun isUnreserved(char: Char): Boolean = char in 'A'..'Z' ||
    char in 'a'..'z' ||
    char in '0'..'9' ||
    char == '-' ||
    char == '_' ||
    char == '.' ||
    char == '~'

private const val HEX_MASK_BYTE = 0xFF
private const val HEX_MASK_NIBBLE = 0x0F
private const val HEX_NIBBLE_SHIFT = 4
private val HEX_DIGITS = "0123456789ABCDEF".toCharArray()
