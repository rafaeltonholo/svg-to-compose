import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import kotlinx.html.link
import java.util.Properties

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kobweb.application)
    alias(libs.plugins.buildconfig)
}

val appProperties = Properties().apply {
    rootProject.rootDir.resolve("../app.properties").inputStream().use { load(it) }
}

buildConfig {
    packageName("dev.tonholo.s2c.website.config")
    useKotlinOutput()
    buildConfigField("VERSION", appProperties["VERSION"].toString())
}

group = "dev.tonholo.s2c.website"
version = "1.0-SNAPSHOT"

kobweb {
    app {
        index {
            description.set("Convert SVG and Android Vector Drawables into Jetpack Compose ImageVector code")
            head.add {
                link {
                    rel = "preconnect"
                    href = "https://fonts.googleapis.com"
                }
                link {
                    rel = "preconnect"
                    href = "https://fonts.gstatic.com"
                    attributes["crossorigin"] = "anonymous"
                }
                link {
                    href = "https://fonts.googleapis.com/css2" +
                        "?family=Space+Grotesk:wght@400;500;600;700" +
                        "&family=JetBrains+Mono:wght@400;500;700" +
                        "&display=swap"
                    rel = "stylesheet"
                }
                link {
                    rel = "icon"
                    type = "image/png"
                    href = basePath.prependTo("/favicon-96x96.png")
                    sizes = "96x96"
                }
                link {
                    rel = "icon"
                    type = "image/svg+xml"
                    href = basePath.prependTo("/favicon.svg")
                }
                link {
                    rel = "shortcut icon"
                    href = basePath.prependTo("/favicon.ico")
                }
                link {
                    rel = "apple-touch-icon"
                    sizes = "180x180"
                    href = basePath.prependTo("/apple-touch-icon.png")
                }
                link {
                    rel = "manifest"
                    href = basePath.prependTo("/site.webmanifest")
                }
            }
        }
    }
}

val copyEditorWasm by tasks.registering(Copy::class) {
    dependsOn(":editor-wasm:wasmJsBrowserDistribution")
    from(project(":editor-wasm").layout.buildDirectory.dir("dist/wasmJs/productionExecutable"))
    into(layout.projectDirectory.dir("src/jsMain/resources/public/editor"))
}

val dokkaOutputDir = rootProject.layout.projectDirectory.dir("../build/dokka")
val copyDokkaHtml by tasks.registering(Sync::class) {
    from(dokkaOutputDir)
    into(layout.projectDirectory.dir("src/jsMain/resources/public/docs"))
}

tasks.configureEach {
    if (name == "jsProcessResources") {
        dependsOn(copyEditorWasm)
        dependsOn(copyDokkaHtml)
    }
}

kotlin {
    configAsKobwebApplication("website")

    sourceSets {
        jsMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.html.core)
            implementation(libs.compose.viewmodel)
            implementation(libs.kobweb.core)
            implementation(libs.kobweb.silk)
            implementation(libs.silk.icons.fa)
            implementation(libs.dev.tonholo.s2c)
            implementation(project(":worker"))
            implementation(npm("jszip", libs.versions.jszip.get()))
        }
    }
}
