package dev.tonholo.s2c.parser.ast.css

import dev.tonholo.s2c.lexer.css.CssTokenKind
import dev.tonholo.s2c.parser.ast.Element

internal sealed interface CssElement : Element

internal data class CssRootNode(
    val rules: List<CssRule>,
) : CssElement

internal sealed interface CssRule : CssElement {
    val components: List<CssComponent>
}

internal data class CssQualifiedRule(
    override val components: List<CssComponent>,
    // TODO: Declarations can also contain at-rules.
    val declarations: List<CssDeclaration>,
) : CssRule

internal data class CssAtRule(
    val name: String,
    override val components: List<CssComponent>,
    val rules: List<CssRule>,
) : CssRule

internal sealed interface CssSelector : CssElement {
    data class Single(val type: CssSelectorType, val value: String) : CssSelector
    data class Multiple(val selectors: List<CssSelector>) : CssSelector
internal sealed interface CssComponent : CssElement {
    data class Single(
        val type: CssComponentType,
        val value: String,
        val combinator: CssCombinator? = null,
    ) : CssComponent

    data class Multiple(val components: List<CssComponent>) : CssComponent
}

internal sealed class CssSelectorType {
    data object Id : CssSelectorType()
    data object Class : CssSelectorType()
    data object Tag : CssSelectorType()
    data object Universal : CssSelectorType()
internal sealed interface CssComponentType {
    data object Id : CssComponentType
    data object Class : CssComponentType
    data object Tag : CssComponentType
}

internal data class CssDeclaration(
    val property: String,
    val value: PropertyValue,
) : CssElement

internal sealed interface PropertyValue : Element {
    data class Color(val value: kotlin.String) : PropertyValue
    data class String(val value: kotlin.String) : PropertyValue
    data class Number(val value: kotlin.String, val units: kotlin.String?) : PropertyValue
    data class Function(val name: kotlin.String, val arguments: List<PropertyValue>) : PropertyValue
    data class Url(val value: kotlin.String) : PropertyValue
    data class Identifier(val value: kotlin.String) : PropertyValue
    data class Multiple(val values: List<PropertyValue>) : PropertyValue
}
