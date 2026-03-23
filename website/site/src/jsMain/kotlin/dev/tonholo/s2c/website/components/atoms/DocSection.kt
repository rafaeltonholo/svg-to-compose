package dev.tonholo.s2c.website.components.atoms

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.functions.clamp
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.scrollMargin
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.theme.HeadlineTextStyle
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.theme.toSitePalette
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vw
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Section
import org.jetbrains.compose.web.dom.Text

private const val LEVEL3_LINE_HEIGHT = 1.3

val DocSectionStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .scrollMargin(top = 5.cssRem)
        .padding(bottom = SiteTheme.dimensions.size.Lg)
}

val DocSectionLevel3HeadingStyle = CssStyle.base {
    Modifier
        .fontSize(clamp(1.1.cssRem, 2.5.vw, 1.5.cssRem))
        .fontWeight(FontWeight.SemiBold)
        .lineHeight(LEVEL3_LINE_HEIGHT)
}

@Composable
fun DocSection(
    id: String,
    title: String,
    modifier: Modifier = Modifier,
    level: Int = 2,
    content: @Composable () -> Unit,
) {
    val palette = ColorMode.current.toSitePalette()
    Section(
        attrs = DocSectionStyle.toModifier()
            .id(id)
            .then(modifier)
            .toAttrs(),
    ) {
        when (level) {
            2 -> H2(
                attrs = HeadlineTextStyle.toModifier()
                    .margin(0.px)
                    .color(palette.onBackground)
                    .padding(bottom = SiteTheme.dimensions.size.Md)
                    .toAttrs(),
            ) {
                Text(title)
            }

            else -> H3(
                attrs = DocSectionLevel3HeadingStyle.toModifier()
                    .margin(0.px)
                    .color(palette.onBackground)
                    .padding(bottom = SiteTheme.dimensions.size.Md)
                    .toAttrs(),
            ) {
                Text(title)
            }
        }
        Column(modifier = Modifier.fillMaxWidth().gap(SiteTheme.dimensions.size.Md)) {
            content()
        }
    }
}
