package dev.tonholo.s2c.website.components.atoms

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import dev.tonholo.s2c.website.components.layouts.PageLayoutData
import kotlinx.browser.document

internal const val BASE_URL = "https://www.svgtocompose.com"
private const val SITE_NAME = "SVG to Compose"
private const val OG_IMAGE_WIDTH = "1200"
private const val OG_IMAGE_HEIGHT = "630"
private const val JSON_LD_SCRIPT_ID = "seo-json-ld"

@Composable
fun SeoHead(data: PageLayoutData, path: String) {
    val fullTitle = "$SITE_NAME - ${data.title}"
    val canonicalPath = data.canonicalPath ?: path
    val canonicalUrl = "$BASE_URL$canonicalPath"
    val ogImageUrl = "$BASE_URL${data.ogImage}"

    LaunchedEffect(data, path) {
        document.title = fullTitle

        // Meta description
        upsertMeta(attributeName = "name", attributeValue = "description", content = data.description)

        // Open Graph
        upsertMeta(attributeName = "property", attributeValue = "og:title", content = fullTitle)
        upsertMeta(attributeName = "property", attributeValue = "og:description", content = data.description)
        upsertMeta(attributeName = "property", attributeValue = "og:type", content = data.ogType)
        upsertMeta(attributeName = "property", attributeValue = "og:url", content = canonicalUrl)
        upsertMeta(attributeName = "property", attributeValue = "og:image", content = ogImageUrl)
        upsertMeta(attributeName = "property", attributeValue = "og:image:width", content = OG_IMAGE_WIDTH)
        upsertMeta(attributeName = "property", attributeValue = "og:image:height", content = OG_IMAGE_HEIGHT)
        upsertMeta(attributeName = "property", attributeValue = "og:site_name", content = SITE_NAME)

        // Twitter Card
        upsertMeta(attributeName = "name", attributeValue = "twitter:card", content = "summary_large_image")
        upsertMeta(attributeName = "name", attributeValue = "twitter:title", content = fullTitle)
        upsertMeta(attributeName = "name", attributeValue = "twitter:description", content = data.description)
        upsertMeta(attributeName = "name", attributeValue = "twitter:image", content = ogImageUrl)

        // Canonical link
        upsertCanonicalLink(canonicalUrl)

        // LLMs discovery links
        upsertLink(rel = "llms", href = "/llms.txt")
        upsertLink(rel = "llms-full", href = "/llms-full.txt")

        // JSON-LD structured data
        val breadcrumbs = buildBreadcrumbs(path)
        upsertJsonLd(listOf(breadcrumbs) + data.structuredData)
    }
}

private fun buildBreadcrumbs(path: String): BreadcrumbListStructuredData {
    val segments = path.trim('/').split('/').filter { it.isNotEmpty() }
    val items = mutableListOf(
        BreadcrumbListStructuredData.BreadcrumbItem(
            name = "Home",
            url = BASE_URL,
        ),
    )
    var currentPath = ""
    for (segment in segments) {
        currentPath += "/$segment"
        val name = segment.replaceFirstChar { it.uppercase() }
            .replace("-", " ")
        items.add(
            BreadcrumbListStructuredData.BreadcrumbItem(
                name = name,
                url = "$BASE_URL$currentPath",
            ),
        )
    }
    return BreadcrumbListStructuredData(items)
}

private fun upsertMeta(attributeName: String, attributeValue: String, content: String) {
    val head = document.head ?: return
    val selector = "meta[$attributeName='$attributeValue']"
    val existing = head.querySelector(selector)
    if (existing != null) {
        existing.setAttribute("content", content)
    } else {
        val meta = document.createElement("meta")
        meta.setAttribute(attributeName, attributeValue)
        meta.setAttribute("content", content)
        head.appendChild(meta)
    }
}

private fun upsertLink(rel: String, href: String) {
    val head = document.head ?: return
    val existing = head.querySelector("link[rel='$rel']")
    if (existing != null) {
        existing.setAttribute("href", href)
    } else {
        val link = document.createElement("link")
        link.setAttribute("rel", rel)
        link.setAttribute("href", href)
        head.appendChild(link)
    }
}

private fun upsertCanonicalLink(href: String) {
    val head = document.head ?: return
    val existing = head.querySelector("link[rel='canonical']")
    if (existing != null) {
        existing.setAttribute("href", href)
    } else {
        val link = document.createElement("link")
        link.setAttribute("rel", "canonical")
        link.setAttribute("href", href)
        head.appendChild(link)
    }
}

private fun upsertJsonLd(structuredData: List<StructuredDataType>) {
    val head = document.head ?: return
    val existing = head.querySelector("#$JSON_LD_SCRIPT_ID")
    if (structuredData.isEmpty()) {
        existing?.let { head.removeChild(it) }
        return
    }

    val jsonLdContent = if (structuredData.size == 1) {
        structuredData.first().toJsonLd()
    } else {
        buildJsonLdArray(structuredData)
    }

    if (existing != null) {
        existing.textContent = jsonLdContent
    } else {
        val script = document.createElement("script")
        script.setAttribute("type", "application/ld+json")
        script.id = JSON_LD_SCRIPT_ID
        script.textContent = jsonLdContent
        head.appendChild(script)
    }
}

private fun buildJsonLdArray(items: List<StructuredDataType>): String = buildString {
    appendLine("[")
    items.forEachIndexed { index, item ->
        append(item.toJsonLd())
        if (index < items.lastIndex) appendLine(",") else appendLine()
    }
    append("]")
}
