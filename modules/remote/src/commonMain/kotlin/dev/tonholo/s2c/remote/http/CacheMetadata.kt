package dev.tonholo.s2c.remote.http

/**
 * Cache validation metadata extracted from response headers, used to decide
 * whether a remote source needs re-downloading.
 */
data class CacheMetadata(val etag: String?, val lastModified: String?, val contentLength: Long?)

/**
 * Issues a HEAD request to [url] and extracts the [CacheMetadata] from the
 * response headers.
 *
 * @throws HttpTransportException when the response status is not a success.
 */
suspend fun HttpTransport.fetchCacheMetadata(url: String): CacheMetadata {
    val response = head(url)
    if (!response.isSuccess) {
        throw HttpTransportException(
            message = "HEAD request to $url returned status ${response.status}",
        )
    }
    return CacheMetadata(
        etag = response.header("ETag"),
        lastModified = response.header("Last-Modified"),
        contentLength = response.header("Content-Length")?.toLongOrNull(),
    )
}
