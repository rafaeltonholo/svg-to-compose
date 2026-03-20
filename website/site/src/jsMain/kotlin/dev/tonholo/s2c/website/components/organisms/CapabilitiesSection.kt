package dev.tonholo.s2c.website.components.organisms

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.borderBottom
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.gridTemplateColumns
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.LabelTextStyle
import dev.tonholo.s2c.website.components.molecules.SectionContainer
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.fr
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

private const val CAPABILITIES_COLUMN_COUNT = 3

val CapabilitiesGridStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .display(DisplayStyle.Grid)
            .gridTemplateColumns { size(1.fr) }
            .gap(2.cssRem)
    }
    Breakpoint.SM {
        Modifier.gridTemplateColumns { repeat(2) { size(1.fr) } }
    }
    Breakpoint.MD {
        Modifier.gridTemplateColumns { repeat(CAPABILITIES_COLUMN_COUNT) { size(1.fr) } }
    }
}

private data class CapabilityCategory(val title: String, val items: List<String>)

private val categories = listOf(
    CapabilityCategory(
        title = "Conversion",
        items = listOf(
            "SVG paths/shapes/groups",
            "Android Vector Drawables",
            "Linear & radial gradients",
            "CSS styles & specificity",
            "Transforms & clipping",
        ),
    ),
    CapabilityCategory(
        title = "Integration",
        items = listOf(
            "Gradle plugin (incremental)",
            "CLI (macOS/Linux/Windows)",
            "Kotlin Multiplatform",
            "SVGO/Avocado optimization",
        ),
    ),
    CapabilityCategory(
        title = "Developer Experience",
        items = listOf(
            "@Preview generation",
            "Code minification",
            "Custom naming rules",
            "Material Icons receiver types",
        ),
    ),
)

@Composable
fun CapabilitiesSection(modifier: Modifier = Modifier) {
    val palette = ColorMode.current.toSitePalette()
    SectionContainer(id = "capabilities", modifier = modifier, altBackground = true) {
        SpanText(
            "Capabilities",
            modifier = LabelTextStyle.toModifier()
                .color(palette.onSurfaceVariant)
                .margin(bottom = 1.cssRem),
        )

        Div(attrs = CapabilitiesGridStyle.toModifier().toAttrs()) {
            categories.forEach { category ->
                Div {
                    SpanText(
                        category.title,
                        modifier = Modifier
                            .fontWeight(FontWeight.SemiBold)
                            .fontSize(1.125.cssRem)
                            .display(DisplayStyle.Block)
                            .padding(bottom = 0.5.cssRem)
                            .margin(bottom = 0.75.cssRem)
                            .borderBottom(
                                width = 2.px,
                                style = LineStyle.Solid,
                                color = palette.primary,
                            ),
                    )
                    category.items.forEach { item ->
                        SpanText(
                            item,
                            modifier = Modifier
                                .display(DisplayStyle.Block)
                                .color(palette.onSurfaceVariant)
                                .lineHeight(2.0),
                        )
                    }
                }
            }
        }
    }
}
