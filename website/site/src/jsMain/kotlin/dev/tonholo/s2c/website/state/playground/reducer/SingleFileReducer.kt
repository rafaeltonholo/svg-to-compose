package dev.tonholo.s2c.website.state.playground.reducer

import dev.tonholo.s2c.website.state.playground.PlaygroundAction
import dev.tonholo.s2c.website.state.playground.PlaygroundState
import dev.tonholo.s2c.website.state.playground.detectExtension

internal class SingleFileReducer : Reducer<PlaygroundAction, PlaygroundState> {
    override fun reduce(state: PlaygroundState, action: PlaygroundAction): PlaygroundState = when (action) {
        is PlaygroundAction.LoadContent -> {
            val detected = detectExtension(action.content)
            state.copy(
                inputCode = action.content,
                extension = detected ?: state.extension,
            )
        }

        is PlaygroundAction.SingleFileLoaded -> {
            val detected = detectExtension(action.content)
            val iconName = action.fileName.substringBeforeLast(".")
                .replaceFirstChar { it.uppercase() }
            state.copy(
                inputCode = action.content,
                extension = detected ?: state.extension,
                inputMode = "paste",
                selectedSample = -1,
                previewExpanded = true,
                inputFileName = action.fileName,
                outputFileName = "$iconName.kt",
                viewingBatchResult = false,
                batchPhase = null,
                fileGroups = emptyList(),
                selectedFiles = emptySet(),
                expandedFolders = emptySet(),
                uploadedFiles = emptyList(),
            )
        }

        is PlaygroundAction.StartConversion -> state.copy(
            isConverting = true,
            conversionError = null,
            conversionProgress = "Starting conversion...",
            zoomLevel = 1f,
        )

        else -> state
    }
}
