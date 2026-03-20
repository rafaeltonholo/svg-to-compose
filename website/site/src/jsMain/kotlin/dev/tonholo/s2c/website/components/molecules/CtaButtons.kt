package dev.tonholo.s2c.website.components.molecules

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.BackgroundImage
import com.varabyte.kobweb.compose.css.BoxShadow
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.JustifyContent
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.css.functions.linearGradient
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.backgroundImage
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.flexWrap
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaGamepad
import com.varabyte.kobweb.silk.components.icons.fa.FaRocket
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.navigation.UncoloredLinkVariant
import com.varabyte.kobweb.silk.components.navigation.UndecoratedLinkVariant
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.deg
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

val HeroCtaRowStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .flexWrap(FlexWrap.Wrap)
        .justifyContent(JustifyContent.Center)
        .gap(1.cssRem)
}

val PrimaryCtaStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .color(Colors.White)
        .borderRadius(0.75.cssRem)
        .padding(topBottom = 0.75.cssRem, leftRight = 1.5.cssRem)
        .fontWeight(FontWeight.SemiBold)
        .textDecorationLine(TextDecorationLine.None)
        .cursor(Cursor.Pointer)
        .border(0.px, LineStyle.None, Colors.Transparent)
        .transition(Transition.all(duration = 200.ms, timingFunction = TransitionTimingFunction.Ease))
        .backgroundImage(
            BackgroundImage.of(
                gradient = linearGradient(
                    angle = 135.deg,
                    from = palette.brand.purple,
                    to = palette.brand.purpleDeep,
                ),
            ),
        )
        .boxShadow(
            BoxShadow.of(
                offsetX = 0.px,
                offsetY = 4.px,
                blurRadius = 15.px,
                color = Color.rgba(r = 127, g = 82, b = 255, a = 0.3f),
            ),
        )
}

val SecondaryCtaStyle = CssStyle.base {
    Modifier
        .backgroundColor(Colors.Transparent)
        .border(1.px, LineStyle.Solid, Color.rgba(127, 82, 255, 0.4f))
        .borderRadius(0.75.cssRem)
        .padding(topBottom = 0.75.cssRem, leftRight = 1.5.cssRem)
        .textDecorationLine(TextDecorationLine.None)
        .cursor(Cursor.Pointer)
        .transition(Transition.all(duration = 200.ms, timingFunction = TransitionTimingFunction.Ease))
}

@Composable
fun CtaButtons() {
    Div(attrs = HeroCtaRowStyle.toModifier().toAttrs()) {
        Link(
            path = "#playground",
            modifier = PrimaryCtaStyle.toModifier()
                .display(DisplayStyle.Flex)
                .alignItems(AlignItems.Center)
                .gap(0.5.cssRem),
            variant = UndecoratedLinkVariant.then(UncoloredLinkVariant),
        ) {
            FaGamepad(size = IconSize.SM)
            SpanText("Try the Playground")
        }
        Link(
            path = "#install",
            modifier = SecondaryCtaStyle.toModifier()
                .display(DisplayStyle.Flex)
                .alignItems(AlignItems.Center)
                .gap(0.5.cssRem),
            variant = UndecoratedLinkVariant.then(UncoloredLinkVariant),
        ) {
            FaRocket(size = IconSize.SM)
            SpanText("Get Started")
        }
    }
}
