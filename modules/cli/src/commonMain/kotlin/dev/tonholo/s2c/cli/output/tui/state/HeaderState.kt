package dev.tonholo.s2c.cli.output.tui.state

import dev.tonholo.s2c.output.RunConfig

internal data class HeaderState(
    val version: String = "",
    val expanded: Boolean = false,
    val config: RunConfig? = null,
    val totalFiles: Int = 0,
)
