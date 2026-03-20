package dev.tonholo.s2c.website.components.molecules

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.AlignItems
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.JustifyContent
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.role
import com.varabyte.kobweb.compose.ui.modifiers.tabIndex
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.events.SyntheticKeyboardEvent

val TabBarContainerStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .justifyContent(JustifyContent.Center)
}

val TabBarStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .gap(0.25.cssRem)
        .padding(0.25.cssRem)
        .borderRadius(0.75.cssRem)
        .backgroundColor(colorMode.toSitePalette().surfaceVariant)
}

val ActiveTabStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .padding(topBottom = 0.5.cssRem, leftRight = 1.cssRem)
        .borderRadius(0.5.cssRem)
        .cursor(Cursor.Pointer)
        .backgroundColor(palette.primary)
        .color(palette.onPrimary)
        .fontWeight(FontWeight.Medium)
        .fontSize(0.875.cssRem)
        .transition(
            Transition.of("background-color", duration = 200.ms, timingFunction = TransitionTimingFunction.Ease),
            Transition.of("color", duration = 200.ms, timingFunction = TransitionTimingFunction.Ease),
        )
}

val InactiveTabStyle = CssStyle {
    base {
        Modifier
            .padding(topBottom = 0.5.cssRem, leftRight = 1.cssRem)
            .borderRadius(0.5.cssRem)
            .cursor(Cursor.Pointer)
            .backgroundColor(Colors.Transparent)
            .color(colorMode.toSitePalette().onSurfaceVariant)
            .fontWeight(FontWeight.Medium)
            .fontSize(0.875.cssRem)
            .transition(
                Transition.of("background-color", duration = 200.ms, timingFunction = TransitionTimingFunction.Ease),
                Transition.of("color", duration = 200.ms, timingFunction = TransitionTimingFunction.Ease),
            )
    }
    cssRule(":hover") {
        Modifier.color(colorMode.toSitePalette().primary)
    }
}

@Composable
fun TabPanel(
    tabs: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
    tabContent: ((index: Int, label: String) -> @Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Div(attrs = modifier.fillMaxWidth().toAttrs()) {
        Div(attrs = TabBarContainerStyle.toModifier().toAttrs()) {
            Row(
                modifier = TabBarStyle.toModifier().role("tablist"),
            ) {
                tabs.forEachIndexed { index, tab ->
                    TabItem(
                        tab = tab,
                        index = index,
                        isSelected = index == selectedIndex,
                        tabs = tabs,
                        onSelect = onSelect,
                        tabContent = tabContent,
                    )
                }
            }
        }
        Div(
            attrs = Modifier
                .fillMaxWidth()
                .margin(top = 0.75.cssRem)
                .toAttrs {
                    attr("role", "tabpanel")
                },
        ) {
            content()
        }
    }
}

@Composable
private fun TabItem(
    tab: String,
    index: Int,
    isSelected: Boolean,
    tabs: List<String>,
    onSelect: (Int) -> Unit,
    tabContent: ((index: Int, label: String) -> @Composable () -> Unit)?,
) {
    val style = if (isSelected) ActiveTabStyle else InactiveTabStyle
    Div(
        attrs = style.toModifier()
            .onClick { onSelect(index) }
            .tabIndex(if (isSelected) 0 else -1)
            .display(DisplayStyle.LegacyInlineFlex)
            .alignItems(AlignItems.Center)
            .gap(0.375.cssRem)
            .toAttrs {
                attr("role", "tab")
                attr("aria-selected", isSelected.toString())
                onKeyDown { event ->
                    handleTabKeyDown(event, index, tabs, onSelect)
                }
            },
    ) {
        val customContent = tabContent?.invoke(index, tab)
        if (customContent != null) {
            customContent()
        } else {
            SpanText(tab)
        }
    }
}

private fun handleTabKeyDown(event: SyntheticKeyboardEvent, index: Int, tabs: List<String>, onSelect: (Int) -> Unit) {
    when (event.key) {
        "ArrowLeft" -> {
            event.preventDefault()
            val prev = if (index > 0) index - 1 else tabs.lastIndex
            onSelect(prev)
        }

        "ArrowRight" -> {
            event.preventDefault()
            val next = if (index < tabs.lastIndex) index + 1 else 0
            onSelect(next)
        }

        " ", "Enter" -> {
            event.preventDefault()
            onSelect(index)
        }
    }
}
