package dev.tonholo.s2c.website.components.atoms

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.navigation.UncoloredLinkVariant
import com.varabyte.kobweb.silk.components.navigation.UndecoratedLinkVariant
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.theme.toSitePalette
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.ms

val NavLinkStyle = CssStyle {
    base {
        Modifier
            .color(colorMode.toSitePalette().onSurfaceVariant)
            .fontWeight(FontWeight.Medium)
            .fontSize(0.875.cssRem)
            .transition(
                Transition.of("color", duration = 150.ms, timingFunction = TransitionTimingFunction.Ease),
            )
    }
    cssRule(":hover") {
        Modifier.color(colorMode.toSitePalette().primary)
    }
}

@Composable
fun NavLink(href: String, text: String, modifier: Modifier = Modifier) {
    Link(
        href,
        text,
        variant = UndecoratedLinkVariant.then(UncoloredLinkVariant),
        modifier = NavLinkStyle.toModifier().then(modifier),
    )
}
