package dev.tonholo.s2c.website.components.layouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gridRow
import com.varabyte.kobweb.compose.ui.modifiers.gridTemplateRows
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.transition
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
import dev.tonholo.s2c.website.theme.LayoutTransitions
import dev.tonholo.s2c.website.theme.SiteTheme
import kotlinx.browser.document
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.fr
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.dom.Main

private val NAV_HEADER_HEIGHT = 4.25.cssRem
private const val BANNER_Z_INDEX = 999
private const val FADE_IN_DURATION_MS = 300
private const val FRAME_DELAY_MS = 50L

private val FadeInTransition = Transition.of("opacity", FADE_IN_DURATION_MS.ms, TransitionTimingFunction.EaseOut)
private val MainTransitions: List<Transition.Listable> = listOf(FadeInTransition) + LayoutTransitions

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

    var contentVisible by remember(ctx.route.path) { mutableStateOf(false) }
    LaunchedEffect(ctx.route.path) {
        delay(FRAME_DELAY_MS)
        contentVisible = true
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
        Main(
            attrs = Modifier
                .fillMaxWidth()
                .opacity(if (contentVisible) 1 else 0)
                .transition(MainTransitions)
                .toAttrs(),
        ) {
            content()
        }
        Footer(Modifier.fillMaxWidth().gridRow(2))
    }
}

data class PageLayoutData(val title: String)
