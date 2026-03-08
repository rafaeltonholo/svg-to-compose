package dev.tonholo.s2c.gradle

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import java.io.File
import java.util.Properties
import kotlin.io.path.createTempDirectory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Integration tests that verify the s2c plugin correctly registers generated
 * source directories with the Android source set across AGP versions.
 *
 * The plugin is compiled against AGP 8's `CommonExtension<*,*,*,*,*,*>` (6 type
 * params). AGP 9 changed `CommonExtension` to have 0 type params. These tests
 * prove the `findByName("android") as? CommonExtension<…>` cast works at
 * runtime with both versions thanks to JVM type erasure.
 *
 * We avoid [GradleRunner.withPluginClasspath] here because it injects the
 * plugin classpath **without** AGP (which is `compileOnly`). Instead we inject
 * both our plugin JARs and the test-specific AGP version into the build
 * script's `buildscript { classpath(...) }` block so they share a single
 * classloader — exactly as they would in a real user project.
 *
 * Both Groovy (`build.gradle`) and Kotlin DSL (`build.gradle.kts`) build
 * scripts are tested to ensure compatibility with either approach.
 */
class AndroidSourceSetRegistrationTest {
    private data class AgpTestConfig(
        val agpVersion: String,
        val compileSdk: Int,
    )

    private enum class BuildScriptDsl {
        GROOVY,
        KOTLIN_DSL,
    }

    companion object {
        private val AGP_8 = AgpTestConfig(agpVersion = "8.13.2", compileSdk = 35)
        private val AGP_9 = AgpTestConfig(agpVersion = "9.1.0", compileSdk = 36)

        private val androidHome: String? =
            System.getenv("ANDROID_HOME") ?: System.getenv("ANDROID_SDK_ROOT")

        /** Classpath entries for the plugin-under-test (excludes AGP). */
        private val pluginClasspath: List<File> by lazy {
            val resource = AndroidSourceSetRegistrationTest::class.java.classLoader
                .getResource("plugin-under-test-metadata.properties")
                ?: error("plugin-under-test-metadata.properties not found on test classpath")
            val props = Properties().apply { resource.openStream().use(::load) }
            props.getProperty("implementation-classpath")
                .split(File.pathSeparator)
                .map(::File)
        }
    }

    // -------------------------------------------------------------------------
    // Groovy DSL — AGP 8.x
    // -------------------------------------------------------------------------

    @Test
    fun `groovy - plugin applies and registers source set with AGP 8`() {
        runSourceSetRegistrationTest(AGP_8, BuildScriptDsl.GROOVY)
    }

    @Test
    fun `groovy - parseSvgToComposeIcon generates icons with AGP 8`() {
        runIconGenerationTest(AGP_8, BuildScriptDsl.GROOVY)
    }

    // -------------------------------------------------------------------------
    // Groovy DSL — AGP 9.x
    // -------------------------------------------------------------------------

    @Test
    fun `groovy - plugin applies and registers source set with AGP 9`() {
        runSourceSetRegistrationTest(AGP_9, BuildScriptDsl.GROOVY)
    }

    @Test
    fun `groovy - parseSvgToComposeIcon generates icons with AGP 9`() {
        runIconGenerationTest(AGP_9, BuildScriptDsl.GROOVY)
    }

    // -------------------------------------------------------------------------
    // Kotlin DSL — AGP 8.x
    // -------------------------------------------------------------------------

    @Test
    fun `kotlin dsl - plugin applies and registers source set with AGP 8`() {
        runSourceSetRegistrationTest(AGP_8, BuildScriptDsl.KOTLIN_DSL)
    }

    @Test
    fun `kotlin dsl - parseSvgToComposeIcon generates icons with AGP 8`() {
        runIconGenerationTest(AGP_8, BuildScriptDsl.KOTLIN_DSL)
    }

    // -------------------------------------------------------------------------
    // Kotlin DSL — AGP 9.x
    // -------------------------------------------------------------------------

    @Test
    fun `kotlin dsl - plugin applies and registers source set with AGP 9`() {
        runSourceSetRegistrationTest(AGP_9, BuildScriptDsl.KOTLIN_DSL)
    }

    @Test
    fun `kotlin dsl - parseSvgToComposeIcon generates icons with AGP 9`() {
        runIconGenerationTest(AGP_9, BuildScriptDsl.KOTLIN_DSL)
    }

    // -------------------------------------------------------------------------
    // Test implementations
    // -------------------------------------------------------------------------

    /**
     * Verifies that the generated source directory is present in the Android
     * "main" source set's Kotlin directories. If the `CommonExtension` cast
     * fails at runtime, the plugin application itself will throw, failing this
     * test.
     */
    private fun runSourceSetRegistrationTest(config: AgpTestConfig, dsl: BuildScriptDsl) {
        skipIfNoAndroidSdk()
        val projectDir = createAndroidProject(config, dsl)
        try {
            val result = gradle(projectDir, "verifySourceSetRegistration")

            val verifyTask = result.task(":verifySourceSetRegistration")
            assertNotNull(verifyTask, "Task :verifySourceSetRegistration was not found in the build")
            assertEquals(TaskOutcome.SUCCESS, verifyTask.outcome)
            assertTrue(
                result.output.contains("SOURCE_SET_VERIFIED"),
                "Expected source set verification output",
            )
        } finally {
            projectDir.deleteRecursively()
        }
    }

    /**
     * Verifies that `parseSvgToComposeIcon` runs successfully and produces
     * generated Kotlin files in the expected output directory.
     */
    private fun runIconGenerationTest(config: AgpTestConfig, dsl: BuildScriptDsl) {
        skipIfNoAndroidSdk()
        val projectDir = createAndroidProject(config, dsl)
        try {
            val result = gradle(projectDir, "parseSvgToComposeIcon")

            val task = result.task(":parseSvgToComposeIcon")
            assertNotNull(task, "Task :parseSvgToComposeIcon was not found in the build")
            assertEquals(TaskOutcome.SUCCESS, task.outcome)

            val generatedDir = projectDir.resolve("build/generated/svgToCompose/main/kotlin")
            assertTrue(generatedDir.exists(), "Generated directory does not exist: $generatedDir")

            val generatedFiles = generatedDir.walkTopDown()
                .filter { it.isFile && it.extension == "kt" }
                .toList()
            assertTrue(
                generatedFiles.isNotEmpty(),
                "No generated .kt files found in $generatedDir",
            )
        } finally {
            projectDir.deleteRecursively()
        }
    }

    // -------------------------------------------------------------------------
    // Gradle runner
    // -------------------------------------------------------------------------

    private fun gradle(projectDir: File, vararg tasks: String) =
        GradleRunner.create()
            .withProjectDir(projectDir)
            // Do NOT use withPluginClasspath() — see class KDoc.
            .withArguments(*tasks, "--stacktrace")
            .forwardOutput()
            .build()

    // -------------------------------------------------------------------------
    // Project scaffolding
    // -------------------------------------------------------------------------

    private fun createAndroidProject(config: AgpTestConfig, dsl: BuildScriptDsl): File {
        val dir = createTempDirectory("s2c-android-test").toFile()

        dir.resolve("local.properties").writeText("sdk.dir=$androidHome\n")

        dir.resolve("settings.gradle.kts").writeText(
            // language=kotlin
            """
            pluginManagement {
                repositories {
                    google()
                    mavenCentral()
                    gradlePluginPortal()
                }
            }
            rootProject.name = "s2c-android-test"
            """.trimIndent()
        )

        val (fileName, content) = when (dsl) {
            BuildScriptDsl.GROOVY -> "build.gradle" to groovyBuildScript(config)
            BuildScriptDsl.KOTLIN_DSL -> "build.gradle.kts" to kotlinDslBuildScript(config)
        }
        dir.resolve(fileName).writeText(content)

        // Minimal AndroidManifest (required for com.android.library)
        val manifestDir = dir.resolve("src/main")
        manifestDir.mkdirs()
        manifestDir.resolve("AndroidManifest.xml").writeText("""<manifest />""")

        // Copy test SVG icons into the project
        val iconsDir = dir.resolve("icons")
        iconsDir.mkdirs()
        val resourceDir = File(
            requireNotNull(javaClass.classLoader.getResource("svg")) {
                "Test resource directory not found: svg"
            }.toURI()
        )
        resourceDir.listFiles().orEmpty()
            .filter { it.isFile }
            .take(2) // only need a couple to prove it works
            .forEach { file -> file.copyTo(iconsDir.resolve(file.name)) }

        return dir
    }

    // -------------------------------------------------------------------------
    // Build script content — Groovy
    // -------------------------------------------------------------------------

    private fun groovyBuildScript(config: AgpTestConfig): String {
        val classpathFiles = pluginClasspath.joinToString(",\n                    ") { entry ->
            "'${entry.absolutePath.replace("\\", "\\\\").replace("'", "\\'")}'"
        }

        // language=groovy
        return """
            buildscript {
                repositories {
                    google()
                    mavenCentral()
                    gradlePluginPortal()
                }
                dependencies {
                    classpath 'com.android.tools.build:gradle:${config.agpVersion}'
                    classpath files(
                        $classpathFiles
                    )
                }
            }

            apply plugin: 'com.android.library'
            apply plugin: 'dev.tonholo.s2c'

            android {
                namespace = 'dev.tonholo.s2c.test'
                compileSdk = ${config.compileSdk}
                defaultConfig {
                    minSdk = 21
                }
            }

            svgToCompose {
                processor {
                    common {
                        optimize(false)
                        icons {
                            noPreview()
                        }
                    }
                    icons {
                        from layout.projectDirectory.dir('icons')
                        destinationPackage 'dev.tonholo.s2c.test.icons'
                    }
                }
            }

            tasks.register('verifySourceSetRegistration') {
                dependsOn 'parseSvgToComposeIcon'
                doLast {
                    def mainSourceSet = android.sourceSets.getByName('main')
                    def kotlinDirs = mainSourceSet.kotlin.srcDirs
                    def generatedDir = layout.buildDirectory
                        .dir('generated/svgToCompose/main/kotlin')
                        .get().asFile
                    def found = kotlinDirs.any { it.absolutePath == generatedDir.absolutePath }
                    if (!found) {
                        throw new GradleException(
                            "Generated source directory not found in main source set. " +
                            "Expected: " + generatedDir.absolutePath +
                            " Actual: " + kotlinDirs.collect { it.absolutePath }
                        )
                    }
                    println "SOURCE_SET_VERIFIED: " + generatedDir.absolutePath
                }
            }
        """.trimIndent()
    }

    // -------------------------------------------------------------------------
    // Build script content — Kotlin DSL
    // -------------------------------------------------------------------------

    /**
     * Kotlin DSL build script using `buildscript` + `apply(plugin = ...)`.
     *
     * Type-safe accessors (e.g., `android { }`) are NOT generated for plugins
     * applied via `apply(plugin = ...)`. We use [configure] with explicit FQCNs
     * instead. This mirrors what a user would do when mixing buildscript-based
     * plugin application with Kotlin DSL.
     *
     * [com.android.build.api.dsl.LibraryExtension] has no type parameters in
     * either AGP 8 or 9, so the cast is safe without erasure concerns.
     */
    private fun kotlinDslBuildScript(config: AgpTestConfig): String {
        val classpathFiles = pluginClasspath.joinToString(",\n                    ") { entry ->
            "\"${entry.absolutePath.replace("\\", "\\\\")}\""
        }

        // language=kotlin
        return """
            buildscript {
                repositories {
                    google()
                    mavenCentral()
                    gradlePluginPortal()
                }
                dependencies {
                    classpath("com.android.tools.build:gradle:${config.agpVersion}")
                    classpath(files(
                        $classpathFiles
                    ))
                }
            }

            apply(plugin = "com.android.library")
            apply(plugin = "dev.tonholo.s2c")

            configure<com.android.build.api.dsl.LibraryExtension> {
                namespace = "dev.tonholo.s2c.test"
                compileSdk = ${config.compileSdk}
                defaultConfig {
                    minSdk = 21
                }
            }

            configure<dev.tonholo.s2c.gradle.dsl.SvgToComposeExtension> {
                processor {
                    common {
                        optimize(false)
                        icons {
                            noPreview()
                        }
                    }
                    val icons by creating {
                        from(layout.projectDirectory.dir("icons"))
                        destinationPackage("dev.tonholo.s2c.test.icons")
                    }
                }
            }

            tasks.register("verifySourceSetRegistration") {
                dependsOn("parseSvgToComposeIcon")
                doLast {
                    val generatedDir = layout.buildDirectory
                        .dir("generated/svgToCompose/main/kotlin")
                        .get().asFile
                    require(generatedDir.exists()) {
                        "Generated source directory does not exist: ${'$'}{generatedDir.absolutePath}"
                    }
                    val generatedFiles = generatedDir.walkTopDown()
                        .filter { it.isFile && it.extension == "kt" }
                        .toList()
                    require(generatedFiles.isNotEmpty()) {
                        "No generated .kt files found in ${'$'}{generatedDir.absolutePath}"
                    }
                    println("SOURCE_SET_VERIFIED: ${'$'}{generatedDir.absolutePath}")
                }
            }
        """.trimIndent()
    }

    private fun skipIfNoAndroidSdk() {
        org.junit.Assume.assumeTrue(
            "Skipping: ANDROID_HOME / ANDROID_SDK_ROOT not set",
            !androidHome.isNullOrBlank(),
        )
    }
}
