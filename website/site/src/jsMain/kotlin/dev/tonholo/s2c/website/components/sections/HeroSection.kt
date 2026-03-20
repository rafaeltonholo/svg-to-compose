package dev.tonholo.s2c.website.components.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.bottom
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.left
import com.varabyte.kobweb.compose.ui.modifiers.letterSpacing
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.right
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaAndroid
import com.varabyte.kobweb.silk.components.icons.fa.FaApple
import com.varabyte.kobweb.silk.components.icons.fa.FaBullseye
import com.varabyte.kobweb.silk.components.icons.fa.FaChevronDown
import com.varabyte.kobweb.silk.components.icons.fa.FaGamepad
import com.varabyte.kobweb.silk.components.icons.fa.FaLinux
import com.varabyte.kobweb.silk.components.icons.fa.FaRocket
import com.varabyte.kobweb.silk.components.icons.fa.FaWindows
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.navigation.UncoloredLinkVariant
import com.varabyte.kobweb.silk.components.navigation.UndecoratedLinkVariant
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.DisplayTextStyle
import dev.tonholo.s2c.website.GradientTextStyle
import dev.tonholo.s2c.website.SubheadlineTextStyle
import dev.tonholo.s2c.website.components.widgets.Badge
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.css.vw
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Pre
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

val HeroBadgeRowStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .styleModifier { property("flex-wrap", "wrap") }
        .gap(0.5.cssRem)
        .styleModifier { property("justify-content", "center") }
}

val HeroCtaRowStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .styleModifier {
            property("flex-wrap", "wrap")
            property("justify-content", "center")
        }
        .gap(1.cssRem)
}

val PrimaryCtaStyle = CssStyle.base {
    Modifier
        .color(Colors.White)
        .borderRadius(0.75.cssRem)
        .padding(topBottom = 0.75.cssRem, leftRight = 1.5.cssRem)
        .fontWeight(FontWeight.SemiBold)
        .textDecorationLine(TextDecorationLine.None)
        .cursor(Cursor.Pointer)
        .border(0.px, LineStyle.None, Colors.Transparent)
        .styleModifier {
            property("background-image", "linear-gradient(135deg, #7F52FF, #5A30DD)")
            property("transition", "all 0.2s ease")
            property("box-shadow", "0 4px 15px rgba(127, 82, 255, 0.3)")
        }
}

val SecondaryCtaStyle = CssStyle.base {
    Modifier
        .backgroundColor(Colors.Transparent)
        .border(1.px, LineStyle.Solid, Color.rgba(127, 82, 255, 0.4f))
        .borderRadius(0.75.cssRem)
        .padding(topBottom = 0.75.cssRem, leftRight = 1.5.cssRem)
        .textDecorationLine(TextDecorationLine.None)
        .cursor(Cursor.Pointer)
        .styleModifier {
            property("transition", "all 0.2s ease")
        }
}

val PlatformBadgeRowStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .styleModifier {
            property("flex-wrap", "wrap")
            property("justify-content", "center")
        }
        .gap(0.5.cssRem)
}

val PlatformPillStyle = CssStyle.base {
    Modifier
        .fontSize(0.75.cssRem)
        .color(colorMode.toSitePalette().muted)
        .padding(topBottom = 0.35.cssRem, leftRight = 0.75.cssRem)
        .borderRadius(9999.px)
        .border(1.px, LineStyle.Solid, colorMode.toSitePalette().border)
        .backgroundColor(colorMode.toSitePalette().surfaceAlt)
}

val CodePreviewPanelStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .maxWidth(64.cssRem)
            .borderRadius(1.cssRem)
            .border(1.px, LineStyle.Solid, colorMode.toSitePalette().borderStrong)
            .overflow(Overflow.Hidden)
            .styleModifier {
                property("box-shadow", "0 0 80px rgba(127, 82, 255, 0.12), 0 32px 64px rgba(0,0,0,0.5)")
            }
    }
}

val CodePreviewGridStyle = CssStyle {
    base {
        Modifier
            .display(DisplayStyle.Grid)
            .styleModifier { property("grid-template-columns", "1fr") }
    }
    Breakpoint.MD {
        Modifier.styleModifier { property("grid-template-columns", "1fr 1fr") }
    }
}

val CodePanelLabelStyle = CssStyle.base {
    Modifier
        .fontFamily("JetBrains Mono", "monospace")
        .fontSize(0.7.cssRem)
        .fontWeight(FontWeight.Medium)
        .letterSpacing(0.05.em)
        .padding(topBottom = 0.5.cssRem, leftRight = 1.cssRem)
        .color(colorMode.toSitePalette().muted)
}

val CodePanelStyle = CssStyle.base {
    Modifier
        .fontFamily("JetBrains Mono", "monospace")
        .fontSize(0.7.cssRem)
        .lineHeight(1.6)
        .padding(1.cssRem)
        .overflow(Overflow.Auto)
        .margin(0.px)
        .styleModifier { property("white-space", "pre") }
}

val GlowPurpleStyle = CssStyle.base {
    Modifier
        .position(Position.Absolute)
        .top((-10).percent)
        .left((-10).percent)
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
        .bottom((-10).percent)
        .right((-10).percent)
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

val ScrollIndicatorStyle = CssStyle.base {
    Modifier
        .margin(top = 3.cssRem)
        .display(DisplayStyle.Flex)
        .styleModifier { property("flex-direction", "column") }
        .alignItems(AlignItems.Center)
        .gap(0.5.cssRem)
        .color(colorMode.toSitePalette().muted)
        .fontSize(0.8.cssRem)
}

val WindowChromeBarStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .alignItems(AlignItems.Center)
        .gap(0.5.cssRem)
        .padding(topBottom = 0.6.cssRem, leftRight = 0.75.cssRem)
        .backgroundColor(colorMode.toSitePalette().surfaceHeader)
}

val WindowChromeDotStyle = CssStyle.base {
    Modifier
        .width(0.75.cssRem)
        .height(0.75.cssRem)
        .borderRadius(50.percent)
}

// endregion

// region Constants

@Suppress("MaxLineLength")
private const val HEART_SVG_CODE = """<svg viewBox="0 0 24 24">
  <path fill="#E91E63"
    d="M12 21.35l-1.45-1.32
       C5.4 15.36 2 12.28
       2 8.5 2 5.42 4.42 3
       7.5 3c1.74 0 3.41.81
       4.5 2.09C13.09 3.81
       14.76 3 16.5 3 19.58
       3 22 5.42 22 8.5c0
       3.78-3.4 6.86-8.55
       11.54L12 21.35z"/>
</svg>"""

@Suppress("MaxLineLength")
private const val HEART_KOTLIN_CODE = """val HeartIcon: ImageVector
    get() {
        if (_heartIcon != null) {
            return _heartIcon!!
        }
        _heartIcon = ImageVector.Builder(
            name = "HeartIcon",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f,
        ).apply {
            path(
                fill = SolidColor(
                    Color(0xFFE91E63)
                ),
            ) {
                moveTo(12f, 21.35f)
                lineTo(-1.45f, -1.32f)
                curveTo(5.4f, 15.36f,
                    2f, 12.28f, 2f, 8.5f)
                curveTo(2f, 5.42f, 4.42f,
                    3f, 7.5f, 3f)
                close()
            }
        }.build()
        return _heartIcon!!
    }

private var _heartIcon: ImageVector? = null"""

private val platforms = listOf("macOS", "Linux", "Windows", "Android", "KMP")

// endregion

@Composable
private fun WindowChrome(title: String) {
    val palette = ColorMode.current.toSitePalette()
    Div(attrs = WindowChromeBarStyle.toModifier().toAttrs()) {
        Div(
            attrs = WindowChromeDotStyle.toModifier()
                .backgroundColor(palette.windowChrome.red)
                .toAttrs(),
        )
        Div(
            attrs = WindowChromeDotStyle.toModifier()
                .backgroundColor(palette.windowChrome.yellow)
                .toAttrs(),
        )
        Div(
            attrs = WindowChromeDotStyle.toModifier()
                .backgroundColor(palette.windowChrome.green)
                .toAttrs(),
        )
        SpanText(
            title,
            modifier = Modifier
                .fontSize(0.7.cssRem)
                .color(palette.muted)
                .margin(left = 0.5.cssRem),
        )
    }
}

@Composable
private fun BadgeRow() {
    Div(attrs = HeroBadgeRowStyle.toModifier().toAttrs()) {
        Badge(
            text = "v2.1.2",
            color = Color.rgb(0x7F52FF),
            modifier = Modifier.fontFamily("JetBrains Mono", "monospace"),
        )
        Badge(text = "Kotlin Multiplatform", color = Color.rgb(0x00D4AA))
        Badge(text = "Maven Central", color = Color.rgb(0xF78C6C))
        Badge(text = "MIT License", color = Color.rgb(0xA5D6A7))
    }
}

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
private fun CtaButtons() {
    Div(attrs = HeroCtaRowStyle.toModifier().toAttrs()) {
        Link(
            path = "#playground",
            modifier = PrimaryCtaStyle.toModifier()
                .display(DisplayStyle.Flex)
                .alignItems(AlignItems.Center)
                .gap(0.5.cssRem),
            variant = UndecoratedLinkVariant.then(UncoloredLinkVariant),
        ) {
            FaGamepad(size = IconSize.SM)
            SpanText("Try the Playground")
        }
        Link(
            path = "#install",
            modifier = SecondaryCtaStyle.toModifier()
                .display(DisplayStyle.Flex)
                .alignItems(AlignItems.Center)
                .gap(0.5.cssRem),
            variant = UndecoratedLinkVariant.then(UncoloredLinkVariant),
        ) {
            FaRocket(size = IconSize.SM)
            SpanText("Get Started")
        }
    }
}

@Composable
private fun PlatformBadges() {
    Div(attrs = PlatformBadgeRowStyle.toModifier().toAttrs()) {
        PlatformPill { FaApple(size = IconSize.SM); SpanText("macOS") }
        PlatformPill { FaLinux(size = IconSize.SM); SpanText("Linux") }
        PlatformPill { FaWindows(size = IconSize.SM); SpanText("Windows") }
        PlatformPill { FaAndroid(size = IconSize.SM); SpanText("Android") }
        PlatformPill { FaBullseye(size = IconSize.SM); SpanText("KMP") }
    }
}

@Composable
private fun PlatformPill(content: @Composable () -> Unit) {
    Span(
        attrs = PlatformPillStyle.toModifier()
            .display(DisplayStyle.Flex)
            .alignItems(AlignItems.Center)
            .gap(0.35.cssRem)
            .toAttrs(),
    ) {
        content()
    }
}

@Composable
private fun CodePreview() {
    val palette = ColorMode.current.toSitePalette()
    Div(attrs = CodePreviewPanelStyle.toModifier().toAttrs()) {
        WindowChrome(title = "svg-to-compose transform")
        Div(attrs = CodePreviewGridStyle.toModifier().toAttrs()) {
            // Left panel - SVG input
            Div(
                attrs = Modifier
                    .backgroundColor(palette.surfaceAlt)
                    .styleModifier { property("border-right", "1px solid ${palette.borderStrong}") }
                    .toAttrs(),
            ) {
                Div(
                    attrs = CodePanelLabelStyle.toModifier()
                        .backgroundColor(palette.surfaceHeader)
                        .display(DisplayStyle.Flex)
                        .alignItems(AlignItems.Center)
                        .gap(0.5.cssRem)
                        .toAttrs(),
                ) {
                    Span(
                        attrs = Modifier
                            .backgroundColor(Color.rgba(240, 113, 120, 0.2f))
                            .color(Color.rgb(0xF07178))
                            .borderRadius(0.25.cssRem)
                            .padding(topBottom = 0.1.cssRem, leftRight = 0.4.cssRem)
                            .fontSize(0.65.cssRem)
                            .fontWeight(FontWeight.Bold)
                            .toAttrs(),
                    ) {
                        Text("INPUT")
                    }
                    SpanText("heart.svg")
                }
                Pre(attrs = CodePanelStyle.toModifier().color(palette.muted).toAttrs()) {
                    Text(HEART_SVG_CODE)
                }
            }
            // Right panel - Kotlin output
            Div(attrs = Modifier.backgroundColor(palette.surface).toAttrs()) {
                Div(
                    attrs = CodePanelLabelStyle.toModifier()
                        .backgroundColor(palette.surfaceHeader)
                        .display(DisplayStyle.Flex)
                        .alignItems(AlignItems.Center)
                        .gap(0.5.cssRem)
                        .toAttrs(),
                ) {
                    Span(
                        attrs = Modifier
                            .backgroundColor(Color.rgba(207, 154, 255, 0.2f))
                            .color(Color.rgb(0xCF9AFF))
                            .borderRadius(0.25.cssRem)
                            .padding(topBottom = 0.1.cssRem, leftRight = 0.4.cssRem)
                            .fontSize(0.65.cssRem)
                            .fontWeight(FontWeight.Bold)
                            .toAttrs(),
                    ) {
                        Text("OUTPUT")
                    }
                    SpanText("HeartIcon.kt")
                }
                Pre(attrs = CodePanelStyle.toModifier().toAttrs()) {
                    Text(HEART_KOTLIN_CODE)
                }
            }
        }
    }
}

@Composable
private fun ScrollIndicator() {
    Div(attrs = ScrollIndicatorStyle.toModifier().toAttrs()) {
        SpanText("Scroll to explore")
        FaChevronDown()
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
            BadgeRow()
            Headline()
            Subtitle()
            CtaButtons()
            PlatformBadges()
            CodePreview()
            ScrollIndicator()
        }
    }
}
