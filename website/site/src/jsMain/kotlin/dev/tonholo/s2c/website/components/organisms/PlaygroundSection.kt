package dev.tonholo.s2c.website.components.organisms

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.breakpoint.displayIfAtLeast
import com.varabyte.kobweb.silk.style.breakpoint.displayUntil
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.GradientTextStyle
import dev.tonholo.s2c.website.HeadlineTextStyle
import dev.tonholo.s2c.website.SubheadlineTextStyle
import dev.tonholo.s2c.website.components.atoms.AnimateOnScroll
import dev.tonholo.s2c.website.components.molecules.playground.InputPanel
import dev.tonholo.s2c.website.components.molecules.OptionsSection
import dev.tonholo.s2c.website.components.molecules.playground.OutputPanel
import dev.tonholo.s2c.website.components.molecules.playground.PreviewPanel
import dev.tonholo.s2c.website.components.molecules.SampleButtons
import dev.tonholo.s2c.website.components.molecules.SectionContainer
import dev.tonholo.s2c.website.components.molecules.playground.PlaygroundToolbar
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

// region Sample Data

private data class SampleData(
    val name: String,
    val svgCode: String,
    val kotlinCode: String,
)

@Suppress("MaxLineLength")
private val samples = listOf(
    SampleData(
        name = "Heart",
        svgCode = """<svg viewBox="0 0 24 24">
  <path fill="#E91E63"
    d="M12 21.35l-1.45-1.32
       C5.4 15.36 2 12.28
       2 8.5 2 5.42 4.42 3
       7.5 3c1.74 0 3.41.81
       4.5 2.09C13.09 3.81
       14.76 3 16.5 3 19.58
       3 22 5.42 22 8.5c0
       3.78-3.4 6.86-8.55
       11.54L12 21.35z"/>
</svg>""",
        kotlinCode = """val HeartIcon: ImageVector
    get() {
        if (_heartIcon != null) {
            return _heartIcon!!
        }
        _heartIcon = ImageVector.Builder(
            name = "HeartIcon",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f,
        ).apply {
            path(
                fill = SolidColor(
                    Color(0xFFE91E63)
                ),
            ) {
                moveTo(12f, 21.35f)
                lineTo(-1.45f, -1.32f)
                curveTo(5.4f, 15.36f,
                    2f, 12.28f, 2f, 8.5f)
                curveTo(2f, 5.42f, 4.42f,
                    3f, 7.5f, 3f)
                close()
            }
        }.build()
        return _heartIcon!!
    }

private var _heartIcon: ImageVector? = null""",
    ),
    SampleData(
        name = "Star",
        svgCode = """<svg viewBox="0 0 24 24">
  <path fill="#FFD700"
    d="M12 2l3.09 6.26L22
       9.27l-5 4.87L18.18
       22 12 18.27 5.82 22
       6.91 14.14 2 9.27l6.91
       -1.01L12 2z"/>
</svg>""",
        kotlinCode = """val StarIcon: ImageVector
    get() {
        if (_starIcon != null) {
            return _starIcon!!
        }
        _starIcon = ImageVector.Builder(
            name = "StarIcon",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f,
        ).apply {
            path(
                fill = SolidColor(
                    Color(0xFFFFD700)
                ),
            ) {
                moveTo(12f, 2f)
                lineTo(3.09f, 6.26f)
                close()
            }
        }.build()
        return _starIcon!!
    }

private var _starIcon: ImageVector? = null""",
    ),
    SampleData(
        name = "Android",
        svgCode = """<svg viewBox="0 0 24 24">
  <path fill="#3DDC84"
    d="M6 18c0 .55.45 1 1
       1h1v3.5c0
       .83.67 1.5 1.5 1.5s1.5
       -.67 1.5-1.5V19h2v3.5c0
       .83.67 1.5 1.5 1.5s1.5
       -.67 1.5-1.5V19h1c.55
       0 1-.45 1-1V8H6v10z"/>
</svg>""",
        kotlinCode = """val AndroidIcon: ImageVector
    get() {
        if (_androidIcon != null) {
            return _androidIcon!!
        }
        _androidIcon = ImageVector.Builder(
            name = "AndroidIcon",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f,
        ).apply {
            path(
                fill = SolidColor(
                    Color(0xFF3DDC84)
                ),
            ) {
                moveTo(6f, 18f)
                close()
            }
        }.build()
        return _androidIcon!!
    }

private var _androidIcon: ImageVector? = null""",
    ),
    SampleData(
        name = "Kotlin",
        svgCode = """<svg viewBox="0 0 24 24">
  <polygon fill="#7F52FF"
    points="0,0 12,0 0,12"/>
  <polygon fill="#7F52FF"
    points="0,12 12,0 24,0
            24,12 12,24 0,24"/>
</svg>""",
        kotlinCode = """val KotlinIcon: ImageVector
    get() {
        if (_kotlinIcon != null) {
            return _kotlinIcon!!
        }
        _kotlinIcon = ImageVector.Builder(
            name = "KotlinIcon",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f,
        ).apply {
            path(
                fill = SolidColor(
                    Color(0xFF7F52FF)
                ),
            ) {
                moveTo(0f, 0f)
                lineTo(12f, 0f)
                lineTo(0f, 12f)
                close()
            }
        }.build()
        return _kotlinIcon!!
    }

private var _kotlinIcon: ImageVector? = null""",
    ),
)

// endregion

// region Styles

val PlaygroundHeadingContainerStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .styleModifier { property("flex-direction", "column") }
        .alignItems(AlignItems.Center)
        .gap(1.cssRem)
        .margin(bottom = 2.cssRem)
}

val EditorPanelStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .borderRadius(0.75.cssRem)
        .border(1.px, LineStyle.Solid, colorMode.toSitePalette().borderStrong)
        .overflow(Overflow.Hidden)
}

val ConvertButtonStyle = CssStyle.base {
    Modifier
        .styleModifier { property("background-image", "linear-gradient(135deg, #7F52FF, #5A30DD)") }
        .color(Colors.White)
        .borderRadius(0.5.cssRem)
        .padding(topBottom = 0.375.cssRem, leftRight = 1.cssRem)
        .fontWeight(FontWeight.SemiBold)
        .fontSize(0.8.cssRem)
        .cursor(Cursor.Pointer)
        .border(0.px, LineStyle.None, Colors.Transparent)
        .styleModifier {
            property("transition", "all 0.2s ease")
            property("margin-left", "auto")
        }
}

val DesktopPanelsStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Grid)
        .styleModifier { property("grid-template-columns", "1fr 1fr 1fr") }
        .minHeight(460.px)
}

val MobileTabBarStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .gap(0.25.cssRem)
        .padding(0.5.cssRem)
        .backgroundColor(colorMode.toSitePalette().surfaceHeader)
}

val MobileTabStyle = CssStyle.base {
    Modifier
        .padding(topBottom = 0.5.cssRem, leftRight = 1.cssRem)
        .borderRadius(0.5.cssRem)
        .cursor(Cursor.Pointer)
        .backgroundColor(Colors.Transparent)
        .color(colorMode.toSitePalette().muted)
        .fontWeight(FontWeight.Medium)
        .fontSize(0.8.cssRem)
        .border(0.px, LineStyle.None, Colors.Transparent)
        .styleModifier {
            property("transition", "all 0.2s ease")
            property("flex", "1")
            property("text-align", "center")
        }
}

val MobileTabActiveStyle = CssStyle.base {
    Modifier
        .padding(topBottom = 0.5.cssRem, leftRight = 1.cssRem)
        .borderRadius(0.5.cssRem)
        .cursor(Cursor.Pointer)
        .styleModifier { property("background-image", "linear-gradient(135deg, #7F52FF, #5A30DD)") }
        .color(Colors.White)
        .fontWeight(FontWeight.Medium)
        .fontSize(0.8.cssRem)
        .border(0.px, LineStyle.None, Colors.Transparent)
        .styleModifier {
            property("transition", "all 0.2s ease")
            property("flex", "1")
            property("text-align", "center")
        }
}

val MobilePanelContentStyle = CssStyle.base {
    Modifier
        .minHeight(300.px)
        .backgroundColor(colorMode.toSitePalette().surfaceAlt)
}

// endregion

@Composable
fun PlaygroundSection() {
    SectionContainer(
        id = "playground",
        contentModifier = Modifier.maxWidth(96.cssRem),
    ) {
        var selectedSample by remember { mutableStateOf(0) }
        var inputCode by remember { mutableStateOf(samples[0].svgCode) }
        var activePanel by remember { mutableStateOf(0) }
        var inputMode by remember { mutableStateOf("paste") }
        var extension by remember { mutableStateOf("svg") }

        val currentSample = samples[selectedSample]

        // Heading
        AnimateOnScroll {
            Div(attrs = PlaygroundHeadingContainerStyle.toModifier().toAttrs()) {
                Div(attrs = HeadlineTextStyle.toAttrs()) {
                    Span(attrs = GradientTextStyle.toModifier().toAttrs()) {
                        Text("Playground")
                    }
                }
                Div(attrs = SubheadlineTextStyle.toAttrs()) {
                    SpanText(
                        "Paste your SVG or AVG code and see the generated Kotlin ImageVector instantly.",
                    )
                }
            }
        }

        // Sample buttons
        SampleButtons(
            sampleNames = samples.map { it.name },
            selectedSample = selectedSample,
            onSelect = { index ->
                selectedSample = index
                inputCode = samples[index].svgCode
            },
        )

        // Editor panel
        Div(attrs = EditorPanelStyle.toModifier().toAttrs()) {
            PlaygroundToolbar(inputMode = inputMode, extension, onInputModeChange = { inputMode = it })
            DesktopPanels(
                inputCode = inputCode,
                svgCode = currentSample.svgCode,
                kotlinCode = currentSample.kotlinCode,
                onInputChange = { inputCode = it },
            )
            MobilePanels(
                activePanel = activePanel,
                onPanelSelect = { activePanel = it },
                inputCode = inputCode,
                svgCode = currentSample.svgCode,
                kotlinCode = currentSample.kotlinCode,
                onInputChange = { inputCode = it },
            )
        }

        // Options
        OptionsSection()
    }
}

@Composable
private fun DesktopPanels(inputCode: String, svgCode: String, kotlinCode: String, onInputChange: (String) -> Unit) {
    Div(
        attrs = DesktopPanelsStyle.toModifier()
            .displayIfAtLeast(Breakpoint.MD)
            .toAttrs(),
    ) {
        InputPanel(inputCode, onInputChange)
        PreviewPanel(svgCode)
        OutputPanel(kotlinCode)
    }
}

@Composable
private fun MobilePanels(
    activePanel: Int,
    onPanelSelect: (Int) -> Unit,
    inputCode: String,
    svgCode: String,
    kotlinCode: String,
    onInputChange: (String) -> Unit = {},
) {
    val mobileTabs = listOf("Input", "Preview", "Output")
    Div(attrs = Modifier.displayUntil(Breakpoint.MD).toAttrs()) {
        // Tab bar
        Div(attrs = MobileTabBarStyle.toModifier().toAttrs()) {
            mobileTabs.forEachIndexed { index, tab ->
                val style = if (index == activePanel) MobileTabActiveStyle else MobileTabStyle
                Div(
                    attrs = style.toModifier()
                        .onClick { onPanelSelect(index) }
                        .toAttrs(),
                ) {
                    SpanText(tab)
                }
            }
        }
        // Active panel content
        Div(attrs = MobilePanelContentStyle.toModifier().toAttrs()) {
            when (activePanel) {
                0 -> InputPanel(inputCode, onInputChange)
                1 -> PreviewPanel(svgCode)
                2 -> OutputPanel(kotlinCode)
            }
        }
    }
}
