plugins {
    id("dev.tonholo.s2c.conventions.common")
    id("dev.tonholo.s2c.conventions.publication")
    org.jetbrains.kotlin.jvm
}

dependencies {
    // Get access to Kotlin multiplatform source sets
    implementation(kotlin("gradle-plugin"))
    compileOnly(gradleApi())
}
