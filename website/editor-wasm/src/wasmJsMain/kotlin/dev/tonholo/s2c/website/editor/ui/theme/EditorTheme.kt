package dev.tonholo.s2c.website.editor.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun EditorTheme(content: @Composable () -> Unit) {
    val colorScheme = if (isSystemInDarkTheme()) DarkColorScheme else LightColorScheme
    MaterialTheme(content = content, colorScheme = colorScheme, typography = Typography)
}
