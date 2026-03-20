package dev.tonholo.s2c.website.components.molecules

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaAndroid
import com.varabyte.kobweb.silk.components.icons.fa.FaApple
import com.varabyte.kobweb.silk.components.icons.fa.FaBullseye
import com.varabyte.kobweb.silk.components.icons.fa.FaLinux
import com.varabyte.kobweb.silk.components.icons.fa.FaWindows
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.components.atoms.Badge
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Div

val PlatformBadgeRowStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .styleModifier {
            property("flex-wrap", "wrap")
            property("justify-content", "center")
        }
        .gap(0.5.cssRem)
}

@Composable
fun PlatformBadges() {
    Div(attrs = PlatformBadgeRowStyle.toModifier().toAttrs()) {
        val color = ColorMode.current.toSitePalette().muted
        Badge(color = color) {
            FaApple(size = IconSize.SM)
            SpanText("macOS")
        }
        Badge(color = color) {
            FaLinux(size = IconSize.SM)
            SpanText("Linux")
        }
        Badge(color = color) {
            FaWindows(size = IconSize.SM)
            SpanText("Windows")
        }
        Badge(color = color) {
            FaAndroid(size = IconSize.SM)
            SpanText("Android")
        }
        Badge(color = color) {
            FaBullseye(size = IconSize.SM)
            SpanText("KMP")
        }
    }
}
