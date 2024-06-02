package dev.tonholo.s2c.domain.svg.transform

import dev.tonholo.s2c.geom.AffineTransformation
import kotlin.jvm.JvmInline

private val transformsMap = mapOf(
    "matrix" to { args: List<Float> ->
        @Suppress("MagicNumber")
        AffineTransformation.Matrix(
            floatArrayOf(args[0], args[2], args[4]),
            floatArrayOf(args[1], args[3], args[5]),
            floatArrayOf(0f, 0f, 1f),
        )
    },
    "translate" to { args: List<Float> ->
        AffineTransformation.Translate(tx = args[0], ty = args.getOrElse(1) { 0f })
    },
    "scale" to { args: List<Float> ->
        AffineTransformation.Scale(sx = args[0], sy = args.getOrElse(index = 1, defaultValue = { args[0] }))
    },
    "rotate" to { args: List<Float> ->
        AffineTransformation.Rotate(
            angle = args[0],
            centerX = args.getOrElse(index = 1, defaultValue = { 0f }),
            centerY = args.getOrElse(index = 2, defaultValue = { 0f }),
        )
    },
    "skewX" to { args: List<Float> ->
        AffineTransformation.SkewX(
            angle = args[0],
        )
    },
    "skewY" to { args: List<Float> ->
        AffineTransformation.SkewY(
            angle = args[0],
        )
    },
)

@JvmInline
value class SvgTransform(val value: String) {
    fun toTransformations(): List<AffineTransformation> {
        return transforms()
            .mapNotNull { (name, values) ->
                transformsMap[name]?.invoke(values)
            }
    }

    private inline fun transforms(): List<Pair<String, List<Float>>> = value
        .split(REGEX_TRANSFORM_SPLIT.toRegex())
        .map { transform ->
            val name = transform.takeWhile { it != '(' }
            val values = transform
                .removePrefix("$name(")
                .removeSuffix(")")
                .split(", ", ", ", " ")
                .map { it.toFloat() }

            name to values
        }

    companion object {
        private const val REGEX_TRANSFORM_SPLIT =
            """(?<=\))\s(?=\w)"""
    }
}
