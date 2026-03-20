package dev.tonholo.s2c.website.worker

import dev.tonholo.s2c.logger.Logger

/** [Logger] implementation that delegates to browser console methods. */
internal object ConsoleLogger : Logger {
    override fun debug(message: Any) {
        console.log("[DEBUG] $message")
    }

    override fun <T> debugSection(title: String, block: () -> T): T {
        console.log("[DEBUG] $title")
        return block()
    }

    override fun <T> verboseSection(title: String, block: () -> T): T {
        console.log("[VERBOSE] $title")
        return block()
    }

    override fun verbose(message: String) {
        console.log("[VERBOSE] $message")
    }

    override fun warn(message: String, throwable: Throwable?) {
        console.warn("[WARN] $message", throwable)
    }

    override fun info(message: String) {
        console.info("[INFO] $message")
    }

    override fun output(message: String) {
        console.log(message)
    }

    override fun error(message: String, throwable: Throwable?) {
        console.error("[ERROR] $message", throwable)
    }
}
