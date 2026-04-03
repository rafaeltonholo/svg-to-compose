package dev.tonholo.s2c.website.pages.docs

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.css.autoLength
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.ariaLabel
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flexDirection
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.gridTemplateColumns
import com.varabyte.kobweb.compose.ui.modifiers.marginInline
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.translateY
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.icons.fa.FaCircleQuestion
import com.varabyte.kobweb.silk.components.icons.fa.FaCode
import com.varabyte.kobweb.silk.components.icons.fa.FaCodeCompare
import com.varabyte.kobweb.silk.components.icons.fa.FaTerminal
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.navigation.LinkStyle
import com.varabyte.kobweb.silk.components.navigation.UncoloredLinkVariant
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.addVariant
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.components.atoms.icon.GradleSvg
import dev.tonholo.s2c.website.components.layouts.PageLayoutData
import dev.tonholo.s2c.website.theme.DisplayTextStyle
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.theme.SubheadlineTextStyle
import dev.tonholo.s2c.website.theme.toSitePalette
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.fr
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text

private const val DOCS_GRID_COLUMNS = 3

val DocsContainerStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .marginInline(autoLength)
            .display(DisplayStyle.Flex)
            .flexDirection(FlexDirection.Column)
            .alignItems(AlignItems.Center)
            .padding(
                top = SiteTheme.dimensions.padding.pageTop,
                bottom = SiteTheme.dimensions.padding.section,
                leftRight = SiteTheme.dimensions.size.Lg,
            )
    }
    Breakpoint.SM {
        Modifier.padding(
            top = SiteTheme.dimensions.padding.pageTop,
            bottom = SiteTheme.dimensions.padding.section,
            leftRight = SiteTheme.dimensions.size.Xl,
        )
    }
    Breakpoint.MD {
        Modifier.padding(
            top = SiteTheme.dimensions.padding.pageTop,
            bottom = SiteTheme.dimensions.padding.section,
            leftRight = SiteTheme.dimensions.size.Xxl,
        )
    }
}

val DocsHeaderStyle = CssStyle {
    base {
        Modifier
            .display(DisplayStyle.Flex)
            .flexDirection(FlexDirection.Column)
            .alignItems(AlignItems.Center)
            .gap(SiteTheme.dimensions.size.Md)
    }
}

val DocsGridStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .display(DisplayStyle.Grid)
            .gridTemplateColumns { repeat(1) { size(1.fr) } }
            .gap(SiteTheme.dimensions.size.Xl)
            .padding(top = SiteTheme.dimensions.padding.section)
    }
    Breakpoint.MD {
        Modifier.gridTemplateColumns { repeat(DOCS_GRID_COLUMNS) { size(1.fr) } }
    }
}

val DocsCardStyle = CssStyle {
    val palette = colorMode.toSitePalette()
    base {
        Modifier
            .backgroundColor(palette.surfaceVariant)
            .border(1.px, LineStyle.Solid, palette.outline)
            .borderRadius(0.75.cssRem)
            .padding(SiteTheme.dimensions.size.Xl)
            .display(DisplayStyle.Flex)
            .flexDirection(FlexDirection.Column)
            .gap(SiteTheme.dimensions.size.Md)
            .cursor(Cursor.Pointer)
            .transition {
                property("border-color", "transform")
                duration(200.ms)
                timingFunction(TransitionTimingFunction.EaseInOut)
            }
    }

    Breakpoint.MD {
        Modifier.minHeight(12.1875.cssRem)
    }

    Breakpoint.LG {
        Modifier.minHeight(9.375.cssRem)
    }

    hover {
        Modifier
            .border(1.px, LineStyle.Solid, palette.primary)
            .translateY(ty = (-2).px)
    }
    cssRule(":focus-within") {
        Modifier
            .border(1.px, LineStyle.Solid, palette.primary)
            .translateY(ty = (-2).px)
    }
}

val DocsCardLinkVariant = LinkStyle.addVariant {
    base {
        Modifier.textDecorationLine(TextDecorationLine.None)
    }
}

@InitRoute
fun initDocsPage(ctx: InitRouteContext) {
    ctx.data.add(
        PageLayoutData(
            title = "Documentation - Guides and API Reference",
            description = "SVG to Compose documentation. Installation guides, CLI reference, " +
                "Gradle plugin setup, and API docs for SVG-to-ImageVector conversion.",
            canonicalPath = "/docs",
        ),
    )
}

@Page
@Layout(".components.layouts.PageLayout")
@Suppress("ModifierMissing")
@Composable
fun DocsPage() {
    val palette = SiteTheme.palette
    Div(
        attrs = DocsContainerStyle.toModifier()
            .toAttrs(),
    ) {
        Div(attrs = DocsHeaderStyle.toModifier().toAttrs()) {
            H1(
                attrs = DisplayTextStyle
                    .toModifier()
                    .color(palette.onBackground)
                    .toAttrs(),
            ) {
                Text("Documentation")
            }
            SpanText(
                "Everything you need to get started with SVG-to-Compose",
                modifier = SubheadlineTextStyle.toModifier(),
            )
        }
        Div(attrs = DocsGridStyle.toModifier().toAttrs()) {
            DocsCard(
                title = "CLI Tool",
                description = "A command-line tool to convert SVG and Android Vector Drawables " +
                    "to Jetpack Compose ImageVector code.",
                href = "/docs/cli",
                icon = { FaTerminal(modifier = Modifier.color(palette.primary), size = IconSize.LG) },
            )
            DocsCard(
                title = "Gradle Plugin",
                description = "Automate SVG/AVG to Compose conversion in your build pipeline " +
                    "with smart caching and incremental builds.",
                href = "/docs/gradle-plugin",
                icon = { GradleSvg(color = palette.primary, modifier = Modifier.size(1.85.cssRem)) },
            )
            DocsCard(
                title = "API Reference",
                description = "Detailed API documentation generated from source code with Dokka.",
                href = "/api-docs/index.html",
                icon = { FaCode(modifier = Modifier.color(palette.primary), size = IconSize.LG) },
            )
            DocsCard(
                title = "FAQ",
                description = "Common questions about SVG to Compose, supported features, " +
                    "and how to get started.",
                href = "/docs/faq",
                icon = { FaCircleQuestion(modifier = Modifier.color(palette.primary), size = IconSize.LG) },
            )
            DocsCard(
                title = "Alternatives",
                description = "Compare SVG to Compose with manual coding, Android Studio, " +
                    "and other conversion tools.",
                href = "/docs/alternatives",
                icon = { FaCodeCompare(modifier = Modifier.color(palette.primary), size = IconSize.LG) },
            )
        }
    }
}

@Composable
private fun DocsCard(title: String, description: String, href: String, icon: @Composable () -> Unit) {
    val palette = SiteTheme.palette
    Link(
        path = href,
        modifier = Modifier.ariaLabel("$title: $description"),
        variant = DocsCardLinkVariant.then(UncoloredLinkVariant),
    ) {
        Div(attrs = DocsCardStyle.toModifier().toAttrs()) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(SiteTheme.dimensions.size.Sm),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                icon()
                SpanText(
                    title,
                    modifier = Modifier
                        .fontSize(1.1.cssRem)
                        .fontWeight(FontWeight.Bold)
                        .color(palette.onSurface),
                )
            }
            SpanText(
                description,
                modifier = Modifier
                    .fontSize(0.85.cssRem)
                    .color(palette.onSurface),
            )
        }
    }
}
