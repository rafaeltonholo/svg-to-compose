package dev.tonholo.s2c.logger

import AppConfig

interface Logger {
    fun debug(message: Any)
    fun <T> debugSection(title: String, block: () -> T): T
    fun <T> verboseSection(title: String, block: () -> T): T
    fun verbose(message: String)
    fun warn(message: String)
    fun info(message: String)
    fun output(message: String)
    fun error(message: String, throwable: Throwable? = null)
}

@Deprecated(
    "Use logger instead.",
    replaceWith = ReplaceWith(
        expression = "logger.debug(message)",
    ),
)
internal fun debug(message: Any) {
    if (!AppConfig.silent && AppConfig.debug) {
        println("D: $message")
    }
}

@Deprecated(
    "Use logger instead.",
    replaceWith = ReplaceWith(
        expression = "logger.debugSection(title, block)",
    ),
)
internal fun <T> debugSection(title: String, block: () -> T): T =
    if (!AppConfig.silent && AppConfig.debug) {
        startSection(title)
        block().also { endSection() }
    } else {
        block()
    }

@Deprecated(
    "Use logger instead.",
    replaceWith = ReplaceWith(
        expression = "logger.verboseSection(title, block)",
    ),
)
internal fun <T> verboseSection(title: String, block: () -> T) =
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

@Deprecated(
    "Use logger instead.",
    replaceWith = ReplaceWith(
        expression = "logger.verbose(message)",
    ),
)
internal fun verbose(message: String) {
    if (!AppConfig.silent && AppConfig.verbose) {
        println("V: $message")
    }
}

@Suppress("ForbiddenComment")
@Deprecated(
    "Use logger instead.",
    replaceWith = ReplaceWith(
        expression = "logger.warn(message)",
    ),
)
internal fun warn(message: String) {
    if (!AppConfig.silent) {
        println("WARNING ⚠️: $message") // TODO: add color to output.
    }
}

@Deprecated(
    "Use logger instead.",
    replaceWith = ReplaceWith(
        expression = "logger.output(message)",
    ),
)
internal fun output(message: String) {
    if (!AppConfig.silent) {
        println(message)
    }
}

internal fun printEmpty() {
    if (!AppConfig.silent) {
        println()
    }
}

internal fun Logger(): Logger = object : Logger {
    override fun debug(message: Any) =
        dev.tonholo.s2c.logger.debug(message)

    override fun <T> debugSection(title: String, block: () -> T): T =
        dev.tonholo.s2c.logger.debugSection(title, block)

    override fun <T> verboseSection(title: String, block: () -> T): T =
        dev.tonholo.s2c.logger.verboseSection(title, block)

    override fun verbose(message: String) =
        dev.tonholo.s2c.logger.verbose(message)

    override fun warn(message: String) =
        dev.tonholo.s2c.logger.warn(message)

    override fun info(message: String) =
        println("I: $message")

    override fun output(message: String) =
        dev.tonholo.s2c.logger.output(message)

    override fun error(message: String, throwable: Throwable?) {
        println("E: $message")
        if (AppConfig.stackTrace) {
            throwable?.printStackTrace()
        }
    }
}
