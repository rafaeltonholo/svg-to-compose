pluginManagement {
    repositories {
        maven { url = uri("../build/localMaven") }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        maven { url = uri("../build/localMaven") }
        mavenCentral()
        google()
    }
    versionCatalogs {
        create("libs") { from(files("../gradle/libs.versions.toml")) }
        create("websiteLibs") {
            from(files("../gradle/libs.website.versions.toml"))
        }
    }
}

// Kobweb snapshot support
gradle.settingsEvaluated {
    fun RepositoryHandler.kobwebSnapshots() {
        maven("https://central.sonatype.com/repository/maven-snapshots/") {
            mavenContent {
                includeGroupByRegex("com\\.varabyte\\.kobweb.*")
                snapshotsOnly()
            }
        }
    }

    pluginManagement.repositories { kobwebSnapshots() }
    dependencyResolutionManagement.repositories { kobwebSnapshots() }
}

rootProject.name = "website"

includeBuild("..") {
    dependencySubstitution {
        substitute(module("dev.tonholo.s2c:svg-to-compose"))
            .using(project(":svg-to-compose"))
    }
}

include(":site")
include(":worker")
include(":editor-wasm")
