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
import dev.tonholo.s2c.website.components.organisms.docs.AlternativesContent

private val alternativesTocEntries = listOf(
    TocEntry(id = "overview", label = "Overview"),
    TocEntry(id = "manual-conversion", label = "Manual Conversion"),
    TocEntry(id = "android-studio-import", label = "Android Studio Import"),
    TocEntry(id = "community-tools", label = "Community Tools"),
    TocEntry(id = "comparison-table", label = "Comparison Table"),
)

@InitRoute
fun initAlternativesPage(ctx: InitRouteContext) {
    ctx.data.add(
        PageLayoutData(
            title = "Alternatives - SVG to Compose vs Other Tools",
            description = "Compare SVG to Compose with alternatives: manual ImageVector coding, " +
                "Android Studio SVG import, and other community tools. Feature comparison " +
                "for Kotlin Multiplatform SVG conversion.",
            canonicalPath = "/docs/alternatives",
        ),
    )
}

@Page
@Layout(".components.layouts.PageLayout")
@Composable
fun AlternativesPage() {
    DocsLayout(
        title = "Alternatives & Comparison",
        tocEntries = alternativesTocEntries,
    ) {
        AlternativesContent()
    }
}
