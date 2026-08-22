package dev.tonholo.s2c.remote.http

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RetryingHttpTransportValidationTest {

    @Test
    fun `given http url without insecure opt-in - when get is called - then throws before any request`() =
        runTest {
            // Arrange
            val fake = FakeHttpTransport()
            val transport = RetryingHttpTransport(fake)

            // Act
            assertFailsWith<IllegalArgumentException> {
                transport.get("http://example.com/icon.svg")
            }

            // Assert
            assertEquals(emptyList(), fake.requests)
        }

    @Test
    fun `given http url with insecure opt-in - when get is called - then request is executed`() =
        runTest {
            // Arrange
            val fake = FakeHttpTransport({ response(status = 200) })
            val transport = RetryingHttpTransport(fake, allowInsecure = true)

            // Act
            val result = transport.get("http://example.com/icon.svg")

            // Assert
            assertEquals(200, result.status)
            assertEquals(1, fake.requests.size)
        }

    @Test
    fun `given custom headers and a retried request - when get is called - then headers are sent on every attempt`() =
        runTest {
            // Arrange
            val fake = FakeHttpTransport(
                { response(status = 500) },
                { response(status = 200) },
            )
            val transport = RetryingHttpTransport(fake)
            val headers = mapOf("If-None-Match" to "\"v42\"")

            // Act
            transport.get("https://example.com/icon.svg", headers = headers)

            // Assert
            assertEquals(2, fake.requests.size)
            assertEquals(listOf(headers, headers), fake.requests.map { request -> request.headers })
        }
}
