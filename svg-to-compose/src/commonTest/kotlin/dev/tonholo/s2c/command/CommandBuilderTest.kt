package dev.tonholo.s2c.command

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CommandBuilderTest {

    // -------------------------------------------------------------------------
    // Command data class
    // -------------------------------------------------------------------------

    @Test
    fun `commandToExecute with args joins program and args with spaces`() {
        val command = Command(
            program = "git",
            args = listOf("commit", "-m", "message"),
        )
        assertEquals("git commit -m message", command.commandToExecute)
    }

    @Test
    fun `commandToExecute with empty args omits args section`() {
        val command = Command(
            program = "ls",
            args = emptyList(),
        )
        assertEquals("ls", command.commandToExecute)
    }

    @Test
    fun `commandToExecute with null args produces only program name`() {
        val command = Command(
            program = "pwd",
            args = null,
        )
        assertEquals("pwd", command.commandToExecute)
    }

    @Test
    fun `commandToExecute with single arg produces correct string`() {
        val command = Command(
            program = "echo",
            args = listOf("hello"),
        )
        assertEquals("echo hello", command.commandToExecute)
    }

    @Test
    fun `Command default values`() {
        val command = Command(program = "test")
        assertNull(command.args)
        assertTrue(command.showStdout)
        assertTrue(command.showStderr)
        assertEquals(false, command.trim)
    }

    @Test
    fun `Command equality based on all fields`() {
        val c1 = Command(program = "echo", args = listOf("a"))
        val c2 = Command(program = "echo", args = listOf("a"))
        val c3 = Command(program = "echo", args = listOf("b"))
        assertEquals(c1, c2)
        assertTrue(c1 != c3)
    }

    // -------------------------------------------------------------------------
    // CommandOutput data class
    // -------------------------------------------------------------------------

    @Test
    fun `CommandOutput defaults stderr to null`() {
        val output = CommandOutput(stdout = "ok")
        assertEquals("ok", output.stdout)
        assertNull(output.stderr)
    }

    @Test
    fun `CommandOutput with explicit null stdout`() {
        val output = CommandOutput(stdout = null, stderr = "error msg")
        assertNull(output.stdout)
        assertEquals("error msg", output.stderr)
    }

    @Test
    fun `CommandOutput equality`() {
        val o1 = CommandOutput(stdout = "out", stderr = "err")
        val o2 = CommandOutput(stdout = "out", stderr = "err")
        assertEquals(o1, o2)
    }

    // -------------------------------------------------------------------------
    // CommandResult data class
    // -------------------------------------------------------------------------

    @Test
    fun `CommandResult holds exitCode and output`() {
        val result = CommandResult(
            exitCode = 0,
            output = CommandOutput(stdout = "success"),
        )
        assertEquals(0, result.exitCode)
        assertEquals("success", result.output.stdout)
    }

    @Test
    fun `CommandResult with non-zero exit code`() {
        val result = CommandResult(
            exitCode = 1,
            output = CommandOutput(stdout = null, stderr = "not found"),
        )
        assertEquals(1, result.exitCode)
        assertEquals("not found", result.output.stderr)
    }

    @Test
    fun `CommandResult equality`() {
        val r1 = CommandResult(exitCode = 0, output = CommandOutput(stdout = "ok"))
        val r2 = CommandResult(exitCode = 0, output = CommandOutput(stdout = "ok"))
        assertEquals(r1, r2)
    }
}