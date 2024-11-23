import java.net.URI

plugins {
    org.jetbrains.dokka
}

dokka {
    dokkaSourceSets.configureEach {
        sourceLink {
            remoteUrl.set(URI("https://github.com/rafaeltonholo/svg-to-compose/tree/main"))
            localDirectory.set(rootDir)
        }
    }
    pluginsConfiguration.html {
        footerMessage.set("(c) Rafael Tonholo")
    }
}
