package dev.tonholo.s2c.remote.http

private const val SECURE_SCHEME = "https://"
private const val INSECURE_SCHEME = "http://"

/**
 * Validates that [url] uses `https`, or `http` when [allowInsecure] is set.
 *
 * @throws IllegalArgumentException when the scheme is unsupported.
 */
fun validateRemoteUrl(url: String, allowInsecure: Boolean = false) {
    val isSecure = url.startsWith(SECURE_SCHEME, ignoreCase = true)
    val isAllowedInsecure = allowInsecure && url.startsWith(INSECURE_SCHEME, ignoreCase = true)
    require(isSecure || isAllowedInsecure) {
        val hint = if (allowInsecure) "https or http" else "https"
        "Unsupported remote source URL '$url': expected a $hint URL"
    }
}
