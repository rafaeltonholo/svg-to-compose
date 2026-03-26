package dev.tonholo.s2c.website.state.playground

/**
 * Returns `true` when every non-blank line is either a comment (`#`)
 * or a section header (`[`). This matches the default scaffold the
 * playground ships — it has structure but no real configuration.
 */
internal fun String.isTemplateStructuralOnly(): Boolean {
    val trimmed = trim()
    if (trimmed.isEmpty()) return true
    return trimmed.lines().all { line ->
        val l = line.trim()
        l.isEmpty() || l.startsWith("#") || l.startsWith("[")
    }
}

/**
 * Returns the TOML string only when it contains real configuration
 * beyond section headers and comments (the default placeholder).
 */
internal fun String.takeIfUsableTemplate(): String? =
    if (isTemplateStructuralOnly()) null else this
