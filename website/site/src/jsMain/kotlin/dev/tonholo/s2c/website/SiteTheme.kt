package dev.tonholo.s2c.website

import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.theme.colors.palette.background
import com.varabyte.kobweb.silk.theme.colors.palette.color

class SitePalette(
    val nearBackground: Color,
    val surface: Color,
    val surfaceAlt: Color,
    val surfaceHeader: Color,
    val border: Color,
    val borderStrong: Color,
    val muted: Color,
    val mutedStrong: Color,
    val brand: Brand,
    val windowChrome: WindowChrome,
) {
    class Brand(
        val purple: Color,
        val purpleDeep: Color,
        val teal: Color,
    )

    class WindowChrome(
        val red: Color = Color.rgb(value = 0xFF5F57),
        val yellow: Color = Color.rgb(value = 0xFEBC2E),
        val green: Color = Color.rgb(value = 0x28C840),
    )
}

data class FeatureColor(
    val text: Color,
    val backgroundAlpha: Float = 0.08f,
    val borderAlpha: Float = 0.2f,
)

object FeatureColors {
    val svgSupport = FeatureColor(text = Color.rgb(value = 0xF07178))
    val androidVector = FeatureColor(text = Color.rgb(value = 0x3DDC84))
    val gradients = FeatureColor(text = Color.rgb(value = 0xFFCB6B))
    val cssStyles = FeatureColor(text = Color.rgb(value = 0x82AAFF))
    val transforms = FeatureColor(text = Color.rgb(value = 0x80DEEA))
    val optimization = FeatureColor(text = Color.rgb(value = 0xCF9AFF))
    val kotlinMultiplatform = FeatureColor(text = Color.rgb(value = 0x7F52FF))
    val gradleIntegration = FeatureColor(text = Color.rgb(value = 0x00D4AA))
    val codeMinification = FeatureColor(text = Color.rgb(value = 0xF78C6C))
    val customNaming = FeatureColor(text = Color.rgb(value = 0xA5D6A7))
    val materialIcons = FeatureColor(text = Color.rgb(value = 0xFF80AB))
    val previewGeneration = FeatureColor(text = Color.rgb(value = 0x89DDFF))
}

object SitePalettes {
    val light = SitePalette(
        nearBackground = Color.rgb(value = 0xF4F6FA),
        surface = Color.rgb(value = 0xFFFFFF),
        surfaceAlt = Color.rgb(value = 0xF3F3F5),
        surfaceHeader = Color.rgb(value = 0xECECF0),
        border = Color.rgba(0, 0, 0, 0.1f),
        borderStrong = Color.rgb(value = 0xD0D0D8),
        muted = Color.rgb(value = 0x717182),
        mutedStrong = Color.rgb(value = 0x546E7A),
        brand = SitePalette.Brand(
            purple = Color.rgb(value = 0x7F52FF),
            purpleDeep = Color.rgb(value = 0x5A30DD),
            teal = Color.rgb(value = 0x00D4AA),
        ),
        windowChrome = SitePalette.WindowChrome(),
    )
    val dark = SitePalette(
        nearBackground = Color.rgb(value = 0x0B0B16),
        surface = Color.rgb(value = 0x0B0B18),
        surfaceAlt = Color.rgb(value = 0x0D0D1A),
        surfaceHeader = Color.rgb(value = 0x12122A),
        border = Color.rgba(127, 82, 255, 0.15f),
        borderStrong = Color.rgb(value = 0x2A2A4A),
        muted = Color.rgb(value = 0x8A8AA0),
        mutedStrong = Color.rgb(value = 0x546E7A),
        brand = SitePalette.Brand(
            purple = Color.rgb(value = 0x7F52FF),
            purpleDeep = Color.rgb(value = 0x5A30DD),
            teal = Color.rgb(value = 0x00D4AA),
        ),
        windowChrome = SitePalette.WindowChrome(),
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
    ctx.theme.palettes.light.background = Color.rgb(value = 0xFFFFFF)
    ctx.theme.palettes.light.color = Colors.Black
    ctx.theme.palettes.dark.background = Color.rgb(value = 0x0A0A12)
    ctx.theme.palettes.dark.color = Color.rgb(value = 0xE2E2F0)
}
