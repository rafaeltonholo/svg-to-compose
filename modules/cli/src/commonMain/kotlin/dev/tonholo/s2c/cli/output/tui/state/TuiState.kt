package dev.tonholo.s2c.cli.output.tui.state

internal data class TuiState(
    val header: HeaderState = HeaderState(),
    val progress: ProgressState? = null,
    val currentFile: CurrentFileState? = null,
    val recentFiles: RecentFilesState = RecentFilesState(),
) {
    fun withHeader(transform: (HeaderState) -> HeaderState): TuiState = copy(header = transform(header))

    fun withProgress(transform: (ProgressState?) -> ProgressState): TuiState = copy(progress = transform(progress))

    fun withCurrentFile(transform: (CurrentFileState?) -> CurrentFileState?): TuiState =
        copy(currentFile = transform(currentFile))

    fun withRecentFiles(transform: (RecentFilesState) -> RecentFilesState): TuiState =
        copy(recentFiles = transform(recentFiles))
}
