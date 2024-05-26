package dev.tonholo.s2c.geom.path

import dev.tonholo.s2c.domain.PathCommand
import dev.tonholo.s2c.domain.PathNodes
import dev.tonholo.s2c.domain.builder.pathNode

/**
 * Removes shorthand nodes from a list of PathNodes.
 *
 * @receiver The list of PathNodes to process.
 * @return A new list of PathNodes with shorthand nodes removed.
 * @throws IllegalArgumentException If any of the nodes are not absolute.
 */
fun List<PathNodes>.removeShorthandNodes(): List<PathNodes> {
    check(all { it.isRelative.not() }) {
        "All nodes must be absolute"
    }

    val nodes = this
    val current = DoubleArray(2)
    return buildList {
        var previousNode: PathNodes? = null
        for (node in nodes) {
            add(
                when (node) {
                    is PathNodes.ReflectiveCurveTo -> {
                        unshortReflectiveCurveTo(node, current, previousNode).also {
                            previousNode = node
                        }
                    }

                    is PathNodes.ReflectiveQuadTo -> {
                        unshortReflectiveQuadTo(node, current, previousNode).also {
                            previousNode = node
                        }
                    }

                    else -> {
                        previousNode = node
                        node
                    }
                },
            )

            computeCurrentPosition(node, current)
        }
    }
}

private fun unshortReflectiveCurveTo(
    node: PathNodes.ReflectiveCurveTo,
    current: DoubleArray,
    previousNode: PathNodes?,
): PathNodes {
    val previousControlPoint = calculatePreviousControlPoint(
        x = node.x2.toDouble(),
        y = node.y2.toDouble(),
        currentX = current[0],
        currentY = current[1],
    ) { previousNode is PathNodes.CurveTo }

    return pathNode(command = PathCommand.CurveTo) {
        args(
            current[0] - previousControlPoint[0],
            current[1] - previousControlPoint[1],
            node.x1,
            node.y1,
            node.x2,
            node.y2,
        )
        minified = node.minified
        close = node.shouldClose
    }
}

private fun unshortReflectiveQuadTo(
    node: PathNodes.ReflectiveQuadTo,
    current: DoubleArray,
    previousNode: PathNodes?,
): PathNodes {
    val previousControlPoint = calculatePreviousControlPoint(
        x = node.x1.toDouble(),
        y = node.y1.toDouble(),
        currentX = current[0],
        currentY = current[1],
    ) { previousNode is PathNodes.QuadTo }

    return pathNode(command = PathCommand.QuadTo) {
        args(
            current[0] - previousControlPoint[0],
            current[1] - previousControlPoint[1],
            node.x1,
            node.y1,
        )
        minified = node.minified
        close = node.shouldClose
    }
}

private fun computeCurrentPosition(node: PathNodes, current: DoubleArray) {
    when (node) {
        is PathNodes.CurveTo -> {
            current[0] = node.x3.toDouble()
            current[1] = node.y3.toDouble()
        }

        is PathNodes.HorizontalLineTo -> {
            current[0] = node.x.toDouble()
        }

        is PathNodes.LineTo, is PathNodes.MoveTo, is PathNodes.ArcTo -> {
            current[0] = node.x.toDouble()
            current[1] = node.y.toDouble()
        }

        is PathNodes.QuadTo, is PathNodes.ReflectiveCurveTo -> {
            current[0] = node.x2.toDouble()
            current[1] = node.y2.toDouble()
        }

        is PathNodes.ReflectiveQuadTo -> {
            current[0] = node.x1.toDouble()
            current[1] = node.y1.toDouble()
        }

        is PathNodes.VerticalLineTo -> {
            current[1] = node.y.toDouble()
        }
    }
}

private fun calculatePreviousControlPoint(
    x: Double,
    y: Double,
    currentX: Double,
    currentY: Double,
    predicate: () -> Boolean,
): DoubleArray = if (predicate()) {
    doubleArrayOf(x - currentX, y - currentY)
} else {
    DoubleArray(size = 2)
}
