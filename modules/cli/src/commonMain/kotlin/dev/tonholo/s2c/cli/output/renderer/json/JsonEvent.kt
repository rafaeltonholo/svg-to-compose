package dev.tonholo.s2c.cli.output.renderer.json

import dev.tonholo.s2c.output.ConversionEvent
import dev.tonholo.s2c.output.ConversionPhase
import dev.tonholo.s2c.output.FileResult
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * Serializable JSON event models for JSONL output.
 *
 * Each subclass maps to a [ConversionEvent] variant and is serialized
 * as a single JSON object with an "event" discriminator field.
 */
@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("event")
internal sealed interface JsonEvent {

    @Serializable
    @SerialName("start")
    data class Start(
        val version: String,
        val input: String,
        val output: String,
        @SerialName("total_files")
        val totalFiles: Int,
    ) : JsonEvent

    @Serializable
    @SerialName("file_start")
    data class FileStart(val file: String, val index: Int) : JsonEvent

    @Serializable
    @SerialName("file_step")
    data class FileStep(val file: String, val step: String) : JsonEvent

    @Serializable
    @SerialName("file_complete")
    data class FileComplete(
        val file: String,
        val status: String,
        @SerialName("duration_ms")
        val durationMs: Long,
        @SerialName("error_code")
        val errorCode: String? = null,
        val message: String? = null,
    ) : JsonEvent

    @Serializable
    @SerialName("done")
    data class Done(
        val succeeded: Int,
        val failed: Int,
        @SerialName("duration_ms")
        val durationMs: Long,
        val throughput: Double,
    ) : JsonEvent

    @Serializable
    @SerialName("update_available")
    data class UpdateAvailable(val current: String, val latest: String, val url: String) : JsonEvent

    companion object {
        /**
         * Converts a [ConversionEvent] to its [JsonEvent] representation.
         */
        fun from(event: ConversionEvent): JsonEvent = when (event) {
            is ConversionEvent.RunStarted -> Start(
                version = event.version,
                input = event.config.inputPath,
                output = event.config.outputPath,
                totalFiles = event.totalFiles,
            )

            is ConversionEvent.FileStarted -> FileStart(
                file = event.fileName,
                index = event.index,
            )

            is ConversionEvent.FileStepChanged -> FileStep(
                file = event.fileName,
                step = event.step.toJsonName(),
            )

            is ConversionEvent.FileCompleted -> fileCompleteFrom(event)

            is ConversionEvent.RunCompleted -> Done(
                succeeded = event.stats.succeeded,
                failed = event.stats.failed,
                durationMs = event.stats.totalDuration.inWholeMilliseconds,
                throughput = computeThroughput(event),
            )

            is ConversionEvent.UpdateAvailable -> UpdateAvailable(
                current = event.current,
                latest = event.latest,
                url = event.releaseUrl,
            )
        }

        private fun fileCompleteFrom(event: ConversionEvent.FileCompleted): FileComplete =
            when (val result = event.result) {
                is FileResult.Success -> FileComplete(
                    file = event.fileName,
                    status = "success",
                    durationMs = event.duration.inWholeMilliseconds,
                )

                is FileResult.Failed -> FileComplete(
                    file = event.fileName,
                    status = "failed",
                    durationMs = event.duration.inWholeMilliseconds,
                    errorCode = result.errorCode.name,
                    message = result.message,
                )
            }

        private fun computeThroughput(event: ConversionEvent.RunCompleted): Double {
            val seconds = event.stats.totalDuration.inWholeMilliseconds / MILLIS_PER_SECOND
            if (seconds <= 0.0) return 0.0
            return event.stats.succeeded.toDouble() / seconds
        }

        private fun ConversionPhase.toJsonName(): String = name.lowercase()

        private const val MILLIS_PER_SECOND = 1000.0
    }
}
