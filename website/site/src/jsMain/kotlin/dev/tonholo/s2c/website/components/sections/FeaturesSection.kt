package dev.tonholo.s2c.website.components.sections

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
import dev.tonholo.s2c.website.components.widgets.Badge
import dev.tonholo.s2c.website.components.widgets.FeatureCard
import dev.tonholo.s2c.website.components.widgets.SectionContainer
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.cssRem
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

        Div(attrs = FeatureGridStyle.toModifier().toAttrs()) {
            FeatureCard(
                icon = { FaFileCode() },
                title = "Full SVG Support",
                description = "Paths, shapes (rect, circle, ellipse, polygon, polyline), groups, use/symbol references, defs, clipping paths, and masks.",
                color = FeatureColors.svgSupport.text,
            )
            FeatureCard(
                icon = { FaAndroid() },
                title = "Android Vector Drawables",
                description = "Native AVG XML parsing with full path data, gradient, group, and clip-path support.",
                color = FeatureColors.androidVector.text,
            )
            FeatureCard(
                icon = { FaPaintbrush() },
                title = "Gradients",
                description = "Linear and radial gradients with stops, offsets, and accurate color mapping to Compose Brush.",
                color = FeatureColors.gradients.text,
            )
            FeatureCard(
                icon = { FaCss3Alt() },
                title = "CSS & Styles",
                description = "Inline styles and <style> blocks with full CSS specificity and cascade support.",
                color = FeatureColors.cssStyles.text,
            )
            FeatureCard(
                icon = { FaArrowsRotate() },
                title = "Transforms",
                description = "Translate, scale, rotate, skewX, skewY, and matrix transforms — all accurately mapped.",
                color = FeatureColors.transforms.text,
            )
            FeatureCard(
                icon = { FaBolt() },
                title = "Optimization",
                description = "Optional integration with SVGO and Avocado for significantly optimized output.",
                color = FeatureColors.optimization.text,
            )
            FeatureCard(
                icon = { FaCode() },
                title = "Kotlin Multiplatform",
                description = "Native binaries for macOS, Linux, and Windows. KMP-compatible @Preview generation.",
                color = FeatureColors.kotlinMultiplatform.text,
            )
            FeatureCard(
                icon = { FaCubes() },
                title = "Gradle Integration",
                description = "Automate conversion at build time with incremental builds and parallel processing.",
                color = FeatureColors.gradleIntegration.text,
            )
            FeatureCard(
                icon = { FaFileZipper() },
                title = "Code Minification",
                description = "Strip comments and inline parameters for minimal generated code size.",
                color = FeatureColors.codeMinification.text,
            )
            FeatureCard(
                icon = { FaTag() },
                title = "Custom Naming",
                description = "Map and transform icon names with regex and lambda-based rules.",
                color = FeatureColors.customNaming.text,
            )
            FeatureCard(
                icon = { FaGem() },
                title = "Material Icons",
                description = "First-class support for Icons.Filled, Icons.Outlined, Icons.Rounded as receiver types.",
                color = FeatureColors.materialIcons.text,
            )
            FeatureCard(
                icon = { FaEye() },
                title = "Preview Generation",
                description = "Auto-generate @Preview composables for visual verification in Android Studio.",
                color = FeatureColors.previewGeneration.text,
            )
        }
    }
}
