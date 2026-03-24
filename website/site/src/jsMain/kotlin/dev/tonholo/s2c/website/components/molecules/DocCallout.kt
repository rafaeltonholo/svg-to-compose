package dev.tonholo.s2c.website.components.molecules

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.StyleVariable
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.ariaHidden
import com.varabyte.kobweb.compose.ui.modifiers.ariaLabel
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderLeft
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.role
import com.varabyte.kobweb.compose.ui.modifiers.setVariable
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaCircleExclamation
import com.varabyte.kobweb.silk.components.icons.fa.FaLightbulb
import com.varabyte.kobweb.silk.components.icons.fa.FaTriangleExclamation
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.theme.SitePalette
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.theme.toSitePalette
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

enum class CalloutVariant {
    TIP,
    WARNING,
    IMPORTANT,
}

object DocCalloutVars {
    val BorderColor by StyleVariable<Color>(prefix = "doc-callout")
    val ContainerColor by StyleVariable<Color>(prefix = "doc-callout")
}

val DocCalloutStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .borderRadius(0.5.cssRem)
        .padding(SiteTheme.dimensions.size.Lg)
        .borderLeft(
            width = 3.px,
            style = LineStyle.Solid,
            color = DocCalloutVars.BorderColor.value(),
        )
        .backgroundColor(DocCalloutVars.ContainerColor.value())
}

@Composable
fun DocCallout(variant: CalloutVariant, modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    val palette = ColorMode.current.toSitePalette()
    val (borderColor, containerColor) = variant.resolveColors(palette)

    Div(
        attrs = DocCalloutStyle.toModifier()
            .setVariable(DocCalloutVars.BorderColor, borderColor)
            .setVariable(DocCalloutVars.ContainerColor, containerColor)
            .ariaLabel(variant.accessibleLabel())
            .role("note")
            .then(modifier)
            .toAttrs(),
    ) {
        Row(
            modifier = Modifier.gap(SiteTheme.dimensions.size.Md).fillMaxWidth(),
            verticalAlignment = Alignment.Top,
        ) {
            CalloutIcon(variant = variant, palette = palette)
            Div(attrs = Modifier.fillMaxWidth().toAttrs()) {
                content()
            }
        }
    }
}

@Composable
private fun CalloutIcon(variant: CalloutVariant, palette: SitePalette) {
    val iconModifier = Modifier
        .padding(top = 0.35.cssRem)
        .color(variant.resolveIconColor(palette))
        .ariaHidden()
    when (variant) {
        CalloutVariant.TIP -> FaLightbulb(
            modifier = iconModifier,
        )

        CalloutVariant.WARNING -> FaTriangleExclamation(
            modifier = iconModifier,
        )

        CalloutVariant.IMPORTANT -> FaCircleExclamation(
            modifier = iconModifier,
        )
    }
}

private fun CalloutVariant.accessibleLabel(): String = when (this) {
    CalloutVariant.TIP -> "Tip"
    CalloutVariant.WARNING -> "Warning"
    CalloutVariant.IMPORTANT -> "Important"
}

private fun CalloutVariant.resolveIconColor(palette: SitePalette): Color = when (this) {
    CalloutVariant.TIP -> palette.primary
    CalloutVariant.WARNING -> palette.warning
    CalloutVariant.IMPORTANT -> palette.error
}

internal fun CalloutVariant.resolveColors(palette: SitePalette): Pair<Color, Color> = when (this) {
    CalloutVariant.TIP -> palette.primary to palette.surfaceVariant
    CalloutVariant.WARNING -> palette.warning to palette.warningContainer
    CalloutVariant.IMPORTANT -> palette.error to palette.surface
}
