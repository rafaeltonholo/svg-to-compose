package dev.tonholo.s2c.website.components.organisms.footer

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.setVariable
import com.varabyte.kobweb.silk.components.layout.DividerVars
import com.varabyte.kobweb.silk.components.layout.HorizontalDivider
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.components.molecules.footer.Attribution
import dev.tonholo.s2c.website.components.molecules.footer.DocsLinks
import dev.tonholo.s2c.website.components.molecules.footer.Links
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.theme.toSitePalette
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px

val LinksAndAttributionStyle = CssStyle {
    base {
        Modifier.gap(1.2.cssRem)
    }

    Breakpoint.MD {
        Modifier.gap(SiteTheme.dimensions.size.Lg)
    }
}

@Composable
fun LinksAndAttribution(modifier: Modifier = Modifier) {
    Column(
        modifier = LinksAndAttributionStyle.toModifier().then(modifier),
    ) {
        Links()
        DocsLinks()
        HorizontalDivider(
            modifier = Modifier
                .margin(0.px)
                .setVariable(
                    DividerVars.Color,
                    ColorMode.current.toSitePalette().outlineVariant,
                ),
        )
        Attribution()
    }
}
