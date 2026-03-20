package dev.tonholo.s2c.website.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div

val TabBarStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .gap(0.25.cssRem)
        .padding(0.25.cssRem)
        .borderRadius(0.75.cssRem)
        .backgroundColor(colorMode.toSitePalette().surfaceAlt)
}

val ActiveTabStyle = CssStyle.base {
    Modifier
        .padding(topBottom = 0.5.cssRem, leftRight = 1.cssRem)
        .borderRadius(0.5.cssRem)
        .cursor(Cursor.Pointer)
        .color(Colors.White)
        .fontWeight(FontWeight.Medium)
        .fontSize(0.875.cssRem)
        .styleModifier {
            property("background-image", "linear-gradient(135deg, #7F52FF, #5A30DD)")
            property("box-shadow", "0 2px 8px rgba(127, 82, 255, 0.3)")
            property("transition", "all 0.2s ease")
        }
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
        .styleModifier {
            property("transition", "all 0.2s ease")
        }
}

@Composable
fun TabPanel(
    tabs: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    content: @Composable () -> Unit,
) {
    Div {
        Row(modifier = TabBarStyle.toModifier()) {
            tabs.forEachIndexed { index, tab ->
                val style = if (index == selectedIndex) ActiveTabStyle else InactiveTabStyle
                Div(
                    attrs = style.toModifier()
                        .onClick { onSelect(index) }
                        .toAttrs()
                ) {
                    SpanText(tab)
                }
            }
        }
        Div(
            attrs = Modifier
                .margin(top = 0.75.cssRem)
                .toAttrs()
        ) {
            content()
        }
    }
}
