package dev.tonholo.s2c.website.editor

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.tonholo.s2c.website.editor.mapper.toImageVector

/** Root composable that renders the current [previewState] as a Compose ImageVector. */
@Composable
fun PreviewApp() {
    val iconFileContents = previewState
    if (iconFileContents != null) {
        val imageVector = iconFileContents.toImageVector()
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                imageVector = imageVector,
                contentDescription = null,
            )
        }
    }
}
