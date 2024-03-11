package dev.tonholo.s2c.domain

import dev.tonholo.s2c.domain.PathFillType.Companion.EvenOdd
import dev.tonholo.s2c.domain.PathFillType.Companion.NonZero
import dev.tonholo.s2c.domain.StrokeCap.Companion.Butt
import dev.tonholo.s2c.domain.StrokeCap.Companion.Round
import dev.tonholo.s2c.domain.StrokeCap.Companion.Square
import dev.tonholo.s2c.domain.StrokeJoin.Companion.Bevel
import dev.tonholo.s2c.domain.StrokeJoin.Companion.Miter
import dev.tonholo.s2c.domain.StrokeJoin.Companion.Round
import kotlin.jvm.JvmInline

/**
 * Represents a property that can be included translated to a compose
 * declaration.
 *
 * All classes or objects which represent a compose-declarable property
 * should implement this interface.
 *
 * Example implementing classes in the LinkedList module include
 * [PathFillType], [StrokeCap], [StrokeJoin] etc.
 */
interface ComposeProperty {
    /**
     * Provides a way to get the canonical string representation of the
     * [ComposeProperty] implementation.
     *
     * In case the conversion is not feasible or does not make sense, the
     * function should return a `null` value.
     *
     * This can be used when generating the compose code using
     * this object.
     *
     * @return the Compose Property translation, or `null` in case of
     * failure to translate the value.
     */
    fun toCompose(): String?
}

/**
 * This is a wrapper class for the [PathFillType] property of the
 * Compose UI library.
 *
 * It encapsulates the value of the [PathFillType] as a string and
 * provides utility methods for conversion and string representation.
 *
 * The class is defined as an inline class to minimise memory overhead.
 *
 * There are two predefined instances available: [EvenOdd] and [NonZero],
 * representing the two supported fill types in Android Compose.
 *
 * The [toString] method is overridden to return the encapsulated
 * string value.
 */
@JvmInline
value class PathFillType private constructor(private val value: String) : ComposeProperty {
    override fun toString(): String = value

    companion object {
        const val IMPORT = "androidx.compose.ui.graphics.PathFillType"
        val EvenOdd = PathFillType("EvenOdd")
        val NonZero = PathFillType("NonZero")

        /**
         * Operator function to create a new instance of [PathFillType].
         * Converts the first character of the given string parameter
         * to uppercase, then returns a [PathFillType] instance with this
         * new string.
         *
         * If the provided value is `null`, this function likewise returns null.
         *
         * @param value A nullable string which will represent the stroke cap.
         * This string, if provided, will have its first character converted
         * to uppercase.
         * @return A new [PathFillType] object, or `null` if the input string was `null`.
         */
        operator fun invoke(value: String?): PathFillType? = value?.let {
            return PathFillType(value.replaceFirstChar { it.uppercaseChar() })
        }
    }

    override fun toCompose(): String? = when (this.value) {
        EvenOdd.value, NonZero.value,
        EvenOdd.value.lowercase(), NonZero.value.lowercase() ->
            "PathFillType.$value"

        else -> null
    }
}

/**
 * This is a wrapper class for the [StrokeCap] property of the
 * Compose UI library.
 *
 * The [StrokeCap] inline class encapsulates the value of the stroke
 * cap as a string, providing utility methods for conversion and
 * string representation.
 *
 * The class is defined as an inline class to minimise memory overhead.
 *
 * The class comes with three predefined instances: [Butt], [Round] and
 * [Square], representing the three supported fill types in Android Compose.
 *
 * The [toString] method is overridden to return the encapsulated
 * string value.
 */
@JvmInline
value class StrokeCap private constructor(private val value: String) : ComposeProperty {
    override fun toString(): String = value

    companion object {
        const val IMPORT = "androidx.compose.ui.graphics.StrokeCap"
        val Butt = StrokeCap("Butt")
        val Round = StrokeCap("Round")
        val Square = StrokeCap("Square")

        /**
         * Operator function to create a new instance of [StrokeCap].
         * Converts the first character of the given string parameter
         * to uppercase, then returns a [StrokeCap] instance with this
         * new string.
         *
         * If the provided value is `null`, this function likewise returns null.
         *
         * @param value A nullable string which will represent the stroke cap.
         * This string, if provided, will have its first character converted
         * to uppercase.
         * @return A new [StrokeCap] object, or `null` if the input string was `null`.
         */
        operator fun invoke(value: String?): StrokeCap? = value?.let {
            return StrokeCap(value.replaceFirstChar { it.uppercaseChar() })
        }
    }

    override fun toCompose(): String? = when (this) {
        Butt, Round, Square -> "StrokeCap.$value"
        else -> null // Unsupported by Compose
    }
}

/**
 * This is a wrapper class for the [StrokeJoin] property of the
 * Compose UI library.
 *
 * The [StrokeJoin] inline class encapsulates the value of the stroke
 * line join as a string, providing utility methods for conversion and
 * string representation.
 *
 * The class is defined as an inline class to minimise memory overhead.
 *
 * The class comes with three predefined instances: [Miter], [Round], and
 * [Bevel], representing the three supported fill types in Android Compose.
 *
 * The [toString] method is overridden to return the encapsulated
 * string value.
 */
@JvmInline
value class StrokeJoin private constructor(private val value: String) : ComposeProperty {
    override fun toString(): String = value

    companion object {
        const val IMPORT = "androidx.compose.ui.graphics.StrokeJoin"
        val Miter = StrokeJoin("Miter")
        val Round = StrokeJoin("Round")
        val Bevel = StrokeJoin("Bevel")

        /**
         * Operator function to create a new instance of [StrokeJoin].
         * Converts the first character of the given string parameter
         * to uppercase, then returns a [StrokeJoin] instance with this
         * new string.
         *
         * If the provided value is `null`, this function likewise returns null.
         *
         * @param value A nullable string which will represent the stroke cap.
         * This string, if provided, will have its first character converted
         * to uppercase.
         * @return A new [StrokeJoin] object, or `null` if the input string was `null`.
         */
        operator fun invoke(value: String?): StrokeJoin? = value?.let {
            return StrokeJoin(value.replaceFirstChar { it.uppercaseChar() })
        }
    }

    override fun toCompose(): String? = when (this) {
        Miter, Round, Bevel -> "StrokeJoin.$value"
        else -> null
    }
}
