package dev.tonholo.s2c.gradle

import dev.tonholo.s2c.gradle.common.GradleFunctionalTest
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Functional tests verifying that `.editorconfig` resolution works end-to-end
 * through the Gradle plugin.
 *
 * The core library resolves `.editorconfig` when [ParserConfig.formatConfig] is
 * null (the Gradle plugin never sets it). These tests assert that formatting
 * settings — indent style, indent size, and final-newline — are reflected in
 * the generated Kotlin output.
 *
 * Temp directories are created in `/tmp/`, so the walker never reaches this
 * repo's `.editorconfig`. When a test DOES supply an `.editorconfig`, it uses
 * `root = true` to stop the walk.
 */
class EditorConfigFunctionalTest : GradleFunctionalTest() {

    override val tempDirPrefix: String = "s2c-editorconfig-test"

    private fun setupBuildGradle(pkg: String) {
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
                        optimize(false)
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

    private fun copyOneIcon() {
        copyResourcesToProject("icons", "svg")
        // Keep only attention-filled.svg for a fast, deterministic test
        val iconsDir = projectDir.resolve("icons")
        iconsDir.listFiles().orEmpty()
            .filter { it.name != "attention-filled.svg" }
            .forEach { it.delete() }
    }

    private fun writeEditorConfig(content: String) {
        projectDir.resolve(".editorconfig").writeText(content)
    }

    private fun assertTaskSuccess(result: org.gradle.testkit.runner.BuildResult) {
        assertTaskOutcome(result, ":parseSvgToComposeIcon", TaskOutcome.SUCCESS)
    }

    private fun readGeneratedFiles(pkg: String): List<String> {
        val generatedDir = generatedBuildDir(pkg)
        assertTrue(generatedDir.exists(), "Generated directory does not exist: $generatedDir")
        return generatedDir.walkTopDown()
            .filter { it.isFile && it.extension == "kt" }
            .map { it.readText() }
            .toList()
            .also { assertTrue(it.isNotEmpty(), "No generated .kt files found") }
    }

    /**
     * Asserts that every indented line uses the expected indentation style and size.
     *
     * @param content The generated file content.
     * @param indentSize The expected number of characters per indent level.
     * @param useTabs Whether indentation should be tabs (true) or spaces (false).
     */
    private fun assertIndentation(
        content: String,
        indentSize: Int,
        useTabs: Boolean,
    ) {
        val lines = content.lines()
        for ((index, line) in lines.withIndex()) {
            if (line.isBlank()) continue
            val leading = line.takeWhile { it == ' ' || it == '\t' }
            if (leading.isEmpty()) continue

            val lineNum = index + 1
            if (useTabs) {
                assertTrue(
                    leading.all { it == '\t' },
                    "Line $lineNum: expected tab indentation but found spaces: \"$leading\"",
                )
            } else {
                assertTrue(
                    leading.all { it == ' ' },
                    "Line $lineNum: expected space indentation but found tabs: \"$leading\"",
                )
                assertEquals(
                    0,
                    leading.length % indentSize,
                    "Line $lineNum: indent ${leading.length} is not a multiple of $indentSize",
                )
            }
        }
    }

    private fun assertFinalNewline(content: String, expected: Boolean) {
        if (expected) {
            assertTrue(content.endsWith("\n"), "File should end with a newline")
        } else {
            assertTrue(!content.endsWith("\n"), "File should not end with a newline")
        }
    }

    // --- Tests ---

    @Test
    fun `default formatting when no editorconfig exists`() {
        val pkg = "dev.tonholo.s2c.test.editorconfig.defaults"

        // Arrange
        setupBuildGradle(pkg)
        copyOneIcon()

        // Act
        val result = runGradle("parseSvgToComposeIcon")

        // Assert
        assertTaskSuccess(result)
        for (content in readGeneratedFiles(pkg)) {
            assertIndentation(content, indentSize = 4, useTabs = false)
            assertFinalNewline(content, expected = true)
        }
    }

    @Test
    fun `editorconfig with 2-space indent is respected`() {
        val pkg = "dev.tonholo.s2c.test.editorconfig.twospace"

        // Arrange
        setupBuildGradle(pkg)
        copyOneIcon()
        writeEditorConfig(
            """
            root = true

            [*]
            indent_style = space
            indent_size = 2
            """.trimIndent(),
        )

        // Act
        val result = runGradle("parseSvgToComposeIcon")

        // Assert
        assertTaskSuccess(result)
        for (content in readGeneratedFiles(pkg)) {
            assertIndentation(content, indentSize = 2, useTabs = false)
        }
    }

    @Test
    fun `editorconfig with tab indent is respected`() {
        val pkg = "dev.tonholo.s2c.test.editorconfig.tabs"

        // Arrange
        setupBuildGradle(pkg)
        copyOneIcon()
        writeEditorConfig(
            """
            root = true

            [*]
            indent_style = tab
            """.trimIndent(),
        )

        // Act
        val result = runGradle("parseSvgToComposeIcon")

        // Assert
        assertTaskSuccess(result)
        for (content in readGeneratedFiles(pkg)) {
            assertIndentation(content, indentSize = 1, useTabs = true)
        }
    }

    @Test
    fun `editorconfig with kotlin-specific section overrides global`() {
        val pkg = "dev.tonholo.s2c.test.editorconfig.ktoverride"

        // Arrange
        setupBuildGradle(pkg)
        copyOneIcon()
        writeEditorConfig(
            """
            root = true

            [*]
            indent_style = space
            indent_size = 4

            [*.kt]
            indent_size = 2
            """.trimIndent(),
        )

        // Act
        val result = runGradle("parseSvgToComposeIcon")

        // Assert
        assertTaskSuccess(result)
        for (content in readGeneratedFiles(pkg)) {
            assertIndentation(content, indentSize = 2, useTabs = false)
        }
    }
}
