package dev.tonholo.s2c.cli.output.tui.state

private const val DEFAULT_MAX_ENTRIES = 10

/**
 * State for the rolling log section showing recently completed files.
 *
 * @property entries the list of recent file entries, newest last.
 * @property maxEntries maximum number of entries to retain (FIFO eviction).
 */
internal data class RecentFilesState(
    val entries: List<RecentFileEntry> = emptyList(),
    val maxEntries: Int = DEFAULT_MAX_ENTRIES,
) {
    /**
     * Adds an entry, evicting the oldest if at capacity.
     */
    fun addEntry(entry: RecentFileEntry): RecentFilesState {
        val updated = entries + entry
        val trimmed = if (updated.size > maxEntries) {
            updated.drop(n = updated.size - maxEntries)
        } else {
            updated
        }
        return copy(entries = trimmed)
    }
}
