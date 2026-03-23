package dev.tonholo.s2c.website.state.playground

import dev.tonholo.s2c.website.state.playground.batch.BatchPhase
import dev.tonholo.s2c.website.state.playground.batch.FileGroup

/** Holds the full mutable state for the playground section. */
internal data class PlaygroundState(
    val selectedSample: Int = -1,
    val inputCode: String = "",
    val activePanel: Int = 0,
    val inputMode: String = "paste",
    val extension: String = "svg",
    val inputFileName: String = "input.svg",
    val outputFileName: String = "MyIcon.kt",
    val previewExpanded: Boolean = false,
    val viewingBatchResult: Boolean = false,
    val viewingBatchIndex: Int = -1,
    val options: PlaygroundOptions = PlaygroundOptions(),
    val convertedKotlinCode: String = "",
    val iconFileContentsJson: String? = null,
    val isConverting: Boolean = false,
    val conversionProgress: String = "",
    val conversionError: String? = null,
    val zoomLevel: Float = 1f,
    val uploadedFiles: List<UploadedFileInfo> = emptyList(),
    val batchPhase: BatchPhase? = null,
    val fileGroups: List<FileGroup> = emptyList(),
    val selectedFiles: Set<String> = emptySet(),
    val expandedFolders: Set<String> = emptySet(),
) {
    companion object {
        // region Sample Data
        internal data class SampleData(val name: String, val path: String)

        internal val samples = listOf(
            SampleData(name = "Heart", path = "/samples/heart.svg"),
            SampleData(name = "Star", path = "/samples/star.svg"),
            SampleData(name = "Droid", path = "/samples/droid.svg"),
            SampleData(name = "Kotlin", path = "/samples/kotlin.svg"),
        )

        // endregion
    }
}
