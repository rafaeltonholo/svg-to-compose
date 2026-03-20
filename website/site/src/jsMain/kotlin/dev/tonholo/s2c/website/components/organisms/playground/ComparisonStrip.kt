package dev.tonholo.s2c.website.components.organisms.playground

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flex
import com.varabyte.kobweb.compose.ui.modifiers.flexDirection
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.transform
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaCode
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.SitePalette
import dev.tonholo.s2c.website.SiteTheme
import dev.tonholo.s2c.website.components.atoms.Badge
import dev.tonholo.s2c.website.components.atoms.CheckerboardPreview
import dev.tonholo.s2c.website.components.atoms.SquaredBadge
import dev.tonholo.s2c.website.components.molecules.playground.ZoomControls
import dev.tonholo.s2c.website.state.playground.preview.SourcePreviewContent
import dev.tonholo.s2c.website.toSitePalette
import dev.tonholo.s2c.website.util.rememberElementSize
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Iframe
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLIFrameElement
import org.w3c.dom.events.MouseEvent

private const val VIEWBOX_PARTS_COUNT = 4
private const val MAX_PREVIEW_WIDTH_PX = 512

val ComparisonStripStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .fillMaxWidth()
        .padding(topBottom = 0.75.cssRem, leftRight = 1.cssRem)
        .backgroundColor(palette.surfaceVariant)
}

/** Layout for the preview row: vertical on mobile, horizontal on desktop. */
val ComparisonRowStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .display(DisplayStyle.Flex)
            .flexDirection(FlexDirection.Column)
            .alignItems(AlignItems.Center)
            .justifyContent(JustifyContent.Center)
            .gap(1.5.cssRem)
    }
    Breakpoint.MD {
        Modifier.flexDirection(FlexDirection.Row)
    }
}

/** Constrains each preview column so it fills available space up to a max width. */
val PreviewColumnStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .flex(1)
    }
    Breakpoint.MD {
        Modifier.maxWidth(MAX_PREVIEW_WIDTH_PX.px)
    }
}

/**
 * Responsive comparison strip showing the source SVG preview and the
 * converted ImageVector (WASM) preview. Stacks vertically on mobile,
 * side-by-side on desktop.
 */
@Composable
fun ComparisonStrip(
    svgCode: String,
    extension: String,
    iconFileContentsJson: String?,
    zoomLevel: Float,
    onZoomChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    val palette = ColorMode.current.toSitePalette()

    // Observe the source preview element to get the measured pixel width
    var previewElement by remember { mutableStateOf<HTMLElement?>(null) }
    val measuredSize = rememberElementSize(previewElement)

    val nativeScale = remember(svgCode, measuredSize.width) {
        if (measuredSize.width > 0) computeNativeScale(svgCode, measuredSize.width) else null
    }

    // Shared pan offset — synced between both previews
    var panX by remember { mutableFloatStateOf(0f) }
    var panY by remember { mutableFloatStateOf(0f) }

    // Reset pan when zoom changes
    LaunchedEffect(zoomLevel) {
        panX = 0f
        panY = 0f
    }

    Column(
        modifier = ComparisonStripStyle.toModifier().then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Div(attrs = ComparisonRowStyle.toModifier().toAttrs()) {
            SourcePreviewColumn(
                svgCode = svgCode,
                extension = extension,
                zoomLevel = zoomLevel,
                panX = panX,
                panY = panY,
                onPan = { dx, dy ->
                    panX += dx
                    panY += dy
                },
                onElementRef = { previewElement = it },
                palette = palette,
            )
            ConvertedPreviewColumn(
                iconFileContentsJson = iconFileContentsJson,
                zoomLevel = zoomLevel,
                panX = panX,
                panY = panY,
            )
        }

        ZoomControls(
            zoomLevel = zoomLevel,
            onZoomChange = onZoomChange,
            nativeScale = nativeScale,
            modifier = Modifier.padding(top = 0.75.cssRem),
        )
    }
}

@Composable
@Suppress("LongParameterList")
private fun SourcePreviewColumn(
    svgCode: String,
    extension: String,
    zoomLevel: Float,
    panX: Float,
    panY: Float,
    onPan: (dx: Float, dy: Float) -> Unit,
    onElementRef: (HTMLElement?) -> Unit,
    palette: SitePalette,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = PreviewColumnStyle.toModifier().gap(0.5.cssRem),
    ) {
        CheckerboardPreview(onElementRef = onElementRef) {
            SourcePreviewContent(
                state = SourcePreviewContent(
                    svgCode = svgCode,
                    extension = extension,
                    zoomLevel = zoomLevel,
                    panX = panX,
                    panY = panY,
                ),
                onPan = onPan,
            )
        }
        SourceLabel(extension = extension, palette = palette)
    }
}

@Composable
private fun SourceLabel(extension: String, palette: SitePalette) {
    Div(
        attrs = Modifier
            .display(DisplayStyle.Flex)
            .alignItems(AlignItems.Center)
            .gap(0.5.cssRem)
            .toAttrs(),
    ) {
        SpanText(
            "Source",
            modifier = Modifier
                .fontSize(0.7.cssRem)
                .color(palette.onSurfaceVariant),
        )
        Badge(
            text = extension.uppercase(),
            color = palette.onSurfaceVariant,
            variant = SquaredBadge,
        )
    }
}

@Composable
private fun ConvertedPreviewColumn(iconFileContentsJson: String?, zoomLevel: Float, panX: Float, panY: Float) {
    val palette = ColorMode.current.toSitePalette()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = PreviewColumnStyle.toModifier().gap(0.5.cssRem),
    ) {
        CheckerboardPreview {
            ComparisonIframe(
                iconFileContentsJson = iconFileContentsJson,
                zoomLevel = zoomLevel,
                panX = panX,
                panY = panY,
            )
        }
        ConvertedLabel(iconFileContentsJson = iconFileContentsJson, palette = palette)
    }
}

@Composable
private fun ConvertedLabel(iconFileContentsJson: String?, palette: SitePalette) {
    Div(
        attrs = Modifier
            .display(DisplayStyle.Flex)
            .alignItems(AlignItems.Center)
            .gap(0.5.cssRem)
            .toAttrs(),
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
    if (parts.size < VIEWBOX_PARTS_COUNT) return null
    val svgWidth = parts[2]
    if (svgWidth <= 0) return null
    return (svgWidth / containerSizePx).toFloat()
}

@Composable
private fun SourcePreviewContent(state: SourcePreviewContent, onPan: (dx: Float, dy: Float) -> Unit) {
    if (state.extension == "avg") {
        Column(
            modifier = Modifier.fillMaxSize(),
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
            svgCode = state.svgCode,
            zoomLevel = state.zoomLevel,
            panX = state.panX,
            panY = state.panY,
            onPan = onPan,
        )
    }
}

/**
 * Renders SVG filling its parent with CSS transform for zoom and pan.
 * Drag-to-pan is enabled when zoomed in (zoomLevel > 1).
 */
@Composable
private fun FittedSvgPreview(
    svgCode: String,
    zoomLevel: Float,
    panX: Float,
    panY: Float,
    onPan: (dx: Float, dy: Float) -> Unit = { _, _ -> },
) {
    var isDragging by remember { mutableStateOf(false) }

    Div(
        attrs = Modifier
            .fillMaxSize()
            .cursor(if (zoomLevel > 1f) Cursor.Grab else Cursor.Default)
            .transform {
                scale(zoomLevel)
                translate(panX.px, panY.px)
            }
            .toAttrs {
                if (zoomLevel > 1f) {
                    attachDragHandlers(
                        isDragging = isDragging,
                        zoomLevel = zoomLevel,
                        onDragStart = { isDragging = true },
                        onDragEnd = { isDragging = false },
                        onPan = onPan,
                    )
                }
            },
    ) {
        DisposableEffect(svgCode) {
            scopeElement.innerHTML = svgCode
            scopeElement.querySelector("svg")?.let { svg ->
                svg.setAttribute("width", "100%")
                svg.setAttribute("height", "100%")
                svg.asDynamic().style.display = "block"
            }
            onDispose { }
        }
    }
}

private fun org.jetbrains.compose.web.attributes.AttrsScope<*>.attachDragHandlers(
    isDragging: Boolean,
    zoomLevel: Float,
    onDragStart: () -> Unit,
    onDragEnd: () -> Unit,
    onPan: (Float, Float) -> Unit,
) {
    onMouseDown { event ->
        onDragStart()
        event.preventDefault()
        (event.currentTarget as? org.w3c.dom.HTMLElement)?.style?.cursor = "grabbing"
    }
    onMouseMove { event ->
        if (!isDragging) return@onMouseMove
        val mouseEvent = event.nativeEvent as? MouseEvent ?: return@onMouseMove
        onPan(
            mouseEvent.asDynamic().movementX as Float / zoomLevel,
            mouseEvent.asDynamic().movementY as Float / zoomLevel,
        )
    }
    onMouseUp {
        onDragEnd()
        (it.currentTarget as? org.w3c.dom.HTMLElement)?.style?.cursor = "grab"
    }
    onMouseLeave { onDragEnd() }
}

@Composable
private fun ComparisonIframe(iconFileContentsJson: String?, zoomLevel: Float, panX: Float, panY: Float) {
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
            .fillMaxSize()
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
