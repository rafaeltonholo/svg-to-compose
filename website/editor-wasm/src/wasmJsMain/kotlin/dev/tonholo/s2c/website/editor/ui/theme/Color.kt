package dev.tonholo.s2c.website.editor.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

internal val LightColorScheme = lightColorScheme(
    background = Color(color = 0xFFFAFAF9),
    surface = Color(color = 0xFFF5F5F4),
    surfaceVariant = Color(color = 0xFFECECE9),
    onBackground = Color(color = 0xFF1C1917),
    onSurface = Color(color = 0xFF1C1917),
    onSurfaceVariant = Color(color = 0xFF78716C),
    outline = Color(color = 0xFFD6D3D1),
    outlineVariant = Color(color = 0xFFA8A29E),
    primary = Color(color = 0xFF7C3AED),
    onPrimary = Color.White,
    primaryContainer = Color(color = 0xFF6D28D9),
)

internal val DarkColorScheme = darkColorScheme(
    background = Color(color = 0xFF0C0A09),
    surface = Color(color = 0xFF1C1917),
    surfaceVariant = Color(color = 0xFF141210),
    onBackground = Color(color = 0xFFE7E5E4),
    onSurface = Color(color = 0xFFE7E5E4),
    onSurfaceVariant = Color(color = 0xFFA8A29E),
    outline = Color(color = 0xFF292524),
    outlineVariant = Color(color = 0xFF44403C),
    primary = Color(color = 0xFF8B5CF6),
    onPrimary = Color.White,
    primaryContainer = Color(color = 0xFFA78BFA),
)
