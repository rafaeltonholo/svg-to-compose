package dev.tonholo.s2c.cli.update

import kotlinx.serialization.json.Json
import okio.FileSystem
import okio.Path

/**
 * Reads and writes the version-check cache file at
 * `[cacheDir]/[CACHE_FILE_NAME]`.
 *
 * The cache has a 24-hour TTL. If the file is missing,
 * corrupted, or older than 24 hours, it is treated as expired.
 */
class UpdateCache(
    private val fileSystem: FileSystem,
    private val cacheDir: Path,
) {
    private val cachePath: Path get() = cacheDir / CACHE_FILE_NAME

    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    /**
     * Reads the cached entry and returns it only when it was checked
     * less than [TTL_MILLIS] milliseconds ago. Returns null when the
     * file is missing, corrupted, or stale.
     *
     * This avoids the TOCTOU issue of calling `isFresh` then `read`
     * separately (two file reads for the same check).
     */
    fun readIfFresh(nowEpochMillis: Long): UpdateCacheEntry? {
        val entry = read() ?: return null
        val elapsed = nowEpochMillis - entry.checkedAtEpochMillis
        return if (elapsed < TTL_MILLIS) entry else null
    }

    /**
     * Reads the cached entry from disk.
     *
     * @return the [UpdateCacheEntry], or null if the file
     *   does not exist or cannot be parsed.
     */
    fun read(): UpdateCacheEntry? = try {
        if (!fileSystem.exists(cachePath)) {
            null
        } else {
            val content = fileSystem.read(cachePath) { readUtf8() }
            json.decodeFromString<UpdateCacheEntry>(content)
        }
    } catch (@Suppress("TooGenericExceptionCaught") _: Exception) {
        null
    }

    /**
     * Writes the given [entry] to the cache file, creating
     * the cache directory if necessary.
     *
     * Cache write failure is non-fatal; the next invocation will retry.
     */
    fun write(entry: UpdateCacheEntry) {
        try {
            fileSystem.createDirectories(cachePath.parent ?: cacheDir)
            fileSystem.write(cachePath) {
                writeUtf8(json.encodeToString(entry))
            }
        } catch (@Suppress("TooGenericExceptionCaught") _: Exception) {
            // Cache write failure is non-fatal; next invocation will retry.
        }
    }

    companion object {
        const val CACHE_FILE_NAME = "update-check.json"
        private const val HOURS_PER_DAY = 24
        private const val MINUTES_PER_HOUR = 60
        private const val SECONDS_PER_MINUTE = 60
        private const val MILLIS_PER_SECOND = 1_000L
        internal const val TTL_MILLIS: Long =
            HOURS_PER_DAY * MINUTES_PER_HOUR * SECONDS_PER_MINUTE * MILLIS_PER_SECOND
    }
}
