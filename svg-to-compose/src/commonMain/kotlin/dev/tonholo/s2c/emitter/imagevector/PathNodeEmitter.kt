package dev.tonholo.s2c.emitter.imagevector

import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.emitter.FormatConfig
import dev.tonholo.s2c.extensions.removeTrailingZero

/**
 * Emits Kotlin path command function calls from [PathNodes] domain objects.
 *
 * Handles all path command types (MoveTo, LineTo, ArcTo, CurveTo, etc.)
 * using a `when` dispatch on the sealed class.
 *
 * @property formatConfig The formatting configuration to use.
 */
internal class PathNodeEmitter(private val formatConfig: FormatConfig = FormatConfig()) {
    /**
     * Emits the Kotlin code for a single [PathNodes] command.
     *
     * @param node The path node to emit.
     * @return The emitted Kotlin code string.
     */
    fun emit(node: PathNodes): String {
        val (fnName, forceInline) = when (node) {
            is PathNodes.MoveTo -> "moveTo" to true
            is PathNodes.LineTo -> "lineTo" to true
            is PathNodes.HorizontalLineTo -> "horizontalLineTo" to true
            is PathNodes.VerticalLineTo -> "verticalLineTo" to true
            is PathNodes.CurveTo -> "curveTo" to false
            is PathNodes.ReflectiveCurveTo -> "reflectiveCurveTo" to false
            is PathNodes.QuadTo -> "quadTo" to false
            is PathNodes.ReflectiveQuadTo -> "reflectiveQuadTo" to false
            is PathNodes.ArcTo -> "arcTo" to false
        }
        return emitPathCommand(node, fnName, forceInline)
    }

    private fun emitPathCommand(
        node: PathNodes,
        fnName: String,
        forceInline: Boolean,
    ): String {
        val params = node.buildParameters()
        val paramsString = params.toParameters(node.minified, forceInline)
        val relativeSuffix = if (node.isRelative) "Relative" else ""
        val comment = if (node.minified) "" else "// ${node.toString().removeTrailingZeroConsiderCloseCommand()}"
        val closeCmd = if (node.shouldClose) {
            """
            |close()
            |
            """.trimMargin()
        } else {
            ""
        }

        val result = """
            |${if (node.minified) "" else comment}
            |$fnName$relativeSuffix($paramsString)
            |$closeCmd
        """.trimMargin()

        return if (node.minified) result.trim() else result
    }

    private fun String.removeTrailingZeroConsiderCloseCommand(): String = removeTrailingZero()
        .replace("\\.0z\\b".toRegex(), "z")

    private fun Set<String>.toParameters(minified: Boolean, forceInline: Boolean): String {
        val indent = if (minified || forceInline) "" else formatConfig.indentUnit
        val separator = if (minified || forceInline) " " else "\n"
        val scape = if (minified || forceInline) " " else "|"
        return joinToString(separator) { "$it," }
            .prependIndent("$scape$indent")
            .let {
                if (minified || forceInline) it.substring(1, it.length - 1) else "\n$it\n"
            }
    }
}
