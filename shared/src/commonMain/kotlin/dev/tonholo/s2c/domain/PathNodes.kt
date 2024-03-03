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
    val minified: Boolean,
) {
    private val shouldClose = values[commandSize - 1].last().lowercase() == "z"

    abstract fun materialize(): String

    protected fun closeCommand(): String = if (shouldClose) {
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
        |${if (minified) "" else comment}
        |$fnName${if (isRelative) "Relative" else ""}(${parameters.toParameters(forceInline)})
        |${closeCommand()}
    """.trimMargin().let {
        if (minified) it.trim() else it
    }

    protected fun Set<String>.toParameters(forceInline: Boolean = false): String {
        val indentSize = if (minified || forceInline) 0 else 4
        val separator = if (minified || forceInline) "" else "\n"
        val scape = if (minified || forceInline) " " else "|"
        return joinToString(separator) {
            "$scape${it.indented(indentSize)},"
        }.let {
            if (minified || forceInline) it.substring(1, it.length - 1) else "\n$it\n"
        }
    }

    private fun String.indented(indentSize: Int) = " ".repeat(indentSize) + this

    class MoveTo(
        values: List<String>,
        isRelative: Boolean,
        minified: Boolean,
    ) : PathNodes(
        values = values,
        isRelative = isRelative,
        command = COMMAND,
        commandSize = 2,
        minified = minified,
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
        commandSize = 7,
        minified = minified,
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
        commandSize = 1,
        minified = minified,
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
        commandSize = 1,
        minified = minified,
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
        commandSize = 2,
        minified = minified,
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
        commandSize = 6,
        minified = minified,
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
        commandSize = 4,
        minified = minified,
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
        commandSize = 4,
        minified = minified,
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
        commandSize = 2,
        minified = minified,
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
