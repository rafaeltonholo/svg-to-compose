package dev.tonholo.s2c.cli.update

/**
 * Orchestrates the version update check by reading from cache,
 * fetching from the remote when the cache is stale, and comparing
 * versions.
 *
 * Pre-release suffixes (e.g. `-SNAPSHOT`, `-rc.1`) on the current
 * version are stripped so that dev builds still participate in
 * update checks. Pre-release tags fetched from GitHub are ignored
 * to avoid prompting users to upgrade to unstable releases.
 *
 * IO-bound work (file access, network) is handled by the injected
 * [cache] and [fetcher] dependencies, which dispatch onto the
 * appropriate coroutine dispatcher internally.
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
    suspend fun check(): UpdateCheckResult {
        val current = SemVer.parse(currentVersion)
            ?: return UpdateCheckResult.NoUpdate

        val now = nowEpochMillis()
        val entry = resolveLatest(now)
            ?: return UpdateCheckResult.NoUpdate

        val latest = SemVer.parse(entry.latestVersion)
            ?: return UpdateCheckResult.NoUpdate

        return if (latest > current) {
            UpdateCheckResult.UpdateAvailable(
                current = current,
                latest = latest,
                releaseUrl = entry.releaseUrl,
            )
        } else {
            UpdateCheckResult.NoUpdate
        }
    }

    /**
     * Returns a cache entry with the latest version info.
     * Uses the cache if it is still fresh, otherwise fetches
     * from the remote, and writes the result to cache.
     *
     * If the cached entry has an unparseable [UpdateCacheEntry.latestVersion],
     * it is treated as stale and a remote fetch is attempted.
     */
    private suspend fun resolveLatest(now: Long): UpdateCacheEntry? {
        val cached = cache.readIfFresh(nowEpochMillis = now)
        if (cached != null && SemVer.parse(cached.latestVersion) != null) return cached

        val release = fetcher.fetch() ?: return null

        // Reject pre-release tags from GitHub so users are not
        // prompted to upgrade to unstable versions.
        val version = release.tagName
            .takeUnless { SemVer.isPreRelease(it) }
            ?.let { SemVer.parse(it) }
            ?: return null

        return UpdateCacheEntry(
            latestVersion = version.toString(),
            releaseUrl = release.releaseUrl,
            checkedAtEpochMillis = now,
        ).also { cache.write(it) }
    }
}
