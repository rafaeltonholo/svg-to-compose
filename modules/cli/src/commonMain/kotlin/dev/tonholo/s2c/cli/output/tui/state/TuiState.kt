package dev.tonholo.s2c.cli.output.tui.state

internal data class TuiState(
    val header: HeaderState = HeaderState(),
    val progress: ProgressState? = null,
    val currentFiles: Map<String, CurrentFileState> = emptyMap(),
    val recentFiles: RecentFilesState = RecentFilesState(),
) {
    fun withHeader(transform: (HeaderState) -> HeaderState): TuiState = copy(header = transform(header))

    fun withProgress(transform: (ProgressState?) -> ProgressState): TuiState = copy(progress = transform(progress))

    fun withCurrentFiles(
        transform: (Map<String, CurrentFileState>) -> Map<String, CurrentFileState>,
    ): TuiState = copy(currentFiles = transform(currentFiles))

    fun withRecentFiles(transform: (RecentFilesState) -> RecentFilesState): TuiState =
        copy(recentFiles = transform(recentFiles))
}
