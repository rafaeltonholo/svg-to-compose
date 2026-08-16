package dev.tonholo.s2c.gradle

import dev.tonholo.s2c.gradle.common.GradleFunctionalTest
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

/**
 * Functional tests covering how the parse task reacts when the worker
 * results it collects do not match the work it submitted.
 */
class WorkerResultsFunctionalTest : GradleFunctionalTest() {

    override val tempDirPrefix: String = "s2c-worker-results-test"

    companion object {
        private const val TASK_NAME = "parseSvgToComposeIcon"
        private const val CONFIGURATION_NAME = "icons"

        // language=svg
        private val SIMPLE_SVG = """
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
              <circle cx="12" cy="12" r="10" fill="black"/>
            </svg>
        """.trimIndent()
    }

    // language=kotlin
    private fun buildGradleContent(pkg: String) = """
        plugins {
            kotlin("multiplatform")
            id("dev.tonholo.s2c")
        }
        repositories {
            mavenCentral()
        }
        kotlin {
            jvm()
        }
        svgToCompose {
            processor {
                val $CONFIGURATION_NAME by creating {
                    from(layout.projectDirectory.dir("icons"))
                    destinationPackage("$pkg")
                    icons { noPreview() }
                }
            }
        }
    """.trimIndent()

    /**
     * Adds a build script snippet that writes an unexpected result file into the
     * worker results directory before the task action runs, simulating a results
     * directory whose content no longer matches the submitted work items.
     */
    // language=kotlin
    private fun seedUnexpectedWorkerResultContent(fileName: String) = """
        val unexpectedResultFile = layout.buildDirectory
            .file("generated/svgToCompose/worker-results/$CONFIGURATION_NAME/$fileName")
            .get()
            .asFile
        tasks.named("$TASK_NAME") {
            doFirst {
                unexpectedResultFile.parentFile.mkdirs()
                unexpectedResultFile.writeText("origin=unknown\noutput=\nstatus=Ok\nmessage=\n")
            }
        }
    """.trimIndent()

    @Test
    fun `given worker results do not match the submitted files - when task runs - then build fails`() {
        // Arrange
        val script = buildGradleContent(pkg = "dev.tonholo.s2c.test.workerresults") +
            "\n" +
            seedUnexpectedWorkerResultContent(fileName = "result-42.properties")
        projectDir.resolve("build.gradle.kts").writeText(script)
        writeSvg("icons", "icon-a.svg", SIMPLE_SVG)

        // Act
        val result = runGradleAndFail(TASK_NAME)

        // Assert
        assertTrue(
            result.output.contains("Expected 1 worker result file(s) but found 2"),
            "Expected the build to fail on a worker result mismatch. Output:\n${result.output}",
        )
    }

    @Test
    fun `given every worker writes its result - when task runs - then build succeeds`() {
        // Arrange
        projectDir.resolve("build.gradle.kts")
            .writeText(buildGradleContent(pkg = "dev.tonholo.s2c.test.workerresults"))
        writeSvg("icons", "icon-a.svg", SIMPLE_SVG)

        // Act
        val result = runGradle(TASK_NAME)

        // Assert
        assertTrue(
            result.output.contains("BUILD SUCCESSFUL"),
            "Expected the build to succeed when all workers report a result. Output:\n${result.output}",
        )
    }
}
