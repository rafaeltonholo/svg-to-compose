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
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.components.atoms.DocSection
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.theme.toSitePalette
import org.jetbrains.compose.web.dom.Li
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
        SpanText(
            text = "There are several ways to get Compose ImageVector code from vector graphics. " +
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
        SpanText(
            text = "Writing ImageVector.Builder code by hand works for simple icons but becomes " +
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
            text = "Android Studio's built-in Vector Asset tool can import SVG files. However, it " +
                "only targets Android (no Kotlin Multiplatform support), has no CLI for CI/CD " +
                "pipelines, no batch processing, and limited SVG feature support. It converts to " +
                "Android Vector Drawable XML, not directly to Compose ImageVector code.",
            modifier = DocsBodyTextStyle.toModifier(),
        )
    }
}

@Composable
private fun CommunityToolsSection() {
    DocSection(id = "community-tools", title = "Community Tools") {
        SpanText(
            text = "Other open-source tools exist for SVG-to-Compose conversion. SVG to Compose " +
                "differentiates with:",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        Ul(attrs = DocsBulletListStyle.toModifier().toAttrs()) {
            Li { Text("Broader Kotlin Multiplatform target support (6 platforms)") }
            Li { Text("Both CLI and Gradle plugin interfaces") }
            Li { Text("SVG optimization integration via SVGO and Avocado") }
            Li { Text("Active maintenance") }
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
    ComparisonRow("KMP Support", "Yes (6 targets)", "Yes", "Android only", "Varies"),
    ComparisonRow("CLI Tool", "Yes", "N/A", "No", "Varies"),
    ComparisonRow("Gradle Plugin", "Yes", "N/A", "No", "Rare"),
    ComparisonRow("Batch Processing", "Yes", "Manual", "No", "Varies"),
    ComparisonRow("SVG Optimization", "Yes (SVGO)", "Manual", "Limited", "Varies"),
    ComparisonRow("Incremental Builds", "Yes", "N/A", "N/A", "Rare"),
    ComparisonRow("Complex SVG Support", "High", "Depends on skill", "Moderate", "Varies"),
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
            attrs = PlatformTableStyle.toModifier()
                .attrsModifier { attr("aria-label", "Feature comparison table") }
                .toAttrs(),
        ) {
            Thead {
                Tr {
                    Th(
                        attrs = PlatformTableHeaderStyle
                            .toModifier()
                            .textAlign(TextAlign.Start)
                            .color(palette.onSurface)
                            .attrsModifier { attr("scope", "col") }
                            .toAttrs(),
                    ) {
                        Text("Feature")
                    }
                    Th(
                        attrs = PlatformTableHeaderStyle
                            .toModifier()
                            .color(palette.onSurface)
                            .attrsModifier { attr("scope", "col") }
                            .toAttrs(),
                    ) {
                        Text("SVG to Compose")
                    }
                    Th(
                        attrs = PlatformTableHeaderStyle
                            .toModifier()
                            .color(palette.onSurface)
                            .attrsModifier { attr("scope", "col") }
                            .toAttrs(),
                    ) {
                        Text("Manual Coding")
                    }
                    Th(
                        attrs = PlatformTableHeaderStyle
                            .toModifier()
                            .color(palette.onSurface)
                            .attrsModifier { attr("scope", "col") }
                            .toAttrs(),
                    ) {
                        Text("Android Studio")
                    }
                    Th(
                        attrs = PlatformTableHeaderStyle
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
                    ComparisonTableRow(row = row, index = index)
                }
            }
        }
    }
}

@Composable
private fun ComparisonTableRow(row: ComparisonRow, index: Int) {
    val palette = ColorMode.current.toSitePalette()
    val backgroundModifier = if (index % 2 != 0) {
        Modifier.backgroundColor(palette.onBackground.toRgb().copyf(alpha = 0.06f))
    } else {
        Modifier
    }
    Tr(attrs = backgroundModifier.toAttrs()) {
        Td(
            attrs = PlatformTableCellStyle
                .toModifier()
                .fontWeight(FontWeight.Medium)
                .color(palette.onSurface)
                .toAttrs(),
        ) {
            Text(row.feature)
        }
        Td(
            attrs = PlatformTableCellStyle
                .toModifier()
                .textAlign(TextAlign.Center)
                .color(palette.onSurface)
                .toAttrs(),
        ) {
            Text(row.svgToCompose)
        }
        Td(
            attrs = PlatformTableCellStyle
                .toModifier()
                .textAlign(TextAlign.Center)
                .color(palette.onSurface)
                .toAttrs(),
        ) {
            Text(row.manualCoding)
        }
        Td(
            attrs = PlatformTableCellStyle
                .toModifier()
                .textAlign(TextAlign.Center)
                .color(palette.onSurface)
                .toAttrs(),
        ) {
            Text(row.androidStudio)
        }
        Td(
            attrs = PlatformTableCellStyle
                .toModifier()
                .textAlign(TextAlign.Center)
                .color(palette.onSurface)
                .toAttrs(),
        ) {
            Text(row.otherTools)
        }
    }
}
