package dev.tonholo.s2c.website.components.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
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
import dev.tonholo.s2c.website.components.widgets.CollapsibleSection
import dev.tonholo.s2c.website.components.widgets.SectionContainer
import dev.tonholo.s2c.website.components.widgets.TabPanel
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div

val UsageCardGridStyle = CssStyle {
    base {
        Modifier
            .display(DisplayStyle.Grid)
            .gap(1.cssRem)
            .fillMaxWidth()
            .styleModifier { property("grid-template-columns", "1fr") }
    }
    Breakpoint.MD {
        Modifier.styleModifier { property("grid-template-columns", "repeat(3, 1fr)") }
    }
}

val UsageCardStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .backgroundColor(palette.surfaceAlt)
        .border(1.px, LineStyle.Solid, palette.borderStrong)
        .borderRadius(0.75.cssRem)
        .padding(1.cssRem)
        .display(DisplayStyle.Flex)
        .styleModifier { property("flex-direction", "column") }
        .gap(0.75.cssRem)
}

val UsageTabContentStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .margin(top = 1.5.cssRem)
}

val OptionsTableStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .padding(1.cssRem)
        .display(DisplayStyle.Flex)
        .styleModifier { property("flex-direction", "column") }
        .gap(0.5.cssRem)
}

val OptionsRowStyle = CssStyle {
    base {
        val palette = colorMode.toSitePalette()
        Modifier
            .display(DisplayStyle.Grid)
            .styleModifier { property("grid-template-columns", "1fr") }
            .gap(0.5.cssRem)
            .padding(0.75.cssRem)
            .borderRadius(0.375.cssRem)
            .backgroundColor(palette.surfaceAlt)
            .fontSize(0.85.cssRem)
            .lineHeight(1.5)
    }
    Breakpoint.MD {
        Modifier.styleModifier { property("grid-template-columns", "12rem 5rem 1fr") }
    }
}

@Composable
fun HowToUseSection() {
    SectionContainer(id = "usage") {
        Badge(
            text = "Examples",
            color = Color.rgb(0x00D4AA),
        )
        SpanText(
            "How to Use",
            modifier = HeadlineTextStyle.toModifier()
                .margin(top = 1.cssRem),
        )
        SpanText(
            "From simple single-file conversions to fully automated Gradle builds.",
            modifier = SubheadlineTextStyle.toModifier()
                .margin(top = 0.5.cssRem, bottom = 2.cssRem),
        )

        var selectedTab by remember { mutableStateOf(0) }
        TabPanel(
            tabs = listOf("CLI", "Gradle Plugin"),
            selectedIndex = selectedTab,
            onSelect = { selectedTab = it },
        ) {
            Div(attrs = UsageTabContentStyle.toModifier().toAttrs()) {
                when (selectedTab) {
                    0 -> CliUsageContent()
                    1 -> GradleUsageContent()
                }
            }
        }
    }
}

@Composable
private fun UsageCard(title: String, description: String, content: @Composable () -> Unit) {
    Div(attrs = UsageCardStyle.toModifier().toAttrs()) {
        SpanText(
            title,
            modifier = Modifier
                .fontSize(1.cssRem)
                .fontWeight(FontWeight.SemiBold),
        )
        SpanText(
            description,
            modifier = Modifier
                .fontSize(0.875.cssRem)
                .color(ColorMode.current.toSitePalette().muted),
        )
        content()
    }
}

@Composable
private fun CliUsageContent() {
    Div(attrs = UsageCardGridStyle.toModifier().toAttrs()) {
        UsageCard(
            title = "\uD83D\uDCC4 Convert a Single File",
            description = "Convert a single SVG file to a Kotlin ImageVector.",
        ) {
            CodeBlock(
                code = """s2c \
  -o Icon.kt \
  -p com.app.icons \
  -t com.app.theme.AppTheme \
  icon.svg""",
                language = "shell",
            )
        }
        UsageCard(
            title = "\uD83D\uDCC1 Batch Convert a Directory",
            description = "Recursively convert all SVG files in a directory.",
        ) {
            CodeBlock(
                code = """s2c \
  -o ./output \
  -p com.app.icons \
  -t com.app.theme.AppTheme \
  ./icons/ -r""",
                language = "shell",
            )
        }
        UsageCard(
            title = "\uD83C\uDFA8 Material Icons Receiver",
            description = "Generate icons with a Material Icons receiver type.",
        ) {
            CodeBlock(
                code = """s2c \
  -o Icon.kt \
  -p com.app.icons \
  -t com.app.theme.AppTheme \
  -rt "Icons.Filled" \
  icon.svg""",
                language = "shell",
            )
        }
    }

    Div(attrs = Modifier.margin(top = 1.5.cssRem).fillMaxWidth().toAttrs()) {
        CollapsibleSection(title = "All CLI Options Reference") {
            Div(attrs = OptionsTableStyle.toModifier().toAttrs()) {
                OptionRow("-o, --output", "String", "Output file or directory path")
                OptionRow("-p, --package", "String", "Kotlin package name for generated code")
                OptionRow("-t, --theme", "String", "Fully-qualified theme class name")
                OptionRow("--optimize", "Boolean", "Enable SVGO/Avocado optimization")
                OptionRow("-rt, --receiver-type", "String", "Receiver type (e.g. Icons.Filled)")
                OptionRow("--add-to-material", "Boolean", "Add as extension to Material Icons")
                OptionRow("--no-preview", "Boolean", "Skip generating @Preview composables")
                OptionRow("--kmp", "Boolean", "Generate KMP-compatible @Preview code")
                OptionRow("--make-internal", "Boolean", "Mark generated symbols as internal")
                OptionRow("--minified", "Boolean", "Generate minified output code")
                OptionRow("-r, --recursive", "Boolean", "Recursively process sub-directories")
                OptionRow("--exclude", "String...", "Glob patterns to exclude from processing")
                OptionRow("--map-icon-name-from-to", "Pair...", "Rename icon name patterns")
            }
        }
    }
}

@Composable
private fun OptionRow(flag: String, type: String, description: String) {
    val palette = ColorMode.current.toSitePalette()
    Div(attrs = OptionsRowStyle.toModifier().toAttrs()) {
        SpanText(
            flag,
            modifier = Modifier
                .fontFamily("JetBrains Mono", "monospace")
                .fontWeight(FontWeight.Medium)
                .color(palette.brand.purple),
        )
        SpanText(
            type,
            modifier = Modifier
                .fontFamily("JetBrains Mono", "monospace")
                .color(palette.muted),
        )
        SpanText(description)
    }
}

@Composable
private fun GradleUsageContent() {
    Div(attrs = UsageCardGridStyle.toModifier().toAttrs()) {
        UsageCard(
            title = "Basic Setup",
            description = "Minimal configuration to get started.",
        ) {
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
        }
    }
}""",
                language = "kotlin",
            )
        }
        UsageCard(
            title = "Multi-source Processing",
            description = "Process multiple directories with different configurations.",
        ) {
            CodeBlock(
                code = """svgToCompose {
    processor {
        val filled by creating {
            from(layout.projectDirectory.dir(
                "assets/icons/filled"
            ))
            destinationPackage(
                "com.example.icons.filled"
            )
            icons {
                receiverType("Icons.Filled")
            }
        }
        val outlined by creating {
            from(layout.projectDirectory.dir(
                "assets/icons/outlined"
            ))
            destinationPackage(
                "com.example.icons.outlined"
            )
            icons {
                receiverType("Icons.Outlined")
            }
        }
    }
}""",
                language = "kotlin",
            )
        }
        UsageCard(
            title = "Advanced Options",
            description = "Exclude files, rename icons, and use parallel processing.",
        ) {
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
                theme("...AppTheme")
                makeInternal(true)
            }
        }
    }
}""",
                language = "kotlin",
            )
        }
    }
}
