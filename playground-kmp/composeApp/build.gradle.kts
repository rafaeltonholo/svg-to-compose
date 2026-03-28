import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(playgroundKmpLibs.plugins.androidMultiplatformLibrary)
    alias(playgroundKmpLibs.plugins.composeMultiplatform)
    alias(libs.plugins.compose.compiler)
    alias(playgroundKmpLibs.plugins.dev.tonholo.s2c)
}

kotlin {
    androidLibrary {
        namespace = "dev.tonholo.svg_to_compose.playground.kmp"
        compileSdk = playgroundKmpLibs.versions.android.compileSdk.get().toInt()
        minSdk = playgroundKmpLibs.versions.android.minSdk.get().toInt()
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    jvm("desktop")

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        outputModuleName.set("composeApp")
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static(rootDirPath)
                    static(projectDirPath)
                }
            }
        }
        binaries.executable()
    }

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(playgroundKmpLibs.compose.runtime)
            implementation(playgroundKmpLibs.compose.foundation)
            implementation(playgroundKmpLibs.compose.material)
            implementation(playgroundKmpLibs.compose.ui)
            implementation(playgroundKmpLibs.compose.components.resources)
            implementation(playgroundKmpLibs.compose.components.ui.tooling.preview)
            implementation(playgroundKmpLibs.androidx.lifecycle.viewmodel)
            implementation(playgroundKmpLibs.androidx.lifecycle.runtime.compose)
        }
        desktopMain.dependencies {
            @Suppress("DEPRECATION")
            implementation(compose.desktop.currentOs)
            implementation(libs.org.jetbrains.kotlinx.coroutines.swing)
        }
        wasmJsMain.dependencies {
            implementation(devNpm("webpack", "^5.94.0"))
            implementation(devNpm("path-to-regexp", "^0.1.12"))
        }
    }
}

compose.desktop {
    application {
        mainClass = "dev.tonholo.svg_to_compose.playground.kmp.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "dev.tonholo.svg_to_compose.playground.kmp"
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
                theme("dev.tonholo.svg_to_compose.playground.kmp.ui.Theme")
                minify()
                mapIconNameTo { iconName -> iconName.replace("-filled", "") }
            }
        }

        create("composeResource") {
            from(layout.projectDirectory.dir("src/commonMain/composeResources/drawable").also { println(it) })
            destinationPackage("dev.tonholo.svg_to_compose.playground.kmp.ui.icons.compose_resources")
        }

        create("svg") {
            from(rootProject.layout.projectDirectory.dir("../samples/svg").also { println(it) })
            destinationPackage("dev.tonholo.svg_to_compose.playground.kmp.ui.icons.samples")
        }
    }
}
