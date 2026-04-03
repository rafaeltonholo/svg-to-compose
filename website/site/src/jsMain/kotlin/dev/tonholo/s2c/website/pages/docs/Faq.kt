package dev.tonholo.s2c.website.pages.docs

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import dev.tonholo.s2c.website.components.atoms.FAQPageStructuredData
import dev.tonholo.s2c.website.components.layouts.DocsLayout
import dev.tonholo.s2c.website.components.layouts.PageLayoutData
import dev.tonholo.s2c.website.components.molecules.TocEntry
import dev.tonholo.s2c.website.components.organisms.docs.FaqContent
import dev.tonholo.s2c.website.components.organisms.docs.faqQuestions

private val faqTocEntries = listOf(
    TocEntry(id = "what-is-svg-to-compose", label = "What is SVG to Compose?"),
    TocEntry(id = "how-to-convert", label = "How to Convert"),
    TocEntry(id = "android-xml-drawables", label = "Android XML Drawables"),
    TocEntry(id = "kotlin-multiplatform", label = "Kotlin Multiplatform"),
    TocEntry(id = "supported-features", label = "Supported Features"),
    TocEntry(id = "cli-vs-gradle", label = "CLI vs Gradle Plugin"),
    TocEntry(id = "optimization", label = "Code Optimization"),
)

@InitRoute
fun initFaqPage(ctx: InitRouteContext) {
    ctx.data.add(
        PageLayoutData(
            title = "FAQ - Frequently Asked Questions",
            description = "Common questions about SVG to Compose. How to convert SVG to " +
                "ImageVector, Android XML Drawable support, Kotlin Multiplatform " +
                "compatibility, and more.",
            canonicalPath = "/docs/faq",
            structuredData = listOf(
                FAQPageStructuredData(questions = faqQuestions),
            ),
        ),
    )
}

@Page
@Layout(".components.layouts.PageLayout")
@Composable
fun FaqPage() {
    DocsLayout(
        title = "Frequently Asked Questions",
        tocEntries = faqTocEntries,
    ) {
        FaqContent()
    }
}
