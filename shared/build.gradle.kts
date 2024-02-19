import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithHostTests

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.io.gitlab.arturbosch.detekt)
    id(libs.plugins.com.codingfeline.buildkonfig.get().pluginId)
}

val rootBuildDir = File(project.rootDir.absolutePath, "build")

kotlin {
    fun createReleaseTask(name: String, targets: List<KotlinNativeTargetWithHostTests>) {
        task("release$name") {
            dependsOn(targets.map { target ->
                ":shared:linkReleaseExecutable${target.name.replaceFirstChar { it.uppercaseChar() }}"
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

dependencies {
    detektPlugins(libs.detekt.formatting)
}
