package dev.tonholo.s2c.domain.svg.transform

import dev.tonholo.s2c.geom.AffineTransformation
import kotlin.jvm.JvmInline

private val transformsMap = mapOf(
    "matrix" to { args: List<Double> ->
        @Suppress("MagicNumber")
        AffineTransformation.Matrix(
            doubleArrayOf(args[0], args[2], args[4]),
            doubleArrayOf(args[1], args[3], args[5]),
            doubleArrayOf(0.0, 0.0, 1.0),
        )
    },
    "translate" to { args: List<Double> ->
        AffineTransformation.Translate(tx = args[0], ty = args.getOrElse(1) { 0.0 })
    },
    "scale" to { args: List<Double> ->
        AffineTransformation.Scale(sx = args[0], sy = args.getOrElse(index = 1, defaultValue = { args[0] }))
    },
    "rotate" to { args: List<Double> ->
        AffineTransformation.Rotate(
            angle = args[0],
            centerX = args.getOrElse(index = 1, defaultValue = { 0.0 }),
            centerY = args.getOrElse(index = 2, defaultValue = { 0.0 }),
        )
    },
    "skewX" to { args: List<Double> ->
        AffineTransformation.SkewX(
            angle = args[0],
        )
    },
    "skewY" to { args: List<Double> ->
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

    private fun transforms(): List<Pair<String, List<Double>>> = value
        .split(REGEX_TRANSFORM_SPLIT.toRegex())
        .mapNotNull { transform ->
            if (transform.isBlank()) {
                null
            } else {
                val name = transform.takeWhile { it != '(' }.trim()
                val values = transform
                    .trim()
                    .removePrefix("$name(")
                    .removeSuffix(")")
                    .split(", ", ", ", " ", ",")
                    .map {
                        requireNotNull(it.toDoubleOrNull()) {
                            "unable to parse value $it to Double. transform = $value"
                        }
                    }

                name to values
            }
        }

    companion object {
        private const val REGEX_TRANSFORM_SPLIT =
            """(?<=\))(?=\s|\w)"""
    }
}
