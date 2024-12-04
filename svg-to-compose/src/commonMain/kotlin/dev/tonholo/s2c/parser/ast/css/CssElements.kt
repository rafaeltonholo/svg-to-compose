package dev.tonholo.s2c.parser.ast.css

import dev.tonholo.s2c.lexer.css.CssTokenKind
import dev.tonholo.s2c.parser.ast.Element

sealed interface CssElement : Element

data class CssRootNode(
    val rules: List<CssRule>,
) : CssElement

sealed interface CssRule : CssElement {
    val components: List<CssComponent>
}

data class CssQualifiedRule(
    override val components: List<CssComponent>,
    // TODO: Declarations can also contain at-rules.
    val declarations: List<CssDeclaration>,
) : CssRule

data class CssAtRule(
    val name: String,
    override val components: List<CssComponent>,
    val rules: List<CssRule>,
) : CssRule

sealed interface CssComponent : CssElement {
    data class Single(
        val type: CssComponentType,
        val value: String,
        val combinator: CssCombinator? = null,
    ) : CssComponent

    data class Multiple(val components: List<CssComponent>) : CssComponent
    data class AtRule(val value: String) : CssComponent
}

sealed interface CssComponentType {
    data object Id : CssComponentType
    data object Class : CssComponentType
    data object Tag : CssComponentType
    data object Universal : CssComponentType
    data class PseudoClass(val parameters: List<CssComponent> = emptyList()) : CssComponentType
    data object PseudoElement : CssComponentType
}

sealed interface CssCombinator {
    data object ChildCombinator : CssCombinator
    data object NextSiblingCombinator : CssCombinator
    data object DescendantCombinator : CssCombinator
    data object SubsequentSiblingCombinator : CssCombinator

    companion object {
        fun from(tokenKind: CssTokenKind?): CssCombinator? = when (tokenKind) {
            CssTokenKind.Greater -> ChildCombinator
            CssTokenKind.Tilde -> NextSiblingCombinator
            CssTokenKind.WhiteSpace -> DescendantCombinator
            CssTokenKind.Plus -> SubsequentSiblingCombinator
            else -> null
        }
    }
}

data class CssDeclaration(
    val property: String,
    val value: PropertyValue,
) : CssElement

sealed interface PropertyValue : Element {
    data class Color(val value: kotlin.String) : PropertyValue
    data class String(val value: kotlin.String) : PropertyValue
    data class Number(val value: kotlin.String) : PropertyValue
    data class Dimension(val value: kotlin.String) : PropertyValue
    data class Percentage(val value: kotlin.String) : PropertyValue
    data class Function(val name: kotlin.String, val arguments: List<PropertyValue>) : PropertyValue
    data class Url(val value: kotlin.String) : PropertyValue
    data class Identifier(val value: kotlin.String) : PropertyValue
    data class Multiple(val values: List<PropertyValue>) : PropertyValue
}
