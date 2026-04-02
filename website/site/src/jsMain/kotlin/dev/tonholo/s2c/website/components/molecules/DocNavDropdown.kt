package dev.tonholo.s2c.website.components.molecules

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.PointerEvents
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.attrsModifier
import com.varabyte.kobweb.compose.ui.modifiers.ariaHidden
import com.varabyte.kobweb.compose.ui.modifiers.ariaLabel
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.left
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.onMouseEnter
import com.varabyte.kobweb.compose.ui.modifiers.onMouseLeave
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.pointerEvents
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.role
import com.varabyte.kobweb.compose.ui.modifiers.tabIndex
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.translateY
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.silk.components.icons.fa.FaChevronDown
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.navigation.UncoloredLinkVariant
import com.varabyte.kobweb.silk.components.navigation.UndecoratedLinkVariant
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.theme.toSitePalette
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

private const val DROPDOWN_Z_INDEX = 100

data class DocLinkEntry(val path: String, val label: String)

val docLinkEntries = listOf(
    DocLinkEntry(path = "/docs/cli", label = "CLI"),
    DocLinkEntry(path = "/docs/gradle-plugin", label = "Gradle Plugin"),
    DocLinkEntry(path = "/api-docs/index.html", label = "API Reference"),
    DocLinkEntry(path = "/docs/faq", label = "FAQ"),
    DocLinkEntry(path = "/docs/alternatives", label = "Alternatives"),
)

val DocNavDropdownTriggerStyle = CssStyle(
    extraModifier = Modifier
        .tabIndex(0)
        .role("button"),
) {
    base {
        val palette = colorMode.toSitePalette()
        Modifier
            .color(palette.onSurfaceVariant)
            .fontWeight(FontWeight.Medium)
            .fontSize(0.875.cssRem)
            .cursor(Cursor.Pointer)
            .transition(
                Transition.of("color", duration = 150.ms, timingFunction = TransitionTimingFunction.Ease),
            )
            .gap(SiteTheme.dimensions.size.Xsm)
    }
    cssRule(":hover") {
        Modifier.color(colorMode.toSitePalette().primary)
    }
}

val DocNavDropdownPanelStyle = CssStyle {
    base {
        val palette = colorMode.toSitePalette()
        Modifier
            .position(Position.Absolute)
            .top(100.percent)
            .left(0.px)
            .minWidth(12.cssRem)
            .padding(SiteTheme.dimensions.size.Sm)
            .backgroundColor(palette.surfaceVariant)
            .border(1.px, LineStyle.Solid, palette.outline)
            .borderRadius(0.5.cssRem)
            .boxShadow(
                offsetX = 0.px,
                offsetY = 4.px,
                blurRadius = 12.px,
                color = palette.outline,
            )
            .zIndex(DROPDOWN_Z_INDEX)
    }
}

val DocNavDropdownLinkStyle = CssStyle {
    base {
        val palette = colorMode.toSitePalette()
        Modifier
            .display(DisplayStyle.Block)
            .padding(topBottom = SiteTheme.dimensions.size.Sm, leftRight = SiteTheme.dimensions.size.Md)
            .fontSize(0.8.cssRem)
            .borderRadius(0.25.cssRem)
            .color(palette.onSurfaceVariant)
            .transition(
                Transition.of("background-color", duration = 150.ms, timingFunction = TransitionTimingFunction.Ease),
                Transition.of("color", duration = 150.ms, timingFunction = TransitionTimingFunction.Ease),
            )
    }
    cssRule(":hover") {
        val palette = colorMode.toSitePalette()
        Modifier
            .backgroundColor(palette.surface)
            .color(palette.primary)
    }
}

@Composable
fun DocNavDropdown(modifier: Modifier = Modifier) {
    var isOpen by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .position(Position.Relative)
            .display(DisplayStyle.InlineBlock)
            .onMouseEnter { isOpen = true }
            .onMouseLeave { isOpen = false }
            .attrsModifier {
                onFocusOut { event ->
                    val related = event.relatedTarget as? org.w3c.dom.Node
                    val container = event.currentTarget as? org.w3c.dom.Node
                    if (container != null && (related == null || !container.contains(related))) {
                        isOpen = false
                    }
                }
            },
    ) {
        Row(
            modifier = DocNavDropdownTriggerStyle
                .toModifier()
                .onClick { isOpen = !isOpen }
                .attrsModifier {
                    attr("aria-expanded", isOpen.toString())
                    attr("aria-haspopup", "true")
                    onKeyDown { event ->
                        when (event.key) {
                            " ", "Enter" -> {
                                event.preventDefault()
                                isOpen = !isOpen
                            }

                            "Escape" -> {
                                isOpen = false
                            }
                        }
                    }
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SpanText("Docs")
            FaChevronDown(
                size = IconSize.XS,
                modifier = Modifier
                    .fontSize(0.6.cssRem)
                    .color(ColorMode.current.toSitePalette().onSurfaceVariant),
            )
        }

        DropdownPanel(isOpen)
    }
}

@Composable
private fun DropdownPanel(isOpen: Boolean) {
    Column(
        modifier = DocNavDropdownPanelStyle.toModifier()
            .opacity(value = if (isOpen) 1 else 0)
            .translateY(ty = if (isOpen) 0.px else (-4).px)
            .pointerEvents(if (isOpen) PointerEvents.Auto else PointerEvents.None)
            .transition(
                Transition.of("opacity", duration = 150.ms, timingFunction = TransitionTimingFunction.EaseOut),
                Transition.of("transform", duration = 150.ms, timingFunction = TransitionTimingFunction.EaseOut),
            )
            .ariaHidden(!isOpen)
            .gap(0.125.cssRem)
            .role("menu")
            .ariaLabel("Documentation")
            .attrsModifier {
                onKeyDown { event ->
                    when (event.key) {
                        "ArrowDown", "ArrowUp" -> {
                            event.preventDefault()
                            val container = event.currentTarget as? org.w3c.dom.Element
                                ?: return@onKeyDown
                            val items = container.querySelectorAll("[role='menuitem']")
                            if (items.length == 0) return@onKeyDown
                            val active = kotlinx.browser.document.activeElement
                            var index = -1
                            for (i in 0 until items.length) {
                                if (items.item(i) == active) {
                                    index = i
                                    break
                                }
                            }
                            val next = if (event.key == "ArrowDown") {
                                if (index < items.length - 1) index + 1 else 0
                            } else {
                                if (index > 0) index - 1 else items.length - 1
                            }
                            (items.item(next) as? org.w3c.dom.HTMLElement)?.focus()
                        }

                        "Home" -> {
                            event.preventDefault()
                            val container = event.currentTarget as? org.w3c.dom.Element
                                ?: return@onKeyDown
                            val items = container.querySelectorAll("[role='menuitem']")
                            (items.item(0) as? org.w3c.dom.HTMLElement)?.focus()
                        }

                        "End" -> {
                            event.preventDefault()
                            val container = event.currentTarget as? org.w3c.dom.Element
                                ?: return@onKeyDown
                            val items = container.querySelectorAll("[role='menuitem']")
                            (items.item(items.length - 1) as? org.w3c.dom.HTMLElement)?.focus()
                        }
                    }
                }
            },
    ) {
        val linkTabIndex = if (isOpen) 0 else -1
        val docLinkVariant = UndecoratedLinkVariant.then(UncoloredLinkVariant)
        docLinkEntries.forEach { entry ->
            Link(
                path = entry.path,
                text = entry.label,
                modifier = DocNavDropdownLinkStyle.toModifier()
                    .tabIndex(linkTabIndex)
                    .role("menuitem"),
                variant = docLinkVariant,
            )
        }
    }
}
