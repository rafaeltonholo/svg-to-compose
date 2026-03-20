package dev.tonholo.s2c.website.components.molecules

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.UserSelect
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.shiki.ShikiCodeBlock
import dev.tonholo.s2c.website.toSitePalette
import kotlinx.browser.window
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Code
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Pre
import org.jetbrains.compose.web.dom.Text

private const val COPY_FEEDBACK_DURATION_MS = 2000L

val CodeBlockContainerStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .fillMaxWidth()
        .backgroundColor(palette.surfaceAlt)
        .border(1.px, LineStyle.Solid, palette.borderStrong)
        .borderRadius(0.75.cssRem)
        .overflow(Overflow.Hidden)
}

val CodeBlockHeaderStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .fillMaxWidth()
        .backgroundColor(palette.surfaceHeader)
        .padding(topBottom = 0.5.cssRem, leftRight = 1.cssRem)
}

val CopyButtonStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .padding(topBottom = 0.25.cssRem, leftRight = 0.5.cssRem)
        .borderRadius(0.375.cssRem)
        .border(1.px, LineStyle.Solid, palette.border)
        .backgroundColor(palette.surfaceAlt)
        .color(palette.muted)
        .cursor(Cursor.Pointer)
        .fontSize(0.7.cssRem)
        .fontFamily("Inter", "sans-serif")
        .styleModifier {
            property("transition", "all 0.2s ease")
        }
}

val LineNumberStyle = CssStyle.base {
    Modifier
        .color(colorMode.toSitePalette().muted)
        .userSelect(UserSelect.None)
        .textAlign(TextAlign.End)
        .padding(right = 1.cssRem)
        .minWidth(2.cssRem)
        .display(DisplayStyle.InlineBlock)
}

@Composable
fun CodeBlock(
    code: String,
    language: String = "",
    filename: String? = null,
    showCopyButton: Boolean = true,
) {
    var copied by remember { mutableStateOf(false) }

    LaunchedEffect(copied) {
        if (copied) {
            delay(COPY_FEEDBACK_DURATION_MS)
            copied = false
        }
    }

    Div(attrs = CodeBlockContainerStyle.toModifier().toAttrs()) {
        if (filename != null) {
            Row(
                modifier = CodeBlockHeaderStyle.toModifier(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SpanText(
                    filename,
                    modifier = Modifier
                        .fontFamily("JetBrains Mono", "monospace")
                        .fontSize(0.75.cssRem)
                        .color(ColorMode.current.toSitePalette().muted)
                )
                Spacer()
                if (showCopyButton) {
                    CopyButton(code = code, copied = copied) { copied = true }
                }
            }
        }
        Div(
            attrs = Modifier
                .position(Position.Relative)
                .toAttrs()
        ) {
            if (filename == null && showCopyButton) {
                Div(
                    attrs = Modifier
                        .position(Position.Absolute)
                        .top(0.5.cssRem)
                        .right(0.5.cssRem)
                        .zIndex(1)
                        .toAttrs()
                ) {
                    CopyButton(code = code, copied = copied) { copied = true }
                }
            }
            if (language.isNotEmpty()) {
                ShikiCodeBlock(
                    language = language,
                    code = code,
                    modifier = Modifier.margin(0.px),
                )
            } else {
                Pre(
                    attrs = Modifier
                        .margin(0.px)
                        .padding(1.cssRem)
                        .overflow { x(Overflow.Auto) }
                        .toAttrs()
                ) {
                    Code(
                        attrs = Modifier
                            .fontFamily("JetBrains Mono", "monospace")
                            .fontSize(0.75.cssRem)
                            .lineHeight(1.6)
                            .toAttrs()
                    ) {
                        val lines = code.split("\n")
                        lines.forEachIndexed { index, line ->
                            Div(
                                attrs = Modifier
                                    .display(DisplayStyle.Flex)
                                    .toAttrs()
                            ) {
                                SpanText(
                                    "${index + 1}",
                                    modifier = LineNumberStyle.toModifier(),
                                )
                                Text(line)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CopyButton(code: String, copied: Boolean, onCopy: () -> Unit) {
    Div(
        attrs = CopyButtonStyle.toModifier()
            .onClick {
                window.navigator.clipboard.writeText(code)
                onCopy()
            }
            .toAttrs()
    ) {
        SpanText(if (copied) "Copied!" else "Copy")
    }
}
