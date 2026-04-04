package dev.tonholo.s2c.website.navigation

/**
 * Represents a navigation route in the website with an optional set of child routes.
 *
 * @property path The URL path for this route.
 * @property label The display label shown in navigation links.
 * @property subRoutes Child routes nested under this route.
 */
data class WebRoute(val path: String, val label: String, val subRoutes: Set<WebRoute> = emptySet()) {
    /**
     * All routes in this hierarchy, including this route and its [subRoutes].
     */
    val all = setOf(this) + subRoutes

    companion object {
        /**
         * Home page route with anchor-based sub-routes for each landing page section.
         */
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
        /**
         * Documentation section route with sub-routes for each documentation page.
         */
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
