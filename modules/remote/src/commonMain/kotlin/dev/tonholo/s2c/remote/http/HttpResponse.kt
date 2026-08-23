package dev.tonholo.s2c.remote.http

private const val SUCCESS_STATUS_MIN = 200
private const val SUCCESS_STATUS_MAX = 299

/**
 * A raw HTTP response: [status] code, response [headers], and [body] bytes.
 */
class HttpResponse(val status: Int, val headers: Map<String, List<String>>, val body: ByteArray) {
    /**
     * Returns the first value of the header named [name], ignoring case.
     */
    fun header(name: String): String? = headers.entries
        .firstOrNull { (key, _) -> key.equals(name, ignoreCase = true) }
        ?.value
        ?.firstOrNull()
}

/**
 * Whether [HttpResponse.status] is in the 2xx success range.
 */
val HttpResponse.isSuccess: Boolean
    get() = status in SUCCESS_STATUS_MIN..SUCCESS_STATUS_MAX
