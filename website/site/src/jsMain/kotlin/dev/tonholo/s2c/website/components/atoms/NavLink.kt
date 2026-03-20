package dev.tonholo.s2c.website.components.atoms

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.navigation.UncoloredLinkVariant
import com.varabyte.kobweb.silk.components.navigation.UndecoratedLinkVariant
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.cssRem

@Composable
fun NavLink(href: String, text: String) {
    Link(
        href,
        text,
        variant = UndecoratedLinkVariant.then(UncoloredLinkVariant),
        modifier = Modifier
            .color(ColorMode.current.toSitePalette().muted)
            .fontWeight(FontWeight.Medium)
            .fontSize(0.875.cssRem)
            .styleModifier {
                property("transition", "color 0.2s ease")
            },
    )
}
