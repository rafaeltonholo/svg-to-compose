package dev.tonholo.s2c.output

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
    data class RunStarted(val config: RunConfig, val totalFiles: Int, val version: String) :
        ConversionEvent

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
    data class FileCompleted(val fileName: String, val duration: Duration, val result: FileResult) :
        ConversionEvent

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
