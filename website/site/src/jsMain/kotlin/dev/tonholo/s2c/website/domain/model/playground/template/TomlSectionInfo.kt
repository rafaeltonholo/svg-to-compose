package dev.tonholo.s2c.website.domain.model.playground.template

/**
 * Describes a TOML section header (e.g. `[templates]`).
 *
 * @property header The section name without brackets.
 * @property description Human-readable explanation shown in tooltips.
 * @property keys Available keys within this section.
 */
data class TomlSectionInfo(val header: String, val description: String, val keys: List<TomlKeyInfo>)
