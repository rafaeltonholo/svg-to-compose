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
        operator fun invoke(value: String): AvgGradientTileMode = when (value) {
            Clamp.toString() -> Clamp
            Mirror.toString() -> Mirror
            Repeat.toString() -> Repeat
            Disabled.toString() -> Disabled
            else -> {
                warn(
                    "'$value' is an unsupported type of tileMode for ${AvgGradient.TAG_NAME} tag. " +
                        "Using default to '${Clamp}",
                )
                Clamp
            }
        }
    }
}
