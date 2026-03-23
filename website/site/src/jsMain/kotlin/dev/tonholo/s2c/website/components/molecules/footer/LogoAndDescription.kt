package dev.tonholo.s2c.website.components.molecules.footer

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import dev.tonholo.s2c.website.components.atoms.InlineCode
import dev.tonholo.s2c.website.theme.SiteTheme
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Composable
fun LogoAndDescription(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.gap(SiteTheme.dimensions.size.Sm),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.gap(SiteTheme.dimensions.size.Sm),
        ) {
            Image(
                src = "/images/s2c-icon.svg",
                modifier = Modifier.size(1.25.cssRem),
            )
            SpanText(
                "svg-to-compose",
                modifier = Modifier
                    .fontWeight(FontWeight.SemiBold)
                    .fontSize(0.875.cssRem),
            )
        }

        Span(
            attrs = Modifier
                .fontSize(0.8.cssRem)
                .color(SiteTheme.palette.onSurfaceVariant)
                .maxWidth(28.cssRem)
                .toAttrs(),
        ) {
            Text(
                "Open-source Kotlin Multiplatform tool that converts SVG and AVG files into Jetpack Compose ",
            )
            InlineCode("ImageVector")
            Text(" code.")
        }
    }
}
