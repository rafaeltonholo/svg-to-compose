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

/**
 * Component kind marker for [CodeAwareSpanTextStyle] and its variants.
 */
sealed interface CodeAwareSpanTextKind : ComponentKind

/**
 * Renders text that may contain inline code segments delimited by backticks.
 *
 * Plain text (no backticks) is rendered as a [SpanText]. When backticks are
 * present, the text is split and segments between backticks are rendered as
 * [InlineCode] elements.
 *
 * Use [variant] to apply callout-specific [InlineCode] colour theming
 * (e.g., [TipCalloutCodeAwareVariant], [ImportantCalloutCodeAwareVariant]).
 *
 * @param text The text to render. Backtick-delimited segments become inline code.
 * @param modifier Additional [Modifier] applied to the root element.
 * @param variant Optional [CssStyleVariant] for contextual styling.
 */
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

/**
 * [CodeAwareSpanTextStyle] variant for [InlineCode] elements inside [CalloutVariant.TIP] callouts.
 */
val TipCalloutCodeAwareVariant = CodeAwareSpanTextStyle.addVariant {
    val palette = colorMode.toSitePalette()
    val (borderColor, containerColor) = CalloutVariant.TIP.resolveColors(palette)
    Modifier
        .setVariable(InlineCodeVars.BorderColor, borderColor.darkened(byPercent = INLINE_CODE_DARKEN_PERCENT))
        .setVariable(InlineCodeVars.ContainerColor, containerColor.lightened(byPercent = INLINE_CODE_LIGHTEN_PERCENT))
}

/**
 * [CodeAwareSpanTextStyle] variant for [InlineCode] elements inside [CalloutVariant.WARNING] callouts.
 */
val WarningCalloutCodeAwareVariant = CodeAwareSpanTextStyle.addVariant {
    val palette = colorMode.toSitePalette()
    val (borderColor, containerColor) = CalloutVariant.WARNING.resolveColors(palette)
    Modifier
        .setVariable(InlineCodeVars.BorderColor, borderColor.darkened(byPercent = INLINE_CODE_DARKEN_PERCENT))
        .setVariable(InlineCodeVars.ContainerColor, containerColor.lightened(byPercent = INLINE_CODE_LIGHTEN_PERCENT))
}

/**
 * [CodeAwareSpanTextStyle] variant for [InlineCode] elements inside [CalloutVariant.IMPORTANT] callouts.
 */
val ImportantCalloutCodeAwareVariant = CodeAwareSpanTextStyle.addVariant {
    val palette = colorMode.toSitePalette()
    val (borderColor, containerColor) = CalloutVariant.IMPORTANT.resolveColors(palette)
    Modifier
        .setVariable(InlineCodeVars.BorderColor, borderColor.darkened(byPercent = INLINE_CODE_DARKEN_PERCENT))
        .setVariable(InlineCodeVars.ContainerColor, containerColor.lightened(byPercent = INLINE_CODE_LIGHTEN_PERCENT))
}
