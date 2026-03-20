package dev.tonholo.s2c.website.components.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.alignSelf
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.borderTop
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flexDirection
import com.varabyte.kobweb.compose.ui.modifiers.flexWrap
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaGithub
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.navigation.UncoloredLinkVariant
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.tonholo.s2c.website.GradientTextStyle
import dev.tonholo.s2c.website.components.widgets.Badge
import dev.tonholo.s2c.website.toSitePalette
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.AlignSelf
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Footer
import org.jetbrains.compose.web.dom.Hr

val FooterStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .padding(topBottom = 3.cssRem, leftRight = 1.cssRem)
            .borderTop(
                width = 1.px,
                style = LineStyle.Solid,
                color = when (colorMode) {
                    ColorMode.LIGHT -> com.varabyte.kobweb.compose.ui.graphics.Color.rgba(0, 0, 0, 0.1f)
                    ColorMode.DARK -> com.varabyte.kobweb.compose.ui.graphics.Color.rgba(127, 82, 255, 0.15f)
                },
            )
    }
    Breakpoint.MD {
        Modifier.padding(leftRight = 2.cssRem)
    }
}

val FooterContentStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .maxWidth(72.cssRem)
            .display(DisplayStyle.Flex)
            .flexDirection(FlexDirection.Column)
            .gap(1.5.cssRem)
            .styleModifier { property("margin", "0 auto") }
    }
}

val FooterTopRowStyle = CssStyle {
    base {
        Modifier
            .display(DisplayStyle.Flex)
            .flexDirection(FlexDirection.Column)
            .gap(1.cssRem)
            .alignItems(AlignItems.Center)
    }
    Breakpoint.MD {
        Modifier
            .flexDirection(FlexDirection.Row)
            .alignItems(AlignItems.Center)
    }
}

@Composable
fun Footer(modifier: Modifier = Modifier) {
    Footer(
        attrs = FooterStyle.toModifier().then(modifier).toAttrs(),
    ) {
        Div(attrs = FooterContentStyle.toModifier().toAttrs()) {
            // Top row: Logo + description | Links
            Div(attrs = FooterTopRowStyle.toModifier().toAttrs()) {
                // Logo & description
                Column(
                    modifier = Modifier.gap(0.5.cssRem),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.gap(0.5.cssRem),
                    ) {
                        SpanText(
                            "s2c",
                            modifier = GradientTextStyle.toModifier()
                                .fontWeight(FontWeight.Bold)
                                .fontSize(1.125.cssRem)
                                .fontFamily("JetBrains Mono", "monospace"),
                        )
                        SpanText(
                            "svg-to-compose",
                            modifier = Modifier
                                .fontWeight(FontWeight.SemiBold)
                                .fontSize(0.875.cssRem),
                        )
                    }
                    SpanText(
                        "Open-source Kotlin Multiplatform tool that converts SVG and AVG files into Jetpack Compose ImageVector code.",
                        modifier = Modifier
                            .fontSize(0.8.cssRem)
                            .color(ColorMode.current.toSitePalette().muted)
                            .textAlign(com.varabyte.kobweb.compose.css.TextAlign.Center)
                            .maxWidth(28.cssRem),
                    )
                }

                Spacer()

                // Link badges
                Row(
                    modifier = Modifier
                        .gap(0.5.cssRem)
                        .flexWrap(FlexWrap.Wrap)
                        .styleModifier { property("justify-content", "center") },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    val palette = ColorMode.current.toSitePalette()
                    Link(
                        "https://github.com/rafaeltonholo/svg-to-compose",
                        modifier = Modifier
                            .textDecorationLine(TextDecorationLine.None)
                            .display(DisplayStyle.Flex)
                            .alignItems(AlignItems.Center)
                            .gap(0.3.cssRem)
                            .padding(leftRight = 0.5.cssRem, topBottom = 0.25.cssRem)
                            .borderRadius(50.percent)
                            .border(1.px, LineStyle.Solid, palette.border)
                            .fontSize(0.7.cssRem)
                            .color(palette.muted),
                        variant = UncoloredLinkVariant,
                    ) {
                        FaGithub()
                        SpanText("GitHub")
                    }
                    Link(
                        "https://search.maven.org/search?q=g:dev.tonholo",
                        modifier = Modifier
                            .textDecorationLine(TextDecorationLine.None)
                            .padding(leftRight = 0.5.cssRem, topBottom = 0.25.cssRem)
                            .borderRadius(50.percent)
                            .border(1.px, LineStyle.Solid, palette.border)
                            .fontSize(0.7.cssRem)
                            .color(palette.muted),
                        variant = UncoloredLinkVariant,
                    ) {
                        SpanText("Maven Central")
                    }
                    Badge("MIT License", com.varabyte.kobweb.compose.ui.graphics.Color.rgb(0xA5D6A7))
                    Badge("v2.1.2", com.varabyte.kobweb.compose.ui.graphics.Color.rgb(0x7F52FF))
                }
            }

            // Divider
            Hr(
                attrs = Modifier
                    .fillMaxWidth()
                    .border(0.px)
                    .height(1.px)
                    .backgroundColor(ColorMode.current.toSitePalette().border)
                    .toAttrs(),
            )

            // Bottom attribution
            SpanText(
                "Built with \u2764\uFE0F by Rafael Tonholo \u00B7 Open source on GitHub.",
                modifier = Modifier
                    .fontSize(0.75.cssRem)
                    .color(ColorMode.current.toSitePalette().mutedStrong)
                    .textAlign(com.varabyte.kobweb.compose.css.TextAlign.Center)
                    .alignSelf(AlignSelf.Center),
            )
        }
    }
}
