// Suppressing MagicNumber in this file since we need to use array positions to access the proper value
// while parsing the commands.
@file:Suppress("MagicNumber")

package dev.tonholo.s2c.domain

import dev.tonholo.s2c.domain.PathNodes.ArcTo
import dev.tonholo.s2c.domain.PathNodes.CurveTo
import dev.tonholo.s2c.domain.PathNodes.HorizontalLineTo
import dev.tonholo.s2c.domain.PathNodes.LineTo
import dev.tonholo.s2c.domain.PathNodes.MoveTo
import dev.tonholo.s2c.domain.PathNodes.QuadTo
import dev.tonholo.s2c.domain.PathNodes.ReflectiveCurveTo
import dev.tonholo.s2c.domain.PathNodes.ReflectiveQuadTo
import dev.tonholo.s2c.domain.PathNodes.VerticalLineTo
import dev.tonholo.s2c.extensions.indented
import dev.tonholo.s2c.extensions.removeTrailingZero
import dev.tonholo.s2c.extensions.toInt
import dev.tonholo.s2c.parser.method.MethodSizeAccountable
import dev.tonholo.s2c.parser.method.MethodSizeAccountable.Companion.BOOLEAN_APPROXIMATE_BYTE_SIZE
import dev.tonholo.s2c.parser.method.MethodSizeAccountable.Companion.FLOAT_APPROXIMATE_BYTE_SIZE

/**
 * PathNodes is a sealed class that contains multiple classes to represent
 * different commands of an SVG/AVG path.
 * These classes include: [MoveTo], [ArcTo], [VerticalLineTo], [HorizontalLineTo],
 * [LineTo], [CurveTo], [ReflectiveCurveTo], [QuadTo] and [ReflectiveQuadTo].
 *
 * @property values - The list of elements representing the parameters of the command.
 * @property isRelative - Boolean to determine if the command is relative or not.
 * @property command - An instance of PathCommand representing the type of the SVG/AVG command.
 * @property minified - A Boolean value indicating if the output should be minified.
 */
sealed class PathNodes(
    val values: List<String>,
    val isRelative: Boolean,
    val command: PathCommand,
    val minified: Boolean,
) : MethodSizeAccountable {
    companion object {
        /**
         * The approximate bytecode size of invoking the method.
         */
        private const val METHOD_INVOKE_BYTE_SIZE = 6
    }

    val shouldClose = values[command.size - 1].last().lowercaseChar() == PathCommand.Close.value
    protected val realCommand = if (isRelative) command else command.uppercaseChar()

    /**
     * Calculates the approximate bytecode size of the path nodes.
     * Accounts for method invocation byte size, float byte sizes,
     * and additional byte size if the path should be closed.
     */
    override val approximateByteSize: Int
        get() = METHOD_INVOKE_BYTE_SIZE +
            FLOAT_APPROXIMATE_BYTE_SIZE * command.size +
            if (shouldClose) METHOD_INVOKE_BYTE_SIZE else 0

    /**
     * Visible for Test only.
     */
    internal abstract fun buildParameters(): Set<String>

    abstract fun materialize(): String

    private fun closeCommand(): String = if (shouldClose) {
        """
        |close()
        |
        """.trimMargin()
    } else {
        ""
    }

    override fun toString(): String = if (shouldClose) {
        PathCommand.Close.value.toString()
    } else {
        ""
    }

    fun String.removeTrailingZeroConsiderCloseCommand(): String =
        this.removeTrailingZero()
            .replace("\\.0z\\b".toRegex(), PathCommand.Close.value.toString())

    /**
     * Materializes the commands on the Compose path functions.`
     * By default, it prints in pretty mode, adding a comment explaining
     * the path command where the parameters were taken and in case of more
     * than two parameters, separate them on new line.
     *
     * @param fnName The function name to be used in the SVG/AVG command.
     * @param forceInline A flag indicating whether the command parameters
     * should be inlined or not. By default, it's set to `false.
     * @return The function representation of the SVG/AVG command.
     */
    protected fun materialize(
        fnName: String,
        forceInline: Boolean = false,
    ): String = """
        |${if (minified) "" else "// ${toString().removeTrailingZeroConsiderCloseCommand()}"}
        |$fnName${if (isRelative) "Relative" else ""}(${buildParameters().toParameters(forceInline)})
        |${closeCommand()}
    """.trimMargin().let {
        if (minified) it.trim() else it
    }

    /**
     * A helper function that helps in indenting parameters, adding
     * separators, and wrapping without initial and end separators
     * if required as per minified or forceInline flags.
     *
     * @param forceInline A boolean flag indicating whether the parameters
     * should be in a single line ignoring minification.
     * @return The formatted parameter string.
     */
    private fun Set<String>.toParameters(forceInline: Boolean = false): String {
        val indentSize = if (minified || forceInline) 0 else 4
        val separator = if (minified || forceInline) "" else "\n"
        val scape = if (minified || forceInline) " " else "|"
        return joinToString(separator) {
            "$scape${it.indented(indentSize)},"
        }.let {
            if (minified || forceInline) it.substring(1, it.length - 1) else "\n$it\n"
        }
    }

    /**
     * The [MoveTo] represents the SVG/AVG command to move the pen to a new location.
     *
     * It's a type of [PathNodes] which holds SVG/AVG command parameters.
     *
     * The new location is determined using the specified `x` and `y` coordinates.
     * The unit of these coordinates is determined based on the `isRelative` value.
     * If [isRelative] is `true`, the [x] and [y] are treated as relative coordinates
     * otherwise they are treated as absolute coordinates.
     *
     * The class contains an overridden [materialize] function which returns a
     * [String] representation of the SVG/AVG command in the form required by
     * the Compose path functions.
     *
     * @property values A List of string values specifying the parameters of the
     * [MoveTo] command.
     * @property isRelative A boolean value indicating whether the move command
     * is relative.
     * @property minified A boolean value indicating whether the output of
     * [materialize] should be minified.
     */
    class MoveTo(
        values: List<String>,
        isRelative: Boolean,
        minified: Boolean,
    ) : PathNodes(
        values = values,
        isRelative = isRelative,
        command = PathCommand.MoveTo,
        minified = minified,
    ), CoordinatePoint {
        override val x = values.first().lowercase().removePrefix(command.toString()).toFloat()
        override val y = values[1]
            .lowercase()
            .removeSuffix(PathCommand.Close)
            .toFloat()

        override fun buildParameters(): Set<String> {
            val relativePrefix = if (isRelative) "d" else ""
            return setOf(
                "${relativePrefix}x = ${x}f",
                "${relativePrefix}y = ${y}f",
            )
        }

        override fun materialize(): String {
            return materialize(
                fnName = "moveTo",
                forceInline = true,
            )
        }

        override fun toString(): String = "$realCommand $x $y" + super.toString()

        /**
         * Creates a copy of this [MoveTo] with the given properties.
         *
         * @param x The new x-coordinate for the move command.
         * @param y The new y-coordinate for the move command.
         * @param isRelative Whether the move command is relative or absolute.
         * @param minified whether the output of [materialize] should be
         * minified or not.
         * @param shouldClose Whether the move command should close the path.
         *
         * @return A new [MoveTo] instance with the updated properties.
         */
        fun copy(
            x: Float = this.x,
            y: Float = this.y,
            isRelative: Boolean = this.isRelative,
            minified: Boolean = this.minified,
            shouldClose: Boolean = this.shouldClose,
        ): MoveTo = MoveTo(
            values = listOf(
                x.toString(),
                y.toString().let {
                    if (shouldClose) it + PathCommand.Close.value
                    else it
                },
            ),
            isRelative = isRelative,
            minified = minified,
        )
    }

    /**
     * The [ArcTo] class represents the SVG/AVG "arc" command which is used to create
     * an arc.
     *
     * It's a type of [PathNodes] which holds SVG/AVG command parameters.
     *
     * The class has several properties which denote the parameters of SVG/AVG "arc"
     * command: horizontal ellipse radius [a], vertical ellipse radius [b], rotation
     * [theta], large arc flag [isMoreThanHalf], sweep flag [isPositiveArc], ending
     * point coordinates [x] and [y].
     *
     * The class contains an overridden [materialize] function which returns a
     * [String] representation of the SVG/AVG command in the form required by
     * the Compose path functions.
     *
     * @property values A List of string values specifying the parameters of the [MoveTo]
     * command.
     * @property isRelative A boolean value indicating whether the move command is
     * relative.
     * @property minified A boolean value indicating whether the output of [materialize]
     * should be minified.
     */
    class ArcTo(
        values: List<String>,
        isRelative: Boolean,
        minified: Boolean,
    ) : PathNodes(
        values = values,
        isRelative = isRelative,
        command = PathCommand.ArcTo,
        minified = minified,
    ), CoordinatePoint {
        /**
         * rx
         */
        val a = values.first().lowercase().removePrefix(command.toString()).toFloat()

        /**
         * ry
         */
        val b = values[1].toFloat()

        /**
         * x-axis-rotation
         */
        val theta = values[2].toFloat()

        /**
         * large-arc-flag
         */
        val isMoreThanHalf = values[3] == "1"

        /**
         * sweep flag
         */
        val isPositiveArc = values[4] == "1"
        override val x = values[5].toFloat()
        override val y = values[6]
            .lowercase()
            .removeSuffix(PathCommand.Close)
            .toFloat()

        /**
         * Calculates the approximate bytecode size of the path nodes.
         * Accounts for method invocation byte size, float byte sizes,
         * and additional byte size if the path should be closed.
         *
         * [ArcTo] differs from the others since it deals with [Boolean]
         * parameters as well.
         */
        override val approximateByteSize: Int
            get() = METHOD_INVOKE_BYTE_SIZE +
                ((command.size - 2) * FLOAT_APPROXIMATE_BYTE_SIZE) +
                (2 * BOOLEAN_APPROXIMATE_BYTE_SIZE) +
                if (shouldClose) METHOD_INVOKE_BYTE_SIZE else 0

        override fun buildParameters(): Set<String> {
            val relativePrefix = if (isRelative) "d" else ""
            val a = (if (isRelative) "a" else "horizontalEllipseRadius") + " = ${this.a}f"
            val b = (if (isRelative) "b" else "verticalEllipseRadius") + " = ${this.b}f"
            return setOf(
                a,
                b,
                "theta = ${theta}f",
                "isMoreThanHalf = $isMoreThanHalf",
                "isPositiveArc = $isPositiveArc",
                "${relativePrefix}x1 = ${x}f",
                "${relativePrefix}y1 = ${y}f",
            )
        }

        override fun materialize(): String {
            return materialize(
                fnName = "arcTo",
            )
        }

        override fun toString(): String =
            "$realCommand ${this.a} ${this.b} $theta ${isMoreThanHalf.toInt()} ${isPositiveArc.toInt()} $x $y" +
                super.toString()

        /**
         * Creates a copy of this [ArcTo] instance with the specified values.
         *
         * @param a (rx) The horizontal radius of the ellipse.
         * @param b (ry) The vertical radius of the ellipse.
         * @param theta (x-axis-rotation) The angle from the x-axis of the
         * current point to the start of the arc.
         * @param isMoreThanHalf (large-arc-flag) Whether the arc is more
         * than half a circle.
         * @param isPositiveArc (sweep flag) Whether the arc is drawn in a
         * positive or negative direction.
         * @param x The x-coordinate of the end point of the arc.
         * @param y The y-coordinate of the end point of the arc.
         * @param isRelative Whether the coordinates are relative to the
         * current point.
         * @param minified whether the output of [materialize] should be
         * minified or not.
         * @param shouldClose Whether the arc should be closed.
         *
         * @return A new [ArcTo] instance with the specified values.
         */
        fun copy(
            a: Float = this.a,
            b: Float = this.b,
            theta: Float = this.theta,
            isMoreThanHalf: Boolean = this.isMoreThanHalf,
            isPositiveArc: Boolean = this.isPositiveArc,
            x: Float = this.x,
            y: Float = this.y,
            isRelative: Boolean = this.isRelative,
            minified: Boolean = this.minified,
            shouldClose: Boolean = this.shouldClose,
        ): ArcTo = ArcTo(
            values = listOf(
                a.toString(),
                b.toString(),
                theta.toString(),
                isMoreThanHalf.toString(),
                isPositiveArc.toString(),
                x.toString(),
                y.toString().let {
                    if (shouldClose) it + PathCommand.Close.value
                    else it
                },
            ),
            isRelative = isRelative,
            minified = minified,
        )
    }

    /**
     * The [VerticalLineTo] class represents the SVG/AVG "vertical line to" command,
     * which instructs the pen to move vertically by the specified amount.
     *
     * A type of [PathNodes], it holds the given SVG/AVG command parameters.
     *
     * The vertical position where the pen should move to is denoted by [y], the
     * first value stripped of the command prefix and the [PathCommand.Close] suffix.
     *
     * Depending on the [isRelative] flag, the [y] value is considered either as
     * an absolute coordinate (if false) or as a distance from the current
     * position (if true).
     *
     * The class contains an overridden [materialize] function which returns a
     * [String] representation of the SVG/AVG command in the form required by
     * the Compose path functions.
     *
     * @property values A List of parameter strings for the [VerticalLineTo] command.
     * @property isRelative A Boolean flag indicating if the command is relative.
     * @property minified A Boolean flag indicating whether the output of [materialize]
     * should be minified.
     * @property y The new y-coordinate to which the line extends.
     */
    class VerticalLineTo(
        values: List<String>,
        isRelative: Boolean,
        minified: Boolean,
    ) : PathNodes(
        values = values,
        isRelative = isRelative,
        command = PathCommand.VerticalLineTo,
        minified = minified,
    ), CoordinateY {
        override val y = values
            .first()
            .lowercase()
            .removePrefix(command.toString())
            .removeSuffix(PathCommand.Close)
            .toFloat()

        override fun buildParameters(): Set<String> {
            val relativePrefix = if (isRelative) "d" else ""
            return setOf("${relativePrefix}y = ${y}f")
        }

        override fun materialize(): String {
            return materialize(
                fnName = "verticalLineTo",
                forceInline = true,
            )
        }

        override fun toString(): String {
            return "$realCommand $y" + super.toString()
        }

        /**
         * Creates a copy of the current [VerticalLineTo] command with the given parameters.
         *
         * @param y the y-coordinate of the end point of the line.
         * @param isRelative whether the command is relative.
         * @param minified Whether the output of [materialize] should be
         * minified or not.
         * @param shouldClose whether the command should be closed.
         *
         * @return a new [VerticalLineTo] command with the updated parameters.
         */
        fun copy(
            y: Float = this.y,
            isRelative: Boolean = this.isRelative,
            minified: Boolean = this.minified,
            shouldClose: Boolean = this.shouldClose,
        ): VerticalLineTo = VerticalLineTo(
            values = listOf(
                y.toString().let {
                    if (shouldClose) it + PathCommand.Close.value
                    else it
                },
            ),
            isRelative = isRelative,
            minified = minified,
        )
    }

    /**
     * The [HorizontalLineTo] class represents the SVG/AVG "horizontal line to" command
     * which is used to draw a horizontal line from the current point to a new point.
     *
     * It's a type of [PathNodes] which holds SVG/AVG command parameters.
     *
     * The class contains an overridden [materialize] function which returns a
     * [String] representation of the SVG/AVG command in the form required by
     * the Compose path functions.
     *
     * @property values A List of string values specifying the parameters of the
     * [HorizontalLineTo] command.
     * @property isRelative A boolean value indicating whether the move command is
     * relative to the current position.
     * @property minified A boolean value indicating whether the output of [materialize]
     * should be minified or not.
     * @property x The new x-coordinate to which the line extends.
     */
    class HorizontalLineTo(
        values: List<String>,
        isRelative: Boolean,
        minified: Boolean,
    ) : PathNodes(
        values = values,
        isRelative = isRelative,
        command = PathCommand.HorizontalLineTo,
        minified = minified,
    ), CoordinateX {
        override val x = values
            .first()
            .lowercase()
            .removePrefix(command.toString())
            .removeSuffix(PathCommand.Close)
            .toFloat()

        override fun buildParameters(): Set<String> {
            val relativePrefix = if (isRelative) "d" else ""
            return setOf("${relativePrefix}x = ${x}f")
        }

        override fun materialize(): String {
            return materialize(
                fnName = "horizontalLineTo",
                forceInline = true,
            )
        }

        override fun toString(): String {
            return "$realCommand $x" + super.toString()
        }

        /**
         * Creates a copy of this [HorizontalLineTo] command.
         *
         * @param x the horizontal coordinate for the end point of this line
         * @param isRelative whether this is a relative command or not
         * @param minified whether the output of [materialize] should be
         * minified or not.
         * @param shouldClose whether the path should be closed after this command
         *
         * @return a copy of the current [HorizontalLineTo] command
         */
        fun copy(
            x: Float = this.x,
            isRelative: Boolean = this.isRelative,
            minified: Boolean = this.minified,
            shouldClose: Boolean = this.shouldClose,
        ): HorizontalLineTo = HorizontalLineTo(
            values = listOf(
                x.toString().let {
                    if (shouldClose) it + PathCommand.Close.value
                    else it
                },
            ),
            isRelative = isRelative,
            minified = minified,
        )
    }

    /**
     * The [LineTo] class represents the SVG/AVG "line to" command used for
     * creating a straight line.
     *
     * It's a type of [PathNodes] which holds SVG/AVG command parameters.
     *
     * The line is defined by destination point coordinates [x] and [y]. The
     * origin of these coordinates can be absolute or relative based on the
     * [isRelative] flag. If [isRelative] is `true`, [x] and [y] are treated
     * as relative coordinates. Otherwise, they are treated as absolute.
     *
     * The class contains an overridden [materialize] function which returns a
     * [String] representation of the SVG/AVG command in the form required by
     * the Compose path functions.
     *
     * @property values A List of string values specifying the parameters of
     * the [LineTo] command.
     * @property isRelative A boolean value indicating whether the move command
     * is relative to the current position.
     * @property minified A boolean value indicating whether the output of
     * [materialize] should be minified or not.
     * @property x The new x-coordinate to which the line extends.
     * @property y The new y-coordinate to which the line extends.
     */
    class LineTo(
        values: List<String>,
        isRelative: Boolean,
        minified: Boolean,
    ) : PathNodes(
        values = values,
        isRelative = isRelative,
        command = PathCommand.LineTo,
        minified = minified,
    ), CoordinatePoint {
        override val x = values
            .first()
            .lowercase()
            .removePrefix(command.toString())
            .toFloat()
        override val y = values[1]
            .lowercase()
            .removeSuffix(PathCommand.Close)
            .toFloat()

        override fun buildParameters(): Set<String> {
            val relativePrefix = if (isRelative) "d" else ""
            return setOf(
                "${relativePrefix}x = ${x}f",
                "${relativePrefix}y = ${y}f",
            )
        }

        override fun materialize(): String {
            return materialize(
                fnName = "lineTo",
                forceInline = true,
            )
        }

        override fun toString(): String {
            return "$realCommand $x $y" + super.toString()
        }

        /**
         * Creates a copy of the [LineTo] command with the specified changes.
         *
         * @param x the new value for the x coordinate
         * @param y the new value for the y coordinate
         * @param isRelative whether the command is relative or absolute
         * @param minified whether the output of [materialize] should be
         * minified or not.
         * @param shouldClose whether the command should close the current path
         * @return a new [LineTo] command with the specified changes
         */
        fun copy(
            x: Float = this.x,
            y: Float = this.y,
            isRelative: Boolean = this.isRelative,
            minified: Boolean = this.minified,
            shouldClose: Boolean = this.shouldClose,
        ): LineTo = LineTo(
            values = listOf(
                x.toString(),
                y.toString().let {
                    if (shouldClose) it + PathCommand.Close.value
                    else it
                },
            ),
            isRelative = isRelative,
            minified = minified,
        )
    }

    /**
     * The [CurveTo] class represents the SVG/AVG "curve to" command which is used
     * to create a cubic Bézier curve.
     *
     * It's a type of [PathNodes] which holds SVG/AVG command parameters.
     *
     * This class contains several properties representing the command parameters.
     * In particular, [values] defines a set of values that represent the
     * [[x1], [y1]], [[x2], [y2]], and [[x3], [y3]] coordinates of the first, second
     * control points, and the end point of the curve.
     *
     * The class contains an overridden [materialize] function which returns a
     * [String] representation of the SVG/AVG command in the form required by
     * the Compose path functions.
     *
     * @property values A List of string values specifying the parameters of the
     * [CurveTo] command.
     * @property isRelative A boolean value indicating whether the move command is
     * relative to the current position.
     * @property minified A boolean value indicating whether the output of [materialize]
     * should be minified or not.
     */
    class CurveTo(
        values: List<String>,
        isRelative: Boolean,
        minified: Boolean,
    ) : PathNodes(
        values = values,
        isRelative = isRelative,
        command = PathCommand.CurveTo,
        minified = minified,
    ), ControlPoint1, ControlPoint2, ControlPoint3 {
        override val x1 = values.first().lowercase().removePrefix(command.toString()).toFloat()
        override val y1 = values[1].toFloat()
        override val x2 = values[2].toFloat()
        override val y2 = values[3].toFloat()
        override val x3 = values[4].toFloat()
        override val y3 = values[5]
            .lowercase()
            .removeSuffix(PathCommand.Close)
            .toFloat()

        override fun buildParameters(): Set<String> {
            val relativePrefix = if (isRelative) "d" else ""
            return setOf(
                "${relativePrefix}x1 = ${x1}f",
                "${relativePrefix}y1 = ${y1}f",
                "${relativePrefix}x2 = ${x2}f",
                "${relativePrefix}y2 = ${y2}f",
                "${relativePrefix}x3 = ${x3}f",
                "${relativePrefix}y3 = ${y3}f",
            )
        }

        override fun materialize(): String {
            return materialize(
                fnName = "curveTo",
            )
        }

        override fun toString(): String {
            return "$realCommand $x1 $y1 $x2 $y2 $x3 $y3" + super.toString()
        }

        /**
         * Creates a copy of the [CurveTo] command with the specified values.
         *
         * @param x1 The x-coordinate of the first control point.
         * @param y1 The y-coordinate of the first control point.
         * @param x2 The x-coordinate of the second control point.
         * @param y2 The y-coordinate of the second control point.
         * @param x3 The x-coordinate of the end point.
         * @param y3 The y-coordinate of the end point.
         * @param isRelative Whether the command is relative.
         * @param minified whether the output of [materialize] should be
         * minified or not.
         * @param shouldClose Whether the command should be closed.
         *
         * @return A new [CurveTo] command.
         */
        fun copy(
            x1: Float = this.x1,
            y1: Float = this.y1,
            x2: Float = this.x2,
            y2: Float = this.y2,
            x3: Float = this.x3,
            y3: Float = this.y3,
            isRelative: Boolean = this.isRelative,
            minified: Boolean = this.minified,
            shouldClose: Boolean = this.shouldClose,
        ): CurveTo = CurveTo(
            values = listOf(
                x1.toString(),
                y1.toString(),
                x2.toString(),
                y2.toString(),
                x3.toString(),
                y3.toString().let {
                    if (shouldClose) it + PathCommand.Close.value
                    else it
                },
            ),
            isRelative = isRelative,
            minified = minified,
        )
    }

    /**
     * The [ReflectiveCurveTo]  represents the SVG/AVG "smooth cubic Bézier"
     * command.
     *
     * This command creates a smooth curve between two points using a reflection
     * of the control point used in previous commands.
     *
     * The [x1], [y1] coordinates represent the first control point for the curve,
     * while [x2], [y2] represent the endpoint of the curve.
     *
     * The class contains an overridden [materialize] function which returns a
     * [String] representation of the SVG/AVG command in the form required by
     * the Compose path functions.
     *
     * @property values A List of string values specifying the parameters of the
     * [ReflectiveCurveTo] command.
     * @property isRelative A boolean value indicating whether the curve command
     * is relative.
     * @property minified A boolean value indicating whether the output of
     * [materialize] should be minified.
     * @property x1 The x-coordinate for the first control point.
     * @property y1 The y-coordinate for the first control point.
     * @property x2 The x-coordinate for the second control point.
     * @property y2 The y-coordinate for the second control point.
     */
    class ReflectiveCurveTo(
        values: List<String>,
        isRelative: Boolean,
        minified: Boolean,
    ) : PathNodes(
        values = values,
        isRelative = isRelative,
        command = PathCommand.ReflectiveCurveTo,
        minified = minified,
    ), ControlPoint1, ControlPoint2 {
        override val x1 = values.first().lowercase().removePrefix(command.toString()).toFloat()
        override val y1 = values[1].toFloat()
        override val x2 = values[2].toFloat()
        override val y2 = values[3]
            .lowercase()
            .removeSuffix(PathCommand.Close)
            .toFloat()

        override fun buildParameters(): Set<String> {
            val relativePrefix = if (isRelative) "d" else ""
            return setOf(
                "${relativePrefix}x1 = ${x1}f",
                "${relativePrefix}y1 = ${y1}f",
                "${relativePrefix}x2 = ${x2}f",
                "${relativePrefix}y2 = ${y2}f",
            )
        }

        override fun materialize(): String {
            return materialize(
                fnName = "reflectiveCurveTo",
            )
        }

        override fun toString(): String {
            return "$realCommand $x1 $y1 $x2 $y2" + super.toString()
        }

        /**
         * Creates a copy of the [ReflectiveCurveTo] command with the specified values.
         *
         * @param x1 the x-coordinate of the first control point
         * @param y1 the y-coordinate of the first control point
         * @param x2 the x-coordinate of the second control point
         * @param y2 the y-coordinate of the second control point
         * @param isRelative whether the coordinates are relative
         * @param minified whether the output of [materialize] should be
         * minified or not.
         * @param shouldClose whether the path should be closed
         * @return a new [ReflectiveCurveTo] object
         */
        fun copy(
            x1: Float = this.x1,
            y1: Float = this.y1,
            x2: Float = this.x2,
            y2: Float = this.y2,
            isRelative: Boolean = this.isRelative,
            minified: Boolean = this.minified,
            shouldClose: Boolean = this.shouldClose,
        ): ReflectiveCurveTo = ReflectiveCurveTo(
            values = listOf(
                x1.toString(),
                y1.toString(),
                x2.toString(),
                y2.toString().let {
                    if (shouldClose) it + PathCommand.Close.value
                    else it
                },
            ),
            isRelative = isRelative,
            minified = minified,
        )
    }

    /**
     * The [QuadTo] class represents the SVG/AVG "quadratic Bézier curve" command
     *
     * It's a type of [PathNodes] which holds SVG/AVG command parameters.
     *
     * The [x1], [y1] coordinates represent the control point for the curve,
     * while [x2], [y2] represent the endpoint of the curve.
     *
     * The class contains an overridden [materialize] function which returns a
     * [String] representation of the SVG/AVG command in the form required by
     * the Compose path functions.
     *
     * @property values A List of string values specifying the parameters of the
     * [QuadTo] command.
     * @property isRelative A boolean value indicating whether the curve command
     * is relative.
     * @property minified A boolean value indicating whether the output of
     * [materialize] should be minified.
     * @property x1 The x-coordinate for the first control point.
     * @property y1 The y-coordinate for the first control point.
     * @property x2 The x-coordinate for the second control point.
     * @property y2 The y-coordinate for the second control point.
     */
    class QuadTo(
        values: List<String>,
        isRelative: Boolean,
        minified: Boolean,
    ) : PathNodes(
        values = values,
        isRelative = isRelative,
        command = PathCommand.QuadTo,
        minified = minified,
    ), ControlPoint1, ControlPoint2 {
        override val x1 = values.first().lowercase().removePrefix(command.toString()).toFloat()
        override val y1 = values[1].toFloat()
        override val x2 = values[2].toFloat()
        override val y2 = values[3]
            .lowercase()
            .removeSuffix(PathCommand.Close)
            .toFloat()

        override fun buildParameters(): Set<String> {
            val relativePrefix = if (isRelative) "d" else ""
            return setOf(
                "${relativePrefix}x1 = ${x1}f",
                "${relativePrefix}y1 = ${y1}f",
                "${relativePrefix}x2 = ${x2}f",
                "${relativePrefix}y2 = ${y2}f",
            )
        }

        override fun materialize(): String {
            return materialize(
                fnName = "quadTo",
            )
        }

        override fun toString(): String {
            return "$realCommand $x1 $y1 $x2 $y2" + super.toString()
        }

        /**
         * Creates a copy of the [QuadTo] command with the given parameters.
         *
         * @param x1 the x-coordinate of the first control point.
         * @param y1 the y-coordinate of the first control point.
         * @param x2 the x-coordinate of the end point.
         * @param y2 the y-coordinate of the end point.
         * @param isRelative whether the command is relative.
         * @param minified whether the output of [materialize] should be
         * minified or not.
         * @param shouldClose whether the command should be closed.
         *
         * @return a copy of the [QuadTo] command.
         */
        fun copy(
            x1: Float = this.x1,
            y1: Float = this.y1,
            x2: Float = this.x2,
            y2: Float = this.y2,
            isRelative: Boolean = this.isRelative,
            minified: Boolean = this.minified,
            shouldClose: Boolean = this.shouldClose,
        ): QuadTo = QuadTo(
            values = listOf(
                x1.toString(),
                y1.toString(),
                x2.toString(),
                y2.toString().let {
                    if (shouldClose) it + PathCommand.Close.value
                    else it
                },
            ),
            isRelative = isRelative,
            minified = minified,
        )
    }

    /**
     * The [ReflectiveQuadTo] represents the SVG/AVG "reflective quadratic Bézier
     * curve to" command, which is used to create a smooth quadratic Bézier curve.
     *
     * It's a type of [PathNodes] which holds SVG/AVG command parameters.
     *
     * The curve is reflective of the control point of the previous command for
     * creating a smooth curve. The curve ends at the point denoted by the
     * coordinates ([x1], [y1]), with the control point being the reflection of
     * the control point on the previous command relative to the starting point.
     *
     * The class contains an overridden [materialize] function which returns a
     * [String] representation of the SVG/AVG command in the form required by
     * the Compose path functions.
     *
     * @property values A List of string values specifying the parameters of the
     * [ReflectiveQuadTo] command.
     * @property isRelative A Boolean value. If `true`, the [x1] and [y1] parameters
     * will be treated as relative coordinates. Otherwise, they will be treated as
     * absolute coordinates.
     * @property minified A boolean value indicating whether the output of
     * [materialize] should be minified.
     * @property x1 represents the x-coordinate for the ending point of the curve
     * @property y1 signifies the y-coordinate for the ending point of the curve
     */
    class ReflectiveQuadTo(
        values: List<String>,
        isRelative: Boolean,
        minified: Boolean,
    ) : PathNodes(
        values = values,
        isRelative = isRelative,
        command = PathCommand.ReflectiveQuadTo,
        minified = minified,
    ), ControlPoint1 {
        override val x1 = values.first().lowercase().removePrefix(command.toString()).toFloat()
        override val y1 = values[1]
            .lowercase()
            .removeSuffix(PathCommand.Close)
            .toFloat()

        override fun buildParameters(): Set<String> {
            val relativePrefix = if (isRelative) "d" else ""
            return setOf(
                "${relativePrefix}x1 = ${x1}f",
                "${relativePrefix}y1 = ${y1}f",
            )
        }

        override fun materialize(): String {
            return materialize(
                fnName = "reflectiveQuadTo",
            )
        }

        override fun toString(): String {
            return "$realCommand $x1 $y1" + super.toString()
        }

        /**
         * Creates a copy of the [ReflectiveQuadTo] command with the
         * given parameters.
         *
         * @param x1 The x-coordinate of the first control point.
         * @param y1 The y-coordinate of the first control point.
         * @param isRelative Whether the coordinates are relative.
         * @param minified whether the output of [materialize] should be
         * minified or not.
         * @param shouldClose Whether the path should be closed.
         *
         * @return A new [ReflectiveQuadTo] with the updated values.
         */
        fun copy(
            x1: Float = this.x1,
            y1: Float = this.y1,
            isRelative: Boolean = this.isRelative,
            minified: Boolean = this.minified,
            shouldClose: Boolean = this.shouldClose,
        ): ReflectiveQuadTo = ReflectiveQuadTo(
            values = listOf(
                x1.toString(),
                y1.toString().let {
                    if (shouldClose) it + PathCommand.Close.value
                    else it
                },
            ),
            isRelative = isRelative,
            minified = minified,
        )
    }
}

val List<PathNodes>.approximateByteSize
    get() = sumOf {
        it.approximateByteSize
    }

/**
 * Interface representing an X-coordinate.
 */
internal interface CoordinateX {
    /**
     * The x-coordinate value.
     */
    val x: Float
}

/**
 * Interface representing a Y-coordinate.
 */
internal interface CoordinateY {
    /**
     * The y-coordinate value.
     */
    val y: Float
}

/**
 * Interface representing a point in a 2D space with both X and Y coordinates.
 */
internal interface CoordinatePoint : CoordinateX, CoordinateY

/**
 * Interface representing the X-coordinate of the first control point.
 *
 * In the context of Bézier curves in vector graphics (such as SVG paths),
 * a control point determines the direction and length of the curve. The
 * first control point helps define the initial slope of the curve.
 */
internal interface ControlPointX1 {
    /**
     * The x-coordinate value of the first control point.
     */
    val x1: Float
}

/**
 * Interface representing the Y-coordinate of the first control point.
 *
 * In the context of Bézier curves in vector graphics (such as SVG paths),
 * a control point determines the direction and length of the curve. The
 * first control point helps define the initial slope of the curve.
 */
internal interface ControlPointY1 {
    /**
     * The y-coordinate value of the first control point.
     */
    val y1: Float
}

/**
 * Interface representing the first control point with both X and Y coordinates.
 *
 * In the context of Bézier curves in vector graphics (such as SVG paths),
 * a control point determines the direction and length of the curve. The
 * first control point helps define the initial slope of the curve.
 */
internal interface ControlPoint1 : ControlPointX1, ControlPointY1

/**
 * Interface representing the X-coordinate of the second control point.
 *
 * In the context of Bézier curves in vector graphics (such as SVG paths),
 * a control point determines the direction and length of the curve. The
 * second control point helps define the curvature towards the end point.
 */
internal interface ControlPointX2 {
    /**
     * The x-coordinate value of the second control point.
     */
    val x2: Float
}

/**
 * Interface representing the Y-coordinate of the second control point.
 *
 * In the context of Bézier curves in vector graphics (such as SVG paths),
 * a control point determines the direction and length of the curve. The
 * second control point helps define the curvature towards the end point.
 */
internal interface ControlPointY2 {
    /**
     * The y-coordinate value of the second control point.
     */
    val y2: Float
}

/**
 * Interface representing the second control point with both X and Y coordinates.
 *
 * In the context of Bézier curves in vector graphics (such as SVG paths),
 * a control point determines the direction and length of the curve. The
 * second control point helps define the curvature towards the end point.
 */
internal interface ControlPoint2 : ControlPointX2, ControlPointY2

/**
 * Interface representing the X-coordinate of the third control point.
 *
 * In some advanced vector graphics or complex paths, a third control
 * point may be used to further influence the shape and direction of a
 * curve. This is less common in basic Bézier curves but can be applicable
 * in more intricate path definitions.
 */
internal interface ControlPointX3 {
    /**
     * The x-coordinate value of the third control point.
     */
    val x3: Float
}

/**
 * Interface representing the Y-coordinate of the third control point.
 *
 * In some advanced vector graphics or complex paths, a third control
 * point may be used to further influence the shape and direction of a
 * curve. This is less common in basic Bézier curves but can be applicable
 * in more intricate path definitions.
 */
internal interface ControlPointY3 {
    /**
     * The y-coordinate value of the third control point.
     */
    val y3: Float
}

/**
 * Interface representing the third control point with both X and Y coordinates.
 *
 * In some advanced vector graphics or complex paths, a third control
 * point may be used to further influence the shape and direction of a
 * curve. This is less common in basic Bézier curves but can be applicable
 * in more intricate path definitions.
 */
internal interface ControlPoint3 : ControlPointX3, ControlPointY3

