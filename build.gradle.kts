plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.org.jetbrains.kotlin.multiplatform) apply false
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
    `kotlin-dsl` apply false
}

buildscript {
    dependencies {
        classpath(libs.com.codingfeline.buildkonfig.gradle.plugin)
    }
}
