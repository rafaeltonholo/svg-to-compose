package dev.tonholo.s2c

import dev.tonholo.s2c.runtime.S2cConfig
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Context for managing the runtime configuration of the SVG to Compose conversion process.
 *
 * Provides reactive and snapshot-based access to [S2cConfig], allowing components
 * to observe changes or retrieve the current state.
 */
interface SvgToComposeContext {
    /**
     * A reactive [StateFlow] representing the current [S2cConfig].
     *
     * This flow allows observers to track and respond to configuration changes
     * throughout the SVG to Compose conversion process.
     */
    val config: StateFlow<S2cConfig>

    /**
     * A read-only snapshot of the current [S2cConfig].
     *
     * Provides a non-reactive, immediate value of the configuration state.
     */
    val configSnapshot: S2cConfig

    /**
     * Updates the current configuration state.
     *
     * This method applies a transformation function to the current [S2cConfig],
     * allowing for atomic updates to the configuration.
     *
     * @param builder A lambda that receives the current [S2cConfig] and returns the updated configuration.
     */
    fun updateConfig(builder: (S2cConfig) -> S2cConfig)
}

@Inject
class SvgToComposeContextImpl(initial: S2cConfig) : SvgToComposeContext {
    private val _config: MutableStateFlow<S2cConfig> = MutableStateFlow(value = initial)
    override val config: StateFlow<S2cConfig> = _config.asStateFlow()

    override val configSnapshot: S2cConfig
        get() = config.value

    override fun updateConfig(builder: (S2cConfig) -> S2cConfig) {
        _config.update(builder)
    }
}

/**
 * Updates the current configuration if it matches the specific type [T].
 *
 * @param T The specific type of [S2cConfig] to be updated.
 */
inline fun <reified T : S2cConfig> SvgToComposeContext.updateConfig(crossinline builder: (T) -> T) {
    updateConfig { config ->
        if (config is T) {
            builder(config)
        } else {
            config
        }
    }
}
