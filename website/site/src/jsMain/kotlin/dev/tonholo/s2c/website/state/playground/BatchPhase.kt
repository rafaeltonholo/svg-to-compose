package dev.tonholo.s2c.website.state.playground

/**
 * Represents the current phase of the batch conversion workflow.
 *
 * Transitions:
 * - Upload → [Select] (files loaded)
 * - [Select] → [Converting] (user starts conversion)
 * - [Converting] → [Results] (all done or cancelled)
 * - [Results] → [Select] (user restarts)
 */
internal sealed interface BatchPhase {
    /** User is selecting which files to convert. */
    data object Select : BatchPhase

    /**
     * Conversion is in progress.
     *
     * Note: completed results are accumulated in the ViewModel's
     * [MutableList] for O(1) append performance, not in this
     * immutable data class. Only [completedCount] is tracked here
     * to avoid O(n²) list copies on every file completion.
     */
    data class Converting(
        val total: Int,
        val completedCount: Int = 0,
        val cancelling: Boolean = false,
        val startTimeMs: Double = 0.0,
    ) : BatchPhase

    /** Conversion finished (or was cancelled). */
    data class Results(
        val completed: List<BatchConversionResult>,
        val cancelled: Boolean,
        val durationMs: Double = 0.0,
    ) : BatchPhase
}

/**
 * A group of files sharing the same folder path, for collapsible
 * display in the batch panel.
 *
 * @property folderPath The relative folder path, or `""` for
 *   root-level files (displayed without a folder header).
 * @property files The files in this group, sorted alphabetically.
 */
internal data class FileGroup(val folderPath: String, val files: List<UploadedFileInfo>)

/**
 * Builds a file key for selection tracking.
 * Format: `"relativePath/name"` or just `"name"` for root files.
 */
internal fun UploadedFileInfo.fileKey(): String = if (relativePath.isNotEmpty()) "$relativePath/$name" else name

/**
 * Groups [UploadedFileInfo] by [UploadedFileInfo.relativePath],
 * sorting groups and files alphabetically.
 */
internal fun List<UploadedFileInfo>.toFileGroups(): List<FileGroup> = groupBy { it.relativePath }
    .entries
    .sortedBy { it.key }
    .map { (path, files) ->
        FileGroup(
            folderPath = path,
            files = files.sortedBy { it.name },
        )
    }
