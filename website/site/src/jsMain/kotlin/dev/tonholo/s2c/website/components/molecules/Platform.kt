package dev.tonholo.s2c.website.components.molecules

import kotlinx.browser.window

enum class Platform(val label: String) {
    MAC_LINUX("macOS / Linux"),
    WINDOWS("Windows"),
    MANUAL("Manual"),
}

fun detectPlatform(): Platform {
    val userAgent = window.navigator.userAgent
    return when {
        userAgent.contains("Win", ignoreCase = true) -> Platform.WINDOWS
        else -> Platform.MAC_LINUX
    }
}
