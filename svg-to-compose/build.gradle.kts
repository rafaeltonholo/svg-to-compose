import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform

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
            implementation(libs.com.github.ajalt.clikt)
            implementation(libs.com.github.ajalt.clikt.markdown)
        }
    }
}

mavenPublishing {
    configure(
        KotlinMultiplatform(
            javadocJar = JavadocJar.Dokka("dokkaHtml"),
            sourcesJar = true,
        )
    )
    pom {
        name.set("SVG/XML to Compose Library")
        description.set("A KMP Library that converts SVG or an Android Vector Drawable (AVG) to Android Jetpack Compose Icons.")
    }
}
