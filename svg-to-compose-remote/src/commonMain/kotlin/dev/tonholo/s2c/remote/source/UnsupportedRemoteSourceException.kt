package dev.tonholo.s2c.remote.source

/**
 * Thrown when no resolver is available for [source].
 */
class UnsupportedRemoteSourceException(
    val source: RemoteSource,
) : IllegalArgumentException("Remote source type is not supported yet: $source")
