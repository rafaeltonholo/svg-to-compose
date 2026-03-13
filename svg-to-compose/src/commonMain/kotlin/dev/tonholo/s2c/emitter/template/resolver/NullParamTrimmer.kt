package dev.tonholo.s2c.emitter.template.resolver

/**
 * Strips null-valued parameter segments from resolved template lines.
 *
 * When a placeholder resolves to null, the surrounding `key = <null>,`
 * text is removed. Lines that become empty (whitespace-only) after
 * trimming are dropped entirely.
 */
internal object NullParamTrimmer {
    /**
     * Assembles resolved tokens into final text, trimming null parameter segments.
     *
     * @param tokens The resolved tokens (after indentation adjustment).
     * @return The assembled string with null parameters stripped.
     */
    fun assemble(tokens: List<ResolvedToken>): String {
        val lines = splitIntoLines(tokens)
        return lines
            .mapNotNull { lineTokens -> trimLine(lineTokens) }
            .joinToString("\n")
    }
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
private fun trimLine(lineTokens: List<ResolvedToken>): String? {
    val hasNull = lineTokens.any { it is ResolvedToken.Null }
    if (!hasNull) {
        return lineTokens.joinToString("") { (it as ResolvedToken.Text).value }
    }

    val assembled = trimNullParams(lineTokens)

    // Drop lines that are whitespace-only after trimming
    return assembled.takeIf { it.isNotBlank() }
}

/**
 * Strips `key = <null>` parameter segments from a line's token list.
 *
 * Scans backward from each [ResolvedToken.Null] to remove the preceding
 * `key = ` text, and forward to clean up associated commas.
 */
private fun trimNullParams(lineTokens: List<ResolvedToken>): String {
    val segments = mutableListOf<Segment>()
    for (token in lineTokens) {
        when (token) {
            is ResolvedToken.Text -> segments.add(Segment.Text(token.value))
            is ResolvedToken.Null -> segments.add(Segment.NullMarker)
        }
    }

    val result = StringBuilder()
    var i = 0
    while (i < segments.size) {
        val seg = segments[i]
        if (seg is Segment.NullMarker) {
            stripPrecedingParam(result)
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

    while (pos >= 0 && result[pos].isWhitespace()) {
        pos--
    }

    if (pos >= 0 && result[pos] == '=') {
        pos--
    } else {
        return
    }

    while (pos >= 0 && result[pos].isWhitespace()) {
        pos--
    }

    val keyEnd = pos
    while (pos >= 0 && (result[pos].isLetterOrDigit() || result[pos] == '_')) {
        pos--
    }

    if (pos == keyEnd) return

    result.setLength(pos + 1)
}

/**
 * After a null marker, skips leading comma and whitespace in the next text segment.
 *
 * @return The next segment index to process.
 */
private fun skipTrailingComma(segments: MutableList<Segment>, nextIndex: Int): Int {
    if (nextIndex >= segments.size) return nextIndex
    val next = segments[nextIndex]
    if (next !is Segment.Text) return nextIndex

    val text = next.value
    var pos = 0
    while (pos < text.length && text[pos].isWhitespace()) pos++
    if (pos < text.length && text[pos] == ',') {
        pos++
        while (pos < text.length && text[pos] == ' ') pos++
    }

    if (pos > 0) {
        segments[nextIndex] = Segment.Text(if (pos < text.length) text.substring(pos) else "")
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
