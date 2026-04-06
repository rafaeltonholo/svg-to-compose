package dev.tonholo.s2c.cli.update

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a cached version-check result persisted to disk.
 */
@Serializable
data class UpdateCacheEntry(
    @SerialName("latest_version")
    val latestVersion: String,
    @SerialName("release_url")
    val releaseUrl: String,
    @SerialName("checked_at_epoch_millis")
    val checkedAtEpochMillis: Long,
)
