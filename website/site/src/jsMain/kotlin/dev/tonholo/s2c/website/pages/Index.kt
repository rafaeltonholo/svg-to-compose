package dev.tonholo.s2c.website.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import dev.tonholo.s2c.website.components.atoms.ScrollReveal
import dev.tonholo.s2c.website.components.atoms.SoftwareApplicationStructuredData
import dev.tonholo.s2c.website.components.atoms.WebSiteStructuredData
import dev.tonholo.s2c.website.components.layouts.PageLayoutData
import dev.tonholo.s2c.website.components.organisms.CapabilitiesSection
import dev.tonholo.s2c.website.components.organisms.HeroSection
import dev.tonholo.s2c.website.components.organisms.HowToUseSection
import dev.tonholo.s2c.website.components.organisms.InstallSection
import dev.tonholo.s2c.website.components.organisms.playground.PlaygroundSection

@InitRoute
fun initHomePage(ctx: InitRouteContext) {
    ctx.data.add(
        PageLayoutData(
            title = "Convert SVG and AVD to Compose ImageVector",
            description = "Convert SVG and Android Vector Drawables into Jetpack Compose ImageVector code. " +
                "CLI tool and Gradle plugin for Kotlin Multiplatform.",
            canonicalPath = "/",
            structuredData = listOf(
                WebSiteStructuredData,
                SoftwareApplicationStructuredData,
            ),
        ),
    )
}

@Page
@Layout(".components.layouts.PageLayout")
@Composable
fun HomePage() {
    var selectedToolTab by remember { mutableIntStateOf(0) }

    HeroSection()
    ScrollReveal { PlaygroundSection() }
    ScrollReveal {
        InstallSection(
            selectedToolTab = selectedToolTab,
            onToolTabSelect = { selectedToolTab = it },
        )
    }
    ScrollReveal {
        HowToUseSection(
            selectedToolTab = selectedToolTab,
            onToolTabSelect = { selectedToolTab = it },
        )
    }
    ScrollReveal { CapabilitiesSection() }
}
