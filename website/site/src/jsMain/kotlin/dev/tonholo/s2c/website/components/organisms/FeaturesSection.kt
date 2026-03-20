package dev.tonholo.s2c.website.components.organisms

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.components.icons.fa.FaAndroid
import com.varabyte.kobweb.silk.components.icons.fa.FaArrowsRotate
import com.varabyte.kobweb.silk.components.icons.fa.FaBolt
import com.varabyte.kobweb.silk.components.icons.fa.FaCode
import com.varabyte.kobweb.silk.components.icons.fa.FaCss3Alt
import com.varabyte.kobweb.silk.components.icons.fa.FaCubes
import com.varabyte.kobweb.silk.components.icons.fa.FaEye
import com.varabyte.kobweb.silk.components.icons.fa.FaFileCode
import com.varabyte.kobweb.silk.components.icons.fa.FaFileZipper
import com.varabyte.kobweb.silk.components.icons.fa.FaGem
import com.varabyte.kobweb.silk.components.icons.fa.FaPaintbrush
import com.varabyte.kobweb.silk.components.icons.fa.FaTag

import dev.tonholo.s2c.website.FeatureColors
import dev.tonholo.s2c.website.HeadlineTextStyle
import dev.tonholo.s2c.website.SubheadlineTextStyle
import dev.tonholo.s2c.website.components.atoms.AnimateOnScroll
import dev.tonholo.s2c.website.components.atoms.Badge
import dev.tonholo.s2c.website.components.molecules.FeatureCard
import dev.tonholo.s2c.website.components.molecules.SectionContainer
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.dom.Div

val FeatureGridStyle = CssStyle {
    base {
        Modifier
            .display(DisplayStyle.Grid)
            .gap(1.cssRem)
            .fillMaxWidth()
            .styleModifier { property("grid-template-columns", "1fr") }
    }
    Breakpoint.SM {
        Modifier.styleModifier { property("grid-template-columns", "repeat(2, 1fr)") }
    }
    Breakpoint.LG {
        Modifier.styleModifier { property("grid-template-columns", "repeat(3, 1fr)") }
    }
    Breakpoint.XL {
        Modifier.styleModifier { property("grid-template-columns", "repeat(4, 1fr)") }
    }
}

@Composable
fun FeaturesSection() {
    val palette = ColorMode.current.toSitePalette()

    SectionContainer(id = "features", altBackground = true) {
        AnimateOnScroll {
            Div(
                attrs = Modifier
                    .fillMaxWidth()
                    .display(DisplayStyle.Flex)
                    .styleModifier { property("flex-direction", "column") }
                    .alignItems(AlignItems.Center)
                    .toAttrs(),
            ) {
                Badge(
                    text = "Capabilities",
                    color = palette.brand.purple,
                )
                SpanText(
                    "Everything You Need",
                    modifier = HeadlineTextStyle.toModifier()
                        .margin(top = 1.cssRem),
                )
                SpanText(
                    "A comprehensive toolkit for generating production-quality Jetpack Compose ImageVector code.",
                    modifier = SubheadlineTextStyle.toModifier()
                        .margin(top = 0.5.cssRem, bottom = 2.cssRem),
                )
            }
        }

        data class FeatureCardData(
            val icon: @Composable () -> Unit,
            val title: String,
            val description: String,
            val color: com.varabyte.kobweb.compose.ui.graphics.Color,
        )

        val features = listOf(
            FeatureCardData(
                icon = { FaFileCode() },
                title = "Full SVG Support",
                description = "Paths, shapes (rect, circle, ellipse, polygon, polyline), groups, use/symbol references, defs, clipping paths, and masks.",
                color = FeatureColors.svgSupport.text,
            ),
            FeatureCardData(
                icon = { FaAndroid() },
                title = "Android Vector Drawables",
                description = "Native AVG XML parsing with full path data, gradient, group, and clip-path support.",
                color = FeatureColors.androidVector.text,
            ),
            FeatureCardData(
                icon = { FaPaintbrush() },
                title = "Gradients",
                description = "Linear and radial gradients with stops, offsets, and accurate color mapping to Compose Brush.",
                color = FeatureColors.gradients.text,
            ),
            FeatureCardData(
                icon = { FaCss3Alt() },
                title = "CSS & Styles",
                description = "Inline styles and <style> blocks with full CSS specificity and cascade support.",
                color = FeatureColors.cssStyles.text,
            ),
            FeatureCardData(
                icon = { FaArrowsRotate() },
                title = "Transforms",
                description = "Translate, scale, rotate, skewX, skewY, and matrix transforms — all accurately mapped.",
                color = FeatureColors.transforms.text,
            ),
            FeatureCardData(
                icon = { FaBolt() },
                title = "Optimization",
                description = "Optional integration with SVGO and Avocado for significantly optimized output.",
                color = FeatureColors.optimization.text,
            ),
            FeatureCardData(
                icon = { FaCode() },
                title = "Kotlin Multiplatform",
                description = "Native binaries for macOS, Linux, and Windows. KMP-compatible @Preview generation.",
                color = FeatureColors.kotlinMultiplatform.text,
            ),
            FeatureCardData(
                icon = { FaCubes() },
                title = "Gradle Integration",
                description = "Automate conversion at build time with incremental builds and parallel processing.",
                color = FeatureColors.gradleIntegration.text,
            ),
            FeatureCardData(
                icon = { FaFileZipper() },
                title = "Code Minification",
                description = "Strip comments and inline parameters for minimal generated code size.",
                color = FeatureColors.codeMinification.text,
            ),
            FeatureCardData(
                icon = { FaTag() },
                title = "Custom Naming",
                description = "Map and transform icon names with regex and lambda-based rules.",
                color = FeatureColors.customNaming.text,
            ),
            FeatureCardData(
                icon = { FaGem() },
                title = "Material Icons",
                description = "First-class support for Icons.Filled, Icons.Outlined, Icons.Rounded as receiver types.",
                color = FeatureColors.materialIcons.text,
            ),
            FeatureCardData(
                icon = { FaEye() },
                title = "Preview Generation",
                description = "Auto-generate @Preview composables for visual verification in Android Studio.",
                color = FeatureColors.previewGeneration.text,
            ),
        )

        Div(attrs = FeatureGridStyle.toModifier().toAttrs()) {
            features.forEachIndexed { index, feature ->
                AnimateOnScroll(delay = ((index % 4) * 70).ms) {
                    FeatureCard(
                        icon = feature.icon,
                        title = feature.title,
                        description = feature.description,
                        color = feature.color,
                    )
                }
            }
        }
    }
}
