package dev.tonholo.s2c.cli.output.tui.state

import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.output.RunConfig
import dev.tonholo.s2c.output.RunStats

/**
 * A single file that failed to convert during a run.
 *
 * The [fileName] is reported verbatim from [dev.tonholo.s2c.output.ConversionEvent.FileCompleted];
 * the core pipeline currently emits basenames, not paths relative to the input directory.
 *
 * @property fileName the name of the file that failed.
 * @property errorCode the [ErrorCode] grouping this failure.
 * @property message the human-readable error message.
 * @property stackTrace the optional exception stack trace captured by the processor.
 */
internal data class FailedFileEntry(
    val fileName: String,
    val errorCode: ErrorCode,
    val message: String,
    val stackTrace: String? = null,
)

/**
 * Snapshot of a completed run used to render the TUI completion summary.
 *
 * The state is built up across events:
 *  - [dev.tonholo.s2c.output.ConversionEvent.RunStarted] captures the header metadata and resets the
 *    list of failed files.
 *  - [dev.tonholo.s2c.output.ConversionEvent.FileCompleted] appends an entry to [failedFiles] when
 *    the file fails.
 *  - [dev.tonholo.s2c.output.ConversionEvent.RunCompleted] finalizes the snapshot by attaching
 *    [stats]; this is the signal the renderer uses to stop the live animation.
 *
 * @property version the CLI version as reported at [dev.tonholo.s2c.output.ConversionEvent.RunStarted].
 * @property config the captured [RunConfig] for the header line; null before the run starts.
 * @property totalFiles the total number of files reported at run start.
 * @property failedFiles the ordered list of failed files accumulated during the run.
 * @property stats the aggregate run statistics; non-null only after the run completes.
 */
internal data class CompletionState(
    val version: String = "",
    val config: RunConfig? = null,
    val totalFiles: Int = 0,
    val failedFiles: List<FailedFileEntry> = emptyList(),
    val stats: RunStats? = null,
) {
    val isCompleted: Boolean get() = stats != null
}
