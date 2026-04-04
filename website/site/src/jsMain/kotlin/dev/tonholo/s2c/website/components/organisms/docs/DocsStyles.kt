package dev.tonholo.s2c.website.components.organisms.docs

import com.varabyte.kobweb.compose.css.BorderCollapse
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.ListStyleType
import com.varabyte.kobweb.compose.css.TableLayout
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderBottom
import com.varabyte.kobweb.compose.ui.modifiers.borderCollapse
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.listStyle
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.tableLayout
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.theme.toSitePalette
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px

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

private const val TABLE_LINE_HEIGHT = 1.5

val DocsTableStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .fillMaxWidth()
        .backgroundColor(palette.surfaceVariant)
        .fontSize(0.75.cssRem)
        .lineHeight(TABLE_LINE_HEIGHT)
        .borderCollapse(BorderCollapse.Collapse)
        .tableLayout(TableLayout.Fixed)
}

val DocsTableHeaderStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .padding(topBottom = SiteTheme.dimensions.size.Md, leftRight = SiteTheme.dimensions.size.Lg)
        .fontWeight(FontWeight.SemiBold)
        .backgroundColor(palette.surface)
        .borderBottom(
            width = 1.px,
            style = LineStyle.Solid,
            color = palette.outline,
        )
}

val DocsTableCellStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .padding(topBottom = SiteTheme.dimensions.size.Md, leftRight = SiteTheme.dimensions.size.Lg)
        .borderBottom(
            width = 1.px,
            style = LineStyle.Solid,
            color = palette.outline,
        )
}
