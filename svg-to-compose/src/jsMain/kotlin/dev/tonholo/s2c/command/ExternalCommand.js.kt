package dev.tonholo.s2c.command

import dev.tonholo.s2c.logger.Logger

/**
 * JS stub for external command execution.
 * Always returns a failure result because shell commands are not available in the browser.
 */
context(logger: Logger)
actual fun executeCommand(command: Command): CommandResult = CommandResult(
    exitCode = 1,
    output = CommandOutput(
        stdout = null,
        stderr = "External commands are not supported on JS platform",
    ),
)
