package dev.tonholo.s2c.command

import dev.tonholo.s2c.logger.verbose
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.refTo
import kotlinx.cinterop.toKStringFromUtf8
import platform.posix.fflush
import platform.posix.fgets
import platform.posix.fprintf
import platform.posix.pclose
import platform.posix.popen
import platform.posix.stderr

@OptIn(ExperimentalForeignApi::class)
actual fun executeCommand(command: Command): CommandResult {
    val commandToExecute = command.commandToExecute
    verbose("Command to execute: $commandToExecute")
    val fp = popen(commandToExecute, "r") ?: error("Failed to run command: $command")

    val output = buildString {
        val buffer = ByteArray(size = 4096)
        while (true) {
            val input = fgets(buffer.refTo(0), buffer.size, fp) ?: break
            val output = input.toKStringFromUtf8()
            append(output)
        }
    }

    val exitCode = pclose(fp)

    if (command.showStdout && exitCode == 0) {
        println(output)
    } else if (command.showStderr) {
        fprintf(stderr, output)
        fflush(stderr)
    }

    return CommandResult(
        exitCode = exitCode,
        output = CommandOutput(
            stdout = if (command.trim) output.trim() else output,
        ),
    )
}
