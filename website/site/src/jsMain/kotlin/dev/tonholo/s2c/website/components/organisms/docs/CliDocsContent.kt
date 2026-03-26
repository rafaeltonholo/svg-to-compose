package dev.tonholo.s2c.website.components.organisms.docs

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.attrsModifier
import com.varabyte.kobweb.compose.ui.modifiers.ariaHidden
import com.varabyte.kobweb.compose.ui.modifiers.ariaLabel
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.role
import com.varabyte.kobweb.compose.ui.modifiers.setVariable
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaCheck
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.components.atoms.DocSection
import dev.tonholo.s2c.website.components.atoms.InlineCode
import dev.tonholo.s2c.website.components.atoms.InlineCodeVars
import dev.tonholo.s2c.website.components.molecules.CalloutVariant
import dev.tonholo.s2c.website.components.molecules.CodeBlock
import dev.tonholo.s2c.website.components.molecules.DocCallout
import dev.tonholo.s2c.website.components.molecules.OptionRow
import dev.tonholo.s2c.website.components.molecules.OptionsHeaderRow
import dev.tonholo.s2c.website.theme.SitePalette
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.theme.toSitePalette
import org.jetbrains.compose.web.dom.Li
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Table
import org.jetbrains.compose.web.dom.Tbody
import org.jetbrains.compose.web.dom.Td
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.Th
import org.jetbrains.compose.web.dom.Thead
import org.jetbrains.compose.web.dom.Tr
import org.jetbrains.compose.web.dom.Ul

// language=sh
private const val S2C_CONVERT_SINGLE_FILE = """
|s2c \
|  -o Icon.kt \
|  -p com.app.icons \
|  -t com.app.theme.AppTheme \
|  icon.svg
"""

// language=sh
private const val S2C_CONVERT_DIRECTORY = """
|s2c \
|  -o ./output \
|  -p com.app.icons \
|  -t com.app.theme.AppTheme \
|  -r \
|  ./icons/
"""

// language=sh
private const val S2C_CONVERT_MATERIAL_ICON_RECEIVER = """
|s2c \
|  -o Icon.kt \
|  -p com.app.icons \
|  -t com.app.theme.AppTheme \
|  -rt "Icons.Filled" \
|  icon.svg
"""

// language=sh
private const val S2C_CONVERT_AVG = """
|s2c \
|  -o Icon.kt \
|  -p com.app.icons \
|  -t com.app.theme.AppTheme \
|  icon.xml
"""

// language=sh
private const val S2C_DISABLE_OPTIMIZATION = """
|s2c \
|  -o Icon.kt \
|  -p com.app.icons \
|  -t com.app.theme.AppTheme \
|  -opt false \
|  icon.svg
"""

private const val OUTPUT_EXAMPLE = """
|package com.app.icons
|
|import androidx.compose.ui.graphics.Color
|import androidx.compose.ui.graphics.SolidColor
|import androidx.compose.ui.graphics.vector.ImageVector
|import androidx.compose.ui.graphics.vector.path
|import androidx.compose.ui.unit.dp
|
|val MyIcon: ImageVector
|    get() {
|        val current = _myIcon
|        if (current != null) return current
|        return ImageVector.Builder(
|            name = "MyIcon",
|            defaultWidth = 24.dp,
|            defaultHeight = 24.dp,
|            viewportWidth = 24f,
|            viewportHeight = 24f,
|        ).apply {
|            path(fill = SolidColor(Color(0xFF000000))) {
|                moveTo(12f, 2f)
|                lineTo(22f, 12f)
|                lineTo(12f, 22f)
|                lineTo(2f, 12f)
|                close()
|            }
|        }.build().also { _myIcon = it }
|    }
|
|private var _myIcon: ImageVector? = null
"""

val CliDocsOptionsTableStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .backgroundColor(colorMode.toSitePalette().surfaceVariant)
}

@Composable
fun CliDocsContent(modifier: Modifier = Modifier) {
    Column(modifier = Modifier.fillMaxWidth().gap(SiteTheme.dimensions.size.Xxl).then(modifier)) {
        OverviewSection()
        PlatformSupportSection()
        InstallationSection()
        ExternalDependenciesSection()
        UsageSection()
        OptionsReferenceSection()
        TemplateSystemCallout()
        OutputExamplesSection()
    }
}

@Composable
private fun OverviewSection() {
    DocSection(id = "overview", title = "Overview") {
        SpanText(
            text = "A command-line tool to convert SVG or Android Vector Drawable (AVG) files into " +
                "Jetpack Compose ImageVector code. Built with Kotlin Native, it runs natively on macOS, " +
                "Linux, and Windows without requiring a JVM.",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        SpanText(
            text = "Key features:",
            modifier = DocsBodyTextStyle.toModifier()
                .fontWeight(FontWeight.SemiBold),
        )
        Ul(attrs = DocsBulletListStyle.toModifier().toAttrs()) {
            Li { Text("Converts SVG and Android Vector Drawables to ImageVector") }
            Li { Text("Optional SVG optimization via SVGO and Avocado") }
            Li { Text("Supports batch conversion of entire directories") }
            Li { Text("Material Icons receiver type support") }
            Li { Text("KMP-compatible output generation") }
        }
    }
}

@Composable
private fun PlatformSupportSection() {
    val palette = ColorMode.current.toSitePalette()

    DocSection(id = "platform-support", title = "Platform Support") {
        SpanText(
            text = "The CLI tool supports the following platforms:",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        Table(
            attrs = DocsTableStyle.toModifier()
                .toAttrs(),
        ) {
            Thead {
                Tr {
                    Th(
                        attrs = DocsTableHeaderStyle
                            .toModifier()
                            .textAlign(TextAlign.Start)
                            .color(palette.onSurface)
                            .attrsModifier { attr("scope", "col") }
                            .toAttrs(),
                    ) {
                        Text("Platform")
                    }
                    Th(
                        attrs = DocsTableHeaderStyle.toModifier()
                            .color(palette.onSurface)
                            .attrsModifier { attr("scope", "col") }
                            .toAttrs(),
                    ) {
                        Text("With optimization")
                    }
                    Th(
                        attrs = DocsTableHeaderStyle.toModifier()
                            .color(palette.onSurface)
                            .attrsModifier { attr("scope", "col") }
                            .toAttrs(),
                    ) {
                        Text("Without optimization")
                    }
                }
            }
            Tbody {
                PlatformRow(platform = "macOS Arm64", index = 0)
                PlatformRow(platform = "macOS x64", index = 1)
                PlatformRow(platform = "Linux x64", index = 2)
                PlatformRow(platform = "Windows (mingwX64)", index = 3)
                PlatformRow(platform = "Windows (WSL)", index = 4)
            }
        }
    }
}

@Composable
private fun PlatformRow(platform: String, index: Int) {
    val palette = ColorMode.current.toSitePalette()
    val backgroundModifier = if (index % 2 != 0) {
        Modifier.backgroundColor(palette.onBackground.toRgb().copyf(alpha = 0.06f))
    } else {
        Modifier
    }
    Tr(attrs = backgroundModifier.toAttrs()) {
        Td(
            attrs = DocsTableCellStyle
                .toModifier()
                .fontWeight(FontWeight.Medium)
                .color(palette.onSurface)
                .toAttrs(),
        ) {
            Text(platform)
        }
        SupportedCell(palette)
        SupportedCell(palette)
    }
}

@Composable
private fun SupportedCell(palette: SitePalette) {
    Td(
        attrs = DocsTableCellStyle.toModifier()
            .ariaLabel("Supported")
            .textAlign(TextAlign.Center)
            .toAttrs(),
    ) {
        FaCheck(
            modifier = Modifier
                .color(palette.primary)
                .ariaHidden(),
        )
    }
}

@Composable
private fun InstallationSection() {
    DocSection(id = "installation", title = "Installation") {
        Span(attrs = DocsBodyTextStyle.toAttrs()) {
            Text("1. Download the latest release binary for your platform from the ")
            Link(
                path = "https://github.com/rafaeltonholo/svg-to-compose/releases",
                text = "GitHub releases page",
            )
            Text(", then give it execution permission:")
        }
        CodeBlock(
            code = "chmod +x s2c",
            language = "console",
        )
        SpanText(
            text = "2. Add the binary to your PATH:",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        CodeBlock(
            code = $$"export PATH=\"/path/to/s2c:$PATH\"",
            language = "shell",
        )
    }
}

@Composable
private fun ExternalDependenciesSection() {
    DocSection(id = "external-dependencies", title = "External Dependencies") {
        SpanText(
            text = "When optimization is enabled (the default), the CLI uses SVGO for SVG optimization " +
                "and Avocado for Android Vector Drawable optimization. Both require Node.js.",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        DocCallout(variant = CalloutVariant.IMPORTANT) {
            val (borderColor, containerColor) = CalloutVariant.IMPORTANT.resolveInlineCodeColors()
            Span(attrs = DocsBodyTextStyle.toAttrs()) {
                Text(
                    "If you do not have Node.js installed or do not need optimization, " +
                        "you can disable it with the ",
                )
                InlineCode(
                    code = "-opt false",
                    modifier = Modifier
                        .setVariable(InlineCodeVars.ContainerColor, containerColor)
                        .setVariable(InlineCodeVars.BorderColor, borderColor),
                )
                Text(" flag.")
            }
        }
        SpanText(
            text = "Install the optimization tools globally:",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        CodeBlock(
            code = "npm -g install svgo\nnpm -g install avocado",
            language = "console",
        )
    }
}

@Composable
private fun UsageSection() {
    DocSection(id = "usage", title = "Usage Examples") {
        SpanText(
            text = "Convert a single SVG file:",
            modifier = DocsBodyTextStyle.toModifier()
                .fontWeight(FontWeight.SemiBold),
        )
        CodeBlock(
            code = S2C_CONVERT_SINGLE_FILE.trimMargin(),
            language = "shell",
        )
        SpanText(
            text = "Batch convert a directory recursively:",
            modifier = DocsBodyTextStyle.toModifier()
                .fontWeight(FontWeight.SemiBold),
        )
        CodeBlock(
            code = S2C_CONVERT_DIRECTORY.trimMargin(),
            language = "shell",
        )
        SpanText(
            text = "Generate with a Material Icons receiver type:",
            modifier = DocsBodyTextStyle.toModifier()
                .fontWeight(FontWeight.SemiBold),
        )
        CodeBlock(
            code = S2C_CONVERT_MATERIAL_ICON_RECEIVER.trimMargin(),
            language = "shell",
        )
        SpanText(
            text = "Convert an Android Vector Drawable (AVG/XML):",
            modifier = DocsBodyTextStyle.toModifier()
                .fontWeight(FontWeight.SemiBold),
        )
        CodeBlock(
            code = S2C_CONVERT_AVG.trimMargin(),
            language = "shell",
        )
        SpanText(
            text = "Disable optimization:",
            modifier = DocsBodyTextStyle.toModifier()
                .fontWeight(FontWeight.SemiBold),
        )
        CodeBlock(
            code = S2C_DISABLE_OPTIMIZATION.trimMargin(),
            language = "shell",
        )
    }
}

@Composable
private fun OptionsReferenceSection() {
    DocSection(id = "options", title = "All Options Reference") {
        SpanText(
            text = "The following table lists all available CLI flags:",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        Column(
            modifier = CliDocsOptionsTableStyle.toModifier()
                .role("table")
                .attrsModifier {
                    attr("aria-label", "CLI options reference")
                },
        ) {
            OptionsHeaderRow()
            CliOption.options.forEachIndexed { index, option ->
                OptionRow(
                    flag = option.flag,
                    type = option.type,
                    description = option.description,
                    index = index,
                )
            }
        }
    }
}

@Composable
private fun TemplateSystemCallout() {
    DocCallout(variant = CalloutVariant.TIP) {
        val (borderColor, containerColor) = CalloutVariant.TIP.resolveInlineCodeColors()
        Span(attrs = DocsBodyTextStyle.toAttrs()) {
            Text("You can customize the generated code using the template system. Use ")
            InlineCode(
                code = "--template=<path>",
                modifier = Modifier
                    .setVariable(InlineCodeVars.ContainerColor, containerColor)
                    .setVariable(InlineCodeVars.BorderColor, borderColor),
            )
            Text(" to specify a template file, or let the CLI auto-discover ")
            InlineCode(
                code = "s2c.template.toml",
                modifier = Modifier
                    .setVariable(InlineCodeVars.ContainerColor, containerColor)
                    .setVariable(InlineCodeVars.BorderColor, borderColor),
            )
            Text(" from your project. See the ")
            Link(path = "/docs/templates", text = "Template System documentation")
            Text(" for the full schema reference.")
        }
    }
}

@Composable
private fun OutputExamplesSection() {
    DocSection(id = "output-examples", title = "Output Examples") {
        SpanText(
            text = "The CLI generates Kotlin files containing ImageVector builders. " +
                "Each icon is exposed as a lazily-initialized property with a backing field for caching.",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        DocCallout(variant = CalloutVariant.TIP) {
            val (borderColor, containerColor) = CalloutVariant.TIP.resolveInlineCodeColors()
            Span(attrs = DocsBodyTextStyle.toAttrs()) {
                Text("The generated code uses the same ")
                InlineCode(
                    code = "ImageVector.Builder",
                    modifier = Modifier
                        .setVariable(InlineCodeVars.ContainerColor, containerColor)
                        .setVariable(InlineCodeVars.BorderColor, borderColor),
                )
                Text(" API that Jetpack Compose uses internally, ensuring full compatibility.")
            }
        }
        CodeBlock(
            code = OUTPUT_EXAMPLE.trimMargin(),
            language = "kotlin",
            filename = "MyIcon.kt",
        )
    }
}
