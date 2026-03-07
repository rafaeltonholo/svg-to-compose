package dev.tonholo.s2c.integrity.icon.svg

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathData
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AndroidDevelopers: ImageVector
    get() {
        val current = _androidDevelopers
        if (current != null) return current

        return ImageVector.Builder(
            name = ".AndroidDevelopers",
            defaultWidth = 129.0.dp,
            defaultHeight = 20.0.dp,
            viewportWidth = 129.0f,
            viewportHeight = 20.0f,
        ).apply {
            group(
                // M25.47 13.32 a13 13 0 0 0 -2.03 -4.43 13 13 0 0 0 -2.1 -2.3 13 13 0 0 0 -1.6 -1.18 l.01 -.03 .78 -1.34 .76 -1.32 .55 -.94 a1.19 1.19 0 0 0 -2.05 -1.19 l-.54 .94 -.77 1.32 -.78 1.34 -.08 .15 L17.5 4.3 a13 13 0 0 0 -4.62 -.84 h-.13 a13 13 0 0 0 -4.57 .88 L8.1 4.2 7.32 2.86 6.56 1.53 6 .6 a1.18 1.18 0 0 0 -2.17 .29 1.2 1.2 0 0 0 .12 .9 l.55 .94 .76 1.32 .78 1.34 V5.4 a13 13 0 0 0 -4.66 5.03 13 13 0 0 0 -1.1 2.9 C.09 14.2 .8 15 1.72 15 h22.35 c.91 0 1.63 -.8 1.41 -1.68
                clipPathData = PathData {
                    // M 25.47 13.32
                    moveTo(x = 25.47f, y = 13.32f)
                    // a 13 13 0 0 0 -2.03 -4.43
                    arcToRelative(
                        a = 13.0f,
                        b = 13.0f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -2.03f,
                        dy1 = -4.43f,
                    )
                    // a 13 13 0 0 0 -2.1 -2.3
                    arcToRelative(
                        a = 13.0f,
                        b = 13.0f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -2.1f,
                        dy1 = -2.3f,
                    )
                    // a 13 13 0 0 0 -1.6 -1.18
                    arcToRelative(
                        a = 13.0f,
                        b = 13.0f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -1.6f,
                        dy1 = -1.18f,
                    )
                    // l 0.01 -0.03
                    lineToRelative(dx = 0.01f, dy = -0.03f)
                    // l 0.78 -1.34
                    lineToRelative(dx = 0.78f, dy = -1.34f)
                    // l 0.76 -1.32
                    lineToRelative(dx = 0.76f, dy = -1.32f)
                    // l 0.55 -0.94
                    lineToRelative(dx = 0.55f, dy = -0.94f)
                    // a 1.19 1.19 0 0 0 -2.05 -1.19
                    arcToRelative(
                        a = 1.19f,
                        b = 1.19f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -2.05f,
                        dy1 = -1.19f,
                    )
                    // l -0.54 0.94
                    lineToRelative(dx = -0.54f, dy = 0.94f)
                    // l -0.77 1.32
                    lineToRelative(dx = -0.77f, dy = 1.32f)
                    // l -0.78 1.34
                    lineToRelative(dx = -0.78f, dy = 1.34f)
                    // l -0.08 0.15
                    lineToRelative(dx = -0.08f, dy = 0.15f)
                    // L 17.5 4.3
                    lineTo(x = 17.5f, y = 4.3f)
                    // a 13 13 0 0 0 -4.62 -0.84
                    arcToRelative(
                        a = 13.0f,
                        b = 13.0f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -4.62f,
                        dy1 = -0.84f,
                    )
                    // h -0.13
                    horizontalLineToRelative(dx = -0.13f)
                    // a 13 13 0 0 0 -4.57 0.88
                    arcToRelative(
                        a = 13.0f,
                        b = 13.0f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -4.57f,
                        dy1 = 0.88f,
                    )
                    // L 8.1 4.2
                    lineTo(x = 8.1f, y = 4.2f)
                    // L 7.32 2.86
                    lineTo(x = 7.32f, y = 2.86f)
                    // L 6.56 1.53
                    lineTo(x = 6.56f, y = 1.53f)
                    // L 6 0.6
                    lineTo(x = 6.0f, y = 0.6f)
                    // a 1.18 1.18 0 0 0 -2.17 0.29
                    arcToRelative(
                        a = 1.18f,
                        b = 1.18f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -2.17f,
                        dy1 = 0.29f,
                    )
                    // a 1.2 1.2 0 0 0 0.12 0.9
                    arcToRelative(
                        a = 1.2f,
                        b = 1.2f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = 0.12f,
                        dy1 = 0.9f,
                    )
                    // l 0.55 0.94
                    lineToRelative(dx = 0.55f, dy = 0.94f)
                    // l 0.76 1.32
                    lineToRelative(dx = 0.76f, dy = 1.32f)
                    // l 0.78 1.34
                    lineToRelative(dx = 0.78f, dy = 1.34f)
                    // V 5.4
                    verticalLineTo(y = 5.4f)
                    // a 13 13 0 0 0 -4.66 5.03
                    arcToRelative(
                        a = 13.0f,
                        b = 13.0f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -4.66f,
                        dy1 = 5.03f,
                    )
                    // a 13 13 0 0 0 -1.1 2.9
                    arcToRelative(
                        a = 13.0f,
                        b = 13.0f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -1.1f,
                        dy1 = 2.9f,
                    )
                    // C 0.09 14.2 0.8 15 1.72 15
                    curveTo(
                        x1 = 0.09f,
                        y1 = 14.2f,
                        x2 = 0.8f,
                        y2 = 15.0f,
                        x3 = 1.72f,
                        y3 = 15.0f,
                    )
                    // h 22.35
                    horizontalLineToRelative(dx = 22.35f)
                    // c 0.91 0 1.63 -0.8 1.41 -1.68
                    curveToRelative(
                        dx1 = 0.91f,
                        dy1 = 0.0f,
                        dx2 = 1.63f,
                        dy2 = -0.8f,
                        dx3 = 1.41f,
                        dy3 = -1.68f,
                    )
                },
            ) {
                // M25.91 -.47 h-27.5 V15 h27.5z
                path(
                    fill = SolidColor(Color(0xFF4FAF53)),
                ) {
                    // M 25.91 -0.47
                    moveTo(x = 25.91f, y = -0.47f)
                    // h -27.5
                    horizontalLineToRelative(dx = -27.5f)
                    // V 15
                    verticalLineTo(y = 15.0f)
                    // h 27.5z
                    horizontalLineToRelative(dx = 27.5f)
                    close()
                }
                // M12.36 5.36 c2.61 2.07 -4.74 2.3 -7.58 6.81 S4.21 24.53 1.6 22.47 C0 19.98 -.26 14.94 2.58 10.42 s7.56 -6 9.77 -5.07z
                path(
                    fill = Brush.linearGradient(
                        0.0f to Color(0xFFA8F0B9),
                        0.807f to Color(0x00ADEEBC),
                        start = Offset(x = 2.442f, y = 10.32f),
                        end = Offset(x = 6.361f, y = 12.778f),
                    ),
                    fillAlpha = 0.4f,
                ) {
                    // M 12.36 5.36
                    moveTo(x = 12.36f, y = 5.36f)
                    // c 2.61 2.07 -4.74 2.3 -7.58 6.81
                    curveToRelative(
                        dx1 = 2.61f,
                        dy1 = 2.07f,
                        dx2 = -4.74f,
                        dy2 = 2.3f,
                        dx3 = -7.58f,
                        dy3 = 6.81f,
                    )
                    // S 4.21 24.53 1.6 22.47
                    reflectiveCurveTo(
                        x1 = 4.21f,
                        y1 = 24.53f,
                        x2 = 1.6f,
                        y2 = 22.47f,
                    )
                    // C 0 19.98 -0.26 14.94 2.58 10.42
                    curveTo(
                        x1 = 0.0f,
                        y1 = 19.98f,
                        x2 = -0.26f,
                        y2 = 14.94f,
                        x3 = 2.58f,
                        y3 = 10.42f,
                    )
                    // s 7.56 -6 9.77 -5.07z
                    reflectiveCurveToRelative(
                        dx1 = 7.56f,
                        dy1 = -6.0f,
                        dx2 = 9.77f,
                        dy2 = -5.07f,
                    )
                    close()
                }
                // M13.49 5.34 c-2.74 1.96 4.98 2.17 7.97 6.44 s.6 11.68 3.34 9.73 c1.69 -2.34 1.95 -7.1 -1.03 -11.37 -2.99 -4.27 -7.94 -5.67 -10.27 -4.8z
                path(
                    fill = Brush.linearGradient(
                        0.292f to Color(0xFFA8F0B9),
                        0.828f to Color(0x00ADEEBC),
                        start = Offset(x = 23.915f, y = 10.032f),
                        end = Offset(x = 20.055f, y = 12.726f),
                    ),
                    fillAlpha = 0.4f,
                ) {
                    // M 13.49 5.34
                    moveTo(x = 13.49f, y = 5.34f)
                    // c -2.74 1.96 4.98 2.17 7.97 6.44
                    curveToRelative(
                        dx1 = -2.74f,
                        dy1 = 1.96f,
                        dx2 = 4.98f,
                        dy2 = 2.17f,
                        dx3 = 7.97f,
                        dy3 = 6.44f,
                    )
                    // s 0.6 11.68 3.34 9.73
                    reflectiveCurveToRelative(
                        dx1 = 0.6f,
                        dy1 = 11.68f,
                        dx2 = 3.34f,
                        dy2 = 9.73f,
                    )
                    // c 1.69 -2.34 1.95 -7.1 -1.03 -11.37
                    curveToRelative(
                        dx1 = 1.69f,
                        dy1 = -2.34f,
                        dx2 = 1.95f,
                        dy2 = -7.1f,
                        dx3 = -1.03f,
                        dy3 = -11.37f,
                    )
                    // c -2.99 -4.27 -7.94 -5.67 -10.27 -4.8z
                    curveToRelative(
                        dx1 = -2.99f,
                        dy1 = -4.27f,
                        dx2 = -7.94f,
                        dy2 = -5.67f,
                        dx3 = -10.27f,
                        dy3 = -4.8f,
                    )
                    close()
                }
                // M-1.39 11.67 c-1.04 3.3 -1.58 6.06 -1.2 6.18 S-1.05 15.4 0 12.11 s5.36 -6.89 4.98 -7 C4.6 4.97 -.34 8.36 -1.39 11.66
                path(
                    fill = SolidColor(Color(0xFF8BD8A0)),
                    fillAlpha = 0.6f,
                    strokeAlpha = 0.6f,
                ) {
                    // M -1.39 11.67
                    moveTo(x = -1.39f, y = 11.67f)
                    // c -1.04 3.3 -1.58 6.06 -1.2 6.18
                    curveToRelative(
                        dx1 = -1.04f,
                        dy1 = 3.3f,
                        dx2 = -1.58f,
                        dy2 = 6.06f,
                        dx3 = -1.2f,
                        dy3 = 6.18f,
                    )
                    // S -1.05 15.4 0 12.11
                    reflectiveCurveTo(
                        x1 = -1.05f,
                        y1 = 15.4f,
                        x2 = 0.0f,
                        y2 = 12.11f,
                    )
                    // s 5.36 -6.89 4.98 -7
                    reflectiveCurveToRelative(
                        dx1 = 5.36f,
                        dy1 = -6.89f,
                        dx2 = 4.98f,
                        dy2 = -7.0f,
                    )
                    // C 4.6 4.97 -0.34 8.36 -1.39 11.66
                    curveTo(
                        x1 = 4.6f,
                        y1 = 4.97f,
                        x2 = -0.34f,
                        y2 = 8.36f,
                        x3 = -1.39f,
                        y3 = 11.66f,
                    )
                }
                // M25.97 11.37 c1.04 3.3 1.58 6.06 1.2 6.18 s-1.54 -2.45 -2.59 -5.74 -5.36 -6.89 -4.98 -7.01 5.32 3.27 6.37 6.57
                path(
                    fill = SolidColor(Color(0xFF8BD8A0)),
                    fillAlpha = 0.5f,
                    strokeAlpha = 0.5f,
                ) {
                    // M 25.97 11.37
                    moveTo(x = 25.97f, y = 11.37f)
                    // c 1.04 3.3 1.58 6.06 1.2 6.18
                    curveToRelative(
                        dx1 = 1.04f,
                        dy1 = 3.3f,
                        dx2 = 1.58f,
                        dy2 = 6.06f,
                        dx3 = 1.2f,
                        dy3 = 6.18f,
                    )
                    // s -1.54 -2.45 -2.59 -5.74
                    reflectiveCurveToRelative(
                        dx1 = -1.54f,
                        dy1 = -2.45f,
                        dx2 = -2.59f,
                        dy2 = -5.74f,
                    )
                    // s -5.36 -6.89 -4.98 -7.01
                    reflectiveCurveToRelative(
                        dx1 = -5.36f,
                        dy1 = -6.89f,
                        dx2 = -4.98f,
                        dy2 = -7.01f,
                    )
                    // s 5.32 3.27 6.37 6.57
                    reflectiveCurveToRelative(
                        dx1 = 5.32f,
                        dy1 = 3.27f,
                        dx2 = 6.37f,
                        dy2 = 6.57f,
                    )
                }
                // M4.9 2.58 C5.94 4.28 6.4 5.1 6.24 5.2 6.1 5.3 5.4 4.62 4.36 2.92 2.79 1.54 4.45 -.32 4.6 -.42 s-1.24 .75 .3 3
                path(
                    fill = SolidColor(Color(0xFF0D652D)),
                    fillAlpha = 0.7f,
                    strokeAlpha = 0.7f,
                ) {
                    // M 4.9 2.58
                    moveTo(x = 4.9f, y = 2.58f)
                    // C 5.94 4.28 6.4 5.1 6.24 5.2
                    curveTo(
                        x1 = 5.94f,
                        y1 = 4.28f,
                        x2 = 6.4f,
                        y2 = 5.1f,
                        x3 = 6.24f,
                        y3 = 5.2f,
                    )
                    // C 6.1 5.3 5.4 4.62 4.36 2.92
                    curveTo(
                        x1 = 6.1f,
                        y1 = 5.3f,
                        x2 = 5.4f,
                        y2 = 4.62f,
                        x3 = 4.36f,
                        y3 = 2.92f,
                    )
                    // C 2.79 1.54 4.45 -0.32 4.6 -0.42
                    curveTo(
                        x1 = 2.79f,
                        y1 = 1.54f,
                        x2 = 4.45f,
                        y2 = -0.32f,
                        x3 = 4.6f,
                        y3 = -0.42f,
                    )
                    // s -1.24 0.75 0.3 3
                    reflectiveCurveToRelative(
                        dx1 = -1.24f,
                        dy1 = 0.75f,
                        dx2 = 0.3f,
                        dy2 = 3.0f,
                    )
                }
                // M6.04 5.37 c.13 -.08 -.53 -1.3 -1.46 -2.73 C3.65 1.22 2.8 .13 2.67 .21 S3.2 1.5 4.13 2.93 s1.79 2.52 1.91 2.44
                path(
                    fill = SolidColor(Color(0xFF202124)),
                    fillAlpha = 0.1f,
                    strokeAlpha = 0.1f,
                ) {
                    // M 6.04 5.37
                    moveTo(x = 6.04f, y = 5.37f)
                    // c 0.13 -0.08 -0.53 -1.3 -1.46 -2.73
                    curveToRelative(
                        dx1 = 0.13f,
                        dy1 = -0.08f,
                        dx2 = -0.53f,
                        dy2 = -1.3f,
                        dx3 = -1.46f,
                        dy3 = -2.73f,
                    )
                    // C 3.65 1.22 2.8 0.13 2.67 0.21
                    curveTo(
                        x1 = 3.65f,
                        y1 = 1.22f,
                        x2 = 2.8f,
                        y2 = 0.13f,
                        x3 = 2.67f,
                        y3 = 0.21f,
                    )
                    // S 3.2 1.5 4.13 2.93
                    reflectiveCurveTo(
                        x1 = 3.2f,
                        y1 = 1.5f,
                        x2 = 4.13f,
                        y2 = 2.93f,
                    )
                    // s 1.79 2.52 1.91 2.44
                    reflectiveCurveToRelative(
                        dx1 = 1.79f,
                        dy1 = 2.52f,
                        dx2 = 1.91f,
                        dy2 = 2.44f,
                    )
                }
                // M6.2 1.72 c1.08 1.78 1.6 2.6 1.73 2.52 .12 -.07 -.21 -1 -1.3 -2.8 C5.58 -.92 3.7 .5 3.88 .75 c.37 .7 .93 -1.52 2.31 .97
                path(
                    fill = SolidColor(Color(0xFFFFFFFF)),
                    fillAlpha = 0.3f,
                    strokeAlpha = 0.3f,
                ) {
                    // M 6.2 1.72
                    moveTo(x = 6.2f, y = 1.72f)
                    // c 1.08 1.78 1.6 2.6 1.73 2.52
                    curveToRelative(
                        dx1 = 1.08f,
                        dy1 = 1.78f,
                        dx2 = 1.6f,
                        dy2 = 2.6f,
                        dx3 = 1.73f,
                        dy3 = 2.52f,
                    )
                    // c 0.12 -0.07 -0.21 -1 -1.3 -2.8
                    curveToRelative(
                        dx1 = 0.12f,
                        dy1 = -0.07f,
                        dx2 = -0.21f,
                        dy2 = -1.0f,
                        dx3 = -1.3f,
                        dy3 = -2.8f,
                    )
                    // C 5.58 -0.92 3.7 0.5 3.88 0.75
                    curveTo(
                        x1 = 5.58f,
                        y1 = -0.92f,
                        x2 = 3.7f,
                        y2 = 0.5f,
                        x3 = 3.88f,
                        y3 = 0.75f,
                    )
                    // c 0.37 0.7 0.93 -1.52 2.31 0.97
                    curveToRelative(
                        dx1 = 0.37f,
                        dy1 = 0.7f,
                        dx2 = 0.93f,
                        dy2 = -1.52f,
                        dx3 = 2.31f,
                        dy3 = 0.97f,
                    )
                }
                // M2.84 1.08 c.2 .65 .95 1.01 1.69 .8 .74 -.22 1.18 -.92 1 -1.58 -.2 -.66 -.95 -1.02 -1.7 -.8 C3.1 -.3 2.66 .42 2.85 1.08
                path(
                    fill = Brush.radialGradient(
                        0.0f to Color(0xFFFFFFFF),
                        0.948f to Color(0x00FFFFFF),
                        center = Offset.Zero,
                        radius = 1.0f,
                    ),
                    fillAlpha = 0.9f,
                    strokeAlpha = 0.15f,
                ) {
                    // M 2.84 1.08
                    moveTo(x = 2.84f, y = 1.08f)
                    // c 0.2 0.65 0.95 1.01 1.69 0.8
                    curveToRelative(
                        dx1 = 0.2f,
                        dy1 = 0.65f,
                        dx2 = 0.95f,
                        dy2 = 1.01f,
                        dx3 = 1.69f,
                        dy3 = 0.8f,
                    )
                    // c 0.74 -0.22 1.18 -0.92 1 -1.58
                    curveToRelative(
                        dx1 = 0.74f,
                        dy1 = -0.22f,
                        dx2 = 1.18f,
                        dy2 = -0.92f,
                        dx3 = 1.0f,
                        dy3 = -1.58f,
                    )
                    // c -0.2 -0.66 -0.95 -1.02 -1.7 -0.8
                    curveToRelative(
                        dx1 = -0.2f,
                        dy1 = -0.66f,
                        dx2 = -0.95f,
                        dy2 = -1.02f,
                        dx3 = -1.7f,
                        dy3 = -0.8f,
                    )
                    // C 3.1 -0.3 2.66 0.42 2.85 1.08
                    curveTo(
                        x1 = 3.1f,
                        y1 = -0.3f,
                        x2 = 2.66f,
                        y2 = 0.42f,
                        x3 = 2.85f,
                        y3 = 1.08f,
                    )
                }
                // M20.13 .9 c.2 .68 1 1.04 1.8 .81 .79 -.23 1.27 -.96 1.08 -1.64 -.2 -.68 -1 -1.04 -1.8 -.8 -.8 .22 -1.28 .96 -1.08 1.63
                path(
                    fill = Brush.radialGradient(
                        0.0f to Color(0xFFFFFFFF),
                        0.948f to Color(0x00FFFFFF),
                        center = Offset.Zero,
                        radius = 1.0f,
                    ),
                    fillAlpha = 0.9f,
                    strokeAlpha = 0.15f,
                ) {
                    // M 20.13 0.9
                    moveTo(x = 20.13f, y = 0.9f)
                    // c 0.2 0.68 1 1.04 1.8 0.81
                    curveToRelative(
                        dx1 = 0.2f,
                        dy1 = 0.68f,
                        dx2 = 1.0f,
                        dy2 = 1.04f,
                        dx3 = 1.8f,
                        dy3 = 0.81f,
                    )
                    // c 0.79 -0.23 1.27 -0.96 1.08 -1.64
                    curveToRelative(
                        dx1 = 0.79f,
                        dy1 = -0.23f,
                        dx2 = 1.27f,
                        dy2 = -0.96f,
                        dx3 = 1.08f,
                        dy3 = -1.64f,
                    )
                    // c -0.2 -0.68 -1 -1.04 -1.8 -0.8
                    curveToRelative(
                        dx1 = -0.2f,
                        dy1 = -0.68f,
                        dx2 = -1.0f,
                        dy2 = -1.04f,
                        dx3 = -1.8f,
                        dy3 = -0.8f,
                    )
                    // c -0.8 0.22 -1.28 0.96 -1.08 1.63
                    curveToRelative(
                        dx1 = -0.8f,
                        dy1 = 0.22f,
                        dx2 = -1.28f,
                        dy2 = 0.96f,
                        dx3 = -1.08f,
                        dy3 = 1.63f,
                    )
                }
                // M19.66 5.46 c-.13 -.08 .5 -1.3 1.42 -2.75 .9 -1.44 1.74 -2.54 1.87 -2.46 .12 .08 -.51 1.3 -1.42 2.75 -.9 1.44 -1.75 2.54 -1.87 2.46Z
                path(
                    fill = SolidColor(Color(0xFF202124)),
                    fillAlpha = 0.1f,
                    strokeAlpha = 0.1f,
                ) {
                    // M 19.66 5.46
                    moveTo(x = 19.66f, y = 5.46f)
                    // c -0.13 -0.08 0.5 -1.3 1.42 -2.75
                    curveToRelative(
                        dx1 = -0.13f,
                        dy1 = -0.08f,
                        dx2 = 0.5f,
                        dy2 = -1.3f,
                        dx3 = 1.42f,
                        dy3 = -2.75f,
                    )
                    // c 0.9 -1.44 1.74 -2.54 1.87 -2.46
                    curveToRelative(
                        dx1 = 0.9f,
                        dy1 = -1.44f,
                        dx2 = 1.74f,
                        dy2 = -2.54f,
                        dx3 = 1.87f,
                        dy3 = -2.46f,
                    )
                    // c 0.12 0.08 -0.51 1.3 -1.42 2.75
                    curveToRelative(
                        dx1 = 0.12f,
                        dy1 = 0.08f,
                        dx2 = -0.51f,
                        dy2 = 1.3f,
                        dx3 = -1.42f,
                        dy3 = 2.75f,
                    )
                    // c -0.9 1.44 -1.75 2.54 -1.87 2.46z
                    curveToRelative(
                        dx1 = -0.9f,
                        dy1 = 1.44f,
                        dx2 = -1.75f,
                        dy2 = 2.54f,
                        dx3 = -1.87f,
                        dy3 = 2.46f,
                    )
                    close()
                }
                // M20.27 5.27 c-1.1 -.46 -2.03 -.72 -2.08 -.59 -.06 .14 .77 .52 1.86 .98 1.09 .47 2.03 .83 2.09 .7 .06 -.14 -.78 -.63 -1.87 -1.09
                path(
                    fill = SolidColor(Color(0xFF0D652D)),
                    fillAlpha = 0.3f,
                    strokeAlpha = 0.3f,
                ) {
                    // M 20.27 5.27
                    moveTo(x = 20.27f, y = 5.27f)
                    // c -1.1 -0.46 -2.03 -0.72 -2.08 -0.59
                    curveToRelative(
                        dx1 = -1.1f,
                        dy1 = -0.46f,
                        dx2 = -2.03f,
                        dy2 = -0.72f,
                        dx3 = -2.08f,
                        dy3 = -0.59f,
                    )
                    // c -0.06 0.14 0.77 0.52 1.86 0.98
                    curveToRelative(
                        dx1 = -0.06f,
                        dy1 = 0.14f,
                        dx2 = 0.77f,
                        dy2 = 0.52f,
                        dx3 = 1.86f,
                        dy3 = 0.98f,
                    )
                    // c 1.09 0.47 2.03 0.83 2.09 0.7
                    curveToRelative(
                        dx1 = 1.09f,
                        dy1 = 0.47f,
                        dx2 = 2.03f,
                        dy2 = 0.83f,
                        dx3 = 2.09f,
                        dy3 = 0.7f,
                    )
                    // c 0.06 -0.14 -0.78 -0.63 -1.87 -1.09
                    curveToRelative(
                        dx1 = 0.06f,
                        dy1 = -0.14f,
                        dx2 = -0.78f,
                        dy2 = -0.63f,
                        dx3 = -1.87f,
                        dy3 = -1.09f,
                    )
                }
                // M18.03 4.4 c-.3 -.15 -.57 -.22 -.6 -.16 s.2 .18 .5 .33 .57 .27 .6 .2 c.03 -.05 -.2 -.22 -.5 -.37
                path(
                    fill = SolidColor(Color(0xFF81C995)),
                    fillAlpha = 0.3f,
                    strokeAlpha = 0.3f,
                ) {
                    // M 18.03 4.4
                    moveTo(x = 18.03f, y = 4.4f)
                    // c -0.3 -0.15 -0.57 -0.22 -0.6 -0.16
                    curveToRelative(
                        dx1 = -0.3f,
                        dy1 = -0.15f,
                        dx2 = -0.57f,
                        dy2 = -0.22f,
                        dx3 = -0.6f,
                        dy3 = -0.16f,
                    )
                    // s 0.2 0.18 0.5 0.33
                    reflectiveCurveToRelative(
                        dx1 = 0.2f,
                        dy1 = 0.18f,
                        dx2 = 0.5f,
                        dy2 = 0.33f,
                    )
                    // s 0.57 0.27 0.6 0.2
                    reflectiveCurveToRelative(
                        dx1 = 0.57f,
                        dy1 = 0.27f,
                        dx2 = 0.6f,
                        dy2 = 0.2f,
                    )
                    // c 0.03 -0.05 -0.2 -0.22 -0.5 -0.37
                    curveToRelative(
                        dx1 = 0.03f,
                        dy1 = -0.05f,
                        dx2 = -0.2f,
                        dy2 = -0.22f,
                        dx3 = -0.5f,
                        dy3 = -0.37f,
                    )
                }
                // M5.37 5.4 c1.08 -.5 2 -.77 2.06 -.64 C7.5 4.9 6.68 5.3 5.6 5.78 S3.58 6.65 3.52 6.51 C3.46 6.38 4.28 5.88 5.37 5.4Z
                path(
                    fill = SolidColor(Color(0xFF0D652D)),
                    fillAlpha = 0.3f,
                    strokeAlpha = 0.3f,
                ) {
                    // M 5.37 5.4
                    moveTo(x = 5.37f, y = 5.4f)
                    // c 1.08 -0.5 2 -0.77 2.06 -0.64
                    curveToRelative(
                        dx1 = 1.08f,
                        dy1 = -0.5f,
                        dx2 = 2.0f,
                        dy2 = -0.77f,
                        dx3 = 2.06f,
                        dy3 = -0.64f,
                    )
                    // C 7.5 4.9 6.68 5.3 5.6 5.78
                    curveTo(
                        x1 = 7.5f,
                        y1 = 4.9f,
                        x2 = 6.68f,
                        y2 = 5.3f,
                        x3 = 5.6f,
                        y3 = 5.78f,
                    )
                    // S 3.58 6.65 3.52 6.51
                    reflectiveCurveTo(
                        x1 = 3.58f,
                        y1 = 6.65f,
                        x2 = 3.52f,
                        y2 = 6.51f,
                    )
                    // C 3.46 6.38 4.28 5.88 5.37 5.4z
                    curveTo(
                        x1 = 3.46f,
                        y1 = 6.38f,
                        x2 = 4.28f,
                        y2 = 5.88f,
                        x3 = 5.37f,
                        y3 = 5.4f,
                    )
                    close()
                }
                // M7.58 4.48 c.3 -.16 .57 -.24 .6 -.18 s-.2 .2 -.5 .35 -.56 .27 -.6 .22 c-.02 -.06 .2 -.24 .5 -.4
                path(
                    fill = SolidColor(Color(0xFF81C995)),
                    fillAlpha = 0.3f,
                    strokeAlpha = 0.3f,
                ) {
                    // M 7.58 4.48
                    moveTo(x = 7.58f, y = 4.48f)
                    // c 0.3 -0.16 0.57 -0.24 0.6 -0.18
                    curveToRelative(
                        dx1 = 0.3f,
                        dy1 = -0.16f,
                        dx2 = 0.57f,
                        dy2 = -0.24f,
                        dx3 = 0.6f,
                        dy3 = -0.18f,
                    )
                    // s -0.2 0.2 -0.5 0.35
                    reflectiveCurveToRelative(
                        dx1 = -0.2f,
                        dy1 = 0.2f,
                        dx2 = -0.5f,
                        dy2 = 0.35f,
                    )
                    // s -0.56 0.27 -0.6 0.22
                    reflectiveCurveToRelative(
                        dx1 = -0.56f,
                        dy1 = 0.27f,
                        dx2 = -0.6f,
                        dy2 = 0.22f,
                    )
                    // c -0.02 -0.06 0.2 -0.24 0.5 -0.4
                    curveToRelative(
                        dx1 = -0.02f,
                        dy1 = -0.06f,
                        dx2 = 0.2f,
                        dy2 = -0.24f,
                        dx3 = 0.5f,
                        dy3 = -0.4f,
                    )
                }
                // M20.93 2.66 c-1 1.72 -1.43 2.54 -1.28 2.63 .16 .09 .84 -.59 1.84 -2.32 1.53 -1.4 -.17 -3.24 -.32 -3.32 -.15 -.1 1.25 .71 -.24 3
                path(
                    fill = SolidColor(Color(0xFF0D652D)),
                    fillAlpha = 0.7f,
                    strokeAlpha = 0.7f,
                ) {
                    // M 20.93 2.66
                    moveTo(x = 20.93f, y = 2.66f)
                    // c -1 1.72 -1.43 2.54 -1.28 2.63
                    curveToRelative(
                        dx1 = -1.0f,
                        dy1 = 1.72f,
                        dx2 = -1.43f,
                        dy2 = 2.54f,
                        dx3 = -1.28f,
                        dy3 = 2.63f,
                    )
                    // c 0.16 0.09 0.84 -0.59 1.84 -2.32
                    curveToRelative(
                        dx1 = 0.16f,
                        dy1 = 0.09f,
                        dx2 = 0.84f,
                        dy2 = -0.59f,
                        dx3 = 1.84f,
                        dy3 = -2.32f,
                    )
                    // c 1.53 -1.4 -0.17 -3.24 -0.32 -3.32
                    curveToRelative(
                        dx1 = 1.53f,
                        dy1 = -1.4f,
                        dx2 = -0.17f,
                        dy2 = -3.24f,
                        dx3 = -0.32f,
                        dy3 = -3.32f,
                    )
                    // c -0.15 -0.1 1.25 0.71 -0.24 3
                    curveToRelative(
                        dx1 = -0.15f,
                        dy1 = -0.1f,
                        dx2 = 1.25f,
                        dy2 = 0.71f,
                        dx3 = -0.24f,
                        dy3 = 3.0f,
                    )
                }
                // M19.56 1.81 c-1.01 1.76 -1.51 2.56 -1.64 2.49 s.16 -1 1.18 -2.76 c1.15 -3.05 3.45 -2.65 3.07 -.98 -.3 .69 -1.33 -1.21 -2.6 1.25Z
                path(
                    fill = SolidColor(Color(0xFFFFFFFF)),
                    fillAlpha = 0.3f,
                    strokeAlpha = 0.3f,
                ) {
                    // M 19.56 1.81
                    moveTo(x = 19.56f, y = 1.81f)
                    // c -1.01 1.76 -1.51 2.56 -1.64 2.49
                    curveToRelative(
                        dx1 = -1.01f,
                        dy1 = 1.76f,
                        dx2 = -1.51f,
                        dy2 = 2.56f,
                        dx3 = -1.64f,
                        dy3 = 2.49f,
                    )
                    // s 0.16 -1 1.18 -2.76
                    reflectiveCurveToRelative(
                        dx1 = 0.16f,
                        dy1 = -1.0f,
                        dx2 = 1.18f,
                        dy2 = -2.76f,
                    )
                    // c 1.15 -3.05 3.45 -2.65 3.07 -0.98
                    curveToRelative(
                        dx1 = 1.15f,
                        dy1 = -3.05f,
                        dx2 = 3.45f,
                        dy2 = -2.65f,
                        dx3 = 3.07f,
                        dy3 = -0.98f,
                    )
                    // c -0.3 0.69 -1.33 -1.21 -2.6 1.25z
                    curveToRelative(
                        dx1 = -0.3f,
                        dy1 = 0.69f,
                        dx2 = -1.33f,
                        dy2 = -1.21f,
                        dx3 = -2.6f,
                        dy3 = 1.25f,
                    )
                    close()
                }
                // M7.94 9.67 c.16 -.2 .02 -.59 -.32 -.9 -.34 -.3 -.75 -.4 -.91 -.2 -.17 .18 -.03 .58 .31 .89 .34 .3 .75 .4 .92 .2
                path(
                    fill = Brush.radialGradient(
                        0.0f to Color(0xFF93E19F),
                        1.0f to Color(0x0093E19F),
                        center = Offset.Zero,
                        radius = 1.0f,
                    ),
                    fillAlpha = 0.7f,
                ) {
                    // M 7.94 9.67
                    moveTo(x = 7.94f, y = 9.67f)
                    // c 0.16 -0.2 0.02 -0.59 -0.32 -0.9
                    curveToRelative(
                        dx1 = 0.16f,
                        dy1 = -0.2f,
                        dx2 = 0.02f,
                        dy2 = -0.59f,
                        dx3 = -0.32f,
                        dy3 = -0.9f,
                    )
                    // c -0.34 -0.3 -0.75 -0.4 -0.91 -0.2
                    curveToRelative(
                        dx1 = -0.34f,
                        dy1 = -0.3f,
                        dx2 = -0.75f,
                        dy2 = -0.4f,
                        dx3 = -0.91f,
                        dy3 = -0.2f,
                    )
                    // c -0.17 0.18 -0.03 0.58 0.31 0.89
                    curveToRelative(
                        dx1 = -0.17f,
                        dy1 = 0.18f,
                        dx2 = -0.03f,
                        dy2 = 0.58f,
                        dx3 = 0.31f,
                        dy3 = 0.89f,
                    )
                    // c 0.34 0.3 0.75 0.4 0.92 0.2
                    curveToRelative(
                        dx1 = 0.34f,
                        dy1 = 0.3f,
                        dx2 = 0.75f,
                        dy2 = 0.4f,
                        dx3 = 0.92f,
                        dy3 = 0.2f,
                    )
                }
                // M19.23 9.48 c.34 -.31 .48 -.7 .3 -.9 -.16 -.19 -.57 -.1 -.9 .21 -.35 .3 -.49 .7 -.32 .9 .17 .19 .58 .09 .92 -.21Z
                path(
                    fill = Brush.radialGradient(
                        0.0f to Color(0xFF93E19F),
                        1.0f to Color(0x0093E19F),
                        center = Offset.Zero,
                        radius = 1.0f,
                    ),
                    fillAlpha = 0.7f,
                ) {
                    // M 19.23 9.48
                    moveTo(x = 19.23f, y = 9.48f)
                    // c 0.34 -0.31 0.48 -0.7 0.3 -0.9
                    curveToRelative(
                        dx1 = 0.34f,
                        dy1 = -0.31f,
                        dx2 = 0.48f,
                        dy2 = -0.7f,
                        dx3 = 0.3f,
                        dy3 = -0.9f,
                    )
                    // c -0.16 -0.19 -0.57 -0.1 -0.9 0.21
                    curveToRelative(
                        dx1 = -0.16f,
                        dy1 = -0.19f,
                        dx2 = -0.57f,
                        dy2 = -0.1f,
                        dx3 = -0.9f,
                        dy3 = 0.21f,
                    )
                    // c -0.35 0.3 -0.49 0.7 -0.32 0.9
                    curveToRelative(
                        dx1 = -0.35f,
                        dy1 = 0.3f,
                        dx2 = -0.49f,
                        dy2 = 0.7f,
                        dx3 = -0.32f,
                        dy3 = 0.9f,
                    )
                    // c 0.17 0.19 0.58 0.09 0.92 -0.21z
                    curveToRelative(
                        dx1 = 0.17f,
                        dy1 = 0.19f,
                        dx2 = 0.58f,
                        dy2 = 0.09f,
                        dx3 = 0.92f,
                        dy3 = -0.21f,
                    )
                    close()
                }
                // M7.8 10.92 C8.2 9.89 7.78 9.26 7.5 9.06 c-.77 -.95 -1.8 -.21 -2.12 .2 -.3 .4 -.76 1.13 -.31 2.13 .44 1 2.2 .81 2.72 -.47 m10.65 .02 c-.4 -1.01 .02 -1.66 .3 -1.84 .76 -.96 1.8 -.22 2.11 .2 .3 .4 .76 1.12 .32 2.1 -.44 1 -2.21 .83 -2.72 -.45
                path(
                    fill = SolidColor(Color(0xFF011B04)),
                    fillAlpha = 0.09f,
                    strokeAlpha = 0.09f,
                ) {
                    // M 7.8 10.92
                    moveTo(x = 7.8f, y = 10.92f)
                    // C 8.2 9.89 7.78 9.26 7.5 9.06
                    curveTo(
                        x1 = 8.2f,
                        y1 = 9.89f,
                        x2 = 7.78f,
                        y2 = 9.26f,
                        x3 = 7.5f,
                        y3 = 9.06f,
                    )
                    // c -0.77 -0.95 -1.8 -0.21 -2.12 0.2
                    curveToRelative(
                        dx1 = -0.77f,
                        dy1 = -0.95f,
                        dx2 = -1.8f,
                        dy2 = -0.21f,
                        dx3 = -2.12f,
                        dy3 = 0.2f,
                    )
                    // c -0.3 0.4 -0.76 1.13 -0.31 2.13
                    curveToRelative(
                        dx1 = -0.3f,
                        dy1 = 0.4f,
                        dx2 = -0.76f,
                        dy2 = 1.13f,
                        dx3 = -0.31f,
                        dy3 = 2.13f,
                    )
                    // c 0.44 1 2.2 0.81 2.72 -0.47
                    curveToRelative(
                        dx1 = 0.44f,
                        dy1 = 1.0f,
                        dx2 = 2.2f,
                        dy2 = 0.81f,
                        dx3 = 2.72f,
                        dy3 = -0.47f,
                    )
                    // m 10.65 0.02
                    moveToRelative(dx = 10.65f, dy = 0.02f)
                    // c -0.4 -1.01 0.02 -1.66 0.3 -1.84
                    curveToRelative(
                        dx1 = -0.4f,
                        dy1 = -1.01f,
                        dx2 = 0.02f,
                        dy2 = -1.66f,
                        dx3 = 0.3f,
                        dy3 = -1.84f,
                    )
                    // c 0.76 -0.96 1.8 -0.22 2.11 0.2
                    curveToRelative(
                        dx1 = 0.76f,
                        dy1 = -0.96f,
                        dx2 = 1.8f,
                        dy2 = -0.22f,
                        dx3 = 2.11f,
                        dy3 = 0.2f,
                    )
                    // c 0.3 0.4 0.76 1.12 0.32 2.1
                    curveToRelative(
                        dx1 = 0.3f,
                        dy1 = 0.4f,
                        dx2 = 0.76f,
                        dy2 = 1.12f,
                        dx3 = 0.32f,
                        dy3 = 2.1f,
                    )
                    // c -0.44 1 -2.21 0.83 -2.72 -0.45
                    curveToRelative(
                        dx1 = -0.44f,
                        dy1 = 1.0f,
                        dx2 = -2.21f,
                        dy2 = 0.83f,
                        dx3 = -2.72f,
                        dy3 = -0.45f,
                    )
                }
                // M20.78 11.64 c.64 -.42 .74 -1.4 .22 -2.18 -.5 -.78 -1.45 -1.06 -2.08 -.64 s-.74 1.41 -.23 2.2 c.52 .77 1.45 1.05 2.09 .62
                path(
                    fill = SolidColor(Color(0xFF202124)),
                ) {
                    // M 20.78 11.64
                    moveTo(x = 20.78f, y = 11.64f)
                    // c 0.64 -0.42 0.74 -1.4 0.22 -2.18
                    curveToRelative(
                        dx1 = 0.64f,
                        dy1 = -0.42f,
                        dx2 = 0.74f,
                        dy2 = -1.4f,
                        dx3 = 0.22f,
                        dy3 = -2.18f,
                    )
                    // c -0.5 -0.78 -1.45 -1.06 -2.08 -0.64
                    curveToRelative(
                        dx1 = -0.5f,
                        dy1 = -0.78f,
                        dx2 = -1.45f,
                        dy2 = -1.06f,
                        dx3 = -2.08f,
                        dy3 = -0.64f,
                    )
                    // s -0.74 1.41 -0.23 2.2
                    reflectiveCurveToRelative(
                        dx1 = -0.74f,
                        dy1 = 1.41f,
                        dx2 = -0.23f,
                        dy2 = 2.2f,
                    )
                    // c 0.52 0.77 1.45 1.05 2.09 0.62
                    curveToRelative(
                        dx1 = 0.52f,
                        dy1 = 0.77f,
                        dx2 = 1.45f,
                        dy2 = 1.05f,
                        dx3 = 2.09f,
                        dy3 = 0.62f,
                    )
                }
                // M7.55 10.98 c.52 -.78 .42 -1.74 -.2 -2.17 C6.72 8.4 5.8 8.7 5.29 9.46 s-.42 1.74 .2 2.16 c.63 .42 1.55 .13 2.06 -.64 m11.14 .01 c-.51 -.78 -.42 -1.74 .2 -2.17 .64 -.41 1.55 -.12 2.06 .65 .52 .77 .43 1.74 -.2 2.16 S19.2 11.76 18.69 11
                path(
                    fill = SolidColor(Color(0xFF000000)),
                ) {
                    // M 7.55 10.98
                    moveTo(x = 7.55f, y = 10.98f)
                    // c 0.52 -0.78 0.42 -1.74 -0.2 -2.17
                    curveToRelative(
                        dx1 = 0.52f,
                        dy1 = -0.78f,
                        dx2 = 0.42f,
                        dy2 = -1.74f,
                        dx3 = -0.2f,
                        dy3 = -2.17f,
                    )
                    // C 6.72 8.4 5.8 8.7 5.29 9.46
                    curveTo(
                        x1 = 6.72f,
                        y1 = 8.4f,
                        x2 = 5.8f,
                        y2 = 8.7f,
                        x3 = 5.29f,
                        y3 = 9.46f,
                    )
                    // s -0.42 1.74 0.2 2.16
                    reflectiveCurveToRelative(
                        dx1 = -0.42f,
                        dy1 = 1.74f,
                        dx2 = 0.2f,
                        dy2 = 2.16f,
                    )
                    // c 0.63 0.42 1.55 0.13 2.06 -0.64
                    curveToRelative(
                        dx1 = 0.63f,
                        dy1 = 0.42f,
                        dx2 = 1.55f,
                        dy2 = 0.13f,
                        dx3 = 2.06f,
                        dy3 = -0.64f,
                    )
                    // m 11.14 0.01
                    moveToRelative(dx = 11.14f, dy = 0.01f)
                    // c -0.51 -0.78 -0.42 -1.74 0.2 -2.17
                    curveToRelative(
                        dx1 = -0.51f,
                        dy1 = -0.78f,
                        dx2 = -0.42f,
                        dy2 = -1.74f,
                        dx3 = 0.2f,
                        dy3 = -2.17f,
                    )
                    // c 0.64 -0.41 1.55 -0.12 2.06 0.65
                    curveToRelative(
                        dx1 = 0.64f,
                        dy1 = -0.41f,
                        dx2 = 1.55f,
                        dy2 = -0.12f,
                        dx3 = 2.06f,
                        dy3 = 0.65f,
                    )
                    // c 0.52 0.77 0.43 1.74 -0.2 2.16
                    curveToRelative(
                        dx1 = 0.52f,
                        dy1 = 0.77f,
                        dx2 = 0.43f,
                        dy2 = 1.74f,
                        dx3 = -0.2f,
                        dy3 = 2.16f,
                    )
                    // S 19.2 11.76 18.69 11
                    reflectiveCurveTo(
                        x1 = 19.2f,
                        y1 = 11.76f,
                        x2 = 18.69f,
                        y2 = 11.0f,
                    )
                }
                // M5.48 9.36 a2.1 2.1 0 0 0 -.49 1.62 2 2 0 0 1 .4 -1.74 2 2 0 0 1 .94 -.58 l.12 -.03 .18 -.03 -.18 .03 -.12 .03 c-.2 .07 -.48 .23 -.86 .7
                path(
                    fill = SolidColor(Color(0xFF000000)),
                ) {
                    // M 5.48 9.36
                    moveTo(x = 5.48f, y = 9.36f)
                    // a 2.1 2.1 0 0 0 -0.49 1.62
                    arcToRelative(
                        a = 2.1f,
                        b = 2.1f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -0.49f,
                        dy1 = 1.62f,
                    )
                    // a 2 2 0 0 1 0.4 -1.74
                    arcToRelative(
                        a = 2.0f,
                        b = 2.0f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        dx1 = 0.4f,
                        dy1 = -1.74f,
                    )
                    // a 2 2 0 0 1 0.94 -0.58
                    arcToRelative(
                        a = 2.0f,
                        b = 2.0f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        dx1 = 0.94f,
                        dy1 = -0.58f,
                    )
                    // l 0.12 -0.03
                    lineToRelative(dx = 0.12f, dy = -0.03f)
                    // l 0.18 -0.03
                    lineToRelative(dx = 0.18f, dy = -0.03f)
                    // l -0.18 0.03
                    lineToRelative(dx = -0.18f, dy = 0.03f)
                    // l -0.12 0.03
                    lineToRelative(dx = -0.12f, dy = 0.03f)
                    // c -0.2 0.07 -0.48 0.23 -0.86 0.7
                    curveToRelative(
                        dx1 = -0.2f,
                        dy1 = 0.07f,
                        dx2 = -0.48f,
                        dy2 = 0.23f,
                        dx3 = -0.86f,
                        dy3 = 0.7f,
                    )
                }
                // M7.69 9.51 c-.2 -.86 -.9 -.94 -1.22 -.88 .28 -.13 .78 .03 1 .28 A1.2 1.2 0 0 1 7.7 9.52z
                path(
                    fill = Brush.linearGradient(
                        0.0f to Color(0xFFE2DDE2),
                        1.0f to Color(0x00E2DDE2),
                        start = Offset(x = 7.05f, y = 8.265f),
                        end = Offset(x = 7.102f, y = 9.478f),
                    ),
                    fillAlpha = 0.8f,
                    strokeAlpha = 0.8f,
                ) {
                    // M 7.69 9.51
                    moveTo(x = 7.69f, y = 9.51f)
                    // c -0.2 -0.86 -0.9 -0.94 -1.22 -0.88
                    curveToRelative(
                        dx1 = -0.2f,
                        dy1 = -0.86f,
                        dx2 = -0.9f,
                        dy2 = -0.94f,
                        dx3 = -1.22f,
                        dy3 = -0.88f,
                    )
                    // c 0.28 -0.13 0.78 0.03 1 0.28
                    curveToRelative(
                        dx1 = 0.28f,
                        dy1 = -0.13f,
                        dx2 = 0.78f,
                        dy2 = 0.03f,
                        dx3 = 1.0f,
                        dy3 = 0.28f,
                    )
                    // A 1.2 1.2 0 0 1 7.7 9.52z
                    arcTo(
                        horizontalEllipseRadius = 1.2f,
                        verticalEllipseRadius = 1.2f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        x1 = 7.7f,
                        y1 = 9.52f,
                    )
                    close()
                }
                // M19.5 8.78 a1.03 1.03 0 0 0 -1 .9 1.2 1.2 0 0 1 .8 -.63 c.46 -.08 1.07 .45 1.32 .73 l.22 -.26 a1.5 1.5 0 0 0 -1.34 -.74
                path(
                    fill = Brush.linearGradient(
                        0.0f to Color(0xFF373637),
                        1.0f to Color(0x00373637),
                        start = Offset(x = 19.67f, y = 8.781f),
                        end = Offset(x = 19.67f, y = 9.776f),
                    ),
                ) {
                    // M 19.5 8.78
                    moveTo(x = 19.5f, y = 8.78f)
                    // a 1.03 1.03 0 0 0 -1 0.9
                    arcToRelative(
                        a = 1.03f,
                        b = 1.03f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -1.0f,
                        dy1 = 0.9f,
                    )
                    // a 1.2 1.2 0 0 1 0.8 -0.63
                    arcToRelative(
                        a = 1.2f,
                        b = 1.2f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        dx1 = 0.8f,
                        dy1 = -0.63f,
                    )
                    // c 0.46 -0.08 1.07 0.45 1.32 0.73
                    curveToRelative(
                        dx1 = 0.46f,
                        dy1 = -0.08f,
                        dx2 = 1.07f,
                        dy2 = 0.45f,
                        dx3 = 1.32f,
                        dy3 = 0.73f,
                    )
                    // l 0.22 -0.26
                    lineToRelative(dx = 0.22f, dy = -0.26f)
                    // a 1.5 1.5 0 0 0 -1.34 -0.74
                    arcToRelative(
                        a = 1.5f,
                        b = 1.5f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -1.34f,
                        dy1 = -0.74f,
                    )
                }
                // M20.77 9.38 A2.1 2.1 0 0 1 21.25 11 a2 2 0 0 0 -.39 -1.74 2 2 0 0 0 -.94 -.58 l-.13 -.03 -.17 -.03 .17 .03 .12 .03 c.21 .07 .49 .23 .87 .7z
                path(
                    fill = SolidColor(Color(0xFF000000)),
                ) {
                    // M 20.77 9.38
                    moveTo(x = 20.77f, y = 9.38f)
                    // A 2.1 2.1 0 0 1 21.25 11
                    arcTo(
                        horizontalEllipseRadius = 2.1f,
                        verticalEllipseRadius = 2.1f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        x1 = 21.25f,
                        y1 = 11.0f,
                    )
                    // a 2 2 0 0 0 -0.39 -1.74
                    arcToRelative(
                        a = 2.0f,
                        b = 2.0f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -0.39f,
                        dy1 = -1.74f,
                    )
                    // a 2 2 0 0 0 -0.94 -0.58
                    arcToRelative(
                        a = 2.0f,
                        b = 2.0f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -0.94f,
                        dy1 = -0.58f,
                    )
                    // l -0.13 -0.03
                    lineToRelative(dx = -0.13f, dy = -0.03f)
                    // l -0.17 -0.03
                    lineToRelative(dx = -0.17f, dy = -0.03f)
                    // l 0.17 0.03
                    lineToRelative(dx = 0.17f, dy = 0.03f)
                    // l 0.12 0.03
                    lineToRelative(dx = 0.12f, dy = 0.03f)
                    // c 0.21 0.07 0.49 0.23 0.87 0.7z
                    curveToRelative(
                        dx1 = 0.21f,
                        dy1 = 0.07f,
                        dx2 = 0.49f,
                        dy2 = 0.23f,
                        dx3 = 0.87f,
                        dy3 = 0.7f,
                    )
                    close()
                }
                // M6.12 9.14 C5.83 9.2 5.62 9.5 5.56 9.64 L5.7 9.87 S5.8 9.7 5.92 9.57 l.36 -.29 -.15 -.14 m14.01 0 c.28 .06 .5 .35 .57 .5 l-.15 .2 s-.1 -.16 -.21 -.28 c-.11 -.11 -.28 -.23 -.35 -.28z M7.3 10.7 l-.44 .22 -.08 .25 a1 1 0 0 0 .43 -.25z M7.72 9.6 7.65 9.4 v.09 l.09 .27z m11.21 1.12 .44 .21 .08 .25 a1 1 0 0 1 -.43 -.25z m-.42 -1.1 .08 -.21 V9.5 L18.5 9.77 q-.02 -.04 0 -.16z
                path(
                    fill = SolidColor(Color(0xFFE2DCE1)),
                    fillAlpha = 0.8f,
                    strokeAlpha = 0.8f,
                ) {
                    // M 6.12 9.14
                    moveTo(x = 6.12f, y = 9.14f)
                    // C 5.83 9.2 5.62 9.5 5.56 9.64
                    curveTo(
                        x1 = 5.83f,
                        y1 = 9.2f,
                        x2 = 5.62f,
                        y2 = 9.5f,
                        x3 = 5.56f,
                        y3 = 9.64f,
                    )
                    // L 5.7 9.87
                    lineTo(x = 5.7f, y = 9.87f)
                    // S 5.8 9.7 5.92 9.57
                    reflectiveCurveTo(
                        x1 = 5.8f,
                        y1 = 9.7f,
                        x2 = 5.92f,
                        y2 = 9.57f,
                    )
                    // l 0.36 -0.29
                    lineToRelative(dx = 0.36f, dy = -0.29f)
                    // l -0.15 -0.14
                    lineToRelative(dx = -0.15f, dy = -0.14f)
                    // m 14.01 0
                    moveToRelative(dx = 14.01f, dy = 0.0f)
                    // c 0.28 0.06 0.5 0.35 0.57 0.5
                    curveToRelative(
                        dx1 = 0.28f,
                        dy1 = 0.06f,
                        dx2 = 0.5f,
                        dy2 = 0.35f,
                        dx3 = 0.57f,
                        dy3 = 0.5f,
                    )
                    // l -0.15 0.2
                    lineToRelative(dx = -0.15f, dy = 0.2f)
                    // s -0.1 -0.16 -0.21 -0.28
                    reflectiveCurveToRelative(
                        dx1 = -0.1f,
                        dy1 = -0.16f,
                        dx2 = -0.21f,
                        dy2 = -0.28f,
                    )
                    // c -0.11 -0.11 -0.28 -0.23 -0.35 -0.28z
                    curveToRelative(
                        dx1 = -0.11f,
                        dy1 = -0.11f,
                        dx2 = -0.28f,
                        dy2 = -0.23f,
                        dx3 = -0.35f,
                        dy3 = -0.28f,
                    )
                    close()
                    // M 7.3 10.7
                    moveTo(x = 7.3f, y = 10.7f)
                    // l -0.44 0.22
                    lineToRelative(dx = -0.44f, dy = 0.22f)
                    // l -0.08 0.25
                    lineToRelative(dx = -0.08f, dy = 0.25f)
                    // a 1 1 0 0 0 0.43 -0.25z
                    arcToRelative(
                        a = 1.0f,
                        b = 1.0f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = 0.43f,
                        dy1 = -0.25f,
                    )
                    close()
                    // M 7.72 9.6
                    moveTo(x = 7.72f, y = 9.6f)
                    // L 7.65 9.4
                    lineTo(x = 7.65f, y = 9.4f)
                    // v 0.09
                    verticalLineToRelative(dy = 0.09f)
                    // l 0.09 0.27z
                    lineToRelative(dx = 0.09f, dy = 0.27f)
                    close()
                    // m 11.21 1.12
                    moveToRelative(dx = 11.21f, dy = 1.12f)
                    // l 0.44 0.21
                    lineToRelative(dx = 0.44f, dy = 0.21f)
                    // l 0.08 0.25
                    lineToRelative(dx = 0.08f, dy = 0.25f)
                    // a 1 1 0 0 1 -0.43 -0.25z
                    arcToRelative(
                        a = 1.0f,
                        b = 1.0f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        dx1 = -0.43f,
                        dy1 = -0.25f,
                    )
                    close()
                    // m -0.42 -1.1
                    moveToRelative(dx = -0.42f, dy = -1.1f)
                    // l 0.08 -0.21
                    lineToRelative(dx = 0.08f, dy = -0.21f)
                    // V 9.5
                    verticalLineTo(y = 9.5f)
                    // L 18.5 9.77
                    lineTo(x = 18.5f, y = 9.77f)
                    // q -0.02 -0.04 0 -0.16z
                    quadToRelative(
                        dx1 = -0.02f,
                        dy1 = -0.04f,
                        dx2 = 0.0f,
                        dy2 = -0.16f,
                    )
                    close()
                }
            }
            // M35.88 0 c4.55 0 8.15 3.4 8.15 7.66 s-3.6 7.68 -8.15 7.68 h-4.11 V0z m-1.72 13.1 h1.72 c3.29 0 5.74 -2.33 5.74 -5.44 s-2.45 -5.42 -5.74 -5.42 h-1.72z m10.95 -2.67 a5.16 5.16 0 0 0 9.46 2.9 l-1.77 -1.19 a3 3 0 0 1 -2.55 1.47 c-1.35 0 -2.5 -1.06 -2.79 -2.49 h7.44 a5 5 0 0 0 .06 -.73 c0 -2.92 -1.95 -5.18 -4.85 -5.18 s-5 2.26 -5 5.22 m2.4 -1.02 c.28 -1.28 1.2 -2.16 2.6 -2.16 s2.32 .88 2.61 2.16z m7.41 -3.89 3.6 9.82 h2.63 l3.6 -9.82 h-2.33 l-2.54 7.35 h-.1 l-2.52 -7.35z m9.77 4.91 a5.16 5.16 0 0 0 9.46 2.9 l-1.76 -1.19 a3 3 0 0 1 -2.55 1.47 c-1.35 0 -2.5 -1.06 -2.79 -2.49 h7.44 a5 5 0 0 0 .06 -.73 c0 -2.92 -1.95 -5.18 -4.85 -5.18 s-5 2.26 -5 5.22 m2.4 -1.02 c.3 -1.28 1.2 -2.16 2.61 -2.16 1.4 0 2.32 .88 2.6 2.16z m8.6 5.93 h2.25 V0 H75.7z m3.4 -4.95 c0 2.95 2.3 5.27 5.21 5.27 s5.22 -2.32 5.22 -5.27 a5.1 5.1 0 0 0 -5.22 -5.18 5.1 5.1 0 0 0 -5.21 5.18 m2.27 0 c0 -1.77 1.29 -3.14 2.94 -3.14 1.66 0 2.94 1.37 2.94 3.14 s-1.3 3.22 -2.94 3.22 c-1.63 0 -2.94 -1.43 -2.94 -3.22 M90.67 20 h2.24 v-5.46 h.1 a3.5 3.5 0 0 0 2.72 1.12 c2.7 0 4.93 -2.38 4.93 -5.2 0 -2.84 -2.26 -5.25 -4.93 -5.25 a3.5 3.5 0 0 0 -2.72 1.2 h-.1 V5.52 h-2.24z m2.08 -9.55 c0 -1.82 1.2 -3.2 2.77 -3.2 1.58 0 2.86 1.41 2.86 3.2 s-1.26 3.17 -2.86 3.17 -2.77 -1.36 -2.77 -3.17 m8.94 -.02 a5.16 5.16 0 0 0 9.46 2.9 l-1.77 -1.19 a3 3 0 0 1 -2.55 1.48 c-1.34 0 -2.49 -1.07 -2.78 -2.5 h7.44 a5 5 0 0 0 .06 -.73 c0 -2.92 -1.95 -5.18 -4.85 -5.18 s-5 2.26 -5 5.22 m2.4 -1.02 c.29 -1.28 1.2 -2.16 2.6 -2.16 s2.32 .88 2.62 2.16z m8.55 5.93 h2.24 V10.2 c0 -2.05 1.07 -2.95 2.86 -2.95 a3 3 0 0 1 .81 .08 V5.34 a4 4 0 0 0 -1.09 -.13 c-3.03 0 -4.82 1.92 -4.82 4.99z m6.3 -2.63 c.18 1.71 1.95 2.95 4.2 2.95 2.35 0 3.87 -1.22 3.87 -3.13 0 -1.59 -.92 -2.5 -2.96 -2.9 l-1.37 -.28 q-1.23 -.24 -1.24 -1.16 c0 -.76 .6 -1.25 1.55 -1.25 1.08 0 1.78 .53 1.93 1.5 l2 -.46 C126.65 6.3 125.07 5.2 123 5.2 c-2.23 0 -3.75 1.23 -3.75 3.08 0 1.51 1.03 2.6 2.86 2.97 l1.36 .27 c.94 .2 1.37 .57 1.37 1.22 0 .73 -.65 1.2 -1.7 1.2 -1.2 0 -1.98 -.63 -2.18 -1.7z
            path(
                fill = SolidColor(Color(0xFF202124)),
            ) {
                // M 35.88 0
                moveTo(x = 35.88f, y = 0.0f)
                // c 4.55 0 8.15 3.4 8.15 7.66
                curveToRelative(
                    dx1 = 4.55f,
                    dy1 = 0.0f,
                    dx2 = 8.15f,
                    dy2 = 3.4f,
                    dx3 = 8.15f,
                    dy3 = 7.66f,
                )
                // s -3.6 7.68 -8.15 7.68
                reflectiveCurveToRelative(
                    dx1 = -3.6f,
                    dy1 = 7.68f,
                    dx2 = -8.15f,
                    dy2 = 7.68f,
                )
                // h -4.11
                horizontalLineToRelative(dx = -4.11f)
                // V 0z
                verticalLineTo(y = 0.0f)
                close()
                // m -1.72 13.1
                moveToRelative(dx = -1.72f, dy = 13.1f)
                // h 1.72
                horizontalLineToRelative(dx = 1.72f)
                // c 3.29 0 5.74 -2.33 5.74 -5.44
                curveToRelative(
                    dx1 = 3.29f,
                    dy1 = 0.0f,
                    dx2 = 5.74f,
                    dy2 = -2.33f,
                    dx3 = 5.74f,
                    dy3 = -5.44f,
                )
                // s -2.45 -5.42 -5.74 -5.42
                reflectiveCurveToRelative(
                    dx1 = -2.45f,
                    dy1 = -5.42f,
                    dx2 = -5.74f,
                    dy2 = -5.42f,
                )
                // h -1.72z
                horizontalLineToRelative(dx = -1.72f)
                close()
                // m 10.95 -2.67
                moveToRelative(dx = 10.95f, dy = -2.67f)
                // a 5.16 5.16 0 0 0 9.46 2.9
                arcToRelative(
                    a = 5.16f,
                    b = 5.16f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 9.46f,
                    dy1 = 2.9f,
                )
                // l -1.77 -1.19
                lineToRelative(dx = -1.77f, dy = -1.19f)
                // a 3 3 0 0 1 -2.55 1.47
                arcToRelative(
                    a = 3.0f,
                    b = 3.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -2.55f,
                    dy1 = 1.47f,
                )
                // c -1.35 0 -2.5 -1.06 -2.79 -2.49
                curveToRelative(
                    dx1 = -1.35f,
                    dy1 = 0.0f,
                    dx2 = -2.5f,
                    dy2 = -1.06f,
                    dx3 = -2.79f,
                    dy3 = -2.49f,
                )
                // h 7.44
                horizontalLineToRelative(dx = 7.44f)
                // a 5 5 0 0 0 0.06 -0.73
                arcToRelative(
                    a = 5.0f,
                    b = 5.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.06f,
                    dy1 = -0.73f,
                )
                // c 0 -2.92 -1.95 -5.18 -4.85 -5.18
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = -2.92f,
                    dx2 = -1.95f,
                    dy2 = -5.18f,
                    dx3 = -4.85f,
                    dy3 = -5.18f,
                )
                // s -5 2.26 -5 5.22
                reflectiveCurveToRelative(
                    dx1 = -5.0f,
                    dy1 = 2.26f,
                    dx2 = -5.0f,
                    dy2 = 5.22f,
                )
                // m 2.4 -1.02
                moveToRelative(dx = 2.4f, dy = -1.02f)
                // c 0.28 -1.28 1.2 -2.16 2.6 -2.16
                curveToRelative(
                    dx1 = 0.28f,
                    dy1 = -1.28f,
                    dx2 = 1.2f,
                    dy2 = -2.16f,
                    dx3 = 2.6f,
                    dy3 = -2.16f,
                )
                // s 2.32 0.88 2.61 2.16z
                reflectiveCurveToRelative(
                    dx1 = 2.32f,
                    dy1 = 0.88f,
                    dx2 = 2.61f,
                    dy2 = 2.16f,
                )
                close()
                // m 7.41 -3.89
                moveToRelative(dx = 7.41f, dy = -3.89f)
                // l 3.6 9.82
                lineToRelative(dx = 3.6f, dy = 9.82f)
                // h 2.63
                horizontalLineToRelative(dx = 2.63f)
                // l 3.6 -9.82
                lineToRelative(dx = 3.6f, dy = -9.82f)
                // h -2.33
                horizontalLineToRelative(dx = -2.33f)
                // l -2.54 7.35
                lineToRelative(dx = -2.54f, dy = 7.35f)
                // h -0.1
                horizontalLineToRelative(dx = -0.1f)
                // l -2.52 -7.35z
                lineToRelative(dx = -2.52f, dy = -7.35f)
                close()
                // m 9.77 4.91
                moveToRelative(dx = 9.77f, dy = 4.91f)
                // a 5.16 5.16 0 0 0 9.46 2.9
                arcToRelative(
                    a = 5.16f,
                    b = 5.16f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 9.46f,
                    dy1 = 2.9f,
                )
                // l -1.76 -1.19
                lineToRelative(dx = -1.76f, dy = -1.19f)
                // a 3 3 0 0 1 -2.55 1.47
                arcToRelative(
                    a = 3.0f,
                    b = 3.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -2.55f,
                    dy1 = 1.47f,
                )
                // c -1.35 0 -2.5 -1.06 -2.79 -2.49
                curveToRelative(
                    dx1 = -1.35f,
                    dy1 = 0.0f,
                    dx2 = -2.5f,
                    dy2 = -1.06f,
                    dx3 = -2.79f,
                    dy3 = -2.49f,
                )
                // h 7.44
                horizontalLineToRelative(dx = 7.44f)
                // a 5 5 0 0 0 0.06 -0.73
                arcToRelative(
                    a = 5.0f,
                    b = 5.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.06f,
                    dy1 = -0.73f,
                )
                // c 0 -2.92 -1.95 -5.18 -4.85 -5.18
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = -2.92f,
                    dx2 = -1.95f,
                    dy2 = -5.18f,
                    dx3 = -4.85f,
                    dy3 = -5.18f,
                )
                // s -5 2.26 -5 5.22
                reflectiveCurveToRelative(
                    dx1 = -5.0f,
                    dy1 = 2.26f,
                    dx2 = -5.0f,
                    dy2 = 5.22f,
                )
                // m 2.4 -1.02
                moveToRelative(dx = 2.4f, dy = -1.02f)
                // c 0.3 -1.28 1.2 -2.16 2.61 -2.16
                curveToRelative(
                    dx1 = 0.3f,
                    dy1 = -1.28f,
                    dx2 = 1.2f,
                    dy2 = -2.16f,
                    dx3 = 2.61f,
                    dy3 = -2.16f,
                )
                // c 1.4 0 2.32 0.88 2.6 2.16z
                curveToRelative(
                    dx1 = 1.4f,
                    dy1 = 0.0f,
                    dx2 = 2.32f,
                    dy2 = 0.88f,
                    dx3 = 2.6f,
                    dy3 = 2.16f,
                )
                close()
                // m 8.6 5.93
                moveToRelative(dx = 8.6f, dy = 5.93f)
                // h 2.25
                horizontalLineToRelative(dx = 2.25f)
                // V 0
                verticalLineTo(y = 0.0f)
                // H 75.7z
                horizontalLineTo(x = 75.7f)
                close()
                // m 3.4 -4.95
                moveToRelative(dx = 3.4f, dy = -4.95f)
                // c 0 2.95 2.3 5.27 5.21 5.27
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = 2.95f,
                    dx2 = 2.3f,
                    dy2 = 5.27f,
                    dx3 = 5.21f,
                    dy3 = 5.27f,
                )
                // s 5.22 -2.32 5.22 -5.27
                reflectiveCurveToRelative(
                    dx1 = 5.22f,
                    dy1 = -2.32f,
                    dx2 = 5.22f,
                    dy2 = -5.27f,
                )
                // a 5.1 5.1 0 0 0 -5.22 -5.18
                arcToRelative(
                    a = 5.1f,
                    b = 5.1f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -5.22f,
                    dy1 = -5.18f,
                )
                // a 5.1 5.1 0 0 0 -5.21 5.18
                arcToRelative(
                    a = 5.1f,
                    b = 5.1f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -5.21f,
                    dy1 = 5.18f,
                )
                // m 2.27 0
                moveToRelative(dx = 2.27f, dy = 0.0f)
                // c 0 -1.77 1.29 -3.14 2.94 -3.14
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = -1.77f,
                    dx2 = 1.29f,
                    dy2 = -3.14f,
                    dx3 = 2.94f,
                    dy3 = -3.14f,
                )
                // c 1.66 0 2.94 1.37 2.94 3.14
                curveToRelative(
                    dx1 = 1.66f,
                    dy1 = 0.0f,
                    dx2 = 2.94f,
                    dy2 = 1.37f,
                    dx3 = 2.94f,
                    dy3 = 3.14f,
                )
                // s -1.3 3.22 -2.94 3.22
                reflectiveCurveToRelative(
                    dx1 = -1.3f,
                    dy1 = 3.22f,
                    dx2 = -2.94f,
                    dy2 = 3.22f,
                )
                // c -1.63 0 -2.94 -1.43 -2.94 -3.22
                curveToRelative(
                    dx1 = -1.63f,
                    dy1 = 0.0f,
                    dx2 = -2.94f,
                    dy2 = -1.43f,
                    dx3 = -2.94f,
                    dy3 = -3.22f,
                )
                // M 90.67 20
                moveTo(x = 90.67f, y = 20.0f)
                // h 2.24
                horizontalLineToRelative(dx = 2.24f)
                // v -5.46
                verticalLineToRelative(dy = -5.46f)
                // h 0.1
                horizontalLineToRelative(dx = 0.1f)
                // a 3.5 3.5 0 0 0 2.72 1.12
                arcToRelative(
                    a = 3.5f,
                    b = 3.5f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 2.72f,
                    dy1 = 1.12f,
                )
                // c 2.7 0 4.93 -2.38 4.93 -5.2
                curveToRelative(
                    dx1 = 2.7f,
                    dy1 = 0.0f,
                    dx2 = 4.93f,
                    dy2 = -2.38f,
                    dx3 = 4.93f,
                    dy3 = -5.2f,
                )
                // c 0 -2.84 -2.26 -5.25 -4.93 -5.25
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = -2.84f,
                    dx2 = -2.26f,
                    dy2 = -5.25f,
                    dx3 = -4.93f,
                    dy3 = -5.25f,
                )
                // a 3.5 3.5 0 0 0 -2.72 1.2
                arcToRelative(
                    a = 3.5f,
                    b = 3.5f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -2.72f,
                    dy1 = 1.2f,
                )
                // h -0.1
                horizontalLineToRelative(dx = -0.1f)
                // V 5.52
                verticalLineTo(y = 5.52f)
                // h -2.24z
                horizontalLineToRelative(dx = -2.24f)
                close()
                // m 2.08 -9.55
                moveToRelative(dx = 2.08f, dy = -9.55f)
                // c 0 -1.82 1.2 -3.2 2.77 -3.2
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = -1.82f,
                    dx2 = 1.2f,
                    dy2 = -3.2f,
                    dx3 = 2.77f,
                    dy3 = -3.2f,
                )
                // c 1.58 0 2.86 1.41 2.86 3.2
                curveToRelative(
                    dx1 = 1.58f,
                    dy1 = 0.0f,
                    dx2 = 2.86f,
                    dy2 = 1.41f,
                    dx3 = 2.86f,
                    dy3 = 3.2f,
                )
                // s -1.26 3.17 -2.86 3.17
                reflectiveCurveToRelative(
                    dx1 = -1.26f,
                    dy1 = 3.17f,
                    dx2 = -2.86f,
                    dy2 = 3.17f,
                )
                // s -2.77 -1.36 -2.77 -3.17
                reflectiveCurveToRelative(
                    dx1 = -2.77f,
                    dy1 = -1.36f,
                    dx2 = -2.77f,
                    dy2 = -3.17f,
                )
                // m 8.94 -0.02
                moveToRelative(dx = 8.94f, dy = -0.02f)
                // a 5.16 5.16 0 0 0 9.46 2.9
                arcToRelative(
                    a = 5.16f,
                    b = 5.16f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 9.46f,
                    dy1 = 2.9f,
                )
                // l -1.77 -1.19
                lineToRelative(dx = -1.77f, dy = -1.19f)
                // a 3 3 0 0 1 -2.55 1.48
                arcToRelative(
                    a = 3.0f,
                    b = 3.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -2.55f,
                    dy1 = 1.48f,
                )
                // c -1.34 0 -2.49 -1.07 -2.78 -2.5
                curveToRelative(
                    dx1 = -1.34f,
                    dy1 = 0.0f,
                    dx2 = -2.49f,
                    dy2 = -1.07f,
                    dx3 = -2.78f,
                    dy3 = -2.5f,
                )
                // h 7.44
                horizontalLineToRelative(dx = 7.44f)
                // a 5 5 0 0 0 0.06 -0.73
                arcToRelative(
                    a = 5.0f,
                    b = 5.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.06f,
                    dy1 = -0.73f,
                )
                // c 0 -2.92 -1.95 -5.18 -4.85 -5.18
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = -2.92f,
                    dx2 = -1.95f,
                    dy2 = -5.18f,
                    dx3 = -4.85f,
                    dy3 = -5.18f,
                )
                // s -5 2.26 -5 5.22
                reflectiveCurveToRelative(
                    dx1 = -5.0f,
                    dy1 = 2.26f,
                    dx2 = -5.0f,
                    dy2 = 5.22f,
                )
                // m 2.4 -1.02
                moveToRelative(dx = 2.4f, dy = -1.02f)
                // c 0.29 -1.28 1.2 -2.16 2.6 -2.16
                curveToRelative(
                    dx1 = 0.29f,
                    dy1 = -1.28f,
                    dx2 = 1.2f,
                    dy2 = -2.16f,
                    dx3 = 2.6f,
                    dy3 = -2.16f,
                )
                // s 2.32 0.88 2.62 2.16z
                reflectiveCurveToRelative(
                    dx1 = 2.32f,
                    dy1 = 0.88f,
                    dx2 = 2.62f,
                    dy2 = 2.16f,
                )
                close()
                // m 8.55 5.93
                moveToRelative(dx = 8.55f, dy = 5.93f)
                // h 2.24
                horizontalLineToRelative(dx = 2.24f)
                // V 10.2
                verticalLineTo(y = 10.2f)
                // c 0 -2.05 1.07 -2.95 2.86 -2.95
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = -2.05f,
                    dx2 = 1.07f,
                    dy2 = -2.95f,
                    dx3 = 2.86f,
                    dy3 = -2.95f,
                )
                // a 3 3 0 0 1 0.81 0.08
                arcToRelative(
                    a = 3.0f,
                    b = 3.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.81f,
                    dy1 = 0.08f,
                )
                // V 5.34
                verticalLineTo(y = 5.34f)
                // a 4 4 0 0 0 -1.09 -0.13
                arcToRelative(
                    a = 4.0f,
                    b = 4.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -1.09f,
                    dy1 = -0.13f,
                )
                // c -3.03 0 -4.82 1.92 -4.82 4.99z
                curveToRelative(
                    dx1 = -3.03f,
                    dy1 = 0.0f,
                    dx2 = -4.82f,
                    dy2 = 1.92f,
                    dx3 = -4.82f,
                    dy3 = 4.99f,
                )
                close()
                // m 6.3 -2.63
                moveToRelative(dx = 6.3f, dy = -2.63f)
                // c 0.18 1.71 1.95 2.95 4.2 2.95
                curveToRelative(
                    dx1 = 0.18f,
                    dy1 = 1.71f,
                    dx2 = 1.95f,
                    dy2 = 2.95f,
                    dx3 = 4.2f,
                    dy3 = 2.95f,
                )
                // c 2.35 0 3.87 -1.22 3.87 -3.13
                curveToRelative(
                    dx1 = 2.35f,
                    dy1 = 0.0f,
                    dx2 = 3.87f,
                    dy2 = -1.22f,
                    dx3 = 3.87f,
                    dy3 = -3.13f,
                )
                // c 0 -1.59 -0.92 -2.5 -2.96 -2.9
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = -1.59f,
                    dx2 = -0.92f,
                    dy2 = -2.5f,
                    dx3 = -2.96f,
                    dy3 = -2.9f,
                )
                // l -1.37 -0.28
                lineToRelative(dx = -1.37f, dy = -0.28f)
                // q -1.23 -0.24 -1.24 -1.16
                quadToRelative(
                    dx1 = -1.23f,
                    dy1 = -0.24f,
                    dx2 = -1.24f,
                    dy2 = -1.16f,
                )
                // c 0 -0.76 0.6 -1.25 1.55 -1.25
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = -0.76f,
                    dx2 = 0.6f,
                    dy2 = -1.25f,
                    dx3 = 1.55f,
                    dy3 = -1.25f,
                )
                // c 1.08 0 1.78 0.53 1.93 1.5
                curveToRelative(
                    dx1 = 1.08f,
                    dy1 = 0.0f,
                    dx2 = 1.78f,
                    dy2 = 0.53f,
                    dx3 = 1.93f,
                    dy3 = 1.5f,
                )
                // l 2 -0.46
                lineToRelative(dx = 2.0f, dy = -0.46f)
                // C 126.65 6.3 125.07 5.2 123 5.2
                curveTo(
                    x1 = 126.65f,
                    y1 = 6.3f,
                    x2 = 125.07f,
                    y2 = 5.2f,
                    x3 = 123.0f,
                    y3 = 5.2f,
                )
                // c -2.23 0 -3.75 1.23 -3.75 3.08
                curveToRelative(
                    dx1 = -2.23f,
                    dy1 = 0.0f,
                    dx2 = -3.75f,
                    dy2 = 1.23f,
                    dx3 = -3.75f,
                    dy3 = 3.08f,
                )
                // c 0 1.51 1.03 2.6 2.86 2.97
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = 1.51f,
                    dx2 = 1.03f,
                    dy2 = 2.6f,
                    dx3 = 2.86f,
                    dy3 = 2.97f,
                )
                // l 1.36 0.27
                lineToRelative(dx = 1.36f, dy = 0.27f)
                // c 0.94 0.2 1.37 0.57 1.37 1.22
                curveToRelative(
                    dx1 = 0.94f,
                    dy1 = 0.2f,
                    dx2 = 1.37f,
                    dy2 = 0.57f,
                    dx3 = 1.37f,
                    dy3 = 1.22f,
                )
                // c 0 0.73 -0.65 1.2 -1.7 1.2
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = 0.73f,
                    dx2 = -0.65f,
                    dy2 = 1.2f,
                    dx3 = -1.7f,
                    dy3 = 1.2f,
                )
                // c -1.2 0 -1.98 -0.63 -2.18 -1.7z
                curveToRelative(
                    dx1 = -1.2f,
                    dy1 = 0.0f,
                    dx2 = -1.98f,
                    dy2 = -0.63f,
                    dx3 = -2.18f,
                    dy3 = -1.7f,
                )
                close()
            }
        }.build().also { _androidDevelopers = it }
    }

@Suppress("ObjectPropertyName")
private var _androidDevelopers: ImageVector? = null
