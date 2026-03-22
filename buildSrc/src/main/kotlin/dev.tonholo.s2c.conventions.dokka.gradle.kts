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
        sourceLink {
            localDirectory.set(rootProject.projectDir)
            remoteUrl("https://github.com/rafaeltonholo/svg-to-compose/tree/main")
            remoteLineSuffix.set("#L")
        }
    }
}
