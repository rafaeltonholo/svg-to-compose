package dev.tonholo.s2c.cli.output.tui.state

import dev.tonholo.s2c.output.FileResult
import kotlin.time.Duration

/**
 * A single entry in the rolling log of recently completed files.
 *
 * @property fileName the name of the processed file.
 * @property result the conversion outcome (success or failure).
 * @property duration how long processing took.
 */
internal data class RecentFileEntry(val fileName: String, val result: FileResult, val duration: Duration)
