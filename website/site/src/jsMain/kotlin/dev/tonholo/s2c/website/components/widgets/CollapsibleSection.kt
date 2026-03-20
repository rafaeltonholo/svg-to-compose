package dev.tonholo.s2c.website.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaChevronDown
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div

val CollapsibleHeaderStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .padding(1.cssRem)
        .cursor(Cursor.Pointer)
        .backgroundColor(colorMode.toSitePalette().surfaceAlt)
        .borderRadius(0.5.cssRem)
        .display(DisplayStyle.Flex)
        .alignItems(AlignItems.Center)
}

@Composable
fun CollapsibleSection(
    title: String,
    initiallyExpanded: Boolean = false,
    content: @Composable () -> Unit,
) {
    var expanded by remember { mutableStateOf(initiallyExpanded) }

    Div {
        Row(
            modifier = CollapsibleHeaderStyle.toModifier()
                .onClick { expanded = !expanded },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SpanText(
                title,
                modifier = Modifier
                    .fontWeight(FontWeight.SemiBold)
                    .fontSize(1.cssRem)
            )
            Spacer()
            FaChevronDown(
                size = IconSize.SM,
                modifier = Modifier
                    .color(ColorMode.current.toSitePalette().muted)
                    .styleModifier {
                        property("transition", "transform 0.3s ease")
                        if (expanded) {
                            property("transform", "rotate(180deg)")
                        }
                    }
            )
        }
        Div(
            attrs = Modifier
                .fillMaxWidth()
                .overflow(Overflow.Hidden)
                .styleModifier {
                    if (expanded) {
                        property("max-height", "200rem")
                        property("opacity", "1")
                    } else {
                        property("max-height", "0")
                        property("opacity", "0")
                    }
                    property("transition", "max-height 0.3s ease, opacity 0.3s ease")
                }
                .toAttrs()
        ) {
            content()
        }
    }
}
