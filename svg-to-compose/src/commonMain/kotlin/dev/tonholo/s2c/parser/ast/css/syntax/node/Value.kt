package dev.tonholo.s2c.parser.ast.css.syntax.node

/**
 * A CSS property value.
 */
sealed interface Value : CssComponentValueNode {
    /**
     * Represents a color value.
     */
    data class Color(
        override val location: CssLocation,
        val value: kotlin.String,
    ) : Value {
        override fun toString(indent: Int): kotlin.String =
            value
    }

    /**
     * Represents a string value.
     */
    data class String(
        override val location: CssLocation,
        val value: kotlin.String,
    ) : Value {
        override fun toString(indent: Int): kotlin.String =
            "'$value'"
    }

    /**
     * Represents an identifier value.
     */
    data class Identifier(
        override val location: CssLocation,
        val name: kotlin.String,
    ) : Value {
        override fun toString(indent: Int): kotlin.String =
            name
    }

    /**
     * Represents a number value.
     */
    data class Number(
        override val location: CssLocation,
        val value: kotlin.String,
    ) : Value {
        override fun toString(indent: Int): kotlin.String =
            value
    }

    /**
     * Represents a dimension value.
     */
    data class Dimension(
        override val location: CssLocation,
        val value: kotlin.String,
        val unit: kotlin.String,
    ) : Value {
        override fun toString(indent: Int): kotlin.String =
            value + unit
    }

    /**
     * Represents a percentage value.
     */
    data class Percentage(
        override val location: CssLocation,
        val value: kotlin.String,
    ) : Value {
        override fun toString(indent: Int): kotlin.String =
            "$value%"
    }

    /**
     * Represents a function value.
     */
    data class Function(
        override val location: CssLocation,
        val name: kotlin.String,
        val arguments: List<Value>,
    ) : Value {
        override fun toString(indent: Int): kotlin.String =
            "$name(${arguments.joinToString(", ") { it.toString(indent = 0) }})"
    }

    /**
     * Represents a URL value.
     */
    data class Url(
        override val location: CssLocation,
        val value: kotlin.String,
    ) : Value {
        override fun toString(indent: Int): kotlin.String {
            val quotedIfNeeded = if (value.startsWith('#')) {
                value
            } else {
                "\"$value\""
            }
            return "url($quotedIfNeeded)"
        }
    }
}
