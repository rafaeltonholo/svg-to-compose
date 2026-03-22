package dev.tonholo.s2c.website.components.organisms.playground

import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.components.molecules.playground.AutocompleteState
import dev.tonholo.s2c.website.components.molecules.playground.TomlTooltipState
import dev.tonholo.s2c.website.components.molecules.playground.computeCursorPosition
import dev.tonholo.s2c.website.components.molecules.playground.resolveAutocompleteSuggestions
import dev.tonholo.s2c.website.components.organisms.playground.template.TemplateEditorSchema
import dev.tonholo.s2c.website.domain.model.playground.template.TemplateValidationError
import dev.tonholo.s2c.website.domain.model.playground.template.TooltipInfo
import dev.tonholo.s2c.website.shiki.DARK_THEME
import dev.tonholo.s2c.website.shiki.LIGHT_THEME
import dev.tonholo.s2c.website.shiki.SHIKI_CODE_BLOCK_DARK_BACKGROUND
import dev.tonholo.s2c.website.shiki.SHIKI_CODE_BLOCK_LIGHT_BACKGROUND
import dev.tonholo.s2c.website.shiki.Shiki
import dev.tonholo.s2c.website.shiki.ShikiTransformers
import dev.tonholo.s2c.website.shiki.codeToHtmlOptions
import org.jetbrains.compose.web.events.SyntheticKeyboardEvent
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLTextAreaElement

private const val HIGHLIGHT_LANGUAGE = "toml"
internal const val INFO_ICON_CLASS = "s2c-info-icon"
private const val TOOLTIP_PADDING = 8.0
private const val TAB_INDENT = "  "
private const val SHIKI_ANNOTATION_MARKER = "# [!code"

internal suspend fun highlightToml(
    toml: String,
    colorMode: ColorMode,
    errorLines: List<Int>,
    sectionHeaderPattern: Regex,
): String {
    if (toml.isBlank()) return ""
    val codeForHighlight = injectShikiAnnotations(toml, errorLines)
    val tomlLines = toml.lines()
    val transformers = arrayOf(
        ShikiTransformers.notationErrorLevel(),
        ShikiTransformers.renderWhitespace(),
        ShikiTransformers.renderIndentGuides(),
        ShikiTransformers.infoIconTransformer(
            tomlLines = tomlLines,
            isSectionHeader = { sectionHeaderPattern.matches(it) },
            tooltipLookup = { lineText ->
                val info = resolveTooltipForText(lineText)
                if (info != null) info.title to info.description else null
            },
        ),
    )
    val options = codeToHtmlOptions(
        lang = HIGHLIGHT_LANGUAGE,
        isDark = colorMode.isDark,
        lightTheme = LIGHT_THEME,
        darkTheme = DARK_THEME,
        colorReplacements = mapOf(
            SHIKI_CODE_BLOCK_DARK_BACKGROUND to "transparent",
            SHIKI_CODE_BLOCK_LIGHT_BACKGROUND to "transparent",
        ),
        transformers = transformers,
    )
    return runCatching { Shiki.instance.codeToHtml(codeForHighlight, options) }
        .onFailure { e -> console.warn("Failed to highlight TOML", e) }
        .getOrDefault("")
}

/**
 * Applies highlighted HTML to the backdrop element and attaches error tooltips.
 *
 * @return `true` if the backdrop was successfully updated, `false` otherwise.
 */
internal fun applyHighlightToBackdrop(
    backdrop: HTMLElement?,
    html: String,
    errorsByLine: Map<Int, TemplateValidationError>,
    tooltipState: TomlTooltipState,
    wrapperElement: HTMLElement?,
): Boolean {
    if (backdrop == null || html.isEmpty()) return false
    backdrop.innerHTML = html
    backdrop.querySelector("pre")?.let { pre ->
        pre.asDynamic().style.backgroundColor = ""
    }
    attachErrorTitles(backdrop, errorsByLine)
    wireInfoIconHandlers(backdrop, tooltipState, wrapperElement)
    return true
}

private fun attachErrorTitles(backdrop: HTMLElement, errorsByLine: Map<Int, TemplateValidationError>) {
    val allLines = backdrop.querySelectorAll(".line")
    for (i in 0 until allLines.length) {
        val lineEl = allLines.item(i) as? HTMLElement ?: continue
        val error = errorsByLine[i + 1]
        if (error != null) {
            lineEl.title = error.message
        }
    }
}

private fun injectShikiAnnotations(toml: String, errorLines: List<Int>): String {
    val errorSet = errorLines.toSet()
    val lines = toml.lines().toMutableList()
    for (i in lines.indices) {
        val trimmed = lines[i].trim()
        val lineNumber = i + 1
        if (trimmed.contains(SHIKI_ANNOTATION_MARKER)) continue
        if (lineNumber in errorSet) {
            lines[i] = "${lines[i]} # [!code error]"
        }
    }
    return lines.joinToString("\n")
}

private fun resolveTooltipForText(lineText: String): TooltipInfo? {
    if (lineText.isEmpty() || lineText.startsWith("#")) return null
    TemplateEditorSchema.findTooltipInfo(lineText)?.let { return it }
    val equalsIndex = lineText.indexOf('=')
    if (equalsIndex > 0) {
        val key = lineText.substring(0, equalsIndex).trim()
        TemplateEditorSchema.findTooltipInfo(key)?.let { return it }
    }
    return null
}

private fun wireInfoIconHandlers(backdrop: HTMLElement, tooltipState: TomlTooltipState, wrapperElement: HTMLElement?) {
    val icons = backdrop.querySelectorAll(".$INFO_ICON_CLASS")
    for (i in 0 until icons.length) {
        val icon = icons.item(i) as? HTMLElement ?: continue
        val title = icon.getAttribute("data-tooltip-title").orEmpty()
        val description = icon.getAttribute("data-tooltip-desc").orEmpty()
        if (title.isEmpty()) continue

        val info = TooltipInfo(title = title, description = description)
        val handler: (org.w3c.dom.events.Event) -> Unit = {
            if (wrapperElement != null) {
                val iconRect = icon.getBoundingClientRect()
                val wrapperRect = wrapperElement.getBoundingClientRect()
                val top = iconRect.bottom - wrapperRect.top
                val left = TOOLTIP_PADDING
                val maxWidth = wrapperRect.width - (TOOLTIP_PADDING * 2)
                if (tooltipState.visible) {
                    tooltipState.hide()
                } else {
                    tooltipState.show(info, top, left, maxWidth)
                }
            }
        }
        icon.addEventListener("click", handler)
    }
}

internal fun handleTemplateKeyDown(
    event: SyntheticKeyboardEvent,
    autocompleteState: AutocompleteState,
    onInputChange: (String) -> Unit,
) {
    val textarea = event.target as? HTMLTextAreaElement ?: return

    if (autocompleteState.visible && handleAutocompleteNav(event, textarea, autocompleteState, onInputChange)) {
        return
    }

    if (handleAutocompleteTrigger(event, textarea, autocompleteState)) return

    if (event.key == "Tab") {
        event.preventDefault()
        val start = textarea.selectionStart ?: 0
        val end = textarea.selectionEnd ?: 0
        val value = textarea.value
        val newValue = value.substring(0, start) + TAB_INDENT + value.substring(end)
        textarea.value = newValue
        textarea.setSelectionRange(start + TAB_INDENT.length, start + TAB_INDENT.length)
        onInputChange(newValue)
    }
}

private fun handleAutocompleteNav(
    event: SyntheticKeyboardEvent,
    textarea: HTMLTextAreaElement,
    state: AutocompleteState,
    onInputChange: (String) -> Unit,
): Boolean = when (event.key) {
    "ArrowDown" -> {
        event.preventDefault()
        state.moveDown()
        true
    }

    "ArrowUp" -> {
        event.preventDefault()
        state.moveUp()
        true
    }

    "Escape" -> {
        event.preventDefault()
        state.hide()
        true
    }

    "Enter", "Tab" -> {
        val selected = state.selectedItem()
        if (selected != null) {
            event.preventDefault()
            insertAutocompleteText(textarea, selected.insertValue, onInputChange)
            state.hide()
            true
        } else {
            false
        }
    }

    else -> false
}

private fun handleAutocompleteTrigger(
    event: SyntheticKeyboardEvent,
    textarea: HTMLTextAreaElement,
    state: AutocompleteState,
): Boolean {
    if (event.key != " " || !(event.ctrlKey || event.metaKey)) return false
    event.preventDefault()
    val cursorPos = textarea.selectionStart ?: 0
    val suggestions = resolveAutocompleteSuggestions(textarea.value, cursorPos)
    if (suggestions.isNotEmpty()) {
        val (top, left) = computeCursorPosition(textarea)
        state.show(suggestions, top, left)
    }
    return true
}

internal fun insertAutocompleteText(
    textarea: HTMLTextAreaElement,
    insertText: String,
    onInputChange: (String) -> Unit,
) {
    val start = textarea.selectionStart ?: 0
    val end = textarea.selectionEnd ?: 0
    val value = textarea.value

    val lineStart = value.lastIndexOf('\n', start - 1) + 1
    val currentLinePrefix = value.substring(lineStart, start)
    val needsNewline = currentLinePrefix.isNotBlank()

    val textToInsert = if (needsNewline) "\n$insertText" else insertText
    val newValue = value.substring(0, start) + textToInsert + value.substring(end)
    val newPos = start + textToInsert.length

    textarea.value = newValue
    textarea.setSelectionRange(newPos, newPos)
    onInputChange(newValue)
}
