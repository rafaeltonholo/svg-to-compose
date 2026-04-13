package dev.tonholo.s2c.cli.output.tui.state

internal data class ProgressState(
    val total: Long = 0,
    val pending: Long = 0,
    val completed: Long = 0,
    val failed: Long = 0,
    val skipped: Long = 0,
    val errors: List<String> = emptyList(),
)
