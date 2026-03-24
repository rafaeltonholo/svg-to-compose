package dev.tonholo.s2c.website.components.atoms

import dev.tonholo.s2c.website.config.BuildConfig

/**
 * Represents a JSON-LD structured data type for SEO.
 */
sealed interface StructuredDataType {
    fun toJsonLd(): String
}

private const val BASE_URL = "https://svgtocompose.tonholo.dev"

data object WebSiteStructuredData : StructuredDataType {
    // language=json
    override fun toJsonLd(): String = """
        {
          "@context": "https://schema.org",
          "@type": "WebSite",
          "name": "SVG to Compose",
          "url": "$BASE_URL",
          "description": "Convert SVG and Android Vector Drawables into Jetpack Compose ImageVector code.",
          "publisher": {
            "@type": "Person",
            "name": "Rafael Tonholo",
            "url": "https://rafael.tonholo.dev"
          }
        }
    """.trimIndent()
}

data object SoftwareApplicationStructuredData : StructuredDataType {
    // language=json
    override fun toJsonLd(): String = """
        {
          "@context": "https://schema.org",
          "@type": "SoftwareApplication",
          "name": "SVG to Compose",
          "applicationCategory": "DeveloperApplication",
          "operatingSystem": "macOS, Linux, Windows",
          "offers": {
            "@type": "Offer",
            "price": "0",
            "priceCurrency": "USD"
          },
          "softwareVersion": "${BuildConfig.VERSION}",
          "url": "$BASE_URL",
          "description": "Convert SVG and Android Vector Drawables into Jetpack Compose ImageVector code. Available as a CLI tool, Gradle plugin, and Kotlin Multiplatform library.",
          "downloadUrl": "https://github.com/rafaeltonholo/svg-to-compose",
          "license": "https://opensource.org/licenses/MIT"
        }
    """.trimIndent()
}
