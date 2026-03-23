package dev.tonholo.s2c.website.components.atoms

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.ariaLabel
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.left
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.tabIndex
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.transform
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.theme.toSitePalette
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.unaryMinus
import org.jetbrains.compose.web.dom.Div

@Composable
fun ToggleSwitch(
    modifier: Modifier = Modifier,
    checked: Boolean = false,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    label: String? = null,
) {
    val palette = ColorMode.current.toSitePalette()
    val trackColor = if (checked) {
        palette.primary
    } else {
        palette.primary.toRgb().copyf(alpha = 0.2f)
    }
    Div(
        attrs = modifier
            .width(2.75.cssRem)
            .height(1.5.cssRem)
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
            .tabIndex(0)
            .let { mod -> if (label != null) mod.ariaLabel(label) else mod }
            .toAttrs {
                attr("role", "switch")
                attr("aria-checked", checked.toString())
                onKeyDown { event ->
                    when (event.key) {
                        " ", "Enter" -> {
                            event.preventDefault()
                            onCheckedChange?.invoke(!checked)
                        }
                    }
                }
            },
    ) {
        val halfSize = 50.percent
        Div(
            attrs = Modifier
                .size(1.125.cssRem)
                .borderRadius(50.percent)
                .backgroundColor(palette.onPrimary)
                .position(Position.Absolute)
                .top(halfSize)
                .left(.2.cssRem)
                .transition(
                    Transition.of("transform", duration = 200.ms, timingFunction = TransitionTimingFunction.Ease),
                )
                .transform {
                    translateY(-halfSize)
                    if (checked) translateX(1.2.cssRem)
                }
                .toAttrs(),
        )
    }
}
