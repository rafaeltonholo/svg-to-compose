package dev.tonholo.s2c.parser.ast.css

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.css.CssTokenKind
import dev.tonholo.s2c.parser.ast.AstParser
import dev.tonholo.s2c.parser.ast.css.elements.IdentifierElementParser
import dev.tonholo.s2c.parser.ast.css.elements.PropertyParser
import dev.tonholo.s2c.parser.ast.css.selectors.SelectorParser

internal val terminalTokens = listOf(
    CssTokenKind.Comma,
    CssTokenKind.OpenCurlyBrace,
    CssTokenKind.Semicolon,
)

internal val selectorStarters = listOf(
    CssTokenKind.Dot,
    CssTokenKind.Hash,
    CssTokenKind.WhiteSpace,
)

internal class CssAstParser(
    private val content: String,
) : AstParser<CssTokenKind, CssRootNode> {
    private val selectorParser: SelectorParser = SelectorParser(
        content = content,
        iterator = this,
        buildErrorMessage = ::buildErrorMessage,
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
        val rules = parseRules(tokens.trim())
        return CssRootNode(rules)
    }

    private fun parseRules(tokens: List<Token<out CssTokenKind>>): List<CssRule> {
        this.tokens.addAll(tokens)
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

    private fun parseNext(sibling: CssElement?): CssElement? {
        val starterToken = next() ?: return null
        val element = when (starterToken.kind) {
            // skip elements
            in terminalTokens -> parseNext(sibling)

            CssTokenKind.CloseCurlyBrace, CssTokenKind.EndOfFile -> null

            // Potential selectors
            in selectorStarters -> selectorParser.parse(starterToken)
            CssTokenKind.Identifier -> identifierElementParser.parse(starterToken)

            else -> null
        }

        return if (element is CssSelector && sibling == null) {
            createCssRule(element)
        } else {
            element
        }
    }

    private fun createCssRule(element: CssSelector): CssElement {
        val selectors = mutableListOf<CssSelector>()
        val declarations = mutableListOf<CssDeclaration>()
        // get siblings selectors
        var next: CssElement? = element
        while (next != null && next is CssSelector) {
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

        return CssRule(
            selectors = selectors,
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
                prev == null || next == null -> i++

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
