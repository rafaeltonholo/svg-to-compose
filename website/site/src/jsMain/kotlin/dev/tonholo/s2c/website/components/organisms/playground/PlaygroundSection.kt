package dev.tonholo.s2c.website.components.organisms.playground

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.StyleVariable
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.css.autoLength
import com.varabyte.kobweb.compose.css.functions.LinearGradient
import com.varabyte.kobweb.compose.css.functions.linearGradient
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.backgroundImage
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flex
import com.varabyte.kobweb.compose.ui.modifiers.flexDirection
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.gridTemplateColumns
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.setVariable
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.breakpoint.displayIfAtLeast
import com.varabyte.kobweb.silk.style.breakpoint.displayUntil
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.worker.rememberWorker
import dev.tonholo.s2c.website.GradientTextStyle
import dev.tonholo.s2c.website.HeadlineTextStyle
import dev.tonholo.s2c.website.SiteTheme
import dev.tonholo.s2c.website.SubheadlineTextStyle
import dev.tonholo.s2c.website.components.atoms.AnimateOnScroll
import dev.tonholo.s2c.website.components.molecules.OptionsSection
import dev.tonholo.s2c.website.components.molecules.SampleButtons
import dev.tonholo.s2c.website.components.molecules.SectionContainer
import dev.tonholo.s2c.website.components.molecules.playground.InputPanel
import dev.tonholo.s2c.website.components.molecules.playground.OutputPanel
import dev.tonholo.s2c.website.components.molecules.playground.PlaygroundToolbar
import dev.tonholo.s2c.website.components.molecules.playground.PreviewPanel
import dev.tonholo.s2c.website.components.organisms.playground.PlaygroundSectionVars.GradientBackground
import dev.tonholo.s2c.website.state.playground.PlaygroundState
import dev.tonholo.s2c.website.state.playground.PlaygroundState.Companion.samples
import dev.tonholo.s2c.website.toSitePalette
import dev.tonholo.s2c.website.worker.ConversionInput
import dev.tonholo.s2c.website.worker.ConversionOutput
import dev.tonholo.s2c.website.worker.IconConvertWorker
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.deg
import org.jetbrains.compose.web.css.fr
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

// region Styles

object PlaygroundSectionVars {
    val GradientBackground by StyleVariable<LinearGradient>()
}

/** Styles the playground heading area with centered column layout and bottom margin. */
val PlaygroundHeadingContainerStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .alignItems(AlignItems.Center)
        .gap(1.cssRem)
        .margin(bottom = 2.cssRem)
}

/** Styles the editor panel wrapper with rounded border and hidden overflow. */
val EditorPanelStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .borderRadius(0.75.cssRem)
        .border(1.px, LineStyle.Solid, colorMode.toSitePalette().borderStrong)
        .overflow(Overflow.Hidden)
}

/** Styles the gradient purple convert action button. */
val ConvertButtonStyle = CssStyle.base {
    Modifier
        .backgroundImage(GradientBackground.value())
        .color(Colors.White)
        .borderRadius(0.5.cssRem)
        .padding(topBottom = 0.375.cssRem, leftRight = 1.cssRem)
        .fontWeight(FontWeight.SemiBold)
        .fontSize(0.8.cssRem)
        .cursor(Cursor.Pointer)
        .border(0.px, LineStyle.None, Colors.Transparent)
        .transition(Transition.all(duration = 200.ms, timingFunction = TransitionTimingFunction.Ease))
        .margin(left = autoLength)
}

/** Styles the three-column desktop grid layout for input, preview, and output panels. */
val DesktopPanelsStyle = CssStyle {
    base {
        Modifier
            .display(DisplayStyle.Grid)
            .gridTemplateColumns { repeat(3) { size(1.fr) } }
            .minWidth(0.px)
            .minHeight(460.px)
            .overflow(Overflow.Hidden)
    }
    cssRule(" > *") {
        Modifier
            .overflow(Overflow.Auto)
            .minWidth(0.px)
    }
}

/** Styles the mobile tab bar row with compact gap and header background. */
val MobileTabBarStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .gap(0.25.cssRem)
        .padding(0.5.cssRem)
        .backgroundColor(colorMode.toSitePalette().surfaceHeader)
}

/** Styles individual mobile tab buttons with active-state gradient highlight. */
val MobileTabStyle = CssStyle {
    base {
        Modifier
            .padding(topBottom = 0.5.cssRem, leftRight = 1.cssRem)
            .borderRadius(0.5.cssRem)
            .cursor(Cursor.Pointer)
            .fontWeight(FontWeight.Medium)
            .fontSize(0.8.cssRem)
            .border(0.px, LineStyle.None, Colors.Transparent)
            .transition(Transition.all(duration = 200.ms, timingFunction = TransitionTimingFunction.Ease))
            .flex(1)
            .textAlign(TextAlign.Center)
            .backgroundColor(Colors.Transparent)
            .color(colorMode.toSitePalette().muted)
    }

    cssRule(".active") {
        Modifier
            .backgroundImage(GradientBackground.value())
            .color(Colors.White)
    }
}

/** Styles the mobile panel content area with minimum height and alt background. */
val MobilePanelContentStyle = CssStyle.base {
    Modifier
        .minHeight(300.px)
        .backgroundColor(colorMode.toSitePalette().surfaceAlt)
}

// endregion


/**
 * Full-width playground section containing the code editor, live preview,
 * output panel, sample selector, and conversion options.
 */
@Composable
fun PlaygroundSection() {
    val purple = SiteTheme.palette.brand.purple
    val deepPurple = SiteTheme.palette.brand.purpleDeep
    SectionContainer(
        id = "playground",
        contentModifier = Modifier
            .maxWidth(96.cssRem)
            .setVariable(
                GradientBackground,
                linearGradient(angle = 135.deg) {
                    add(color = purple)
                    add(color = deepPurple)
                },
            ),
    ) {
        var state by remember { mutableStateOf(PlaygroundState()) }

        val worker = rememberWorker {
            IconConvertWorker { output ->
                when (output) {
                    is ConversionOutput.Progress -> {
                        state = state.copy(
                            isConverting = true,
                            conversionProgress = output.stage,
                        )
                    }

                    is ConversionOutput.Success -> {
                        state = state.copy(
                            convertedKotlinCode = output.kotlinCode,
                            iconFileContentsJson = output.iconFileContentsJson,
                            isConverting = false,
                            conversionProgress = "",
                            conversionError = null,
                        )
                    }

                    is ConversionOutput.Error -> {
                        state = state.copy(
                            isConverting = false,
                            conversionProgress = "",
                            conversionError = output.message,
                        )
                    }
                }
            }
        }

        val onConvert = {
            state = state.copy(
                isConverting = true,
                conversionError = null,
                conversionProgress = "Starting conversion...",
            )
            worker.postInput(
                ConversionInput(
                    svgContent = state.inputCode,
                    iconName = "MyIcon",
                    fileType = state.extension,
                    optimize = state.options.optimize,
                    pkg = state.options.pkg,
                    theme = state.options.theme,
                    noPreview = state.options.noPreview,
                    makeInternal = state.options.makeInternal,
                    minified = state.options.minified,
                    kmpPreview = state.options.kmpPreview,
                    receiverType = state.options.receiverType,
                ),
            )
        }

        // Heading
        AnimateOnScroll {
            Div(attrs = PlaygroundHeadingContainerStyle.toModifier().toAttrs()) {
                Div(attrs = HeadlineTextStyle.toAttrs()) {
                    Span(attrs = GradientTextStyle.toModifier().toAttrs()) {
                        Text("Playground")
                    }
                }
                Div(attrs = SubheadlineTextStyle.toAttrs()) {
                    SpanText(
                        "Paste your SVG or AVG code and see the generated Kotlin ImageVector instantly.",
                    )
                }
            }
        }

        // Sample buttons
        SampleButtons(
            sampleNames = samples.map { it.name },
            selectedSample = state.selectedSample,
            onSelect = { index ->
                state = state.copy(
                    selectedSample = index,
                    inputCode = samples[index].svgCode,
                )
            },
        )

        // Editor panel
        Div(attrs = EditorPanelStyle.toModifier().toAttrs()) {
            PlaygroundToolbar(
                inputMode = state.inputMode,
                extension = state.extension,
                isConverting = state.isConverting,
                onInputModeChange = { state = state.copy(inputMode = it) },
                onExtensionChange = { state = state.copy(extension = it) },
                onConvert = onConvert,
            )
            DesktopPanels(
                state = state,
                onInputChange = { state = state.copy(inputCode = it) },
            )
            MobilePanels(
                state = state,
                onPanelSelect = { state = state.copy(activePanel = it) },
                onInputChange = { state = state.copy(inputCode = it) },
            )
        }

        // Options
        OptionsSection(
            options = state.options,
            onOptionsChange = { state = state.copy(options = it) },
        )
    }
}

/** Desktop three-column grid containing input, preview, and output panels. */
@Composable
private fun DesktopPanels(
    state: PlaygroundState,
    onInputChange: (String) -> Unit,
) {
    Div(
        attrs = DesktopPanelsStyle.toModifier()
            .displayIfAtLeast(Breakpoint.MD)
            .toAttrs(),
    ) {
        InputPanel(state.inputCode, onInputChange)
        PreviewPanel(svgCode = state.inputCode, iconFileContentsJson = state.iconFileContentsJson)
        OutputPanel(
            kotlinCode = state.convertedKotlinCode,
            isConverting = state.isConverting,
            conversionProgress = state.conversionProgress,
            conversionError = state.conversionError,
        )
    }
}

/** Mobile tabbed view switching between input, preview, and output panels. */
@Composable
private fun MobilePanels(
    state: PlaygroundState,
    onPanelSelect: (Int) -> Unit,
    onInputChange: (String) -> Unit,
) {
    val mobileTabs = listOf("Input", "Preview", "Output")
    Div(attrs = Modifier.displayUntil(Breakpoint.MD).toAttrs()) {
        // Tab bar
        Div(attrs = MobileTabBarStyle.toModifier().toAttrs()) {
            mobileTabs.forEachIndexed { index, tab ->
                Div(
                    attrs = MobileTabStyle
                        .toModifier()
                        .onClick { onPanelSelect(index) }
                        .toAttrs {
                            if (index == state.activePanel) {
                                classes("active")
                            }
                        },
                ) {
                    SpanText(tab)
                }
            }
        }
        // Active panel content
        Div(attrs = MobilePanelContentStyle.toModifier().toAttrs()) {
            when (state.activePanel) {
                0 -> InputPanel(state.inputCode, onInputChange)
                1 -> PreviewPanel(
                    svgCode = state.inputCode,
                    iconFileContentsJson = state.iconFileContentsJson,
                )

                2 -> OutputPanel(
                    kotlinCode = state.convertedKotlinCode,
                    isConverting = state.isConverting,
                    conversionProgress = state.conversionProgress,
                    conversionError = state.conversionError,
                )
            }
        }
    }
}
