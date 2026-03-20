import com.varabyte.kobweb.gradle.worker.util.configAsKobwebWorker

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kobweb.worker)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.metro)
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
            api(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kobweb.worker)
            implementation(libs.kobwebx.serialization.kotlinx)
            implementation(libs.dev.tonholo.s2c)
            implementation(npm("svgo", "4.0.1"))
        }
    }
}
