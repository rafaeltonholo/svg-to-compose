package dev.tonholo.s2c.emitter.template.lexer

/**
 * Tokens produced by [TemplateLexer].
 */
internal sealed interface TemplateToken {
    /** Literal text between placeholders. */
    data class Literal(val text: String) : TemplateToken

    /** A `${namespace:key}` placeholder reference. */
    data class Placeholder(val namespace: String, val key: String) : TemplateToken
}
