package dev.tonholo.s2c.output

/**
 * The ordered phases a single file goes through during conversion.
 *
 * Declaration order represents the processing pipeline sequence.
 * Renderers may rely on [ordinal] to display progress indicators,
 * so new phases must be inserted at the correct pipeline position.
 */
enum class ConversionPhase {
    Optimizing,
    Parsing,
    Generating,
    Writing,
}
