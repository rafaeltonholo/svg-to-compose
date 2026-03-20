package dev.tonholo.s2c.website.editor.mapper

import androidx.compose.ui.graphics.vector.PathBuilder
import dev.tonholo.s2c.domain.PathNodes

/** Translates a [PathNodes] domain node into the corresponding [PathBuilder] command. */
internal fun PathBuilder.applyPathNode(node: PathNodes) {
    when (node) {
        is PathNodes.MoveTo -> handleMoveTo(node)
        is PathNodes.LineTo -> handleLineTo(node)
        is PathNodes.HorizontalLineTo -> handleHorizontalLineTo(node)
        is PathNodes.VerticalLineTo -> handleVerticalLineTo(node)
        is PathNodes.CurveTo -> handleCurveTo(node)
        is PathNodes.ReflectiveCurveTo -> handleReflectiveCurveTo(node)
        is PathNodes.QuadTo -> handleQuadTo(node)
        is PathNodes.ReflectiveQuadTo -> handleReflectiveQuadTo(node)
        is PathNodes.ArcTo -> handleArcTo(node)
    }
    if (node.shouldClose) close()
}

private fun PathBuilder.handleHorizontalLineTo(node: PathNodes.HorizontalLineTo) {
    if (node.isRelative) {
        horizontalLineToRelative(node.x.toFloat())
    } else {
        horizontalLineTo(node.x.toFloat())
    }
}

private fun PathBuilder.handleVerticalLineTo(node: PathNodes.VerticalLineTo) {
    if (node.isRelative) {
        verticalLineToRelative(node.y.toFloat())
    } else {
        verticalLineTo(node.y.toFloat())
    }
}

private fun PathBuilder.handleCurveTo(node: PathNodes.CurveTo) {
    if (node.isRelative) {
        curveToRelative(
            node.x1.toFloat(), node.y1.toFloat(),
            node.x2.toFloat(), node.y2.toFloat(),
            node.x3.toFloat(), node.y3.toFloat(),
        )
    } else {
        curveTo(
            node.x1.toFloat(), node.y1.toFloat(),
            node.x2.toFloat(), node.y2.toFloat(),
            node.x3.toFloat(), node.y3.toFloat(),
        )
    }
}

private fun PathBuilder.handleReflectiveCurveTo(node: PathNodes.ReflectiveCurveTo) {
    if (node.isRelative) {
        reflectiveCurveToRelative(
            node.x1.toFloat(), node.y1.toFloat(),
            node.x2.toFloat(), node.y2.toFloat(),
        )
    } else {
        reflectiveCurveTo(
            node.x1.toFloat(), node.y1.toFloat(),
            node.x2.toFloat(), node.y2.toFloat(),
        )
    }
}

private fun PathBuilder.handleQuadTo(node: PathNodes.QuadTo) {
    if (node.isRelative) {
        quadToRelative(
            node.x1.toFloat(), node.y1.toFloat(),
            node.x2.toFloat(), node.y2.toFloat(),
        )
    } else {
        quadTo(
            node.x1.toFloat(), node.y1.toFloat(),
            node.x2.toFloat(), node.y2.toFloat(),
        )
    }
}

private fun PathBuilder.handleReflectiveQuadTo(node: PathNodes.ReflectiveQuadTo) {
    if (node.isRelative) {
        reflectiveQuadToRelative(node.x1.toFloat(), node.y1.toFloat())
    } else {
        reflectiveQuadTo(node.x1.toFloat(), node.y1.toFloat())
    }
}

private fun PathBuilder.handleArcTo(node: PathNodes.ArcTo) {
    if (node.isRelative) {
        arcToRelative(
            a = node.a.toFloat(),
            b = node.b.toFloat(),
            theta = node.theta.toFloat(),
            isMoreThanHalf = node.isMoreThanHalf,
            isPositiveArc = node.isPositiveArc,
            dx1 = node.x.toFloat(),
            dy1 = node.y.toFloat(),
        )
    } else {
        arcTo(
            horizontalEllipseRadius = node.a.toFloat(),
            verticalEllipseRadius = node.b.toFloat(),
            theta = node.theta.toFloat(),
            isMoreThanHalf = node.isMoreThanHalf,
            isPositiveArc = node.isPositiveArc,
            x1 = node.x.toFloat(),
            y1 = node.y.toFloat(),
        )
    }
}

private fun PathBuilder.handleMoveTo(node: PathNodes.MoveTo) {
    if (node.isRelative) {
        moveToRelative(node.x.toFloat(), node.y.toFloat())
    } else {
        moveTo(node.x.toFloat(), node.y.toFloat())
    }
}

private fun PathBuilder.handleLineTo(node: PathNodes.LineTo) {
    if (node.isRelative) {
        lineToRelative(node.x.toFloat(), node.y.toFloat())
    } else {
        lineTo(node.x.toFloat(), node.y.toFloat())
    }
}
