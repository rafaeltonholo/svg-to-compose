package dev.tonholo.s2c.cli.output.tui.reducer

import dev.tonholo.s2c.cli.output.tui.state.ProgressState
import dev.tonholo.s2c.cli.output.tui.state.TuiState
import dev.tonholo.s2c.output.ConversionEvent
import kotlin.time.TimeSource

internal fun reduce(
    state: TuiState,
    event: ConversionEvent,
    timeSource: TimeSource = TimeSource.Monotonic,
): TuiState = when (event) {
    is ConversionEvent.RunStarted -> state.copy(
        header = state.header.copy(
            version = event.version,
            config = event.config,
            totalFiles = event.totalFiles,
        ),
        progress = state.progress.copy(
            total = event.totalFiles,
            startTime = timeSource.markNow(),
        ),
    )

    is ConversionEvent.FileCompleted -> state.copy(
        progress = state.progress.copy(
            completed = state.progress.completed + 1,
            recentDurations = (state.progress.recentDurations + event.duration)
                .takeLast(n = ProgressState.ROLLING_WINDOW_SIZE),
        ),
    )

    is ConversionEvent.RunCompleted -> state.copy(
        progress = state.progress.copy(finished = true),
    )

    is ConversionEvent.FileStarted,
    is ConversionEvent.FileStepChanged,
    is ConversionEvent.UpdateAvailable,
    -> state
}
