package dev.tonholo.s2c.website.components.molecules

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.flexWrap
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaArrowUpRightFromSquare
import com.varabyte.kobweb.silk.components.icons.fa.FaChevronRight
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
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.dom.Div

val CtaRowStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .flexWrap(FlexWrap.Wrap)
        .alignItems(AlignItems.Center)
        .gap(1.5.cssRem)
}

val PrimaryCtaStyle = CssStyle {
    base {
        val palette = colorMode.toSitePalette()
        Modifier
            .backgroundColor(palette.primary)
            .color(palette.onPrimary)
            .borderRadius(0.375.cssRem)
            .padding(topBottom = 0.625.cssRem, leftRight = 1.cssRem)
            .fontSize(0.875.cssRem)
            .fontWeight(FontWeight.Medium)
            .display(DisplayStyle.Flex)
            .alignItems(AlignItems.Center)
            .gap(0.375.cssRem)
            .transition(
                Transition.of("background-color", duration = 150.ms, timingFunction = TransitionTimingFunction.Ease),
            )
    }
    cssRule(":hover") {
        Modifier.backgroundColor(colorMode.toSitePalette().primaryContainer)
    }
}

val SecondaryCtaStyle = CssStyle {
    base {
        val palette = colorMode.toSitePalette()
        Modifier
            .color(palette.onSurfaceVariant)
            .fontSize(0.875.cssRem)
            .fontWeight(FontWeight.Medium)
            .display(DisplayStyle.Flex)
            .alignItems(AlignItems.Center)
            .gap(0.375.cssRem)
            .transition(
                Transition.of("color", duration = 150.ms, timingFunction = TransitionTimingFunction.Ease),
            )
    }
    cssRule(":hover") {
        Modifier.color(colorMode.toSitePalette().primary)
    }
}

@Composable
fun CtaButtons(modifier: Modifier = Modifier) {
    Div(attrs = CtaRowStyle.toModifier().then(modifier).toAttrs()) {
        Link(
            path = "#install",
            modifier = PrimaryCtaStyle.toModifier(),
            variant = UndecoratedLinkVariant.then(UncoloredLinkVariant),
        ) {
            SpanText("Get Started")
            FaChevronRight(size = IconSize.XS)
        }
        Link(
            path = "https://github.com/rafaeltonholo/svg-to-compose",
            modifier = SecondaryCtaStyle.toModifier(),
            variant = UndecoratedLinkVariant.then(UncoloredLinkVariant),
        ) {
            SpanText("View on GitHub")
            FaArrowUpRightFromSquare(size = IconSize.XS)
        }
    }
}
