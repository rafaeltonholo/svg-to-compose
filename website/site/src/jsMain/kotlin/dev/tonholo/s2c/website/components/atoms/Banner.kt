package dev.tonholo.s2c.website.components.atoms

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.StyleVariable
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderBottom
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.setVariable
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.ComponentKind
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.theme.SiteTheme
import org.jetbrains.compose.web.css.CSSLengthValue
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px

sealed interface BannerKind : ComponentKind

object BannerVars {
    val ContentColor by StyleVariable<Color>(prefix = "banner")
    val ContainerColor by StyleVariable<Color>(prefix = "banner")
    val BorderColor by StyleVariable<Color>(prefix = "banner")
    val FontSize by StyleVariable<CSSLengthValue>(prefix = "banner")
}

val BannerStyle = CssStyle<BannerKind> {
    base {
        Modifier
            .fillMaxWidth()
            .padding(topBottom = SiteTheme.dimensions.size.Sm, leftRight = SiteTheme.dimensions.size.Lg)
            .backgroundColor(BannerVars.ContainerColor.value())
            .color(BannerVars.ContentColor.value())
            .borderBottom(
                width = 1.px,
                style = LineStyle.Solid,
                color = BannerVars.BorderColor.value(),
            )
            .fontSize(BannerVars.FontSize.value(0.85.cssRem))
            .fontWeight(FontWeight.Medium)
            .textAlign(TextAlign.Center)
            .justifyContent(JustifyContent.Center)
    }
}

@Composable
fun Banner(
    text: String,
    contentColor: Color,
    containerColor: Color,
    borderColor: Color,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier = BannerStyle
            .toModifier()
            .setVariable(BannerVars.ContentColor, contentColor)
            .setVariable(BannerVars.ContainerColor, containerColor)
            .setVariable(BannerVars.BorderColor, borderColor)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.gap(SiteTheme.dimensions.size.Sm),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            leadingIcon?.invoke()
            SpanText(text)
        }
    }
}
