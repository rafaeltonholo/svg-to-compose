package dev.tonholo.s2c.output

import dev.tonholo.s2c.error.ErrorCode
import kotlin.time.Duration

/**
 * Aggregate statistics for a completed conversion run.
 *
 * @property totalFiles the total number of files that were processed.
 * @property succeeded the number of files that converted successfully.
 * @property failed the number of files that failed to convert.
 * @property totalDuration the wall-clock duration of the entire run.
 * @property errorCounts per-[ErrorCode] counts for failures.
 */
data class RunStats(
    val totalFiles: Int,
    val succeeded: Int,
    val failed: Int,
    val totalDuration: Duration,
    val errorCounts: Map<ErrorCode, Int>,
) {
    init {
        require(totalFiles >= 0) { "totalFiles must be non-negative, was $totalFiles" }
        require(succeeded >= 0) { "succeeded must be non-negative, was $succeeded" }
        require(failed >= 0) { "failed must be non-negative, was $failed" }
        require(succeeded + failed == totalFiles) {
            "succeeded ($succeeded) + failed ($failed) must equal totalFiles ($totalFiles)"
        }
    }
}
