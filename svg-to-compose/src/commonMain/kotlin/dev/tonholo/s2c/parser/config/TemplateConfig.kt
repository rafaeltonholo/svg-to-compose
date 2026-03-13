package dev.tonholo.s2c.parser.config

import okio.Path

/**
 * Configuration for template-based output customization.
 *
 * @property configPath optional path to an `s2c.template.toml` file.
 * When `null` and [noDiscovery] is false, the processor will attempt
 * auto-discovery by walking up from the output directory.
 * @property noDiscovery if `true`, disables auto-discovery of template files.
 */
data class TemplateConfig(val configPath: Path? = null, val noDiscovery: Boolean = false)
