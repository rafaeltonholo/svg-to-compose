package dev.tonholo.s2c.website.components.organisms.docs

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.components.atoms.DocSection
import dev.tonholo.s2c.website.components.atoms.FAQPageStructuredData
import dev.tonholo.s2c.website.components.atoms.InlineCode
import dev.tonholo.s2c.website.components.molecules.CodeBlock
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.theme.common.SiteLinkStyleVariant
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

internal val faqQuestions: List<FAQPageStructuredData.QuestionAnswer> = listOf(
    FAQPageStructuredData.QuestionAnswer(
        question = "What is SVG to Compose?",
        answer = "SVG to Compose is a Kotlin Multiplatform tool that converts SVG and Android XML " +
            "Drawable files into Jetpack Compose ImageVector code. It eliminates the manual, " +
            "error-prone process of hand-writing ImageVector builder code. Available as a CLI " +
            "tool, a Gradle plugin, and a library.",
    ),
    FAQPageStructuredData.QuestionAnswer(
        question = "How do I convert an SVG file to Jetpack Compose ImageVector?",
        answer = "Install the CLI tool and run: s2c -o Icon.kt -p com.app.icons " +
            "-t com.app.theme.AppTheme icon.svg. For automated build integration, use the " +
            "Gradle plugin instead. Both approaches use the same conversion engine.",
    ),
    FAQPageStructuredData.QuestionAnswer(
        question = "Does it support Android XML Drawables?",
        answer = "Yes. Pass an .xml file instead of .svg. The parser handles Android Vector " +
            "Drawable XML format including path data, groups, gradients, and clip paths.",
    ),
    FAQPageStructuredData.QuestionAnswer(
        question = "Can I use it in a Kotlin Multiplatform project?",
        answer = "Yes. SVG to Compose is built with Kotlin Multiplatform. The core library " +
            "targets JVM, JS, WASM/JS, macOS, Linux, and Windows. Generated code uses Jetpack " +
            "Compose's ImageVector API which works across all Compose targets. Use the --kmp " +
            "flag with the CLI for KMP-compatible output.",
    ),
    FAQPageStructuredData.QuestionAnswer(
        question = "What SVG features are supported?",
        answer = "Supported features include all path commands, basic shapes, groups with " +
            "transforms, linear and radial gradients, clip paths, fill and stroke styling, " +
            "viewBox, and opacity. Not yet supported: filters, masks, patterns, text elements, " +
            "and animations.",
    ),
    FAQPageStructuredData.QuestionAnswer(
        question = "How does the Gradle plugin differ from the CLI?",
        answer = "The Gradle plugin integrates conversion into your build pipeline with " +
            "incremental build support, smart caching, and parallel processing. It automatically " +
            "re-converts when source files change. The CLI is a standalone tool for one-off " +
            "conversions or CI scripts.",
    ),
    FAQPageStructuredData.QuestionAnswer(
        question = "Is the generated code optimized?",
        answer = "Yes. SVG to Compose integrates with SVGO (for SVG files) and Avocado (for " +
            "Android XML Drawables) to optimize vector paths before code generation. The " +
            "generated ImageVector uses lazy initialization with a backing field for efficient " +
            "caching.",
    ),
)

@Composable
fun FaqContent(modifier: Modifier = Modifier) {
    Column(modifier = Modifier.fillMaxWidth().gap(SiteTheme.dimensions.size.Xxl).then(modifier)) {
        WhatIsS2cSection()
        HowToConvertSection()
        AndroidXmlDrawablesSection()
        KotlinMultiplatformSection()
        SupportedFeaturesSection()
        CliVsGradleSection()
        OptimizationSection()
    }
}

@Composable
private fun WhatIsS2cSection() {
    DocSection(id = "what-is-svg-to-compose", title = "What is SVG to Compose?") {
        Span(attrs = DocsBodyTextStyle.toAttrs()) {
            Text(
                "SVG to Compose is a Kotlin Multiplatform tool that converts SVG and Android XML " +
                    "Drawable files into Jetpack Compose ",
            )
            InlineCode("ImageVector")
            Text(
                " code. It eliminates the manual, error-prone process of hand-writing " +
                    "ImageVector builder code. Available as a CLI tool, a Gradle plugin, and a library.",
            )
        }
    }
}

@Composable
private fun HowToConvertSection() {
    DocSection(id = "how-to-convert", title = "How to Convert") {
        SpanText(
            text = "Install the CLI tool and run:",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        CodeBlock(
            code = "s2c -o Icon.kt -p com.app.icons -t com.app.theme.AppTheme icon.svg",
            language = "console",
        )
        SpanText(
            text = "For automated build integration, use the Gradle plugin instead. Both approaches " +
                "use the same conversion engine.",
            modifier = DocsBodyTextStyle.toModifier(),
        )
    }
}

@Composable
private fun AndroidXmlDrawablesSection() {
    DocSection(id = "android-xml-drawables", title = "Does it support Android XML Drawables?") {
        Span(attrs = DocsBodyTextStyle.toAttrs()) {
            Text("Yes. Pass an ")
            InlineCode(".xml")
            Text(" file instead of ")
            InlineCode(".svg")
            Text(
                ". The parser handles Android Vector Drawable XML format including path data, " +
                    "groups, gradients, and clip paths.",
            )
        }
    }
}

@Composable
private fun KotlinMultiplatformSection() {
    DocSection(id = "kotlin-multiplatform", title = "Can I use it in a Kotlin Multiplatform project?") {
        Span(attrs = DocsBodyTextStyle.toAttrs()) {
            Text(
                "Yes. SVG to Compose is built with Kotlin Multiplatform. The core library targets " +
                    "JVM, JS, WASM/JS, macOS, Linux, and Windows. Generated code uses Jetpack Compose's ",
            )
            InlineCode("ImageVector")
            Text(" API which works across all Compose targets. Use the ")
            InlineCode("--kmp")
            Text(" flag with the CLI for KMP-compatible output.")
        }
    }
}

@Composable
private fun SupportedFeaturesSection() {
    DocSection(id = "supported-features", title = "What SVG features are supported?") {
        SpanText(
            text = "Supported features include all path commands, basic shapes, groups with transforms, " +
                "linear and radial gradients, clip paths, fill and stroke styling, viewBox, and opacity. " +
                "Not yet supported: filters, masks, patterns, text elements, and animations.",
            modifier = DocsBodyTextStyle.toModifier(),
        )
    }
}

@Composable
private fun CliVsGradleSection() {
    DocSection(id = "cli-vs-gradle", title = "How does the Gradle plugin differ from the CLI?") {
        SpanText(
            text = "The Gradle plugin integrates conversion into your build pipeline with incremental " +
                "build support, smart caching, and parallel processing. It automatically re-converts " +
                "when source files change. The CLI is a standalone tool for one-off conversions or " +
                "CI scripts.",
            modifier = DocsBodyTextStyle.toModifier(),
        )
    }
}

@Composable
private fun OptimizationSection() {
    DocSection(id = "optimization", title = "Is the generated code optimized?") {
        Span(attrs = DocsBodyTextStyle.toAttrs()) {
            Text(
                "Yes. SVG to Compose integrates with SVGO for SVG optimization and Avocado " +
                    "for XML Drawable optimization to optimize vector paths before code generation. The generated ",
            )
            InlineCode("ImageVector")
            Text(" uses lazy initialization with a backing field for efficient caching.")
        }
        SpanText(
            text = "Install the optimization tools globally:",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        CodeBlock(
            code = """
                |npm install -g svgo    # SVG optimization
                |npm install -g avocado # XML Drawable optimization
            """.trimMargin(),
            language = "console",
        )
        Span(attrs = DocsBodyTextStyle.toAttrs()) {
            Text("For more details, see the ")
            Link(path = "/docs/cli", variant = SiteLinkStyleVariant) { Text("CLI documentation") }
            Text(" section on external dependencies.")
        }
    }
}
