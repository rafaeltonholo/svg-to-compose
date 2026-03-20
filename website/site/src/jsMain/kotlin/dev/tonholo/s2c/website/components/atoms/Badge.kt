package dev.tonholo.s2c.website.components.atoms

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.StyleVariable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.setVariable
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.ComponentKind
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.CssStyleVariant
import com.varabyte.kobweb.silk.style.addVariant
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.CSSLengthValue
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Span

sealed interface BadgeKind : ComponentKind

object BadgeVars {
    val ContentColor by StyleVariable<Color>(prefix = "badge")
    val ContainerColor by StyleVariable<Color>(prefix = "badge")
    val BorderColor by StyleVariable<Color>(prefix = "badge")
    val BorderRadius by StyleVariable<CSSLengthValue>(prefix = "badge")
    val PaddingHorizontal by StyleVariable<CSSLengthValue>(prefix = "badge")
    val PaddingVertical by StyleVariable<CSSLengthValue>(prefix = "badge")
}

val BadgeStyle = CssStyle<BadgeKind> {
    base {
        Modifier
            .styleModifier {
                property("display", "inline-flex")
                property("align-items", "center")
            }
            .gap(0.375.cssRem)
            .borderRadius(BadgeVars.BorderRadius.value(100.px))
            .padding(
                topBottom = BadgeVars.PaddingVertical.value(0.25.cssRem),
                leftRight = BadgeVars.PaddingHorizontal.value(0.75.cssRem),
            )
            .fontSize(0.75.cssRem)
            .fontWeight(FontWeight.Medium)
            .color(BadgeVars.ContentColor.value())
            .backgroundColor(BadgeVars.ContainerColor.value())
            .border(1.px, LineStyle.Solid, BadgeVars.BorderColor.value())
    }
}

val SquaredBadge = BadgeStyle.addVariant {
    base {
        Modifier
            .setVariable(BadgeVars.BorderRadius, 0.25.cssRem)
            .setVariable(BadgeVars.PaddingVertical, 0.125.cssRem)
            .setVariable(BadgeVars.PaddingHorizontal, 0.5.cssRem)
    }
}

@Composable
fun Badge(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    variant: CssStyleVariant<BadgeKind>? = null,
) {
    Badge(
        color = color,
        modifier = modifier,
        leadingIcon = leadingIcon,
        variant = variant,
    ) {
        SpanText(text)
    }
}

@Composable
fun Badge(
    color: Color,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    variant: CssStyleVariant<BadgeKind>? = null,
    content: @Composable () -> Unit,
) {
    val rgb = color.toRgb()
    val bgColor = rgb.copyf(alpha = 0.1f)
    val borderColor = rgb.copyf(alpha = 0.3f)
    Span(
        attrs = BadgeStyle
            .toModifier(variant)
            .then(modifier)
            .setVariable(BadgeVars.ContentColor, color)
            .setVariable(BadgeVars.ContainerColor, bgColor)
            .setVariable(BadgeVars.BorderColor, borderColor)
            .toAttrs(),
    ) {
        leadingIcon?.invoke()
        content()
    }
}
