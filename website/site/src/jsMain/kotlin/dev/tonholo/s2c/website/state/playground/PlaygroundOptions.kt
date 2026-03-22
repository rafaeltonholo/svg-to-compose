package dev.tonholo.s2c.website.state.playground

import kotlinx.serialization.Serializable

/**
 * Configuration toggles and text fields exposed in the playground options panel.
 */
@Serializable
data class PlaygroundOptions(
    val optimize: Boolean = false,
    val minified: Boolean = false,
    val kmpPreview: Boolean = false,
    val noPreview: Boolean = true,
    val makeInternal: Boolean = false,
    val pkg: String = "com.example.icons",
    val theme: String = "com.example.theme.AppTheme",
    val receiverType: String = "",
)
