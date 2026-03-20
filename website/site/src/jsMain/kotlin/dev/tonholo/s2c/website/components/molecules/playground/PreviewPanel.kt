package dev.tonholo.s2c.website.components.molecules.playground

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.AlignSelf
import com.varabyte.kobweb.compose.css.JustifyContent
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.alignSelf
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRight
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flex
import com.varabyte.kobweb.compose.ui.modifiers.flexDirection
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaEye
import com.varabyte.kobweb.silk.components.layout.HorizontalDivider
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.SiteTheme
import dev.tonholo.s2c.website.components.atoms.Badge
import dev.tonholo.s2c.website.components.atoms.SquaredBadge
import dev.tonholo.s2c.website.components.atoms.SvgPreview
import dev.tonholo.s2c.website.components.molecules.PanelHeaderStyle
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Iframe
import org.w3c.dom.HTMLIFrameElement

/** Styles the preview panel column with a right border separator. */
val PreviewPanelStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .borderRight {
            width(1.px)
            style(LineStyle.Solid)
            color(colorMode.toSitePalette().outlineVariant)
        }
}

/** Styles each half of the split preview with flex column layout and padding. */
val PreviewHalfStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .flex(1)
        .padding(1.cssRem)
        .overflow(Overflow.Auto)
}

/** Styles the SVG/Compose preview container with centered flex layout. */
val PreviewSvgContainerStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .justifyContent(JustifyContent.Center)
        .alignItems(AlignItems.Center)
        .flex(1)
}

/** Styles the embedded WASM iframe with no border and full dimensions. */
val ComposePreviewIframeStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .border(0.px, LineStyle.None, Colors.Transparent)
        .height(100.percent)
        .minHeight(200.px)
        .backgroundColor(Colors.Transparent)
}


/**
 * Split preview panel showing the raw SVG source (top) and a live Compose
 * ImageVector render via an embedded WASM iframe (bottom).
 *
 * @param svgCode Raw SVG/AVG markup to render in the source preview.
 * @param iconFileContentsJson Serialised [IconFileContents] JSON sent to the
 *   WASM iframe for Compose rendering. `null` until conversion completes.
 */
@Composable
fun PreviewPanel(svgCode: String, iconFileContentsJson: String? = null) {
    Column(modifier = PreviewPanelStyle.toModifier()) {
        // Top half: Source Preview
        Column(
            modifier = PreviewSvgContainerStyle.toModifier(),
        ) {
            PanelHeader(
                icon = { FaEye() },
                title = "SVG (browser)",
            )
            PanelPreview {
                SvgPreview(svgCode = svgCode)
            }
        }

        // Divider
        HorizontalDivider(modifier = Modifier.margin(0.px))

        // Bottom half: Compose Preview (Wasm Compose render via iframe)
        Column(
            modifier = PreviewSvgContainerStyle.toModifier(),
        ) {
            PanelHeader(
                icon = {
                    SpanText(
                        "\u25CF",
                        modifier = Modifier.color(SiteTheme.palette.primary)
                            .fontSize(0.5.cssRem),
                    )
                },
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        SpanText("ImageVector (WASM)")
                        if (iconFileContentsJson != null) {
                            Badge(
                                text = "ImageVector",
                                color = SiteTheme.palette.primary,
                                modifier = Modifier.alignSelf(AlignSelf.FlexEnd),
                                variant = SquaredBadge,
                            )
                        }
                    }
                },
            )
            PanelPreview {
                ComposePreviewIframe(iconFileContentsJson)
            }
        }
    }
}

/** Embedded WASM iframe that renders the Compose ImageVector preview. */
@Composable
private fun ComposePreviewIframe(iconFileContentsJson: String?) {
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

    val colorMode = ColorMode.current
    Iframe(
        attrs = ComposePreviewIframeStyle.toModifier().toAttrs {
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

/** Panel header row with an icon and a string title. */
@Composable
private fun PanelHeader(
    icon: @Composable () -> Unit,
    title: String,
    modifier: Modifier = Modifier,
) {
    PanelHeader(
        icon = icon,
        title = { SpanText(title) },
        modifier = modifier,
    )
}

/** Panel header row with an icon and composable title slot. */
@Composable
private fun PanelHeader(
    icon: @Composable () -> Unit,
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = PanelHeaderStyle
            .toModifier()
            .then(modifier)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(0.5.cssRem),
    ) {
        icon()
        title()
    }
}

/** Centered container for preview content within a panel half. */
@Composable
private fun PanelPreview(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .flex(1),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        content()
    }
}
