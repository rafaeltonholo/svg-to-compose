package dev.tonholo.s2c.remote.source

import okio.Path

/**
 * Resolves a [RemoteSource] into local files or pre-parsed image vectors.
 */
interface SourceResolver {
    /**
     * Resolves [source], writing any produced files under [outputDir].
     *
     * @throws UnsupportedRemoteSourceException when no resolver is available
     *  for the given source type.
     */
    suspend fun resolve(source: RemoteSource, outputDir: Path): ResolvedSource
}
