package dev.tonholo.s2c.domain

import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.error.ExitProgramException
import dev.tonholo.s2c.extensions.EMPTY
import dev.tonholo.s2c.extensions.toInt

sealed interface ImageVectorNode {
    fun materialize(): String

    data class Path(
        val fillColor: String,
        val nodes: List<PathNodes>,
    ) : ImageVectorNode {
        override fun materialize(): String {
            val realColor = fillColor.uppercase().removePrefix("#").let { color ->
                when {
                    color.length == 6 -> "FF$color"
                    else -> color
                }
            }
            val normalizedNodes = "TODO"
            val indentSize = 4
            val pathNodes = nodes.joinToString("\n\n${" ".repeat(indentSize)}") {
                it.materialize()
                    .replace("\n", "\n${" ".repeat(indentSize)}") // Fix indent
            }
            return """
                |// $normalizedNodes
                |path(
                |   fill = SolidColor(Color(0x$realColor)),
                |) {
                |    $pathNodes
                |}
                """.trimMargin()
        }
    }

    data class Group(
        val clipPath: List<PathNodes>,
        val nodes: List<Path>,
    ) : ImageVectorNode {
        override fun materialize(): String {
            val indentSize = 4
            val clipPathData = clipPath
                .joinToString("\n\n${" ".repeat(indentSize * 2)}") {
                    it.materialize()
                        .replace("\n", "\n${" ".repeat(indentSize * 2)}")
                }
            val groupPaths = nodes
                .joinToString("\n\n${" ".repeat(indentSize)}") {
                    it.materialize()
                        .replace("\n", "\n${" ".repeat(indentSize)}")
                }
            return """
                |group(
                |   clipPathData = PathData {
                |        $clipPathData
                |   }
                |) {
                |   $groupPaths
                |}
                """.trimMargin()
        }
    }
}

sealed class PathNodes(
    val values: List<String>,
    val isRelative: Boolean,
    val command: Char,
    val commandSize: Int,
) {
    private val shouldClose = values.last().last().lowercase() == "z"

    abstract fun materialize(): String

    protected fun closeCommand(): String = if (shouldClose) {
        """
        |close()
        """.trimMargin()
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
            val a = if (isRelative) "a" else "horizontalEllipseRadius" + " = ${this.a}f"
            val b = if (isRelative) "b" else "verticalEllipseRadius" + " = ${this.b}f"
            return """
                |// $command $a $b $theta ${isMoreThanHalf.toInt()} ${isPositiveArc.toInt()} $x $y
                |arcTo${relative}(
                |   $a,
                |   $b,
                |   theta = ${theta}f,
                |   isMoreThanHalf = $isMoreThanHalf,
                |   isPositiveArc = $isPositiveArc,
                |   ${relativePrefix}x1 = ${x}f, 
                |   ${relativePrefix}y1 = ${y}f,
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
                |   ${relativePrefix}x1 = ${x1}f, 
                |   ${relativePrefix}y1 = ${y1}f,
                |   ${relativePrefix}x2 = ${x2}f, 
                |   ${relativePrefix}y2 = ${y2}f,
                |   ${relativePrefix}x3 = ${x3}f, 
                |   ${relativePrefix}y3 = ${y3}f,
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
                |   ${relativePrefix}x1 = ${x1}f, 
                |   ${relativePrefix}y1 = ${y1}f,
                |   ${relativePrefix}x2 = ${x2}f, 
                |   ${relativePrefix}y2 = ${y2}f,
                |)
                |${closeCommand()}""".trimMargin()
        }
    }
}

fun String.asNodes(): List<PathNodes> {
    val normalizedPath = normalizePath(this)
    if (AppConfig.debug) {
        println()
        println("========================== Starting path ==========================")
        println()
    }

    val commands = normalizedPath.split(" ").toMutableList()
    println("commands=$commands")
    var lastCommand = Char.EMPTY
    val nodes = mutableListOf<PathNodes>()
    while (commands.size > 0) {
        var current = commands.first()
//        println("current=$current")
        var currentCommand = current.first()
//        println("currentCommand=$currentCommand")

        if ((currentCommand.isDigit() || currentCommand == '-') && lastCommand != Char.EMPTY) {
            currentCommand = lastCommand
            current = lastCommand + current
        }
        val isRelative = currentCommand.isLowerCase()
        current = current.lowercase()
        val node = when {
            current.startsWith(PathNodes.MoveTo.COMMAND) -> PathNodes.MoveTo(
                values = commands,
                isRelative = isRelative,
            )

            current.startsWith(PathNodes.ArcTo.COMMAND) -> PathNodes.ArcTo(
                values = commands,
                isRelative = isRelative,
            )

            current.startsWith(PathNodes.VerticalLineTo.COMMAND) -> PathNodes.VerticalLineTo(
                values = commands,
                isRelative = isRelative,
            )

            current.startsWith(PathNodes.HorizontalLineTo.COMMAND) -> PathNodes.HorizontalLineTo(
                values = commands,
                isRelative = isRelative,
            )

            current.startsWith(PathNodes.LineTo.COMMAND) -> PathNodes.LineTo(
                values = commands,
                isRelative = isRelative,
            )

            current.startsWith(PathNodes.CurveTo.COMMAND) -> PathNodes.CurveTo(
                values = commands,
                isRelative = isRelative,
            )

            current.startsWith(PathNodes.ReflectiveCurveTo.COMMAND) -> PathNodes.ReflectiveCurveTo(
                values = commands,
                isRelative = isRelative,
            )

            else -> throw ExitProgramException(
                errorCode = ErrorCode.NotSupportedFileError,
                message = "Not support SVG/Android Vector command. Command = $currentCommand"
            )
        }
        lastCommand = currentCommand
        nodes.add(node)
        // Looping to avoid recreating a new list by using .drop() instead.
        for (i in 1..node.commandSize) {
            commands.removeFirst()
        }
    }
    return nodes
}

private fun normalizePath(path: String): String {
    if (AppConfig.debug) {
        println()
        println("========================= Normalizing path =========================")
        println("Original path value = $path")
        println("====================================================================")
        println()
    }

    val parsedPath = StringBuilder()
    var lastChar = Char.EMPTY
    for (char in path.replace(",", " ")) {
        parsedPath.append(
            if ((char.isLetter() && char.lowercaseChar() != 'z') || (lastChar.isDigit() && char == '-')) {
                " $char"
            } else {
                char
            }
        )
        lastChar = char
    }

    if (AppConfig.debug) {
        println()
        println("======================= Finished Normalizing =======================")
        println("Normalized path value = $parsedPath")
        println("====================================================================")
        println()
    }

    return parsedPath.toString().trimStart()
}
