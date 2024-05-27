package dev.tonholo.s2c.domain.svg.gradient

import dev.tonholo.s2c.domain.compose.GradientTileMode
import dev.tonholo.s2c.logger.warn

enum class SvgGradientSpreadMethod {
    Pad,
    Reflect,
    Repeat,
    ;

    override fun toString(): String = name.lowercase()

    fun toCompose(): GradientTileMode = when (this) {
        Pad -> GradientTileMode.Clamp
        Reflect -> GradientTileMode.Mirror
        Repeat -> GradientTileMode.Repeated
    }

    companion object {
        operator fun invoke(value: String): SvgGradientSpreadMethod = when (value.lowercase()) {
            Pad.toString() -> Pad
            Reflect.toString() -> Reflect
            Repeat.toString() -> Repeat
            else -> {
                warn(
                    "'$value' is an unsupported type of spreadMethod. Using default to '${Pad}'",
                )
                Pad
            }
        }
    }
}
