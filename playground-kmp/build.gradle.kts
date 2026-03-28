plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(playgroundKmpLibs.plugins.androidApplication) apply false
    alias(playgroundKmpLibs.plugins.androidMultiplatformLibrary) apply false
    alias(playgroundKmpLibs.plugins.composeHotReload) apply false
    alias(playgroundKmpLibs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
}
