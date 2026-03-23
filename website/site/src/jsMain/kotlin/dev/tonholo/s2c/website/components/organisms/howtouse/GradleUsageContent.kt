package dev.tonholo.s2c.website.components.organisms.howtouse

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaArrowRight
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.components.molecules.CodeBlock
import dev.tonholo.s2c.website.components.molecules.UsageCard
import dev.tonholo.s2c.website.theme.SiteTheme
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.dom.Div

val GradleUsageCardGridStyle = CssStyle {
    base {
        Modifier
            .gap(SiteTheme.dimensions.size.Lg)
    }
}

@Composable
fun GradleUsageContent(modifier: Modifier = Modifier) {
    Column(modifier = GradleUsageCardGridStyle.toModifier().then(modifier)) {
        BasicSetupCard()
        MultiSourceCard()
        AdvancedOptionsCard()
        Div(
            attrs = Modifier
                .margin(top = SiteTheme.dimensions.size.Lg)
                .fillMaxWidth()
                .display(DisplayStyle.Flex)
                .toAttrs(),
        ) {
            Link(
                path = "/docs/gradle-plugin",
                variant = SeeFullDocumentationLinkButton,
            ) {
                SpanText("See full Gradle Plugin documentation")
                FaArrowRight(size = IconSize.XS)
            }
        }
    }
}

@Composable
private fun BasicSetupCard() {
    UsageCard(
        title = "Basic Setup",
        description = "Minimal configuration to get started.",
        modifier = Modifier.fillMaxWidth(),
    ) {
        CodeBlock(
            // language=kotlin
            code = """
                |svgToCompose {
                |    processor {
                |        val icons by creating {
                |            from(layout.projectDirectory.dir("src/main/resources/icons"))
                |            destinationPackage("com.example.app.ui.icons")
                |        }
                |    }
                |}
            """.trimMargin(),
            language = "kotlin",
        )
    }
}

@Composable
private fun MultiSourceCard() {
    UsageCard(
        title = "Multi-source Processing",
        description = "Process multiple directories with different configurations.",
        modifier = Modifier.fillMaxWidth(),
    ) {
        CodeBlock(
            // language=kotlin
            code = $$"""
                |svgToCompose {
                |    processor {
                |        common {
                |            recursive()
                |            optimize(enabled = false)
                |        }
                |        val basePackage = "com.example.icons" 
                |        val filled by creating {
                |            from(layout.projectDirectory.dir("assets/icons/filled"))
                |            destinationPackage("$basePackage.filled")
                |            icons {
                |                receiverType("Icons.Filled")
                |            }
                |        }
                |        val outlined by creating {
                |            from(layout.projectDirectory.dir("assets/icons/outlined"))
                |            destinationPackage("$basePackage.outlined")
                |            icons {
                |                receiverType("Icons.Outlined")
                |            }
                |        }
                |    }
                |}
            """.trimMargin(),
            language = "kotlin",
        )
    }
}

@Composable
private fun AdvancedOptionsCard() {
    UsageCard(
        title = "Advanced Options",
        description = "Optimize, theme previews, minify, persist, control visibility, and others. " +
            "See full documentation for more.",
        modifier = Modifier.fillMaxWidth(),
    ) {
        CodeBlock(
            // language=kotlin
            code = """
                |svgToCompose {
                |    processor {
                |        val icons by creating {
                |            from(layout.projectDirectory.dir("src/main/resources/icons"))
                |            destinationPackage("com.example.app.ui.icons")
                |            optimize()
                |            icons {
                |                theme("com.example.app.ui.AppTheme")
                |                makeInternal()
                |                minify()
                |                @OptIn(DelicateSvg2ComposeApi::class)
                |                persist()
                |            }
                |        }
                |    }
                |}
            """.trimMargin(),
            language = "kotlin",
        )
    }
}
