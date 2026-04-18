package dev.tonholo.s2c.cli.output.tui.reducer

import dev.tonholo.s2c.cli.output.tui.state.CurrentFileState
import dev.tonholo.s2c.cli.output.tui.state.HeaderState
import dev.tonholo.s2c.cli.output.tui.state.ProgressState
import dev.tonholo.s2c.cli.output.tui.state.RecentFileEntry
import dev.tonholo.s2c.cli.output.tui.state.RecentFilesState
import dev.tonholo.s2c.cli.output.tui.state.SingleFileCompletion
import dev.tonholo.s2c.cli.output.tui.state.TuiMode
import dev.tonholo.s2c.cli.output.tui.state.UpdateNotificationState
import dev.tonholo.s2c.output.ConversionEvent
import dev.tonholo.s2c.output.ConversionPhase
import dev.tonholo.s2c.output.FileResult

private const val SINGLE_FILE_THRESHOLD = 1

internal fun reduceMode(state: TuiMode, event: ConversionEvent): TuiMode = when (event) {
    is ConversionEvent.RunStarted ->
        if (event.totalFiles == SINGLE_FILE_THRESHOLD) TuiMode.Single else TuiMode.Batch

    is ConversionEvent.FileStarted,
    is ConversionEvent.FileStepChanged,
    is ConversionEvent.FileCompleted,
    is ConversionEvent.RunCompleted,
    is ConversionEvent.UpdateAvailable,
    -> state
}

internal fun reduceSingleFileCompletion(
    state: SingleFileCompletion?,
    mode: TuiMode,
    event: ConversionEvent,
): SingleFileCompletion? = when (event) {
    is ConversionEvent.RunStarted -> null

    is ConversionEvent.FileCompleted -> {
        if (mode != TuiMode.Single) {
            state
        } else {
            when (val outcome = event.result) {
                is FileResult.Success -> SingleFileCompletion.Success(
                    elapsedMs = event.duration.inWholeMilliseconds,
                )

                is FileResult.Failed -> SingleFileCompletion.Failure(
                    errorCode = outcome.errorCode,
                    message = outcome.message.substringBefore(delimiter = '\n'),
                )
            }
        }
    }

    is ConversionEvent.FileStarted,
    is ConversionEvent.FileStepChanged,
    is ConversionEvent.RunCompleted,
    is ConversionEvent.UpdateAvailable,
    -> state
}

internal fun reduceHeader(state: HeaderState, event: ConversionEvent): HeaderState = when (event) {
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

internal fun reduceProgress(state: ProgressState?, event: ConversionEvent): ProgressState =
    when (event) {
        is ConversionEvent.RunStarted -> ProgressState(
            total = event.totalFiles.toLong(),
            pending = event.totalFiles.toLong(),
        )

        is ConversionEvent.FileStarted -> {
            val current = state ?: return ProgressState()
            current.copy(pending = (current.pending - 1).coerceAtLeast(0))
        }

        is ConversionEvent.FileCompleted -> {
            val current = state ?: return ProgressState()
            when (event.result) {
                is FileResult.Success -> current.copy(
                    completed = current.completed + 1,
                )

                is FileResult.Failed -> {
                    val failed = event.result as FileResult.Failed
                    current.copy(
                        failed = current.failed + 1,
                        errors = current.errors + failed.message,
                    )
                }
            }
        }

        is ConversionEvent.FileStepChanged,
        is ConversionEvent.RunCompleted,
        is ConversionEvent.UpdateAvailable,
        -> state ?: ProgressState()
    }

internal fun reduceCurrentFiles(
    state: Map<String, CurrentFileState>,
    event: ConversionEvent,
    optimizationEnabled: Boolean,
): Map<String, CurrentFileState> = when (event) {
    is ConversionEvent.FileStarted -> {
        val firstPhase = if (optimizationEnabled) ConversionPhase.Optimizing else ConversionPhase.Parsing
        val updated = LinkedHashMap(state)
        updated[event.fileName] = CurrentFileState(
            fileName = event.fileName,
            currentPhase = firstPhase,
            optimizationEnabled = optimizationEnabled,
        )
        updated
    }

    is ConversionEvent.FileStepChanged -> {
        val existing = state[event.fileName] ?: return state
        if (event.step == existing.currentPhase) return state
        val updated = LinkedHashMap(state)
        updated[event.fileName] = existing.copy(
            currentPhase = event.step,
            completedPhases = existing.completedPhases + existing.currentPhase,
        )
        updated
    }

    is ConversionEvent.FileCompleted -> {
        if (event.fileName !in state) return state
        val updated = LinkedHashMap(state)
        updated.remove(event.fileName)
        updated
    }

    is ConversionEvent.RunStarted,
    is ConversionEvent.RunCompleted,
    is ConversionEvent.UpdateAvailable,
    -> state
}

internal fun reduceRecentFiles(state: RecentFilesState, event: ConversionEvent): RecentFilesState =
    when (event) {
        is ConversionEvent.FileCompleted -> state.addEntry(
            entry = RecentFileEntry(
                fileName = event.fileName,
                result = event.result,
                duration = event.duration,
            ),
        )

        is ConversionEvent.RunStarted,
        is ConversionEvent.FileStarted,
        is ConversionEvent.FileStepChanged,
        is ConversionEvent.RunCompleted,
        is ConversionEvent.UpdateAvailable,
        -> state
    }

internal fun reduceUpdateNotification(
    state: UpdateNotificationState?,
    event: ConversionEvent,
): UpdateNotificationState? = when (event) {
    is ConversionEvent.UpdateAvailable -> state ?: UpdateNotificationState(
        currentVersion = event.current,
        latestVersion = event.latest,
        releaseUrl = event.releaseUrl,
        isWrapper = event.isWrapper,
    )

    is ConversionEvent.RunStarted,
    is ConversionEvent.FileStarted,
    is ConversionEvent.FileStepChanged,
    is ConversionEvent.FileCompleted,
    is ConversionEvent.RunCompleted,
    -> state
}
