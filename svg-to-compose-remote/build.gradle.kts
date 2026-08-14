import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.SourcesJar
import dev.tonholo.s2c.conventions.kmp.targets.useJs
import dev.tonholo.s2c.conventions.kmp.targets.useWasmJs

plugins {
    alias(libs.plugins.dev.tonholo.s2c.conventions.kmp)
    alias(libs.plugins.dev.tonholo.s2c.conventions.testing)
}

kotlin {
    useJs()
    useWasmJs()

    sourceSets {
        commonMain.dependencies {
            api(projects.svgToCompose)
            implementation(libs.com.squareup.okio)
        }

        commonTest.dependencies {
            implementation(libs.com.squareup.okio.fakefilesystem)
        }

        jvmMain.dependencies {
            implementation(libs.com.squareup.okhttp3.okhttp)
        }

        nativeMain.dependencies {
            implementation(libs.io.ktor.ktorClientCore)
        }

        jsMain.dependencies {
            implementation(libs.io.ktor.ktorClientCore)
            implementation(npm("jszip", libs.versions.jszip.get()))
        }

        wasmJsMain.dependencies {
            implementation(npm("jszip", libs.versions.jszip.get()))
        }
    }
}

buildConfig {
    packageName("dev.tonholo.s2c.remote.config")
}

mavenPublishing {
    configure(
        KotlinMultiplatform(
            javadocJar = JavadocJar.Dokka("dokkaGeneratePublicationHtml"),
            sourcesJar = SourcesJar.Sources(),
        ),
    )
    pom {
        name.set("SVG/XML to Compose Remote Sources")
        description.set(
            "Remote source support for SVG-to-Compose: downloads SVG/AVG icons from URLs, ZIP archives, and icon fonts.",
        )
    }
}
