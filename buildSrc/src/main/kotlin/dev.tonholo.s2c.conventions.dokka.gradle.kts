import org.jetbrains.dokka.DokkaConfiguration
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.jetbrains.dokka.gradle.DokkaTaskPartial
import java.net.URL

plugins {
    org.jetbrains.dokka
}

//dokka {
//    dokkaSourceSets.configureEach {
//        sourceLink {
//            remoteUrl.set(URI("https://github.com/rafaeltonholo/svg-to-compose/tree/main"))
//            localDirectory.set(rootDir)
//        }
//    }
//    pluginsConfiguration.html {
//        footerMessage.set("(c) Rafael Tonholo")
//    }
//}

tasks.withType<DokkaMultiModuleTask> {
    moduleName.set("SVG to Compose Documentation")
    outputDirectory.set(rootProject.layout.buildDirectory.dir("dokka"))
//    includes.setFrom(rootProject.layout.projectDirectory.file("README.md"))
//    dokkaSourceSets.configureEach {
//        includes.from(rootProject.layout.projectDirectory.file("README.md"))
//        documentedVisibilities.set(
//            setOf(
//                DokkaConfiguration.Visibility.PUBLIC,
//                DokkaConfiguration.Visibility.PROTECTED,
//            ),
//        )
//    }
}

subprojects {
    apply(plugin = "org.jetbrains.dokka")
    tasks.withType<DokkaTaskPartial>().configureEach {
        dokkaSourceSets.configureEach {
            documentedVisibilities.set(
                setOf(
                    DokkaConfiguration.Visibility.PUBLIC,
                    DokkaConfiguration.Visibility.PROTECTED,
                ),
            )
            sourceLink {
                localDirectory.set(rootProject.projectDir)
                remoteUrl.set(URL("https://github.com/rafaeltonholo/svg-to-compose/tree/main"))
                remoteLineSuffix.set("#L")
            }
        }
    }
}
