package dev.tonholo.s2c.website.components.molecules

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.WhiteSpace
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderBottom
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.gridTemplateColumns
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.role
import com.varabyte.kobweb.compose.ui.modifiers.whiteSpace
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.ComponentKind
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.addVariantBase
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.theme.toSitePalette
import dev.tonholo.s2c.website.theme.typography.FontFamilies
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.fr
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

private const val OPTIONS_LINE_HEIGHT = 1.5

val OptionsHeaderRowStyle = CssStyle {
    base {
        val palette = colorMode.toSitePalette()
        Modifier
            .fillMaxWidth()
            .display(DisplayStyle.Grid)
            .gridTemplateColumns { size(1.fr) }
            .gap(SiteTheme.dimensions.size.Sm)
            .padding(topBottom = SiteTheme.dimensions.size.Md, leftRight = SiteTheme.dimensions.size.Lg)
            .fontSize(0.75.cssRem)
            .lineHeight(OPTIONS_LINE_HEIGHT)
            .backgroundColor(palette.surface)
            .borderBottom(
                width = 1.px,
                style = LineStyle.Solid,
                color = palette.outline,
            )
    }
    Breakpoint.MD {
        Modifier.gridTemplateColumns {
            size(14.cssRem)
            size(6.cssRem)
            size(1.fr)
        }
    }
}

sealed interface OptionsRowKind : ComponentKind

val OptionsRowStyle = CssStyle<OptionsRowKind> {
    base {
        Modifier
            .fillMaxWidth()
            .display(DisplayStyle.Grid)
            .gridTemplateColumns { size(1.fr) }
            .gap(SiteTheme.dimensions.size.Sm)
            .padding(topBottom = 0.625.cssRem, leftRight = SiteTheme.dimensions.size.Lg)
            .fontFamily(values = FontFamilies.mono)
            .fontWeight(FontWeight.Medium)
            .whiteSpace(WhiteSpace.NoWrap)
            .fontSize(0.75.cssRem)
            .lineHeight(value = 1.5)
    }
    Breakpoint.MD {
        Modifier.gridTemplateColumns {
            size(14.cssRem)
            size(6.cssRem)
            size(1.fr)
        }
    }
}

val OddRowVariant = OptionsRowStyle.addVariantBase {
    Modifier.backgroundColor(colorMode.toSitePalette().onBackground.toRgb().copyf(alpha = 0.06f))
}

@Composable
fun OptionsHeaderRow(modifier: Modifier = Modifier) {
    val palette = ColorMode.current.toSitePalette()
    val headerModifier = Modifier
        .fontWeight(FontWeight.SemiBold)
        .color(palette.onSurface)
        .role("columnheader")
    Div(
        attrs = OptionsHeaderRowStyle.toModifier()
            .role("row")
            .then(modifier)
            .toAttrs(),
    ) {
        SpanText("Flag", modifier = headerModifier)
        SpanText("Type", modifier = headerModifier)
        SpanText("Description", modifier = headerModifier)
    }
}

@Composable
fun OptionRow(flag: String, type: String, description: String, index: Int, modifier: Modifier = Modifier) {
    val palette = ColorMode.current.toSitePalette()
    val variant = if (index % 2 != 0) OddRowVariant else null
    Div(
        attrs = OptionsRowStyle.toModifier(variant)
            .role("row")
            .then(modifier)
            .toAttrs(),
    ) {
        SpanText(
            flag,
            modifier = Modifier
                .fontWeight(FontWeight.Medium)
                .color(palette.primary)
                .role("cell"),
        )
        SpanText(
            type,
            modifier = Modifier
                .color(palette.primary)
                .role("cell"),
        )
        SpanText(
            description,
            modifier = Modifier
                .color(palette.onSurfaceVariant)
                .role("cell"),
        )
    }
}
