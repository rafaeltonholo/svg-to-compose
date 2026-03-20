package dev.tonholo.s2c.website

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.theme.colors.palette.background
import com.varabyte.kobweb.silk.theme.colors.palette.color

object SiteTheme {
    const val VERSION = "v2.1.2"

    val palette: SitePalette
        @Composable
        @ReadOnlyComposable
        get() = ColorMode.current.toSitePalette()
}

data class SitePalette(
    val background: Color,
    val surface: Color,
    val surfaceVariant: Color,
    val onBackground: Color,
    val onSurface: Color,
    val onSurfaceVariant: Color,
    val outline: Color,
    val outlineVariant: Color,
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val error: Color,
)

object SitePalettes {
    val light = SitePalette(
        background = Color.rgb(value = 0xFAFAF9),
        surface = Color.rgb(value = 0xF5F5F4),
        surfaceVariant = Color.rgb(value = 0xECECE9),
        onBackground = Color.rgb(value = 0x1C1917),
        onSurface = Color.rgb(value = 0x1C1917),
        onSurfaceVariant = Color.rgb(value = 0x78716C),
        outline = Color.rgb(value = 0xD6D3D1),
        outlineVariant = Color.rgb(value = 0xA8A29E),
        primary = Color.rgb(value = 0x7C3AED),
        onPrimary = Colors.White,
        primaryContainer = Color.rgb(value = 0x6D28D9),
        error = Color.rgb(value = 0xDC2626),
    )
    val dark = SitePalette(
        background = Color.rgb(value = 0x0C0A09),
        surface = Color.rgb(value = 0x1C1917),
        surfaceVariant = Color.rgb(value = 0x141210),
        onBackground = Color.rgb(value = 0xE7E5E4),
        onSurface = Color.rgb(value = 0xE7E5E4),
        onSurfaceVariant = Color.rgb(value = 0xA8A29E),
        outline = Color.rgb(value = 0x292524),
        outlineVariant = Color.rgb(value = 0x44403C),
        primary = Color.rgb(value = 0x8B5CF6),
        onPrimary = Colors.White,
        primaryContainer = Color.rgb(value = 0xA78BFA),
        error = Color.rgb(value = 0xF87171),
    )
}

fun ColorMode.toSitePalette(): SitePalette {
    return when (this) {
        ColorMode.LIGHT -> SitePalettes.light
        ColorMode.DARK -> SitePalettes.dark
    }
}

@InitSilk
fun initTheme(ctx: InitSilkContext) {
    ctx.theme.palettes.light.background = SitePalettes.light.background
    ctx.theme.palettes.light.color = SitePalettes.light.onBackground
    ctx.theme.palettes.dark.background = SitePalettes.dark.background
    ctx.theme.palettes.dark.color = SitePalettes.dark.onBackground
}
