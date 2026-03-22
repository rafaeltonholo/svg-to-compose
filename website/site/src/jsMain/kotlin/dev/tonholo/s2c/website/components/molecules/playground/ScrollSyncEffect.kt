package dev.tonholo.s2c.website.components.molecules.playground

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLTextAreaElement
import org.w3c.dom.events.Event

/**
 * Synchronises the scroll position of a [textareaElement] with a
 * [backdropElement] so that the Shiki-highlighted backdrop always
 * stays aligned with the editable textarea.
 */
@Composable
fun ScrollSyncEffect(textareaElement: HTMLTextAreaElement?, backdropElement: HTMLElement?) {
    DisposableEffect(textareaElement, backdropElement) {
        val textarea = textareaElement ?: return@DisposableEffect onDispose { }
        val backdrop = backdropElement ?: return@DisposableEffect onDispose { }
        val onScroll: (Event) -> Unit = {
            backdrop.scrollTop = textarea.scrollTop
            backdrop.scrollLeft = textarea.scrollLeft
        }
        textarea.addEventListener("scroll", onScroll)
        onDispose {
            textarea.removeEventListener("scroll", onScroll)
        }
    }
}
