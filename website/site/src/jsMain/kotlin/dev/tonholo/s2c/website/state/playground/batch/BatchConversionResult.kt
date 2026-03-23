package dev.tonholo.s2c.website.state.playground.batch

/**
 * Result of converting a single file during batch conversion.
 */
internal data class BatchConversionResult(
    val fileName: String,
    val originalContent: String,
    val detectedExtension: String,
    val relativePath: String = "",
    val kotlinCode: String? = null,
    val iconFileContentsJson: String? = null,
    val error: String? = null,
) {
    val isSuccess: Boolean get() = kotlinCode != null && error == null
}
