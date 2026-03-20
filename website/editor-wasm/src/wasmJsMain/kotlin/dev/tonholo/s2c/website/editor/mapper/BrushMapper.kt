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
private val commentRegex = "/\\*.*?\\*/".toRegex()

/** Converts a [ComposeBrush] domain model to a Compose [Brush]. */
internal fun ComposeBrush.toBrush(): Brush? = when (this) {
    is ComposeBrush.SolidColor -> {
        val color = parseColor(ComposeColor(value))
        color?.let { SolidColor(it) }
    }

    is ComposeBrush.Gradient.Linear -> {
        val parsedColors = colors.mapNotNull { parseColor(it) }
        val localStops = stops
        if (!localStops.isNullOrEmpty()) {
            Brush.linearGradient(
                colorStops = localStops.zip(parsedColors).map { (s, c) -> s to c }.toTypedArray(),
                start = start.toOffset(),
                end = end.toOffset(),
                tileMode = tileMode?.toTileMode() ?: TileMode.Clamp,
            )
        } else {
            Brush.linearGradient(
                colors = parsedColors,
                start = start.toOffset(),
                end = end.toOffset(),
                tileMode = tileMode?.toTileMode() ?: TileMode.Clamp,
            )
        }
    }

    is ComposeBrush.Gradient.Radial -> {
        val parsedColors = colors.mapNotNull { parseColor(it) }
        val localStops = stops
        if (!localStops.isNullOrEmpty()) {
            Brush.radialGradient(
                colorStops = localStops.zip(parsedColors).map { (s, c) -> s to c }.toTypedArray(),
                center = center?.toOffset() ?: Offset.Unspecified,
                radius = radius ?: Float.POSITIVE_INFINITY,
                tileMode = tileMode?.toTileMode() ?: TileMode.Clamp,
            )
        } else {
            Brush.radialGradient(
                colors = parsedColors,
                center = center?.toOffset() ?: Offset.Unspecified,
                radius = radius ?: Float.POSITIVE_INFINITY,
                tileMode = tileMode?.toTileMode() ?: TileMode.Clamp,
            )
        }
    }

    is ComposeBrush.Gradient.Sweep -> {
        val parsedColors = colors.mapNotNull { parseColor(it) }
        val localStops = stops
        if (!localStops.isNullOrEmpty()) {
            Brush.sweepGradient(
                colorStops = localStops.zip(parsedColors).map { (s, c) -> s to c }.toTypedArray(),
                center = center?.toOffset() ?: Offset.Unspecified,
            )
        } else {
            Brush.sweepGradient(
                colors = parsedColors,
                center = center?.toOffset() ?: Offset.Unspecified,
            )
        }
    }
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
