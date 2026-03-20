package dev.tonholo.s2c.domain.svg

import kotlin.jvm.JvmInline
import kotlin.math.roundToInt

private val possibleUnits = setOf("em", "ex", "px", "in", "cm", "mm", "pt", "pc")
private const val PERCENT = "%"

/**
 * SvgLength is a Kotlin inline value class useful for handling
 * SVG length specifications.
 *
 * @constructor takes a string parameter as the SVG length value,
 * which could be a plain number, or a number that includes length
 * units.
 *
 * See [possibleUnits] to understand all the supported units.
 *
 * @see <a href="https://www.w3.org/TR/SVG11/single-page.html#types-DataTypeLength">
 *  SVG Length definition
 * </a>
 */
@JvmInline
internal value class SvgLength(private val value: String) : Comparable<String> by value {
    /**
     * Method that converts a [SvgLength] to an [Int].
     *
     * It walks through all possible units to find a match.
     *
     * If a match is found, it will remove the unit and return the
     * remaining value as an integer.
     *
     * If no unit is found, it will directly return the value as an
     * integer.
     *
     * The implementation use a [Set] of [String]s to store the possible
     * units to avoid using [Regex] and have performance penalties.
     *
     * @return The [Int] representation of this [SvgLength] object.
     */
    fun toInt(baseDimension: Float): Int = toFloat(baseDimension).roundToInt()
    fun toIntOrNull(baseDimension: Float?): Int? = toFloatOrNull(baseDimension)?.roundToInt()

    fun toInt(baseDimension: Int): Int = toInt(baseDimension.toFloat())
    fun toIntOrNull(baseDimension: Int?): Int? = toIntOrNull(baseDimension?.toFloat())

    fun toDouble(baseDimension: Double): Double = toDoubleOrNull(baseDimension)
        ?: throw NumberFormatException("$value is not a Number")

    fun toDoubleOrNull(baseDimension: Double?): Double? = toFloatingPointOrNull(
        baseDimension = baseDimension,
        toTNumberOrNull = String::toDoubleOrNull,
        toTNumber = String::toDouble,
        calculatePercentage = { dimension, percentValue -> (percentValue / 100.0 * dimension) },
    )

    fun toFloat(baseDimension: Float): Float = toFloatOrNull(baseDimension)
        ?: throw NumberFormatException("$value is not a Number")

    fun toFloatOrNull(baseDimension: Float?): Float? = toFloatingPointOrNull(
        baseDimension = baseDimension,
        toTNumberOrNull = String::toFloatOrNull,
        toTNumber = String::toFloat,
        calculatePercentage = { dimension, percentValue -> (percentValue / 100f * dimension) },
    )

    private fun <TNumber : Number> toFloatingPointOrNull(
        baseDimension: TNumber?,
        toTNumberOrNull: String.() -> TNumber?,
        toTNumber: String.() -> TNumber,
        calculatePercentage: (baseDimension: TNumber, percentValue: TNumber) -> TNumber,
    ): TNumber? {
        val directParse = value.toTNumberOrNull()
        return when {
            directParse != null -> directParse

            value.length > 1 && value.endsWith(PERCENT) && baseDimension != null -> {
                val value = value.removeSuffix(PERCENT)
                calculatePercentage(baseDimension, value.toTNumber())
            }

            value.length > 2 && value.substring(value.length - 2) in possibleUnits ->
                value.substring(0, value.length - 2).toTNumber()

            else -> null
        }
    }
}

/**
 * Extension function on the [String] class to convert it to an [SvgLength]
 * instance or `null` if the conversion fails.
 *
 * The conversion procedure is as follows:
 * 1. If the string can be converted to an [Int] it's interpreted as an SVG
 * length value without any units.
 * 2. If the string ends with any of the possible SVG length units, it's interpreted
 * as an SVG length value with units.
 *
 * In any other case, the conversion will fail and this function will return `null`.
 *
 * @return An instance of [SvgLength] if the conversion is successful or `null`
 * otherwise.
 */
internal fun String.toSvgLengthOrNull(): SvgLength? = when {
    toIntOrNull() != null -> this
    toFloatOrNull() != null -> this
    length > 1 && endsWith(PERCENT) -> this
    length > 2 && substring(length - 2) in possibleUnits -> this
    else -> null
}?.let(::SvgLength)
