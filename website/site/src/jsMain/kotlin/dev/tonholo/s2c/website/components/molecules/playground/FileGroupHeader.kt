package dev.tonholo.s2c.website.components.molecules.playground

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.ariaLabel
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderBottom
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flex
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.role
import com.varabyte.kobweb.compose.ui.modifiers.tabIndex
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaChevronDown
import com.varabyte.kobweb.silk.components.icons.fa.FaChevronRight
import com.varabyte.kobweb.silk.components.icons.fa.FaFolder
import com.varabyte.kobweb.silk.components.icons.fa.FaFolderOpen
import com.varabyte.kobweb.silk.components.icons.fa.FaMinus
import com.varabyte.kobweb.silk.components.icons.fa.FaSquare
import com.varabyte.kobweb.silk.components.icons.fa.FaSquareCheck
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.SitePalette
import dev.tonholo.s2c.website.SiteTheme
import dev.tonholo.s2c.website.state.playground.BatchPhase
import dev.tonholo.s2c.website.state.playground.FileGroup
import dev.tonholo.s2c.website.state.playground.fileKey
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

private const val FOLDER_HEADER_Z_INDEX = 5

val FileGroupHeaderStyle = CssStyle {
    base {
        val palette = colorMode.toSitePalette()
        Modifier
            .fillMaxWidth()
            .padding(topBottom = 0.375.cssRem, leftRight = 0.5.cssRem)
            .backgroundColor(palette.surfaceVariant)
            .borderBottom {
                width(1.px)
                style(LineStyle.Solid)
                color(palette.outline.toRgb().copyf(alpha = 0.5f))
            }
            .cursor(Cursor.Pointer)
            .gap(0.5.cssRem)
            .position(Position.Sticky)
            .top(0.px)
            .zIndex(FOLDER_HEADER_Z_INDEX)
            .boxShadow(offsetY = 2.px, blurRadius = 4.px, color = Colors.Black.copyf(alpha = 0.1f))
            .transition(
                Transition.of("background-color", duration = 150.ms, timingFunction = TransitionTimingFunction.Ease),
            )
    }
    hover {
        Modifier.backgroundColor(colorMode.toSitePalette().surface)
    }
}

val FileGroupProgressBarTrackStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .height(4.px)
        .width(80.px)
        .backgroundColor(palette.outline)
        .borderRadius(2.px)
}

val FileGroupProgressBarFillStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .height(4.px)
        .backgroundColor(palette.primary)
        .borderRadius(2.px)
}

/**
 * Collapsible folder header row rendered differently per [BatchPhase]:
 * - **Select**: checkbox + folder icon + folder name + "(N files)"
 * - **Converting**: folder icon + folder name + "42/156" + mini progress bar
 * - **Results**: folder icon + folder name + "150 ok, 6 errors"
 */
@Composable
internal fun FileGroupHeader(
    group: FileGroup,
    phase: BatchPhase,
    isExpanded: Boolean,
    selectedCount: Int,
    selectedFiles: Set<String>,
    completedCount: Int,
    onToggleExpand: () -> Unit,
    onToggleSelection: (shiftKey: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val palette = SiteTheme.palette
    val iconModifier = Modifier.fontSize(0.75.cssRem).color(palette.onSurfaceVariant)

    Row(
        modifier = FileGroupHeaderStyle.toModifier()
            .then(modifier)
            .onClick { onToggleExpand() }
            .tabIndex(0)
            .role("button")
            .ariaLabel("Toggle folder ${group.folderPath}"),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Expand/collapse chevron
        if (isExpanded) {
            FaChevronDown(modifier = iconModifier)
        } else {
            FaChevronRight(modifier = iconModifier)
        }

        // Checkbox (only shown in Select phase)
        if (phase is BatchPhase.Select) {
            FileGroupCheckbox(
                selectedCount = selectedCount,
                totalCount = group.files.size,
                onToggleSelection = onToggleSelection,
            )
        }

        // Folder icon
        if (isExpanded) {
            FaFolderOpen(modifier = Modifier.fontSize(0.8.cssRem).color(palette.onSurfaceVariant))
        } else {
            FaFolder(modifier = Modifier.fontSize(0.8.cssRem).color(palette.onSurfaceVariant))
        }

        // Folder name
        SpanText(
            group.folderPath.ifEmpty { "(root)" },
            modifier = Modifier
                .fontWeight(FontWeight.Medium)
                .fontSize(0.8.cssRem)
                .flex(1),
        )

        // Phase-specific trailing content
        when (phase) {
            is BatchPhase.Select -> {
                SpanText(
                    "(${group.files.size} ${if (group.files.size == 1) "file" else "files"})",
                    modifier = Modifier
                        .fontSize(0.75.cssRem)
                        .color(palette.onSurfaceVariant),
                )
            }

            is BatchPhase.Converting -> {
                FileGroupConvertingTrailing(
                    completedCount = completedCount,
                    totalCount = group.files.size,
                )
            }

            is BatchPhase.Results -> {
                val selectedInGroup = group.files.count { file ->
                    file.fileKey() in selectedFiles
                }
                FileGroupResultsTrailing(
                    completedCount = completedCount,
                    selectedCount = selectedInGroup,
                    palette = palette,
                )
            }
        }
    }
}

@Composable
private fun FileGroupCheckbox(selectedCount: Int, totalCount: Int, onToggleSelection: (shiftKey: Boolean) -> Unit) {
    val palette = SiteTheme.palette
    val allSelected = selectedCount == totalCount
    Div(
        attrs = Modifier
            .fontSize(0.9.cssRem)
            .color(palette.primary)
            .cursor(Cursor.Pointer)
            .padding(0.375.cssRem)
            .role("checkbox")
            .tabIndex(0)
            .ariaLabel("Toggle folder selection")
            .onClick { event ->
                event.stopPropagation()
                onToggleSelection(event.shiftKey)
            }
            .toAttrs {
                attr("aria-checked", allSelected.toString())
                attr("title", "Shift+click to select range")
            },
    ) {
        when {
            allSelected -> FaSquareCheck()
            selectedCount > 0 -> FaMinus()
            else -> FaSquare()
        }
    }
}

@Composable
private fun FileGroupConvertingTrailing(completedCount: Int, totalCount: Int) {
    val palette = SiteTheme.palette
    val progressPercent = if (totalCount > 0) (completedCount * 100 / totalCount) else 0

    Row(
        modifier = Modifier.gap(0.5.cssRem),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SpanText(
            "$completedCount/$totalCount",
            modifier = Modifier
                .fontSize(0.75.cssRem)
                .color(palette.onSurfaceVariant)
                .styleModifier { property("font-variant-numeric", "tabular-nums") },
        )
        Div(attrs = FileGroupProgressBarTrackStyle.toModifier().toAttrs()) {
            Div(
                attrs = FileGroupProgressBarFillStyle.toModifier()
                    .width(progressPercent.percent)
                    .toAttrs(),
            )
        }
    }
}

@Composable
private fun FileGroupResultsTrailing(completedCount: Int, selectedCount: Int, palette: SitePalette) {
    if (selectedCount == 0) return
    val errorCount = selectedCount - completedCount

    Row(modifier = Modifier.gap(0.35.cssRem)) {
        if (completedCount > 0) {
            SpanText(
                "$completedCount ok",
                modifier = Modifier
                    .fontSize(0.75.cssRem)
                    .color(palette.primary),
            )
        }
        if (errorCount > 0) {
            SpanText(
                "$errorCount errors",
                modifier = Modifier
                    .fontSize(0.75.cssRem)
                    .color(palette.error),
            )
        }
    }
}
