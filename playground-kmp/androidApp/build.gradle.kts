import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(playgroundKmpLibs.plugins.androidApplication)
    alias(playgroundKmpLibs.plugins.composeMultiplatform)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "dev.tonholo.svg_to_compose.playground.kmp.app"
    compileSdk = playgroundKmpLibs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "dev.tonholo.svg_to_compose.playground.kmp"
        minSdk = playgroundKmpLibs.versions.android.minSdk.get().toInt()
        targetSdk = playgroundKmpLibs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_11)
    }
}

dependencies {
    implementation(projects.composeApp)
    implementation(playgroundKmpLibs.androidx.activity.compose)
    implementation(playgroundKmpLibs.compose.ui.tooling.preview)
    debugImplementation(playgroundKmpLibs.compose.ui.tooling)
}
