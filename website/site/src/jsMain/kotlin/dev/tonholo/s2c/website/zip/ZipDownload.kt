package dev.tonholo.s2c.website.zip

import dev.tonholo.s2c.website.state.playground.batch.BatchConversionResult
import kotlinx.browser.document
import kotlinx.coroutines.await
import org.w3c.dom.HTMLAnchorElement
import org.w3c.dom.url.URL

/**
 * Creates a zip archive from successful [BatchConversionResult]s
 * and triggers a browser download.
 *
 * Uses JSZip with STORE compression (no compression) for speed.
 */
internal suspend fun downloadAsZip(results: List<BatchConversionResult>) {
    val zip = JSZip()
    results.forEach { result ->
        val kotlinCode = result.kotlinCode ?: return@forEach
        val relativePath = result.relativePath
        val iconName = result.fileName
            .substringBeforeLast(".")
            .replaceFirstChar { it.uppercase() }
        val filePath = if (relativePath.isNotEmpty()) {
            "$relativePath/$iconName.kt"
        } else {
            "$iconName.kt"
        }
        zip.file(filePath, kotlinCode)
    }
    val blob = zip.generateAsync(js("({type:'blob'})")).await()
    val url = URL.createObjectURL(blob)
    val anchor = document.createElement("a") as HTMLAnchorElement
    anchor.href = url
    anchor.download = "icons.zip"
    anchor.click()
    URL.revokeObjectURL(url)
}
