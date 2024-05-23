plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(sampleLibs.plugins.androidApplication) apply false
    alias(sampleLibs.plugins.kotlinAndroid) apply false
    alias(sampleLibs.plugins.compose.compiler) apply false
}

buildscript {
    dependencies {
        classpath(libs.com.codingfeline.buildkonfig.gradle.plugin)
    }
}
