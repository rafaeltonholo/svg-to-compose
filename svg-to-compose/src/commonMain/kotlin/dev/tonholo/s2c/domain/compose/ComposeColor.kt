package dev.tonholo.s2c.domain.compose

import kotlin.jvm.JvmInline
import kotlin.math.roundToInt

private const val FULL_HEXADECIMAL_COLOR_SIZE = 6
private const val HALF_HEXADECIMAL_COLOR_SIZE = 3
private const val RGBA_PREFIX = "RGBA"
private const val RGBA_SIZE = 4
private const val RGB_PREFIX = "RGB"
private const val RGB_SIZE = 3
private const val RGB_MAX_VALUE = 255

/**
 * Converts a hexadecimal color into a Color object
 */
@JvmInline
value class ComposeColor(override val value: String) : ComposeType<String> {
    companion object {
        private const val NAME = "Color"
        const val IMPORT = "androidx.compose.ui.graphics.$NAME"
    }

    override val name: String
        get() = NAME
    override val imports: Set<String>
        get() = emptySet() // should never be used directly.

    val color: String
        get() = value.uppercase()
            .removePrefix("#")
            .let {
                when {
                    it.startsWith(RGBA_PREFIX) || it.startsWith(RGB_PREFIX) -> fromRgba(it)

                    it.length == FULL_HEXADECIMAL_COLOR_SIZE -> "FF$it"

                    it.length == HALF_HEXADECIMAL_COLOR_SIZE -> {
                        val (r, g, b) = it.toCharArray()
                        "FF$r$r$g$g$b$b"
                    }

                    it.isEmpty() || it.lowercase().contains("url") ->
                        "FF000000 /* not supported this \"$it\" */" // not supported yet.
                    else -> it
                }
            }

    override fun toCompose(): String? = color
        .takeIf { it.lowercase() != "none" }
        ?.let { "$NAME(0x$it)" }

    private fun fromRgba(rgba: String): String {
        val values = rgba
            .removePrefix("$RGBA_PREFIX(")
            .removePrefix("$RGB_PREFIX(")
            .removeSuffix(")")
            .split(", ", ",")
        return when (values.size) {
            RGBA_SIZE -> {
                values.foldIndexed("") { index, acc, value ->
                    if (index == values.lastIndex) {
                        (value.toFloat() * RGB_MAX_VALUE).roundToInt().toString(radix = 16) + acc
                    } else {
                        acc + value.toInt().toString(radix = 16)
                    }
                }.uppercase()
            }

            RGB_SIZE -> {
                values.fold("") { acc, value -> acc + value.toInt().toString(radix = 16) }
            }

            else -> "none"
        }
    }
}
