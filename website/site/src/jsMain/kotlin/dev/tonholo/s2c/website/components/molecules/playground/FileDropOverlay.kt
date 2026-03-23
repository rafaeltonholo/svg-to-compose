package dev.tonholo.s2c.website.components.molecules.playground

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.PointerEvents
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.bottom
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.left
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.pointerEvents
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.right
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaUpload
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.theme.toSitePalette
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

private const val FILE_DROP_Z_INDEX = 10

val FileDropOverlayStyle = CssStyle.base {
    Modifier
        .position(Position.Absolute)
        .top(0.px)
        .left(0.px)
        .right(0.px)
        .bottom(0.px)
        .zIndex(FILE_DROP_Z_INDEX)
        .pointerEvents(PointerEvents.None)
}

val FileDropOverlayInnerStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .backgroundColor(palette.primary.toRgb().copyf(alpha = 0.08f))
        .border(2.px, LineStyle.Dashed, palette.primary)
        .borderRadius(0.75.cssRem)
        .margin(SiteTheme.dimensions.size.Sm)
        .position(Position.Absolute)
        .top(0.px)
        .left(0.px)
        .right(0.px)
        .bottom(0.px)
}

/**
 * Translucent overlay shown when files are being dragged over the editor.
 */
@Composable
fun FileDropOverlay(modifier: Modifier = Modifier) {
    val palette = ColorMode.current.toSitePalette()
    Div(
        attrs = FileDropOverlayStyle.toModifier().then(modifier).toAttrs {
            attr("role", "status")
            attr("aria-live", "polite")
            attr("aria-label", "Drop files here to upload")
        },
    ) {
        Column(
            modifier = FileDropOverlayInnerStyle.toModifier(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            FaUpload(
                size = IconSize.X2,
                modifier = Modifier
                    .color(palette.primary)
                    .margin(bottom = SiteTheme.dimensions.size.Md),
            )
            SpanText(
                "Drop files here",
                modifier = Modifier
                    .fontSize(1.cssRem)
                    .fontWeight(FontWeight.SemiBold)
                    .color(palette.primary),
            )
            SpanText(
                "SVG or AVG (Android XML)",
                modifier = Modifier
                    .fontSize(0.75.cssRem)
                    .color(palette.onSurfaceVariant),
            )
        }
    }
}
