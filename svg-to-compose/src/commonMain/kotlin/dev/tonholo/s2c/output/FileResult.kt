package dev.tonholo.s2c.output

import dev.tonholo.s2c.error.ErrorCode

/**
 * The outcome of converting a single file.
 */
sealed interface FileResult {
    /**
     * The file was converted successfully.
     */
    data object Success : FileResult

    /**
     * The file conversion failed.
     *
     * @property errorCode the [ErrorCode] identifying the failure category.
     * @property message a human-readable description of what went wrong.
     * @property stackTrace the exception stack trace, if available.
     */
    data class Failed(val errorCode: ErrorCode, val message: String, val stackTrace: String? = null) : FileResult
}
