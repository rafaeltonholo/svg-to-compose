package dev.tonholo.s2c.cli.output.tui.state

/**
 * Indicates which TUI layout mode is active.
 *
 * The batch dashboard renders progress bars, rolling logs, and summary
 * counters. The single-file layout omits those sections and shows only
 * the header, the current-file step list, and a terminal completion line.
 *
 * Mode is selected on [dev.tonholo.s2c.output.ConversionEvent.RunStarted]
 * based on the total number of files.
 */
internal sealed interface TuiMode {
    /**
     * Multi-file dashboard mode.
     */
    data object Batch : TuiMode

    /**
     * Single-file conversion mode.
     */
    data object Single : TuiMode
}
