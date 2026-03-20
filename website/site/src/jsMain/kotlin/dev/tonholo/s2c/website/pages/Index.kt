package dev.tonholo.s2c.website.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import dev.tonholo.s2c.website.components.layouts.PageLayoutData
import dev.tonholo.s2c.website.components.sections.FeaturesSection
import dev.tonholo.s2c.website.components.sections.GetStartedSection
import dev.tonholo.s2c.website.components.sections.HeroSection
import dev.tonholo.s2c.website.components.sections.HowToUseSection
import dev.tonholo.s2c.website.components.sections.PlaygroundSection

@InitRoute
fun initHomePage(ctx: InitRouteContext) {
    ctx.data.add(PageLayoutData("Home"))
}

@Page
@Layout(".components.layouts.PageLayout")
@Composable
fun HomePage() {
    HeroSection()
    PlaygroundSection()
    GetStartedSection()
    HowToUseSection()
    FeaturesSection()
}
