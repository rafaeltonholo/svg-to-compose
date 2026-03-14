package dev.tonholo.s2c.gradle

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.io.path.createTempDirectory
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
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
class EditorConfigFunctionalTest {

    private fun createTempProject(): File {
        val dir = createTempDirectory("s2c-editorconfig-test").toFile()
        dir.resolve("settings.gradle.kts").writeText(
            // language=kotlin
            """
            pluginManagement {
                repositories {
                    mavenCentral()
                    gradlePluginPortal()
                }
            }
            rootProject.name = "test-project"
            """.trimIndent(),
        )
        return dir
    }

    private fun buildGradleContent(pkg: String) =
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
        """.trimIndent()

    private fun copyOneIcon(projectDir: File) {
        val iconsDir = projectDir.resolve("icons")
        iconsDir.mkdirs()
        val resourceDir = File(
            requireNotNull(javaClass.classLoader.getResource("svg")) {
                "Test resource directory not found: svg"
            }.toURI(),
        )
        val icon = resourceDir.listFiles().orEmpty()
            .filter { it.isFile && it.extension == "svg" }
            .first { it.name == "attention-filled.svg" }
        icon.copyTo(iconsDir.resolve(icon.name))
    }

    private fun writeEditorConfig(projectDir: File, content: String) {
        projectDir.resolve(".editorconfig").writeText(content)
    }

    private fun runGradle(projectDir: File): BuildResult = GradleRunner.create()
        .withProjectDir(projectDir)
        .withPluginClasspath()
        .withArguments("parseSvgToComposeIcon", "--stacktrace")
        .forwardOutput()
        .build()

    private fun readGeneratedFiles(projectDir: File, pkg: String): List<String> {
        val pkgPath = pkg.replace('.', '/')
        val generatedDir = projectDir
            .resolve("build/generated/svgToCompose/commonMain/kotlin/$pkgPath")
        assertTrue(generatedDir.exists(), "Generated directory does not exist: $generatedDir")
        return generatedDir.walkTopDown()
            .filter { it.isFile && it.extension == "kt" }
            .map { it.readText() }
            .toList()
            .also { assertTrue(it.isNotEmpty(), "No generated .kt files found") }
    }

    private fun assertTaskSuccess(result: BuildResult) {
        val taskName = "parseSvgToComposeIcon"
        val task = result.task(":$taskName")
        assertNotNull(task, "Task :$taskName was not found in the build")
        assertEquals(TaskOutcome.SUCCESS, task.outcome, "Task :$taskName did not succeed")
    }

    /**
     * Asserts that every indented line uses the expected indentation style and size.
     *
     * @param content The generated file content.
     * @param indentSize The expected number of characters per indent level.
     * @param useTabs Whether indentation should be tabs (true) or spaces (false).
     */
    private fun assertIndentation(content: String, indentSize: Int, useTabs: Boolean) {
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
        val projectDir = createTempProject()
        try {
            projectDir.resolve("build.gradle.kts").writeText(buildGradleContent(pkg))
            copyOneIcon(projectDir)

            val result = runGradle(projectDir)
            assertTaskSuccess(result)

            for (content in readGeneratedFiles(projectDir, pkg)) {
                assertIndentation(content, indentSize = 4, useTabs = false)
                assertFinalNewline(content, expected = true)
            }
        } finally {
            projectDir.deleteRecursively()
        }
    }

    @Test
    fun `editorconfig with 2-space indent is respected`() {
        val pkg = "dev.tonholo.s2c.test.editorconfig.twospace"
        val projectDir = createTempProject()
        try {
            projectDir.resolve("build.gradle.kts").writeText(buildGradleContent(pkg))
            copyOneIcon(projectDir)
            writeEditorConfig(
                projectDir,
                """
                root = true

                [*]
                indent_style = space
                indent_size = 2
                """.trimIndent(),
            )

            val result = runGradle(projectDir)
            assertTaskSuccess(result)

            for (content in readGeneratedFiles(projectDir, pkg)) {
                assertIndentation(content, indentSize = 2, useTabs = false)
            }
        } finally {
            projectDir.deleteRecursively()
        }
    }

    @Test
    fun `editorconfig with tab indent is respected`() {
        val pkg = "dev.tonholo.s2c.test.editorconfig.tabs"
        val projectDir = createTempProject()
        try {
            projectDir.resolve("build.gradle.kts").writeText(buildGradleContent(pkg))
            copyOneIcon(projectDir)
            writeEditorConfig(
                projectDir,
                """
                root = true

                [*]
                indent_style = tab
                """.trimIndent(),
            )

            val result = runGradle(projectDir)
            assertTaskSuccess(result)

            for (content in readGeneratedFiles(projectDir, pkg)) {
                assertIndentation(content, indentSize = 1, useTabs = true)
            }
        } finally {
            projectDir.deleteRecursively()
        }
    }

    @Test
    fun `editorconfig with kotlin-specific section overrides global`() {
        val pkg = "dev.tonholo.s2c.test.editorconfig.ktoverride"
        val projectDir = createTempProject()
        try {
            projectDir.resolve("build.gradle.kts").writeText(buildGradleContent(pkg))
            copyOneIcon(projectDir)
            writeEditorConfig(
                projectDir,
                """
                root = true

                [*]
                indent_style = space
                indent_size = 4

                [*.kt]
                indent_size = 2
                """.trimIndent(),
            )

            val result = runGradle(projectDir)
            assertTaskSuccess(result)

            for (content in readGeneratedFiles(projectDir, pkg)) {
                assertIndentation(content, indentSize = 2, useTabs = false)
            }
        } finally {
            projectDir.deleteRecursively()
        }
    }
}
