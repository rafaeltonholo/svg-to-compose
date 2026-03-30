package dev.tonholo.s2c.website.components.organisms

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaTerminal
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.components.atoms.icon.GradleSvg
import dev.tonholo.s2c.website.components.layouts.SectionContainer
import dev.tonholo.s2c.website.components.molecules.TabPanel
import dev.tonholo.s2c.website.components.organisms.howtouse.CliUsageContent
import dev.tonholo.s2c.website.components.organisms.howtouse.GradleUsageContent
import dev.tonholo.s2c.website.theme.LabelTextStyle
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.theme.toSitePalette
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Text

val UsageTabContentStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .margin(top = SiteTheme.dimensions.size.Xl)
}

@Composable
fun HowToUseSection(selectedToolTab: Int, onToolTabSelect: (Int) -> Unit, modifier: Modifier = Modifier) {
    val palette = ColorMode.current.toSitePalette()
    SectionContainer(id = "usage", modifier = modifier) {
        H2(
            attrs = LabelTextStyle.toModifier()
                .color(palette.onSurfaceVariant)
                .margin(bottom = SiteTheme.dimensions.size.Lg)
                .toAttrs(),
        ) {
            Text("Usage")
        }

        TabPanel(
            tabs = listOf("CLI", "Gradle Plugin"),
            selectedIndex = selectedToolTab,
            onSelect = onToolTabSelect,
            tabContent = { index, label ->
                {
                    when (index) {
                        0 -> FaTerminal(size = IconSize.SM)

                        1 -> GradleSvg(
                            color = if (selectedToolTab == index) {
                                palette.onSurface
                            } else {
                                palette.onSurfaceVariant
                            },
                            width = 16,
                            height = 16,
                        )
                    }
                    SpanText(label)
                }
            },
        ) {
            Div(attrs = UsageTabContentStyle.toModifier().toAttrs()) {
                when (selectedToolTab) {
                    0 -> CliUsageContent()
                    1 -> GradleUsageContent()
                }
            }
        }
    }
}
