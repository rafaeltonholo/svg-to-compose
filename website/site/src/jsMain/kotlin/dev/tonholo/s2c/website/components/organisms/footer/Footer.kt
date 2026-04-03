package dev.tonholo.s2c.website.components.organisms.footer

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.borderTop
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flexDirection
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssStyle
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
import org.jetbrains.compose.web.css.keywords.auto
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
            .padding {
                top(SiteTheme.dimensions.padding.section)
                left(SiteTheme.dimensions.size.Lg)
                right(SiteTheme.dimensions.size.Lg)
                bottom(SiteTheme.dimensions.size.Sm)
            }
            .gap(SiteTheme.dimensions.padding.section)
            .borderTop(
                width = 1.px,
                style = LineStyle.Solid,
                color = palette.outline,
            )
    }
    Breakpoint.MD {
        Modifier
            .padding(
                topBottom = SiteTheme.dimensions.padding.footerVertical,
                leftRight = SiteTheme.dimensions.size.Xxl,
            )
    }
}

val FooterContentStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .maxWidth(SiteTheme.dimensions.layout.contentMaxWidth)
            .display(DisplayStyle.Flex)
            .flexDirection(FlexDirection.Column)
            .alignItems(AlignItems.Center)
            .gap(SiteTheme.dimensions.padding.section)
            .fillMaxWidth()
    }

    Breakpoint.MD {
        Modifier
            .flexDirection(FlexDirection.Row)
            .alignItems(AlignItems.Stretch)
            .justifyContent(JustifyContent.SpaceBetween)
    }
}

val FooterInnerContentStyle = CssStyle {
    base { Modifier.fillMaxWidth() }
    Breakpoint.MD { Modifier.width(auto) }
}

@Composable
fun Footer(modifier: Modifier = Modifier) {
    Footer(
        attrs = FooterStyle.toModifier().then(modifier).toAttrs(),
    ) {
        Div(
            attrs = FooterContentStyle
                .toModifier()
                .toAttrs(),
        ) {
            LogoAndDescription(modifier = FooterInnerContentStyle.toModifier())
            LinksAndAttribution(modifier = FooterInnerContentStyle.toModifier())
        }
    }
}
