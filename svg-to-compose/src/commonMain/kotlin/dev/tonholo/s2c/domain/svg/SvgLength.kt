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

    /**
     * Converts this [SvgLength] to a [Double] value.
     *
     * This method attempts to parse the [SvgLength] value and convert it to a [Double].
     * It handles various SVG length units and converts them appropriately based on the
     * provided base dimension.
     * If the value cannot be converted to a valid number, a NumberFormatException is
     * thrown.
     *
     * @param baseDimension The base dimension value used for relative unit calculations
     *  (e.g., percentage values)
     * @return The [Double] representation of this [SvgLength] object
     * @throws NumberFormatException if the value cannot be converted to a valid [Double]
     */
    fun toDouble(baseDimension: Double): Double = toDoubleOrNull(baseDimension)
        ?: throw NumberFormatException("$value is not a Number")

    /**
     * Converts this [SvgLength] to a [Double] value, or returns null if the conversion fails.
     *
     * This method attempts to parse the [SvgLength] value and convert it to a [Double].
     * It handles various SVG length units and converts them appropriately based on the
     * provided base dimension. If the value cannot be converted to a valid number, null
     * is returned instead of throwing an exception.
     *
     * @param baseDimension The base dimension value used for relative unit calculations
     *  (e.g., percentage values). If null, percentage-based conversions will return null.
     * @return The [Double] representation of this [SvgLength] object, or null if the
     *  conversion fails or is not possible
     */
    fun toDoubleOrNull(baseDimension: Double?): Double? = toFloatingPointOrNull(
        baseDimension = baseDimension,
        toTNumberOrNull = String::toDoubleOrNull,
        calculatePercentage = { dimension, percentValue -> (percentValue / 100.0 * dimension) },
    )

    /**
     * Converts this [SvgLength] to a [Float] value.
     *
     * This method attempts to parse the [SvgLength] value and convert it to a [Float].
     * It handles various SVG length units and converts them appropriately based on the
     * provided base dimension.
     * If the value cannot be converted to a valid number, a NumberFormatException is thrown.
     *
     * @param baseDimension The base dimension value used for relative unit calculations
     *  (e.g., percentage values)
     * @return The [Float] representation of this [SvgLength] object
     * @throws NumberFormatException if the value cannot be converted to a valid [Float]
     */
    fun toFloat(baseDimension: Float): Float = toFloatOrNull(baseDimension)
        ?: throw NumberFormatException("$value is not a Number")

    /**
     * Converts this [SvgLength] to a [Float] value, or returns null if the conversion fails.
     *
     * This method attempts to parse the [SvgLength] value and convert it to a [Float].
     * It handles various SVG length units and converts them appropriately based on the
     * provided base dimension. If the value cannot be converted to a valid number, null
     * is returned instead of throwing an exception.
     *
     * @param baseDimension The base dimension value used for relative unit calculations
     *  (e.g., percentage values). If null, percentage-based conversions will return null.
     * @return The [Float] representation of this [SvgLength] object, or null if the
     *  conversion fails or is not possible
     */
    fun toFloatOrNull(baseDimension: Float?): Float? = toFloatingPointOrNull(
        baseDimension = baseDimension,
        toTNumberOrNull = String::toFloatOrNull,
        calculatePercentage = { dimension, percentValue -> (percentValue / 100f * dimension) },
    )

    /**
     * Converts this SvgLength to a floating-point number type, or returns null if the conversion fails.
     * 
     * This generic method handles the conversion of SVG length values to floating-point number types
     * ([Float] or [Double]). It supports direct numeric values, percentage-based values, and values with
     * SVG length unit suffixes. The conversion process attempts multiple parsing strategies in order
     * of precedence: direct numeric parsing, percentage calculation (if a base dimension is provided),
     * and unit suffix removal.
     *
     * @param baseDimension The base dimension value used for percentage calculations. If null,
     *  percentage-based conversions will return null.
     * @param toTNumberOrNull A function that attempts to convert a String to TNumber, returning null
     *  if the conversion fails
     * @param calculatePercentage A function that calculates the percentage value based on the base
     *  dimension and the parsed percentage value
     * @return The TNumber representation of this SvgLength object, or null if the conversion fails
     *  or is not possible
     */
    private fun <TNumber : Number> toFloatingPointOrNull(
        baseDimension: TNumber?,
        toTNumberOrNull: String.() -> TNumber?,
        calculatePercentage: (baseDimension: TNumber, percentValue: TNumber) -> TNumber,
    ): TNumber? {
        val directParse = value.toTNumberOrNull()
        return when {
            directParse != null -> directParse

            value.length > 1 && value.endsWith(PERCENT) && baseDimension != null -> {
                val value = value.removeSuffix(PERCENT)
                value.toTNumberOrNull()?.let { calculatePercentage(baseDimension, it) }
            }

            value.length > 2 && value.substring(value.length - 2) in possibleUnits ->
                value.substring(0, value.length - 2).toTNumberOrNull()

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
