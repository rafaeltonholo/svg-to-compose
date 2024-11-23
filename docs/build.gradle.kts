plugins {
    org.jetbrains.kotlin.jvm
    dev.tonholo.s2c.conventions.dokka
}

dependencies {
    dokka(projects.svgToCompose)
    dokka(projects.svgToComposeGradlePlugin)
}

dokka {
    moduleName.set("SVG to Compose Documentation")
    dokkaPublications.html {
        includes.from(rootProject.layout.projectDirectory.file("README.md"))
        outputDirectory.set(rootProject.layout.buildDirectory.dir("dokka"))
    }
}
