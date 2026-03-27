package dev.tonholo.s2c.emitter.template

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.emitter.FormatConfig
import dev.tonholo.s2c.emitter.imagevector.ImageVectorNodeEmitter
import dev.tonholo.s2c.emitter.template.TemplateConstants.Fragment
import dev.tonholo.s2c.emitter.template.TemplateConstants.Namespace
import dev.tonholo.s2c.emitter.template.resolver.PlaceholderResolver

private const val FRAGMENT_LEVEL_RESOLUTION_DEPTH = 1

/**
 * Per-node template fragment resolution.
 *
 * For nodes where a fragment is defined in the template config, resolves the
 * fragment with the node's variables. Otherwise delegates to [fallbackEmitter].
 *
 * @property formatConfig The formatting configuration.
 * @property fallbackEmitter The default node emitter used when no fragment is defined.
 */
internal class TemplateNodeEmitter(
    private val formatConfig: FormatConfig,
    private val fallbackEmitter: ImageVectorNodeEmitter,
) {
    /**
     * Emits code for a single [ImageVectorNode] within a template context.
     *
     * @param node The node to emit.
     * @param context The template resolution context.
     * @return The emitted Kotlin code string.
     */
    fun emit(node: ImageVectorNode, context: TemplateContext): String = when (node) {
        is ImageVectorNode.Path -> emitPath(node, context)
        is ImageVectorNode.Group -> emitGroup(node, context)
        is ImageVectorNode.ChunkFunction -> emitChunkFunction(node)
    }

    private fun emitPath(path: ImageVectorNode.Path, context: TemplateContext): String {
        val fragment = context.fragments[Fragment.PATH_BUILDER] ?: return fallbackEmitter.emit(path)

        val pathVars = TemplateContext.pathVariables(path.params)
            .mapValues { (_, v) -> context.applyColorMappings(v) }
        val pathCommands = fallbackEmitter.emitPathCommands(path)

        // depth=1: fragment-level resolution skips column-aware indentation
        // so that multi-line values (e.g. gradients) keep only their own
        // relative indent. The parent icon_template resolve at depth=0
        // applies the final column-based shift uniformly.
        val resolved = PlaceholderResolver.resolve(
            template = fragment.trim(),
            context = context,
            nodeVariables = pathVars,
            nodeNamespace = Namespace.PATH,
            depth = FRAGMENT_LEVEL_RESOLUTION_DEPTH,
        )

        val formatted = wrapMultiLineCall(resolved)
        return "$formatted {\n${pathCommands.prependIndent(formatConfig.indentUnit)}\n}"
    }

    private fun emitGroup(group: ImageVectorNode.Group, context: TemplateContext): String {
        // Recursively emit child nodes template-aware regardless of whether
        // a group fragment is configured, so nested paths/groups still get
        // color mappings and fragment resolution.
        val childBody = group.commands.joinToString("\n") { emit(it, context) }

        return if (Fragment.GROUP_BUILDER !in context.fragments) {
            // No group fragment — use fallback for the group header only
            val header = fallbackEmitter.emitGroupHeader(group)
            "$header {\n${childBody.prependIndent(formatConfig.indentUnit)}\n}"
        } else {
            val fragment = context.fragments.getValue(Fragment.GROUP_BUILDER)

            val groupVars = TemplateContext
                .groupVariables(group.params)
                .mapValues { (_, v) -> context.applyColorMappings(v) }

            val resolved = PlaceholderResolver.resolve(
                template = fragment.trim(),
                context = context,
                nodeVariables = groupVars,
                nodeNamespace = Namespace.GROUP,
                depth = FRAGMENT_LEVEL_RESOLUTION_DEPTH,
            )

            "$resolved {\n${childBody.prependIndent(formatConfig.indentUnit)}\n}"
        }
    }

    private fun emitChunkFunction(chunk: ImageVectorNode.ChunkFunction): String {
        // Chunk functions are always emitted by the fallback emitter
        return fallbackEmitter.emit(chunk)
    }

    /**
     * When a resolved call like `iconPath(fill = Brush.linearGradient(...), ...)` contains
     * multi-line parameter values, reformats it so each parameter is on its own indented line:
     * ```
     * iconPath(
     *     fill = Brush.linearGradient(
     *         ...
     *     ),
     *     fillAlpha = 0.4f,
     * )
     * ```
     * Single-line calls are returned unchanged.
     */
    private fun wrapMultiLineCall(resolved: String): String {
        if ('\n' !in resolved) return resolved

        val indent = formatConfig.indentUnit

        val openParen = resolved.indexOf('(')
        val closeParen = if (openParen >= 0) {
            findMatchingCloseParen(resolved, openParen)
        } else {
            -1
        }
        if (openParen < 0 || closeParen < 0) return resolved
        val funcName = resolved.substring(0, openParen)
        val innerContent = resolved.substring(openParen + 1, closeParen)
        val params = splitTopLevelParams(innerContent)
        if (params.isEmpty()) return resolved

        return buildString {
            append(funcName)
            appendLine("(")
            for (param in params) {
                val trimmed = param.trim()
                if (trimmed.isEmpty()) continue
                val trailing = if (!trimmed.endsWith(",")) "," else ""
                appendLine("$trimmed$trailing".prependIndent(indent))
            }
            append(")")
        }
    }
}

/**
 * Tracks nesting depth for parentheses, angle brackets, braces, brackets,
 * and string literals. Shared by [findMatchingCloseParen] and [splitTopLevelParams].
 */
private class NestingTracker {
    var parenDepth = 0
        private set
    var angleDepth = 0
        private set
    var braceDepth = 0
        private set
    var bracketDepth = 0
        private set
    var inString = false
        private set

    val isTopLevel: Boolean
        get() = parenDepth == 0 && angleDepth == 0 && braceDepth == 0 && bracketDepth == 0

    /**
     * Processes a character and updates nesting state.
     *
     * @return `true` if the caller should skip (advance index by 2 for escape), `false` otherwise.
     */
    fun process(ch: Char, hasNext: Boolean): Boolean = when {
        inString && ch == '\\' && hasNext -> true // skip escape
        inString -> {
            if (ch == '"') inString = false
            false
        }

        else -> {
            updateDepth(ch)
            false
        }
    }

    private fun updateDepth(ch: Char) {
        when (ch) {
            '"' -> inString = true
            '(' -> parenDepth++
            ')' -> parenDepth--
            '<' -> angleDepth++
            '>' -> if (angleDepth > 0) angleDepth--
            '{' -> braceDepth++
            '}' -> braceDepth--
            '[' -> bracketDepth++
            ']' -> bracketDepth--
        }
    }
}

/**
 * Finds the closing parenthesis matching the opening paren at [openIndex],
 * tracking nesting depth and skipping string literals so inner parens are
 * handled correctly.
 *
 * @return The index of the matching `)`, or `-1` if unbalanced.
 */
private fun findMatchingCloseParen(text: String, openIndex: Int): Int {
    val tracker = NestingTracker()
    var i = openIndex + 1
    while (i < text.length) {
        val ch = text[i]
        if (tracker.process(ch, i + 1 < text.length)) {
            i += 2
            continue
        }
        if (!tracker.inString && ch == ')' && tracker.parenDepth == 0) return i
        i++
    }
    return -1
}

/**
 * Splits a parameter string at top-level commas, respecting nested
 * parentheses, angle brackets, braces, brackets, and string literals
 * so that arguments like `name = "Foo, Bar"` or `Brush.linearGradient(0.05f to Color(...), ...)`
 * are kept as a single parameter.
 */
private fun splitTopLevelParams(content: String): List<String> {
    val params = mutableListOf<String>()
    val tracker = NestingTracker()
    var start = 0
    var i = 0

    while (i < content.length) {
        val ch = content[i]
        if (tracker.process(ch, i + 1 < content.length)) {
            i += 2
            continue
        }
        if (!tracker.inString && ch == ',' && tracker.isTopLevel) {
            params.add(content.substring(start, i))
            start = i + 1
        }
        i++
    }

    // Add the last parameter
    val last = content.substring(start)
    if (last.isNotBlank()) {
        params.add(last)
    }

    return params
}
