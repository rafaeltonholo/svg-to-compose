package dev.tonholo.s2c.emitter.template.config

import com.akuleshov7.ktoml.Toml
import com.akuleshov7.ktoml.TomlInputConfig
import dev.tonholo.s2c.emitter.template.TemplateConstants.Namespace
import kotlinx.serialization.serializer

/**
 * Parses and validates `s2c.template.toml` content into a [TemplateEmitterConfig].
 */
object TemplateConfigParser {
    private val toml = Toml(
        inputConfig = TomlInputConfig(
            ignoreUnknownNames = true,
        ),
    )

    /**
     * Deserializes TOML content into a [TemplateEmitterConfig].
     *
     * @param content The raw TOML string.
     * @return The parsed configuration.
     * @throws IllegalArgumentException if validation fails.
     * @throws kotlinx.serialization.SerializationException if the TOML is malformed.
     */
    fun parse(content: String): TemplateEmitterConfig {
        val config = toml.decodeFromString(serializer<TemplateEmitterConfig>(), content)
        validate(config)
        return config
    }

    private fun validate(config: TemplateEmitterConfig) {
        val definedImportKeys = config.definitions.imports.keys
        val defPattern = Regex("""\$\{${Namespace.DEFINITIONS}:([a-z][a-z0-9_.]*)}""")

        val allTemplateTexts = buildList {
            config.templates.iconTemplate?.let(::add)
            config.templates.preview?.template?.let(::add)
            config.fragments.values.forEach(::add)
        }

        for (text in allTemplateTexts) {
            for (match in defPattern.findAll(text)) {
                val key = match.groupValues[1]
                require(key in definedImportKeys) {
                    $$"Template references undefined import key '${def:$$key}'. " +
                        $$"Available keys: $${definedImportKeys.joinToString()}"
                }
            }
        }
    }
}
