package dev.tonholo.s2c.website.components.molecules.playground

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderBottom
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.flex
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.role
import com.varabyte.kobweb.compose.ui.modifiers.tabIndex
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.events.SyntheticKeyboardEvent

val MobileTabBarStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .gap(0.25.cssRem)
        .padding(0.5.cssRem)
        .backgroundColor(colorMode.toSitePalette().surfaceVariant)
}

val MobileTabStyle = CssStyle {
    base {
        val palette = colorMode.toSitePalette()
        Modifier
            .padding(topBottom = 0.75.cssRem, leftRight = 1.cssRem)
            .cursor(Cursor.Pointer)
            .fontWeight(FontWeight.Medium)
            .fontSize(0.8.cssRem)
            .border(0.px, LineStyle.None, Colors.Transparent)
            .borderBottom(width = 2.px, style = LineStyle.Solid, color = Colors.Transparent)
            .borderRadius(0.px)
            .transition(
                Transition.of("color", duration = 150.ms, timingFunction = TransitionTimingFunction.Ease),
                Transition.of("border-bottom-color", duration = 150.ms, timingFunction = TransitionTimingFunction.Ease),
            )
            .flex(1)
            .textAlign(TextAlign.Center)
            .backgroundColor(Colors.Transparent)
            .color(palette.onSurfaceVariant)
    }
    cssRule(".active") {
        val palette = colorMode.toSitePalette()
        Modifier
            .color(palette.primary)
            .fontWeight(FontWeight.SemiBold)
            .borderBottom(width = 2.px, style = LineStyle.Solid, color = palette.primary)
    }
}

/**
 * Tab bar for mobile layout with keyboard navigation
 * (ArrowLeft/Right, Home/End, Space/Enter).
 */
@Composable
fun MobileTabBar(tabs: List<String>, activePanel: Int, onPanelSelect: (Int) -> Unit) {
    Div(
        attrs = MobileTabBarStyle.toModifier()
            .role("tablist")
            .toAttrs(),
    ) {
        tabs.forEachIndexed { index, tab ->
            MobileTab(
                label = tab,
                isSelected = index == activePanel,
                tabCount = tabs.size,
                index = index,
                onSelect = { onPanelSelect(index) },
                onNavigate = onPanelSelect,
            )
        }
    }
}

@Composable
private fun MobileTab(
    label: String,
    isSelected: Boolean,
    tabCount: Int,
    index: Int,
    onSelect: () -> Unit = {},
    onNavigate: (Int) -> Unit = {},
) {
    Div(
        attrs = MobileTabStyle
            .toModifier()
            .onClick { onSelect() }
            .tabIndex(if (isSelected) 0 else -1)
            .toAttrs {
                attr("role", "tab")
                attr("aria-selected", isSelected.toString())
                if (isSelected) classes("active")
                onKeyDown { event ->
                    handleTabKeyDown(event, index, tabCount, onSelect, onNavigate)
                }
            },
    ) {
        SpanText(label)
    }
}

private fun handleTabKeyDown(
    event: SyntheticKeyboardEvent,
    index: Int,
    tabCount: Int,
    onSelect: () -> Unit,
    onNavigate: (Int) -> Unit,
) {
    when (event.key) {
        "ArrowLeft" -> {
            event.preventDefault()
            onNavigate(if (index > 0) index - 1 else tabCount - 1)
        }

        "ArrowRight" -> {
            event.preventDefault()
            onNavigate(if (index < tabCount - 1) index + 1 else 0)
        }

        "Home" -> {
            event.preventDefault()
            onNavigate(0)
        }

        "End" -> {
            event.preventDefault()
            onNavigate(tabCount - 1)
        }

        " ", "Enter" -> {
            event.preventDefault()
            onSelect()
        }
    }
}
