package dev.tonholo.s2c.website.components.atoms

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.StyleVariable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.setVariable
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.ComponentKind
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.CssStyleVariant
import com.varabyte.kobweb.silk.style.addVariant
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.CSSLengthValue
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Span

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
    val (bgColor, borderColor) = color.resolveBadgeColors()
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

sealed interface BadgeKind : ComponentKind

object BadgeVars {
    val ContentColor by StyleVariable<Color>()
    val ContentHoverColor by StyleVariable<Color>()
    val ContainerColor by StyleVariable<Color>()
    val ContainerHoverColor by StyleVariable<Color>()
    val BorderColor by StyleVariable<Color>()
    val BorderHoverColor by StyleVariable<Color>()
    val BorderRadius by StyleVariable<CSSLengthValue>()
    val PaddingHorizontal by StyleVariable<CSSLengthValue>()
    val PaddingVertical by StyleVariable<CSSLengthValue>()
}

val BadgeStyle = CssStyle<BadgeKind> {
    base {
        Modifier
            .display(DisplayStyle.LegacyInlineFlex)
            .alignItems(AlignItems.Center)
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
            .border(width = 1.px, style = LineStyle.Solid, color = BadgeVars.BorderColor.value())
    }
    hover {
        Modifier
            .color(BadgeVars.ContentHoverColor.value(BadgeVars.ContentColor.value()))
            .backgroundColor(BadgeVars.ContainerHoverColor.value(BadgeVars.ContainerColor.value()))
            .border(
                width = 1.px,
                style = LineStyle.Solid,
                color = BadgeVars.BorderHoverColor.value(BadgeVars.BorderColor.value()),
            )
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


fun Color.resolveBadgeColors(): Pair<Color, Color> {
    val rgb = toRgb()
    val bgColor = rgb.copyf(alpha = 0.1f)
    val borderColor = rgb.copyf(alpha = 0.3f)
    return bgColor to borderColor
}
