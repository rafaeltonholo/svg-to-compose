package dev.tonholo.s2c.website.pages.docs

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import dev.tonholo.s2c.website.components.atoms.HowToStructuredData
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
    ctx.data.add(
        PageLayoutData(
            title = "Gradle Plugin for SVG to Compose",
            description = "Automate SVG and Android XML Drawable to Compose ImageVector " +
                "conversion in your Gradle build. Incremental builds, smart caching, " +
                "and parallel processing.",
            canonicalPath = "/docs/gradle-plugin",
            structuredData = listOf(
                HowToStructuredData(
                    name = "How to convert SVG to Compose ImageVector using the Gradle plugin",
                    description = "Step-by-step guide to setting up the SVG to Compose Gradle " +
                        "plugin for automated conversion in your build pipeline.",
                    steps = listOf(
                        HowToStructuredData.HowToStep(
                            name = "Add the plugin",
                            text = "Add id(\"dev.tonholo.s2c\") to your build.gradle.kts plugins block.",
                        ),
                        HowToStructuredData.HowToStep(
                            name = "Configure the processor",
                            text = "Add a svgToCompose { processor { } } block with your source " +
                                "directory, destination package, and icon options.",
                        ),
                        HowToStructuredData.HowToStep(
                            name = "Build your project",
                            text = "Run ./gradlew build. The plugin automatically converts SVG " +
                                "files to ImageVector code during the build.",
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
fun GradlePluginDocsPage() {
    DocsLayout(
        title = "Gradle Plugin",
        tocEntries = gradlePluginTocEntries,
    ) {
        GradlePluginDocsContent()
    }
}
