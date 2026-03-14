package dev.tonholo.s2c.emitter.imagevector

import dev.tonholo.s2c.domain.IconFileContents
import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.emitter.CodeEmitter
import dev.tonholo.s2c.emitter.FormatConfig
import dev.tonholo.s2c.extensions.camelCase
import dev.tonholo.s2c.extensions.pascalCase
import dev.tonholo.s2c.logger.Logger
import dev.tonholo.s2c.parser.method.MethodSizeAccountable
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.roundToInt

private const val ICON_BASE_STRUCTURE_BYTE_SIZE = 73
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
    @Suppress("UnusedPrivateProperty")
    private val formatConfig: FormatConfig = FormatConfig(),
) : CodeEmitter {
    private val nodeEmitter = ImageVectorNodeEmitter()

    override fun emit(contents: IconFileContents): String = logger.verboseSection("Generating file") {
        logParameters(contents)

        val iconPropertyName = buildIconPropertyName(contents)
        val (nodes, chunkFunctions) = chunkNodesIfNeeded(contents)
        val chunkFunctionsContent = buildChunkFunctionsContent(chunkFunctions)

        val indentSize = 12
        val pathNodes = nodes
            .joinToString("\n${" ".repeat(indentSize)}") {
                nodeEmitter.emit(it)
                    .replace("\n", "\n${" ".repeat(indentSize)}")
            }

        val preview = buildPreview(contents, iconPropertyName)
        val extraContent = buildExtraContent(chunkFunctionsContent, preview)
        val visibilityModifier = if (contents.makeInternal) "internal " else ""

        return@verboseSection """
            |package ${contents.pkg}
            |
            |${contents.imports.sorted().joinToString("\n") { "import $it" }}
            |
            |${visibilityModifier}val $iconPropertyName: ImageVector
            |    get() {
            |        val current = _${contents.iconName.camelCase()}
            |        if (current != null) return current
            |
            |        return ImageVector.Builder(
            |            name = "${contents.theme}.${contents.iconName.pascalCase()}",
            |            defaultWidth = ${contents.width}.dp,
            |            defaultHeight = ${contents.height}.dp,
            |            viewportWidth = ${contents.viewportWidth}f,
            |            viewportHeight = ${contents.viewportHeight}f,
            |        ).apply {
            |            $pathNodes
            |        }.build().also { _${contents.iconName.camelCase()} = it }
            |    }
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
           |    nodes=[${contents.nodes.size} node(s)]
           |    receiver_type=${contents.receiverType}
           |    imports=${contents.imports}
           |
            """.trimMargin(),
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

    private fun buildPreview(contents: IconFileContents, iconPropertyName: String): String = if (contents.noPreview) {
        ""
    } else {
        """
            |
            |@Preview
            |@Composable
            |private fun IconPreview() {
            |    ${contents.theme} {
            |        Column(
            |            verticalArrangement = Arrangement.spacedBy(8.dp),
            |            horizontalAlignment = Alignment.CenterHorizontally,
            |        ) {
            |            Image(
            |                imageVector = $iconPropertyName,
            |                contentDescription = null,
            |                modifier = Modifier
            |                    .width((${max(contents.width, contents.viewportWidth)}).dp)
            |                    .height((${max(contents.height, contents.viewportHeight)}).dp),
            |            )
            |        }
            |    }
            |}
            """
    }

    private fun buildChunkFunctionsContent(chunkFunctions: List<ImageVectorNode.ChunkFunction>?): String =
        if (!chunkFunctions.isNullOrEmpty()) {
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

    private fun chunkNodesIfNeeded(
        contents: IconFileContents,
    ): Pair<List<ImageVectorNode>, List<ImageVectorNode.ChunkFunction>?> {
        val byteSize = ICON_BASE_STRUCTURE_BYTE_SIZE + contents.nodes
            .sumOf { it.approximateByteSize }
        val shouldChunkNodes = byteSize > MethodSizeAccountable.METHOD_SIZE_THRESHOLD

        val nodes = if (shouldChunkNodes) {
            var i = 1
            val chunks = ceil(byteSize.toFloat() / MethodSizeAccountable.METHOD_SIZE_THRESHOLD)
                .roundToInt()
            val chunkSize = max(1, contents.nodes.size / chunks)
            logger.warn(
                "Potential large icon detected. Splitting icon's content in $chunks chunks to avoid " +
                    "compilation issues. However, that won't affect the performance of displaying this icon.",
            )
            contents.nodes.chunked(chunkSize) { chunk ->
                val snapshot = chunk.toList()
                ImageVectorNode.ChunkFunction(
                    functionName = "${contents.iconName.camelCase()}Chunk${i++}",
                    nodes = snapshot,
                )
            }
        } else {
            contents.nodes
        }
        val chunkFunctions = if (shouldChunkNodes) {
            nodes.filterIsInstance<ImageVectorNode.ChunkFunction>()
        } else {
            null
        }

        return nodes to chunkFunctions
    }
}
