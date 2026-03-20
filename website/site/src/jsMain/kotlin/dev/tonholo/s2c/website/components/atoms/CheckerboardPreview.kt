package dev.tonholo.s2c.website.components.atoms

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.aspectRatio
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.w3c.dom.HTMLElement

/** Checkerboard background container for icon previews. Uses
 * repeating-conic-gradient via styleModifier — no Kobweb native
 * modifier exists for conic gradients. Always clips overflow —
 * zoom/pan is handled via CSS transforms inside the content. */
val CheckerboardPreviewStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .fillMaxWidth()
        .aspectRatio(1, 1)
        .display(DisplayStyle.Flex)
        .alignItems(AlignItems.Center)
        .justifyContent(JustifyContent.Center)
        .border(1.px, LineStyle.Solid, palette.outlineVariant)
        .borderRadius(0.5.cssRem)
        .overflow(Overflow.Hidden)
        .styleModifier {
            val c1 = palette.surface
            val c2 = palette.surfaceVariant
            property(
                "background",
                "repeating-conic-gradient($c1 0% 25%, $c2 0% 50%) 0 0 / 16px 16px",
            )
        }
}

/**
 * Responsive square container with a checkerboard background for
 * previewing icons with transparency. Fills parent width and
 * maintains 1:1 aspect ratio via CSS. Always clips overflow — zoom
 * and pan are handled by the content via CSS transforms or Compose
 * graphicsLayer.
 *
 * @param onElementRef Called with the container [HTMLElement] on
 *   attach and `null` on detach. Use with [rememberElementSize] to
 *   read the measured pixel dimensions.
 * @param content The preview content (SVG element, iframe, or placeholder).
 */
@Composable
fun CheckerboardPreview(
    modifier: Modifier = Modifier,
    onElementRef: (HTMLElement?) -> Unit = {},
    content: @Composable () -> Unit,
) {
    Div(
        attrs = CheckerboardPreviewStyle.toModifier()
            .then(modifier)
            .toAttrs {
                ref { element ->
                    onElementRef(element)
                    onDispose { onElementRef(null) }
                }
            },
    ) {
        content()
    }
}
