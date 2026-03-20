package dev.tonholo.s2c.website.components.molecules

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.WhiteSpace
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
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
import com.varabyte.kobweb.compose.ui.modifiers.whiteSpace
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.ComponentKind
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.addVariantBase
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.fr
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

private val headerBorderColor = Color.rgba(127, 82, 255, 0.2f)

val OptionsHeaderRowStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .display(DisplayStyle.Grid)
            .gridTemplateColumns { size(1.fr) }
            .gap(0.5.cssRem)
            .padding(topBottom = 0.75.cssRem, leftRight = 1.cssRem)
            .fontSize(0.75.cssRem)
            .lineHeight(1.5)
            .backgroundColor(Color.rgba(127, 82, 255, 0.08f))
            .borderBottom(width = 1.px, style = LineStyle.Solid, color = headerBorderColor)
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
            .gap(0.5.cssRem)
            .padding(topBottom = 0.625.cssRem, leftRight = 1.cssRem)
            .fontFamily("JetBrains Mono", "monospace")
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
    Modifier.backgroundColor(Color.rgba(0, 0, 0, 0.15f))
}

@Composable
fun OptionsHeaderRow() {
    val palette = ColorMode.current.toSitePalette()
    val headerModifier = Modifier
        .fontWeight(FontWeight.SemiBold)
        .color(palette.muted)
    Div(attrs = OptionsHeaderRowStyle.toModifier().toAttrs()) {
        SpanText("Flag", modifier = headerModifier)
        SpanText("Type", modifier = headerModifier)
        SpanText("Description", modifier = headerModifier)
    }
}

@Composable
fun OptionRow(flag: String, type: String, description: String, index: Int) {
    val palette = ColorMode.current.toSitePalette()
    val variant = if (index % 2 != 0) OddRowVariant else null
    Div(attrs = OptionsRowStyle.toModifier(variant).toAttrs()) {
        SpanText(
            flag,
            modifier = Modifier
                .fontWeight(FontWeight.Medium)
                .color(palette.brand.blue),
        )
        SpanText(
            type,
            modifier = Modifier.color(palette.brand.orange),
        )
        SpanText(
            description,
            modifier = Modifier.color(palette.muted),
        )
    }
}
