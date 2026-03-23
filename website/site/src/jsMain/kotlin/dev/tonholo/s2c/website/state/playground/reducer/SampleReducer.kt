package dev.tonholo.s2c.website.state.playground.reducer

import dev.tonholo.s2c.website.state.playground.PlaygroundAction
import dev.tonholo.s2c.website.state.playground.PlaygroundState

internal class SampleReducer : Reducer<PlaygroundAction, PlaygroundState> {
    override fun reduce(state: PlaygroundState, action: PlaygroundAction): PlaygroundState = when (action) {
        is PlaygroundAction.SelectSample -> state.copy(
            selectedSample = action.index,
            inputCode = "",
            inputMode = "paste",
            previewExpanded = true,
            inputFileName = "input.svg",
            outputFileName = "MyIcon.kt",
            viewingBatchResult = false,
            batchPhase = null,
            fileGroups = emptyList(),
            selectedFiles = emptySet(),
            expandedFolders = emptySet(),
            uploadedFiles = emptyList(),
        )

        is PlaygroundAction.SampleLoaded -> state.copy(
            inputCode = action.svgCode,
            inputFileName = action.samplePath,
            outputFileName = "${action.iconName}.kt",
            previewExpanded = true,
        )

        else -> state
    }
}
