package dev.tonholo.s2c.emitter

/**
 * Supported output formats for code generation.
 *
 * Currently only [IMAGE_VECTOR] is supported. Future formats
 * (e.g., Canvas, VectorPainter) can be added without modifying
 * domain classes.
 */
enum class OutputFormat {
    /** Generates Jetpack Compose `ImageVector.Builder` code. */
    IMAGE_VECTOR,
}
