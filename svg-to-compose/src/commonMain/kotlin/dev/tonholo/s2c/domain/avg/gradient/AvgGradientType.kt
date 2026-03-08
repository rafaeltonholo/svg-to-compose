package dev.tonholo.s2c.domain.avg.gradient

import dev.tonholo.s2c.logger.Logger

enum class AvgGradientType {
    Linear,
    Radial,
    Sweep,
    ;

    override fun toString(): String = name.lowercase()

    companion object {
        context(logger: Logger)
        operator fun invoke(value: String): AvgGradientType = when (value) {
            Linear.toString() -> Linear
            Radial.toString() -> Radial
            Sweep.toString() -> Sweep
            else -> {
                logger.warn(
                    "'$value' is an unsupported type of type for ${AvgGradient.TAG_NAME} tag. " +
                        "Using default to '$Linear",
                )
                Linear
            }
        }
    }
}
