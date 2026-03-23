package dev.tonholo.s2c.website.components.organisms.howtouse

import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.silk.style.extendedByBase
import dev.tonholo.s2c.website.theme.SiteTheme
import dev.tonholo.s2c.website.theme.common.SiteLinkStyleVariant
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle

val SeeFullDocumentationLinkButton = SiteLinkStyleVariant.extendedByBase {
    Modifier
        .display(DisplayStyle.Flex)
        .alignItems(AlignItems.Center)
        .gap(SiteTheme.dimensions.size.Xsm)
}
