package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.domain.xml.XmlNode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SvgClipPathTests : BaseSvgTest() {

    @Test
    fun `ensure SvgClipPath initializes correctly`() {
        val children = mutableSetOf<XmlNode>()
        val attributes = mutableMapOf<String, String>()
        val clipPath = SvgClipPath(root, children, attributes)

        assertEquals("clipPath", clipPath.tagName)
        assertEquals(root, clipPath.parent)
        assertEquals(children, clipPath.children)
        assertEquals(attributes, clipPath.attributes)
    }

    @Test
    fun `ensure SvgClipPath asNodeWrapper handles empty clip path`() {
        val children = mutableSetOf<XmlNode>()
        val attributes = mutableMapOf<String, String>()
        val clipPath = SvgClipPath(root, children, attributes)

        val nodeWrapper = clipPath.asNodeWrapper(minified = false)

        assertTrue(nodeWrapper.nodes.isEmpty())
        assertEquals("", nodeWrapper.normalizedPath)
    }

    @Test
    fun `ensure SvgClipPath asNodeWrapper generates correct NodeWrapper with children`() {
        // Arrange
        val attributes = mutableMapOf<String, String>()
        val children = mutableSetOf<XmlNode>(
            SvgPathNode(
                parent = root,
                attributes = mutableMapOf(
                    "d" to "M10,10 L20,20",
                )
            ),
            SvgPathNode(
                parent = root,
                attributes = mutableMapOf(
                    "d" to "M30,30 L40,40",
                )
            ),
            SvgCircleNode(
                parent = root,
                attributes = mutableMapOf(
                    "r" to "50",
                )
            ),
        )
        val clipPath = SvgClipPath(root, children, attributes)
        val minified = false

        // Act
        val nodeWrapper = clipPath.asNodeWrapper(minified = minified)
        val expectedWrapper = ImageVectorNode.NodeWrapper(
            normalizedPath = "M 10 10 L 20 20 M 30 30 L 40 40 M 0 0 m -50 0 a 50 50 0 1 1 100 0 a 50 50 0 1 1 -100 0z",
            nodes = listOf(
                PathNodes.MoveTo(
                    values = listOf("10", "10"),
                    isRelative = false,
                    minified = minified,
                ),
                PathNodes.LineTo(
                    values = listOf("20", "20"),
                    isRelative = false,
                    minified = minified,
                ),
                PathNodes.MoveTo(
                    values = listOf("30", "30"),
                    isRelative = false,
                    minified = minified,
                ),
                PathNodes.LineTo(
                    values = listOf("40", "40"),
                    isRelative = false,
                    minified = minified,
                ),
                PathNodes.MoveTo(
                    values = listOf("0", "0"),
                    isRelative = false,
                    minified = minified,
                ),
                PathNodes.MoveTo(
                    values = listOf("-50", "0"),
                    isRelative = true,
                    minified = minified,
                ),
                PathNodes.ArcTo(
                    values = listOf("50", "50", "0", "1", "1", "100", "0"),
                    isRelative = true,
                    minified = minified,
                ),
                PathNodes.ArcTo(
                    values = listOf("50", "50", "0", "1", "1", "-100", "0z"),
                    isRelative = true,
                    minified = minified,
                ),
            )
        )

        // Assert
        assertEquals(expected = expectedWrapper.normalizedPath, actual = nodeWrapper.normalizedPath)
        val expectedNodes = expectedWrapper.nodes
        val actualNodes = nodeWrapper.nodes
        assertEquals(expected = expectedNodes.size, actual = actualNodes.size)
        for (i in expectedNodes.indices) {
            val expected = expectedNodes[i]
            val actual = actualNodes[i]
            assertEquals(expected = expected.command, actual = actual.command)
            assertEquals(expected = expected.buildParameters(), actual = actual.buildParameters())
        }
    }
}
