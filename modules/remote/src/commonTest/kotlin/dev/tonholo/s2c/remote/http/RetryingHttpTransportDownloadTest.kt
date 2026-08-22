package dev.tonholo.s2c.remote.http

import kotlinx.coroutines.test.runTest
import okio.Buffer
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RetryingHttpTransportDownloadTest {

    private val url = "https://example.com/icons.zip"

    @Test
    fun `given server errors then success - when download is called - then body is written once after retries`() =
        runTest {
            // Arrange
            val fake = FakeHttpTransport(
                { response(status = 500) },
                { response(status = 200, body = "zip-bytes".encodeToByteArray()) },
            )
            val transport = RetryingHttpTransport(fake)
            val buffers = mutableListOf<Buffer>()

            // Act
            val result = transport.download(url = url) {
                Buffer().also(buffers::add)
            }

            // Assert
            assertEquals(200, result.status)
            assertEquals(0, result.body.size)
            assertEquals(listOf("DOWNLOAD", "DOWNLOAD"), fake.requests.map { request -> request.method })
            assertEquals(1, buffers.size)
            assertEquals("zip-bytes", buffers.single().readUtf8())
        }

    @Test
    fun `given http url without insecure opt-in - when download is called - then throws before any request`() =
        runTest {
            // Arrange
            val fake = FakeHttpTransport()
            val transport = RetryingHttpTransport(fake)

            // Act
            assertFailsWith<IllegalArgumentException> {
                transport.download(url = "http://example.com/icons.zip") { Buffer() }
            }

            // Assert
            assertEquals(emptyList(), fake.requests)
        }
}
