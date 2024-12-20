package dev.tonholo.s2c.domain.svg

import app.cash.burst.Burst
import app.cash.burst.burstValues
import dev.tonholo.s2c.domain.xml.XmlRootNode
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class SvgViewBoxTest {
    @Test
    @Burst
    fun `should create view box with spaces as separators`(
        params: Pair<String, FloatArray> = burstValues(
            "0 0 120 120" to floatArrayOf(0f, 0f, 120f, 120f),
            "10 0 3000 120" to floatArrayOf(10f, 0f, 3000f, 120f),
            "0 0 0 1200" to floatArrayOf(0f, 0f, 0f, 1200f),
            "-10 -150 500 20" to floatArrayOf(-10f, -150f, 500f, 20f),
        ),
    ) {
        val (viewBox, expected) = params
        println("viewBox = $viewBox, expected = $expected")
        val svgNode = SvgRootNode(
            parent = XmlRootNode(children = mutableSetOf()),
            children = mutableSetOf(),
            attributes = mutableMapOf(
                "viewBox" to viewBox,
            ),
        )
        val actual = svgNode.viewBox
        assertContentEquals(expected, actual)
    }

    @Test
    @Burst
    fun `should create view box with comma as separators`(
        params: Pair<String, FloatArray> = burstValues(
            "0,0,120,120" to floatArrayOf(0f, 0f, 120f, 120f),
            "10,0,3000,120" to floatArrayOf(10f, 0f, 3000f, 120f),
            "0,0,0,1200" to floatArrayOf(0f, 0f, 0f, 1200f),
            "10,150,500,20" to floatArrayOf(10f, 150f, 500f, 20f),
            "-10,-150,500,20" to floatArrayOf(-10f, -150f, 500f, 20f),
        ),
    ) {
        val (viewBox, expected) = params
        println("viewBox = $viewBox, expected = $expected")
        val svgNode = SvgRootNode(
            parent = XmlRootNode(children = mutableSetOf()),
            children = mutableSetOf(),
            attributes = mutableMapOf(
                "viewBox" to viewBox,
            ),
        )
        val actual = svgNode.viewBox
        assertContentEquals(expected, actual)
    }

    @Test
    @Burst
    fun `should create view box with comma and spaces as separators`(
        params: Pair<String, FloatArray> = burstValues(
            "0, 0, 120, 120" to floatArrayOf(0f, 0f, 120f, 120f),
            "10, 0, 3000, 120" to floatArrayOf(10f, 0f, 3000f, 120f),
            "0, 0, 0, 1200" to floatArrayOf(0f, 0f, 0f, 1200f),
            "10, 150, 500, 20" to floatArrayOf(10f, 150f, 500f, 20f),
            "-10, -150, 500, 20" to floatArrayOf(-10f, -150f, 500f, 20f),
        ),
    ) {
        val (viewBox, expected) = params
        println("viewBox = $viewBox, expected = $expected")
        val svgNode = SvgRootNode(
            parent = XmlRootNode(children = mutableSetOf()),
            children = mutableSetOf(),
            attributes = mutableMapOf(
                "viewBox" to viewBox,
            ),
        )
        val actual = svgNode.viewBox
        assertContentEquals(expected, actual)
    }

    @Test
    @Burst
    fun `should create viewBox based on width and height`(
        params: Pair<Pair<String, String>, FloatArray> = burstValues(
            "120" to "120" to floatArrayOf(0f, 0f, 120f, 120f),
            "3000" to "120" to floatArrayOf(0f, 0f, 3000f, 120f),
            "-123" to "987.5" to floatArrayOf(0f, 0f, -123f, 987.5f),
        )
    ) {
        val (dimensions, expected) = params
        val (width, height) = dimensions
        val svgNode = SvgRootNode(
            parent = XmlRootNode(children = mutableSetOf()),
            children = mutableSetOf(),
            attributes = mutableMapOf(
                "width" to width,
                "height" to height,
            ),
        )
        val actual = svgNode.viewBox
        assertContentEquals(expected, actual)
    }

    @Test
    fun `when missing viewBox must convert to floatArrayOf of '0 0 300 150'`() {
        val svgNode = SvgRootNode(
            parent = XmlRootNode(children = mutableSetOf()),
            children = mutableSetOf(),
            attributes = mutableMapOf(),
        )
        val expected = floatArrayOf(0f, 0f, 300f, 150f)
        val actual = svgNode.viewBox
        assertContentEquals(expected, actual)
    }

    @Test
    fun `when incomplete viewBox must convert to floatArrayOf of '0 0 300 150'`() {
        val svgNode = SvgRootNode(
            parent = XmlRootNode(children = mutableSetOf()),
            children = mutableSetOf(),
            attributes = mutableMapOf(),
        )
        val expected = floatArrayOf(0f, 0f, 300f, 150f)
        val actual = svgNode.viewBox
        assertContentEquals(expected, actual)
    }

    @Test
    fun `ensure view box memoization works`() {
        val svgNode = SvgRootNode(
            parent = XmlRootNode(children = mutableSetOf()),
            children = mutableSetOf(),
            attributes = mutableMapOf(
                "viewBox" to "100, 150, 123, 654",
            ),
        )
        val expected = svgNode.viewBox

        // We use equals in this situation since we want to check if
        // the viewBox instance is the same.
        assertEquals(expected, svgNode.viewBox)
    }
}
