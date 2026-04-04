package dev.tonholo.s2c.website.state.playground

import kotlinx.browser.window
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * Persists playground configuration (options and template TOML) to
 * localStorage so that settings survive page reloads.
 *
 * The stored payload is versioned so future schema changes can be
 * handled gracefully — unknown or corrupt entries are silently
 * discarded.
 */
internal object PlaygroundPersistence {
    private const val STORAGE_KEY = "s2c-playground-config"
    private const val CURRENT_VERSION = 2

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    @Serializable
    private data class StoragePayload(
        val version: Int,
        val options: PlaygroundOptions,
        val templateToml: String? = null,
    )

    /**
     * Writes the current playground configuration to localStorage.
     */
    fun save(options: PlaygroundOptions, templateToml: String?) {
        val payload = StoragePayload(
            version = CURRENT_VERSION,
            options = options,
            templateToml = templateToml,
        )
        val encoded = runCatching { json.encodeToString(payload) }
            .getOrElse { e ->
                console.warn("Failed to serialize playground config", e)
                return
            }
        runCatching { window.localStorage.setItem(STORAGE_KEY, encoded) }
            .onFailure { e -> console.warn("Failed to persist playground config", e) }
    }

    /**
     * Loads the persisted playground configuration from localStorage.
     *
     * Returns `null` when the entry is missing, corrupt, or belongs to
     * an unknown schema version.
     */
    fun load(): Pair<PlaygroundOptions, String?>? {
        val raw = window.localStorage.getItem(STORAGE_KEY) ?: return null
        val payload = runCatching { json.decodeFromString<StoragePayload>(raw) }
            .onFailure { e -> console.warn("Discarding corrupt playground config", e) }
            .getOrNull() ?: return null
        if (payload.version != CURRENT_VERSION) return null
        return payload.options to payload.templateToml
    }
}
