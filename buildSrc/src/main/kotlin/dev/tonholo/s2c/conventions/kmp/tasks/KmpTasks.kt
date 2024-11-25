package dev.tonholo.s2c.conventions.kmp.tasks

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithHostTests

internal fun Project.createBuildTasks(target: KotlinNativeTargetWithHostTests) {
    val projectName = name
    val targetName = target.name.replaceFirstChar { it.uppercaseChar() }
    val taskName = "buildDebug$targetName"
    if (tasks.findByName(taskName) != null) {
        println("task '$taskName' already registered for $targetName inside ${this@createBuildTasks.name}")
    }
    task(taskName) {
        dependsOn(
            ":$projectName:compileKotlin$targetName",
            ":$projectName:linkDebugExecutable$targetName"
        )
        group = "build"
        description = "Build debug binary for ${target.targetName}"
    }

    task("buildRelease$targetName") {
        dependsOn(
            ":$projectName:compileKotlin$targetName",
            ":$projectName:linkReleaseExecutable$targetName"
        )
        group = "build"
        description = "Build release binary for ${target.targetName}"
    }
}

internal fun Project.createReleaseTask(name: String, targets: List<KotlinNativeTargetWithHostTests>) {
    task("release$name") {
        dependsOn(
            targets.map { target ->
                ":${project.name}:linkReleaseExecutable${target.name.replaceFirstChar { it.uppercaseChar() }}"
            },
        )
        description = "Build release binaries for all $name targets"
        group = "release"
    }
}
