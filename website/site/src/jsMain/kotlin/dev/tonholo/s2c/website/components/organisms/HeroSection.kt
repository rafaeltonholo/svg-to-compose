package dev.tonholo.s2c.website.components.organisms

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.animation
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.bottom
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.left
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.right
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.animation.toAnimation
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.DisplayTextStyle
import dev.tonholo.s2c.website.FadeInUpKeyframes
import dev.tonholo.s2c.website.GradientTextStyle
import dev.tonholo.s2c.website.SubheadlineTextStyle
import dev.tonholo.s2c.website.components.atoms.ScrollIndicator
import dev.tonholo.s2c.website.components.molecules.BadgeRow
import dev.tonholo.s2c.website.components.molecules.CodePreview
import dev.tonholo.s2c.website.components.molecules.CtaButtons
import dev.tonholo.s2c.website.components.molecules.PlatformBadges
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.AnimationFillMode
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.css.vw
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Section
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

// region Styles

val HeroSectionStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .minHeight(100.vh)
            .position(Position.Relative)
            .overflow(Overflow.Hidden)
            .display(DisplayStyle.Flex)
            .styleModifier {
                property("flex-direction", "column")
                property("justify-content", "center")
            }
            .alignItems(AlignItems.Center)
            .padding(top = 6.cssRem, bottom = 0.cssRem, leftRight = 1.cssRem)
    }
    Breakpoint.SM {
        Modifier.padding(top = 6.cssRem, bottom = 3.cssRem, leftRight = 1.5.cssRem)
    }
    Breakpoint.MD {
        Modifier.padding(top = 8.cssRem, bottom = 5.cssRem, leftRight = 2.cssRem)
    }
}

val HeroContentStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .maxWidth(72.cssRem)
        .display(DisplayStyle.Flex)
        .styleModifier { property("flex-direction", "column") }
        .alignItems(AlignItems.Center)
        .gap(2.cssRem)
        .position(Position.Relative)
        .zIndex(1)
}

val GlowPurpleStyle = CssStyle.base {
    Modifier
        .position(Position.Absolute)
        .top(value = (-10).percent)
        .left(value = (-10).percent)
        .width(40.vw)
        .height(40.vw)
        .borderRadius(50.percent)
        .styleModifier {
            property("background-image", "radial-gradient(circle, rgba(127, 82, 255, 0.2) 0%, transparent 70%)")
            property("filter", "blur(80px)")
            property("pointer-events", "none")
        }
        .zIndex(0)
}

val GlowTealStyle = CssStyle.base {
    Modifier
        .position(Position.Absolute)
        .bottom(value = (-10).percent)
        .right(value = (-10).percent)
        .width(40.vw)
        .height(40.vw)
        .borderRadius(50.percent)
        .styleModifier {
            property("background-image", "radial-gradient(circle, rgba(0, 212, 170, 0.15) 0%, transparent 70%)")
            property("filter", "blur(80px)")
            property("pointer-events", "none")
        }
        .zIndex(0)
}

// endregion

@Composable
private fun Headline() {
    Div(attrs = DisplayTextStyle.toAttrs()) {
        SpanText("SVG to Compose,")
        Div {
            Span(attrs = GradientTextStyle.toModifier().toAttrs()) {
                Text("Instantly.")
            }
        }
    }
}

@Composable
private fun Subtitle() {
    Div(attrs = SubheadlineTextStyle.toAttrs()) {
        SpanText("Convert SVG and Android Vector Drawables into type-safe Kotlin ")
        Span(
            attrs = Modifier.color(ColorMode.current.toSitePalette().brand.teal).toAttrs(),
        ) {
            Text("ImageVector")
        }
        SpanText(" code. CLI. Gradle Plugin. Kotlin Multiplatform.")
    }
}

@Composable
fun HeroSection() {
    Section(
        attrs = HeroSectionStyle.toModifier()
            .id("hero")
            .toAttrs(),
    ) {
        // Background glow effects
        Div(attrs = GlowPurpleStyle.toModifier().toAttrs())
        Div(attrs = GlowTealStyle.toModifier().toAttrs())

        // Main content
        Div(attrs = HeroContentStyle.toModifier().toAttrs()) {
            Div(
                attrs = Modifier
                    .styleModifier { property("opacity", "0") }
                    .animation(
                        FadeInUpKeyframes.toAnimation(
                            duration = 500.ms,
                            delay = 500.ms,
                            fillMode = AnimationFillMode.Forwards,
                        ),
                    )
                    .toAttrs(),
            ) {
                BadgeRow()
            }
            Div(
                attrs = Modifier
                    .styleModifier { property("opacity", "0") }
                    .animation(
                        FadeInUpKeyframes.toAnimation(
                            duration = 600.ms,
                            delay = 100.ms,
                            fillMode = AnimationFillMode.Forwards,
                        ),
                    )
                    .toAttrs(),
            ) {
                Headline()
            }
            Div(
                attrs = Modifier
                    .styleModifier { property("opacity", "0") }
                    .animation(
                        FadeInUpKeyframes.toAnimation(
                            duration = 600.ms,
                            delay = 200.ms,
                            fillMode = AnimationFillMode.Forwards,
                        ),
                    )
                    .toAttrs(),
            ) {
                Subtitle()
            }
            Div(
                attrs = Modifier
                    .styleModifier { property("opacity", "0") }
                    .animation(
                        FadeInUpKeyframes.toAnimation(
                            duration = 600.ms,
                            delay = 300.ms,
                            fillMode = AnimationFillMode.Forwards,
                        ),
                    )
                    .toAttrs(),
            ) {
                CtaButtons()
            }
            Div(
                attrs = Modifier
                    .styleModifier { property("opacity", "0") }
                    .animation(
                        FadeInUpKeyframes.toAnimation(
                            duration = 600.ms,
                            delay = 400.ms,
                            fillMode = AnimationFillMode.Forwards,
                        ),
                    )
                    .toAttrs(),
            ) {
                PlatformBadges()
            }
            Div(
                attrs = Modifier
                    .styleModifier { property("opacity", "0") }
                    .animation(
                        FadeInUpKeyframes.toAnimation(
                            duration = 800.ms,
                            delay = 500.ms,
                            fillMode = AnimationFillMode.Forwards,
                        ),
                    )
                    .toAttrs(),
            ) {
                CodePreview()
            }
            Div(
                attrs = Modifier
                    .styleModifier { property("opacity", "0") }
                    .animation(
                        FadeInUpKeyframes.toAnimation(
                            duration = 600.ms,
                            delay = 1200.ms,
                            fillMode = AnimationFillMode.Forwards,
                        ),
                    )
                    .toAttrs(),
            ) {
                ScrollIndicator()
            }
        }
    }
}
