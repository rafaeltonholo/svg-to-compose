package dev.tonholo.s2c.website.components.atoms

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.browser.dom.observers.IntersectionObserver
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.translateY
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

private const val REVEAL_DURATION_MS = 500
private const val REVEAL_OFFSET_PX = 12

private val RevealTransitions = listOf(
    Transition.of("opacity", REVEAL_DURATION_MS.ms, TransitionTimingFunction.EaseOut),
    Transition.of("transform", REVEAL_DURATION_MS.ms, TransitionTimingFunction.EaseOut),
)

@Composable
fun ScrollReveal(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    var isVisible by remember { mutableStateOf(false) }
    val observer = remember {
        IntersectionObserver(
            options = IntersectionObserver.Options(
                rootMargin = "0px 0px -60px 0px",
            ),
            resized = { entries, obs ->
                entries.forEach { entry ->
                    if (entry.isIntersecting) {
                        isVisible = true
                        obs.unobserve(entry.target)
                    }
                }
            },
        )
    }

    Div(
        attrs = modifier
            .opacity(if (isVisible) 1 else 0)
            .translateY(if (isVisible) 0.px else REVEAL_OFFSET_PX.px)
            .transition(RevealTransitions)
            .toAttrs {
                ref { element ->
                    observer.observe(element)
                    onDispose { observer.disconnect() }
                }
            },
    ) {
        content()
    }
}
