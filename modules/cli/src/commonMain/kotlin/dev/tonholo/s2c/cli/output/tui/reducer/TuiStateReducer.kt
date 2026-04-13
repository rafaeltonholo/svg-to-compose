package dev.tonholo.s2c.cli.output.tui.reducer

import dev.tonholo.s2c.cli.output.tui.state.HeaderState
import dev.tonholo.s2c.output.ConversionEvent

internal fun reduceHeader(
    state: HeaderState,
    event: ConversionEvent,
): HeaderState = when (event) {
    is ConversionEvent.RunStarted -> state.copy(
        version = event.version,
        config = event.config,
        totalFiles = event.totalFiles,
    )

    is ConversionEvent.FileStarted,
    is ConversionEvent.FileStepChanged,
    is ConversionEvent.FileCompleted,
    is ConversionEvent.RunCompleted,
    is ConversionEvent.UpdateAvailable,
    -> state
}
