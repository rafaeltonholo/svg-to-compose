package dev.tonholo.s2c.website.state.playground.folder

import dev.tonholo.s2c.website.state.playground.batch.BatchConversionResult
import dev.tonholo.s2c.website.state.playground.batch.BatchPhase
import dev.tonholo.s2c.website.state.playground.batch.FileGroup

internal data class FolderGroupState(
    val group: FileGroup,
    val phase: BatchPhase,
    val selectedFiles: Set<String>,
    val expandedFolders: Set<String>,
    val completedResultsByKey: Map<String, BatchConversionResult>,
    val completedCountByFolder: Map<String, Int>,
    val selectedCountForFolder: Int,
)
