package dev.tonholo.s2c.domain

import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.error.ExitProgramException
import dev.tonholo.s2c.extensions.EMPTY
import dev.tonholo.s2c.extensions.indented
import dev.tonholo.s2c.extensions.toComposeColor
import dev.tonholo.s2c.logger.debug
import dev.tonholo.s2c.logger.debugEndSection
import dev.tonholo.s2c.logger.debugSection

sealed interface ImageVectorNode {
    fun materialize(): String

    data class Path(
        val params: Params,
        val wrapper: NodeWrapper,
        val minified: Boolean,
    ) : ImageVectorNode {
        data class Params(
            val fill: String,
            val fillAlpha: Float? = null,
            val pathFillType: PathFillType? = null,
            val stroke: String? = null,
            val strokeAlpha: Float? = null,
            val strokeLineCap: StrokeCap? = null,
            val strokeLineJoin: StrokeJoin? = null,
            val strokeMiterLimit: Float? = null,
            val strokeLineWidth: Float? = null,
        )

        fun pathImports(): Set<String> = buildSet {
            if (params.pathFillType != null) {
                add(PathFillType.IMPORT)
            }
            if (params.strokeLineCap != null) {
                add(StrokeCap.IMPORT)
            }
            if (params.strokeLineJoin != null) {
                add(StrokeJoin.IMPORT)
            }
        }

        override fun materialize(): String {
            val indentSize = 4
            val pathNodes = wrapper.nodes.joinToString("\n${" ".repeat(indentSize)}") {
                it.materialize()
                    .replace("\n", "\n${" ".repeat(indentSize)}") // Fix indent
                    .trimEnd()
            }

            val pathParams = buildList {
                with(params) {
                    if (params.fill.isNotEmpty()) {
                        params.fill.toComposeColor()?.let { add("fill" to it) }
                    }
                    fillAlpha?.let {
                        add("fillAlpha" to "${it}f")
                    }
                    pathFillType?.let {
                        add("pathFillType" to "${it.toCompose()}")
                    }
                    stroke?.let { stroke ->
                        stroke.toComposeColor()?.let { add("stroke" to it) }
                    }
                    strokeAlpha?.let {
                        add("strokeAlpha" to "${it}f")
                    }
                    strokeLineCap?.let {
                        add("strokeLineCap" to "${it.toCompose()}")
                    }
                    strokeLineJoin?.let {
                        add("strokeLineJoin" to "${it.toCompose()}")
                    }
                    strokeMiterLimit?.let {
                        add("strokeMiterLimit" to "${it}f")
                    }
                    strokeLineWidth?.let {
                        add("strokeLineWidth" to "${it}f")
                    }
                }
            }

            val pathParamsString = if (pathParams.isNotEmpty()) {
                """(
                |${pathParams.joinToString("\n") { (param, value) -> "$param = $value,".indented(4) }}
                |)"""
            } else {
                ""
            }

            val comment = if (minified) "" else "// ${wrapper.normalizedPath}\n|"

            return """
                |${comment}path$pathParamsString {
                |    $pathNodes
                |}
            """.trimMargin()
        }
    }

    data class Group(
        val clipPath: NodeWrapper?,
        val commands: List<ImageVectorNode>,
        val minified: Boolean,
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

                val clipPathComment = if (minified) {
                    ""
                } else {
                    "\n|${"// ${clipPath.normalizedPath}".indented(4)}"
                }
                """($clipPathComment
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

fun String.asNodeWrapper(minified: Boolean): ImageVectorNode.NodeWrapper {
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
            currentCommand = when {
                // For any subsequent coordinate pair(s) after MoveTo (M/m) are interpreted as parameter(s)
                // for implicit absolute LineTo (L/l) command(s)
                lastCommand.lowercaseChar() == PathNodes.MoveTo.COMMAND && lastCommand.isLowerCase() ->
                    PathNodes.LineTo.COMMAND

                lastCommand.lowercaseChar() == PathNodes.MoveTo.COMMAND ->
                    PathNodes.LineTo.COMMAND.uppercaseChar()

                else -> lastCommand
            }
            current = currentCommand + current
        }

        debug("current commands list=$commands")
        debug("current=$current, currentCommand=$currentCommand")

        val isRelative = currentCommand.isLowerCase()
        current = current.lowercase()
        val node = createNode(current, commands, isRelative, currentCommand, minified)
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
    currentCommand: Char,
    minified: Boolean,
) = when {
    current.startsWith(PathNodes.MoveTo.COMMAND) -> PathNodes.MoveTo(
        values = commands,
        isRelative = isRelative,
        minified = minified,
    )

    current.startsWith(PathNodes.ArcTo.COMMAND) -> PathNodes.ArcTo(
        values = commands,
        isRelative = isRelative,
        minified = minified,
    )

    current.startsWith(PathNodes.VerticalLineTo.COMMAND) -> PathNodes.VerticalLineTo(
        values = commands,
        isRelative = isRelative,
        minified = minified,
    )

    current.startsWith(PathNodes.HorizontalLineTo.COMMAND) -> PathNodes.HorizontalLineTo(
        values = commands,
        isRelative = isRelative,
        minified = minified,
    )

    current.startsWith(PathNodes.LineTo.COMMAND) -> PathNodes.LineTo(
        values = commands,
        isRelative = isRelative,
        minified = minified,
    )

    current.startsWith(PathNodes.CurveTo.COMMAND) -> PathNodes.CurveTo(
        values = commands,
        isRelative = isRelative,
        minified = minified,
    )

    current.startsWith(PathNodes.ReflectiveCurveTo.COMMAND) -> PathNodes.ReflectiveCurveTo(
        values = commands,
        isRelative = isRelative,
        minified = minified,
    )

    current.startsWith(PathNodes.QuadTo.COMMAND) -> PathNodes.QuadTo(
        values = commands,
        isRelative = isRelative,
        minified = minified,
    )

    current.startsWith(PathNodes.ReflectiveQuadTo.COMMAND) -> PathNodes.ReflectiveQuadTo(
        values = commands,
        isRelative = isRelative,
        minified = minified,
    )

    else -> throw ExitProgramException(
        errorCode = ErrorCode.NotSupportedFileError,
        message = "Not support SVG/Android Vector command. Command = $currentCommand"
    )
}

private inline fun resetDotCount(current: Char): Int =
    if (current == '.') 1 else 0

private inline fun calculateDotCount(char: Char, dotCount: Int, lastChar: Char): Int =
    if (char == '.') {
        dotCount + 1
    } else if ((lastChar.isDigit() && char.isWhitespace()) || lastChar.isWhitespace()) {
        resetDotCount(current = char)
    } else {
        dotCount
    }

private fun StringBuilder.trimWhitespaceBeforeClose(
    lastChar: Char,
    current: Char,
): StringBuilder = apply {
    if (lastChar == ' ' && current == 'z') {
        setLength(length - 1)
    }
}

private fun normalizePath(path: String): String {
    debugSection("Normalizing path")
    debug("Original path value = $path")
    debugEndSection()

    val parsedPath = StringBuilder()
    var lastChar = Char.EMPTY
    var dotCount = 0
    for (char in path.replace(",", " ")) {
        dotCount = calculateDotCount(char, dotCount, lastChar)
        if ((lastChar.isLetter() && char.isWhitespace()) || (lastChar.isWhitespace() && char.isWhitespace())) {
            continue
        }

        val isNotClosingCommand = (char.isLetter() && char.lowercaseChar() != 'z')
        val isNegativeSign = (lastChar.isDigit() && char == '-')
        val reachMaximumDotNumbers = dotCount == 2

        parsedPath
            .trimWhitespaceBeforeClose(lastChar, current = char)
            .append(
                if (isNotClosingCommand || isNegativeSign || reachMaximumDotNumbers) {
                    dotCount = resetDotCount(current = char)
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
