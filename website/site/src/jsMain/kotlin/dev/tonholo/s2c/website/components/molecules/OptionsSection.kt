package dev.tonholo.s2c.website.components.molecules

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.WhiteSpace
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flexDirection
import com.varabyte.kobweb.compose.ui.modifiers.flexWrap
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.gridTemplateColumns
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
import com.varabyte.kobweb.compose.ui.modifiers.outline
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.whiteSpace
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.components.atoms.ToggleSwitch
import dev.tonholo.s2c.website.state.playground.PlaygroundOptions
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.fr
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.TextInput

/** Styles the options container with rounded border and hidden overflow. */
val OptionsContainerStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .margin(top = 1.cssRem)
        .borderRadius(0.75.cssRem)
        .border(1.px, LineStyle.Solid, colorMode.toSitePalette().outlineVariant)
        .overflow(Overflow.Hidden)
}

/** Styles monospace text inputs used for option fields. */
val OptionInputStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .padding(topBottom = 0.375.cssRem, leftRight = 0.75.cssRem)
        .borderRadius(0.375.cssRem)
        .border(1.px, LineStyle.Solid, colorMode.toSitePalette().outline)
        .backgroundColor(colorMode.toSitePalette().surfaceVariant)
        .color(colorMode.toSitePalette().onSurfaceVariant)
        .fontSize(0.8.cssRem)
        .fontFamily("JetBrains Mono", "monospace")
        .outline(style = LineStyle.None)
}

/** Responsive grid for text inputs: 1 column on mobile, 3 columns on desktop. */
val OptionsInputGridStyle = CssStyle {
    base {
        Modifier
            .display(DisplayStyle.Grid)
            .gridTemplateColumns { size(1.fr) }
            .gap(1.cssRem)
    }
    Breakpoint.MD {
        Modifier.gridTemplateColumns { repeat(3) { size(1.fr) } }
    }
}

/**
 * Always-visible section displaying conversion options (toggles and text inputs).
 *
 * @param options Current playground option values.
 * @param onOptionsChange Callback invoked when any option changes.
 */
@Composable
fun OptionsSection(
    options: PlaygroundOptions,
    onOptionsChange: (PlaygroundOptions) -> Unit,
) {
    val palette = ColorMode.current.toSitePalette()

    Div(attrs = OptionsContainerStyle.toModifier().toAttrs()) {
        Div(
            attrs = Modifier
                .padding(1.cssRem)
                .display(DisplayStyle.Flex)
                .flexDirection(FlexDirection.Column)
                .gap(1.cssRem)
                .toAttrs(),
        ) {
            // Toggle switches row
            Div(
                attrs = Modifier
                    .display(DisplayStyle.Flex)
                    .flexWrap(FlexWrap.Wrap)
                    .gap(1.5.cssRem)
                    .toAttrs(),
            ) {
                OptionToggle("Optimize", options.optimize) {
                    onOptionsChange(options.copy(optimize = it))
                }
                OptionToggle("Minify", options.minified) {
                    onOptionsChange(options.copy(minified = it))
                }
                OptionToggle("KMP Compatible", options.kmpPreview) {
                    onOptionsChange(options.copy(kmpPreview = it))
                }
                OptionToggle("No Preview", options.noPreview) {
                    onOptionsChange(options.copy(noPreview = it))
                }
                OptionToggle("Make Internal", options.makeInternal) {
                    onOptionsChange(options.copy(makeInternal = it))
                }
            }
            // Text inputs grid (responsive)
            Div(attrs = OptionsInputGridStyle.toModifier().toAttrs()) {
                OptionInput("Package Name", options.pkg, "com.example.icons") {
                    onOptionsChange(options.copy(pkg = it))
                }
                OptionInput("Theme", options.theme, "com.example.theme.AppTheme") {
                    onOptionsChange(options.copy(theme = it))
                }
                OptionInput("Receiver Type", options.receiverType, "Icons.Filled (optional)") {
                    onOptionsChange(options.copy(receiverType = it))
                }
            }
        }
    }
}

/**
 * Options body content without outer container. Intended to be wrapped
 * by a CollapsibleSection or similar container.
 */
@Composable
fun OptionsContent(
    options: PlaygroundOptions,
    onOptionsChange: (PlaygroundOptions) -> Unit,
) {
    Div(
        attrs = Modifier
            .padding(1.cssRem)
            .display(DisplayStyle.Flex)
            .flexDirection(FlexDirection.Column)
            .gap(1.cssRem)
            .toAttrs(),
    ) {
        // Toggle switches row
        Div(
            attrs = Modifier
                .display(DisplayStyle.Flex)
                .flexWrap(FlexWrap.Wrap)
                .gap(1.5.cssRem)
                .toAttrs(),
        ) {
            OptionToggle("Optimize", options.optimize) {
                onOptionsChange(options.copy(optimize = it))
            }
            OptionToggle("Minify", options.minified) {
                onOptionsChange(options.copy(minified = it))
            }
            OptionToggle("KMP Compatible", options.kmpPreview) {
                onOptionsChange(options.copy(kmpPreview = it))
            }
            OptionToggle("No Preview", options.noPreview) {
                onOptionsChange(options.copy(noPreview = it))
            }
            OptionToggle("Make Internal", options.makeInternal) {
                onOptionsChange(options.copy(makeInternal = it))
            }
        }
        // Text inputs grid
        Div(attrs = OptionsInputGridStyle.toModifier().toAttrs()) {
            OptionInput("Package Name", options.pkg, "com.example.icons") {
                onOptionsChange(options.copy(pkg = it))
            }
            OptionInput("Theme", options.theme, "com.example.theme.AppTheme") {
                onOptionsChange(options.copy(theme = it))
            }
            OptionInput("Receiver Type", options.receiverType, "Icons.Filled (optional)") {
                onOptionsChange(options.copy(receiverType = it))
            }
        }
    }
}

/** Labeled toggle switch for a single boolean option. */
@Composable
internal fun OptionToggle(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    val palette = ColorMode.current.toSitePalette()
    Div(
        attrs = Modifier
            .display(DisplayStyle.Flex)
            .alignItems(AlignItems.Center)
            .gap(0.5.cssRem)
            .minWidth(7.cssRem)
            .toAttrs(),
    ) {
        ToggleSwitch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            label = label,
        )
        SpanText(
            label,
            modifier = Modifier.fontSize(0.8.cssRem).color(palette.onSurfaceVariant)
                .whiteSpace(WhiteSpace.NoWrap),
        )
    }
}

/** Labeled text input for a single string option. */
@Composable
internal fun OptionInput(
    label: String,
    value: String,
    placeholderText: String,
    onValueChange: (String) -> Unit,
) {
    val palette = ColorMode.current.toSitePalette()
    Div {
        SpanText(
            label,
            modifier = Modifier
                .display(DisplayStyle.Block)
                .fontSize(0.75.cssRem)
                .color(palette.onSurfaceVariant)
                .margin(bottom = 0.25.cssRem),
        )
        TextInput(
            value = value,
            attrs = OptionInputStyle.toModifier().toAttrs {
                attr("aria-label", label)
                placeholder(placeholderText)
                onInput { onValueChange(it.value) }
            },
        )
    }
}
