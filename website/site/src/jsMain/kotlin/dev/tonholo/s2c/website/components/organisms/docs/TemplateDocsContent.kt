package dev.tonholo.s2c.website.components.organisms.docs

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.BorderCollapse
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TableLayout
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.attrsModifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderBottom
import com.varabyte.kobweb.compose.ui.modifiers.borderCollapse
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.tableLayout
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.components.atoms.DocSection
import dev.tonholo.s2c.website.components.atoms.InlineCode
import dev.tonholo.s2c.website.components.molecules.CalloutVariant
import dev.tonholo.s2c.website.components.molecules.CodeAwareSpanText
import dev.tonholo.s2c.website.components.molecules.CodeBlock
import dev.tonholo.s2c.website.components.molecules.DocCallout
import dev.tonholo.s2c.website.components.molecules.ImportantCalloutCodeAwareVariant
import dev.tonholo.s2c.website.components.molecules.TipCalloutCodeAwareVariant
import dev.tonholo.s2c.website.navigation.WebRoute
import dev.tonholo.s2c.website.theme.SitePalette
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.theme.common.SiteLinkStyleVariant
import dev.tonholo.s2c.website.theme.toSitePalette
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Li
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Table
import org.jetbrains.compose.web.dom.Tbody
import org.jetbrains.compose.web.dom.Td
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.Th
import org.jetbrains.compose.web.dom.Thead
import org.jetbrains.compose.web.dom.Tr
import org.jetbrains.compose.web.dom.Ul

private const val TABLE_LINE_HEIGHT = 1.5
private const val ALTERNATING_ROW_ALPHA = 0.06f
private const val TRIPLE_QUOTE = "\"\"\""

// language=toml
private val QUICK_START_TEMPLATE = $$"""
|[definitions]
|receiver = { name = "Icons", package = "com.example.icons" }
|
|[definitions.imports]
|icon_builder = "com.example.icons.icon"
|icon_path = "com.example.icons.iconPath"
|theme = "com.example.theme.AppTheme"
|
|[templates]
|file_header = $$TRIPLE_QUOTE
|// Copyright 2026 My Company. All rights reserved.
|// SPDX-License-Identifier: Apache-2.0
|$$TRIPLE_QUOTE
|
|icon_template = $$TRIPLE_QUOTE
|${icon:visibility} val ${icon:property_name}: ImageVector by lazy {
|    ${template:icon_builder} {
|        ${icon:body}
|    }
|}
|$$TRIPLE_QUOTE
|
|[templates.preview]
|template = $$TRIPLE_QUOTE
|@Preview(name = "${icon:name}", showBackground = true)
|@Composable
|private fun ${icon:name}Preview() {
|    ${def:theme} {
|        Image(
|            imageVector = ${icon:name},
|            contentDescription = null,
|        )
|    }
|}
|$$TRIPLE_QUOTE
|
|[fragments]
|icon_builder = "${def:icon_builder}(name = ${icon:name}, viewportWidth = ${icon:viewport_width}, viewportHeight = ${icon:viewport_height})"
|path_builder = "${def:icon_path}(fill = ${path:fill}, fillAlpha = ${path:fill_alpha})"
|group_builder = "group(rotate = ${group:rotate}, pivotX = ${group:pivot_x}, pivotY = ${group:pivot_y})"
""".trimMargin()

// language=toml
private val RECEIVER_EXAMPLE = """
|[definitions]
|receiver = { name = "Icons", package = "com.example.icons" }
""".trimMargin()

// language=toml
private val IMPORTS_EXAMPLE = """
|[definitions.imports]
|icon_builder = "com.example.icons.icon"
|icon_path = "com.example.icons.iconPath"
|theme = "com.example.theme.AppTheme"
""".trimMargin()

// language=toml
private val COLOR_MAPPING_EXAMPLE = """
|[[definitions.color_mapping]]
|name = "BLACK"
|import = "com.example.theme.colors"
|value = "0xFF121212"
|
|[[definitions.color_mapping]]
|name = "PRIMARY"
|import = "com.example.theme.colors"
|value = "0xFF0066FF"
""".trimMargin()

// language=toml
private val FILE_HEADER_EXAMPLE = """
|[templates]
|file_header = $TRIPLE_QUOTE
|// Copyright 2026 My Company. All rights reserved.
|// SPDX-License-Identifier: Apache-2.0
|@file:Suppress("ktlint")
|$TRIPLE_QUOTE
""".trimMargin()

// language=toml
private val ICON_TEMPLATE_EXAMPLE = $$"""
|[templates]
|icon_template = $$TRIPLE_QUOTE
|${icon:visibility} val ${icon:property_name}: ImageVector by lazy {
|    ${template:icon_builder} {
|        ${icon:body}
|    }
|}
|$$TRIPLE_QUOTE
""".trimMargin()

// language=toml
private val previewExample = $$"""
|[templates.preview]
|template = $$TRIPLE_QUOTE
|@Preview(name = "${icon:name}")
|@Composable
|private fun ${icon:name}Preview() {
|    ${def:theme} {
|        Image(imageVector = ${icon:name}, contentDescription = null)
|    }
|}
|$$TRIPLE_QUOTE
""".trimMargin()

// language=toml
private val fragmentsExample = $$"""
|[fragments]
|icon_builder = "${def:icon_builder}(name = ${icon:name}, viewportWidth = ${icon:viewport_width}, viewportHeight = ${icon:viewport_height})"
|path_builder = "${def:icon_path}(fill = ${path:fill}, fillAlpha = ${path:fill_alpha})"
|group_builder = "group(rotate = ${group:rotate}, pivotX = ${group:pivot_x}, pivotY = ${group:pivot_y})"
|
|# Optional: customize chunk function names
|chunk_function_name = "${icon:name}Part${chunk:index}"
|chunk_function_definition = "private fun ${def:custom_builder}.${chunk:name}() {\n${chunk:body}\n}"
""".trimMargin()

// language=kotlin
private val gradlePerConfigExample = """
|svgToCompose {
|    processor {
|        common {
|            icons {
|                // Default template for all processors
|                templateFile(layout.projectDirectory.file("templates/default.template.toml"))
|            }
|        }
|
|        val outlined by creating {
|            from(layout.projectDirectory.dir("icons/outlined"))
|            destinationPackage("com.example.icons.outlined")
|            icons {
|                theme("com.example.theme.AppTheme")
|                // Override with a different template
|                templateFile(layout.projectDirectory.file("templates/outlined.template.toml"))
|            }
|        }
|
|        val filled by creating {
|            from(layout.projectDirectory.dir("icons/filled"))
|            destinationPackage("com.example.icons.filled")
|            icons {
|                theme("com.example.theme.AppTheme")
|                // No templateFile() -> inherits from common config
|            }
|        }
|    }
|}
""".trimMargin()

val TemplateTableStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .fillMaxWidth()
        .backgroundColor(palette.surfaceVariant)
        .fontSize(0.75.cssRem)
        .lineHeight(TABLE_LINE_HEIGHT)
        .borderCollapse(BorderCollapse.Collapse)
        .tableLayout(TableLayout.Fixed)
}

val TemplateTableHeaderStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .padding(topBottom = SiteTheme.dimensions.size.Md, leftRight = SiteTheme.dimensions.size.Lg)
        .fontWeight(FontWeight.SemiBold)
        .backgroundColor(palette.surface)
        .borderBottom(
            width = 1.px,
            style = LineStyle.Solid,
            color = palette.outline,
        )
}

val TemplateTableCellStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .padding(topBottom = SiteTheme.dimensions.size.Md, leftRight = SiteTheme.dimensions.size.Lg)
        .borderBottom(
            width = 1.px,
            style = LineStyle.Solid,
            color = palette.outline,
        )
}

@Composable
fun TemplateDocsContent(modifier: Modifier = Modifier) {
    Column(modifier = Modifier.fillMaxWidth().gap(SiteTheme.dimensions.size.Xxl).then(modifier)) {
        OverviewSection()
        QuickStartSection()
        DefinitionsSection()
        TemplatesSection()
        FragmentsSection()
        PlaceholderGrammarSection()
        VariablesByNamespaceSection()
        NullHandlingSection()
        AutoDiscoverySection()
        ExamplesSection()
    }
}

@Composable
private fun OverviewSection() {
    DocSection(id = "overview", title = "Overview") {
        CodeAwareSpanText(
            text = "The template system lets you control the shape of the generated Kotlin code " +
                "without modifying the tool. Place an `s2c.template.toml` file in your project " +
                "and configure builder functions, receiver types, imports, and property patterns.",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        SpanText(
            text = "Key capabilities:",
            modifier = DocsBodyTextStyle.toModifier()
                .fontWeight(FontWeight.SemiBold),
        )
        Ul(attrs = DocsBulletListStyle.toModifier().toAttrs()) {
            Li { Text("Custom file headers (license blocks, suppressions)") }
            Li { Text("Custom icon property shapes (lazy, backing-field, function)") }
            Li { Text("Custom builder call signatures (icon, path, group)") }
            Li { Text("Named import definitions resolved via placeholders") }
            Li { Text("Colour mapping from hex values to named constants") }
            Li { Text("Preview function customisation or suppression") }
        }
        DocCallout(variant = CalloutVariant.TIP) {
            P(attrs = DocsBodyTextStyle.toModifier().margin { top(0.px) }.toAttrs()) {
                Text(
                    value = "Every template piece is independently optional. Missing pieces fall back to the " +
                        "default output, so you only need to define what you want to change.",
                )
            }
            P(attrs = DocsBodyTextStyle.toAttrs()) {
                Text(
                    value = "You can use our ",
                )
                Link(
                    path = WebRoute.Playground.path,
                    text = "interactive template playground",
                    variant = SiteLinkStyleVariant,
                )
                Text(
                    value = " to experiment with template syntax and see how it affects the generated code. " +
                        "The playground also features an auto-complete reference for all available variables and " +
                        "placeholders.",
                )
            }
        }
    }
}

@Composable
private fun QuickStartSection() {
    DocSection(id = "quick-start", title = "Quick Start") {
        CodeAwareSpanText(
            text = "Create `s2c.template.toml` in your project root (or next to the output directory):",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        CodeBlock(
            code = QUICK_START_TEMPLATE.trimMargin(),
            language = "toml",
            filename = "s2c.template.toml",
        )
        SpanText(
            text = "Run the CLI with the template flag:",
            modifier = DocsBodyTextStyle.toModifier()
                .fontWeight(FontWeight.SemiBold),
        )
        CodeBlock(
            code = "./s2c --template s2c.template.toml -p com.example -t MyTheme input.svg -o output/",
            language = "shell",
            filename = "Terminal",
        )
        SpanText(
            text = "Or configure via the Gradle plugin:",
            modifier = DocsBodyTextStyle.toModifier()
                .fontWeight(FontWeight.SemiBold),
        )
        CodeBlock(
            // language=kotlin
            code = """
                |svgToCompose {
                |    processor {
                |        val icons by creating {
                |            from(layout.projectDirectory.dir("assets/icons"))
                |            destinationPackage("com.example.icons")
                |            icons {
                |                theme("com.example.theme.AppTheme")
                |                templateFile(layout.projectDirectory.file("s2c.template.toml"))
                |            }
                |        }
                |    }
                |}
            """.trimMargin(),
            language = "kotlin",
            filename = "build.gradle.kts",
        )
    }
}

@Composable
private fun DefinitionsSection() {
    DocSection(id = "definitions", title = "Schema Reference: Definitions") {
        CodeAwareSpanText(
            text = "The `[definitions]` section declares reusable values referenced throughout templates " +
                "and fragments.",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        ReceiverSubsection()
        ImportsSubsection()
        ColorMappingSubsection()
    }
}

@Composable
private fun ReceiverSubsection() {
    DocSection(id = "definitions-receiver", title = "Receiver", level = 3) {
        CodeAwareSpanText(
            text = "Optional receiver type for the icon property. Adds " +
                "`val <Receiver>.<IconName>: ImageVector` and auto-imports the receiver.",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        CodeBlock(
            code = RECEIVER_EXAMPLE.trimMargin(),
            language = "toml",
            filename = "s2c.template.toml",
        )
        FieldTable(
            headers = listOf("Field", "Type", "Description"),
            rows = listOf(
                listOf("name", "String", "Receiver name (e.g., Icons)"),
                listOf("package", "String", "Package to auto-import"),
            ),
        )
    }
}

@Composable
private fun ImportsSubsection() {
    DocSection(id = "definitions-imports", title = "Imports", level = 3) {
        CodeAwareSpanText(
            text = $$"Keyed imports referenced in templates via `${def:<key>}`. Each key maps to a fully " +
                $$"qualified import path. During resolution, `${def:key}` is replaced with the simple name " +
                "(last segment), and the full path is added to the file's import list.",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        CodeBlock(
            code = IMPORTS_EXAMPLE.trimMargin(),
            language = "toml",
            filename = "s2c.template.toml",
        )
    }
}

@Composable
private fun ColorMappingSubsection() {
    DocSection(id = "definitions-color-mapping", title = "Colour Mapping", level = 3) {
        CodeAwareSpanText(
            text = "Optional array of colour mappings. When a generated " +
                "`Color(<hex>)` expression matches a mapping's value, it is replaced with the mapping's name " +
                "and the full import is added to the file.",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        CodeBlock(
            code = COLOR_MAPPING_EXAMPLE.trimMargin(),
            language = "toml",
            filename = "s2c.template.toml",
        )
        FieldTable(
            headers = listOf("Field", "Type", "Description"),
            rows = listOf(
                listOf("name", "String", "Constant name used in generated code (e.g., BLACK)"),
                listOf("import", "String", "Package containing the constant"),
                listOf("value", "String", "Hex colour value to match (e.g., 0xFF121212)"),
            ),
        )
        DocCallout(variant = CalloutVariant.TIP) {
            CodeAwareSpanText(
                text = "For example, given `fill=\"#121212\"` in the SVG, the emitter generates " +
                    "`SolidColor(Color(0xFF121212))`. With the mapping above, this becomes `SolidColor(BLACK)` and " +
                    "`import com.example.theme.colors.BLACK` is added.",
                modifier = DocsBodyTextStyle.toModifier(),
                variant = TipCalloutCodeAwareVariant,
            )
        }
    }
}

@Composable
private fun TemplatesSection() {
    DocSection(id = "templates", title = "Schema Reference: Templates") {
        CodeAwareSpanText(
            text = "The `[templates]` section controls the overall shape of the generated output file.",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        FileHeaderSubsection()
        IconTemplateSubsection()
        PreviewSubsection()
    }
}

@Composable
private fun FileHeaderSubsection() {
    DocSection(id = "templates-file-header", title = "File Header", level = 3) {
        CodeAwareSpanText(
            text = "Optional file header placed before the package statement. Use for license " +
                "headers, `@file:Suppress(...)`, or `@file:OptIn(...)` annotations. Supports " +
                "all icon-level placeholders.",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        CodeBlock(
            code = FILE_HEADER_EXAMPLE.trimMargin(),
            language = "toml",
            filename = "s2c.template.toml",
        )
    }
}

@Composable
private fun IconTemplateSubsection() {
    DocSection(id = "templates-icon-template", title = "Icon Template", level = 3) {
        CodeAwareSpanText(
            text = "The outer icon property template. Controls the generated property/function shape. If absent, " +
                $$"the default backing-field pattern is used. Use `${icon:visibility}` to place the visibility " +
                "modifier. When the icon is not internal, this resolves to an empty string and leading whitespace " +
                "is trimmed.",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        CodeBlock(
            code = ICON_TEMPLATE_EXAMPLE.trimMargin(),
            language = "toml",
            filename = "s2c.template.toml",
        )
    }
}

@Composable
private fun PreviewSubsection() {
    DocSection(id = "templates-preview", title = "Preview", level = 3) {
        CodeAwareSpanText(
            text = "Full control over the preview function. If absent, default preview behaviour " +
                "applies (controlled by `--no-preview` / `--theme`). If present with an empty " +
                "template, preview generation is suppressed.",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        CodeBlock(
            code = previewExample.trimMargin(),
            language = "toml",
            filename = "s2c.template.toml",
        )
    }
}

@Composable
private fun FragmentsSection() {
    DocSection(id = "fragments", title = "Schema Reference: Fragments") {
        SpanText(
            text = "Named template fragments for builder call shapes. The engine applies " +
                "path_builder per-path node, group_builder per-group node, and " +
                "icon_builder once per icon. If a fragment is absent, the default " +
                "ImageVector.Builder DSL call is used.",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        CodeBlock(
            code = fragmentsExample.trimMargin(),
            language = "toml",
            filename = "s2c.template.toml",
        )
        SpanText(
            text = "Two optional fragments control chunk function generation for large icons:",
            modifier = DocsBodyTextStyle.toModifier()
                .fontWeight(FontWeight.SemiBold),
        )
        Ul(attrs = DocsBulletListStyle.toModifier().toAttrs()) {
            Li {
                CodeAwareSpanText(
                    text = $$"`chunk_function_name`: controls the function name. Receives `${icon:name}`" +
                        $$" and `${chunk:index}`.",
                )
            }
            Li {
                CodeAwareSpanText(
                    text = "`chunk_function_definition`: controls the full function signature and body. " +
                        $$"Receives `${chunk:name}` and `${chunk:body}`.",
                )
            }
        }
    }
}

@Composable
private fun PlaceholderGrammarSection() {
    val palette = ColorMode.current.toSitePalette()

    DocSection(id = "placeholder-grammar", title = "Placeholder Grammar") {
        CodeAwareSpanText(
            text = "Placeholders use the syntax " +
                $$"`${namespace:key}`" +
                ". The regex pattern is: " +
                "`\\$\\{(icon|path|group|chunk|template|def):([a-z][a-z0-9_.]*)\\}`",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        Table(attrs = TemplateTableStyle.toModifier().toAttrs()) {
            PlaceholderGrammarTableHeader(palette)
            Tbody {
                NamespaceRow(
                    namespace = "icon",
                    syntax = $$"${icon:<field>}",
                    resolves = "Value from icon metadata",
                    scope = "`icon_template`, `icon_builder`",
                    index = 0,
                )
                NamespaceRow(
                    namespace = "path",
                    syntax = $$"${path:<field>}",
                    resolves = "Value from path node parameters",
                    scope = "`path_builder` fragment only",
                    index = 1,
                )
                NamespaceRow(
                    namespace = "group",
                    syntax = $$"${group:<field>}",
                    resolves = "Value from group node parameters",
                    scope = "`group_builder` fragment only",
                    index = 2,
                )
                NamespaceRow(
                    namespace = "template",
                    syntax = $$"${template:<name>}",
                    resolves = "Rendered fragment output",
                    scope = "`icon_template`",
                    index = 3,
                )
                NamespaceRow(
                    namespace = "chunk",
                    syntax = $$"${chunk:<field>}",
                    resolves = "Value from chunk function context",
                    scope = "`chunk_function_*` fragments",
                    index = 4,
                )
                NamespaceRow(
                    namespace = "def",
                    syntax = $$"${def:<key>}",
                    resolves = "Simple name of import",
                    scope = "Any template or fragment",
                    index = 5,
                )
            }
        }
    }
}

@Composable
private fun PlaceholderGrammarTableHeader(palette: SitePalette) {
    Thead {
        Tr {
            TableHeader(text = "Namespace", palette = palette, align = TextAlign.Start)
            TableHeader(text = "Syntax", palette = palette, align = TextAlign.Start)
            TableHeader(text = "Resolves to", palette = palette, align = TextAlign.Start)
            TableHeader(text = "Scope", palette = palette, align = TextAlign.Start)
        }
    }
}

private val iconVariables = listOf(
    $$"${icon:name}" to "PascalCase icon name",
    $$"${icon:property_name}" to "Full property name with receiver prefix",
    $$"${icon:receiver}" to "Receiver from definitions or CLI",
    $$"${icon:theme}" to "Theme name",
    $$"${icon:width}" to "Width in dp",
    $$"${icon:height}" to "Height in dp",
    $$"${icon:viewport_width}" to "Viewport width (float with f suffix)",
    $$"${icon:viewport_height}" to "Viewport height (float with f suffix)",
    $$"${icon:body}" to "Engine-generated body (all nodes)",
    $$"${icon:package}" to "Package name",
    $$"${icon:visibility}" to "\"internal\" or \"\" based on CLI flag",
)

private val pathVariables = listOf(
    $$"${path:fill}" to "Fill brush/colour",
    $$"${path:fill_alpha}" to "Fill alpha (float)",
    $$"${path:fill_type}" to "Path fill type",
    $$"${path:stroke}" to "Stroke brush/colour",
    $$"${path:stroke_alpha}" to "Stroke alpha (float)",
    $$"${path:stroke_line_cap}" to "Stroke line cap",
    $$"${path:stroke_line_join}" to "Stroke line join",
    $$"${path:stroke_miter_limit}" to "Stroke miter limit",
    $$"${path:stroke_line_width}" to "Stroke line width",
)

private val groupVariables = listOf(
    $$"${group:rotate}" to "Rotation angle (float)",
    $$"${group:pivot_x}" to "Pivot X (float)",
    $$"${group:pivot_y}" to "Pivot Y (float)",
    $$"${group:scale_x}" to "Scale X (float)",
    $$"${group:scale_y}" to "Scale Y (float)",
    $$"${group:translation_x}" to "Translation X (float)",
    $$"${group:translation_y}" to "Translation Y (float)",
)

private val chunkVariables = listOf(
    $$"${chunk:index}" to "Chunk index (chunk_function_name only)",
    $$"${chunk:name}" to "Resolved chunk name (chunk_function_definition only)",
    $$"${chunk:body}" to "Chunk node code (chunk_function_definition only)",
)

@Composable
private fun VariablesByNamespaceSection() {
    val palette = ColorMode.current.toSitePalette()

    DocSection(id = "variables-by-namespace", title = "Variables by Namespace") {
        SpanText(
            text = "Icon Variables",
            modifier = DocsBodyTextStyle.toModifier()
                .fontWeight(FontWeight.SemiBold),
        )
        VariableTable(
            palette = palette,
            variables = iconVariables,
        )

        SpanText(
            text = "Path Variables",
            modifier = DocsBodyTextStyle.toModifier()
                .fontWeight(FontWeight.SemiBold),
        )
        VariableTable(
            palette = palette,
            variables = pathVariables,
        )

        SpanText(
            text = "Group Variables",
            modifier = DocsBodyTextStyle.toModifier()
                .fontWeight(FontWeight.SemiBold),
        )
        VariableTable(
            palette = palette,
            variables = groupVariables,
        )

        SpanText(
            text = "Chunk Variables",
            modifier = DocsBodyTextStyle.toModifier()
                .fontWeight(FontWeight.SemiBold),
        )
        VariableTable(
            palette = palette,
            variables = chunkVariables,
        )
    }
}

@Composable
private fun NullHandlingSection() {
    DocSection(id = "null-handling", title = "Null Handling & Line Trimming") {
        SpanText(
            text = "When a variable resolves to null, the entire line containing only that parameter " +
                "assignment is trimmed from the output. This keeps generated code clean without " +
                "requiring conditional logic in your template.",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        CodeAwareSpanText(
            text = $$"For example, if `${path:fill_alpha}` is null:",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        CodeBlock(
            code = $$"""
                |path(
                |    fill = SolidColor(Color.Black),
                |    fillAlpha = ${path:fill_alpha},    // <- this line is removed
                |    pathFillType = EvenOdd,
                |)
            """.trimMargin(),
            language = "kotlin",
            filename = "s2c.template.toml",
        )
    }
}

@Composable
private fun AutoDiscoverySection() {
    DocSection(id = "auto-discovery", title = "Auto-Discovery") {
        CodeAwareSpanText(
            text = "When no explicit template path is given, the tool walks up from the output " +
                "directory looking for `s2c.template.toml`. This matches the behaviour of " +
                "`.editorconfig` discovery.",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        DocCallout(variant = CalloutVariant.IMPORTANT) {
            CodeAwareSpanText(
                text = "Disable auto-discovery with `--no-template` (CLI) or by omitting " +
                    "`templateFile()` from the Gradle DSL.",
                modifier = DocsBodyTextStyle.toModifier(),
                variant = ImportantCalloutCodeAwareVariant,
            )
        }
        SpanText(
            text = "Precedence (highest to lowest):",
            modifier = DocsBodyTextStyle.toModifier()
                .fontWeight(FontWeight.SemiBold),
        )
        Ul(attrs = DocsBulletListStyle.toModifier().toAttrs()) {
            Li { Text("CLI flags / Gradle DSL explicit values") }
            Li { Text("Template file definitions") }
            Li { Text("Hardcoded defaults") }
        }
    }
}

@Composable
private fun ExamplesSection() {
    DocSection(id = "examples", title = "Examples") {
        SpanText(
            text = "Gradle plugin: per-configuration templates",
            modifier = DocsBodyTextStyle.toModifier()
                .fontWeight(FontWeight.SemiBold),
        )
        SpanText(
            text = "Each processor configuration can specify its own template file, allowing " +
                "different icon sets to use different output shapes:",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        CodeBlock(
            code = gradlePerConfigExample.trimMargin(),
            language = "kotlin",
            filename = "build.gradle.kts",
        )
        SpanText(
            text = "CLI usage:",
            modifier = DocsBodyTextStyle.toModifier()
                .fontWeight(FontWeight.SemiBold),
        )
        CodeBlock(
            code = "./s2c --template s2c.template.toml -p com.example -t MyTheme input.svg -o output/",
            language = "shell",
            filename = "Terminal",
        )
        DocCallout(variant = CalloutVariant.TIP) {
            Span(attrs = DocsBodyTextStyle.toAttrs()) {
                Text("See the ")
                Link(path = "/docs/cli", text = "CLI documentation", variant = SiteLinkStyleVariant)
                Text(" and ")
                Link(
                    path = "/docs/gradle-plugin",
                    text = "Gradle plugin documentation",
                    variant = SiteLinkStyleVariant,
                )
                Text(" for integration details.")
            }
        }
    }
}

@Composable
private fun TableHeader(text: String, palette: SitePalette, align: TextAlign) {
    Th(
        attrs = TemplateTableHeaderStyle
            .toModifier()
            .textAlign(align)
            .color(palette.onSurface)
            .attrsModifier { attr("scope", "col") }
            .toAttrs(),
    ) {
        Text(text)
    }
}

@Composable
private fun NamespaceRow(namespace: String, syntax: String, resolves: String, scope: String, index: Int) {
    val palette = ColorMode.current.toSitePalette()
    Tr(attrs = alternatingRowModifier(palette, index).toAttrs()) {
        Td(
            attrs = TemplateTableCellStyle
                .toModifier()
                .fontWeight(FontWeight.Medium)
                .color(palette.onSurface)
                .toAttrs(),
        ) {
            InlineCode(namespace)
        }
        Td(attrs = TemplateTableCellStyle.toModifier().color(palette.onSurface).toAttrs()) {
            InlineCode(syntax)
        }
        Td(attrs = TemplateTableCellStyle.toModifier().color(palette.onSurface).toAttrs()) {
            Text(resolves)
        }
        Td(attrs = TemplateTableCellStyle.toModifier().color(palette.onSurface).toAttrs()) {
            CodeAwareSpanText(scope)
        }
    }
}

@Composable
private fun VariableTable(palette: SitePalette, variables: List<Pair<String, String>>) {
    Table(attrs = TemplateTableStyle.toModifier().toAttrs()) {
        Thead {
            Tr {
                TableHeader(text = "Variable", palette = palette, align = TextAlign.Start)
                TableHeader(text = "Description", palette = palette, align = TextAlign.Start)
            }
        }
        Tbody {
            variables.forEachIndexed { index, (variable, description) ->
                VariableRow(variable = variable, description = description, index = index)
            }
        }
    }
}

@Composable
private fun VariableRow(variable: String, description: String, index: Int) {
    val palette = ColorMode.current.toSitePalette()
    Tr(attrs = alternatingRowModifier(palette, index).toAttrs()) {
        Td(
            attrs = TemplateTableCellStyle
                .toModifier()
                .fontWeight(FontWeight.Medium)
                .color(palette.onSurface)
                .toAttrs(),
        ) {
            InlineCode(variable)
        }
        Td(attrs = TemplateTableCellStyle.toModifier().color(palette.onSurface).toAttrs()) {
            Text(description)
        }
    }
}

@Composable
private fun FieldTable(headers: List<String>, rows: List<List<String>>) {
    val palette = ColorMode.current.toSitePalette()
    Table(attrs = TemplateTableStyle.toModifier().toAttrs()) {
        Thead {
            Tr {
                headers.forEach { header ->
                    TableHeader(text = header, palette = palette, align = TextAlign.Start)
                }
            }
        }
        Tbody {
            rows.forEachIndexed { index, row ->
                FieldTableRow(palette = palette, row = row, index = index)
            }
        }
    }
}

@Composable
private fun FieldTableRow(palette: SitePalette, row: List<String>, index: Int) {
    Tr(attrs = alternatingRowModifier(palette, index).toAttrs()) {
        row.forEachIndexed { cellIndex, cell ->
            FieldTableCell(palette = palette, cell = cell, isFirstColumn = cellIndex == 0)
        }
    }
}

@Composable
private fun FieldTableCell(palette: SitePalette, cell: String, isFirstColumn: Boolean) {
    Td(
        attrs = TemplateTableCellStyle
            .toModifier()
            .let { if (isFirstColumn) it.fontWeight(FontWeight.Medium) else it }
            .color(palette.onSurface)
            .toAttrs(),
    ) {
        if (isFirstColumn) {
            InlineCode(cell)
        } else {
            Text(cell)
        }
    }
}

private fun alternatingRowModifier(palette: SitePalette, index: Int): Modifier = if (index % 2 != 0) {
    Modifier.backgroundColor(palette.onBackground.toRgb().copyf(alpha = ALTERNATING_ROW_ALPHA))
} else {
    Modifier
}
