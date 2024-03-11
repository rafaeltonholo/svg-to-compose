// Suppressing MagicNumber in this file since we need to use array positions to access the proper value
// while parsing the commands.
@file:Suppress("MagicNumber")

package dev.tonholo.s2c.domain

import dev.tonholo.s2c.domain.PathNodes.Companion.CLOSE_COMMAND
import dev.tonholo.s2c.extensions.indented
import dev.tonholo.s2c.extensions.removeTrailingZero
import dev.tonholo.s2c.extensions.toInt

sealed class PathNodes(
    val values: List<String>,
    val isRelative: Boolean,
    val command: PathCommand,
    val commandSize: Int,
    val minified: Boolean,
) {
    companion object {
        const val CLOSE_COMMAND = "z"
    }

    private val shouldClose = values[commandSize - 1].last().lowercase() == CLOSE_COMMAND

    abstract fun materialize(): String

    private fun closeCommand(): String = if (shouldClose) {
        """
        |close()
        |
        """.trimMargin()
    } else {
        ""
    }

    protected fun materialize(
        comment: String,
        fnName: String,
        parameters: Set<String>,
        forceInline: Boolean = false,
    ): String = """
        |${if (minified) "" else "${comment.removeTrailingZero()}${if (shouldClose) "z" else ""}"}
        |$fnName${if (isRelative) "Relative" else ""}(${parameters.toParameters(forceInline)})
        |${closeCommand()}
    """.trimMargin().let {
        if (minified) it.trim() else it
    }

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

    class MoveTo(
        values: List<String>,
        isRelative: Boolean,
        minified: Boolean,
    ) : PathNodes(
        values = values,
        isRelative = isRelative,
        command = COMMAND,
        commandSize = COMMAND_SIZE,
        minified = minified,
    ) {
        companion object {
            val COMMAND = PathCommand('m')
            const val COMMAND_SIZE = 2
        }

        private val x = values.first().lowercase().removePrefix(command.toString()).toFloat()
        private val y = values[1]
            .lowercase()
            .removeSuffix(CLOSE_COMMAND)
            .toFloat()

        override fun materialize(): String {
            val command = if (isRelative) this.command else this.command.uppercaseChar()
            val relativePrefix = if (isRelative) "d" else ""
            return materialize(
                comment = "// $command $x $y",
                fnName = "moveTo",
                parameters = setOf(
                    "${relativePrefix}x = ${x}f",
                    "${relativePrefix}y = ${y}f",
                ),
                forceInline = true,
            )
        }
    }

    class ArcTo(
        values: List<String>,
        isRelative: Boolean,
        minified: Boolean,
    ) : PathNodes(
        values = values,
        isRelative = isRelative,
        command = COMMAND,
        commandSize = COMMAND_SIZE,
        minified = minified,
    ) {
        companion object {
            val COMMAND = PathCommand('a')
            const val COMMAND_SIZE = 7
        }

        private val a = values.first().lowercase().removePrefix(command.toString()).toFloat()
        private val b = values[1].toFloat()
        private val theta = values[2].toFloat()
        private val isMoreThanHalf = values[3] == "1"
        private val isPositiveArc = values[4] == "1"
        private val x = values[5].toFloat()
        private val y = values[6]
            .lowercase()
            .removeSuffix(CLOSE_COMMAND)
            .toFloat()

        override fun materialize(): String {
            val command = if (isRelative) this.command else this.command.uppercaseChar()
            val relativePrefix = if (isRelative) "d" else ""
            val a = (if (isRelative) "a" else "horizontalEllipseRadius") + " = ${this.a}f"
            val b = (if (isRelative) "b" else "verticalEllipseRadius") + " = ${this.b}f"
            return materialize(
                comment =
                "// $command ${this.a} ${this.b} $theta ${isMoreThanHalf.toInt()} ${isPositiveArc.toInt()} $x $y",
                fnName = "arcTo",
                parameters = setOf(
                    a,
                    b,
                    "theta = ${theta}f",
                    "isMoreThanHalf = $isMoreThanHalf",
                    "isPositiveArc = $isPositiveArc",
                    "${relativePrefix}x1 = ${x}f",
                    "${relativePrefix}y1 = ${y}f",
                ),
            )
        }
    }

    class VerticalLineTo(
        values: List<String>,
        isRelative: Boolean,
        minified: Boolean,
    ) : PathNodes(
        values = values,
        isRelative = isRelative,
        command = COMMAND,
        commandSize = COMMAND_SIZE,
        minified = minified,
    ) {
        companion object {
            val COMMAND = PathCommand('v')
            const val COMMAND_SIZE = 1
        }

        private val y = values
            .first()
            .lowercase()
            .removePrefix(command.toString())
            .removeSuffix(CLOSE_COMMAND)
            .toFloat()

        override fun materialize(): String {
            val command = if (isRelative) this.command else this.command.uppercaseChar()
            val relativePrefix = if (isRelative) "d" else ""
            return materialize(
                comment = "// $command $y",
                fnName = "verticalLineTo",
                parameters = setOf("${relativePrefix}y = ${y}f"),
                forceInline = true,
            )
        }
    }

    class HorizontalLineTo(
        values: List<String>,
        isRelative: Boolean,
        minified: Boolean,
    ) : PathNodes(
        values = values,
        isRelative = isRelative,
        command = COMMAND,
        commandSize = COMMAND_SIZE,
        minified = minified,
    ) {
        companion object {
            val COMMAND = PathCommand('h')
            const val COMMAND_SIZE = 1
        }

        private val x = values
            .first()
            .lowercase()
            .removePrefix(command.toString())
            .removeSuffix(CLOSE_COMMAND)
            .toFloat()

        override fun materialize(): String {
            val command = if (isRelative) this.command else this.command.uppercaseChar()
            val relativePrefix = if (isRelative) "d" else ""
            return materialize(
                comment = "// $command $x",
                fnName = "horizontalLineTo",
                parameters = setOf("${relativePrefix}x = ${x}f"),
                forceInline = true,
            )
        }
    }

    class LineTo(
        values: List<String>,
        isRelative: Boolean,
        minified: Boolean,
    ) : PathNodes(
        values = values,
        isRelative = isRelative,
        command = COMMAND,
        commandSize = COMMAND_SIZE,
        minified = minified,
    ) {
        companion object {
            val COMMAND = PathCommand('l')
            const val COMMAND_SIZE = 2
        }

        private val x = values
            .first()
            .lowercase()
            .removePrefix(command.toString())
            .toFloat()
        private val y = values[1]
            .lowercase()
            .removeSuffix(CLOSE_COMMAND)
            .toFloat()

        override fun materialize(): String {
            val command = if (isRelative) this.command else this.command.uppercaseChar()
            val relativePrefix = if (isRelative) "d" else ""
            return materialize(
                comment = "// $command $x $y",
                fnName = "lineTo",
                parameters = setOf(
                    "${relativePrefix}x = ${x}f",
                    "${relativePrefix}y = ${y}f",
                ),
                forceInline = true,
            )
        }
    }

    class CurveTo(
        values: List<String>,
        isRelative: Boolean,
        minified: Boolean,
    ) : PathNodes(
        values = values,
        isRelative = isRelative,
        command = COMMAND,
        commandSize = COMMAND_SIZE,
        minified = minified,
    ) {
        companion object {
            val COMMAND = PathCommand('c')
            const val COMMAND_SIZE = 6
        }

        private val x1 = values.first().lowercase().removePrefix(command.toString()).toFloat()
        private val y1 = values[1].toFloat()
        private val x2 = values[2].toFloat()
        private val y2 = values[3].toFloat()
        private val x3 = values[4].toFloat()
        private val y3 = values[5]
            .lowercase()
            .removeSuffix(CLOSE_COMMAND)
            .toFloat()

        override fun materialize(): String {
            val command = if (isRelative) this.command else this.command.uppercaseChar()
            val relativePrefix = if (isRelative) "d" else ""

            return materialize(
                comment = "// $command $x1 $y1 $x2 $y2 $x3 $y3",
                fnName = "curveTo",
                parameters = setOf(
                    "${relativePrefix}x1 = ${x1}f",
                    "${relativePrefix}y1 = ${y1}f",
                    "${relativePrefix}x2 = ${x2}f",
                    "${relativePrefix}y2 = ${y2}f",
                    "${relativePrefix}x3 = ${x3}f",
                    "${relativePrefix}y3 = ${y3}f",
                ),
            )
        }
    }

    class ReflectiveCurveTo(
        values: List<String>,
        isRelative: Boolean,
        minified: Boolean,
    ) : PathNodes(
        values = values,
        isRelative = isRelative,
        command = COMMAND,
        commandSize = COMMAND_SIZE,
        minified = minified,
    ) {
        companion object {
            val COMMAND = PathCommand('s')
            const val COMMAND_SIZE = 4
        }

        private val x1 = values.first().lowercase().removePrefix(command.toString()).toFloat()
        private val y1 = values[1].toFloat()
        private val x2 = values[2].toFloat()
        private val y2 = values[3]
            .lowercase()
            .removeSuffix(CLOSE_COMMAND)
            .toFloat()

        override fun materialize(): String {
            val command = if (isRelative) this.command else this.command.uppercaseChar()
            val relativePrefix = if (isRelative) "d" else ""
            return materialize(
                comment = "// $command $x1 $y1 $x2 $y2",
                fnName = "reflectiveCurveTo",
                parameters = setOf(
                    "${relativePrefix}x1 = ${x1}f",
                    "${relativePrefix}y1 = ${y1}f",
                    "${relativePrefix}x2 = ${x2}f",
                    "${relativePrefix}y2 = ${y2}f",
                ),
            )
        }
    }

    class QuadTo(
        values: List<String>,
        isRelative: Boolean,
        minified: Boolean,
    ) : PathNodes(
        values = values,
        isRelative = isRelative,
        command = COMMAND,
        commandSize = COMMAND_SIZE,
        minified = minified,
    ) {
        companion object {
            val COMMAND = PathCommand('q')
            const val COMMAND_SIZE = 4
        }

        private val x1 = values.first().lowercase().removePrefix(command.toString()).toFloat()
        private val y1 = values[1].toFloat()
        private val x2 = values[2].toFloat()
        private val y2 = values[3]
            .lowercase()
            .removeSuffix(CLOSE_COMMAND)
            .toFloat()

        override fun materialize(): String {
            val command = if (isRelative) this.command else this.command.uppercaseChar()
            val relativePrefix = if (isRelative) "d" else ""
            return materialize(
                comment = "// $command $x1 $y1 $x2 $y2",
                fnName = "quadTo",
                parameters = setOf(
                    "${relativePrefix}x1 = ${x1}f",
                    "${relativePrefix}y1 = ${y1}f",
                    "${relativePrefix}x2 = ${x2}f",
                    "${relativePrefix}y2 = ${y2}f",
                ),
            )
        }
    }

    class ReflectiveQuadTo(
        values: List<String>,
        isRelative: Boolean,
        minified: Boolean,
    ) : PathNodes(
        values = values,
        isRelative = isRelative,
        command = COMMAND,
        commandSize = COMMAND_SIZE,
        minified = minified,
    ) {
        companion object {
            val COMMAND = PathCommand('t')
            const val COMMAND_SIZE = 2
        }

        private val x1 = values.first().lowercase().removePrefix(command.toString()).toFloat()
        private val y1 = values[1]
            .lowercase()
            .removeSuffix(CLOSE_COMMAND)
            .toFloat()

        override fun materialize(): String {
            val command = if (isRelative) this.command else this.command.uppercaseChar()
            val relativePrefix = if (isRelative) "d" else ""
            return materialize(
                comment = "// $command $x1 $y1",
                fnName = "reflectiveQuadTo",
                parameters = setOf(
                    "${relativePrefix}x1 = ${x1}f",
                    "${relativePrefix}y1 = ${y1}f",
                ),
            )
        }
    }
}

@DslMarker
annotation class PathNodesDsl

@PathNodesDsl
class PathNodesBuilder(val command: Char) {
    private val commandMap = mapOf(
        PathNodes.MoveTo.COMMAND to PathNodes.MoveTo.COMMAND_SIZE,
        PathNodes.ArcTo.COMMAND to PathNodes.ArcTo.COMMAND_SIZE,
        PathNodes.VerticalLineTo.COMMAND to PathNodes.VerticalLineTo.COMMAND_SIZE,
        PathNodes.HorizontalLineTo.COMMAND to PathNodes.HorizontalLineTo.COMMAND_SIZE,
        PathNodes.LineTo.COMMAND to PathNodes.LineTo.COMMAND_SIZE,
        PathNodes.CurveTo.COMMAND to PathNodes.CurveTo.COMMAND_SIZE,
        PathNodes.ReflectiveCurveTo.COMMAND to PathNodes.ReflectiveCurveTo.COMMAND_SIZE,
        PathNodes.QuadTo.COMMAND to PathNodes.QuadTo.COMMAND_SIZE,
        PathNodes.ReflectiveQuadTo.COMMAND to PathNodes.ReflectiveQuadTo.COMMAND_SIZE,
    )

    init {
        check(command in commandMap.keys) {
            "The command $command is not a Path command."
        }
    }

    private var args: List<String>? = null
    var isRelative: Boolean = false
    var minified: Boolean = false

    fun args(vararg args: Any) {
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
        val hasCloseCommand = args.last() == CLOSE_COMMAND
        val size = if (hasCloseCommand) args.size - 1 else args.size
        check(size == commandMap[command]) {
            "Invalid number of arguments. Expected ${commandMap[command]}, given ${args.size}"
        }

        return if (hasCloseCommand) {
            args.dropLast(1)
                .mapIndexed { index: Int, s: String ->
                    if (index == size - 1) "${s}z" else s
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
            PathNodes.MoveTo.COMMAND -> PathNodes.MoveTo(
                values = enforceArgumentSize(args),
                isRelative = isRelative,
                minified = minified,
            )

            PathNodes.ArcTo.COMMAND -> PathNodes.ArcTo(
                values = enforceArgumentSize(args),
                isRelative = isRelative,
                minified = minified,
            )

            PathNodes.VerticalLineTo.COMMAND -> PathNodes.VerticalLineTo(
                values = enforceArgumentSize(args),
                isRelative = isRelative,
                minified = minified,
            )

            PathNodes.HorizontalLineTo.COMMAND -> PathNodes.HorizontalLineTo(
                values = enforceArgumentSize(args),
                isRelative = isRelative,
                minified = minified,
            )

            PathNodes.LineTo.COMMAND -> PathNodes.LineTo(
                values = enforceArgumentSize(args),
                isRelative = isRelative,
                minified = minified,
            )

            PathNodes.CurveTo.COMMAND -> PathNodes.CurveTo(
                values = enforceArgumentSize(args),
                isRelative = isRelative,
                minified = minified,
            )

            PathNodes.ReflectiveCurveTo.COMMAND -> PathNodes.ReflectiveCurveTo(
                values = enforceArgumentSize(args),
                isRelative = isRelative,
                minified = minified,
            )

            PathNodes.QuadTo.COMMAND -> PathNodes.QuadTo(
                values = enforceArgumentSize(args),
                isRelative = isRelative,
                minified = minified,
            )

            PathNodes.ReflectiveQuadTo.COMMAND -> PathNodes.ReflectiveQuadTo(
                values = enforceArgumentSize(args),
                isRelative = isRelative,
                minified = minified,
            )

            else -> error("Unsupported path command $command")
        }
    }
}

internal fun pathNode(command: PathCommand, configuration: PathNodesBuilder.() -> Unit): PathNodes =
    PathNodesBuilder(command).apply(configuration).build()

