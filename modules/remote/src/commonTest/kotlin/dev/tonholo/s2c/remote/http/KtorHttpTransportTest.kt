package dev.tonholo.s2c.remote.http

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteChannel
import kotlinx.coroutines.test.runTest
import kotlinx.io.IOException
import okio.Buffer
import okio.Sink
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class KtorHttpTransportTest {

    private class RecordedCall {
        val methods = mutableListOf<HttpMethod>()
        val headers = mutableListOf<String?>()
    }

    private class ClosingSink(private val delegate: Sink) : Sink by delegate {
        var closed = false

        override fun close() {
            closed = true
            delegate.close()
        }
    }

    private fun transportReturning(body: String, recorded: RecordedCall): KtorHttpTransport {
        val engine = MockEngine { request ->
            recorded.methods += request.method
            recorded.headers += request.headers["If-None-Match"]
            respond(content = body, status = HttpStatusCode.OK, headers = headersOf("ETag", "\"v7\""))
        }
        return KtorHttpTransport(HttpClient(engine))
    }

    @Test
    fun `given mock engine - when get is called - then status headers and body are returned`() =
        runTest {
            // Arrange
            val recorded = RecordedCall()
            val transport = transportReturning(body = "payload", recorded = recorded)

            // Act
            val response = transport.get("https://example.com/icon.svg", headers = mapOf("If-None-Match" to "\"v7\""))

            // Assert
            assertEquals(200, response.status)
            assertEquals("\"v7\"", response.header("etag"))
            assertEquals("payload", response.body.decodeToString())
            assertEquals(listOf(HttpMethod.Get), recorded.methods)
            assertEquals(listOf<String?>("\"v7\""), recorded.headers)
        }

    @Test
    fun `given mock engine - when head is called - then head method is used`() = runTest {
        // Arrange
        val recorded = RecordedCall()
        val transport = transportReturning(body = "", recorded = recorded)

        // Act
        val response = transport.head("https://example.com/icon.svg")

        // Assert
        assertEquals(200, response.status)
        assertEquals(listOf(HttpMethod.Head), recorded.methods)
    }

    @Test
    fun `given mock engine - when download is called - then body streams to sink and response body is empty`() =
        runTest {
            // Arrange
            val recorded = RecordedCall()
            val transport = transportReturning(body = "zip-bytes", recorded = recorded)
            val buffer = Buffer()

            // Act
            val response = transport.download("https://example.com/icons.zip") { buffer }

            // Assert
            assertEquals(200, response.status)
            assertEquals(0, response.body.size)
            assertEquals("zip-bytes", buffer.readUtf8())
            assertEquals(listOf(HttpMethod.Get), recorded.methods)
        }

    @Test
    fun `given mock engine - when download completes - then the sink is closed`() = runTest {
        // Arrange
        val transport = transportReturning(body = "zip-bytes", recorded = RecordedCall())
        val sink = ClosingSink(Buffer())

        // Act
        transport.download("https://example.com/icons.zip") { sink }

        // Assert
        assertTrue(sink.closed)
    }

    @Test
    fun `given engine that fails with a non-io error - when get is called - then throws HttpTransportException`() =
        runTest {
            // Arrange: WinHttp reports connection failures as IllegalStateException, not IOException.
            val engine = MockEngine { error("Failed to send request: A connection could not be established") }
            val transport = KtorHttpTransport(HttpClient(engine))

            // Act / Assert
            assertFailsWith<HttpTransportException> {
                transport.get("https://example.com/icon.svg")
            }
        }

    @Test
    fun `given body that fails while reading - when get is called - then throws HttpTransportException`() =
        runTest {
            // Arrange
            val engine = MockEngine {
                val channel = ByteChannel(autoFlush = true)
                channel.cancel(IOException("connection reset"))
                respond(content = channel, status = HttpStatusCode.OK)
            }
            val transport = KtorHttpTransport(HttpClient(engine))

            // Act / Assert
            assertFailsWith<HttpTransportException> {
                transport.get("https://example.com/icon.svg")
            }
        }
}
