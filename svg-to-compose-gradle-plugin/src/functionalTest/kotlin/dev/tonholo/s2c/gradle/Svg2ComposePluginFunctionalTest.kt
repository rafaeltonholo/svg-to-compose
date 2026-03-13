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

    // language=kotlin
    private fun buildGradleContent(optimize: Boolean, pkg: String) =
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

    // language=kotlin
    private fun buildTemplateGradleContent(pkg: String) =
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
                    icons {
                        noPreview()
                        templateFile(layout.projectDirectory.file("s2c.template.toml"))
                    }
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
            }.toURI(),
        )
        resourceDir.listFiles().orEmpty().forEach { file ->
            file.copyTo(iconsDir.resolve(file.name))
        }
    }

    private val templateSvgFiles = listOf(
        "samples/svg/attention-filled.svg",
        "samples/svg/android.svg",
        "samples/svg/brasil.svg",
        "samples/svg/gradient/linear-gradient01.svg",
        "samples/svg/mask/mask-with-group.svg",
    )

    private val templateAvgFiles = listOf(
        "samples/avg/shield-halved-solid.xml",
        "samples/avg/android.xml",
        "samples/avg/gradient/stroke_gradient.xml",
    )

    private fun copyTemplateIconsToProject(projectDir: File, type: String) {
        val iconsDir = projectDir.resolve("icons")
        iconsDir.mkdirs()
        val files = if (type == "svg") templateSvgFiles else templateAvgFiles
        for (relativePath in files) {
            val source = projectRoot.resolve(relativePath)
            source.copyTo(iconsDir.resolve(source.name))
        }
    }

    private fun copyTemplateConfigToProject(projectDir: File) {
        val templateSource = projectRoot.resolve("playground/s2c.template.toml")
        templateSource.copyTo(projectDir.resolve("s2c.template.toml"))
    }

    private fun runGradle(projectDir: File, vararg args: String): BuildResult = GradleRunner.create()
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

    private fun assertTemplateOutputMatchesExpected(projectDir: File, pkg: String, fileType: String) {
        val templateExpectedDir = expectedDir.resolve("template")
        val pkgPath = pkg.replace('.', '/')
        val generatedDir = projectDir.resolve("build/generated/svgToCompose/commonMain/kotlin/$pkgPath")
        assertTrue(generatedDir.exists(), "Generated directory does not exist: $generatedDir")

        val generatedFiles = generatedDir.walkTopDown()
            .filter { it.isFile && it.extension == "kt" }
            .sortedBy { it.name }
            .toList()
        assertTrue(generatedFiles.isNotEmpty(), "No generated .kt files found in $generatedDir")

        for (generatedFile in generatedFiles) {
            val expectedFileName = "${generatedFile.nameWithoutExtension}.$fileType.template.kt"
            val expectedFile = templateExpectedDir.resolve(expectedFileName)

            if (!expectedFile.exists()) {
                templateExpectedDir.mkdirs()
                generatedFile.copyTo(expectedFile, overwrite = true)
                println("BOOTSTRAP: Wrote expected file template/$expectedFileName")
                continue
            }

            assertEquals(
                expectedFile.readText(),
                generatedFile.readText(),
                "Generated file content does not match expected for template/$expectedFileName",
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

    // --- Template tests ---

    @Test
    fun `svg with template matches expected output`() {
        val pkg = "dev.tonholo.s2c.integrity.icon.svg"
        val projectDir = createTempProject()
        try {
            projectDir.resolve("build.gradle.kts").writeText(buildTemplateGradleContent(pkg))
            copyTemplateIconsToProject(projectDir, "svg")
            copyTemplateConfigToProject(projectDir)

            val result = runGradle(projectDir, "parseSvgToComposeIcon")
            assertTaskSuccess(result, "parseSvgToComposeIcon")
            assertTemplateOutputMatchesExpected(projectDir, pkg, fileType = "svg")
        } finally {
            projectDir.deleteRecursively()
        }
    }

    @Test
    fun `avg with template matches expected output`() {
        val pkg = "dev.tonholo.s2c.integrity.icon.avg"
        val projectDir = createTempProject()
        try {
            projectDir.resolve("build.gradle.kts").writeText(buildTemplateGradleContent(pkg))
            copyTemplateIconsToProject(projectDir, "avg")
            copyTemplateConfigToProject(projectDir)

            val result = runGradle(projectDir, "parseSvgToComposeIcon")
            assertTaskSuccess(result, "parseSvgToComposeIcon")
            assertTemplateOutputMatchesExpected(projectDir, pkg, fileType = "xml")
        } finally {
            projectDir.deleteRecursively()
        }
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
