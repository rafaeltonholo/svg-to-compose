package dev.tonholo.s2c.website.components.organisms.playground

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.RowScope
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.lightened
import com.varabyte.kobweb.compose.ui.modifiers.BorderSideScope
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderBottom
import com.varabyte.kobweb.compose.ui.modifiers.borderLeft
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.borderRight
import com.varabyte.kobweb.compose.ui.modifiers.borderTop
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.flexWrap
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaClipboard
import com.varabyte.kobweb.silk.components.icons.fa.FaPlay
import com.varabyte.kobweb.silk.components.icons.fa.FaSpinner
import com.varabyte.kobweb.silk.components.icons.fa.FaUpload
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.ComponentKind
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.CssStyleVariant
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.components.molecules.playground.ConvertButtonStyle
import dev.tonholo.s2c.website.components.molecules.playground.SpinnerIconStyle
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.theme.toSitePalette
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div

/** Styles the toolbar container with flex layout, gap, and header background. */
val ToolbarStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .alignItems(AlignItems.Center)
        .flexWrap(FlexWrap.Wrap)
        .gap(SiteTheme.dimensions.size.Md)
        .padding(topBottom = SiteTheme.dimensions.size.Md, leftRight = SiteTheme.dimensions.size.Lg)
        .backgroundColor(colorMode.toSitePalette().surfaceVariant)
}

/** Styles the grouped button row with connected border-radius on first/last children. */
val ToolbarButtonRowStyle = CssStyle {
    val borderRadius = 0.375.cssRem
    fun BorderSideScope.applyBorder() {
        width(1.px)
        style(LineStyle.Solid)
        color(colorMode.toSitePalette().primary.darkened(byPercent = .75f))
    }
    cssRule("> :first-child") {
        Modifier
            .borderRadius {
                topLeft(borderRadius)
                bottomLeft(borderRadius)
            }
            .borderLeft { applyBorder() }
            .borderRight { style(LineStyle.None) }
            .borderTop { applyBorder() }
            .borderBottom { applyBorder() }
    }
    cssRule("> :last-child") {
        Modifier
            .borderRadius {
                topRight(borderRadius)
                bottomRight(borderRadius)
            }
            .borderLeft { style(LineStyle.None) }
            .borderRight { applyBorder() }
            .borderTop { applyBorder() }
            .borderBottom { applyBorder() }
    }
}

/** Styles sample name buttons with outline border and active-state highlight. */
val SampleButtonStyle = CssStyle {
    base {
        val palette = colorMode.toSitePalette()
        Modifier
            .border(1.px, LineStyle.Solid, palette.outline)
            .borderRadius(0.5.cssRem)
            .padding(topBottom = SiteTheme.dimensions.size.Md, leftRight = 0.875.cssRem)
            .fontSize(0.8.cssRem)
            .fontWeight(FontWeight.Medium)
            .color(palette.onSurfaceVariant)
            .backgroundColor(palette.surface)
            .cursor(Cursor.Pointer)
            .transition(
                Transition.of("color", duration = 150.ms, timingFunction = TransitionTimingFunction.Ease),
                Transition.of("background-color", duration = 150.ms, timingFunction = TransitionTimingFunction.Ease),
                Transition.of("border-color", duration = 150.ms, timingFunction = TransitionTimingFunction.Ease),
            )
    }
    cssRule(":hover") {
        Modifier.color(colorMode.toSitePalette().primary)
    }
    cssRule(".active") {
        val palette = colorMode.toSitePalette()
        Modifier
            .backgroundColor(palette.primary)
            .color(palette.onPrimary)
            .border(1.px, LineStyle.Solid, palette.primary)
    }
    cssRule(".active:hover") {
        val palette = colorMode.toSitePalette()
        Modifier.backgroundColor(palette.primaryContainer)
    }
}

/** Component kind marker for toolbar toggle buttons. */
sealed interface ToolbarButtonKind : ComponentKind

/** Styles individual toolbar buttons with active-state highlight. */
val ToolbarButtonStyle = CssStyle<ToolbarButtonKind> {
    base {
        val sitePalette = colorMode.toSitePalette()
        Modifier
            .backgroundColor(sitePalette.surface)
            .padding(topBottom = SiteTheme.dimensions.size.Sm, leftRight = SiteTheme.dimensions.size.Md)
            .fontSize(0.75.cssRem)
            .cursor(Cursor.Pointer)
            .color(sitePalette.surface.inverted())
            .fontWeight(FontWeight.Medium)
            .transition(
                Transition.of("color", duration = 150.ms, timingFunction = TransitionTimingFunction.Ease),
                Transition.of("background-color", duration = 150.ms, timingFunction = TransitionTimingFunction.Ease),
            )
    }
    cssRule(":hover") {
        Modifier.color(colorMode.toSitePalette().primary)
    }
    cssRule(".active") {
        val sitePalette = colorMode.toSitePalette()
        Modifier
            .backgroundColor(sitePalette.primary.darkened(byPercent = .65f))
            .color(sitePalette.primary.lightened(byPercent = .5f))
    }
}

/**
 * Toolbar row with sample selector, input-mode toggle (paste/upload),
 * format selector (SVG/AVG), and a convert button.
 */
@Composable
fun PlaygroundToolbar(
    inputMode: String,
    isConverting: Boolean,
    sampleNames: List<String>,
    selectedSample: Int,
    onSampleSelect: (Int) -> Unit = {},
    onInputModeChange: (String) -> Unit = {},
    onConvert: () -> Unit = {},
) {
    Div(attrs = ToolbarStyle.toModifier().toAttrs()) {
        SampleButtons(sampleNames, selectedSample, onSampleSelect)
        ToolbarSeparator()
        InputModeButtons(inputMode, onInputModeChange)
        ConvertButton(isConverting, onConvert)
    }
}

@Composable
private fun SampleButtons(sampleNames: List<String>, selectedSample: Int, onSampleSelect: (Int) -> Unit) {
    Div(
        attrs = Modifier
            .display(DisplayStyle.Flex)
            .flexWrap(FlexWrap.Wrap)
            .gap(0.375.cssRem)
            .toAttrs(),
    ) {
        sampleNames.forEachIndexed { index, name ->
            Button(
                attrs = SampleButtonStyle.toModifier()
                    .onClick { onSampleSelect(index) }
                    .toAttrs {
                        if (index == selectedSample) classes("active")
                    },
            ) {
                SpanText(name)
            }
        }
    }
}

@Composable
private fun ToolbarSeparator() {
    Div(
        attrs = Modifier
            .width(1.px)
            .height(1.cssRem)
            .backgroundColor(ColorMode.current.toSitePalette().outline)
            .toAttrs(),
    )
}

@Composable
private fun InputModeButtons(inputMode: String, onInputModeChange: (String) -> Unit) {
    ToolbarButtonRow(modifier = ToolbarButtonRowStyle.toModifier()) {
        ToolbarButton(
            active = inputMode == "paste",
            onClick = { onInputModeChange("paste") },
        ) {
            FaClipboard()
            SpanText("Paste Code")
        }
        ToolbarButton(
            active = inputMode == "upload",
            onClick = { onInputModeChange("upload") },
        ) {
            FaUpload()
            SpanText("Upload File")
        }
    }
}

@Composable
private fun ConvertButton(isConverting: Boolean, onConvert: () -> Unit) {
    Button(
        attrs = ConvertButtonStyle.toModifier()
            .display(DisplayStyle.Flex).alignItems(AlignItems.Center).gap(0.4.cssRem)
            .let { if (isConverting) it.opacity(value = 0.7f).cursor(Cursor.Default) else it }
            .onClick { if (!isConverting) onConvert() }
            .toAttrs(),
    ) {
        if (isConverting) {
            FaSpinner(modifier = SpinnerIconStyle.toModifier())
            SpanText("Converting...")
        } else {
            FaPlay()
            SpanText("Convert")
        }
    }
}

/** Row wrapper that applies [ToolbarButtonRowStyle] for grouped button layout. */
@Composable
fun ToolbarButtonRow(modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit) {
    Row(modifier = ToolbarButtonRowStyle.toModifier().then(modifier), content = content)
}

/** Styled button that toggles an `active` CSS class based on the [active] flag. */
@Composable
fun ToolbarButton(
    active: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: CssStyleVariant<ToolbarButtonKind>? = null,
    content: @Composable () -> Unit,
) {
    Button(
        attrs = ToolbarButtonStyle
            .toModifier(variant)
            .then(modifier)
            .display(DisplayStyle.Flex).alignItems(AlignItems.Center).gap(0.35.cssRem)
            .onClick { onClick() }
            .toAttrs {
                if (active) {
                    classes("active")
                }
            },
    ) {
        content()
    }
}
