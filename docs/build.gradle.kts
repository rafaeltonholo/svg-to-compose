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

// Copy assets referenced by README.md into the Dokka output root,
// since Dokka doesn't resolve relative image paths from included markdown.
tasks.named("dokkaGenerate") {
    doLast {
        val dokkaDir = rootProject.layout.buildDirectory.dir("dokka").get().asFile
        val logo = rootProject.layout.projectDirectory.file("s2c-logo.svg").asFile
        if (logo.exists()) {
            logo.copyTo(dokkaDir.resolve("s2c-logo.svg"), overwrite = true)
        }
    }
}
