package dev.tonholo.s2c.output

/**
 * Functional interface for consuming [ConversionEvent]s.
 *
 * Implementations decide how to present or record conversion progress.
 * For example, a CLI module might render a progress bar while a Gradle
 * plugin might log structured messages.
 *
 * Can be implemented as a lambda:
 * ```kotlin
 * val renderer = OutputRenderer { event -> println(event) }
 * ```
 */
fun interface OutputRenderer {
    /**
     * Called each time a [ConversionEvent] is produced during a conversion run.
     *
     * Implementations should avoid long-running or blocking work inside this
     * callback to keep the conversion pipeline responsive.
     *
     * @param event the conversion event to handle.
     */
    fun onEvent(event: ConversionEvent)
}
