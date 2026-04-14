package dev.tonholo.s2c.dispatching

import okio.Path

/**
 * Result of processing a single file through the conversion pipeline.
 */
sealed interface FileProcessingResult {
    /** File was successfully converted. */
    data class Success(val path: Path) : FileProcessingResult

    /** File conversion failed with a recoverable error. */
    data class Failed(val file: Path, val error: Exception) : FileProcessingResult
}
