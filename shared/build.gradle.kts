plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
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
