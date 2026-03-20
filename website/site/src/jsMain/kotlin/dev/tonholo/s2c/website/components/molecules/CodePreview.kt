package dev.tonholo.s2c.website.components.molecules

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.WhiteSpace
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.borderRight
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.gridTemplateColumns
import com.varabyte.kobweb.compose.ui.modifiers.letterSpacing
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.whiteSpace
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.SiteTheme
import dev.tonholo.s2c.website.components.atoms.Badge
import dev.tonholo.s2c.website.components.atoms.SquaredBadge
import dev.tonholo.s2c.website.components.atoms.SvgPreview
import dev.tonholo.s2c.website.components.atoms.WindowChrome
import dev.tonholo.s2c.website.shiki.ShikiCodeBlock
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.fr
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

// region Styles

val CodePreviewPanelStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .maxWidth(64.cssRem)
            .borderRadius(1.cssRem)
            .border(1.px, LineStyle.Solid, colorMode.toSitePalette().borderStrong)
            .overflow(Overflow.Hidden)
            .styleModifier {
                property("box-shadow", "0 0 80px rgba(127, 82, 255, 0.12), 0 32px 64px rgba(0,0,0,0.5)")
            }
    }
}

val CodePreviewGridStyle = CssStyle {
    base {
        Modifier
            .display(DisplayStyle.Grid)
            .gridTemplateColumns { size(1.fr) }
    }
    Breakpoint.MD {
        Modifier.gridTemplateColumns {
            size(1.fr)
            size(1.fr)
        }
    }
}

val CodePanelLabelStyle = CssStyle.base {
    Modifier
        .fontFamily("JetBrains Mono", "monospace")
        .fontSize(0.7.cssRem)
        .fontWeight(FontWeight.Medium)
        .letterSpacing(0.05.em)
        .padding(topBottom = 0.5.cssRem, leftRight = 1.cssRem)
        .color(colorMode.toSitePalette().muted)
}

val CodePanelStyle = CssStyle.base {
    Modifier
        .fillMaxSize()
        .fontFamily("JetBrains Mono", "monospace")
        .fontSize(0.7.cssRem)
        .lineHeight(value = 1.6)
        .overflow(Overflow.Auto)
        .margin(0.px)
        .whiteSpace(WhiteSpace.Pre)
}

val CodePreviewBadgeStyle = CssStyle.base {
    Modifier
        .fontSize(0.65.cssRem)
        .fontWeight(FontWeight.Bold)
}

// endregion

// region Constants

@Suppress("MaxLineLength")
private const val HEART_SVG_CODE = """<svg viewBox="0 0 24 24">
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
</svg>"""

@Suppress("MaxLineLength")
private const val HEART_KOTLIN_CODE = """val HeartIcon: ImageVector
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

private var _heartIcon: ImageVector? = null"""

// endregion

@Composable
fun CodePreview() {
    val palette = ColorMode.current.toSitePalette()
    Div(attrs = CodePreviewPanelStyle.toModifier().toAttrs()) {
        WindowChrome(title = "svg-to-compose transform")
        Div(attrs = CodePreviewGridStyle.toModifier().toAttrs()) {
            // Left panel - SVG input
            Div(
                attrs = Modifier
                    .backgroundColor(palette.surfaceAlt)
                    .borderRight(width = 1.px, style = LineStyle.Solid, color = palette.borderStrong)
                    .toAttrs(),
            ) {
                CodePreviewPanel(
                    filename = "heart.svg",
                    preview = {
                        SvgPreview(svgCode = HEART_SVG_CODE)
                    },
                    badgeText = "INPUT",
                    badgeColor = SiteTheme.palette.brand.red,
                    code = HEART_SVG_CODE,
                    codeLanguage = "xml",
                )
            }
            // Right panel - Kotlin output
            Div(attrs = Modifier.backgroundColor(palette.surface).toAttrs()) {
                CodePreviewPanel(
                    filename = "HeartIcon.kt",
                    badgeText = "OUTPUT",
                    badgeColor = SiteTheme.palette.brand.violet,
                    code = HEART_KOTLIN_CODE,
                    codeLanguage = "kotlin",
                )
            }
        }
    }
}

@Composable
private fun CodePreviewPanel(
    filename: String,
    preview: (@Composable () -> Unit)? = null,
    badgeText: String,
    badgeColor: Color,
    code: String,
    codeLanguage: String,
) {
    Div(attrs = Modifier.fillMaxSize().backgroundColor(SiteTheme.palette.surface).toAttrs()) {
        Div(
            attrs = CodePanelLabelStyle.toModifier()
                .display(DisplayStyle.Flex)
                .alignItems(AlignItems.Center)
                .gap(0.5.cssRem)
                .toAttrs(),
        ) {
            Badge(
                color = badgeColor,
                variant = SquaredBadge,
                modifier = CodePreviewBadgeStyle.toModifier(),
            ) {
                Text(badgeText)
            }
            SpanText(filename)
        }
        preview?.invoke()
        ShikiCodeBlock(
            language = codeLanguage,
            code = code,
            modifier = CodePanelStyle.toModifier(),
        )
    }
}
