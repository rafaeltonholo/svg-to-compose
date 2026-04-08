package dev.tonholo.s2c.cli.update

/**
 * Result of a version update check.
 */
sealed interface UpdateCheckResult {
    /**
     * No update is available, or the check could not be performed.
     */
    data object NoUpdate : UpdateCheckResult

    /**
     * A newer version is available.
     *
     * @property current the currently running version.
     * @property latest the newest available version.
     * @property releaseUrl the URL to the release page.
     */
    data class UpdateAvailable(val current: SemVer, val latest: SemVer, val releaseUrl: String) : UpdateCheckResult
}
