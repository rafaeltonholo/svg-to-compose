package dev.tonholo.s2c.website.components.molecules.playground

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.attrsModifier
import com.varabyte.kobweb.compose.ui.modifiers.ariaLabel
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.tabIndex
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.theme.toSitePalette
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button

val ZoomButtonStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .backgroundColor(palette.surface)
        .color(palette.onSurfaceVariant)
        .border(1.px, LineStyle.Solid, palette.outlineVariant)
        .borderRadius(0.25.cssRem)
        .padding(topBottom = 0.25.cssRem, leftRight = 0.5.cssRem)
        .fontSize(0.75.cssRem)
        .fontWeight(FontWeight.Medium)
        .cursor(Cursor.Pointer)
}

private const val ZOOM_QUARTER = 0.25f
private const val ZOOM_HALF = 0.5f
private const val ZOOM_QUADRUPLE = 4f
private const val ZOOM_OCTUPLE = 8f
private const val PERCENT_MULTIPLIER = 100

private val ZOOM_LEVELS = floatArrayOf(ZOOM_QUARTER, ZOOM_HALF, 1f, 2f, ZOOM_QUADRUPLE, ZOOM_OCTUPLE)

/**
 * Zoom control bar with [-], [+], and [1:1] buttons.
 *
 * @param zoomLevel Current zoom level relative to fit (1f = fit).
 * @param onZoomChange Callback with new zoom level.
 * @param nativeScale The scale factor that shows the icon at native pixel
 *   size. Computed as containerSize / iconNativeSize (e.g., 200/24 = ~8.3f).
 *   Pass null to hide the [1:1] button when native size is unknown.
 */
@Composable
fun ZoomControls(
    zoomLevel: Float,
    onZoomChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    nativeScale: Float? = null,
) {
    val palette = ColorMode.current.toSitePalette()

    Row(
        modifier = modifier.gap(0.5.cssRem)
            .tabIndex(0)
            .attrsModifier {
                onKeyDown { event ->
                    when (event.key) {
                        "-" -> {
                            event.preventDefault()
                            previousZoom(zoomLevel)?.let(onZoomChange)
                        }

                        "+", "=" -> {
                            event.preventDefault()
                            nextZoom(zoomLevel)?.let(onZoomChange)
                        }

                        "0" -> {
                            event.preventDefault()
                            onZoomChange(1f)
                        }
                    }
                }
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Button(
            attrs = ZoomButtonStyle.toModifier()
                .ariaLabel("Zoom out")
                .onClick { previousZoom(zoomLevel)?.let(onZoomChange) }
                .toAttrs(),
        ) {
            SpanText("\u2212") // minus sign
        }
        SpanText(
            "${(zoomLevel * PERCENT_MULTIPLIER).toInt()}%",
            modifier = Modifier.fontSize(0.7.cssRem).color(palette.onSurfaceVariant),
        )
        Button(
            attrs = ZoomButtonStyle.toModifier()
                .ariaLabel("Zoom in")
                .onClick { nextZoom(zoomLevel)?.let(onZoomChange) }
                .toAttrs(),
        ) {
            SpanText("+")
        }
        if (nativeScale != null) {
            Button(
                attrs = ZoomButtonStyle.toModifier()
                    .ariaLabel("Zoom to native size")
                    .onClick { onZoomChange(nativeScale) }
                    .toAttrs(),
            ) {
                SpanText("1:1")
            }
        }
    }
}

/** Finds the previous zoom level, handling arbitrary values not in ZOOM_LEVELS. */
private fun previousZoom(current: Float): Float? {
    // Find the largest level strictly less than current (with small epsilon for float comparison)
    val prev = ZOOM_LEVELS.lastOrNull { it < current - 0.001f }
    if (prev != null) return prev
    // If current is at or below minimum, no zoom out possible
    return null
}

/** Finds the next zoom level, handling arbitrary values not in ZOOM_LEVELS. */
private fun nextZoom(current: Float): Float? {
    val next = ZOOM_LEVELS.firstOrNull { it > current + 0.001f }
    if (next != null) return next
    return null
}
