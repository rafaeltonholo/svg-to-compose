package dev.tonholo.s2c.website.components.molecules

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.gridTemplateColumns
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaApple
import com.varabyte.kobweb.silk.components.icons.fa.FaLinux
import com.varabyte.kobweb.silk.components.icons.fa.FaWindows
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.SiteTheme
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.fr
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

val PlatformGridStyle = CssStyle {
    base {
        Modifier
            .display(DisplayStyle.Grid)
            .gap(0.5.cssRem)
            .fillMaxWidth()
            .gridTemplateColumns { repeat(count = 2) { size(1.fr) } }
    }
    Breakpoint.SM {
        Modifier.gridTemplateColumns { repeat(count = 3) { size(1.fr) } }
    }
    Breakpoint.MD {
        Modifier.gridTemplateColumns { repeat(count = 5) { size(1.fr) } }
    }
}

val PlatformItemStyle = CssStyle.base {
    val palette = colorMode.toSitePalette()
    Modifier
        .backgroundColor(palette.surfaceAlt)
        .border(1.px, LineStyle.Solid, palette.border)
        .borderRadius(0.5.cssRem)
        .padding(0.75.cssRem)
        .textAlign(TextAlign.Center)
        .fontSize(0.8.cssRem)
}

data class PlatformInfo(val icon: @Composable () -> Unit, val name: String, val subtitle: String)

private val platforms = listOf(
    PlatformInfo(
        icon = {
            FaApple(modifier = Modifier.display(DisplayStyle.Block).fontSize(1.5.cssRem))
        },
        "macOS ARM64",
        "Apple Silicon",
    ),
    PlatformInfo(
        icon = {
            FaApple(modifier = Modifier.display(DisplayStyle.Block).fontSize(1.5.cssRem))
        },
        "macOS x64",
        "Intel",
    ),
    PlatformInfo(
        icon = {
            FaLinux(modifier = Modifier.display(DisplayStyle.Block).fontSize(1.5.cssRem))
        },
        "Linux x64",
        "Ubuntu / Debian",
    ),
    PlatformInfo(
        icon = {
            FaWindows(modifier = Modifier.display(DisplayStyle.Block).fontSize(1.5.cssRem))
        },
        "Windows x64",
        "Native",
    ),
    PlatformInfo(
        icon = {
            FaWindows(modifier = Modifier.display(DisplayStyle.Block).fontSize(1.5.cssRem))
        },
        "Windows WSL",
        "WSL2",
    ),
)

@Composable
fun PlatformGrid() {
    Div(attrs = PlatformGridStyle.toModifier().toAttrs()) {
        platforms.forEach { platform ->
            Column(
                modifier = PlatformItemStyle
                    .toModifier()
                    .alignItems(AlignItems.Center)
                    .padding(topBottom = 1.cssRem, leftRight = 0.75.cssRem),
                verticalArrangement = Arrangement.spacedBy(0.25.cssRem),
            ) {
                platform.icon()
                SpanText(
                    platform.name,
                    modifier = Modifier.fontWeight(FontWeight.Medium).fontSize(0.85.cssRem),
                )
                SpanText(
                    platform.subtitle,
                    modifier = Modifier.fontSize(0.7.cssRem).color(SiteTheme.palette.muted),
                )
            }
        }
    }
}
