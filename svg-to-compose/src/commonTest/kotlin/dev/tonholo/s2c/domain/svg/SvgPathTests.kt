package dev.tonholo.s2c.domain.svg

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class SvgPathNodeTests : BaseSvgTest() {
    @Test
    fun `ensure SvgPathNode initializes correctly`() {
        val attributes = mutableMapOf("d" to "M10 10 L20 20")
        val svgPathNode = SvgPathNode(root, attributes)

        assertEquals("path", svgPathNode.tagName)
        assertEquals("M10 10 L20 20", svgPathNode.d)
        assertNull(svgPathNode.clipPath)
    }

    @Test
    fun `ensure SvgPathNode correctly sets attributes`() {
        val attributes = mutableMapOf(
            "d" to "M10 10 L20 20",
            "clip-path" to "url(#clip)"
        )
        val svgPathNode = SvgPathNode(root, attributes)

        assertEquals("url(#clip)", svgPathNode.clipPath)
    }

    @Test
    fun `ensure SvgPathNode handles missing attributes`() {
        val attributes = mutableMapOf("d" to "M10 10 L20 20")
        val svgPathNode = SvgPathNode(root, attributes)

        assertNull(svgPathNode.clipPath)
    }

    @Test
    fun `ensure SvgPathNode graphicNodeParams generates correct parameters`() {
        val attributes = mutableMapOf(
            "d" to "M10 10 L20 20",
            "fill" to "#FFFFFF",
            "stroke" to "#000000",
            "stroke-width" to "2",
            "stroke-linejoin" to "round",
            "stroke-opacity" to "0.5",
            "stroke-miterlimit" to "10",
            "stroke-dasharray" to "5,10",
        )
        val svgPathNode = SvgPathNode(root, attributes)

        val expectedParams = "fill=\"#FFFFFF\" stroke=\"#000000\" stroke-width=\"2\" stroke-line-join=\"round\" " +
            "stroke-opacity=\"0.5\" stroke-miter-limit=\"10\" stroke-dasharray=\"5,10\" "
        assertEquals(expectedParams, svgPathNode.graphicNodeParams())
    }

    @Test
    fun `ensure SvgPathNode handle empty attributes`() {
        val attributes = mutableMapOf(
            "d" to "M 0,0 L1,2z"
        )
        val svgPathNode = SvgPathNode(root, attributes)

        assertNull(svgPathNode.fill)
        assertNull(svgPathNode.opacity)
        assertNull(svgPathNode.fillOpacity)
        assertNull(svgPathNode.style)
        assertNull(svgPathNode.stroke)
        assertNull(svgPathNode.strokeWidth)
        assertNull(svgPathNode.strokeLineJoin)
        assertNull(svgPathNode.strokeLineCap)
        assertNull(svgPathNode.fillRule)
        assertNull(svgPathNode.strokeOpacity)
        assertNull(svgPathNode.strokeMiterLimit)
        assertNull(svgPathNode.strokeDashArray)
    }

    @Test
    fun `ensure SvgPathNode throws IllegalStateException for missing mandatory attribute`() {
        val attributes = mutableMapOf<String, String>() // No 'd' attribute provided
        val path = SvgPathNode(root, attributes)
        val exception = assertFailsWith<IllegalStateException> {
            path.d
        }

        assertEquals(
            expected = "Required attribute 'd' on tag 'path' was not found",
            actual = exception.message,
        )
    }

    @Test
    fun `ensure SvgPathNode attribute returns null for invalid attribute type`() {
        val attributes = mutableMapOf(
            "d" to "M10 10 L20 20",
            "opacity" to "not valid float",
            "stroke-width" to "not valid float",
            "stroke-linecap" to "not valid linecap",
        )
        val path = SvgPathNode(root, attributes)

        assertNull(path.opacity)
        assertNull(path.strokeWidth)
        assertNull(path.strokeLineCap)
    }

    @Test
    fun `ensure SvgPathNode sets stroke width with root viewport dimensions`() {
        root.attributes["width"] = "100"
        root.attributes["height"] = "50"
        val attributes = mutableMapOf(
            "d" to "M10 10 L20 20",
            "stroke-width" to "50%",
        )
        val svgPathNode = SvgPathNode(root, attributes)

        assertEquals(expected = 50f, actual = svgPathNode.strokeWidth) // 50% of viewport width
    }

}
