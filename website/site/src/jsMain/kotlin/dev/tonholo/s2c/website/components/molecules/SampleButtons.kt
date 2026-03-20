package dev.tonholo.s2c.website.components.molecules

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.AlignSelf
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.JustifyContent
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.alignSelf
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.flexWrap
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.scale
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div

val SampleButtonRowStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .flexWrap(FlexWrap.Wrap)
        .justifyContent(JustifyContent.Center)
        .gap(0.5.cssRem)
        .margin(bottom = 1.5.cssRem)
}

val SampleButtonStyle = CssStyle {
    base {
        val sitePalette = colorMode.toSitePalette()
        Modifier
            .backgroundColor(sitePalette.brand.purpleDeep.darkened(byPercent = .8f))
            .border(1.px, LineStyle.Solid, sitePalette.border)
            .borderRadius(0.625.cssRem)
            .padding(topBottom = 0.25.cssRem, leftRight = 0.75.cssRem)
            .fontSize(0.75.cssRem)
            .fontFamily("JetBrains Mono", "monospace")
            .cursor(Cursor.Pointer)
            .color(sitePalette.muted)
            .transition(Transition.all(duration = 200.ms, timingFunction = TransitionTimingFunction.Ease))
    }
    cssRule(".active") {
        val sitePalette = colorMode.toSitePalette()
        Modifier
            .backgroundColor(sitePalette.brand.purple.darkened(byPercent = .6f))
    }
    cssRule(":hover") {
        Modifier.scale(105.percent)
    }
}

@Composable
fun SampleButtons(
    sampleNames: List<String>,
    selectedSample: Int,
    onSelect: (Int) -> Unit,
) {
    Div(
        attrs = SampleButtonRowStyle.toModifier()
            .alignItems(AlignItems.Center)
            .toAttrs(),
    ) {
        SpanText(
            "Samples:",
            modifier = Modifier
                .color(ColorMode.current.toSitePalette().mutedStrong)
                .fontSize(0.8.cssRem)
                .alignSelf(AlignSelf.Center),
        )
        sampleNames.forEachIndexed { index, name ->
            Button(
                attrs = SampleButtonStyle.toModifier()
                    .onClick { onSelect(index) }
                    .toAttrs {
                        if (index == selectedSample) {
                            classes("active")
                        }
                    },
            ) {
                SpanText(name)
            }
        }
    }
}
