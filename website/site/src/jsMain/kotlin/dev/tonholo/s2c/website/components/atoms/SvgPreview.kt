package dev.tonholo.s2c.website.components.atoms

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Div

/**
 * Renders an SVG string at its actual pixel size, centered in a
 * full-width container.
 *
 * The SVG dimensions come from its `width`/`height` attributes or,
 * if absent, from the `viewBox` (third and fourth values). The SVG
 * is rendered at exactly that pixel size, centered horizontally
 * within the full-width container with padding.
 */
@Composable
fun SvgPreview(
    svgCode: String,
    modifier: Modifier = Modifier,
) {
    Div(
        attrs = modifier
            .fillMaxWidth()
            .display(DisplayStyle.Flex)
            .styleModifier {
                property("justify-content", "center")
                property("align-items", "center")
            }
            .padding(1.cssRem)
            .toAttrs(),
    ) {
        Div(
            attrs = Modifier
                .styleModifier { property("line-height", "0") }
                .toAttrs(),
        ) {
            DisposableEffect(svgCode) {
                scopeElement.innerHTML = svgCode
                val svg = scopeElement.querySelector("svg")
                if (svg != null) {
                    val viewBox = svg.getAttribute("viewBox")
                    val attrWidth = svg.getAttribute("width")
                        ?.replace("px", "")?.trim()?.toDoubleOrNull()
                    val attrHeight = svg.getAttribute("height")
                        ?.replace("px", "")?.trim()?.toDoubleOrNull()

                    val (w, h) = when {
                        attrWidth != null && attrHeight != null -> attrWidth to attrHeight
                        viewBox != null -> {
                            val parts = viewBox.split(Regex("[\\s,]+"))
                                .mapNotNull { it.trim().toDoubleOrNull() }
                            if (parts.size >= 4) parts[2] to parts[3]
                            else null to null
                        }
                        else -> null to null
                    }

                    if (w != null && h != null) {
                        svg.setAttribute("width", "${w}px")
                        svg.setAttribute("height", "${h}px")
                    }
                    svg.asDynamic().style.display = "block"
                }
                onDispose { }
            }
        }
    }
}
