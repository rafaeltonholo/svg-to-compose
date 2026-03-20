package dev.tonholo.s2c.website.editor

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.tonholo.s2c.domain.IconFileContents
import dev.tonholo.s2c.website.editor.mapper.toImageVector
import kotlinx.browser.window
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.w3c.dom.MessageEvent
import org.w3c.dom.events.Event

/** Mutable state holding the latest [IconFileContents] received via postMessage. */

private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

/** Root composable that renders the current [previewState] as a Compose ImageVector. */
@OptIn(ExperimentalWasmJsInterop::class)
@Composable
internal fun PreviewApp() {
    var previewState by mutableStateOf<IconFileContents?>(null)
    OnMessageEffect { iconFileContents ->
        previewState = iconFileContents
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        val iconFileContents = previewState
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            AnimatedContent(iconFileContents) { iconFileContents ->
                if (iconFileContents != null) {
                    val imageVector = iconFileContents.toImageVector()
                    Image(
                        imageVector = imageVector,
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalWasmJsInterop::class)
private fun OnMessageEffect(onMessage: (IconFileContents) -> Unit) {
    DisposableEffect(window) {
        val expectedOrigin = window.location.origin
        fun onMessageEvent(event: Event) {
            val messageEvent = event as? MessageEvent ?: return
            if (messageEvent.origin != expectedOrigin) return
            val data = messageEvent.data ?: return
            val jsonString = data.toString()
            if (jsonString.isNotEmpty() && jsonString.startsWith("{")) {
                try {
                    onMessage(json.decodeFromString<IconFileContents>(jsonString))
                } catch (e: SerializationException) {
                    println("Error while parsing json content to IconFileContents: ${e.message}")
                    println("MALFORMED JSON: $jsonString")
                } catch (e: IllegalArgumentException) {
                    println("The given json content is not a valid instance of IconFileContents: ${e.message}")
                    println("MALFORMED JSON: $jsonString")
                }
            }
        }

        window.addEventListener("message", ::onMessageEvent)

        onDispose {
            window.removeEventListener("message", ::onMessageEvent)
        }
    }
}
