package dev.tonholo.s2c.website.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Span

@Composable
fun Badge(text: String, color: Color, modifier: Modifier = Modifier) {
    val rgb = color.toRgb()
    val bgColor = rgb.copyf(alpha = 0.08f)
    val borderColor = rgb.copyf(alpha = 0.2f)
    Span(
        attrs = modifier
            .borderRadius(9999.px)
            .padding(topBottom = 0.375.cssRem, leftRight = 0.875.cssRem)
            .fontSize(0.75.cssRem)
            .fontWeight(FontWeight.Medium)
            .color(color)
            .backgroundColor(bgColor)
            .border(1.px, LineStyle.Solid, borderColor)
            .toAttrs()
    ) {
        SpanText(text)
    }
}
