import dev.tonholo.s2c.annotations.DelicateSvg2ComposeApi
import dev.tonholo.s2c.annotations.ExperimentalParallelProcessing

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.dev.tonholo.s2c)
}

android {
    namespace = "dev.tonholo.svg_to_compose.playground"
    compileSdk = 36

    defaultConfig {
        applicationId = "dev.tonholo.svg_to_compose.playground"
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
                theme("dev.tonholo.svg_to_compose.playground.ui.theme.SampleAppTheme")
            }
        }
        val basePackage = "dev.tonholo.svg_to_compose.playground.ui.icon"
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
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.google.material)

    debugImplementation(libs.bundles.androidx.compose.ui.debug)
}
