package dev.tonholo.s2c.remote.source

import okio.Path

/**
 * The normalized output of resolving a [RemoteSource].
 */
sealed interface ResolvedSource {
    /**
     * Local SVG/XML files at [paths], ready for the core parser pipeline.
     */
    data class Files(val paths: List<Path>) : ResolvedSource

    /**
     * Pre-parsed image vectors in [vectors], bypassing the SVG parser.
     */
    data class Vectors(val vectors: List<NamedImageVector>) : ResolvedSource
}
