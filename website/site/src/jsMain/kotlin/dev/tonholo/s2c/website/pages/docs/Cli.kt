package dev.tonholo.s2c.website.pages.docs

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import dev.tonholo.s2c.website.components.layouts.DocsLayout
import dev.tonholo.s2c.website.components.atoms.HowToStructuredData
import dev.tonholo.s2c.website.components.layouts.PageLayoutData
import dev.tonholo.s2c.website.components.molecules.TocEntry
import dev.tonholo.s2c.website.components.organisms.docs.CliDocsContent

private val cliTocEntries = listOf(
    TocEntry(id = "overview", label = "Overview"),
    TocEntry(id = "platform-support", label = "Platform Support"),
    TocEntry(id = "installation", label = "Installation"),
    TocEntry(id = "external-dependencies", label = "External Dependencies"),
    TocEntry(id = "usage", label = "Usage Examples"),
    TocEntry(id = "options", label = "All Options Reference"),
    TocEntry(id = "output-examples", label = "Output Examples"),
)

@InitRoute
fun initCliDocsPage(ctx: InitRouteContext) {
    ctx.data.add(
        PageLayoutData(
            title = "CLI Tool - Convert SVG to Compose",
            description = "Convert SVG and Android XML Drawable files to Jetpack Compose " +
                "ImageVector from the command line. Installation, usage examples, " +
                "and complete options reference.",
            canonicalPath = "/docs/cli",
            structuredData = listOf(
                HowToStructuredData(
                    name = "How to convert SVG to Compose ImageVector using the CLI",
                    description = "Step-by-step guide to converting SVG files to Jetpack " +
                        "Compose ImageVector code using the s2c command-line tool.",
                    steps = listOf(
                        HowToStructuredData.HowToStep(
                            name = "Install the CLI tool",
                            text = "Download the s2c script: curl -o s2c " +
                                "https://raw.githubusercontent.com/rafaeltonholo/svg-to-compose/main/s2c " +
                                "&& chmod +x s2c",
                        ),
                        HowToStructuredData.HowToStep(
                            name = "Run the conversion",
                            text = "Convert your SVG file: ./s2c -o Icon.kt -p com.app.icons " +
                                "-t com.app.theme.AppTheme icon.svg",
                        ),
                        HowToStructuredData.HowToStep(
                            name = "Use the generated ImageVector",
                            text = "Import the generated Icon.kt file in your Compose code " +
                                "and use the ImageVector with the Icon composable.",
                        ),
                    ),
                ),
            ),
        ),
    )
}

@Page
@Layout(".components.layouts.PageLayout")
@Composable
fun CliDocsPage() {
    DocsLayout(
        title = "CLI Tool",
        tocEntries = cliTocEntries,
    ) {
        CliDocsContent()
    }
}
