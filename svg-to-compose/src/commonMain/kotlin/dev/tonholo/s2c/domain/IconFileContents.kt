package dev.tonholo.s2c.domain

import dev.tonholo.s2c.extensions.camelCase
import dev.tonholo.s2c.extensions.pascalCase
import dev.tonholo.s2c.logger.verbose
import dev.tonholo.s2c.logger.verboseSection
import dev.tonholo.s2c.logger.warn
import dev.tonholo.s2c.parser.method.MethodSizeAccountable
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.roundToInt

private const val ICON_BASE_STRUCTURE_BYTE_SIZE = 73
private const val EXTRA_CONTENT_PLACEHOLDER = "[EXTRA_CONTENT_PLACEHOLDER]"

val defaultImports = setOf(
    "androidx.compose.ui.graphics.vector.ImageVector",
    "androidx.compose.ui.unit.dp",
)

val previewImports = setOf(
    "androidx.compose.foundation.Image",
    "androidx.compose.foundation.layout.Arrangement",
    "androidx.compose.foundation.layout.Column",
    "androidx.compose.foundation.layout.height",
    "androidx.compose.foundation.layout.width",
    "androidx.compose.runtime.Composable",
    "androidx.compose.ui.Alignment",
    "androidx.compose.ui.Modifier",
    "androidx.compose.ui.tooling.preview.Preview",
)

val materialReceiverTypeImport = setOf(
    "androidx.compose.material.icons.Icons"
)

data class IconFileContents(
    val pkg: String,
    val iconName: String,
    val theme: String,
    val width: Float,
    val height: Float,
    val viewportWidth: Float = width,
    val viewportHeight: Float = height,
    val nodes: List<ImageVectorNode>,
    val receiverType: String? = null,
    val addToMaterial: Boolean = false,
    val noPreview: Boolean = false,
    val makeInternal: Boolean = false,
    val imports: Set<String> = defaultImports,
) {
    fun materialize(): String = verboseSection("Generating file") {
        verbose(
            """Parameters:
           |    package=$pkg
           |    icon_name=$iconName
           |    theme=$theme
           |    width=$width
           |    height=$height
           |    viewport_width=$viewportWidth
           |    viewport_height=$viewportHeight
           |    nodes=${nodes.map { it.materialize() }}
           |    receiver_type=$receiverType
           |    imports=$imports
           |
            """.trimMargin()
        )

        val iconPropertyName = when {
            receiverType?.isNotEmpty() == true -> {
                // as we add the dot in the next line, remove it in case the user adds a leftover dot
                // to avoid compile issues.
                receiverType.removeSuffix(".")
                "$receiverType.${iconName.pascalCase()}"
            }

            addToMaterial -> "Icons.Filled.${iconName.pascalCase()}"
            else -> iconName.pascalCase()
        }

        val (nodes, chunkFunctions) = chunkNodesIfNeeded()

        val chunkFunctionsStr = if (!chunkFunctions.isNullOrEmpty()) {
            """|
               |${chunkFunctions.joinToString("\n\n") { it.createChunkFunction() }}
               """
        } else {
            ""
        }

        val indentSize = 12
        val pathNodes = nodes
            .joinToString("\n${" ".repeat(indentSize)}") {
                it.materialize()
                    .replace("\n", "\n${" ".repeat(indentSize)}") // fix indent
            }

        val preview = if (noPreview) {
            ""
        } else {
            """
            |
            |@Preview
            |@Composable
            |private fun IconPreview() {
            |    $theme {
            |        Column(
            |            verticalArrangement = Arrangement.spacedBy(8.dp),
            |            horizontalAlignment = Alignment.CenterHorizontally,
            |        ) {
            |            Image(
            |                imageVector = $iconPropertyName,
            |                contentDescription = null,
            |                modifier = Modifier
            |                    .width((${max(width, viewportWidth)}).dp)
            |                    .height((${max(height, viewportHeight)}).dp),
            |            )
            |        }
            |    }
            |}
            """
        }

        val extraContent = buildString {
            if (chunkFunctionsStr.isNotEmpty()) {
                appendLine(chunkFunctionsStr.trimMargin())
            }
            if (preview.isNotEmpty()) {
                appendLine(preview.trimMargin())
            }
        }

        val visibilityModifier = if (makeInternal) "internal " else ""

        return@verboseSection """
            |package $pkg
            |
            |${imports.sorted().joinToString("\n") { "import $it" }}
            |
            |${visibilityModifier}val $iconPropertyName: ImageVector
            |    get() {
            |        val current = _${iconName.camelCase()}
            |        if (current != null) return current
            |
            |        return ImageVector.Builder(
            |            name = "$theme.${iconName.pascalCase()}",
            |            defaultWidth = $width.dp,
            |            defaultHeight = $height.dp,
            |            viewportWidth = ${viewportWidth}f,
            |            viewportHeight = ${viewportHeight}f,
            |        ).apply {
            |            $pathNodes
            |        }.build().also { _${iconName.camelCase()} = it }
            |    }
            |$EXTRA_CONTENT_PLACEHOLDER
            |@Suppress("ObjectPropertyName")
            |private var _${iconName.camelCase()}: ImageVector? = null
            |
        """.replace(EXTRA_CONTENT_PLACEHOLDER, extraContent)
            .trimMargin()
    }

    private fun chunkNodesIfNeeded(): Pair<List<ImageVectorNode>, List<ImageVectorNode.ChunkFunction>?> {
        val byteSize = ICON_BASE_STRUCTURE_BYTE_SIZE + nodes
            .sumOf { it.approximateByteSize }
        val shouldChunkNodes = byteSize > MethodSizeAccountable.METHOD_SIZE_THRESHOLD

        val nodes = if (shouldChunkNodes) {
            var i = 1
            val chunks = ceil(byteSize.toFloat() / MethodSizeAccountable.METHOD_SIZE_THRESHOLD)
                .roundToInt()
            val chunkSize = nodes.size / chunks
            warn(
                "Potential large icon detected. Splitting icon's content in $chunks chunks to avoid " +
                    "compilation issues. However, that won't affect the performance of displaying this icon."
            )
            nodes.chunked(chunkSize) { chunk ->
                val snapshot = chunk.toList()
                ImageVectorNode.ChunkFunction(
                    functionName = "${iconName.camelCase()}Chunk${i++}",
                    nodes = snapshot,
                )
            }
        } else {
            nodes
        }
        val chunkFunctions = if (shouldChunkNodes) {
            nodes.filterIsInstance<ImageVectorNode.ChunkFunction>()
        } else {
            null
        }

        return nodes to chunkFunctions
    }
}
