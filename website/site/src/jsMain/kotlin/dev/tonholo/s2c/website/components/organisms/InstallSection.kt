package dev.tonholo.s2c.website.components.organisms

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.gridTemplateColumns
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.LabelTextStyle
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.components.layouts.SectionContainer
import dev.tonholo.s2c.website.components.molecules.CodeBlock
import dev.tonholo.s2c.website.config.BuildConfig
import dev.tonholo.s2c.website.theme.toSitePalette
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.fr
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

val InstallGridStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .display(DisplayStyle.Grid)
            .gridTemplateColumns { size(1.fr) }
            .gap(2.cssRem)
    }
    cssRule(" > *") {
        Modifier.minWidth(0.px).overflow(Overflow.Hidden)
    }
    Breakpoint.MD {
        Modifier.gridTemplateColumns { repeat(2) { size(1.fr) } }
    }
}

@Composable
fun InstallSection(modifier: Modifier = Modifier) {
    val palette = ColorMode.current.toSitePalette()
    SectionContainer(id = "install", modifier = modifier, altBackground = true) {
        SpanText(
            "Install",
            modifier = LabelTextStyle.toModifier()
                .color(palette.onSurfaceVariant)
                .margin(bottom = 1.cssRem),
        )

        Div(attrs = InstallGridStyle.toModifier().toAttrs()) {
            // CLI column
            Div {
                SpanText(
                    "CLI",
                    modifier = Modifier
                        .fontWeight(FontWeight.SemiBold)
                        .fontSize(1.125.cssRem)
                        .color(SiteTheme.palette.onSurface)
                        .margin(bottom = 0.75.cssRem)
                        .display(DisplayStyle.Block),
                )
                CodeBlock(
                    language = "bash",
                    // language=sh
                    code = $$"""
                        |# Download the s2c wrapper script directly from GitHub.
                        |curl -o your/target/path/s2c \ 
                        |     https://raw.githubusercontent.com/rafaeltonholo/svg-to-compose/main/s2c
                        |# Add execution permission to the script.
                        |chmod +x your/target/path/s2c
                        |# Add s2c to your path, so you can use it anywhere.
                        |export PATH="your/target/path/s2c:$PATH"
                        |# Call s2c
                        |s2c --help
                    """.trimMargin(),
                )
            }

            // Gradle column
            Div {
                SpanText(
                    "Gradle Plugin",
                    modifier = Modifier
                        .fontWeight(FontWeight.SemiBold)
                        .fontSize(1.125.cssRem)
                        .color(SiteTheme.palette.onSurface)
                        .margin(bottom = 0.75.cssRem)
                        .display(DisplayStyle.Block),
                )
                CodeBlock(
                    language = "kotlin",
                    // language=kotlin
                    code = """plugins {
                            |    id("dev.tonholo.s2c") version "${BuildConfig.VERSION}"
                            |}
                    """.trimMargin(),
                )
            }
        }
    }
}
