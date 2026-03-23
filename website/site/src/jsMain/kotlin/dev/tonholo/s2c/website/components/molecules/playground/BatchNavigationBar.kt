package dev.tonholo.s2c.website.components.molecules.playground

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.ariaLabel
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flexDirection
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.role
import com.varabyte.kobweb.compose.ui.modifiers.tabIndex
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaArrowLeft
import com.varabyte.kobweb.silk.components.icons.fa.FaChevronLeft
import com.varabyte.kobweb.silk.components.icons.fa.FaChevronRight
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toModifier
import dev.tonholo.s2c.website.theme.SiteTheme
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Div

/**
 * Navigation bar shown when inspecting a single batch result.
 * Provides Back, Prev/Next, and position indicator.
 */
@Composable
internal fun BatchNavigationBar(
    viewingIndex: Int,
    totalResults: Int,
    onBack: () -> Unit,
    onPrev: () -> Unit,
    onNext: () -> Unit,
) {
    Div(
        attrs = Modifier
            .fillMaxWidth()
            .padding(topBottom = SiteTheme.dimensions.size.Sm, leftRight = SiteTheme.dimensions.size.Lg)
            .backgroundColor(SiteTheme.palette.surfaceVariant)
            .display(DisplayStyle.Flex)
            .alignItems(AlignItems.Center)
            .gap(SiteTheme.dimensions.size.Sm)
            .toAttrs(),
    ) {
        BatchNavButton(
            label = "Back to list",
            ariaLabel = "Back to batch results list",
            onClick = onBack,
        ) {
            FaArrowLeft(size = IconSize.SM)
        }

        if (totalResults > 1 && viewingIndex >= 0) {
            SpanText(
                "${viewingIndex + 1} / $totalResults",
                modifier = Modifier
                    .fontSize(0.8.cssRem)
                    .color(SiteTheme.palette.onSurfaceVariant),
            )
            BatchNavButton(
                label = "Prev",
                ariaLabel = "Previous icon",
                enabled = viewingIndex > 0,
                onClick = onPrev,
            ) {
                FaChevronLeft(size = IconSize.SM)
            }
            BatchNavButton(
                label = "Next",
                ariaLabel = "Next icon",
                enabled = viewingIndex < totalResults - 1,
                labelFirst = false,
                onClick = onNext,
            ) {
                FaChevronRight(size = IconSize.SM)
            }
        }
    }
}

@Composable
private fun BatchNavButton(
    label: String,
    ariaLabel: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    labelFirst: Boolean = true,
    icon: @Composable () -> Unit,
) {
    Div(
        attrs = ConvertButtonStyle.toModifier()
            .display(DisplayStyle.Flex)
            .flexDirection(if (labelFirst) FlexDirection.Row else FlexDirection.RowReverse)
            .alignItems(AlignItems.Center)
            .gap(0.35.cssRem)
            .cursor(if (enabled) Cursor.Pointer else Cursor.Default)
            .let { if (!enabled) it.opacity(value = 0.4f) else it }
            .tabIndex(if (enabled) 0 else -1)
            .role("button")
            .ariaLabel(ariaLabel)
            .onClick { if (enabled) onClick() }
            .toAttrs(),
    ) {
        icon()
        SpanText(label)
    }
}
