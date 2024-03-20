package dev.tonholo.s2c.domain

import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.error.ExitProgramException
import dev.tonholo.s2c.extensions.EMPTY
import dev.tonholo.s2c.extensions.indented
import dev.tonholo.s2c.extensions.toComposeColor
import dev.tonholo.s2c.logger.debug
import dev.tonholo.s2c.logger.debugSection
import dev.tonholo.s2c.logger.verbose
import dev.tonholo.s2c.logger.verboseSection

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

            val pathParams = buildParameterList()

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

        private fun buildParameterList(): List<Pair<String, String>> = buildList {
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
    val nodes = verboseSection("Starting path") {
        val commands = normalizedPath.split(" ").filter { it.isNotEmpty() }.toMutableList()
        verbose("commands=$commands")
        var lastCommand = Char.EMPTY
        val nodes = mutableListOf<PathNodes>()
        while (commands.size > 0) {
            var current = commands.first()
            var currentCommand = current.first()
            val isContinuation = (currentCommand.isDigit() || currentCommand == '-' || currentCommand == '.') &&
                lastCommand != Char.EMPTY
            if (isContinuation) {
                currentCommand = when {
                    // For any subsequent coordinate pair(s) after MoveTo (M/m) are interpreted as parameter(s)
                    // for implicit absolute LineTo (L/l) command(s)
                    lastCommand.lowercaseChar() == PathCommand.MoveTo.value && lastCommand.isLowerCase() ->
                        PathCommand.LineTo.value

                    lastCommand.lowercaseChar() == PathCommand.MoveTo.value ->
                        PathCommand.LineTo.uppercaseChar()

                    else -> lastCommand
                }
                current = currentCommand + current
            }

            verbose("current commands list=$commands")
            verbose("current=$current, currentCommand=$currentCommand")

            val isRelative = currentCommand.isLowerCase()
            current = current.lowercase()
            val node = createNode(current, commands, isRelative, currentCommand, minified)
            lastCommand = currentCommand
            nodes.add(node)
            // Looping to avoid recreating a new list by using .drop() instead.
            repeat(node.command.size) {
                commands.removeFirst()
            }
        }

        return@verboseSection nodes
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
) = try {
    val pathCommand = current.first().toPathCommand()
    when {
        pathCommand != null -> pathCommand.createNode(commands, isRelative, minified)

        else -> throw ExitProgramException(
            errorCode = ErrorCode.NotSupportedFileError,
            message = "Not support SVG/Android Vector command. Command = $currentCommand"
        )
    }
} catch (e: NumberFormatException) {
    debug(
        """
        |Error while parsing Path command. Received parameters:
        |current = $current,
        |commands = $commands,
        |isRelative = $isRelative,
        |currentCommand = $currentCommand,
        |minified = $minified,
        |
        """.trimMargin()
    )
    throw e
} catch (e: NoSuchElementException) {
    debug(
        """
        |Error while parsing Path command. 
        |Path string must not be empty.
        |
        |Received parameters:
        |current = $current,
        |commands = $commands,
        |isRelative = $isRelative,
        |currentCommand = $currentCommand,
        |minified = $minified,
        |
        """.trimMargin()
    )
    throw e
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
    debugSection("Normalizing path") {
        debug("Original path value = $path")
    }

    val parsedPath = StringBuilder()
    var lastChar = Char.EMPTY
    var dotCount = 0
    // The path always starts with move to.
    var command = PathCommand.MoveTo

    var arcCommandPosition = -1
    for (char in path.replace(",", " ")) {
        dotCount = calculateDotCount(char, dotCount, lastChar)
        if ((lastChar.isLetter() && char.isWhitespace()) || (lastChar.isWhitespace() && char.isWhitespace())) {
            continue
        }

        val isNotClosingCommand = (char.isLetter() && char.lowercaseChar() != PathCommand.Close.value)
        val isNegativeSign = (lastChar.isDigit() && char == '-')
        val reachMaximumDotNumbers = dotCount == 2

        char.toPathCommand()?.let {
            command = it
        }
        arcCommandPosition = calculateArcCommandPosition(
            command, position = arcCommandPosition, current = char, lastChar = lastChar,
        )
        val isSweepFlagWithNoSpace = arcCommandPosition == PathCommand.ARC_TO_SWEEP_FLAG_POSITION &&
            lastChar.isDigit() &&
            char.isWhitespace().not()

        parsedPath
            .trimWhitespaceBeforeClose(lastChar, current = char)
            .append(
                when {
                    isNotClosingCommand || isNegativeSign || reachMaximumDotNumbers -> {
                        dotCount = resetDotCount(current = char)
                        " $char"
                    }

                    isSweepFlagWithNoSpace -> " $char "

                    else -> {
                        char
                    }
                }
            )
        lastChar = char
    }

    debugSection("Finished Normalizing") {
        debug("Normalized path value = $parsedPath")
    }

    return parsedPath.toString().trimStart()
}

private fun calculateArcCommandPosition(
    command: PathCommand,
    position: Int,
    current: Char,
    lastChar: Char,
): Int {
    return when {
        command != PathCommand.ArcTo -> -1
        lastChar == PathCommand.ArcTo.value || current == PathCommand.ArcTo.value -> 0
        position in PathCommand.ARC_TO_LARGE_ARC_POSITION..PathCommand.ARC_TO_SWEEP_FLAG_POSITION &&
            lastChar.isDigit() -> position + 1
        lastChar.isWhitespace() && (current.isDigit() || current == '-') -> position + 1
        else -> position
    }
}
