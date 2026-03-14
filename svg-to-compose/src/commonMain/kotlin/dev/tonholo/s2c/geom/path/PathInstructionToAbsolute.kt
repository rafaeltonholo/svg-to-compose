package dev.tonholo.s2c.geom.path

import dev.tonholo.s2c.domain.PathNodes

/**
 * Converts all relative nodes in the list to absolute nodes.
 *
 * @return a new list with absolute nodes
 */
fun List<PathNodes>.toAbsolute(): List<PathNodes> {
    val current = DoubleArray(2)
    val nodes = this
    return buildList {
        for (node in nodes) {
            add(
                when (node) {
                    is PathNodes.LineTo -> node.toAbsolute(current)
                    is PathNodes.MoveTo -> node.toAbsolute(current)
                    is PathNodes.HorizontalLineTo -> node.toAbsolute(current)
                    is PathNodes.VerticalLineTo -> node.toAbsolute(current)
                    is PathNodes.CurveTo -> node.toAbsolute(current)
                    is PathNodes.ReflectiveCurveTo -> node.toAbsolute(current)
                    is PathNodes.QuadTo -> node.toAbsolute(current)
                    is PathNodes.ReflectiveQuadTo -> node.toAbsolute(current)
                    is PathNodes.ArcTo -> node.toAbsolute(current)
                },
            )
        }
    }
}

private fun PathNodes.LineTo.toAbsolute(current: DoubleArray): PathNodes.LineTo = if (isRelative.not()) {
    current[0] = x
    current[1] = y
    this
} else {
    current[0] += x
    current[1] += y
    copy(
        x = current[0],
        y = current[1],
        isRelative = false,
    )
}

private fun PathNodes.MoveTo.toAbsolute(current: DoubleArray): PathNodes.MoveTo = if (isRelative.not()) {
    current[0] = x
    current[1] = y
    this
} else {
    current[0] += x
    current[1] += y
    copy(
        x = current[0],
        y = current[1],
        isRelative = false,
    )
}

private fun PathNodes.HorizontalLineTo.toAbsolute(current: DoubleArray): PathNodes.HorizontalLineTo =
    if (isRelative.not()) {
        current[0] = x
        this
    } else {
        current[0] += x
        copy(
            x = current[0],
            isRelative = false,
        )
    }

private fun PathNodes.VerticalLineTo.toAbsolute(current: DoubleArray): PathNodes.VerticalLineTo =
    if (isRelative.not()) {
        current[1] = y
        this
    } else {
        current[1] += y
        copy(
            y = current[1],
            isRelative = false,
        )
    }

fun PathNodes.CurveTo.toAbsolute(current: DoubleArray): PathNodes.CurveTo = if (isRelative.not()) {
    current[0] = x3
    current[1] = y3
    this
} else {
    val currentX = current[0]
    val currentY = current[1]
    copy(
        x1 = currentX + x1,
        y1 = currentY + y1,
        x2 = currentX + x2,
        y2 = currentY + y2,
        x3 = (currentX + x3).also {
            current[0] = it
        },
        y3 = (currentY + y3).also {
            current[1] = it
        },
        isRelative = false,
    )
}

fun PathNodes.ReflectiveCurveTo.toAbsolute(current: DoubleArray): PathNodes.ReflectiveCurveTo = if (isRelative.not()) {
    current[0] = x2
    current[1] = y2
    this
} else {
    val currentX = current[0]
    val currentY = current[1]
    copy(
        x1 = currentX + x1,
        y1 = currentY + y1,
        x2 = (currentX + x2).also {
            current[0] = it
        },
        y2 = (currentY + y2).also {
            current[1] = it
        },
        isRelative = false,
    )
}

private fun PathNodes.QuadTo.toAbsolute(current: DoubleArray): PathNodes.QuadTo = if (isRelative.not()) {
    current[0] = x2
    current[1] = y2
    this
} else {
    val currentX = current[0]
    val currentY = current[1]
    copy(
        x1 = currentX + x1,
        y1 = currentY + y1,
        x2 = (currentX + x2).also {
            current[0] = it
        },
        y2 = (currentY + y2).also {
            current[1] = it
        },
        isRelative = false,
    )
}

private fun PathNodes.ReflectiveQuadTo.toAbsolute(current: DoubleArray): PathNodes.ReflectiveQuadTo =
    if (isRelative.not()) {
        current[0] = x1
        current[1] = y1
        this
    } else {
        current[0] += x1
        current[1] += y1
        copy(
            x1 = current[0],
            y1 = current[1],
            isRelative = false,
        )
    }

fun PathNodes.ArcTo.toAbsolute(current: DoubleArray): PathNodes.ArcTo = if (isRelative.not()) {
    current[0] = x
    current[1] = y
    this
} else {
    current[0] += x
    current[1] += y
    copy(
        x = current[0],
        y = current[1],
        isRelative = false,
    )
}
