import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.InternalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithHostTests
import org.jetbrains.kotlin.gradle.targets.jvm.tasks.KotlinJvmRun
import org.jetbrains.kotlin.gradle.tasks.KotlinTest

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.io.gitlab.arturbosch.detekt)
    id(libs.plugins.com.codingfeline.buildkonfig.get().pluginId)
    alias(libs.plugins.org.jetbrains.kotlin.plugin.powerAssert)
}

val rootBuildDir = File(project.rootDir.absolutePath, "build")

kotlin {
    fun createReleaseTask(name: String, targets: List<KotlinNativeTargetWithHostTests>) {
        task("release$name") {
            dependsOn(targets.map { target ->
                ":svg-to-compose:linkReleaseExecutable${target.name.replaceFirstChar { it.uppercaseChar() }}"
            })
            description = "Build release binaries for all $name targets"
            group = "release"
        }
    }

    val macosTargets = listOf(
        macosArm64(),
        macosX64(),
    ).also { targets -> createReleaseTask("MacOS", targets) }

    val linuxTargets = listOf(
        linuxX64(),
    ).also { targets -> createReleaseTask("Linux", targets) }

    val windowsTargets = listOf(
        mingwX64(),
    ).also { targets -> createReleaseTask("Windows", targets) }

    (macosTargets + linuxTargets + windowsTargets).forEach { target ->
        target.binaries {
            executable {
                entryPoint = "main"
                baseName = "s2c"

                debuggable = true
                runTask?.let {
                    val args = providers.gradleProperty("runArgs")
                    it.argumentProviders.add(CommandLineArgumentProvider {
                        args.orNull?.split(" ")
                    })
                }
            }
        }

        val targetName = target.name.replaceFirstChar { it.uppercaseChar() }
        task("buildDebug$targetName") {
            dependsOn(
                ":svg-to-compose:compileKotlin$targetName",
                ":svg-to-compose:linkDebugExecutable$targetName"
            )
            group = "build"
            description = "Build debug binary for ${target.name}"
        }

        task("buildRelease$targetName") {
            dependsOn(
                ":svg-to-compose:compileKotlin$targetName",
                ":svg-to-compose:linkReleaseExecutable$targetName"
            )
            group = "build"
            description = "Build release binary for ${target.name}"
        }
    }

    // Enabled only for debugging purposes.
    // Not meant to be a target.
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.com.squareup.okio)
            implementation(libs.com.fleeksoft.ksoup)
            implementation(kotlin("reflect"))
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        nativeMain.dependencies {
            implementation(libs.clikt)
        }
    }
}

buildkonfig {
    packageName = "dev.tonholo.s2c"
    defaultConfigs {
        val envFile = File("${rootDir.absolutePath}/app.properties")
        val env = mutableListOf<Pair<String, String>>()
        envFile.forEachLine { line ->
            val (name, value) = line.split("=")
            env += name to value
        }
        env.forEach { (name, value) ->
            buildConfigField(STRING, name, value)
        }
    }
}

detekt {
    autoCorrect = true
    buildUponDefaultConfig = true // preconfigure defaults
    allRules = false // activate all available (even unstable) rules.
    // point to your custom config defining rules to run, overwriting default behavior
    config.setFrom("${rootProject.rootDir}/config/detekt.yml")
}

@OptIn(ExperimentalKotlinGradlePluginApi::class)
powerAssert {
    functions = listOf("kotlin.assert", "kotlin.test.assertTrue", "kotlin.test.assertEquals", "kotlin.test.assertNull")
}

dependencies {
    detektPlugins(libs.detekt.formatting)
    commonTestImplementation(kotlin("test"))
    commonTestImplementation(kotlin("stdlib"))
}

tasks.withType<Detekt>().configureEach {
    reports {
        html.required.set(true) // observe findings in your browser with structure and code snippets
        xml.required.set(true) // checkstyle like format mainly for integrations like Jenkins
        txt.required.set(true) // similar to the console output, contains issue signature to manually edit baseline files
        sarif.required.set(true) // standardized SARIF format (https://sarifweb.azurewebsites.net/) to support integrations with GitHub Code Scanning
        md.required.set(true) // simple Markdown format
    }
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = JavaVersion.VERSION_1_8.toString()
    exclude {
        with(it.file.absolutePath) {
            contains("build") ||
                contains("sampleApp") ||
                contains("jvm") // JVM is not officially supported. It is only added to enable debugging.
        }
    }
}
tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = JavaVersion.VERSION_1_8.toString()
}

tasks.withType<KotlinTest>().configureEach {
    testLogging {
        events(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
        exceptionFormat = TestExceptionFormat.FULL
        showExceptions = true
        showCauses = true
        showStackTraces = false
        showStandardStreams = true
    }
}

@OptIn(InternalKotlinGradlePluginApi::class)
tasks.withType<KotlinJvmRun> {
    isIgnoreExitValue = true
}
