package dev.tonholo.s2c.remote.http

import okio.Sink

/**
 * Minimal HTTP client abstraction for downloading remote sources.
 *
 * Every method fails with [HttpTransportException] when the request cannot be
 * completed, or with [IllegalArgumentException] when a header name or value is
 * rejected before the request is sent. Only the former is retried, because a
 * rejected header fails identically on every attempt. Header values taken from
 * a previous response, such as an `ETag` echoed back as `If-None-Match`, are
 * the realistic source of the latter.
 */
interface HttpTransport {
    /**
     * Executes a GET request against [url] with optional extra [headers].
     */
    suspend fun get(url: String, headers: Map<String, String> = emptyMap()): HttpResponse

    /**
     * Executes a HEAD request against [url] with optional extra [headers].
     */
    suspend fun head(url: String, headers: Map<String, String> = emptyMap()): HttpResponse

    /**
     * Executes a GET request against [url], streaming the response body into
     * a sink from [sinkFactory] when the response is successful, so large
     * archives are never buffered in memory. Each attempt requests a fresh
     * sink, and the sink is closed before returning.
     *
     * @return the response status and headers with an empty body.
     */
    suspend fun download(
        url: String,
        headers: Map<String, String> = emptyMap(),
        sinkFactory: () -> Sink,
    ): HttpResponse
}
