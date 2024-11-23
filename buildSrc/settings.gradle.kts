dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    // Workaround for using version catalog in Kotlin script convention plugins
    // https://github.com/gradle/gradle/issues/15383#issuecomment-779893192
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}
rootProject.name = "buildSrc"
