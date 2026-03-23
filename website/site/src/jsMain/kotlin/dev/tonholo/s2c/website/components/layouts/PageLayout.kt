package dev.tonholo.s2c.website.components.layouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gridRow
import com.varabyte.kobweb.compose.ui.modifiers.gridTemplateRows
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.data.getValue
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.icons.fa.FaTriangleExclamation
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.components.atoms.Banner
import dev.tonholo.s2c.website.components.organisms.NavHeader
import dev.tonholo.s2c.website.components.organisms.footer.Footer
import dev.tonholo.s2c.website.theme.SiteTheme
import kotlinx.browser.document
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.fr
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.dom.Main

private val NAV_HEADER_HEIGHT = 4.cssRem
private const val BANNER_Z_INDEX = 999

val RootStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .minHeight(100.vh)
            .gridTemplateRows {
                size(1.fr)
                size(minContent)
            }
    }
}

@Composable
@Layout
@Suppress("ModifierMissing")
fun PageLayout(ctx: PageContext, content: @Composable () -> Unit) {
    val data = ctx.data.getValue<PageLayoutData>()
    LaunchedEffect(data.title) {
        document.title = "SVG to Compose — ${data.title}"
    }

    Box(
        modifier = RootStyle.toModifier(),
        contentAlignment = Alignment.Center,
    ) {
        NavHeader()
        val palette = SiteTheme.palette
        Banner(
            text = "This site is a work in progress - feedback welcome!",
            contentColor = palette.onWarningContainer,
            containerColor = palette.warningContainer,
            borderColor = palette.warning,
            modifier = Modifier
                .position(Position.Fixed)
                .top(NAV_HEADER_HEIGHT)
                .zIndex(BANNER_Z_INDEX),
            leadingIcon = { FaTriangleExclamation() },
        )
        Main(attrs = Modifier.fillMaxWidth().toAttrs()) {
            content()
        }
        Footer(Modifier.fillMaxWidth().gridRow(2))
    }
}

data class PageLayoutData(val title: String)
