package dev.tonholo.s2c.website.components.organisms

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.browser.dom.ElementTarget
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.css.functions.clamp
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.animation
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderBottom
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.onAnimationEnd
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.setVariable
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.translateX
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.CloseIcon
import com.varabyte.kobweb.silk.components.icons.HamburgerIcon
import com.varabyte.kobweb.silk.components.icons.MoonIcon
import com.varabyte.kobweb.silk.components.icons.SunIcon
import com.varabyte.kobweb.silk.components.icons.fa.FaGithub
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.navigation.UncoloredLinkVariant
import com.varabyte.kobweb.silk.components.overlay.Overlay
import com.varabyte.kobweb.silk.components.overlay.OverlayVars
import com.varabyte.kobweb.silk.components.overlay.PopupPlacement
import com.varabyte.kobweb.silk.components.overlay.Tooltip
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.animation.toAnimation
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.breakpoint.displayIfAtLeast
import com.varabyte.kobweb.silk.style.breakpoint.displayUntil
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.GradientTextStyle
import dev.tonholo.s2c.website.components.atoms.IconButton
import dev.tonholo.s2c.website.components.atoms.NavLink
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.AnimationDirection
import org.jetbrains.compose.web.css.AnimationFillMode
import org.jetbrains.compose.web.css.AnimationTimingFunction
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Nav

val NavHeaderStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .padding(leftRight = 1.cssRem, topBottom = 0.75.cssRem)
        .position(Position.Fixed)
        .top(0.px)
        .zIndex(value = 1000)
        .backgroundColor(
            when (colorMode) {
                ColorMode.LIGHT -> com.varabyte.kobweb.compose.ui.graphics.Color.rgba(255, 255, 255, 0.92f)
                ColorMode.DARK -> com.varabyte.kobweb.compose.ui.graphics.Color.rgba(10, 10, 18, 0.92f)
            },
        )
        .styleModifier {
            property("backdrop-filter", "blur(16px)")
            property("-webkit-backdrop-filter", "blur(16px)")
        }
        .borderBottom(
            width = 1.px,
            style = LineStyle.Solid,
            color = when (colorMode) {
                ColorMode.LIGHT -> com.varabyte.kobweb.compose.ui.graphics.Color.rgba(0, 0, 0, 0.1f)
                ColorMode.DARK -> com.varabyte.kobweb.compose.ui.graphics.Color.rgba(127, 82, 255, 0.15f)
            },
        )
}

@Composable
fun NavHeader() {
    Nav(
        attrs = Modifier.fillMaxWidth().toAttrs(),
    ) {
        Row(NavHeaderStyle.toModifier(), verticalAlignment = Alignment.CenterVertically) {
            S2CLogo()

            Spacer()

            Row(
                Modifier.gap(1.5.cssRem).displayIfAtLeast(Breakpoint.MD),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                MenuItems()
                GitHubButton()
                ColorModeButton()
            }

            Row(
                Modifier
                    .fontSize(1.5.cssRem)
                    .gap(1.cssRem)
                    .displayUntil(Breakpoint.MD),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                var menuState by remember { mutableStateOf(SideMenuState.CLOSED) }

                ColorModeButton()
                HamburgerButton(onClick = { menuState = SideMenuState.OPEN })

                if (menuState != SideMenuState.CLOSED) {
                    SideMenu(
                        menuState,
                        close = { menuState = menuState.close() },
                        onAnimationEnd = { if (menuState == SideMenuState.CLOSING) menuState = SideMenuState.CLOSED },
                    )
                }
            }
        }
    }
}

@Composable
private fun S2CLogo() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.gap(0.5.cssRem),
    ) {
        SpanText(
            "s2c",
            modifier = GradientTextStyle.toModifier()
                .fontWeight(FontWeight.Bold)
                .fontSize(1.25.cssRem)
                .fontFamily("JetBrains Mono", "monospace"),
        )
        SpanText(
            "svg-to-compose",
            modifier = Modifier
                .fontWeight(FontWeight.SemiBold)
                .fontSize(0.9.cssRem)
                .displayIfAtLeast(Breakpoint.SM),
        )
    }
}

@Composable
private fun MenuItems() {
    NavLink("#playground", "Playground")
    NavLink("#install", "Install")
    NavLink("#usage", "Usage")
    NavLink("#features", "Features")
}

@Composable
private fun GitHubButton() {
    Link(
        "https://github.com/rafaeltonholo/svg-to-compose",
        modifier = Modifier
            .textDecorationLine(TextDecorationLine.None)
            .display(DisplayStyle.Flex)
            .alignItems(AlignItems.Center)
            .gap(0.4.cssRem)
            .padding(leftRight = 0.75.cssRem, topBottom = 0.375.cssRem)
            .borderRadius(0.5.cssRem)
            .border(1.px, LineStyle.Solid, ColorMode.current.toSitePalette().border)
            .fontSize(0.8.cssRem)
            .fontWeight(FontWeight.Medium)
            .color(ColorMode.current.toSitePalette().muted)
            .transition(Transition.all(duration = 200.ms, timingFunction = TransitionTimingFunction.Ease)),
        variant = UncoloredLinkVariant,
    ) {
        FaGithub()
        SpanText("GitHub")
    }
}

@Composable
private fun ColorModeButton() {
    var colorMode by ColorMode.currentState
    IconButton(onClick = { colorMode = colorMode.opposite }) {
        if (colorMode.isLight) MoonIcon() else SunIcon()
    }
    Tooltip(ElementTarget.PreviousSibling, "Toggle color mode", placement = PopupPlacement.BottomRight)
}

@Composable
private fun HamburgerButton(onClick: () -> Unit) {
    IconButton(onClick) {
        HamburgerIcon()
    }
}

@Composable
private fun CloseButton(onClick: () -> Unit) {
    IconButton(onClick) {
        CloseIcon()
    }
}

val SideMenuSlideInAnim = Keyframes {
    from {
        Modifier.translateX(100.percent)
    }
    to {
        Modifier
    }
}

enum class SideMenuState {
    CLOSED,
    OPEN,

    CLOSING;
    fun close() = when (this) {
        CLOSED -> CLOSED
        OPEN -> CLOSING
        CLOSING -> CLOSING
    }
}

@Composable
private fun SideMenu(menuState: SideMenuState, close: () -> Unit, onAnimationEnd: () -> Unit) {
    Overlay(
        Modifier
            .setVariable(OverlayVars.BackgroundColor, Colors.Transparent)
            .onClick { close() },
    ) {
        key(menuState) {
            Column(
                Modifier
                    .fillMaxHeight()
                    .width(clamp(8.cssRem, 33.percent, 10.cssRem))
                    .align(Alignment.CenterEnd)
                    .padding(top = 1.cssRem, leftRight = 1.cssRem)
                    .gap(1.5.cssRem)
                    .backgroundColor(ColorMode.current.toSitePalette().nearBackground)
                    .animation(
                        SideMenuSlideInAnim.toAnimation(
                            duration = 200.ms,
                            timingFunction = if (menuState == SideMenuState.OPEN) AnimationTimingFunction.EaseOut else AnimationTimingFunction.EaseIn,
                            direction = if (menuState == SideMenuState.OPEN) AnimationDirection.Normal else AnimationDirection.Reverse,
                            fillMode = AnimationFillMode.Forwards,
                        ),
                    )
                    .borderRadius(topLeft = 2.cssRem)
                    .onClick { it.stopPropagation() }
                    .onAnimationEnd { onAnimationEnd() },
                horizontalAlignment = Alignment.End,
            ) {
                CloseButton(onClick = { close() })
                Column(
                    Modifier
                        .padding(right = 0.75.cssRem)
                        .gap(1.5.cssRem)
                        .fontSize(1.4.cssRem),
                    horizontalAlignment = Alignment.End,
                ) {
                    MenuItems()
                    Link(
                        "https://github.com/rafaeltonholo/svg-to-compose",
                        modifier = Modifier
                            .textDecorationLine(TextDecorationLine.None)
                            .display(DisplayStyle.Flex)
                            .alignItems(AlignItems.Center)
                            .gap(0.4.cssRem)
                            .color(ColorMode.current.toSitePalette().muted),
                        variant = UncoloredLinkVariant,
                    ) {
                        FaGithub()
                        SpanText("GitHub")
                    }
                }
            }
        }
    }
}
