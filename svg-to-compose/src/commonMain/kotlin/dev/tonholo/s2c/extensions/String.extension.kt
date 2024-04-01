package dev.tonholo.s2c.extensions

import kotlin.math.max
import kotlin.math.roundToInt

private const val PERCENT = 100f

private fun String.replaceDividers(): String {
    val pattern = "([_\\-. ])[a-zA-Z0-9]".toRegex()
    return replace(pattern) { it.value.last().uppercase() }
}

fun String.camelCase(): String = replaceDividers()
    .replaceFirstChar { it.lowercaseChar() }

fun String.pascalCase(): String = replaceDividers()
    .replaceFirstChar { it.uppercaseChar() }

fun String.indented(indentSize: Int) = " ".repeat(indentSize) + this

/**
 * Some properties on SVG can receive a Number or a percentage.
 * This extension function helps to extract the percentage, in case of present, and
 * transform in the correct float value.
 *
 * @param width The SVG/AVD width
 * @param height The SVG/AVD height
 * @return The percentage calculation based on the max of [width] and [height],
 * in case of [String] with `%`, or a direct conversion to [Float]
 */
fun String.toLengthFloat(
    width: Float,
    height: Float,
): Float = toLengthFloatOrNull(width, height) ?: throw NumberFormatException("Can't parse '$this' to Float")

fun String.toLengthFloatOrNull(
    width: Float,
    height: Float,
): Float? = if (this.contains("%")) {
    max(width, height) * this.removeSuffix("%").toFloat() / PERCENT
} else {
    this.toFloatOrNull()
}

fun String.toLengthInt(
    width: Int,
    height: Int = width,
): Int = toLengthFloat(width.toFloat(), height.toFloat()).roundToInt()

fun String.toLengthInt(
    height: Int,
): Int = toLengthFloat(height.toFloat(), height.toFloat()).roundToInt()

fun String.toPercentage(): Float {
    val value = this.toFloatOrNull()
    return when {
        value == null && this.endsWith("%") -> this.removeSuffix("%").toInt() / PERCENT
        value != null -> value
        else -> error("Invalid percentage value '$this'")
    }.coerceIn(range = 0f..1f)
}

inline fun String.removeTrailingZero(): String = replace("\\.0\\b".toRegex(), "")
