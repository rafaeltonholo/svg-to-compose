package dev.tonholo.s2c.website.components.organisms

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import dev.tonholo.s2c.website.components.atoms.icon.GradleSvg
import dev.tonholo.s2c.website.components.layouts.SectionContainer
import dev.tonholo.s2c.website.components.molecules.TabPanel
import dev.tonholo.s2c.website.components.organisms.howtouse.CliUsageContent
import dev.tonholo.s2c.website.components.organisms.howtouse.GradleUsageContent
import dev.tonholo.s2c.website.theme.LabelTextStyle
import dev.tonholo.s2c.website.theme.SiteTheme
import org.jetbrains.compose.web.dom.Div

val UsageTabContentStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .margin(top = SiteTheme.dimensions.size.Xl)
}

@Composable
fun HowToUseSection(modifier: Modifier = Modifier) {
    SectionContainer(id = "usage", modifier = modifier) {
        SpanText(
            "Usage",
            modifier = LabelTextStyle.toModifier()
                .color(SiteTheme.palette.onSurfaceVariant)
                .margin(bottom = SiteTheme.dimensions.size.Lg),
        )

        var selectedTab by remember { mutableIntStateOf(0) }
        TabPanel(
            tabs = listOf("CLI", "Gradle Plugin"),
            selectedIndex = selectedTab,
            onSelect = { selectedTab = it },
            tabContent = { index, label ->
                {
                    when (index) {
                        0 -> FaTerminal(size = IconSize.SM)

                        1 -> GradleSvg(
                            color = if (selectedTab == index) {
                                SiteTheme.palette.onSurface
                            } else {
                                SiteTheme.palette.onSurfaceVariant
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
                when (selectedTab) {
                    0 -> CliUsageContent()
                    1 -> GradleUsageContent()
                }
            }
        }
    }
}
