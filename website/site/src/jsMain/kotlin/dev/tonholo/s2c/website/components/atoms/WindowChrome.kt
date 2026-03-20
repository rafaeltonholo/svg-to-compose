package dev.tonholo.s2c.website.components.atoms

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div

val WindowChromeStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .backgroundColor(colorMode.toSitePalette().surfaceHeader)
        .padding(topBottom = 0.75.cssRem, leftRight = 1.cssRem)
}

@Composable
fun WindowChrome(title: String? = null, modifier: Modifier = Modifier) {
    val palette = ColorMode.current.toSitePalette()
    Row(
        modifier = WindowChromeStyle.toModifier().then(modifier),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(modifier = Modifier.gap(0.5.cssRem)) {
            ChromeDot(palette.windowChrome.red)
            ChromeDot(palette.windowChrome.yellow)
            ChromeDot(palette.windowChrome.green)
        }
        if (title != null) {
            Spacer()
            SpanText(
                title,
                modifier = Modifier
                    .fontFamily("JetBrains Mono", "monospace")
                    .fontSize(0.75.cssRem)
                    .color(palette.muted)
            )
            Spacer()
            // Invisible spacer to balance the dots on the left
            Div(
                attrs = Modifier
                    .width(3.5.cssRem) // approximate width of the 3 dots + gaps
                    .toAttrs()
            )
        }
    }
}

@Composable
private fun ChromeDot(color: Color) {
    Div(
        attrs = Modifier
            .size(12.px)
            .borderRadius(100.percent)
            .backgroundColor(color)
            .toAttrs()
    )
}
