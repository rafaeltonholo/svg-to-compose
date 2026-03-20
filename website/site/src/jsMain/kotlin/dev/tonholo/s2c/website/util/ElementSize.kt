package dev.tonholo.s2c.website.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.w3c.dom.HTMLElement

/**
 * Observed dimensions of an element, updated via [ResizeObserver].
 * Defaults to (0, 0) before the first observation fires.
 */
data class ElementSize(val width: Int, val height: Int)

/**
 * Observes [element] with [ResizeObserver] and returns its current
 * content-box dimensions. Re-observes whenever [element] changes and
 * disconnects on dispose.
 */
@Composable
fun rememberElementSize(element: HTMLElement?): ElementSize {
    var size by remember { mutableStateOf(ElementSize(width = 0, height = 0)) }

    DisposableEffect(element) {
        if (element == null) {
            return@DisposableEffect onDispose { }
        }
        val observer = ResizeObserver { entries ->
            val entry = entries.firstOrNull() ?: return@ResizeObserver
            size = ElementSize(
                width = entry.contentRect.width.toInt(),
                height = entry.contentRect.height.toInt(),
            )
        }
        observer.observe(element)
        onDispose { observer.disconnect() }
    }

    return size
}

// Browser ResizeObserver API — not yet in kotlin-wrappers
private external class ResizeObserver(callback: (Array<ResizeObserverEntry>) -> Unit) {
    fun observe(target: org.w3c.dom.Element)
    fun disconnect()
}

private external interface ResizeObserverEntry {
    val contentRect: DOMRectReadOnly
}

private external interface DOMRectReadOnly {
    val width: Double
    val height: Double
}
