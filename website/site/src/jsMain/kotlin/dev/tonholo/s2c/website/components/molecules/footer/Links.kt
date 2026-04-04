package dev.tonholo.s2c.website.components.molecules.footer

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.lightened
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.ariaLabel
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.flexWrap
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.setVariable
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaArrowUpRightFromSquare
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.extendedByBase
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.components.atoms.Badge
import dev.tonholo.s2c.website.components.atoms.BadgeVars
import dev.tonholo.s2c.website.components.atoms.SquaredBadge
import dev.tonholo.s2c.website.components.atoms.icon.GithubSvg
import dev.tonholo.s2c.website.components.atoms.resolveBadgeColors
import dev.tonholo.s2c.website.navigation.WebRoute
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.theme.common.SiteLinkStyleVariant
import dev.tonholo.s2c.website.theme.palette
import dev.tonholo.s2c.website.theme.toSitePalette
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.dom.Nav
import org.jetbrains.compose.web.dom.Span

@Composable
fun Links(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .gap(SiteTheme.dimensions.size.Xsm)
            .flexWrap(FlexWrap.Wrap),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val palette = ColorMode.current.toSitePalette()
        Link(
            "https://github.com/rafaeltonholo/svg-to-compose",
            variant = FooterLinksVariant,
        ) {
            Badge(
                color = palette.primary,
                variant = FooterLinkBadgeVariant,
            ) {
                GithubSvg(
                    color = palette.primary,
                )
                SpanText("GitHub")
                FaArrowUpRightFromSquare(size = IconSize.SM)
            }
        }
        Link(
            "https://central.sonatype.com/artifact/dev.tonholo.s2c/svg-to-compose",
            variant = FooterLinksVariant,
        ) {
            Badge(
                color = palette.primary,
                variant = FooterLinkBadgeVariant,
            ) {
                SpanText("Maven Central")
                FaArrowUpRightFromSquare(size = IconSize.SM)
            }
        }
        Badge(
            text = "MIT License",
            color = palette.primary,
            variant = SquaredBadge,
        )
        Badge(
            text = SiteTheme.VERSION,
            color = palette.primary,
            variant = SquaredBadge,
        )
    }
}

@Composable
fun DocsLinks(modifier: Modifier = Modifier) {
    Nav(
        attrs = modifier
            .ariaLabel("Documentation links")
            .toAttrs(),
    ) {
        Span(
            attrs = Modifier
                .fontSize(0.8.cssRem)
                .toAttrs(),
        ) {
            WebRoute.Docs.all.forEachIndexed { index, entry ->
                if (index > 0) {
                    SpanText(
                        " \u00B7 ",
                        modifier = Modifier.color(ColorMode.current.toSitePalette().onSurfaceVariant),
                    )
                }
                Link(
                    path = entry.path,
                    text = entry.label,
                    variant = DocsLinkVariant,
                )
            }
        }
    }
}

val FooterLinksVariant = SiteLinkStyleVariant.extendedByBase {
    Modifier
        .display(DisplayStyle.Flex)
        .alignItems(AlignItems.Center)
}

val DocsLinkVariant = SiteLinkStyleVariant

val FooterLinkBadgeVariant = SquaredBadge.extendedByBase {
    val color = palette.primary.lightened(byPercent = 0.5f)
    val (bgColor, borderColor) = color.resolveBadgeColors()
    Modifier
        .setVariable(BadgeVars.ContainerHoverColor, bgColor)
        .setVariable(BadgeVars.ContentHoverColor, color)
        .setVariable(BadgeVars.BorderHoverColor, borderColor)
        .transition {
            property("color", "background-color", "border-color")
            duration(200.ms)
            timingFunction(TransitionTimingFunction.EaseInOut)
        }
        .cursor(Cursor.Pointer)
}
