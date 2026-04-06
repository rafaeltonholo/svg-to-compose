rootProject.name = "SVG-to-Compose-parent"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://s01.oss.sonatype.org/content/repositories/releases/")
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        google()
        mavenCentral()
    }
}

include(
    ":docs",
    ":svg-to-compose",
    ":svg-to-compose-gradle-plugin",
)

includeBuild("website")

// Playground builds are only included when running as the root project
// (not when included by another build like modules/cli) and not in CI.
if (gradle.parent == null && System.getenv("CI") == null) {
    includeBuild("playground")
    includeBuild("playground-kmp")
}
