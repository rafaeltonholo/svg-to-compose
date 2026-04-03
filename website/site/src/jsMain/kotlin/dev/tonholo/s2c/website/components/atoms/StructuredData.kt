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
        |{
        |  "@context": "https://schema.org",
        |  "@type": "WebSite",
        |  "name": "SVG to Compose",
        |  "url": "${BASE_URL.escapeJsonString()}",
        |  "description": "Convert SVG and Android Vector Drawables into Jetpack Compose ImageVector code.",
        |  "publisher": {
        |    "@type": "Person",
        |    "name": "Rafael Tonholo",
        |    "url": "https://rafael.tonholo.dev"
        |  },
        |  "sameAs": ["https://github.com/rafaeltonholo/svg-to-compose"]
        |}
    """.trimMargin()
}

data object SoftwareApplicationStructuredData : StructuredDataType {
    // language=json
    override fun toJsonLd(): String = """
        |{
        |  "@context": "https://schema.org",
        |  "@type": "SoftwareApplication",
        |  "name": "SVG to Compose",
        |  "applicationCategory": "DeveloperApplication",
        |  "operatingSystem": "macOS, Linux, Windows",
        |  "offers": {
        |    "@type": "Offer",
        |    "price": "0",
        |    "priceCurrency": "USD"
        |  },
        |  "softwareVersion": "${BuildConfig.VERSION.escapeJsonString()}",
        |  "url": "${BASE_URL.escapeJsonString()}",
        |  "description": "Convert SVG and Android Vector Drawables into Jetpack Compose ImageVector code. Available as a CLI tool, Gradle plugin, and Kotlin Multiplatform library.",
        |  "downloadUrl": "https://github.com/rafaeltonholo/svg-to-compose",
        |  "license": "https://opensource.org/licenses/MIT"
        |}
    """.trimMargin()
}

data class BreadcrumbListStructuredData(val items: List<BreadcrumbItem>) : StructuredDataType {
    data class BreadcrumbItem(val name: String, val url: String)

    override fun toJsonLd(): String {
        val itemsJson = items.mapIndexed { index, item ->
            // language=json
            """
            |    {
            |      "@type": "ListItem",
            |      "position": ${index + 1},
            |      "name": "${item.name.escapeJsonString()}",
            |      "item": "${item.url.escapeJsonString()}"
            |    }
            """.trimMargin()
        }.joinToString(",\n")

        // language=json
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

data class FAQPageStructuredData(val questions: List<QuestionAnswer>) : StructuredDataType {
    data class QuestionAnswer(val question: String, val answer: String)

    override fun toJsonLd(): String {
        val questionsJson = questions.joinToString(",\n") { qa ->
            // language=json
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

        // language=json
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

data class HowToStructuredData(val name: String, val description: String, val steps: List<HowToStep>) :
    StructuredDataType {
    data class HowToStep(val name: String, val text: String)

    override fun toJsonLd(): String {
        val stepsJson = steps.mapIndexed { index, step ->
            // language=json
            """
            |    {
            |      "@type": "HowToStep",
            |      "position": ${index + 1},
            |      "name": "${step.name.escapeJsonString()}",
            |      "text": "${step.text.escapeJsonString()}"
            |    }
            """.trimMargin()
        }.joinToString(",\n")

        // language=json
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

private val CONTROL_CHAR_REGEX = Regex("[\\u0000-\\u001F]")

internal fun String.escapeJsonString(): String = replace("\\", "\\\\")
    .replace("\"", "\\\"")
    .replace("\n", "\\n")
    .replace("\r", "\\r")
    .replace("\t", "\\t")
    .replace("\u000C", "\\f")
    .replace("\u0008", "\\b")
    .replace(CONTROL_CHAR_REGEX) { match ->
        "\\u${match.value[0].code.toString(radix = 16).padStart(length = 4, padChar = '0')}"
    }
