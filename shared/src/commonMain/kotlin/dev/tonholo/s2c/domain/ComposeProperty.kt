package dev.tonholo.s2c.domain

import kotlin.jvm.JvmInline

interface ComposeProperty {
    fun toCompose(): String?
}

@JvmInline
value class PathFillType private constructor(private val value: String) : ComposeProperty {
    override fun toString(): String = value

    companion object {
        const val IMPORT = "androidx.compose.ui.graphics.PathFillType"
        val EvenOdd = PathFillType("EvenOdd")
        val NonZero = PathFillType("NonZero")

        operator fun invoke(value: String?): PathFillType? = value?.let {
            return PathFillType(value.replaceFirstChar { it.uppercaseChar() })
        }
    }

    override fun toCompose(): String? = when (this.value) {
        EvenOdd.value, NonZero.value,
        EvenOdd.value.lowercase(), NonZero.value.lowercase() ->
            "PathFillType.$value"

        else -> null
    }
}

@JvmInline
value class StrokeCap private constructor(private val value: String) : ComposeProperty {
    override fun toString(): String = value

    companion object {
        const val IMPORT = "androidx.compose.ui.graphics.StrokeCap"
        val Butt = StrokeCap("Butt")
        val Round = StrokeCap("Round")
        val Square = StrokeCap("Square")

        operator fun invoke(value: String?): StrokeCap? = value?.let {
            return StrokeCap(value.replaceFirstChar { it.uppercaseChar() })
        }
    }

    override fun toCompose(): String? = when (this) {
        Butt, Round, Square -> "StrokeCap.$value"
        else -> null // Unsupported by Compose
    }
}

@JvmInline
value class StrokeJoin private constructor(private val value: String) : ComposeProperty {
    override fun toString(): String = value

    companion object {
        const val IMPORT = "androidx.compose.ui.graphics.StrokeJoin"
        val Miter = StrokeJoin("Miter")
        val Round = StrokeJoin("Round")
        val Bevel = StrokeJoin("Bevel")

        operator fun invoke(value: String?): StrokeJoin? = value?.let {
            return StrokeJoin(value.replaceFirstChar { it.uppercaseChar() })
        }
    }

    override fun toCompose(): String? = when (this) {
        Miter, Round, Bevel -> "StrokeJoin.$value"
        else -> null
    }
}
