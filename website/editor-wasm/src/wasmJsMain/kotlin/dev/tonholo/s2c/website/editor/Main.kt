package dev.tonholo.s2c.website.editor

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import dev.tonholo.s2c.website.editor.ui.theme.EditorTheme
import kotlinx.browser.document


/** Entry point for the WASM preview module; registers a postMessage listener and mounts [ComposeViewport]. */
@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val body = document.body ?: return
    ComposeViewport(
        viewportContainer = body,
        configure = {
            this.isA11YEnabled = true
        },
    ) {
        EditorTheme {
            PreviewApp()
        }
    }
}
