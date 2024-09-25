plugins {
    `kotlin-dsl`
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    alias(libs.plugins.com.vanniktech.gradle.maven.publish)
}

group = "dev.tonholo.s2c"
version = "1.0.0-alpha01"

gradlePlugin {
    plugins {
        create("svgToCompose") {
            id = group.toString()
            implementationClass = "dev.tonholo.s2c.gradle.Svg2ComposePlugin"
            displayName = "SVG/AVG to Compose"
            description = "Converts SVG and AVG to Android Jetpack Compose Icons."
        }
    }
}

kotlin {
    compilerOptions.freeCompilerArgs.add("-Xcontext-receivers")
}

dependencies {
    // Get access to Kotlin multiplatform source sets
    implementation(kotlin("gradle-plugin"))
    compileOnly(gradleApi())
    compileOnly(libs.com.android.tools.build.gradle)
    implementation(libs.org.jetbrains.kotlin.gradle.plugin)
    implementation(libs.com.squareup.okio)
    implementation(projects.svgToCompose)
}

publishing {
    repositories {
        maven {
            name = "testMaven"
            url = uri(rootProject.layout.buildDirectory.dir("localMaven"))
        }
    }
}
