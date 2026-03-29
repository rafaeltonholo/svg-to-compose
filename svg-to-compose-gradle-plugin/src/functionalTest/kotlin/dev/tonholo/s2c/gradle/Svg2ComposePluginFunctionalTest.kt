package dev.tonholo.s2c.gradle

import dev.tonholo.s2c.gradle.common.GradleFunctionalTest
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Svg2ComposePluginFunctionalTest : GradleFunctionalTest() {

    override val tempDirPrefix: String = "svg2compose-functional-test"

    private val expectedDir = repoRoot.resolve("integrity-check/expected")

    private fun setupBuildGradle(optimize: Boolean, pkg: String) {
        projectDir.resolve("build.gradle.kts").writeText(
            // language=kotlin
            """
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
                    common {
                        optimize($optimize)
                        icons { noPreview() }
                    }
                    val icons by creating {
                        from(layout.projectDirectory.dir("icons"))
                        destinationPackage("$pkg")
                    }
                }
            }
            """.trimIndent(),
        )
    }

    /**
     * Asserts all generated .kt files match their corresponding expected files.
     *
     * Expected files are stored in `integrity-check/gradle-plugin/expected/` using the
     * naming convention `{IconName}.{fileType}.{optimized|nonoptimized}.kt`, where
     * `fileType` is the input extension (`svg` or `xml`) and `iconName` is the
     * PascalCase name of the generated `ImageVector`.
     *
     * On the first run, if no expected file exists yet (bootstrap), the generated
     * content is written as the new expected baseline and the check passes.
     */
    private fun assertAllOutputsMatchExpected(pkg: String, fileType: String, optimized: Boolean) {
        val suffix = if (optimized) "optimized" else "nonoptimized"
        val generatedDir = generatedBuildDir(pkg)
        assertTrue(generatedDir.exists(), "Generated directory does not exist: $generatedDir")

        val generatedFiles = generatedDir.walkTopDown()
            .filter { it.isFile && it.extension == "kt" }
            .sortedBy { it.name }
            .toList()
        assertTrue(generatedFiles.isNotEmpty(), "No generated .kt files found in $generatedDir")

        for (generatedFile in generatedFiles) {
            val expectedFileName = "${generatedFile.nameWithoutExtension}.$fileType.$suffix.kt"
            val expectedFile = expectedDir.resolve(expectedFileName)

            if (!expectedFile.exists()) {
                expectedDir.mkdirs()
                generatedFile.copyTo(expectedFile, overwrite = true)
                println("BOOTSTRAP: Wrote expected file $expectedFileName")
                continue
            }

            assertEquals(
                expectedFile.readText(),
                generatedFile.readText(),
                "Generated file content does not match expected for $expectedFileName",
            )
        }
    }

    private fun assertTaskSuccess(result: BuildResult, taskName: String) {
        assertTaskOutcome(result, ":$taskName", TaskOutcome.SUCCESS)
    }

    // --- SVG tests ---

    @Test
    fun `svg non-optimized conversion matches expected output`() {
        val pkg = "dev.tonholo.s2c.integrity.icon.svg"
        setupBuildGradle(false, pkg)
        copyResourcesToProject("icons", "svg")

        val result = runGradle("parseSvgToComposeIcon")
        assertTaskSuccess(result, "parseSvgToComposeIcon")
        assertAllOutputsMatchExpected(pkg, fileType = "svg", optimized = false)
    }

    @Test
    fun `svg optimized conversion matches expected output`() {
        val pkg = "dev.tonholo.s2c.integrity.icon.svg"
        setupBuildGradle(true, pkg)
        copyResourcesToProject("icons", "svg")

        val result = runGradle("parseSvgToComposeIcon")
        assertTaskSuccess(result, "parseSvgToComposeIcon")
        assertAllOutputsMatchExpected(pkg, fileType = "svg", optimized = true)
    }

    // --- AVG tests ---

    @Test
    fun `avg non-optimized conversion matches expected output`() {
        val pkg = "dev.tonholo.s2c.integrity.icon.avg"
        setupBuildGradle(false, pkg)
        copyResourcesToProject("icons", "avg")

        val result = runGradle("parseSvgToComposeIcon")
        assertTaskSuccess(result, "parseSvgToComposeIcon")
        assertAllOutputsMatchExpected(pkg, fileType = "xml", optimized = false)
    }

    @Test
    fun `avg optimized conversion matches expected output`() {
        val pkg = "dev.tonholo.s2c.integrity.icon.avg"
        setupBuildGradle(true, pkg)
        copyResourcesToProject("icons", "avg")

        val result = runGradle("parseSvgToComposeIcon")
        assertTaskSuccess(result, "parseSvgToComposeIcon")
        assertAllOutputsMatchExpected(pkg, fileType = "xml", optimized = true)
    }

    // --- Configuration cache tests ---

    @Test
    fun `svg conversion is compatible with configuration cache`() {
        val pkg = "dev.tonholo.s2c.test.svg.cache"
        setupBuildGradle(false, pkg)
        copyResourcesToProject("icons", "svg")

        // First run: store the configuration cache
        val firstResult = runGradle("parseSvgToComposeIcon", "--configuration-cache")
        assertTaskSuccess(firstResult, "parseSvgToComposeIcon")
        assertTrue(
            firstResult.output.contains("Configuration cache entry stored"),
            "Expected configuration cache to be stored on first run",
        )

        // Second run: reuse the configuration cache
        val secondResult = runGradle("parseSvgToComposeIcon", "--configuration-cache")
        assertTrue(
            secondResult.output.contains("Configuration cache entry reused"),
            "Expected configuration cache to be reused on second run",
        )
    }
}
