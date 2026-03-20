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
import com.varabyte.kobweb.compose.ui.modifiers.flexDirection
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaLightbulb
import com.varabyte.kobweb.silk.components.icons.fa.FaTerminal
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.SiteTheme
import dev.tonholo.s2c.website.components.molecules.CodeBlock
import dev.tonholo.s2c.website.components.molecules.PlatformGrid
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

val SubHeadingStyle = CssStyle.base {
    Modifier
        .fontSize(0.875.cssRem)
        .fontWeight(FontWeight.SemiBold)
}

val OptimizerCardStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .fillMaxWidth()
        .backgroundColor(palette.surfaceHeader)
        .border(1.px, LineStyle.Solid, palette.border)
        .borderRadius(0.75.cssRem)
        .padding(1.cssRem)
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .gap(0.75.cssRem)
        .fontSize(0.9.cssRem)
}

@Composable
fun CliTabContent() {
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
            FaTerminal(size = IconSize.SM)
            SpanText("Installation")
        }
        CodeBlock(
            code = """curl -o s2c https://raw.githubusercontent.com/rafaeltonholo/svg-to-compose/main/s2c
chmod +x s2c
./s2c --help""",
            language = "shell",
            filename = "Terminal",
        )

        Div(attrs = OptimizerCardStyle.toModifier().margin(top = 1.5.cssRem).toAttrs()) {
            Div(
                attrs = SubHeadingStyle
                    .toModifier()
                    .display(DisplayStyle.Flex)
                    .alignItems(AlignItems.Center)
                    .gap(0.5.cssRem)
                    .fontWeight(FontWeight.SemiBold)
                    .toAttrs(),
            ) {
                FaLightbulb(size = IconSize.SM)
                SpanText("Optional Optimizers", modifier = Modifier.color(SiteTheme.palette.brand.yellow))
            }
            Div {
                SpanText("For ")
                SpanText(
                    "SVG optimization",
                    modifier = Modifier
                        .fontWeight(FontWeight.Bold)
                        .color(SiteTheme.palette.brand.red),
                )
                SpanText(", install SVGO:")
            }
            CodeBlock(code = "npm -g install svgo", language = "shell")
            Div {
                SpanText(
                    "For ",
                    modifier = Modifier
                        .fontSize(0.9.cssRem)
                        .color(SiteTheme.palette.brand.blue),
                )
                SpanText(
                    "AVG optimization",
                    modifier = Modifier
                        .fontWeight(FontWeight.Bold)
                        .color(SiteTheme.palette.brand.blue),
                )
                SpanText(", install avocado:")
            }
            CodeBlock(code = "npm -g install avocado", language = "shell")
        }

        SpanText(
            "Supported Platforms",
            modifier = SubHeadingStyle.toModifier(),
        )
        PlatformGrid()
    }
}
