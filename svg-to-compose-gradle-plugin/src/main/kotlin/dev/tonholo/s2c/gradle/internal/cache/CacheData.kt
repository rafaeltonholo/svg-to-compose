package dev.tonholo.s2c.gradle.internal.cache

import java.io.Serializable

/**
 * Represents data to be cached.
 *
 * This data class holds information about files and their associated
 * data, as well as configuration specific to file extensions.
 *
 * @property files A map where keys are file paths and values are the
 *  corresponding file data (e.g., content).
 * @property extensionConfiguration A map where keys are file extensions
 *  and values are configuration strings specific to that extension.
 *  This could be used for storing formatting preferences, linting rules,
 *  etc.
 */
data class CacheData(
    val files: Map<String, String>,
    val extensionConfiguration: Map<String, String>,
) : Serializable {
    companion object {
        private const val serialVersionUID = 1L
    }
}
