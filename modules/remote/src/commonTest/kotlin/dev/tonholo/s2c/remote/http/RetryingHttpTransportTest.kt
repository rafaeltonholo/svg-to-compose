package dev.tonholo.s2c.remote.http

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okio.IOException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class RetryingHttpTransportTest {

    private val url = "https://example.com/icon.svg"

    @Test
    fun `given server errors then success - when get is called - then retries until success`() =
        runTest {
            // Arrange
            val fake = FakeHttpTransport(
                { response(status = 500) },
                { response(status = 503) },
                { response(status = 200) },
            )
            val transport = RetryingHttpTransport(fake)

            // Act
            val result = transport.get(url)

            // Assert
            assertEquals(200, result.status)
            assertEquals(3, fake.requestedUrls.size)
        }

    @Test
    fun `given client error - when get is called - then returns immediately without retry`() =
        runTest {
            // Arrange
            val fake = FakeHttpTransport(
                { response(status = 404) },
            )
            val transport = RetryingHttpTransport(fake)

            // Act
            val result = transport.get(url)

            // Assert
            assertEquals(404, result.status)
            assertEquals(1, fake.requestedUrls.size)
        }

    @Test
    fun `given persistent server error - when get is called - then returns last response after max attempts`() =
        runTest {
            // Arrange
            val fake = FakeHttpTransport(
                { response(status = 500) },
                { response(status = 500) },
                { response(status = 500) },
            )
            val transport = RetryingHttpTransport(fake)

            // Act
            val result = transport.get(url)

            // Assert
            assertEquals(500, result.status)
            assertEquals(3, fake.requestedUrls.size)
        }

    @Test
    fun `given persistent io failure - when get is called - then throws with url in message`() =
        runTest {
            // Arrange
            val fake = FakeHttpTransport(
                failing(IOException("connection reset")),
                failing(IOException("connection reset")),
                failing(IOException("connection reset")),
            )
            val transport = RetryingHttpTransport(fake)

            // Act
            val exception = assertFailsWith<HttpTransportException> {
                transport.get(url)
            }

            // Assert
            assertTrue(url in checkNotNull(exception.message))
            assertEquals(3, fake.requestedUrls.size)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `given io failure then success - when head is called - then retries with exponential backoff`() =
        runTest {
            // Arrange
            val fake = FakeHttpTransport(
                failing(IOException("timeout")),
                failing(IOException("timeout")),
                { response(status = 200) },
            )
            val transport = RetryingHttpTransport(fake)

            // Act
            val result = transport.head(url)

            // Assert
            assertEquals(200, result.status)
            assertEquals(3, fake.requestedUrls.size)
            assertEquals(3000, testScheduler.currentTime)
            assertEquals(listOf("HEAD", "HEAD", "HEAD"), fake.requests.map { request -> request.method })
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `given four attempts - when every attempt fails - then each delay doubles the previous one`() =
        runTest {
            // Arrange
            val fake = FakeHttpTransport(
                { response(status = 500) },
                { response(status = 500) },
                { response(status = 500) },
                { response(status = 500) },
            )
            val transport = RetryingHttpTransport(fake, maxAttempts = 4)

            // Act
            transport.get(url)

            // Assert
            assertEquals(4, fake.requestedUrls.size)
            assertEquals(1000 + 2000 + 4000, testScheduler.currentTime)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `given immediate success - when get is called - then no delay is introduced`() = runTest {
        // Arrange
        val fake = FakeHttpTransport({ response(status = 200) })
        val transport = RetryingHttpTransport(fake)

        // Act
        transport.get(url)

        // Assert
        assertEquals(0, testScheduler.currentTime)
    }
}
