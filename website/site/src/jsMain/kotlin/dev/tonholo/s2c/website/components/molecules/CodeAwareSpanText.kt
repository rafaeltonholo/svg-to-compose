package dev.tonholo.s2c.website.components.molecules

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.lightened
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.setVariable
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.ComponentKind
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.CssStyleVariant
import com.varabyte.kobweb.silk.style.addVariant
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.components.atoms.InlineCode
import dev.tonholo.s2c.website.components.atoms.InlineCodeVars
import dev.tonholo.s2c.website.theme.toSitePalette
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

private const val INLINE_CODE_DARKEN_PERCENT = .25f
private const val INLINE_CODE_LIGHTEN_PERCENT = .25f

sealed interface CodeAwareSpanTextKind : ComponentKind

@Composable
fun CodeAwareSpanText(
    text: String,
    modifier: Modifier = Modifier,
    variant: CssStyleVariant<CodeAwareSpanTextKind>? = null,
) {
    val parts = text.split("`")
    val hasInlineCode = parts.size > 1
    val styleModifier = CodeAwareSpanTextStyle.toModifier(variant).then(modifier)
    if (!hasInlineCode) {
        SpanText(
            text = text,
            modifier = styleModifier,
        )
    } else {
        Span(attrs = styleModifier.toAttrs()) {
            // if the text starts with backticks, the first part will be empty, so we can ignore it
            val startIndex = if (parts.first().isEmpty()) 1 else 0
            parts.drop(startIndex).forEachIndexed { index, part ->
                if (index % 2 == 0) {
                    Text(value = part)
                } else {
                    InlineCode(code = part)
                }
            }
        }
    }
}

val CodeAwareSpanTextStyle = CssStyle<CodeAwareSpanTextKind> {
    cssRule("> .inline-code") {
        Modifier.fontSize(0.85.em)
    }
}

val TipCalloutCodeAwareVariant = CodeAwareSpanTextStyle.addVariant {
    val palette = colorMode.toSitePalette()
    val (borderColor, containerColor) = CalloutVariant.TIP.resolveColors(palette)
    Modifier
        .setVariable(InlineCodeVars.BorderColor, borderColor.darkened(byPercent = INLINE_CODE_DARKEN_PERCENT))
        .setVariable(InlineCodeVars.ContainerColor, containerColor.lightened(byPercent = INLINE_CODE_LIGHTEN_PERCENT))
}

val WarningCalloutCodeAwareVariant = CodeAwareSpanTextStyle.addVariant {
    val palette = colorMode.toSitePalette()
    val (borderColor, containerColor) = CalloutVariant.WARNING.resolveColors(palette)
    Modifier
        .setVariable(InlineCodeVars.BorderColor, borderColor.darkened(byPercent = INLINE_CODE_DARKEN_PERCENT))
        .setVariable(InlineCodeVars.ContainerColor, containerColor.lightened(byPercent = INLINE_CODE_LIGHTEN_PERCENT))
}

val ImportantCalloutCodeAwareVariant = CodeAwareSpanTextStyle.addVariant {
    val palette = colorMode.toSitePalette()
    val (borderColor, containerColor) = CalloutVariant.IMPORTANT.resolveColors(palette)
    Modifier
        .setVariable(InlineCodeVars.BorderColor, borderColor.darkened(byPercent = INLINE_CODE_DARKEN_PERCENT))
        .setVariable(InlineCodeVars.ContainerColor, containerColor.lightened(byPercent = INLINE_CODE_LIGHTEN_PERCENT))
}
