package dev.tonholo.s2c.website

import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.TextTransform
import com.varabyte.kobweb.compose.css.functions.clamp
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.letterSpacing
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.scrollBehavior
import com.varabyte.kobweb.compose.ui.modifiers.setVariable
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.textTransform
import com.varabyte.kobweb.compose.ui.modifiers.translateY
import com.varabyte.kobweb.compose.ui.styleModifier
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
import org.jetbrains.compose.web.css.CSSMediaQuery
import org.jetbrains.compose.web.css.StylePropertyValue
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vw

// Animation keyframes
val FadeInUpKeyframes = Keyframes {
    from { Modifier.opacity(0).translateY(20.px) }
    to { Modifier.opacity(1).translateY(0.px) }
}

val FadeInKeyframes = Keyframes {
    from { Modifier.opacity(0) }
    to { Modifier.opacity(1) }
}

val PulseKeyframes = Keyframes {
    from { Modifier.opacity(1) }
    50.percent { Modifier.opacity(0.5) }
    to { Modifier.opacity(1) }
}

@InitSilk
fun initSiteStyles(ctx: InitSilkContext) {
    ctx.stylesheet.registerStyle("html") {
        cssRule(CSSMediaQuery.MediaFeature("prefers-reduced-motion", StylePropertyValue("no-preference"))) {
            Modifier.scrollBehavior(com.varabyte.kobweb.compose.css.ScrollBehavior.Smooth)
        }
    }

    ctx.stylesheet.registerStyle("*") {
        cssRule(CSSMediaQuery.MediaFeature("prefers-reduced-motion", StylePropertyValue("reduce"))) {
            Modifier.styleModifier {
                property("animation-duration", "0.01ms !important")
                property("animation-iteration-count", "1 !important")
                property("transition-duration", "0.01ms !important")
            }
        }
    }

    ctx.stylesheet.registerStyleBase("body") {
        Modifier
            .fontFamily("Inter", "sans-serif")
            .fontSize(value = 16.px)
            .lineHeight(value = 1.5)
    }

    ctx.theme.modifyStyleBase(HorizontalDividerStyle) {
        Modifier.fillMaxWidth()
    }
}

// Typography
val DisplayTextStyle = CssStyle.base {
    Modifier
        .fontSize(clamp(2.5.cssRem, 6.vw, 5.cssRem))
        .fontWeight(FontWeight.Bold)
        .lineHeight(1.1)
        .letterSpacing((-0.02).em)
        .textAlign(TextAlign.Center)
}

val HeadlineTextStyle = CssStyle.base {
    Modifier
        .fontSize(clamp(1.8.cssRem, 4.vw, 2.8.cssRem))
        .fontWeight(FontWeight.Bold)
        .lineHeight(1.2)
        .textAlign(TextAlign.Center)
}

val SubheadlineTextStyle = CssStyle.base {
    Modifier
        .fontSize(clamp(1.cssRem, 2.vw, 1.2.cssRem))
        .lineHeight(1.7)
        .textAlign(TextAlign.Center)
        .color(colorMode.toSitePalette().muted)
}

val MonospaceTextStyle = CssStyle.base {
    Modifier
        .fontFamily("JetBrains Mono", "monospace")
        .fontSize(value = 0.75.cssRem)
        .lineHeight(value = 1.6)
}

val GradientTextStyle = CssStyle.base {
    Modifier
        .styleModifier { property("background-image", "linear-gradient(135deg, #7F52FF 0%, #00D4AA 100%)") }
        .styleModifier {
            property("-webkit-background-clip", "text")
            property("-webkit-text-fill-color", "transparent")
            property("background-clip", "text")
        }
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
