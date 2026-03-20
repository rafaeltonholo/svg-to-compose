package dev.tonholo.s2c.website.components.molecules.playground

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaCode
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.SiteTheme
import dev.tonholo.s2c.website.components.atoms.Badge
import dev.tonholo.s2c.website.components.atoms.CheckerboardPreview
import dev.tonholo.s2c.website.components.atoms.SquaredBadge
import dev.tonholo.s2c.website.components.atoms.ZoomControls
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Iframe
import org.w3c.dom.HTMLIFrameElement
import org.w3c.dom.events.MouseEvent

val ComparisonStripStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .fillMaxWidth()
        .padding(topBottom = 0.75.cssRem, leftRight = 1.cssRem)
        .backgroundColor(palette.surfaceVariant)
}

/**
 * Horizontal comparison strip showing the source SVG preview and the
 * converted ImageVector (WASM) preview side-by-side.
 */
@Composable
fun ComparisonStrip(
    svgCode: String,
    extension: String,
    iconFileContentsJson: String?,
    zoomLevel: Float,
    onZoomChange: (Float) -> Unit,
    previewSizePx: Int = 96,
) {
    val palette = ColorMode.current.toSitePalette()

    val nativeScale = remember(svgCode, previewSizePx) {
        computeNativeScale(svgCode, previewSizePx)
    }

    // Shared pan offset — synced between both previews
    var panX by remember { mutableStateOf(0f) }
    var panY by remember { mutableStateOf(0f) }

    // Reset pan when zoom changes
    LaunchedEffect(zoomLevel) {
        panX = 0f
        panY = 0f
    }

    Column(
        modifier = ComparisonStripStyle.toModifier(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.gap(1.5.cssRem),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center,
        ) {
            // Source preview
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.gap(0.5.cssRem),
            ) {
                CheckerboardPreview(sizePx = previewSizePx) {
                    SourcePreviewContent(
                        svgCode = svgCode,
                        extension = extension,
                        zoomLevel = zoomLevel,
                        panX = panX,
                        panY = panY,
                        onPan = { dx, dy ->
                            panX += dx
                            panY += dy
                        },
                        containerSizePx = previewSizePx,
                    )
                }
                SpanText(
                    "Source",
                    modifier = Modifier
                        .fontSize(0.7.cssRem)
                        .color(palette.onSurfaceVariant),
                )
            }

            // "vs" label
            SpanText(
                "vs",
                modifier = Modifier
                    .fontSize(0.7.cssRem)
                    .color(palette.onSurfaceVariant)
                    .padding(top = (previewSizePx / 2 - 8).px),
            )

            // Converted preview
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.gap(0.5.cssRem),
            ) {
                CheckerboardPreview(sizePx = previewSizePx) {
                    ComparisonIframe(
                        iconFileContentsJson = iconFileContentsJson,
                        zoomLevel = zoomLevel,
                        panX = panX,
                        panY = panY,
                        sizePx = previewSizePx,
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.gap(0.5.cssRem),
                ) {
                    SpanText(
                        "Converted",
                        modifier = Modifier
                            .fontSize(0.7.cssRem)
                            .color(palette.onSurfaceVariant),
                    )
                    if (iconFileContentsJson != null) {
                        Badge(
                            text = "ImageVector",
                            color = SiteTheme.palette.primary,
                            variant = SquaredBadge,
                        )
                    }
                }
            }
        }

        ZoomControls(
            zoomLevel = zoomLevel,
            onZoomChange = onZoomChange,
            nativeScale = nativeScale,
            modifier = Modifier.padding(top = 0.75.cssRem),
        )
    }
}

/**
 * Computes the zoom scale that shows the icon at its native pixel size.
 * At fit (1x), the icon fills the container. Native means 1 SVG unit = 1 CSS pixel.
 * For a 24px icon in a 200px container: nativeScale = 24/200 = 0.12 (12%).
 */
private fun computeNativeScale(svgCode: String, containerSizePx: Int): Float? {
    val viewBoxRegex = Regex("""viewBox\s*=\s*"([^"]+)"""")
    val match = viewBoxRegex.find(svgCode) ?: return null
    val parts = match.groupValues[1].split(Regex("[\\s,]+"))
        .mapNotNull { it.trim().toDoubleOrNull() }
    if (parts.size < 4) return null
    val svgWidth = parts[2]
    if (svgWidth <= 0) return null
    return (svgWidth / containerSizePx).toFloat()
}

@Composable
private fun SourcePreviewContent(
    svgCode: String,
    extension: String,
    zoomLevel: Float,
    panX: Float,
    panY: Float,
    onPan: (dx: Float, dy: Float) -> Unit,
    containerSizePx: Int,
) {
    if (extension == "avg") {
        Column(
            modifier = Modifier.size(containerSizePx.px),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            FaCode(
                modifier = Modifier
                    .fontSize(1.cssRem)
                    .color(ColorMode.current.toSitePalette().onSurfaceVariant),
            )
            SpanText(
                "AVG preview not available",
                modifier = Modifier
                    .fontSize(0.5.cssRem)
                    .color(ColorMode.current.toSitePalette().onSurfaceVariant),
            )
        }
    } else {
        FittedSvgPreview(
            svgCode = svgCode,
            zoomLevel = zoomLevel,
            panX = panX,
            panY = panY,
            onPan = onPan,
            containerSizePx = containerSizePx,
        )
    }
}

/**
 * Renders SVG at container size with CSS transform for zoom and pan.
 * Drag-to-pan is enabled when zoomed in (zoomLevel > 1).
 */
@Composable
private fun FittedSvgPreview(
    svgCode: String,
    zoomLevel: Float,
    panX: Float,
    panY: Float,
    onPan: (dx: Float, dy: Float) -> Unit,
    containerSizePx: Int,
) {
    var isDragging by remember { mutableStateOf(false) }

    Div(
        attrs = Modifier
            .size(containerSizePx.px)
            .cursor(if (zoomLevel > 1f) Cursor.Grab else Cursor.Default)
            .styleModifier {
                // Visual zoom + pan via CSS transform. No layout change —
                // the container clips overflow and drag-to-pan moves the view.
                property(
                    "transform",
                    "scale($zoomLevel) translate(${panX}px, ${panY}px)",
                )
                property("transform-origin", "center center")
            }
            .toAttrs {
                if (zoomLevel > 1f) {
                    onMouseDown { event ->
                        isDragging = true
                        event.preventDefault()
                        (event.currentTarget as? org.w3c.dom.HTMLElement)
                            ?.style?.cursor = "grabbing"
                    }
                    onMouseMove { event ->
                        if (isDragging) {
                            val mouseEvent = event.nativeEvent as? MouseEvent
                            if (mouseEvent != null) {
                                // Movement delta divided by zoom so pan speed
                                // matches cursor movement at the zoomed scale.
                                onPan(
                                    mouseEvent.asDynamic().movementX as Float / zoomLevel,
                                    mouseEvent.asDynamic().movementY as Float / zoomLevel,
                                )
                            }
                        }
                    }
                    onMouseUp {
                        isDragging = false
                        (it.currentTarget as? org.w3c.dom.HTMLElement)
                            ?.style?.cursor = "grab"
                    }
                    onMouseLeave {
                        isDragging = false
                    }
                }
            },
    ) {
        DisposableEffect(svgCode) {
            scopeElement.innerHTML = svgCode
            val svg = scopeElement.querySelector("svg")
            if (svg != null) {
                svg.setAttribute("width", "100%")
                svg.setAttribute("height", "100%")
                svg.asDynamic().style.display = "block"
            }
            onDispose { }
        }
    }
}

@Composable
private fun ComparisonIframe(
    iconFileContentsJson: String?,
    zoomLevel: Float,
    panX: Float,
    panY: Float,
    sizePx: Int,
) {
    var iframeLoaded by remember { mutableStateOf(false) }
    val iframeRef = remember { mutableStateOf<HTMLIFrameElement?>(null) }

    LaunchedEffect(iconFileContentsJson, iframeLoaded) {
        if (iframeLoaded) {
            iframeRef.value?.contentWindow?.postMessage(
                iconFileContentsJson,
                kotlinx.browser.window.location.origin,
            )
        }
    }

    LaunchedEffect(zoomLevel, panX, panY, iframeLoaded) {
        if (iframeLoaded) {
            val msg = """{"type":"zoom","level":$zoomLevel,"panX":$panX,"panY":$panY}"""
            iframeRef.value?.contentWindow?.postMessage(
                msg,
                kotlinx.browser.window.location.origin,
            )
        }
    }

    val colorMode = ColorMode.current
    Iframe(
        attrs = Modifier
            .size(sizePx.px)
            .border(0.px, LineStyle.None, Colors.Transparent)
            .backgroundColor(Colors.Transparent)
            .toAttrs {
                attr("src", "/editor/index.html?preview=true&color_mode=${colorMode.name.lowercase()}")
                attr("title", "Compose ImageVector preview")
            },
    ) {
        DisposableEffect(Unit) {
            val iframe = scopeElement
            iframeRef.value = iframe
            iframe.onload = {
                iframeLoaded = true
                null
            }
            onDispose {
                iframeRef.value = null
                iframeLoaded = false
            }
        }
    }
}
