package dev.tonholo.s2c.cli.output.tui.state

internal data class TuiState(
    val header: HeaderState = HeaderState(),
    val progress: ProgressState? = null,
    val currentFiles: LinkedHashMap<String, CurrentFileState> = linkedMapOf(),
    val recentFiles: RecentFilesState = RecentFilesState(),
) {
    fun withHeader(transform: (HeaderState) -> HeaderState): TuiState = copy(header = transform(header))

    fun withProgress(transform: (ProgressState?) -> ProgressState): TuiState = copy(progress = transform(progress))

    fun withCurrentFiles(
        transform: (LinkedHashMap<String, CurrentFileState>) -> LinkedHashMap<String, CurrentFileState>,
    ): TuiState = copy(currentFiles = transform(currentFiles))

    fun withRecentFiles(transform: (RecentFilesState) -> RecentFilesState): TuiState =
        copy(recentFiles = transform(recentFiles))
}
