package dev.tonholo.s2c.remote.http

import kotlinx.coroutines.delay
import okio.IOException
import okio.Sink

private const val DEFAULT_MAX_ATTEMPTS = 3
private const val DEFAULT_INITIAL_DELAY_MS = 1_000L
private const val BACKOFF_MULTIPLIER = 2
private const val SERVER_ERROR_MIN = 500
private const val SERVER_ERROR_MAX = 599
private val SERVER_ERROR_STATUSES = SERVER_ERROR_MIN..SERVER_ERROR_MAX

/**
 * [HttpTransport] decorator that retries server errors and IO failures with
 * exponential backoff. Client errors (4xx) are returned without retrying.
 */
class RetryingHttpTransport(
    private val delegate: HttpTransport,
    private val maxAttempts: Int = DEFAULT_MAX_ATTEMPTS,
    private val initialDelayMs: Long = DEFAULT_INITIAL_DELAY_MS,
    private val allowInsecure: Boolean = false,
) : HttpTransport {
    override suspend fun get(url: String, headers: Map<String, String>): HttpResponse {
        validateRemoteUrl(url, allowInsecure)
        return withRetries(url) { delegate.get(url, headers) }
    }

    override suspend fun head(url: String, headers: Map<String, String>): HttpResponse {
        validateRemoteUrl(url, allowInsecure)
        return withRetries(url) { delegate.head(url, headers) }
    }

    override suspend fun download(
        url: String,
        headers: Map<String, String>,
        sinkFactory: () -> Sink,
    ): HttpResponse {
        validateRemoteUrl(url, allowInsecure)
        return withRetries(url) { delegate.download(url, headers, sinkFactory) }
    }

    private suspend fun withRetries(
        url: String,
        request: suspend () -> HttpResponse,
    ): HttpResponse {
        var lastResponse: HttpResponse? = null
        var lastFailure: IOException? = null
        var backoffMs = initialDelayMs
        repeat(maxAttempts) { attempt ->
            if (attempt > 0) {
                delay(backoffMs)
                backoffMs *= BACKOFF_MULTIPLIER
            }
            try {
                val response = request()
                if (response.status !in SERVER_ERROR_STATUSES) {
                    return response
                }
                lastResponse = response
            } catch (exception: IOException) {
                lastFailure = exception
            }
        }
        return lastResponse
            ?: throw HttpTransportException(
                message = "Request to $url failed after $maxAttempts attempts",
                cause = lastFailure,
            )
    }
}
