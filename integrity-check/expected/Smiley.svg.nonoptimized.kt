package dev.tonholo.s2c.integrity.icon.svg

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Smiley: ImageVector
    get() {
        val current = _smiley
        if (current != null) return current

        return ImageVector.Builder(
            name = ".Smiley",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f,
        ).apply {
            // M8.456 14.494 a.75 .75 0 0 1 1.068 .17 3.08 3.08 0 0 0 .572 .492 A3.381 3.381 0 0 0 12 15.72 c.855 0 1.487 -.283 1.904 -.562 a3.081 3.081 0 0 0 .572 -.492 l.021 -.026 a.75 .75 0 0 1 1.197 .905 l-.027 .034 c-.013 .016 -.03 .038 -.052 .063 -.044 .05 -.105 .119 -.184 .198 a4.569 4.569 0 0 1 -.695 .566 A4.88 4.88 0 0 1 12 17.22 a4.88 4.88 0 0 1 -2.736 -.814 4.57 4.57 0 0 1 -.695 -.566 3.253 3.253 0 0 1 -.236 -.261 c-.259 -.332 -.223 -.824 .123 -1.084Z
            path(
                fill = SolidColor(Color(0xFF000000)),
            ) {
                // M 8.456 14.494
                moveTo(x = 8.456f, y = 14.494f)
                // a 0.75 0.75 0 0 1 1.068 0.17
                arcToRelative(
                    a = 0.75f,
                    b = 0.75f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 1.068f,
                    dy1 = 0.17f,
                )
                // a 3.08 3.08 0 0 0 0.572 0.492
                arcToRelative(
                    a = 3.08f,
                    b = 3.08f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.572f,
                    dy1 = 0.492f,
                )
                // A 3.381 3.381 0 0 0 12 15.72
                arcTo(
                    horizontalEllipseRadius = 3.381f,
                    verticalEllipseRadius = 3.381f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 12.0f,
                    y1 = 15.72f,
                )
                // c 0.855 0 1.487 -0.283 1.904 -0.562
                curveToRelative(
                    dx1 = 0.855f,
                    dy1 = 0.0f,
                    dx2 = 1.487f,
                    dy2 = -0.283f,
                    dx3 = 1.904f,
                    dy3 = -0.562f,
                )
                // a 3.081 3.081 0 0 0 0.572 -0.492
                arcToRelative(
                    a = 3.081f,
                    b = 3.081f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.572f,
                    dy1 = -0.492f,
                )
                // l 0.021 -0.026
                lineToRelative(dx = 0.021f, dy = -0.026f)
                // a 0.75 0.75 0 0 1 1.197 0.905
                arcToRelative(
                    a = 0.75f,
                    b = 0.75f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 1.197f,
                    dy1 = 0.905f,
                )
                // l -0.027 0.034
                lineToRelative(dx = -0.027f, dy = 0.034f)
                // c -0.013 0.016 -0.03 0.038 -0.052 0.063
                curveToRelative(
                    dx1 = -0.013f,
                    dy1 = 0.016f,
                    dx2 = -0.03f,
                    dy2 = 0.038f,
                    dx3 = -0.052f,
                    dy3 = 0.063f,
                )
                // c -0.044 0.05 -0.105 0.119 -0.184 0.198
                curveToRelative(
                    dx1 = -0.044f,
                    dy1 = 0.05f,
                    dx2 = -0.105f,
                    dy2 = 0.119f,
                    dx3 = -0.184f,
                    dy3 = 0.198f,
                )
                // a 4.569 4.569 0 0 1 -0.695 0.566
                arcToRelative(
                    a = 4.569f,
                    b = 4.569f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.695f,
                    dy1 = 0.566f,
                )
                // A 4.88 4.88 0 0 1 12 17.22
                arcTo(
                    horizontalEllipseRadius = 4.88f,
                    verticalEllipseRadius = 4.88f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 12.0f,
                    y1 = 17.22f,
                )
                // a 4.88 4.88 0 0 1 -2.736 -0.814
                arcToRelative(
                    a = 4.88f,
                    b = 4.88f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -2.736f,
                    dy1 = -0.814f,
                )
                // a 4.57 4.57 0 0 1 -0.695 -0.566
                arcToRelative(
                    a = 4.57f,
                    b = 4.57f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.695f,
                    dy1 = -0.566f,
                )
                // a 3.253 3.253 0 0 1 -0.236 -0.261
                arcToRelative(
                    a = 3.253f,
                    b = 3.253f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.236f,
                    dy1 = -0.261f,
                )
                // c -0.259 -0.332 -0.223 -0.824 0.123 -1.084z
                curveToRelative(
                    dx1 = -0.259f,
                    dy1 = -0.332f,
                    dx2 = -0.223f,
                    dy2 = -0.824f,
                    dx3 = 0.123f,
                    dy3 = -1.084f,
                )
                close()
            }
            // M12 1 c6.075 0 11 4.925 11 11 s-4.925 11 -11 11 S1 18.075 1 12 5.925 1 12 1Z M2.5 12 a9.5 9.5 0 0 0 9.5 9.5 9.5 9.5 0 0 0 9.5 -9.5 A9.5 9.5 0 0 0 12 2.5 9.5 9.5 0 0 0 2.5 12Z
            path(
                fill = SolidColor(Color(0xFF000000)),
            ) {
                // M 12 1
                moveTo(x = 12.0f, y = 1.0f)
                // c 6.075 0 11 4.925 11 11
                curveToRelative(
                    dx1 = 6.075f,
                    dy1 = 0.0f,
                    dx2 = 11.0f,
                    dy2 = 4.925f,
                    dx3 = 11.0f,
                    dy3 = 11.0f,
                )
                // s -4.925 11 -11 11
                reflectiveCurveToRelative(
                    dx1 = -4.925f,
                    dy1 = 11.0f,
                    dx2 = -11.0f,
                    dy2 = 11.0f,
                )
                // S 1 18.075 1 12
                reflectiveCurveTo(
                    x1 = 1.0f,
                    y1 = 18.075f,
                    x2 = 1.0f,
                    y2 = 12.0f,
                )
                // S 5.925 1 12 1z
                reflectiveCurveTo(
                    x1 = 5.925f,
                    y1 = 1.0f,
                    x2 = 12.0f,
                    y2 = 1.0f,
                )
                close()
                // M 2.5 12
                moveTo(x = 2.5f, y = 12.0f)
                // a 9.5 9.5 0 0 0 9.5 9.5
                arcToRelative(
                    a = 9.5f,
                    b = 9.5f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 9.5f,
                    dy1 = 9.5f,
                )
                // a 9.5 9.5 0 0 0 9.5 -9.5
                arcToRelative(
                    a = 9.5f,
                    b = 9.5f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 9.5f,
                    dy1 = -9.5f,
                )
                // A 9.5 9.5 0 0 0 12 2.5
                arcTo(
                    horizontalEllipseRadius = 9.5f,
                    verticalEllipseRadius = 9.5f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 12.0f,
                    y1 = 2.5f,
                )
                // A 9.5 9.5 0 0 0 2.5 12z
                arcTo(
                    horizontalEllipseRadius = 9.5f,
                    verticalEllipseRadius = 9.5f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 2.5f,
                    y1 = 12.0f,
                )
                close()
            }
            // M9 10.75 a1.25 1.25 0 1 1 -2.5 0 1.25 1.25 0 0 1 2.5 0Z M16.25 12 a1.25 1.25 0 1 0 0 -2.5 1.25 1.25 0 0 0 0 2.5Z
            path(
                fill = SolidColor(Color(0xFF000000)),
            ) {
                // M 9 10.75
                moveTo(x = 9.0f, y = 10.75f)
                // a 1.25 1.25 0 1 1 -2.5 0
                arcToRelative(
                    a = 1.25f,
                    b = 1.25f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = true,
                    dx1 = -2.5f,
                    dy1 = 0.0f,
                )
                // a 1.25 1.25 0 0 1 2.5 0z
                arcToRelative(
                    a = 1.25f,
                    b = 1.25f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 2.5f,
                    dy1 = 0.0f,
                )
                close()
                // M 16.25 12
                moveTo(x = 16.25f, y = 12.0f)
                // a 1.25 1.25 0 1 0 0 -2.5
                arcToRelative(
                    a = 1.25f,
                    b = 1.25f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = 0.0f,
                    dy1 = -2.5f,
                )
                // a 1.25 1.25 0 0 0 0 2.5z
                arcToRelative(
                    a = 1.25f,
                    b = 1.25f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.0f,
                    dy1 = 2.5f,
                )
                close()
            }
        }.build().also { _smiley = it }
    }

@Suppress("ObjectPropertyName")
private var _smiley: ImageVector? = null
