package dev.tonholo.s2c.parser.ast.css

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.css.CssTokenKind
import dev.tonholo.s2c.parser.ast.AstParser
import dev.tonholo.s2c.parser.ast.css.consumer.AtRuleConsumer
import dev.tonholo.s2c.parser.ast.css.consumer.CssConsumer
import dev.tonholo.s2c.parser.ast.css.consumer.PseudoClassConsumer
import dev.tonholo.s2c.parser.ast.css.elements.IdentifierElementParser
import dev.tonholo.s2c.parser.ast.css.elements.PropertyParser
import dev.tonholo.s2c.parser.ast.css.selectors.SelectorParser

internal val terminalTokens = listOf(
    CssTokenKind.Comma,
    CssTokenKind.OpenCurlyBrace,
    CssTokenKind.Semicolon,
    CssTokenKind.CloseParenthesis,
)

internal val selectorStarters = listOf(
    CssTokenKind.Dot,
    CssTokenKind.Hash,
    CssTokenKind.WhiteSpace,
    CssTokenKind.Greater,
    CssTokenKind.Plus,
    CssTokenKind.Tilde,
)

internal class CssAstParser(
    private val content: String,
) : AstParser<CssTokenKind, CssRootNode> {
    private val atRuleConsumer: CssConsumer<CssAtRule> = AtRuleConsumer(
        content = content,
        parser = this,
    )
    private val selectorParser: SelectorParser = SelectorParser(
        content = content,
        parser = this,
        buildErrorMessage = ::buildErrorMessage,
    )
    private val pseudoClassConsumer: CssConsumer<CssComponent> = PseudoClassConsumer(
        content = content,
        parser = this,
        selectorParser = selectorParser,
    )
    private val identifierElementParser: IdentifierElementParser = IdentifierElementParser(
        content = content,
        iterator = this,
        propertyParser = PropertyParser(
            content = content,
            iterator = this,
            buildErrorMessage = ::buildErrorMessage,
        ),
        selectorParser = selectorParser,
        buildErrorMessage = ::buildErrorMessage,
    )

    private var offset = 0
    private val tokens = mutableListOf<Token<out CssTokenKind>>()
    override fun parse(tokens: List<Token<out CssTokenKind>>): CssRootNode {
        this.tokens.addAll(tokens.trim())
        val rules = parseRules()
        return CssRootNode(rules)
    }

    fun parseRules(): List<CssRule> {
        val rules = mutableListOf<CssRule>()
        do {
            val rule = parseNext(null) as? CssRule
            if (rule != null) {
                rules += rule
            }
        } while (rule != null)
        return rules
    }

    override fun next(): Token<out CssTokenKind>? = if (offset < tokens.size) {
        tokens[offset++]
    } else {
        null
    }

    override fun peek(steps: Int): Token<out CssTokenKind>? = tokens.getOrNull(offset + steps)

    override fun rewind(steps: Int) {
        offset -= steps
    }

    override fun buildErrorMessage(message: String, backtrack: Int, forward: Int) = buildString {
        appendLine(message)
        val prev = tokens.getOrNull(offset - backtrack)?.startOffset ?: 0
        val next = tokens.getOrNull(offset + forward)?.endOffset ?: content.length
        val current = tokens.getOrNull(offset)
        appendLine("Start offset: ${current?.startOffset}")
        appendLine("End offset: ${current?.endOffset}")
        appendLine("Content:")
        var indent = 4
        append(" ".repeat(indent))
        appendLine(content.substring(prev, next))
        indent += current?.startOffset?.minus(prev) ?: 0
        append(" ".repeat(indent))
        append("^".repeat(next.minus(current?.startOffset ?: 0)))
    }

    fun parseNext(sibling: CssElement?): CssElement? {
        val starterToken = next() ?: return null
        val element = when (starterToken.kind) {
            is CssTokenKind.AtKeyword -> atRuleConsumer.consume(starterToken)
            is CssTokenKind.Colon -> pseudoClassConsumer.consume(starterToken)

            // skip elements
            in terminalTokens -> parseNext(sibling)

            CssTokenKind.CloseCurlyBrace, CssTokenKind.EndOfFile -> null

            // Potential selectors
            in selectorStarters -> selectorParser.parse(starterToken)
            CssTokenKind.Identifier -> identifierElementParser.parse(starterToken)

            else -> null
        }

        val isPseudoClass = element is CssComponent.Single && element.type is CssComponentType.PseudoClass
        return if (element is CssComponent && !isPseudoClass && sibling == null) {
            createCssRule(element)
        } else {
            element
        }
    }

    private fun createCssRule(element: CssComponent): CssElement {
        val selectors = mutableListOf<CssComponent>()
        val declarations = mutableListOf<CssDeclaration>()
        // get siblings selectors
        var next: CssElement? = element
        while (next != null && next is CssComponent) {
            selectors += next
            next = parseNext(sibling = element)
            if (next is CssDeclaration) {
                break
            }
        }

        // get rule declarations
        while (next != null && next is CssDeclaration) {
            declarations += next
            next = parseNext(sibling = null)
            if (next !is CssDeclaration) {
                break
            }
        }

        return CssQualifiedRule(
            components = selectors,
            declarations = declarations,
        )
    }
}

private fun List<Token<out CssTokenKind>>.trim(): List<Token<out CssTokenKind>> {
    val tokens = this
    var i = 0
    return buildList {
        while (i < tokens.size) {
            val token = tokens[i]
            add(token)
            val prev = tokens.getOrNull(i - 1)
            val next = tokens.getOrNull(i + 1)
            when {
                // trim leading or trailing whitespaces
                prev == null || next == null -> {
                    removeLast()
                    i++
                }

                (token.kind is CssTokenKind.WhiteSpace &&
                    prev.kind in CssTokenKind.WhiteSpace.significantAdjacentTokens &&
                    next.kind in CssTokenKind.WhiteSpace.significantAdjacentTokens) -> i++

                token.kind is CssTokenKind.WhiteSpace -> {
                    removeLast()
                    i++
                }

                else -> i++
            }
        }
    }
}
