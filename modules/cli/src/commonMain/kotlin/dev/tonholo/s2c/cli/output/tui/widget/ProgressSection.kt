package dev.tonholo.s2c.cli.output.tui.widget

import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.rendering.Widget
import com.github.ajalt.mordant.table.verticalLayout
import com.github.ajalt.mordant.widgets.Text
import dev.tonholo.s2c.cli.output.tui.state.ProgressState
import kotlin.math.roundToLong
import kotlin.time.Duration

private const val PROGRESS_LABEL = "Progress "
private const val PROGRESS_COUNTER_PADDING = 20

internal fun progressSection(state: ProgressState, terminalWidth: Int): Widget {
    if (state.total <= 0) {
        return Text("")
    }

    val percentage = state.completed.toDouble() / state.total

    val counterText = "${state.completed}/${state.total}  ${formatPercentage(percentage)}"
    val barWidth = (terminalWidth - PROGRESS_LABEL.length - counterText.length - PROGRESS_COUNTER_PADDING)
        .coerceAtLeast(minimumValue = 10)
    val filledWidth = (barWidth * percentage).toInt()
    val emptyWidth = barWidth - filledWidth

    val bar = TextColors.green("\u2588".repeat(filledWidth)) +
        TextColors.gray("\u2591".repeat(emptyWidth))
    val progressLine = Text("$PROGRESS_LABEL$bar $counterText")

    val statsLine = buildStatsLine(state = state)

    return verticalLayout {
        cell(progressLine)
        cell(statsLine)
    }
}

private fun buildStatsLine(state: ProgressState): Widget {
    val elapsed = state.startTime?.elapsedNow()
    val elapsedText = elapsed?.let { "Elapsed: ${formatDuration(it)}" } ?: "Elapsed: --"

    val remaining = state.total - state.completed
    val eta = calculateEta(recentDurations = state.recentDurations, remaining = remaining)
    val etaText = eta?.let { "ETA: ~${formatDuration(it)}" } ?: "ETA: --"

    val throughput = if (elapsed != null) {
        calculateThroughput(completed = state.completed, elapsed = elapsed)
    } else {
        0.0
    }
    val throughputText = "Throughput: ${formatOneDecimal(throughput)} icons/sec"

    return Text("$elapsedText  $etaText  $throughputText")
}

private const val PERCENTAGE_MULTIPLIER = 100

private fun formatPercentage(value: Double): String = "${formatOneDecimal(value * PERCENTAGE_MULTIPLIER)}%"

private fun formatOneDecimal(value: Double): String {
    val scaled = (value * 10).roundToLong()
    val whole = scaled / 10
    val frac = scaled % 10
    return "$whole.$frac"
}

internal fun formatDuration(duration: Duration): String {
    val totalSeconds = duration.inWholeSeconds
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return if (minutes > 0) {
        "${minutes}m ${seconds}s"
    } else {
        "${seconds}s"
    }
}
