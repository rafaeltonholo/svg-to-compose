package dev.tonholo.s2c.domain

import kotlin.jvm.JvmInline

@JvmInline
value class PathCommand(val value: Char) : Comparable<Char> by value {
    fun uppercaseChar(): Char = value.uppercaseChar()
    override fun toString(): String = value.toString()
}

inline fun String.startsWith(pathCommand: PathCommand): Boolean =
    startsWith(pathCommand.value)
