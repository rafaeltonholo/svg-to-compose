package dev.tonholo.s2c.remote.http

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CacheMetadataTest {

    @Test
    fun `given head response with cache headers - when fetchCacheMetadata is called - then metadata is extracted`() =
        runTest {
            // Arrange
            val transport = FakeHttpTransport(
                {
                    response(
                        status = 200,
                        headers = mapOf(
                            "etag" to listOf("\"v42\""),
                            "Last-Modified" to listOf("Wed, 01 Jan 2026 00:00:00 GMT"),
                            "CONTENT-LENGTH" to listOf("1024"),
                        ),
                    )
                },
            )

            // Act
            val metadata = transport.fetchCacheMetadata("https://example.com/icons.zip")

            // Assert
            assertEquals("\"v42\"", metadata.etag)
            assertEquals("Wed, 01 Jan 2026 00:00:00 GMT", metadata.lastModified)
            assertEquals(1024L, metadata.contentLength)
        }

    @Test
    fun `given head request - when fetchCacheMetadata is called - then exactly one head request is issued`() =
        runTest {
            // Arrange
            val transport = FakeHttpTransport({ response(status = 200) })

            // Act
            transport.fetchCacheMetadata("https://example.com/icons.zip")

            // Assert
            assertEquals(listOf("HEAD"), transport.requests.map { request -> request.method })
        }

    @Test
    fun `given not found head response - when fetchCacheMetadata is called - then throws with url and status`() =
        runTest {
            // Arrange
            val transport = FakeHttpTransport({ response(status = 404) })
            val url = "https://example.com/missing.zip"

            // Act
            val exception = assertFailsWith<HttpTransportException> {
                transport.fetchCacheMetadata(url)
            }

            // Assert
            val message = checkNotNull(exception.message)
            assertTrue(url in message)
            assertTrue("404" in message)
        }

    @Test
    fun `given head response without cache headers - when fetchCacheMetadata is called - then fields are null`() =
        runTest {
            // Arrange
            val transport = FakeHttpTransport(
                { response(status = 200, headers = mapOf("content-length" to listOf("not-a-number"))) },
            )

            // Act
            val metadata = transport.fetchCacheMetadata("https://example.com/icons.zip")

            // Assert
            assertNull(metadata.etag)
            assertNull(metadata.lastModified)
            assertNull(metadata.contentLength)
        }
}
