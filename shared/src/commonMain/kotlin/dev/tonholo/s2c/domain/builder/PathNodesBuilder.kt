package dev.tonholo.s2c.domain.builder

import dev.tonholo.s2c.domain.PathCommand
import dev.tonholo.s2c.domain.PathNodes

/**
 * PathNodesBuilder is a helper class designed to create individual path
 * commands in an SVG path string.
 * This class is used to construct SVG path commands like MoveTo, ArcTo,
 * LineTo, etc.
 * It allows you to set flags such as 'isRelative' and 'minified' and set
 * the arguments for a path command.
 *
 * This class validates the command and modifies the command arguments if
 * needed by considering whether the command is relative, minified, and
 * if the SVG command needs to be closed.
 *
 * It supports adding arguments as a vararg or as a list.
 * It also supports different types of arguments - String, Number, Char,
 * Boolean. For boolean arguments, it uses '1' for `true` and '0' for `false`.
 *
 * PathNodesBuilder ensures the number of arguments of each SVG command is
 * correct before constructing the node.
 * When building the path node, the specific path node object (e.g., MoveTo
 * or ArcTo) is selected based on the command chars.
 *
 * This class throws an error when the command is unsupported.
 *
 * USAGE:
 * You should use this build by using the [pathNode] DSL function.
 *
 * NOTE:
 * This class is marked with the @[PathNodesDsl] attribute, which means it is part
 * of a domain-specific language (DSL) for creating SVG paths.
 * @see [pathNode] DSL
 */
@PathNodesDsl
internal class PathNodesBuilder(val command: PathCommand) {
    private val commandMap = mapOf(
        PathCommand.MoveTo to PathCommand.MoveTo.size,
        PathCommand.ArcTo to PathCommand.ArcTo.size,
        PathCommand.VerticalLineTo to PathCommand.VerticalLineTo.size,
        PathCommand.HorizontalLineTo to PathCommand.HorizontalLineTo.size,
        PathCommand.LineTo to PathCommand.LineTo.size,
        PathCommand.CurveTo to PathCommand.CurveTo.size,
        PathCommand.ReflectiveCurveTo to PathCommand.ReflectiveCurveTo.size,
        PathCommand.QuadTo to PathCommand.QuadTo.size,
        PathCommand.ReflectiveQuadTo to PathCommand.ReflectiveQuadTo.size,
    )

    init {
        check(command in commandMap.keys) {
            "The command $command is not a Path command."
        }
    }

    private var args: List<String>? = null
    var isRelative: Boolean = false
    var minified: Boolean = false
    var close: Boolean = false

    fun args(vararg args: Any) {
        args(args.toList())
    }

    fun args(args: List<Any>) {
        val parsedArgs = args.map { arg ->
            when (arg) {
                is String, is Number, is Char -> arg.toString()
                is Boolean -> if (arg) "1" else "0"
                else -> error("The argument type of ${arg::class} is unsupported.")
            }
        }
        this.args = parsedArgs
    }

    private fun enforceArgumentSize(args: List<String>): List<String> {
        check(args.size == commandMap[command]) {
            "Invalid number of arguments. Expected ${commandMap[command]}, given ${args.size}"
        }

        return if (close) {
            args.mapIndexed { index: Int, arg: String ->
                if (index == args.lastIndex) "${arg}${PathCommand.Close}" else arg
            }
        } else {
            args
        }
    }

    fun build(): PathNodes {
        val args = checkNotNull(args) {
            "Missing path arguments."
        }

        return when (command) {
            PathCommand.MoveTo -> PathNodes.MoveTo(
                values = enforceArgumentSize(args),
                isRelative = isRelative,
                minified = minified,
            )

            PathCommand.ArcTo -> PathNodes.ArcTo(
                values = enforceArgumentSize(args),
                isRelative = isRelative,
                minified = minified,
            )

            PathCommand.VerticalLineTo -> PathNodes.VerticalLineTo(
                values = enforceArgumentSize(args),
                isRelative = isRelative,
                minified = minified,
            )

            PathCommand.HorizontalLineTo -> PathNodes.HorizontalLineTo(
                values = enforceArgumentSize(args),
                isRelative = isRelative,
                minified = minified,
            )

            PathCommand.LineTo -> PathNodes.LineTo(
                values = enforceArgumentSize(args),
                isRelative = isRelative,
                minified = minified,
            )

            PathCommand.CurveTo -> PathNodes.CurveTo(
                values = enforceArgumentSize(args),
                isRelative = isRelative,
                minified = minified,
            )

            PathCommand.ReflectiveCurveTo -> PathNodes.ReflectiveCurveTo(
                values = enforceArgumentSize(args),
                isRelative = isRelative,
                minified = minified,
            )

            PathCommand.QuadTo -> PathNodes.QuadTo(
                values = enforceArgumentSize(args),
                isRelative = isRelative,
                minified = minified,
            )

            PathCommand.ReflectiveQuadTo -> PathNodes.ReflectiveQuadTo(
                values = enforceArgumentSize(args),
                isRelative = isRelative,
                minified = minified,
            )

            else -> error("Unsupported path command $command")
        }
    }
}

@DslMarker
annotation class PathNodesDsl

/**
 * This function helps to create a path node. It initializes and builds a `PathNodesBuilder` object
 * which can construct `PathNodes` representations.
 *
 * USAGE:
 * Creates a relative and not minified [PathCommand.MoveTo] command
 * ```kotlin
 * pathNode(command = PathCommand.MoveTo) {
 *      args(x, y)
 *      isRelative = true
 *      minified = false
 *  }
 * ```
 *
 * Creates a non-relative and minified [PathCommand.ArcTo] command
 * ```kotlin
 * pathNode(command = PathCommand.ArcTo) {
 *      args(
 *          horizontalEllipseRadius, \* Float *\
 *          verticalEllipseRadius, \* Float *\
 *          theta, \* Float *\
 *          isMoreThanHalf, \* Boolean *\
 *          isPositiveArc, \* Boolean *\
 *          x1, \* Float *\
 *          y1, \* Float *\
 *      )
 *      isRelative = false
 *      minified = true
 *  }
 * ```
 *
 * Creates a non-relative and minified [PathCommand.ArcTo] command with a close command
 * ```kotlin
 * pathNode(command = PathCommand.ArcTo) {
 *      args(
 *          a, \* Float *\
 *          b, \* Float *\
 *          theta, \* Float *\
 *          isMoreThanHalf, \* Boolean *\
 *          isPositiveArc, \* Boolean *\
 *          x1, \* Float *\
 *          y1, \* Float *\
 *      )
 *      isRelative = false
 *      minified = true
 *      close = true
 *  }
 * ```
 *
 * To understand the expected [PathNodesBuilder.args], refer to [PathNodes] classes.
 *
 * @param command The command of type [PathCommand] that will be given to the [PathNodesBuilder]
 * @param configuration configuration block to configure the [PathNodesBuilder].
 * @see [PathCommand] for available commands
 * @see [PathNodes.MoveTo]
 * @see [PathNodes.ArcTo]
 * @see [PathNodes.VerticalLineTo]
 * @see [PathNodes.HorizontalLineTo]
 * @see [PathNodes.LineTo]
 * @see [PathNodes.CurveTo]
 * @see [PathNodes.ReflectiveCurveTo]
 * @see [PathNodes.QuadTo]
 * @see [PathNodes.ReflectiveQuadTo]
 *
 * @return returns `PathNodes` which is the result of invoked build() function on `PathNodesBuilder` object.
 */
internal fun pathNode(command: PathCommand, configuration: PathNodesBuilder.() -> Unit): PathNodes =
    PathNodesBuilder(command).apply(configuration).build()
