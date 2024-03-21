package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.ImageVectorNode
import dev.tonholo.s2c.domain.PathCommand
import dev.tonholo.s2c.domain.builder.pathNode
import dev.tonholo.s2c.domain.compose.toBrush
import dev.tonholo.s2c.domain.xml.XmlNode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class SvgGroupNodeTests : BaseSvgTest() {
    @Test
    fun `ensure asNode function returns ImageVectorNode Group`() {
        // Arrange
        val svgGroupNode = SvgGroupNode(
            parent = root,
            children = mutableSetOf(
                SvgPathNode(
                    parent = root,
                    attributes = mutableMapOf(
                        "d" to "M 10,10 L20,20",
                        "fill" to "#FFFFFF",
                    ),
                ),
                SvgPathNode(
                    parent = root,
                    attributes = mutableMapOf(
                        "d" to "M 30,30 L40,40",
                        "fill" to "#000000",
                    ),
                )
            ),
            attributes = mutableMapOf(),
        )

        val minified = false
        val expectedNodes = listOf(
            ImageVectorNode.Path(
                params = ImageVectorNode.Path.Params(fill = "#FFFFFF".toBrush()),
                wrapper = ImageVectorNode.NodeWrapper(
                    normalizedPath = "M10 10  L20 20",
                    nodes = listOf(
                        pathNode(PathCommand.MoveTo) {
                            args(10, 10)
                        },
                        pathNode(PathCommand.LineTo) {
                            args(20, 20)
                        },
                    ),
                ),
                minified = minified,
            ),
            ImageVectorNode.Path(
                params = ImageVectorNode.Path.Params(fill = "#000000".toBrush()),
                wrapper = ImageVectorNode.NodeWrapper(
                    normalizedPath = "M30 30  L40 40",
                    nodes = listOf(
                        pathNode(PathCommand.MoveTo) {
                            args(30, 30)
                        },
                        pathNode(PathCommand.LineTo) {
                            args(40, 40)
                        },
                    ),
                ),
                minified = minified,
            ),
        )

        // Act
        val group = svgGroupNode.asNode(
            masks = emptyList(),
            minified = minified,
        )

        // Assert
        assertEquals(2, group.commands.size)
        assertNull(group.clipPath)
        val actualNodes = group.commands.filterIsInstance<ImageVectorNode.Path>()
        assertEquals(2, actualNodes.size)
        for (i in expectedNodes.indices) {
            val expected = expectedNodes[i]
            val actual = actualNodes[i]
            assertEquals(expected = expected.params.fill, actual = actual.params.fill)
            assertEquals(expected = expected.wrapper.normalizedPath, actual = actual.wrapper.normalizedPath)
            assertEquals(expected = expected.wrapper.nodes.size, actual = actual.wrapper.nodes.size)
            val expectedNodeParams = expected.wrapper.nodes.map { it.buildParameters() }
            val actualNodeParams = actual.wrapper.nodes.map { it.buildParameters() }
            assertEquals(expected = expectedNodeParams, actual = actualNodeParams)
        }
    }

    @Test
    fun `ensure asNode function returns ImageVectorNode Group with clipPath`() {
        val maskId = "id_of_mask"
        // Prepare test data
        val groupChildren = mutableSetOf<XmlNode>()
        val group = SvgGroupNode(
            parent = root,
            children = groupChildren,
            attributes = mutableMapOf(
                "mask" to "url(#$maskId)",
            ),
        )
        groupChildren += SvgPathNode(
            parent = group,
            attributes = mutableMapOf(
                "d" to "M10,10 L20,20",
                "fill" to "#FFFFFF",
            ),
        )
        groupChildren += SvgPathNode(
            parent = group,
            attributes = mutableMapOf(
                "d" to "M30,30 L40,40",
                "fill" to "#000000",
            ),
        )

        val maskChildren = mutableSetOf<XmlNode>()
        val mask = SvgMaskNode(
            parent = root,
            children = maskChildren,
            attributes = mutableMapOf("id" to maskId),
        )
        maskChildren += SvgPathNode(
            parent = mask,
            attributes = mutableMapOf(
                "d" to "M50,50 L40,40z"
            ),
        )

        val expectedClipPath = ImageVectorNode.NodeWrapper(
            normalizedPath = "M50 50  L40 40z",
            nodes = listOf(
                pathNode(PathCommand.MoveTo) {
                    args(50, 50)
                },
                pathNode(PathCommand.LineTo) {
                    args(40, 40)
                    close = true
                },
            )
        )

        val expectedNodes = listOf(
            ImageVectorNode.Path(
                params = ImageVectorNode.Path.Params(fill = "#FFFFFF".toBrush()),
                wrapper = ImageVectorNode.NodeWrapper(
                    normalizedPath = "M10 10  L20 20",
                    nodes = listOf(
                        pathNode(PathCommand.MoveTo) {
                            args(10, 10)
                        },
                        pathNode(PathCommand.LineTo) {
                            args(20, 20)
                        },
                    ),
                ),
                minified = false,
            ),
            ImageVectorNode.Path(
                params = ImageVectorNode.Path.Params(fill = "#000000".toBrush()),
                wrapper = ImageVectorNode.NodeWrapper(
                    normalizedPath = "M30 30  L40 40",
                    nodes = listOf(
                        pathNode(PathCommand.MoveTo) {
                            args(30, 30)
                        },
                        pathNode(PathCommand.LineTo) {
                            args(40, 40)
                        },
                    ),
                ),
                minified = false,
            ),
        )

        // Act
        val groupNode = group.asNode(masks = listOf(mask), minified = false)

        // Assert
        assertEquals(2, groupNode.commands.size)
        val actualClipPath = groupNode.clipPath
        assertNotNull(actualClipPath)
        assertEquals(expected = expectedClipPath.normalizedPath, actual = actualClipPath.normalizedPath)
        val expectedClipPathParams = expectedClipPath.nodes.map { it.buildParameters() }
        val actualClipPathParams = actualClipPath.nodes.map { it.buildParameters() }
        assertEquals(expected = expectedClipPathParams, actual = actualClipPathParams)

        val actualNodes = groupNode.commands.filterIsInstance<ImageVectorNode.Path>()
        assertEquals(2, actualNodes.size)
        for (i in expectedNodes.indices) {
            val expected = expectedNodes[i]
            val actual = actualNodes[i]
            assertEquals(expected = expected.params.fill, actual = actual.params.fill)
            assertEquals(expected = expected.wrapper.normalizedPath, actual = actual.wrapper.normalizedPath)
            assertEquals(expected = expected.wrapper.nodes.size, actual = actual.wrapper.nodes.size)
            val expectedNodeParams = expected.wrapper.nodes.map { it.buildParameters() }
            val actualNodeParams = actual.wrapper.nodes.map { it.buildParameters() }
            assertEquals(expected = expectedNodeParams, actual = actualNodeParams)
        }

    }
}
