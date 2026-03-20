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
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px

val PanelHeaderStyle = CssStyle.base {
    Modifier
        .fontFamily("JetBrains Mono", "monospace")
        .fontSize(0.75.cssRem)
        .color(colorMode.toSitePalette().muted)
        .padding(topBottom = 0.5.cssRem, leftRight = 1.cssRem)
        .backgroundColor(colorMode.toSitePalette().surfaceAlt)
        .borderBottom {
            width(1.px)
            style(LineStyle.Solid)
            color(colorMode.toSitePalette().borderStrong)
        }
}
