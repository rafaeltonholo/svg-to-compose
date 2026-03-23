package dev.tonholo.s2c.website.state.playground.reducer

import dev.tonholo.s2c.website.state.playground.BatchConversionResult
import dev.tonholo.s2c.website.state.playground.BatchPhase
import dev.tonholo.s2c.website.state.playground.PlaygroundAction
import dev.tonholo.s2c.website.state.playground.PlaygroundState
import dev.tonholo.s2c.website.state.playground.fileKey
import dev.tonholo.s2c.website.state.playground.toFileGroups
import kotlin.js.Date

internal class BatchReducer : Reducer<PlaygroundAction, PlaygroundState> {
    override fun reduce(state: PlaygroundState, action: PlaygroundAction): PlaygroundState = when (action) {
        is PlaygroundAction.StartBatch,
        is PlaygroundAction.StartBatchConversion,
        is PlaygroundAction.BatchFileProgress,
        is PlaygroundAction.BatchFileCompleted,
        is PlaygroundAction.BatchCompleted,
        is PlaygroundAction.CancelBatch,
        is PlaygroundAction.BatchCancelled,
        is PlaygroundAction.RestartBatch,
        is PlaygroundAction.ClearBatchResults,
        -> reduceBatchLifecycle(state, action)

        is PlaygroundAction.ToggleFileSelection,
        is PlaygroundAction.ToggleFolderSelection,
        is PlaygroundAction.SetFoldersSelection,
        is PlaygroundAction.ToggleSelectAll,
        is PlaygroundAction.ToggleFolderExpanded,
        -> reduceBatchSelection(state, action)

        is PlaygroundAction.InspectBatchResult,
        is PlaygroundAction.NavigatePrev,
        is PlaygroundAction.NavigateNext,
        is PlaygroundAction.BackToBatchList,
        -> reduceBatchNavigation(state, action)

        else -> state
    }

    private fun reduceBatchLifecycle(state: PlaygroundState, action: PlaygroundAction): PlaygroundState =
        when (action) {
            is PlaygroundAction.StartBatch -> {
                val groups = action.files.toFileGroups()
                val allKeys = action.files.map { it.fileKey() }.toSet()
                state.copy(
                    uploadedFiles = action.files,
                    fileGroups = groups,
                    selectedFiles = allKeys,
                    batchPhase = BatchPhase.Select,
                    inputMode = "upload",
                    selectedSample = -1,
                    previewExpanded = false,
                )
            }

            is PlaygroundAction.StartBatchConversion -> state.copy(
                batchPhase = BatchPhase.Converting(
                    total = state.selectedFiles.size,
                    startTimeMs = Date.now(),
                ),
            )

            is PlaygroundAction.BatchFileProgress -> state

            is PlaygroundAction.BatchFileCompleted -> {
                val phase = state.batchPhase
                if (phase is BatchPhase.Converting) {
                    state.copy(
                        batchPhase = phase.copy(
                            completedCount = phase.completedCount + 1,
                        ),
                    )
                } else {
                    state
                }
            }

            is PlaygroundAction.BatchCompleted ->
                transitionToResults(state, cancelled = false, results = action.results)

            is PlaygroundAction.CancelBatch -> {
                val phase = state.batchPhase
                if (phase is BatchPhase.Converting) {
                    state.copy(batchPhase = phase.copy(cancelling = true))
                } else {
                    state
                }
            }

            is PlaygroundAction.BatchCancelled ->
                transitionToResults(state, cancelled = true, results = action.results)

            is PlaygroundAction.RestartBatch -> state.copy(batchPhase = BatchPhase.Select)

            is PlaygroundAction.ClearBatchResults -> state.copy(
                batchPhase = null,
                uploadedFiles = emptyList(),
                fileGroups = emptyList(),
                selectedFiles = emptySet(),
                expandedFolders = emptySet(),
                viewingBatchResult = false,
                viewingBatchIndex = -1,
            )

            else -> state
        }

    private fun transitionToResults(
        state: PlaygroundState,
        cancelled: Boolean,
        results: List<BatchConversionResult>,
    ): PlaygroundState {
        val phase = state.batchPhase
        return if (phase is BatchPhase.Converting) {
            val duration = Date.now() - phase.startTimeMs
            state.copy(
                batchPhase = BatchPhase.Results(
                    completed = results,
                    cancelled = cancelled,
                    durationMs = duration,
                ),
            )
        } else {
            state
        }
    }

    private fun reduceBatchSelection(state: PlaygroundState, action: PlaygroundAction): PlaygroundState =
        when (action) {
            is PlaygroundAction.ToggleFileSelection -> toggleFileSelection(state, action.fileKey)
            is PlaygroundAction.ToggleFolderSelection -> toggleFolderSelection(state, action.folderPath)
            is PlaygroundAction.SetFoldersSelection -> setFoldersSelection(state, action.folderPaths, action.selected)
            is PlaygroundAction.ToggleSelectAll -> toggleSelectAll(state)
            is PlaygroundAction.ToggleFolderExpanded -> toggleFolderExpanded(state, action.folderPath)
            else -> state
        }

    private fun toggleFileSelection(state: PlaygroundState, fileKey: String): PlaygroundState {
        if (state.batchPhase !is BatchPhase.Select) return state
        val updated = if (fileKey in state.selectedFiles) {
            state.selectedFiles - fileKey
        } else {
            state.selectedFiles + fileKey
        }
        return state.copy(selectedFiles = updated)
    }

    private fun setFoldersSelection(
        state: PlaygroundState,
        folderPaths: List<String>,
        selected: Boolean,
    ): PlaygroundState {
        if (state.batchPhase !is BatchPhase.Select) return state
        val affectedKeys = state.uploadedFiles
            .filter { it.relativePath in folderPaths }
            .map { it.fileKey() }
            .toSet()
        val updated = if (selected) {
            state.selectedFiles + affectedKeys
        } else {
            state.selectedFiles - affectedKeys
        }
        return state.copy(selectedFiles = updated)
    }

    private fun toggleFolderSelection(state: PlaygroundState, folderPath: String): PlaygroundState {
        if (state.batchPhase !is BatchPhase.Select) return state
        val folderFileKeys = state.uploadedFiles
            .filter { it.relativePath == folderPath }
            .map { it.fileKey() }
            .toSet()
        val allSelected = folderFileKeys.all { it in state.selectedFiles }
        val updated = if (allSelected) {
            state.selectedFiles - folderFileKeys
        } else {
            state.selectedFiles + folderFileKeys
        }
        return state.copy(selectedFiles = updated)
    }

    private fun toggleSelectAll(state: PlaygroundState): PlaygroundState {
        if (state.batchPhase !is BatchPhase.Select) return state
        val allKeys = state.uploadedFiles.map { it.fileKey() }.toSet()
        val updated = if (state.selectedFiles == allKeys) emptySet() else allKeys
        return state.copy(selectedFiles = updated)
    }

    private fun toggleFolderExpanded(state: PlaygroundState, folderPath: String): PlaygroundState {
        val updated = if (folderPath in state.expandedFolders) {
            state.expandedFolders - folderPath
        } else {
            state.expandedFolders + folderPath
        }
        return state.copy(expandedFolders = updated)
    }

    private fun reduceBatchNavigation(state: PlaygroundState, action: PlaygroundAction): PlaygroundState =
        when (action) {
            is PlaygroundAction.InspectBatchResult -> {
                val results = (state.batchPhase as? BatchPhase.Results)?.completed
                val index = results?.indexOf(action.result) ?: -1
                if (results != null && index >= 0) {
                    applyInspection(state, action.result, index)
                } else {
                    state
                }
            }

            is PlaygroundAction.NavigatePrev -> {
                val results = (state.batchPhase as? BatchPhase.Results)?.completed
                val idx = state.viewingBatchIndex
                if (results != null && idx > 0) {
                    applyInspection(state, results[idx - 1], idx - 1)
                } else {
                    state
                }
            }

            is PlaygroundAction.NavigateNext -> {
                val results = (state.batchPhase as? BatchPhase.Results)?.completed
                val idx = state.viewingBatchIndex
                if (results != null && idx < results.lastIndex) {
                    applyInspection(state, results[idx + 1], idx + 1)
                } else {
                    state
                }
            }

            is PlaygroundAction.BackToBatchList -> state.copy(
                viewingBatchResult = false,
                viewingBatchIndex = -1,
            )

            is PlaygroundAction.ZipUploadError -> state.copy(
                conversionError = action.message,
                isConverting = false,
                batchPhase = null,
            )

            else -> state
        }

    private fun applyInspection(state: PlaygroundState, result: BatchConversionResult, index: Int): PlaygroundState {
        val iconName = result.fileName.substringBeforeLast(".")
            .replaceFirstChar { it.uppercase() }
        return state.copy(
            inputCode = result.originalContent,
            extension = result.detectedExtension,
            convertedKotlinCode = result.kotlinCode ?: "",
            iconFileContentsJson = result.iconFileContentsJson,
            conversionError = result.error,
            inputMode = "paste",
            zoomLevel = 1f,
            previewExpanded = true,
            inputFileName = result.fileName,
            outputFileName = "$iconName.kt",
            viewingBatchResult = true,
            viewingBatchIndex = index,
        )
    }
}
