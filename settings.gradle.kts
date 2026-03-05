rootProject.name = "SVG-to-Compose-parent"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://s01.oss.sonatype.org/content/repositories/releases/")
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        google()
        mavenCentral()
    }
}

include(
    // docs module is currently disabled; uncomment when re-enabled in this build
    // ":docs",
    ":svg-to-compose",
    ":svg-to-compose-gradle-plugin",
)
