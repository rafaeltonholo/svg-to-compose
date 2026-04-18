package dev.tonholo.s2c.cli.output.tui.state

import dev.tonholo.s2c.error.ErrorCode

/**
 * Terminal completion state shown as the last line in single-file mode.
 *
 * Populated when the single file finishes processing. Not used in batch mode,
 * which renders a summary section instead.
 */
internal sealed interface SingleFileCompletion {
    /**
     * The file was converted successfully.
     *
     * @property elapsedMs how long the conversion took, in milliseconds.
     */
    data class Success(val elapsedMs: Long) : SingleFileCompletion

    /**
     * The conversion failed.
     *
     * @property errorCode the category of the failure.
     * @property message the first line of the failure message.
     */
    data class Failure(val errorCode: ErrorCode, val message: String) : SingleFileCompletion
}
