package dev.tonholo.s2c.website.components.atoms

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.StyleVariable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.theme.palette
import dev.tonholo.s2c.website.theme.typography.FontFamilies
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Code
import org.jetbrains.compose.web.dom.Text

@Composable
fun InlineCode(
    code: String,
    modifier: Modifier = Modifier,
) {
    Code(attrs = InlineCodeStyle.toModifier().then(modifier).toAttrs()) {
        Text(code)
    }
}

object InlineCodeVars {
    val ContainerColor by StyleVariable<Color>("inline-code")
    val ContentColor by StyleVariable<Color>("inline-code")
    val BorderColor by StyleVariable<Color>("inline-code")
}

val InlineCodeStyle = CssStyle.base {
    Modifier
        .fontFamily(values = FontFamilies.mono)
        .fontSize(.85.em)
        .backgroundColor(InlineCodeVars.ContainerColor.value(palette.surface))
        .color(InlineCodeVars.ContentColor.value(palette.onSurface))
        .padding(topBottom = 0.15.cssRem, leftRight = 0.3.cssRem)
        .lineHeight(1.cssRem)
        .borderRadius(.25.cssRem)
        .border {
            width(1.px)
            style(LineStyle.Solid)
            color(InlineCodeVars.BorderColor.value(palette.outline.toRgb().copyf(alpha = .5f)))
        }
}
