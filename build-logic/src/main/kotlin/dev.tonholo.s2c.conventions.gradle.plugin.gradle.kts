import dev.tonholo.s2c.conventions.inject.configureMetro

plugins {
    id("dev.tonholo.s2c.conventions.common")
    id("dev.tonholo.s2c.conventions.dokka")
    id("dev.tonholo.s2c.conventions.publication")
    org.jetbrains.kotlin.jvm
    id("org.jetbrains.kotlin.plugin.sam.with.receiver")
    id("com.gradle.plugin-publish")
    dev.zacsweers.metro
}

samWithReceiver {
    annotation("org.gradle.api.HasImplicitReceiver")
}

configureMetro()

dependencies {
    // Get access to Kotlin multiplatform source sets
    implementation(kotlin("gradle-plugin"))
    compileOnly(gradleApi())
    compileOnly(gradleKotlinDsl())

    testImplementation(gradleTestKit())
    testImplementation(kotlin("test"))
}
