package dev.tonholo.s2c.website.components.molecules.footer

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.GradientTextStyle
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.cssRem

@Composable
fun LogoAndDescription() {
    Column(
        modifier = Modifier.gap(0.5.cssRem),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.gap(0.5.cssRem),
        ) {
            SpanText(
                "s2c",
                modifier = GradientTextStyle.toModifier()
                    .fontWeight(FontWeight.Bold)
                    .fontSize(1.125.cssRem)
                    .fontFamily("JetBrains Mono", "monospace"),
            )
            SpanText(
                "svg-to-compose",
                modifier = Modifier
                    .fontWeight(FontWeight.SemiBold)
                    .fontSize(0.875.cssRem),
            )
        }
        SpanText(
            "Open-source Kotlin Multiplatform tool that converts\n" +
                "SVG and AVG files into Jetpack Compose\n" +
                "`ImageVector` code.",
            modifier = Modifier
                .fontSize(0.8.cssRem)
                .color(ColorMode.current.toSitePalette().muted)
                .maxWidth(28.cssRem),
        )
    }
}
