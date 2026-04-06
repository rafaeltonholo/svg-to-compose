package dev.tonholo.s2c

import dev.tonholo.s2c.runtime.S2cConfig

/**
 * Process-scoped holder for the active [SvgToComposeContext].
 *
 * Entry points (CLI, Gradle plugin) call [initialize] or
 * [initializeWith] before processing and [reset] afterward.
 * Core library code reads [current] to access config without
 * constructor injection.
 */
object SvgToComposeContextProvider {
    private var _current: SvgToComposeContext? = null

    val current: SvgToComposeContext
        get() = checkNotNull(_current) {
            "SvgToComposeContext not initialized. Call initialize() before processing."
        }

    val currentOrNull: SvgToComposeContext?
        get() = _current

    fun initialize(context: SvgToComposeContext) {
        _current = context
    }

    /**
     * Creates a [SvgToComposeContext] from the given [config] and
     * sets it as the current context.
     */
    fun initializeWith(config: S2cConfig) {
        _current = SvgToComposeContextImpl(initial = config)
    }

    fun reset() {
        _current = null
    }
}
