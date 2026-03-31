package dev.tonholo.s2c.website.components.molecules

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.AlignItems
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderBottom
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.gridTemplateRows
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.role
import com.varabyte.kobweb.compose.ui.modifiers.tabIndex
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.theme.toSitePalette
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.fr
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.events.SyntheticKeyboardEvent

private const val HEIGHT_TRANSITION_MS = 300

val TabBarStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .display(DisplayStyle.Flex)
        .gap(SiteTheme.dimensions.size.Xl)
        .borderBottom(width = 1.px, style = LineStyle.Solid, color = palette.outline)
}

val ActiveTabStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .padding(bottom = SiteTheme.dimensions.size.Sm)
        .cursor(Cursor.Pointer)
        .backgroundColor(Colors.Transparent)
        .color(palette.onSurface)
        .fontWeight(FontWeight.SemiBold)
        .fontSize(0.875.cssRem)
        .borderBottom(width = 2.px, style = LineStyle.Solid, color = palette.primary)
        .transition(
            Transition.of("color", duration = 200.ms, timingFunction = TransitionTimingFunction.EaseOut),
            Transition.of("border-color", duration = 250.ms, timingFunction = TransitionTimingFunction.EaseOut),
        )
}

val InactiveTabStyle = CssStyle {
    base {
        Modifier
            .padding(bottom = SiteTheme.dimensions.size.Sm)
            .cursor(Cursor.Pointer)
            .backgroundColor(Colors.Transparent)
            .color(colorMode.toSitePalette().onSurfaceVariant)
            .fontWeight(FontWeight.Medium)
            .fontSize(0.875.cssRem)
            .borderBottom(width = 2.px, style = LineStyle.Solid, color = Colors.Transparent)
            .transition(
                Transition.of("color", duration = 200.ms, timingFunction = TransitionTimingFunction.EaseOut),
                Transition.of("border-color", duration = 250.ms, timingFunction = TransitionTimingFunction.EaseOut),
            )
    }
    cssRule(":hover") {
        Modifier.color(colorMode.toSitePalette().primary)
    }
}

/**
 * Tabbed panel with smooth height animation using the CSS grid
 * `grid-template-rows: 0fr -> 1fr` technique. Each panel lives in its own
 * grid wrapper that collapses/expands. All panels stay in the DOM - no
 * creation/destruction, no layout jank.
 *
 * @see <a href="https://keithjgrant.com/posts/2023/04/transitioning-to-height-auto/">Transitioning to height auto</a>
 */
@Composable
fun TabPanel(
    tabs: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
    tabContent: ((index: Int, label: String) -> @Composable () -> Unit)? = null,
    panels: List<@Composable () -> Unit>,
) {
    require(panels.size == tabs.size) {
        "TabPanel: panels.size (${panels.size}) must match tabs.size (${tabs.size})"
    }
    Div(attrs = modifier.fillMaxWidth().toAttrs()) {
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
        // Each panel in its own collapsible grid wrapper
        panels.forEachIndexed { index, panel ->
            val isActive = index == selectedIndex
            // Outer: grid with row that transitions between 0fr and 1fr
            Div(
                attrs = Modifier
                    .display(DisplayStyle.Grid)
                    .gridTemplateRows {
                        if (isActive) size(1.fr) else size(0.fr)
                    }
                    .transition(
                        Transition.of(
                            "grid-template-rows",
                            duration = HEIGHT_TRANSITION_MS.ms,
                            timingFunction = TransitionTimingFunction.EaseOut,
                        ),
                    )
                    .role("tabpanel")
                    .toAttrs(),
            ) {
                // Inner: overflow hidden to clip during collapse, fade opacity
                Div(
                    attrs = Modifier
                        .overflow(Overflow.Hidden)
                        .fillMaxWidth()
                        .margin(top = if (isActive) SiteTheme.dimensions.size.Md else 0.px)
                        .opacity(if (isActive) 1f else 0f)
                        .transition(
                            Transition.of(
                                "opacity",
                                duration = HEIGHT_TRANSITION_MS.ms,
                                timingFunction = TransitionTimingFunction.EaseOut,
                            ),
                        )
                        .toAttrs(),
                ) {
                    panel()
                }
            }
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
            .role("tab")
            .toAttrs {
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
