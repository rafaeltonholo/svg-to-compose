package dev.tonholo.s2c.website.state.playground.preview

internal data class SourcePreviewContent(
    val svgCode: String,
    val extension: String,
    val zoomLevel: Float,
    val panX: Float,
    val panY: Float,
)
