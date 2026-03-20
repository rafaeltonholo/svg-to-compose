package dev.tonholo.s2c.website.components.molecules

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.JustifyContent
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.flexWrap
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.SiteTheme
import dev.tonholo.s2c.website.components.atoms.Badge
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Div

val HeroBadgeRowStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .flexWrap(FlexWrap.Wrap)
        .gap(0.5.cssRem)
        .justifyContent(JustifyContent.Center)
}

@Composable
fun BadgeRow() {
    Div(attrs = HeroBadgeRowStyle.toModifier().toAttrs()) {
        Badge(
            text = "v2.1.2",
            color = SiteTheme.palette.brand.violet,
            modifier = Modifier.fontFamily("JetBrains Mono", "monospace"),
        )
        Badge(text = "Kotlin Multiplatform", color = SiteTheme.palette.brand.purple)
        Badge(text = "Maven Central", color = SiteTheme.palette.brand.orange)
        Badge(text = "MIT License", color = SiteTheme.palette.brand.green)
    }
}
