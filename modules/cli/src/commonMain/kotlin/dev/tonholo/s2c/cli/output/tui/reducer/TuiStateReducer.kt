package dev.tonholo.s2c.cli.output.tui.reducer

import dev.tonholo.s2c.cli.output.tui.state.HeaderState
import dev.tonholo.s2c.cli.output.tui.state.ProgressState
import dev.tonholo.s2c.output.ConversionEvent
import dev.tonholo.s2c.output.FileResult

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

internal fun reduceProgress(state: ProgressState?, event: ConversionEvent): ProgressState = when (event) {
    is ConversionEvent.RunStarted -> ProgressState(
        total = event.totalFiles.toLong(),
        pending = event.totalFiles.toLong(),
    )

    is ConversionEvent.FileCompleted -> {
        val current = state ?: return ProgressState()
        when (event.result) {
            is FileResult.Success -> current.copy(
                completed = current.completed + 1,
                pending = current.pending - 1,
            )

            is FileResult.Failed -> {
                val failed = event.result as FileResult.Failed
                current.copy(
                    failed = current.failed + 1,
                    pending = current.pending - 1,
                    errors = current.errors + failed.message,
                )
            }
        }
    }

    is ConversionEvent.FileStarted,
    is ConversionEvent.FileStepChanged,
    is ConversionEvent.RunCompleted,
    is ConversionEvent.UpdateAvailable,
    -> state ?: ProgressState()
}
