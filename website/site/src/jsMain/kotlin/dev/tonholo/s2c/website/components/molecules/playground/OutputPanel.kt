package dev.tonholo.s2c.website.components.molecules.playground

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.JustifyContent
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.WhiteSpace
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
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
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.animation
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.whiteSpace
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaCode
import com.varabyte.kobweb.silk.components.icons.fa.FaCopy
import com.varabyte.kobweb.silk.components.icons.fa.FaCircleExclamation
import com.varabyte.kobweb.silk.components.icons.fa.FaSpinner
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.animation.toAnimation
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.SpinKeyframes
import dev.tonholo.s2c.website.components.molecules.PanelHeaderStyle
import dev.tonholo.s2c.website.shiki.ShikiCodeBlock
import dev.tonholo.s2c.website.toSitePalette
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
}

/** Styles the spinner icon with a continuous rotation animation. */
val SpinnerIconStyle = CssStyle.base {
    Modifier
        .animation(
            SpinKeyframes.toAnimation(
                duration = 1000.ms,
                timingFunction = AnimationTimingFunction.Linear,
            ),
        )
        .styleModifier {
            property("animation-iteration-count", "infinite")
        }
}

/** Styles the monospace code block inside the output panel. */
val OutputCodeStyle = CssStyle.base {
    Modifier
        .fillMaxSize()
        .fontFamily("JetBrains Mono", "monospace")
        .fontSize(0.7.cssRem)
        .lineHeight(value = 1.6)
        .overflow(Overflow.Auto)
        .margin(0.px)
        .whiteSpace(WhiteSpace.Pre)
        .flex(1)
}

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
) {
    val palette = ColorMode.current.toSitePalette()
    Div(attrs = OutputPanelStyle.toModifier().toAttrs()) {
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
                SpanText("MyIcon.kt")
            }
            if (kotlinCode.isNotEmpty()) {
                Div(
                    attrs = Modifier
                        .display(DisplayStyle.Flex).alignItems(AlignItems.Center).gap(0.3.cssRem)
                        .fontSize(0.65.cssRem)
                        .cursor(Cursor.Pointer)
                        .toAttrs(),
                ) {
                    FaCopy(size = IconSize.LG)
                    SpanText("Copy")
                }
            }
        }

        when {
            isConverting -> {
                Column(
                    modifier = Modifier.fillMaxSize().padding(2.cssRem),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    FaSpinner(
                        modifier = SpinnerIconStyle
                            .toModifier()
                            .fontSize(1.5.cssRem)
                            .color(palette.brand.purple),
                    )
                    SpanText(
                        conversionProgress,
                        modifier = Modifier
                            .fontSize(0.75.cssRem)
                            .color(palette.muted)
                            .padding(top = 0.5.cssRem),
                    )
                }
            }
            conversionError != null -> {
                Column(
                    modifier = Modifier.fillMaxSize().padding(2.cssRem),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    FaCircleExclamation(
                        modifier = Modifier
                            .fontSize(1.5.cssRem)
                            .color(palette.brand.red),
                    )
                    SpanText(
                        conversionError,
                        modifier = Modifier
                            .fontSize(0.75.cssRem)
                            .color(palette.brand.red)
                            .padding(top = 0.5.cssRem),
                    )
                }
            }
            kotlinCode.isEmpty() -> {
                Column(
                    modifier = Modifier.fillMaxSize().padding(2.cssRem),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    FaCode(
                        modifier = Modifier
                            .fontSize(1.5.cssRem)
                            .color(palette.muted),
                    )
                    SpanText(
                        "Click Convert to see output",
                        modifier = Modifier
                            .fontSize(0.75.cssRem)
                            .color(palette.muted)
                            .padding(top = 0.5.cssRem),
                    )
                }
            }
            else -> {
                ShikiCodeBlock(
                    language = "kotlin",
                    code = kotlinCode,
                    modifier = OutputCodeStyle.toModifier(),
                )
            }
        }
    }
}
