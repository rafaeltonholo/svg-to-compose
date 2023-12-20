package dev.tonholo.s2c.domain

import AppConfig
import dev.tonholo.s2c.extensions.camelCase
import dev.tonholo.s2c.extensions.pascalCase

val defaultImports = setOf(
    "androidx.compose.foundation.Image",
    "androidx.compose.foundation.layout.Arrangement",
    "androidx.compose.foundation.layout.Column",
    "androidx.compose.foundation.layout.size",
    "androidx.compose.runtime.Composable",
    "androidx.compose.ui.Alignment",
    "androidx.compose.ui.Modifier",
    "androidx.compose.ui.graphics.Color",
    "androidx.compose.ui.graphics.SolidColor",
    "androidx.compose.ui.graphics.vector.ImageVector",
    "androidx.compose.ui.graphics.vector.path",
    "androidx.compose.ui.tooling.preview.Preview",
    "androidx.compose.ui.unit.dp",
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
    val contextProvider: String? = null,
    val imports: Set<String> = defaultImports,
) {
    fun materialize(): String {
        if (AppConfig.debug) {
            println()
            println("========================= Generating file =========================")
            println(
                """Parameters:
                   |    package=${pkg}
                   |    icon_name=${iconName}
                   |    theme=${theme}
                   |    width=${width}
                   |    height=${height}
                   |    viewport_width=${viewportWidth}
                   |    viewport_height=${viewportHeight}
                   |    nodes=${nodes.map { it.materialize() }}
                   |    context_provider=${contextProvider}
                   |    imports=${imports}
                   |    """.trimMargin()
            )
        }

        val indentSize = 12
        val pathNodes = nodes.joinToString("\n${" ".repeat(indentSize)}") {
            it.materialize()
                .replace("\n", "\n${" ".repeat(indentSize)}") // fix indent
        }
        return """
            |package $pkg
            |
            |${imports.sorted().joinToString("\n")}
            |
            |val ${iconName.pascalCase()}: ImageVector
            |   get() {
            |       val current = _${iconName.camelCase()}
            |       if (current != null) return current
            |
            |       return ImageVector.Builder(
            |           name = "${theme}.${iconName.pascalCase()}"
            |           defaultWidth = {width}.dp,
            |           defaultHeight = {height}.dp,
            |           viewportWidth = {viewport_width}f,
            |           viewportHeight = {viewport_height}f,
            |       ).apply {
            |           $pathNodes
            |       }.build().also { _${iconName.camelCase()} = it }
            |   }
            |
            |@Preview
            |@Composable
            |private fun IconPreview() {
            |   $theme {
            |       Column(
            |           verticalArrangement = Arrangement.spacedBy(8.dp),
            |           horizontalAlignment = Alignment.CenterHorizontally,
            |       ) {
            |           Image(
            |               imageVector = ${iconName.pascalCase()},
            |               contentDescription = null,
            |               modifier = Modifier.size(100.dp),
            |           )
            |       }
            |   }
            |}
            |
        """.trimMargin()
    }
}
