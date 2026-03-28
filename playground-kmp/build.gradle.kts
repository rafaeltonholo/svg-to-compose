plugins {
    alias(playgroundKmpLibs.plugins.androidApplication) apply false
    alias(playgroundKmpLibs.plugins.androidMultiplatformLibrary) apply false
    alias(playgroundKmpLibs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
}
