package dev.tonholo.s2c.website.components.organisms.playground

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.ariaLabel
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flex
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.opacity
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
import com.varabyte.kobweb.silk.components.icons.fa.FaDownload
import com.varabyte.kobweb.silk.components.icons.fa.FaPlay
import com.varabyte.kobweb.silk.components.icons.fa.FaRotate
import com.varabyte.kobweb.silk.components.icons.fa.FaSpinner
import com.varabyte.kobweb.silk.components.icons.fa.FaSquare
import com.varabyte.kobweb.silk.components.icons.fa.FaSquareCheck
import com.varabyte.kobweb.silk.components.icons.fa.FaStop
import com.varabyte.kobweb.silk.components.icons.fa.FaXmark
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.components.molecules.playground.SpinnerIconStyle
import dev.tonholo.s2c.website.state.playground.BatchPhase
import dev.tonholo.s2c.website.theme.SitePalette
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.theme.toSitePalette
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import kotlin.js.Date

private const val HEADER_Z_INDEX = 10

val BatchPhaseHeaderStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .fillMaxWidth()
        .backgroundColor(palette.surfaceVariant)
        .padding(topBottom = 0.625.cssRem, leftRight = SiteTheme.dimensions.size.Lg)
        .position(Position.Sticky)
        .top(0.px)
        .zIndex(HEADER_Z_INDEX)
}

val BatchPhaseProgressBarContainerStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .fillMaxWidth()
        .height(4.px)
        .backgroundColor(palette.outlineVariant)
        .borderRadius(2.px)
}

val BatchPhaseProgressBarFillStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .height(4.px)
        .backgroundColor(palette.primary)
        .borderRadius(2.px)
}

val BatchPhasePrimaryButtonStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .backgroundColor(palette.primary)
        .color(palette.onPrimary)
        .border(0.px, LineStyle.None, palette.primary)
        .borderRadius(0.375.cssRem)
        .padding(topBottom = SiteTheme.dimensions.size.Sm, leftRight = SiteTheme.dimensions.size.Md)
        .fontSize(0.75.cssRem)
        .fontWeight(FontWeight.Medium)
        .cursor(Cursor.Pointer)
        .transition(
            Transition.of("box-shadow", duration = 200.ms, timingFunction = TransitionTimingFunction.Ease),
        )
}

val BatchPhaseSecondaryButtonStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .backgroundColor(palette.surface)
        .color(palette.onSurface)
        .border(1.px, LineStyle.Solid, palette.outline)
        .borderRadius(0.375.cssRem)
        .padding(topBottom = SiteTheme.dimensions.size.Sm, leftRight = SiteTheme.dimensions.size.Md)
        .fontSize(0.75.cssRem)
        .fontWeight(FontWeight.Medium)
        .cursor(Cursor.Pointer)
        .transition(
            Transition.of("box-shadow", duration = 200.ms, timingFunction = TransitionTimingFunction.Ease),
        )
}

/**
 * Sticky header bar at the top of the batch panel that adapts its content
 * to the current [BatchPhase]:
 * - [BatchPhase.Select]: select-all checkbox, file count, and start button.
 * - [BatchPhase.Converting]: progress text, progress bar, and cancel button.
 * - [BatchPhase.Results]: summary text and download/restart/clear buttons.
 */
@Composable
internal fun BatchPhaseHeader(
    phase: BatchPhase,
    totalFiles: Int,
    selectedCount: Int,
    allSelected: Boolean,
    modifier: Modifier = Modifier,
    onToggleSelectAll: () -> Unit = {},
    onStartConversion: () -> Unit = {},
    onCancel: () -> Unit = {},
    onDownload: () -> Unit = {},
    onRestart: () -> Unit = {},
    onClear: () -> Unit = {},
) {
    Column(
        modifier = BatchPhaseHeaderStyle.toModifier().then(modifier).gap(SiteTheme.dimensions.size.Sm),
    ) {
        when (phase) {
            BatchPhase.Select -> SelectPhaseContent(
                totalFiles = totalFiles,
                selectedCount = selectedCount,
                allSelected = allSelected,
                onToggleSelectAll = onToggleSelectAll,
                onStartConversion = onStartConversion,
            )

            is BatchPhase.Converting -> ConvertingPhaseContent(
                phase = phase,
                onCancel = onCancel,
            )

            is BatchPhase.Results -> ResultsPhaseContent(
                phase = phase,
                totalFiles = totalFiles,
                onDownload = onDownload,
                onRestart = onRestart,
                onClear = onClear,
            )
        }
    }
}

@Composable
private fun SelectPhaseContent(
    totalFiles: Int,
    selectedCount: Int,
    allSelected: Boolean,
    onToggleSelectAll: () -> Unit,
    onStartConversion: () -> Unit,
) {
    val palette = ColorMode.current.toSitePalette()
    val canStart = selectedCount > 0

    Row(
        modifier = Modifier.fillMaxWidth().gap(0.625.cssRem),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SelectAllCheckbox(
            allSelected = allSelected,
            palette = palette,
            onToggleSelectAll = onToggleSelectAll,
        )
        SpanText(
            if (selectedCount == 0) {
                "$totalFiles files found \u2014 select files to convert"
            } else {
                "$totalFiles files found ($selectedCount selected)"
            },
            modifier = Modifier
                .flex(1)
                .fontSize(0.8.cssRem)
                .color(palette.onSurfaceVariant),
        )
        Button(
            attrs = BatchPhasePrimaryButtonStyle.toModifier()
                .let { if (!canStart) it.opacity(value = 0.4f).cursor(Cursor.Default) else it }
                .ariaLabel("Start conversion of $selectedCount selected files")
                .onClick { if (canStart) onStartConversion() }
                .toAttrs(),
        ) {
            Row(
                modifier = Modifier.gap(0.35.cssRem),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                FaPlay(modifier = Modifier.fontSize(0.7.cssRem))
                SpanText("Start Conversion")
            }
        }
    }
}

@Composable
private fun SelectAllCheckbox(allSelected: Boolean, palette: SitePalette, onToggleSelectAll: () -> Unit) {
    Div(
        attrs = Modifier
            .cursor(Cursor.Pointer)
            .color(if (allSelected) palette.primary else palette.onSurfaceVariant)
            .fontSize(1.cssRem)
            .borderRadius(0.25.cssRem)
            .role("checkbox")
            .tabIndex(0)
            .ariaLabel(if (allSelected) "Deselect all files" else "Select all files")
            .onClick { onToggleSelectAll() }
            .toAttrs {
                attr("aria-checked", allSelected.toString())
            },
    ) {
        if (allSelected) {
            FaSquareCheck()
        } else {
            FaSquare()
        }
    }
}

@Composable
private fun ConvertingPhaseContent(phase: BatchPhase.Converting, onCancel: () -> Unit) {
    val palette = ColorMode.current.toSitePalette()
    val completedCount = phase.completedCount
    val total = phase.total
    val percentage = if (total > 0) (completedCount * 100) / total else 0
    val elapsedText = rememberElapsedTime(phase.startTimeMs)

    Column(modifier = Modifier.fillMaxWidth().gap(SiteTheme.dimensions.size.Sm)) {
        Row(
            modifier = Modifier.fillMaxWidth().gap(0.625.cssRem),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            FaSpinner(modifier = SpinnerIconStyle.toModifier())
            SpanText(
                "Converting... $completedCount / $total — $elapsedText",
                modifier = Modifier
                    .flex(1)
                    .fontSize(0.8.cssRem)
                    .fontWeight(FontWeight.Medium)
                    .color(palette.onSurfaceVariant)
                    .styleModifier { property("font-variant-numeric", "tabular-nums") },
            )
            CancelButton(isCancelling = phase.cancelling, onCancel = onCancel)
        }

        BatchProgressBar(percentage = percentage)
    }
}

@Composable
private fun CancelButton(isCancelling: Boolean, onCancel: () -> Unit) {
    Button(
        attrs = BatchPhaseSecondaryButtonStyle.toModifier()
            .let { if (isCancelling) it.opacity(value = 0.6f).cursor(Cursor.Default) else it }
            .ariaLabel(if (isCancelling) "Cancelling conversion" else "Cancel conversion")
            .onClick { if (!isCancelling) onCancel() }
            .toAttrs(),
    ) {
        Row(
            modifier = Modifier.gap(0.35.cssRem),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            FaStop(modifier = Modifier.fontSize(0.7.cssRem))
            SpanText(if (isCancelling) "Cancelling..." else "Cancel")
        }
    }
}

@Composable
private fun BatchProgressBar(percentage: Int) {
    Div(
        attrs = BatchPhaseProgressBarContainerStyle.toModifier()
            .ariaLabel("Conversion progress")
            .toAttrs {
                attr("role", "progressbar")
                attr("aria-valuenow", percentage.toString())
                attr("aria-valuemin", "0")
                attr("aria-valuemax", "100")
            },
    ) {
        Div(
            attrs = BatchPhaseProgressBarFillStyle.toModifier()
                .width(percentage.percent)
                .toAttrs(),
        )
    }
}

@Composable
private fun ResultsPhaseContent(
    phase: BatchPhase.Results,
    totalFiles: Int,
    onDownload: () -> Unit,
    onRestart: () -> Unit,
    onClear: () -> Unit,
) {
    val palette = ColorMode.current.toSitePalette()
    val successCount = phase.completed.count { it.isSuccess }
    val failureCount = phase.completed.size - successCount

    val duration = formatDuration(phase.durationMs)
    val summaryText = if (phase.cancelled) {
        "Cancelled — ${phase.completed.size} of $totalFiles completed in $duration"
    } else {
        "Conversion complete — $successCount succeeded, $failureCount failed in $duration"
    }

    Row(
        modifier = Modifier.fillMaxWidth().gap(SiteTheme.dimensions.size.Sm),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        SpanText(
            summaryText,
            modifier = Modifier
                .flex(1)
                .fontSize(0.8.cssRem)
                .fontWeight(FontWeight.Medium)
                .color(palette.onSurfaceVariant),
        )
        ResultsActionButtons(
            phase = phase,
            successCount = successCount,
            onDownload = onDownload,
            onRestart = onRestart,
            onClear = onClear,
        )
    }
}

@Composable
private fun ResultsActionButtons(
    phase: BatchPhase.Results,
    successCount: Int,
    onDownload: () -> Unit,
    onRestart: () -> Unit,
    onClear: () -> Unit,
) {
    Row(modifier = Modifier.gap(SiteTheme.dimensions.size.Sm), verticalAlignment = Alignment.CenterVertically) {
        if (successCount > 0) {
            Button(
                attrs = BatchPhasePrimaryButtonStyle.toModifier()
                    .ariaLabel(if (phase.cancelled) "Download partial results" else "Download all converted files")
                    .onClick { onDownload() }
                    .toAttrs(),
            ) {
                Row(
                    modifier = Modifier.gap(0.35.cssRem),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    FaDownload(modifier = Modifier.fontSize(0.7.cssRem))
                    SpanText(if (phase.cancelled) "Download Partial" else "Download All")
                }
            }
        }
        if (phase.cancelled) {
            Button(
                attrs = BatchPhaseSecondaryButtonStyle.toModifier()
                    .ariaLabel("Restart batch conversion")
                    .onClick { onRestart() }
                    .toAttrs(),
            ) {
                Row(
                    modifier = Modifier.gap(0.35.cssRem),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    FaRotate(modifier = Modifier.fontSize(0.7.cssRem))
                    SpanText("Restart")
                }
            }
        }
        Button(
            attrs = BatchPhaseSecondaryButtonStyle.toModifier()
                .ariaLabel("Clear batch results")
                .onClick { onClear() }
                .toAttrs(),
        ) {
            Row(
                modifier = Modifier.gap(0.35.cssRem),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                FaXmark(modifier = Modifier.fontSize(0.7.cssRem))
                SpanText("Clear")
            }
        }
    }
}

private const val MILLIS_PER_SECOND = 1000L
private const val SECONDS_PER_MINUTE = 60
private const val TIMER_TICK_MS = 1000L

/**
 * Formats a duration in milliseconds as "Xs", "Xm Ys", or "Xh Ym Zs".
 */
private fun formatDuration(ms: Double): String {
    val totalSeconds = (ms / MILLIS_PER_SECOND).toLong()
    val hours = totalSeconds / (SECONDS_PER_MINUTE * SECONDS_PER_MINUTE)
    val minutes = (totalSeconds % (SECONDS_PER_MINUTE * SECONDS_PER_MINUTE)) / SECONDS_PER_MINUTE
    val seconds = totalSeconds % SECONDS_PER_MINUTE
    return buildString {
        if (hours > 0) append("${hours}h ")
        if (minutes > 0 || hours > 0) append("${minutes}m ")
        append("${seconds}s")
    }
}

/**
 * Live elapsed time ticker that updates every second.
 */
@Composable
private fun rememberElapsedTime(startTimeMs: Double): String {
    var elapsed by remember { mutableStateOf(Date.now() - startTimeMs) }
    LaunchedEffect(startTimeMs) {
        while (true) {
            elapsed = Date.now() - startTimeMs
            delay(TIMER_TICK_MS)
        }
    }
    return formatDuration(elapsed)
}
