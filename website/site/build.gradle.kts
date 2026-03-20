import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import kotlinx.html.link

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kobweb.application)
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
            }
        }
    }
}

val copyEditorWasm by tasks.registering(Copy::class) {
    dependsOn(":editor-wasm:wasmJsBrowserDistribution")
    from(project(":editor-wasm").layout.buildDirectory.dir("dist/wasmJs/productionExecutable"))
    into(layout.projectDirectory.dir("src/jsMain/resources/public/editor"))
}
tasks.configureEach {
    if (name == "jsProcessResources") {
        dependsOn(copyEditorWasm)
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
