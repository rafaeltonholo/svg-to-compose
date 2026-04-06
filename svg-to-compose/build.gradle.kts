import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.SourcesJar
import dev.tonholo.s2c.conventions.kmp.targets.useJs
import dev.tonholo.s2c.conventions.kmp.targets.useWasmJs

plugins {
    alias(libs.plugins.dev.tonholo.s2c.conventions.kmp)
    alias(libs.plugins.dev.tonholo.s2c.conventions.testing)
    alias(libs.plugins.app.cash.burst)
    alias(libs.plugins.org.jetbrains.kotlin.serialization)
}

kotlin {
    useJs()
    useWasmJs()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.com.squareup.okio)
            implementation(libs.com.fleeksoft.ksoup)
            implementation(libs.com.akuleshov7.ktoml.core)
            implementation(libs.org.jetbrains.annotations)
            implementation(kotlin("reflect"))
            implementation(libs.org.jetbrains.kotlinx.coroutines.core)
            implementation(libs.org.jetbrains.kotlinx.serialization.json)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.org.jetbrains.kotlinx.coroutines.test)
            implementation(libs.com.squareup.okio.fakefilesystem)
        }

        jsMain.dependencies {
            implementation(libs.com.squareup.okio.fakefilesystem)
        }

        wasmJsMain.dependencies {
            implementation(libs.com.squareup.okio.fakefilesystem)
        }
    }
}

mavenPublishing {
    configure(
        KotlinMultiplatform(
            javadocJar = JavadocJar.Dokka("dokkaGeneratePublicationHtml"),
            sourcesJar = SourcesJar.Sources(),
        ),
    )
    pom {
        name.set("SVG/XML to Compose Library")
        description.set("A KMP Library that converts SVG or an Android Vector Drawable (AVG) to Android Jetpack Compose Icons.")
    }
}
