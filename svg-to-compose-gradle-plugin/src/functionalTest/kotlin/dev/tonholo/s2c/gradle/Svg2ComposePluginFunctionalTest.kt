package dev.tonholo.s2c.gradle

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import java.io.File
import kotlin.io.path.createTempDirectory
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import org.junit.jupiter.api.Test

class Svg2ComposePluginFunctionalTest {
    private val projectRoot = File(System.getProperty("user.dir")).parentFile

    private val expectedDir = projectRoot.resolve("integrity-check/expected")

    private fun createTempProject(): File {
        val dir = createTempDirectory("svg2compose-functional-test").toFile()
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
            """.trimIndent()
        )
        return dir
    }

    private fun buildGradleContent(
        optimize: Boolean,
        pkg: String,
    ) = // language=kotlin
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
        """.trimIndent()

    private fun copyIconsToProject(projectDir: File, type: String) {
        val iconsDir = projectDir.resolve("icons")
        iconsDir.mkdirs()
        val resourceDir = File(
            requireNotNull(javaClass.classLoader.getResource(type)) {
                "Test resource directory not found: $type"
            }.toURI()
        )
        resourceDir.listFiles().orEmpty().forEach { file ->
            file.copyTo(iconsDir.resolve(file.name))
        }
    }

    private fun runGradle(
        projectDir: File,
        vararg args: String,
    ): BuildResult = GradleRunner.create()
        .withProjectDir(projectDir)
        .withPluginClasspath()
        .withArguments(*args, "--stacktrace")
        .forwardOutput()
        .build()

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
    private fun assertAllOutputsMatchExpected(
        projectDir: File,
        pkg: String,
        fileType: String,
        optimized: Boolean,
    ) {
        val suffix = if (optimized) "optimized" else "nonoptimized"
        val pkgPath = pkg.replace('.', '/')
        // KMP projects output to commonMain/kotlin/<pkg>, non-KMP to main/kotlin/<pkg>
        val generatedDir = projectDir.resolve("build/generated/svgToCompose/commonMain/kotlin/$pkgPath")
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
        val task = result.task(":$taskName")
        assertNotNull(task, "Task :$taskName was not found in the build")
        assertEquals(TaskOutcome.SUCCESS, task.outcome, "Task :$taskName did not succeed")
    }

    // --- SVG tests ---

    @Test
    fun `svg non-optimized conversion matches expected output`() {
        val pkg = "dev.tonholo.s2c.integrity.icon.svg"
        val projectDir = createTempProject()
        try {
            projectDir.resolve("build.gradle.kts").writeText(buildGradleContent(false, pkg))
            copyIconsToProject(projectDir, "svg")

            val result = runGradle(projectDir, "parseSvgToComposeIcon")
            assertTaskSuccess(result, "parseSvgToComposeIcon")
            assertAllOutputsMatchExpected(projectDir, pkg, fileType = "svg", optimized = false)
        } finally {
            projectDir.deleteRecursively()
        }
    }

    @Test
    fun `svg optimized conversion matches expected output`() {
        val pkg = "dev.tonholo.s2c.integrity.icon.svg"
        val projectDir = createTempProject()
        try {
            projectDir.resolve("build.gradle.kts").writeText(buildGradleContent(true, pkg))
            copyIconsToProject(projectDir, "svg")

            val result = runGradle(projectDir, "parseSvgToComposeIcon")
            assertTaskSuccess(result, "parseSvgToComposeIcon")
            assertAllOutputsMatchExpected(projectDir, pkg, fileType = "svg", optimized = true)
        } finally {
            projectDir.deleteRecursively()
        }
    }

    // --- AVG tests ---

    @Test
    fun `avg non-optimized conversion matches expected output`() {
        val pkg = "dev.tonholo.s2c.integrity.icon.avg"
        val projectDir = createTempProject()
        try {
            projectDir.resolve("build.gradle.kts").writeText(buildGradleContent(false, pkg))
            copyIconsToProject(projectDir, "avg")

            val result = runGradle(projectDir, "parseSvgToComposeIcon")
            assertTaskSuccess(result, "parseSvgToComposeIcon")
            assertAllOutputsMatchExpected(projectDir, pkg, fileType = "xml", optimized = false)
        } finally {
            projectDir.deleteRecursively()
        }
    }

    @Test
    fun `avg optimized conversion matches expected output`() {
        val pkg = "dev.tonholo.s2c.integrity.icon.avg"
        val projectDir = createTempProject()
        try {
            projectDir.resolve("build.gradle.kts").writeText(buildGradleContent(true, pkg))
            copyIconsToProject(projectDir, "avg")

            val result = runGradle(projectDir, "parseSvgToComposeIcon")
            assertTaskSuccess(result, "parseSvgToComposeIcon")
            assertAllOutputsMatchExpected(projectDir, pkg, fileType = "xml", optimized = true)
        } finally {
            projectDir.deleteRecursively()
        }
    }

    // --- Configuration cache tests ---

    @Test
    fun `svg conversion is compatible with configuration cache`() {
        val pkg = "dev.tonholo.s2c.test.svg.cache"
        val projectDir = createTempProject()
        try {
            projectDir.resolve("build.gradle.kts").writeText(buildGradleContent(false, pkg))
            copyIconsToProject(projectDir, "svg")

            // First run: store the configuration cache
            val firstResult = runGradle(
                projectDir,
                "parseSvgToComposeIcon",
                "--configuration-cache",
            )
            assertTaskSuccess(firstResult, "parseSvgToComposeIcon")
            assertTrue(
                firstResult.output.contains("Configuration cache entry stored"),
                "Expected configuration cache to be stored on first run",
            )

            // Second run: reuse the configuration cache
            val secondResult = runGradle(
                projectDir,
                "parseSvgToComposeIcon",
                "--configuration-cache",
            )
            assertTrue(
                secondResult.output.contains("Configuration cache entry reused"),
                "Expected configuration cache to be reused on second run",
            )
        } finally {
            projectDir.deleteRecursively()
        }
    }
}
