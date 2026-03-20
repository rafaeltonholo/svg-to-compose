package dev.tonholo.s2c.website.components.molecules.playground

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.DisposableEffectResult
import androidx.compose.runtime.DisposableEffectScope
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Outline
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.PointerEvents
import com.varabyte.kobweb.compose.css.ScrollbarWidth
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.WhiteSpace
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRight
import com.varabyte.kobweb.compose.ui.modifiers.bottom
import com.varabyte.kobweb.compose.ui.modifiers.caretColor
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flex
import com.varabyte.kobweb.compose.ui.modifiers.flexDirection
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.left
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.outline
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.pointerEvents
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.right
import com.varabyte.kobweb.compose.ui.modifiers.scrollbarWidth
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.whiteSpace
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaCode
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.components.molecules.PanelHeaderStyle
import dev.tonholo.s2c.website.shiki.DARK_THEME
import dev.tonholo.s2c.website.shiki.LIGHT_THEME
import dev.tonholo.s2c.website.shiki.ONE_DARK_PRO_BACKGROUND
import dev.tonholo.s2c.website.shiki.ONE_LIGHT_BACKGROUND
import dev.tonholo.s2c.website.shiki.Shiki
import dev.tonholo.s2c.website.shiki.codeToHtmlOptions
import dev.tonholo.s2c.website.theme.typography.FontFamilies
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.TextArea
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLTextAreaElement
import org.w3c.dom.events.Event

private const val HIGHLIGHT_LANGUAGE = "xml"
private const val TAB_INDENT = "  "

val InputPanelStyle = CssStyle.base {
    Modifier
        .backgroundColor(colorMode.toSitePalette().surfaceVariant)
        .borderRight(width = 1.px, style = LineStyle.Solid, color = colorMode.toSitePalette().outlineVariant)
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .flex(1)
        // min-height: 0 prevents flex children from overflowing their
        // grid/flex parent — a common CSS gotcha with nested flex layouts.
        .minHeight(0.px)
}

val EditorContainerStyle = CssStyle.base {
    Modifier
        .position(Position.Relative)
        .fillMaxWidth()
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .flex(1)
        .minHeight(0.px)
        .overflow(Overflow.Hidden)
        .backgroundColor(colorMode.toSitePalette().surfaceVariant)
}

val EditorBackdropStyle = CssStyle {
    base {
        Modifier
            .position(Position.Absolute)
            .top(value = 0.px)
            .left(value = 0.px)
            .right(value = 0.px)
            .bottom(value = 0.px)
            .fontFamily(values = FontFamilies.mono)
            .fontSize(value = 0.75.cssRem)
            .lineHeight(value = 1.6)
            .padding(all = 1.cssRem)
            .margin(all = 0.px)
            .overflow(Overflow.Auto)
            .backgroundColor(Colors.Transparent)
            .pointerEvents(PointerEvents.None)
            .scrollbarWidth(ScrollbarWidth.None)
            .styleModifier {
                property("-ms-overflow-style", "none")
            }
    }
    cssRule("::-webkit-scrollbar") {
        Modifier.display(DisplayStyle.None)
    }
    cssRule(" pre") {
        Modifier
            .margin(all = 0.px)
            .padding(all = 0.px)
            .backgroundColor(Colors.Transparent)
            .minHeight(100.percent)
    }
    // Line numbers via CSS counters — same approach as ShikiCodeBlock
    cssRule(" code") {
        Modifier
            .styleModifier {
                property("counter-reset", "step")
                property("counter-increment", "step 0")
            }
    }
    cssRule(" code .line::before") {
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

val EditorTextareaStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .flex(1)
            .position(Position.Relative)
            .zIndex(1)
            .fontFamily(values = FontFamilies.mono)
            .fontSize(0.75.cssRem)
            .lineHeight(value = 1.6)
            // Extra left padding to align with backdrop text after line numbers
            // (1rem base + 1rem number width + 1.25rem number margin = 3.25rem)
            .padding(top = 1.cssRem, right = 1.cssRem, bottom = 1.cssRem, left = 3.25.cssRem)
            .backgroundColor(Colors.Transparent)
            .color(colorMode.toSitePalette().onSurfaceVariant)
            .border(0.px, LineStyle.None, Colors.Transparent)
            .outline("none".unsafeCast<Outline>())
            // Disable native textarea appearance so browser doesn't
            // draw its own background over the transparent one.
            .styleModifier { property("appearance", "none") }
            .caretColor(colorMode.toSitePalette().primary)
            .whiteSpace(WhiteSpace.Pre)
            .overflow(Overflow.Auto)
    }
    cssRule("::placeholder") {
        Modifier.color(colorMode.toSitePalette().onSurfaceVariant)
    }
}

@Composable
fun InputPanel(
    inputCode: String,
    onInputChange: (String) -> Unit,
    onPaste: ((String) -> Unit)? = null,
    fileName: String = "input.svg",
) {
    val colorMode = ColorMode.current
    val state = remember { InputPanelState() }
    var textareaElement by remember { mutableStateOf<HTMLTextAreaElement?>(null) }
    var backdropElement by remember { mutableStateOf<HTMLElement?>(null) }

    LaunchedEffect(Unit) {
        Shiki.initialize()
        state.onReady(inputCode, colorMode)
    }

    Div(attrs = InputPanelStyle.toModifier().toAttrs()) {
        Div(
            attrs = PanelHeaderStyle.toModifier()
                .display(DisplayStyle.Flex).alignItems(AlignItems.Center).gap(0.4.cssRem)
                .toAttrs(),
        ) {
            FaCode()
            SpanText(fileName)
        }
        Div(attrs = EditorContainerStyle.toModifier().toAttrs()) {
            Div(
                attrs = EditorBackdropStyle.toModifier().toAttrs {
                    ref { element ->
                        backdropElement = element
                        onDispose { backdropElement = null }
                    }
                },
            ) {
                DisposableEffect(state.highlightedHtml) {
                    scopeElement.innerHTML = state.highlightedHtml
                    // Strip Shiki's inline background-color on <pre> so our
                    // CSS rule (transparent) takes effect. Inline styles have
                    // higher specificity than class-based rules.
                    scopeElement.querySelector("pre")?.let { pre ->
                        pre.asDynamic().style.backgroundColor = ""
                    }
                    onDispose { }
                }
            }
            CodeEditorTextarea(
                inputCode,
                onEffect = { element ->
                    textareaElement = element
                    onDispose { textareaElement = null }
                },
                onInputChange,
                onPaste,
            )
        }
    }
    ScrollSyncEffect(textareaElement, backdropElement)

    LaunchedEffect(inputCode, colorMode, state) {
        state.onInputChange(inputCode, colorMode)
    }

    // When highlighting is ready, make textarea text transparent so the
    // coloured backdrop shows through. Use direct DOM style so we
    // bypass CssStyle specificity issues.
    LaunchedEffect(state.highlightedHtml, inputCode) {
        val textarea = textareaElement ?: return@LaunchedEffect
        if (state.highlightedHtml.isNotEmpty() && inputCode.isNotBlank()) {
            textarea.style.setProperty("color", "transparent")
            textarea.style.setProperty("-webkit-text-fill-color", "transparent")
        } else {
            textarea.style.removeProperty("color")
            textarea.style.removeProperty("-webkit-text-fill-color")
        }
    }
}

@Stable
class InputPanelState {
    var highlightedHtml by mutableStateOf("")
        private set
    private var isShikiReady by mutableStateOf(false)

    suspend fun onReady(inputCode: String, colorMode: ColorMode) {
        isShikiReady = true
        onInputChange(inputCode, colorMode)
    }

    suspend fun onInputChange(inputCode: String, colorMode: ColorMode) {
        highlightedHtml = if (!isShikiReady || inputCode.isBlank()) {
            ""
        } else {
            val options = codeToHtmlOptions(
                lang = HIGHLIGHT_LANGUAGE,
                isDark = colorMode.isDark,
                lightTheme = LIGHT_THEME,
                darkTheme = DARK_THEME,
                colorReplacements = mapOf(
                    ONE_DARK_PRO_BACKGROUND to "transparent",
                    ONE_LIGHT_BACKGROUND to "transparent",
                ),
            )
            Shiki.instance.codeToHtml(inputCode, options)
        }
    }
}

@Composable
private fun CodeEditorTextarea(
    inputCode: String,
    onEffect: DisposableEffectScope.(HTMLTextAreaElement) -> DisposableEffectResult,
    onInputChange: (String) -> Unit,
    onPaste: ((String) -> Unit)? = null,
) {
    TextArea(
        value = inputCode,
        attrs = EditorTextareaStyle.toModifier()
            .toAttrs {
                placeholder("Paste your SVG code here...")
                attr("spellcheck", "false")
                onInput { onInputChange(it.value) }
                if (onPaste != null) {
                    addEventListener("paste") { event ->
                        val pastedText = event.asDynamic().clipboardData
                            ?.getData("text/plain") as? String
                        if (pastedText != null) onPaste(pastedText)
                    }
                }
                onKeyDown { event ->
                    handleEditorTab(event, onInputChange)
                }
                ref(onEffect)
            },
    )
}

private fun handleEditorTab(
    event: org.jetbrains.compose.web.events.SyntheticKeyboardEvent,
    onInputChange: (String) -> Unit,
) {
    if (event.key != "Tab") return
    event.preventDefault()
    val textarea = event.target as? HTMLTextAreaElement ?: return
    val start = textarea.selectionStart ?: 0
    val end = textarea.selectionEnd ?: 0
    val value = textarea.value

    val (newValue, newPos) = if (event.shiftKey) {
        computeOutdent(value, start)
    } else {
        computeIndent(value, start, end)
    } ?: return

    textarea.value = newValue
    textarea.setSelectionRange(newPos, newPos)
    onInputChange(newValue)
}

private data class EditorEdit(val newValue: String, val newPos: Int)

private fun computeOutdent(value: String, start: Int): EditorEdit? {
    val lineStart = value.lastIndexOf('\n', start - 1) + 1
    if (!value.substring(lineStart).startsWith(TAB_INDENT)) return null
    val newValue = value.substring(0, lineStart) + value.substring(lineStart + TAB_INDENT.length)
    val newPos = maxOf(lineStart, start - TAB_INDENT.length)
    return EditorEdit(newValue, newPos)
}

private fun computeIndent(value: String, start: Int, end: Int): EditorEdit {
    val newValue = value.substring(0, start) + TAB_INDENT + value.substring(end)
    return EditorEdit(newValue, start + TAB_INDENT.length)
}

@Composable
private fun ScrollSyncEffect(textareaElement: HTMLTextAreaElement?, backdropElement: HTMLElement?) {
    DisposableEffect(textareaElement, backdropElement) {
        val textarea = textareaElement ?: return@DisposableEffect onDispose { }
        val backdrop = backdropElement ?: return@DisposableEffect onDispose { }
        val onScroll: (Event) -> Unit = {
            backdrop.scrollTop = textarea.scrollTop
            backdrop.scrollLeft = textarea.scrollLeft
        }
        textarea.addEventListener("scroll", onScroll)
        onDispose {
            textarea.removeEventListener("scroll", onScroll)
        }
    }
}
