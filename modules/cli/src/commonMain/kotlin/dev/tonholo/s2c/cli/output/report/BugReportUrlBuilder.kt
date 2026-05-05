package dev.tonholo.s2c.cli.output.report

import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.output.RunConfig

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
 * The URL body includes the CLI version, host platform, run configuration,
 * and a summary of failed files. To avoid exceeding browser / GitHub URL
 * length limits, the body is truncated to a version + platform + error-code
 * summary when the full body would push the URL over [maxUrlLength].
 *
 * @param maxUrlLength the maximum allowed URL length; the builder truncates
 * the body section if the full URL would exceed this length.
 */
internal class BugReportUrlBuilder(private val maxUrlLength: Int = DEFAULT_MAX_URL_LENGTH) {
    fun build(
        version: String,
        platform: String,
        config: RunConfig,
        totalFiles: Int,
        succeeded: Int,
        failedFiles: List<BugReportFailure>,
        reportPath: String,
    ): String {
        val title = buildTitle(failedFiles = failedFiles)
        val fullBody = buildFullBody(
            version = version,
            platform = platform,
            config = config,
            totalFiles = totalFiles,
            succeeded = succeeded,
            failedFiles = failedFiles,
            reportPath = reportPath,
        )
        val full = assemble(title = title, body = fullBody)
        if (full.length <= maxUrlLength) return full

        val truncatedBody = buildTruncatedBody(
            version = version,
            platform = platform,
            failedFiles = failedFiles,
            reportPath = reportPath,
        )
        return assemble(title = title, body = truncatedBody)
    }

    private fun assemble(title: String, body: String): String = buildString {
        append(BASE_URL)
        append('?')
        append("template=")
        append(percentEncode(value = DEFAULT_TEMPLATE))
        append('&')
        append("title=")
        append(percentEncode(value = title))
        append('&')
        append("body=")
        append(percentEncode(value = body))
    }

    private fun buildTitle(failedFiles: List<BugReportFailure>): String {
        val codes = failedFiles.map { it.errorCode.name }.distinct()
        val joined = codes.joinToString(separator = ", ")
        return "$TITLE_PREFIX$joined"
    }

    private fun buildFullBody(
        version: String,
        platform: String,
        config: RunConfig,
        totalFiles: Int,
        succeeded: Int,
        failedFiles: List<BugReportFailure>,
        reportPath: String,
    ): String = buildString {
        appendCommonHeader(version = version, platform = platform)
        append("Input: ")
        appendLine(config.inputPath)
        append("Output: ")
        appendLine(config.outputPath)
        append("Optimize: ")
        appendLine(if (config.optimizationEnabled) "on" else "off")
        appendLine()
        append("Failed (")
        append(failedFiles.size)
        append('/')
        append(totalFiles)
        append(", succeeded=")
        append(succeeded)
        appendLine("):")
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
        appendLine("Full error details are saved locally in:")
        appendLine(reportPath)
        appendLine()
        appendLine(
            "Please attach a minimal reproducing SVG if possible. " +
                "Do not paste proprietary SVG content.",
        )
    }

    private fun buildTruncatedBody(
        version: String,
        platform: String,
        failedFiles: List<BugReportFailure>,
        reportPath: String,
    ): String = buildString {
        appendCommonHeader(version = version, platform = platform)
        val codes = failedFiles.map { it.errorCode.name }.distinct()
        append("Error codes: ")
        appendLine(codes.joinToString(separator = ", "))
        appendLine()
        appendLine(
            "Body truncated to fit URL length limit. " +
                "See the saved log file for full details:",
        )
        appendLine(reportPath)
    }

    private fun StringBuilder.appendCommonHeader(version: String, platform: String) {
        append("svg-to-compose v")
        appendLine(version)
        append("Platform: ")
        appendLine(platform)
        appendLine()
    }

    companion object {
        private const val BASE_URL =
            "https://github.com/rafaeltonholo/svg-to-compose/issues/new"
        private const val DEFAULT_TEMPLATE = "bug_report.md"
        private const val TITLE_PREFIX = "[Bug] Conversion failed: "

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
 *
 * Exposed at package level so tests and callers don't each reinvent it.
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
