package dev.tonholo.s2c.website.components.molecules.playground

import com.varabyte.kobweb.compose.css.AnimationIterationCount
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.animation
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.outline
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import dev.tonholo.s2c.website.SpinKeyframes
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.AnimationTimingFunction
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px

val ConvertButtonStyle = CssStyle {
    base {
        val palette = colorMode.toSitePalette()
        Modifier
            .backgroundColor(palette.primary)
            .color(palette.onPrimary)
            .borderRadius(0.5.cssRem)
            .padding(topBottom = 0.5.cssRem, leftRight = 1.cssRem)
            .fontWeight(FontWeight.SemiBold)
            .fontSize(0.8.cssRem)
            .cursor(Cursor.Pointer)
            .border(0.px, LineStyle.None, Colors.Transparent)
            .transition(
                Transition.of("color", duration = 150.ms, timingFunction = TransitionTimingFunction.Ease),
                Transition.of("background-color", duration = 150.ms, timingFunction = TransitionTimingFunction.Ease),
            )
    }
    cssRule(":hover") {
        Modifier.backgroundColor(colorMode.toSitePalette().primaryContainer)
    }
    cssRule(":focus-visible") {
        val palette = colorMode.toSitePalette()
        Modifier
            .outline(2.px, LineStyle.Solid, palette.onPrimary)
            .backgroundColor(palette.primaryContainer)
    }
}

/** Styles the spinner icon with a continuous rotation animation. */
val SpinnerIconStyle = CssStyle.base {
    Modifier
        .animation(
            SpinKeyframes.toAnimation(
                duration = 1000.ms,
                timingFunction = AnimationTimingFunction.Linear,
                iterationCount = AnimationIterationCount.Infinite,
            ),
        )
}
