package dev.tonholo.s2c.website.components.molecules

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaGear
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.components.atoms.ToggleSwitch
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

val OptionsContainerStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .margin(top = 1.cssRem)
        .borderRadius(0.75.cssRem)
        .border(1.px, LineStyle.Solid, colorMode.toSitePalette().borderStrong)
        .overflow(Overflow.Hidden)
}

val OptionsHeaderStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .display(DisplayStyle.Flex)
        .alignItems(AlignItems.Center)
        .styleModifier { property("justify-content", "space-between") }
        .padding(topBottom = 0.75.cssRem, leftRight = 1.cssRem)
        .backgroundColor(colorMode.toSitePalette().surfaceHeader)
        .cursor(Cursor.Pointer)
        .border(0.px, LineStyle.None, Colors.Transparent)
        .fontWeight(FontWeight.Medium)
        .fontSize(0.875.cssRem)
}

val OptionsContentStyle = CssStyle {
    base {
        Modifier
            .padding(1.cssRem)
            .display(DisplayStyle.Grid)
            .gap(1.cssRem)
            .styleModifier { property("grid-template-columns", "1fr") }
    }
    Breakpoint.MD {
        Modifier.styleModifier { property("grid-template-columns", "1fr 1fr 1fr") }
    }
}

val OptionLabelStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .alignItems(AlignItems.Center)
        .gap(0.5.cssRem)
        .fontSize(0.8.cssRem)
        .color(colorMode.toSitePalette().muted)
        .cursor(Cursor.Pointer)
}

val OptionInputStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .padding(topBottom = 0.375.cssRem, leftRight = 0.75.cssRem)
        .borderRadius(0.375.cssRem)
        .border(1.px, LineStyle.Solid, colorMode.toSitePalette().border)
        .backgroundColor(colorMode.toSitePalette().surfaceAlt)
        .color(colorMode.toSitePalette().muted)
        .fontSize(0.8.cssRem)
        .fontFamily("JetBrains Mono", "monospace")
        .styleModifier { property("outline", "none") }
}

@Composable
fun OptionsSection() {
    var optionsExpanded by remember { mutableStateOf(false) }
    val palette = ColorMode.current.toSitePalette()
    val toggleOptions = listOf("Optimize", "Minify", "KMP Compatible", "No Preview", "Make Internal")
    val toggleStates = remember {
        toggleOptions.map { mutableStateOf(false) }
    }

    Div(attrs = OptionsContainerStyle.toModifier().toAttrs()) {
        Div(
            attrs = OptionsHeaderStyle.toModifier()
                .color(palette.muted)
                .display(DisplayStyle.Flex)
                .alignItems(AlignItems.Center)
                .gap(0.5.cssRem)
                .onClick { optionsExpanded = !optionsExpanded }
                .toAttrs(),
        ) {
            FaGear(size = IconSize.SM)
            SpanText("Options")
            Div(attrs = Modifier.styleModifier { property("margin-left", "auto") }.toAttrs()) {
                SpanText(if (optionsExpanded) "\u25B2" else "\u25BC", modifier = Modifier.fontSize(0.6.cssRem))
            }
        }

        if (optionsExpanded) {
            Div(
                attrs = Modifier
                    .padding(1.cssRem)
                    .display(DisplayStyle.Flex)
                    .styleModifier { property("flex-direction", "column") }
                    .gap(1.cssRem)
                    .toAttrs(),
            ) {
                // Toggle switches row
                Div(
                    attrs = Modifier
                        .display(DisplayStyle.Flex)
                        .styleModifier { property("flex-wrap", "wrap") }
                        .gap(1.5.cssRem)
                        .toAttrs(),
                ) {
                    toggleOptions.forEachIndexed { index, option ->
                        Div(
                            attrs = Modifier
                                .display(DisplayStyle.Flex)
                                .alignItems(AlignItems.Center)
                                .gap(0.5.cssRem)
                                .toAttrs(),
                        ) {
                            ToggleSwitch(
                                checked = toggleStates[index].value,
                                onCheckedChange = { toggleStates[index].value = it },
                            )
                            SpanText(
                                option,
                                modifier = Modifier.fontSize(0.8.cssRem).color(palette.muted),
                            )
                        }
                    }
                }
                // Text inputs row
                Div(
                    attrs = Modifier
                        .display(DisplayStyle.Grid)
                        .styleModifier { property("grid-template-columns", "1fr 1fr 1fr") }
                        .gap(1.cssRem)
                        .toAttrs(),
                ) {
                    listOf(
                        "Package Name" to "com.example.icons",
                        "Theme" to "com.example.theme.AppTheme",
                        "Receiver Type" to "Icons.Filled (optional)",
                    ).forEach { (label, placeholderText) ->
                        Div {
                            SpanText(
                                label,
                                modifier = Modifier
                                    .display(DisplayStyle.Block)
                                    .fontSize(0.75.cssRem)
                                    .color(palette.muted)
                                    .margin(bottom = 0.25.cssRem),
                            )
                            org.jetbrains.compose.web.dom.TextInput(
                                value = "",
                                attrs = OptionInputStyle.toModifier().toAttrs {
                                    placeholder(placeholderText)
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}
