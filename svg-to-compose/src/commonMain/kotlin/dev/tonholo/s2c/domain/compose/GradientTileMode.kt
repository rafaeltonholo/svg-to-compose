package dev.tonholo.s2c.domain.compose

import kotlin.jvm.JvmInline

@JvmInline
value class GradientTileMode private constructor(override val value: String) : ComposeType<String> {
    companion object {
        private const val NAME = "TileMode"
        private val IMPORT = setOf("androidx.compose.ui.graphics.$NAME")
        val Clamp = GradientTileMode("Clamp")
        val Repeated = GradientTileMode("Repeated")
        val Mirror = GradientTileMode("Mirror")
        val Decal = GradientTileMode("Decal")

        operator fun invoke(value: String?): GradientTileMode? = value?.let {
            val tileMode = GradientTileMode(value.replaceFirstChar { it.uppercaseChar() })
            return tileMode.toCompose()?.let { tileMode }
        }
    }

    override val name: String
        get() = NAME
    override val imports: Set<String>
        get() = IMPORT

    override fun toCompose(): String? = when (this.value) {
        Clamp.value, Clamp.value.lowercase(),
        Repeated.value, Repeated.value.lowercase(),
        Mirror.value, Mirror.value.lowercase(),
        Decal.value, Decal.value.lowercase(),
        -> "$NAME.$value"

        else -> null
    }
}
