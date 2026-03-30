package dev.tonholo.s2c.website.components.atoms

import dev.tonholo.s2c.website.config.BuildConfig

/**
 * Represents a JSON-LD structured data type for SEO.
 */
sealed interface StructuredDataType {
    fun toJsonLd(): String
}

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

data class BreadcrumbListStructuredData(
    val items: List<BreadcrumbItem>,
) : StructuredDataType {
    data class BreadcrumbItem(
        val name: String,
        val url: String,
    )

    override fun toJsonLd(): String {
        val itemsJson = items.mapIndexed { index, item ->
            """
            |    {
            |      "@type": "ListItem",
            |      "position": ${index + 1},
            |      "name": "${item.name}",
            |      "item": "${item.url}"
            |    }
            """.trimMargin()
        }.joinToString(",\n")

        return """
            |{
            |  "@context": "https://schema.org",
            |  "@type": "BreadcrumbList",
            |  "itemListElement": [
            |$itemsJson
            |  ]
            |}
        """.trimMargin()
    }
}

data class FAQPageStructuredData(
    val questions: List<QuestionAnswer>,
) : StructuredDataType {
    data class QuestionAnswer(
        val question: String,
        val answer: String,
    )

    override fun toJsonLd(): String {
        val questionsJson = questions.joinToString(",\n") { qa ->
            """
            |    {
            |      "@type": "Question",
            |      "name": "${qa.question.escapeJsonString()}",
            |      "acceptedAnswer": {
            |        "@type": "Answer",
            |        "text": "${qa.answer.escapeJsonString()}"
            |      }
            |    }
            """.trimMargin()
        }

        return """
            |{
            |  "@context": "https://schema.org",
            |  "@type": "FAQPage",
            |  "mainEntity": [
            |$questionsJson
            |  ]
            |}
        """.trimMargin()
    }
}

data class HowToStructuredData(
    val name: String,
    val description: String,
    val steps: List<HowToStep>,
) : StructuredDataType {
    data class HowToStep(
        val name: String,
        val text: String,
    )

    override fun toJsonLd(): String {
        val stepsJson = steps.mapIndexed { index, step ->
            """
            |    {
            |      "@type": "HowToStep",
            |      "position": ${index + 1},
            |      "name": "${step.name.escapeJsonString()}",
            |      "text": "${step.text.escapeJsonString()}"
            |    }
            """.trimMargin()
        }.joinToString(",\n")

        return """
            |{
            |  "@context": "https://schema.org",
            |  "@type": "HowTo",
            |  "name": "${name.escapeJsonString()}",
            |  "description": "${description.escapeJsonString()}",
            |  "step": [
            |$stepsJson
            |  ]
            |}
        """.trimMargin()
    }
}

internal fun String.escapeJsonString(): String =
    replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\n", "\\n")
        .replace("\r", "\\r")
        .replace("\t", "\\t")
