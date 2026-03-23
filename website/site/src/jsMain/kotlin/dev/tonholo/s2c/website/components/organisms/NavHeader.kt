package dev.tonholo.s2c.website.components.organisms

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.browser.dom.ElementTarget
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.css.autoLength
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
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.marginInline
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.onAnimationEnd
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.setVariable
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.translateX
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.graphics.Image
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
import dev.tonholo.s2c.website.components.atoms.IconButton
import dev.tonholo.s2c.website.components.atoms.NavLink
import dev.tonholo.s2c.website.components.molecules.DocNavDropdown
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.theme.toSitePalette
import kotlinx.browser.window
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
import org.w3c.dom.SMOOTH
import org.w3c.dom.ScrollBehavior
import org.w3c.dom.ScrollToOptions

val NavHeaderStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .fillMaxWidth()
        .maxWidth(72.cssRem)
        .marginInline(autoLength)
        .padding(leftRight = SiteTheme.dimensions.size.Lg, topBottom = SiteTheme.dimensions.size.Md)
}

val NavContainerStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .fillMaxWidth()
        .position(Position.Fixed)
        .top(0.px)
        .zIndex(value = 1000)
        .backgroundColor(colorMode.toSitePalette().background)
        .borderBottom(
            width = 1.px,
            style = LineStyle.Solid,
            color = palette.outline,
        )
}

@Composable
fun NavHeader(modifier: Modifier = Modifier) {
    Nav(
        attrs = NavContainerStyle.toModifier().then(modifier).toAttrs(),
    ) {
        Row(NavHeaderStyle.toModifier(), verticalAlignment = Alignment.CenterVertically) {
            S2CLogo()

            Spacer()

            Row(
                Modifier.gap(SiteTheme.dimensions.size.Xl).displayIfAtLeast(Breakpoint.MD),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                MenuItems()
                GitHubButton()
                ColorModeButton()
            }

            Row(
                Modifier
                    .fontSize(1.5.cssRem)
                    .gap(SiteTheme.dimensions.size.Lg)
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
        modifier = Modifier
            .gap(SiteTheme.dimensions.size.Sm)
            .cursor(Cursor.Pointer)
            .onClick {
                if (window.location.pathname == "/") {
                    window.scrollTo(
                        options = ScrollToOptions(
                            top = 0.0,
                            behavior = ScrollBehavior.SMOOTH,
                        ),
                    )
                } else {
                    window.location.href = "/"
                }
            },
    ) {
        Image(
            src = "/images/s2c-icon.svg",
            modifier = Modifier.size(1.25.cssRem),
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
    NavLink("/#playground", "Playground")
    NavLink("/#install", "Install")
    NavLink("/#usage", "Usage")
    NavLink("/#capabilities", "Capabilities")
    DocNavDropdown()
}

@Composable
private fun SideMenuItems() {
    val palette = ColorMode.current.toSitePalette()
    NavLink("/#playground", "Playground")
    NavLink("/#install", "Install")
    NavLink("/#usage", "Usage")
    NavLink("/#capabilities", "Capabilities")
    SpanText(
        "Docs",
        modifier = Modifier
            .fontWeight(FontWeight.SemiBold)
            .color(palette.onSurfaceVariant)
            .fontSize(0.75.cssRem)
            .padding(top = SiteTheme.dimensions.size.Sm),
    )
    NavLink("/docs/cli", "CLI")
    NavLink("/docs/gradle-plugin", "Gradle Plugin")
    NavLink("/api-docs/index.html", "API Reference")
}

val GitHubButtonStyle = CssStyle {
    base {
        val palette = colorMode.toSitePalette()
        Modifier
            .textDecorationLine(TextDecorationLine.None)
            .display(DisplayStyle.Flex)
            .alignItems(AlignItems.Center)
            .gap(0.4.cssRem)
            .padding(leftRight = SiteTheme.dimensions.size.Md, topBottom = SiteTheme.dimensions.size.Sm)
            .borderRadius(0.5.cssRem)
            .border(1.px, LineStyle.Solid, palette.outline)
            .fontSize(0.8.cssRem)
            .fontWeight(FontWeight.Medium)
            .color(palette.onSurfaceVariant)
            .transition(
                Transition.of("color", duration = 150.ms, timingFunction = TransitionTimingFunction.Ease),
                Transition.of("border-color", duration = 150.ms, timingFunction = TransitionTimingFunction.Ease),
            )
    }
    cssRule(":hover") {
        val palette = colorMode.toSitePalette()
        Modifier
            .color(palette.primary)
            .border(1.px, LineStyle.Solid, palette.primary)
    }
}

@Composable
private fun GitHubButton() {
    Link(
        "https://github.com/rafaeltonholo/svg-to-compose",
        modifier = GitHubButtonStyle.toModifier(),
        variant = UncoloredLinkVariant,
    ) {
        FaGithub()
        SpanText("GitHub")
    }
}

@Composable
private fun ColorModeButton() {
    var colorMode by ColorMode.currentState
    IconButton(onClick = { colorMode = colorMode.opposite }, ariaLabel = "Toggle color mode") {
        if (colorMode.isLight) MoonIcon() else SunIcon()
    }
    Tooltip(ElementTarget.PreviousSibling, "Toggle color mode", placement = PopupPlacement.BottomRight)
}

@Composable
private fun HamburgerButton(onClick: () -> Unit) {
    IconButton(onClick, ariaLabel = "Open menu") {
        HamburgerIcon()
    }
}

@Composable
private fun CloseButton(onClick: () -> Unit) {
    IconButton(onClick, ariaLabel = "Close menu") {
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

    CLOSING,

    ;

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
                    .padding(top = SiteTheme.dimensions.size.Lg, leftRight = SiteTheme.dimensions.size.Lg)
                    .gap(SiteTheme.dimensions.size.Xl)
                    .backgroundColor(ColorMode.current.toSitePalette().surface)
                    .animation(
                        SideMenuSlideInAnim.toAnimation(
                            duration = 200.ms,
                            timingFunction = if (menuState ==
                                SideMenuState.OPEN
                            ) {
                                AnimationTimingFunction.EaseOut
                            } else {
                                AnimationTimingFunction.EaseIn
                            },
                            direction = if (menuState ==
                                SideMenuState.OPEN
                            ) {
                                AnimationDirection.Normal
                            } else {
                                AnimationDirection.Reverse
                            },
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
                        .padding(right = SiteTheme.dimensions.size.Md)
                        .gap(SiteTheme.dimensions.size.Xl)
                        .fontSize(1.4.cssRem),
                    horizontalAlignment = Alignment.End,
                ) {
                    SideMenuItems()
                    Link(
                        "https://github.com/rafaeltonholo/svg-to-compose",
                        modifier = Modifier
                            .textDecorationLine(TextDecorationLine.None)
                            .display(DisplayStyle.Flex)
                            .alignItems(AlignItems.Center)
                            .gap(0.4.cssRem)
                            .color(ColorMode.current.toSitePalette().onSurfaceVariant),
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
