package dev.tonholo.s2c.website.editor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import dev.tonholo.s2c.domain.IconFileContents
import dev.tonholo.s2c.website.editor.ui.theme.EditorTheme
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.w3c.dom.MessageEvent

/** Mutable state holding the latest [IconFileContents] received via postMessage. */
internal var previewState by mutableStateOf<IconFileContents?>(null)

private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

/** Entry point for the WASM preview module; registers a postMessage listener and mounts [ComposeViewport]. */
@OptIn(ExperimentalComposeUiApi::class, ExperimentalWasmJsInterop::class)
fun main() {
    val expectedOrigin = window.location.origin
    window.addEventListener("message") { event ->
        val messageEvent = event as? MessageEvent ?: return@addEventListener
        if (messageEvent.origin != expectedOrigin) return@addEventListener
        val data = messageEvent.data ?: return@addEventListener
        val jsonString = data.toString()
        if (jsonString.isNotEmpty() && jsonString.startsWith("{")) {
            try {
                previewState = json.decodeFromString<IconFileContents>(jsonString)
            } catch (e: SerializationException) {
                println("Error while parsing json content to IconFileContents: ${e.message}")
                println("MALFORMED JSON: $jsonString")
            } catch (e: IllegalArgumentException) {
                println("The given json content is not a valid instance of IconFileContents: ${e.message}")
                println("MALFORMED JSON: $jsonString")
            }
        }
    }

    val body = document.body ?: return
    ComposeViewport(body) {
        EditorTheme {
            PreviewApp()
        }
    }
}
