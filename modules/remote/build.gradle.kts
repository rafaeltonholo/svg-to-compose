import dev.tonholo.s2c.conventions.kmp.targets.useJs
import dev.tonholo.s2c.conventions.kmp.targets.useWasmJs
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.dev.tonholo.s2c.conventions.kmp)
    alias(libs.plugins.dev.tonholo.s2c.conventions.testing)
}

kotlin {
    useJs()
    useWasmJs()

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    applyDefaultHierarchyTemplate {
        common {
            group("jvmAndNative") {
                withJvm()
                withNative()
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.svgToCompose)
            implementation(libs.com.squareup.okio)
            implementation(libs.org.jetbrains.kotlinx.coroutines.core)
            implementation(libs.io.ktor.ktorClientCore)
        }

        commonTest.dependencies {
            implementation(libs.com.squareup.okio.fakefilesystem)
            implementation(libs.org.jetbrains.kotlinx.coroutines.test)
            implementation(libs.io.ktor.ktorClientMock)
        }

        jvmMain.dependencies {
            implementation(libs.io.ktor.ktorClientOkhttp)
        }

        appleMain.dependencies {
            implementation(libs.io.ktor.ktorClientCio)
        }

        linuxMain.dependencies {
            implementation(libs.io.ktor.ktorClientCio)
        }

        mingwMain.dependencies {
            implementation(libs.io.ktor.ktorClientWinhttp)
        }

        jsMain.dependencies {
            implementation(npm("jszip", libs.versions.jszip.get()))
        }
    }
}

buildConfig {
    packageName("dev.tonholo.s2c.remote.config")
}

mavenPublishing {
    pom {
        name.set("SVG/XML to Compose Remote Sources")
        description.set(
            "Remote source support for SVG-to-Compose: downloads SVG/AVG icons from URLs, ZIP archives, and icon fonts.",
        )
    }
}
