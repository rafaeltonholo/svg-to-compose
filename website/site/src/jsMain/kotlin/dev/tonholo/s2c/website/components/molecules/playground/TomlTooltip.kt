package dev.tonholo.s2c.website.components.molecules.playground

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.BoxSizing
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.OverflowWrap
import com.varabyte.kobweb.compose.css.PointerEvents
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.boxSizing
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flexDirection
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.left
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.overflowWrap
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.pointerEvents
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.role
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.components.organisms.playground.template.TemplateEditorSchema
import dev.tonholo.s2c.website.domain.model.playground.template.TooltipInfo
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.theme.toSitePalette
import dev.tonholo.s2c.website.theme.typography.FontFamilies
import kotlinx.browser.window
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.MouseEvent

private const val TOOLTIP_Z_INDEX = 30
private const val TOOLTIP_MAX_WIDTH_REM = 20.0
private const val TOOLTIP_OFFSET_PX = 8

val TomlTooltipStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .position(Position.Absolute)
        .zIndex(TOOLTIP_Z_INDEX)
        .backgroundColor(palette.surface)
        .border(1.px, LineStyle.Solid, palette.outline)
        .borderRadius(0.375.cssRem)
        .padding(SiteTheme.dimensions.size.Sm)
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .gap(SiteTheme.dimensions.size.Xsm)
        .pointerEvents(PointerEvents.None)
        .overflowWrap(OverflowWrap.BreakWord)
        .boxSizing(BoxSizing.BorderBox)
}

val TooltipTitleStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .fontWeight(FontWeight.SemiBold)
        .fontSize(0.75.cssRem)
        .fontFamily(values = FontFamilies.mono)
        .color(palette.primary)
}

val TooltipDescriptionStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .fontSize(0.7.cssRem)
        .color(palette.onSurface)
}

val TooltipContextStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .fontSize(0.65.cssRem)
        .color(palette.onSurfaceVariant)
        .fontFamily(values = FontFamilies.mono)
}

val TooltipKeyListStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .fontSize(0.65.cssRem)
        .color(palette.onSurfaceVariant)
        .fontFamily(values = FontFamilies.mono)
        .padding(left = SiteTheme.dimensions.size.Sm)
}

/**
 * Mutable state holder for the TOML hover tooltip.
 */
@Stable
class TomlTooltipState {
    var visible by mutableStateOf(false)
        private set
    var info by mutableStateOf<TooltipInfo?>(null)
        private set
    var topPx by mutableDoubleStateOf(0.0)
        private set
    var leftPx by mutableDoubleStateOf(0.0)
        private set
    var maxWidthPx by mutableDoubleStateOf(0.0)
        private set

    fun show(tooltipInfo: TooltipInfo, top: Double, left: Double, maxWidth: Double = 0.0) {
        this.info = tooltipInfo
        this.topPx = top
        this.leftPx = left
        this.maxWidthPx = maxWidth
        this.visible = true
    }

    fun hide() {
        visible = false
        info = null
    }
}

/**
 * Resolves the TOML line under the mouse cursor by mapping the Y coordinate
 * to a line number in the textarea, then looks up tooltip info for that line's content.
 */
private fun resolveTooltipForLine(
    mouseY: Double,
    textareaElement: HTMLElement,
    tomlLines: List<String>,
): TooltipInfo? {
    val style = window.getComputedStyle(textareaElement)
    val lineHeight = style.getPropertyValue("line-height").removeSuffix("px").toDoubleOrNull() ?: return null
    val paddingTop = style.getPropertyValue("padding-top").removeSuffix("px").toDoubleOrNull() ?: 0.0
    val rect = textareaElement.getBoundingClientRect()
    val scrollTop = textareaElement.scrollTop

    val relativeY = mouseY - rect.top - paddingTop + scrollTop
    val lineIndex = (relativeY / lineHeight).toInt()
    if (lineIndex < 0 || lineIndex >= tomlLines.size) return null

    val lineContent = tomlLines[lineIndex].trim()

    // Try full line first (matches section headers), then extract key from key-value lines
    return TemplateEditorSchema.findTooltipInfo(lineContent)
        ?: lineContent.indexOf('=').takeIf { it > 0 }?.let { equalsIndex ->
            val key = lineContent.substring(0, equalsIndex).trim()
            TemplateEditorSchema.findTooltipInfo(key)
        }
}

/**
 * Sets up mousemove listeners on the textarea element to show tooltips
 * for known TOML tokens. Uses the textarea because the backdrop has
 * pointerEvents: None.
 */
@Composable
fun TomlTooltipEffect(
    textareaElement: HTMLElement?,
    wrapperElement: HTMLElement?,
    toml: String,
    state: TomlTooltipState,
) {
    DisposableEffect(textareaElement, wrapperElement, toml) {
        val textarea = textareaElement ?: return@DisposableEffect onDispose { }
        val wrapper = wrapperElement ?: return@DisposableEffect onDispose { }
        val lines = toml.lines()

        val onMouseMove: (Event) -> Unit = handler@{ event ->
            val mouseEvent = event as? MouseEvent ?: return@handler
            val info = resolveTooltipForLine(mouseEvent.clientY.toDouble(), textarea, lines)
            if (info != null) {
                val rect = wrapper.getBoundingClientRect()
                val top = mouseEvent.clientY - rect.top + TOOLTIP_OFFSET_PX
                val padding = TOOLTIP_OFFSET_PX.toDouble()
                val maxWidth = rect.width - (padding * 2)
                state.show(tooltipInfo = info, top = top, left = padding, maxWidth = maxWidth)
            } else {
                state.hide()
            }
        }

        val onMouseLeave: (Event) -> Unit = {
            state.hide()
        }

        textarea.addEventListener("mousemove", onMouseMove)
        textarea.addEventListener("mouseleave", onMouseLeave)
        onDispose {
            textarea.removeEventListener("mousemove", onMouseMove)
            textarea.removeEventListener("mouseleave", onMouseLeave)
        }
    }
}

/**
 * Tooltip popup positioned absolutely within the editor wrapper.
 * Uses dynamic maxWidth from state to stay within wrapper bounds.
 */
@Composable
fun TomlTooltip(state: TomlTooltipState) {
    val tooltipInfo = state.info
    if (!state.visible || tooltipInfo == null) return

    val widthModifier = if (state.maxWidthPx > 0) {
        Modifier.maxWidth(state.maxWidthPx.px)
    } else {
        Modifier.maxWidth(TOOLTIP_MAX_WIDTH_REM.cssRem)
    }

    Div(
        attrs = TomlTooltipStyle.toModifier()
            .then(widthModifier)
            .top(state.topPx.px)
            .left(state.leftPx.px)
            .role("tooltip")
            .toAttrs(),
    ) {
        TooltipContent(tooltipInfo)
    }
}

val TomlTooltipBlockStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .fillMaxWidth()
        .backgroundColor(palette.surface)
        .border(1.px, LineStyle.Solid, palette.outline)
        .borderRadius(0.375.cssRem)
        .padding(SiteTheme.dimensions.size.Sm)
        .display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .gap(SiteTheme.dimensions.size.Xsm)
        .overflowWrap(OverflowWrap.BreakWord)
        .boxSizing(BoxSizing.BorderBox)
}

@Composable
private fun TooltipContent(tooltipInfo: TooltipInfo) {
    Span(attrs = TooltipTitleStyle.toModifier().toAttrs()) {
        Text(tooltipInfo.title)
    }
    Span(attrs = TooltipDescriptionStyle.toModifier().toAttrs()) {
        Text(tooltipInfo.description)
    }
    if (tooltipInfo.context != null) {
        Span(attrs = TooltipContextStyle.toModifier().toAttrs()) {
            Text(tooltipInfo.context)
        }
    }
    if (!tooltipInfo.keys.isNullOrEmpty()) {
        Div(attrs = TooltipKeyListStyle.toModifier().toAttrs()) {
            tooltipInfo.keys.forEach { keyDesc ->
                Div {
                    Text(keyDesc)
                }
            }
        }
    }
}
