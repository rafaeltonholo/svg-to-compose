package dev.tonholo.s2c.emitter.imagevector

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.svg.SvgColor
import dev.tonholo.s2c.domain.svg.toBrush
import dev.tonholo.s2c.emitter.FormatConfig
import dev.tonholo.s2c.extensions.toStringConsistent
/**
 * Emits Kotlin code for [ImageVectorNode] instances (Path, Group, ChunkFunction).
 *
 * Delegates path command emission to [PathNodeEmitter].
 *
 * @property formatConfig The formatting configuration to use.
 * @property pathNodeEmitter The emitter for individual path commands.
 */
internal class ImageVectorNodeEmitter(
    private val formatConfig: FormatConfig = FormatConfig(),
    private val pathNodeEmitter: PathNodeEmitter = PathNodeEmitter(formatConfig),
) {
    private val indent: String get() = formatConfig.indentUnit

    /**
     * Emits the Kotlin code for an [ImageVectorNode].
     *
     * @param node The node to emit.
     * @return The emitted Kotlin code string.
     */
    fun emit(node: ImageVectorNode): String = when (node) {
        is ImageVectorNode.Path -> emitPath(node)
        is ImageVectorNode.Group -> emitGroup(node)
        is ImageVectorNode.ChunkFunction -> emitChunkFunctionCall(node)
    }

    /**
     * Emits only the path drawing commands (moveTo, lineTo, etc.) for a [path] node,
     * without the surrounding `path(...)` call or braces.
     *
     * @param path The path node whose commands to emit.
     * @return The emitted path command strings, one per line.
     */
    fun emitPathCommands(path: ImageVectorNode.Path): String =
        path.wrapper.nodes.joinToString("\n") {
            pathNodeEmitter.emit(it).trimEnd()
        }

    /**
     * Emits a `PathData { ... }` block for a clip path.
     *
     * @param clipPath The clip path wrapper containing the path nodes.
     * @return The emitted `PathData { ... }` string, or `null` if [clipPath] is `null`.
     */
    fun emitClipPathData(clipPath: ImageVectorNode.NodeWrapper?): String? {
        if (clipPath == null) return null
        val doubleIndent = indent.repeat(2)
        val clipPathData = clipPath.nodes
            .joinToString("\n$doubleIndent") { node ->
                pathNodeEmitter.emit(node)
                    .replace("\n", "\n$doubleIndent")
                    .trimEnd()
            }
        return """
            |PathData {
            |$indent$indent$clipPathData
            |$indent}"""
            .trimMargin()
    }

    /**
     * Emits a chunk function definition (the private extension function body).
     *
     * @param chunk The chunk function to emit.
     * @return The emitted function definition.
     */
    fun emitChunkFunctionDefinition(chunk: ImageVectorNode.ChunkFunction): String {
        val bodyFunction = chunk.nodes.joinToString("\n") {
            emit(it).trimEnd()
        }.prependIndent(indent)

        return """
                |private fun ImageVector.Builder.${chunk.functionName}() {
                |$bodyFunction
                |}
        """.trimMargin()
    }

    private fun emitPath(path: ImageVectorNode.Path): String {
        val pathNodes = path.wrapper.nodes.joinToString("\n$indent") {
            pathNodeEmitter.emit(it)
                .replace("\n", "\n$indent")
                .trimEnd()
        }

        val pathParams = buildPathParameterList(path)

        val pathParamsString = if (pathParams.isNotEmpty()) {
            """(
            |${
                pathParams.joinToString("\n") { (param, value) ->
                    "$param = $value,".prependIndent(formatConfig.indentUnit)
                }
            }
            |)"""
        } else {
            ""
        }

        val comment = if (path.minified) "" else "// ${path.wrapper.normalizedPath}\n|"

        return """
            |${comment}path$pathParamsString {
            |$indent$pathNodes
            |}
        """.trimMargin()
    }

    private fun emitGroup(group: ImageVectorNode.Group): String {
        val groupPaths = group.commands
            .joinToString("\n") {
                emit(it).trimEnd()
            }.prependIndent(indent)

        val header = emitGroupHeader(group)
        return """
            |$header {
            |$groupPaths
            |}
        """.trimMargin()
    }

    /**
     * Emits only the `group(...)` call signature without the body or braces.
     * Used by [TemplateNodeEmitter] when no group fragment is configured but
     * template-aware recursion of children is still needed.
     */
    fun emitGroupHeader(group: ImageVectorNode.Group): String {
        val groupParams = buildGroupParameters(group)

        val groupParamsString = if (groupParams.isNotEmpty()) {
            val params = groupParams.joinToString("\n") { (param, value) ->
                if (param == CLIP_PATH_PARAM_NAME && !group.minified && group.params.clipPath != null) {
                    "$indent// ${group.params.clipPath.normalizedPath}\n"
                } else {
                    ""
                } + "$indent$param = $value,"
            }
            """(
            |$params
            |)
            """.trimMargin()
        } else {
            ""
        }
        return "group$groupParamsString"
    }

    private fun emitChunkFunctionCall(chunk: ImageVectorNode.ChunkFunction): String =
        "${chunk.functionName}()"

    private fun buildPathParameterList(path: ImageVectorNode.Path): List<Pair<String, String>> =
        buildList {
            with(path.params) {
                fill?.let { brush ->
                    brush.toCompose()?.let { add("fill" to it) }
                }
                fillAlpha?.let {
                    add("fillAlpha" to "${it.toStringConsistent()}f")
                }
                pathFillType?.let {
                    add("pathFillType" to "${it.toCompose()}")
                }
                stroke?.let { stroke ->
                    stroke.toCompose()?.let { add("stroke" to it) }
                }
                strokeAlpha?.let {
                    add("strokeAlpha" to "${it.toStringConsistent()}f")
                }
                strokeLineCap?.let {
                    add("strokeLineCap" to "${it.toCompose()}")
                }
                strokeLineJoin?.let {
                    add("strokeLineJoin" to "${it.toCompose()}")
                }
                strokeMiterLimit?.let {
                    add("strokeLineMiter" to "${it.toStringConsistent()}f")
                }
                strokeLineWidth?.let {
                    add("strokeLineWidth" to "${it.toStringConsistent()}f")
                }

                if (fill == null && stroke == null) {
                    add("fill" to requireNotNull(SvgColor.Default.toBrush().toCompose()))
                }
            }
        }

    @Suppress("CyclomaticComplexMethod")
    private fun buildGroupParameters(group: ImageVectorNode.Group): Set<Pair<String, String>> =
        with(group.params) {
            buildSet {
                emitClipPathData(clipPath)?.let { add(CLIP_PATH_PARAM_NAME to it) }
                rotate?.let { add(ROTATE_PARAM_NAME to "${rotate.toStringConsistent()}f") }
                pivotX?.let { add(PIVOT_X_PARAM_NAME to "${pivotX.toStringConsistent()}f") }
                pivotY?.let { add(PIVOT_Y_PARAM_NAME to "${pivotY.toStringConsistent()}f") }
                scaleX?.let { add(SCALE_X_PARAM_NAME to "${scaleX.toStringConsistent()}f") }
                scaleY?.let { add(SCALE_Y_PARAM_NAME to "${scaleY.toStringConsistent()}f") }
                translationX?.let { add(TRANSLATION_X_PARAM_NAME to "${translationX.toStringConsistent()}f") }
                translationY?.let { add(TRANSLATION_Y_PARAM_NAME to "${translationY.toStringConsistent()}f") }
            }
        }

    private companion object {
        const val CLIP_PATH_PARAM_NAME = "clipPathData"
        const val ROTATE_PARAM_NAME = "rotate"
        const val PIVOT_X_PARAM_NAME = "pivotX"
        const val PIVOT_Y_PARAM_NAME = "pivotY"
        const val SCALE_X_PARAM_NAME = "scaleX"
        const val SCALE_Y_PARAM_NAME = "scaleY"
        const val TRANSLATION_X_PARAM_NAME = "translationX"
        const val TRANSLATION_Y_PARAM_NAME = "translationY"
    }
}
