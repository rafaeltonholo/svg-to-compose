package dev.tonholo.s2c.domain.avg

import dev.tonholo.s2c.domain.compose.ComposeBrush
import dev.tonholo.s2c.domain.xml.XmlRootNode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

/**
 * Tests for [AvgGradientNode] color stop computation,
 * specifically verifying that `centerColor` is included
 * when building gradient stops from shorthand attributes.
 */
class AvgGradientNodeTest {
    private val root = XmlRootNode(children = mutableSetOf())

    /**
     * Creates an [AvgGradientNode] with no child `<item>` elements,
     * using only shorthand color attributes.
     */
    private fun createGradient(attributes: MutableMap<String, String>): AvgGradientNode {
        return AvgGradientNode(
            parent = root,
            children = mutableSetOf(),
            attributes = attributes,
        )
    }

    @Test
    fun `given linear gradient with startColor and endColor only - when toBrush - then produces 2 color stops`() {
        val gradient = createGradient(
            mutableMapOf(
                "android:type" to "linear",
                "android:startColor" to "#FF0000",
                "android:endColor" to "#0000FF",
                "android:startX" to "0",
                "android:startY" to "0",
                "android:endX" to "24",
                "android:endY" to "24",
            ),
        )

        val brush = gradient.toBrush()

        assertIs<ComposeBrush.Gradient.Linear>(brush)
        assertEquals(expected = 2, actual = brush.colors.size)
        assertEquals(expected = listOf(0f, 1f), actual = brush.stops)
    }

    @Test
    fun `given linear gradient with centerColor - when toBrush - then produces 3 color stops`() {
        val gradient = createGradient(
            mutableMapOf(
                "android:type" to "linear",
                "android:startColor" to "#FF0000",
                "android:centerColor" to "#00FF00",
                "android:endColor" to "#0000FF",
                "android:startX" to "0",
                "android:startY" to "0",
                "android:endX" to "24",
                "android:endY" to "24",
            ),
        )

        val brush = gradient.toBrush()

        assertIs<ComposeBrush.Gradient.Linear>(brush)
        assertEquals(expected = 3, actual = brush.colors.size)
        assertEquals(expected = listOf(0f, 0.5f, 1f), actual = brush.stops)
    }

    @Test
    fun `given radial gradient with centerColor - when toBrush - then produces 3 color stops`() {
        val gradient = createGradient(
            mutableMapOf(
                "android:type" to "radial",
                "android:startColor" to "#FF0000",
                "android:centerColor" to "#00FF00",
                "android:endColor" to "#0000FF",
                "android:centerX" to "12",
                "android:centerY" to "12",
                "android:gradientRadius" to "12",
            ),
        )

        val brush = gradient.toBrush()

        assertIs<ComposeBrush.Gradient.Radial>(brush)
        assertEquals(expected = 3, actual = brush.colors.size)
        assertEquals(expected = listOf(0f, 0.5f, 1f), actual = brush.stops)
    }
}
