package dev.tonholo.s2c.website.components.organisms.docs

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.ListStyleType
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.lightened
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.listStyle
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import dev.tonholo.s2c.website.components.molecules.CalloutVariant
import dev.tonholo.s2c.website.components.molecules.resolveColors
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.theme.toSitePalette
import org.jetbrains.compose.web.css.cssRem

private const val DOCS_BODY_LINE_HEIGHT = 1.7
private const val DOCS_BODY_FONT_SIZE_REM = 1.0

val DocsBodyTextStyle = CssStyle.base {
    Modifier
        .fontSize(DOCS_BODY_FONT_SIZE_REM.cssRem)
        .lineHeight(DOCS_BODY_LINE_HEIGHT)
        .color(colorMode.toSitePalette().onSurface)
}

val DocsBulletListStyle = CssStyle.base {
    Modifier
        .padding(left = SiteTheme.dimensions.size.Xl)
        .margin(top = SiteTheme.dimensions.size.Sm, bottom = SiteTheme.dimensions.size.Sm)
        .listStyle(ListStyleType.Disc)
        .fontSize(DOCS_BODY_FONT_SIZE_REM.cssRem)
        .lineHeight(DOCS_BODY_LINE_HEIGHT)
        .color(colorMode.toSitePalette().onSurface)
}

@Composable
fun CalloutVariant.resolveInlineCodeColors(): Pair<Color, Color> {
    val (borderColor, containerColor) = resolveColors(SiteTheme.palette)
    return borderColor.darkened(byPercent = .25f) to containerColor.lightened(byPercent = .25f)
}
