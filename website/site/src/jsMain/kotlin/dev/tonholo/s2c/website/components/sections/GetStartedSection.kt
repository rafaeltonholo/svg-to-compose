package dev.tonholo.s2c.website.components.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.HeadlineTextStyle
import dev.tonholo.s2c.website.SubheadlineTextStyle
import dev.tonholo.s2c.website.components.widgets.Badge
import dev.tonholo.s2c.website.components.widgets.CodeBlock
import dev.tonholo.s2c.website.components.widgets.SectionContainer
import dev.tonholo.s2c.website.components.widgets.TabPanel
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div

val SubHeadingStyle = CssStyle.base {
    Modifier
        .fontSize(1.1.cssRem)
        .fontWeight(FontWeight.SemiBold)
        .margin(top = 1.5.cssRem, bottom = 0.75.cssRem)
}

val OptimizerCardsGridStyle = CssStyle {
    base {
        Modifier
            .display(DisplayStyle.Grid)
            .gap(1.cssRem)
            .fillMaxWidth()
            .styleModifier { property("grid-template-columns", "1fr") }
    }
    Breakpoint.MD {
        Modifier.styleModifier { property("grid-template-columns", "repeat(2, 1fr)") }
    }
}

val OptimizerCardStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .backgroundColor(palette.surface)
        .border(1.px, LineStyle.Solid, palette.border)
        .borderRadius(0.75.cssRem)
        .padding(1.cssRem)
}

val PlatformGridStyle = CssStyle {
    base {
        Modifier
            .display(DisplayStyle.Grid)
            .gap(0.5.cssRem)
            .fillMaxWidth()
            .styleModifier { property("grid-template-columns", "repeat(2, 1fr)") }
    }
    Breakpoint.SM {
        Modifier.styleModifier { property("grid-template-columns", "repeat(3, 1fr)") }
    }
    Breakpoint.LG {
        Modifier.styleModifier { property("grid-template-columns", "repeat(5, 1fr)") }
    }
}

val PlatformItemStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .backgroundColor(palette.surfaceAlt)
        .border(1.px, LineStyle.Solid, palette.border)
        .borderRadius(0.5.cssRem)
        .padding(0.75.cssRem)
        .textAlign(TextAlign.Center)
        .fontSize(0.8.cssRem)
}

val InfoCardStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .backgroundColor(palette.surfaceAlt)
        .border(
            width = 1.px,
            style = LineStyle.Solid,
            color = palette.brand.teal.toRgb().copyf(alpha = 0.2f),
        )
        .borderRadius(0.75.cssRem)
        .padding(1.cssRem)
        .fontSize(0.9.cssRem)
        .lineHeight(1.6)
        .color(palette.muted)
}

val TabContentStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .margin(top = 1.5.cssRem)
}

@Composable
fun GetStartedSection() {
    val palette = ColorMode.current.toSitePalette()

    SectionContainer(id = "install", altBackground = true) {
        Badge(
            text = "Quick Start",
            color = palette.brand.teal,
        )
        SpanText(
            "Get Started in Minutes",
            modifier = HeadlineTextStyle.toModifier()
                .margin(top = 1.cssRem),
        )
        SpanText(
            "Choose your preferred installation method and start converting SVG to Compose today.",
            modifier = SubheadlineTextStyle.toModifier()
                .margin(top = 0.5.cssRem, bottom = 2.cssRem),
        )

        var selectedTab by remember { mutableStateOf(0) }
        TabPanel(
            tabs = listOf("CLI", "Gradle Plugin"),
            selectedIndex = selectedTab,
            onSelect = { selectedTab = it },
        ) {
            Div(attrs = TabContentStyle.toModifier().toAttrs()) {
                when (selectedTab) {
                    0 -> CliTabContent()
                    1 -> GradlePluginTabContent()
                }
            }
        }
    }
}

@Composable
private fun CliTabContent() {
    SpanText(
        "Installation",
        modifier = SubHeadingStyle.toModifier(),
    )
    CodeBlock(
        code = """curl -o s2c https://raw.githubusercontent.com/rafaeltonholo/svg-to-compose/main/s2c
chmod +x s2c
./s2c --help""",
        language = "shell",
        filename = "Terminal",
    )

    SpanText(
        "Optional Optimizers",
        modifier = SubHeadingStyle.toModifier(),
    )
    Div(attrs = OptimizerCardStyle.toModifier().margin(bottom = 0.5.cssRem).toAttrs()) {
        SpanText(
            "For ",
            modifier = Modifier.fontSize(0.9.cssRem),
        )
        SpanText(
            "SVG optimization",
            modifier = Modifier
                .fontSize(0.9.cssRem)
                .fontWeight(FontWeight.Bold)
                .color(ColorMode.current.toSitePalette().brand.purple),
        )
        SpanText(
            ", install SVGO:",
            modifier = Modifier.fontSize(0.9.cssRem),
        )
        CodeBlock(code = "npm -g install svgo", language = "shell")
    }
    Div(attrs = OptimizerCardStyle.toModifier().toAttrs()) {
        SpanText(
            "For ",
            modifier = Modifier.fontSize(0.9.cssRem),
        )
        SpanText(
            "AVG optimization",
            modifier = Modifier
                .fontSize(0.9.cssRem)
                .fontWeight(FontWeight.Bold)
                .color(ColorMode.current.toSitePalette().brand.purple),
        )
        SpanText(
            ", install avocado:",
            modifier = Modifier.fontSize(0.9.cssRem),
        )
        CodeBlock(code = "npm -g install avocado", language = "shell")
    }

    SpanText(
        "Supported Platforms",
        modifier = SubHeadingStyle.toModifier(),
    )
    Div(attrs = PlatformGridStyle.toModifier().toAttrs()) {
        data class PlatformInfo(val icon: String, val name: String, val subtitle: String)
        val platforms = listOf(
            PlatformInfo("\uD83C\uDF4E", "macOS ARM64", "Apple Silicon"),
            PlatformInfo("\uD83C\uDF4E", "macOS x64", "Intel"),
            PlatformInfo("\uD83D\uDC27", "Linux x64", "Ubuntu / Debian"),
            PlatformInfo("\uD83E\uDE9F", "Windows x64", "Native"),
            PlatformInfo("\uD83E\uDE9F", "Windows WSL", "WSL2"),
        )
        val palette = ColorMode.current.toSitePalette()
        platforms.forEach { platform ->
            Div(
                attrs = PlatformItemStyle.toModifier()
                    .display(DisplayStyle.Flex)
                    .styleModifier {
                        property("flex-direction", "column")
                        property("align-items", "center")
                    }
                    .gap(0.25.cssRem)
                    .padding(topBottom = 1.cssRem, leftRight = 0.75.cssRem)
                    .toAttrs()
            ) {
                SpanText(
                    platform.icon,
                    modifier = Modifier.fontSize(1.5.cssRem),
                )
                SpanText(
                    platform.name,
                    modifier = Modifier.fontWeight(FontWeight.Medium).fontSize(0.85.cssRem),
                )
                SpanText(
                    platform.subtitle,
                    modifier = Modifier.fontSize(0.7.cssRem).color(palette.muted),
                )
            }
        }
    }
}

@Composable
private fun GradlePluginTabContent() {
    SpanText(
        "Step 1 — Add the plugin",
        modifier = SubHeadingStyle.toModifier(),
    )
    CodeBlock(
        code = """plugins {
    id("dev.tonholo.s2c") version "2.1.2"
}""",
        language = "kotlin",
        filename = "build.gradle.kts",
    )

    SpanText(
        "Step 2 — Configure conversion processors",
        modifier = SubHeadingStyle.toModifier(),
    )
    CodeBlock(
        code = """svgToCompose {
    processor {
        val icons by creating {
            from(layout.projectDirectory.dir(
                "src/main/resources/icons"
            ))
            destinationPackage(
                "com.example.app.ui.icons"
            )
            optimize(true)
            icons {
                theme(
                    "com.example.app.ui.theme.AppTheme"
                )
            }
        }
    }
}""",
        language = "kotlin",
        filename = "build.gradle.kts",
    )

    Div(attrs = InfoCardStyle.toModifier().margin(top = 1.cssRem).toAttrs()) {
        SpanText(
            "Zero configuration required. The Gradle plugin integrates into the standard build lifecycle. " +
                "Icons are converted automatically during build, with incremental processing and parallel execution support.",
        )
    }

    SpanText(
        "Then run the conversion task:",
        modifier = SubHeadingStyle.toModifier(),
    )
    CodeBlock(
        code = "./gradlew svgToCompose",
        language = "shell",
        filename = "Terminal",
    )
}
