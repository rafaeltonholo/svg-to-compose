import java.util.Properties

rootProject.name = "SVG-to-Compose-parent"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        gradlePluginPortal()
        mavenCentral()
        maven("https://s01.oss.sonatype.org/content/repositories/releases/")
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

include(
    ":svg-to-compose",
    ":svg-to-compose-gradle-plugin",
)

val localPropertiesFile = file("local.properties")
if (localPropertiesFile.exists()) {
    val properties = Properties().apply {
        load(file("local.properties").reader())
    }

// Sample app is a development tool to understand if the changes
// are not going to change the way end result of the icon in the
// Jetpack Compose Preview.
    if (properties["enable_sample_app"]?.toString()?.toBoolean() == true) {
        includeBuild("sample-app")
    }
}
