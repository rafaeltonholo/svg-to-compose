package dev.tonholo.s2c.output

import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.parser.ParserConfig
import kotlin.time.Duration

/**
 * Represents events emitted during the SVG/AVG-to-Compose conversion process.
 *
 * Consumers observe these events through an [OutputRenderer] to display
 * progress, collect metrics, or drive UI updates.
 */
sealed interface ConversionEvent {
    /**
     * Emitted once at the beginning of a conversion run.
     *
     * @property config the resolved runtime configuration for this run.
     * @property totalFiles the number of files that will be processed.
     * @property version the current version of the tool.
     */
    data class RunStarted(val config: RunConfig, val totalFiles: Int, val version: String) : ConversionEvent

    /**
     * Emitted when an individual file begins processing.
     *
     * @property fileName the name of the file being processed.
     * @property index the zero-based position of this file in the run.
     */
    data class FileStarted(val fileName: String, val index: Int) : ConversionEvent

    /**
     * Emitted when a file transitions to a new conversion phase.
     *
     * @property fileName the name of the file being processed.
     * @property step the phase the file has entered.
     */
    data class FileStepChanged(val fileName: String, val step: ConversionPhase) : ConversionEvent

    /**
     * Emitted when a file finishes processing, successfully or not.
     *
     * @property fileName the name of the file that was processed.
     * @property duration how long processing took.
     * @property result the outcome of the conversion.
     */
    data class FileCompleted(val fileName: String, val duration: Duration, val result: FileResult) : ConversionEvent

    /**
     * Emitted once at the end of a conversion run with aggregate statistics.
     *
     * @property stats the summary statistics for the completed run.
     */
    data class RunCompleted(val stats: RunStats) : ConversionEvent

    /**
     * Emitted when a newer version of the tool is available.
     *
     * @property current the currently running version string.
     * @property latest the newest available version string.
     * @property releaseUrl a URL pointing to the release page.
     * @property isWrapper true if the update applies to the Gradle wrapper plugin.
     */
    data class UpdateAvailable(
        val current: String,
        val latest: String,
        val releaseUrl: String,
        val isWrapper: Boolean,
    ) : ConversionEvent
}

/**
 * The ordered phases a single file goes through during conversion.
 *
 * Declaration order represents the processing pipeline sequence.
 * Renderers may rely on [ordinal] to display progress indicators,
 * so new phases must be inserted at the correct pipeline position.
 */
enum class ConversionPhase {
    Optimizing,
    Parsing,
    Generating,
    Writing,
}

/**
 * The outcome of converting a single file.
 */
sealed interface FileResult {
    /**
     * The file was converted successfully.
     */
    data object Success : FileResult

    /**
     * The file conversion failed.
     *
     * @property errorCode the [ErrorCode] identifying the failure category.
     * @property message a human-readable description of what went wrong.
     */
    data class Failed(val errorCode: ErrorCode, val message: String) : FileResult
}

/**
 * Display-oriented snapshot of the parameters for a conversion run.
 *
 * This is intentionally a separate type from [dev.tonholo.s2c.runtime.S2cConfig]
 * and [ParserConfig]. While those types control runtime behavior and may be mutable
 * or extended over time, [RunConfig] captures exactly the information that output
 * renderers need to display in headers and summaries. Keeping it decoupled avoids
 * leaking internal configuration details into the rendering layer.
 *
 * @property inputPath the source file or directory path.
 * @property outputPath the destination file or directory path.
 * @property packageName the Kotlin package for generated code.
 * @property optimizationEnabled whether SVG/AVG optimization is active.
 * @property recursive whether directory traversal is recursive.
 */
data class RunConfig(
    val inputPath: String,
    val outputPath: String,
    val packageName: String,
    val optimizationEnabled: Boolean,
    val recursive: Boolean,
) {
    companion object {
        /**
         * Creates a [RunConfig] from the runtime configuration objects.
         *
         * This is the single translation site between internal config types and the
         * display-oriented [RunConfig].
         *
         * @param config the parser configuration for this run.
         * @param inputPath the source file or directory path.
         * @param outputPath the destination file or directory path.
         * @param recursive whether directory traversal is recursive.
         */
        fun from(config: ParserConfig, inputPath: String, outputPath: String, recursive: Boolean): RunConfig =
            RunConfig(
                inputPath = inputPath,
                outputPath = outputPath,
                packageName = config.pkg,
                optimizationEnabled = config.optimize,
                recursive = recursive,
            )
    }
}

/**
 * Aggregate statistics for a completed conversion run.
 *
 * @property totalFiles the total number of files that were processed.
 * @property succeeded the number of files that converted successfully.
 * @property failed the number of files that failed to convert.
 * @property totalDuration the wall-clock duration of the entire run.
 * @property errorCounts per-[ErrorCode] counts for failures.
 */
data class RunStats(
    val totalFiles: Int,
    val succeeded: Int,
    val failed: Int,
    val totalDuration: Duration,
    val errorCounts: Map<ErrorCode, Int>,
) {
    init {
        require(totalFiles >= 0) { "totalFiles must be non-negative, was $totalFiles" }
        require(succeeded >= 0) { "succeeded must be non-negative, was $succeeded" }
        require(failed >= 0) { "failed must be non-negative, was $failed" }
        require(succeeded + failed == totalFiles) {
            "succeeded ($succeeded) + failed ($failed) must equal totalFiles ($totalFiles)"
        }
    }
}
