package dev.tonholo.s2c.emitter.template

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.emitter.FormatConfig
import dev.tonholo.s2c.emitter.imagevector.ImageVectorNodeEmitter
import dev.tonholo.s2c.emitter.template.TemplateConstants.Fragment
import dev.tonholo.s2c.emitter.template.TemplateConstants.GroupVar
import dev.tonholo.s2c.emitter.template.TemplateConstants.Namespace
import dev.tonholo.s2c.emitter.template.TemplateConstants.PathVar
import dev.tonholo.s2c.emitter.template.resolver.PlaceholderResolver
import dev.tonholo.s2c.logger.Logger

/**
 * Per-node template fragment resolution.
 *
 * For nodes where a fragment is defined in the template config, resolves the
 * fragment with the node's variables. Otherwise delegates to [fallbackEmitter].
 *
 * @property logger The logger instance.
 * @property formatConfig The formatting configuration.
 * @property fallbackEmitter The default node emitter used when no fragment is defined.
 */
internal class TemplateNodeEmitter(
    private val logger: Logger,
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
            .toMutableMap()
        val pathCommands = fallbackEmitter.emitPathCommands(path)
        pathVars[PathVar.COMMANDS] = pathCommands

        val resolved = PlaceholderResolver.resolve(
            template = fragment.trim(),
            context = context,
            nodeVariables = pathVars,
            nodeNamespace = Namespace.PATH,
        )

        return "$resolved {\n${pathCommands.prependIndent(formatConfig.indentUnit)}\n}"
    }

    private fun emitGroup(group: ImageVectorNode.Group, context: TemplateContext): String {
        val fragment = context.fragments[Fragment.GROUP_BUILDER] ?: return fallbackEmitter.emit(group)

        val groupVars = TemplateContext.groupVariables(group.params)
            .mapValues { (_, v) -> context.applyColorMappings(v) }
            .toMutableMap()

        // Recursively emit child nodes
        val childBody = group.commands.joinToString("\n") { emit(it, context) }
        groupVars[GroupVar.BODY] = childBody

        val resolved = PlaceholderResolver.resolve(
            template = fragment.trim(),
            context = context,
            nodeVariables = groupVars,
            nodeNamespace = Namespace.GROUP,
        )

        return "$resolved {\n${childBody.prependIndent(formatConfig.indentUnit)}\n}"
    }

    private fun emitChunkFunction(chunk: ImageVectorNode.ChunkFunction): String {
        // Chunk functions are always emitted by the fallback emitter
        return fallbackEmitter.emit(chunk)
    }
}
