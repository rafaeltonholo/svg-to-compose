package dev.tonholo.s2c.website.components.atoms

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.role
import com.varabyte.kobweb.compose.ui.modifiers.tabIndex
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.components.organisms.playground.MobileTabBarStyle
import dev.tonholo.s2c.website.components.organisms.playground.MobileTabStyle
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.events.SyntheticKeyboardEvent

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
    onSelect: () -> Unit,
    onNavigate: (Int) -> Unit,
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
