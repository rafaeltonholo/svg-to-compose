package dev.tonholo.s2c.website.components.atoms

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import com.varabyte.kobweb.browser.dom.observers.IntersectionObserver
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.dom.Div

/**
 * Renders a sentinel [Div] that fires [onVisible] when it enters
 * the viewport (or a region extended by [rootMargin]).
 *
 * Useful for infinite-scroll / progressive-loading patterns where
 * more items should load as the user scrolls near the bottom.
 */
@Composable
internal fun IntersectionObserverTrigger(
    onVisible: () -> Unit,
    modifier: Modifier = Modifier,
    rootMargin: String = "200px",
) {
    val currentOnVisible by rememberUpdatedState(onVisible)
    val observer = remember(rootMargin) {
        IntersectionObserver(
            options = IntersectionObserver.Options(
                rootMargin = rootMargin,
            ),
            resized = { entries, _ ->
                entries.forEach { entry ->
                    if (entry.isIntersecting) {
                        currentOnVisible()
                    }
                }
            },
        )
    }

    DisposableEffect(observer) {
        onDispose { observer.disconnect() }
    }

    Div(
        attrs = modifier.toAttrs {
            ref { element ->
                observer.observe(element)
                onDispose { observer.disconnect() }
            }
        },
    )
}
