package dev.tonholo.s2c.parser.ast.css

import dev.tonholo.s2c.parser.ast.Element

internal sealed interface CssElement : Element

internal data class CssRootNode(
    val rules: List<CssRule>
) : CssElement

internal data class CssRule(
    val selectors: List<CssSelector>,
    val declarations: List<CssDeclaration>
) : CssElement

internal data class CssSelector(
    val type: CssSelectorType,
    val value: String
) : CssElement

internal sealed class CssSelectorType {
    data object Id : CssSelectorType()
    data object Class : CssSelectorType()
    data object Tag : CssSelectorType()
    data object Universal : CssSelectorType()
}

internal data class CssDeclaration(
    val property: String,
    val value: PropertyValue,
) : CssElement

internal sealed interface PropertyValue : Element {
    data class Color(val value: String) : PropertyValue
    data class StringLiteral(val value: String) : PropertyValue
    data class Number(val value: String, val units: String?) : PropertyValue
    data class Function(val name: String, val arguments: List<PropertyValue>) : PropertyValue
    data class Url(val value: String) : PropertyValue
    data class Identifier(val value: String) : PropertyValue
}
