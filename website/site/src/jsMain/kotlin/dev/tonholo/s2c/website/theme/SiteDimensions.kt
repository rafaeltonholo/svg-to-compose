package dev.tonholo.s2c.website.theme

import com.varabyte.kobweb.compose.css.StyleVariable
import com.varabyte.kobweb.compose.css.functions.calc
import org.jetbrains.compose.web.css.CSSSizeValue
import org.jetbrains.compose.web.css.CSSUnit
import org.jetbrains.compose.web.css.cssRem

private const val SIZE_PREFIX = "dimension-size"

/**
 * Provides a centralized access point for consistent dimension values used throughout the site.
 *
 * This singleton object exposes predefined size scale values, semantic margin tokens, and
 * semantic padding tokens that ensure uniform spacing and layout consistency across the
 * application. The values are retrieved from the underlying [SiteSizeVar] CSS custom properties.
 *
 * **IMPORTANT**: Must not be called directly. Use the corresponding values from
 * [SiteTheme.dimensions] instead to ensure proper theming and responsiveness.
 */
object SiteDimensions {
    /**
     * Provides access to predefined spacing size values used throughout the site.
     *
     * This object exposes a set of spacing constants that correspond to the site's spacing system,
     * ranging from extra small to extra extra large. These values are derived from the [SiteSizeVar]
     * CSS custom properties and can be used to maintain consistent spacing across UI components.
     */
    val size: SizeTokens = SizeTokens

    /**
     * Semantic margin tokens for layout-level spacing.
     *
     * These tokens compose on top of base [size] tokens using `calc`, so they automatically
     * scale with breakpoint changes to the underlying CSS custom properties.
     */
    val margin: MarginTokens = MarginTokens

    /**
     * Semantic padding tokens for layout-level spacing.
     *
     * These tokens compose on top of base [size] tokens using `calc`, so they automatically
     * scale with breakpoint changes to the underlying CSS custom properties.
     */
    val padding: PaddingTokens = PaddingTokens
}

object SizeTokens {
    val Xsm get() = SiteSizeVar.Xsm.value()
    val Sm get() = SiteSizeVar.Sm.value()
    val Md get() = SiteSizeVar.Md.value()
    val Lg get() = SiteSizeVar.Lg.value()
    val Xl get() = SiteSizeVar.Xl.value()
    val Xxl get() = SiteSizeVar.Xxl.value()
}

object MarginTokens {
    /** Body content margin (4rem base -> 5rem MD+) */
    val body get() = calc { SiteSizeVar.Xxl.value() + 2.cssRem }
}

object PaddingTokens {
    /** Section vertical padding (3rem base -> 4rem MD+) */
    val section get() = calc { SiteSizeVar.Xxl.value() + 1.cssRem }

    /** Page/docs top padding (4rem base -> 5rem MD+) */
    val pageTop get() = calc { SiteSizeVar.Xxl.value() + 2.cssRem }

    /** Hero section top padding (6rem base -> 7rem MD+) */
    val heroTop get() = calc { SiteSizeVar.Xxl.value() + 4.cssRem }

    /** Footer vertical padding (5rem at MD+) */
    val footerVertical get() = calc { SiteSizeVar.Xxl.value() + 2.cssRem }

    /** Docs layout top padding including nav offset (10rem base) */
    val docsLayoutTop get() = calc { SiteSizeVar.Xxl.value() + 8.cssRem }
}

/**
 * Internal object that defines CSS custom properties for site-wide size values.
 *
 * This object provides a set of predefined size scale variables that maintain consistent
 * spacing throughout the application. All values are defined in rem units to ensure
 * scalability and accessibility.
 *
 * The size scale follows a standard progression from extra small to extra extra large,
 * allowing for consistent spacing patterns across different UI components and layouts.
 *
 * **IMPORTANT**: Must not be called directly. Use the corresponding values from
 * [SiteTheme.dimensions] instead to ensure proper theming and responsiveness.
 */
object SiteSizeVar {
    val Xsm by StyleVariable<CSSSizeValue<CSSUnit.rem>>(SIZE_PREFIX)
    val Sm by StyleVariable<CSSSizeValue<CSSUnit.rem>>(SIZE_PREFIX)
    val Md by StyleVariable<CSSSizeValue<CSSUnit.rem>>(SIZE_PREFIX)
    val Lg by StyleVariable<CSSSizeValue<CSSUnit.rem>>(SIZE_PREFIX)
    val Xl by StyleVariable<CSSSizeValue<CSSUnit.rem>>(SIZE_PREFIX)
    val Xxl by StyleVariable<CSSSizeValue<CSSUnit.rem>>(SIZE_PREFIX)
}
