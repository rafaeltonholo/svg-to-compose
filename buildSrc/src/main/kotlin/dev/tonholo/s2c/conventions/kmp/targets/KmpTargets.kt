package dev.tonholo.s2c.conventions.kmp.targets

import dev.tonholo.s2c.conventions.kmp.tasks.createBuildTasks
import dev.tonholo.s2c.conventions.kmp.tasks.createReleaseTask
import org.gradle.api.Project
import org.gradle.process.CommandLineArgumentProvider
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithHostTests

context(Project)
fun KotlinMultiplatformExtension.useNative() {
    val macosTargets = useMacOs()
    val linuxTargets = useLinux()
    val windowsTargets = useWindows()

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
        createBuildTasks(target)
    }
}

context(Project)
fun KotlinMultiplatformExtension.useMacOs(): List<KotlinNativeTargetWithHostTests> =
    listOf(
        macosArm64(),
        macosX64(),
    ).also { targets -> createReleaseTask("MacOS", targets) }

context(Project)
fun KotlinMultiplatformExtension.useLinux(): List<KotlinNativeTargetWithHostTests> =
    listOf(
        linuxX64(),
    ).also { targets -> createReleaseTask("Linux", targets) }

context(Project)
fun KotlinMultiplatformExtension.useWindows(): List<KotlinNativeTargetWithHostTests> =
    listOf(
        mingwX64(),
    ).also { targets -> createReleaseTask("Windows", targets) }

fun KotlinMultiplatformExtension.useJvm() {
    jvm()
}
