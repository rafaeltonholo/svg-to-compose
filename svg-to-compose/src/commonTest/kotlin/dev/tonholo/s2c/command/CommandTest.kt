package dev.tonholo.s2c.command

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CommandTest {

    // -------------------------------------------------------------------------
    // Command.commandToExecute
    // -------------------------------------------------------------------------

    @Test
    fun `commandToExecute returns just the program name when args is null`() {
        val command = Command(program = "svgc", args = null)
        assertEquals("svgc", command.commandToExecute)
    }

    @Test
    fun `commandToExecute returns just the program name when args is empty`() {
        val command = Command(program = "svgc", args = emptyList())
        assertEquals("svgc", command.commandToExecute)
    }

    @Test
    fun `commandToExecute includes space-separated args after the program`() {
        val command = Command(program = "svgc", args = listOf("--input", "file.svg", "--output", "out/"))
        assertEquals("svgc --input file.svg --output out/", command.commandToExecute)
    }

    @Test
    fun `commandToExecute handles a single arg`() {
        val command = Command(program = "echo", args = listOf("hello"))
        assertEquals("echo hello", command.commandToExecute)
    }

    @Test
    fun `commandToExecute handles args with spaces in individual arg values`() {
        val command = Command(program = "git", args = listOf("commit", "-m", "fix: trailing comma"))
        assertEquals("git commit -m fix: trailing comma", command.commandToExecute)
    }

    // -------------------------------------------------------------------------
    // Command default property values
    // -------------------------------------------------------------------------

    @Test
    fun `Command has showStdout true by default`() {
        val command = Command(program = "prog")
        assertTrue(command.showStdout)
    }

    @Test
    fun `Command has showStderr true by default`() {
        val command = Command(program = "prog")
        assertTrue(command.showStderr)
    }

    @Test
    fun `Command has trim false by default`() {
        val command = Command(program = "prog")
        assertEquals(false, command.trim)
    }

    @Test
    fun `Command args is null by default`() {
        val command = Command(program = "prog")
        assertNull(command.args)
    }

    // -------------------------------------------------------------------------
    // CommandOutput data class
    // -------------------------------------------------------------------------

    @Test
    fun `CommandOutput stores stdout`() {
        val output = CommandOutput(stdout = "hello world")
        assertEquals("hello world", output.stdout)
    }

    @Test
    fun `CommandOutput has null stderr by default`() {
        val output = CommandOutput(stdout = "output")
        assertNull(output.stderr)
    }

    @Test
    fun `CommandOutput stores both stdout and stderr`() {
        val output = CommandOutput(stdout = "out", stderr = "err")
        assertEquals("out", output.stdout)
        assertEquals("err", output.stderr)
    }

    @Test
    fun `CommandOutput with null stdout`() {
        val output = CommandOutput(stdout = null)
        assertNull(output.stdout)
    }

    @Test
    fun `CommandOutput equality holds for equal instances`() {
        val a = CommandOutput(stdout = "x", stderr = "y")
        val b = CommandOutput(stdout = "x", stderr = "y")
        assertEquals(a, b)
    }

    // -------------------------------------------------------------------------
    // CommandResult data class
    // -------------------------------------------------------------------------

    @Test
    fun `CommandResult stores exitCode and output`() {
        val output = CommandOutput(stdout = "done")
        val result = CommandResult(exitCode = 0, output = output)
        assertEquals(0, result.exitCode)
        assertEquals(output, result.output)
    }

    @Test
    fun `CommandResult non-zero exitCode indicates failure`() {
        val output = CommandOutput(stdout = null, stderr = "error occurred")
        val result = CommandResult(exitCode = 1, output = output)
        assertEquals(1, result.exitCode)
        assertEquals("error occurred", result.output.stderr)
    }

    @Test
    fun `CommandResult equality holds for equal instances`() {
        val output = CommandOutput(stdout = "ok")
        val a = CommandResult(exitCode = 0, output = output)
        val b = CommandResult(exitCode = 0, output = output)
        assertEquals(a, b)
    }

    // -------------------------------------------------------------------------
    // Command copy / data class behaviour
    // -------------------------------------------------------------------------

    @Test
    fun `Command copy produces independent instance with changed fields`() {
        val original = Command(program = "svgc", args = listOf("--help"))
        val copy = original.copy(program = "other")
        assertEquals("other", copy.program)
        assertEquals(listOf("--help"), copy.args)
    }
}