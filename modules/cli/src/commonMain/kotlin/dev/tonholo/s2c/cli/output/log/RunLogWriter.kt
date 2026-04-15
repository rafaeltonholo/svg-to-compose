package dev.tonholo.s2c.cli.output.log

import dev.tonholo.s2c.io.FileManager
import dev.tonholo.s2c.output.FileResult
import dev.tonholo.s2c.output.RunConfig
import dev.tonholo.s2c.output.RunStats
import kotlin.time.Duration
import kotlin.time.TimeSource
import okio.Path
import okio.Path.Companion.toPath

internal class RunLogWriter(
    private val fileManager: FileManager,
    private val logDir: Path,
) {
    fun write(
        config: RunConfig,
        stats: RunStats,
        entries: List<FileCompletionEntry>,
    ): Path {
        fileManager.createDirectories(logDir, mustCreate = false)
        val timestamp = formatTimestamp()
        val logFile = logDir / "run-$timestamp.log"

        fileManager.write(logFile) {
            writeUtf8("svg-to-compose Run Log\n")
            writeUtf8("=====================\n\n")

            writeUtf8("Configuration:\n")
            writeUtf8("  Input:    ${config.inputPath}\n")
            writeUtf8("  Output:   ${config.outputPath}\n")
            writeUtf8("  Package:  ${config.packageName}\n")
            writeUtf8("  Optimize: ${config.optimizationEnabled}\n")
            writeUtf8("  Parallel: ${config.parallel}\n")
            writeUtf8("\n")

            writeUtf8("Results: ${stats.succeeded} succeeded, ${stats.failed} failed, ${stats.totalFiles} total\n")
            writeUtf8("Duration: ${formatDuration(stats.totalDuration)}\n")
            writeUtf8("\n")

            if (entries.any { it.result is FileResult.Failed }) {
                writeUtf8("Failed Files\n")
                writeUtf8("------------\n")
                for (entry in entries) {
                    val result = entry.result
                    if (result is FileResult.Failed) {
                        writeUtf8("\n")
                        writeUtf8("  ${entry.fileName}: ${result.errorCode.name}\n")
                        writeUtf8("  Message: ${result.message}\n")
                        val stackTrace = result.stackTrace
                        if (stackTrace != null) {
                            writeUtf8("  Stacktrace:\n")
                            for (line in stackTrace.lines()) {
                                writeUtf8("    $line\n")
                            }
                        }
                    }
                }
                writeUtf8("\n")
            }

            writeUtf8("All Files\n")
            writeUtf8("---------\n")
            for (entry in entries) {
                val status = when (entry.result) {
                    is FileResult.Success -> "[OK]   ${entry.duration.inWholeMilliseconds}ms"
                    is FileResult.Failed -> "[FAIL] ${entry.result.errorCode.name}"
                }
                writeUtf8("  $status  ${entry.fileName}\n")
            }
        }

        return logFile
    }

    companion object {
        val DEFAULT_LOG_DIR: Path = ".s2c/logs".toPath()

        private const val NANOS_PER_MILLI = 1_000_000

        // Captured at class load; elapsed nanos from this mark produce
        // unique, monotonically increasing timestamps within a process.
        private val epoch = TimeSource.Monotonic.markNow()

        private fun formatTimestamp(): String {
            return epoch.elapsedNow().inWholeNanoseconds.toString()
        }

        internal fun formatDuration(duration: Duration): String =
            duration.toComponents { hours, minutes, seconds, nanoseconds ->
                val parts = mutableListOf<String>()
                if (hours > 0) parts += "${hours}h"
                if (minutes > 0) parts += "${minutes}m"
                if (seconds > 0) parts += "${seconds}s"
                if (nanoseconds > 0) {
                    val millis = nanoseconds / NANOS_PER_MILLI
                    parts += if (millis > 0) "${millis}ms" else "${nanoseconds}ns"
                }
                if (parts.isEmpty()) "0ns" else parts.joinToString(separator = " ")
            }
    }
}

data class FileCompletionEntry(
    val fileName: String,
    val duration: Duration,
    val result: FileResult,
)
