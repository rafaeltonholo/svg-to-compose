package dev.tonholo.s2c.emitter.template.resolver

import dev.tonholo.s2c.emitter.template.TemplateContext
import dev.tonholo.s2c.emitter.template.lexer.TemplateLexer
import dev.tonholo.s2c.emitter.template.lexer.TemplateToken

private const val MAX_RESOLUTION_DEPTH = 5

/**
 * Resolves `${namespace:key}` placeholders in template strings using a
 * tokenizer-based approach.
 *
 * Supports three resolution strategies:
 * - **Variable** (`icon.*`, `path.*`, `group.*`): direct value lookup
 * - **Fragment** (`template:*`): recursive template resolution
 * - **Definition** (`def:*`): simple name lookup + import registration
 *
 * Null values are handled structurally: when a placeholder resolves to null,
 * the surrounding `key = <null>,` parameter segment is stripped. Lines that
 * become empty after elision are removed entirely.
 */
object PlaceholderResolver {
    /**
     * Resolves all placeholders in [template] using the provided context and
     * optional per-node variable maps.
     *
     * @param template The template string with `${...}` placeholders.
     * @param context The resolution context containing imports, fragments, and icon variables.
     * @param nodeVariables Optional node-level variables (path or group namespace).
     * @param nodeNamespace The namespace name for [nodeVariables] (e.g., "path" or "group").
     * @param depth Current recursion depth (for cycle detection).
     * @return The resolved template string.
     * @throws IllegalStateException if resolution depth exceeds [MAX_RESOLUTION_DEPTH].
     */
    fun resolve(
        template: String,
        context: TemplateContext,
        nodeVariables: Map<String, String?>? = null,
        nodeNamespace: String? = null,
        depth: Int = 0,
    ): String {
        check(depth <= MAX_RESOLUTION_DEPTH) {
            "Template resolution depth exceeded $MAX_RESOLUTION_DEPTH. " +
                "Possible cycle in template fragment references."
        }

        val tokens = TemplateLexer.tokenize(template)
        val resolved = resolveTokens(tokens, context, nodeVariables, nodeNamespace, depth)
        return assembleWithNullElision(resolved)
    }

    private fun resolveTokens(
        tokens: List<TemplateToken>,
        context: TemplateContext,
        nodeVariables: Map<String, String?>?,
        nodeNamespace: String?,
        depth: Int,
    ): List<ResolvedToken> = tokens.map { token ->
        when (token) {
            is TemplateToken.Literal -> ResolvedToken.Text(token.text)
            is TemplateToken.Placeholder -> resolveToken(
                token, context, nodeVariables, nodeNamespace, depth,
            )
        }
    }

    private fun resolveToken(
        token: TemplateToken.Placeholder,
        context: TemplateContext,
        nodeVariables: Map<String, String?>?,
        nodeNamespace: String?,
        depth: Int,
    ): ResolvedToken = when (token.namespace) {
        "icon" -> valueOrNull(context.iconVariables[token.key])
        "path", "group" -> {
            if (token.namespace == nodeNamespace && nodeVariables != null) {
                valueOrNull(nodeVariables[token.key])
            } else {
                ResolvedToken.Null
            }
        }

        "template" -> {
            val fragment = context.fragments[token.key]
                ?: error("Unknown template fragment: '\${template:${token.key}}'")
            val resolved = resolve(fragment.trim(), context, nodeVariables, nodeNamespace, depth + 1)
            ResolvedToken.Text(resolved)
        }

        "def" -> {
            val importPath = context.definitions[token.key]
                ?: error("Unknown definition key: '\${def:${token.key}}'")
            context.collectedImports.add(importPath)
            ResolvedToken.Text(importPath.substringAfterLast('.'))
        }

        else -> error("Unknown placeholder namespace: '${token.namespace}'")
    }

    private fun valueOrNull(value: String?): ResolvedToken =
        if (value != null) ResolvedToken.Text(value) else ResolvedToken.Null
}

/**
 * Intermediate token after placeholder resolution.
 */
private sealed interface ResolvedToken {
    /** Successfully resolved text value. */
    data class Text(val value: String) : ResolvedToken

    /** A placeholder that resolved to null. */
    data object Null : ResolvedToken
}

/**
 * Assembles resolved tokens into final text, eliding null parameter segments.
 *
 * For each line, null tokens cause the surrounding `key = <null>,` segment
 * to be stripped. Lines that become empty (whitespace-only) after elision
 * are dropped entirely.
 */
private fun assembleWithNullElision(tokens: List<ResolvedToken>): String {
    // Split tokens into lines, preserving which parts are null
    val lines = splitIntoLines(tokens)
    return lines
        .mapNotNull { lineTokens -> elideLine(lineTokens) }
        .joinToString("\n")
}

/**
 * Splits a flat list of resolved tokens into per-line groups,
 * breaking on newlines within [ResolvedToken.Text] values.
 */
private fun splitIntoLines(tokens: List<ResolvedToken>): List<List<ResolvedToken>> {
    val lines = mutableListOf<MutableList<ResolvedToken>>()
    var currentLine = mutableListOf<ResolvedToken>()
    lines.add(currentLine)

    for (token in tokens) {
        when (token) {
            is ResolvedToken.Text -> {
                val parts = token.value.split('\n')
                currentLine.add(ResolvedToken.Text(parts[0]))
                for (i in 1 until parts.size) {
                    currentLine = mutableListOf()
                    lines.add(currentLine)
                    currentLine.add(ResolvedToken.Text(parts[i]))
                }
            }

            is ResolvedToken.Null -> currentLine.add(token)
        }
    }

    return lines
}

/**
 * Processes a single line's tokens, stripping `key = <null>,` segments.
 *
 * @return The assembled line string, or null if the line should be dropped.
 */
private fun elideLine(lineTokens: List<ResolvedToken>): String? {
    val hasNull = lineTokens.any { it is ResolvedToken.Null }
    if (!hasNull) {
        return lineTokens.joinToString("") { (it as ResolvedToken.Text).value }
    }

    // Build segments for null-aware assembly
    val assembled = elideNullParams(lineTokens)

    // Drop lines that are whitespace-only after elision
    return assembled.takeIf { it.isNotBlank() }
}

/**
 * Strips `key = <null>` parameter segments from a line's token list.
 *
 * Scans backward from each [ResolvedToken.Null] to remove the preceding
 * `key = ` text, and forward to clean up associated commas.
 */
private fun elideNullParams(lineTokens: List<ResolvedToken>): String {
    // Flatten to a mutable list of text segments and null markers
    val segments = mutableListOf<Segment>()
    for (token in lineTokens) {
        when (token) {
            is ResolvedToken.Text -> segments.add(Segment.Text(token.value))
            is ResolvedToken.Null -> segments.add(Segment.NullMarker)
        }
    }

    // Process each null marker: strip the preceding `key = ` and associated comma
    val result = StringBuilder()
    var i = 0
    while (i < segments.size) {
        val seg = segments[i]
        if (seg is Segment.NullMarker) {
            // Strip the `key = ` prefix from the preceding text
            stripPrecedingParam(result)
            // Skip any trailing comma/whitespace in the next text segment
            i = skipTrailingComma(segments, i + 1)
        } else {
            result.append((seg as Segment.Text).value)
            i++
        }
    }

    return cleanupCommaArtifacts(result.toString())
}

/**
 * Removes the trailing `key = ` prefix from the accumulated result so far.
 *
 * Does NOT remove a preceding comma — that is left for [cleanupCommaArtifacts]
 * to handle, avoiding double-removal when both a preceding and trailing comma
 * exist around the null parameter.
 */
private fun stripPrecedingParam(result: StringBuilder) {
    var pos = result.length - 1

    // Skip trailing whitespace
    while (pos >= 0 && result[pos].isWhitespace()) pos--

    // Expect `=`
    if (pos >= 0 && result[pos] == '=') {
        pos--
    } else {
        return
    }

    // Skip whitespace before `=`
    while (pos >= 0 && result[pos].isWhitespace()) pos--

    // Skip the key (word characters: letters, digits, underscores)
    val keyEnd = pos
    while (pos >= 0 && (result[pos].isLetterOrDigit() || result[pos] == '_')) pos--

    // Only strip if we actually found a key
    if (pos == keyEnd) return

    // Truncate to just before the key
    result.setLength(pos + 1)
}

/**
 * After a null marker, skips leading comma and whitespace in the next text segment.
 *
 * @return The next segment index to process.
 */
private fun skipTrailingComma(segments: List<Segment>, nextIndex: Int): Int {
    if (nextIndex >= segments.size) return nextIndex
    val next = segments[nextIndex]
    if (next !is Segment.Text) return nextIndex

    val text = next.value
    var pos = 0
    // Skip whitespace
    while (pos < text.length && text[pos].isWhitespace()) pos++
    // Skip comma
    if (pos < text.length && text[pos] == ',') {
        pos++
        // Skip whitespace after comma
        while (pos < text.length && text[pos] == ' ') pos++
    }

    // Replace the segment with the trimmed remainder
    if (pos > 0 && pos < text.length) {
        (segments as MutableList<Segment>)[nextIndex] = Segment.Text(text.substring(pos))
    } else if (pos >= text.length) {
        (segments as MutableList<Segment>)[nextIndex] = Segment.Text("")
    }

    return nextIndex
}

/**
 * Cleans up comma artifacts after null parameter stripping.
 */
private fun cleanupCommaArtifacts(text: String): String {
    var result = text
    // "(," → "("
    result = result.replace(Regex("""\(\s*,\s*"""), "(")
    // ",)" → ")"
    result = result.replace(Regex(""",\s*\)"""), ")")
    // ",," → ","
    result = result.replace(Regex(""",\s*,"""), ",")
    return result
}

private sealed interface Segment {
    data class Text(val value: String) : Segment
    data object NullMarker : Segment
}
