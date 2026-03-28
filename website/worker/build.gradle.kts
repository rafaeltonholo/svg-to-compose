import com.varabyte.kobweb.gradle.worker.util.configAsKobwebWorker

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(websiteLibs.plugins.kobweb.worker)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.dev.zacsweers.metro)
}

group = "dev.tonholo.s2c.website.worker"
version = "1.0-SNAPSHOT"

kotlin {
    configAsKobwebWorker("s2c-converter")
    compilerOptions {
        freeCompilerArgs.addAll(
            "-Xcontext-parameters",
        )
    }
    sourceSets {
        jsMain.dependencies {
            api(libs.org.jetbrains.kotlinx.serialization.json)
            implementation(libs.org.jetbrains.kotlinx.coroutines.core)
            implementation(websiteLibs.kobweb.worker)
            implementation(websiteLibs.kobwebx.serialization.kotlinx)
            implementation(websiteLibs.dev.tonholo.s2c)
            implementation(npm("svgo", "4.0.1"))
        }
    }
}
