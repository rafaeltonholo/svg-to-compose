package dev.tonholo.s2c.website.components.molecules.playground

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
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
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flex
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.role
import com.varabyte.kobweb.compose.ui.modifiers.tabIndex
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaFile
import com.varabyte.kobweb.silk.components.icons.fa.FaFolderOpen
import com.varabyte.kobweb.silk.components.icons.fa.FaShieldHalved
import com.varabyte.kobweb.silk.components.icons.fa.FaUpload
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button

val UploadPanelStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .fillMaxWidth()
        .flex(1)
        .minHeight(0.px)
        .backgroundColor(palette.surfaceVariant)
        .padding(2.cssRem)
}

val UploadDropZoneStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .fillMaxWidth()
        .flex(1)
        .border(2.px, LineStyle.Dashed, palette.outline)
        .borderRadius(0.75.cssRem)
        .padding(2.cssRem)
        .cursor(Cursor.Pointer)
}

val UploadButtonStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .backgroundColor(palette.surface)
        .border(1.px, LineStyle.Solid, palette.outline)
        .borderRadius(0.5.cssRem)
        .padding(topBottom = 0.5.cssRem, leftRight = 1.cssRem)
        .fontSize(0.8.cssRem)
        .fontWeight(FontWeight.Medium)
        .cursor(Cursor.Pointer)
        .color(palette.onSurface)
}

/**
 * Panel shown when the user selects "Upload File" mode.
 * Contains a drop zone and buttons to pick files or folders.
 */
@Composable
fun UploadPanel(onFilePickerClick: () -> Unit, onFolderPickerClick: () -> Unit, modifier: Modifier = Modifier) {
    val palette = ColorMode.current.toSitePalette()

    Column(
        modifier = UploadPanelStyle.toModifier().then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Column(
            modifier = UploadDropZoneStyle.toModifier()
                .onClick { onFilePickerClick() }
                .tabIndex(0)
                .role("button")
                .ariaLabel("Drop files here or click to upload")
                .attrsModifier {
                    onKeyDown { event ->
                        when (event.key) {
                            " ", "Enter" -> {
                                event.preventDefault()
                                onFilePickerClick()
                            }
                        }
                    }
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            FaUpload(
                size = IconSize.X2,
                modifier = Modifier
                    .color(palette.onSurfaceVariant)
                    .margin(bottom = 1.cssRem),
            )
            SpanText(
                "Drop SVG/AVG files, a folder, or a zip file here",
                modifier = Modifier
                    .fontSize(0.9.cssRem)
                    .fontWeight(FontWeight.SemiBold)
                    .color(palette.onSurface),
            )
            SpanText(
                "or use the buttons below",
                modifier = Modifier
                    .fontSize(0.75.cssRem)
                    .color(palette.onSurfaceVariant)
                    .margin(top = 0.25.cssRem),
            )

            Row(
                modifier = Modifier
                    .margin(top = 1.cssRem)
                    .gap(0.75.cssRem),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Button(
                    attrs = UploadButtonStyle.toModifier()
                        .onClick { event ->
                            event.stopPropagation()
                            onFilePickerClick()
                        }
                        .toAttrs(),
                ) {
                    Row(
                        modifier = Modifier.gap(0.4.cssRem),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        FaFile()
                        SpanText("Choose File")
                    }
                }
                Button(
                    attrs = UploadButtonStyle.toModifier()
                        .onClick { event ->
                            event.stopPropagation()
                            onFolderPickerClick()
                        }
                        .toAttrs(),
                ) {
                    Row(
                        modifier = Modifier.gap(0.4.cssRem),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        FaFolderOpen()
                        SpanText("Choose Folder")
                    }
                }
            }

            Row(
                modifier = Modifier
                    .margin(top = 1.25.cssRem)
                    .gap(0.35.cssRem),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                FaShieldHalved(
                    modifier = Modifier
                        .fontSize(0.75.cssRem)
                        .color(palette.onSurfaceVariant),
                )
                SpanText(
                    "All processing happens locally in your browser. Works offline too.",
                    modifier = Modifier
                        .fontSize(0.75.cssRem)
                        .color(palette.onSurfaceVariant),
                )
            }
        }
    }
}
