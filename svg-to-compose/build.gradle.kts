plugins {
    dev.tonholo.s2c.conventions.kmp
    dev.tonholo.s2c.conventions.testing
    dev.tonholo.s2c.conventions.dokka
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

dokka {
    moduleName.set("SVG/XML to Compose Library")
}
