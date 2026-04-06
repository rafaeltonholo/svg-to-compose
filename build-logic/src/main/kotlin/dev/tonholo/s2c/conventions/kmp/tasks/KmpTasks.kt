package dev.tonholo.s2c.conventions.kmp.tasks

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithHostTests

fun Project.createBuildTasks(target: KotlinNativeTargetWithHostTests) {
    val projectPath = path
    val targetName = target.name.replaceFirstChar { it.uppercaseChar() }
    val taskPrefix = if (projectPath == ":") ":" else "$projectPath:"
    val taskName = "buildDebug$targetName"
    if (tasks.findByName(taskName) != null) {
        println("task '$taskName' already registered for $targetName inside ${this@createBuildTasks.name}")
    }
    tasks.register(taskName) {
        dependsOn(
            "${taskPrefix}compileKotlin$targetName",
            "${taskPrefix}linkDebugExecutable$targetName",
        )
        group = "build"
        description = "Build debug binary for ${target.targetName}"
    }

    tasks.register("buildRelease$targetName") {
        dependsOn(
            "${taskPrefix}compileKotlin$targetName",
            "${taskPrefix}linkReleaseExecutable$targetName",
        )
        group = "build"
        description = "Build release binary for ${target.targetName}"
    }
}

fun Project.createReleaseTask(name: String, targets: List<KotlinNativeTargetWithHostTests>) {
    val projectPath = path
    val taskPrefix = if (projectPath == ":") ":" else "$projectPath:"
    tasks.register("release$name") {
        dependsOn(
            targets.map { target ->
                "${taskPrefix}linkReleaseExecutable${target.name.replaceFirstChar { it.uppercaseChar() }}"
            },
        )
        description = "Build release binaries for all $name targets"
        group = "release"
    }
}
