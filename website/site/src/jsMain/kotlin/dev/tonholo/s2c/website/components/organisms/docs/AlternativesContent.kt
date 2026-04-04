package dev.tonholo.s2c.website.components.organisms.docs

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.attrsModifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.components.atoms.DocSection
import dev.tonholo.s2c.website.components.molecules.CodeAwareSpanText
import dev.tonholo.s2c.website.theme.SitePalette
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.theme.common.SiteLinkStyleVariant
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

@Composable
fun AlternativesContent(modifier: Modifier = Modifier) {
    Column(modifier = Modifier.fillMaxWidth().gap(SiteTheme.dimensions.size.Xxl).then(modifier)) {
        OverviewSection()
        ManualConversionSection()
        AndroidStudioImportSection()
        CommunityToolsSection()
        ComparisonTableSection()
    }
}

@Composable
private fun OverviewSection() {
    DocSection(id = "overview", title = "Overview") {
        CodeAwareSpanText(
            text = "There are several ways to get Compose `ImageVector` code from vector graphics. " +
                "Each approach has trade-offs in automation, platform support, and feature coverage. " +
                "SVG to Compose aims to provide the most comprehensive solution for Kotlin " +
                "Multiplatform projects.",
            modifier = DocsBodyTextStyle.toModifier(),
        )
    }
}

@Composable
private fun ManualConversionSection() {
    DocSection(id = "manual-conversion", title = "Manual Conversion") {
        CodeAwareSpanText(
            text = "Writing `ImageVector.Builder` code by hand works for simple icons but becomes " +
                "tedious and error-prone for complex vectors with many path commands. There is no " +
                "optimization, no automation, and no batch processing. Manual coding is suitable " +
                "only for very simple icons with fewer than 5 path commands.",
            modifier = DocsBodyTextStyle.toModifier(),
        )
    }
}

@Composable
private fun AndroidStudioImportSection() {
    DocSection(id = "android-studio-import", title = "Android Studio Import") {
        SpanText(
            text = "Android Studio's built-in Vector Asset tool can import SVG files. While it " +
                "technically works with Compose Multiplatform, it requires creating the drawable " +
                "inside the Android target and then manually moving it to composeResources, " +
                "which is impractical for larger icon sets.",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        SpanText(
            text = "The Android Studio import also has no CLI for CI/CD pipelines, no batch " +
                "processing, and limited SVG feature support. It converts to Android Vector " +
                "Drawable XML, not directly to Compose ImageVector code.",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        CodeAwareSpanText(
            text = "A key limitation is that Android Vector Drawables only support a subset of " +
                "SVG features. Most community tools rely on the same Android Studio import " +
                "algorithm, inheriting these limitations. SVG to Compose uses its own parsing " +
                "algorithm, supporting almost all SVG features that the `ImageVector` API can " +
                "represent - including features like `stroke-dasharray` that other tools cannot handle.",
            modifier = DocsBodyTextStyle.toModifier(),
        )
    }
}

@Composable
private fun CommunityToolsSection() {
    DocSection(id = "community-tools", title = "Community Tools") {
        SpanText(
            text = "Other open-source tools exist for SVG-to-Compose conversion. Most of them " +
                "rely on the same Android Studio import algorithm, which limits them to the " +
                "subset of SVG features that Android Vector Drawables support. " +
                "SVG to Compose differentiates with:",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        Ul(attrs = DocsBulletListStyle.toModifier().toAttrs()) {
            Li { Text("Custom parsing algorithm with broader SVG feature coverage") }
            Li { Text("Kotlin Multiplatform support across JVM, JS, WASM, macOS, Linux, and Windows") }
            Li { Text("Both CLI and Gradle plugin interfaces") }
            Li { Text("SVG optimization integration via SVGO and Avocado") }
            Li { Text("Active maintenance") }
        }
        Span(attrs = DocsBodyTextStyle.toAttrs()) {
            Text("Learn more about the ")
            Link(path = "/docs/cli", variant = SiteLinkStyleVariant) { Text("CLI tool") }
            Text(" and ")
            Link(path = "/docs/gradle-plugin", variant = SiteLinkStyleVariant) { Text("Gradle plugin") }
            Text(", or check the ")
            Link(path = "/docs/faq", variant = SiteLinkStyleVariant) { Text("FAQ") }
            Text(" for common questions.")
        }
    }
}

private data class ComparisonRow(
    val feature: String,
    val svgToCompose: String,
    val manualCoding: String,
    val androidStudio: String,
    val otherTools: String,
)

private val comparisonRows = listOf(
    ComparisonRow("KMP Support", "Yes", "Yes", "Android only", "Varies"),
    ComparisonRow("CLI Tool", "Yes", "N/A", "No", "Varies"),
    ComparisonRow("Gradle Plugin", "Yes", "N/A", "No", "Rare"),
    ComparisonRow("Batch Processing", "Yes", "Manual", "No", "Varies"),
    ComparisonRow("SVG Optimization", "Yes (SVGO)", "Manual", "Limited", "Varies"),
    ComparisonRow("Incremental Builds", "Yes", "N/A", "N/A", "Rare"),
    ComparisonRow(
        "SVG Feature Coverage",
        "High (custom parser)",
        "Depends on skill",
        "Limited (SVG subset)",
        "Limited (SVG subset)",
    ),
)

@Composable
private fun ComparisonTableSection() {
    val palette = ColorMode.current.toSitePalette()

    DocSection(id = "comparison-table", title = "Comparison Table") {
        SpanText(
            text = "The following table compares SVG to Compose against common alternatives across " +
                "key features:",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        Table(
            attrs = DocsTableStyle.toModifier()
                .attrsModifier { attr("aria-label", "Feature comparison table") }
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
                        Text("Feature")
                    }
                    Th(
                        attrs = DocsTableHeaderStyle
                            .toModifier()
                            .color(palette.onSurface)
                            .attrsModifier { attr("scope", "col") }
                            .toAttrs(),
                    ) {
                        Text("SVG to Compose")
                    }
                    Th(
                        attrs = DocsTableHeaderStyle
                            .toModifier()
                            .color(palette.onSurface)
                            .attrsModifier { attr("scope", "col") }
                            .toAttrs(),
                    ) {
                        Text("Manual Coding")
                    }
                    Th(
                        attrs = DocsTableHeaderStyle
                            .toModifier()
                            .color(palette.onSurface)
                            .attrsModifier { attr("scope", "col") }
                            .toAttrs(),
                    ) {
                        Text("Android Studio")
                    }
                    Th(
                        attrs = DocsTableHeaderStyle
                            .toModifier()
                            .color(palette.onSurface)
                            .attrsModifier { attr("scope", "col") }
                            .toAttrs(),
                    ) {
                        Text("Other Tools")
                    }
                }
            }
            Tbody {
                comparisonRows.forEachIndexed { index, row ->
                    ComparisonTableRow(row = row, index = index, palette = palette)
                }
            }
        }
    }
}

@Composable
private fun ComparisonTableRow(row: ComparisonRow, index: Int, palette: SitePalette) {
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
            Text(row.feature)
        }
        Td(
            attrs = DocsTableCellStyle
                .toModifier()
                .textAlign(TextAlign.Center)
                .color(palette.onSurface)
                .toAttrs(),
        ) {
            Text(row.svgToCompose)
        }
        Td(
            attrs = DocsTableCellStyle
                .toModifier()
                .textAlign(TextAlign.Center)
                .color(palette.onSurface)
                .toAttrs(),
        ) {
            Text(row.manualCoding)
        }
        Td(
            attrs = DocsTableCellStyle
                .toModifier()
                .textAlign(TextAlign.Center)
                .color(palette.onSurface)
                .toAttrs(),
        ) {
            Text(row.androidStudio)
        }
        Td(
            attrs = DocsTableCellStyle
                .toModifier()
                .textAlign(TextAlign.Center)
                .color(palette.onSurface)
                .toAttrs(),
        ) {
            Text(row.otherTools)
        }
    }
}
