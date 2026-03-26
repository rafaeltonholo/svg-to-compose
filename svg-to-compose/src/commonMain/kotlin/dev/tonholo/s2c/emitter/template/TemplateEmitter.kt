package dev.tonholo.s2c.emitter.template

import dev.tonholo.s2c.domain.IconFileContents
import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.emitter.CodeEmitter
import dev.tonholo.s2c.emitter.FormatConfig
import dev.tonholo.s2c.emitter.NodeChunker
import dev.tonholo.s2c.emitter.imagevector.ImageVectorEmitter
import dev.tonholo.s2c.emitter.imagevector.ImageVectorNodeEmitter
import dev.tonholo.s2c.emitter.template.TemplateConstants.ChunkVar
import dev.tonholo.s2c.emitter.template.TemplateConstants.Fragment
import dev.tonholo.s2c.emitter.template.TemplateConstants.IconVar
import dev.tonholo.s2c.emitter.template.config.TemplateEmitterConfig
import dev.tonholo.s2c.emitter.template.resolver.PlaceholderResolver
import dev.tonholo.s2c.extensions.camelCase
import dev.tonholo.s2c.logger.Logger

/**
 * A [CodeEmitter] that uses a TOML-based template configuration to generate
 * customized Kotlin code.
 *
 * When the template config lacks an `icon_template`, delegates entirely to
 * [fallbackEmitter].
 *
 * @property logger The logger instance.
 * @property formatConfig The formatting configuration.
 * @property templateEmitterConfig The parsed template configuration.
 * @property fallbackEmitter The default emitter used as fallback.
 */
internal class TemplateEmitter(
    private val logger: Logger,
    private val formatConfig: FormatConfig,
    private val templateEmitterConfig: TemplateEmitterConfig,
    private val fallbackEmitter: ImageVectorEmitter,
) : CodeEmitter {
    private val nodeEmitter = ImageVectorNodeEmitter(formatConfig)
    private val templateNodeEmitter = TemplateNodeEmitter(formatConfig, nodeEmitter)
    private val chunker = NodeChunker(logger)

    override fun emit(contents: IconFileContents): String {
        val iconTemplate = templateEmitterConfig.templates.iconTemplate
            ?: return fallbackEmitter.emit(contents)

        return logger.verboseSection("Generating file with template") {
            val (nodes, chunkFunctions) = chunker.chunkIfNeeded(contents, ::resolveChunkFunctionName)

            // Build a shared context for node emission that will accumulate imports
            val nodeContext = buildNodeContext(contents)

            // Build the icon body by emitting all nodes.
            val iconBody = nodes.joinToString("\n") { node ->
                templateNodeEmitter.emit(node, nodeContext).trimEnd()
            }

            val context = TemplateContext.forIcon(contents, templateEmitterConfig, iconBody)
            context.addImports(nodeContext.collectedImports)
            val resolvedTemplate = PlaceholderResolver.resolve(iconTemplate.trim(), context).trimStart()
            val preview = buildPreview(contents, context)
            val chunkContent = buildChunkFunctionsContent(chunkFunctions)

            buildString {
                val fileHeader = buildFileHeader(context)
                if (fileHeader.isNotEmpty()) {
                    appendLine(fileHeader)
                    appendLine()
                }

                appendLine("package ${contents.pkg}")
                appendLine()

                val allImports = context.collectedImports.sorted()
                appendLine(allImports.joinToString("\n") { "import $it" })
                appendLine()

                appendLine(resolvedTemplate)

                if (chunkContent.isNotEmpty()) {
                    appendLine()
                    appendLine(chunkContent)
                }

                if (preview.isNotEmpty()) {
                    appendLine()
                    append(preview)
                }
            }.trimEnd() + "\n"
        }
    }

    private fun buildNodeContext(contents: IconFileContents): TemplateContext {
        val defs = templateEmitterConfig.definitions.imports
        val frags = templateEmitterConfig.fragments
        return TemplateContext(
            iconVariables = emptyMap(),
            definitions = defs,
            fragments = frags,
            initialImports = contents.imports.toSet(),
            colorMappings = templateEmitterConfig.definitions.colorMapping,
        )
    }

    private fun buildPreview(contents: IconFileContents, context: TemplateContext): String {
        val previewConfig = templateEmitterConfig.templates.preview
        if (previewConfig != null) {
            val previewTemplate = previewConfig.template ?: return ""
            if (previewTemplate.isBlank()) return ""
            return PlaceholderResolver.resolve(previewTemplate.trim(), context)
        }
        // No template preview config — use CLI/Gradle defaults via noPreview flag
        return if (contents.noPreview) "" else buildDefaultPreview(contents, context)
    }

    private fun buildDefaultPreview(contents: IconFileContents, context: TemplateContext): String {
        val preview = fallbackEmitter.buildPreviewSnippet(contents)
        if (preview.isEmpty()) return ""
        // Register preview-related imports that the fallback emitter would have added
        context.addImports(PREVIEW_IMPORTS)
        return preview
    }

    private fun buildFileHeader(context: TemplateContext): String {
        val headerTemplate = templateEmitterConfig.templates.fileHeader ?: return ""
        if (headerTemplate.isBlank()) return ""
        return PlaceholderResolver.resolve(headerTemplate.trim(), context)
    }

    private companion object {
        val PREVIEW_IMPORTS: Set<String> = ImageVectorEmitter.PREVIEW_IMPORTS
    }

    private fun buildChunkFunctionsContent(chunkFunctions: List<ImageVectorNode.ChunkFunction>?): String {
        if (chunkFunctions.isNullOrEmpty()) return ""

        val defFragment = templateEmitterConfig.fragments[Fragment.CHUNK_FUNCTION_DEFINITION]
            ?: return chunkFunctions.joinToString("\n\n") {
                nodeEmitter.emitChunkFunctionDefinition(it)
            }

        return chunkFunctions.joinToString("\n\n") { chunk ->
            val body = chunk.nodes.joinToString("\n") {
                nodeEmitter.emit(it).trimEnd()
            }.prependIndent(formatConfig.indentUnit)

            val context = TemplateContext(
                iconVariables = emptyMap(),
                chunkVariables = mapOf(
                    ChunkVar.NAME to chunk.functionName,
                    ChunkVar.BODY to body,
                ),
                definitions = templateEmitterConfig.definitions.imports,
                fragments = templateEmitterConfig.fragments,
            )
            PlaceholderResolver.resolve(defFragment.trim(), context)
        }
    }

    private fun resolveChunkFunctionName(contents: IconFileContents, index: Int): String {
        val fragment = templateEmitterConfig.fragments[Fragment.CHUNK_FUNCTION_NAME]
            ?: return "${contents.iconName.camelCase()}Chunk$index"

        val context = TemplateContext(
            iconVariables = mapOf(
                IconVar.NAME to contents.iconName.camelCase(),
            ),
            chunkVariables = mapOf(
                ChunkVar.INDEX to index.toString(),
            ),
            definitions = templateEmitterConfig.definitions.imports,
            fragments = templateEmitterConfig.fragments,
        )
        return PlaceholderResolver.resolve(fragment.trim(), context)
    }
}
