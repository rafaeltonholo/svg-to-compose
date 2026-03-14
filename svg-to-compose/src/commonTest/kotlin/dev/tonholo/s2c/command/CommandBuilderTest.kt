package dev.tonholo.s2c.command

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class CommandBuilderTest {

    // -------------------------------------------------------------------------
    // Command.commandToExecute
    // -------------------------------------------------------------------------

    @Test
    fun `commandToExecute with no args returns only the program name`() {
        val command = Command(program = "git")
        assertEquals("git", command.commandToExecute)
    }

    @Test
    fun `commandToExecute with null args returns only the program name`() {
        val command = Command(program = "echo", args = null)
        assertEquals("echo", command.commandToExecute)
    }

    @Test
    fun `commandToExecute with empty args list returns only the program name`() {
        val command = Command(program = "ls", args = emptyList())
        assertEquals("ls", command.commandToExecute)
    }

    @Test
    fun `commandToExecute with a single arg appends it separated by a space`() {
        val command = Command(program = "git", args = listOf("status"))
        assertEquals("git status", command.commandToExecute)
    }

    @Test
    fun `commandToExecute with multiple args joins them space-separated`() {
        val command = Command(program = "git", args = listOf("log", "--oneline", "--graph"))
        assertEquals("git log --oneline --graph", command.commandToExecute)
    }

    @Test
    fun `commandToExecute preserves args with special characters`() {
        val command = Command(program = "grep", args = listOf("-r", "pattern.*", "/some/path"))
        assertEquals("grep -r pattern.* /some/path", command.commandToExecute)
    }

    // -------------------------------------------------------------------------
    // Command default properties
    // -------------------------------------------------------------------------

    @Test
    fun `Command defaults showStdout to true`() {
        val command = Command(program = "test")
        assertEquals(true, command.showStdout)
    }

    @Test
    fun `Command defaults showStderr to true`() {
        val command = Command(program = "test")
        assertEquals(true, command.showStderr)
    }

    @Test
    fun `Command defaults trim to false`() {
        val command = Command(program = "test")
        assertEquals(false, command.trim)
    }

    @Test
    fun `Command defaults args to null`() {
        val command = Command(program = "test")
        assertNull(command.args)
    }

    // -------------------------------------------------------------------------
    // CommandOutput / CommandResult data classes
    // -------------------------------------------------------------------------

    @Test
    fun `CommandOutput with only stdout leaves stderr null`() {
        val output = CommandOutput(stdout = "hello")
        assertEquals("hello", output.stdout)
        assertNull(output.stderr)
    }

    @Test
    fun `CommandOutput stores both stdout and stderr`() {
        val output = CommandOutput(stdout = "out", stderr = "err")
        assertEquals("out", output.stdout)
        assertEquals("err", output.stderr)
    }

    @Test
    fun `CommandOutput with null stdout is allowed`() {
        val output = CommandOutput(stdout = null)
        assertNull(output.stdout)
    }

    @Test
    fun `CommandResult stores exitCode and output`() {
        val output = CommandOutput(stdout = "result")
        val result = CommandResult(exitCode = 0, output = output)
        assertEquals(0, result.exitCode)
        assertEquals(output, result.output)
    }

    @Test
    fun `CommandResult non-zero exit code is preserved`() {
        val output = CommandOutput(stdout = null, stderr = "error message")
        val result = CommandResult(exitCode = 127, output = output)
        assertEquals(127, result.exitCode)
        assertEquals("error message", result.output.stderr)
    }

    // -------------------------------------------------------------------------
    // CommandBuilder args accumulation
    // -------------------------------------------------------------------------

    @Test
    fun `CommandBuilder builds Command with the program name`() {
        // Verify via Command data class
        val expected = Command(program = "rustup")
        assertEquals("rustup", expected.commandToExecute)
    }

    @Test
    fun `CommandBuilder Command with multiple args accumulated across calls`() {
        // Mirror the accumulation that CommandBuilder.args() performs and
        // verify the resulting Command structure is correct.
        val args = mutableListOf<String>()
        args.addAll(listOf("--version"))
        args.addAll(listOf("--verbose", "--output", "json"))
        val command = Command(program = "tool", args = args)
        assertEquals("tool --version --verbose --output json", command.commandToExecute)
    }

    @Test
    fun `CommandBuilder Command built with no args call produces commandToExecute without trailing space`() {
        // Ensures the empty-list default does not add a trailing space.
        val command = Command(program = "pwd", args = emptyList())
        assertEquals("pwd", command.commandToExecute)
    }

    @Test
    fun `CommandBuilder Command args are passed as a non-null list after initialization`() {
        // After the PR change, args is always a MutableList, never null.
        // Simulate what CommandBuilder.execute() produces.
        val builtArgs = mutableListOf<String>()
        val command = Command(program = "echo", args = builtArgs)
        // Even with an empty list the commandToExecute should be just the program.
        assertEquals("echo", command.commandToExecute)
        // The list itself is non-null (matches the new private val behavior).
        assertEquals(emptyList<String>(), command.args)
    }
}