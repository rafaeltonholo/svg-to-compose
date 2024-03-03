plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.kotlinMultiplatform) apply false
//    alias(libs.plugins.io.gitlab.arturbosch.detekt)
}

buildscript {
    dependencies {
        classpath(libs.com.codingfeline.buildkonfig.gradle.plugin)
    }
}
