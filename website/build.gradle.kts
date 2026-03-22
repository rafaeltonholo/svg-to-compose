import dev.detekt.gradle.Detekt

plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.compose.multiplatform) apply false
    alias(libs.plugins.kobweb.application) apply false
    alias(libs.plugins.kobweb.worker) apply false
    alias(libs.plugins.kotlinx.serialization) apply false
    alias(libs.plugins.metro) apply false
    alias(libs.plugins.buildconfig) apply false
    alias(libs.plugins.detekt) apply false
}

subprojects {
    apply(plugin = rootProject.libs.plugins.detekt.get().pluginId)

    configure<dev.detekt.gradle.extensions.DetektExtension> {
        autoCorrect = true
        buildUponDefaultConfig = true
        config.setFrom("${rootProject.rootDir.parentFile}/config/detekt.yml")
        baseline = file("detekt-baseline.xml")
        source.setFrom(
            "src/jsMain/kotlin",
            "src/commonMain/kotlin",
            "src/wasmJsMain/kotlin",
        )
    }

    tasks.withType<Detekt>().configureEach {
        jvmTarget = JavaVersion.VERSION_17.toString()
        exclude {
            setOf("build", "generated").any { dir ->
                dir in it.file.absolutePath
            }
        }
    }

    dependencies {
        "detektPlugins"(rootProject.libs.detekt.rules.ktlint.wrapper)
        "detektPlugins"(rootProject.libs.io.nlopez.compose.rules.detekt)
    }
}
