package dev.tonholo.s2c.website.components.molecules.playground

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.AnimationIterationCount
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.JustifyContent
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.css.WhiteSpace
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.animation
import com.varabyte.kobweb.compose.ui.modifiers.ariaLabel
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.flex
import com.varabyte.kobweb.compose.ui.modifiers.flexDirection
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.whiteSpace
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaCheck
import com.varabyte.kobweb.silk.components.icons.fa.FaCircleExclamation
import com.varabyte.kobweb.silk.components.icons.fa.FaCode
import com.varabyte.kobweb.silk.components.icons.fa.FaCopy
import com.varabyte.kobweb.silk.components.icons.fa.FaSpinner
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.SpinKeyframes
import dev.tonholo.s2c.website.components.molecules.PanelHeaderStyle
import dev.tonholo.s2c.website.shiki.ShikiCodeBlock
import dev.tonholo.s2c.website.theme.typography.FontFamilies
import dev.tonholo.s2c.website.toSitePalette
import kotlinx.browser.window
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.AnimationTimingFunction
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

/** Styles the output panel column with surface background. */
val OutputPanelStyle = CssStyle.base {
    Modifier
        .backgroundColor(colorMode.toSitePalette().surface)
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .flex(1)
        .minHeight(0.px)
        .overflow(Overflow.Auto)
}

/** Styles the spinner icon with a continuous rotation animation. */
val SpinnerIconStyle = CssStyle.base {
    Modifier
        .animation(
            SpinKeyframes.toAnimation(
                duration = 1000.ms,
                timingFunction = AnimationTimingFunction.Linear,
                iterationCount = AnimationIterationCount.Infinite,
            ),
        )
}

/** Styles the monospace code block inside the output panel. */
val OutputCodeStyle = CssStyle.base {
    Modifier
        .fillMaxSize()
        .fontFamily(values = FontFamilies.mono)
        .fontSize(0.7.cssRem)
        .lineHeight(value = 1.6)
        .overflow(Overflow.Auto)
        .margin(0.px)
        .whiteSpace(WhiteSpace.Pre)
        .flex(1)
}

private const val COPY_FEEDBACK_DURATION_MS = 2000L

/**
 * Panel that displays the generated Kotlin code, conversion progress, or error state.
 *
 * @param kotlinCode Generated Kotlin source to display.
 * @param isConverting Whether a conversion is currently in progress.
 * @param conversionProgress Human-readable progress message.
 * @param conversionError Error message if the conversion failed, or `null`.
 */
@Composable
fun OutputPanel(
    kotlinCode: String,
    isConverting: Boolean = false,
    conversionProgress: String = "",
    conversionError: String? = null,
    fileName: String = "MyIcon.kt",
) {
    Div(attrs = OutputPanelStyle.toModifier().toAttrs()) {
        OutputPanelHeader(fileName, kotlinCode)
        OutputPanelContent(kotlinCode, isConverting, conversionProgress, conversionError)
    }
}

@Composable
private fun OutputPanelHeader(fileName: String, kotlinCode: String) {
    ColorMode.current.toSitePalette()
    var copied by remember { mutableStateOf(false) }

    LaunchedEffect(copied) {
        if (copied) {
            delay(COPY_FEEDBACK_DURATION_MS)
            copied = false
        }
    }

    Div(
        attrs = PanelHeaderStyle.toModifier()
            .display(DisplayStyle.Flex)
            .alignItems(AlignItems.Center)
            .justifyContent(JustifyContent.SpaceBetween)
            .toAttrs(),
    ) {
        Div(
            attrs = Modifier
                .display(DisplayStyle.Flex).alignItems(AlignItems.Center).gap(0.4.cssRem)
                .toAttrs(),
        ) {
            FaCode()
            SpanText(fileName)
        }
        if (kotlinCode.isNotEmpty()) {
            CopyButton(
                code = kotlinCode,
                copied = copied,
                onCopy = { copied = true },
            )
        }
    }
}

@Composable
private fun CopyButton(code: String, copied: Boolean, onCopy: () -> Unit) {
    val palette = ColorMode.current.toSitePalette()
    Div(
        attrs = Modifier
            .display(DisplayStyle.Flex).alignItems(AlignItems.Center).gap(0.3.cssRem)
            .fontSize(0.75.cssRem)
            .cursor(Cursor.Pointer)
            .color(if (copied) palette.primary else palette.onSurfaceVariant)
            .ariaLabel(if (copied) "Copied to clipboard" else "Copy code to clipboard")
            .transition(
                Transition.of("color", duration = 200.ms, timingFunction = TransitionTimingFunction.Ease),
            )
            .onClick {
                window.navigator.clipboard.writeText(code)
                onCopy()
            }
            .toAttrs(),
    ) {
        if (copied) {
            FaCheck(size = IconSize.LG)
            SpanText("Copied!")
        } else {
            FaCopy(size = IconSize.LG)
            SpanText("Copy")
        }
    }
}

@Composable
private fun OutputPanelContent(
    kotlinCode: String,
    isConverting: Boolean,
    conversionProgress: String,
    conversionError: String?,
) {
    val palette = ColorMode.current.toSitePalette()
    when {
        isConverting -> OutputConvertingState(conversionProgress, palette)

        conversionError != null -> OutputErrorState(conversionError, palette)

        kotlinCode.isEmpty() -> OutputEmptyState(palette)

        else -> ShikiCodeBlock(
            language = "kotlin",
            code = kotlinCode,
            modifier = OutputCodeStyle.toModifier(),
        )
    }
}

@Composable
private fun OutputConvertingState(progress: String, palette: dev.tonholo.s2c.website.SitePalette) {
    Column(
        modifier = Modifier.fillMaxSize().padding(2.cssRem),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        FaSpinner(
            modifier = SpinnerIconStyle.toModifier()
                .fontSize(1.5.cssRem)
                .color(palette.primary),
        )
        SpanText(
            progress,
            modifier = Modifier
                .fontSize(0.75.cssRem)
                .color(palette.onSurfaceVariant)
                .padding(top = 0.5.cssRem)
                .transition(
                    Transition.of("opacity", duration = 200.ms, timingFunction = TransitionTimingFunction.Ease),
                ),
        )
    }
}

@Composable
private fun OutputErrorState(error: String, palette: dev.tonholo.s2c.website.SitePalette) {
    Column(
        modifier = Modifier.fillMaxSize().padding(2.cssRem),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        FaCircleExclamation(
            modifier = Modifier.fontSize(1.5.cssRem).color(palette.error),
        )
        SpanText(
            error,
            modifier = Modifier
                .fontSize(0.75.cssRem)
                .color(palette.error)
                .padding(top = 0.5.cssRem),
        )
    }
}

@Composable
private fun OutputEmptyState(palette: dev.tonholo.s2c.website.SitePalette) {
    Column(
        modifier = Modifier.fillMaxSize().padding(2.cssRem),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        FaCode(
            modifier = Modifier.fontSize(1.5.cssRem).color(palette.onSurfaceVariant),
        )
        SpanText(
            "Click Convert to see output",
            modifier = Modifier
                .fontSize(0.75.cssRem)
                .color(palette.onSurfaceVariant)
                .padding(top = 0.5.cssRem),
        )
    }
}
