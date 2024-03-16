package dev.tonholo.s2c.domain

import dev.tonholo.s2c.domain.builder.pathNode
import kotlin.math.abs

class StrokeDashArray(value: String) {
    val dashesAndGaps = value
        .split(" ")
        .map { it.toInt() }
        .toIntArray()

    override fun toString(): String = dashesAndGaps.joinToString(" ")
}

private enum class StrokeDashDrawDirection(
    val command: PathCommand,
    val edgeCommand: PathCommand,
) {
    RIGHT(command = PathCommand.HorizontalLineTo, edgeCommand = PathCommand.VerticalLineTo),
    DOWN(command = PathCommand.VerticalLineTo, edgeCommand = RIGHT.command),
    LEFT(command = PathCommand.HorizontalLineTo, edgeCommand = DOWN.command),
    UP(command = PathCommand.VerticalLineTo, edgeCommand = LEFT.command),
    ;

    fun next(): StrokeDashDrawDirection =
        entries[(this.ordinal + 1) % entries.size]
}

fun StrokeDashArray.createDashedPathForRect(
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    strokeWidth: Int,
    isMinified: Boolean,
): List<PathNodes> {
    val perimeter = 2f * (width + height)
    var drawLength = 0f
    var i = 0

    val edges = floatArrayOf(
        width.toFloat(),
        (width + height).toFloat(),
        ((2 * width) + height).toFloat(),
        perimeter,
    )

    var direction = StrokeDashDrawDirection.RIGHT
    return buildList {
        // Move draw cursor to the translation position.
        add(pathNode(PathCommand.MoveTo) { args(x, y) })

        while (drawLength < perimeter) {
            val dashOrGap = dashesAndGaps[i % dashesAndGaps.size].toFloat()
            val nextDrawLength = drawLength + dashOrGap
            val edge = edges[direction.ordinal]
            val atTheEdge = nextDrawLength >= edge
            val diff = nextDrawLength - edge
            if (atTheEdge) {
                direction = direction.next()
            }

            if (i % 2 == 0) {
                addDash(atTheEdge, diff, direction, dashOrGap, isMinified, strokeWidth)
            } else {
                if (nextDrawLength >= perimeter) break
                addGap(atTheEdge, dashOrGap, diff, direction, isMinified)
            }
            i++
            drawLength += dashOrGap
        }

        addCloseCommand()
    }
}

private fun MutableList<PathNodes>.addCloseCommand() {
    removeLast().let { node ->
        add(
            pathNode(node.command) {
                args(node.values)
                isRelative = node.isRelative
                minified = node.minified
                close = true
            },
        )
    }
}

private fun MutableList<PathNodes>.addDash(
    atTheEdge: Boolean,
    diff: Float,
    direction: StrokeDashDrawDirection,
    dashOrGap: Float,
    isMinified: Boolean,
    strokeWidth: Int,
) {
    if (atTheEdge) {
        addDashOnTheEdge(diff, direction, dashOrGap, isMinified, strokeWidth)
    } else {
        addDashOutOfEdge(direction, dashOrGap, isMinified)
    }
}

private fun MutableList<PathNodes>.addDashOnTheEdge(
    diff: Float,
    direction: StrokeDashDrawDirection,
    dashOrGap: Float,
    isMinified: Boolean,
    strokeWidth: Int,
) {
    if (diff > 0) {
        val partialDash = when (direction) {
            StrokeDashDrawDirection.DOWN -> dashOrGap - diff
            StrokeDashDrawDirection.LEFT -> dashOrGap - diff
            StrokeDashDrawDirection.UP -> -(dashOrGap - diff)
            else -> 0f
        }
        add(
            pathNode(command = direction.edgeCommand) {
                args(partialDash)
                isRelative = true
                minified = isMinified
            }
        )
        val rest = dashOrGap - abs(partialDash)

        val moveArg = when (direction) {
            StrokeDashDrawDirection.RIGHT -> null
            StrokeDashDrawDirection.DOWN -> arrayOf(0f, -strokeWidth / 2f)
            StrokeDashDrawDirection.LEFT -> arrayOf(strokeWidth / 2f, 0f)
            StrokeDashDrawDirection.UP -> arrayOf(0f, strokeWidth / 2f)
        }

        moveArg?.let {
            add(
                pathNode(command = PathCommand.MoveTo) {
                    args(it.toList())
                    isRelative = true
                    minified = isMinified
                }
            )
        }

        val drawArg = when (direction) {
            StrokeDashDrawDirection.DOWN -> rest + strokeWidth / 2f
            StrokeDashDrawDirection.LEFT -> -(rest + strokeWidth / 2f)
            StrokeDashDrawDirection.UP -> -(rest + strokeWidth / 2f)
            else -> "unknown"
        }
        add(
            pathNode(command = direction.command) {
                args(drawArg)
                isRelative = true
                minified = isMinified
            }
        )
    } else {
        val drawArg = when (direction) {
            StrokeDashDrawDirection.RIGHT -> -(dashOrGap + strokeWidth / 2f)
            StrokeDashDrawDirection.DOWN -> -dashOrGap
            StrokeDashDrawDirection.LEFT -> dashOrGap
            StrokeDashDrawDirection.UP -> -dashOrGap
        }
        add(
            pathNode(command = direction.edgeCommand) {
                args(drawArg)
                isRelative = true
                minified = isMinified
            }
        )
    }
}

private fun MutableList<PathNodes>.addDashOutOfEdge(
    direction: StrokeDashDrawDirection,
    dashOrGap: Float,
    isMinified: Boolean,
) {
    val drawArg = when (direction) {
        StrokeDashDrawDirection.RIGHT -> dashOrGap
        StrokeDashDrawDirection.DOWN -> dashOrGap
        StrokeDashDrawDirection.LEFT -> -dashOrGap
        StrokeDashDrawDirection.UP -> -dashOrGap
    }
    add(
        pathNode(command = direction.command) {
            args(drawArg)
            isRelative = true
            minified = isMinified
        },
    )
}

private fun MutableList<PathNodes>.addGap(
    atTheEdge: Boolean,
    dashOrGap: Float,
    diff: Float,
    direction: StrokeDashDrawDirection,
    isMinified: Boolean,
) {
    val moveArgs = if (atTheEdge) {
        val partialGap = dashOrGap - diff
        val rest = dashOrGap - partialGap
        when (direction) {
            StrokeDashDrawDirection.DOWN -> arrayOf(partialGap, rest)
            StrokeDashDrawDirection.LEFT -> arrayOf(-diff, partialGap)
            StrokeDashDrawDirection.UP -> arrayOf(-partialGap, -diff)
            else -> arrayOf()
        }
    } else {
        when (direction) {
            StrokeDashDrawDirection.RIGHT -> arrayOf(dashOrGap, 0f)
            StrokeDashDrawDirection.DOWN -> arrayOf(0f, dashOrGap)
            StrokeDashDrawDirection.LEFT -> arrayOf(-dashOrGap, 0f)
            StrokeDashDrawDirection.UP -> arrayOf(0f, -dashOrGap)
        }
    }
    add(
        pathNode(PathCommand.MoveTo) {
            args(moveArgs.toList())
            isRelative = true
            minified = isMinified
        },
    )
}
