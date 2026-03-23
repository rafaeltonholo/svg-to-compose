package dev.tonholo.s2c.website.components.molecules.playground

import androidx.compose.runtime.Composable
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
import com.varabyte.kobweb.compose.ui.modifiers.borderBottom
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flex
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.role
import com.varabyte.kobweb.compose.ui.modifiers.tabIndex
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.silk.components.icons.fa.FaCheck
import com.varabyte.kobweb.silk.components.icons.fa.FaCircleExclamation
import com.varabyte.kobweb.silk.components.icons.fa.FaFile
import com.varabyte.kobweb.silk.components.icons.fa.FaSpinner
import com.varabyte.kobweb.silk.components.icons.fa.FaSquare
import com.varabyte.kobweb.silk.components.icons.fa.FaSquareCheck
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.state.playground.BatchConversionResult
import dev.tonholo.s2c.website.state.playground.BatchPhase
import dev.tonholo.s2c.website.state.playground.UploadedFileInfo
import dev.tonholo.s2c.website.theme.SitePalette
import dev.tonholo.s2c.website.theme.toSitePalette
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px

private fun Modifier.fileRowCommon(palette: SitePalette): Modifier = this then Modifier
    .fillMaxWidth()
    .padding(topBottom = 0.625.cssRem, leftRight = 0.625.cssRem)
    .borderBottom {
        width(1.px)
        style(LineStyle.Solid)
        color(palette.outline.toRgb().copyf(alpha = 0.5f))
    }
    .backgroundColor(palette.surface)
    .transition(
        Transition.of("background-color", duration = 150.ms, timingFunction = TransitionTimingFunction.Ease),
    )

val FileRowStyle = CssStyle {
    base {
        val palette = colorMode.toSitePalette()
        Modifier.fileRowCommon(palette)
    }
    hover {
        Modifier.backgroundColor(colorMode.toSitePalette().surfaceVariant)
    }
}

val FileRowClickableStyle = CssStyle {
    base {
        val palette = colorMode.toSitePalette()
        Modifier
            .fileRowCommon(palette)
            .cursor(Cursor.Pointer)
    }
    hover {
        Modifier.backgroundColor(colorMode.toSitePalette().surfaceVariant)
    }
}

val FileExtensionBadgeStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .backgroundColor(palette.primaryContainer)
        .color(palette.onPrimary)
        .borderRadius(0.25.cssRem)
        .padding(topBottom = 0.15.cssRem, leftRight = 0.4.cssRem)
        .fontSize(0.75.cssRem)
        .fontWeight(FontWeight.SemiBold)
}

val FileRowErrorTextStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .fontSize(0.7.cssRem)
        .color(palette.error)
}

val FileRowCheckboxStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .fontSize(0.9.cssRem)
        .color(palette.primary)
        .cursor(Cursor.Pointer)
}

val FileRowUncheckedStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .fontSize(0.9.cssRem)
        .color(palette.outlineVariant)
        .cursor(Cursor.Pointer)
}

val FileRowNameStyle = CssStyle.base {
    Modifier
        .flex(1)
        .fontSize(0.8.cssRem)
        .fontWeight(FontWeight.Medium)
}

/**
 * Renders a single file row in the batch file list.
 *
 * The appearance varies by [phase]:
 * - [BatchPhase.Select]: checkbox + file icon + file name + extension badge
 * - [BatchPhase.Converting]: status icon + file name + status text (no checkbox)
 * - [BatchPhase.Results]: status icon + file name, clickable to inspect if result available
 */
@Composable
internal fun FileRow(
    file: UploadedFileInfo,
    phase: BatchPhase,
    isSelected: Boolean,
    conversionResult: BatchConversionResult?,
    isCurrentlyConverting: Boolean,
    onToggleSelection: () -> Unit,
    onInspect: ((BatchConversionResult) -> Unit)?,
    modifier: Modifier = Modifier,
) {
    when (phase) {
        is BatchPhase.Select -> FileRowSelectPhase(
            file = file,
            isSelected = isSelected,
            onToggleSelection = onToggleSelection,
            modifier = modifier,
        )

        is BatchPhase.Converting -> FileRowConvertingPhase(
            file = file,
            conversionResult = conversionResult,
            isCurrentlyConverting = isCurrentlyConverting,
            modifier = modifier,
        )

        is BatchPhase.Results -> FileRowResultsPhase(
            file = file,
            conversionResult = conversionResult,
            onInspect = onInspect,
            modifier = modifier,
        )
    }
}

@Composable
private fun FileRowSelectPhase(
    file: UploadedFileInfo,
    isSelected: Boolean,
    onToggleSelection: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val palette = ColorMode.current.toSitePalette()

    Row(
        modifier = FileRowStyle.toModifier()
            .then(modifier)
            .onClick { onToggleSelection() }
            .cursor(Cursor.Pointer)
            .tabIndex(0)
            .role("checkbox")
            .ariaLabel("Select ${file.name}"),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(0.5.cssRem),
    ) {
        if (isSelected) {
            FaSquareCheck(modifier = FileRowCheckboxStyle.toModifier())
        } else {
            FaSquare(modifier = FileRowUncheckedStyle.toModifier())
        }

        FaFile(
            modifier = Modifier
                .fontSize(0.8.cssRem)
                .color(palette.onSurfaceVariant),
        )

        SpanText(
            file.name,
            modifier = FileRowNameStyle.toModifier(),
        )

        FileExtensionBadge(file.detectedExtension)
    }
}

@Composable
private fun FileRowConvertingPhase(
    file: UploadedFileInfo,
    conversionResult: BatchConversionResult?,
    isCurrentlyConverting: Boolean,
    modifier: Modifier = Modifier,
) {
    val palette = ColorMode.current.toSitePalette()

    Row(
        modifier = FileRowStyle.toModifier().then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(0.5.cssRem),
    ) {
        when {
            isCurrentlyConverting -> FaSpinner(
                modifier = SpinnerIconStyle.toModifier()
                    .fontSize(0.8.cssRem)
                    .color(palette.primary),
            )

            conversionResult != null && conversionResult.isSuccess -> FaCheck(
                modifier = Modifier
                    .fontSize(0.8.cssRem)
                    .color(palette.primary),
            )

            conversionResult != null && conversionResult.error != null -> FaCircleExclamation(
                modifier = Modifier
                    .fontSize(0.8.cssRem)
                    .color(palette.error),
            )

            else -> FaSquare(
                modifier = Modifier
                    .fontSize(0.5.cssRem)
                    .color(palette.outlineVariant),
            )
        }

        Column(modifier = Modifier.flex(1).gap(0.1.cssRem)) {
            SpanText(
                file.name,
                modifier = FileRowNameStyle.toModifier(),
            )
            ConvertingStatusText(
                conversionResult = conversionResult,
                isCurrentlyConverting = isCurrentlyConverting,
            )
        }
    }
}

@Composable
private fun ConvertingStatusText(conversionResult: BatchConversionResult?, isCurrentlyConverting: Boolean) {
    val palette = ColorMode.current.toSitePalette()
    val statusText = when {
        isCurrentlyConverting -> "Converting..."
        conversionResult != null && conversionResult.isSuccess -> "Done"
        conversionResult != null -> conversionResult.error ?: "Conversion failed"
        else -> "Waiting..."
    }
    val statusColor = when {
        conversionResult != null && conversionResult.error != null -> palette.error
        conversionResult != null && conversionResult.isSuccess -> palette.primary
        else -> palette.onSurfaceVariant
    }

    SpanText(
        statusText,
        modifier = Modifier
            .fontSize(0.7.cssRem)
            .color(statusColor),
    )
}

@Composable
private fun FileRowResultsPhase(
    file: UploadedFileInfo,
    conversionResult: BatchConversionResult?,
    onInspect: ((BatchConversionResult) -> Unit)?,
    modifier: Modifier = Modifier,
) {
    val palette = ColorMode.current.toSitePalette()
    val isClickable = conversionResult != null && onInspect != null
    val baseModifier = if (isClickable) {
        FileRowClickableStyle.toModifier()
    } else {
        FileRowStyle.toModifier()
    }
    val rowModifier = if (isClickable && conversionResult != null) {
        baseModifier
            .then(modifier)
            .onClick { onInspect?.invoke(conversionResult) }
            .tabIndex(0)
            .role("button")
            .ariaLabel("Inspect ${file.name}")
    } else {
        baseModifier.then(modifier)
    }

    Row(
        modifier = rowModifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(0.5.cssRem),
    ) {
        ResultStatusIcon(conversionResult = conversionResult, palette = palette)

        Column(modifier = Modifier.flex(1).gap(0.1.cssRem)) {
            SpanText(
                file.name,
                modifier = FileRowNameStyle.toModifier().let { nameModifier ->
                    if (isClickable) nameModifier.color(palette.primary) else nameModifier
                },
            )
            ResultErrorText(conversionResult = conversionResult)
        }
    }
}

@Composable
private fun ResultStatusIcon(conversionResult: BatchConversionResult?, palette: SitePalette) {
    when {
        conversionResult != null && conversionResult.isSuccess -> FaCheck(
            modifier = Modifier
                .fontSize(0.8.cssRem)
                .color(palette.primary),
        )

        conversionResult != null && conversionResult.error != null -> FaCircleExclamation(
            modifier = Modifier
                .fontSize(0.8.cssRem)
                .color(palette.error),
        )

        else -> FaSquare(
            modifier = Modifier
                .fontSize(0.5.cssRem)
                .color(palette.outlineVariant),
        )
    }
}

@Composable
private fun ResultErrorText(conversionResult: BatchConversionResult?) {
    if (conversionResult != null && conversionResult.error != null) {
        SpanText(
            conversionResult.error,
            modifier = FileRowErrorTextStyle.toModifier(),
        )
    }
}

@Composable
private fun FileExtensionBadge(extension: String) {
    SpanText(
        extension.uppercase(),
        modifier = FileExtensionBadgeStyle.toModifier(),
    )
}
