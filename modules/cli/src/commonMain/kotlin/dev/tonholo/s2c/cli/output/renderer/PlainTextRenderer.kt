package dev.tonholo.s2c.cli.output.renderer

import dev.tonholo.s2c.output.ConversionEvent
import dev.tonholo.s2c.output.FileResult
import dev.tonholo.s2c.output.OutputRenderer
import kotlin.math.round

private const val MILLIS_PER_SECOND = 1000.0
private const val SECONDS_PER_MINUTE = 60
private const val ROUNDING_FACTOR = 10.0

/**
 * Renders conversion events as sequential, ANSI-free plain text lines.
 *
 * Designed for non-interactive environments where output may be piped,
 * redirected to a file, or parsed with tools like `grep`.
 *
 * @param writer function that receives each output line.
 *               Defaults to [println] for stdout output.
 */
internal class PlainTextRenderer(private val writer: (String) -> Unit = ::println) :
    OutputRenderer {

    private val failedFiles = mutableListOf<FailedFileEntry>()

    override fun onEvent(event: ConversionEvent) {
        when (event) {
            is ConversionEvent.RunStarted -> renderRunStarted(event)
            is ConversionEvent.FileStarted -> Unit
            is ConversionEvent.FileStepChanged -> Unit
            is ConversionEvent.FileCompleted -> renderFileCompleted(event)
            is ConversionEvent.RunCompleted -> renderRunCompleted(event)
            is ConversionEvent.UpdateAvailable -> renderUpdateAvailable(event)
        }
    }

    private fun renderRunStarted(event: ConversionEvent.RunStarted) {
        failedFiles.clear()
        writer("[INFO] svg-to-compose v${event.version}")
        val optimize = if (event.config.optimizationEnabled) "on" else "off"
        writer(
            "[INFO] Input: ${event.config.inputPath} | " +
                "Output: ${event.config.outputPath} | " +
                "Files: ${event.totalFiles} | " +
                "Optimize: $optimize",
        )
    }

    private fun renderFileCompleted(event: ConversionEvent.FileCompleted) {
        when (val result = event.result) {
            is FileResult.Success -> {
                val ms = event.duration.inWholeMilliseconds
                writer("[OK]   ${event.fileName} (${ms}ms)")
            }

            is FileResult.Failed -> {
                writer("[FAIL] ${event.fileName} - ${result.errorCode.name}: ${result.message}")
                failedFiles.add(
                    FailedFileEntry(
                        fileName = event.fileName,
                        errorCode = result.errorCode.name,
                        message = result.message,
                    ),
                )
            }
        }
    }

    private fun renderRunCompleted(event: ConversionEvent.RunCompleted) {
        val stats = event.stats
        val duration = formatDuration(durationMs = stats.totalDuration.inWholeMilliseconds)
        val throughput = computeThroughput(
            succeeded = stats.succeeded,
            durationMs = stats.totalDuration.inWholeMilliseconds,
        )
        writer("[DONE] ${stats.succeeded} succeeded, ${stats.failed} failed ($duration, $throughput icons/sec)")

        if (failedFiles.isNotEmpty()) {
            writer("")
            writer("Failed:")
            for (entry in failedFiles) {
                writer("  ${entry.fileName}: ${entry.errorCode} - ${entry.message}")
            }
        }
    }

    private fun renderUpdateAvailable(event: ConversionEvent.UpdateAvailable) {
        writer("[UPDATE] v${event.latest} available - download from ${event.releaseUrl}")
    }

    private fun formatDuration(durationMs: Long): String {
        val totalSeconds = durationMs / MILLIS_PER_SECOND
        val minutes = (totalSeconds / SECONDS_PER_MINUTE).toInt()
        val seconds = (totalSeconds % SECONDS_PER_MINUTE).toInt()
        return if (minutes > 0) "${minutes}m ${seconds}s" else "${seconds}s"
    }

    private fun computeThroughput(succeeded: Int, durationMs: Long): String {
        val seconds = durationMs / MILLIS_PER_SECOND
        if (seconds <= 0.0) return "0.0"
        val rate = succeeded.toDouble() / seconds
        // Round to 1 decimal place without String.format (unavailable in KMP common)
        val rounded = round(rate * ROUNDING_FACTOR) / ROUNDING_FACTOR
        return rounded.toString()
    }

    private data class FailedFileEntry(
        val fileName: String,
        val errorCode: String,
        val message: String,
    )
}
