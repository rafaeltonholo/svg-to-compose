plugins {
    id("dev.tonholo.s2c.conventions.common")
    org.jetbrains.kotlin.jvm
}

dependencies {
    // Get access to Kotlin multiplatform source sets
    implementation(kotlin("gradle-plugin"))
    compileOnly(gradleApi())
}
