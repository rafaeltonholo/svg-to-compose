package dev.tonholo.s2c.remote.http

import kotlinx.io.IOException

internal actual fun translateEngineFailure(failure: Throwable): IOException? =
    (failure as? Error)?.let { error ->
        IOException(error.message ?: "Network request failed", error)
    }
