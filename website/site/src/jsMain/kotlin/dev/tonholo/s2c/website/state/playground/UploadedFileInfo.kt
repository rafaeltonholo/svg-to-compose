package dev.tonholo.s2c.website.state.playground

/**
 * Metadata for a single file uploaded by the user (from a directory upload).
 */
internal data class UploadedFileInfo(
    val name: String,
    val content: String,
    val detectedExtension: String,
    val relativePath: String = "",
)
