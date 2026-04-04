package dev.tonholo.s2c.website.navigation

data class WebRoute(
    val path: String,
    val label: String,
    val subRoutes: Set<WebRoute> = emptySet(),
) {
    val all = setOf(this) + subRoutes

    companion object {
        val Home = WebRoute(
            path = "/",
            label = "Home",
            subRoutes = setOf(
                WebRoute(path = "/#playground", label = "Playground"),
                WebRoute(path = "/#install", label = "Install"),
                WebRoute(path = "/#usage", label = "Usage"),
                WebRoute(path = "/#capabilities", label = "Capabilities"),
            ),
        )
        val Docs = WebRoute(
            path = "/docs",
            label = "Docs",
            subRoutes = setOf(
                WebRoute(path = "/docs/cli", label = "CLI"),
                WebRoute(path = "/docs/gradle-plugin", label = "Gradle Plugin"),
                WebRoute(path = "/docs/templates", label = "Template System"),
                WebRoute(path = "/api-docs/index.html", label = "API Reference"),
                WebRoute(path = "/docs/faq", label = "FAQ"),
                WebRoute(path = "/docs/alternatives", label = "Alternatives"),
            ),
        )
    }
}
