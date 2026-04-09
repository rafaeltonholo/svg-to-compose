package dev.tonholo.s2c.website.worker

import dev.tonholo.s2c.logger.Logger
import dev.tonholo.s2c.website.worker.inject.EnableDebugQualifier
import dev.zacsweers.metro.Inject

/** [Logger] implementation that delegates to browser console methods. */
@Inject
internal class ConsoleLogger(@param:EnableDebugQualifier val enableLogs: Boolean) : Logger {
    override fun debug(message: Any) {
        if (enableLogs) {
            console.log("[DEBUG] $message")
        }
    }

    override fun <T> debugSection(title: String, block: () -> T): T {
        if (enableLogs) {
            console.log("[DEBUG] $title")
        }
        return block()
    }

    override fun <T> verboseSection(title: String, block: () -> T): T {
        if (enableLogs) {
            console.log("[VERBOSE] $title")
        }
        return block()
    }

    override fun verbose(message: String) {
        if (enableLogs) {
            console.log("[VERBOSE] $message")
        }
    }

    override fun warn(message: String, throwable: Throwable?) {
        console.warn("[WARN] $message", throwable)
    }

    override fun info(message: String) {
        console.info("[INFO] $message")
    }

    override fun output(message: String) {
        if (enableLogs) {
            console.log(message)
        }
    }

    override fun error(message: String, throwable: Throwable?) {
        console.error("[ERROR] $message", throwable)
    }

    override fun printEmpty() {
        console.log("")
    }
}
