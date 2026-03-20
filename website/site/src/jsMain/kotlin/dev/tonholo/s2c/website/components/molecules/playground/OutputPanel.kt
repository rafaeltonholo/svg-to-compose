package dev.tonholo.s2c.website.components.molecules.playground

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaCode
import com.varabyte.kobweb.silk.components.icons.fa.FaCopy
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.components.molecules.PanelHeaderStyle
import dev.tonholo.s2c.website.shiki.ShikiCodeBlock
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

val OutputPanelStyle = CssStyle.base {
    Modifier
        .backgroundColor(colorMode.toSitePalette().surface)
        .display(DisplayStyle.Flex)
        .styleModifier { property("flex-direction", "column") }
}

val OutputCodeStyle = CssStyle.base {
    Modifier
        .fillMaxSize()
        .fontFamily("JetBrains Mono", "monospace")
        .fontSize(0.7.cssRem)
        .lineHeight(value = 1.6)
        .overflow(Overflow.Auto)
        .margin(0.px)
        .styleModifier { property("white-space", "pre") }
        .styleModifier { property("flex", "1") }
}

@Composable
fun OutputPanel(kotlinCode: String) {
    Div(attrs = OutputPanelStyle.toModifier().toAttrs()) {
        Div(
            attrs = PanelHeaderStyle.toModifier()
                .display(DisplayStyle.Flex)
                .alignItems(AlignItems.Center)
                .styleModifier { property("justify-content", "space-between") }
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
        ShikiCodeBlock(
            language = "kotlin",
            code = kotlinCode,
            modifier = OutputCodeStyle.toModifier(),
        )
    }
}
