package dev.tonholo.s2c.website.components.molecules

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div

val FeatureCardStyle = CssStyle {
    base {
        Modifier
            .padding(1.25.cssRem)
            .borderRadius(0.75.cssRem)
            .styleModifier {
                property("transition", "transform 0.2s ease, box-shadow 0.2s ease")
            }
    }
    cssRule(":hover") {
        Modifier.styleModifier {
            property("transform", "scale(1.02) translateY(-4px)")
            property("box-shadow", "0 0 32px var(--card-glow-color)")
        }
    }
}

@Composable
fun FeatureCard(
    icon: @Composable () -> Unit,
    title: String,
    description: String,
    color: Color,
) {
    val palette = ColorMode.current.toSitePalette()
    val rgb = color.toRgb()
    val bgColor = rgb.copyf(alpha = 0.08f)
    val borderColor = rgb.copyf(alpha = 0.2f)
    val iconBgColor = rgb.copyf(alpha = 0.15f)

    Div(
        attrs = FeatureCardStyle.toModifier()
            .backgroundColor(bgColor)
            .border(1.px, LineStyle.Solid, borderColor)
            .styleModifier {
                property("--card-glow-color", borderColor.toString())
            }
            .toAttrs()
    ) {
        Div(
            attrs = Modifier
                .size(2.5.cssRem)
                .borderRadius(0.75.cssRem)
                .backgroundColor(iconBgColor)
                .display(DisplayStyle.Flex)
                .alignItems(AlignItems.Center)
                .justifyContent(JustifyContent.Center)
                .fontSize(1.25.cssRem)
                .color(color)
                .toAttrs()
        ) {
            icon()
        }
        SpanText(
            title,
            modifier = Modifier
                .display(DisplayStyle.Block)
                .fontSize(1.cssRem)
                .fontWeight(FontWeight.SemiBold)
                .margin(top = 0.75.cssRem)
        )
        SpanText(
            description,
            modifier = Modifier
                .display(DisplayStyle.Block)
                .fontSize(0.875.cssRem)
                .color(palette.muted)
                .margin(top = 0.375.cssRem)
                .lineHeight(1.5)
        )
    }
}
