plugins {
    `kotlin-dsl`
}

kotlin {
    compilerOptions.freeCompilerArgs.addAll(
        "-Xcontext-receivers",
        "-opt-in=org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi",
    )
}

dependencies {
    // Workaround for using version catalog in Kotlin script convention plugins
    // https://github.com/gradle/gradle/issues/15383#issuecomment-779893192
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    implementation(libs.org.jetbrains.kotlin.gradle.plugin)
    implementation(libs.org.jetbrains.kotlin.powerAssert.plugin)
    implementation(libs.io.gitlab.arturbosch.detekt.gradle.plugin)
    implementation(libs.com.github.gmazzo.buildconfig.plugin)
    implementation(libs.com.vanniktech.gradle.maven.publish)
}
