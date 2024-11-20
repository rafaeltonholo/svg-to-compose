plugins {
    `kotlin-dsl`
    dev.tonholo.s2c.conventions.gradle.plugin
}

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
    compileOnly(libs.com.android.tools.build.gradle)
    implementation(libs.org.jetbrains.kotlin.gradle.plugin)
    implementation(libs.org.jetbrains.kotlinx.coroutines.core)
    implementation(libs.com.squareup.okio)
    implementation(projects.svgToCompose)
}
