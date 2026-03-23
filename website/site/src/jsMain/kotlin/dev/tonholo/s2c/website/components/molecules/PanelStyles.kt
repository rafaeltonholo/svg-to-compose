package dev.tonholo.s2c.website.components.molecules

import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderBottom
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.theme.toSitePalette
import dev.tonholo.s2c.website.theme.typography.FontFamilies
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px

val PanelHeaderStyle = CssStyle.base {
    Modifier
        .fontFamily(values = FontFamilies.mono)
        .fontSize(0.75.cssRem)
        .color(colorMode.toSitePalette().onSurfaceVariant)
        .padding(topBottom = SiteTheme.dimensions.size.Sm, leftRight = SiteTheme.dimensions.size.Lg)
        .backgroundColor(colorMode.toSitePalette().surfaceVariant)
        .borderBottom {
            width(1.px)
            style(LineStyle.Solid)
            color(colorMode.toSitePalette().outlineVariant)
        }
}
