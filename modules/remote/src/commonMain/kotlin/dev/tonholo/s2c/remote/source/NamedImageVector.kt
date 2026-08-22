package dev.tonholo.s2c.remote.source

import dev.tonholo.s2c.domain.ImageVectorNode

/**
 * A pre-parsed image vector identified by [name], ready for code emission
 * without going through the SVG parser.
 */
data class NamedImageVector(
    val name: String,
    val nodes: List<ImageVectorNode>,
    val width: Float,
    val height: Float,
    val viewportWidth: Float,
    val viewportHeight: Float,
)
