plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

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
    }

    sourceSets {
        commonMain.dependencies {
        }

        nativeMain.dependencies {
            implementation(libs.clikt)
        }
    }
}
