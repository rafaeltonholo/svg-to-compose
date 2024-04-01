package dev.tonholo.s2c.domain.compose

import dev.tonholo.s2c.parser.method.MethodSizeAccountable

data class ComposeOffset(val x: Float?, val y: Float?) : ComposeType<Pair<Float?, Float?>>, MethodSizeAccountable {
    companion object {
        private const val NAME = "Offset"
        private const val BYTECODE_SIZE = 6
        const val INFINITE = "$NAME.Infinite"
        const val ZERO = "$NAME.Zero"
        private val IMPORT = setOf("androidx.compose.ui.geometry.$NAME")
        val Infinite = ComposeOffset(x = null, y = null)
        val Zero = ComposeOffset(x = 0f, y = 0f)
    }

    override val name: String = NAME
    override val imports: Set<String> = IMPORT
    override val value: Pair<Float?, Float?> = x to y

    // Each Offset account, approximately, with 6 bytes.
    override val approximateByteSize: Int = BYTECODE_SIZE

    override fun toCompose(): String = when {
        this == Infinite -> "$name.Infinite" // should it be Unspecified instead?
        this == Zero -> "$name.Zero"
        x != null && y == null || x == null && y != null -> "$name.Unspecified" // Should be Offset(x, 0) and Offset(0, y) instead?
        else -> "$name(x = ${x}f, y = ${y}f)"
    }
}
