package dev.tonholo.s2c.website.state.playground.batch

import dev.tonholo.s2c.website.state.playground.PlaygroundState

internal data class BatchFileListState(
    val playgroundState: PlaygroundState,
    val phase: BatchPhase,
    val completedResultsByKey: Map<String, BatchConversionResult>,
    val completedCountByFolder: Map<String, Int>,
    val selectedCountByFolder: Map<String, Int>,
    val folderPaths: List<String>,
    val lastClickedFolderIndex: Int,
)
