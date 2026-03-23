package dev.tonholo.s2c.website.components.molecules.footer

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.theme.common.SiteLinkStyleVariant
import dev.tonholo.s2c.website.theme.toSitePalette
import dev.tonholo.s2c.website.theme.typography.FontFamilies
import org.jetbrains.compose.web.css.cssRem

@Composable
fun Attribution(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(0.375.cssRem),
        modifier = AttributionStyle.toModifier().then(modifier),
    ) {
        SpanText("Built with ❤️ by")
        Link(path = "https://rafael.tonholo.dev", text = "Rafael Tonholo", variant = AttributionLinkVariant)
        SpanText("· Open source on")
        Link(
            path = "https://github.com/rafaeltonholo/svg-to-compose",
            text = "GitHub",
            variant = AttributionLinkVariant,
        )
    }
}

val AttributionStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .fontFamily(values = FontFamilies.sans)
        .fontSize(0.75.cssRem)
        .lineHeight(1.125.cssRem)
        .color(palette.onSurfaceVariant)
}

val AttributionLinkVariant = SiteLinkStyleVariant
