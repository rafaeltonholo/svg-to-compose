package dev.tonholo.s2c.website.state.playground.folder

internal data class FileGroupHeaderState(
    val groupState: FolderGroupState,
    val isExpanded: Boolean,
    val selectedCount: Int,
    val completedCount: Int,
)
