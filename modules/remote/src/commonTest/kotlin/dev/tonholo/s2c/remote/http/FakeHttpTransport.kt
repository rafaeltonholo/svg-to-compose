package dev.tonholo.s2c.remote.http

import okio.IOException
import okio.Sink
import okio.buffer
import okio.use

internal data class RecordedRequest(
    val method: String,
    val url: String,
    val headers: Map<String, String>,
)

internal class FakeHttpTransport(vararg responses: () -> HttpResponse) : HttpTransport {
    private val queue = ArrayDeque(responses.toList())
    val requests = mutableListOf<RecordedRequest>()
    val requestedUrls: List<String> get() = requests.map { request -> request.url }

    override suspend fun get(url: String, headers: Map<String, String>): HttpResponse =
        respond(method = "GET", url = url, headers = headers)

    override suspend fun head(url: String, headers: Map<String, String>): HttpResponse =
        respond(method = "HEAD", url = url, headers = headers)

    override suspend fun download(
        url: String,
        headers: Map<String, String>,
        sinkFactory: () -> Sink,
    ): HttpResponse {
        val response = respond(method = "DOWNLOAD", url = url, headers = headers)
        if (response.status in 200..299) {
            sinkFactory().buffer().use { sink -> sink.write(response.body) }
        }
        return HttpResponse(status = response.status, headers = response.headers, body = ByteArray(0))
    }

    private fun respond(
        method: String,
        url: String,
        headers: Map<String, String>,
    ): HttpResponse {
        requests += RecordedRequest(method = method, url = url, headers = headers)
        val next = queue.removeFirstOrNull() ?: throw IOException("FakeHttpTransport queue is empty")
        return next()
    }
}

internal fun response(
    status: Int,
    headers: Map<String, List<String>> = emptyMap(),
    body: ByteArray = ByteArray(0),
): HttpResponse = HttpResponse(status = status, headers = headers, body = body)

internal fun failing(exception: Throwable): () -> HttpResponse = { throw exception }
