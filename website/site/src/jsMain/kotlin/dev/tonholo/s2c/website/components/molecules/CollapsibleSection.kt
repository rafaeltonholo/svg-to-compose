package dev.tonholo.s2c.website.components.molecules

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.gridTemplateRows
import com.varabyte.kobweb.compose.ui.modifiers.ariaLabel
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.tabIndex
import com.varabyte.kobweb.compose.ui.modifiers.transform
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.attrsModifier
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaChevronDown
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.deg
import org.jetbrains.compose.web.css.fr
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

val CollapsibleContainerStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .fillMaxWidth()
        .border(1.px, LineStyle.Solid, palette.primary.toRgb().copyf(alpha = 0.2f))
        .borderRadius(0.5.cssRem)
        .overflow(Overflow.Hidden)
}

val CollapsibleHeaderStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .padding(1.cssRem)
        .cursor(Cursor.Pointer)
        .backgroundColor(colorMode.toSitePalette().surfaceVariant)
        .display(DisplayStyle.Flex)
        .alignItems(AlignItems.Center)
        .fontSize(0.875.cssRem)
        .gap(0.5.cssRem)
}

@Composable
fun CollapsibleSection(
    title: String,
    initiallyExpanded: Boolean = false,
    leadingIcon: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    var expanded by remember { mutableStateOf(initiallyExpanded) }

    Div(attrs = CollapsibleContainerStyle.toModifier().toAttrs()) {
        Row(
            modifier = CollapsibleHeaderStyle.toModifier()
                .onClick { expanded = !expanded }
                .tabIndex(0)
                .ariaLabel(title)
                .attrsModifier {
                    attr("aria-expanded", expanded.toString())
                    onKeyDown { event ->
                        when (event.key) {
                            " ", "Enter" -> {
                                event.preventDefault()
                                expanded = !expanded
                            }
                        }
                    }
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            leadingIcon?.invoke()
            SpanText(
                title,
                modifier = Modifier
                    .fontWeight(FontWeight.SemiBold),
            )
            Spacer()
            FaChevronDown(
                size = IconSize.SM,
                modifier = Modifier
                    .color(ColorMode.current.toSitePalette().onSurfaceVariant)
                    .transition(
                        Transition.of(
                            "transform",
                            duration = 300.ms,
                            timingFunction = TransitionTimingFunction.Ease,
                        ),
                    )
                    .thenIf(expanded, Modifier.transform { rotate(180.deg) }),
            )
        }
        // grid-template-rows transition: 0fr → 1fr for smooth GPU-composited expand
        Div(
            attrs = Modifier
                .display(DisplayStyle.Grid)
                .gridTemplateRows {
                    if (expanded) size(1.fr) else size(0.fr)
                }
                .transition(
                    Transition.of("grid-template-rows", duration = 300.ms, timingFunction = TransitionTimingFunction.Ease),
                    Transition.of("opacity", duration = 300.ms, timingFunction = TransitionTimingFunction.Ease),
                )
                .opacity(if (expanded) 1 else 0)
                .toAttrs(),
        ) {
            Div(
                attrs = Modifier
                    .overflow(Overflow.Hidden)
                    .toAttrs(),
            ) {
                content()
            }
        }
    }
}
