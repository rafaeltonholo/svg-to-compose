package dev.tonholo.s2c.cli.update

/**
 * Abstraction for fetching the latest release information from GitHub.
 *
 * The actual HTTP implementation is wired at the call site.
 * Returning null signals that the fetch failed or was unavailable.
 */
fun interface GitHubReleaseFetcher {
    /**
     * Fetches the latest release information.
     *
     * @return the [LatestReleaseInfo], or null on failure.
     */
    fun fetch(): LatestReleaseInfo?
}

/**
 * Holds the tag name and URL of the latest GitHub release.
 */
data class LatestReleaseInfo(
    val tagName: String,
    val releaseUrl: String,
)
