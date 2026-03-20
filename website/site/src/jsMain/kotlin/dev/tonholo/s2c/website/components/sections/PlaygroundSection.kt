package dev.tonholo.s2c.website.components.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.left
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.scale
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaCode
import com.varabyte.kobweb.silk.components.icons.fa.FaCopy
import com.varabyte.kobweb.silk.components.icons.fa.FaEye
import com.varabyte.kobweb.silk.components.icons.fa.FaGear
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.breakpoint.displayIfAtLeast
import com.varabyte.kobweb.silk.style.breakpoint.displayUntil
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.GradientTextStyle
import dev.tonholo.s2c.website.HeadlineTextStyle
import dev.tonholo.s2c.website.SubheadlineTextStyle
import dev.tonholo.s2c.website.components.widgets.Badge
import dev.tonholo.s2c.website.components.widgets.SectionContainer
import dev.tonholo.s2c.website.components.widgets.Toolbar
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Pre
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.TextArea

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

val SampleButtonRowStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .styleModifier {
            property("flex-wrap", "wrap")
            property("justify-content", "center")
        }
        .gap(0.5.cssRem)
        .margin(bottom = 1.5.cssRem)
}

val SampleButtonStyle = CssStyle {
    base {
        val sitePalette = colorMode.toSitePalette()
        Modifier
            .backgroundColor(sitePalette.brand.purpleDeep.darkened(byPercent = .8f))
            .border(1.px, LineStyle.Solid, sitePalette.border)
            .borderRadius(0.625.cssRem)
            .padding(topBottom = 0.25.cssRem, leftRight = 0.75.cssRem)
            .fontSize(0.75.cssRem)
            .fontFamily("JetBrains Mono", "monospace")
            .cursor(Cursor.Pointer)
            .color(sitePalette.muted)
            .styleModifier {
                property("transition", "all 0.2s ease")
            }
    }
    cssRule(".active") {
        val sitePalette = colorMode.toSitePalette()
        Modifier
            .backgroundColor(sitePalette.brand.purple.darkened(byPercent = .6f))
    }
    cssRule(":hover") {
        Modifier.scale(105.percent)
    }
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

val InputPanelStyle = CssStyle.base {
    Modifier
        .backgroundColor(colorMode.toSitePalette().surfaceAlt)
        .styleModifier { property("border-right", "1px solid ${colorMode.toSitePalette().borderStrong}") }
        .display(DisplayStyle.Flex)
        .styleModifier { property("flex-direction", "column") }
}

val PanelHeaderStyle = CssStyle.base {
    Modifier
        .fontFamily("JetBrains Mono", "monospace")
        .fontSize(0.75.cssRem)
        .color(colorMode.toSitePalette().muted)
        .padding(topBottom = 0.5.cssRem, leftRight = 1.cssRem)
        .backgroundColor(colorMode.toSitePalette().surfaceHeader)
}

val EditorTextareaStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .styleModifier { property("flex", "1") }
        .fontFamily("JetBrains Mono", "monospace")
        .fontSize(0.75.cssRem)
        .lineHeight(1.6)
        .padding(1.cssRem)
        .backgroundColor(Colors.Transparent)
        .color(colorMode.toSitePalette().muted)
        .border(0.px, LineStyle.None, Colors.Transparent)
        .styleModifier {
            property("outline", "none")
            property("resize", "none")
            property("caret-color", "#7F52FF")
        }
}

val PreviewPanelStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .styleModifier { property("flex-direction", "column") }
        .styleModifier { property("border-right", "1px solid ${colorMode.toSitePalette().borderStrong}") }
}

val PreviewHalfStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .styleModifier { property("flex-direction", "column") }
        .styleModifier { property("flex", "1") }
        .padding(1.cssRem)
        .overflow(Overflow.Auto)
}

val OutputPanelStyle = CssStyle.base {
    Modifier
        .backgroundColor(colorMode.toSitePalette().surface)
        .display(DisplayStyle.Flex)
        .styleModifier { property("flex-direction", "column") }
}

val OutputCodeStyle = CssStyle.base {
    Modifier
        .fontFamily("JetBrains Mono", "monospace")
        .fontSize(0.7.cssRem)
        .lineHeight(1.6)
        .padding(1.cssRem)
        .overflow(Overflow.Auto)
        .margin(0.px)
        .styleModifier { property("white-space", "pre") }
        .styleModifier { property("flex", "1") }
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

val OptionsContainerStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .margin(top = 1.cssRem)
        .borderRadius(0.75.cssRem)
        .border(1.px, LineStyle.Solid, colorMode.toSitePalette().borderStrong)
        .overflow(Overflow.Hidden)
}

val OptionsHeaderStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .display(DisplayStyle.Flex)
        .alignItems(AlignItems.Center)
        .styleModifier { property("justify-content", "space-between") }
        .padding(topBottom = 0.75.cssRem, leftRight = 1.cssRem)
        .backgroundColor(colorMode.toSitePalette().surfaceHeader)
        .cursor(Cursor.Pointer)
        .border(0.px, LineStyle.None, Colors.Transparent)
        .fontWeight(FontWeight.Medium)
        .fontSize(0.875.cssRem)
}

val OptionsContentStyle = CssStyle {
    base {
        Modifier
            .padding(1.cssRem)
            .display(DisplayStyle.Grid)
            .gap(1.cssRem)
            .styleModifier { property("grid-template-columns", "1fr") }
    }
    Breakpoint.MD {
        Modifier.styleModifier { property("grid-template-columns", "1fr 1fr 1fr") }
    }
}

val OptionLabelStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .alignItems(AlignItems.Center)
        .gap(0.5.cssRem)
        .fontSize(0.8.cssRem)
        .color(colorMode.toSitePalette().muted)
        .cursor(Cursor.Pointer)
}

val OptionInputStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .padding(topBottom = 0.375.cssRem, leftRight = 0.75.cssRem)
        .borderRadius(0.375.cssRem)
        .border(1.px, LineStyle.Solid, colorMode.toSitePalette().border)
        .backgroundColor(colorMode.toSitePalette().surfaceAlt)
        .color(colorMode.toSitePalette().muted)
        .fontSize(0.8.cssRem)
        .fontFamily("JetBrains Mono", "monospace")
        .styleModifier { property("outline", "none") }
}

val PreviewSvgContainerStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .styleModifier {
            property("justify-content", "center")
            property("align-items", "center")
        }
        .styleModifier { property("flex", "1") }
        .padding(1.cssRem)
}

// endregion

@Composable
fun PlaygroundSection() {
    SectionContainer(id = "playground") {
        var selectedSample by remember { mutableStateOf(0) }
        var inputCode by remember { mutableStateOf(samples[0].svgCode) }
        var activePanel by remember { mutableStateOf(0) }
        var inputMode by remember { mutableStateOf("paste") }
        var extension by remember { mutableStateOf("svg") }

        val currentSample = samples[selectedSample]

        // Heading
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

        // Sample buttons
        SampleButtons(
            selectedSample = selectedSample,
            onSelect = { index ->
                selectedSample = index
                inputCode = samples[index].svgCode
            },
        )

        // Editor panel
        Div(attrs = EditorPanelStyle.toModifier().toAttrs()) {
            Toolbar(inputMode = inputMode, extension, onInputModeChange = { inputMode = it })
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
private fun SampleButtons(selectedSample: Int, onSelect: (Int) -> Unit) {
    Div(
        attrs = SampleButtonRowStyle.toModifier()
            .alignItems(AlignItems.Center)
            .toAttrs(),
    ) {
        SpanText(
            "Samples:",
            modifier = Modifier
                .color(ColorMode.current.toSitePalette().mutedStrong)
                .fontSize(0.8.cssRem)
                .styleModifier { property("align-self", "center") },
        )
        samples.forEachIndexed { index, sample ->
            Button(
                attrs = SampleButtonStyle.toModifier()
                    .onClick { onSelect(index) }
                    .toAttrs {
                        if (index == selectedSample) {
                            classes("active")
                        }
                    },
            ) {
                SpanText(sample.name)
            }
        }
    }
}

@Composable
private fun InputPanel(inputCode: String, onInputChange: (String) -> Unit) {
    Div(attrs = InputPanelStyle.toModifier().toAttrs()) {
        Div(
            attrs = PanelHeaderStyle.toModifier()
                .display(DisplayStyle.Flex).alignItems(AlignItems.Center).gap(0.4.cssRem)
                .toAttrs(),
        ) {
            FaCode()
            SpanText("input.svg")
        }
        TextArea(
            value = inputCode,
            attrs = EditorTextareaStyle.toModifier().toAttrs {
                placeholder("Paste your SVG code here...")
                onInput { onInputChange(it.value) }
            },
        )
    }
}

@Composable
fun PreviewPanel(svgCode: String) {
    val palette = ColorMode.current.toSitePalette()
    Div(attrs = PreviewPanelStyle.toModifier().toAttrs()) {
        // Top half: Source Preview
        Div(
            attrs = Modifier
                .display(DisplayStyle.Flex)
                .styleModifier { property("flex-direction", "column") }
                .styleModifier { property("flex", "1") }
                .toAttrs(),
        ) {
            Div(
                attrs = PanelHeaderStyle.toModifier()
                    .display(DisplayStyle.Flex).alignItems(AlignItems.Center).gap(0.4.cssRem)
                    .toAttrs(),
            ) {
                FaEye()
                SpanText("Source Preview")
            }
            Div(
                attrs = PreviewSvgContainerStyle.toModifier()
                    .backgroundColor(palette.surfaceAlt)
                    .toAttrs(),
            ) {
                Box(
                    modifier = Modifier
                        .margin(2.cssRem)
                        .background()
                ) {
                    DisposableEffect(svgCode) {
                        console.log("SVG code changed, updating preview.")
                        scopeElement.innerHTML = svgCode
                        onDispose { }
                    }
                }
            }
        }

        // Divider
        Div(
            attrs = Modifier
                .fillMaxWidth()
                .height(1.px)
                .backgroundColor(palette.borderStrong)
                .toAttrs(),
        )

        // Bottom half: Compose Preview
        Div(
            attrs = Modifier
                .display(DisplayStyle.Flex)
                .styleModifier { property("flex-direction", "column") }
                .styleModifier { property("flex", "1") }
                .toAttrs(),
        ) {
            Div(
                attrs = PanelHeaderStyle.toModifier()
                    .display(DisplayStyle.Flex).alignItems(AlignItems.Center).gap(0.4.cssRem)
                    .toAttrs(),
            ) {
                SpanText(
                    "\u25CF",
                    modifier = Modifier.color(Color.rgb(0x7F52FF))
                        .fontSize(0.5.cssRem),
                )
                SpanText("Compose Preview")
            }
            Div(
                attrs = PreviewSvgContainerStyle.toModifier()
                    .backgroundColor(palette.surface)
                    .toAttrs(),
            ) {
                Div(
                    attrs = Modifier
                        .display(DisplayStyle.Flex)
                        .styleModifier { property("flex-direction", "column") }
                        .alignItems(AlignItems.Center)
                        .gap(0.5.cssRem)
                        .toAttrs(),
                ) {
                    Div(
                        attrs = Modifier
                            .width(4.cssRem)
                            .height(4.cssRem)
                            .toAttrs {
                                ref { element ->
                                    element.innerHTML = svgCode
                                    onDispose { }
                                }
                            },
                    )
                    Badge(text = "@Composable", color = Color.rgb(0x7F52FF))
                    SpanText(
                        "ImageVector renders identically",
                        modifier = Modifier
                            .fontSize(0.65.cssRem)
                            .color(palette.muted),
                    )
                }
            }
        }
    }
}

@Composable
private fun OutputPanel(kotlinCode: String) {
    Div(attrs = OutputPanelStyle.toModifier().toAttrs()) {
        Div(
            attrs = PanelHeaderStyle.toModifier()
                .display(DisplayStyle.Flex)
                .alignItems(AlignItems.Center)
                .styleModifier { property("justify-content", "space-between") }
                .toAttrs(),
        ) {
            Div(
                attrs = Modifier
                    .display(DisplayStyle.Flex).alignItems(AlignItems.Center).gap(0.4.cssRem)
                    .toAttrs(),
            ) {
                FaCode()
                SpanText("MyIcon.kt")
            }
            Div(
                attrs = Modifier
                    .display(DisplayStyle.Flex).alignItems(AlignItems.Center).gap(0.3.cssRem)
                    .fontSize(0.65.cssRem)
                    .cursor(Cursor.Pointer)
                    .toAttrs(),
            ) {
                FaCopy(size = IconSize.LG)
                SpanText("Copy")
            }
        }
        Pre(attrs = OutputCodeStyle.toModifier().toAttrs()) {
            Text(kotlinCode)
        }
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
    onInputChange: (String) -> Unit,
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

@Composable
private fun ToggleSwitch() {
    val palette = ColorMode.current.toSitePalette()
    Div(
        attrs = Modifier
            .width(2.25.cssRem)
            .height(1.125.cssRem)
            .borderRadius(9999.px)
            .backgroundColor(palette.brand.purple.toRgb().copyf(alpha = 0.2f))
            .cursor(Cursor.Pointer)
            .position(Position.Relative)
            .styleModifier { property("transition", "all 0.2s ease") }
            .toAttrs(),
    ) {
        Div(
            attrs = Modifier
                .size(0.875.cssRem)
                .borderRadius(50.percent)
                .backgroundColor(Colors.White)
                .position(Position.Absolute)
                .top(50.percent)
                .left(2.px)
                .styleModifier { property("transform", "translateY(-50%)") }
                .toAttrs(),
        )
    }
}

@Composable
private fun OptionsSection() {
    var optionsExpanded by remember { mutableStateOf(false) }
    val palette = ColorMode.current.toSitePalette()
    val toggleOptions = listOf("Optimize", "Minify", "KMP Compatible", "No Preview", "Make Internal")

    Div(attrs = OptionsContainerStyle.toModifier().toAttrs()) {
        Div(
            attrs = OptionsHeaderStyle.toModifier()
                .color(palette.muted)
                .display(DisplayStyle.Flex)
                .alignItems(AlignItems.Center)
                .gap(0.5.cssRem)
                .onClick { optionsExpanded = !optionsExpanded }
                .toAttrs(),
        ) {
            FaGear(size = IconSize.SM)
            SpanText("Options")
            Div(attrs = Modifier.styleModifier { property("margin-left", "auto") }.toAttrs()) {
                SpanText(if (optionsExpanded) "\u25B2" else "\u25BC", modifier = Modifier.fontSize(0.6.cssRem))
            }
        }

        if (optionsExpanded) {
            Div(
                attrs = Modifier
                    .padding(1.cssRem)
                    .display(DisplayStyle.Flex)
                    .styleModifier { property("flex-direction", "column") }
                    .gap(1.cssRem)
                    .toAttrs(),
            ) {
                // Toggle switches row
                Div(
                    attrs = Modifier
                        .display(DisplayStyle.Flex)
                        .styleModifier { property("flex-wrap", "wrap") }
                        .gap(1.5.cssRem)
                        .toAttrs(),
                ) {
                    toggleOptions.forEach { option ->
                        Div(
                            attrs = Modifier
                                .display(DisplayStyle.Flex)
                                .alignItems(AlignItems.Center)
                                .gap(0.5.cssRem)
                                .toAttrs(),
                        ) {
                            ToggleSwitch()
                            SpanText(
                                option,
                                modifier = Modifier.fontSize(0.8.cssRem).color(palette.muted),
                            )
                        }
                    }
                }
                // Text inputs row
                Div(
                    attrs = Modifier
                        .display(DisplayStyle.Grid)
                        .styleModifier { property("grid-template-columns", "1fr 1fr 1fr") }
                        .gap(1.cssRem)
                        .toAttrs(),
                ) {
                    listOf(
                        "Package Name" to "com.example.icons",
                        "Theme" to "com.example.theme.AppTheme",
                        "Receiver Type" to "Icons.Filled (optional)",
                    ).forEach { (label, placeholderText) ->
                        Div {
                            SpanText(
                                label,
                                modifier = Modifier
                                    .display(DisplayStyle.Block)
                                    .fontSize(0.75.cssRem)
                                    .color(palette.muted)
                                    .margin(bottom = 0.25.cssRem),
                            )
                            org.jetbrains.compose.web.dom.TextInput(
                                value = "",
                                attrs = OptionInputStyle.toModifier().toAttrs {
                                    placeholder(placeholderText)
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}
