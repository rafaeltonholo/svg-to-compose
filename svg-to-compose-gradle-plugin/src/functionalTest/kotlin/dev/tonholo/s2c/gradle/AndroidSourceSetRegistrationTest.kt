package dev.tonholo.s2c.gradle

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Assumptions
import org.junit.jupiter.api.Test
import java.io.File
import java.util.Properties
import kotlin.io.path.createTempDirectory
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Functional tests verifying the s2c plugin correctly registers generated
 * source directories with Android builds across AGP versions.
 *
 * The plugin uses [com.android.build.api.variant.AndroidComponentsExtension.onVariants]
 * to wire generated sources via `addGeneratedSourceDirectory`. These tests verify
 * that the plugin applies correctly alongside AGP and produces generated Kotlin
 * files in the expected output directory.
 *
 * We cannot use [GradleRunner.withPluginClasspath] here because the injected
 * plugin classpath (which includes KGP as an `implementation` dep) lives in a
 * separate classloader from the repository-resolved AGP. KGP references AGP
 * classes at runtime (e.g. `BaseExtension`), causing [NoClassDefFoundError].
 * Instead, we inject both plugin JARs and AGP into the build script's
 * `buildscript { classpath }` block so they share a single classloader —
 * exactly as they would in a real user project.
 *
 * Both Groovy (`build.gradle`) and Kotlin DSL (`build.gradle.kts`) build
 * scripts are tested to ensure compatibility with either approach.
 *
 * Each test runs with `--no-daemon` to avoid Metaspace exhaustion from loading
 * multiple AGP versions in the same daemon JVM.
 */
class AndroidSourceSetRegistrationTest {
    private data class AgpTestConfig(val agpVersion: String, val compileSdk: Int) {
        /** AGP 9+ bundles Kotlin support; the separate KGP plugin must NOT be applied. */
        val needsKotlinPlugin: Boolean get() = agpVersion.startsWith("8")
    }

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
    // Source set registration tests
    // -------------------------------------------------------------------------

    @Test
    fun `groovy - plugin applies and registers source set with AGP 8`() {
        runSourceSetRegistrationTest(AGP_8, BuildScriptDsl.GROOVY)
    }

    @Test
    fun `groovy - plugin applies and registers source set with AGP 9`() {
        runSourceSetRegistrationTest(AGP_9, BuildScriptDsl.GROOVY)
    }

    @Test
    fun `kotlin dsl - plugin applies and registers source set with AGP 8`() {
        runSourceSetRegistrationTest(AGP_8, BuildScriptDsl.KOTLIN_DSL)
    }

    @Test
    fun `kotlin dsl - plugin applies and registers source set with AGP 9`() {
        runSourceSetRegistrationTest(AGP_9, BuildScriptDsl.KOTLIN_DSL)
    }

    // -------------------------------------------------------------------------
    // Icon generation tests
    // -------------------------------------------------------------------------

    @Test
    fun `groovy - parseSvgToComposeIcon generates icons with AGP 8`() {
        runIconGenerationTest(AGP_8, BuildScriptDsl.GROOVY)
    }

    @Test
    fun `groovy - parseSvgToComposeIcon generates icons with AGP 9`() {
        runIconGenerationTest(AGP_9, BuildScriptDsl.GROOVY)
    }

    @Test
    fun `kotlin dsl - parseSvgToComposeIcon generates icons with AGP 8`() {
        runIconGenerationTest(AGP_8, BuildScriptDsl.KOTLIN_DSL)
    }

    @Test
    fun `kotlin dsl - parseSvgToComposeIcon generates icons with AGP 9`() {
        runIconGenerationTest(AGP_9, BuildScriptDsl.KOTLIN_DSL)
    }

    // -------------------------------------------------------------------------
    // buildDirectory override tests
    // -------------------------------------------------------------------------

    @Test
    fun `groovy - source set tracks buildDirectory override with AGP 8`() {
        runBuildDirectoryOverrideTest(AGP_8, BuildScriptDsl.GROOVY)
    }

    @Test
    fun `groovy - source set tracks buildDirectory override with AGP 9`() {
        runBuildDirectoryOverrideTest(AGP_9, BuildScriptDsl.GROOVY)
    }

    @Test
    fun `kotlin dsl - source set tracks buildDirectory override with AGP 8`() {
        runBuildDirectoryOverrideTest(AGP_8, BuildScriptDsl.KOTLIN_DSL)
    }

    @Test
    fun `kotlin dsl - source set tracks buildDirectory override with AGP 9`() {
        runBuildDirectoryOverrideTest(AGP_9, BuildScriptDsl.KOTLIN_DSL)
    }

    // -------------------------------------------------------------------------
    // Test implementations
    // -------------------------------------------------------------------------

    /**
     * Verifies that the plugin applies and configures without errors when
     * used alongside AGP. Runs `parseSvgToComposeIcon` and checks it succeeds.
     */
    private fun runSourceSetRegistrationTest(config: AgpTestConfig, dsl: BuildScriptDsl) {
        skipIfNoAndroidSdk()
        val projectDir = scaffoldProject(config, dsl) { buildScript(config, dsl) }
        try {
            val result = gradle(projectDir, "parseSvgToComposeIcon")
            val task = result.task(":parseSvgToComposeIcon")
            assertNotNull(task, "Task :parseSvgToComposeIcon was not found in the build")
            assertEquals(TaskOutcome.SUCCESS, task.outcome)
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
        val projectDir = scaffoldProject(config, dsl) { buildScript(config, dsl) }
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

    /**
     * Verifies that when `layout.buildDirectory` is overridden after plugin
     * application, the generated sources end up in the custom build directory.
     */
    private fun runBuildDirectoryOverrideTest(config: AgpTestConfig, dsl: BuildScriptDsl) {
        skipIfNoAndroidSdk()
        val buildDirOverride = "custom-build"
        val projectDir = scaffoldProject(config, dsl) {
            buildScript(config, dsl, buildDirOverride = buildDirOverride)
        }
        try {
            val result = gradle(projectDir, "parseSvgToComposeIcon")

            val task = result.task(":parseSvgToComposeIcon")
            assertNotNull(task, "Task :parseSvgToComposeIcon was not found in the build")
            assertEquals(TaskOutcome.SUCCESS, task.outcome)

            // Verify generated files are in the custom build directory
            val customGeneratedDir =
                projectDir.resolve("$buildDirOverride/generated/svgToCompose/main/kotlin")
            assertTrue(
                customGeneratedDir.exists(),
                "Generated directory does not exist at custom build path: $customGeneratedDir",
            )

            val generatedFiles = customGeneratedDir.walkTopDown()
                .filter { it.isFile && it.extension == "kt" }
                .toList()
            assertTrue(
                generatedFiles.isNotEmpty(),
                "No generated .kt files found in $customGeneratedDir",
            )

            // Guard: no generated files should be in the default build directory
            val defaultGeneratedDir = projectDir.resolve("build/generated/svgToCompose")
            assertTrue(
                !defaultGeneratedDir.exists() ||
                    defaultGeneratedDir.walkTopDown().filter { it.isFile }.none(),
                "Generated files found in default build dir — buildDirectory override was not respected",
            )
        } finally {
            projectDir.deleteRecursively()
        }
    }

    // -------------------------------------------------------------------------
    // Gradle runner
    // -------------------------------------------------------------------------

    private fun gradle(projectDir: File, vararg tasks: String) = GradleRunner.create()
        .withProjectDir(projectDir)
        // Do NOT use withPluginClasspath() — see class KDoc.
        .withArguments(*tasks, "--stacktrace")
        .forwardOutput()
        .build()

    // -------------------------------------------------------------------------
    // Project scaffolding
    // -------------------------------------------------------------------------

    private fun scaffoldProject(config: AgpTestConfig, dsl: BuildScriptDsl, buildScriptProvider: () -> String): File {
        val dir = createTempDirectory("s2c-android-test").toFile()

        dir.resolve("local.properties").writeText("sdk.dir=$androidHome\n")

        // Increase Metaspace for the Gradle daemon that TestKit reuses across tests.
        // Each test loads AGP classes, which accumulate in the daemon's Metaspace.
        dir.resolve("gradle.properties").writeText("org.gradle.jvmargs=-XX:MaxMetaspaceSize=2g\n")

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
            dependencyResolutionManagement {
                repositories {
                    google()
                    mavenCentral()
                }
            }
            rootProject.name = "s2c-android-test"
            """.trimIndent(),
        )

        val fileName = when (dsl) {
            BuildScriptDsl.GROOVY -> "build.gradle"
            BuildScriptDsl.KOTLIN_DSL -> "build.gradle.kts"
        }
        dir.resolve(fileName).writeText(buildScriptProvider())

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
            }.toURI(),
        )
        resourceDir.listFiles().orEmpty()
            .filter { it.isFile }
            .take(2) // only need a couple to prove it works
            .forEach { file -> file.copyTo(iconsDir.resolve(file.name)) }

        return dir
    }

    // -------------------------------------------------------------------------
    // Build script generation
    // -------------------------------------------------------------------------

    /**
     * Generates a complete build script. Uses `buildscript { classpath }` to
     * inject both AGP and the plugin-under-test into the same classloader.
     */
    private fun buildScript(config: AgpTestConfig, dsl: BuildScriptDsl, buildDirOverride: String? = null): String =
        when (dsl) {
            BuildScriptDsl.GROOVY -> groovyBuildScript(config, buildDirOverride)
            BuildScriptDsl.KOTLIN_DSL -> kotlinDslBuildScript(config, buildDirOverride)
        }

    // -------------------------------------------------------------------------
    // Groovy build script
    // -------------------------------------------------------------------------

    private fun groovyBuildScript(config: AgpTestConfig, buildDirOverride: String?): String {
        val classpathFiles = pluginClasspath.joinToString(",\n                    ") { entry ->
            "'${entry.absolutePath.replace("\\", "\\\\").replace("'", "\\'")}'"
        }

        val buildDirLine = buildDirOverride?.let {
            "\nlayout.buildDirectory = layout.projectDirectory.dir('$it')\n"
        }.orEmpty()

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

            apply plugin: 'com.android.library'${if (config.needsKotlinPlugin) "\n            apply plugin: 'org.jetbrains.kotlin.android'" else ""}
            apply plugin: 'dev.tonholo.s2c'

            android {
                namespace = 'dev.tonholo.s2c.test'
                compileSdk = ${config.compileSdk}
                defaultConfig {
                    minSdk = 21
                }
                compileOptions {
                    sourceCompatibility JavaVersion.VERSION_17
                    targetCompatibility JavaVersion.VERSION_17
                }
            }${if (config.needsKotlinPlugin) {
            """

            tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile).configureEach {
                compilerOptions {
                    jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
                }
            }"""
        } else {
            ""
        }}

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
            $buildDirLine
        """.trimIndent()
    }

    // -------------------------------------------------------------------------
    // Kotlin DSL build script
    // -------------------------------------------------------------------------

    /**
     * Kotlin DSL build script using `buildscript` + `apply(plugin = ...)`.
     *
     * Type-safe accessors (e.g., `android { }`) are NOT generated for plugins
     * applied via `apply(plugin = ...)`. We use [configure] with explicit FQCNs
     * instead.
     */
    private fun kotlinDslBuildScript(config: AgpTestConfig, buildDirOverride: String?): String {
        val classpathFiles = pluginClasspath.joinToString(",\n                    ") { entry ->
            "\"${entry.absolutePath.replace("\\", "\\\\")}\""
        }

        val buildDirLine = buildDirOverride?.let {
            "\nlayout.buildDirectory.set(layout.projectDirectory.dir(\"$it\"))\n"
        }.orEmpty()

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

            apply(plugin = "com.android.library")${if (config.needsKotlinPlugin) "\n            apply(plugin = \"org.jetbrains.kotlin.android\")" else ""}
            apply(plugin = "dev.tonholo.s2c")

            configure<com.android.build.api.dsl.LibraryExtension> {
                namespace = "dev.tonholo.s2c.test"
                compileSdk = ${config.compileSdk}
                defaultConfig {
                    minSdk = 21
                }
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }
            }${if (config.needsKotlinPlugin) {
            """

            tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
                compilerOptions {
                    jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
                }
            }"""
        } else {
            ""
        }}

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
            $buildDirLine
        """.trimIndent()
    }

    private fun skipIfNoAndroidSdk() {
        Assumptions.assumeFalse(
            androidHome.isNullOrBlank(),
            "Skipping: ANDROID_HOME / ANDROID_SDK_ROOT not set",
        )
    }
}
