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
import com.varabyte.kobweb.silk.components.icons.fa.FaFile
import com.varabyte.kobweb.silk.components.icons.fa.FaFolderOpen
import com.varabyte.kobweb.silk.components.icons.fa.FaPalette
import com.varabyte.kobweb.silk.components.icons.fa.FaTerminal
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.SiteTheme
import dev.tonholo.s2c.website.components.molecules.CodeBlock
import dev.tonholo.s2c.website.components.molecules.CollapsibleSection
import dev.tonholo.s2c.website.components.molecules.OptionRow
import dev.tonholo.s2c.website.components.molecules.OptionsHeaderRow
import dev.tonholo.s2c.website.components.molecules.UsageCard
import dev.tonholo.s2c.website.toSitePalette
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

val UsageCardGridStyle = CssStyle {
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
    Div(attrs = UsageCardGridStyle.toModifier().then(modifier).toAttrs()) {
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

private data class CliOption(val flag: String, val type: String, val description: String) {
    companion object {
        val options = setOf(
            CliOption(flag = "-o, --output", type = "String", description = "Output file or directory path"),
            CliOption(flag = "-p, --package", type = "String", description = "Kotlin package name for generated code"),
            CliOption(flag = "-t, --theme", type = "String", description = "Fully-qualified theme class name"),
            CliOption(
                flag = "-opt, --optimize",
                type = "Boolean",
                description = "Enable SVG/AVG optimization (default: true)",
            ),
            CliOption(
                flag = "-rt, --receiver-type",
                type = "String",
                description = "Receiver type (e.g. Icons.Filled)",
            ),
            CliOption(flag = "--add-to-material", type = "Boolean", description = "Add as extension to Material Icons"),
            CliOption(
                flag = "-np, --no-preview",
                type = "Boolean",
                description = "Skip generating @Preview composables",
            ),
            CliOption(
                flag = "--kmp",
                type = "Boolean",
                description = "Ensure output is KMP-compatible (default: false)",
            ),
            CliOption(flag = "--make-internal", type = "Boolean", description = "Mark generated symbols as internal"),
            CliOption(
                flag = "--minified",
                type = "Boolean",
                description = "Remove comments and inline method parameters",
            ),
            CliOption(flag = "-r, --recursive", type = "Boolean", description = "Recursively process sub-directories"),
            CliOption(
                flag = "--recursive-depth, --depth",
                type = "Int",
                description = "Depth level for recursive search (default: 10)",
            ),
            CliOption(
                flag = "--exclude",
                type = "String...",
                description = "Regex pattern to exclude icons from processing",
            ),
            CliOption(
                flag = "--map-icon-name-from-to",
                type = "Pair...",
                description = "Replace icon name pattern (old to new)",
            ),
        )
    }
}
