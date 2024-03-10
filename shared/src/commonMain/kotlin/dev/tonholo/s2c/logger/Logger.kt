package dev.tonholo.s2c.logger

import AppConfig

fun debug(message: Any) {
    if (AppConfig.debug) {
        println("D: $message")
    }
}

fun <T> debugSection(title: String, block: () -> T): T =
    if (AppConfig.debug) {
        startSection(title)
        block().also { endSection() }
    } else {
        block()
    }

fun <T> verboseSection(title: String, block: () -> T) =
    if (AppConfig.verbose) {
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

fun verbose(message: String) {
    if (AppConfig.verbose) {
        println("V: $message")
    }
}

inline fun warn(message: String) {
    println("WARNING ⚠️: $message") // TODO: add color to output.
}

inline fun output(message: String) {
    println(message)
}

inline fun printEmpty() = println()
