package dev.tonholo.s2c.domain

import dev.tonholo.s2c.domain.compose.ComposeBrush
import dev.tonholo.s2c.domain.compose.PathFillType
import dev.tonholo.s2c.domain.compose.StrokeCap
import dev.tonholo.s2c.domain.compose.StrokeJoin
import dev.tonholo.s2c.domain.svg.SvgColor
import dev.tonholo.s2c.domain.svg.toBrush
import dev.tonholo.s2c.emitter.imagevector.ImageVectorNodeEmitter
import dev.tonholo.s2c.error.ErrorCode
import dev.tonholo.s2c.error.ExitProgramException
import dev.tonholo.s2c.extensions.EMPTY
import dev.tonholo.s2c.geom.AffineTransformation
import dev.tonholo.s2c.geom.applyTransformations
import dev.tonholo.s2c.logger.Logger
import dev.tonholo.s2c.parser.method.MethodSizeAccountable
import dev.tonholo.s2c.parser.method.MethodSizeAccountable.Companion.FLOAT_APPROXIMATE_BYTE_SIZE
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
sealed interface ImageVectorNode : MethodSizeAccountable {
    val transformations: List<AffineTransformation>?
    val imports: Set<String>

    @Deprecated(
        message = "Use ImageVectorNodeEmitter.emit() instead.",
        replaceWith = ReplaceWith(
            expression = "ImageVectorNodeEmitter().emit(this)",
            imports = ["dev.tonholo.s2c.emitter.imagevector.ImageVectorNodeEmitter"],
        ),
    )
    fun materialize(): String

    fun applyTransformation(): ImageVectorNode = transformations?.let { transformations ->
        when (this) {
            is Group -> copy(
                params = params.copy(
                    clipPath = params.clipPath?.copy(
                        nodes = params
                            .clipPath
                            .nodes
                            .applyTransformations(transformations = transformations.toTypedArray())
                            .toList(),
                    ),
                ),
                commands = commands.map { it.applyTransformation() },
            )

            is Path -> copy(
                wrapper = wrapper.copy(
                    nodes = wrapper
                        .nodes
                        .applyTransformations(transformations = transformations.toTypedArray())
                        .toList(),
                ),
            )

            is ChunkFunction -> error("Transformation should be applied before creating helper functions.")
        }
    } ?: this

    @Serializable
    data class Path(
        val params: Params,
        val wrapper: NodeWrapper,
        val minified: Boolean,
        @Transient override val transformations: List<AffineTransformation>? = null,
    ) : ImageVectorNode,
        MethodSizeAccountable {
        companion object {
            const val PATH_IMPORT = "androidx.compose.ui.graphics.vector.path"

            /**
             * The approximate byte size of the `Path` class itself.
             */
            private const val PATH_APPROXIMATE_BYTE_SIZE = 133
        }

        @Serializable
        data class Params(
            val fill: ComposeBrush? = null,
            val fillAlpha: Float? = null,
            val pathFillType: PathFillType? = null,
            val stroke: ComposeBrush? = null,
            val strokeAlpha: Float? = null,
            val strokeLineCap: StrokeCap? = null,
            val strokeLineJoin: StrokeJoin? = null,
            val strokeMiterLimit: Float? = null,
            val strokeLineWidth: Float? = null,
        ) : MethodSizeAccountable {
            /**
             * The approximate bytecode size accounted of the path parameters.
             */
            override val approximateByteSize: Int = (fill?.approximateByteSize ?: 0) +
                (fillAlpha?.let { FLOAT_APPROXIMATE_BYTE_SIZE } ?: 0) +
                (pathFillType?.approximateByteSize ?: 0) +
                (stroke?.approximateByteSize ?: 0) +
                (strokeAlpha?.let { FLOAT_APPROXIMATE_BYTE_SIZE } ?: 0) +
                (strokeLineCap?.approximateByteSize ?: 0) +
                (strokeLineJoin?.approximateByteSize ?: 0) +
                (strokeMiterLimit?.let { FLOAT_APPROXIMATE_BYTE_SIZE } ?: 0) +
                (strokeLineWidth?.let { FLOAT_APPROXIMATE_BYTE_SIZE } ?: 0)
        }

        override val imports: Set<String> = buildSet {
            add(PATH_IMPORT)
            params.pathFillType?.let { addAll(it.imports) }
            params.strokeLineCap?.let { addAll(it.imports) }
            params.strokeLineJoin?.let { addAll(it.imports) }
            params.fill?.let { addAll(it.imports) }
            params.stroke?.let { addAll(it.imports) }
            if (params.fill == null && params.stroke == null) {
                addAll(SvgColor.Default.toBrush().imports)
            }
        }

        /**
         * The approximate bytecode size of the `path` instruction,
         * accounting for its components.
         */
        override val approximateByteSize: Int
            get() = PATH_APPROXIMATE_BYTE_SIZE +
                params.approximateByteSize +
                wrapper.nodes.approximateByteSize

        @Deprecated(
            "Use ImageVectorNodeEmitter.emit() instead.",
            replaceWith = ReplaceWith(
                "ImageVectorNodeEmitter().emit(this)",
                "dev.tonholo.s2c.emitter.imagevector.ImageVectorNodeEmitter",
            ),
        )
        override fun materialize(): String = ImageVectorNodeEmitter().emit(this)
    }

    @Serializable
    data class Group(
        val commands: List<ImageVectorNode>,
        val minified: Boolean,
        val params: Params = Params(),
        @Transient override val transformations: List<AffineTransformation>? = null,
    ) : ImageVectorNode,
        MethodSizeAccountable {
        companion object {
            private const val GROUP_APPROXIMATE_BYTE_SIZE = 90
            private const val CLIP_PATH_APPROXIMATE_BYTE_SIZE = 30
        }

        @Serializable
        data class Params(
            val clipPath: NodeWrapper? = null,
            val rotate: Float? = null,
            val pivotX: Float? = null,
            val pivotY: Float? = null,
            val scaleX: Float? = null,
            val scaleY: Float? = null,
            val translationX: Float? = null,
            val translationY: Float? = null,
        ) : MethodSizeAccountable {
            /**
             * The approximate bytecode size accounted of the group parameters.
             */
            override val approximateByteSize: Int
                get() = (rotate?.let { FLOAT_APPROXIMATE_BYTE_SIZE } ?: 0) +
                    (pivotX?.let { FLOAT_APPROXIMATE_BYTE_SIZE } ?: 0) +
                    (pivotY?.let { FLOAT_APPROXIMATE_BYTE_SIZE } ?: 0) +
                    (scaleX?.let { FLOAT_APPROXIMATE_BYTE_SIZE } ?: 0) +
                    (scaleY?.let { FLOAT_APPROXIMATE_BYTE_SIZE } ?: 0) +
                    (translationX?.let { FLOAT_APPROXIMATE_BYTE_SIZE } ?: 0) +
                    (translationY?.let { FLOAT_APPROXIMATE_BYTE_SIZE } ?: 0) +
                    (clipPath?.nodes?.approximateByteSize?.let(CLIP_PATH_APPROXIMATE_BYTE_SIZE::plus) ?: 0)

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
                addAll(commands.flatMap { it.imports })
            }
        }

        /**
         * The approximate bytecode size of the `group` instruction,
         * accounting for its components.
         */
        override val approximateByteSize: Int
            get() = GROUP_APPROXIMATE_BYTE_SIZE +
                params.approximateByteSize +
                commands.sumOf { it.approximateByteSize + 1 }

        @Deprecated(
            "Use ImageVectorNodeEmitter.emit() instead.",
            replaceWith = ReplaceWith(
                "ImageVectorNodeEmitter().emit(this)",
                "dev.tonholo.s2c.emitter.imagevector.ImageVectorNodeEmitter",
            ),
        )
        override fun materialize(): String = ImageVectorNodeEmitter().emit(this)
    }

    /**
     * A Chunk function wrapper to separate the icon instructions in smaller pieces.
     */
    @Serializable
    data class ChunkFunction(val functionName: String, val nodes: List<ImageVectorNode>) :
        ImageVectorNode {
        override val transformations: List<AffineTransformation>
            get() = error("Transformation should be applied before creating chunk functions.")
        override val imports: Set<String> = emptySet()
        override val approximateByteSize: Int
            get() = error("A chunk function should not compute its byte size")

        init {
            check(nodes.none { it is ChunkFunction })
        }

        /**
         * Create the chunk function wrapping the `path`/`group` instructions
         * within a smaller method.
         */
        @Deprecated(
            message = "Use ImageVectorNodeEmitter.emitChunkFunctionDefinition() instead.",
            replaceWith = ReplaceWith(
                expression = "ImageVectorNodeEmitter().emitChunkFunctionDefinition(this)",
                imports = ["dev.tonholo.s2c.emitter.imagevector.ImageVectorNodeEmitter"],
            ),
        )
        fun createChunkFunction(): String =
            ImageVectorNodeEmitter().emitChunkFunctionDefinition(this)

        @Deprecated(
            "Use ImageVectorNodeEmitter.emit() instead.",
            replaceWith = ReplaceWith(
                "ImageVectorNodeEmitter().emit(this)",
                "dev.tonholo.s2c.emitter.imagevector.ImageVectorNodeEmitter",
            ),
        )
        override fun materialize(): String = ImageVectorNodeEmitter().emit(this)
    }

    // Support class to Paths. It should not be inherited from ImageVectorNode
    @Serializable
    data class NodeWrapper(val normalizedPath: String, val nodes: List<PathNodes>) {
        operator fun plus(other: NodeWrapper): NodeWrapper = NodeWrapper(
            normalizedPath = "$normalizedPath ${other.normalizedPath}",
            // Handle auto close if last command isn't closing path.
            nodes = nodes + other.nodes,
        )
    }
}

context(logger: Logger)
internal fun String.asNodeWrapper(minified: Boolean): ImageVectorNode.NodeWrapper {
    val normalizedPath = normalizePath(this)
    val nodes = logger.verboseSection("Starting path") {
        val commands = normalizedPath.split(" ").filter { it.isNotEmpty() }.toMutableList()
        logger.verbose("commands=$commands")
        var lastCommand = Char.EMPTY
        val nodes = mutableListOf<PathNodes>()
        while (commands.isNotEmpty()) {
            var current = commands.first()
            var currentCommand = current.first()
            val isContinuation = (currentCommand.isDigit() || currentCommand == '-' || currentCommand == '.') &&
                lastCommand != Char.EMPTY
            if (isContinuation) {
                currentCommand = when (PathCommand.MoveTo.value) {
                    // For any subsequent coordinate pair(s) after MoveTo (M/m) are interpreted as parameter(s)
                    // for implicit absolute LineTo (L/l) command(s)
                    lastCommand.lowercaseChar() if lastCommand.isLowerCase() ->
                        PathCommand.LineTo.value

                    lastCommand.lowercaseChar() -> PathCommand.LineTo.uppercaseChar()

                    else -> lastCommand
                }
                current = currentCommand + current
            }

            logger.verbose("current commands list=$commands")
            logger.verbose("current=$current, currentCommand=$currentCommand")

            val isRelative = isRelativeCommand(nodes, currentCommand)
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

/**
 * Verifies if the current command is a relative command.
 *
 * @return `true` in case the command is lowercased and it is not the very
 *  first move to command, which should be absolute
 */
private fun isRelativeCommand(node: List<PathNodes>, command: Char): Boolean =
    command.isLowerCase() && (
        node.isNotEmpty() || !command.equals(PathCommand.MoveTo.value, ignoreCase = true)
        )

context(logger: Logger)
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
            message = "Not support SVG/Android Vector command. Command = $currentCommand",
        )
    }
} catch (e: NumberFormatException) {
    logger.debug(
        """
        |Error while parsing Path command. Received parameters:
        |current = $current,
        |commands = $commands,
        |isRelative = $isRelative,
        |currentCommand = $currentCommand,
        |minified = $minified,
        |
        """.trimMargin(),
    )
    throw e
} catch (e: NoSuchElementException) {
    logger.debug(
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
        """.trimMargin(),
    )
    throw e
}

private fun resetDotCount(current: Char): Int = if (current == '.') 1 else 0

private fun calculateDotCount(
    char: Char,
    dotCount: Int,
    lastChar: Char,
): Int = if (char == '.') {
    dotCount + 1
} else if ((lastChar.isDigit() && char.isWhitespace()) || lastChar.isWhitespace()) {
    resetDotCount(current = char)
} else {
    dotCount
}

private fun StringBuilder.trimWhitespaceBeforeClose(lastChar: Char, current: Char): StringBuilder =
    apply {
        if (lastChar == ' ' && current == 'z') {
            setLength(length - 1)
        }
    }

context(logger: Logger)
private fun normalizePath(path: String): String {
    logger.debugSection("Normalizing path") {
        logger.debug("Original path value = $path")
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

        val isNotENotation = char.lowercaseChar() != 'e'
        val isNotClosingCommand = (char.isLetter() && char.lowercaseChar() != PathCommand.Close.value)
        val isNegativeSignSeparator = (lastChar.isDigit() && char == '-')
        val reachMaximumDotNumbers = dotCount == 2

        char.toPathCommand()?.let {
            command = it
        }
        arcCommandPosition = calculateArcCommandPosition(
            command,
            position = arcCommandPosition,
            current = char,
            lastChar = lastChar,
        )
        val isSweepFlagWithNoSpace = arcCommandPosition == PathCommand.ARC_TO_SWEEP_FLAG_POSITION &&
            lastChar.isDigit() &&
            char.isWhitespace().not()

        parsedPath
            .trimWhitespaceBeforeClose(lastChar, current = char)
            .append(
                when {
                    isNotENotation && (isNotClosingCommand || isNegativeSignSeparator || reachMaximumDotNumbers) -> {
                        dotCount = resetDotCount(current = char)
                        " $char"
                    }

                    isSweepFlagWithNoSpace -> " $char "

                    else -> {
                        char
                    }
                },
            )
        lastChar = char
    }

    logger.debugSection("Finished Normalizing") {
        logger.debug("Normalized path value = $parsedPath")
    }

    return parsedPath.toString().trimStart()
}

private fun calculateArcCommandPosition(
    command: PathCommand,
    position: Int,
    current: Char,
    lastChar: Char,
): Int = when {
    command != PathCommand.ArcTo -> -1

    lastChar == PathCommand.ArcTo.value || current == PathCommand.ArcTo.value -> 0

    position in PathCommand.ARC_TO_LARGE_ARC_POSITION..PathCommand.ARC_TO_SWEEP_FLAG_POSITION &&
        lastChar.isDigit() -> position + 1

    lastChar.isWhitespace() && (current.isDigit() || current == '-') -> position + 1

    else -> position
}
