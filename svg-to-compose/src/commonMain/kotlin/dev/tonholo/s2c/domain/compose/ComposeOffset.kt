package dev.tonholo.s2c.domain.compose

data class ComposeOffset(val x: Float?, val y: Float?) : ComposeType<Pair<Float?, Float?>> {
    companion object {
        private const val NAME = "Offset"
        private val IMPORT = setOf("androidx.compose.ui.geometry.$NAME")
    }

    override val name: String = NAME
    override val imports: Set<String> = IMPORT
    override val value: Pair<Float?, Float?> = x to y

    override fun toCompose(): String = when {
        x == null && y == null -> "$name.Infinite"
        x == 0f && y == 0f -> "$name.Zero"
        x != null && y == null || x == null && y != null -> "$name.Unspecified"
        else -> "$name(x = ${x}f, y = ${y}f)"
    }
}
