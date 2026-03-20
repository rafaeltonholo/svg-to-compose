package dev.tonholo.s2c.website.components.atoms

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.animation
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaChevronDown
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.animation.toAnimation
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.PulseKeyframes
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.dom.Div

val ScrollIndicatorStyle = CssStyle.base {
    Modifier
        .margin(top = 3.cssRem)
        .display(DisplayStyle.Flex)
        .styleModifier { property("flex-direction", "column") }
        .alignItems(AlignItems.Center)
        .gap(0.5.cssRem)
        .color(colorMode.toSitePalette().muted)
        .fontSize(0.8.cssRem)
        .animation(
            PulseKeyframes.toAnimation(
                duration = 2000.ms,
            ),
        )
        .styleModifier {
            property("animation-iteration-count", "infinite")
            property("animation-delay", "1800ms")
        }
}

@Composable
fun ScrollIndicator() {
    Div(attrs = ScrollIndicatorStyle.toModifier().toAttrs()) {
        SpanText("Scroll to explore")
        FaChevronDown()
    }
}
