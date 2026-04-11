package dev.tonholo.s2c.cli.output.tui.widget

import kotlin.time.Duration

/**
 * Estimates remaining time based on a rolling average of recent file durations.
 *
 * @param recentDurations the rolling window of recent file processing durations.
 * @param remaining the number of files left to process.
 * @return estimated time remaining, or null if no duration data is available.
 */
internal fun calculateEta(recentDurations: List<Duration>, remaining: Int): Duration? {
    if (recentDurations.isEmpty()) return null
    if (remaining <= 0) return Duration.ZERO
    val average = recentDurations.fold(Duration.ZERO, Duration::plus) / recentDurations.size
    return average * remaining
}

/**
 * Calculates processing throughput in files per second.
 *
 * @param completed the number of files processed so far.
 * @param elapsed the total elapsed time since the run started.
 * @return files per second, or 0.0 if elapsed is zero or no files completed.
 */
internal fun calculateThroughput(completed: Int, elapsed: Duration): Double {
    if (elapsed == Duration.ZERO || completed <= 0) return 0.0
    val elapsedSeconds = elapsed.inWholeMilliseconds.toDouble() / MILLIS_PER_SECOND
    return completed.toDouble() / elapsedSeconds
}

private const val MILLIS_PER_SECOND = 1000
