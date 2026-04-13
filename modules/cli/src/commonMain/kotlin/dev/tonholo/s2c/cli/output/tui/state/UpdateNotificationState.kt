package dev.tonholo.s2c.cli.output.tui.state

/**
 * Holds the data needed to render an update notification in the TUI.
 *
 * @property currentVersion the version currently running.
 * @property latestVersion the newest version available.
 * @property releaseUrl a URL pointing to the release page.
 * @property isWrapper true when the CLI was invoked through the wrapper script,
 *   which means the user can run `s2c --upgrade` to update.
 */
internal data class UpdateNotificationState(
    val currentVersion: String,
    val latestVersion: String,
    val releaseUrl: String,
    val isWrapper: Boolean,
)
