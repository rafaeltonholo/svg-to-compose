package dev.tonholo.s2c.website.pages.docs

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import dev.tonholo.s2c.website.components.layouts.DocsLayout
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
            title = "CLI Documentation",
            description = "SVG to Compose CLI tool documentation. " +
                "Installation, usage examples, platform support, and all options reference.",
            canonicalPath = "/docs/cli",
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
