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
            // M8.46 14.5 a.75 .75 0 0 1 1.06 .16 3 3 0 0 0 .58 .5 3.4 3.4 0 0 0 1.9 .56 3.4 3.4 0 0 0 1.9 -.56 3 3 0 0 0 .58 -.5 l.02 -.02 a.75 .75 0 0 1 1.2 .9 l-.03 .04 -.06 .06 -.18 .2 a5 5 0 0 1 -.7 .57 A5 5 0 0 1 12 17.22 a5 5 0 0 1 -2.74 -.81 5 5 0 0 1 -.7 -.57 l-.23 -.26 a.77 .77 0 0 1 .13 -1.09
            path(
                fill = SolidColor(Color(0xFF000000)),
            ) {
                // M 8.46 14.5
                moveTo(x = 8.46f, y = 14.5f)
                // a 0.75 0.75 0 0 1 1.06 0.16
                arcToRelative(
                    a = 0.75f,
                    b = 0.75f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 1.06f,
                    dy1 = 0.16f,
                )
                // a 3 3 0 0 0 0.58 0.5
                arcToRelative(
                    a = 3.0f,
                    b = 3.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.58f,
                    dy1 = 0.5f,
                )
                // a 3.4 3.4 0 0 0 1.9 0.56
                arcToRelative(
                    a = 3.4f,
                    b = 3.4f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 1.9f,
                    dy1 = 0.56f,
                )
                // a 3.4 3.4 0 0 0 1.9 -0.56
                arcToRelative(
                    a = 3.4f,
                    b = 3.4f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 1.9f,
                    dy1 = -0.56f,
                )
                // a 3 3 0 0 0 0.58 -0.5
                arcToRelative(
                    a = 3.0f,
                    b = 3.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.58f,
                    dy1 = -0.5f,
                )
                // l 0.02 -0.02
                lineToRelative(dx = 0.02f, dy = -0.02f)
                // a 0.75 0.75 0 0 1 1.2 0.9
                arcToRelative(
                    a = 0.75f,
                    b = 0.75f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 1.2f,
                    dy1 = 0.9f,
                )
                // l -0.03 0.04
                lineToRelative(dx = -0.03f, dy = 0.04f)
                // l -0.06 0.06
                lineToRelative(dx = -0.06f, dy = 0.06f)
                // l -0.18 0.2
                lineToRelative(dx = -0.18f, dy = 0.2f)
                // a 5 5 0 0 1 -0.7 0.57
                arcToRelative(
                    a = 5.0f,
                    b = 5.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.7f,
                    dy1 = 0.57f,
                )
                // A 5 5 0 0 1 12 17.22
                arcTo(
                    horizontalEllipseRadius = 5.0f,
                    verticalEllipseRadius = 5.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 12.0f,
                    y1 = 17.22f,
                )
                // a 5 5 0 0 1 -2.74 -0.81
                arcToRelative(
                    a = 5.0f,
                    b = 5.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -2.74f,
                    dy1 = -0.81f,
                )
                // a 5 5 0 0 1 -0.7 -0.57
                arcToRelative(
                    a = 5.0f,
                    b = 5.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.7f,
                    dy1 = -0.57f,
                )
                // l -0.23 -0.26
                lineToRelative(dx = -0.23f, dy = -0.26f)
                // a 0.77 0.77 0 0 1 0.13 -1.09
                arcToRelative(
                    a = 0.77f,
                    b = 0.77f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.13f,
                    dy1 = -1.09f,
                )
            }
            // M12 1 a11 11 0 1 1 0 22 11 11 0 0 1 0 -22 M2.5 12 a9.5 9.5 0 0 0 9.5 9.5 9.5 9.5 0 0 0 9.5 -9.5 A9.5 9.5 0 0 0 12 2.5 9.5 9.5 0 0 0 2.5 12
            path(
                fill = SolidColor(Color(0xFF000000)),
            ) {
                // M 12 1
                moveTo(x = 12.0f, y = 1.0f)
                // a 11 11 0 1 1 0 22
                arcToRelative(
                    a = 11.0f,
                    b = 11.0f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = true,
                    dx1 = 0.0f,
                    dy1 = 22.0f,
                )
                // a 11 11 0 0 1 0 -22
                arcToRelative(
                    a = 11.0f,
                    b = 11.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.0f,
                    dy1 = -22.0f,
                )
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
                // A 9.5 9.5 0 0 0 2.5 12
                arcTo(
                    horizontalEllipseRadius = 9.5f,
                    verticalEllipseRadius = 9.5f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 2.5f,
                    y1 = 12.0f,
                )
            }
            // M9 10.75 a1.25 1.25 0 1 1 -2.5 0 1.25 1.25 0 0 1 2.5 0 M16.25 12 a1.25 1.25 0 1 0 0 -2.5 1.25 1.25 0 0 0 0 2.5
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
                // a 1.25 1.25 0 0 1 2.5 0
                arcToRelative(
                    a = 1.25f,
                    b = 1.25f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 2.5f,
                    dy1 = 0.0f,
                )
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
                // a 1.25 1.25 0 0 0 0 2.5
                arcToRelative(
                    a = 1.25f,
                    b = 1.25f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.0f,
                    dy1 = 2.5f,
                )
            }
        }.build().also { _smiley = it }
    }

@Suppress("ObjectPropertyName")
private var _smiley: ImageVector? = null
