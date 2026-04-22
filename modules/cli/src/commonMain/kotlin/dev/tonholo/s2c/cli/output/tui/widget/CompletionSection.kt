package dev.tonholo.s2c.cli.output.tui.widget

import com.github.ajalt.mordant.rendering.TextColors
import dev.tonholo.s2c.cli.output.tui.state.CompletionState
import dev.tonholo.s2c.cli.output.tui.state.FailedFileEntry
import dev.tonholo.s2c.output.RunConfig
import dev.tonholo.s2c.output.RunStats
import kotlin.math.round

private const val MILLIS_PER_SECOND = 1000.0
private const val SECONDS_PER_MINUTE = 60
private const val ROUNDING_FACTOR = 10.0
private const val DIVIDER_WIDTH = 75
private const val PROGRESS_BAR_WIDTH = 40
private const val PERCENT_SCALE = 100

private const val SINGLE_FAILED_FILE = 1

/**
 * Builds the plain-text completion summary printed after the live animation stops.
 *
 * Returns an empty string when the snapshot has not yet received a
 * [dev.tonholo.s2c.output.ConversionEvent.RunCompleted] event; otherwise the returned string
 * is ready to hand to `Terminal.println` (or any other newline-aware sink).
 *
 * @param state the accumulated [CompletionState].
 * @param stackTraceEnabled when true, full stack traces are included under each failed file.
 */
internal fun buildCompletionSummary(state: CompletionState, stackTraceEnabled: Boolean): String {
    val stats = state.stats ?: return ""
    return buildString {
        appendLine(
            headerLine(
                version = state.version,
                config = state.config,
                totalFiles = state.totalFiles,
            ),
        )
        appendLine(divider())
        appendLine(progressLine(stats = stats))
        appendLine(statsLine(stats = stats))
        appendLine(divider())
        appendLine()
        appendLine(countersLine(stats = stats))

        if (stats.failed > 0 && state.failedFiles.isNotEmpty()) {
            appendLine()
            appendLine(divider())
            appendLine("Failed files (${stats.failed}):")
            appendLine()
            appendFailedGroups(
                entries = state.failedFiles,
                stackTraceEnabled = stackTraceEnabled,
            )
        }
    }.trimEnd()
}

private fun headerLine(
    version: String,
    config: RunConfig?,
    totalFiles: Int,
): String {
    // Mordant renders the string verbatim; any ANSI styling would make the line
    // hard to pattern-match in tests and add noise to non-TTY output. Styling
    // for the TUI is applied at the widget layer only when needed.
    val prefix = "svg-to-compose v$version"
    if (config == null) return prefix
    return buildString {
        append(prefix)
        append("  input: ")
        append(config.inputPath)
        append("  output: ")
        append(config.outputPath)
        append("  files: ")
        append(totalFiles)
        append("  optimize: ")
        append(if (config.optimizationEnabled) "on" else "off")
    }
}

private fun divider(): String = "-".repeat(DIVIDER_WIDTH)

private fun progressLine(stats: RunStats): String {
    val completed = stats.succeeded + stats.failed
    val ratio = if (stats.totalFiles > 0) {
        completed.toDouble() / stats.totalFiles
    } else {
        0.0
    }
    val filled = (ratio * PROGRESS_BAR_WIDTH).toInt().coerceIn(0, PROGRESS_BAR_WIDTH)
    val bar = "\u2588".repeat(filled) + " ".repeat(PROGRESS_BAR_WIDTH - filled)
    val percentage = (ratio * PERCENT_SCALE).toInt()
    val colouredBar = if (percentage >= PERCENT_SCALE) TextColors.green(bar) else bar
    return "Progress $colouredBar $completed/${stats.totalFiles}  $percentage%"
}

private fun statsLine(stats: RunStats): String {
    val duration = formatDuration(durationMs = stats.totalDuration.inWholeMilliseconds)
    val throughput = computeThroughput(
        succeeded = stats.succeeded,
        durationMs = stats.totalDuration.inWholeMilliseconds,
    )
    return "Completed in $duration  Throughput: $throughput icons/sec"
}

private fun countersLine(stats: RunStats): String = buildString {
    append(TuiIcons.success)
    append(' ')
    append(stats.succeeded)
    append(" succeeded    ")
    append(TuiIcons.failure)
    append(' ')
    append(stats.failed)
    append(" failed")
}

private fun StringBuilder.appendFailedGroups(
    entries: List<FailedFileEntry>,
    stackTraceEnabled: Boolean,
) {
    val groups = entries.groupBy { it.errorCode }
    for ((errorCode, group) in groups) {
        val label = if (group.size == SINGLE_FAILED_FILE) "file" else "files"
        appendLine("  ${errorCode.name} (${group.size} $label)")
        for (entry in group) {
            appendFailedEntry(entry = entry, stackTraceEnabled = stackTraceEnabled)
        }
        appendLine()
    }
}

private fun StringBuilder.appendFailedEntry(entry: FailedFileEntry, stackTraceEnabled: Boolean) {
    appendLine("    ${TuiIcons.failure} ${entry.fileName}")
    val firstMessageLine = entry.message.lineSequence().firstOrNull().orEmpty()
    if (firstMessageLine.isNotEmpty()) {
        appendLine("      $firstMessageLine")
    }
    if (stackTraceEnabled && !entry.stackTrace.isNullOrBlank()) {
        // `trimEnd()` drops a trailing newline so `lineSequence()` does not emit
        // a stray empty line rendered as six spaces at the bottom of the trace.
        for (line in entry.stackTrace.trimEnd().lineSequence()) {
            appendLine("      $line")
        }
    }
}

private fun formatDuration(durationMs: Long): String {
    if (durationMs < MILLIS_PER_SECOND) return "${durationMs}ms"
    val totalSeconds = durationMs / MILLIS_PER_SECOND
    val minutes = (totalSeconds / SECONDS_PER_MINUTE).toInt()
    val seconds = (totalSeconds % SECONDS_PER_MINUTE).toInt()
    return if (minutes > 0) "${minutes}m ${seconds}s" else "${seconds}s"
}

private fun computeThroughput(succeeded: Int, durationMs: Long): String {
    val seconds = durationMs / MILLIS_PER_SECOND
    if (seconds <= 0.0) return "0.0"
    val rate = succeeded.toDouble() / seconds
    val rounded = round(rate * ROUNDING_FACTOR) / ROUNDING_FACTOR
    return rounded.toString()
}
