plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {

    listOf(
        macosArm64(),
        macosX64(),
        linuxArm64(),
        linuxX64(),
        mingwX64(),
    ).forEach { target ->
        
        target.binaries {
            executable {
                entryPoint = "main"
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
        }
    }
}
