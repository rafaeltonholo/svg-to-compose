package dev.tonholo.s2c.website.components.molecules.playground

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.AlignSelf
import com.varabyte.kobweb.compose.css.JustifyContent
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.alignSelf
import com.varabyte.kobweb.compose.ui.modifiers.borderRight
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flex
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.components.icons.fa.FaEye
import com.varabyte.kobweb.silk.components.layout.HorizontalDivider
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.components.atoms.Badge
import dev.tonholo.s2c.website.components.atoms.SquaredBadge
import dev.tonholo.s2c.website.components.atoms.SvgPreview
import dev.tonholo.s2c.website.components.molecules.PanelHeaderStyle
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px

val PreviewPanelStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .borderRight {
            width(1.px)
            style(LineStyle.Solid)
            color(colorMode.toSitePalette().borderStrong)
        }
}

val PreviewHalfStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .styleModifier { property("flex-direction", "column") }
        .styleModifier { property("flex", "1") }
        .padding(1.cssRem)
        .overflow(Overflow.Auto)
}

val PreviewSvgContainerStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .justifyContent(JustifyContent.Center)
        .alignItems(AlignItems.Center)
        .flex(1)
}


@Composable
fun PreviewPanel(svgCode: String) {
    val palette = ColorMode.current.toSitePalette()
    Column(modifier = PreviewPanelStyle.toModifier()) {
        // Top half: Source Preview
        Column(
            modifier = PreviewSvgContainerStyle.toModifier(),
        ) {
            PanelHeader(
                icon = { FaEye() },
                title = "Source Preview",
            )
            PanelPreview {
                SvgPreview(svgCode = svgCode)
            }
        }

        // Divider
        HorizontalDivider(modifier = Modifier.margin(0.px))

        // Bottom half: Compose Preview
        Column(
            modifier = PreviewSvgContainerStyle.toModifier(),
        ) {
            PanelHeader(
                icon = {
                    SpanText(
                        "\u25CF",
                        modifier = Modifier.color(Color.rgb(0x7F52FF))
                            .fontSize(0.5.cssRem),
                    )
                },
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        SpanText("Compose Preview")
                        Badge(
                            text = "identical",
                            color = Color.rgb(value = 0x00D4AA),
                            modifier = Modifier.alignSelf(AlignSelf.FlexEnd),
                            variant = SquaredBadge,
                        )
                    }
                },
            )
            PanelPreview {
                Badge(
                    text = "@Composable",
                    color = Color.rgb(0x7F52FF),
                    modifier = Modifier.alignSelf(AlignSelf.FlexStart),
                )
                SvgPreview(svgCode = svgCode)
                SpanText(
                    "ImageVector renders identically",
                    modifier = Modifier
                        .fontSize(0.65.cssRem)
                        .color(palette.muted)
                        .padding(bottom = 0.5.cssRem),
                )
            }
        }
    }
}

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
