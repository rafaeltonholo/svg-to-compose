package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.compose.ComposeBrush
import dev.tonholo.s2c.domain.compose.ComposeOffset
import kotlin.math.sqrt
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertIs
import kotlin.test.assertNotNull

class SvgRadialGradientNodeTest : BaseSvgTest() {

    private fun createGradientWithStops(attributes: MutableMap<String, String>): SvgRadialGradientNode {
        val gradient = SvgRadialGradientNode(
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
            attributes = mutableMapOf("offset" to "0.5", "stop-color" to "#00FF00"),
        )
        val stop3 = SvgGradientStopNode(
            parent = gradient,
            attributes = mutableMapOf("offset" to "1", "stop-color" to "#0000FF"),
        )
        gradient.children.addAll(listOf(stop1, stop2, stop3))
        return gradient
    }

    @Test
    fun `given userSpaceOnUse radial gradient with translate scale transform - when toBrush is called - then center and radius are transformed`() {
        // Arrange
        root.attributes["width"] = "22"
        root.attributes["height"] = "22"
        root.attributes["viewBox"] = "0 0 22 22"
        val gradient = createGradientWithStops(
            attributes = mutableMapOf(
                "id" to "grad1",
                "cx" to "0",
                "cy" to "0",
                "r" to "1",
                "gradientUnits" to "userSpaceOnUse",
                "gradientTransform" to "translate(19.335 1.822) scale(22.9097)",
            ),
        )
        root.gradients["grad1"] = gradient

        // Act
        val brush = gradient.toBrush(target = emptyList())

        // Assert
        assertIs<ComposeBrush.Gradient.Radial>(brush)
        assertEquals(
            expected = ComposeOffset(x = 19.335f, y = 1.822f),
            actual = brush.center,
        )
        assertEquals(
            expected = 22.9097f,
            actual = brush.radius,
        )
    }

    @Test
    fun `given userSpaceOnUse radial gradient without gradientTransform - when toBrush is called - then coordinates pass through unchanged`() {
        // Arrange
        root.attributes["width"] = "100"
        root.attributes["height"] = "100"
        root.attributes["viewBox"] = "0 0 100 100"
        val gradient = createGradientWithStops(
            attributes = mutableMapOf(
                "id" to "grad2",
                "cx" to "50",
                "cy" to "60",
                "r" to "40",
                "gradientUnits" to "userSpaceOnUse",
            ),
        )
        root.gradients["grad2"] = gradient

        // Act
        val brush = gradient.toBrush(target = emptyList())

        // Assert
        assertIs<ComposeBrush.Gradient.Radial>(brush)
        assertEquals(
            expected = ComposeOffset(x = 50f, y = 60f),
            actual = brush.center,
        )
        assertEquals(
            expected = 40f,
            actual = brush.radius,
        )
    }

    @Test
    fun `given userSpaceOnUse radial gradient with translate only transform - when toBrush is called - then center is translated and radius unchanged`() {
        // Arrange
        root.attributes["width"] = "100"
        root.attributes["height"] = "100"
        root.attributes["viewBox"] = "0 0 100 100"
        val gradient = createGradientWithStops(
            attributes = mutableMapOf(
                "id" to "grad3",
                "cx" to "10",
                "cy" to "20",
                "r" to "30",
                "gradientUnits" to "userSpaceOnUse",
                "gradientTransform" to "translate(5 15)",
            ),
        )
        root.gradients["grad3"] = gradient

        // Act
        val brush = gradient.toBrush(target = emptyList())

        // Assert
        assertIs<ComposeBrush.Gradient.Radial>(brush)
        assertEquals(
            expected = ComposeOffset(x = 15f, y = 35f),
            actual = brush.center,
        )
        assertEquals(
            expected = 30f,
            actual = brush.radius,
        )
    }

    @Test
    fun `given userSpaceOnUse radial gradient with scale only transform - when toBrush is called - then center and radius are scaled`() {
        // Arrange
        root.attributes["width"] = "100"
        root.attributes["height"] = "100"
        root.attributes["viewBox"] = "0 0 100 100"
        val gradient = createGradientWithStops(
            attributes = mutableMapOf(
                "id" to "grad4",
                "cx" to "10",
                "cy" to "20",
                "r" to "5",
                "gradientUnits" to "userSpaceOnUse",
                "gradientTransform" to "scale(2)",
            ),
        )
        root.gradients["grad4"] = gradient

        // Act
        val brush = gradient.toBrush(target = emptyList())

        // Assert
        assertIs<ComposeBrush.Gradient.Radial>(brush)
        assertEquals(
            expected = ComposeOffset(x = 20f, y = 40f),
            actual = brush.center,
        )
        assertEquals(
            expected = 10f,
            actual = brush.radius,
        )
    }

    @Test
    fun `given userSpaceOnUse radial gradient with non-uniform scale - when toBrush is called - then radius uses RMS approximation`() {
        // Arrange
        root.attributes["width"] = "100"
        root.attributes["height"] = "100"
        root.attributes["viewBox"] = "0 0 100 100"
        val gradient = createGradientWithStops(
            attributes = mutableMapOf(
                "id" to "grad5",
                "cx" to "0",
                "cy" to "0",
                "r" to "1",
                "gradientUnits" to "userSpaceOnUse",
                "gradientTransform" to "scale(3 5)",
            ),
        )
        root.gradients["grad5"] = gradient

        // Act
        val brush = gradient.toBrush(target = emptyList())

        // Assert — center: (0*3, 0*5) = (0,0)
        //          radius: 1 * sqrt((3²+5²)/2) = sqrt((9+25)/2) = sqrt(17) ≈ 4.123
        assertIs<ComposeBrush.Gradient.Radial>(brush)
        assertEquals(
            expected = ComposeOffset(x = 0f, y = 0f),
            actual = brush.center,
        )
        assertEquals(
            expected = sqrt(17.0).toFloat(),
            actual = brush.radius,
        )
    }

    @Test
    fun `given userSpaceOnUse radial gradient with rotate transform - when toBrush is called - then center is rotated and radius unchanged`() {
        // Arrange — rotation preserves distances, so radius should stay the same
        root.attributes["width"] = "100"
        root.attributes["height"] = "100"
        root.attributes["viewBox"] = "0 0 100 100"
        val gradient = createGradientWithStops(
            attributes = mutableMapOf(
                "id" to "grad6",
                "cx" to "50",
                "cy" to "0",
                "r" to "25",
                "gradientUnits" to "userSpaceOnUse",
                "gradientTransform" to "rotate(90)",
            ),
        )
        root.gradients["grad6"] = gradient

        // Act
        val brush = gradient.toBrush(target = emptyList())

        // Assert — rotating (50,0) by 90° gives (0,50)
        //          radius stays 25 (rotation is isometric: a²+b²+c²+d² = cos²+sin²+(-sin)²+cos² = 2)
        assertIs<ComposeBrush.Gradient.Radial>(brush)
        assertEquals(0f, brush.center?.x ?: 0f, 0.001f)
        assertEquals(50f, brush.center?.y ?: 0f, 0.001f)
        assertEquals(25f, brush.radius ?: 0f, 0.001f)
    }

    @Test
    fun `given userSpaceOnUse radial gradient with matrix transform - when toBrush is called - then full matrix is applied`() {
        // Arrange — matrix(2, 0, 0, 3, 10, 20) = scale(2,3) + translate(10,20)
        root.attributes["width"] = "100"
        root.attributes["height"] = "100"
        root.attributes["viewBox"] = "0 0 100 100"
        val gradient = createGradientWithStops(
            attributes = mutableMapOf(
                "id" to "grad7",
                "cx" to "5",
                "cy" to "5",
                "r" to "10",
                "gradientUnits" to "userSpaceOnUse",
                "gradientTransform" to "matrix(2 0 0 3 10 20)",
            ),
        )
        root.gradients["grad7"] = gradient

        // Act
        val brush = gradient.toBrush(target = emptyList())

        // Assert — center: (2*5+10, 3*5+20) = (20, 35)
        //          radius: 10 * sqrt((4+0+0+9)/2) = 10 * sqrt(6.5) ≈ 25.495
        assertIs<ComposeBrush.Gradient.Radial>(brush)
        assertEquals(
            expected = ComposeOffset(x = 20f, y = 35f),
            actual = brush.center,
        )
        assertEquals(
            expected = (10 * sqrt(6.5)).toFloat(),
            actual = brush.radius,
        )
    }

    @Test
    fun `given userSpaceOnUse radial gradient with origin center and translate scale - when toBrush is called - then center moves to translate offset`() {
        // Arrange — common pattern: gradient at origin, positioned via transform (like kotlin.svg)
        root.attributes["width"] = "100"
        root.attributes["height"] = "100"
        root.attributes["viewBox"] = "0 0 100 100"
        val gradient = createGradientWithStops(
            attributes = mutableMapOf(
                "id" to "grad8",
                "cx" to "0",
                "cy" to "0",
                "r" to "1",
                "gradientUnits" to "userSpaceOnUse",
                "gradientTransform" to "translate(50 50) scale(40)",
            ),
        )
        root.gradients["grad8"] = gradient

        // Act
        val brush = gradient.toBrush(target = emptyList())

        // Assert — (0,0) → (50,50), r: 1*40 = 40
        assertIs<ComposeBrush.Gradient.Radial>(brush)
        assertEquals(
            expected = ComposeOffset(x = 50f, y = 50f),
            actual = brush.center,
        )
        assertEquals(
            expected = 40f,
            actual = brush.radius,
        )
    }

    @Test
    fun `given userSpaceOnUse radial gradient with identity matrix - when toBrush is called - then coordinates pass through unchanged`() {
        // Arrange
        root.attributes["width"] = "100"
        root.attributes["height"] = "100"
        root.attributes["viewBox"] = "0 0 100 100"
        val gradient = createGradientWithStops(
            attributes = mutableMapOf(
                "id" to "grad9",
                "cx" to "30",
                "cy" to "40",
                "r" to "20",
                "gradientUnits" to "userSpaceOnUse",
                "gradientTransform" to "matrix(1 0 0 1 0 0)",
            ),
        )
        root.gradients["grad9"] = gradient

        // Act
        val brush = gradient.toBrush(target = emptyList())

        // Assert — identity matrix should not change anything
        assertIs<ComposeBrush.Gradient.Radial>(brush)
        assertEquals(
            expected = ComposeOffset(x = 30f, y = 40f),
            actual = brush.center,
        )
        assertEquals(
            expected = 20f,
            actual = brush.radius,
        )
    }

    @Test
    fun `given non-square viewport with userSpaceOnUse radial gradient - when toBrush is called - then radius uses max dimension`() {
        // Arrange
        root.attributes["width"] = "200"
        root.attributes["height"] = "100"
        root.attributes["viewBox"] = "0 0 200 100"
        val gradient = createGradientWithStops(
            attributes = mutableMapOf(
                "id" to "grad10",
                "cx" to "50",
                "cy" to "50",
                "r" to "30",
                "gradientUnits" to "userSpaceOnUse",
            ),
        )
        root.gradients["grad10"] = gradient

        // Act
        val brush = gradient.toBrush(target = emptyList())

        // Assert — cx resolves against width (200), cy against height (100),
        //          r resolves against max(200,100) = 200 but plain numbers
        //          just pass through regardless
        assertIs<ComposeBrush.Gradient.Radial>(brush)
        assertEquals(
            expected = ComposeOffset(x = 50f, y = 50f),
            actual = brush.center,
        )
        assertEquals(
            expected = 30f,
            actual = brush.radius,
        )
    }

    @Test
    fun `given userSpaceOnUse radial gradient with percentage coordinates - when toBrush is called - then percentages resolve against viewport`() {
        // Arrange
        root.attributes["width"] = "200"
        root.attributes["height"] = "100"
        root.attributes["viewBox"] = "0 0 200 100"
        val gradient = createGradientWithStops(
            attributes = mutableMapOf(
                "id" to "grad11",
                "cx" to "50%",
                "cy" to "50%",
                "r" to "50%",
                "gradientUnits" to "userSpaceOnUse",
            ),
        )
        root.gradients["grad11"] = gradient

        // Act
        val brush = gradient.toBrush(target = emptyList())

        // Assert — cx: 50% of viewportWidth(200) = 100
        //          cy: 50% of viewportHeight(100) = 50
        //          r: 50% of max(200,100) = 100
        assertIs<ComposeBrush.Gradient.Radial>(brush)
        assertEquals(
            expected = ComposeOffset(x = 100f, y = 50f),
            actual = brush.center,
        )
        assertEquals(
            expected = 100f,
            actual = brush.radius,
        )
    }

    @Test
    fun `given radialGradient href referencing another radialGradient - when toBrush is called - then inherits stops and local geometry wins`() {
        // Arrange
        root.attributes["width"] = "100"
        root.attributes["height"] = "100"
        root.attributes["viewBox"] = "0 0 100 100"
        val baseGradient = createGradientWithStops(
            attributes = mutableMapOf("id" to "baseGrad"),
        )
        root.gradients["baseGrad"] = baseGradient
        val referencingGradient = SvgRadialGradientNode(
            parent = root,
            children = mutableSetOf(),
            attributes = mutableMapOf(
                "id" to "refGrad",
                "xlink:href" to "#baseGrad",
                "cx" to "50",
                "cy" to "50",
                "r" to "40",
                "gradientUnits" to "userSpaceOnUse",
            ),
        )
        root.gradients["refGrad"] = referencingGradient

        // Act
        val brush = referencingGradient.toBrush(target = emptyList())

        // Assert — local cx/cy/r override referenced, stops are inherited from baseGrad
        assertIs<ComposeBrush.Gradient.Radial>(brush)
        assertEquals(
            expected = ComposeOffset(x = 50f, y = 50f),
            actual = brush.center,
        )
        assertEquals(
            expected = 40f,
            actual = brush.radius,
        )
        assertEquals(expected = 3, actual = brush.colors.size)
        assertEquals(expected = listOf(0f, 0.5f, 1f), actual = brush.stops)
    }

    @Test
    fun `given radialGradient href referencing a linearGradient - when toBrush is called - then throws IllegalStateException`() {
        // Arrange
        root.attributes["width"] = "100"
        root.attributes["height"] = "100"
        root.attributes["viewBox"] = "0 0 100 100"
        val linearGradient = SvgLinearGradientNode(
            parent = root,
            children = mutableSetOf(),
            attributes = mutableMapOf("id" to "linGrad1"),
        )
        root.gradients["linGrad1"] = linearGradient
        val radialGradient = createGradientWithStops(
            attributes = mutableMapOf(
                "id" to "radGradRef",
                "xlink:href" to "#linGrad1",
            ),
        )

        // Act & Assert
        val exception = assertFailsWith<IllegalStateException> {
            radialGradient.toBrush(target = emptyList())
        }
        val message = exception.message
        assertNotNull(message)
        assertContains(message, "linGrad1")
        assertContains(message, "SvgLinearGradientNode")
    }
}
