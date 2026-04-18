package dev.tonholo.s2c.cli.logger

import dev.tonholo.s2c.SvgToComposeContext
import dev.tonholo.s2c.logger.Logger
import dev.tonholo.s2c.runtime.S2cConfig
import dev.zacsweers.metro.Inject

/**
 * Creates a [dev.tonholo.s2c.logger.Logger] backed by the given [S2cConfig] flags.
 *
 * This is the public entry point for obtaining a console-based logger.
 * The underlying implementation is internal to the library.
 */
@Inject
internal class CliLogger(private val context: SvgToComposeContext) : Logger {
    private companion object {
        private const val SECTION_WIDTH = 50
        private const val SECTION_PADDING = SECTION_WIDTH / 2
    }

    private val config get() = context.configSnapshot

    override fun debug(message: Any) {
        if (!config.silent && config.debug) {
            println("D: $message")
        }
    }

    override fun <T> debugSection(title: String, block: () -> T): T =
        if (!config.silent && config.debug) {
            startSection(title)
            block().also { endSection() }
        } else {
            block()
        }

    override fun <T> verboseSection(title: String, block: () -> T): T =
        if (!config.silent && config.verbose) {
            startSection(title)
            block().also { endSection() }
        } else {
            block()
        }

    private fun startSection(message: String) {
        println()
        val section = "=".repeat(SECTION_PADDING - (message.length / 2))
        println("$section $message $section")
        println()
    }

    private fun endSection() {
        println()
        println("=".repeat(SECTION_WIDTH))
        println()
    }

    override fun verbose(message: String) {
        if (!config.silent && config.verbose) {
            println("V: $message")
        }
    }

    override fun warn(message: String, throwable: Throwable?) {
        if (!config.silent) {
            // TODO(https://github.com/rafaeltonholo/svg-to-compose/issues/85): add color to output.
            println("WARNING ⚠️: $message")
            if (config.stackTrace) {
                throwable?.printStackTrace()
            }
        }
    }

    override fun info(message: String) {
        if (!config.silent) {
            println("I: $message")
        }
    }

    override fun output(message: String) {
        if (!config.silent) {
            println(message)
        }
    }

    override fun error(message: String, throwable: Throwable?) {
        if (!config.silent) {
            println("E: $message")
            if (config.stackTrace) {
                throwable?.printStackTrace()
            }
        }
    }

    override fun printEmpty() {
        if (!config.silent) {
            println()
        }
    }
}
