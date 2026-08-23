package dev.tonholo.s2c.remote.http

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.request.headers
import io.ktor.client.request.prepareRequest
import io.ktor.client.request.request
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.HttpMethod
import io.ktor.http.IllegalHeaderNameException
import io.ktor.http.IllegalHeaderValueException
import io.ktor.http.isSuccess
import io.ktor.util.toMap
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.readRemaining
import kotlinx.coroutines.CancellationException
import kotlinx.io.readByteArray
import okio.Sink
import okio.buffer
import okio.use

private const val DOWNLOAD_CHUNK_BYTES = 8_192L

/**
 * Creates the [HttpTransport] backed by the Ktor engine on the classpath.
 */
fun createHttpTransport(): HttpTransport = KtorHttpTransport(
    HttpClient {
        install(HttpTimeout) {
            connectTimeoutMillis = HttpTimeouts.CONNECT_TIMEOUT_MS
            socketTimeoutMillis = HttpTimeouts.SOCKET_TIMEOUT_MS
        }
    },
)

internal class KtorHttpTransport(private val client: HttpClient) : HttpTransport {
    override suspend fun get(url: String, headers: Map<String, String>): HttpResponse =
        execute(url = url, headers = headers, method = HttpMethod.Get)

    override suspend fun head(url: String, headers: Map<String, String>): HttpResponse =
        execute(url = url, headers = headers, method = HttpMethod.Head)

    override suspend fun download(
        url: String,
        headers: Map<String, String>,
        sinkFactory: () -> Sink,
    ): HttpResponse = runCatching {
        client.prepareRequest(url) {
            method = HttpMethod.Get
            headers {
                headers.forEach { (name, value) -> append(name, value) }
            }
        }.execute { response ->
            if (response.status.isSuccess()) {
                response.bodyAsChannel().writeTo(sinkFactory())
            }
            HttpResponse(
                status = response.status.value,
                headers = response.headers.toMap(),
                body = ByteArray(0),
            )
        }
    }.getOrElse { failure -> throw failure.asTransportFailure(url) }

    private suspend fun execute(
        url: String,
        headers: Map<String, String>,
        method: HttpMethod,
    ): HttpResponse {
        val response = runCatching {
            client.request(url) {
                this.method = method
                headers {
                    headers.forEach { (name, value) -> append(name, value) }
                }
            }
        }.getOrElse { failure -> throw failure.asTransportFailure(url) }
        return HttpResponse(
            status = response.status.value,
            headers = response.headers.toMap(),
            body = response.body(),
        )
    }
}

private suspend fun ByteReadChannel.writeTo(sink: Sink) {
    sink.buffer().use { bufferedSink ->
        while (!isClosedForRead) {
            val chunk = readRemaining(DOWNLOAD_CHUNK_BYTES)
            bufferedSink.write(chunk.readByteArray())
        }
    }
}

/**
 * Every Ktor engine reports connection failures with its own exception type:
 * `IOException` on CIO and OkHttp, `IllegalStateException` on WinHttp, and a
 * bare `kotlin.Error` on the JS engines. Anything escaping a request call is a
 * failed request, so it is wrapped rather than matched by type.
 *
 * Rejected headers are the exception: the request never left, and retrying
 * re-sends the same invalid value, so they propagate unwrapped.
 */
private fun Throwable.asTransportFailure(url: String): Throwable = when (this) {
    is CancellationException,
    is IllegalHeaderNameException,
    is IllegalHeaderValueException,
    -> this

    else -> HttpTransportException(message = "Request to $url failed", cause = this)
}
