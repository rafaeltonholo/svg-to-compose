package dev.tonholo.s2c.domain.compose

data class ComposeOffset(val x: Float, val y: Float) : ComposeType<Pair<Float, Float>> {
    companion object {
        private const val NAME = "Offset"
        private val IMPORT = setOf("androidx.compose.ui.geometry.$NAME")
    }

    override val name: String = NAME
    override val imports: Set<String> = IMPORT
    override val value: Pair<Float, Float> = x to y

    override fun toCompose(): String =
        "$name(x = ${x}f, y = ${y}f)"
}
