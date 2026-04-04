package dev.tonholo.s2c.website.state.playground.reducer

import dev.tonholo.s2c.website.state.playground.PlaygroundAction
import dev.tonholo.s2c.website.state.playground.PlaygroundState
import dev.tonholo.s2c.website.worker.ConversionOutput

/**
 * Pure function that computes the next [PlaygroundState] given the current
 * state and an [PlaygroundAction]. Contains no side effects — all state
 * transitions are testable in isolation.
 */
internal class PlaygroundReducer(
    private val sampleReducer: SampleReducer = SampleReducer(),
    private val batchReducer: BatchReducer = BatchReducer(),
    private val singleFileReducer: SingleFileReducer = SingleFileReducer(),
    private val uiReducer: PlaygroundUiReducer = PlaygroundUiReducer(),
    private val templateReducer: TemplateReducer = TemplateReducer(),
) : Reducer<PlaygroundAction, PlaygroundState> {
    override fun reduce(state: PlaygroundState, action: PlaygroundAction): PlaygroundState = when (action) {
        is PlaygroundAction.SelectSample, is PlaygroundAction.SampleLoaded -> sampleReducer.reduce(state, action)

        is PlaygroundAction.ChangeInputMode,
        is PlaygroundAction.UpdateInputCode,
        is PlaygroundAction.ChangeOptions,
        is PlaygroundAction.SelectMobilePanel,
        is PlaygroundAction.ChangeZoom,
        is PlaygroundAction.ChangePreviewExpanded,
        -> uiReducer.reduce(state, action)

        is PlaygroundAction.LoadContent, is PlaygroundAction.SingleFileLoaded, is PlaygroundAction.StartConversion ->
            singleFileReducer.reduce(state, action)

        is PlaygroundAction.StartBatch,
        is PlaygroundAction.StartBatchConversion,
        is PlaygroundAction.BatchFileProgress,
        is PlaygroundAction.BatchFileCompleted,
        is PlaygroundAction.BatchCompleted,
        is PlaygroundAction.CancelBatch,
        is PlaygroundAction.BatchCancelled,
        is PlaygroundAction.RestartBatch,
        is PlaygroundAction.ClearBatchResults,
        is PlaygroundAction.ToggleFileSelection,
        is PlaygroundAction.ToggleFolderSelection,
        is PlaygroundAction.SetFoldersSelection,
        is PlaygroundAction.ToggleSelectAll,
        is PlaygroundAction.ToggleFolderExpanded,
        is PlaygroundAction.InspectBatchResult,
        is PlaygroundAction.NavigatePrev,
        is PlaygroundAction.NavigateNext,
        is PlaygroundAction.BackToBatchList,
        is PlaygroundAction.ZipUploadError,
        -> batchReducer.reduce(state, action)

        is PlaygroundAction.UpdateTemplateToml,
        is PlaygroundAction.TemplateValidated,
        is PlaygroundAction.TemplateFileLoaded,
        is PlaygroundAction.ClearTemplate,
        is PlaygroundAction.ChangeTemplateExpanded,
        -> templateReducer.reduce(state, action)

        is PlaygroundAction.ConversionOutputReceived ->
            reduceConversionOutput(state, action.output)
    }

    private fun reduceConversionOutput(state: PlaygroundState, output: ConversionOutput): PlaygroundState =
        when (output) {
            is ConversionOutput.Progress -> state.copy(
                isConverting = true,
                conversionProgress = output.stage,
            )

            is ConversionOutput.Success -> state.copy(
                convertedKotlinCode = output.kotlinCode,
                iconFileContentsJson = output.iconFileContentsJson,
                isConverting = false,
                conversionProgress = "",
                conversionError = null,
                previewExpanded = true,
            )

            is ConversionOutput.Error -> state.copy(
                isConverting = false,
                conversionProgress = "",
                conversionError = output.message,
            )
        }
}
