package dev.tonholo.s2c.logger

import AppConfig

fun debug(message: Any) {
    if (AppConfig.debug) {
        println(message)
    }
}

fun debugSection(message: String) {
    if (AppConfig.debug) {
        println()
        val section = "=".repeat(25 - (message.length / 2))
        println("$section $message $section")
        println()
    }
}

fun debugEndSection() {
    if (AppConfig.debug) {
        println()
        println("=".repeat(n = 50))
        println()
    }
}

fun verbose(message: String) {
    if (AppConfig.verbose) {
        println(message)
    }
}

fun output(message: String) {
    println(message)
}

fun printEmpty() = println()
