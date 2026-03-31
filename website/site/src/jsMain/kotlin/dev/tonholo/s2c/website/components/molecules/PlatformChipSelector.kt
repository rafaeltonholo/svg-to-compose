package dev.tonholo.s2c.website.components.molecules

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextTransform
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.letterSpacing
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.role
import com.varabyte.kobweb.compose.ui.modifiers.tabIndex
import com.varabyte.kobweb.compose.ui.modifiers.textTransform
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.theme.toSitePalette
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

val ChipContainerStyle = CssStyle.base {
    Modifier
        .gap(SiteTheme.dimensions.size.Sm)
}

val ActiveChipStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .fontSize(0.6875.cssRem)
        .letterSpacing(0.05.em)
        .textTransform(TextTransform.Uppercase)
        .fontWeight(FontWeight.SemiBold)
        .color(palette.onSurface)
        .backgroundColor(palette.background)
        .border(1.px, LineStyle.Solid, palette.outline)
        .padding(topBottom = 4.px, leftRight = 12.px)
        .borderRadius(4.px)
        .cursor(Cursor.Pointer)
        .transition(
            Transition.of("background-color", duration = 200.ms, timingFunction = TransitionTimingFunction.EaseOut),
            Transition.of("color", duration = 200.ms, timingFunction = TransitionTimingFunction.EaseOut),
        )
}

val InactiveChipStyle = CssStyle {
    base {
        Modifier
            .fontSize(0.6875.cssRem)
            .letterSpacing(0.05.em)
            .textTransform(TextTransform.Uppercase)
            .fontWeight(FontWeight.Medium)
            .color(colorMode.toSitePalette().onSurfaceVariant)
            .backgroundColor(Colors.Transparent)
            .border(1.px, LineStyle.Solid, Colors.Transparent)
            .padding(topBottom = 4.px, leftRight = 12.px)
            .borderRadius(4.px)
            .cursor(Cursor.Pointer)
            .transition(
                Transition.of("background-color", duration = 200.ms, timingFunction = TransitionTimingFunction.EaseOut),
                Transition.of("color", duration = 200.ms, timingFunction = TransitionTimingFunction.EaseOut),
            )
    }
    cssRule(":hover") {
        Modifier.color(colorMode.toSitePalette().primary)
    }
}

@Composable
fun PlatformChipSelector(selectedPlatform: Platform, onSelect: (Platform) -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = ChipContainerStyle.toModifier()
            .role("tablist")
            .then(modifier),
    ) {
        Platform.entries.forEach { platform ->
            val isSelected = platform == selectedPlatform
            val style = if (isSelected) ActiveChipStyle else InactiveChipStyle
            Div(
                attrs = style.toModifier()
                    .onClick { onSelect(platform) }
                    .tabIndex(if (isSelected) 0 else -1)
                    .role("tab")
                    .toAttrs {
                        attr("aria-selected", isSelected.toString())
                        onKeyDown { event ->
                            when (event.key) {
                                " ", "Enter" -> {
                                    event.preventDefault()
                                    onSelect(platform)
                                }
                            }
                        }
                    },
            ) {
                Span { Text(platform.label) }
            }
        }
    }
}
