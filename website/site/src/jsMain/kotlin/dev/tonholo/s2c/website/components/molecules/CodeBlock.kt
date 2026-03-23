package dev.tonholo.s2c.website.components.molecules

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.css.UserSelect
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.right
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.userSelect
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.shiki.ShikiCodeBlock
import dev.tonholo.s2c.website.theme.typography.FontFamilies
import dev.tonholo.s2c.website.theme.toSitePalette
import kotlinx.browser.window
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Code
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Pre
import org.jetbrains.compose.web.dom.Text

private const val COPY_FEEDBACK_DURATION_MS = 2000L
private const val CODE_LINE_HEIGHT = 1.6

val CodeBlockContainerStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .fillMaxWidth()
        .backgroundColor(palette.surfaceVariant)
        .border(1.px, LineStyle.Solid, palette.outlineVariant)
        .overflow(Overflow.Hidden)
}

val CodeBlockHeaderStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .fillMaxWidth()
        .backgroundColor(palette.surfaceVariant)
        .padding(topBottom = 0.5.cssRem, leftRight = 1.cssRem)
}

val CopyButtonStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .padding(topBottom = 0.25.cssRem, leftRight = 0.5.cssRem)
        .borderRadius(0.375.cssRem)
        .border(1.px, LineStyle.Solid, palette.outline)
        .backgroundColor(palette.surfaceVariant)
        .color(palette.onSurfaceVariant)
        .cursor(Cursor.Pointer)
        .fontSize(0.7.cssRem)
        .fontFamily(values = FontFamilies.sans)
        .transition(
            Transition.of("background-color", duration = 200.ms, timingFunction = TransitionTimingFunction.Ease),
            Transition.of("color", duration = 200.ms, timingFunction = TransitionTimingFunction.Ease),
            Transition.of("border-color", duration = 200.ms, timingFunction = TransitionTimingFunction.Ease),
        )
}

val LineNumberStyle = CssStyle.base {
    Modifier
        .color(colorMode.toSitePalette().onSurfaceVariant)
        .userSelect(UserSelect.None)
        .textAlign(TextAlign.End)
        .padding(right = 1.cssRem)
        .minWidth(2.cssRem)
        .display(DisplayStyle.InlineBlock)
}

@Composable
fun CodeBlock(
    code: String,
    modifier: Modifier = Modifier,
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

    Div(attrs = CodeBlockContainerStyle.toModifier().then(modifier).toAttrs()) {
        if (filename != null) {
            CodeBlockHeader(
                filename = filename,
                code = code,
                copied = copied,
                showCopyButton = showCopyButton,
                onCopy = { copied = true },
            )
        }
        CodeBlockBody(
            code = code,
            language = language,
            hasHeader = filename != null,
            showCopyButton = showCopyButton,
            copied = copied,
            onCopy = { copied = true },
        )
    }
}

@Composable
private fun CodeBlockHeader(
    filename: String,
    code: String,
    copied: Boolean,
    showCopyButton: Boolean,
    onCopy: () -> Unit,
) {
    Row(
        modifier = CodeBlockHeaderStyle.toModifier(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SpanText(
            filename,
            modifier = Modifier
                .fontFamily(values = FontFamilies.mono)
                .fontSize(0.75.cssRem)
                .color(ColorMode.current.toSitePalette().onSurfaceVariant),
        )
        Spacer()
        if (showCopyButton) {
            CopyButton(code = code, copied = copied, onCopy = onCopy)
        }
    }
}

@Composable
private fun CodeBlockBody(
    code: String,
    language: String,
    hasHeader: Boolean,
    showCopyButton: Boolean,
    copied: Boolean,
    onCopy: () -> Unit,
) {
    Div(
        attrs = Modifier
            .position(Position.Relative)
            .toAttrs(),
    ) {
        if (!hasHeader && showCopyButton) {
            Div(
                attrs = Modifier
                    .position(Position.Absolute)
                    .top(0.5.cssRem)
                    .right(0.5.cssRem)
                    .zIndex(1)
                    .toAttrs(),
            ) {
                CopyButton(code = code, copied = copied, onCopy = onCopy)
            }
        }
        if (language.isNotEmpty()) {
            ShikiCodeBlock(
                language = language,
                code = code,
                modifier = Modifier.margin(0.px),
            )
        } else {
            PlainCodeBlock(code = code)
        }
    }
}

@Composable
private fun PlainCodeBlock(code: String) {
    Pre(
        attrs = Modifier
            .margin(0.px)
            .padding(1.cssRem)
            .overflow { x(Overflow.Auto) }
            .toAttrs(),
    ) {
        Code(
            attrs = Modifier
                .fontFamily(values = FontFamilies.mono)
                .fontSize(0.75.cssRem)
                .lineHeight(CODE_LINE_HEIGHT)
                .toAttrs(),
        ) {
            val lines = code.split("\n")
            lines.forEachIndexed { index, line ->
                Div(
                    attrs = Modifier
                        .display(DisplayStyle.Flex)
                        .toAttrs(),
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

@Composable
private fun CopyButton(code: String, copied: Boolean, onCopy: () -> Unit) {
    Div(
        attrs = CopyButtonStyle.toModifier()
            .onClick {
                window.navigator.clipboard.writeText(code)
                onCopy()
            }
            .toAttrs(),
    ) {
        SpanText(if (copied) "Copied!" else "Copy")
    }
}
