import dev.tonholo.s2c.conventions.kmp.targets.configureNativeExecutable

plugins {
    alias(libs.plugins.dev.tonholo.s2c.conventions.kmp)
    alias(libs.plugins.dev.tonholo.s2c.conventions.testing)
    alias(libs.plugins.com.gradleup.shadow)
    alias(libs.plugins.org.jetbrains.kotlin.serialization)
}

detekt {
    config.setFrom("${rootProject.rootDir.parentFile.parentFile}/config/detekt.yml")
}

kotlin {
    configureNativeExecutable(
        project = project,
        entryPoint = "dev.tonholo.s2c.cli.main",
        baseName = "s2c",
    )

    sourceSets {
        commonMain.dependencies {
            implementation(cliLibs.dev.tonholo.s2c.svgToCompose)
            implementation(cliLibs.com.github.ajalt.clikt)
            implementation(cliLibs.com.github.ajalt.clikt.markdown)
            implementation(libs.com.squareup.okio)
            implementation(libs.org.jetbrains.kotlinx.serialization.json)
        }
        commonTest.dependencies {
            implementation(libs.com.squareup.okio.fakefilesystem)
        }
    }
}

buildConfig {
    packageName("dev.tonholo.s2c.cli.config")
}

tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    archiveClassifier.set("all")
    manifest {
        attributes["Main-Class"] = "dev.tonholo.s2c.cli.MainKt"
    }
    minimize {
        // Mordant/Clikt use ServiceLoader for terminal interface discovery.
        exclude(dependency("com.github.ajalt.mordant:.*:.*"))
        exclude(dependency("com.github.ajalt.clikt:.*:.*"))
    }
    mergeServiceFiles()
}
