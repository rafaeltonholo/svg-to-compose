package dev.tonholo.s2c.website.editor

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.tonholo.s2c.domain.IconFileContents
import dev.tonholo.s2c.website.editor.mapper.toImageVector
import kotlinx.browser.window
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.float
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.w3c.dom.MessageEvent
import org.w3c.dom.events.Event

private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

/** Root composable that renders the current [previewState] as a Compose ImageVector. */
@Composable
internal fun PreviewApp() {
    var previewState by remember { mutableStateOf<IconFileContents?>(null) }
    var zoomScale by remember { mutableFloatStateOf(1f) }
    var panXPx by remember { mutableFloatStateOf(0f) }
    var panYPx by remember { mutableFloatStateOf(0f) }

    OnMessageEffect(
        onIconData = { previewState = it },
        onZoom = { level, px, py ->
            zoomScale = level
            panXPx = px
            panYPx = py
        },
    )

    Surface(modifier = Modifier.fillMaxSize()) {
        val iconFileContents = previewState
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Crossfade(
                targetState = iconFileContents,
            ) { contents ->
                if (contents != null) {
                    val imageVector = contents.toImageVector()
                    val density = LocalDensity.current
                    Image(
                        imageVector = imageVector,
                        contentDescription = null,
                        modifier = Modifier
                            // Allow the Image to exceed its parent bounds when
                            // zoomed in, so the vector is re-rendered at the
                            // larger resolution instead of scaling a raster.
                            .wrapContentSize(unbounded = true)
                            .size(
                                width = maxWidth * zoomScale,
                                height = maxHeight * zoomScale,
                            )
                            .aspectRatio(imageVector.viewportWidth / imageVector.viewportHeight)
                            .graphicsLayer {
                                // Convert CSS px pan offset to Compose px
                                with(density) {
                                    translationX = panXPx.dp.toPx() * zoomScale
                                    translationY = panYPx.dp.toPx() * zoomScale
                                }
                            },
                    )
                } else {
                    Text(
                        text = "Convert to compare",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalWasmJsInterop::class)
private fun OnMessageEffect(
    onIconData: (IconFileContents) -> Unit,
    onZoom: (level: Float, panX: Float, panY: Float) -> Unit,
) {
    val currentOnIconData by rememberUpdatedState(onIconData)
    val currentOnZoom by rememberUpdatedState(onZoom)
    DisposableEffect(window) {
        val expectedOrigin = window.location.origin
        val listener: (Event) -> Unit = { event ->
            handleMessageEvent(event, expectedOrigin, currentOnIconData, currentOnZoom)
        }
        window.addEventListener("message", listener)
        onDispose {
            window.removeEventListener("message", listener)
        }
    }
}

@OptIn(ExperimentalWasmJsInterop::class)
private fun handleMessageEvent(
    event: Event,
    expectedOrigin: String,
    onIconData: (IconFileContents) -> Unit,
    onZoom: (level: Float, panX: Float, panY: Float) -> Unit,
) {
    val messageEvent = event as? MessageEvent ?: return
    val data = messageEvent.data
    val jsonString = data?.toString().orEmpty()
    if (messageEvent.origin != expectedOrigin || jsonString.isEmpty() || !jsonString.startsWith("{")) return

    try {
        val jsonElement = json.parseToJsonElement(jsonString)
        val obj = jsonElement.jsonObject
        if (obj["type"]?.jsonPrimitive?.content == "zoom") {
            val level = obj["level"]?.jsonPrimitive?.float
            if (level != null) {
                val px = obj["panX"]?.jsonPrimitive?.float ?: 0f
                val py = obj["panY"]?.jsonPrimitive?.float ?: 0f
                onZoom(level, px, py)
            }
        } else {
            onIconData(json.decodeFromJsonElement<IconFileContents>(jsonElement))
        }
    } catch (e: SerializationException) {
        println("Error parsing message: ${e.message}")
    } catch (e: IllegalArgumentException) {
        println("Invalid message content: ${e.message}")
    }
}
