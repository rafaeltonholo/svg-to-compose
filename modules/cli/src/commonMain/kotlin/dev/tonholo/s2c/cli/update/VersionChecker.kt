package dev.tonholo.s2c.cli.update

/**
 * Orchestrates the version update check by reading from cache,
 * fetching from the remote when the cache is stale, and comparing
 * versions.
 *
 * @param currentVersion the version string of the running CLI (e.g. "2.0.0" or "v2.0.0").
 * @param cache the [UpdateCache] used for reading and writing cached results.
 * @param fetcher the [GitHubReleaseFetcher] abstraction for remote lookups.
 * @param nowEpochMillis provider for the current time in epoch milliseconds.
 */
class VersionChecker(
    private val currentVersion: String,
    private val cache: UpdateCache,
    private val fetcher: GitHubReleaseFetcher,
    private val nowEpochMillis: () -> Long,
) {
    /**
     * Checks whether a newer version of the CLI is available.
     *
     * 1. Parses the [currentVersion]. If it is invalid, returns [UpdateCheckResult.NoUpdate].
     * 2. If the cache is fresh, uses the cached latest version.
     * 3. Otherwise, calls the [fetcher]. On failure, returns [UpdateCheckResult.NoUpdate].
     * 4. Compares the current version with the latest. Returns
     *    [UpdateCheckResult.UpdateAvailable] only when the latest is strictly newer.
     */
    fun check(): UpdateCheckResult {
        val current = SemVer.parse(currentVersion)
            ?: return UpdateCheckResult.NoUpdate

        val now = nowEpochMillis()
        val entry = resolveLatest(now)
            ?: return UpdateCheckResult.NoUpdate

        val latest = SemVer.parse(entry.latestVersion)
            ?: return UpdateCheckResult.NoUpdate

        if (latest <= current) {
            return UpdateCheckResult.NoUpdate
        }

        return UpdateCheckResult.UpdateAvailable(
            current = current,
            latest = latest,
            releaseUrl = entry.releaseUrl,
        )
    }

    /**
     * Returns a cache entry with the latest version info.
     * Uses the cache if it is still fresh, otherwise fetches
     * from the remote and writes the result to cache.
     */
    private fun resolveLatest(now: Long): UpdateCacheEntry? {
        if (cache.isFresh(nowEpochMillis = now)) {
            return cache.read()
        }

        val release = fetcher.fetch() ?: return null
        val version = SemVer.parse(release.tagName) ?: return null

        val entry = UpdateCacheEntry(
            latestVersion = version.toString(),
            releaseUrl = release.releaseUrl,
            checkedAtEpochMillis = now,
        )
        cache.write(entry)
        return entry
    }
}
