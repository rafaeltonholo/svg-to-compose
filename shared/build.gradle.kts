plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
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
            }
        }

        val targetName = target.name.replaceFirstChar { it.uppercaseChar() }
        task("buildDebug$targetName")
            .dependsOn(
                ":shared:compileKotlin$targetName",
                ":shared:linkDebugExecutable$targetName"
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
