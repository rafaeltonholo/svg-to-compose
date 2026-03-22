import dev.tonholo.s2c.annotations.DelicateSvg2ComposeApi
import dev.tonholo.s2c.annotations.ExperimentalParallelProcessing

plugins {
    alias(playgroundLibs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(playgroundLibs.plugins.dev.tonholo.s2c)
}

android {
    namespace = "dev.tonholo.svgtocompose.playground"
    compileSdk = 36

    defaultConfig {
        applicationId = "dev.tonholo.svgtocompose.playground"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

svgToCompose {
    processor {
        @OptIn(ExperimentalParallelProcessing::class)
        useParallelism(parallelism = 20)

        common {
            recursive()
            optimize(enabled = false)
            icons {
                theme("dev.tonholo.svgtocompose.playground.ui.theme.SampleAppTheme")
            }
        }
        val basePackage = "dev.tonholo.svgtocompose.playground.ui.icon"
        create("svg") {
            from(layout.projectDirectory.dir("../../samples/svg"))
            destinationPackage("$basePackage.svg")
            icons {
                optimize(enabled = false)
                minify()
            }
        }
        val avg by creating {
            from(layout.projectDirectory.dir("../../samples/avg"))
            destinationPackage("$basePackage.avg")
            icons {
                @OptIn(DelicateSvg2ComposeApi::class)
                persist()
            }
        }
        create("template") {
            from(layout.projectDirectory.dir("samples/template"))
            destinationPackage("$basePackage.template.generated")
            icons {
                templateFile(rootProject.layout.projectDirectory.file("s2c.template.toml"))
            }
        }
    }
}

dependencies {

    implementation(playgroundLibs.core.ktx)
    implementation(playgroundLibs.lifecycle.runtime.ktx)
    implementation(playgroundLibs.activity.compose)
    implementation(platform(playgroundLibs.compose.bom))
    implementation(playgroundLibs.ui)
    implementation(playgroundLibs.ui.graphics)
    implementation(playgroundLibs.ui.tooling.preview)
    implementation(playgroundLibs.material3)
    implementation(playgroundLibs.google.material)

    debugImplementation(playgroundLibs.bundles.androidx.compose.ui.debug)
}
