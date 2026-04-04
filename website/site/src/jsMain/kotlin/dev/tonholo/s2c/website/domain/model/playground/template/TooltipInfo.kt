package dev.tonholo.s2c.website.domain.model.playground.template

/**
 * Data shown in a hover tooltip.
 */
data class TooltipInfo(
    val title: String,
    val description: String,
    val context: String? = null,
    val keys: List<String>? = null,
)
