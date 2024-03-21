package dev.tonholo.s2c.domain.avg

import dev.tonholo.s2c.domain.compose.ComposeColor
import kotlin.jvm.JvmInline

@JvmInline
value class AvgColor(val value: String) {
    override fun toString(): String = value
}

fun AvgColor.toComposeColor(): ComposeColor = ComposeColor(value)
