package dev.tonholo.s2c.website.components.atoms

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Div

private const val VIEWBOX_MIN_PARTS = 4
private const val VIEWBOX_HEIGHT_INDEX = 3

private data class SvgDimensions(val width: Double, val height: Double)

private fun parseSvgDimensions(svg: org.w3c.dom.Element): SvgDimensions? {
    val attrWidth = svg.getAttribute("width")
        ?.replace("px", "")?.trim()?.toDoubleOrNull()
    val attrHeight = svg.getAttribute("height")
        ?.replace("px", "")?.trim()?.toDoubleOrNull()
    if (attrWidth != null && attrHeight != null) {
        return SvgDimensions(attrWidth, attrHeight)
    }

    val viewBox = svg.getAttribute("viewBox") ?: return null
    val parts = viewBox.split(Regex("[\\s,]+"))
        .mapNotNull { it.trim().toDoubleOrNull() }
    if (parts.size < VIEWBOX_MIN_PARTS) return null
    return SvgDimensions(parts[2], parts[VIEWBOX_HEIGHT_INDEX])
}

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
fun SvgPreview(svgCode: String, modifier: Modifier = Modifier) {
    Div(
        attrs = modifier
            .fillMaxWidth()
            .display(DisplayStyle.Flex)
            .justifyContent(JustifyContent.Center)
            .alignItems(AlignItems.Center)
            .padding(1.cssRem)
            .toAttrs(),
    ) {
        Div(
            attrs = Modifier
                .lineHeight(0)
                .toAttrs(),
        ) {
            DisposableEffect(svgCode) {
                scopeElement.innerHTML = svgCode
                val svg = scopeElement.querySelector("svg")
                if (svg != null) {
                    val dimensions = parseSvgDimensions(svg)
                    if (dimensions != null) {
                        svg.setAttribute("width", "${dimensions.width}px")
                        svg.setAttribute("height", "${dimensions.height}px")
                    }
                    svg.asDynamic().style.display = "block"
                }
                onDispose { }
            }
        }
    }
}
