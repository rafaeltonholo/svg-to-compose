package dev.tonholo.s2c.emitter.template.lexer

import dev.tonholo.s2c.emitter.template.TemplateConstants.Namespace

/**
 * Scans a template string into a sequence of [TemplateToken]s.
 *
 * Recognises `${namespace:key}` where namespace is one of
 * `icon`, `path`, `group`, `template`, `def`, `chunk` and key matches
 * `[a-z][a-z0-9_.]*`. Everything else is emitted as [TemplateToken.Literal].
 * Invalid or incomplete placeholders are treated as literal text.
 */
internal object TemplateLexer {
    private val VALID_NAMESPACES = setOf(
        Namespace.ICON,
        Namespace.PATH,
        Namespace.GROUP,
        Namespace.TEMPLATE,
        Namespace.DEFINITIONS,
        Namespace.CHUNK,
    )

    /**
     * Tokenizes [input] into a list of [TemplateToken].
     */
    fun tokenize(input: String): List<TemplateToken> {
        if (input.isEmpty()) return emptyList()

        val tokens = mutableListOf<TemplateToken>()
        val literal = StringBuilder()
        var cursor = 0

        while (cursor < input.length) {
            if (input[cursor] == '$' && cursor + 1 < input.length && input[cursor + 1] == '{') {
                val result = tryParsePlaceholder(input, cursor + 2)
                if (result != null) {
                    if (literal.isNotEmpty()) {
                        tokens.add(TemplateToken.Literal(literal.toString()))
                        literal.clear()
                    }
                    tokens.add(result.token)
                    cursor = result.endIndex
                } else {
                    literal.append(input[cursor])
                    cursor++
                }
            } else {
                literal.append(input[cursor])
                cursor++
            }
        }

        if (literal.isNotEmpty()) {
            tokens.add(TemplateToken.Literal(literal.toString()))
        }

        return tokens
    }

    /**
     * Tries to parse a placeholder starting after `${`.
     *
     * @param input The full input string.
     * @param start Index right after the `{` of `${`.
     * @return A [ParseResult] if a valid placeholder was found, null otherwise.
     */
    private fun tryParsePlaceholder(input: String, start: Int): ParseResult? {
        // Find closing brace first, then look for colon within that range.
        // This avoids matching colons far away from the placeholder opener.
        val closeBrace = input.indexOf('}', start)
        if (closeBrace < 0) return null
        val colonIndex = input.indexOf(':', start)
        if (colonIndex !in 0..<closeBrace) return null

        val namespace = input.substring(start, colonIndex)
        val key = input.substring(colonIndex + 1, closeBrace)
        if (namespace !in VALID_NAMESPACES || !isValidKey(key)) return null

        return ParseResult(
            token = TemplateToken.Placeholder(namespace, key),
            endIndex = closeBrace + 1,
        )
    }

    private fun isValidKey(key: String): Boolean {
        if (key.isEmpty()) return false
        if (key[0] !in 'a'..'z') return false
        return key.all { it in 'a'..'z' || it in '0'..'9' || it == '_' || it == '.' }
    }

    private class ParseResult(val token: TemplateToken.Placeholder, val endIndex: Int)
}
