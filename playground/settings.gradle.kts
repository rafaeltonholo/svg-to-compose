pluginManagement {
    repositories {
        maven { url = uri("../build/localMaven") }
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") { from(files("../gradle/libs.versions.toml")) }
        create("playgroundLibs") {
            from(files("../gradle/libs.playground.versions.toml"))
        }
    }
}

rootProject.name = "SVG-to-Compose-playground"
include(":app")
