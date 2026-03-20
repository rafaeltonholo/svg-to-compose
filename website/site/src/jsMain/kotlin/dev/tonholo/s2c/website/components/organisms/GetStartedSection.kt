package dev.tonholo.s2c.website.components.organisms

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flexDirection
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaRocket
import com.varabyte.kobweb.silk.components.icons.fa.FaTerminal
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.HeadlineTextStyle
import dev.tonholo.s2c.website.SiteTheme
import dev.tonholo.s2c.website.SubheadlineTextStyle
import dev.tonholo.s2c.website.components.atoms.AnimateOnScroll
import dev.tonholo.s2c.website.components.atoms.Badge
import dev.tonholo.s2c.website.components.atoms.icon.GradleSvg
import dev.tonholo.s2c.website.components.molecules.SectionContainer
import dev.tonholo.s2c.website.components.molecules.TabPanel
import dev.tonholo.s2c.website.components.organisms.getstarted.CliTabContent
import dev.tonholo.s2c.website.components.organisms.getstarted.GradlePluginTabContent
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Div

val TabContentStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .margin(top = 1.5.cssRem)
}

@Composable
fun GetStartedSection() {
    val palette = ColorMode.current.toSitePalette()

    SectionContainer(id = "install", altBackground = true) {
        AnimateOnScroll {
            Div(
                attrs = Modifier
                    .fillMaxWidth()
                    .display(DisplayStyle.Flex)
                    .flexDirection(FlexDirection.Column)
                    .alignItems(AlignItems.Center)
                    .toAttrs(),
            ) {
                Badge(
                    text = "Quick Start",
                    color = palette.brand.teal,
                    leadingIcon = { FaRocket(size = IconSize.SM) },
                )
                SpanText(
                    "Get Started in Minutes",
                    modifier = HeadlineTextStyle.toModifier()
                        .margin(top = 1.cssRem),
                )
                SpanText(
                    "Choose your preferred installation method and start converting SVG to Compose today.",
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
                        1 -> GradleSvg(color = SiteTheme.palette.muted, width = 16, height = 16)
                    }
                    SpanText(label)
                }
            },
        ) {
            Div(attrs = TabContentStyle.toModifier().toAttrs()) {
                when (selectedTab) {
                    0 -> CliTabContent()
                    1 -> GradlePluginTabContent()
                }
            }
        }
    }
}
