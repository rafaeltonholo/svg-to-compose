plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.io.gitlab.arturbosch.detekt)
}

val rootBuildDir = File(project.rootDir.absolutePath, "build")

kotlin {
    listOf(
        macosArm64(),
        macosX64(),
        linuxX64(),
        mingwX64(),
    ).also { targets ->
        task("buildRelease") {
            dependsOn(targets.map { target ->
                ":shared:linkReleaseExecutable${target.name.replaceFirstChar { it.uppercaseChar() }}"
            })
            description = "Build release binaries for all targets"
            group = "build"
        }
    }.forEach { target ->
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
                ":shared:compileKotlin$targetName",
                ":shared:linkDebugExecutable$targetName"
            )
            group = "build"
            description = "Build debug binary for ${target.name}"
        }

        task("buildRelease$targetName") {
            dependsOn(
                ":shared:compileKotlin$targetName",
                ":shared:linkReleaseExecutable$targetName"
            )
            group = "build"
            description = "Build release binary for ${target.name}"
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.com.kgit2.kommand)
            implementation(libs.com.squareup.okio)
            implementation(libs.xmlutil.core)
            implementation(libs.xmlutil.serialization)
        }

        nativeMain.dependencies {
            implementation(libs.clikt)
        }
    }
}

dependencies {
    detektPlugins(libs.detekt.formatting)
}
