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

val previewComponentsImports = setOf(
    "androidx.compose.foundation.Image",
    "androidx.compose.foundation.layout.Arrangement",
    "androidx.compose.foundation.layout.Column",
    "androidx.compose.foundation.layout.height",
    "androidx.compose.foundation.layout.width",
    "androidx.compose.runtime.Composable",
    "androidx.compose.ui.Alignment",
    "androidx.compose.ui.Modifier",
)
val androidPreviewImports = buildSet {
    addAll(previewComponentsImports)
    add("androidx.compose.ui.tooling.preview.Preview")
}
val kmpPreviewImports = buildSet {
    addAll(previewComponentsImports)
    add("org.jetbrains.compose.ui.tooling.preview.Preview")
}

val materialReceiverTypeImport = setOf(
    "androidx.compose.material.icons.Icons"
)

/**
 * Represents the contents of an icon file, including its package, icon name,
 * theme, dimensions, nodes, and other attributes.
 *
 * @property pkg The package name of the icon.
 * @property iconName The name of the icon.
 * @property theme The theme of the Compose application.
 * @property width The width of the icon in density-independent pixels.
 * @property height The height of the icon in density-independent pixels.
 * @property viewportWidth The width of the viewport in floating-point units.
 * @property viewportHeight The height of the viewport in floating-point units.
 * @property nodes The list of nodes that make up the icon.
 * @property receiverType The type of the receiver object for the icon, if any.
 * @property addToMaterial Whether to add the icon to the `Icons.Filled` object.
 * @property noPreview Whether to disable the preview for the icon.
 * @property makeInternal Whether to make the icon internal.
 * @property imports The set of imports to include in the generated code.
 */
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
    /**
     * Generates the Kotlin code for the icon.
     *
     * @return The generated Kotlin code.
     */
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

        val iconPropertyName = buildIconPropertyName(receiverType)
        val (nodes, chunkFunctions) = chunkNodesIfNeeded()
        val chunkFunctionsContent = buildChunkFunctionsContent(chunkFunctions)

        val indentSize = 12
        val pathNodes = nodes
            .joinToString("\n${" ".repeat(indentSize)}") {
                it.materialize()
                    .replace("\n", "\n${" ".repeat(indentSize)}") // fix indent
            }

        val preview = buildPreview(iconPropertyName)
        val extraContent = buildExtraContent(chunkFunctionsContent, preview)
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

    /**
     * Builds the content of the `chunkFunctions` property for the generated class.
     *
     * @param chunkFunctions The list of `ChunkFunction` objects.
     * @return The content of the `chunkFunctions` property as a string.
     */
    private fun buildChunkFunctionsContent(chunkFunctions: List<ImageVectorNode.ChunkFunction>?) =
        if (!chunkFunctions.isNullOrEmpty()) {
            """|
               |${chunkFunctions.joinToString("\n\n") { it.createChunkFunction() }}
               """
        } else {
            ""
        }

    private fun buildIconPropertyName(receiverType: String?) = when {
        receiverType?.isNotEmpty() == true -> {
            // as we add the dot in the next line, remove it in case the user adds a leftover dot
            // to avoid compile issues.
            receiverType.removeSuffix(".")
            "$receiverType.${iconName.pascalCase()}"
        }

        addToMaterial -> "Icons.Filled.${iconName.pascalCase()}"
        else -> iconName.pascalCase()
    }

    /**
     * Builds the preview code for the given icon property name.
     *
     * @param iconPropertyName The name of the icon property to generate the preview for.
     * @return The preview code as a string.
     */
    private fun buildPreview(iconPropertyName: String) = if (noPreview) {
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

    /**
     * Builds a string containing the extra content, which includes the chunk
     * functions and the preview.
     *
     * @param chunkFunctionsContent The string representation of the chunk functions.
     * @param preview The preview string.
     * @return A string containing the extra content.
     */
    private fun buildExtraContent(chunkFunctionsContent: String, preview: String) = buildString {
        if (chunkFunctionsContent.isNotEmpty()) {
            appendLine(chunkFunctionsContent.trimMargin())
        }
        if (preview.isNotEmpty()) {
            appendLine(preview.trimMargin())
        }
    }

    /**
     * Checks if the size of the icon exceeds the threshold for a single method
     * and splits the icon into chunks if needed.
     *
     * @return A pair containing the list of nodes and the list of chunk functions
     * (if any).
     */
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
