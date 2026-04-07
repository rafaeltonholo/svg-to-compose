package dev.tonholo.s2c.cli.update

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

// TODO(#304): Wire VersionChecker into CLI startup via CliGraph and emit UpdateAvailable event after RunCompleted

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
 * @param currentVersion the version string of the running CLI (e.g. "2.0.0" or "v2.0.0").
 * @param cache the [UpdateCache] used for reading and writing cached results.
 * @param fetcher the [GitHubReleaseFetcher] abstraction for remote lookups.
 * @param nowEpochMillis provider for the current time in epoch milliseconds.
 * @param ioDispatcher the [CoroutineDispatcher] used for IO-bound work (file and network).
 */
class VersionChecker(
    private val currentVersion: String,
    private val cache: UpdateCache,
    private val fetcher: GitHubReleaseFetcher,
    private val nowEpochMillis: () -> Long,
    private val ioDispatcher: CoroutineDispatcher,
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
    suspend fun check(): UpdateCheckResult = withContext(ioDispatcher) {
        val current = SemVer.parse(currentVersion)
            ?: return@withContext UpdateCheckResult.NoUpdate

        val now = nowEpochMillis()
        val entry = resolveLatest(now)
            ?: return@withContext UpdateCheckResult.NoUpdate

        val latest = SemVer.parse(entry.latestVersion)
            ?: return@withContext UpdateCheckResult.NoUpdate

        if (latest > current) {
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
     * from the remote and writes the result to cache.
     */
    private suspend fun resolveLatest(now: Long): UpdateCacheEntry? {
        cache.readIfFresh(nowEpochMillis = now)?.let { return it }

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
