plugins {
    dev.tonholo.s2c.conventions.kmp
    dev.tonholo.s2c.conventions.testing
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.com.squareup.okio)
            implementation(libs.com.fleeksoft.ksoup)
            implementation(kotlin("reflect"))
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        nativeMain.dependencies {
            implementation(libs.clikt)
        }
    }
}
