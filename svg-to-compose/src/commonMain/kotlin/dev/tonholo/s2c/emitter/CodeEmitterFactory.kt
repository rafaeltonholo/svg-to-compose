package dev.tonholo.s2c.emitter

import dev.tonholo.s2c.emitter.imagevector.ImageVectorEmitter
import dev.tonholo.s2c.emitter.template.TemplateEmitter
import dev.tonholo.s2c.emitter.template.config.TemplateEmitterConfig
import dev.tonholo.s2c.logger.Logger
import dev.zacsweers.metro.Inject

/**
 * Factory for creating [CodeEmitter] instances based on [OutputFormat].
 *
 * @property logger The logger instance for diagnostic output.
 */
@Inject
class CodeEmitterFactory(private val logger: Logger) {
    /**
     * Creates a [CodeEmitter] for the given output format and format configuration.
     *
     * When [templateEmitterConfig] is provided, wraps the default emitter with a
     * [TemplateEmitter] that applies the template configuration.
     *
     * @param outputFormat The desired output format.
     * @param formatConfig The formatting configuration to use.
     * @param templateEmitterConfig Optional template configuration for custom output.
     * @return A [CodeEmitter] instance.
     */
    fun create(
        outputFormat: OutputFormat = OutputFormat.IMAGE_VECTOR,
        formatConfig: FormatConfig = FormatConfig(),
        templateEmitterConfig: TemplateEmitterConfig? = null,
    ): CodeEmitter {
        val baseEmitter = when (outputFormat) {
            OutputFormat.IMAGE_VECTOR -> ImageVectorEmitter(logger, formatConfig)
        }
        return if (templateEmitterConfig != null) {
            TemplateEmitter(logger, formatConfig, templateEmitterConfig, baseEmitter)
        } else {
            baseEmitter
        }
    }
}
