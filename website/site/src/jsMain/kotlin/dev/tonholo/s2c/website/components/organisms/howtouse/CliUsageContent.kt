package dev.tonholo.s2c.website.components.organisms.howtouse

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.gridTemplateColumns
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaArrowRight
import com.varabyte.kobweb.silk.components.icons.fa.FaFile
import com.varabyte.kobweb.silk.components.icons.fa.FaFolderOpen
import com.varabyte.kobweb.silk.components.icons.fa.FaPalette
import com.varabyte.kobweb.silk.components.icons.fa.FaTerminal
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.navigation.UndecoratedLinkVariant
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.components.molecules.CodeBlock
import dev.tonholo.s2c.website.components.molecules.CollapsibleSection
import dev.tonholo.s2c.website.components.molecules.OptionRow
import dev.tonholo.s2c.website.components.molecules.OptionsHeaderRow
import dev.tonholo.s2c.website.components.molecules.UsageCard
import dev.tonholo.s2c.website.components.organisms.docs.CliOption
import dev.tonholo.s2c.website.theme.toSitePalette
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.fr
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

// language=sh
private const val S2C_CONVERT_SINGLE_FILE = """
|s2c \
|  -o Icon.kt \
|  -p com.app.icons \
|  -t com.app.theme.AppTheme \
|  icon.svg
"""

// language=sh
private const val S2C_CONVERT_DIRECTORY = """
|s2c \
|  -o ./output \
|  -p com.app.icons \
|  -t com.app.theme.AppTheme \
|  -r \
|  ./icons/
"""

// language=sh
private const val S2C_CONVERT_MATERIAL_ICON_RECEIVER = """
|s2c \
|  -o Icon.kt \
|  -p com.app.icons \
|  -t com.app.theme.AppTheme \
|  -rt "Icons.Filled" \
|  icon.svg
"""

val CliUsageCardGridStyle = CssStyle {
    base {
        Modifier
            .display(DisplayStyle.Grid)
            .gap(1.cssRem)
            .fillMaxWidth()
            .gridTemplateColumns {
                size(1.fr)
            }
    }
    Breakpoint.MD {
        Modifier
            .gridTemplateColumns {
                repeat(count = 2) {
                    minmax(0.px, 1.fr)
                }
            }
    }
    Breakpoint.LG {
        Modifier
            .gridTemplateColumns {
                repeat(count = 3) {
                    minmax(0.px, 1.fr)
                }
            }
    }
}

val OptionsTableStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .background(colorMode.toSitePalette().surfaceVariant)
}

@Composable
fun CliUsageContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Div(attrs = CliUsageCardGridStyle.toModifier().toAttrs()) {
            UsageCard(
                title = "Convert a Single File",
                description = "Convert a single SVG file to a Kotlin ImageVector.",
                icon = { FaFile(size = IconSize.SM) },
            ) {
                CodeBlock(
                    code = S2C_CONVERT_SINGLE_FILE.trimMargin(),
                    language = "shell",
                )
            }
            UsageCard(
                title = "Batch Convert a Directory",
                description = "Recursively convert all SVG files in a directory.",
                icon = { FaFolderOpen(size = IconSize.SM) },
            ) {
                CodeBlock(
                    code = S2C_CONVERT_DIRECTORY.trimMargin(),
                    language = "shell",
                )
            }
            UsageCard(
                title = "Material Icons Receiver",
                description = "Generate icons with a Material Icons receiver type.",
                icon = { FaPalette(size = IconSize.SM) },
            ) {
                CodeBlock(
                    code = S2C_CONVERT_MATERIAL_ICON_RECEIVER.trimMargin(),
                    language = "shell",
                )
            }
        }

        Div(
            attrs = Modifier
                .margin(top = 1.cssRem)
                .fillMaxWidth()
                .display(DisplayStyle.Flex)
                .toAttrs(),
        ) {
            Link(
                path = "/docs/cli",
                modifier = Modifier
                    .color(SiteTheme.palette.primary)
                    .gap(0.25.cssRem)
                    .display(DisplayStyle.Flex),
                variant = UndecoratedLinkVariant,
            ) {
                SpanText("See full CLI documentation")
                FaArrowRight(size = IconSize.XS)
            }
        }

        Div(attrs = Modifier.margin(top = 1.5.cssRem).fillMaxWidth().toAttrs()) {
            CollapsibleSection(
                title = "All CLI Options Reference",
                leadingIcon = { FaTerminal(size = IconSize.SM, modifier = Modifier.color(SiteTheme.palette.primary)) },
            ) {
                Column(modifier = OptionsTableStyle.toModifier()) {
                    OptionsHeaderRow()
                    CliOption.options.forEachIndexed { index, option ->
                        OptionRow(
                            flag = option.flag,
                            type = option.type,
                            description = option.description,
                            index = index,
                        )
                    }
                }
            }
        }
    }
}
