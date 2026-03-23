package dev.tonholo.s2c.website.state.playground.reducer

import dev.tonholo.s2c.website.state.playground.PlaygroundAction
import dev.tonholo.s2c.website.state.playground.PlaygroundState

internal class PlaygroundUiReducer : Reducer<PlaygroundAction, PlaygroundState> {
    override fun reduce(state: PlaygroundState, action: PlaygroundAction): PlaygroundState = when (action) {
        is PlaygroundAction.ChangeInputMode -> if (action.mode == "upload") {
            state.copy(
                inputMode = action.mode,
                selectedSample = -1,
                inputCode = "",
                convertedKotlinCode = "",
                iconFileContentsJson = null,
                conversionError = null,
                previewExpanded = false,
                inputFileName = "input.svg",
                outputFileName = "MyIcon.kt",
                batchPhase = null,
                fileGroups = emptyList(),
                selectedFiles = emptySet(),
                expandedFolders = emptySet(),
                uploadedFiles = emptyList(),
            )
        } else {
            state.copy(inputMode = action.mode)
        }

        is PlaygroundAction.UpdateInputCode -> state.copy(inputCode = action.code)

        is PlaygroundAction.ChangeOptions -> state.copy(options = action.options)

        is PlaygroundAction.SelectMobilePanel -> state.copy(activePanel = action.panel)

        is PlaygroundAction.ChangeZoom -> state.copy(zoomLevel = action.zoom)

        is PlaygroundAction.ChangePreviewExpanded -> state.copy(previewExpanded = action.expanded)

        else -> state
    }
}
