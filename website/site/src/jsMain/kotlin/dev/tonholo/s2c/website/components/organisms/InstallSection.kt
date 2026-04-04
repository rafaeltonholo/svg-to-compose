package dev.tonholo.s2c.website.components.organisms

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.components.atoms.icon.GradleSvg
import dev.tonholo.s2c.website.components.layouts.SectionContainer
import dev.tonholo.s2c.website.components.molecules.CodeBlock
import dev.tonholo.s2c.website.components.molecules.Platform
import dev.tonholo.s2c.website.components.molecules.PlatformChipSelector
import dev.tonholo.s2c.website.components.molecules.TabPanel
import dev.tonholo.s2c.website.components.molecules.detectPlatform
import dev.tonholo.s2c.website.config.BuildConfig
import dev.tonholo.s2c.website.theme.LabelTextStyle
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.theme.toSitePalette
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Text

val InstallContentStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .margin(top = SiteTheme.dimensions.size.Xl)
}

@Composable
fun InstallSection(selectedToolTab: Int, onToolTabSelect: (Int) -> Unit, modifier: Modifier = Modifier) {
    val palette = ColorMode.current.toSitePalette()
    SectionContainer(id = "install", modifier = modifier, altBackground = true) {
        H2(
            attrs = LabelTextStyle.toModifier()
                .color(palette.onSurfaceVariant)
                .margin(bottom = SiteTheme.dimensions.size.Lg)
                .toAttrs(),
        ) {
            Text("Install")
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
            panels = listOf(
                {
                    Div(attrs = InstallContentStyle.toModifier().toAttrs()) {
                        CliInstallContent()
                    }
                },
                {
                    Div(attrs = InstallContentStyle.toModifier().toAttrs()) {
                        GradlePluginInstallContent()
                    }
                },
            ),
        )
    }
}

@Composable
private fun CliInstallContent() {
    var selectedPlatform by remember { mutableStateOf(detectPlatform()) }

    PlatformChipSelector(
        selectedPlatform = selectedPlatform,
        onSelect = { selectedPlatform = it },
        modifier = Modifier.margin(bottom = SiteTheme.dimensions.size.Lg),
    )

    when (selectedPlatform) {
        Platform.MAC_LINUX -> CodeBlock(
            language = "shell",
            // language=sh
            code = """
                |brew tap dev-tonholo/svg-to-compose
                |brew install s2c
                |# Call s2c
                |s2c --help
            """.trimMargin(),
            filename = "Terminal",
        )

        Platform.WINDOWS -> CodeBlock(
            language = "shell",
            // language=sh
            code = """
                |scoop bucket add svg-to-compose https://github.com/dev-tonholo/scoop-svg-to-compose
                |scoop install s2c
                |# Call s2c
                |s2c --help
            """.trimMargin(),
            filename = "PowerShell",
        )

        Platform.MANUAL -> CodeBlock(
            language = "shell",
            // language=sh
            code = $$"""
                |# Download the s2c wrapper script directly from GitHub.
                |curl -o your/target/path/s2c \
                |     https://raw.githubusercontent.com/rafaeltonholo/svg-to-compose/main/s2c
                |# Add execution permission to the script.
                |chmod +x your/target/path/s2c
                |# Add s2c to your path, so you can use it anywhere.
                |export PATH="your/target/path:$PATH"
                |# Call s2c
                |s2c --help
            """.trimMargin(),
            filename = "Terminal",
        )
    }
}

@Composable
private fun GradlePluginInstallContent() {
    CodeBlock(
        language = "kotlin",
        // language=kotlin
        code = """plugins {
            |    id("dev.tonholo.s2c") version "${BuildConfig.VERSION}"
            |}
        """.trimMargin(),
    )
}
