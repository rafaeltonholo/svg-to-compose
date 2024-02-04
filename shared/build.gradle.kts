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
    ).forEach { target ->
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
        task("buildDebug$targetName")
            .dependsOn(
                ":shared:compileKotlin$targetName",
                ":shared:linkDebugExecutable$targetName"
            )
         task("buildRelease$targetName")
            .dependsOn(
                ":shared:compileKotlin$targetName",
                ":shared:linkReleaseExecutable$targetName"
            )
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
