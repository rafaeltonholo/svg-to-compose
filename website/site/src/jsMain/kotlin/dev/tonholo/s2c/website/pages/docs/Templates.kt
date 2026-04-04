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
import dev.tonholo.s2c.website.components.organisms.docs.TemplateDocsContent

private val templatesTocEntries = listOf(
    TocEntry(id = "overview", label = "Overview"),
    TocEntry(id = "quick-start", label = "Quick Start"),
    TocEntry(id = "definitions", label = "Definitions"),
    TocEntry(id = "definitions-receiver", label = "Receiver", level = 3),
    TocEntry(id = "definitions-imports", label = "Imports", level = 3),
    TocEntry(id = "definitions-color-mapping", label = "Color Mapping", level = 3),
    TocEntry(id = "templates", label = "Templates"),
    TocEntry(id = "templates-file-header", label = "File Header", level = 3),
    TocEntry(id = "templates-icon-template", label = "Icon Template", level = 3),
    TocEntry(id = "templates-preview", label = "Preview", level = 3),
    TocEntry(id = "fragments", label = "Fragments"),
    TocEntry(id = "placeholder-grammar", label = "Placeholder Grammar"),
    TocEntry(id = "variables-by-namespace", label = "Variables by Namespace"),
    TocEntry(id = "null-handling", label = "Null Handling & Line Trimming"),
    TocEntry(id = "auto-discovery", label = "Auto-Discovery"),
    TocEntry(id = "examples", label = "Examples"),
)

@InitRoute
fun initTemplatesDocsPage(ctx: InitRouteContext) {
    ctx.data.add(
        PageLayoutData(
            title = "Template System Documentation",
            description = "SVG to Compose template system documentation. " +
                "Customize generated Kotlin code with TOML-based templates, placeholders, and fragments.",
            canonicalPath = "/docs/templates",
        ),
    )
}

@Page
@Layout(".components.layouts.PageLayout")
@Composable
fun TemplatesDocsPage() {
    DocsLayout(
        title = "Template System",
        tocEntries = templatesTocEntries,
    ) {
        TemplateDocsContent()
    }
}
