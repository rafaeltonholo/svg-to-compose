package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.compose.ComposeBrush
import dev.tonholo.s2c.domain.compose.ComposeOffset
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class SvgLinearGradientNodeTest : BaseSvgTest() {

    private fun createGradientWithStops(attributes: MutableMap<String, String>): SvgLinearGradientNode {
        val gradient = SvgLinearGradientNode(
            parent = root,
            children = mutableSetOf(),
            attributes = attributes,
        )
        val stop1 = SvgGradientStopNode(
            parent = gradient,
            attributes = mutableMapOf("offset" to "0", "stop-color" to "#FF0000"),
        )
        val stop2 = SvgGradientStopNode(
            parent = gradient,
            attributes = mutableMapOf("offset" to "1", "stop-color" to "#0000FF"),
        )
        gradient.children.addAll(listOf(stop1, stop2))
        return gradient
    }

    @Test
    fun `given userSpaceOnUse linear gradient with translate transform - when toBrush is called - then start and end offsets are transformed`() {
        // Arrange
        root.attributes["width"] = "100"
        root.attributes["height"] = "100"
        root.attributes["viewBox"] = "0 0 100 100"
        val gradient = createGradientWithStops(
            attributes = mutableMapOf(
                "id" to "linGrad1",
                "x1" to "10",
                "y1" to "20",
                "x2" to "80",
                "y2" to "90",
                "gradientUnits" to "userSpaceOnUse",
                "gradientTransform" to "translate(5 10)",
            ),
        )
        root.gradients["linGrad1"] = gradient

        // Act
        val brush = gradient.toBrush(target = emptyList())

        // Assert
        assertIs<ComposeBrush.Gradient.Linear>(brush)
        assertEquals(
            expected = ComposeOffset(x = 15f, y = 30f),
            actual = brush.start,
        )
        assertEquals(
            expected = ComposeOffset(x = 85f, y = 100f),
            actual = brush.end,
        )
    }

    @Test
    fun `given userSpaceOnUse linear gradient without gradientTransform - when toBrush is called - then coordinates pass through unchanged`() {
        // Arrange
        root.attributes["width"] = "100"
        root.attributes["height"] = "100"
        root.attributes["viewBox"] = "0 0 100 100"
        val gradient = createGradientWithStops(
            attributes = mutableMapOf(
                "id" to "linGrad2",
                "x1" to "10",
                "y1" to "20",
                "x2" to "80",
                "y2" to "90",
                "gradientUnits" to "userSpaceOnUse",
            ),
        )
        root.gradients["linGrad2"] = gradient

        // Act
        val brush = gradient.toBrush(target = emptyList())

        // Assert
        assertIs<ComposeBrush.Gradient.Linear>(brush)
        assertEquals(
            expected = ComposeOffset(x = 10f, y = 20f),
            actual = brush.start,
        )
        assertEquals(
            expected = ComposeOffset(x = 80f, y = 90f),
            actual = brush.end,
        )
    }

    @Test
    fun `given userSpaceOnUse linear gradient with scale transform - when toBrush is called - then start and end offsets are scaled`() {
        // Arrange
        root.attributes["width"] = "100"
        root.attributes["height"] = "100"
        root.attributes["viewBox"] = "0 0 100 100"
        val gradient = createGradientWithStops(
            attributes = mutableMapOf(
                "id" to "linGrad3",
                "x1" to "0",
                "y1" to "0",
                "x2" to "50",
                "y2" to "50",
                "gradientUnits" to "userSpaceOnUse",
                "gradientTransform" to "scale(2)",
            ),
        )
        root.gradients["linGrad3"] = gradient

        // Act
        val brush = gradient.toBrush(target = emptyList())

        // Assert
        assertIs<ComposeBrush.Gradient.Linear>(brush)
        assertEquals(
            expected = ComposeOffset(x = 0f, y = 0f),
            actual = brush.start,
        )
        assertEquals(
            expected = ComposeOffset(x = 100f, y = 100f),
            actual = brush.end,
        )
    }

    @Test
    fun `given userSpaceOnUse linear gradient with rotate transform - when toBrush is called - then start and end offsets are rotated`() {
        // Arrange
        root.attributes["width"] = "100"
        root.attributes["height"] = "100"
        root.attributes["viewBox"] = "0 0 100 100"
        val gradient = createGradientWithStops(
            attributes = mutableMapOf(
                "id" to "linGrad4",
                "x1" to "100",
                "y1" to "0",
                "x2" to "0",
                "y2" to "0",
                "gradientUnits" to "userSpaceOnUse",
                "gradientTransform" to "rotate(90)",
            ),
        )
        root.gradients["linGrad4"] = gradient

        // Act
        val brush = gradient.toBrush(target = emptyList())

        // Assert — rotating (100,0) by 90° gives (0,100); (0,0) stays (0,0)
        assertIs<ComposeBrush.Gradient.Linear>(brush)
        assertEquals(0f, brush.start.x ?: 0f, 0.001f)
        assertEquals(100f, brush.start.y ?: 0f, 0.001f)
        assertEquals(0f, brush.end.x ?: 0f, 0.001f)
        assertEquals(0f, brush.end.y ?: 0f, 0.001f)
    }

    @Test
    fun `given userSpaceOnUse linear gradient with non-uniform scale - when toBrush is called - then axes are scaled independently`() {
        // Arrange
        root.attributes["width"] = "100"
        root.attributes["height"] = "100"
        root.attributes["viewBox"] = "0 0 100 100"
        val gradient = createGradientWithStops(
            attributes = mutableMapOf(
                "id" to "linGrad5",
                "x1" to "10",
                "y1" to "10",
                "x2" to "20",
                "y2" to "20",
                "gradientUnits" to "userSpaceOnUse",
                "gradientTransform" to "scale(3 5)",
            ),
        )
        root.gradients["linGrad5"] = gradient

        // Act
        val brush = gradient.toBrush(target = emptyList())

        // Assert
        assertIs<ComposeBrush.Gradient.Linear>(brush)
        assertEquals(
            expected = ComposeOffset(x = 30f, y = 50f),
            actual = brush.start,
        )
        assertEquals(
            expected = ComposeOffset(x = 60f, y = 100f),
            actual = brush.end,
        )
    }

    @Test
    fun `given userSpaceOnUse linear gradient with combined translate and scale - when toBrush is called - then both transforms apply`() {
        // Arrange
        root.attributes["width"] = "100"
        root.attributes["height"] = "100"
        root.attributes["viewBox"] = "0 0 100 100"
        val gradient = createGradientWithStops(
            attributes = mutableMapOf(
                "id" to "linGrad6",
                "x1" to "0",
                "y1" to "0",
                "x2" to "1",
                "y2" to "1",
                "gradientUnits" to "userSpaceOnUse",
                "gradientTransform" to "translate(10 20) scale(50)",
            ),
        )
        root.gradients["linGrad6"] = gradient

        // Act
        val brush = gradient.toBrush(target = emptyList())

        // Assert — scale(50) then translate(10,20): (0,0)→(10,20), (1,1)→(60,70)
        assertIs<ComposeBrush.Gradient.Linear>(brush)
        assertEquals(
            expected = ComposeOffset(x = 10f, y = 20f),
            actual = brush.start,
        )
        assertEquals(
            expected = ComposeOffset(x = 60f, y = 70f),
            actual = brush.end,
        )
    }

    @Test
    fun `given userSpaceOnUse linear gradient with matrix transform - when toBrush is called - then full matrix is applied`() {
        // Arrange — matrix(a,b,c,d,e,f) = matrix(2, 0, 0, 3, 10, 20)
        // This is scale(2,3) + translate(10,20)
        root.attributes["width"] = "100"
        root.attributes["height"] = "100"
        root.attributes["viewBox"] = "0 0 100 100"
        val gradient = createGradientWithStops(
            attributes = mutableMapOf(
                "id" to "linGrad7",
                "x1" to "5",
                "y1" to "5",
                "x2" to "15",
                "y2" to "10",
                "gradientUnits" to "userSpaceOnUse",
                "gradientTransform" to "matrix(2 0 0 3 10 20)",
            ),
        )
        root.gradients["linGrad7"] = gradient

        // Act
        val brush = gradient.toBrush(target = emptyList())

        // Assert — (5,5) → (2*5+10, 3*5+20) = (20, 35)
        //          (15,10) → (2*15+10, 3*10+20) = (40, 50)
        assertIs<ComposeBrush.Gradient.Linear>(brush)
        assertEquals(
            expected = ComposeOffset(x = 20f, y = 35f),
            actual = brush.start,
        )
        assertEquals(
            expected = ComposeOffset(x = 40f, y = 50f),
            actual = brush.end,
        )
    }

    @Test
    fun `given userSpaceOnUse linear gradient with origin at zero and translate - when toBrush is called - then start moves to translate offset`() {
        // Arrange — common pattern: gradient defined at origin, positioned via transform
        root.attributes["width"] = "100"
        root.attributes["height"] = "100"
        root.attributes["viewBox"] = "0 0 100 100"
        val gradient = createGradientWithStops(
            attributes = mutableMapOf(
                "id" to "linGrad8",
                "x1" to "0",
                "y1" to "0",
                "x2" to "0",
                "y2" to "1",
                "gradientUnits" to "userSpaceOnUse",
                "gradientTransform" to "translate(50 50) scale(30)",
            ),
        )
        root.gradients["linGrad8"] = gradient

        // Act
        val brush = gradient.toBrush(target = emptyList())

        // Assert — (0,0)→(50,50), (0,1)→(50,80)
        assertIs<ComposeBrush.Gradient.Linear>(brush)
        assertEquals(
            expected = ComposeOffset(x = 50f, y = 50f),
            actual = brush.start,
        )
        assertEquals(
            expected = ComposeOffset(x = 50f, y = 80f),
            actual = brush.end,
        )
    }

    @Test
    fun `given non-square viewport with userSpaceOnUse linear gradient - when toBrush is called - then coordinates use correct axis dimensions`() {
        // Arrange — non-square viewport to catch the XY vs Y bug
        root.attributes["width"] = "200"
        root.attributes["height"] = "100"
        root.attributes["viewBox"] = "0 0 200 100"
        val gradient = createGradientWithStops(
            attributes = mutableMapOf(
                "id" to "linGrad9",
                "x1" to "0",
                "y1" to "50",
                "x2" to "100",
                "y2" to "50",
                "gradientUnits" to "userSpaceOnUse",
            ),
        )
        root.gradients["linGrad9"] = gradient

        // Act
        val brush = gradient.toBrush(target = emptyList())

        // Assert — y=50 should resolve to 50, not be affected by width
        assertIs<ComposeBrush.Gradient.Linear>(brush)
        assertEquals(
            expected = ComposeOffset(x = 0f, y = 50f),
            actual = brush.start,
        )
        assertEquals(
            expected = ComposeOffset(x = 100f, y = 50f),
            actual = brush.end,
        )
    }

    @Test
    fun `given linearGradient href referencing a radialGradient - when toBrush is called - then produces linear brush with own stops`() {
        // Arrange - per SVG spec, cross-type href inherits stops; but since this
        // linearGradient has its own stops, it should use them and produce a linear brush.
        root.attributes["width"] = "100"
        root.attributes["height"] = "100"
        root.attributes["viewBox"] = "0 0 100 100"
        val radialGradient = SvgRadialGradientNode(
            parent = root,
            children = mutableSetOf(),
            attributes = mutableMapOf("id" to "radGrad1"),
        )
        root.gradients["radGrad1"] = radialGradient
        val linearGradient = createGradientWithStops(
            attributes = mutableMapOf(
                "id" to "linGradRef",
                "xlink:href" to "#radGrad1",
                "gradientUnits" to "userSpaceOnUse",
                "x1" to "0",
                "y1" to "0",
                "x2" to "100",
                "y2" to "100",
            ),
        )
        root.gradients["linGradRef"] = linearGradient

        // Act
        val brush = linearGradient.toBrush(target = emptyList())

        // Assert - must be a LINEAR brush, not radial. Uses own 2 stops.
        assertIs<ComposeBrush.Gradient.Linear>(brush)
        assertEquals(expected = 2, actual = brush.colors.size)
        assertEquals(expected = listOf(0f, 1f), actual = brush.stops)
    }
}
