package dev.tonholo.s2c.conventions.kmp.targets

import dev.tonholo.s2c.conventions.kmp.tasks.createBuildTasks
import dev.tonholo.s2c.conventions.kmp.tasks.createReleaseTask
import org.gradle.api.Project
import org.gradle.process.CommandLineArgumentProvider
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithHostTests

fun KotlinMultiplatformExtension.useNative() {
    useMacOs()
    useLinux()
    useWindows()
}

fun KotlinMultiplatformExtension.configureNativeExecutable(
    project: Project,
    entryPoint: String = "main",
    baseName: String = "s2c",
) {
    val macosTargets = mutableListOf<KotlinNativeTargetWithHostTests>()
    val linuxTargets = mutableListOf<KotlinNativeTargetWithHostTests>()
    val windowsTargets = mutableListOf<KotlinNativeTargetWithHostTests>()

    targets.filterIsInstance<KotlinNativeTargetWithHostTests>().forEach { target ->
        target.binaries {
            executable {
                this.entryPoint = entryPoint
                this.baseName = baseName
                debuggable = true
                runTaskProvider?.configure {
                    val args = project.providers.gradleProperty("runArgs")
                    argumentProviders.add(
                        CommandLineArgumentProvider {
                            args.orNull?.split(" ")
                        },
                    )
                }
            }
        }
        project.createBuildTasks(target)

        when {
            target.name.startsWith("macos") -> macosTargets.add(target)
            target.name.startsWith("linux") -> linuxTargets.add(target)
            target.name.startsWith("mingw") -> windowsTargets.add(target)
        }
    }

    if (macosTargets.isNotEmpty()) project.createReleaseTask("MacOS", macosTargets)
    if (linuxTargets.isNotEmpty()) project.createReleaseTask("Linux", linuxTargets)
    if (windowsTargets.isNotEmpty()) project.createReleaseTask("Windows", windowsTargets)
}

fun KotlinMultiplatformExtension.useMacOs(): List<KotlinNativeTargetWithHostTests> =
    listOf(
        macosArm64(),
    )

fun KotlinMultiplatformExtension.useLinux(): List<KotlinNativeTargetWithHostTests> =
    listOf(
        linuxX64(),
    )

fun KotlinMultiplatformExtension.useWindows(): List<KotlinNativeTargetWithHostTests> =
    listOf(
        mingwX64(),
    )

fun KotlinMultiplatformExtension.useJvm() {
    jvm()
}

fun KotlinMultiplatformExtension.useJs() {
    js {
        browser {
            testTask {
                enabled = false
            }
        }
        nodejs()
    }
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
fun KotlinMultiplatformExtension.useWasmJs() {
    wasmJs {
        browser {
            testTask {
                enabled = false
            }
        }
        nodejs {
            testTask {
                // TODO(#243): re-enable once ktoml ships a wasm-compatible release
                //  (https://github.com/akuleshov7/ktoml — WASM CompileError).
                enabled = false
            }
        }
    }
}

