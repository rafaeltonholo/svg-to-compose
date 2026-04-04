@file:Suppress("TooManyFunctions")

package dev.tonholo.s2c.website.components.organisms.docs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.ListStyleType
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.listStyle
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.components.atoms.DocSection
import dev.tonholo.s2c.website.components.atoms.InlineCode
import dev.tonholo.s2c.website.components.molecules.CalloutVariant
import dev.tonholo.s2c.website.components.molecules.CodeAwareSpanText
import dev.tonholo.s2c.website.components.molecules.CodeBlock
import dev.tonholo.s2c.website.components.molecules.DocCallout
import dev.tonholo.s2c.website.components.molecules.TabPanel
import dev.tonholo.s2c.website.components.molecules.TipCalloutCodeAwareVariant
import dev.tonholo.s2c.website.components.molecules.WarningCalloutCodeAwareVariant
import dev.tonholo.s2c.website.theme.SiteTheme
import org.jetbrains.compose.web.dom.Li
import org.jetbrains.compose.web.dom.Ol
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.Ul

@Composable
fun GradlePluginDocsContent(modifier: Modifier = Modifier) {
    Column(modifier = Modifier.fillMaxWidth().gap(SiteTheme.dimensions.size.Xxl).then(modifier)) {
        OverviewSection()
        PlatformSupportSection()
        InstallationSection()
        HowItWorksSection()
        BasicConfigurationSection()
        CommonConfigurationSection()
        ConfigurationOptionsSection()
        ParallelProcessingSection()
        PersistentGenerationSection()
    }
}

@Composable
private fun OverviewSection() {
    DocSection(id = "overview", title = "Overview") {
        SpanText(
            text = "The SVG/XML to Compose Gradle Plugin simplifies the process of converting SVG " +
                "and Android Vector Drawable (AVG/XML) files into Jetpack Compose ImageVector " +
                "properties. It automates the conversion process during your build, ensuring " +
                "consistency and saving development time.",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        SpanText(
            text = "Key features:",
            modifier = DocsBodyTextStyle.toModifier()
                .fontWeight(FontWeight.SemiBold),
        )
        Ul(attrs = DocsBulletListStyle.toModifier().toAttrs()) {
            Li { Text("Automatic conversion during Kotlin compilation") }
            Li { Text("Smart caching for incremental builds") }
            Li { Text("Configurable processing options per icon set") }
            Li { Text("Parallel processing support via Gradle Worker API") }
            Li { Text("Common configuration for shared settings across processors") }
        }
    }
}

@Composable
private fun PlatformSupportSection() {
    DocSection(id = "platform-support", title = "Platform Support") {
        DocCallout(variant = CalloutVariant.TIP) {
            SpanText(
                text = "The Gradle plugin supports Android and Kotlin Multiplatform projects.",
                modifier = DocsBodyTextStyle.toModifier(),
            )
        }
    }
}

@Composable
private fun InstallationSection() {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = remember { listOf("Plugins DSL", "buildSrc") }

    DocSection(id = "installation", title = "Installation") {
        TabPanel(
            tabs = tabs,
            selectedIndex = selectedTab,
            onSelect = { selectedTab = it },
            panels = listOf(
                { PluginsDslTab() },
                { BuildSrcTab() },
            ),
        )
    }
}

@Composable
private fun PluginsDslTab() {
    Column(modifier = Modifier.gap(SiteTheme.dimensions.size.Md)) {
        CodeBlock(
            // language=kotlin
            code = """
                |plugins {
                |    id("dev.tonholo.s2c") version "<latest-version>"
                |}
            """.trimMargin(),
            language = "kotlin",
            filename = "build.gradle.kts",
        )
        DocCallout(variant = CalloutVariant.TIP) {
            CodeAwareSpanText(
                text = "Make sure Maven Central is included in your plugin repositories `settings.gradle.kts`.",
                modifier = DocsBodyTextStyle.toModifier(),
                variant = TipCalloutCodeAwareVariant,
            )
        }
    }
}

@Composable
private fun BuildSrcTab() {
    Column(modifier = Modifier.gap(SiteTheme.dimensions.size.Md)) {
        SpanText(
            text = "Add the plugin dependency to your buildSrc project:",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        CodeBlock(
            // language=kotlin
            code = """
                |// buildSrc/build.gradle.kts
                |repositories {
                |    mavenCentral()
                |}
                |
                |dependencies {
                |    implementation("dev.tonholo.s2c:svg-to-compose-gradle-plugin:<latest-version>")
                |}
            """.trimMargin(),
            language = "kotlin",
            filename = "buildSrc/build.gradle.kts",
        )
        SpanText(
            text = "Then apply the plugin in your module:",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        CodeBlock(
            // language=kotlin
            code = """
                |plugins {
                |    id("dev.tonholo.s2c")
                |}
            """.trimMargin(),
            language = "kotlin",
            filename = "build.gradle.kts",
        )
    }
}

@Composable
private fun HowItWorksSection() {
    DocSection(id = "how-it-works", title = "How It Works") {
        SpanText(
            text = "The plugin integrates into your Gradle build lifecycle with the following " +
                "processing flow:",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        Ol(attrs = DocsBulletListStyle.toModifier().listStyle(ListStyleType.Decimal).toAttrs()) {
            Li {
                SpanText("Configuration Parsing", modifier = Modifier.fontWeight(FontWeight.SemiBold))
                Text(": reads the svgToCompose extension settings from your build script")
            }
            Li {
                SpanText("Icon Scanning", modifier = Modifier.fontWeight(FontWeight.SemiBold))
                Text(": finds SVG and AVG files in the specified source directories")
            }
            Li {
                SpanText("Icon Processing", modifier = Modifier.fontWeight(FontWeight.SemiBold))
                Text(": applies configured options such as optimization and minification")
            }
            Li {
                SpanText("Code Generation", modifier = Modifier.fontWeight(FontWeight.SemiBold))
                Text(": generates Kotlin ImageVector code in the specified package")
            }
            Li {
                SpanText("Build Integration", modifier = Modifier.fontWeight(FontWeight.SemiBold))
                Text(": generated code is included in the Kotlin compilation classpath")
            }
        }
        DocCallout(variant = CalloutVariant.TIP) {
            SpanText(
                text = "The plugin checks for changes and uses a built-in cache. When " +
                    "configuration changes, the cache is invalidated to regenerate icons.",
                modifier = DocsBodyTextStyle.toModifier(),
            )
        }
    }
}

@Composable
private fun BasicConfigurationSection() {
    DocSection(id = "basic-configuration", title = "Basic Configuration") {
        SpanText(
            text = "Configure the plugin using the svgToCompose extension in your build script:",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        CodeBlock(
            // language=kotlin
            code = """
                |svgToCompose {
                |    processor {
                |        val icons by creating {
                |            from(layout.projectDirectory.dir("src/main/resources/icons"))
                |            destinationPackage("com.example.app.ui.icons")
                |            optimize(true)
                |            recursive()
                |            icons {
                |                theme("com.example.app.ui.theme.AppTheme")
                |                minify()
                |                exclude(".*_exclude\\.svg".toRegex())
                |                mapIconNameTo { iconName ->
                |                    iconName.replace("_filled", "")
                |                }
                |            }
                |        }
                |    }
                |}
            """.trimMargin(),
            language = "kotlin",
            filename = "build.gradle.kts",
        )
    }
}

@Composable
private fun CommonConfigurationSection() {
    DocSection(id = "common-configuration", title = "Common Configuration") {
        SpanText(
            text = "Use the common block to define shared settings that apply to all processors. " +
                "Individual processor configurations can override common settings.",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        CodeBlock(
            // language=kotlin
            code = """
                |svgToCompose {
                |    processor {
                |        common {
                |            optimize(true)
                |            recursive()
                |            icons {
                |                minify()
                |            }
                |        }
                |
                |        val outlinedIcons by creating {
                |            from(layout.projectDirectory.dir("src/main/resources/icons/outlined"))
                |            destinationPackage("com.example.app.ui.icons.outlined")
                |        }
                |
                |        val filledIcons by creating {
                |            from(layout.projectDirectory.dir("src/main/resources/icons/filled"))
                |            destinationPackage("com.example.app.ui.icons.filled")
                |        }
                |    }
                |}
            """.trimMargin(),
            language = "kotlin",
            filename = "build.gradle.kts",
        )
    }
}

@Composable
private fun ConfigurationOptionsSection() {
    DocSection(id = "configuration-options", title = "Configuration Options") {
        ProcessorConfigurationSubsection()
        IconParserConfigurationSubsection()
    }
}

@Composable
private fun ProcessorConfigurationSubsection() {
    DocSection(id = "processor-configuration", title = "Processor Configuration", level = 3) {
        SpanText(
            text = "These options are available at the processor level:",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        Ul(
            attrs = DocsBulletListStyle
                .toModifier()
                .gap(SiteTheme.dimensions.size.Sm)
                .toAttrs(),
        ) {
            OptionItem(name = "from(Directory)", description = "Source directory containing SVG/AVG icon files")
            OptionItem(
                name = "destinationPackage(String)",
                description = "Target package for generated ImageVector objects",
            )
            OptionItem(name = "optimize(Boolean)", description = "Enable SVG optimization before conversion")
            OptionItem(name = "recursive()", description = "Recursively search for icons in subdirectories")
            OptionItem(
                name = "maxDepth(Int)",
                description = "Maximum depth for recursive directory search",
            )
        }
    }
}

@Composable
private fun IconParserConfigurationSubsection() {
    DocSection(id = "icon-parser-configuration", title = "Icon Parser Configuration", level = 3) {
        SpanText(
            text = "These options are available inside the icons { } block:",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        Ul(
            attrs = DocsBulletListStyle
                .toModifier()
                .gap(SiteTheme.dimensions.size.Sm)
                .toAttrs(),
        ) {
            OptionItem(
                name = "theme(String)",
                description = "Fully qualified theme class name used for preview annotations",
            )
            OptionItem(name = "minify()", description = "Remove comments and extra whitespace from generated code")
            OptionItem(name = "noPreview()", description = "Disable generation of @Preview composable functions")
            OptionItem(name = "makeInternal()", description = "Add the internal visibility modifier to generated code")
            OptionItem(
                name = "receiverType(String)",
                description = "Set a receiver type for the generated ImageVector extension property",
            )
            OptionItem(
                name = "addToMaterialIcons()",
                description = "Use Material Icons as the receiver type for generated properties",
            )
            OptionItem(
                name = "mapIconNameTo((String) -> String)",
                description = "Transform function to customise generated icon names",
            )
            OptionItem(
                name = "exclude(vararg Regex)",
                description = "Exclude files matching the given filename patterns",
            )
            OptionItem(
                name = "persist()",
                description = "Persist generated files to the source directory (delicate API)",
            )
            OptionItem(
                name = "templateFile(path: RegularFile)",
                description = "Path to an s2c.template.toml file for customising generated code",
            )
        }
        TemplateFileExample()
    }
}

@Composable
private fun TemplateFileExample() {
    SpanText(
        text = "Example usage with a template file:",
        modifier = DocsBodyTextStyle.toModifier()
            .fontWeight(FontWeight.SemiBold),
    )
    CodeBlock(
        // language=kotlin
        code = """
            |svgToCompose {
            |    processor {
            |        val icons by creating {
            |            from(layout.projectDirectory.dir("src/main/resources/icons"))
            |            destinationPackage("com.example.app.ui.icons")
            |            icons {
            |                theme("com.example.app.ui.theme.AppTheme")
            |                // TIP: You can choose whatever name for the template, as soon as it is a toml file.
            |                templateFile(layout.projectDirectory.file("s2c.template.toml"))
            |            }
            |        }
            |    }
            |}
        """.trimMargin(),
        language = "kotlin",
        filename = "build.gradle.kts",
    )
    DocCallout(variant = CalloutVariant.TIP) {
        Span(attrs = DocsBodyTextStyle.toAttrs()) {
            Text("See the ")
            Link(path = "/docs/templates", text = "Template System documentation")
            Text(" for the full schema reference and placeholder grammar.")
        }
    }
}

@Composable
private fun OptionItem(name: String, description: String) {
    Li {
        InlineCode(name)
        Text(": $description")
    }
}

@Composable
private fun ParallelProcessingSection() {
    DocSection(id = "parallel-processing", title = "Parallel Processing") {
        SpanText(
            text = "Enable parallel processing to speed up conversion of large icon sets:",
            modifier = DocsBodyTextStyle.toModifier(),
        )
        CodeBlock(
            // language=kotlin
            code = """
                |svgToCompose {
                |    processor {
                |        useParallelism(parallelism = 4)
                |        // Processor configurations...
                |    }
                |}
            """.trimMargin(),
            language = "kotlin",
            filename = "build.gradle.kts",
        )
        Ul(
            attrs = DocsBulletListStyle
                .toModifier()
                .toAttrs(),
        ) {
            Li {
                CodeAwareSpanText("Parallelism is bounded by Gradle's `--max-workers` setting.")
            }
            Li { Text("Default value (0 or 1) runs processing sequentially") }
            Li { Text("Caching is preserved: unchanged icons are skipped regardless of parallelism") }
        }
    }
}

@Composable
private fun PersistentGenerationSection() {
    DocSection(id = "persistent-generation", title = "Persistent Generation") {
        DocCallout(variant = CalloutVariant.WARNING) {
            CodeAwareSpanText(
                text = "The `persist()` function is a delicate API. When enabled, generated files " +
                    "are written directly to your source directory instead of the build directory. " +
                    "Use this only when you need to commit generated code to version control.",
                modifier = DocsBodyTextStyle.toModifier(),
                variant = WarningCalloutCodeAwareVariant,
            )
        }
        CodeBlock(
            // language=kotlin
            code = """
                |svgToCompose {
                |    processor {
                |        val persistentIcons by creating {
                |            from(layout.projectDirectory.dir("src/main/resources/icons"))
                |            destinationPackage("com.example.app.ui.icons")
                |            icons {
                |                @OptIn(dev.tonholo.s2c.annotations.DelicateSvg2ComposeApi::class)
                |                persist()
                |            }
                |        }
                |    }
                |}
            """.trimMargin(),
            language = "kotlin",
            filename = "build.gradle.kts",
        )
    }
}
