package dev.tonholo.s2c.parser.ast.css

import dev.tonholo.s2c.lexer.Token
import dev.tonholo.s2c.lexer.css.CssTokenKind
import dev.tonholo.s2c.parser.ast.AstParser

internal class CssAstParser(
    private val content: String,
) : AstParser<CssTokenKind, CssRootNode> {
    private var offset = 0
    private val tokens = mutableListOf<Token<out CssTokenKind>>()
    override fun parse(tokens: List<Token<out CssTokenKind>>): CssRootNode {
        this.tokens.addAll(tokens)
        val rules = mutableListOf<CssRule>()
        do {
            val rule = parseNext(null) as? CssRule
            if (rule != null) {
                rules += rule
            }
        } while (rule != null)
        return CssRootNode(rules)
    }

    private fun next(): Token<out CssTokenKind>? = tokens
        .takeIf { offset < it.size }
        ?.get(offset++)

    private fun parseNext(sibling: CssElement?): CssElement? {
        val starterToken = next() ?: return null
        val element = when (starterToken.kind) {
            // skip elements
            CssTokenKind.WhiteSpace,
            CssTokenKind.Comma,
            CssTokenKind.OpenCurlyBrace,
            CssTokenKind.Semicolon -> parseNext(sibling)

            CssTokenKind.CloseCurlyBrace, CssTokenKind.EndOfFile -> null

            // Potential selectors
            CssTokenKind.Dot -> createClassSelector()
            CssTokenKind.Hash -> createIdSelector()
            CssTokenKind.Identifier -> createIdentifierElement(starterToken)

            else -> null
        }

        return if (element is CssSelector && sibling == null) {
            createCssRule(element)
        } else {
            element
        }
    }

    private fun createClassSelector(): CssSelector {
        val token = requireNotNull(next()) { "Expected token but found null" }
        check(token.kind is CssTokenKind.Identifier) { "Expected identifier but found ${token.kind}" }
        return CssSelector(
            type = CssSelectorType.Class,
            value = content.substring(token.startOffset, token.endOffset),
        )
    }

    private fun createIdSelector(): CssSelector {
        val token = requireNotNull(next()) { "Expected token but found null" }
        check(token.kind is CssTokenKind.Identifier) { "Expected identifier but found ${token.kind}" }
        return CssSelector(
            type = CssSelectorType.Id,
            value = content.substring(token.startOffset, token.endOffset),
        )
    }

    private fun createIdentifierElement(starterToken: Token<out CssTokenKind>): CssElement? {
        val token = requireNotNull(next()) { "Expected token but found null" }
        return when (token.kind) {
            is CssTokenKind.WhiteSpace,
            is CssTokenKind.Comma,
            is CssTokenKind.OpenCurlyBrace -> {
                CssSelector(
                    type = CssSelectorType.Tag,
                    value = content.substring(starterToken.startOffset, starterToken.endOffset),
                )
            }

            is CssTokenKind.Colon -> {
                val property = content.substring(starterToken.startOffset, starterToken.endOffset)
                CssDeclaration(
                    property = property,
                    value = requireNotNull(next()?.parsePropertyValue()) {
                        "Incomplete property '$property' value"
                    },
                )
            }

            else -> null
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

    private fun Token<out CssTokenKind>.parsePropertyValue(): PropertyValue? {
        var token = this
        while (token.kind is CssTokenKind.WhiteSpace || token.kind is CssTokenKind.Colon) {
            token = next() ?: return null
        }
        return when (token.kind) {
            CssTokenKind.Hash -> {
                val value = next() ?: return null
                check(value.kind is CssTokenKind.HexDigit) { "Expected hex digit but found ${value.kind}" }
                PropertyValue.Color(content.substring(token.startOffset, value.endOffset))
            }

            CssTokenKind.StringLiteral -> {
                PropertyValue.StringLiteral(content.substring(token.startOffset, token.endOffset))
            }

            CssTokenKind.Identifier ->
                PropertyValue.Identifier(content.substring(token.startOffset, token.endOffset))

            CssTokenKind.Number -> {
                val unitsIdentifier = next()
                val units = if (unitsIdentifier != null && unitsIdentifier.kind is CssTokenKind.Identifier) {
                    content.substring(unitsIdentifier.startOffset, unitsIdentifier.endOffset)
                } else {
                    rewind()
                    null
                }

                PropertyValue.Number(
                    value = content.substring(token.startOffset, token.endOffset),
                    units = units,
                )
            }

            CssTokenKind.StartUrl -> {
                var next = next()
                check(next != null && next.kind !is CssTokenKind.EndUrl) {
                    rewind()
                    rewind() // return to start url.
                    buildErrorMessage(
                        message = "Incomplete URL value.",
                        backtrack = 3,
                        forward = 2,
                    )
                }
                val startOffset = next.startOffset
                var endOffset: Int
                do {
                    next = next()
                    check(next != null) {
                        rewind()
                        buildErrorMessage("Incomplete URL value.")
                    }
                    endOffset = next.endOffset
                } while (next?.kind != CssTokenKind.EndUrl)
                PropertyValue.Url(value = content.substring(startOffset, endOffset - 1))
            }

            else -> null
        }
    }

    private fun rewind() {
        offset--
    }

    private fun buildErrorMessage(
        message: String,
        backtrack: Int = 1,
        forward: Int = 1,
    ) = buildString {
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
}
