package dev.tonholo.s2c.website

import com.varabyte.kobweb.compose.css.Animation
import com.varabyte.kobweb.compose.css.AnimationIterationCount
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.css.TextTransform
import com.varabyte.kobweb.compose.css.functions.clamp
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.animation
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.letterSpacing
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.outline
import com.varabyte.kobweb.compose.ui.modifiers.outlineOffset
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.scrollBehavior
import com.varabyte.kobweb.compose.ui.modifiers.setVariable
import com.varabyte.kobweb.compose.ui.modifiers.textTransform
import com.varabyte.kobweb.compose.ui.modifiers.transform
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.silk.components.forms.ButtonStyle
import com.varabyte.kobweb.silk.components.forms.ButtonVars
import com.varabyte.kobweb.silk.components.layout.HorizontalDividerStyle
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.init.registerStyleBase
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.addVariantBase
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.theme.modifyStyleBase
import dev.tonholo.s2c.website.theme.typography.FontFamilies
import org.jetbrains.compose.web.css.CSSMediaQuery
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.StylePropertyValue
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.deg
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vw

// Animation keyframes
val SpinKeyframes = Keyframes {
    from { Modifier.transform { rotate(0.deg) } }
    to { Modifier.transform { rotate(360.deg) } }
}

@InitSilk
fun initSiteStyles(ctx: InitSilkContext) {
    ctx.stylesheet.registerStyle("html") {
        cssRule(CSSMediaQuery.MediaFeature("prefers-reduced-motion", StylePropertyValue("no-preference"))) {
            Modifier.scrollBehavior(ScrollBehavior.Smooth)
        }
    }

    ctx.stylesheet.registerStyle("*") {
        cssRule(CSSMediaQuery.MediaFeature("prefers-reduced-motion", StylePropertyValue("reduce"))) {
            Modifier
                .animation(
                    Animation.of(
                        duration = 0.01.ms,
                        iterationCount = AnimationIterationCount.of(1),
                        name = "reduced-motion",
                    ),
                )
                .transition {
                    duration(0.01.ms)
                }
        }
    }

    ctx.stylesheet.registerStyleBase("body") {
        Modifier
            .fontFamily(values = FontFamilies.sans)
            .fontSize(value = 16.px)
            .lineHeight(value = 1.5)
    }

    ctx.stylesheet.registerStyleBase(":focus-visible") {
        Modifier
            .outline(width = 2.px, style = LineStyle.Solid, color = SitePalettes.light.primary)
            .outlineOffset(2.px)
            .borderRadius(2.px)
    }

    ctx.stylesheet.registerStyleBase(":focus:not(:focus-visible)") {
        Modifier.outline(style = LineStyle.None)
    }

    ctx.theme.modifyStyleBase(HorizontalDividerStyle) {
        Modifier.fillMaxWidth()
    }
}

// Typography
private const val DISPLAY_LINE_HEIGHT = 1.1
private const val DISPLAY_LETTER_SPACING = -0.02

val DisplayTextStyle = CssStyle.base {
    Modifier
        .fontSize(clamp(1.75.cssRem, 5.vw, 3.5.cssRem))
        .fontWeight(FontWeight.Bold)
        .lineHeight(DISPLAY_LINE_HEIGHT)
        .letterSpacing(DISPLAY_LETTER_SPACING.em)
}

private const val HEADLINE_LINE_HEIGHT = 1.2

val HeadlineTextStyle = CssStyle.base {
    Modifier
        .fontSize(clamp(1.4.cssRem, 3.vw, 2.cssRem))
        .fontWeight(FontWeight.Bold)
        .lineHeight(HEADLINE_LINE_HEIGHT)
}

private const val SUBHEADLINE_LINE_HEIGHT = 1.7

val SubheadlineTextStyle = CssStyle.base {
    Modifier
        .fontSize(clamp(0.9.cssRem, 2.vw, 1.1.cssRem))
        .lineHeight(SUBHEADLINE_LINE_HEIGHT)
        .color(colorMode.toSitePalette().onSurfaceVariant)
}

val MonospaceTextStyle = CssStyle.base {
    Modifier
        .fontFamily(values = FontFamilies.mono)
        .fontSize(value = 0.75.cssRem)
        .lineHeight(value = 1.6)
}

val LabelTextStyle = CssStyle.base {
    Modifier
        .fontSize(0.75.cssRem)
        .fontWeight(FontWeight.Medium)
        .letterSpacing(0.05.em)
        .textTransform(TextTransform.Uppercase)
}

// Button variants
val CircleButtonVariant = ButtonStyle.addVariantBase {
    Modifier.padding(0.px).borderRadius(50.percent)
}

val UncoloredButtonVariant = ButtonStyle.addVariantBase {
    Modifier.setVariable(ButtonVars.BackgroundDefaultColor, Colors.Transparent)
}
