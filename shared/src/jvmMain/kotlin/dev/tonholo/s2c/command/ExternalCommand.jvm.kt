package dev.tonholo.s2c.command

import java.io.BufferedReader
import java.io.InputStreamReader

actual fun executeCommand(command: Command): CommandResult {
    val processBuilder = ProcessBuilder().apply {
        command("bash", "-c", command.commandToExecute)
    }

    val process = processBuilder.start()
    val bufferedReader = BufferedReader(InputStreamReader(process.inputStream))
    val output = buildString {
        bufferedReader.useLines { lines ->
            lines.forEach { appendLine(it) }
        }
    }

    val errorOutput = buildString {
        BufferedReader(InputStreamReader(process.errorStream)).useLines { lines ->
            lines.forEach { appendLine(it) }
        }
    }

    val exitCode = process.waitFor()
    if (command.showStdout && exitCode == 0) {
        println(output)
    }

    return CommandResult(
        exitCode = exitCode,
        output = CommandOutput(
            stdout = if (command.trim) output.trim() else output,
            stderr = if (command.trim) errorOutput.trim() else errorOutput,
        )
    )
}
