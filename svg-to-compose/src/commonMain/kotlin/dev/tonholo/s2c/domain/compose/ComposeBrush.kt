package dev.tonholo.s2c.domain.compose

import dev.tonholo.s2c.extensions.indented
import kotlin.jvm.JvmInline

sealed interface ComposeBrush : ComposeType<String> {
    @JvmInline
    value class SolidColor(override val value: String) : ComposeBrush {
        companion object {
            private const val NAME = "SolidColor"
            private val IMPORT = setOf(
                ComposeColor.IMPORT,
                "androidx.compose.ui.graphics.$NAME",
            )
        }

        override val name: String
            get() = NAME
        override val imports: Set<String>
            get() = IMPORT

        override fun toCompose(): String? = ComposeColor(value).toCompose()?.let { color ->
            "$name($color)"
        }

        override fun toString(): String = value
    }

    sealed interface Gradient : ComposeBrush {
        companion object {
            private const val NAME = "Brush"
            private val IMPORT =
                setOf(
                    ComposeColor.IMPORT,
                    "androidx.compose.ui.graphics.$NAME",
                )
            private const val INDENT_SIZE = 4
        }

        val colors: List<ComposeColor>
        val stops: List<Float>?

        override val name: String
            get() = NAME
        override val imports: Set<String>
            get() = IMPORT

        data class Linear(
            val start: ComposeOffset,
            val end: ComposeOffset,
            val tileMode: GradientTileMode,
            override val colors: List<ComposeColor>,
            override val stops: List<Float>? = null,
        ) : Gradient {
            override val value: String = toCompose()

            override fun toCompose(): String = buildString {
                append(name)
                appendLine(".linearGradient(")
                appendColors(stops, colors, INDENT_SIZE)
                appendLine("start = $start,".indented(INDENT_SIZE))
                appendLine("end = $end,".indented(INDENT_SIZE))
                if (tileMode != GradientTileMode.Clamp) {
                    appendLine("tileMode = $tileMode,".indented(INDENT_SIZE))
                }
                appendLine(")")
            }
        }

        data class Radial(
            val radius: Float,
            val center: ComposeOffset,
            val tileMode: GradientTileMode,
            override val colors: List<ComposeColor>,
            override val stops: List<Float>? = null,
        ) : Gradient {
            override val value: String = toCompose()

            override fun toCompose(): String = buildString {
                append(name)
                appendLine(".radialGradient(")
                appendColors(stops, colors, INDENT_SIZE)
                appendLine("center = $center,".indented(INDENT_SIZE))
                appendLine("radius = $radius,".indented(INDENT_SIZE))
                if (tileMode != GradientTileMode.Clamp) {
                    appendLine("tileMode = $tileMode,".indented(INDENT_SIZE))
                }
                appendLine(")")
            }
        }

        data class Sweep(
            val center: ComposeOffset,
            override val colors: List<ComposeColor>,
            override val stops: List<Float>? = null,
        ) : Gradient {
            override val value: String = toCompose()

            override fun toCompose(): String = buildString {
                append(name)
                appendLine(".radialGradient(")
                appendColors(stops, colors, INDENT_SIZE)
                appendLine("center = $center,".indented(INDENT_SIZE))
                appendLine(")")
            }
        }
    }
}

private fun StringBuilder.appendColors(
    stops: List<Float>?,
    colors: List<ComposeColor>,
    indent: Int,
) {
    if (stops != null) {
        stops
            .zip(colors)
            .asSequence()
            .mapNotNull { (stop, color) ->
                // filter not valid Compose colors
                color.toCompose()?.let { stop to it }
            }
            .map { (stop, color) ->
                // map to vararg arguments
                "${stop}f to $color,".indented(indent)
            }
            .forEach(::append)
    } else {
        appendLine("colors = listOf(".indented(indent))
        colors
            .asSequence()
            // filter not valid Compose colors
            .mapNotNull { it.toCompose() }
            // add indentation
            .map { "$it,".indented(indent * 2) }
            .forEach(::append)
        appendLine("),".indented(indent))
    }
}

fun String.toBrush(): ComposeBrush = ComposeBrush.SolidColor(this)
