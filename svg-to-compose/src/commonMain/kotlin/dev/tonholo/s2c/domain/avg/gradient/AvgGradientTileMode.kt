package dev.tonholo.s2c.domain.avg.gradient

import dev.tonholo.s2c.logger.warn

enum class AvgGradientTileMode {
    Clamp,
    Mirror,
    Repeat,
    Disabled,
    ;

    override fun toString(): String = name.lowercase()

    companion object {
        operator fun invoke(value: String): AvgGradientTileMode = when (value.lowercase()) {
            Clamp.toString().lowercase() -> Clamp
            Mirror.toString().lowercase() -> Mirror
            Repeat.toString().lowercase() -> Repeat
            Disabled.toString().lowercase() -> Disabled
            else -> {
                warn(
                    "'$value' is an unsupported type of tileMode for ${AvgGradient.TAG_NAME} tag. " +
                        "Using default to '$Clamp",
                )
                Clamp
            }
        }
    }
}
