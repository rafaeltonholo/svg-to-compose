import com.vanniktech.maven.publish.GradlePlugin
import com.vanniktech.maven.publish.JavadocJar

plugins {
    `kotlin-dsl`
    dev.tonholo.s2c.conventions.gradle.plugin
}

gradlePlugin {
    website.set("https://github.com/rafaeltonholo/svg-to-compose")
    vcsUrl.set("https://github.com/rafaeltonholo/svg-to-compose")
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
    compilerOptions {
        freeCompilerArgs.add("-Xcontext-receivers")
    }
}
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions {
        apiVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_1)
        languageVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_1)
    }
}

mavenPublishing {
    configure(
        GradlePlugin(
            javadocJar = JavadocJar.Dokka("dokkaHtml"),
            sourcesJar = true,
        )
    )
    pom {
        name.set("SVG/XML to Compose Gradle Plugin")
        description.set("A Gradle plugin that converts SVG or an Android Vector Drawable (AVG) to Android Jetpack Compose Icons.")
    }
}

//dokka {
//    moduleName.set("SVG/XML to Compose Gradle Plugin")
//}

dependencies {
    compileOnly(libs.com.android.tools.build.gradle)
    implementation(libs.org.jetbrains.kotlin.gradle.plugin)
    implementation(libs.com.squareup.okio)
    implementation(projects.svgToCompose)
}
