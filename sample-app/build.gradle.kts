@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(sampleLibs.plugins.androidApplication)
    alias(sampleLibs.plugins.kotlinAndroid)
}

android {
    namespace = "dev.tonholo.sampleApp"
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.tonholo.sampleApp"
        minSdk = 28
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = sampleLibs.versions.androidxComposeCompiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(sampleLibs.core.ktx)
    implementation(sampleLibs.lifecycle.runtime.ktx)
    implementation(sampleLibs.activity.compose)
    implementation(platform(sampleLibs.compose.bom))
    implementation(sampleLibs.ui)
    implementation(sampleLibs.ui.graphics)
    implementation(sampleLibs.ui.tooling.preview)
    implementation(sampleLibs.material3)
    implementation(sampleLibs.google.material)

    debugImplementation(sampleLibs.bundles.androidx.compose.ui.debug)
}
