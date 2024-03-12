package dev.tonholo.s2c.extensions

import kotlin.math.max
import kotlin.math.roundToInt

private const val FULL_HEXADECIMAL_COLOR_SIZE = 6
private const val HALF_HEXADECIMAL_COLOR_SIZE = 3
private const val PERCENT = 100f
private const val RGBA_PREFIX = "RGBA"
private const val RGBA_SIZE = 4
private const val RGB_PREFIX = "RGB"
private const val RGB_SIZE = 3
private const val RGB_MAX_VALUE = 255

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
): Float = if (this.contains("%")) {
    max(width, height) * this.removeSuffix("%").toInt() / PERCENT
} else {
    this.toFloat()
}

/**
 * Converts a hexadecimal color into a SolidColor brush
 */
fun String.toComposeColor(): String? = uppercase()
    .removePrefix("#")
    .let {
        when {
            it.startsWith(RGBA_PREFIX) || it.startsWith(RGB_PREFIX) -> {
                val values = it
                    .removePrefix("$RGBA_PREFIX(")
                    .removePrefix("$RGB_PREFIX(")
                    .removeSuffix(")")
                    .split(", ", ",")
                when (values.size) {
                    RGBA_SIZE -> {
                        values.foldIndexed("") { index, acc, value ->
                            if (index == values.lastIndex) {
                                (value.toFloat() * RGB_MAX_VALUE).roundToInt().toString(radix = 16) + acc
                            } else {
                                acc + value.toInt().toString(radix = 16)
                            }
                        }.uppercase()
                    }

                    RGB_SIZE -> {
                        values.fold("") { acc, value -> acc + value.toInt().toString(radix = 16) }
                    }

                    else -> "none"
                }
            }

            it.length == FULL_HEXADECIMAL_COLOR_SIZE -> "FF$it"
            it.length == HALF_HEXADECIMAL_COLOR_SIZE -> "FF$it$it"
            it.isEmpty() || it.lowercase().contains("url") ->
                "FF000000 /* not supported this \"$it\" */" // not supported yet.
            else -> it
        }
    }
    .takeIf { it.lowercase() != "none" }
    ?.let { "SolidColor(Color(0x$it))" }

inline fun String.removeTrailingZero(): String = replace("\\.0\\b".toRegex(), "")
