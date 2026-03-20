package dev.tonholo.s2c.website.components.organisms.howtouse

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.components.molecules.CodeBlock
import dev.tonholo.s2c.website.components.molecules.UsageCard
import org.jetbrains.compose.web.dom.Div

@Composable
fun GradleUsageContent() {
    Div(attrs = UsageCardGridStyle.toModifier().toAttrs()) {
        UsageCard(
            title = "Basic Setup",
            description = "Minimal configuration to get started.",
        ) {
            CodeBlock(
                code = """svgToCompose {
    processor {
        val icons by creating {
            from(layout.projectDirectory.dir(
                "src/main/resources/icons"
            ))
            destinationPackage(
                "com.example.app.ui.icons"
            )
        }
    }
}""",
                language = "kotlin",
            )
        }
        UsageCard(
            title = "Multi-source Processing",
            description = "Process multiple directories with different configurations.",
        ) {
            CodeBlock(
                code = """svgToCompose {
    processor {
        val filled by creating {
            from(layout.projectDirectory.dir(
                "assets/icons/filled"
            ))
            destinationPackage(
                "com.example.icons.filled"
            )
            icons {
                receiverType("Icons.Filled")
            }
        }
        val outlined by creating {
            from(layout.projectDirectory.dir(
                "assets/icons/outlined"
            ))
            destinationPackage(
                "com.example.icons.outlined"
            )
            icons {
                receiverType("Icons.Outlined")
            }
        }
    }
}""",
                language = "kotlin",
            )
        }
        UsageCard(
            title = "Advanced Options",
            description = "Exclude files, rename icons, and use parallel processing.",
        ) {
            CodeBlock(
                code = """svgToCompose {
    processor {
        val icons by creating {
            from(layout.projectDirectory.dir(
                "src/main/resources/icons"
            ))
            destinationPackage(
                "com.example.app.ui.icons"
            )
            optimize(true)
            icons {
                theme("...AppTheme")
                makeInternal(true)
            }
        }
    }
}""",
                language = "kotlin",
            )
        }
    }
}
