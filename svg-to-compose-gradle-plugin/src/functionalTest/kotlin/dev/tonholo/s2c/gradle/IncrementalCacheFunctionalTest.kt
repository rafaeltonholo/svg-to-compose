package dev.tonholo.s2c.gradle

import dev.tonholo.s2c.gradle.common.GradleFunctionalTest
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
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
class IncrementalCacheFunctionalTest : GradleFunctionalTest() {

    override val tempDirPrefix: String = "s2c-incremental-cache-test"

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

    private fun assertTaskOutcome(result: BuildResult, expected: TaskOutcome) {
        assertTaskOutcome(result, TASK_PATH, expected)
    }

    private fun setupBuildGradle(pkg: String, optimize: Boolean = false) {
        projectDir.resolve("build.gradle.kts").writeText(buildGradleContent(pkg, optimize))
    }

    private fun setupPersistentBuildGradle(pkg: String) {
        projectDir.resolve("build.gradle.kts").writeText(persistentBuildGradleContent(pkg))
    }

    private fun setupMultiConfigBuildGradle(
        pkgA: String,
        pkgB: String,
        optimizeA: Boolean = false,
        optimizeB: Boolean = false,
        persistA: Boolean = false,
        persistB: Boolean = false,
    ) {
        projectDir.resolve("build.gradle.kts").writeText(
            multiConfigBuildGradleContent(pkgA, pkgB, optimizeA, optimizeB, persistA, persistB),
        )
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

    // endregion [ Helpers ]

    // region [ Single Configuration Tests ]

    @Test
    fun `given unchanged project, when task runs again, then outcome is UP_TO_DATE`() {
        // Arrange
        setupBuildGradle("dev.tonholo.s2c.test.incremental.uptodate")
        writeSvg("icons", "icon-a.svg", SIMPLE_SVG_A)
        val firstResult = runGradle(TASK_NAME)
        assertTaskOutcome(firstResult, TaskOutcome.SUCCESS)

        // Act
        val secondResult = runGradle(TASK_NAME)

        // Assert
        assertTaskOutcome(secondResult, TaskOutcome.UP_TO_DATE)
    }

    @Test
    fun `given one SVG, when second SVG added, then both outputs exist and first is unchanged`() {
        val pkg = "dev.tonholo.s2c.test.incremental.add"

        // Arrange
        setupBuildGradle(pkg)
        writeSvg("icons", "icon-a.svg", SIMPLE_SVG_A)
        runGradle(TASK_NAME)
        val genDir = generatedBuildDir(pkg)
        val firstOutputContent = genDir.resolve("IconA.kt").readText()

        // Act
        writeSvg("icons", "icon-b.svg", SIMPLE_SVG_B)
        val secondResult = runGradle(TASK_NAME)

        // Assert
        assertTaskOutcome(secondResult, TaskOutcome.SUCCESS)
        assertTrue(genDir.resolve("IconA.kt").exists(), "IconA.kt should still exist")
        assertTrue(genDir.resolve("IconB.kt").exists(), "IconB.kt should have been generated")
        assertEquals(firstOutputContent, genDir.resolve("IconA.kt").readText(), "IconA.kt should be unchanged")
    }

    @Test
    fun `given two SVGs, when one is modified, then unmodified output is unchanged`() {
        val pkg = "dev.tonholo.s2c.test.incremental.modify"

        // Arrange
        setupBuildGradle(pkg)
        writeSvg("icons", "icon-a.svg", SIMPLE_SVG_A)
        writeSvg("icons", "icon-b.svg", SIMPLE_SVG_B)
        runGradle(TASK_NAME)
        val genDir = generatedBuildDir(pkg)
        val iconBContent = genDir.resolve("IconB.kt").readText()

        // Act
        writeSvg("icons", "icon-a.svg", SIMPLE_SVG_A_MODIFIED)
        val secondResult = runGradle(TASK_NAME)

        // Assert
        assertTaskOutcome(secondResult, TaskOutcome.SUCCESS)
        assertEquals(iconBContent, genDir.resolve("IconB.kt").readText(), "IconB.kt should be unchanged")
    }

    @Test
    fun `given two SVGs, when one is deleted, then its output is removed and other remains`() {
        val pkg = "dev.tonholo.s2c.test.incremental.delete"

        // Arrange
        setupBuildGradle(pkg)
        writeSvg("icons", "icon-a.svg", SIMPLE_SVG_A)
        writeSvg("icons", "icon-b.svg", SIMPLE_SVG_B)
        runGradle(TASK_NAME)
        val genDir = generatedBuildDir(pkg)
        assertTrue(genDir.resolve("IconA.kt").exists(), "IconA.kt should exist after first run")
        assertTrue(genDir.resolve("IconB.kt").exists(), "IconB.kt should exist after first run")

        // Act
        projectDir.resolve("icons/icon-a.svg").delete()
        val secondResult = runGradle(TASK_NAME)

        // Assert
        assertTaskOutcome(secondResult, TaskOutcome.SUCCESS)
        assertFalse(genDir.resolve("IconA.kt").exists(), "IconA.kt should be removed after deleting icon-a.svg")
        assertTrue(genDir.resolve("IconB.kt").exists(), "IconB.kt should still exist")
    }

    @Test
    fun `given optimize false, when changed to optimize true, then output content changes`() {
        val pkg = "dev.tonholo.s2c.test.incremental.configchange"

        // Arrange
        setupBuildGradle(pkg, optimize = false)
        writeSvg("icons", "icon-a.svg", SIMPLE_SVG_A)
        runGradle(TASK_NAME)
        val genDir = generatedBuildDir(pkg)
        val beforeContent = genDir.resolve("IconA.kt").readText()

        // Act
        setupBuildGradle(pkg, optimize = true)
        val secondResult = runGradle(TASK_NAME)

        // Assert
        assertTaskOutcome(secondResult, TaskOutcome.SUCCESS)
        val afterContent = genDir.resolve("IconA.kt").readText()
        assertTrue(beforeContent != afterContent, "Output should change when optimize flag changes")
    }

    @Test
    fun `given completed build, when clean then rebuild, then output is regenerated`() {
        val pkg = "dev.tonholo.s2c.test.incremental.afterclean"

        // Arrange
        setupBuildGradle(pkg)
        writeSvg("icons", "icon-a.svg", SIMPLE_SVG_A)
        runGradle(TASK_NAME)
        val genDir = generatedBuildDir(pkg)
        assertTrue(genDir.resolve("IconA.kt").exists(), "IconA.kt should exist after first run")

        // Act
        runGradle("clean")
        assertFalse(genDir.resolve("IconA.kt").exists(), "IconA.kt should be gone after clean")
        val rebuildResult = runGradle(TASK_NAME)

        // Assert
        assertTaskOutcome(rebuildResult, TaskOutcome.SUCCESS)
        assertTrue(genDir.resolve("IconA.kt").exists(), "IconA.kt should be regenerated after clean + rebuild")
    }

    // endregion [ Single Configuration Tests ]

    // region [ Incrementality Verification Tests ]

    @Test
    fun `given first run, when task executes, then build is non-incremental`() {
        // Arrange
        setupBuildGradle("dev.tonholo.s2c.test.verify.first.run")
        writeSvg("icons", "icon-a.svg", SIMPLE_SVG_A)

        // Act
        val result = runGradle(TASK_NAME, info = true)

        // Assert
        assertTaskOutcome(result, TaskOutcome.SUCCESS)
        assertTrue(
            result.output.contains("Non-incremental build for configuration"),
            "First run should be non-incremental",
        )
    }

    @Test
    fun `given file added, when task runs incrementally, then only new file is processed`() {
        // Arrange
        setupBuildGradle("dev.tonholo.s2c.test.verify.incr.add")
        writeSvg("icons", "icon-a.svg", SIMPLE_SVG_A)
        runGradle(TASK_NAME)

        // Act
        writeSvg("icons", "icon-b.svg", SIMPLE_SVG_B)
        val result = runGradle(TASK_NAME, info = true)

        // Assert
        assertTaskOutcome(result, TaskOutcome.SUCCESS)
        assertFalse(
            result.output.contains("Non-incremental build for configuration"),
            "Second run should be incremental, not non-incremental",
        )
        assertTrue(
            result.output.contains("Files eligible for processing: [icon-b.svg]"),
            "Only the newly added file should be processed, but output was:\n${
                result.output.lines().filter { it.contains("eligible") }.joinToString("\n")
            }",
        )
    }

    @Test
    fun `given file modified, when task runs incrementally, then only modified file is processed`() {
        // Arrange
        setupBuildGradle("dev.tonholo.s2c.test.verify.incr.modify")
        writeSvg("icons", "icon-a.svg", SIMPLE_SVG_A)
        writeSvg("icons", "icon-b.svg", SIMPLE_SVG_B)
        runGradle(TASK_NAME)

        // Act
        writeSvg("icons", "icon-a.svg", SIMPLE_SVG_A_MODIFIED)
        val result = runGradle(TASK_NAME, info = true)

        // Assert
        assertTaskOutcome(result, TaskOutcome.SUCCESS)
        assertFalse(
            result.output.contains("Non-incremental build for configuration"),
            "Second run should be incremental",
        )
        assertTrue(
            result.output.contains("Files eligible for processing: [icon-a.svg]"),
            "Only the modified file should be processed, but output was:\n${
                result.output.lines().filter { it.contains("eligible") }.joinToString("\n")
            }",
        )
    }

    @Test
    fun `given file modified then reverted, when task runs each time, then only changed file is processed`() {
        val pkg = "dev.tonholo.s2c.test.verify.incr.revert"

        // Arrange - initial build with both SVGs
        setupBuildGradle(pkg)
        writeSvg("icons", "icon-a.svg", SIMPLE_SVG_A)
        writeSvg("icons", "icon-b.svg", SIMPLE_SVG_B)
        runGradle(TASK_NAME)
        val genDir = generatedBuildDir(pkg)
        val originalIconAContent = genDir.resolve("IconA.kt").readText()
        val iconBContent = genDir.resolve("IconB.kt").readText()

        // Act 1 - modify icon-a
        writeSvg("icons", "icon-a.svg", SIMPLE_SVG_A_MODIFIED)
        val modifiedResult = runGradle(TASK_NAME, info = true)

        // Assert 1 - only icon-a reprocessed, output changed
        assertTaskOutcome(modifiedResult, TaskOutcome.SUCCESS)
        assertFalse(
            modifiedResult.output.contains("Non-incremental build for configuration"),
            "Should be incremental after modifying one file",
        )
        assertTrue(
            modifiedResult.output.contains("Files eligible for processing: [icon-a.svg]"),
            "Only icon-a.svg should be processed after modification",
        )
        val modifiedIconAContent = genDir.resolve("IconA.kt").readText()
        assertTrue(originalIconAContent != modifiedIconAContent, "IconA.kt should change after modification")
        assertEquals(iconBContent, genDir.resolve("IconB.kt").readText(), "IconB.kt should be unchanged")

        // Act 2 - revert icon-a back to original
        writeSvg("icons", "icon-a.svg", SIMPLE_SVG_A)
        val revertedResult = runGradle(TASK_NAME, info = true)

        // Assert 2 - only icon-a reprocessed, output matches original
        assertTaskOutcome(revertedResult, TaskOutcome.SUCCESS)
        assertFalse(
            revertedResult.output.contains("Non-incremental build for configuration"),
            "Should be incremental after reverting one file",
        )
        assertTrue(
            revertedResult.output.contains("Files eligible for processing: [icon-a.svg]"),
            "Only icon-a.svg should be processed after revert",
        )
        assertEquals(originalIconAContent, genDir.resolve("IconA.kt").readText(), "IconA.kt should match original")
        assertEquals(iconBContent, genDir.resolve("IconB.kt").readText(), "IconB.kt should still be unchanged")
    }

    @Test
    fun `given config changed, when task runs, then build is non-incremental`() {
        // Arrange
        setupBuildGradle("dev.tonholo.s2c.test.verify.noninc.config", optimize = false)
        writeSvg("icons", "icon-a.svg", SIMPLE_SVG_A)
        runGradle(TASK_NAME)

        // Act
        setupBuildGradle("dev.tonholo.s2c.test.verify.noninc.config", optimize = true)
        val result = runGradle(TASK_NAME, info = true)

        // Assert
        assertTaskOutcome(result, TaskOutcome.SUCCESS)
        assertTrue(
            result.output.contains("Non-incremental build for configuration"),
            "Config change should trigger non-incremental build",
        )
    }

    // endregion [ Incrementality Verification Tests ]

    // region [ Persistent Mode Tests ]

    @Test
    fun `given persistent mode, when task runs, then output is in src and not in build`() {
        val pkg = "dev.tonholo.s2c.test.persistent.output"

        // Arrange
        setupPersistentBuildGradle(pkg)
        writeSvg("icons", "icon-a.svg", SIMPLE_SVG_A)

        // Act
        val result = runGradle(TASK_NAME)

        // Assert
        assertTaskOutcome(result, TaskOutcome.SUCCESS)
        val srcDir = generatedSrcDir(pkg)
        val buildDir = generatedBuildDir(pkg)
        assertTrue(srcDir.resolve("IconA.kt").exists(), "IconA.kt should exist in src/")
        assertFalse(buildDir.exists(), "build/generated output directory should not exist in persistent mode")
    }

    @Test
    fun `given persistent mode with two SVGs, when one is deleted, then orphaned output is removed from src`() {
        val pkg = "dev.tonholo.s2c.test.persistent.delete"

        // Arrange
        setupPersistentBuildGradle(pkg)
        writeSvg("icons", "icon-a.svg", SIMPLE_SVG_A)
        writeSvg("icons", "icon-b.svg", SIMPLE_SVG_B)
        runGradle(TASK_NAME)
        val srcDir = generatedSrcDir(pkg)
        assertTrue(srcDir.resolve("IconA.kt").exists(), "IconA.kt should exist after first run")
        assertTrue(srcDir.resolve("IconB.kt").exists(), "IconB.kt should exist after first run")

        // Act
        projectDir.resolve("icons/icon-a.svg").delete()
        val secondResult = runGradle(TASK_NAME)

        // Assert
        assertTaskOutcome(secondResult, TaskOutcome.SUCCESS)
        assertFalse(srcDir.resolve("IconA.kt").exists(), "IconA.kt should be removed from src/")
        assertTrue(srcDir.resolve("IconB.kt").exists(), "IconB.kt should still exist in src/")
    }

    @Test
    fun `given persistent mode, when output manually deleted and clean run, then it is regenerated`() {
        val pkg = "dev.tonholo.s2c.test.persistent.regen"

        // Arrange
        setupPersistentBuildGradle(pkg)
        writeSvg("icons", "icon-a.svg", SIMPLE_SVG_A)
        runGradle(TASK_NAME)
        val srcDir = generatedSrcDir(pkg)
        assertTrue(srcDir.resolve("IconA.kt").exists(), "IconA.kt should exist after first run")
        srcDir.resolve("IconA.kt").delete()
        assertFalse(srcDir.resolve("IconA.kt").exists(), "IconA.kt should be gone after manual delete")

        // Act
        // Persistent outputs live outside build/, so Gradle's up-to-date check
        // doesn't know about them. A clean is needed to force re-generation.
        runGradle("clean")
        val secondResult = runGradle(TASK_NAME)

        // Assert
        assertTaskOutcome(secondResult, TaskOutcome.SUCCESS)
        assertTrue(srcDir.resolve("IconA.kt").exists(), "IconA.kt should be regenerated")
    }

    @Test
    fun `given persistent mode with build cache, when SVG reverted to previous content, then output is not stale`() {
        val pkg = "dev.tonholo.s2c.test.persistent.cache.revert"

        // Arrange - initial build with build cache enabled
        setupPersistentBuildGradle(pkg)
        writeSvg("icons", "icon-a.svg", SIMPLE_SVG_A)
        runGradle(TASK_NAME, "--build-cache")
        val srcDir = generatedSrcDir(pkg)
        val originalContent = srcDir.resolve("IconA.kt").readText()

        // Act 1 - modify the SVG
        writeSvg("icons", "icon-a.svg", SIMPLE_SVG_A_MODIFIED)
        runGradle(TASK_NAME, "--build-cache")
        val modifiedContent = srcDir.resolve("IconA.kt").readText()
        assertTrue(originalContent != modifiedContent, "Output should change after SVG modification")

        // Act 2 - revert the SVG back to original (this would be a cache hit without the fix)
        writeSvg("icons", "icon-a.svg", SIMPLE_SVG_A)
        val revertedResult = runGradle(TASK_NAME, "--build-cache")

        // Assert - output must match original, NOT be stale from the modified build
        assertTaskOutcome(revertedResult, TaskOutcome.SUCCESS)
        val revertedContent = srcDir.resolve("IconA.kt").readText()
        assertEquals(originalContent, revertedContent, "Persistent output should match original after revert")
    }

    // endregion [ Persistent Mode Tests ]

    // region [ Multi-Configuration Tests ]

    @Test
    fun `given two non-persistent configs, when task runs twice, then both output to build and second run is UP_TO_DATE`() {
        val pkgA = "dev.tonholo.s2c.test.multi.builda"
        val pkgB = "dev.tonholo.s2c.test.multi.buildb"

        // Arrange
        setupMultiConfigBuildGradle(pkgA, pkgB)
        writeSvg("icons-a", "icon-a.svg", SIMPLE_SVG_A)
        writeSvg("icons-b", "icon-b.svg", SIMPLE_SVG_B)
        val firstResult = runGradle(TASK_NAME)
        assertTaskOutcome(firstResult, TaskOutcome.SUCCESS)
        val genDirA = generatedBuildDir(pkgA)
        val genDirB = generatedBuildDir(pkgB)
        assertTrue(genDirA.resolve("IconA.kt").exists(), "IconA.kt should exist in build/ for config A")
        assertTrue(genDirB.resolve("IconB.kt").exists(), "IconB.kt should exist in build/ for config B")

        // Act
        val secondResult = runGradle(TASK_NAME)

        // Assert
        assertTaskOutcome(secondResult, TaskOutcome.UP_TO_DATE)
    }

    @Test
    fun `given two persistent configs, when task runs, then both output to src`() {
        val pkgA = "dev.tonholo.s2c.test.multi.persista"
        val pkgB = "dev.tonholo.s2c.test.multi.persistb"

        // Arrange
        setupMultiConfigBuildGradle(pkgA, pkgB, persistA = true, persistB = true)
        writeSvg("icons-a", "icon-a.svg", SIMPLE_SVG_A)
        writeSvg("icons-b", "icon-b.svg", SIMPLE_SVG_B)

        // Act
        val result = runGradle(TASK_NAME)

        // Assert
        assertTaskOutcome(result, TaskOutcome.SUCCESS)
        val srcDirA = generatedSrcDir(pkgA)
        val srcDirB = generatedSrcDir(pkgB)
        assertTrue(srcDirA.resolve("IconA.kt").exists(), "IconA.kt should exist in src/ for config A")
        assertTrue(srcDirB.resolve("IconB.kt").exists(), "IconB.kt should exist in src/ for config B")
    }

    @Test
    fun `given mixed config, when task runs, then A outputs to build and B outputs to src`() {
        val pkgA = "dev.tonholo.s2c.test.multi.mixbuild"
        val pkgB = "dev.tonholo.s2c.test.multi.mixsrc"

        // Arrange
        setupMultiConfigBuildGradle(pkgA, pkgB, persistA = false, persistB = true)
        writeSvg("icons-a", "icon-a.svg", SIMPLE_SVG_A)
        writeSvg("icons-b", "icon-b.svg", SIMPLE_SVG_B)

        // Act
        val result = runGradle(TASK_NAME)

        // Assert
        assertTaskOutcome(result, TaskOutcome.SUCCESS)
        val buildDirA = generatedBuildDir(pkgA)
        val srcDirB = generatedSrcDir(pkgB)
        assertTrue(buildDirA.resolve("IconA.kt").exists(), "IconA.kt should exist in build/ for config A")
        assertTrue(srcDirB.resolve("IconB.kt").exists(), "IconB.kt should exist in src/ for config B")
    }

    @Test
    fun `given mixed config, when one SVG modified in each, then only modified files reprocessed`() {
        val pkgA = "dev.tonholo.s2c.test.multi.mixincra"
        val pkgB = "dev.tonholo.s2c.test.multi.mixincrb"

        // Arrange
        setupMultiConfigBuildGradle(pkgA, pkgB, persistA = false, persistB = true)
        writeSvg("icons-a", "icon-a.svg", SIMPLE_SVG_A)
        writeSvg("icons-b", "icon-b.svg", SIMPLE_SVG_B)
        runGradle(TASK_NAME)
        val buildDirA = generatedBuildDir(pkgA)
        val srcDirB = generatedSrcDir(pkgB)
        val iconABefore = buildDirA.resolve("IconA.kt").readText()
        val iconBBefore = srcDirB.resolve("IconB.kt").readText()

        // Act
        writeSvg("icons-a", "icon-a.svg", SIMPLE_SVG_A_MODIFIED)
        writeSvg("icons-b", "icon-b.svg", SIMPLE_SVG_A) // change B to something different
        val secondResult = runGradle(TASK_NAME)

        // Assert
        assertTaskOutcome(secondResult, TaskOutcome.SUCCESS)
        val iconAAfter = buildDirA.resolve("IconA.kt").readText()
        val iconBAfter = srcDirB.resolve("IconB.kt").readText()
        assertTrue(iconABefore != iconAAfter, "IconA.kt should change after modification")
        assertTrue(iconBBefore != iconBAfter, "IconB.kt should change after modification")
    }

    @Test
    fun `given mixed config, when optimize changed on config A, then rebuild occurs`() {
        val pkgA = "dev.tonholo.s2c.test.multi.cfgchange.a"
        val pkgB = "dev.tonholo.s2c.test.multi.cfgchange.b"

        // Arrange
        setupMultiConfigBuildGradle(
            pkgA,
            pkgB,
            optimizeA = false,
            optimizeB = false,
            persistA = false,
            persistB = true,
        )
        writeSvg("icons-a", "icon-a.svg", SIMPLE_SVG_A)
        writeSvg("icons-b", "icon-b.svg", SIMPLE_SVG_B)
        runGradle(TASK_NAME)

        // Act
        setupMultiConfigBuildGradle(
            pkgA,
            pkgB,
            optimizeA = true,
            optimizeB = false,
            persistA = false,
            persistB = true,
        )
        val secondResult = runGradle(TASK_NAME)

        // Assert
        assertTaskOutcome(secondResult, TaskOutcome.SUCCESS)
    }

    // endregion [ Multi-Configuration Tests ]
}
