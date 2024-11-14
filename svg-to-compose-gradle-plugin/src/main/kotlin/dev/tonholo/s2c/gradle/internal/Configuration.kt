package dev.tonholo.s2c.gradle.internal

import org.jetbrains.kotlin.konan.util.Named

typealias ConfigurationErrors = List<String>

internal interface Configuration: Named {
    val parentName: String
    val fullName: String get() = "$parentName.$name"
    fun configurationName(): String =
        "Configuration '$fullName'"
    fun validate(): ConfigurationErrors
}
