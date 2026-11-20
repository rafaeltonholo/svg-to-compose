package dev.tonholo.s2c.gradle.tasks

import dev.tonholo.s2c.error.ExitProgramException
import okio.Path
import java.io.File
import java.nio.file.Files
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertFailsWith

class WorkerResultsTest {
    private lateinit var resultsDir: File

    @BeforeTest
    fun setUp() {
        resultsDir = Files.createTempDirectory("worker-results-test").toFile()
    }

    @AfterTest
    fun tearDown() {
        resultsDir.deleteRecursively()
    }

    private fun writeErrorResult(
        fileName: String,
        origin: String,
        message: String,
    ) {
        resultsDir.resolve(fileName).writeText(
            "origin=$origin\noutput=\nstatus=Error\nmessage=$message\n",
        )
    }

    private fun process(expectedResultCount: Int): ExitProgramException =
        assertFailsWith<ExitProgramException> {
            processWorkerResults(
                configurationName = "icons",
                expectedResultCount = expectedResultCount,
                resultsDir = resultsDir,
                errors = mutableMapOf<Path, Throwable>(),
                outputFiles = mutableMapOf<Path, Path>(),
            )
        }

    @Test
    fun `given fewer results than submitted files - when results are processed - then failure reports the counts`() {
        // Arrange
        writeErrorResult(fileName = "result-0.properties", origin = "/icons/a.svg", message = "unsupported element")

        // Act
        val failure = process(expectedResultCount = 2)

        // Assert
        assertContains(failure.message.orEmpty(), "Expected 2 worker result file(s) but found 1")
    }

    @Test
    fun `given fewer results than submitted files - when results are processed - then failure keeps the per-file errors`() {
        // Arrange
        writeErrorResult(fileName = "result-0.properties", origin = "/icons/a.svg", message = "unsupported element")

        // Act
        val failure = process(expectedResultCount = 2)

        // Assert
        assertContains(failure.message.orEmpty(), "/icons/a.svg: unsupported element")
    }
}
