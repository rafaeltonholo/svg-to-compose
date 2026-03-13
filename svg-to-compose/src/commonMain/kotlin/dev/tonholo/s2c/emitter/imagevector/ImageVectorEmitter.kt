package dev.tonholo.s2c.emitter.imagevector

import dev.tonholo.s2c.domain.IconFileContents
import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.emitter.CodeEmitter
import dev.tonholo.s2c.emitter.FormatConfig
import dev.tonholo.s2c.emitter.NodeChunker
import dev.tonholo.s2c.extensions.camelCase
import dev.tonholo.s2c.extensions.pascalCase
import dev.tonholo.s2c.logger.Logger
import kotlin.math.max

private const val EXTRA_CONTENT_PLACEHOLDER = "[EXTRA_CONTENT_PLACEHOLDER]"

/**
 * Emits Jetpack Compose `ImageVector.Builder` Kotlin code from [IconFileContents].
 *
 * This is the primary [CodeEmitter] implementation, extracted from the
 * `IconFileContents.materialize()` method to separate domain from formatting.
 *
 * @property logger The logger instance for diagnostic output.
 * @property formatConfig The formatting configuration to use.
 */
class ImageVectorEmitter(
    private val logger: Logger,
    private val formatConfig: FormatConfig = FormatConfig(),
) : CodeEmitter {
    private val nodeEmitter = ImageVectorNodeEmitter(logger, formatConfig)
    private val chunker = NodeChunker(logger)

    /** Single indent level derived from [formatConfig]. */
    private val indent: String get() = formatConfig.indentUnit

    override fun emit(contents: IconFileContents): String = logger.verboseSection("Generating file") {
        logParameters(contents)

        val iconPropertyName = buildIconPropertyName(contents)
        val (nodes, chunkFunctions) = chunker.chunkIfNeeded(contents) { contents, index ->
            "${contents.iconName.camelCase()}Chunk$index"
        }
        val chunkFunctionsContent = buildChunkFunctionsContent(chunkFunctions)

        // Path nodes sit inside: val > get() > Builder.apply { ... } — 3 indent levels.
        val pathNodesIndent = indent.repeat(3)
        val pathNodes = nodes
            .joinToString("\n") { nodeEmitter.emit(it) }
            .prependIndent(pathNodesIndent)

        val preview = buildPreview(contents, iconPropertyName)
        val extraContent = buildExtraContent(chunkFunctionsContent, preview)
        val visibilityModifier = if (contents.makeInternal) "internal " else ""

        val level1 = indent          // 1 level
        val level2 = indent.repeat(2) // 2 levels
        val level3 = indent.repeat(3) // 3 levels

        return@verboseSection """
            |package ${contents.pkg}
            |
            |${contents.imports.sorted().joinToString("\n") { "import $it" }}
            |
            |${visibilityModifier}val $iconPropertyName: ImageVector
            |${level1}get() {
            |${level2}val current = _${contents.iconName.camelCase()}
            |${level2}if (current != null) return current
            |
            |${level2}return ImageVector.Builder(
            |${level3}name = "${contents.theme}.${contents.iconName.pascalCase()}",
            |${level3}defaultWidth = ${contents.width}.dp,
            |${level3}defaultHeight = ${contents.height}.dp,
            |${level3}viewportWidth = ${contents.viewportWidth}f,
            |${level3}viewportHeight = ${contents.viewportHeight}f,
            |${level2}).apply {
            |$pathNodes
            |${level2}}.build().also { _${contents.iconName.camelCase()} = it }
            |${level1}}
            |$EXTRA_CONTENT_PLACEHOLDER
            |@Suppress("ObjectPropertyName")
            |private var _${contents.iconName.camelCase()}: ImageVector? = null
            |
        """.replace(EXTRA_CONTENT_PLACEHOLDER, extraContent)
            .trimMargin()
    }

    private fun logParameters(contents: IconFileContents) {
        logger.verbose(
            """Parameters:
           |    package=${contents.pkg}
           |    icon_name=${contents.iconName}
           |    theme=${contents.theme}
           |    width=${contents.width}
           |    height=${contents.height}
           |    viewport_width=${contents.viewportWidth}
           |    viewport_height=${contents.viewportHeight}
           |    nodes=${contents.nodes.map { nodeEmitter.emit(it) }}
           |    receiver_type=${contents.receiverType}
           |    imports=${contents.imports}
           |
            """.trimMargin()
        )
    }

    private fun buildIconPropertyName(contents: IconFileContents): String = when {
        contents.receiverType?.isNotEmpty() == true -> {
            val receiverType = contents.receiverType.removeSuffix(".")
            "$receiverType.${contents.iconName.pascalCase()}"
        }

        contents.addToMaterial -> "Icons.Filled.${contents.iconName.pascalCase()}"
        else -> contents.iconName.pascalCase()
    }

    /**
     * Builds the default `@Preview` function snippet for an icon.
     *
     * Returns the preview code (starting with `@Preview`) ready to append,
     * or an empty string when preview is suppressed.
     *
     * @param contents The icon file contents.
     * @return The preview snippet, or empty string.
     */
    fun buildPreviewSnippet(contents: IconFileContents): String {
        if (contents.noPreview) return ""
        val iconPropertyName = buildIconPropertyName(contents)
        return buildPreview(contents, iconPropertyName).trimMargin().trim()
    }

    private fun buildPreview(contents: IconFileContents, iconPropertyName: String): String =
        if (contents.noPreview) {
            ""
        } else {
            val level1 = indent
            val level2 = indent.repeat(2)
            val level3 = indent.repeat(3)
            val level4 = indent.repeat(4)
            val level5 = indent.repeat(5)
            """
            |
            |@Preview
            |@Composable
            |private fun IconPreview() {
            |${level1}${contents.theme} {
            |${level2}Column(
            |${level3}verticalArrangement = Arrangement.spacedBy(8.dp),
            |${level3}horizontalAlignment = Alignment.CenterHorizontally,
            |${level2}) {
            |${level3}Image(
            |${level4}imageVector = $iconPropertyName,
            |${level4}contentDescription = null,
            |${level4}modifier = Modifier
            |${level5}.width((${max(contents.width, contents.viewportWidth)}).dp)
            |${level5}.height((${max(contents.height, contents.viewportHeight)}).dp),
            |${level3})
            |${level2}}
            |${level1}}
            |}
            """
        }

    private fun buildChunkFunctionsContent(
        chunkFunctions: List<ImageVectorNode.ChunkFunction>?,
    ): String = if (!chunkFunctions.isNullOrEmpty()) {
        """|
           |${chunkFunctions.joinToString("\n\n") { nodeEmitter.emitChunkFunctionDefinition(it) }}
           """
    } else {
        ""
    }

    private fun buildExtraContent(chunkFunctionsContent: String, preview: String): String = buildString {
        if (chunkFunctionsContent.isNotEmpty()) {
            appendLine(chunkFunctionsContent.trimMargin())
        }
        if (preview.isNotEmpty()) {
            appendLine(preview.trimMargin())
        }
    }

}
