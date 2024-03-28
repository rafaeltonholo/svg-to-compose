package dev.tonholo.s2c.domain

import dev.tonholo.s2c.domain.compose.ComposeBrush
import dev.tonholo.s2c.domain.compose.PathFillType
import dev.tonholo.s2c.domain.compose.StrokeCap
import dev.tonholo.s2c.domain.compose.StrokeJoin
import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.error.ExitProgramException
import dev.tonholo.s2c.extensions.EMPTY
import dev.tonholo.s2c.extensions.indented
import dev.tonholo.s2c.geom.AffineTransformation
import dev.tonholo.s2c.geom.applyTransformations
import dev.tonholo.s2c.logger.debug
import dev.tonholo.s2c.logger.debugSection
import dev.tonholo.s2c.logger.verbose
import dev.tonholo.s2c.logger.verboseSection

sealed interface ImageVectorNode {
    val transformations: List<AffineTransformation>?
    val imports: Set<String>

    fun materialize(): String

    fun applyTransformation(): ImageVectorNode {
        return transformations?.let { transformations ->
            when (this) {
                is Group -> copy(
                    params = params.copy(
                        clipPath = params.clipPath?.copy(
                            nodes = params
                                .clipPath
                                .nodes
                                .applyTransformations(transformations = transformations.toTypedArray())
                                .toList(),
                        )
                    ),
                    commands = commands.map { it.applyTransformation() },
                )

                is Path -> copy(
                    wrapper = wrapper.copy(
                        nodes = wrapper
                            .nodes
                            .applyTransformations(transformations = transformations.toTypedArray())
                            .toList(),
                    )
                )

                is HelperFunction -> copy(
                    node = node.applyTransformation()
                )
            }
        } ?: this
    }

    data class Path(
        val params: Params,
        val wrapper: NodeWrapper,
        val minified: Boolean,
        override val transformations: List<AffineTransformation>? = null,
    ) : ImageVectorNode {
        data class Params(
            val fill: ComposeBrush?,
            val fillAlpha: Float? = null,
            val pathFillType: PathFillType? = null,
            val stroke: ComposeBrush? = null,
            val strokeAlpha: Float? = null,
            val strokeLineCap: StrokeCap? = null,
            val strokeLineJoin: StrokeJoin? = null,
            val strokeMiterLimit: Float? = null,
            val strokeLineWidth: Float? = null,
        )

        override val imports: Set<String> = buildSet {
            add("androidx.compose.ui.graphics.vector.path")
            params.pathFillType?.let { addAll(it.imports) }
            params.strokeLineCap?.let { addAll(it.imports) }
            params.strokeLineJoin?.let { addAll(it.imports) }
            params.fill?.let { addAll(it.imports) }
            params.stroke?.let { addAll(it.imports) }
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
                params.fill?.let { brush ->
                    brush.toCompose()?.let { add("fill" to it) }
                }
                fillAlpha?.let {
                    add("fillAlpha" to "${it}f")
                }
                pathFillType?.let {
                    add("pathFillType" to "${it.toCompose()}")
                }
                stroke?.let { stroke ->
                    stroke.toCompose()?.let { add("stroke" to it) }
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
        val commands: List<ImageVectorNode>,
        val minified: Boolean,
        val params: Params = Params(),
        override val transformations: List<AffineTransformation>? = null,
    ) : ImageVectorNode {
        companion object {
            private const val CLIP_PATH_PARAM_NAME = "clipPathData"
            private const val ROTATE_PARAM_NAME = "rotate"
            private const val PIVOT_X_PARAM_NAME = "pivotX"
            private const val PIVOT_Y_PARAM_NAME = "pivotY"
            private const val SCALE_X_PARAM_NAME = "scaleX"
            private const val SCALE_Y_PARAM_NAME = "scaleY"
            private const val TRANSLATION_X_PARAM_NAME = "translationX"
            private const val TRANSLATION_Y_PARAM_NAME = "translationY"
        }

        data class Params(
            val clipPath: NodeWrapper? = null,
            val rotate: Float? = null,
            val pivotX: Float? = null,
            val pivotY: Float? = null,
            val scaleX: Float? = null,
            val scaleY: Float? = null,
            val translationX: Float? = null,
            val translationY: Float? = null,
        ) {
            fun isEmpty(): Boolean = clipPath == null &&
                rotate == null &&
                pivotX == null &&
                pivotY == null &&
                scaleX == null &&
                scaleY == null &&
                translationX == null &&
                translationY == null
        }

        override val imports: Set<String> = with(params) {
            buildSet {
                add("androidx.compose.ui.graphics.vector.group")
                clipPath?.let { add("androidx.compose.ui.graphics.vector.PathData") }
            }
        }

        private fun buildParameters(indentSize: Int): Set<Pair<String, String>> = with(params) {
            buildSet {
                clipPath?.let {
                    val clipPathData = clipPath.nodes
                        .joinToString("\n${" ".repeat(indentSize * 2)}") {
                            it.materialize()
                                .replace("\n", "\n${" ".repeat(indentSize * 2)}")
                                .trimEnd()
                        }
                    val value = """
                        |PathData {
                        |    ${clipPathData.indented(indentSize = 4)}
                        |${"}".indented(indentSize)}"""
                        .trimMargin()
                    add(CLIP_PATH_PARAM_NAME to value)
                }
                rotate?.let { add(ROTATE_PARAM_NAME to "${rotate}f") }
                pivotX?.let { add(PIVOT_X_PARAM_NAME to "${pivotX}f") }
                pivotY?.let { add(PIVOT_Y_PARAM_NAME to "${pivotY}f") }
                scaleX?.let { add(SCALE_X_PARAM_NAME to "${scaleX}f") }
                scaleY?.let { add(SCALE_Y_PARAM_NAME to "${scaleY}f") }
                translationX?.let { add(TRANSLATION_X_PARAM_NAME to "${translationX}f") }
                translationY?.let { add(TRANSLATION_Y_PARAM_NAME to "${translationY}f") }
            }
        }

        override fun materialize(): String {
            val indentSize = 4
            val groupPaths = commands
                .joinToString("\n${" ".repeat(indentSize)}") {
                    it.materialize()
                        .replace("\n", "\n${" ".repeat(indentSize)}")
                        .trimEnd()
                }

            val groupParams = buildParameters(indentSize)

            val groupParamsString = if (groupParams.isNotEmpty()) {
                val params = groupParams.joinToString("\n") { (param, value) ->
                    if (param == CLIP_PATH_PARAM_NAME && minified.not() && params.clipPath != null) {
                        "${"// ${params.clipPath.normalizedPath}".indented(4)}\n"
                    } else {
                        ""
                    } + "$param = $value,".indented(indentSize)
                }
                """(
                |$params
                |)"""
            } else {
                ""
            }

            return """
                |group$groupParamsString {
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
