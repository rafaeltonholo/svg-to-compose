pluginManagement {
    includeBuild("../../build-logic")
    repositories {
        maven { url = uri("../../build/localMaven") }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        maven { url = uri("../../build/localMaven") }
        mavenCentral()
        google()
    }
    versionCatalogs {
        create("libs") { from(files("../../gradle/libs.versions.toml")) }
        create("cliLibs") {
            from(files("../../gradle/libs.cli.versions.toml"))
        }
    }
}

rootProject.name = "svg-to-compose-cli"

// When running as an included build (from the root project), the root
// project already provides :svg-to-compose via project substitution.
// Only include the root build when running standalone to avoid a
// circular composite build reference.
if (gradle.parent == null) {
    includeBuild("../..") {
        name = "svg-to-compose-root"
        dependencySubstitution {
            substitute(module("dev.tonholo.s2c:svg-to-compose"))
                .using(project(":svg-to-compose"))
        }
    }
}
