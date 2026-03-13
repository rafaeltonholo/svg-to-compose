package dev.tonholo.s2c.emitter.template.resolver

import dev.tonholo.s2c.emitter.template.TemplateConstants.Namespace
import dev.tonholo.s2c.emitter.template.TemplateContext
import dev.tonholo.s2c.emitter.template.lexer.TemplateLexer
import dev.tonholo.s2c.emitter.template.lexer.TemplateToken

private const val MAX_RESOLUTION_DEPTH = 5

/**
 * Resolves `${namespace:key}` placeholders in template strings using a
 * tokenizer-based approach.
 *
 * Supports three resolution strategies:
 * - **Variable** (`icon.*`, `path.*`, `group.*`): direct value lookup
 * - **Fragment** (`template:*`): recursive template resolution
 * - **Definition** (`def:*`): simple name lookup + import registration
 *
 * After resolution, delegates to [indentMultiLineValues] for column-aware
 * indentation and [NullParamTrimmer] for null parameter stripping.
 */
internal object PlaceholderResolver {
    /**
     * Resolves all placeholders in [template] using the provided context and
     * optional per-node variable maps.
     *
     * @param template The template string with `${...}` placeholders.
     * @param context The resolution context containing imports, fragments, and icon variables.
     * @param nodeVariables Optional node-level variables (path or group namespace).
     * @param nodeNamespace The namespace name for [nodeVariables] (e.g., "path" or "group").
     * @param depth Current recursion depth (for cycle detection).
     * @return The resolved template string.
     * @throws IllegalStateException if resolution depth exceeds [MAX_RESOLUTION_DEPTH].
     */
    fun resolve(
        template: String,
        context: TemplateContext,
        nodeVariables: Map<String, String?>? = null,
        nodeNamespace: String? = null,
        depth: Int = 0,
    ): String {
        check(depth <= MAX_RESOLUTION_DEPTH) {
            "Template resolution depth exceeded $MAX_RESOLUTION_DEPTH. " +
                "Possible cycle in template fragment references."
        }

        val tokens = TemplateLexer.tokenize(template)
        val resolved = resolveTokens(tokens, context, nodeVariables, nodeNamespace, depth)
        // Only apply column-aware indentation at the top level (depth == 0).
        // Nested fragment resolutions produce values with correct relative
        // indentation already; re-indenting at every depth would compound
        // the indent (e.g. gradient arguments from ComposeBrush).
        val indented = if (depth == 0) indentMultiLineValues(resolved) else resolved
        return NullParamTrimmer.assemble(indented)
    }

    private fun resolveTokens(
        tokens: List<TemplateToken>,
        context: TemplateContext,
        nodeVariables: Map<String, String?>?,
        nodeNamespace: String?,
        depth: Int,
    ): List<ResolvedToken> = tokens.map { token ->
        when (token) {
            is TemplateToken.Literal -> ResolvedToken.Text(token.text)

            is TemplateToken.Placeholder -> resolveToken(
                token,
                context,
                nodeVariables,
                nodeNamespace,
                depth,
            )
        }
    }

    private fun resolveToken(
        token: TemplateToken.Placeholder,
        context: TemplateContext,
        nodeVariables: Map<String, String?>?,
        nodeNamespace: String?,
        depth: Int,
    ): ResolvedToken = when (token.namespace) {
        Namespace.ICON -> valueOrNull(context.iconVariables[token.key])

        Namespace.CHUNK -> valueOrNull(context.chunkVariables[token.key])

        Namespace.PATH, Namespace.GROUP -> {
            if (token.namespace == nodeNamespace && nodeVariables != null) {
                valueOrNull(nodeVariables[token.key])
            } else {
                ResolvedToken.Null
            }
        }

        Namespace.TEMPLATE -> {
            val fragment = context.fragments[token.key]
                ?: error($$"Unknown template fragment: '${template:$${token.key}}'")

            val resolved = resolve(fragment.trim(), context, nodeVariables, nodeNamespace, depth + 1)
            ResolvedToken.Text(resolved, fromPlaceholder = true)
        }

        Namespace.DEFINITIONS -> {
            val importPath = context.definitions[token.key]
                ?: error($$"Unknown definition key: '${def:$${token.key}}'")

            context.addImport(importPath)
            ResolvedToken.Text(importPath.substringAfterLast('.'), fromPlaceholder = true)
        }

        else -> error("Unknown placeholder namespace: '${token.namespace}'")
    }

    private fun valueOrNull(value: String?): ResolvedToken =
        if (value != null) ResolvedToken.Text(value, fromPlaceholder = true) else ResolvedToken.Null
}
