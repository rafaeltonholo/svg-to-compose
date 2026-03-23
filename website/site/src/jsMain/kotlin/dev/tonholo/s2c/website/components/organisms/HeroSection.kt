package dev.tonholo.s2c.website.components.organisms

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flexDirection
import com.varabyte.kobweb.compose.ui.modifiers.flexWrap
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.components.atoms.Badge
import dev.tonholo.s2c.website.components.molecules.CtaButtons
import dev.tonholo.s2c.website.theme.DisplayTextStyle
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.theme.SubheadlineTextStyle
import dev.tonholo.s2c.website.theme.toSitePalette
import dev.tonholo.s2c.website.theme.typography.FontFamilies
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Section

val CompactIntroStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .display(DisplayStyle.Flex)
            .flexDirection(FlexDirection.Column)
            .alignItems(AlignItems.Center)
            .padding(
                top = SiteTheme.dimensions.padding.heroTop,
                leftRight = SiteTheme.dimensions.size.Lg,
            )
    }
    Breakpoint.SM {
        Modifier.padding(
            top = SiteTheme.dimensions.padding.heroTop,
            leftRight = SiteTheme.dimensions.size.Xl,
        )
    }
    Breakpoint.MD {
        Modifier.padding(
            top = SiteTheme.dimensions.padding.heroTop,
            leftRight = SiteTheme.dimensions.size.Xxl,
        )
    }
}

val IntroContentStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .maxWidth(SiteTheme.dimensions.layout.contentMaxWidth)
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .gap(SiteTheme.dimensions.size.Md)
}

@Composable
fun HeroSection(modifier: Modifier = Modifier) {
    val palette = ColorMode.current.toSitePalette()
    Section(
        attrs = CompactIntroStyle.toModifier()
            .id("hero")
            .then(modifier)
            .toAttrs(),
    ) {
        Div(attrs = IntroContentStyle.toModifier().toAttrs()) {
            Div(
                attrs = DisplayTextStyle
                    .toModifier()
                    .display(DisplayStyle.Flex)
                    .flexWrap(FlexWrap.Wrap)
                    .alignItems(AlignItems.Center)
                    .gap(SiteTheme.dimensions.size.Md)
                    .toAttrs(),
            ) {
                SpanText("SVG to Compose")
                Badge(
                    text = SiteTheme.VERSION,
                    color = palette.onSurfaceVariant,
                    modifier = Modifier.fontFamily(values = FontFamilies.mono),
                )
            }
            Div(
                attrs = SubheadlineTextStyle
                    .toModifier()
                    .maxWidth(40.cssRem)
                    .toAttrs(),
            ) {
                SpanText(
                    "Convert SVG and Android Vector Drawables into type-safe Kotlin ImageVector code. " +
                        "CLI, Gradle Plugin, Kotlin Multiplatform.",
                )
            }
            CtaButtons()
        }
    }
}
