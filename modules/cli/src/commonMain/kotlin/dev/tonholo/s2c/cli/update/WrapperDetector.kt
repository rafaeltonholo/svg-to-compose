package dev.tonholo.s2c.cli.update

/**
 * Detects whether the CLI is running through the s2c wrapper script.
 *
 * The wrapper script sets the `S2C_WRAPPER` environment variable to "true"
 * before invoking the binary. This detector reads that variable to determine
 * the invocation context, which affects how update notifications are displayed
 * (e.g., suggesting `s2c --upgrade` vs. a download link).
 *
 * @param envReader a function that reads the `S2C_WRAPPER` environment variable.
 *   Injected for testability and KMP compatibility.
 */
class WrapperDetector(private val envReader: () -> String?) {
    /**
     * Returns `true` if the CLI was invoked through the s2c wrapper script.
     */
    fun isRunningFromWrapper(): Boolean {
        val value = envReader() ?: return false
        return value.trim().equals(other = "true", ignoreCase = true)
    }

    companion object {
        /**
         * The name of the environment variable set by the wrapper script.
         */
        const val ENV_VAR_NAME = "S2C_WRAPPER"
    }
}
