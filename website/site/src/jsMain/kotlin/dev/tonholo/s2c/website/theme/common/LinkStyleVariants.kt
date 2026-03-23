package dev.tonholo.s2c.website.theme.common

import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.lightened
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.silk.components.navigation.LinkStyle
import com.varabyte.kobweb.silk.style.addVariant
import com.varabyte.kobweb.silk.style.selectors.hover
import dev.tonholo.s2c.website.theme.palette
import org.jetbrains.compose.web.css.ms

val SiteLinkStyleVariant = LinkStyle.addVariant {
    base {
        Modifier
            .textDecorationLine(TextDecorationLine.None)
            .color(palette.primary)
            .fontWeight(FontWeight.SemiBold)
            .transition {
                property("color")
                duration(200.ms)
                timingFunction(TransitionTimingFunction.EaseInOut)
            }
    }
    hover {
        Modifier
            .color(palette.primary.lightened(byPercent = 0.5f))
    }
}
