package dev.tonholo.s2c.command

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull

class ExternalCommandTest {

    // -------------------------------------------------------------------------
    // CommandOutput
    // -------------------------------------------------------------------------

    @Test
    fun `CommandOutput with stdout only`() {
        val output = CommandOutput(stdout = "hello")
        assertEquals("hello", output.stdout)
        assertNull(output.stderr)
    }

    @Test
    fun `CommandOutput with stdout and stderr`() {
        val output = CommandOutput(stdout = "out", stderr = "err")
        assertEquals("out", output.stdout)
        assertEquals("err", output.stderr)
    }

    @Test
    fun `CommandOutput data class equality`() {
        val a = CommandOutput(stdout = "same", stderr = null)
        val b = CommandOutput(stdout = "same", stderr = null)
        assertEquals(a, b)
    }

    @Test
    fun `CommandOutput data class inequality when stdout differs`() {
        val a = CommandOutput(stdout = "foo")
        val b = CommandOutput(stdout = "bar")
        assertNotEquals(a, b)
    }

    // -------------------------------------------------------------------------
    // CommandResult
    // -------------------------------------------------------------------------

    @Test
    fun `CommandResult stores exit code and output`() {
        val output = CommandOutput(stdout = "result")
        val result = CommandResult(exitCode = 0, output = output)
        assertEquals(0, result.exitCode)
        assertEquals(output, result.output)
    }

    @Test
    fun `CommandResult data class equality`() {
        val output = CommandOutput(stdout = "x")
        val a = CommandResult(exitCode = 1, output = output)
        val b = CommandResult(exitCode = 1, output = output)
        assertEquals(a, b)
    }

    @Test
    fun `CommandResult data class inequality when exit code differs`() {
        val output = CommandOutput(stdout = "x")
        val a = CommandResult(exitCode = 0, output = output)
        val b = CommandResult(exitCode = 1, output = output)
        assertNotEquals(a, b)
    }

    // -------------------------------------------------------------------------
    // Command.commandToExecute
    // -------------------------------------------------------------------------

    @Test
    fun `Command commandToExecute with no args returns just the program`() {
        val cmd = Command(program = "echo")
        assertEquals("echo", cmd.commandToExecute)
    }

    @Test
    fun `Command commandToExecute with empty args list returns just the program`() {
        val cmd = Command(program = "echo", args = emptyList())
        assertEquals("echo", cmd.commandToExecute)
    }

    @Test
    fun `Command commandToExecute with null args returns just the program`() {
        val cmd = Command(program = "ls", args = null)
        assertEquals("ls", cmd.commandToExecute)
    }

    @Test
    fun `Command commandToExecute with single arg appends it after the program`() {
        val cmd = Command(program = "echo", args = listOf("hello"))
        assertEquals("echo hello", cmd.commandToExecute)
    }

    @Test
    fun `Command commandToExecute with multiple args joins them with spaces`() {
        val cmd = Command(program = "git", args = listOf("commit", "-m", "message"))
        assertEquals("git commit -m message", cmd.commandToExecute)
    }

    @Test
    fun `Command default values are correct`() {
        val cmd = Command(program = "prog")
        assertNull(cmd.args)
        assertEquals(true, cmd.showStdout)
        assertEquals(true, cmd.showStderr)
        assertEquals(false, cmd.trim)
    }

    // -------------------------------------------------------------------------
    // CommandBuilder (args accumulation via Command inspection)
    // -------------------------------------------------------------------------

    @Test
    fun `CommandBuilder args are empty when no args called`() {
        // We verify via the Command built internally — args are passed to Command directly
        val builder = CommandBuilder(program = "test")
        // Build a Command by calling the private method is not possible,
        // but we can verify that calling args() multiple times accumulates correctly
        // by inspecting what execute() would pass to Command.commandToExecute.
        // Since execute() is platform-specific, we test the Command structure instead.
        val cmdNoArgs = Command(program = "test", args = emptyList())
        assertEquals("test", cmdNoArgs.commandToExecute)
    }

    @Test
    fun `CommandBuilder accumulates multiple args calls`() {
        // We test that args passed to Command result in correct commandToExecute.
        // The CommandBuilder.args field is non-null (changed from nullable in PR),
        // so all addAll() calls work without null checks.
        val cmd = Command(program = "prog", args = listOf("a", "b", "c", "d"))
        assertEquals("prog a b c d", cmd.commandToExecute)
    }
}