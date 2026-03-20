package dev.tonholo.s2c.website.components.organisms

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaBookOpen
import com.varabyte.kobweb.silk.components.icons.fa.FaTerminal
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.HeadlineTextStyle
import dev.tonholo.s2c.website.SiteTheme
import dev.tonholo.s2c.website.SubheadlineTextStyle
import dev.tonholo.s2c.website.components.atoms.AnimateOnScroll
import dev.tonholo.s2c.website.components.atoms.Badge
import dev.tonholo.s2c.website.components.atoms.icon.GradleSvg
import dev.tonholo.s2c.website.components.molecules.SectionContainer
import dev.tonholo.s2c.website.components.molecules.TabPanel
import dev.tonholo.s2c.website.components.organisms.howtouse.CliUsageContent
import dev.tonholo.s2c.website.components.organisms.howtouse.GradleUsageContent
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Div

val UsageTabContentStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .margin(top = 1.5.cssRem)
}

@Composable
fun HowToUseSection() {
    SectionContainer(id = "usage") {
        AnimateOnScroll {
            Div(
                attrs = Modifier
                    .fillMaxWidth()
                    .display(DisplayStyle.Flex)
                    .styleModifier { property("flex-direction", "column") }
                    .alignItems(AlignItems.Center)
                    .toAttrs(),
            ) {
                Badge(
                    text = "Examples",
                    color = SiteTheme.palette.brand.green,
                    leadingIcon = { FaBookOpen(size = IconSize.SM) },
                )
                SpanText(
                    "How to Use",
                    modifier = HeadlineTextStyle.toModifier()
                        .margin(top = 1.cssRem),
                )
                SpanText(
                    "From simple single-file conversions to fully automated Gradle builds.",
                    modifier = SubheadlineTextStyle.toModifier()
                        .margin(top = 0.5.cssRem, bottom = 2.cssRem),
                )
            }
        }

        var selectedTab by remember { mutableStateOf(0) }
        TabPanel(
            tabs = listOf("CLI", "Gradle Plugin"),
            selectedIndex = selectedTab,
            onSelect = { selectedTab = it },
            tabContent = { index, label ->
                {
                    when (index) {
                        0 -> FaTerminal(size = IconSize.SM)
                        1 -> GradleSvg(
                            color = if (selectedTab == index) Colors.White else SiteTheme.palette.muted,
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
