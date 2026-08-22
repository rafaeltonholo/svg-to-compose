package dev.tonholo.s2c.remote.http

import okio.IOException

/**
 * Thrown when a request cannot be completed after exhausting retries.
 */
class HttpTransportException(message: String, cause: Throwable? = null) :
    IOException(message, cause)
