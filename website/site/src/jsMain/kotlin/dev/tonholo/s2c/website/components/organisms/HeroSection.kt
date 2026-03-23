package dev.tonholo.s2c.website.components.organisms

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flexDirection
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
import dev.tonholo.s2c.website.DisplayTextStyle
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.SubheadlineTextStyle
import dev.tonholo.s2c.website.components.atoms.Badge
import dev.tonholo.s2c.website.components.molecules.CtaButtons
import dev.tonholo.s2c.website.theme.typography.FontFamilies
import dev.tonholo.s2c.website.theme.toSitePalette
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Section

val CompactIntroStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .display(DisplayStyle.Flex)
            .flexDirection(FlexDirection.Column)
            .alignItems(AlignItems.FlexStart)
            .padding(top = 6.cssRem, bottom = 2.cssRem, leftRight = 1.cssRem)
    }
    Breakpoint.SM {
        Modifier.padding(top = 6.cssRem, bottom = 2.cssRem, leftRight = 1.5.cssRem)
    }
    Breakpoint.MD {
        Modifier.padding(top = 7.cssRem, bottom = 2.cssRem, leftRight = 2.cssRem)
    }
}

val IntroContentStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .maxWidth(72.cssRem)
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .gap(0.75.cssRem)
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
                    .alignItems(AlignItems.Center)
                    .gap(0.75.cssRem)
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
