import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(playgroundKmpLibs.plugins.androidMultiplatformLibrary)
    alias(playgroundKmpLibs.plugins.composeMultiplatform)
    alias(libs.plugins.compose.compiler)
    alias(playgroundKmpLibs.plugins.composeHotReload)
    alias(playgroundKmpLibs.plugins.dev.tonholo.s2c)
}

kotlin {
    android {
        namespace = "dev.tonholo.svgtocompose.playground.kmp"
        compileSdk = playgroundKmpLibs.versions.android.compileSdk.get().toInt()

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
        androidResources {
            enable = true
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    jvm()

    js {
        browser()
        binaries.executable()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets {
        androidMain.dependencies {
            implementation(playgroundKmpLibs.compose.ui.tooling.preview)
            implementation(playgroundKmpLibs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(playgroundKmpLibs.compose.runtime)
            implementation(playgroundKmpLibs.compose.foundation)
            implementation(playgroundKmpLibs.compose.material3)
            implementation(playgroundKmpLibs.compose.ui)
            implementation(playgroundKmpLibs.compose.components.resources)
            implementation(playgroundKmpLibs.compose.ui.tooling.preview)
            implementation(playgroundKmpLibs.androidx.lifecycle.viewmodelCompose)
            implementation(playgroundKmpLibs.androidx.lifecycle.runtime.compose)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(playgroundKmpLibs.kotlinx.coroutinesSwing)
        }
    }
}

dependencies {
    androidRuntimeClasspath(playgroundKmpLibs.compose.ui.tooling)
}

compose.desktop {
    application {
        mainClass = "dev.tonholo.svgtocompose.playground.kmp.app.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "dev.tonholo.svgtocompose.playground.kmp.app"
            packageVersion = "1.0.0"
        }
    }
}

svgToCompose {
    processor {
        common {
            optimize(enabled = false)
            recursive()
            icons {
                noPreview()
                theme("dev.tonholo.svgtocompose.playground.kmp.ui.Theme")
                minify()
                mapIconNameTo { iconName -> iconName.replace("-filled", "") }
            }
        }

        create("composeResource") {
            from(layout.projectDirectory.dir("src/commonMain/composeResources/drawable"))
            destinationPackage("dev.tonholo.svgtocompose.playground.kmp.ui.icons.compose_resources")
        }

        create("svg") {
            from(rootProject.layout.projectDirectory.dir("../samples/svg"))
            destinationPackage("dev.tonholo.svgtocompose.playground.kmp.ui.icons.samples")
        }

        create("avg") {
            from(rootProject.layout.projectDirectory.dir("../samples/avg"))
            destinationPackage("dev.tonholo.svgtocompose.playground.kmp.ui.icons.samples.avg")
        }
    }
}
