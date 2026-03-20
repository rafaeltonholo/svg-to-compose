package dev.tonholo.s2c.website.components.molecules

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.AlignItems
import com.varabyte.kobweb.compose.css.BackgroundImage
import com.varabyte.kobweb.compose.css.BoxShadow
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.JustifyContent
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.css.functions.linearGradient
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.backgroundImage
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.deg
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

val TabBarContainerStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .justifyContent(JustifyContent.Center)
}

val TabBarStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .gap(0.25.cssRem)
        .padding(0.25.cssRem)
        .borderRadius(0.75.cssRem)
        .backgroundColor(colorMode.toSitePalette().surfaceAlt)
}

val ActiveTabStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .padding(topBottom = 0.5.cssRem, leftRight = 1.cssRem)
        .borderRadius(0.5.cssRem)
        .cursor(Cursor.Pointer)
        .color(Colors.White)
        .fontWeight(FontWeight.Medium)
        .fontSize(0.875.cssRem)
        .transition(Transition.all(duration = 200.ms, timingFunction = TransitionTimingFunction.Ease))
        .backgroundImage(
            BackgroundImage.of(
                gradient = linearGradient(
                    angle = 135.deg,
                    from = palette.brand.purple,
                    to = palette.brand.purpleDeep,
                ),
            ),
        )
        .boxShadow(
            BoxShadow.of(
                offsetX = 0.px,
                offsetY = 4.px,
                blurRadius = 15.px,
                color = Color.rgba(r = 127, g = 82, b = 255, a = 0.3f),
            ),
        )
}

val InactiveTabStyle = CssStyle.base {
    Modifier
        .padding(topBottom = 0.5.cssRem, leftRight = 1.cssRem)
        .borderRadius(0.5.cssRem)
        .cursor(Cursor.Pointer)
        .backgroundColor(Colors.Transparent)
        .color(colorMode.toSitePalette().muted)
        .fontWeight(FontWeight.Medium)
        .fontSize(0.875.cssRem)
        .transition(Transition.all(duration = 200.ms, timingFunction = TransitionTimingFunction.Ease))
}

@Composable
fun TabPanel(
    tabs: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    tabContent: ((index: Int, label: String) -> @Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Div(attrs = Modifier.fillMaxWidth().toAttrs()) {
        Div(attrs = TabBarContainerStyle.toModifier().toAttrs()) {
            Row(modifier = TabBarStyle.toModifier()) {
                tabs.forEachIndexed { index, tab ->
                    val style = if (index == selectedIndex) ActiveTabStyle else InactiveTabStyle
                    Div(
                        attrs = style.toModifier()
                            .onClick { onSelect(index) }
                            .display(DisplayStyle.LegacyInlineFlex)
                            .alignItems(AlignItems.Center)
                            .gap(0.375.cssRem)
                            .toAttrs(),
                    ) {
                        val customContent = tabContent?.invoke(index, tab)
                        if (customContent != null) {
                            customContent()
                        } else {
                            SpanText(tab)
                        }
                    }
                }
            }
        }
        Div(
            attrs = Modifier
                .fillMaxWidth()
                .margin(top = 0.75.cssRem)
                .toAttrs(),
        ) {
            content()
        }
    }
}
