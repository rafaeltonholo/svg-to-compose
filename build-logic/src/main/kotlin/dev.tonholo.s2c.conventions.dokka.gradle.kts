import org.jetbrains.dokka.gradle.engine.parameters.VisibilityModifier

plugins {
    org.jetbrains.dokka
}

dokka {
    dokkaSourceSets.configureEach {
        documentedVisibilities(
            VisibilityModifier.Public,
            VisibilityModifier.Protected,
        )
        val moduleDocFile = project.layout.projectDirectory.file("MODULE.md")
        if (moduleDocFile.asFile.exists()) {
            includes.from(moduleDocFile)
        }
        sourceLink {
            localDirectory.set(rootProject.projectDir)
            remoteUrl("https://github.com/rafaeltonholo/svg-to-compose/tree/main")
            remoteLineSuffix.set("#L")
        }
    }
    pluginsConfiguration.html {
        customStyleSheets.from(rootProject.layout.projectDirectory.file("docs/styles/custom-styles.css"))
        customAssets.from(
            rootProject.layout.projectDirectory.file("docs/styles/logo-icon.svg"),
            rootProject.layout.projectDirectory.file("s2c-logo.svg"),
        )
        footerMessage.set("(c) Rafael Tonholo")
        homepageLink.set("https://www.svgtocompose.com")
    }
}
