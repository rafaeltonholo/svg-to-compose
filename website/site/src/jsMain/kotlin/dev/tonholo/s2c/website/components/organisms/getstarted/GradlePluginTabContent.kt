package dev.tonholo.s2c.website.components.organisms.getstarted

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.AlignItems
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaWandMagicSparkles
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.SiteTheme
import dev.tonholo.s2c.website.components.atoms.icon.GradleSvg
import dev.tonholo.s2c.website.components.molecules.CodeBlock
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

val InfoCardStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .backgroundColor(palette.surfaceHeader)
        .border(
            width = 1.px,
            style = LineStyle.Solid,
            color = palette.brand.teal.toRgb().copyf(alpha = 0.2f),
        )
        .borderRadius(0.75.cssRem)
        .padding(1.cssRem)
        .fontSize(0.9.cssRem)
        .lineHeight(value = 1.6)
        .color(palette.muted)
}

@Composable
fun GradlePluginTabContent() {
    Column(
        verticalArrangement = Arrangement.spacedBy(1.cssRem),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Div(
            attrs = SubHeadingStyle.toModifier()
                .display(DisplayStyle.LegacyInlineFlex)
                .alignItems(AlignItems.Center)
                .gap(0.5.cssRem)
                .toAttrs(),
        ) {
            GradleSvg(color = SiteTheme.palette.muted, width = 18, height = 18)
            SpanText("Step 1 — Add the plugin to your ")
            SpanText(
                "build.gradle.kts",
                modifier = Modifier.fontWeight(FontWeight.Bold),
            )
        }
        CodeBlock(
            code = """plugins {
    id("dev.tonholo.s2c") version "2.1.2"
}""",
            language = "kotlin",
            filename = "build.gradle.kts",
        )

        Div(
            attrs = SubHeadingStyle.toModifier()
                .display(DisplayStyle.LegacyInlineFlex)
                .alignItems(AlignItems.Center)
                .gap(0.5.cssRem)
                .toAttrs(),
        ) {
            GradleSvg(color = SiteTheme.palette.muted, width = 18, height = 18)
            SpanText("Step 2 — Configure conversion processors")
        }
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

        val palette = ColorMode.current.toSitePalette()
        Div(attrs = InfoCardStyle.toModifier().margin(top = 1.cssRem).toAttrs()) {
            FaWandMagicSparkles(
                modifier = Modifier.color(palette.brand.yellow),
            )
            SpanText(" ")
            SpanText(
                "Zero configuration required.",
                modifier = Modifier.fontWeight(FontWeight.SemiBold).color(palette.brand.teal),
            )
            SpanText(
                " The Gradle plugin integrates into the standard build lifecycle. " +
                    "Icons are converted automatically during build, with ",
            )
            SpanText(
                "incremental processing",
                modifier = Modifier.color(palette.brand.violet),
            )
            SpanText(" and ")
            SpanText(
                "parallel execution",
                modifier = Modifier.color(palette.brand.blue),
            )
            SpanText(" support.")
        }

        SpanText(
            "Then run the conversion task:",
            modifier = Modifier
                .fontSize(0.9.cssRem)
                .color(palette.muted)
                .margin(top = 1.5.cssRem),
        )
        CodeBlock(
            code = """./gradlew svgToCompose
# or to convert only specific processors:
./gradlew svgToComposeIcons""",
            language = "shell",
            filename = "Terminal",
        )
    }
}
