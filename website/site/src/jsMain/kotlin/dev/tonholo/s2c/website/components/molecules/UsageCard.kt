package dev.tonholo.s2c.website.components.molecules

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.flexDirection
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

val UsageCardStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .backgroundColor(palette.surfaceVariant)
        .border(1.px, LineStyle.Solid, palette.outlineVariant)
        .borderRadius(0.75.cssRem)
        .padding(1.cssRem)
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .minWidth(0.px)
        .gap(0.75.cssRem)
}

@Composable
fun UsageCard(
    title: String,
    description: String,
    icon: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Div(attrs = UsageCardStyle.toModifier().toAttrs()) {
        Div(
            attrs = Modifier
                .display(DisplayStyle.Flex)
                .alignItems(AlignItems.Center)
                .gap(0.5.cssRem)
                .toAttrs(),
        ) {
            icon?.invoke()
            SpanText(
                title,
                modifier = Modifier
                    .fontSize(1.cssRem)
                    .fontWeight(FontWeight.SemiBold),
            )
        }
        SpanText(
            description,
            modifier = Modifier
                .fontSize(0.875.cssRem)
                .color(ColorMode.current.toSitePalette().onSurfaceVariant),
        )
        content()
    }
}
