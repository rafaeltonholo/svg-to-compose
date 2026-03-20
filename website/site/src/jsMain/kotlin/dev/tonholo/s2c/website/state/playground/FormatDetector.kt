package dev.tonholo.s2c.website.state.playground

/**
 * Detects the format extension from raw XML content by inspecting
 * the root element tag.
 *
 * @return `"svg"` if root is `<svg`, `"avg"` if root is `<vector`,
 *         or `null` if the format cannot be determined.
 */
internal fun detectExtension(content: String): String? {
    val trimmed = content.trimStart()
    val body = if (trimmed.startsWith("<?xml")) {
        val end = trimmed.indexOf("?>")
        if (end >= 0) trimmed.substring(end + 2).trimStart() else trimmed
    } else {
        trimmed
    }
    return when {
        body.startsWith("<svg", ignoreCase = true) -> "svg"
        body.startsWith("<vector", ignoreCase = true) -> "avg"
        else -> null
    }
}
