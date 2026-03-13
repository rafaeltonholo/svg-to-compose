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
        val fragment = context.fragments[Fragment.GROUP_BUILDER] ?: return fallbackEmitter.emit(group)

        val groupVars = TemplateContext.groupVariables(group.params)
            .mapValues { (_, v) -> context.applyColorMappings(v) }

        // Recursively emit child nodes
        val childBody = group.commands.joinToString("\n") { emit(it, context) }

        val resolved = PlaceholderResolver.resolve(
            template = fragment.trim(),
            context = context,
            nodeVariables = groupVars,
            nodeNamespace = Namespace.GROUP,
            depth = FRAGMENT_LEVEL_RESOLUTION_DEPTH,
        )

        return "$resolved {\n${childBody.prependIndent(formatConfig.indentUnit)}\n}"
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
        val closeParen = if (openParen >= 0) findMatchingCloseParen(resolved, openParen) else -1
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
 * Finds the closing parenthesis matching the opening paren at [openIndex],
 * tracking nesting depth so inner parens are skipped.
 *
 * @return The index of the matching `)`, or `-1` if unbalanced.
 */
private fun findMatchingCloseParen(text: String, openIndex: Int): Int {
    var depth = 1
    for (i in openIndex + 1 until text.length) {
        when (text[i]) {
            '(' -> depth++

            ')' -> {
                depth--
                if (depth == 0) return i
            }
        }
    }
    return -1
}

/**
 * Splits a parameter string at top-level commas, respecting nested
 * parentheses so that `Brush.linearGradient(0.05f to Color(...), ...)` is
 * kept as a single parameter.
 */
private fun splitTopLevelParams(content: String): List<String> {
    val params = mutableListOf<String>()
    var depth = 0
    var start = 0

    for (i in content.indices) {
        when (content[i]) {
            '(' -> depth++

            ')' -> depth--

            ',' -> if (depth == 0) {
                params.add(content.substring(start, i))
                start = i + 1
            }
        }
    }

    // Add the last parameter
    val last = content.substring(start)
    if (last.isNotBlank()) {
        params.add(last)
    }

    return params
}
