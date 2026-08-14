package dev.tonholo.s2c.remote.source

import okio.Path

/**
 * [SourceResolver] that routes each [RemoteSource] type to its resolver.
 *
 * No source types are wired yet; every call throws
 * [UnsupportedRemoteSourceException] until the per-type resolvers land.
 */
class DefaultSourceResolver : SourceResolver {
    override suspend fun resolve(source: RemoteSource, outputDir: Path): ResolvedSource {
        throw UnsupportedRemoteSourceException(source)
    }
}
