package dev.tonholo.s2c.website.components.atoms

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.left
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.transform
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.minus
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.unaryMinus
import org.jetbrains.compose.web.dom.Div

@Composable
fun ToggleSwitch(
    checked: Boolean = false,
    onCheckedChange: ((Boolean) -> Unit)? = null,
) {
    val palette = ColorMode.current.toSitePalette()
    val trackColor = if (checked) {
        palette.brand.purple
    } else {
        palette.brand.purple.toRgb().copyf(alpha = 0.2f)
    }
    val thumbTranslateX = if (checked) "calc(100% - 2px)" else "2px"
    Div(
        attrs = Modifier
            .width(2.5.cssRem)
            .height(1.25.cssRem)
            .borderRadius(9999.px)
            .backgroundColor(trackColor)
            .cursor(Cursor.Pointer)
            .position(Position.Relative)
            .transition(
                Transition.of(
                    "background-color",
                    duration = 200.ms,
                    timingFunction = TransitionTimingFunction.Ease,
                ),
            )
            .onClick { onCheckedChange?.invoke(!checked) }
            .toAttrs(),
    ) {
        val halfSize = 50.percent
        Div(
            attrs = Modifier
                .size(1.cssRem)
                .borderRadius(50.percent)
                .backgroundColor(Colors.White)
                .position(Position.Absolute)
                .top(halfSize)
                .transition(Transition.of("left", duration = 200.ms, timingFunction = TransitionTimingFunction.Ease))
                .left(if (checked) 100.percent - 1.2.cssRem else .2.cssRem)
                .transform {
                    translateY(-halfSize)
                }
                .toAttrs(),
        )
    }
}
