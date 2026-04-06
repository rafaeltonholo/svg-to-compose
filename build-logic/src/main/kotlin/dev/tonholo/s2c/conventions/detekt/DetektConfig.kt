package dev.tonholo.s2c.conventions.detekt

import dev.tonholo.s2c.conventions.Versions

object DetektConfig {
    val javaVersion = Versions.javaVersion.toString()
    val excludedDirs = setOf("build", "playground")
}
