package dev.tonholo.s2c.remote.http

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class KtorTransportFailureTest {

    @Test
    fun `given unreachable host - when get is called through retries - then throws HttpTransportException`() =
        runTest {
            // Arrange
            val url = "http://127.0.0.1:9/icon.svg"
            val transport = RetryingHttpTransport(createHttpTransport(), allowInsecure = true)

            // Act
            val exception = assertFailsWith<HttpTransportException> {
                transport.get(url)
            }

            // Assert
            assertTrue(url in checkNotNull(exception.message))
        }
}
