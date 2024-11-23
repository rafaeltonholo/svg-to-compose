import com.vanniktech.maven.publish.GradlePlugin
import com.vanniktech.maven.publish.JavadocJar

plugins {
    `kotlin-dsl`
    dev.tonholo.s2c.conventions.gradle.plugin
    dev.tonholo.s2c.conventions.dokka
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

mavenPublishing {
    configure(
        GradlePlugin(
            javadocJar = JavadocJar.Dokka("dokkaGenerate"),
            sourcesJar = true,
        )
    )
    pom {
        name.set("SVG/XML to Compose Gradle Plugin")
        description.set("A Gradle plugin that converts SVG or an Android Vector Drawable (AVG) to Android Jetpack Compose Icons.")
    }
}

dokka {
    moduleName.set("SVG/XML to Compose Gradle Plugin")
}

dependencies {
    compileOnly(libs.com.android.tools.build.gradle)
    implementation(libs.org.jetbrains.kotlin.gradle.plugin)
    implementation(libs.org.jetbrains.kotlinx.coroutines.core)
    implementation(libs.com.squareup.okio)
    implementation(projects.svgToCompose)
}
