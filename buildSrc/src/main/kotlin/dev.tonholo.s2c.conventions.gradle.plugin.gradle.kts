plugins {
    id("dev.tonholo.s2c.conventions.common")
    id("dev.tonholo.s2c.conventions.publication")
    org.jetbrains.kotlin.jvm
    id("org.jetbrains.kotlin.plugin.sam.with.receiver")
}

samWithReceiver {
    annotation("org.gradle.api.HasImplicitReceiver")
}

dependencies {
    // Get access to Kotlin multiplatform source sets
    implementation(kotlin("gradle-plugin"))
    compileOnly(gradleApi())
    compileOnly(gradleKotlinDsl())

    testImplementation(gradleTestKit())
    testImplementation(kotlin("test"))
}
