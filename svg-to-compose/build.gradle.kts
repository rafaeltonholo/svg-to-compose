import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform

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

mavenPublishing {
    mavenPublishing {
        configure(
            KotlinMultiplatform(
                javadocJar = JavadocJar.Dokka("dokkaGenerate"),
                sourcesJar = true,
            )
        )
    }
    pom {
        name.set("SVG/XML to Compose Library")
        description.set("A KMP Library that converts SVG or an Android Vector Drawable (AVG) to Android Jetpack Compose Icons.")
    }
}

dokka {
    moduleName.set("SVG/XML to Compose Library")
}
