package dev.tonholo.s2c.command

import kotlin.test.Test
import kotlin.test.assertEquals

class CommandTest {
    @Test
    fun `commandToExecute with null args returns just the program name`() {
        val command = Command(program = "echo", args = null)
        assertEquals("echo", command.commandToExecute)
    }

    @Test
    fun `commandToExecute with empty args returns just the program name`() {
        val command = Command(program = "echo", args = emptyList())
        assertEquals("echo", command.commandToExecute)
    }

    @Test
    fun `commandToExecute with single arg returns program and arg`() {
        val command = Command(program = "echo", args = listOf("hello"))
        assertEquals("echo hello", command.commandToExecute)
    }

    @Test
    fun `commandToExecute with multiple args joins them with spaces`() {
        val command = Command(program = "git", args = listOf("commit", "-m", "fix bug"))
        assertEquals("git commit -m fix bug", command.commandToExecute)
    }

    @Test
    fun `commandToExecute with path-like program includes all parts`() {
        val command = Command(program = "/usr/bin/ls", args = listOf("-la", "/tmp"))
        assertEquals("/usr/bin/ls -la /tmp", command.commandToExecute)
    }

    @Test
    fun `Command data class equals based on values`() {
        val cmd1 = Command(program = "echo", args = listOf("hello"), showStdout = true, showStderr = false)
        val cmd2 = Command(program = "echo", args = listOf("hello"), showStdout = true, showStderr = false)
        assertEquals(cmd1, cmd2)
    }

    @Test
    fun `Command defaults showStdout and showStderr to true and trim to false`() {
        val command = Command(program = "test")
        assertEquals(true, command.showStdout)
        assertEquals(true, command.showStderr)
        assertEquals(false, command.trim)
    }

    @Test
    fun `CommandOutput data class holds stdout and stderr`() {
        val output = CommandOutput(stdout = "output text", stderr = "error text")
        assertEquals("output text", output.stdout)
        assertEquals("error text", output.stderr)
    }

    @Test
    fun `CommandOutput stderr defaults to null`() {
        val output = CommandOutput(stdout = "out")
        assertEquals(null, output.stderr)
    }

    @Test
    fun `CommandResult data class holds exitCode and output`() {
        val output = CommandOutput(stdout = "result")
        val result = CommandResult(exitCode = 0, output = output)
        assertEquals(0, result.exitCode)
        assertEquals(output, result.output)
    }

    @Test
    fun `CommandResult with non-zero exit code preserved`() {
        val output = CommandOutput(stdout = null, stderr = "command not found")
        val result = CommandResult(exitCode = 127, output = output)
        assertEquals(127, result.exitCode)
    }
}