// Suppressing MagicNumber in this file since we need to use array positions to access the proper value
// while parsing the commands.
@file:Suppress("MagicNumber")

package dev.tonholo.s2c.domain

import dev.tonholo.s2c.extensions.toInt

sealed class PathNodes(
    val values: List<String>,
    val isRelative: Boolean,
    val command: Char,
    val commandSize: Int,
) {
    private val shouldClose = values[commandSize - 1].last().lowercase() == "z"

    abstract fun materialize(): String

    protected fun closeCommand(): String = if (shouldClose) {
        """
        |close()
        |
        |""".trimMargin()
    } else {
        ""
    }

    class MoveTo(
        values: List<String>,
        isRelative: Boolean,
    ) : PathNodes(
        values = values,
        isRelative = isRelative,
        command = COMMAND,
        commandSize = 2,
    ) {
        companion object {
            const val COMMAND = 'm'
        }

        private val x = values.first().lowercase().removePrefix(command.toString()).toFloat()
        private val y = values[1]
            .lowercase()
            .removeSuffix("z")
            .toFloat()

        override fun materialize(): String {
            val command = if (isRelative) this.command else this.command.uppercaseChar()
            val relative = if (isRelative) "Relative" else ""
            val relativePrefix = if (isRelative) "d" else ""
            return """
                |// $command $x $y
                |moveTo${relative}(${relativePrefix}x = ${x}f, ${relativePrefix}y = ${y}f)
                |${closeCommand()}""".trimMargin()
        }
    }

    class ArcTo(
        values: List<String>,
        isRelative: Boolean,
    ) : PathNodes(
        values = values,
        isRelative = isRelative,
        command = COMMAND,
        commandSize = 7,
    ) {
        companion object {
            const val COMMAND = 'a'
        }

        private val a = values.first().lowercase().removePrefix(command.toString()).toFloat()
        private val b = values[1].toFloat()
        private val theta = values[2].toFloat()
        private val isMoreThanHalf = values[3] == "1"
        private val isPositiveArc = values[4] == "1"
        private val x = values[5].toFloat()
        private val y = values[6]
            .lowercase()
            .removeSuffix("z")
            .toFloat()

        override fun materialize(): String {
            val command = if (isRelative) this.command else this.command.uppercaseChar()
            val relative = if (isRelative) "Relative" else ""
            val relativePrefix = if (isRelative) "d" else ""
            val a = (if (isRelative) "a" else "horizontalEllipseRadius") + " = ${this.a}f"
            val b = (if (isRelative) "b" else "verticalEllipseRadius") + " = ${this.b}f"
            return """
                |// $command ${this.a} ${this.b} $theta ${isMoreThanHalf.toInt()} ${isPositiveArc.toInt()} $x $y
                |arcTo${relative}(
                |    $a,
                |    $b,
                |    theta = ${theta}f,
                |    isMoreThanHalf = $isMoreThanHalf,
                |    isPositiveArc = $isPositiveArc,
                |    ${relativePrefix}x1 = ${x}f,
                |    ${relativePrefix}y1 = ${y}f,
                |)
                |${closeCommand()}""".trimMargin()
        }
    }

    class VerticalLineTo(
        values: List<String>,
        isRelative: Boolean,
    ) : PathNodes(
        values = values,
        isRelative = isRelative,
        command = COMMAND,
        commandSize = 1,
    ) {
        companion object {
            const val COMMAND = 'v'
        }

        private val y = values
            .first()
            .lowercase()
            .removePrefix(command.toString())
            .removeSuffix("z")
            .toFloat()

        override fun materialize(): String {
            val command = if (isRelative) this.command else this.command.uppercaseChar()
            val relative = if (isRelative) "Relative" else ""
            val relativePrefix = if (isRelative) "d" else ""
            return """
                |// $command $y
                |verticalLineTo${relative}(${relativePrefix}y = ${y}f)
                |${closeCommand()}""".trimMargin()
        }
    }

    class HorizontalLineTo(
        values: List<String>,
        isRelative: Boolean,
    ) : PathNodes(
        values = values,
        isRelative = isRelative,
        command = COMMAND,
        commandSize = 1,
    ) {
        companion object {
            const val COMMAND = 'h'
        }

        private val x = values
            .first()
            .lowercase()
            .removePrefix(command.toString())
            .removeSuffix("z")
            .toFloat()

        override fun materialize(): String {
            val command = if (isRelative) this.command else this.command.uppercaseChar()
            val relative = if (isRelative) "Relative" else ""
            val relativePrefix = if (isRelative) "d" else ""
            return """
                |// $command $x
                |horizontalLineTo${relative}(${relativePrefix}x = ${x}f)
                |${closeCommand()}""".trimMargin()
        }
    }

    class LineTo(
        values: List<String>,
        isRelative: Boolean,
    ) : PathNodes(
        values = values,
        isRelative = isRelative,
        command = COMMAND,
        commandSize = 2,
    ) {
        companion object {
            const val COMMAND = 'l'
        }

        private val x = values
            .first()
            .lowercase()
            .removePrefix(command.toString())
            .toFloat()
        private val y = values[1]
            .lowercase()
            .removeSuffix("z")
            .toFloat()

        override fun materialize(): String {
            val command = if (isRelative) this.command else this.command.uppercaseChar()
            val relative = if (isRelative) "Relative" else ""
            val relativePrefix = if (isRelative) "d" else ""
            return """
                |// $command $x $y
                |lineTo${relative}(${relativePrefix}x = ${x}f, ${relativePrefix}y = ${y}f)
                |${closeCommand()}""".trimMargin()
        }
    }

    class CurveTo(
        values: List<String>,
        isRelative: Boolean,
    ) : PathNodes(
        values = values,
        isRelative = isRelative,
        command = COMMAND,
        commandSize = 6,
    ) {
        companion object {
            const val COMMAND = 'c'
        }

        private val x1 = values.first().lowercase().removePrefix(command.toString()).toFloat()
        private val y1 = values[1].toFloat()
        private val x2 = values[2].toFloat()
        private val y2 = values[3].toFloat()
        private val x3 = values[4].toFloat()
        private val y3 = values[5]
            .lowercase()
            .removeSuffix("z")
            .toFloat()

        override fun materialize(): String {
            val command = if (isRelative) this.command else this.command.uppercaseChar()
            val relative = if (isRelative) "Relative" else ""
            val relativePrefix = if (isRelative) "d" else ""

            return """
                |// $command $x1 $y1 $x2 $y2 $x3 $y3
                |curveTo${relative}(
                |    ${relativePrefix}x1 = ${x1}f,
                |    ${relativePrefix}y1 = ${y1}f,
                |    ${relativePrefix}x2 = ${x2}f,
                |    ${relativePrefix}y2 = ${y2}f,
                |    ${relativePrefix}x3 = ${x3}f,
                |    ${relativePrefix}y3 = ${y3}f,
                |)
                |${closeCommand()}""".trimMargin()
        }
    }

    class ReflectiveCurveTo(
        values: List<String>,
        isRelative: Boolean,
    ) : PathNodes(
        values = values,
        isRelative = isRelative,
        command = COMMAND,
        commandSize = 4,
    ) {
        companion object {
            const val COMMAND = 's'
        }

        private val x1 = values.first().lowercase().removePrefix(command.toString()).toFloat()
        private val y1 = values[1].toFloat()
        private val x2 = values[2].toFloat()
        private val y2 = values[3]
            .lowercase()
            .removeSuffix("z")
            .toFloat()

        override fun materialize(): String {
            val command = if (isRelative) this.command else this.command.uppercaseChar()
            val relative = if (isRelative) "Relative" else ""
            val relativePrefix = if (isRelative) "d" else ""
            return """
                |// $command $x1 $y1 $x2 $y2
                |reflectiveCurveTo${relative}(
                |    ${relativePrefix}x1 = ${x1}f,
                |    ${relativePrefix}y1 = ${y1}f,
                |    ${relativePrefix}x2 = ${x2}f,
                |    ${relativePrefix}y2 = ${y2}f,
                |)
                |${closeCommand()}""".trimMargin()
        }
    }

    class QuadTo(
        values: List<String>,
        isRelative: Boolean,
    ) : PathNodes(
        values = values,
        isRelative = isRelative,
        command = COMMAND,
        commandSize = 4,
    ) {
        companion object {
            const val COMMAND = 'q'
        }

        private val x1 = values.first().lowercase().removePrefix(command.toString()).toFloat()
        private val y1 = values[1].toFloat()
        private val x2 = values[2].toFloat()
        private val y2 = values[3]
            .lowercase()
            .removeSuffix("z")
            .toFloat()

        override fun materialize(): String {
            val command = if (isRelative) this.command else this.command.uppercaseChar()
            val relative = if (isRelative) "Relative" else ""
            val relativePrefix = if (isRelative) "d" else ""
            return """
                |// $command $x1 $y1 $x2 $y2
                |quadTo${relative}(
                |    ${relativePrefix}x1 = ${x1}f,
                |    ${relativePrefix}y1 = ${y1}f,
                |    ${relativePrefix}x2 = ${x2}f,
                |    ${relativePrefix}y2 = ${y2}f,
                |)
                |${closeCommand()}""".trimMargin()
        }
    }
    class ReflectiveQuadTo(
        values: List<String>,
        isRelative: Boolean,
    ) : PathNodes(
        values = values,
        isRelative = isRelative,
        command = COMMAND,
        commandSize = 2,
    ) {
        companion object {
            const val COMMAND = 't'
        }

        private val x1 = values.first().lowercase().removePrefix(command.toString()).toFloat()
        private val y1 = values[1]
            .lowercase()
            .removeSuffix("z")
            .toFloat()

        override fun materialize(): String {
            val command = if (isRelative) this.command else this.command.uppercaseChar()
            val relative = if (isRelative) "Relative" else ""
            val relativePrefix = if (isRelative) "d" else ""
            return """
                |// $command $x1 $y1
                |reflectiveQuadTo${relative}(
                |    ${relativePrefix}x1 = ${x1}f,
                |    ${relativePrefix}y1 = ${y1}f,
                |)
                |${closeCommand()}""".trimMargin()
        }
    }
}
