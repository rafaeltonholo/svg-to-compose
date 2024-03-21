package dev.tonholo.s2c.domain.avg

import kotlin.jvm.JvmInline

@JvmInline
value class AvgColor(val value: String) {
    override fun toString(): String = value
}
