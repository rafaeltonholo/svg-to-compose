package dev.tonholo.s2c.website.shiki

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.SitePalettes
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

internal const val LIGHT_THEME = "one-light"
internal const val DARK_THEME = "one-dark-pro"

// Original theme backgrounds → site palette backgrounds
internal const val ONE_DARK_PRO_BACKGROUND = "#282c34"
internal const val ONE_LIGHT_BACKGROUND = "#FAFAFA"

val ShikiCodeBlockStyle = CssStyle {
    base {
        Modifier
            .fontFamily("JetBrains Mono", "monospace")
            .fontSize(value = 0.75.cssRem)
            .lineHeight(value = 1.6)
            .overflow { x(Overflow.Auto) }
    }
    cssRule("pre") {
        Modifier
            .fillMaxSize()
            .padding(top = 2.em, left = 1.em, right = 2.em, bottom = 2.em)
            .margin(0.px)
            .overflow(Overflow.Auto)
    }
    cssRule("code") {
        Modifier
            .styleModifier {
                property("counter-reset", "step")
                property("counter-increment", "step 0")
            }
    }
    cssRule("code .line::before") {
        Modifier
            .styleModifier {
                property("content", "counter(step)")
                property("counter-increment", "step")
            }
            .width(1.cssRem)
            .margin(right = 1.25.cssRem)
            .display(DisplayStyle.InlineBlock)
            .textAlign(TextAlign.Right)
            .color(colorMode.toSitePalette().onSurfaceVariant)
    }
}

@Composable
fun ShikiCodeBlock(
    language: String,
    code: String,
    modifier: Modifier = Modifier,
) {
    val colorMode = ColorMode.current
    var parsedCode by remember { mutableStateOf("") }

    LaunchedEffect(code, language, colorMode) {
        Shiki.initialize()
        val options = codeToHtmlOptions(
            lang = language,
            isDark = colorMode.isDark,
            lightTheme = LIGHT_THEME,
            darkTheme = DARK_THEME,
            colorReplacements = mapOf(
                ONE_DARK_PRO_BACKGROUND to SitePalettes.dark.surface.toHexString(),
                ONE_LIGHT_BACKGROUND to SitePalettes.dark.surface.toHexString(),
            ),
        )
        parsedCode = Shiki.instance.codeToHtml(code, options)
    }

    Div(
        attrs = ShikiCodeBlockStyle.toModifier().then(modifier).toAttrs(),
    ) {
        DisposableEffect(parsedCode) {
            scopeElement.innerHTML = parsedCode
            onDispose { }
        }
    }
}

private const val MIN_HEX_COLOR_LENGTH = 6
private const val MAX_COLOR_VALUE = 0xFFFFFF

private val format = HexFormat {
    upperCase = true
    number {
        prefix = "#"
        removeLeadingZeros = true
        minLength = MIN_HEX_COLOR_LENGTH
    }
}

private fun Color.toHexString(): String =
    (toRgb().value and MAX_COLOR_VALUE).toHexString(format)
