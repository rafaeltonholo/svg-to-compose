package dev.tonholo.s2c.remote.http

import com.sun.net.httpserver.HttpServer
import kotlinx.coroutines.test.runTest
import okio.Buffer
import java.net.InetSocketAddress
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class HttpTransportIntegrationTest {

    @Test
    fun `given local server - when get and head are called - then status headers and body are returned`() =
        runTest {
            // Arrange
            val body = "<svg/>".encodeToByteArray()
            val seenHeaders = mutableListOf<String?>()
            val server = HttpServer.create(InetSocketAddress(0), 0)
            server.createContext("/icon.svg") { exchange ->
                seenHeaders += exchange.requestHeaders.getFirst("If-None-Match")
                exchange.responseHeaders.add("ETag", "\"v1\"")
                if (exchange.requestMethod == "HEAD") {
                    exchange.sendResponseHeaders(200, -1)
                } else {
                    exchange.sendResponseHeaders(200, body.size.toLong())
                    exchange.responseBody.use { stream -> stream.write(body) }
                }
                exchange.close()
            }
            server.start()

            try {
                val transport = createHttpTransport()
                val url = "http://localhost:${server.address.port}/icon.svg"

                // Act
                val getResponse = transport.get(url, headers = mapOf("If-None-Match" to "\"v0\""))
                val headResponse = transport.head(url)

                // Assert
                assertEquals(200, getResponse.status)
                assertEquals("<svg/>", getResponse.body.decodeToString())
                assertEquals("\"v1\"", getResponse.header("etag"))
                assertEquals(200, headResponse.status)
                assertEquals("\"v1\"", headResponse.header("ETag"))
                assertEquals(0, headResponse.body.size)
                assertEquals(listOf("\"v0\"", null), seenHeaders)
            } finally {
                server.stop(0)
            }
        }

    @Test
    fun `given local server - when download is called - then body streams to sink and response body is empty`() =
        runTest {
            // Arrange
            val body = "zip-bytes".encodeToByteArray()
            val server = HttpServer.create(InetSocketAddress(0), 0)
            server.createContext("/icons.zip") { exchange ->
                exchange.sendResponseHeaders(200, body.size.toLong())
                exchange.responseBody.use { stream -> stream.write(body) }
                exchange.close()
            }
            server.start()

            try {
                val transport = createHttpTransport()
                val url = "http://localhost:${server.address.port}/icons.zip"
                val buffer = Buffer()

                // Act
                val response = transport.download(url) { buffer }

                // Assert
                assertEquals(200, response.status)
                assertEquals(0, response.body.size)
                assertEquals("zip-bytes", buffer.readUtf8())
            } finally {
                server.stop(0)
            }
        }

    @Test
    fun `given server that truncates the body - when get is called - then throws HttpTransportException`() =
        runTest {
            // Arrange
            val promisedLength = 1_024L
            val server = HttpServer.create(InetSocketAddress(0), 0)
            server.createContext("/icon.svg") { exchange ->
                exchange.sendResponseHeaders(200, promisedLength)
                exchange.responseBody.write("<svg".encodeToByteArray())
                exchange.responseBody.flush()
                exchange.close()
            }
            server.start()

            try {
                val transport = createHttpTransport()
                val url = "http://localhost:${server.address.port}/icon.svg"

                // Act / Assert
                assertFailsWith<HttpTransportException> {
                    transport.get(url)
                }
            } finally {
                server.stop(0)
            }
        }
}
