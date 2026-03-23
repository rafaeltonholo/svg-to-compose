package dev.tonholo.s2c.website.components.molecules

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.browser.dom.observers.IntersectionObserver
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.ariaLabel
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.maxHeight
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.navigation.UncoloredLinkVariant
import com.varabyte.kobweb.silk.components.navigation.UndecoratedLinkVariant
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.theme.LabelTextStyle
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.theme.toSitePalette
import kotlinx.browser.document
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.minus
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.dom.Nav

private const val TOC_ROOT_MARGIN = "-80px 0px -60% 0px"
private const val TOC_LEVEL_INDENT = 3

data class TocEntry(val id: String, val label: String, val level: Int = 2)

val TocContainerStyle = CssStyle.base {
    Modifier
        .position(Position.Sticky)
        .top(6.5.cssRem)
        .maxHeight(100.vh - 7.5.cssRem)
        .overflow { y(Overflow.Auto) }
        .padding(top = SiteTheme.dimensions.size.Lg, right = SiteTheme.dimensions.size.Lg)
}

val TocLinkStyle = CssStyle {
    base {
        Modifier
            .color(colorMode.toSitePalette().onSurfaceVariant)
            .fontSize(0.8.cssRem)
            .transition(
                Transition.of("color", duration = 150.ms, timingFunction = TransitionTimingFunction.Ease),
            )
    }
    cssRule(":hover") {
        Modifier.color(colorMode.toSitePalette().primary)
    }
}

val TocActiveLinkStyle = CssStyle.base {
    Modifier
        .color(colorMode.toSitePalette().primary)
        .fontWeight(FontWeight.SemiBold)
}

@Composable
fun rememberActiveTocSection(entries: List<TocEntry>): String? {
    var activeId by remember { mutableStateOf<String?>(null) }
    val currentEntries by rememberUpdatedState(entries)

    DisposableEffect(currentEntries.map { it.id }) {
        val observer = IntersectionObserver(
            options = IntersectionObserver.Options(
                rootMargin = TOC_ROOT_MARGIN,
            ),
            resized = { observedEntries, _ ->
                observedEntries.forEach { entry ->
                    if (entry.isIntersecting) {
                        val entryId = entry.target.id
                        activeId = entryId
                    }
                }
            },
        )

        currentEntries.forEach { tocEntry ->
            val element = document.getElementById(tocEntry.id)
            if (element != null) {
                observer.observe(element)
            }
        }

        onDispose { observer.disconnect() }
    }

    return activeId
}

@Composable
fun TableOfContents(entries: List<TocEntry>, activeId: String?, modifier: Modifier = Modifier) {
    Nav(
        attrs = TocContainerStyle.toModifier()
            .then(modifier)
            .ariaLabel("Table of contents")
            .toAttrs(),
    ) {
        Column {
            SpanText(
                "Table of Contents",
                modifier = LabelTextStyle.toModifier()
                    .padding(bottom = SiteTheme.dimensions.size.Md),
            )
            entries.forEach { entry ->
                val isActive = entry.id == activeId
                val linkModifier = TocLinkStyle.toModifier()
                    .then(
                        if (isActive) {
                            TocActiveLinkStyle.toModifier()
                        } else {
                            Modifier
                        },
                    )
                    .then(
                        if (entry.level >= TOC_LEVEL_INDENT) {
                            Modifier.padding(left = SiteTheme.dimensions.size.Lg)
                        } else {
                            Modifier
                        },
                    )
                    .padding(topBottom = SiteTheme.dimensions.size.Xsm)

                Link(
                    path = "#${entry.id}",
                    text = entry.label,
                    modifier = linkModifier,
                    variant = UndecoratedLinkVariant.then(UncoloredLinkVariant),
                )
            }
        }
    }
}
