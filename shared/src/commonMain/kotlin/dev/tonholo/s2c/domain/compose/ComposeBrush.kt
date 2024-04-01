package dev.tonholo.s2c.domain.compose

import dev.tonholo.s2c.extensions.indented
import dev.tonholo.s2c.parser.method.MethodSizeAccountable
import kotlin.jvm.JvmInline

sealed interface ComposeBrush : ComposeType<String>, MethodSizeAccountable {
    @JvmInline
    value class SolidColor(override val value: String) : ComposeBrush {
        companion object {
            // By instantiating SolidColor, Color and an Integer (0xFF<color value>),
            // SolidColor brush accounts with 18 bytes approximately.
            private const val BYTECODE_SIZE = 18
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

        override val approximateByteSize: Int get() = BYTECODE_SIZE

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
            private const val INDENT_SIZE = 8

            // By calling Brush.linearGradient, Brush.radialGradient or Brush.sweepGradient
            // with no parameters, we add 16 bytes, approximately, to the method size.
            private const val BRUSH_GRADIENT_METHOD_BYTECODE_SIZE = 16
        }

        val colors: List<ComposeColor>
        val stops: List<Float>?

        override val name: String
            get() = NAME
        override val imports: Set<String>
            get() = IMPORT

        // Base calculation for all gradients.
        // The end value will differ when specific parameters get im place.
        override val approximateByteSize: Int
            get() = BRUSH_GRADIENT_METHOD_BYTECODE_SIZE +
                calculateColorBytes()

        fun calculateColorBytes(): Int {
            val stops = stops
            return if (stops.isNullOrEmpty()) {
                val size = colors.size
                val baseColorByteSize = 13
                if (size > 1) {
                    25 + (baseColorByteSize * (size - 1))
                } else {
                    8
                }
            } else {
                val size = stops.size
                val baseColorByteSize = 22
                25 + (baseColorByteSize * (size - 1))
            }
        }

        data class Linear(
            val start: ComposeOffset,
            val end: ComposeOffset,
            val tileMode: GradientTileMode?,
            override val colors: List<ComposeColor>,
            override val stops: List<Float>? = null,
        ) : Gradient {
            override val value: String = toCompose()
            override val imports: Set<String> = buildSet {
                addAll(super.imports)
                if (start != ComposeOffset.Zero) {
                    addAll(start.imports)
                }
                if (end != ComposeOffset.Infinite) {
                    addAll(end.imports)
                }
                tileMode?.imports?.let(::addAll)
            }

            override val approximateByteSize: Int
                get() = super.approximateByteSize +
                    (if (start != ComposeOffset.Zero) start.approximateByteSize else 0) +
                    (if (end != ComposeOffset.Infinite) end.approximateByteSize else 0) +
                    (tileMode?.approximateByteSize ?: 0)

            override fun toCompose(): String = buildString {
                append(name)
                appendLine(".linearGradient(")
                appendColors(stops, colors, INDENT_SIZE)
                start.toCompose().let {
                    if (it != ComposeOffset.ZERO) {
                        appendLine("start = $it,".indented(INDENT_SIZE))
                    }
                }
                end.toCompose().let {
                    if (it != ComposeOffset.INFINITE) {
                        appendLine("end = $it,".indented(INDENT_SIZE))
                    }
                }
                if (tileMode != null && tileMode != GradientTileMode.Clamp) {
                    appendLine("tileMode = ${tileMode.toCompose()},".indented(INDENT_SIZE))
                }
                append(")".indented(INDENT_SIZE / 2))
            }
        }

        data class Radial(
            val radius: Float?,
            val center: ComposeOffset?,
            val tileMode: GradientTileMode?,
            override val colors: List<ComposeColor>,
            override val stops: List<Float>? = null,
        ) : Gradient {
            override val value: String = toCompose()
            override val imports: Set<String> = buildSet {
                addAll(super.imports)
                if (center != null && center != ComposeOffset.Infinite) {
                    addAll(center.imports)
                }
                tileMode?.imports?.let(::addAll)
            }

            override val approximateByteSize: Int
                get() = super.approximateByteSize +
                    (if (radius != null) 1 else 0) +
                    (if (center != null && center != ComposeOffset.Infinite) center.approximateByteSize else 0) +
                    (tileMode?.approximateByteSize ?: 0)

            override fun toCompose(): String = buildString {
                append(name)
                appendLine(".radialGradient(")
                appendColors(stops, colors, INDENT_SIZE)
                if (center != null && center != ComposeOffset.Infinite) {
                    appendLine("center = ${center.toCompose()},".indented(INDENT_SIZE))
                }
                if (radius != null) {
                    appendLine("radius = ${radius}f,".indented(INDENT_SIZE))
                }
                if (tileMode != null && tileMode != GradientTileMode.Clamp) {
                    appendLine("tileMode = ${tileMode.toCompose()},".indented(INDENT_SIZE))
                }
                append(")".indented(INDENT_SIZE / 2))
            }
        }

        data class Sweep(
            val center: ComposeOffset?,
            override val colors: List<ComposeColor>,
            override val stops: List<Float>? = null,
        ) : Gradient {
            override val value: String = toCompose()
            override val imports: Set<String> = if (center == null) {
                super.imports
            } else {
                buildSet {
                    addAll(super.imports)
                    addAll(center.imports)
                }
            }

            override val approximateByteSize: Int
                get() = super.approximateByteSize +
                    (if (center != null && center != ComposeOffset.Infinite) center.approximateByteSize else 0)

            override fun toCompose(): String = buildString {
                append(name)
                appendLine(".sweepGradient(")
                appendColors(stops, colors, INDENT_SIZE)
                if (center != null) {
                    appendLine("center = ${center.toCompose()},".indented(INDENT_SIZE))
                }
                append(")".indented(INDENT_SIZE / 2))
            }
        }
    }
}

private fun StringBuilder.appendColors(
    stops: List<Float>?,
    colors: List<ComposeColor>,
    indent: Int,
) {
    if (stops.isNullOrEmpty()) {
        appendLine("colors = listOf(".indented(indent))
        colors
            .asSequence()
            // filter not valid Compose colors
            .mapNotNull { it.toCompose() }
            // add indentation
            .map { "$it,".indented(indent * 2) }
            .forEach(::appendLine)
        appendLine("),".indented(indent))
    } else {
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
            .forEach(::appendLine)
    }
}

fun String.toBrush(): ComposeBrush = ComposeBrush.SolidColor(this)
