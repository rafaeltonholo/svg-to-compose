package dev.tonholo.s2c.gradle.common

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import java.io.File
import kotlin.io.path.createTempDirectory
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * Abstract base class for Gradle plugin functional tests that provides common infrastructure
 * for testing Gradle builds in isolation.
 *
 * This class manages the lifecycle of temporary project directories, provides utilities for
 * running Gradle builds, and includes helper methods for verifying build outcomes and managing
 * test resources. Each test creates a fresh temporary project directory with a basic
 * settings.gradle.kts configuration.
 *
 * Subclasses must implement the `tempDirPrefix` property to specify a unique prefix for
 * their temporary test directories.
 *
 * The test lifecycle:
 * - Before each test: Creates a temporary project directory with a basic Gradle configuration
 * - After each test: Cleans up the temporary directory
 *
 * @property repoRoot The root directory of the repository, derived from the current working directory's parent
 * @property projectDir The temporary project directory created for each test. This is initialized
 *           in the setUp method and should not be accessed before that point.
 */
abstract class GradleFunctionalTest {
    protected val repoRoot: File = File(System.getProperty("user.dir")).parentFile

    protected lateinit var projectDir: File
        private set

    /**
     * Unique prefix for temporary test directories. Each subclass must provide its own
     * value to avoid collisions between parallel test runs.
     */
    protected abstract val tempDirPrefix: String

    @BeforeEach
    fun setUp() {
        projectDir = createTempDirectory(tempDirPrefix).toFile()
        projectDir.resolve("settings.gradle.kts").writeText(
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
    }

    @AfterEach
    fun tearDown() {
        if (::projectDir.isInitialized) {
            projectDir.deleteRecursively()
        }
    }

    /**
     * Runs a Gradle build in the [projectDir] with the plugin classpath pre-configured.
     *
     * @param args Gradle arguments (task names, flags, etc.).
     * @param info When `true`, adds `--info` for verbose logging (useful for verifying
     *             incremental behavior via task log output).
     * @return The [BuildResult] containing task outcomes and build output.
     */
    protected fun runGradle(vararg args: String, info: Boolean = false): BuildResult =
        GradleRunner.create()
            .withProjectDir(projectDir)
            .withPluginClasspath()
            .withArguments(
                buildList {
                    addAll(args)
                    add("--stacktrace")
                    if (info) add("--info")
                },
            )
            .forwardOutput()
            .build()

    /**
     * Asserts that a task in the build result has the [expected] outcome.
     *
     * @param result The build result to inspect.
     * @param taskPath The fully qualified task path (e.g. `:parseSvgToComposeIcon`).
     * @param expected The expected [TaskOutcome].
     */
    protected fun assertTaskOutcome(
        result: BuildResult,
        taskPath: String,
        expected: TaskOutcome,
    ) {
        val task = result.task(taskPath)
        assertNotNull(task, "Task $taskPath was not found in the build")
        assertEquals(
            expected = expected,
            actual = task.outcome,
            message = "Task $taskPath expected $expected but was ${task.outcome}",
        )
    }

    /**
     * Returns the path to the generated output directory under `build/` for a KMP commonMain source set.
     *
     * @param pkg The destination package (e.g. `dev.tonholo.s2c.test.icons`).
     */
    protected fun generatedBuildDir(pkg: String): File {
        val pkgPath = pkg.replace('.', '/')
        return projectDir.resolve("build/generated/svgToCompose/commonMain/kotlin/$pkgPath")
    }

    /**
     * Returns the path to the persistent output directory under `src/commonMain/kotlin/`
     * for a KMP project with `isCodeGenerationPersistent = true`.
     *
     * @param pkg The destination package (e.g. `dev.tonholo.s2c.test.icons`).
     */
    protected fun generatedSrcDir(pkg: String): File {
        val pkgPath = pkg.replace('.', '/')
        return projectDir.resolve("src/commonMain/kotlin/$pkgPath")
    }

    /**
     * Writes an SVG (or XML) file into the test project.
     *
     * @param dirName Directory name relative to [projectDir] (e.g. `"icons"`). Created if absent.
     * @param fileName The file name (e.g. `"icon-a.svg"`).
     * @param content The SVG/XML content to write.
     */
    protected fun writeSvg(
        dirName: String,
        fileName: String,
        content: String,
    ) {
        val iconsDir = projectDir.resolve(dirName)
        iconsDir.mkdirs()
        iconsDir.resolve(fileName).writeText(content)
    }

    /**
     * Copies test resources from the classpath into the test project.
     *
     * @param dirName Target directory name relative to [projectDir] (e.g. `"icons"`). Created if absent.
     * @param resourceType Classpath resource folder name (e.g. `"svg"`, `"avg"`).
     * @throws IllegalArgumentException If the resource directory is not found on the classpath.
     */
    protected fun copyResourcesToProject(dirName: String, resourceType: String) {
        val targetDir = projectDir.resolve(dirName)
        targetDir.mkdirs()
        val resourceDir = File(
            requireNotNull(javaClass.classLoader.getResource(resourceType)) {
                "Test resource directory not found: $resourceType"
            }.toURI(),
        )
        resourceDir.listFiles().orEmpty().forEach { file ->
            file.copyTo(targetDir.resolve(file.name))
        }
    }
}
