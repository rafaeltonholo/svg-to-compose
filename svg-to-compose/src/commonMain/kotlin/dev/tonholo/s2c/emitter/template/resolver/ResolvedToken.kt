package dev.tonholo.s2c.emitter.template.resolver

/**
 * Intermediate token after placeholder resolution.
 *
 * Used as the shared representation between [PlaceholderResolver],
 * [indentMultiLineValues], and [NullParamTrimmer].
 */
internal sealed interface ResolvedToken {
    /** Successfully resolved text value. */
    data class Text(val value: String, val fromPlaceholder: Boolean = false) : ResolvedToken

    /** A placeholder that resolved to null. */
    data object Null : ResolvedToken
}

/**
 * Adjusts multi-line values from placeholder resolution to preserve the
 * indentation of the placeholder's column position.
 *
 * Template literals already carry correct indentation. Only values from
 * placeholder resolution (where [ResolvedToken.Text.fromPlaceholder] is true)
 * need subsequent lines indented to match the column where they are inserted.
 */
internal fun indentMultiLineValues(tokens: List<ResolvedToken>): List<ResolvedToken> {
    val result = mutableListOf<ResolvedToken>()
    var column = 0

    for (token in tokens) {
        when (token) {
            is ResolvedToken.Null -> result.add(token)

            is ResolvedToken.Text if token.fromPlaceholder && '\n' in token.value -> {
                val indent = " ".repeat(column)
                val lines = token.value.split('\n')
                val indented = lines.mapIndexed { idx, line ->
                    when {
                        idx == 0 -> line
                        line.isEmpty() -> line
                        else -> indent + line
                    }
                }.joinToString("\n")
                result.add(ResolvedToken.Text(indented, fromPlaceholder = true))
                column = indented.substringAfterLast('\n').length
            }

            is ResolvedToken.Text -> {
                result.add(token)
                val value = token.value
                if ('\n' in value) {
                    column = value.substringAfterLast('\n').length
                } else {
                    column += value.length
                }
            }
        }
    }

    return result
}
