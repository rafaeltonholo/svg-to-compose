package dev.tonholo.s2c.remote.http

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class HttpResponseTest {

    @Test
    fun `given headers with mixed casing - when header is queried - then lookup is case-insensitive`() {
        // Arrange
        val response = HttpResponse(
            status = 200,
            headers = mapOf(
                "ETag" to listOf("\"abc123\""),
                "last-modified" to listOf("Wed, 01 Jan 2026 00:00:00 GMT"),
            ),
            body = ByteArray(0),
        )

        // Act / Assert
        assertEquals("\"abc123\"", response.header("etag"))
        assertEquals("Wed, 01 Jan 2026 00:00:00 GMT", response.header("Last-Modified"))
        assertNull(response.header("content-length"))
    }

    @Test
    fun `given header with multiple values - when header is queried - then first value is returned`() {
        // Arrange
        val response = HttpResponse(
            status = 200,
            headers = mapOf("Vary" to listOf("Accept-Encoding", "User-Agent")),
            body = ByteArray(0),
        )

        // Act / Assert
        assertEquals("Accept-Encoding", response.header("vary"))
    }
}
