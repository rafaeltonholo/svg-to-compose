plugins {
    alias(playgroundKmpLibs.plugins.androidApplication) apply false
    alias(playgroundKmpLibs.plugins.androidLibrary) apply false
    alias(playgroundKmpLibs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
}