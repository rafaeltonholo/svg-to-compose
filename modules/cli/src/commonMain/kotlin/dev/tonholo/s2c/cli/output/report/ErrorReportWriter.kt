package dev.tonholo.s2c.cli.output.report

import dev.tonholo.s2c.output.RunConfig
import okio.FileSystem
import okio.IOException
import okio.Path
import kotlin.time.Instant

/**
 * Writes a user-facing error report next to the working directory when one
 * or more files failed to convert. The report is intended for pasting into
 * a GitHub issue and deliberately omits proprietary SVG content; it carries
 * version, platform, run configuration, and the per-file failure summary.
 *
 * The report is only written when [failedFiles] is non-empty; [write] is a
 * no-op that returns `null` for successful runs.
 *
 * @param fileSystem destination file system (real or a test fake).
 * @param workingDir directory the report file is written into.
 * @param nowEpochMillis supplier for the current epoch time; injected so
 * tests can pin the filename timestamp.
 * @param platformInfoProvider supplier returning a human-readable platform
 * descriptor; injected so the expect/actual `platformInfo()` does not need
 * to be called from shared tests.
 */
internal class ErrorReportWriter(
    private val fileSystem: FileSystem,
    private val workingDir: Path,
    private val nowEpochMillis: () -> Long,
    private val platformInfoProvider: () -> String,
) {
    @Throws(IOException::class)
    fun write(
        version: String,
        config: RunConfig,
        totalFiles: Int,
        succeeded: Int,
        failedFiles: List<BugReportFailure>,
        stackTraceEnabled: Boolean,
    ): Path? {
        if (failedFiles.isEmpty()) return null

        val timestamp = formatTimestamp(epochMillis = nowEpochMillis())
        val reportPath = workingDir / "s2c-errors-$timestamp.log"

        val report = buildReport(
            timestamp = timestamp,
            version = version,
            config = config,
            totalFiles = totalFiles,
            succeeded = succeeded,
            failedFiles = failedFiles,
            stackTraceEnabled = stackTraceEnabled,
        )
        fileSystem.write(file = reportPath) { writeUtf8(report) }
        return reportPath
    }

    private fun buildReport(
        timestamp: String,
        version: String,
        config: RunConfig,
        totalFiles: Int,
        succeeded: Int,
        failedFiles: List<BugReportFailure>,
        stackTraceEnabled: Boolean,
    ): String = buildString {
        appendHeader(
            timestamp = timestamp,
            version = version,
            config = config,
            totalFiles = totalFiles,
        )
        appendLine()
        append("Failed files (")
        append(failedFiles.size)
        append('/')
        append(totalFiles)
        append(", succeeded=")
        append(succeeded)
        appendLine("):")
        appendLine()
        for (entry in failedFiles) {
            appendFailedEntry(entry = entry, stackTraceEnabled = stackTraceEnabled)
        }
    }

    private fun StringBuilder.appendHeader(
        timestamp: String,
        version: String,
        config: RunConfig,
        totalFiles: Int,
    ) {
        append("svg-to-compose v")
        appendLine(version)
        append("Date: ")
        appendLine(timestamp)
        append("Platform: ")
        appendLine(platformInfoProvider())
        append("Input: ")
        append(config.inputPath)
        append(" (")
        append(totalFiles)
        appendLine(" files)")
        append("Output: ")
        appendLine(config.outputPath)
        append("Optimize: ")
        appendLine(if (config.optimizationEnabled) "on" else "off")
    }

    private fun StringBuilder.appendFailedEntry(
        entry: BugReportFailure,
        stackTraceEnabled: Boolean,
    ) {
        append('[')
        append(entry.errorCode.name)
        append("] ")
        appendLine(entry.fileName)
        val firstLine = entry.message.lineSequence().firstOrNull().orEmpty()
        if (firstLine.isNotEmpty()) {
            append("  ")
            appendLine(firstLine)
        }
        val trace = entry.stackTrace
        if (stackTraceEnabled && !trace.isNullOrBlank()) {
            for (line in trace.trimEnd().lineSequence()) {
                append("  ")
                appendLine(line)
            }
        }
        appendLine()
    }

    /**
     * Produces a filesystem-safe ISO-8601 timestamp such as
     * `2026-04-03T14-30-00` (colons replaced by hyphens so the filename is
     * usable across Windows as well as POSIX-like filesystems).
     */
    private fun formatTimestamp(epochMillis: Long): String {
        val iso = Instant.fromEpochMilliseconds(epochMillis).toString()
        // `Instant.toString()` yields, e.g., "2026-04-03T14:30:00Z" or
        // "2026-04-03T14:30:00.123Z"; strip fractional seconds + trailing Z
        // and normalize colons for Windows compatibility.
        val withoutFraction = iso.substringBefore(delimiter = '.', missingDelimiterValue = iso)
        val withoutZone = withoutFraction.removeSuffix(suffix = "Z")
        return withoutZone.replace(oldChar = ':', newChar = '-')
    }
}
