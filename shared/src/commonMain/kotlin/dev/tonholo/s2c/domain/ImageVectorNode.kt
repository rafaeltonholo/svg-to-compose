package dev.tonholo.s2c.domain

import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.error.ExitProgramException
import dev.tonholo.s2c.extensions.EMPTY
import dev.tonholo.s2c.logger.debug
import dev.tonholo.s2c.logger.debugEndSection
import dev.tonholo.s2c.logger.debugSection

sealed interface ImageVectorNode {
    fun materialize(): String

    data class Path(
        val fillColor: String,
        val wrapper: NodeWrapper,
    ) : ImageVectorNode {
        override fun materialize(): String {
            val realColor = fillColor.uppercase().removePrefix("#").let { color ->
                when {
                    color.length == 6 -> "FF$color"
                    color.length == 3 -> "FF$color$color"
                    color.isEmpty() || color.lowercase().contains("url") ->
                        "FF000000 /* not supported color \"$color\" */" // not supported yet.
                    else -> color
                }
            }
            val indentSize = 4
            val pathNodes = wrapper.nodes.joinToString("\n${" ".repeat(indentSize)}") {
                it.materialize()
                    .replace("\n", "\n${" ".repeat(indentSize)}") // Fix indent
                    .trimEnd()
            }

            val pathParams = buildList {
                if (fillColor.isNotEmpty()) {
                    add("fill" to "SolidColor(Color(0x$realColor))")
                }
            }

            val pathParamsString = if (pathParams.isNotEmpty()){
                """(
                |${pathParams.joinToString("\n") { (param, value) -> "    $param = $value," }}
                |)
                """
            } else {
                ""
            }

            return """
                |// ${wrapper.normalizedPath}
                |path$pathParamsString {
                |    $pathNodes
                |}
                """.trimMargin()
        }
    }

    data class Group(
        val clipPath: NodeWrapper?,
        val commands: List<ImageVectorNode>,
    ) : ImageVectorNode {
        override fun materialize(): String {
            val indentSize = 4
            val groupPaths = commands
                .joinToString("\n${" ".repeat(indentSize)}") {
                    it.materialize()
                        .replace("\n", "\n${" ".repeat(indentSize)}")
                        .trimEnd()
                }
            val groupParams = if (clipPath != null) {
                val clipPathData = clipPath.nodes
                    .joinToString("\n${" ".repeat(indentSize * 2)}") {
                        it.materialize()
                            .replace("\n", "\n${" ".repeat(indentSize * 2)}")
                            .trimEnd()
                    }
                """(
                |    // ${clipPath.normalizedPath}
                |    clipPathData = PathData {
                |        $clipPathData
                |    },
                |)"""
            } else {
                ""
            }

            return """
                |group$groupParams {
                |    $groupPaths
                |}
                """.trimMargin()
        }
    }

    data class NodeWrapper(
        val normalizedPath: String,
        val nodes: List<PathNodes>,
    ) // Support class to Paths. It should not be inherited from ImageVectorNode
}

fun String.asNodeWrapper(): ImageVectorNode.NodeWrapper {
    val normalizedPath = normalizePath(this)
    debugSection("Starting path")

    val commands = normalizedPath.split(" ").filter { it.isNotEmpty() }.toMutableList()
    debug("commands=$commands")
    var lastCommand = Char.EMPTY
    val nodes = mutableListOf<PathNodes>()
    while (commands.size > 0) {
        var current = commands.first()
        var currentCommand = current.first()

        if ((currentCommand.isDigit() || currentCommand == '-' || currentCommand == '.') && lastCommand != Char.EMPTY) {
            currentCommand = if (lastCommand.lowercaseChar() == PathNodes.MoveTo.COMMAND) {
                // For any subsequent coordinate pair(s) after MoveTo (M/m) are interpreted as parameter(s)
                // for implicit absolute LineTo (L/l) command(s)
                if (lastCommand.isLowerCase()) {
                    PathNodes.LineTo.COMMAND
                } else {
                    PathNodes.LineTo.COMMAND.uppercaseChar()
                }
            } else {
                lastCommand
            }
            current = currentCommand + current
        }

        debug("current commands list=$commands")
        debug("current=$current, currentCommand=$currentCommand")

        val isRelative = currentCommand.isLowerCase()
        current = current.lowercase()
        val node = createNode(current, commands, isRelative, currentCommand)
        lastCommand = currentCommand
        nodes.add(node)
        // Looping to avoid recreating a new list by using .drop() instead.
        repeat(node.commandSize) {
            commands.removeFirst()
        }
    }
    return ImageVectorNode.NodeWrapper(
        normalizedPath = normalizedPath,
        nodes = nodes,
    )
}

private fun createNode(
    current: String,
    commands: MutableList<String>,
    isRelative: Boolean,
    currentCommand: Char
) = when {
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

    current.startsWith(PathNodes.QuadTo.COMMAND) -> PathNodes.QuadTo(
        values = commands,
        isRelative = isRelative,
    )

    current.startsWith(PathNodes.ReflectiveQuadTo.COMMAND) -> PathNodes.ReflectiveQuadTo(
        values = commands,
        isRelative = isRelative,
    )

    else -> throw ExitProgramException(
        errorCode = ErrorCode.NotSupportedFileError,
        message = "Not support SVG/Android Vector command. Command = $currentCommand"
    )
}

private fun normalizePath(path: String): String {
    debugSection("Normalizing path")
    debug("Original path value = $path")
    debugEndSection()

    val parsedPath = StringBuilder()
    var lastChar = Char.EMPTY
    var dotCount = 0
    for (char in path.replace(",", " ")) {
        if (char == '.') dotCount++
        if (lastChar.isDigit() && char == ' ') dotCount = 0
        if (lastChar == ' ') dotCount = if (char == '.') 1 else 0

        parsedPath.append(
            if ((char.isLetter() && char.lowercaseChar() != 'z') || (lastChar.isDigit() && char == '-') || dotCount == 2) {
                dotCount = if (char == '.') 1 else 0
                " $char"
            } else {
                char
            }
        )
        lastChar = char
    }

    debugSection("Finished Normalizing")
    debug("Normalized path value = $parsedPath")

    return parsedPath.toString().trimStart()
}
