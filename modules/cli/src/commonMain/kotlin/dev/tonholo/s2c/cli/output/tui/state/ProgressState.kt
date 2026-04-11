package dev.tonholo.s2c.cli.output.tui.state

import kotlin.time.Duration
import kotlin.time.TimeMark

internal data class ProgressState(
    val completed: Int = 0,
    val total: Int = 0,
    val startTime: TimeMark? = null,
    val recentDurations: List<Duration> = emptyList(),
    val finished: Boolean = false,
) {
    companion object {
        const val ROLLING_WINDOW_SIZE = 20
    }
}
