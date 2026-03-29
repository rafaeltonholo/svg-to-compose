package dev.tonholo.s2c.gradle

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.io.path.createTempDirectory
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Functional tests for incremental caching, persistent mode, and
 * multi-configuration scenarios.
 *
 * These tests exercise the Gradle plugin end-to-end through GradleRunner,
 * verifying that:
 * - Up-to-date checks work when nothing changes
 * - Incremental builds process only changed files
 * - Persistent mode outputs to src/ instead of build/
 * - Multi-configuration setups work with mixed modes
 */
@Suppress("FunctionName")
class IncrementalCacheFunctionalTest {

    companion object {
        private const val TASK_NAME = "parseSvgToComposeIcon"
        private const val TASK_PATH = ":$TASK_NAME"

        // language=svg
        private val SIMPLE_SVG_A = """
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
              <circle cx="12" cy="12" r="10" fill="black"/>
            </svg>
        """.trimIndent()

        // language=svg
        private val SIMPLE_SVG_B = """
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
              <rect x="2" y="2" width="20" height="20" fill="black"/>
            </svg>
        """.trimIndent()

        // language=svg
        private val SIMPLE_SVG_A_MODIFIED = """
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
              <circle cx="12" cy="12" r="8" fill="red"/>
            </svg>
        """.trimIndent()
    }

    // region [ Helpers ]

    private fun createTempProject(prefix: String = "s2c-incremental-test"): File {
        val dir = createTempDirectory(prefix).toFile()
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

    private fun buildGradleContent(pkg: String, optimize: Boolean = false): String =
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
        """.trimIndent()

    private fun persistentBuildGradleContent(pkg: String): String =
        // language=kotlin
        """
        @file:OptIn(dev.tonholo.s2c.annotations.DelicateSvg2ComposeApi::class)
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
                    icons { persist() }
                }
            }
        }
        """.trimIndent()

    private fun multiConfigBuildGradleContent(
        pkgA: String,
        pkgB: String,
        optimizeA: Boolean = false,
        optimizeB: Boolean = false,
        persistA: Boolean = false,
        persistB: Boolean = false,
    ): String {
        val persistAnnotation = if (persistA || persistB) {
            "@file:OptIn(dev.tonholo.s2c.annotations.DelicateSvg2ComposeApi::class)\n"
        } else {
            ""
        }
        val iconsBlockA = if (persistA) "persist()" else ""
        val iconsBlockB = if (persistB) "persist()" else ""
        // language=kotlin
        return """
        ${persistAnnotation}plugins {
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
                    icons { noPreview() }
                }
                val configA by creating {
                    from(layout.projectDirectory.dir("icons-a"))
                    destinationPackage("$pkgA")
                    optimize($optimizeA)
                    icons { $iconsBlockA }
                }
                val configB by creating {
                    from(layout.projectDirectory.dir("icons-b"))
                    destinationPackage("$pkgB")
                    optimize($optimizeB)
                    icons { $iconsBlockB }
                }
            }
        }
        """.trimIndent()
    }

    private fun writeSvg(projectDir: File, dirName: String, fileName: String, content: String) {
        val iconsDir = projectDir.resolve(dirName)
        iconsDir.mkdirs()
        iconsDir.resolve(fileName).writeText(content)
    }

    private fun runGradle(projectDir: File, vararg args: String): BuildResult = GradleRunner.create()
        .withProjectDir(projectDir)
        .withPluginClasspath()
        .withArguments(*args, "--stacktrace")
        .forwardOutput()
        .build()

    private fun assertTaskOutcome(result: BuildResult, expected: TaskOutcome) {
        val task = result.task(TASK_PATH)
        assertNotNull(task, "Task $TASK_PATH was not found in the build")
        assertEquals(
            expected = expected,
            actual = task.outcome,
            message = "Task $TASK_PATH expected $expected but was ${task.outcome}",
        )
    }

    private fun generatedBuildDir(projectDir: File, pkg: String): File {
        val pkgPath = pkg.replace('.', '/')
        return projectDir.resolve("build/generated/svgToCompose/commonMain/kotlin/$pkgPath")
    }

    private fun generatedSrcDir(projectDir: File, pkg: String): File {
        val pkgPath = pkg.replace('.', '/')
        return projectDir.resolve("src/commonMain/kotlin/$pkgPath")
    }

    // endregion [ Helpers ]

    // region [ Single Configuration Tests ]

    @Test
    fun `given unchanged project, when task runs again, then outcome is UP_TO_DATE`() {
        val pkg = "dev.tonholo.s2c.test.incremental.uptodate"
        val projectDir = createTempProject()
        try {
            // Arrange
            projectDir.resolve("build.gradle.kts").writeText(buildGradleContent(pkg))
            writeSvg(projectDir, "icons", "icon-a.svg", SIMPLE_SVG_A)
            val firstResult = runGradle(projectDir, TASK_NAME)
            assertTaskOutcome(firstResult, TaskOutcome.SUCCESS)

            // Act
            val secondResult = runGradle(projectDir, TASK_NAME)

            // Assert
            assertTaskOutcome(secondResult, TaskOutcome.UP_TO_DATE)
        } finally {
            projectDir.deleteRecursively()
        }
    }

    @Test
    fun `given one SVG, when second SVG added, then both outputs exist and first is unchanged`() {
        val pkg = "dev.tonholo.s2c.test.incremental.add"
        val projectDir = createTempProject()
        try {
            // Arrange
            projectDir.resolve("build.gradle.kts").writeText(buildGradleContent(pkg))
            writeSvg(projectDir, "icons", "icon-a.svg", SIMPLE_SVG_A)
            runGradle(projectDir, TASK_NAME)
            val genDir = generatedBuildDir(projectDir, pkg)
            val firstOutputContent = genDir.resolve("IconA.kt").readText()

            // Act
            writeSvg(projectDir, "icons", "icon-b.svg", SIMPLE_SVG_B)
            val secondResult = runGradle(projectDir, TASK_NAME)

            // Assert
            assertTaskOutcome(secondResult, TaskOutcome.SUCCESS)
            assertTrue(genDir.resolve("IconA.kt").exists(), "IconA.kt should still exist")
            assertTrue(genDir.resolve("IconB.kt").exists(), "IconB.kt should have been generated")
            assertEquals(firstOutputContent, genDir.resolve("IconA.kt").readText(), "IconA.kt should be unchanged")
        } finally {
            projectDir.deleteRecursively()
        }
    }

    @Test
    fun `given two SVGs, when one is modified, then unmodified output is unchanged`() {
        val pkg = "dev.tonholo.s2c.test.incremental.modify"
        val projectDir = createTempProject()
        try {
            // Arrange
            projectDir.resolve("build.gradle.kts").writeText(buildGradleContent(pkg))
            writeSvg(projectDir, "icons", "icon-a.svg", SIMPLE_SVG_A)
            writeSvg(projectDir, "icons", "icon-b.svg", SIMPLE_SVG_B)
            runGradle(projectDir, TASK_NAME)
            val genDir = generatedBuildDir(projectDir, pkg)
            val iconBContent = genDir.resolve("IconB.kt").readText()

            // Act
            writeSvg(projectDir, "icons", "icon-a.svg", SIMPLE_SVG_A_MODIFIED)
            val secondResult = runGradle(projectDir, TASK_NAME)

            // Assert
            assertTaskOutcome(secondResult, TaskOutcome.SUCCESS)
            assertEquals(iconBContent, genDir.resolve("IconB.kt").readText(), "IconB.kt should be unchanged")
        } finally {
            projectDir.deleteRecursively()
        }
    }

    @Test
    fun `given two SVGs, when one is deleted, then its output is removed and other remains`() {
        val pkg = "dev.tonholo.s2c.test.incremental.delete"
        val projectDir = createTempProject()
        try {
            // Arrange
            projectDir.resolve("build.gradle.kts").writeText(buildGradleContent(pkg))
            writeSvg(projectDir, "icons", "icon-a.svg", SIMPLE_SVG_A)
            writeSvg(projectDir, "icons", "icon-b.svg", SIMPLE_SVG_B)
            runGradle(projectDir, TASK_NAME)
            val genDir = generatedBuildDir(projectDir, pkg)
            assertTrue(genDir.resolve("IconA.kt").exists(), "IconA.kt should exist after first run")
            assertTrue(genDir.resolve("IconB.kt").exists(), "IconB.kt should exist after first run")

            // Act
            projectDir.resolve("icons/icon-a.svg").delete()
            val secondResult = runGradle(projectDir, TASK_NAME)

            // Assert
            assertTaskOutcome(secondResult, TaskOutcome.SUCCESS)
            assertFalse(genDir.resolve("IconA.kt").exists(), "IconA.kt should be removed after deleting icon-a.svg")
            assertTrue(genDir.resolve("IconB.kt").exists(), "IconB.kt should still exist")
        } finally {
            projectDir.deleteRecursively()
        }
    }

    @Test
    fun `given optimize false, when changed to optimize true, then output content changes`() {
        val pkg = "dev.tonholo.s2c.test.incremental.configchange"
        val projectDir = createTempProject()
        try {
            // Arrange
            projectDir.resolve("build.gradle.kts").writeText(buildGradleContent(pkg, optimize = false))
            writeSvg(projectDir, "icons", "icon-a.svg", SIMPLE_SVG_A)
            runGradle(projectDir, TASK_NAME)
            val genDir = generatedBuildDir(projectDir, pkg)
            val beforeContent = genDir.resolve("IconA.kt").readText()

            // Act
            projectDir.resolve("build.gradle.kts").writeText(buildGradleContent(pkg, optimize = true))
            val secondResult = runGradle(projectDir, TASK_NAME)

            // Assert
            assertTaskOutcome(secondResult, TaskOutcome.SUCCESS)
            val afterContent = genDir.resolve("IconA.kt").readText()
            assertTrue(beforeContent != afterContent, "Output should change when optimize flag changes")
        } finally {
            projectDir.deleteRecursively()
        }
    }

    @Test
    fun `given completed build, when clean then rebuild, then output is regenerated`() {
        val pkg = "dev.tonholo.s2c.test.incremental.afterclean"
        val projectDir = createTempProject()
        try {
            // Arrange
            projectDir.resolve("build.gradle.kts").writeText(buildGradleContent(pkg))
            writeSvg(projectDir, "icons", "icon-a.svg", SIMPLE_SVG_A)
            runGradle(projectDir, TASK_NAME)
            val genDir = generatedBuildDir(projectDir, pkg)
            assertTrue(genDir.resolve("IconA.kt").exists(), "IconA.kt should exist after first run")

            // Act
            runGradle(projectDir, "clean")
            assertFalse(genDir.resolve("IconA.kt").exists(), "IconA.kt should be gone after clean")
            val rebuildResult = runGradle(projectDir, TASK_NAME)

            // Assert
            assertTaskOutcome(rebuildResult, TaskOutcome.SUCCESS)
            assertTrue(genDir.resolve("IconA.kt").exists(), "IconA.kt should be regenerated after clean + rebuild")
        } finally {
            projectDir.deleteRecursively()
        }
    }

    // endregion [ Single Configuration Tests ]

    // region [ Persistent Mode Tests ]

    @Test
    fun `given persistent mode, when task runs, then output is in src and not in build`() {
        val pkg = "dev.tonholo.s2c.test.persistent.output"
        val projectDir = createTempProject()
        try {
            // Arrange
            projectDir.resolve("build.gradle.kts").writeText(persistentBuildGradleContent(pkg))
            writeSvg(projectDir, "icons", "icon-a.svg", SIMPLE_SVG_A)

            // Act
            val result = runGradle(projectDir, TASK_NAME)

            // Assert
            assertTaskOutcome(result, TaskOutcome.SUCCESS)
            val srcDir = generatedSrcDir(projectDir, pkg)
            val buildDir = generatedBuildDir(projectDir, pkg)
            assertTrue(srcDir.resolve("IconA.kt").exists(), "IconA.kt should exist in src/")
            assertFalse(buildDir.exists(), "build/generated output directory should not exist in persistent mode")
        } finally {
            projectDir.deleteRecursively()
        }
    }

    @Test
    fun `given persistent mode with two SVGs, when one is deleted, then orphaned output is removed from src`() {
        val pkg = "dev.tonholo.s2c.test.persistent.delete"
        val projectDir = createTempProject()
        try {
            // Arrange
            projectDir.resolve("build.gradle.kts").writeText(persistentBuildGradleContent(pkg))
            writeSvg(projectDir, "icons", "icon-a.svg", SIMPLE_SVG_A)
            writeSvg(projectDir, "icons", "icon-b.svg", SIMPLE_SVG_B)
            runGradle(projectDir, TASK_NAME)
            val srcDir = generatedSrcDir(projectDir, pkg)
            assertTrue(srcDir.resolve("IconA.kt").exists(), "IconA.kt should exist after first run")
            assertTrue(srcDir.resolve("IconB.kt").exists(), "IconB.kt should exist after first run")

            // Act
            projectDir.resolve("icons/icon-a.svg").delete()
            val secondResult = runGradle(projectDir, TASK_NAME)

            // Assert
            assertTaskOutcome(secondResult, TaskOutcome.SUCCESS)
            assertFalse(srcDir.resolve("IconA.kt").exists(), "IconA.kt should be removed from src/")
            assertTrue(srcDir.resolve("IconB.kt").exists(), "IconB.kt should still exist in src/")
        } finally {
            projectDir.deleteRecursively()
        }
    }

    @Test
    fun `given persistent mode, when output manually deleted and clean run, then it is regenerated`() {
        val pkg = "dev.tonholo.s2c.test.persistent.regen"
        val projectDir = createTempProject()
        try {
            // Arrange
            projectDir.resolve("build.gradle.kts").writeText(persistentBuildGradleContent(pkg))
            writeSvg(projectDir, "icons", "icon-a.svg", SIMPLE_SVG_A)
            runGradle(projectDir, TASK_NAME)
            val srcDir = generatedSrcDir(projectDir, pkg)
            assertTrue(srcDir.resolve("IconA.kt").exists(), "IconA.kt should exist after first run")
            srcDir.resolve("IconA.kt").delete()
            assertFalse(srcDir.resolve("IconA.kt").exists(), "IconA.kt should be gone after manual delete")

            // Act
            // Persistent outputs live outside build/, so Gradle's up-to-date check
            // doesn't know about them. A clean is needed to force re-generation.
            runGradle(projectDir, "clean")
            val secondResult = runGradle(projectDir, TASK_NAME)

            // Assert
            assertTaskOutcome(secondResult, TaskOutcome.SUCCESS)
            assertTrue(srcDir.resolve("IconA.kt").exists(), "IconA.kt should be regenerated")
        } finally {
            projectDir.deleteRecursively()
        }
    }

    // endregion [ Persistent Mode Tests ]

    // region [ Multi-Configuration Tests ]

    @Test
    fun `given two non-persistent configs, when task runs twice, then both output to build and second run is UP_TO_DATE`() {
        val pkgA = "dev.tonholo.s2c.test.multi.builda"
        val pkgB = "dev.tonholo.s2c.test.multi.buildb"
        val projectDir = createTempProject()
        try {
            // Arrange
            projectDir.resolve("build.gradle.kts").writeText(
                multiConfigBuildGradleContent(pkgA, pkgB),
            )
            writeSvg(projectDir, "icons-a", "icon-a.svg", SIMPLE_SVG_A)
            writeSvg(projectDir, "icons-b", "icon-b.svg", SIMPLE_SVG_B)
            val firstResult = runGradle(projectDir, TASK_NAME)
            assertTaskOutcome(firstResult, TaskOutcome.SUCCESS)
            val genDirA = generatedBuildDir(projectDir, pkgA)
            val genDirB = generatedBuildDir(projectDir, pkgB)
            assertTrue(genDirA.resolve("IconA.kt").exists(), "IconA.kt should exist in build/ for config A")
            assertTrue(genDirB.resolve("IconB.kt").exists(), "IconB.kt should exist in build/ for config B")

            // Act
            val secondResult = runGradle(projectDir, TASK_NAME)

            // Assert
            assertTaskOutcome(secondResult, TaskOutcome.UP_TO_DATE)
        } finally {
            projectDir.deleteRecursively()
        }
    }

    @Test
    fun `given two persistent configs, when task runs, then both output to src`() {
        val pkgA = "dev.tonholo.s2c.test.multi.persista"
        val pkgB = "dev.tonholo.s2c.test.multi.persistb"
        val projectDir = createTempProject()
        try {
            // Arrange
            projectDir.resolve("build.gradle.kts").writeText(
                multiConfigBuildGradleContent(pkgA, pkgB, persistA = true, persistB = true),
            )
            writeSvg(projectDir, "icons-a", "icon-a.svg", SIMPLE_SVG_A)
            writeSvg(projectDir, "icons-b", "icon-b.svg", SIMPLE_SVG_B)

            // Act
            val result = runGradle(projectDir, TASK_NAME)

            // Assert
            assertTaskOutcome(result, TaskOutcome.SUCCESS)
            val srcDirA = generatedSrcDir(projectDir, pkgA)
            val srcDirB = generatedSrcDir(projectDir, pkgB)
            assertTrue(srcDirA.resolve("IconA.kt").exists(), "IconA.kt should exist in src/ for config A")
            assertTrue(srcDirB.resolve("IconB.kt").exists(), "IconB.kt should exist in src/ for config B")
        } finally {
            projectDir.deleteRecursively()
        }
    }

    @Test
    fun `given mixed config, when task runs, then A outputs to build and B outputs to src`() {
        val pkgA = "dev.tonholo.s2c.test.multi.mixbuild"
        val pkgB = "dev.tonholo.s2c.test.multi.mixsrc"
        val projectDir = createTempProject()
        try {
            // Arrange
            projectDir.resolve("build.gradle.kts").writeText(
                multiConfigBuildGradleContent(pkgA, pkgB, persistA = false, persistB = true),
            )
            writeSvg(projectDir, "icons-a", "icon-a.svg", SIMPLE_SVG_A)
            writeSvg(projectDir, "icons-b", "icon-b.svg", SIMPLE_SVG_B)

            // Act
            val result = runGradle(projectDir, TASK_NAME)

            // Assert
            assertTaskOutcome(result, TaskOutcome.SUCCESS)
            val buildDirA = generatedBuildDir(projectDir, pkgA)
            val srcDirB = generatedSrcDir(projectDir, pkgB)
            assertTrue(buildDirA.resolve("IconA.kt").exists(), "IconA.kt should exist in build/ for config A")
            assertTrue(srcDirB.resolve("IconB.kt").exists(), "IconB.kt should exist in src/ for config B")
        } finally {
            projectDir.deleteRecursively()
        }
    }

    @Test
    fun `given mixed config, when one SVG modified in each, then only modified files reprocessed`() {
        val pkgA = "dev.tonholo.s2c.test.multi.mixincra"
        val pkgB = "dev.tonholo.s2c.test.multi.mixincrb"
        val projectDir = createTempProject()
        try {
            // Arrange
            projectDir.resolve("build.gradle.kts").writeText(
                multiConfigBuildGradleContent(pkgA, pkgB, persistA = false, persistB = true),
            )
            writeSvg(projectDir, "icons-a", "icon-a.svg", SIMPLE_SVG_A)
            writeSvg(projectDir, "icons-b", "icon-b.svg", SIMPLE_SVG_B)
            runGradle(projectDir, TASK_NAME)
            val buildDirA = generatedBuildDir(projectDir, pkgA)
            val srcDirB = generatedSrcDir(projectDir, pkgB)
            val iconABefore = buildDirA.resolve("IconA.kt").readText()
            val iconBBefore = srcDirB.resolve("IconB.kt").readText()

            // Act
            writeSvg(projectDir, "icons-a", "icon-a.svg", SIMPLE_SVG_A_MODIFIED)
            writeSvg(projectDir, "icons-b", "icon-b.svg", SIMPLE_SVG_A) // change B to something different
            val secondResult = runGradle(projectDir, TASK_NAME)

            // Assert
            assertTaskOutcome(secondResult, TaskOutcome.SUCCESS)
            val iconAAfter = buildDirA.resolve("IconA.kt").readText()
            val iconBAfter = srcDirB.resolve("IconB.kt").readText()
            assertTrue(iconABefore != iconAAfter, "IconA.kt should change after modification")
            assertTrue(iconBBefore != iconBAfter, "IconB.kt should change after modification")
        } finally {
            projectDir.deleteRecursively()
        }
    }

    @Test
    fun `given mixed config, when optimize changed on config A, then rebuild occurs`() {
        val pkgA = "dev.tonholo.s2c.test.multi.cfgchange.a"
        val pkgB = "dev.tonholo.s2c.test.multi.cfgchange.b"
        val projectDir = createTempProject()
        try {
            // Arrange
            projectDir.resolve("build.gradle.kts").writeText(
                multiConfigBuildGradleContent(
                    pkgA,
                    pkgB,
                    optimizeA = false,
                    optimizeB = false,
                    persistA = false,
                    persistB = true,
                ),
            )
            writeSvg(projectDir, "icons-a", "icon-a.svg", SIMPLE_SVG_A)
            writeSvg(projectDir, "icons-b", "icon-b.svg", SIMPLE_SVG_B)
            runGradle(projectDir, TASK_NAME)

            // Act
            projectDir.resolve("build.gradle.kts").writeText(
                multiConfigBuildGradleContent(
                    pkgA,
                    pkgB,
                    optimizeA = true,
                    optimizeB = false,
                    persistA = false,
                    persistB = true,
                ),
            )
            val secondResult = runGradle(projectDir, TASK_NAME)

            // Assert
            assertTaskOutcome(secondResult, TaskOutcome.SUCCESS)
        } finally {
            projectDir.deleteRecursively()
        }
    }

    // endregion [ Multi-Configuration Tests ]
}
