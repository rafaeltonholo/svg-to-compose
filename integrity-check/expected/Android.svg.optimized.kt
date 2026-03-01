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

val Android: ImageVector
    get() {
        val current = _android
        if (current != null) return current

        return ImageVector.Builder(
            name = ".Android",
            defaultWidth = 95.0.dp,
            defaultHeight = 95.0.dp,
            viewportWidth = 95.0f,
            viewportHeight = 95.0f,
        ).apply {
            group(
                // M85.12 64.8 a39 39 0 0 0 -3.26 -8.65 A39 39 0 0 0 72.8 44.7 a39 39 0 0 0 -4.82 -3.54 l.05 -.08 2.33 -4.02 2.28 -3.93 1.64 -2.82 A3.54 3.54 0 0 0 73 25.5 a3.6 3.6 0 0 0 -2.17 -.48 3.5 3.5 0 0 0 -2.7 1.74 l-1.63 2.82 -2.28 3.93 -2.33 4.02 -.26 .44 -.35 -.13 A39 39 0 0 0 47.5 35.3 h-.4 a39 39 0 0 0 -12.33 2.14 l-1.33 .5 -.24 -.41 -2.33 -4.03 -3.92 -6.75 a3.5 3.5 0 0 0 -3.36 -1.74 3.5 3.5 0 0 0 -1.5 .48 3.5 3.5 0 0 0 -1.62 2.13 3.5 3.5 0 0 0 .36 2.68 l1.64 2.83 2.28 3.92 2.33 4.03 .02 .03 a39 39 0 0 0 -11.15 10.45 38 38 0 0 0 -2.8 4.58 39 39 0 0 0 -3.27 8.65 c-.65 2.65 1.5 5.02 4.23 5.02 h66.78 c2.73 0 4.88 -2.38 4.23 -5.03Z
                clipPathData = PathData {
                    // M 85.12 64.8
                    moveTo(x = 85.12f, y = 64.8f)
                    // a 39 39 0 0 0 -3.26 -8.65
                    arcToRelative(
                        a = 39.0f,
                        b = 39.0f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -3.26f,
                        dy1 = -8.65f,
                    )
                    // A 39 39 0 0 0 72.8 44.7
                    arcTo(
                        horizontalEllipseRadius = 39.0f,
                        verticalEllipseRadius = 39.0f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        x1 = 72.8f,
                        y1 = 44.7f,
                    )
                    // a 39 39 0 0 0 -4.82 -3.54
                    arcToRelative(
                        a = 39.0f,
                        b = 39.0f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -4.82f,
                        dy1 = -3.54f,
                    )
                    // l 0.05 -0.08
                    lineToRelative(dx = 0.05f, dy = -0.08f)
                    // l 2.33 -4.02
                    lineToRelative(dx = 2.33f, dy = -4.02f)
                    // l 2.28 -3.93
                    lineToRelative(dx = 2.28f, dy = -3.93f)
                    // l 1.64 -2.82
                    lineToRelative(dx = 1.64f, dy = -2.82f)
                    // A 3.54 3.54 0 0 0 73 25.5
                    arcTo(
                        horizontalEllipseRadius = 3.54f,
                        verticalEllipseRadius = 3.54f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        x1 = 73.0f,
                        y1 = 25.5f,
                    )
                    // a 3.6 3.6 0 0 0 -2.17 -0.48
                    arcToRelative(
                        a = 3.6f,
                        b = 3.6f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -2.17f,
                        dy1 = -0.48f,
                    )
                    // a 3.5 3.5 0 0 0 -2.7 1.74
                    arcToRelative(
                        a = 3.5f,
                        b = 3.5f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -2.7f,
                        dy1 = 1.74f,
                    )
                    // l -1.63 2.82
                    lineToRelative(dx = -1.63f, dy = 2.82f)
                    // l -2.28 3.93
                    lineToRelative(dx = -2.28f, dy = 3.93f)
                    // l -2.33 4.02
                    lineToRelative(dx = -2.33f, dy = 4.02f)
                    // l -0.26 0.44
                    lineToRelative(dx = -0.26f, dy = 0.44f)
                    // l -0.35 -0.13
                    lineToRelative(dx = -0.35f, dy = -0.13f)
                    // A 39 39 0 0 0 47.5 35.3
                    arcTo(
                        horizontalEllipseRadius = 39.0f,
                        verticalEllipseRadius = 39.0f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        x1 = 47.5f,
                        y1 = 35.3f,
                    )
                    // h -0.4
                    horizontalLineToRelative(dx = -0.4f)
                    // a 39 39 0 0 0 -12.33 2.14
                    arcToRelative(
                        a = 39.0f,
                        b = 39.0f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -12.33f,
                        dy1 = 2.14f,
                    )
                    // l -1.33 0.5
                    lineToRelative(dx = -1.33f, dy = 0.5f)
                    // l -0.24 -0.41
                    lineToRelative(dx = -0.24f, dy = -0.41f)
                    // l -2.33 -4.03
                    lineToRelative(dx = -2.33f, dy = -4.03f)
                    // l -3.92 -6.75
                    lineToRelative(dx = -3.92f, dy = -6.75f)
                    // a 3.5 3.5 0 0 0 -3.36 -1.74
                    arcToRelative(
                        a = 3.5f,
                        b = 3.5f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -3.36f,
                        dy1 = -1.74f,
                    )
                    // a 3.5 3.5 0 0 0 -1.5 0.48
                    arcToRelative(
                        a = 3.5f,
                        b = 3.5f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -1.5f,
                        dy1 = 0.48f,
                    )
                    // a 3.5 3.5 0 0 0 -1.62 2.13
                    arcToRelative(
                        a = 3.5f,
                        b = 3.5f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -1.62f,
                        dy1 = 2.13f,
                    )
                    // a 3.5 3.5 0 0 0 0.36 2.68
                    arcToRelative(
                        a = 3.5f,
                        b = 3.5f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = 0.36f,
                        dy1 = 2.68f,
                    )
                    // l 1.64 2.83
                    lineToRelative(dx = 1.64f, dy = 2.83f)
                    // l 2.28 3.92
                    lineToRelative(dx = 2.28f, dy = 3.92f)
                    // l 2.33 4.03
                    lineToRelative(dx = 2.33f, dy = 4.03f)
                    // l 0.02 0.03
                    lineToRelative(dx = 0.02f, dy = 0.03f)
                    // a 39 39 0 0 0 -11.15 10.45
                    arcToRelative(
                        a = 39.0f,
                        b = 39.0f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -11.15f,
                        dy1 = 10.45f,
                    )
                    // a 38 38 0 0 0 -2.8 4.58
                    arcToRelative(
                        a = 38.0f,
                        b = 38.0f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -2.8f,
                        dy1 = 4.58f,
                    )
                    // a 39 39 0 0 0 -3.27 8.65
                    arcToRelative(
                        a = 39.0f,
                        b = 39.0f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -3.27f,
                        dy1 = 8.65f,
                    )
                    // c -0.65 2.65 1.5 5.02 4.23 5.02
                    curveToRelative(
                        dx1 = -0.65f,
                        dy1 = 2.65f,
                        dx2 = 1.5f,
                        dy2 = 5.02f,
                        dx3 = 4.23f,
                        dy3 = 5.02f,
                    )
                    // h 66.78
                    horizontalLineToRelative(dx = 66.78f)
                    // c 2.73 0 4.88 -2.38 4.23 -5.03z
                    curveToRelative(
                        dx1 = 2.73f,
                        dy1 = 0.0f,
                        dx2 = 4.88f,
                        dy2 = -2.38f,
                        dx3 = 4.23f,
                        dy3 = -5.03f,
                    )
                    close()
                },
            ) {
                // M86.42 23.58 H4.22 v46.24 h82.2z
                path(
                    fill = Brush.radialGradient(
                        0.307f to Color(0xFF4FAF53),
                        1.0f to Color(0xFF118016),
                        1.0f to Color(0x004FAF53),
                        center = Offset.Zero,
                        radius = 1.0f,
                    ),
                ) {
                    // M 86.42 23.58
                    moveTo(x = 86.42f, y = 23.58f)
                    // H 4.22
                    horizontalLineTo(x = 4.22f)
                    // v 46.24
                    verticalLineToRelative(dy = 46.24f)
                    // h 82.2z
                    horizontalLineToRelative(dx = 82.2f)
                    close()
                }
                // M50.32 38.42 c8.14 5.75 -14.79 6.38 -23.64 18.93 s-1.76 34.37 -9.9 28.63 c-5.04 -6.9 -5.8 -20.9 3.06 -33.45 S43.4 35.85 50.32 38.42
                path(
                    fill = Brush.linearGradient(
                        0.0f to Color(0xFFA8F0B9),
                        1.0f to Color(0x00ADEEBC),
                        start = Offset(x = 19.398f, y = 52.22f),
                        end = Offset(x = 30.779f, y = 60.247f),
                    ),
                    fillAlpha = 0.4f,
                    strokeAlpha = 0.8f,
                ) {
                    // M 50.32 38.42
                    moveTo(x = 50.32f, y = 38.42f)
                    // c 8.14 5.75 -14.79 6.38 -23.64 18.93
                    curveToRelative(
                        dx1 = 8.14f,
                        dy1 = 5.75f,
                        dx2 = -14.79f,
                        dy2 = 6.38f,
                        dx3 = -23.64f,
                        dy3 = 18.93f,
                    )
                    // s -1.76 34.37 -9.9 28.63
                    reflectiveCurveToRelative(
                        dx1 = -1.76f,
                        dy1 = 34.37f,
                        dx2 = -9.9f,
                        dy2 = 28.63f,
                    )
                    // c -5.04 -6.9 -5.8 -20.9 3.06 -33.45
                    curveToRelative(
                        dx1 = -5.04f,
                        dy1 = -6.9f,
                        dx2 = -5.8f,
                        dy2 = -20.9f,
                        dx3 = 3.06f,
                        dy3 = -33.45f,
                    )
                    // S 43.4 35.85 50.32 38.42
                    reflectiveCurveTo(
                        x1 = 43.4f,
                        y1 = 35.85f,
                        x2 = 50.32f,
                        y2 = 38.42f,
                    )
                }
                // M47.86 38.65 c-7.46 5.4 13.52 5.99 21.63 17.77 s1.6 32.25 9.06 26.86 c4.6 -6.47 5.3 -19.6 -2.8 -31.39 s-21.57 -15.65 -27.9 -13.24
                path(
                    fill = Brush.linearGradient(
                        0.0f to Color(0xFFA8F0B9),
                        1.0f to Color(0x00ADEEBC),
                        start = Offset(x = 74.868f, y = 53.06f),
                        end = Offset(x = 65.599f, y = 58.921f),
                    ),
                    fillAlpha = 0.4f,
                    strokeAlpha = 0.7f,
                ) {
                    // M 47.86 38.65
                    moveTo(x = 47.86f, y = 38.65f)
                    // c -7.46 5.4 13.52 5.99 21.63 17.77
                    curveToRelative(
                        dx1 = -7.46f,
                        dy1 = 5.4f,
                        dx2 = 13.52f,
                        dy2 = 5.99f,
                        dx3 = 21.63f,
                        dy3 = 17.77f,
                    )
                    // s 1.6 32.25 9.06 26.86
                    reflectiveCurveToRelative(
                        dx1 = 1.6f,
                        dy1 = 32.25f,
                        dx2 = 9.06f,
                        dy2 = 26.86f,
                    )
                    // c 4.6 -6.47 5.3 -19.6 -2.8 -31.39
                    curveToRelative(
                        dx1 = 4.6f,
                        dy1 = -6.47f,
                        dx2 = 5.3f,
                        dy2 = -19.6f,
                        dx3 = -2.8f,
                        dy3 = -31.39f,
                    )
                    // s -21.57 -15.65 -27.9 -13.24
                    reflectiveCurveToRelative(
                        dx1 = -21.57f,
                        dy1 = -15.65f,
                        dx2 = -27.9f,
                        dy2 = -13.24f,
                    )
                }
                // M7.96 58.97 c-3.13 9.85 -4.74 18.12 -3.6 18.48 1.14 .37 4.6 -7.32 7.73 -17.16 S28.13 39.7 27 39.35 s-15.9 9.78 -19.03 19.62
                path(
                    fill = SolidColor(Color(0xFF8BD8A0)),
                    fillAlpha = 0.6f,
                    strokeAlpha = 0.6f,
                ) {
                    // M 7.96 58.97
                    moveTo(x = 7.96f, y = 58.97f)
                    // c -3.13 9.85 -4.74 18.12 -3.6 18.48
                    curveToRelative(
                        dx1 = -3.13f,
                        dy1 = 9.85f,
                        dx2 = -4.74f,
                        dy2 = 18.12f,
                        dx3 = -3.6f,
                        dy3 = 18.48f,
                    )
                    // c 1.14 0.37 4.6 -7.32 7.73 -17.16
                    curveToRelative(
                        dx1 = 1.14f,
                        dy1 = 0.37f,
                        dx2 = 4.6f,
                        dy2 = -7.32f,
                        dx3 = 7.73f,
                        dy3 = -17.16f,
                    )
                    // S 28.13 39.7 27 39.35
                    reflectiveCurveTo(
                        x1 = 28.13f,
                        y1 = 39.7f,
                        x2 = 27.0f,
                        y2 = 39.35f,
                    )
                    // s -15.9 9.78 -19.03 19.62
                    reflectiveCurveToRelative(
                        dx1 = -15.9f,
                        dy1 = 9.78f,
                        dx2 = -19.03f,
                        dy2 = 19.62f,
                    )
                }
                // M86.59 58.97 c3.13 9.85 4.74 18.12 3.6 18.48 -1.15 .37 -4.6 -7.32 -7.74 -17.16 -3.13 -9.85 -16.03 -20.58 -14.9 -20.94 1.15 -.37 15.9 9.78 19.04 19.62
                path(
                    fill = SolidColor(Color(0xFF8BD8A0)),
                    fillAlpha = 0.5f,
                    strokeAlpha = 0.5f,
                ) {
                    // M 86.59 58.97
                    moveTo(x = 86.59f, y = 58.97f)
                    // c 3.13 9.85 4.74 18.12 3.6 18.48
                    curveToRelative(
                        dx1 = 3.13f,
                        dy1 = 9.85f,
                        dx2 = 4.74f,
                        dy2 = 18.12f,
                        dx3 = 3.6f,
                        dy3 = 18.48f,
                    )
                    // c -1.15 0.37 -4.6 -7.32 -7.74 -17.16
                    curveToRelative(
                        dx1 = -1.15f,
                        dy1 = 0.37f,
                        dx2 = -4.6f,
                        dy2 = -7.32f,
                        dx3 = -7.74f,
                        dy3 = -17.16f,
                    )
                    // c -3.13 -9.85 -16.03 -20.58 -14.9 -20.94
                    curveToRelative(
                        dx1 = -3.13f,
                        dy1 = -9.85f,
                        dx2 = -16.03f,
                        dy2 = -20.58f,
                        dx3 = -14.9f,
                        dy3 = -20.94f,
                    )
                    // c 1.15 -0.37 15.9 9.78 19.04 19.62
                    curveToRelative(
                        dx1 = 1.15f,
                        dy1 = -0.37f,
                        dx2 = 15.9f,
                        dy2 = 9.78f,
                        dx3 = 19.04f,
                        dy3 = 19.62f,
                    )
                }
                // M28.66 41.06 c.37 -.25 -1.59 -3.9 -4.37 -8.15 s-5.34 -7.52 -5.71 -7.28 1.58 3.9 4.37 8.15 c2.78 4.26 5.34 7.52 5.71 7.28Z
                path(
                    fill = SolidColor(Color(0xFF202124)),
                    fillAlpha = 0.1f,
                    strokeAlpha = 0.1f,
                ) {
                    // M 28.66 41.06
                    moveTo(x = 28.66f, y = 41.06f)
                    // c 0.37 -0.25 -1.59 -3.9 -4.37 -8.15
                    curveToRelative(
                        dx1 = 0.37f,
                        dy1 = -0.25f,
                        dx2 = -1.59f,
                        dy2 = -3.9f,
                        dx3 = -4.37f,
                        dy3 = -8.15f,
                    )
                    // s -5.34 -7.52 -5.71 -7.28
                    reflectiveCurveToRelative(
                        dx1 = -5.34f,
                        dy1 = -7.52f,
                        dx2 = -5.71f,
                        dy2 = -7.28f,
                    )
                    // s 1.58 3.9 4.37 8.15
                    reflectiveCurveToRelative(
                        dx1 = 1.58f,
                        dy1 = 3.9f,
                        dx2 = 4.37f,
                        dy2 = 8.15f,
                    )
                    // c 2.78 4.26 5.34 7.52 5.71 7.28z
                    curveToRelative(
                        dx1 = 2.78f,
                        dy1 = 4.26f,
                        dx2 = 5.34f,
                        dy2 = 7.52f,
                        dx3 = 5.71f,
                        dy3 = 7.28f,
                    )
                    close()
                }
                // M27.5 30.13 c3.25 5.32 4.83 7.76 5.19 7.54 s-.64 -3.01 -3.88 -8.34 c-3.12 -7.1 -8.76 -2.87 -8.2 -2.09 1.08 2.09 2.75 -4.58 6.9 2.89
                path(
                    fill = SolidColor(Color(0xFFFFFFFF)),
                    fillAlpha = 0.3f,
                    strokeAlpha = 0.3f,
                ) {
                    // M 27.5 30.13
                    moveTo(x = 27.5f, y = 30.13f)
                    // c 3.25 5.32 4.83 7.76 5.19 7.54
                    curveToRelative(
                        dx1 = 3.25f,
                        dy1 = 5.32f,
                        dx2 = 4.83f,
                        dy2 = 7.76f,
                        dx3 = 5.19f,
                        dy3 = 7.54f,
                    )
                    // s -0.64 -3.01 -3.88 -8.34
                    reflectiveCurveToRelative(
                        dx1 = -0.64f,
                        dy1 = -3.01f,
                        dx2 = -3.88f,
                        dy2 = -8.34f,
                    )
                    // c -3.12 -7.1 -8.76 -2.87 -8.2 -2.09
                    curveToRelative(
                        dx1 = -3.12f,
                        dy1 = -7.1f,
                        dx2 = -8.76f,
                        dy2 = -2.87f,
                        dx3 = -8.2f,
                        dy3 = -2.09f,
                    )
                    // c 1.08 2.09 2.75 -4.58 6.9 2.89
                    curveToRelative(
                        dx1 = 1.08f,
                        dy1 = 2.09f,
                        dx2 = 2.75f,
                        dy2 = -4.58f,
                        dx3 = 6.9f,
                        dy3 = 2.89f,
                    )
                }
                // M18.48 28.28 c.58 2.02 2.99 3.1 5.36 2.42 2.38 -.7 3.83 -2.89 3.25 -4.9 -.59 -2.03 -2.99 -3.1 -5.37 -2.42 s-3.83 2.88 -3.24 4.9Z
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
                    // M 18.48 28.28
                    moveTo(x = 18.48f, y = 28.28f)
                    // c 0.58 2.02 2.99 3.1 5.36 2.42
                    curveToRelative(
                        dx1 = 0.58f,
                        dy1 = 2.02f,
                        dx2 = 2.99f,
                        dy2 = 3.1f,
                        dx3 = 5.36f,
                        dy3 = 2.42f,
                    )
                    // c 2.38 -0.7 3.83 -2.89 3.25 -4.9
                    curveToRelative(
                        dx1 = 2.38f,
                        dy1 = -0.7f,
                        dx2 = 3.83f,
                        dy2 = -2.89f,
                        dx3 = 3.25f,
                        dy3 = -4.9f,
                    )
                    // c -0.59 -2.03 -2.99 -3.1 -5.37 -2.42
                    curveToRelative(
                        dx1 = -0.59f,
                        dy1 = -2.03f,
                        dx2 = -2.99f,
                        dy2 = -3.1f,
                        dx3 = -5.37f,
                        dy3 = -2.42f,
                    )
                    // s -3.83 2.88 -3.24 4.9z
                    reflectiveCurveToRelative(
                        dx1 = -3.83f,
                        dy1 = 2.88f,
                        dx2 = -3.24f,
                        dy2 = 4.9f,
                    )
                    close()
                }
                // M67.64 28.5 c.59 2.03 3 3.1 5.37 2.42 s3.83 -2.88 3.24 -4.9 -2.98 -3.1 -5.36 -2.42 c-2.38 .7 -3.83 2.89 -3.25 4.9
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
                    // M 67.64 28.5
                    moveTo(x = 67.64f, y = 28.5f)
                    // c 0.59 2.03 3 3.1 5.37 2.42
                    curveToRelative(
                        dx1 = 0.59f,
                        dy1 = 2.03f,
                        dx2 = 3.0f,
                        dy2 = 3.1f,
                        dx3 = 5.37f,
                        dy3 = 2.42f,
                    )
                    // s 3.83 -2.88 3.24 -4.9
                    reflectiveCurveToRelative(
                        dx1 = 3.83f,
                        dy1 = -2.88f,
                        dx2 = 3.24f,
                        dy2 = -4.9f,
                    )
                    // s -2.98 -3.1 -5.36 -2.42
                    reflectiveCurveToRelative(
                        dx1 = -2.98f,
                        dy1 = -3.1f,
                        dx2 = -5.36f,
                        dy2 = -2.42f,
                    )
                    // c -2.38 0.7 -3.83 2.89 -3.25 4.9
                    curveToRelative(
                        dx1 = -2.38f,
                        dy1 = 0.7f,
                        dx2 = -3.83f,
                        dy2 = 2.89f,
                        dx3 = -3.25f,
                        dy3 = 4.9f,
                    )
                }
                // M66.12 41.32 c-.38 -.24 1.52 -3.92 4.24 -8.22 2.71 -4.3 5.22 -7.6 5.59 -7.36 s-1.52 3.91 -4.24 8.22 c-2.71 4.3 -5.22 7.6 -5.6 7.36
                path(
                    fill = SolidColor(Color(0xFF202124)),
                    fillAlpha = 0.1f,
                    strokeAlpha = 0.1f,
                ) {
                    // M 66.12 41.32
                    moveTo(x = 66.12f, y = 41.32f)
                    // c -0.38 -0.24 1.52 -3.92 4.24 -8.22
                    curveToRelative(
                        dx1 = -0.38f,
                        dy1 = -0.24f,
                        dx2 = 1.52f,
                        dy2 = -3.92f,
                        dx3 = 4.24f,
                        dy3 = -8.22f,
                    )
                    // c 2.71 -4.3 5.22 -7.6 5.59 -7.36
                    curveToRelative(
                        dx1 = 2.71f,
                        dy1 = -4.3f,
                        dx2 = 5.22f,
                        dy2 = -7.6f,
                        dx3 = 5.59f,
                        dy3 = -7.36f,
                    )
                    // s -1.52 3.91 -4.24 8.22
                    reflectiveCurveToRelative(
                        dx1 = -1.52f,
                        dy1 = 3.91f,
                        dx2 = -4.24f,
                        dy2 = 8.22f,
                    )
                    // c -2.71 4.3 -5.22 7.6 -5.6 7.36
                    curveToRelative(
                        dx1 = -2.71f,
                        dy1 = 4.3f,
                        dx2 = -5.22f,
                        dy2 = 7.6f,
                        dx3 = -5.6f,
                        dy3 = 7.36f,
                    )
                }
                // M62.87 38.13 c-.91 -.45 -1.72 -.67 -1.8 -.49 -.1 .18 .59 .56 1.5 1 .9 .45 1.7 .8 1.79 .62 .08 -.18 -.58 -.69 -1.5 -1.13
                path(
                    fill = SolidColor(Color(0xFF81C995)),
                    fillAlpha = 0.3f,
                    strokeAlpha = 0.3f,
                ) {
                    // M 62.87 38.13
                    moveTo(x = 62.87f, y = 38.13f)
                    // c -0.91 -0.45 -1.72 -0.67 -1.8 -0.49
                    curveToRelative(
                        dx1 = -0.91f,
                        dy1 = -0.45f,
                        dx2 = -1.72f,
                        dy2 = -0.67f,
                        dx3 = -1.8f,
                        dy3 = -0.49f,
                    )
                    // c -0.1 0.18 0.59 0.56 1.5 1
                    curveToRelative(
                        dx1 = -0.1f,
                        dy1 = 0.18f,
                        dx2 = 0.59f,
                        dy2 = 0.56f,
                        dx3 = 1.5f,
                        dy3 = 1.0f,
                    )
                    // c 0.9 0.45 1.7 0.8 1.79 0.62
                    curveToRelative(
                        dx1 = 0.9f,
                        dy1 = 0.45f,
                        dx2 = 1.7f,
                        dy2 = 0.8f,
                        dx3 = 1.79f,
                        dy3 = 0.62f,
                    )
                    // c 0.08 -0.18 -0.58 -0.69 -1.5 -1.13
                    curveToRelative(
                        dx1 = 0.08f,
                        dy1 = -0.18f,
                        dx2 = -0.58f,
                        dy2 = -0.69f,
                        dx3 = -1.5f,
                        dy3 = -1.13f,
                    )
                }
                // M31.64 38.37 c.9 -.46 1.7 -.7 1.79 -.52 s-.58 .57 -1.48 1.03 -1.69 .83 -1.78 .65 .57 -.7 1.47 -1.16Z
                path(
                    fill = SolidColor(Color(0xFF81C995)),
                    fillAlpha = 0.3f,
                    strokeAlpha = 0.3f,
                ) {
                    // M 31.64 38.37
                    moveTo(x = 31.64f, y = 38.37f)
                    // c 0.9 -0.46 1.7 -0.7 1.79 -0.52
                    curveToRelative(
                        dx1 = 0.9f,
                        dy1 = -0.46f,
                        dx2 = 1.7f,
                        dy2 = -0.7f,
                        dx3 = 1.79f,
                        dy3 = -0.52f,
                    )
                    // s -0.58 0.57 -1.48 1.03
                    reflectiveCurveToRelative(
                        dx1 = -0.58f,
                        dy1 = 0.57f,
                        dx2 = -1.48f,
                        dy2 = 1.03f,
                    )
                    // s -1.69 0.83 -1.78 0.65
                    reflectiveCurveToRelative(
                        dx1 = -1.69f,
                        dy1 = 0.83f,
                        dx2 = -1.78f,
                        dy2 = 0.65f,
                    )
                    // s 0.57 -0.7 1.47 -1.16z
                    reflectiveCurveToRelative(
                        dx1 = 0.57f,
                        dy1 = -0.7f,
                        dx2 = 1.47f,
                        dy2 = -1.16f,
                    )
                    close()
                }
                // M67.45 30.41 c-3.03 5.25 -4.52 7.66 -4.9 7.44 s.48 -2.99 3.51 -8.24 c3.46 -9.12 10.31 -7.92 9.19 -2.93 -.89 2.05 -3.96 -3.64 -7.8 3.73
                path(
                    fill = SolidColor(Color(0xFFFFFFFF)),
                    fillAlpha = 0.3f,
                    strokeAlpha = 0.3f,
                ) {
                    // M 67.45 30.41
                    moveTo(x = 67.45f, y = 30.41f)
                    // c -3.03 5.25 -4.52 7.66 -4.9 7.44
                    curveToRelative(
                        dx1 = -3.03f,
                        dy1 = 5.25f,
                        dx2 = -4.52f,
                        dy2 = 7.66f,
                        dx3 = -4.9f,
                        dy3 = 7.44f,
                    )
                    // s 0.48 -2.99 3.51 -8.24
                    reflectiveCurveToRelative(
                        dx1 = 0.48f,
                        dy1 = -2.99f,
                        dx2 = 3.51f,
                        dy2 = -8.24f,
                    )
                    // c 3.46 -9.12 10.31 -7.92 9.19 -2.93
                    curveToRelative(
                        dx1 = 3.46f,
                        dy1 = -9.12f,
                        dx2 = 10.31f,
                        dy2 = -7.92f,
                        dx3 = 9.19f,
                        dy3 = -2.93f,
                    )
                    // c -0.89 2.05 -3.96 -3.64 -7.8 3.73
                    curveToRelative(
                        dx1 = -0.89f,
                        dy1 = 2.05f,
                        dx2 = -3.96f,
                        dy2 = -3.64f,
                        dx3 = -7.8f,
                        dy3 = 3.73f,
                    )
                }
            }
            // M31.74 55.8 c.46 -.53 .07 -1.65 -.88 -2.5 s-2.1 -1.13 -2.57 -.6 -.07 1.65 .88 2.5 2.1 1.12 2.57 .6
            path(
                fill = Brush.radialGradient(
                    0.0f to Color(0xFF93E19F),
                    1.0f to Color(0x0093E19F),
                    center = Offset.Zero,
                    radius = 1.0f,
                ),
                fillAlpha = 0.7f,
            ) {
                // M 31.74 55.8
                moveTo(x = 31.74f, y = 55.8f)
                // c 0.46 -0.53 0.07 -1.65 -0.88 -2.5
                curveToRelative(
                    dx1 = 0.46f,
                    dy1 = -0.53f,
                    dx2 = 0.07f,
                    dy2 = -1.65f,
                    dx3 = -0.88f,
                    dy3 = -2.5f,
                )
                // s -2.1 -1.13 -2.57 -0.6
                reflectiveCurveToRelative(
                    dx1 = -2.1f,
                    dy1 = -1.13f,
                    dx2 = -2.57f,
                    dy2 = -0.6f,
                )
                // s -0.07 1.65 0.88 2.5
                reflectiveCurveToRelative(
                    dx1 = -0.07f,
                    dy1 = 1.65f,
                    dx2 = 0.88f,
                    dy2 = 2.5f,
                )
                // s 2.1 1.12 2.57 0.6
                reflectiveCurveToRelative(
                    dx1 = 2.1f,
                    dy1 = 1.12f,
                    dx2 = 2.57f,
                    dy2 = 0.6f,
                )
            }
            // M64.41 54.45 c.95 -.86 1.35 -1.98 .88 -2.5 -.47 -.53 -1.61 -.27 -2.56 .59 -.95 .85 -1.35 1.97 -.88 2.5 .46 .53 1.61 .26 2.56 -.6
            path(
                fill = Brush.radialGradient(
                    0.0f to Color(0xFF93E19F),
                    1.0f to Color(0x0093E19F),
                    center = Offset.Zero,
                    radius = 1.0f,
                ),
                fillAlpha = 0.7f,
            ) {
                // M 64.41 54.45
                moveTo(x = 64.41f, y = 54.45f)
                // c 0.95 -0.86 1.35 -1.98 0.88 -2.5
                curveToRelative(
                    dx1 = 0.95f,
                    dy1 = -0.86f,
                    dx2 = 1.35f,
                    dy2 = -1.98f,
                    dx3 = 0.88f,
                    dy3 = -2.5f,
                )
                // c -0.47 -0.53 -1.61 -0.27 -2.56 0.59
                curveToRelative(
                    dx1 = -0.47f,
                    dy1 = -0.53f,
                    dx2 = -1.61f,
                    dy2 = -0.27f,
                    dx3 = -2.56f,
                    dy3 = 0.59f,
                )
                // c -0.95 0.85 -1.35 1.97 -0.88 2.5
                curveToRelative(
                    dx1 = -0.95f,
                    dy1 = 0.85f,
                    dx2 = -1.35f,
                    dy2 = 1.97f,
                    dx3 = -0.88f,
                    dy3 = 2.5f,
                )
                // c 0.46 0.53 1.61 0.26 2.56 -0.6
                curveToRelative(
                    dx1 = 0.46f,
                    dy1 = 0.53f,
                    dx2 = 1.61f,
                    dy2 = 0.26f,
                    dx3 = 2.56f,
                    dy3 = -0.6f,
                )
            }
            // M32.36 58.5 c1.16 -2.88 -.05 -4.66 -.8 -5.21 -2.17 -2.67 -5.08 -.61 -5.94 .55 -.87 1.14 -2.13 3.17 -.9 5.96 1.24 2.8 6.2 2.3 7.64 -1.3 m29.9 .06 c-1.16 -2.85 .05 -4.65 .8 -5.18 2.16 -2.67 5.07 -.6 5.94 .56 .86 1.13 2.13 3.17 .9 5.93 -1.25 2.76 -6.2 2.3 -7.65 -1.28z
            path(
                fill = SolidColor(Color(0xFF011B04)),
                fillAlpha = 0.09f,
                strokeAlpha = 0.09f,
            ) {
                // M 32.36 58.5
                moveTo(x = 32.36f, y = 58.5f)
                // c 1.16 -2.88 -0.05 -4.66 -0.8 -5.21
                curveToRelative(
                    dx1 = 1.16f,
                    dy1 = -2.88f,
                    dx2 = -0.05f,
                    dy2 = -4.66f,
                    dx3 = -0.8f,
                    dy3 = -5.21f,
                )
                // c -2.17 -2.67 -5.08 -0.61 -5.94 0.55
                curveToRelative(
                    dx1 = -2.17f,
                    dy1 = -2.67f,
                    dx2 = -5.08f,
                    dy2 = -0.61f,
                    dx3 = -5.94f,
                    dy3 = 0.55f,
                )
                // c -0.87 1.14 -2.13 3.17 -0.9 5.96
                curveToRelative(
                    dx1 = -0.87f,
                    dy1 = 1.14f,
                    dx2 = -2.13f,
                    dy2 = 3.17f,
                    dx3 = -0.9f,
                    dy3 = 5.96f,
                )
                // c 1.24 2.8 6.2 2.3 7.64 -1.3
                curveToRelative(
                    dx1 = 1.24f,
                    dy1 = 2.8f,
                    dx2 = 6.2f,
                    dy2 = 2.3f,
                    dx3 = 7.64f,
                    dy3 = -1.3f,
                )
                // m 29.9 0.06
                moveToRelative(dx = 29.9f, dy = 0.06f)
                // c -1.16 -2.85 0.05 -4.65 0.8 -5.18
                curveToRelative(
                    dx1 = -1.16f,
                    dy1 = -2.85f,
                    dx2 = 0.05f,
                    dy2 = -4.65f,
                    dx3 = 0.8f,
                    dy3 = -5.18f,
                )
                // c 2.16 -2.67 5.07 -0.6 5.94 0.56
                curveToRelative(
                    dx1 = 2.16f,
                    dy1 = -2.67f,
                    dx2 = 5.07f,
                    dy2 = -0.6f,
                    dx3 = 5.94f,
                    dy3 = 0.56f,
                )
                // c 0.86 1.13 2.13 3.17 0.9 5.93
                curveToRelative(
                    dx1 = 0.86f,
                    dy1 = 1.13f,
                    dx2 = 2.13f,
                    dy2 = 3.17f,
                    dx3 = 0.9f,
                    dy3 = 5.93f,
                )
                // c -1.25 2.76 -6.2 2.3 -7.65 -1.28z
                curveToRelative(
                    dx1 = -1.25f,
                    dy1 = 2.76f,
                    dx2 = -6.2f,
                    dy2 = 2.3f,
                    dx3 = -7.65f,
                    dy3 = -1.28f,
                )
                close()
            }
            // M68.77 60.53 c1.79 -1.19 2.08 -3.95 .63 -6.14 -1.44 -2.18 -4.06 -2.96 -5.85 -1.77 -1.79 1.2 -2.07 3.96 -.63 6.14 s4.06 2.96 5.85 1.77
            path(
                fill = SolidColor(Color(0xFF202124)),
            ) {
                // M 68.77 60.53
                moveTo(x = 68.77f, y = 60.53f)
                // c 1.79 -1.19 2.08 -3.95 0.63 -6.14
                curveToRelative(
                    dx1 = 1.79f,
                    dy1 = -1.19f,
                    dx2 = 2.08f,
                    dy2 = -3.95f,
                    dx3 = 0.63f,
                    dy3 = -6.14f,
                )
                // c -1.44 -2.18 -4.06 -2.96 -5.85 -1.77
                curveToRelative(
                    dx1 = -1.44f,
                    dy1 = -2.18f,
                    dx2 = -4.06f,
                    dy2 = -2.96f,
                    dx3 = -5.85f,
                    dy3 = -1.77f,
                )
                // c -1.79 1.2 -2.07 3.96 -0.63 6.14
                curveToRelative(
                    dx1 = -1.79f,
                    dy1 = 1.2f,
                    dx2 = -2.07f,
                    dy2 = 3.96f,
                    dx3 = -0.63f,
                    dy3 = 6.14f,
                )
                // s 4.06 2.96 5.85 1.77
                reflectiveCurveToRelative(
                    dx1 = 4.06f,
                    dy1 = 2.96f,
                    dx2 = 5.85f,
                    dy2 = 1.77f,
                )
            }
            // M31.67 58.67 c1.44 -2.19 1.19 -4.89 -.57 -6.08 -1.76 -1.17 -4.33 -.35 -5.77 1.8 -1.44 2.18 -1.18 4.89 .58 6.08 1.76 1.16 4.32 .35 5.76 -1.8 m31.25 .03 c-1.44 -2.18 -1.18 -4.89 .58 -6.08 1.76 -1.16 4.32 -.35 5.76 1.8 1.44 2.19 1.19 4.9 -.57 6.09 -1.76 1.16 -4.33 .34 -5.77 -1.8
            path(
                fill = SolidColor(Color(0xFF000000)),
            ) {
                // M 31.67 58.67
                moveTo(x = 31.67f, y = 58.67f)
                // c 1.44 -2.19 1.19 -4.89 -0.57 -6.08
                curveToRelative(
                    dx1 = 1.44f,
                    dy1 = -2.19f,
                    dx2 = 1.19f,
                    dy2 = -4.89f,
                    dx3 = -0.57f,
                    dy3 = -6.08f,
                )
                // c -1.76 -1.17 -4.33 -0.35 -5.77 1.8
                curveToRelative(
                    dx1 = -1.76f,
                    dy1 = -1.17f,
                    dx2 = -4.33f,
                    dy2 = -0.35f,
                    dx3 = -5.77f,
                    dy3 = 1.8f,
                )
                // c -1.44 2.18 -1.18 4.89 0.58 6.08
                curveToRelative(
                    dx1 = -1.44f,
                    dy1 = 2.18f,
                    dx2 = -1.18f,
                    dy2 = 4.89f,
                    dx3 = 0.58f,
                    dy3 = 6.08f,
                )
                // c 1.76 1.16 4.32 0.35 5.76 -1.8
                curveToRelative(
                    dx1 = 1.76f,
                    dy1 = 1.16f,
                    dx2 = 4.32f,
                    dy2 = 0.35f,
                    dx3 = 5.76f,
                    dy3 = -1.8f,
                )
                // m 31.25 0.03
                moveToRelative(dx = 31.25f, dy = 0.03f)
                // c -1.44 -2.18 -1.18 -4.89 0.58 -6.08
                curveToRelative(
                    dx1 = -1.44f,
                    dy1 = -2.18f,
                    dx2 = -1.18f,
                    dy2 = -4.89f,
                    dx3 = 0.58f,
                    dy3 = -6.08f,
                )
                // c 1.76 -1.16 4.32 -0.35 5.76 1.8
                curveToRelative(
                    dx1 = 1.76f,
                    dy1 = -1.16f,
                    dx2 = 4.32f,
                    dy2 = -0.35f,
                    dx3 = 5.76f,
                    dy3 = 1.8f,
                )
                // c 1.44 2.19 1.19 4.9 -0.57 6.09
                curveToRelative(
                    dx1 = 1.44f,
                    dy1 = 2.19f,
                    dx2 = 1.19f,
                    dy2 = 4.9f,
                    dx3 = -0.57f,
                    dy3 = 6.09f,
                )
                // c -1.76 1.16 -4.33 0.34 -5.77 -1.8
                curveToRelative(
                    dx1 = -1.76f,
                    dy1 = 1.16f,
                    dx2 = -4.33f,
                    dy2 = 0.34f,
                    dx3 = -5.77f,
                    dy3 = -1.8f,
                )
            }
            // M32.18 54.41 c-.83 -2.34 -2.78 -2.36 -3.64 -2.1 .73 -.42 2.15 -.14 2.83 .5 a3.4 3.4 0 0 1 .84 1.63z
            path(
                fill = Brush.linearGradient(
                    0.0f to Color(0xFFE2DDE2),
                    1.0f to Color(0x00E2DDE2),
                    start = Offset(x = 30.031f, y = 51.131f),
                    end = Offset(x = 30.54f, y = 54.499f),
                ),
                fillAlpha = 0.8f,
                strokeAlpha = 0.8f,
            ) {
                // M 32.18 54.41
                moveTo(x = 32.18f, y = 54.41f)
                // c -0.83 -2.34 -2.78 -2.36 -3.64 -2.1
                curveToRelative(
                    dx1 = -0.83f,
                    dy1 = -2.34f,
                    dx2 = -2.78f,
                    dy2 = -2.36f,
                    dx3 = -3.64f,
                    dy3 = -2.1f,
                )
                // c 0.73 -0.42 2.15 -0.14 2.83 0.5
                curveToRelative(
                    dx1 = 0.73f,
                    dy1 = -0.42f,
                    dx2 = 2.15f,
                    dy2 = -0.14f,
                    dx3 = 2.83f,
                    dy3 = 0.5f,
                )
                // a 3.4 3.4 0 0 1 0.84 1.63z
                arcToRelative(
                    a = 3.4f,
                    b = 3.4f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.84f,
                    dy1 = 1.63f,
                )
                close()
            }
            // M65.17 52.5 a2.9 2.9 0 0 0 -2.8 2.54 3.2 3.2 0 0 1 2.26 -1.78 c1.3 -.23 3 1.26 3.69 2.04 l.63 -.73 a4.1 4.1 0 0 0 -3.78 -2.06
            path(
                fill = Brush.linearGradient(
                    0.0f to Color(0xFF373637),
                    1.0f to Color(0x00373637),
                    start = Offset(x = 65.663f, y = 52.508f),
                    end = Offset(x = 65.663f, y = 55.3f),
                ),
            ) {
                // M 65.17 52.5
                moveTo(x = 65.17f, y = 52.5f)
                // a 2.9 2.9 0 0 0 -2.8 2.54
                arcToRelative(
                    a = 2.9f,
                    b = 2.9f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -2.8f,
                    dy1 = 2.54f,
                )
                // a 3.2 3.2 0 0 1 2.26 -1.78
                arcToRelative(
                    a = 3.2f,
                    b = 3.2f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 2.26f,
                    dy1 = -1.78f,
                )
                // c 1.3 -0.23 3 1.26 3.69 2.04
                curveToRelative(
                    dx1 = 1.3f,
                    dy1 = -0.23f,
                    dx2 = 3.0f,
                    dy2 = 1.26f,
                    dx3 = 3.69f,
                    dy3 = 2.04f,
                )
                // l 0.63 -0.73
                lineToRelative(dx = 0.63f, dy = -0.73f)
                // a 4.1 4.1 0 0 0 -3.78 -2.06
                arcToRelative(
                    a = 4.1f,
                    b = 4.1f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -3.78f,
                    dy1 = -2.06f,
                )
            }
            // M68.74 54.2 a6 6 0 0 1 1.36 4.53 5.4 5.4 0 0 0 -1.1 -4.89 5.3 5.3 0 0 0 -2.65 -1.63 c-.11 -.02 -.26 -.05 -.34 -.08 a2 2 0 0 0 -.5 -.09 l.5 .09 c.11 0 .23 .06 .34 .08 .58 .2 1.36 .64 2.42 1.98z
            path(
                fill = SolidColor(Color(0xFF000000)),
            ) {
                // M 68.74 54.2
                moveTo(x = 68.74f, y = 54.2f)
                // a 6 6 0 0 1 1.36 4.53
                arcToRelative(
                    a = 6.0f,
                    b = 6.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 1.36f,
                    dy1 = 4.53f,
                )
                // a 5.4 5.4 0 0 0 -1.1 -4.89
                arcToRelative(
                    a = 5.4f,
                    b = 5.4f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -1.1f,
                    dy1 = -4.89f,
                )
                // a 5.3 5.3 0 0 0 -2.65 -1.63
                arcToRelative(
                    a = 5.3f,
                    b = 5.3f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -2.65f,
                    dy1 = -1.63f,
                )
                // c -0.11 -0.02 -0.26 -0.05 -0.34 -0.08
                curveToRelative(
                    dx1 = -0.11f,
                    dy1 = -0.02f,
                    dx2 = -0.26f,
                    dy2 = -0.05f,
                    dx3 = -0.34f,
                    dy3 = -0.08f,
                )
                // a 2 2 0 0 0 -0.5 -0.09
                arcToRelative(
                    a = 2.0f,
                    b = 2.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -0.5f,
                    dy1 = -0.09f,
                )
                // l 0.5 0.09
                lineToRelative(dx = 0.5f, dy = 0.09f)
                // c 0.11 0 0.23 0.06 0.34 0.08
                curveToRelative(
                    dx1 = 0.11f,
                    dy1 = 0.0f,
                    dx2 = 0.23f,
                    dy2 = 0.06f,
                    dx3 = 0.34f,
                    dy3 = 0.08f,
                )
                // c 0.58 0.2 1.36 0.64 2.42 1.98z
                curveToRelative(
                    dx1 = 0.58f,
                    dy1 = 0.2f,
                    dx2 = 1.36f,
                    dy2 = 0.64f,
                    dx3 = 2.42f,
                    dy3 = 1.98f,
                )
                close()
            }
            // M27.66 53.5 c-.81 .18 -1.41 1.02 -1.59 1.43 l.4 .61 s.3 -.5 .61 -.81 c.32 -.32 .8 -.67 1.01 -.82 l-.4 -.4z m39.3 .02 c.78 .15 1.38 .96 1.58 1.37 l-.4 .58 s-.29 -.46 -.6 -.78 -.79 -.64 -.99 -.79z m-36.01 4.34 a23 23 0 0 1 -1.24 .61 l-.23 .7 a4 4 0 0 0 1.2 -.7z m1.2 -3.06 a5 5 0 0 0 -.23 -.6 v.25 a3 3 0 0 1 .23 .76 c0 -.06 .06 -.23 0 -.44z m31.44 3.12 c.37 .2 .98 .5 1.24 .61 l.23 .7 a4 4 0 0 1 -1.21 -.7z m-1.19 -3.05 a5 5 0 0 1 .23 -.62 v.27 a3 3 0 0 0 -.23 .75 c0 -.06 -.05 -.23 0 -.43z
            path(
                fill = SolidColor(Color(0xFFE2DCE1)),
                fillAlpha = 0.8f,
                strokeAlpha = 0.8f,
            ) {
                // M 27.66 53.5
                moveTo(x = 27.66f, y = 53.5f)
                // c -0.81 0.18 -1.41 1.02 -1.59 1.43
                curveToRelative(
                    dx1 = -0.81f,
                    dy1 = 0.18f,
                    dx2 = -1.41f,
                    dy2 = 1.02f,
                    dx3 = -1.59f,
                    dy3 = 1.43f,
                )
                // l 0.4 0.61
                lineToRelative(dx = 0.4f, dy = 0.61f)
                // s 0.3 -0.5 0.61 -0.81
                reflectiveCurveToRelative(
                    dx1 = 0.3f,
                    dy1 = -0.5f,
                    dx2 = 0.61f,
                    dy2 = -0.81f,
                )
                // c 0.32 -0.32 0.8 -0.67 1.01 -0.82
                curveToRelative(
                    dx1 = 0.32f,
                    dy1 = -0.32f,
                    dx2 = 0.8f,
                    dy2 = -0.67f,
                    dx3 = 1.01f,
                    dy3 = -0.82f,
                )
                // l -0.4 -0.4z
                lineToRelative(dx = -0.4f, dy = -0.4f)
                close()
                // m 39.3 0.02
                moveToRelative(dx = 39.3f, dy = 0.02f)
                // c 0.78 0.15 1.38 0.96 1.58 1.37
                curveToRelative(
                    dx1 = 0.78f,
                    dy1 = 0.15f,
                    dx2 = 1.38f,
                    dy2 = 0.96f,
                    dx3 = 1.58f,
                    dy3 = 1.37f,
                )
                // l -0.4 0.58
                lineToRelative(dx = -0.4f, dy = 0.58f)
                // s -0.29 -0.46 -0.6 -0.78
                reflectiveCurveToRelative(
                    dx1 = -0.29f,
                    dy1 = -0.46f,
                    dx2 = -0.6f,
                    dy2 = -0.78f,
                )
                // s -0.79 -0.64 -0.99 -0.79z
                reflectiveCurveToRelative(
                    dx1 = -0.79f,
                    dy1 = -0.64f,
                    dx2 = -0.99f,
                    dy2 = -0.79f,
                )
                close()
                // m -36.01 4.34
                moveToRelative(dx = -36.01f, dy = 4.34f)
                // a 23 23 0 0 1 -1.24 0.61
                arcToRelative(
                    a = 23.0f,
                    b = 23.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.24f,
                    dy1 = 0.61f,
                )
                // l -0.23 0.7
                lineToRelative(dx = -0.23f, dy = 0.7f)
                // a 4 4 0 0 0 1.2 -0.7z
                arcToRelative(
                    a = 4.0f,
                    b = 4.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 1.2f,
                    dy1 = -0.7f,
                )
                close()
                // m 1.2 -3.06
                moveToRelative(dx = 1.2f, dy = -3.06f)
                // a 5 5 0 0 0 -0.23 -0.6
                arcToRelative(
                    a = 5.0f,
                    b = 5.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -0.23f,
                    dy1 = -0.6f,
                )
                // v 0.25
                verticalLineToRelative(dy = 0.25f)
                // a 3 3 0 0 1 0.23 0.76
                arcToRelative(
                    a = 3.0f,
                    b = 3.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.23f,
                    dy1 = 0.76f,
                )
                // c 0 -0.06 0.06 -0.23 0 -0.44z
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = -0.06f,
                    dx2 = 0.06f,
                    dy2 = -0.23f,
                    dx3 = 0.0f,
                    dy3 = -0.44f,
                )
                close()
                // m 31.44 3.12
                moveToRelative(dx = 31.44f, dy = 3.12f)
                // c 0.37 0.2 0.98 0.5 1.24 0.61
                curveToRelative(
                    dx1 = 0.37f,
                    dy1 = 0.2f,
                    dx2 = 0.98f,
                    dy2 = 0.5f,
                    dx3 = 1.24f,
                    dy3 = 0.61f,
                )
                // l 0.23 0.7
                lineToRelative(dx = 0.23f, dy = 0.7f)
                // a 4 4 0 0 1 -1.21 -0.7z
                arcToRelative(
                    a = 4.0f,
                    b = 4.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.21f,
                    dy1 = -0.7f,
                )
                close()
                // m -1.19 -3.05
                moveToRelative(dx = -1.19f, dy = -3.05f)
                // a 5 5 0 0 1 0.23 -0.62
                arcToRelative(
                    a = 5.0f,
                    b = 5.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.23f,
                    dy1 = -0.62f,
                )
                // v 0.27
                verticalLineToRelative(dy = 0.27f)
                // a 3 3 0 0 0 -0.23 0.75
                arcToRelative(
                    a = 3.0f,
                    b = 3.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -0.23f,
                    dy1 = 0.75f,
                )
                // c 0 -0.06 -0.05 -0.23 0 -0.43z
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = -0.06f,
                    dx2 = -0.05f,
                    dy2 = -0.23f,
                    dx3 = 0.0f,
                    dy3 = -0.43f,
                )
                close()
            }
        }.build().also { _android = it }
    }

@Suppress("ObjectPropertyName")
private var _android: ImageVector? = null
