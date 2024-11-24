package dev.tonholo.s2c.logger

internal class CommonLogger : Logger {
    override fun debug(message: Any) {
        if (!AppConfig.silent && AppConfig.debug) {
            println("D: $message")
        }
    }

    override fun <T> debugSection(title: String, block: () -> T): T =
        if (!AppConfig.silent && AppConfig.debug) {
            startSection(title)
            block().also { endSection() }
        } else {
            block()
        }

    override fun <T> verboseSection(title: String, block: () -> T): T =
        if (!AppConfig.silent && AppConfig.verbose) {
            startSection(title)
            block().also { endSection() }
        } else {
            block()
        }

    private fun startSection(message: String) {
        println()
        val section = "=".repeat(25 - (message.length / 2))
        println("$section $message $section")
        println()
    }

    private fun endSection() {
        println()
        println("=".repeat(n = 50))
        println()
    }

    override fun verbose(message: String) {
        if (!AppConfig.silent && AppConfig.verbose) {
            println("V: $message")
        }
    }

    override fun warn(message: String, throwable: Throwable?) {
        if (!AppConfig.silent) {
            // TODO(https://github.com/rafaeltonholo/svg-to-compose/issues/85): add color to output.
            println("WARNING ⚠️: $message")
            if (AppConfig.stackTrace) {
                throwable?.printStackTrace()
            }
        }
    }

    override fun info(message: String) =
        println("I: $message")

    override fun output(message: String) {
        if (!AppConfig.silent) {
            println(message)
        }
    }

    override fun error(message: String, throwable: Throwable?) {
        println("E: $message")
        if (AppConfig.stackTrace) {
            throwable?.printStackTrace()
        }
    }
}
