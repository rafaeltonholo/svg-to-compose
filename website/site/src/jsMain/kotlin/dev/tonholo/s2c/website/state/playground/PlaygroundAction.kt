package dev.tonholo.s2c.website.state.playground

import dev.tonholo.s2c.website.state.playground.reducer.PlaygroundReducer
import dev.tonholo.s2c.website.worker.ConversionOutput

/**
 * All possible user and system actions that can modify [PlaygroundState].
 * Used with [PlaygroundReducer] for predictable state transitions.
 */
internal sealed interface PlaygroundAction {
    // Editor actions
    data class SelectSample(val index: Int) : PlaygroundAction
    data class SampleLoaded(val svgCode: String, val samplePath: String, val iconName: String) : PlaygroundAction
    data class ChangeInputMode(val mode: String) : PlaygroundAction
    data class UpdateInputCode(val code: String) : PlaygroundAction
    data class LoadContent(val content: String) : PlaygroundAction
    data class ChangeOptions(val options: PlaygroundOptions) : PlaygroundAction
    data class SelectMobilePanel(val panel: Int) : PlaygroundAction
    data class ChangeZoom(val zoom: Float) : PlaygroundAction
    data class ChangePreviewExpanded(val expanded: Boolean) : PlaygroundAction

    // Single file actions
    data class SingleFileLoaded(val fileName: String, val content: String) : PlaygroundAction
    data object StartConversion : PlaygroundAction

    // Batch lifecycle
    data class StartBatch(val files: List<UploadedFileInfo>) : PlaygroundAction
    data object StartBatchConversion : PlaygroundAction
    data class BatchFileProgress(val fileName: String, val stage: String) : PlaygroundAction
    data class BatchFileCompleted(val result: BatchConversionResult) : PlaygroundAction
    data class BatchCompleted(val results: List<BatchConversionResult>) : PlaygroundAction
    data object CancelBatch : PlaygroundAction
    data class BatchCancelled(val results: List<BatchConversionResult>) : PlaygroundAction
    data object RestartBatch : PlaygroundAction
    data object ClearBatchResults : PlaygroundAction

    // File selection (only effective during BatchPhase.Select)
    data class ToggleFileSelection(val fileKey: String) : PlaygroundAction
    data class ToggleFolderSelection(val folderPath: String) : PlaygroundAction
    data class SetFoldersSelection(val folderPaths: List<String>, val selected: Boolean) : PlaygroundAction
    data object ToggleSelectAll : PlaygroundAction
    data class ToggleFolderExpanded(val folderPath: String) : PlaygroundAction

    // Batch navigation (inspection)
    data class InspectBatchResult(val result: BatchConversionResult) : PlaygroundAction
    data object NavigatePrev : PlaygroundAction
    data object NavigateNext : PlaygroundAction
    data object BackToBatchList : PlaygroundAction

    // Upload errors
    data class ZipUploadError(val message: String) : PlaygroundAction

    // Conversion output
    data class ConversionOutputReceived(val output: ConversionOutput) : PlaygroundAction
}
