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
import dev.tonholo.s2c.website.components.organisms.docs.GradlePluginDocsContent

private val gradlePluginTocEntries = listOf(
    TocEntry(id = "overview", label = "Overview"),
    TocEntry(id = "platform-support", label = "Platform Support"),
    TocEntry(id = "installation", label = "Installation"),
    TocEntry(id = "how-it-works", label = "How It Works"),
    TocEntry(id = "basic-configuration", label = "Basic Configuration"),
    TocEntry(id = "common-configuration", label = "Common Configuration"),
    TocEntry(id = "configuration-options", label = "Configuration Options"),
    TocEntry(id = "processor-configuration", label = "Processor Configuration", level = 3),
    TocEntry(id = "icon-parser-configuration", label = "Icon Parser Configuration", level = 3),
    TocEntry(id = "parallel-processing", label = "Parallel Processing"),
    TocEntry(id = "persistent-generation", label = "Persistent Generation"),
)

@InitRoute
fun initGradlePluginDocsPage(ctx: InitRouteContext) {
    ctx.data.add(PageLayoutData("Gradle Plugin Documentation"))
}

@Page
@Layout(".components.layouts.PageLayout")
@Composable
fun GradlePluginDocsPage() {
    DocsLayout(
        title = "Gradle Plugin",
        tocEntries = gradlePluginTocEntries,
    ) {
        GradlePluginDocsContent()
    }
}
