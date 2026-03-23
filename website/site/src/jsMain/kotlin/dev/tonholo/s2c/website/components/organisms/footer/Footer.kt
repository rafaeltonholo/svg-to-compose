package dev.tonholo.s2c.website.components.organisms.footer

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.autoLength
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.borderTop
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flexDirection
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.marginInline
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.components.molecules.footer.LogoAndDescription
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.theme.toSitePalette
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Footer

val FooterStyle = CssStyle {
    val palette = colorMode.toSitePalette()
    base {
        Modifier
            .fillMaxWidth()
            .display(DisplayStyle.Flex)
            .flexDirection(FlexDirection.Column)
            .alignItems(AlignItems.Center)
            .padding(topBottom = SiteTheme.dimensions.padding.section, leftRight = SiteTheme.dimensions.size.Lg)
            .gap(SiteTheme.dimensions.padding.section)
            .borderTop(
                width = 1.px,
                style = LineStyle.Solid,
                color = palette.outline,
            )
    }
    Breakpoint.SM {
        Modifier.padding(topBottom = SiteTheme.dimensions.padding.section, leftRight = SiteTheme.dimensions.size.Xl)
    }
    Breakpoint.MD {
        Modifier.padding(topBottom = SiteTheme.dimensions.padding.footerVertical, leftRight = SiteTheme.dimensions.size.Xxl)
    }
}

val FooterContentStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .maxWidth(72.cssRem)
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .gap(SiteTheme.dimensions.padding.section)
}

val FooterContentMdStyle = CssStyle {
    Breakpoint.MD {
        Modifier
            .flexDirection(FlexDirection.Row)
            .justifyContent(JustifyContent.SpaceBetween)
    }
}

@Composable
fun Footer(modifier: Modifier = Modifier) {
    Footer(
        attrs = FooterStyle.toModifier().then(modifier).toAttrs(),
    ) {
        Div(
            attrs = FooterContentStyle.toModifier()
                .then(FooterContentMdStyle.toModifier())
                .toAttrs(),
        ) {
            LogoAndDescription()
            LinksAndAttribution()
        }
    }
}
