package dev.tonholo.s2c.cli.output.tui.widget

import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.rendering.Widget
import com.github.ajalt.mordant.table.verticalLayout
import com.github.ajalt.mordant.widgets.Text
import dev.tonholo.s2c.cli.output.tui.state.CompletionState
import dev.tonholo.s2c.cli.output.tui.state.FailedFileEntry
import dev.tonholo.s2c.error.ErrorCode
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
 * Builds the plain-text lines that make up the completion summary.
 *
 * Keeping the rendering logic in a pure list-of-strings function makes the
 * output trivially unit-testable; [completionSection] wraps these lines in a
 * Mordant [Widget] when displayed as part of the TUI. The function returns an
 * empty list when the snapshot has not yet received a [dev.tonholo.s2c.output.ConversionEvent.RunCompleted] event.
 *
 * @param state the accumulated [CompletionState].
 * @param stackTraceEnabled when true, full stack traces are included under each failed file.
 */
internal fun buildCompletionLines(
    state: CompletionState,
    stackTraceEnabled: Boolean,
): List<String> {
    val stats = state.stats ?: return emptyList()
    val config = state.config
    val lines = mutableListOf<String>()

    lines += headerLine(
        version = state.version,
        config = config,
        totalFiles = state.totalFiles,
    )
    lines += divider()
    lines += progressLine(stats = stats)
    lines += statsLine(stats = stats)
    lines += divider()
    lines += ""
    lines += countersLine(stats = stats)
    lines += ""

    if (stats.failed > 0 && state.failedFiles.isNotEmpty()) {
        lines += divider()
        lines += "Failed files (${stats.failed}):"
        lines += ""
        lines += buildFailedGroups(
            entries = state.failedFiles,
            stackTraceEnabled = stackTraceEnabled,
        )
    }

    return lines
}

/**
 * Renders [state] as a vertical [Widget] suitable for printing after the live
 * animation has stopped. See [buildCompletionLines] for the line contents.
 */
internal fun completionSection(state: CompletionState, stackTraceEnabled: Boolean): Widget =
    verticalLayout {
        for (line in buildCompletionLines(state = state, stackTraceEnabled = stackTraceEnabled)) {
            cell(Text(line))
        }
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

private fun buildFailedGroups(
    entries: List<FailedFileEntry>,
    stackTraceEnabled: Boolean,
): List<String> {
    val groups = groupByErrorCode(entries = entries)
    val out = mutableListOf<String>()
    for ((errorCode, group) in groups) {
        val label = if (group.size == SINGLE_FAILED_FILE) "file" else "files"
        out += "  ${errorCode.name} (${group.size} $label)"
        for (entry in group) {
            out += renderFailedEntry(entry = entry, stackTraceEnabled = stackTraceEnabled)
        }
        out += ""
    }
    return out
}

private fun groupByErrorCode(
    entries: List<FailedFileEntry>,
): Map<ErrorCode, List<FailedFileEntry>> {
    val groups = LinkedHashMap<ErrorCode, MutableList<FailedFileEntry>>()
    for (entry in entries) {
        groups.getOrPut(entry.errorCode) { mutableListOf() } += entry
    }
    return groups
}

private fun renderFailedEntry(entry: FailedFileEntry, stackTraceEnabled: Boolean): String {
    val firstMessageLine = entry.message.lineSequence().firstOrNull().orEmpty()
    val header = "    ${TuiIcons.failure} ${entry.fileName}"
    val body = if (firstMessageLine.isNotEmpty()) "\n      $firstMessageLine" else ""
    val trace = if (stackTraceEnabled && !entry.stackTrace.isNullOrBlank()) {
        buildString {
            append("\n")
            for ((index, line) in entry.stackTrace.lines().withIndex()) {
                if (index > 0) append("\n")
                append("      ")
                append(line)
            }
        }
    } else {
        ""
    }
    return header + body + trace
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
    val rounded = round(rate * ROUNDING_FACTOR) / ROUNDING_FACTOR
    return rounded.toString()
}
