package dev.tonholo.s2c.emitter.imagevector

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.svg.SvgColor
import dev.tonholo.s2c.domain.svg.toBrush
import dev.tonholo.s2c.emitter.FormatConfig
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
            |)"""
        } else {
            ""
        }

        return """
            |group$groupParamsString {
            |$groupPaths
            |}
        """.trimMargin()
    }

    private fun emitChunkFunctionCall(chunk: ImageVectorNode.ChunkFunction): String = "${chunk.functionName}()"

    private fun buildPathParameterList(path: ImageVectorNode.Path): List<Pair<String, String>> = buildList {
        with(path.params) {
            fill?.let { brush ->
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
                add("strokeLineMiter" to "${it}f")
            }
            strokeLineWidth?.let {
                add("strokeLineWidth" to "${it}f")
            }

            if (fill == null && stroke == null) {
                add("fill" to requireNotNull(SvgColor.Default.toBrush().toCompose()))
            }
        }
    }

    @Suppress("CyclomaticComplexMethod")
    private fun buildGroupParameters(group: ImageVectorNode.Group): Set<Pair<String, String>> = with(group.params) {
        val doubleIndent = indent.repeat(2)
        buildSet {
            clipPath?.let {
                val clipPathData = clipPath.nodes
                    .joinToString("\n$doubleIndent") { node ->
                        pathNodeEmitter.emit(node)
                            .replace("\n", "\n$doubleIndent")
                            .trimEnd()
                    }
                val value = """
                    |PathData {
                    |$indent$indent$clipPathData
                    |$indent}"""
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
