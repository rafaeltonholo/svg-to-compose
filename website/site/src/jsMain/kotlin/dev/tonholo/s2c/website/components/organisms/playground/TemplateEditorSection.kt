package dev.tonholo.s2c.website.components.organisms.playground

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.PointerEvents
import com.varabyte.kobweb.compose.css.Resize
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flex
import com.varabyte.kobweb.compose.ui.modifiers.flexDirection
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.pointerEvents
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.resize
import com.varabyte.kobweb.compose.ui.modifiers.right
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaFileCode
import com.varabyte.kobweb.silk.components.icons.fa.FaTrash
import com.varabyte.kobweb.silk.components.icons.fa.FaUpload
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.components.molecules.CollapsibleSection
import dev.tonholo.s2c.website.components.molecules.playground.AutocompleteDropdown
import dev.tonholo.s2c.website.components.molecules.playground.AutocompleteState
import dev.tonholo.s2c.website.components.molecules.playground.ScrollSyncEffect
import dev.tonholo.s2c.website.components.molecules.playground.TomlTooltip
import dev.tonholo.s2c.website.components.molecules.playground.TomlTooltipEffect
import dev.tonholo.s2c.website.components.molecules.playground.TomlTooltipState
import dev.tonholo.s2c.website.domain.model.playground.template.TemplateValidationError
import dev.tonholo.s2c.website.shiki.Shiki
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.theme.toSitePalette
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.TextArea
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLTextAreaElement
import org.w3c.files.FileReader

/**
 * Default scaffolded TOML content with section headers.
 *
 * Stored without Shiki annotations — `# [!code highlight]` is injected
 * dynamically when building the Shiki backdrop input.
 */
// language=toml
const val DEFAULT_TEMPLATE_TOML = """
[definitions]

# Insert here your custom definitions

[definitions.imports]

# Insert here your custom imports

[[definitions.color_mapping]]

# Use [[definitions.color_mapping]] to define each entry of your custom colour mapping

[templates]

# Insert here your custom templates

[templates.preview]

# Set here your desired preview template

[fragments]

# Insert here your custom fragments
"""

/** Section headers that should be highlighted in the Shiki backdrop. */
@Suppress("RegExpRedundantEscape")
private val SECTION_HEADER_PATTERN = Regex("""^\[{1,2}[^\]]+\]{1,2}\s*$""")

val TemplateEditorContainerStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .gap(SiteTheme.dimensions.size.Md)
        .padding(SiteTheme.dimensions.size.Lg)
}

val TemplateToolbarStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .alignItems(AlignItems.Center)
        .gap(SiteTheme.dimensions.size.Md)
}

val TemplateToolbarButtonStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .display(DisplayStyle.Flex)
        .alignItems(AlignItems.Center)
        .gap(SiteTheme.dimensions.size.Xsm)
        .backgroundColor(palette.surfaceVariant)
        .color(palette.onSurfaceVariant)
        .borderRadius(0.375.cssRem)
        .padding(topBottom = SiteTheme.dimensions.size.Xsm, leftRight = SiteTheme.dimensions.size.Md)
        .border(1.px, LineStyle.Solid, palette.outline)
        .fontSize(0.75.cssRem)
        .fontWeight(FontWeight.Medium)
        .cursor(Cursor.Pointer)
}

private const val HIGHLIGHT_OPACITY = 0.12f
private const val ERROR_OPACITY = 0.15f
private const val INFO_ICON_OPACITY = 0.5

val TemplateEditorWrapperStyle = CssStyle {
    base {
        Modifier
            .position(Position.Relative)
            .fillMaxWidth()
            .minHeight(12.cssRem)
            .backgroundColor(colorMode.toSitePalette().surfaceVariant)
            .borderRadius(0.375.cssRem)
            .border(1.px, LineStyle.Solid, colorMode.toSitePalette().outline)
            .overflow(Overflow.Hidden)
            .display(DisplayStyle.Flex)
            .flexDirection(FlexDirection.Column)
            .resize(Resize.Vertical)
    }
    // Lines containing an info icon get position: relative for absolute icon positioning
    cssRule(" .line:has(.$INFO_ICON_CLASS)") {
        Modifier
            .display(DisplayStyle.InlineBlock)
            .fillMaxWidth()
            .position(Position.Relative)
    }
    // Info icon rendered by Shiki infoIconTransformer
    cssRule(" .$INFO_ICON_CLASS") {
        Modifier
            .position(Position.Absolute)
            .right(0.5.cssRem)
            .cursor(Cursor.Pointer)
            .fontSize(0.7.cssRem)
            .pointerEvents(PointerEvents.Auto)
            .color(colorMode.toSitePalette().onSurfaceVariant)
            .opacity(INFO_ICON_OPACITY)
    }
    // Shiki transformerNotationHighlight adds .highlighted to lines
    cssRule(" .line.highlighted") {
        Modifier
            .display(DisplayStyle.InlineBlock)
            .fillMaxWidth()
            .backgroundColor(colorMode.toSitePalette().primary.toRgb().copyf(alpha = HIGHLIGHT_OPACITY))
    }
    // Shiki transformerNotationErrorLevel adds .highlighted.error to lines
    cssRule(" .line.highlighted.error") {
        Modifier
            .display(DisplayStyle.InlineBlock)
            .fillMaxWidth()
            .backgroundColor(colorMode.toSitePalette().error.toRgb().copyf(alpha = ERROR_OPACITY))
    }
    // Shiki transformerNotationErrorLevel adds .highlighted.warning to lines
    cssRule(" .line.highlighted.warning") {
        Modifier
            .display(DisplayStyle.InlineBlock)
            .fillMaxWidth()
            .backgroundColor(colorMode.toSitePalette().warning.toRgb().copyf(alpha = HIGHLIGHT_OPACITY))
    }
}

/**
 * Collapsible "Template" section for the playground.
 *
 * Contains a TOML code editor (textarea + Shiki backdrop), file upload
 * button, and clear button. Collapsed by default.
 */
@Composable
fun TemplateEditorSection(
    templateToml: String,
    templateErrors: List<TemplateValidationError>,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onTemplateChange: (String) -> Unit,
    onTemplateFileLoad: (String) -> Unit,
    onClear: () -> Unit,
) {
    CollapsibleSection(
        title = "Template",
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        leadingIcon = { FaFileCode() },
    ) {
        Div(attrs = TemplateEditorContainerStyle.toModifier().toAttrs()) {
            HelperText()
            TemplateToolbar(onTemplateFileLoad, onClear)
            TemplateCodeEditor(
                toml = templateToml,
                templateErrors = templateErrors,
                onInputChange = onTemplateChange,
            )
        }
    }
}

@Composable
private fun HelperText() {
    val palette = ColorMode.current.toSitePalette()
    SpanText(
        "Customise generated code with a template",
        modifier = Modifier
            .fontSize(0.75.cssRem)
            .color(palette.onSurfaceVariant),
    )
}

@Composable
private fun TemplateToolbar(onTemplateFileLoad: (String) -> Unit, onClear: () -> Unit) {
    var fileInputRef by remember { mutableStateOf<HTMLInputElement?>(null) }

    Input(
        type = InputType.File,
        attrs = Modifier.display(DisplayStyle.None).toAttrs {
            attr("accept", ".toml")
            ref { input ->
                fileInputRef = input
                onDispose { fileInputRef = null }
            }
            addEventListener("change") { event ->
                val input = event.target as? HTMLInputElement ?: return@addEventListener
                val file = input.files?.item(0) ?: return@addEventListener
                val reader = FileReader()
                reader.onload = { loadEvent ->
                    val content = loadEvent.target.asDynamic().result as? String
                    if (content != null) onTemplateFileLoad(content)
                    null
                }
                reader.readAsText(file)
                input.value = ""
            }
        },
    )

    Div(attrs = TemplateToolbarStyle.toModifier().toAttrs()) {
        Button(
            attrs = TemplateToolbarButtonStyle.toModifier().toAttrs {
                onClick { fileInputRef?.click() }
            },
        ) {
            FaUpload()
            Text("Upload .toml")
        }
        Button(
            attrs = TemplateToolbarButtonStyle.toModifier().toAttrs {
                onClick { onClear() }
            },
        ) {
            FaTrash()
            Text("Clear")
        }
    }
}

@Composable
private fun TemplateCodeEditor(
    toml: String,
    templateErrors: List<TemplateValidationError>,
    onInputChange: (String) -> Unit,
) {
    val colorMode = ColorMode.current
    val autocompleteState = remember { AutocompleteState() }
    val tooltipState = remember { TomlTooltipState() }
    var textareaElement by remember { mutableStateOf<HTMLTextAreaElement?>(null) }
    var backdropElement by remember { mutableStateOf<HTMLElement?>(null) }
    var wrapperElement by remember { mutableStateOf<HTMLElement?>(null) }
    var hasHighlight by remember { mutableStateOf(false) }

    val errorLines = templateErrors.map { it.line }
    val errorsByLine = templateErrors.associateBy { it.line }
    LaunchedEffect(toml, colorMode, templateErrors) {
        Shiki.initialize()
        val html = highlightToml(toml, colorMode, errorLines, SECTION_HEADER_PATTERN)
        hasHighlight = applyHighlightToBackdrop(backdropElement, html, errorsByLine, tooltipState, wrapperElement)
    }

    EditorWrapper(
        toml = toml,
        autocompleteState = autocompleteState,
        tooltipState = tooltipState,
        textareaElement = textareaElement,
        onInputChange = onInputChange,
        onWrapperRef = { wrapperElement = it },
        onBackdropRef = { backdropElement = it },
        onTextareaRef = { textareaElement = it },
    )

    TomlTooltipEffect(textareaElement, wrapperElement, toml, tooltipState)
    ScrollSyncEffect(textareaElement, backdropElement)

    LaunchedEffect(hasHighlight, toml) {
        val textarea = textareaElement ?: return@LaunchedEffect
        updateTextareaVisibility(textarea, hasHighlight && toml.isNotBlank())
    }
}

@Composable
private fun EditorWrapper(
    toml: String,
    autocompleteState: AutocompleteState,
    tooltipState: TomlTooltipState,
    textareaElement: HTMLTextAreaElement?,
    onInputChange: (String) -> Unit,
    onWrapperRef: (HTMLElement?) -> Unit,
    onBackdropRef: (HTMLElement?) -> Unit,
    onTextareaRef: (HTMLTextAreaElement?) -> Unit,
) {
    Div(
        attrs = TemplateEditorWrapperStyle.toModifier().toAttrs {
            ref { element ->
                onWrapperRef(element)
                onDispose { onWrapperRef(null) }
            }
        },
    ) {
        Div(
            attrs = Modifier.display(DisplayStyle.Flex).flexDirection(FlexDirection.Column).flex(1)
                .minHeight(0.px).overflow(Overflow.Hidden)
                .toAttrs(),
        ) {
            Div(
                attrs = EditorBackdropStyle.toModifier().toAttrs {
                    ref { element ->
                        onBackdropRef(element)
                        onDispose { onBackdropRef(null) }
                    }
                },
            )

            TextArea(
                value = toml,
                attrs = EditorTextareaStyle.toModifier()
                    .toAttrs {
                        attr("spellcheck", "false")
                        attr("autocomplete", "off")
                        onInput { onInputChange(it.value) }
                        onKeyDown { event ->
                            handleTemplateKeyDown(event, autocompleteState, onInputChange)
                        }
                        ref { element ->
                            onTextareaRef(element)
                            onDispose { onTextareaRef(null) }
                        }
                    },
            )
        }

        TomlTooltip(state = tooltipState)
        AutocompleteDropdown(
            state = autocompleteState,
            onSelect = { item ->
                val textarea = textareaElement ?: return@AutocompleteDropdown
                insertAutocompleteText(textarea, item.insertValue, onInputChange)
            },
        )
    }
}

private fun updateTextareaVisibility(textarea: HTMLTextAreaElement, transparent: Boolean) {
    if (transparent) {
        textarea.style.setProperty("color", "transparent")
        textarea.style.setProperty("-webkit-text-fill-color", "transparent")
    } else {
        textarea.style.removeProperty("color")
        textarea.style.removeProperty("-webkit-text-fill-color")
    }
}
