package dev.tonholo.s2c.website.components.organisms

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.functions.clamp
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.alignSelf
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flexDirection
import com.varabyte.kobweb.compose.ui.modifiers.flexWrap
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.addVariant
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.components.atoms.Badge
import dev.tonholo.s2c.website.components.atoms.BadgeStyle
import dev.tonholo.s2c.website.components.atoms.InlineCode
import dev.tonholo.s2c.website.components.molecules.CtaButtons
import dev.tonholo.s2c.website.theme.DisplayTextStyle
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.theme.SubheadlineTextStyle
import dev.tonholo.s2c.website.theme.toSitePalette
import dev.tonholo.s2c.website.theme.typography.FontFamilies
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.AlignSelf
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.vw
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Section
import org.jetbrains.compose.web.dom.Text

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

val VersionBadgeVariant = BadgeStyle.addVariant {
    base {
        Modifier
            .fontFamily(values = FontFamilies.mono)
            .alignSelf(AlignSelf.Center)
            .margin { top(clamp(0.cssRem, 1.vw, 0.875.cssRem)) }
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
            Row(
                modifier = Modifier
                    .display(DisplayStyle.Flex)
                    .flexWrap(FlexWrap.Wrap)
                    .alignItems(AlignItems.Center)
                    .gap(SiteTheme.dimensions.size.Md),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                H1(
                    attrs = DisplayTextStyle.toModifier().toAttrs(),
                ) {
                    Text("SVG to Compose")
                }
                Badge(
                    text = SiteTheme.VERSION,
                    color = palette.onSurfaceVariant,
                    variant = VersionBadgeVariant,
                )
            }
            H2(
                attrs = SubheadlineTextStyle
                    .toModifier()
                    .fontWeight(FontWeight.Normal)
                    .maxWidth(40.cssRem)
                    .toAttrs(),
            ) {
                Text(
                    "Convert SVG and Android Vector Drawables into type-safe Kotlin ",
                )
                InlineCode("ImageVector")
                Text(" code. CLI, Gradle Plugin, Kotlin Multiplatform.")
            }
            CtaButtons()
        }
    }
}
