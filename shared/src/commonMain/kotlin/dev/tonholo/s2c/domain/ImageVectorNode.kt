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
                    else -> color
                }
            }
            val indentSize = 4
            val pathNodes = wrapper.nodes.joinToString("\n${" ".repeat(indentSize)}") {
                it.materialize()
                    .replace("\n", "\n${" ".repeat(indentSize)}") // Fix indent
                    .trimEnd()
            }

            return """
                |// ${wrapper.normalizedPath}
                |path(
                |    fill = SolidColor(Color(0x$realColor)),
                |) {
                |    $pathNodes
                |}
                """.trimMargin()
        }
    }

    data class Group(
        val clipPath: NodeWrapper,
        val nodes: List<Path>,
    ) : ImageVectorNode {
        override fun materialize(): String {
            val indentSize = 4
            val clipPathData = clipPath.nodes
                .joinToString("\n${" ".repeat(indentSize * 2)}") {
                    it.materialize()
                        .replace("\n", "\n${" ".repeat(indentSize * 2)}")
                        .trimEnd()
                }
            val groupPaths = nodes
                .joinToString("\n${" ".repeat(indentSize)}") {
                    it.materialize()
                        .replace("\n", "\n${" ".repeat(indentSize)}")
                        .trimEnd()
                }
            return """
                |group(
                |    // ${clipPath.normalizedPath}
                |    clipPathData = PathData {
                |        $clipPathData
                |    },
                |) {
                |    $groupPaths
                |}
                """.trimMargin()
        }
    }

    data class NodeWrapper(
        val normalizedPath: String,
        val nodes: List<PathNodes>,
    ) // Support class to Paths. It should not inherit from ImageVectorNode
}

fun String.asNodeWrapper(): ImageVectorNode.NodeWrapper {
    val normalizedPath = normalizePath(this)
    debugSection("Starting path")

    val commands = normalizedPath.split(" ").toMutableList()
    debug("commands=$commands")
    var lastCommand = Char.EMPTY
    val nodes = mutableListOf<PathNodes>()
    while (commands.size > 0) {
        var current = commands.first()
        var currentCommand = current.first()

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
        repeat(node.commandSize) {
            commands.removeFirst()
        }
    }
    return ImageVectorNode.NodeWrapper(
        normalizedPath = normalizedPath,
        nodes = nodes,
    )
}

private fun normalizePath(path: String): String {
    debugSection("Normalizing path")
    debug("Original path value = $path")
    debugEndSection()

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

    debugSection("Finished Normalizing")
    debug("Normalized path value = $parsedPath")

    return parsedPath.toString().trimStart()
}
