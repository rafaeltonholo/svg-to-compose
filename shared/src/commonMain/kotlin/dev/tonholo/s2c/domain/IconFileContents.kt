package dev.tonholo.s2c.domain

import dev.tonholo.s2c.extensions.camelCase
import dev.tonholo.s2c.extensions.pascalCase
import dev.tonholo.s2c.logger.verbose
import dev.tonholo.s2c.logger.verboseSection
import kotlin.math.max

val defaultImports = setOf(
    "androidx.compose.ui.graphics.Color",
    "androidx.compose.ui.graphics.SolidColor",
    "androidx.compose.ui.graphics.vector.ImageVector",
    "androidx.compose.ui.graphics.vector.path",
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

val groupImports = setOf(
    "androidx.compose.ui.graphics.vector.PathData",
    "androidx.compose.ui.graphics.vector.group",
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

        val indentSize = 12
        val pathNodes = nodes.joinToString("\n${" ".repeat(indentSize)}") {
            it.materialize()
                .replace("\n", "\n${" ".repeat(indentSize)}") // fix indent
        }

        val preview = if (noPreview) {
            "|"
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
            |                   .width((${max(width, viewportWidth)}).dp)
            |                   .height((${max(height, viewportHeight)}).dp),
            |            )
            |        }
            |    }
            |}
            |
            """
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
            ${preview.trim()}
            |@Suppress("ObjectPropertyName")
            |private var _${iconName.camelCase()}: ImageVector? = null
            |
        """.trimMargin()
    }
}
