package dev.tonholo.s2c.cli.output.log

import dev.tonholo.s2c.dispatching.availableProcessors
import dev.tonholo.s2c.io.FileManager
import dev.tonholo.s2c.output.FileResult
import dev.tonholo.s2c.output.RunConfig
import dev.tonholo.s2c.output.RunStats
import dev.tonholo.s2c.runtime.S2cConfig
import okio.BufferedSink
import okio.Path
import okio.Path.Companion.toPath
import kotlin.random.Random
import kotlin.time.Clock
import kotlin.time.Duration

internal class RunLogWriter(private val fileManager: FileManager, private val logDir: Path) {
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

            writeConfiguration(config)
            writeResults(stats)
            writeFailures(entries)
            writeSummary(entries)
        }

        return logFile
    }

    private fun BufferedSink.writeResults(stats: RunStats) {
        writeUtf8("Results: ${stats.succeeded} succeeded, ${stats.failed} failed, ${stats.totalFiles} total\n")
        writeUtf8("Duration: ${formatDuration(stats.totalDuration)}\n")
        writeUtf8("\n")
    }

    private fun BufferedSink.writeSummary(entries: List<FileCompletionEntry>) {
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

    private fun BufferedSink.writeFailures(entries: List<FileCompletionEntry>) {
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
    }

    private fun BufferedSink.writeConfiguration(config: RunConfig) {
        writeUtf8("Configuration:\n")
        writeUtf8("  Input:    ${config.inputPath}\n")
        writeUtf8("  Output:   ${config.outputPath}\n")
        writeUtf8("  Package:  ${config.packageName}\n")
        writeUtf8("  Optimize: ${config.optimizationEnabled}\n")
        writeUtf8("  Parallel: ${formatParallel(config.parallel)}\n")
        writeUtf8("\n")
    }

    companion object {
        val DEFAULT_LOG_DIR: Path = ".s2c/logs".toPath()

        private const val SUFFIX_HEX_DIGITS = 4
        private const val SUFFIX_BITS = SUFFIX_HEX_DIGITS * 4
        private const val SUFFIX_MASK = (1 shl SUFFIX_BITS) - 1

        private fun formatTimestamp(): String {
            val millis = Clock.System.now().toEpochMilliseconds()
            val suffix = Random.nextInt()
                .and(SUFFIX_MASK)
                .toString(radix = 16)
                .padStart(length = SUFFIX_HEX_DIGITS, padChar = '0')
            return "$millis-$suffix"
        }

        private fun formatParallel(parallel: Int): String = when (parallel) {
            S2cConfig.PARALLEL_DISABLED -> "disabled"
            S2cConfig.PARALLEL_CPU_CORES -> "CPU cores (${availableProcessors()})"
            else -> parallel.toString()
        }

        private const val NANOS_PER_MILLI = 1_000_000

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

internal data class FileCompletionEntry(
    val fileName: String,
    val duration: Duration,
    val result: FileResult,
)
