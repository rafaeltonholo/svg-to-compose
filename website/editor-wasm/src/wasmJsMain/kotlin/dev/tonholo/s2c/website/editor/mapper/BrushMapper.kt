package dev.tonholo.s2c.website.editor.mapper

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.TileMode
import dev.tonholo.s2c.domain.compose.ComposeBrush
import dev.tonholo.s2c.domain.compose.ComposeColor
import dev.tonholo.s2c.domain.compose.GradientTileMode

private const val HEX_RADIX = 16

/**
 * Compose's gradient brush factories require at least two colors. SVG gradients
 * with unresolved `xlink:href` chains or no stops at all would otherwise crash
 * the preview renderer with `IllegalArgumentException: colors must have length
 * of at least 2 if colorStops is omitted`. See issue #315.
 */
private const val MIN_GRADIENT_COLORS = 2

private val commentRegex = "/\\*.*?\\*/".toRegex()

/** Converts a [ComposeBrush] domain model to a Compose [Brush]. */
internal fun ComposeBrush.toBrush(): Brush? = when (this) {
    is ComposeBrush.SolidColor -> parseColor(ComposeColor(value))?.let(::SolidColor)
    is ComposeBrush.Gradient.Linear -> toLinearBrush()
    is ComposeBrush.Gradient.Radial -> toRadialBrush()
    is ComposeBrush.Gradient.Sweep -> toSweepBrush()
}

private fun ComposeBrush.Gradient.Linear.toLinearBrush(): Brush? {
    val parsedColors = colors.mapNotNull { parseColor(it) }
    if (parsedColors.size < MIN_GRADIENT_COLORS) return null

    val resolvedTileMode = tileMode?.toTileMode() ?: TileMode.Clamp
    val pairedStops = stops.toUsableColorStops(colors)
    return if (pairedStops != null) {
        Brush.linearGradient(
            colorStops = pairedStops,
            start = start.toOffset(),
            end = end.toOffset(),
            tileMode = resolvedTileMode,
        )
    } else {
        Brush.linearGradient(
            colors = parsedColors,
            start = start.toOffset(),
            end = end.toOffset(),
            tileMode = resolvedTileMode,
        )
    }
}

private fun ComposeBrush.Gradient.Radial.toRadialBrush(): Brush? {
    val parsedColors = colors.mapNotNull { parseColor(it) }
    if (parsedColors.size < MIN_GRADIENT_COLORS) return null

    val resolvedCenter = center?.toOffset() ?: Offset.Unspecified
    val resolvedRadius = radius ?: Float.POSITIVE_INFINITY
    val resolvedTileMode = tileMode?.toTileMode() ?: TileMode.Clamp
    val pairedStops = stops.toUsableColorStops(colors)
    return if (pairedStops != null) {
        Brush.radialGradient(
            colorStops = pairedStops,
            center = resolvedCenter,
            radius = resolvedRadius,
            tileMode = resolvedTileMode,
        )
    } else {
        Brush.radialGradient(
            colors = parsedColors,
            center = resolvedCenter,
            radius = resolvedRadius,
            tileMode = resolvedTileMode,
        )
    }
}

private fun ComposeBrush.Gradient.Sweep.toSweepBrush(): Brush? {
    val parsedColors = colors.mapNotNull { parseColor(it) }
    if (parsedColors.size < MIN_GRADIENT_COLORS) return null

    val resolvedCenter = center?.toOffset() ?: Offset.Unspecified
    val pairedStops = stops.toUsableColorStops(colors)
    return if (pairedStops != null) {
        Brush.sweepGradient(
            colorStops = pairedStops,
            center = resolvedCenter,
        )
    } else {
        Brush.sweepGradient(
            colors = parsedColors,
            center = resolvedCenter,
        )
    }
}

/**
 * Pairs each stop with its original color before parsing, so unparseable colors
 * drop their stop too instead of shifting downstream stops onto the wrong color.
 * Returns null when the stops list is missing or fewer than [MIN_GRADIENT_COLORS]
 * pairs survive parsing, signalling the caller to fall back to the colors-only
 * overload.
 */
private fun List<Float>?.toUsableColorStops(rawColors: List<ComposeColor>): Array<Pair<Float, Color>>? {
    if (this.isNullOrEmpty()) return null
    val pairs = zip(rawColors)
        .mapNotNull { (stop, rawColor) -> parseColor(rawColor)?.let { stop to it } }
    if (pairs.size < MIN_GRADIENT_COLORS) return null
    return pairs.toTypedArray()
}

/** Parses a [ComposeColor] hex string into a Compose [Color], returning null for unparseable values. */
private fun parseColor(composeColor: ComposeColor): Color? {
    val hex = composeColor.color
    if (hex.lowercase() == "none") return null
    return try {
        val cleaned = hex.replace(commentRegex, "").trim()
        Color(cleaned.toLong(HEX_RADIX).toInt())
    } catch (@Suppress("TooGenericExceptionCaught") _: Exception) {
        // Return null so the brush factory can skip unparseable colors
        // rather than silently rendering black.
        null
    }
}

/** Converts a [GradientTileMode] domain value to a Compose [TileMode]. */
private fun GradientTileMode.toTileMode(): TileMode = when (value.lowercase()) {
    "clamp" -> TileMode.Clamp
    "repeated", "repeat" -> TileMode.Repeated
    "mirror" -> TileMode.Mirror
    "decal" -> TileMode.Decal
    else -> TileMode.Clamp
}
