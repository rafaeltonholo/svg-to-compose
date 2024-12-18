package dev.tonholo.s2c.extensions

import kotlin.math.max

private const val PERCENT = 100f

/**
 * Replaces dividers in the string with empty strings and capitalizes the following letter.
 *
 * Dividers are considered to be any of the following characters: '_', '-', '.', ' '.
 *
 * For example:
 * "my_string" becomes "myString"
 * "my-string" becomes "myString"
 * "my.string" becomes "myString"
 * "my string" becomes "myString"
 */
private fun String.replaceDividers(): String {
    val pattern = "([_\\-. ])[a-zA-Z0-9]".toRegex()
    return replace(pattern) { it.value.last().uppercase() }
}

/**
 * Converts a string to camel case.
 */
fun String.camelCase(): String = replaceDividers()
    .replaceFirstChar { it.lowercaseChar() }

/**
 * Converts a string to pascal case.
 */
fun String.pascalCase(): String = replaceDividers()
    .replaceFirstChar { it.uppercaseChar() }

@Deprecated(
    message = "Use prependIndent instead",
    replaceWith = ReplaceWith(expression = "this.prependIndent(indentSize)"),
)
fun String.indented(indentSize: Int) = " ".repeat(indentSize) + this

/**
 * Prepends an indent to the string.
 */
fun String.prependIndent(indentSize: Int) = prependIndent(" ".repeat(indentSize))

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

/**
 * Converts a string representation of a length to a float value.
 *
 * The input string can be either a percentage value (e.g., "50%") or a
 * plain float value (e.g., "10.5").
 *
 * If the input string is a percentage value, the result is calculated as
 * a percentage of the maximum between the given width and height.
 *
 * For example, if the input is "50%" and the width is 100 and the height is 200,
 * the result will be 100 (50% of 200).
 *
 * If the input string is a plain float value, the result is the float value parsed from the string.
 *
 * If the input string cannot be parsed to a float value, the result is null.
 *
 * @param width The width to use for percentage calculations.
 * @param height The height to use for percentage calculations.
 * @return The float value represented by the input string, or null if the string cannot be parsed.
 */
fun String.toLengthFloatOrNull(
    width: Float,
    height: Float,
): Float? = if (this.contains("%")) {
    max(width, height) * this.removeSuffix("%").toFloat() / PERCENT
} else {
    this.toFloatOrNull()
}

/**
 * Converts a string to a percentage float value.
 */
fun String.toPercentage(): Float {
    val value = this.toFloatOrNull()
    return when {
        value == null && this.endsWith("%") -> this.removeSuffix("%").toInt() / PERCENT
        value != null -> value
        else -> error("Invalid percentage value '$this'")
    }.coerceIn(range = 0f..1f)
}

/**
 * Removes trailing zeros from a string representing a number.
 */
inline fun String.removeTrailingZero(): String = replace("\\.0\\b".toRegex(), "")
