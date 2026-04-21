package dev.tonholo.s2c.cli.output.tui.state

internal data class TuiState(
    val mode: TuiMode = TuiMode.Batch,
    val header: HeaderState = HeaderState(),
    val progress: ProgressState? = null,
    val currentFiles: Map<String, CurrentFileState> = emptyMap(),
    val recentFiles: RecentFilesState = RecentFilesState(),
    val updateNotification: UpdateNotificationState? = null,
    val singleFileCompletion: SingleFileCompletion? = null,
) {
    fun withMode(transform: (TuiMode) -> TuiMode): TuiState = copy(mode = transform(mode))

    fun withHeader(transform: (HeaderState) -> HeaderState): TuiState =
        copy(header = transform(header))

    fun withProgress(transform: (ProgressState?) -> ProgressState): TuiState =
        copy(progress = transform(progress))

    fun withCurrentFiles(
        transform: (Map<String, CurrentFileState>) -> Map<String, CurrentFileState>,
    ): TuiState = copy(currentFiles = transform(currentFiles))

    fun withRecentFiles(transform: (RecentFilesState) -> RecentFilesState): TuiState =
        copy(recentFiles = transform(recentFiles))

    fun withUpdateNotification(
        transform: (UpdateNotificationState?) -> UpdateNotificationState?,
    ): TuiState = copy(updateNotification = transform(updateNotification))

    fun withSingleFileCompletion(
        transform: (SingleFileCompletion?) -> SingleFileCompletion?,
    ): TuiState = copy(singleFileCompletion = transform(singleFileCompletion))
}
