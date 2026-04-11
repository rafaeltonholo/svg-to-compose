package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.domain.compose.ComposeBrush
import dev.tonholo.s2c.domain.compose.ComposeOffset
import kotlin.math.sqrt
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

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
    fun `given radialGradient href referencing a linearGradient with stops - when toBrush is called - then produces radial brush with linear's stops`() {
        // Arrange - per SVG spec, cross-type href inherits stops from the referenced gradient
        root.attributes["width"] = "100"
        root.attributes["height"] = "100"
        root.attributes["viewBox"] = "0 0 100 100"
        val linearStop1 = SvgGradientStopNode(
            parent = root,
            attributes = mutableMapOf("offset" to "0", "stop-color" to "#AABBCC"),
        )
        val linearStop2 = SvgGradientStopNode(
            parent = root,
            attributes = mutableMapOf("offset" to "1", "stop-color" to "#DDEEFF"),
        )
        val linearGradient = SvgLinearGradientNode(
            parent = root,
            children = mutableSetOf(linearStop1, linearStop2),
            attributes = mutableMapOf("id" to "linGrad1"),
        )
        root.gradients["linGrad1"] = linearGradient
        val radialGradient = SvgRadialGradientNode(
            parent = root,
            children = mutableSetOf(),
            attributes = mutableMapOf(
                "id" to "radGradRef",
                "xlink:href" to "#linGrad1",
                "cx" to "50",
                "cy" to "50",
                "r" to "40",
                "gradientUnits" to "userSpaceOnUse",
            ),
        )
        root.gradients["radGradRef"] = radialGradient

        // Act
        val brush = radialGradient.toBrush(target = emptyList())

        // Assert - must be a RADIAL brush (element type), not linear
        assertIs<ComposeBrush.Gradient.Radial>(brush)
        assertEquals(
            expected = ComposeOffset(x = 50f, y = 50f),
            actual = brush.center,
        )
        assertEquals(expected = 40f, actual = brush.radius)
        assertEquals(expected = 2, actual = brush.colors.size)
        assertEquals(expected = listOf(0f, 1f), actual = brush.stops)
    }

    @Test
    fun `given radialGradient with local stops and href - when toBrush is called - then uses local stops over referenced stops`() {
        // Arrange - per SVG spec, if the referencing gradient has its own stops, they override
        root.attributes["width"] = "100"
        root.attributes["height"] = "100"
        root.attributes["viewBox"] = "0 0 100 100"
        val baseGradient = createGradientWithStops(
            attributes = mutableMapOf("id" to "baseStops"),
        )
        root.gradients["baseStops"] = baseGradient

        // Referencing gradient has its own 2 stops (different from baseGradient's 3)
        val localStop1 = SvgGradientStopNode(
            parent = root,
            attributes = mutableMapOf("offset" to "0", "stop-color" to "#111111"),
        )
        val localStop2 = SvgGradientStopNode(
            parent = root,
            attributes = mutableMapOf("offset" to "1", "stop-color" to "#222222"),
        )
        val referencingGradient = SvgRadialGradientNode(
            parent = root,
            children = mutableSetOf(localStop1, localStop2),
            attributes = mutableMapOf(
                "id" to "localStopsGrad",
                "xlink:href" to "#baseStops",
                "cx" to "50",
                "cy" to "50",
                "r" to "40",
                "gradientUnits" to "userSpaceOnUse",
            ),
        )
        root.gradients["localStopsGrad"] = referencingGradient

        // Act
        val brush = referencingGradient.toBrush(target = emptyList())

        // Assert - local 2 stops should be used, not referenced 3 stops
        assertIs<ComposeBrush.Gradient.Radial>(brush)
        assertEquals(expected = 2, actual = brush.colors.size)
        assertEquals(expected = listOf(0f, 1f), actual = brush.stops)
    }

    @Test
    fun `given chained radialGradient href A to B to C - when toBrush is called - then resolves deepest stops and uses outermost attributes`() {
        // Arrange - A -> B -> C chain; C has stops, A has geometry overrides
        root.attributes["width"] = "500"
        root.attributes["height"] = "400"
        root.attributes["viewBox"] = "0 0 500 400"
        val gradientC = createGradientWithStops(
            attributes = mutableMapOf("id" to "gradC"),
        )
        root.gradients["gradC"] = gradientC

        val gradientB = SvgRadialGradientNode(
            parent = root,
            children = mutableSetOf(),
            attributes = mutableMapOf(
                "id" to "gradB",
                "xlink:href" to "#gradC",
                "spreadMethod" to "reflect",
                "gradientUnits" to "userSpaceOnUse",
                "cx" to "225",
                "cy" to "75",
                "r" to "55",
            ),
        )
        root.gradients["gradB"] = gradientB

        val gradientA = SvgRadialGradientNode(
            parent = root,
            children = mutableSetOf(),
            attributes = mutableMapOf(
                "id" to "gradA",
                "xlink:href" to "#gradB",
                "cx" to "225",
                "cy" to "75",
                "r" to "40",
            ),
        )
        root.gradients["gradA"] = gradientA

        // Act
        val brush = gradientA.toBrush(target = emptyList())

        // Assert - A's r=40 overrides B's r=55, B's spreadMethod/gradientUnits inherited,
        // C's stops inherited through the chain
        assertIs<ComposeBrush.Gradient.Radial>(brush)
        assertEquals(expected = 3, actual = brush.colors.size)
        assertEquals(expected = listOf(0f, 0.5f, 1f), actual = brush.stops)
        assertEquals(expected = 40f, actual = brush.radius)
    }

    @Test
    fun `given radialGradient href with conflicting attributes - when toBrush is called - then local attributes override referenced`() {
        // Arrange - referenced has cx=30, r=20; referencing has cx=70, r=50. Local should win.
        root.attributes["width"] = "200"
        root.attributes["height"] = "200"
        root.attributes["viewBox"] = "0 0 200 200"
        val baseGradient = createGradientWithStops(
            attributes = mutableMapOf(
                "id" to "conflictBase",
                "cx" to "30",
                "cy" to "30",
                "r" to "20",
                "gradientUnits" to "userSpaceOnUse",
            ),
        )
        root.gradients["conflictBase"] = baseGradient

        val referencingGradient = SvgRadialGradientNode(
            parent = root,
            children = mutableSetOf(),
            attributes = mutableMapOf(
                "id" to "conflictRef",
                "xlink:href" to "#conflictBase",
                "cx" to "70",
                "cy" to "70",
                "r" to "50",
            ),
        )
        root.gradients["conflictRef"] = referencingGradient

        // Act
        val brush = referencingGradient.toBrush(target = emptyList())

        // Assert - local cx=70, cy=70, r=50 must win over referenced cx=30, cy=30, r=20
        assertIs<ComposeBrush.Gradient.Radial>(brush)
        assertEquals(
            expected = ComposeOffset(x = 70f, y = 70f),
            actual = brush.center,
        )
        assertEquals(expected = 50f, actual = brush.radius)
        assertEquals(expected = 3, actual = brush.colors.size)
    }

    @Test
    fun `given objectBoundingBox radial gradient with fractional values - when toBrush is called - then fractions scale by bounding box dimensions`() {
        // Arrange - Case 6 from the test SVG: derivedBBox with objectBoundingBox
        // Rect at x=315, y=165, width=120, height=120
        root.attributes["width"] = "500"
        root.attributes["height"] = "400"
        root.attributes["viewBox"] = "0 0 500 400"
        val gradient = createGradientWithStops(
            attributes = mutableMapOf(
                "id" to "bboxGrad",
                "gradientUnits" to "objectBoundingBox",
                "cx" to "0.4",
                "cy" to "0.4",
                "r" to "0.6",
            ),
        )
        root.gradients["bboxGrad"] = gradient
        val target = createRectTarget(x = 315.0, y = 165.0, width = 120.0, height = 120.0)

        // Act
        val brush = gradient.toBrush(target = target)

        // Assert
        // cx = 0.4 * 120 + 315 = 363
        // cy = 0.4 * 120 + 165 = 213
        // r = 0.6 * 120 = 72
        assertIs<ComposeBrush.Gradient.Radial>(brush)
        assertEquals(
            expected = ComposeOffset(x = 363f, y = 213f),
            actual = brush.center,
        )
        assertEquals(expected = 72f, actual = brush.radius)
    }

    @Test
    fun `given userSpaceOnUse radial gradient with fx fy different from cx cy - when toBrush is called - then center uses cx cy and fx fy is dropped`() {
        // Arrange - Compose has no focal point parameter, so we use cx/cy as
        // the gradient center (matching Android Studio behavior). The focal
        // point offset is dropped as an accepted limitation.
        root.attributes["width"] = "500"
        root.attributes["height"] = "400"
        root.attributes["viewBox"] = "0 0 500 400"
        val gradient = createGradientWithStops(
            attributes = mutableMapOf(
                "id" to "focalGrad",
                "cx" to "150",
                "cy" to "350",
                "r" to "45",
                "fx" to "135",
                "fy" to "340",
                "gradientUnits" to "userSpaceOnUse",
            ),
        )
        root.gradients["focalGrad"] = gradient

        // Act
        val brush = gradient.toBrush(target = emptyList())

        // Assert - center uses cx/cy, not fx/fy
        assertIs<ComposeBrush.Gradient.Radial>(brush)
        assertEquals(
            expected = ComposeOffset(x = 150f, y = 350f),
            actual = brush.center,
        )
        assertEquals(expected = 45f, actual = brush.radius)
        // Stops are unchanged
        assertEquals(expected = 3, actual = brush.colors.size)
        assertEquals(expected = listOf(0f, 0.5f, 1f), actual = brush.stops)
    }

    // region SVG2 plain "href" tests (mirror of xlink:href tests above)

    @Test
    fun `given radialGradient with plain href referencing another radialGradient - when toBrush is called - then inherits stops and local geometry wins`() {
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
                "href" to "#baseGrad",
                "cx" to "50",
                "cy" to "50",
                "r" to "40",
                "gradientUnits" to "userSpaceOnUse",
            ),
        )
        root.gradients["refGrad"] = referencingGradient

        // Act
        val brush = referencingGradient.toBrush(target = emptyList())

        // Assert
        assertIs<ComposeBrush.Gradient.Radial>(brush)
        assertEquals(
            expected = ComposeOffset(x = 50f, y = 50f),
            actual = brush.center,
        )
        assertEquals(expected = 40f, actual = brush.radius)
        assertEquals(expected = 3, actual = brush.colors.size)
        assertEquals(expected = listOf(0f, 0.5f, 1f), actual = brush.stops)
    }

    @Test
    fun `given radialGradient with plain href referencing a linearGradient - when toBrush is called - then produces radial brush with linear's stops`() {
        // Arrange
        root.attributes["width"] = "100"
        root.attributes["height"] = "100"
        root.attributes["viewBox"] = "0 0 100 100"
        val linearStop1 = SvgGradientStopNode(
            parent = root,
            attributes = mutableMapOf("offset" to "0", "stop-color" to "#AABBCC"),
        )
        val linearStop2 = SvgGradientStopNode(
            parent = root,
            attributes = mutableMapOf("offset" to "1", "stop-color" to "#DDEEFF"),
        )
        val linearGradient = SvgLinearGradientNode(
            parent = root,
            children = mutableSetOf(linearStop1, linearStop2),
            attributes = mutableMapOf("id" to "linGrad1"),
        )
        root.gradients["linGrad1"] = linearGradient
        val radialGradient = SvgRadialGradientNode(
            parent = root,
            children = mutableSetOf(),
            attributes = mutableMapOf(
                "id" to "radGradRef",
                "href" to "#linGrad1",
                "cx" to "50",
                "cy" to "50",
                "r" to "40",
                "gradientUnits" to "userSpaceOnUse",
            ),
        )
        root.gradients["radGradRef"] = radialGradient

        // Act
        val brush = radialGradient.toBrush(target = emptyList())

        // Assert
        assertIs<ComposeBrush.Gradient.Radial>(brush)
        assertEquals(
            expected = ComposeOffset(x = 50f, y = 50f),
            actual = brush.center,
        )
        assertEquals(expected = 40f, actual = brush.radius)
        assertEquals(expected = 2, actual = brush.colors.size)
        assertEquals(expected = listOf(0f, 1f), actual = brush.stops)
    }

    @Test
    fun `given radialGradient with local stops and plain href - when toBrush is called - then uses local stops over referenced stops`() {
        // Arrange
        root.attributes["width"] = "100"
        root.attributes["height"] = "100"
        root.attributes["viewBox"] = "0 0 100 100"
        val baseGradient = createGradientWithStops(
            attributes = mutableMapOf("id" to "baseStops"),
        )
        root.gradients["baseStops"] = baseGradient

        val localStop1 = SvgGradientStopNode(
            parent = root,
            attributes = mutableMapOf("offset" to "0", "stop-color" to "#111111"),
        )
        val localStop2 = SvgGradientStopNode(
            parent = root,
            attributes = mutableMapOf("offset" to "1", "stop-color" to "#222222"),
        )
        val referencingGradient = SvgRadialGradientNode(
            parent = root,
            children = mutableSetOf(localStop1, localStop2),
            attributes = mutableMapOf(
                "id" to "localStopsGrad",
                "href" to "#baseStops",
                "cx" to "50",
                "cy" to "50",
                "r" to "40",
                "gradientUnits" to "userSpaceOnUse",
            ),
        )
        root.gradients["localStopsGrad"] = referencingGradient

        // Act
        val brush = referencingGradient.toBrush(target = emptyList())

        // Assert
        assertIs<ComposeBrush.Gradient.Radial>(brush)
        assertEquals(expected = 2, actual = brush.colors.size)
        assertEquals(expected = listOf(0f, 1f), actual = brush.stops)
    }

    // endregion

    /**
     * Creates a list of absolute path nodes forming a rectangle,
     * used as target for objectBoundingBox gradient calculations.
     */
    private fun createRectTarget(x: Double, y: Double, width: Double, height: Double): List<PathNodes> = listOf(
        PathNodes.MoveTo(values = listOf("$x", "$y"), isRelative = false, minified = false),
        PathNodes.LineTo(values = listOf("${x + width}", "$y"), isRelative = false, minified = false),
        PathNodes.LineTo(values = listOf("${x + width}", "${y + height}"), isRelative = false, minified = false),
        PathNodes.LineTo(values = listOf("$x", "${y + height}"), isRelative = false, minified = false),
    )
}
