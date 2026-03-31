import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import kotlinx.html.link
import java.time.LocalDate
import java.util.Properties

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(websiteLibs.plugins.kobweb.application)
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

val generateLlmsTxt by tasks.registering {
    val templateFile = layout.projectDirectory.file("src/jsMain/resources/llms.txt.template")
    val publicDir = layout.projectDirectory.dir("src/jsMain/resources/public")
    val outputFile = publicDir.file("llms.txt")
    val wellKnownOutputFile = publicDir.file(".well-known/llms.txt")
    val appVersion = appProperties["VERSION"].toString()
    inputs.file(templateFile)
    inputs.property("version", appVersion)
    outputs.files(outputFile, wellKnownOutputFile)
    doLast {
        val content = templateFile.asFile.readText().replace("\${VERSION}", appVersion)
        outputFile.asFile.writeText(content)
        wellKnownOutputFile.asFile.apply {
            parentFile.mkdirs()
            writeText(content)
        }
    }
}

val generateLlmsFullTxt by tasks.registering {
    val templateFile = layout.projectDirectory.file("src/jsMain/resources/llms-full.txt.template")
    val publicDir = layout.projectDirectory.dir("src/jsMain/resources/public")
    val outputFile = publicDir.file("llms-full.txt")
    val wellKnownOutputFile = publicDir.file(".well-known/llms-full.txt")
    val appVersion = appProperties["VERSION"].toString()
    inputs.file(templateFile)
    inputs.property("version", appVersion)
    outputs.files(outputFile, wellKnownOutputFile)
    doLast {
        val content = templateFile.asFile.readText().replace("\${VERSION}", appVersion)
        outputFile.asFile.writeText(content)
        wellKnownOutputFile.asFile.apply {
            parentFile.mkdirs()
            writeText(content)
        }
    }
}

val generateSitemap by tasks.registering {
    val pagesDir = layout.projectDirectory.dir("src/jsMain/kotlin/dev/tonholo/s2c/website/pages")
    val outputFile = layout.projectDirectory.file("src/jsMain/resources/public/sitemap.xml")
    val baseUrl = "https://www.svgtocompose.com"

    // Per-path overrides for changefreq and priority.
    // Pages not listed here get defaults: monthly, 0.5
    val pageMetadata = mapOf(
        "/" to ("weekly" to "1.0"),
        "/docs" to ("monthly" to "0.8"),
        "/docs/cli" to ("monthly" to "0.7"),
        "/docs/gradle-plugin" to ("monthly" to "0.7"),
        "/docs/faq" to ("monthly" to "0.6"),
        "/docs/alternatives" to ("monthly" to "0.6"),
    )

    inputs.dir(pagesDir)
    outputs.file(outputFile)

    doLast {
        // Discover Kobweb @Page files and map to URL paths.
        // Kobweb convention: pages/Index.kt -> "/", pages/docs/Cli.kt -> "/docs/cli"
        val pageFiles = pagesDir.asFile.walkTopDown()
            .filter { it.isFile && it.extension == "kt" }
            .filter { file -> file.readText().contains("@Page") }
            .map { file ->
                val relativePath = file.relativeTo(pagesDir.asFile).path
                    .removeSuffix(".kt")
                    .replace("\\", "/")
                // Index files map to their parent directory
                val urlPath = if (relativePath == "Index") {
                    "/"
                } else {
                    "/" + relativePath.replace("/Index", "")
                        .split("/")
                        .joinToString("/") { segment ->
                            // Convert PascalCase to kebab-case
                            segment.replace(Regex("([a-z])([A-Z])")) { match ->
                                "${match.groupValues[1]}-${match.groupValues[2]}"
                            }.lowercase()
                        }
                }
                urlPath
            }
            .sorted()
            .toList()

        val today = LocalDate.now().toString()
        val sitemap = buildString {
            appendLine("""<?xml version="1.0" encoding="UTF-8"?>""")
            appendLine("""<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">""")
            for (path in pageFiles) {
                val (changefreq, priority) = pageMetadata[path] ?: ("monthly" to "0.5")
                appendLine("  <url>")
                appendLine("    <loc>$baseUrl$path</loc>")
                appendLine("    <lastmod>$today</lastmod>")
                appendLine("    <changefreq>$changefreq</changefreq>")
                appendLine("    <priority>$priority</priority>")
                appendLine("  </url>")
            }
            appendLine("</urlset>")
        }
        outputFile.asFile.writeText(sitemap)
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
    into(layout.projectDirectory.dir("src/jsMain/resources/public/api-docs"))
}

tasks.configureEach {
    if (name == "jsProcessResources") {
        dependsOn(generateLlmsTxt)
        dependsOn(generateLlmsFullTxt)
        dependsOn(generateSitemap)
        dependsOn(copyEditorWasm)
        dependsOn(copyDokkaHtml)
    }
}

kotlin {
    configAsKobwebApplication("website")

    sourceSets {
        jsMain.dependencies {
            implementation(websiteLibs.compose.runtime)
            implementation(websiteLibs.compose.html.core)
            implementation(websiteLibs.compose.viewmodel)
            implementation(websiteLibs.kobweb.core)
            implementation(websiteLibs.kobweb.silk)
            implementation(websiteLibs.silk.icons.fa)
            implementation(websiteLibs.dev.tonholo.s2c)
            implementation(project(":worker"))
            implementation(npm("jszip", websiteLibs.versions.jszip.get()))
        }
    }
}
