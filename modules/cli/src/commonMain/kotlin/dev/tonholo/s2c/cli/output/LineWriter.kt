package dev.tonholo.s2c.cli.output

/**
 * Sink for the lines the CLI prints to standard output.
 *
 * Bound to `println` in the application graph. Tests bind a collecting
 * implementation so the whole stdout stream can be asserted on without
 * touching the process streams.
 */
internal fun interface LineWriter {
    fun write(line: String)
}
