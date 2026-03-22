package dev.tonholo.s2c.website.domain.model.playground.template

/**
 * Describes a single key that can appear inside a TOML section.
 *
 * @property key The TOML key name (left side of `=`).
 * @property description Human-readable explanation shown in autocomplete
 *   and tooltips.
 * @property insertValue The text inserted when the suggestion is accepted
 *   (the full `key = "..."` snippet).
 */
data class TomlKeyInfo(val key: String, val description: String, val insertValue: String)
