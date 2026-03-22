package dev.tonholo.s2c.website.components.molecules.playground

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.left
import com.varabyte.kobweb.compose.ui.modifiers.maxHeight
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.components.organisms.playground.template.TemplateEditorSchema
import dev.tonholo.s2c.website.domain.model.playground.template.TomlKeyInfo
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.theme.toSitePalette
import dev.tonholo.s2c.website.theme.typography.FontFamilies
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLTextAreaElement
import org.w3c.dom.events.Event

private const val DROPDOWN_Z_INDEX = 20
private const val DROPDOWN_MAX_HEIGHT_REM = 12.0
private const val DROPDOWN_MIN_WIDTH_REM = 16.0

val AutocompleteDropdownStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .position(Position.Absolute)
        .zIndex(DROPDOWN_Z_INDEX)
        .backgroundColor(palette.surface)
        .border(1.px, LineStyle.Solid, palette.outline)
        .borderRadius(0.375.cssRem)
        .overflow(Overflow.Auto)
        .maxHeight(DROPDOWN_MAX_HEIGHT_REM.cssRem)
        .minWidth(DROPDOWN_MIN_WIDTH_REM.cssRem)
        .padding(topBottom = SiteTheme.dimensions.size.Xsm)
}

val AutocompleteItemStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .display(DisplayStyle.Flex)
        .padding(topBottom = SiteTheme.dimensions.size.Xsm, leftRight = SiteTheme.dimensions.size.Md)
        .cursor(Cursor.Pointer)
        .fontSize(0.75.cssRem)
        .fontFamily(values = FontFamilies.mono)
        .color(palette.onSurface)
}

val AutocompleteItemHoveredStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier.backgroundColor(palette.primaryContainer.toRgb().copyf(alpha = 0.15f))
}

val AutocompleteItemKeyStyle = CssStyle.base {
    Modifier
        .fontWeight(FontWeight.SemiBold)
        .fontSize(0.75.cssRem)
}

val AutocompleteItemDescStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .color(palette.onSurfaceVariant)
        .fontSize(0.7.cssRem)
        .padding(left = SiteTheme.dimensions.size.Sm)
}

/**
 * Mutable state holder for autocomplete visibility and position.
 */
@Stable
class AutocompleteState {
    var visible by mutableStateOf(false)
        private set
    var suggestions by mutableStateOf<List<TomlKeyInfo>>(emptyList())
        private set
    var topPx by mutableDoubleStateOf(0.0)
        private set
    var leftPx by mutableDoubleStateOf(0.0)
        private set
    var selectedIndex by mutableIntStateOf(0)
        private set

    /**
     * Show autocomplete at the given position with the given suggestions.
     */
    fun show(suggestions: List<TomlKeyInfo>, top: Double, left: Double) {
        this.suggestions = suggestions
        this.topPx = top
        this.leftPx = left
        this.selectedIndex = 0
        this.visible = true
    }

    fun hide() {
        visible = false
        suggestions = emptyList()
    }

    fun moveUp() {
        if (suggestions.isNotEmpty()) {
            selectedIndex = (selectedIndex - 1 + suggestions.size) % suggestions.size
        }
    }

    fun moveDown() {
        if (suggestions.isNotEmpty()) {
            selectedIndex = (selectedIndex + 1) % suggestions.size
        }
    }

    fun selectedItem(): TomlKeyInfo? = suggestions.getOrNull(selectedIndex)
}

/**
 * Resolves autocomplete suggestions based on the current cursor position
 * in the TOML text.
 *
 * Scans backward from [cursorPos] to find the nearest `[section]` header
 * and returns the valid keys for that section.
 */
fun resolveAutocompleteSuggestions(toml: String, cursorPos: Int): List<TomlKeyInfo> {
    val textBeforeCursor = toml.substring(0, minOf(cursorPos, toml.length))
    val lines = textBeforeCursor.lines()

    // Scan backward to find the nearest section header
    for (i in lines.indices.reversed()) {
        val line = lines[i].trim()
        val headerMatch = SECTION_HEADER_REGEX.matchEntire(line)
        if (headerMatch != null) {
            val sectionName = headerMatch.groupValues[1]
            val section = TemplateEditorSchema.sections[sectionName]
            if (section != null) {
                return section.keys
            }
            // Unknown section, no suggestions
            return emptyList()
        }
    }

    // No section header found: offer top-level sections
    return TemplateEditorSchema.topLevelSections
}

// Escape on `]` is required for JS platform (unicode flag disallows raw brackets).
@Suppress("RegExpRedundantEscape")
private val SECTION_HEADER_REGEX = Regex("""\[{1,2}([^\]]+)\]{1,2}""")

/**
 * Calculates the pixel position of the cursor inside a textarea,
 * relative to the textarea's top-left corner.
 *
 * Uses a hidden mirror div to measure text dimensions.
 */
fun computeCursorPosition(textarea: HTMLTextAreaElement): Pair<Double, Double> {
    val mirror = document.createElement("div")
    val style = mirror.asDynamic().style

    val computedStyle = window.getComputedStyle(textarea)
    style.position = "absolute"
    style.visibility = "hidden"
    style.whiteSpace = "pre-wrap"
    style.wordWrap = "break-word"
    style.overflow = "hidden"
    style.width = computedStyle.getPropertyValue("width")
    style.fontFamily = computedStyle.getPropertyValue("font-family")
    style.fontSize = computedStyle.getPropertyValue("font-size")
    style.lineHeight = computedStyle.getPropertyValue("line-height")
    style.padding = computedStyle.getPropertyValue("padding")
    style.border = computedStyle.getPropertyValue("border")

    val cursorPos = textarea.selectionStart ?: 0
    val textBefore = textarea.value.substring(0, cursorPos)

    val textNode = document.createTextNode(textBefore)
    val cursorSpan = document.createElement("span")
    cursorSpan.textContent = "|"

    mirror.appendChild(textNode)
    mirror.appendChild(cursorSpan)
    document.body?.appendChild(mirror)

    val spanRect = cursorSpan.getBoundingClientRect()
    val mirrorRect = mirror.getBoundingClientRect()

    val top = spanRect.top - mirrorRect.top + spanRect.height - textarea.scrollTop
    val left = spanRect.left - mirrorRect.left - textarea.scrollLeft

    document.body?.removeChild(mirror)

    return top to left
}

/**
 * Dropdown overlay that shows autocomplete suggestions for the TOML editor.
 *
 * Positioned absolutely relative to the editor wrapper.
 */
@Composable
fun AutocompleteDropdown(state: AutocompleteState, onSelect: (TomlKeyInfo) -> Unit, modifier: Modifier = Modifier) {
    if (!state.visible || state.suggestions.isEmpty()) return

    var hoveredIndex by remember(state.suggestions) { mutableStateOf(-1) }

    // Dismiss on click outside
    DisposableEffect(state.visible) {
        val handler: (Event) -> Unit = { state.hide() }
        // Use capture phase so we get clicks before the textarea
        window.addEventListener("mousedown", handler)
        onDispose {
            window.removeEventListener("mousedown", handler)
        }
    }

    Div(
        attrs = AutocompleteDropdownStyle.toModifier()
            .then(modifier)
            .top(state.topPx.px)
            .left(state.leftPx.px)
            .toAttrs {
                attr("role", "listbox")
                // Prevent the mousedown from reaching the window handler
                addEventListener("mousedown") { event ->
                    event.stopPropagation()
                }
            },
    ) {
        state.suggestions.forEachIndexed { index, item ->
            val isSelected = index == state.selectedIndex || index == hoveredIndex
            Div(
                attrs = AutocompleteItemStyle.toModifier()
                    .then(if (isSelected) AutocompleteItemHoveredStyle.toModifier() else Modifier)
                    .toAttrs {
                        attr("role", "option")
                        addEventListener("mouseenter") { hoveredIndex = index }
                        addEventListener("mouseleave") { hoveredIndex = -1 }
                        addEventListener("click") {
                            onSelect(item)
                            state.hide()
                        }
                    },
            ) {
                Span(attrs = AutocompleteItemKeyStyle.toModifier().toAttrs()) {
                    Text(item.key)
                }
                Span(attrs = AutocompleteItemDescStyle.toModifier().toAttrs()) {
                    Text(item.description)
                }
            }
        }
    }
}
