package dev.tonholo.s2c.website.components.molecules

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.components.atoms.Badge
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Div

val HeroBadgeRowStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .styleModifier { property("flex-wrap", "wrap") }
        .gap(0.5.cssRem)
        .styleModifier { property("justify-content", "center") }
}

@Composable
fun BadgeRow() {
    Div(attrs = HeroBadgeRowStyle.toModifier().toAttrs()) {
        Badge(
            text = "v2.1.2",
            color = Color.rgb(0x7F52FF),
            modifier = Modifier.fontFamily("JetBrains Mono", "monospace"),
        )
        Badge(text = "Kotlin Multiplatform", color = Color.rgb(0x00D4AA))
        Badge(text = "Maven Central", color = Color.rgb(0xF78C6C))
        Badge(text = "MIT License", color = Color.rgb(0xA5D6A7))
    }
}
