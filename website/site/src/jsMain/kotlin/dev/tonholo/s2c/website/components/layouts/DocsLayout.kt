package dev.tonholo.s2c.website.components.layouts

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.autoLength
import com.varabyte.kobweb.compose.css.functions.clamp
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.gridTemplateColumns
import com.varabyte.kobweb.compose.ui.modifiers.leftRight
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.marginInline
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.navigation.UncoloredLinkVariant
import com.varabyte.kobweb.silk.components.navigation.UndecoratedLinkVariant
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.breakpoint.displayIfAtLeast
import com.varabyte.kobweb.silk.style.breakpoint.displayUntil
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.components.molecules.CollapsibleSection
import dev.tonholo.s2c.website.components.molecules.TableOfContents
import dev.tonholo.s2c.website.components.molecules.TocEntry
import dev.tonholo.s2c.website.components.molecules.TocLinkStyle
import dev.tonholo.s2c.website.components.molecules.rememberActiveTocSection
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.theme.toSitePalette
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.fr
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vw
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text

val DocsLayoutStyle = CssStyle {
    base {
        Modifier
            .display(DisplayStyle.Grid)
            .gridTemplateColumns { size(1.fr) }
            .gap(SiteTheme.dimensions.size.Xxl)
            .fillMaxWidth()
            .maxWidth(SiteTheme.dimensions.layout.contentMaxWidth)
            .marginInline(autoLength)
            .padding(top = SiteTheme.dimensions.padding.docsLayoutTop, leftRight = SiteTheme.dimensions.size.Lg)
    }
    Breakpoint.SM {
        Modifier.padding {
            leftRight(SiteTheme.dimensions.size.Xl)
        }
    }
    Breakpoint.MD {
        Modifier
            .gridTemplateColumns {
                size(16.cssRem)
                size(1.fr)
            }
            .padding {
                leftRight(SiteTheme.dimensions.size.Xxl)
            }
    }
    Breakpoint.XL {
        Modifier.gridTemplateColumns {
            size(18.cssRem)
            size(1.fr)
        }
    }
}

val DocsContentStyle = CssStyle.base {
    Modifier
        .minWidth(0.px)
        .maxWidth(48.cssRem)
        .padding {
            bottom(SiteTheme.dimensions.size.Xxl)
        }
}

private const val DOCS_TITLE_LINE_HEIGHT = 1.2

val DocsTitleStyle = CssStyle.base {
    Modifier
        .fontSize(clamp(1.5.cssRem, 4.vw, 2.5.cssRem))
        .fontWeight(FontWeight.Bold)
        .lineHeight(DOCS_TITLE_LINE_HEIGHT)
        .margin(top = 0.px, bottom = SiteTheme.dimensions.size.Xxl)
}

@Composable
fun DocsLayout(
    title: String,
    tocEntries: List<TocEntry>,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val activeId = rememberActiveTocSection(tocEntries)
    val palette = ColorMode.current.toSitePalette()

    Div(
        attrs = DocsLayoutStyle.toModifier()
            .then(modifier)
            .toAttrs(),
    ) {
        // Sidebar TOC - visible at MD and above
        Div(
            attrs = Modifier
                .displayIfAtLeast(Breakpoint.MD)
                .toAttrs(),
        ) {
            TableOfContents(
                entries = tocEntries,
                activeId = activeId,
            )
        }

        // Main content column
        Column(
            modifier = DocsContentStyle.toModifier(),
            verticalArrangement = Arrangement.spacedBy(SiteTheme.dimensions.size.Xxl),
        ) {
            // Collapsible TOC for mobile - visible below MD
            Div(
                attrs = Modifier
                    .displayUntil(Breakpoint.MD)
                    .padding(bottom = SiteTheme.dimensions.size.Lg)
                    .toAttrs(),
            ) {
                CollapsibleSection(title = "Table of Contents") {
                    tocEntries.forEach { entry ->
                        val linkPadding = if (entry.level >= MOBILE_TOC_INDENT_LEVEL) {
                            Modifier.padding(left = SiteTheme.dimensions.size.Lg)
                        } else {
                            Modifier
                        }
                        Link(
                            path = "#${entry.id}",
                            text = entry.label,
                            modifier = TocLinkStyle.toModifier()
                                .then(linkPadding)
                                .padding(topBottom = SiteTheme.dimensions.size.Sm),
                            variant = UndecoratedLinkVariant.then(UncoloredLinkVariant),
                        )
                    }
                }
            }

            H1(
                attrs = DocsTitleStyle.toModifier()
                    .color(palette.onBackground)
                    .toAttrs(),
            ) {
                Text(title)
            }
            content()
        }
    }
}

private const val MOBILE_TOC_INDENT_LEVEL = 3
