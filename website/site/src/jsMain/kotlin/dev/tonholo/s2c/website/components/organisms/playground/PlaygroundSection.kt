package dev.tonholo.s2c.website.components.organisms.playground

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
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
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.role
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.modifiers.tabIndex
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.breakpoint.displayIfAtLeast
import com.varabyte.kobweb.silk.style.breakpoint.displayUntil
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.worker.rememberWorker
import dev.tonholo.s2c.website.LabelTextStyle
import dev.tonholo.s2c.website.SiteTheme
import dev.tonholo.s2c.website.components.molecules.CollapsibleSection
import dev.tonholo.s2c.website.components.molecules.OptionsContent
import dev.tonholo.s2c.website.components.molecules.SectionContainer
import dev.tonholo.s2c.website.components.molecules.playground.ComparisonStrip
import dev.tonholo.s2c.website.components.molecules.playground.InputPanel
import dev.tonholo.s2c.website.components.molecules.playground.OutputPanel
import dev.tonholo.s2c.website.components.molecules.playground.PlaygroundToolbar
import dev.tonholo.s2c.website.state.playground.PlaygroundState
import dev.tonholo.s2c.website.state.playground.PlaygroundState.Companion.samples
import dev.tonholo.s2c.website.toSitePalette
import dev.tonholo.s2c.website.worker.ConversionInput
import dev.tonholo.s2c.website.worker.ConversionOutput
import dev.tonholo.s2c.website.worker.IconConvertWorker
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.fr
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

// region Styles

/** Styles the playground heading area with bottom margin. */
val PlaygroundHeadingContainerStyle = CssStyle.base {
    Modifier.margin(bottom = 1.cssRem)
}

/** Styles the editor panel wrapper with rounded border and hidden overflow. */
val EditorPanelStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .borderRadius(0.75.cssRem)
        .border(1.px, LineStyle.Solid, colorMode.toSitePalette().outlineVariant)
        .overflow(Overflow.Hidden)
}

/** Styles the solid purple convert action button. */
val ConvertButtonStyle = CssStyle {
    base {
        val palette = colorMode.toSitePalette()
        Modifier
            .backgroundColor(palette.primary)
            .color(palette.onPrimary)
            .borderRadius(0.5.cssRem)
            .padding(topBottom = 0.5.cssRem, leftRight = 1.cssRem)
            .fontWeight(FontWeight.SemiBold)
            .fontSize(0.8.cssRem)
            .cursor(Cursor.Pointer)
            .border(0.px, LineStyle.None, Colors.Transparent)
            .transition(
                Transition.of("color", duration = 150.ms, timingFunction = TransitionTimingFunction.Ease),
                Transition.of("background-color", duration = 150.ms, timingFunction = TransitionTimingFunction.Ease),
            )
    }
    cssRule(":hover") {
        Modifier.backgroundColor(colorMode.toSitePalette().primaryContainer)
    }
}

/** Styles the two-column desktop grid for input and output panels. */
val DesktopPanelsStyle = CssStyle {
    base {
        Modifier
            .display(DisplayStyle.Grid)
            .gridTemplateColumns { size(2.fr); size(3.fr) }
            .minWidth(0.px)
            .minHeight(300.px)
            .overflow(Overflow.Hidden)
            // No native Kobweb modifier for max-height with this value
            .styleModifier {
                property("max-height", "500px")
            }
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
        .backgroundColor(colorMode.toSitePalette().surfaceVariant)
}

/** Styles individual mobile tab buttons with active-state highlight. */
val MobileTabStyle = CssStyle {
    base {
        Modifier
            .padding(topBottom = 0.5.cssRem, leftRight = 1.cssRem)
            .borderRadius(0.5.cssRem)
            .cursor(Cursor.Pointer)
            .fontWeight(FontWeight.Medium)
            .fontSize(0.8.cssRem)
            .border(0.px, LineStyle.None, Colors.Transparent)
            .transition(
                Transition.of("color", duration = 150.ms, timingFunction = TransitionTimingFunction.Ease),
                Transition.of("background-color", duration = 150.ms, timingFunction = TransitionTimingFunction.Ease),
            )
            .flex(1)
            .textAlign(TextAlign.Center)
            .backgroundColor(Colors.Transparent)
            .color(colorMode.toSitePalette().onSurfaceVariant)
    }

    cssRule(".active") {
        val palette = colorMode.toSitePalette()
        Modifier
            .backgroundColor(palette.primary)
            .color(palette.onPrimary)
    }
}

/** Styles the mobile panel content area with explicit height so child
 * panels (InputPanel, OutputPanel) can flex to fill the space. */
val MobilePanelContentStyle = CssStyle.base {
    Modifier
        .height(50.vh)
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .backgroundColor(colorMode.toSitePalette().surfaceVariant)
}

// endregion


/**
 * Full-width playground section containing the code editor, live preview,
 * output panel, sample selector, and conversion options.
 */
@Composable
fun PlaygroundSection() {
    SectionContainer(
        id = "playground",
        contentModifier = Modifier.maxWidth(96.cssRem),
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
                zoomLevel = 1f,
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
        Div(attrs = PlaygroundHeadingContainerStyle.toModifier().toAttrs()) {
            SpanText(
                "Try it",
                modifier = LabelTextStyle.toModifier()
                    .color(SiteTheme.palette.onSurfaceVariant),
            )
        }

        // Editor panel
        Div(attrs = EditorPanelStyle.toModifier().toAttrs()) {
            PlaygroundToolbar(
                inputMode = state.inputMode,
                extension = state.extension,
                isConverting = state.isConverting,
                sampleNames = samples.map { it.name },
                selectedSample = state.selectedSample,
                onSampleSelect = { index ->
                    state = state.copy(
                        selectedSample = index,
                        inputCode = samples[index].svgCode,
                    )
                },
                onInputModeChange = { state = state.copy(inputMode = it) },
                onExtensionChange = { state = state.copy(extension = it) },
                onConvert = onConvert,
            )

            // Collapsible options
            CollapsibleSection(title = "Options") {
                OptionsContent(
                    options = state.options,
                    onOptionsChange = { state = state.copy(options = it) },
                )
            }

            // Desktop comparison strip (200px)
            Div(attrs = Modifier.displayIfAtLeast(Breakpoint.MD).toAttrs()) {
                ComparisonStrip(
                    svgCode = state.inputCode,
                    extension = state.extension,
                    iconFileContentsJson = state.iconFileContentsJson,
                    zoomLevel = state.zoomLevel,
                    onZoomChange = { state = state.copy(zoomLevel = it) },
                    previewSizePx = 200,
                )
            }

            // Mobile comparison strip (140px)
            Div(attrs = Modifier.displayUntil(Breakpoint.MD).toAttrs()) {
                ComparisonStrip(
                    svgCode = state.inputCode,
                    extension = state.extension,
                    iconFileContentsJson = state.iconFileContentsJson,
                    zoomLevel = state.zoomLevel,
                    onZoomChange = { state = state.copy(zoomLevel = it) },
                    previewSizePx = 140,
                )
            }

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
    }
}

/** Desktop two-column grid containing input and output panels. */
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
        OutputPanel(
            kotlinCode = state.convertedKotlinCode,
            isConverting = state.isConverting,
            conversionProgress = state.conversionProgress,
            conversionError = state.conversionError,
        )
    }
}

/** Mobile tabbed view switching between input and output panels. */
@Composable
private fun MobilePanels(
    state: PlaygroundState,
    onPanelSelect: (Int) -> Unit,
    onInputChange: (String) -> Unit,
) {
    val mobileTabs = listOf("Input", "Output")
    Div(attrs = Modifier.displayUntil(Breakpoint.MD).toAttrs()) {
        // Tab bar
        Div(
            attrs = MobileTabBarStyle.toModifier()
                .role("tablist")
                .toAttrs(),
        ) {
            mobileTabs.forEachIndexed { index, tab ->
                val isSelected = index == state.activePanel
                Div(
                    attrs = MobileTabStyle
                        .toModifier()
                        .onClick { onPanelSelect(index) }
                        .tabIndex(if (isSelected) 0 else -1)
                        .toAttrs {
                            attr("role", "tab")
                            attr("aria-selected", isSelected.toString())
                            if (isSelected) {
                                classes("active")
                            }
                            onKeyDown { event ->
                                when (event.key) {
                                    "ArrowLeft" -> {
                                        event.preventDefault()
                                        val prev = if (index > 0) index - 1 else mobileTabs.lastIndex
                                        onPanelSelect(prev)
                                    }
                                    "ArrowRight" -> {
                                        event.preventDefault()
                                        val next = if (index < mobileTabs.lastIndex) index + 1 else 0
                                        onPanelSelect(next)
                                    }
                                    " ", "Enter" -> {
                                        event.preventDefault()
                                        onPanelSelect(index)
                                    }
                                }
                            }
                        },
                ) {
                    SpanText(tab)
                }
            }
        }
        // Active panel content
        Div(
            attrs = MobilePanelContentStyle.toModifier().toAttrs {
                attr("role", "tabpanel")
            },
        ) {
            when (state.activePanel) {
                0 -> InputPanel(state.inputCode, onInputChange)
                1 -> OutputPanel(
                    kotlinCode = state.convertedKotlinCode,
                    isConverting = state.isConverting,
                    conversionProgress = state.conversionProgress,
                    conversionError = state.conversionError,
                )
            }
        }
    }
}
