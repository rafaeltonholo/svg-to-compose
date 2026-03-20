package dev.tonholo.s2c.website.components.organisms.footer

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.borderTop
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flexDirection
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.components.molecules.footer.LogoAndDescription
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Footer

val FooterStyle = CssStyle {
    val palette = colorMode.toSitePalette()
    base {
        Modifier
            .fillMaxWidth()
            .display(DisplayStyle.Flex)
            .flexDirection(FlexDirection.Column)
            .padding(topBottom = 3.cssRem, leftRight = 1.cssRem)
            .gap(3.cssRem)
            .borderTop(
                width = 1.px,
                style = LineStyle.Solid,
                color = palette.outline,
            )
    }
    Breakpoint.MD {
        Modifier
            .flexDirection(FlexDirection.Row)
            .justifyContent(JustifyContent.SpaceAround)
            .padding(topBottom = 5.cssRem, leftRight = 1.5.cssRem)
    }
}

@Composable
fun Footer(modifier: Modifier = Modifier) {
    Footer(
        attrs = FooterStyle.toModifier().then(modifier).toAttrs(),
    ) {
        LogoAndDescription()
        LinksAndAttribution()
    }
}

