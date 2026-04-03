package dev.tonholo.s2c.domain.avg

import dev.tonholo.s2c.domain.compose.ComposeBrush
import dev.tonholo.s2c.domain.compose.ComposeColor
import dev.tonholo.s2c.domain.xml.XmlNode
import dev.tonholo.s2c.domain.xml.XmlRootNode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

/**
 * Tests for [AvgGradientNode] color stop computation,
 * verifying both shorthand color attributes and explicit `<item>` children.
 */
class AvgGradientNodeTest {
    private val root = XmlRootNode(children = mutableSetOf())

    private fun createGradient(
        attributes: MutableMap<String, String>,
        children: MutableSet<XmlNode> = mutableSetOf(),
    ): AvgGradientNode = AvgGradientNode(
        parent = root,
        children = children,
        attributes = attributes,
    )

    private fun createItem(
        parent: AvgGradientNode,
        color: String? = null,
        offset: String? = null,
    ): AvgGradientItemNode {
        val attrs = mutableMapOf<String, String>()
        color?.let { attrs["android:color"] = it }
        offset?.let { attrs["android:offset"] = it }
        return AvgGradientItemNode(parent = parent, attributes = attrs)
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
        assertEquals(
            expected = listOf(ComposeColor("#FF0000"), ComposeColor("#0000FF")),
            actual = brush.colors,
        )
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
        assertEquals(
            expected = listOf(
                ComposeColor("#FF0000"),
                ComposeColor("#00FF00"),
                ComposeColor("#0000FF"),
            ),
            actual = brush.colors,
        )
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
        assertEquals(
            expected = listOf(
                ComposeColor("#FF0000"),
                ComposeColor("#00FF00"),
                ComposeColor("#0000FF"),
            ),
            actual = brush.colors,
        )
        assertEquals(expected = listOf(0f, 0.5f, 1f), actual = brush.stops)
    }

    @Test
    fun `given sweep gradient with centerColor - when toBrush - then produces 3 color stops`() {
        val gradient = createGradient(
            mutableMapOf(
                "android:type" to "sweep",
                "android:startColor" to "#FF0000",
                "android:centerColor" to "#00FF00",
                "android:endColor" to "#0000FF",
                "android:centerX" to "12",
                "android:centerY" to "12",
            ),
        )

        val brush = gradient.toBrush()

        assertIs<ComposeBrush.Gradient.Sweep>(brush)
        assertEquals(
            expected = listOf(
                ComposeColor("#FF0000"),
                ComposeColor("#00FF00"),
                ComposeColor("#0000FF"),
            ),
            actual = brush.colors,
        )
        assertEquals(expected = listOf(0f, 0.5f, 1f), actual = brush.stops)
    }

    @Test
    fun `given gradient with item children - when toBrush - then uses item colors and stops`() {
        val children = mutableSetOf<XmlNode>()
        val gradient = createGradient(
            attributes = mutableMapOf(
                "android:type" to "linear",
                "android:startX" to "0",
                "android:startY" to "0",
                "android:endX" to "24",
                "android:endY" to "24",
            ),
            children = children,
        )
        children.add(createItem(gradient, color = "#FF0000", offset = "0"))
        children.add(createItem(gradient, color = "#00FF00", offset = "0.5"))
        children.add(createItem(gradient, color = "#0000FF", offset = "1"))

        val brush = gradient.toBrush()

        assertIs<ComposeBrush.Gradient.Linear>(brush)
        assertEquals(
            expected = listOf(
                ComposeColor("#FF0000"),
                ComposeColor("#00FF00"),
                ComposeColor("#0000FF"),
            ),
            actual = brush.colors,
        )
        assertEquals(expected = listOf(0f, 0.5f, 1f), actual = brush.stops)
    }

    @Test
    fun `given gradient items with missing color or offset - when toBrush - then skips incomplete items`() {
        val children = mutableSetOf<XmlNode>()
        val gradient = createGradient(
            attributes = mutableMapOf(
                "android:type" to "linear",
                "android:startX" to "0",
                "android:startY" to "0",
                "android:endX" to "24",
                "android:endY" to "24",
            ),
            children = children,
        )
        children.add(createItem(gradient, color = "#FF0000", offset = "0"))
        children.add(createItem(gradient, color = "#00FF00", offset = null))
        children.add(createItem(gradient, color = null, offset = "0.5"))
        children.add(createItem(gradient, color = "#0000FF", offset = "1"))

        val brush = gradient.toBrush()

        assertIs<ComposeBrush.Gradient.Linear>(brush)
        assertEquals(
            expected = listOf(ComposeColor("#FF0000"), ComposeColor("#0000FF")),
            actual = brush.colors,
        )
        assertEquals(expected = listOf(0f, 1f), actual = brush.stops)
    }
}
