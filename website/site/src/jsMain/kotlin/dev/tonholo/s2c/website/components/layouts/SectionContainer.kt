package dev.tonholo.s2c.website.components.layouts

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flexDirection
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.theme.toSitePalette
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Section

val SectionContainerStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .padding(topBottom = 3.cssRem, leftRight = 1.cssRem)
            .display(DisplayStyle.Flex)
            .flexDirection(FlexDirection.Column)
            .alignItems(AlignItems.Center)
    }
    Breakpoint.SM {
        Modifier.padding(topBottom = 3.cssRem, leftRight = 1.5.cssRem)
    }
    Breakpoint.MD {
        Modifier.padding(topBottom = 4.cssRem, leftRight = 2.cssRem)
    }
}

val SectionContentStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .maxWidth(72.cssRem)
    }
}

@Composable
fun SectionContainer(
    id: String,
    modifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
    altBackground: Boolean = false,
    content: @Composable () -> Unit,
) {
    val palette = ColorMode.current.toSitePalette()
    Section(
        attrs = SectionContainerStyle.toModifier()
            .id(id)
            .then(
                if (altBackground) {
                    Modifier.backgroundColor(palette.surfaceVariant)
                } else {
                    Modifier
                },
            )
            .then(modifier)
            .toAttrs(),
    ) {
        Column(
            modifier = SectionContentStyle.toModifier().then(contentModifier),
            horizontalAlignment = Alignment.Start,
        ) {
            content()
        }
    }
}
