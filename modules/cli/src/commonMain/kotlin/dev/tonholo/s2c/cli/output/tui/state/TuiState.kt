package dev.tonholo.s2c.cli.output.tui.state

internal data class TuiState(val header: HeaderState = HeaderState(), val progress: ProgressState? = null) {
    fun withHeader(transform: (HeaderState) -> HeaderState): TuiState = copy(header = transform(header))

    fun withProgress(transform: (ProgressState?) -> ProgressState): TuiState = copy(progress = transform(progress))
}
