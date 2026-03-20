package dev.tonholo.s2c.website.components.molecules.footer

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.flexWrap
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.silk.components.icons.fa.FaArrowUpRightFromSquare
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.navigation.UncoloredLinkVariant
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.SiteTheme
import dev.tonholo.s2c.website.components.atoms.Badge
import dev.tonholo.s2c.website.components.atoms.SquaredBadge
import dev.tonholo.s2c.website.components.atoms.icon.GithubSvg
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.cssRem

@Composable
fun Links(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .gap(0.5.cssRem)
            .flexWrap(FlexWrap.Wrap),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val palette = ColorMode.current.toSitePalette()
        Link(
            "https://github.com/rafaeltonholo/svg-to-compose",
            modifier = Modifier
                .textDecorationLine(TextDecorationLine.None)
                .display(DisplayStyle.Flex)
                .alignItems(AlignItems.Center),
            variant = UncoloredLinkVariant,
        ) {
            Badge(
                color = palette.primary,
                variant = SquaredBadge,
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
            modifier = Modifier
                .textDecorationLine(TextDecorationLine.None),
            variant = UncoloredLinkVariant,
        ) {
            Badge(
                color = palette.primary,
                variant = SquaredBadge,
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
