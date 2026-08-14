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

val Tux: ImageVector
    get() {
        val current = _tux
        if (current != null) return current

        return ImageVector.Builder(
            name = ".Tux",
            defaultWidth = 216.0.dp,
            defaultHeight = 256.0.dp,
            viewportWidth = 216.0f,
            viewportHeight = 256.0f,
        ).apply {
            tuxChunk1()
            tuxChunk2()
        }.build().also { _tux = it }
    }

private fun ImageVector.Builder.tuxChunk1() {
    // M106.95 0 c-6 0 -12.02 1.18 -17.46 4.12 a32 32 0 0 0 -13.43 13.97 c-2.92 5.88 -4.06 12.16 -4.24 19.08 a387 387 0 0 0 1.29 39.41 c.26 3.8 .74 6.02 .25 9.93 -1.62 8.3 -8.88 13.88 -12.76 21.17 -4.27 8.04 -6.07 17.13 -9.29 25.65 -2.95 7.79 -7.09 15.1 -9.88 22.95 -3.91 10.97 -5.08 23.03 -2.5 34.39 a59.5 59.5 0 0 0 11.62 23.73 q-1.18 2.18 -2.4 4.34 c-2.57 4.43 -5.71 8.64 -7.17 13.55 a16 16 0 0 0 -.55 7.59 11 11 0 0 0 3.75 6.53 11.5 11.5 0 0 0 4.53 2.1 21 21 0 0 0 5 .43 c6.37 -.14 12.55 -2.07 18.71 -3.69 a223 223 0 0 1 11.03 -2.58 c13.14 -2.69 27.8 -1.61 39.99 .15 q6.2 .94 12.29 2.43 c6.36 1.54 12.69 3.5 19.23 3.69 a21 21 0 0 0 5.14 -.4 11.5 11.5 0 0 0 4.65 -2.13 11 11 0 0 0 3.76 -6.54 16 16 0 0 0 -.56 -7.61 c-1.48 -4.92 -4.65 -9.11 -7.27 -13.52 -1.04 -1.75 -2 -3.53 -3.03 -5.28 7.9 -8.87 14.26 -19.13 17.94 -30.4 4.01 -12.3 4.75 -25.55 3.06 -38.38 s-5.76 -25.27 -11.11 -37.05 c-6.72 -14.76 -12.37 -20.1 -16.47 -33.07 -4.42 -14.02 -.77 -30.61 -4.06 -43.32 a44 44 0 0 0 -5.45 -12.23 42 42 0 0 0 -10.65 -11.47 A41 41 0 0 0 106.95 0
    path(
        fill = SolidColor(Color(0xFF020204)),
    ) {
        // M 106.95 0
        moveTo(x = 106.95f, y = 0.0f)
        // c -6 0 -12.02 1.18 -17.46 4.12
        curveToRelative(
            dx1 = -6.0f,
            dy1 = 0.0f,
            dx2 = -12.02f,
            dy2 = 1.18f,
            dx3 = -17.46f,
            dy3 = 4.12f,
        )
        // a 32 32 0 0 0 -13.43 13.97
        arcToRelative(
            a = 32.0f,
            b = 32.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -13.43f,
            dy1 = 13.97f,
        )
        // c -2.92 5.88 -4.06 12.16 -4.24 19.08
        curveToRelative(
            dx1 = -2.92f,
            dy1 = 5.88f,
            dx2 = -4.06f,
            dy2 = 12.16f,
            dx3 = -4.24f,
            dy3 = 19.08f,
        )
        // a 387 387 0 0 0 1.29 39.41
        arcToRelative(
            a = 387.0f,
            b = 387.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 1.29f,
            dy1 = 39.41f,
        )
        // c 0.26 3.8 0.74 6.02 0.25 9.93
        curveToRelative(
            dx1 = 0.26f,
            dy1 = 3.8f,
            dx2 = 0.74f,
            dy2 = 6.02f,
            dx3 = 0.25f,
            dy3 = 9.93f,
        )
        // c -1.62 8.3 -8.88 13.88 -12.76 21.17
        curveToRelative(
            dx1 = -1.62f,
            dy1 = 8.3f,
            dx2 = -8.88f,
            dy2 = 13.88f,
            dx3 = -12.76f,
            dy3 = 21.17f,
        )
        // c -4.27 8.04 -6.07 17.13 -9.29 25.65
        curveToRelative(
            dx1 = -4.27f,
            dy1 = 8.04f,
            dx2 = -6.07f,
            dy2 = 17.13f,
            dx3 = -9.29f,
            dy3 = 25.65f,
        )
        // c -2.95 7.79 -7.09 15.1 -9.88 22.95
        curveToRelative(
            dx1 = -2.95f,
            dy1 = 7.79f,
            dx2 = -7.09f,
            dy2 = 15.1f,
            dx3 = -9.88f,
            dy3 = 22.95f,
        )
        // c -3.91 10.97 -5.08 23.03 -2.5 34.39
        curveToRelative(
            dx1 = -3.91f,
            dy1 = 10.97f,
            dx2 = -5.08f,
            dy2 = 23.03f,
            dx3 = -2.5f,
            dy3 = 34.39f,
        )
        // a 59.5 59.5 0 0 0 11.62 23.73
        arcToRelative(
            a = 59.5f,
            b = 59.5f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 11.62f,
            dy1 = 23.73f,
        )
        // q -1.18 2.18 -2.4 4.34
        quadToRelative(
            dx1 = -1.18f,
            dy1 = 2.18f,
            dx2 = -2.4f,
            dy2 = 4.34f,
        )
        // c -2.57 4.43 -5.71 8.64 -7.17 13.55
        curveToRelative(
            dx1 = -2.57f,
            dy1 = 4.43f,
            dx2 = -5.71f,
            dy2 = 8.64f,
            dx3 = -7.17f,
            dy3 = 13.55f,
        )
        // a 16 16 0 0 0 -0.55 7.59
        arcToRelative(
            a = 16.0f,
            b = 16.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -0.55f,
            dy1 = 7.59f,
        )
        // a 11 11 0 0 0 3.75 6.53
        arcToRelative(
            a = 11.0f,
            b = 11.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 3.75f,
            dy1 = 6.53f,
        )
        // a 11.5 11.5 0 0 0 4.53 2.1
        arcToRelative(
            a = 11.5f,
            b = 11.5f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 4.53f,
            dy1 = 2.1f,
        )
        // a 21 21 0 0 0 5 0.43
        arcToRelative(
            a = 21.0f,
            b = 21.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 5.0f,
            dy1 = 0.43f,
        )
        // c 6.37 -0.14 12.55 -2.07 18.71 -3.69
        curveToRelative(
            dx1 = 6.37f,
            dy1 = -0.14f,
            dx2 = 12.55f,
            dy2 = -2.07f,
            dx3 = 18.71f,
            dy3 = -3.69f,
        )
        // a 223 223 0 0 1 11.03 -2.58
        arcToRelative(
            a = 223.0f,
            b = 223.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 11.03f,
            dy1 = -2.58f,
        )
        // c 13.14 -2.69 27.8 -1.61 39.99 0.15
        curveToRelative(
            dx1 = 13.14f,
            dy1 = -2.69f,
            dx2 = 27.8f,
            dy2 = -1.61f,
            dx3 = 39.99f,
            dy3 = 0.15f,
        )
        // q 6.2 0.94 12.29 2.43
        quadToRelative(
            dx1 = 6.2f,
            dy1 = 0.94f,
            dx2 = 12.29f,
            dy2 = 2.43f,
        )
        // c 6.36 1.54 12.69 3.5 19.23 3.69
        curveToRelative(
            dx1 = 6.36f,
            dy1 = 1.54f,
            dx2 = 12.69f,
            dy2 = 3.5f,
            dx3 = 19.23f,
            dy3 = 3.69f,
        )
        // a 21 21 0 0 0 5.14 -0.4
        arcToRelative(
            a = 21.0f,
            b = 21.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 5.14f,
            dy1 = -0.4f,
        )
        // a 11.5 11.5 0 0 0 4.65 -2.13
        arcToRelative(
            a = 11.5f,
            b = 11.5f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 4.65f,
            dy1 = -2.13f,
        )
        // a 11 11 0 0 0 3.76 -6.54
        arcToRelative(
            a = 11.0f,
            b = 11.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 3.76f,
            dy1 = -6.54f,
        )
        // a 16 16 0 0 0 -0.56 -7.61
        arcToRelative(
            a = 16.0f,
            b = 16.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -0.56f,
            dy1 = -7.61f,
        )
        // c -1.48 -4.92 -4.65 -9.11 -7.27 -13.52
        curveToRelative(
            dx1 = -1.48f,
            dy1 = -4.92f,
            dx2 = -4.65f,
            dy2 = -9.11f,
            dx3 = -7.27f,
            dy3 = -13.52f,
        )
        // c -1.04 -1.75 -2 -3.53 -3.03 -5.28
        curveToRelative(
            dx1 = -1.04f,
            dy1 = -1.75f,
            dx2 = -2.0f,
            dy2 = -3.53f,
            dx3 = -3.03f,
            dy3 = -5.28f,
        )
        // c 7.9 -8.87 14.26 -19.13 17.94 -30.4
        curveToRelative(
            dx1 = 7.9f,
            dy1 = -8.87f,
            dx2 = 14.26f,
            dy2 = -19.13f,
            dx3 = 17.94f,
            dy3 = -30.4f,
        )
        // c 4.01 -12.3 4.75 -25.55 3.06 -38.38
        curveToRelative(
            dx1 = 4.01f,
            dy1 = -12.3f,
            dx2 = 4.75f,
            dy2 = -25.55f,
            dx3 = 3.06f,
            dy3 = -38.38f,
        )
        // s -5.76 -25.27 -11.11 -37.05
        reflectiveCurveToRelative(
            dx1 = -5.76f,
            dy1 = -25.27f,
            dx2 = -11.11f,
            dy2 = -37.05f,
        )
        // c -6.72 -14.76 -12.37 -20.1 -16.47 -33.07
        curveToRelative(
            dx1 = -6.72f,
            dy1 = -14.76f,
            dx2 = -12.37f,
            dy2 = -20.1f,
            dx3 = -16.47f,
            dy3 = -33.07f,
        )
        // c -4.42 -14.02 -0.77 -30.61 -4.06 -43.32
        curveToRelative(
            dx1 = -4.42f,
            dy1 = -14.02f,
            dx2 = -0.77f,
            dy2 = -30.61f,
            dx3 = -4.06f,
            dy3 = -43.32f,
        )
        // a 44 44 0 0 0 -5.45 -12.23
        arcToRelative(
            a = 44.0f,
            b = 44.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -5.45f,
            dy1 = -12.23f,
        )
        // a 42 42 0 0 0 -10.65 -11.47
        arcToRelative(
            a = 42.0f,
            b = 42.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -10.65f,
            dy1 = -11.47f,
        )
        // A 41 41 0 0 0 106.95 0
        arcTo(
            horizontalEllipseRadius = 41.0f,
            verticalEllipseRadius = 41.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            x1 = 106.95f,
            y1 = 0.0f,
        )
    }
    // M83.13 74 a10 10 0 0 0 -1.84 3.89 21 21 0 0 0 -.54 4.3 c-.11 2.89 .07 5.83 -.7 8.62 -.82 2.98 -2.65 5.57 -4.44 8.08 -3.11 4.36 -6.25 8.84 -7.78 13.97 a25 25 0 0 0 -.91 9.62 103 103 0 0 0 -8.98 16.18 89 89 0 0 0 -7.28 27.01 c-1.12 11.41 .34 23.15 4.85 33.69 a55 55 0 0 0 14.38 20.04 49 49 0 0 0 10.5 6.97 c13.11 6.45 29.31 6.46 42.2 -.41 6.74 -3.59 12.43 -8.84 17.91 -14.15 3.3 -3.2 6.59 -6.48 9.11 -10.32 4.85 -7.41 6.54 -16.41 7.59 -25.2 1.83 -15.36 1.89 -31.6 -4.85 -45.53 a50 50 0 0 0 -9.12 -13.05 71 71 0 0 0 -5.76 -19.42 c-2.05 -4.45 -4.54 -8.68 -6.44 -13.18 -.78 -1.85 -1.46 -3.75 -2.32 -5.56 a17 17 0 0 0 -3.39 -4.94 14 14 0 0 0 -5.28 -3.07 23 23 0 0 0 -6.06 -1.04 c-4.11 -.21 -8.22 .33 -12.33 .16 -3.27 -.13 -6.53 -.7 -9.8 -.51 a15 15 0 0 0 -4.78 1.01 A9.6 9.6 0 0 0 83.13 74
    path(
        fill = SolidColor(Color(0xFFFDFDFB)),
    ) {
        // M 83.13 74
        moveTo(x = 83.13f, y = 74.0f)
        // a 10 10 0 0 0 -1.84 3.89
        arcToRelative(
            a = 10.0f,
            b = 10.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -1.84f,
            dy1 = 3.89f,
        )
        // a 21 21 0 0 0 -0.54 4.3
        arcToRelative(
            a = 21.0f,
            b = 21.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -0.54f,
            dy1 = 4.3f,
        )
        // c -0.11 2.89 0.07 5.83 -0.7 8.62
        curveToRelative(
            dx1 = -0.11f,
            dy1 = 2.89f,
            dx2 = 0.07f,
            dy2 = 5.83f,
            dx3 = -0.7f,
            dy3 = 8.62f,
        )
        // c -0.82 2.98 -2.65 5.57 -4.44 8.08
        curveToRelative(
            dx1 = -0.82f,
            dy1 = 2.98f,
            dx2 = -2.65f,
            dy2 = 5.57f,
            dx3 = -4.44f,
            dy3 = 8.08f,
        )
        // c -3.11 4.36 -6.25 8.84 -7.78 13.97
        curveToRelative(
            dx1 = -3.11f,
            dy1 = 4.36f,
            dx2 = -6.25f,
            dy2 = 8.84f,
            dx3 = -7.78f,
            dy3 = 13.97f,
        )
        // a 25 25 0 0 0 -0.91 9.62
        arcToRelative(
            a = 25.0f,
            b = 25.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -0.91f,
            dy1 = 9.62f,
        )
        // a 103 103 0 0 0 -8.98 16.18
        arcToRelative(
            a = 103.0f,
            b = 103.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -8.98f,
            dy1 = 16.18f,
        )
        // a 89 89 0 0 0 -7.28 27.01
        arcToRelative(
            a = 89.0f,
            b = 89.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -7.28f,
            dy1 = 27.01f,
        )
        // c -1.12 11.41 0.34 23.15 4.85 33.69
        curveToRelative(
            dx1 = -1.12f,
            dy1 = 11.41f,
            dx2 = 0.34f,
            dy2 = 23.15f,
            dx3 = 4.85f,
            dy3 = 33.69f,
        )
        // a 55 55 0 0 0 14.38 20.04
        arcToRelative(
            a = 55.0f,
            b = 55.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 14.38f,
            dy1 = 20.04f,
        )
        // a 49 49 0 0 0 10.5 6.97
        arcToRelative(
            a = 49.0f,
            b = 49.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 10.5f,
            dy1 = 6.97f,
        )
        // c 13.11 6.45 29.31 6.46 42.2 -0.41
        curveToRelative(
            dx1 = 13.11f,
            dy1 = 6.45f,
            dx2 = 29.31f,
            dy2 = 6.46f,
            dx3 = 42.2f,
            dy3 = -0.41f,
        )
        // c 6.74 -3.59 12.43 -8.84 17.91 -14.15
        curveToRelative(
            dx1 = 6.74f,
            dy1 = -3.59f,
            dx2 = 12.43f,
            dy2 = -8.84f,
            dx3 = 17.91f,
            dy3 = -14.15f,
        )
        // c 3.3 -3.2 6.59 -6.48 9.11 -10.32
        curveToRelative(
            dx1 = 3.3f,
            dy1 = -3.2f,
            dx2 = 6.59f,
            dy2 = -6.48f,
            dx3 = 9.11f,
            dy3 = -10.32f,
        )
        // c 4.85 -7.41 6.54 -16.41 7.59 -25.2
        curveToRelative(
            dx1 = 4.85f,
            dy1 = -7.41f,
            dx2 = 6.54f,
            dy2 = -16.41f,
            dx3 = 7.59f,
            dy3 = -25.2f,
        )
        // c 1.83 -15.36 1.89 -31.6 -4.85 -45.53
        curveToRelative(
            dx1 = 1.83f,
            dy1 = -15.36f,
            dx2 = 1.89f,
            dy2 = -31.6f,
            dx3 = -4.85f,
            dy3 = -45.53f,
        )
        // a 50 50 0 0 0 -9.12 -13.05
        arcToRelative(
            a = 50.0f,
            b = 50.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -9.12f,
            dy1 = -13.05f,
        )
        // a 71 71 0 0 0 -5.76 -19.42
        arcToRelative(
            a = 71.0f,
            b = 71.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -5.76f,
            dy1 = -19.42f,
        )
        // c -2.05 -4.45 -4.54 -8.68 -6.44 -13.18
        curveToRelative(
            dx1 = -2.05f,
            dy1 = -4.45f,
            dx2 = -4.54f,
            dy2 = -8.68f,
            dx3 = -6.44f,
            dy3 = -13.18f,
        )
        // c -0.78 -1.85 -1.46 -3.75 -2.32 -5.56
        curveToRelative(
            dx1 = -0.78f,
            dy1 = -1.85f,
            dx2 = -1.46f,
            dy2 = -3.75f,
            dx3 = -2.32f,
            dy3 = -5.56f,
        )
        // a 17 17 0 0 0 -3.39 -4.94
        arcToRelative(
            a = 17.0f,
            b = 17.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -3.39f,
            dy1 = -4.94f,
        )
        // a 14 14 0 0 0 -5.28 -3.07
        arcToRelative(
            a = 14.0f,
            b = 14.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -5.28f,
            dy1 = -3.07f,
        )
        // a 23 23 0 0 0 -6.06 -1.04
        arcToRelative(
            a = 23.0f,
            b = 23.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -6.06f,
            dy1 = -1.04f,
        )
        // c -4.11 -0.21 -8.22 0.33 -12.33 0.16
        curveToRelative(
            dx1 = -4.11f,
            dy1 = -0.21f,
            dx2 = -8.22f,
            dy2 = 0.33f,
            dx3 = -12.33f,
            dy3 = 0.16f,
        )
        // c -3.27 -0.13 -6.53 -0.7 -9.8 -0.51
        curveToRelative(
            dx1 = -3.27f,
            dy1 = -0.13f,
            dx2 = -6.53f,
            dy2 = -0.7f,
            dx3 = -9.8f,
            dy3 = -0.51f,
        )
        // a 15 15 0 0 0 -4.78 1.01
        arcToRelative(
            a = 15.0f,
            b = 15.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -4.78f,
            dy1 = 1.01f,
        )
        // A 9.6 9.6 0 0 0 83.13 74
        arcTo(
            horizontalEllipseRadius = 9.6f,
            verticalEllipseRadius = 9.6f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            x1 = 83.13f,
            y1 = 74.0f,
        )
    }
    // M68.67 115.18 c.87 1.31 -.55 5.84 19.86 2.94 0 0 -3.59 .39 -7.12 1.21 -5.49 1.84 -10.27 3.89 -13.97 6.61 -3.65 2.7 -6.33 6.21 -9.68 9.22 0 0 5.43 -9.92 6.78 -12.91 s-.22 -2.85 .85 -7.25 a35 35 0 0 1 3.69 -8.63 s-2.14 6.22 -.41 8.81
    path(
        fill = Brush.radialGradient(
            0.0f to Color(0xFF000000),
            1.0f to Color(0x40000000),
            center = Offset(x = 61.18f, y = 121.19f),
            radius = 18.506756f,
        ),
        fillAlpha = 0.25f,
        strokeAlpha = 0.25f,
    ) {
        // M 68.67 115.18
        moveTo(x = 68.67f, y = 115.18f)
        // c 0.87 1.31 -0.55 5.84 19.86 2.94
        curveToRelative(
            dx1 = 0.87f,
            dy1 = 1.31f,
            dx2 = -0.55f,
            dy2 = 5.84f,
            dx3 = 19.86f,
            dy3 = 2.94f,
        )
        // c 0 0 -3.59 0.39 -7.12 1.21
        curveToRelative(
            dx1 = 0.0f,
            dy1 = 0.0f,
            dx2 = -3.59f,
            dy2 = 0.39f,
            dx3 = -7.12f,
            dy3 = 1.21f,
        )
        // c -5.49 1.84 -10.27 3.89 -13.97 6.61
        curveToRelative(
            dx1 = -5.49f,
            dy1 = 1.84f,
            dx2 = -10.27f,
            dy2 = 3.89f,
            dx3 = -13.97f,
            dy3 = 6.61f,
        )
        // c -3.65 2.7 -6.33 6.21 -9.68 9.22
        curveToRelative(
            dx1 = -3.65f,
            dy1 = 2.7f,
            dx2 = -6.33f,
            dy2 = 6.21f,
            dx3 = -9.68f,
            dy3 = 9.22f,
        )
        // c 0 0 5.43 -9.92 6.78 -12.91
        curveToRelative(
            dx1 = 0.0f,
            dy1 = 0.0f,
            dx2 = 5.43f,
            dy2 = -9.92f,
            dx3 = 6.78f,
            dy3 = -12.91f,
        )
        // s -0.22 -2.85 0.85 -7.25
        reflectiveCurveToRelative(
            dx1 = -0.22f,
            dy1 = -2.85f,
            dx2 = 0.85f,
            dy2 = -7.25f,
        )
        // a 35 35 0 0 1 3.69 -8.63
        arcToRelative(
            a = 35.0f,
            b = 35.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 3.69f,
            dy1 = -8.63f,
        )
        // s -2.14 6.22 -0.41 8.81
        reflectiveCurveToRelative(
            dx1 = -2.14f,
            dy1 = 6.22f,
            dx2 = -0.41f,
            dy2 = 8.81f,
        )
    }
    // M134.28 113.99 c-4.16 2.9 -6.6 2.56 -11.64 3.12 s-18.7 .36 -18.7 .36 1.97 -.03 6.36 .78 c4.38 .82 13.31 1.6 18.34 3.51 5.04 1.92 6.87 2.47 9.93 4.4 4.35 2.75 7.55 7.06 11.71 10.08 0 0 .2 -4 -1.48 -6.99 s-6.2 -7.7 -7.53 -12.1 c-1.32 -4.4 -1.96 -13.04 -1.96 -13.04 s-.88 6.99 -5.03 9.88
    path(
        fill = Brush.radialGradient(
            0.0f to Color(0xFF000000),
            1.0f to Color(0x40000000),
            center = Offset(x = 125.74f, y = 131.6f),
            radius = 20.987616f,
        ),
        fillAlpha = 0.42f,
        strokeAlpha = 0.42f,
    ) {
        // M 134.28 113.99
        moveTo(x = 134.28f, y = 113.99f)
        // c -4.16 2.9 -6.6 2.56 -11.64 3.12
        curveToRelative(
            dx1 = -4.16f,
            dy1 = 2.9f,
            dx2 = -6.6f,
            dy2 = 2.56f,
            dx3 = -11.64f,
            dy3 = 3.12f,
        )
        // s -18.7 0.36 -18.7 0.36
        reflectiveCurveToRelative(
            dx1 = -18.7f,
            dy1 = 0.36f,
            dx2 = -18.7f,
            dy2 = 0.36f,
        )
        // s 1.97 -0.03 6.36 0.78
        reflectiveCurveToRelative(
            dx1 = 1.97f,
            dy1 = -0.03f,
            dx2 = 6.36f,
            dy2 = 0.78f,
        )
        // c 4.38 0.82 13.31 1.6 18.34 3.51
        curveToRelative(
            dx1 = 4.38f,
            dy1 = 0.82f,
            dx2 = 13.31f,
            dy2 = 1.6f,
            dx3 = 18.34f,
            dy3 = 3.51f,
        )
        // c 5.04 1.92 6.87 2.47 9.93 4.4
        curveToRelative(
            dx1 = 5.04f,
            dy1 = 1.92f,
            dx2 = 6.87f,
            dy2 = 2.47f,
            dx3 = 9.93f,
            dy3 = 4.4f,
        )
        // c 4.35 2.75 7.55 7.06 11.71 10.08
        curveToRelative(
            dx1 = 4.35f,
            dy1 = 2.75f,
            dx2 = 7.55f,
            dy2 = 7.06f,
            dx3 = 11.71f,
            dy3 = 10.08f,
        )
        // c 0 0 0.2 -4 -1.48 -6.99
        curveToRelative(
            dx1 = 0.0f,
            dy1 = 0.0f,
            dx2 = 0.2f,
            dy2 = -4.0f,
            dx3 = -1.48f,
            dy3 = -6.99f,
        )
        // s -6.2 -7.7 -7.53 -12.1
        reflectiveCurveToRelative(
            dx1 = -6.2f,
            dy1 = -7.7f,
            dx2 = -7.53f,
            dy2 = -12.1f,
        )
        // c -1.32 -4.4 -1.96 -13.04 -1.96 -13.04
        curveToRelative(
            dx1 = -1.32f,
            dy1 = -4.4f,
            dx2 = -1.96f,
            dy2 = -13.04f,
            dx3 = -1.96f,
            dy3 = -13.04f,
        )
        // s -0.88 6.99 -5.03 9.88
        reflectiveCurveToRelative(
            dx1 = -0.88f,
            dy1 = 6.99f,
            dx2 = -5.03f,
            dy2 = 9.88f,
        )
    }
    // M95.17 107.81 a59 59 0 0 1 -.6 3.74 11 11 0 0 1 -.48 1.8 4 4 0 0 1 -1.02 1.55 5 5 0 0 1 -1.4 .85 21 21 0 0 1 -6.12 1.74 74 74 0 0 1 2.53 .23 q.8 .07 1.57 .25 a5 5 0 0 1 1.46 .65 4 4 0 0 1 1.12 1.34 9 9 0 0 1 .83 3.37 28 28 0 0 1 .03 4.46 14 14 0 0 1 .62 -2.92 13 13 0 0 1 2.69 -4.65 8.86 8.86 0 0 1 7.84 -2.93 9.7 9.7 0 0 1 -6.2 -1.93 8 8 0 0 1 -2.22 -2.5 7 7 0 0 1 -.65 -5.05
    path(
        fill = Brush.radialGradient(
            0.0f to Color(0xFF000000),
            1.0f to Color(0x40000000),
            center = Offset(x = 94.21f, y = 127.47f),
            radius = 9.680457f,
        ),
        fillAlpha = 0.2f,
        strokeAlpha = 0.2f,
    ) {
        // M 95.17 107.81
        moveTo(x = 95.17f, y = 107.81f)
        // a 59 59 0 0 1 -0.6 3.74
        arcToRelative(
            a = 59.0f,
            b = 59.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -0.6f,
            dy1 = 3.74f,
        )
        // a 11 11 0 0 1 -0.48 1.8
        arcToRelative(
            a = 11.0f,
            b = 11.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -0.48f,
            dy1 = 1.8f,
        )
        // a 4 4 0 0 1 -1.02 1.55
        arcToRelative(
            a = 4.0f,
            b = 4.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -1.02f,
            dy1 = 1.55f,
        )
        // a 5 5 0 0 1 -1.4 0.85
        arcToRelative(
            a = 5.0f,
            b = 5.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -1.4f,
            dy1 = 0.85f,
        )
        // a 21 21 0 0 1 -6.12 1.74
        arcToRelative(
            a = 21.0f,
            b = 21.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -6.12f,
            dy1 = 1.74f,
        )
        // a 74 74 0 0 1 2.53 0.23
        arcToRelative(
            a = 74.0f,
            b = 74.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 2.53f,
            dy1 = 0.23f,
        )
        // q 0.8 0.07 1.57 0.25
        quadToRelative(
            dx1 = 0.8f,
            dy1 = 0.07f,
            dx2 = 1.57f,
            dy2 = 0.25f,
        )
        // a 5 5 0 0 1 1.46 0.65
        arcToRelative(
            a = 5.0f,
            b = 5.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 1.46f,
            dy1 = 0.65f,
        )
        // a 4 4 0 0 1 1.12 1.34
        arcToRelative(
            a = 4.0f,
            b = 4.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 1.12f,
            dy1 = 1.34f,
        )
        // a 9 9 0 0 1 0.83 3.37
        arcToRelative(
            a = 9.0f,
            b = 9.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 0.83f,
            dy1 = 3.37f,
        )
        // a 28 28 0 0 1 0.03 4.46
        arcToRelative(
            a = 28.0f,
            b = 28.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 0.03f,
            dy1 = 4.46f,
        )
        // a 14 14 0 0 1 0.62 -2.92
        arcToRelative(
            a = 14.0f,
            b = 14.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 0.62f,
            dy1 = -2.92f,
        )
        // a 13 13 0 0 1 2.69 -4.65
        arcToRelative(
            a = 13.0f,
            b = 13.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 2.69f,
            dy1 = -4.65f,
        )
        // a 8.86 8.86 0 0 1 7.84 -2.93
        arcToRelative(
            a = 8.86f,
            b = 8.86f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 7.84f,
            dy1 = -2.93f,
        )
        // a 9.7 9.7 0 0 1 -6.2 -1.93
        arcToRelative(
            a = 9.7f,
            b = 9.7f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -6.2f,
            dy1 = -1.93f,
        )
        // a 8 8 0 0 1 -2.22 -2.5
        arcToRelative(
            a = 8.0f,
            b = 8.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -2.22f,
            dy1 = -2.5f,
        )
        // a 7 7 0 0 1 -0.65 -5.05
        arcToRelative(
            a = 7.0f,
            b = 7.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -0.65f,
            dy1 = -5.05f,
        )
    }
    // M89.85 137.14 a75 75 0 0 0 -2.17 12.31 c-.55 5.87 -.42 11.78 -.74 17.67 -.26 4.99 -.85 10.04 .02 14.97 a25 25 0 0 0 2.2 6.78 23 23 0 0 0 .36 -2.47 c.37 -4 -.3 -8.01 -.53 -12.01 -.4 -7.02 .57 -14.04 .97 -21.06 q.45 -8.1 -.11 -16.19
    path(
        fillAlpha = 0.11f,
        strokeAlpha = 0.11f,
        fill = SolidColor(Color(0xFF000000)),
    ) {
        // M 89.85 137.14
        moveTo(x = 89.85f, y = 137.14f)
        // a 75 75 0 0 0 -2.17 12.31
        arcToRelative(
            a = 75.0f,
            b = 75.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -2.17f,
            dy1 = 12.31f,
        )
        // c -0.55 5.87 -0.42 11.78 -0.74 17.67
        curveToRelative(
            dx1 = -0.55f,
            dy1 = 5.87f,
            dx2 = -0.42f,
            dy2 = 11.78f,
            dx3 = -0.74f,
            dy3 = 17.67f,
        )
        // c -0.26 4.99 -0.85 10.04 0.02 14.97
        curveToRelative(
            dx1 = -0.26f,
            dy1 = 4.99f,
            dx2 = -0.85f,
            dy2 = 10.04f,
            dx3 = 0.02f,
            dy3 = 14.97f,
        )
        // a 25 25 0 0 0 2.2 6.78
        arcToRelative(
            a = 25.0f,
            b = 25.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 2.2f,
            dy1 = 6.78f,
        )
        // a 23 23 0 0 0 0.36 -2.47
        arcToRelative(
            a = 23.0f,
            b = 23.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.36f,
            dy1 = -2.47f,
        )
        // c 0.37 -4 -0.3 -8.01 -0.53 -12.01
        curveToRelative(
            dx1 = 0.37f,
            dy1 = -4.0f,
            dx2 = -0.3f,
            dy2 = -8.01f,
            dx3 = -0.53f,
            dy3 = -12.01f,
        )
        // c -0.4 -7.02 0.57 -14.04 0.97 -21.06
        curveToRelative(
            dx1 = -0.4f,
            dy1 = -7.02f,
            dx2 = 0.57f,
            dy2 = -14.04f,
            dx3 = 0.97f,
            dy3 = -21.06f,
        )
        // q 0.45 -8.1 -0.11 -16.19
        quadToRelative(
            dx1 = 0.45f,
            dy1 = -8.1f,
            dx2 = -0.11f,
            dy2 = -16.19f,
        )
    }
    // M160.08 131.23 c1.03 -.16 7.34 5.21 6.48 7.21 -.86 1.99 -2.49 .79 -3.65 .8 s-4.33 1.46 -4.86 .55 1.4 -3.03 2.41 -4.81 c.82 -1.43 -1.4 -3.59 -.38 -3.75
    path(
        fill = SolidColor(Color(0xFF7C7C7C)),
        fillAlpha = 0.75f,
        strokeAlpha = 0.75f,
    ) {
        // M 160.08 131.23
        moveTo(x = 160.08f, y = 131.23f)
        // c 1.03 -0.16 7.34 5.21 6.48 7.21
        curveToRelative(
            dx1 = 1.03f,
            dy1 = -0.16f,
            dx2 = 7.34f,
            dy2 = 5.21f,
            dx3 = 6.48f,
            dy3 = 7.21f,
        )
        // c -0.86 1.99 -2.49 0.79 -3.65 0.8
        curveToRelative(
            dx1 = -0.86f,
            dy1 = 1.99f,
            dx2 = -2.49f,
            dy2 = 0.79f,
            dx3 = -3.65f,
            dy3 = 0.8f,
        )
        // s -4.33 1.46 -4.86 0.55
        reflectiveCurveToRelative(
            dx1 = -4.33f,
            dy1 = 1.46f,
            dx2 = -4.86f,
            dy2 = 0.55f,
        )
        // s 1.4 -3.03 2.41 -4.81
        reflectiveCurveToRelative(
            dx1 = 1.4f,
            dy1 = -3.03f,
            dx2 = 2.41f,
            dy2 = -4.81f,
        )
        // c 0.82 -1.43 -1.4 -3.59 -0.38 -3.75
        curveToRelative(
            dx1 = 0.82f,
            dy1 = -1.43f,
            dx2 = -1.4f,
            dy2 = -3.59f,
            dx3 = -0.38f,
            dy3 = -3.75f,
        )
    }
    // M121.52 11.12 c-2.21 1.56 -1.25 3.51 -.3 5.46 s-2.09 7.59 -2.12 7.83 5.98 -2.85 7.62 -4.87 c1.94 -2.37 6.83 3.22 6.56 2.37 .01 -1.52 -9.55 -12.34 -11.76 -10.79
    path(
        fill = SolidColor(Color(0xFF7C7C7C)),
    ) {
        // M 121.52 11.12
        moveTo(x = 121.52f, y = 11.12f)
        // c -2.21 1.56 -1.25 3.51 -0.3 5.46
        curveToRelative(
            dx1 = -2.21f,
            dy1 = 1.56f,
            dx2 = -1.25f,
            dy2 = 3.51f,
            dx3 = -0.3f,
            dy3 = 5.46f,
        )
        // s -2.09 7.59 -2.12 7.83
        reflectiveCurveToRelative(
            dx1 = -2.09f,
            dy1 = 7.59f,
            dx2 = -2.12f,
            dy2 = 7.83f,
        )
        // s 5.98 -2.85 7.62 -4.87
        reflectiveCurveToRelative(
            dx1 = 5.98f,
            dy1 = -2.85f,
            dx2 = 7.62f,
            dy2 = -4.87f,
        )
        // c 1.94 -2.37 6.83 3.22 6.56 2.37
        curveToRelative(
            dx1 = 1.94f,
            dy1 = -2.37f,
            dx2 = 6.83f,
            dy2 = 3.22f,
            dx3 = 6.56f,
            dy3 = 2.37f,
        )
        // c 0.01 -1.52 -9.55 -12.34 -11.76 -10.79
        curveToRelative(
            dx1 = 0.01f,
            dy1 = -1.52f,
            dx2 = -9.55f,
            dy2 = -12.34f,
            dx3 = -11.76f,
            dy3 = -10.79f,
        )
    }
    // M138.27 76.63 c-1.86 1.7 .88 4.25 2.17 7.24 .81 1.86 3.04 4.49 5.2 4.07 1.63 -.32 2.63 -2.66 2.48 -4.3 -.3 -3.18 -2.98 -3.93 -4.93 -5.02 -1.54 -.86 -3.61 -3.18 -4.92 -1.99
    path(
        fill = SolidColor(Color(0xFF838384)),
    ) {
        // M 138.27 76.63
        moveTo(x = 138.27f, y = 76.63f)
        // c -1.86 1.7 0.88 4.25 2.17 7.24
        curveToRelative(
            dx1 = -1.86f,
            dy1 = 1.7f,
            dx2 = 0.88f,
            dy2 = 4.25f,
            dx3 = 2.17f,
            dy3 = 7.24f,
        )
        // c 0.81 1.86 3.04 4.49 5.2 4.07
        curveToRelative(
            dx1 = 0.81f,
            dy1 = 1.86f,
            dx2 = 3.04f,
            dy2 = 4.49f,
            dx3 = 5.2f,
            dy3 = 4.07f,
        )
        // c 1.63 -0.32 2.63 -2.66 2.48 -4.3
        curveToRelative(
            dx1 = 1.63f,
            dy1 = -0.32f,
            dx2 = 2.63f,
            dy2 = -2.66f,
            dx3 = 2.48f,
            dy3 = -4.3f,
        )
        // c -0.3 -3.18 -2.98 -3.93 -4.93 -5.02
        curveToRelative(
            dx1 = -0.3f,
            dy1 = -3.18f,
            dx2 = -2.98f,
            dy2 = -3.93f,
            dx3 = -4.93f,
            dy3 = -5.02f,
        )
        // c -1.54 -0.86 -3.61 -3.18 -4.92 -1.99
        curveToRelative(
            dx1 = -1.54f,
            dy1 = -0.86f,
            dx2 = -3.61f,
            dy2 = -3.18f,
            dx3 = -4.92f,
            dy3 = -1.99f,
        )
    }
    // M63.98 100.91 c-6.1 6.92 -12.37 13.63 -15.81 21.12 -1.71 3.8 -2.51 7.93 -3.68 11.93 a90 90 0 0 1 -5.14 13.22 c-1.87 3.95 -3.93 7.81 -5.98 11.66 -1.5 2.81 -3.02 5.67 -3.54 8.81 a18 18 0 0 0 .46 7.47 34 34 0 0 0 2.79 6.98 69 69 0 0 0 20.89 24.07 74 74 0 0 0 12.58 7.35 c2.4 1.09 4.92 2.07 7.56 2.11 a9 9 0 0 0 3.86 -.72 7 7 0 0 0 3 -2.49 7 7 0 0 0 1 -4.66 11 11 0 0 0 -1.65 -4.53 c-2.06 -3.38 -5.31 -5.83 -8.44 -8.25 a283 283 0 0 1 -19.55 -16.58 c-1.76 -1.65 -3.53 -3.34 -4.76 -5.42 a20 20 0 0 1 -2.29 -6.63 37 37 0 0 1 1.25 -19.07 c.85 -2.38 1.96 -4.65 3.04 -6.93 1.86 -3.95 3.62 -7.98 6.07 -11.6 3.05 -4.51 7.13 -8.33 9.61 -13.17 2.1 -4.09 2.95 -8.68 3.76 -13.2 .64 -3.54 1.85 -7 2.47 -10.54 -1.21 2.3 -5.11 6.07 -7.5 9.07
    path(
        fill = SolidColor(Color(0xFF020204)),
    ) {
        // M 63.98 100.91
        moveTo(x = 63.98f, y = 100.91f)
        // c -6.1 6.92 -12.37 13.63 -15.81 21.12
        curveToRelative(
            dx1 = -6.1f,
            dy1 = 6.92f,
            dx2 = -12.37f,
            dy2 = 13.63f,
            dx3 = -15.81f,
            dy3 = 21.12f,
        )
        // c -1.71 3.8 -2.51 7.93 -3.68 11.93
        curveToRelative(
            dx1 = -1.71f,
            dy1 = 3.8f,
            dx2 = -2.51f,
            dy2 = 7.93f,
            dx3 = -3.68f,
            dy3 = 11.93f,
        )
        // a 90 90 0 0 1 -5.14 13.22
        arcToRelative(
            a = 90.0f,
            b = 90.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -5.14f,
            dy1 = 13.22f,
        )
        // c -1.87 3.95 -3.93 7.81 -5.98 11.66
        curveToRelative(
            dx1 = -1.87f,
            dy1 = 3.95f,
            dx2 = -3.93f,
            dy2 = 7.81f,
            dx3 = -5.98f,
            dy3 = 11.66f,
        )
        // c -1.5 2.81 -3.02 5.67 -3.54 8.81
        curveToRelative(
            dx1 = -1.5f,
            dy1 = 2.81f,
            dx2 = -3.02f,
            dy2 = 5.67f,
            dx3 = -3.54f,
            dy3 = 8.81f,
        )
        // a 18 18 0 0 0 0.46 7.47
        arcToRelative(
            a = 18.0f,
            b = 18.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.46f,
            dy1 = 7.47f,
        )
        // a 34 34 0 0 0 2.79 6.98
        arcToRelative(
            a = 34.0f,
            b = 34.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 2.79f,
            dy1 = 6.98f,
        )
        // a 69 69 0 0 0 20.89 24.07
        arcToRelative(
            a = 69.0f,
            b = 69.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 20.89f,
            dy1 = 24.07f,
        )
        // a 74 74 0 0 0 12.58 7.35
        arcToRelative(
            a = 74.0f,
            b = 74.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 12.58f,
            dy1 = 7.35f,
        )
        // c 2.4 1.09 4.92 2.07 7.56 2.11
        curveToRelative(
            dx1 = 2.4f,
            dy1 = 1.09f,
            dx2 = 4.92f,
            dy2 = 2.07f,
            dx3 = 7.56f,
            dy3 = 2.11f,
        )
        // a 9 9 0 0 0 3.86 -0.72
        arcToRelative(
            a = 9.0f,
            b = 9.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 3.86f,
            dy1 = -0.72f,
        )
        // a 7 7 0 0 0 3 -2.49
        arcToRelative(
            a = 7.0f,
            b = 7.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 3.0f,
            dy1 = -2.49f,
        )
        // a 7 7 0 0 0 1 -4.66
        arcToRelative(
            a = 7.0f,
            b = 7.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 1.0f,
            dy1 = -4.66f,
        )
        // a 11 11 0 0 0 -1.65 -4.53
        arcToRelative(
            a = 11.0f,
            b = 11.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -1.65f,
            dy1 = -4.53f,
        )
        // c -2.06 -3.38 -5.31 -5.83 -8.44 -8.25
        curveToRelative(
            dx1 = -2.06f,
            dy1 = -3.38f,
            dx2 = -5.31f,
            dy2 = -5.83f,
            dx3 = -8.44f,
            dy3 = -8.25f,
        )
        // a 283 283 0 0 1 -19.55 -16.58
        arcToRelative(
            a = 283.0f,
            b = 283.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -19.55f,
            dy1 = -16.58f,
        )
        // c -1.76 -1.65 -3.53 -3.34 -4.76 -5.42
        curveToRelative(
            dx1 = -1.76f,
            dy1 = -1.65f,
            dx2 = -3.53f,
            dy2 = -3.34f,
            dx3 = -4.76f,
            dy3 = -5.42f,
        )
        // a 20 20 0 0 1 -2.29 -6.63
        arcToRelative(
            a = 20.0f,
            b = 20.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -2.29f,
            dy1 = -6.63f,
        )
        // a 37 37 0 0 1 1.25 -19.07
        arcToRelative(
            a = 37.0f,
            b = 37.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 1.25f,
            dy1 = -19.07f,
        )
        // c 0.85 -2.38 1.96 -4.65 3.04 -6.93
        curveToRelative(
            dx1 = 0.85f,
            dy1 = -2.38f,
            dx2 = 1.96f,
            dy2 = -4.65f,
            dx3 = 3.04f,
            dy3 = -6.93f,
        )
        // c 1.86 -3.95 3.62 -7.98 6.07 -11.6
        curveToRelative(
            dx1 = 1.86f,
            dy1 = -3.95f,
            dx2 = 3.62f,
            dy2 = -7.98f,
            dx3 = 6.07f,
            dy3 = -11.6f,
        )
        // c 3.05 -4.51 7.13 -8.33 9.61 -13.17
        curveToRelative(
            dx1 = 3.05f,
            dy1 = -4.51f,
            dx2 = 7.13f,
            dy2 = -8.33f,
            dx3 = 9.61f,
            dy3 = -13.17f,
        )
        // c 2.1 -4.09 2.95 -8.68 3.76 -13.2
        curveToRelative(
            dx1 = 2.1f,
            dy1 = -4.09f,
            dx2 = 2.95f,
            dy2 = -8.68f,
            dx3 = 3.76f,
            dy3 = -13.2f,
        )
        // c 0.64 -3.54 1.85 -7 2.47 -10.54
        curveToRelative(
            dx1 = 0.64f,
            dy1 = -3.54f,
            dx2 = 1.85f,
            dy2 = -7.0f,
            dx3 = 2.47f,
            dy3 = -10.54f,
        )
        // c -1.21 2.3 -5.11 6.07 -7.5 9.07
        curveToRelative(
            dx1 = -1.21f,
            dy1 = 2.3f,
            dx2 = -5.11f,
            dy2 = 6.07f,
            dx3 = -7.5f,
            dy3 = 9.07f,
        )
    }
    // M56.96 126.1 q-3.02 2.78 -5.13 6.31 c-2.3 3.84 -3.65 8.16 -5.33 12.31 -1.24 3.09 -2.69 6.2 -2.86 9.53 -.09 1.71 .16 3.42 .22 5.13 s-.1 3.49 -.94 4.98 a6 6 0 0 1 -3.22 2.71 9.6 9.6 0 0 1 4.6 3.33 c.96 1.3 1.58 2.81 2.41 4.18 a11 11 0 0 0 2.54 2.97 7 7 0 0 0 3.54 1.56 6.2 6.2 0 0 0 4.97 -1.58 117 117 0 0 1 4.44 -46.37 c.29 -.94 .59 -1.89 .67 -2.87 a4 4 0 0 0 -.72 -2.81 3 3 0 0 0 -2.77 -1.17 3 3 0 0 0 -2.42 1.79
    path(
        fill = SolidColor(Color(0xFF7C7C7C)),
        fillAlpha = 0.95f,
        strokeAlpha = 0.95f,
    ) {
        // M 56.96 126.1
        moveTo(x = 56.96f, y = 126.1f)
        // q -3.02 2.78 -5.13 6.31
        quadToRelative(
            dx1 = -3.02f,
            dy1 = 2.78f,
            dx2 = -5.13f,
            dy2 = 6.31f,
        )
        // c -2.3 3.84 -3.65 8.16 -5.33 12.31
        curveToRelative(
            dx1 = -2.3f,
            dy1 = 3.84f,
            dx2 = -3.65f,
            dy2 = 8.16f,
            dx3 = -5.33f,
            dy3 = 12.31f,
        )
        // c -1.24 3.09 -2.69 6.2 -2.86 9.53
        curveToRelative(
            dx1 = -1.24f,
            dy1 = 3.09f,
            dx2 = -2.69f,
            dy2 = 6.2f,
            dx3 = -2.86f,
            dy3 = 9.53f,
        )
        // c -0.09 1.71 0.16 3.42 0.22 5.13
        curveToRelative(
            dx1 = -0.09f,
            dy1 = 1.71f,
            dx2 = 0.16f,
            dy2 = 3.42f,
            dx3 = 0.22f,
            dy3 = 5.13f,
        )
        // s -0.1 3.49 -0.94 4.98
        reflectiveCurveToRelative(
            dx1 = -0.1f,
            dy1 = 3.49f,
            dx2 = -0.94f,
            dy2 = 4.98f,
        )
        // a 6 6 0 0 1 -3.22 2.71
        arcToRelative(
            a = 6.0f,
            b = 6.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -3.22f,
            dy1 = 2.71f,
        )
        // a 9.6 9.6 0 0 1 4.6 3.33
        arcToRelative(
            a = 9.6f,
            b = 9.6f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 4.6f,
            dy1 = 3.33f,
        )
        // c 0.96 1.3 1.58 2.81 2.41 4.18
        curveToRelative(
            dx1 = 0.96f,
            dy1 = 1.3f,
            dx2 = 1.58f,
            dy2 = 2.81f,
            dx3 = 2.41f,
            dy3 = 4.18f,
        )
        // a 11 11 0 0 0 2.54 2.97
        arcToRelative(
            a = 11.0f,
            b = 11.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 2.54f,
            dy1 = 2.97f,
        )
        // a 7 7 0 0 0 3.54 1.56
        arcToRelative(
            a = 7.0f,
            b = 7.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 3.54f,
            dy1 = 1.56f,
        )
        // a 6.2 6.2 0 0 0 4.97 -1.58
        arcToRelative(
            a = 6.2f,
            b = 6.2f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 4.97f,
            dy1 = -1.58f,
        )
        // a 117 117 0 0 1 4.44 -46.37
        arcToRelative(
            a = 117.0f,
            b = 117.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 4.44f,
            dy1 = -46.37f,
        )
        // c 0.29 -0.94 0.59 -1.89 0.67 -2.87
        curveToRelative(
            dx1 = 0.29f,
            dy1 = -0.94f,
            dx2 = 0.59f,
            dy2 = -1.89f,
            dx3 = 0.67f,
            dy3 = -2.87f,
        )
        // a 4 4 0 0 0 -0.72 -2.81
        arcToRelative(
            a = 4.0f,
            b = 4.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -0.72f,
            dy1 = -2.81f,
        )
        // a 3 3 0 0 0 -2.77 -1.17
        arcToRelative(
            a = 3.0f,
            b = 3.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -2.77f,
            dy1 = -1.17f,
        )
        // a 3 3 0 0 0 -2.42 1.79
        arcToRelative(
            a = 3.0f,
            b = 3.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -2.42f,
            dy1 = 1.79f,
        )
    }
    // M162.76 127.12 c5.24 4.22 8.57 10.59 9.6 17.24 .8 5.18 .28 10.51 -.89 15.62 -1.17 5.12 -2.97 10.06 -4.77 15 a30 30 0 0 0 -1.71 6.02 10 10 0 0 0 .89 6.11 9.3 9.3 0 0 0 5.59 4.24 13.4 13.4 0 0 0 7.02 .09 c2.3 -.57 6.17 -1.31 8.04 -2.77 4.75 -3.69 5.88 -10.1 7.01 -15.72 1.17 -5.87 .6 -12.02 -.43 -17.95 a121 121 0 0 0 -6.79 -23.62 82 82 0 0 0 -8.44 -15.96 c-3.32 -4.89 -8.02 -8.7 -11.5 -13.48 -1.21 -1.66 -2.66 -3.38 -3.84 -5.06 -2.56 -3.62 -1.98 -2.94 -3.57 -5.29 -1.15 -1.7 -2.97 -2.28 -4.88 -3.02 a9.7 9.7 0 0 0 -6.04 -.41 9.5 9.5 0 0 0 -5.86 5.24 13.8 13.8 0 0 0 -.89 7.95 c.57 3.44 2.14 6.64 3.92 9.64 2 3.39 4.32 6.66 7.35 9.18 3.16 2.63 6.98 4.37 10.19 6.95
    path(
        fill = SolidColor(Color(0xFF020204)),
    ) {
        // M 162.76 127.12
        moveTo(x = 162.76f, y = 127.12f)
        // c 5.24 4.22 8.57 10.59 9.6 17.24
        curveToRelative(
            dx1 = 5.24f,
            dy1 = 4.22f,
            dx2 = 8.57f,
            dy2 = 10.59f,
            dx3 = 9.6f,
            dy3 = 17.24f,
        )
        // c 0.8 5.18 0.28 10.51 -0.89 15.62
        curveToRelative(
            dx1 = 0.8f,
            dy1 = 5.18f,
            dx2 = 0.28f,
            dy2 = 10.51f,
            dx3 = -0.89f,
            dy3 = 15.62f,
        )
        // c -1.17 5.12 -2.97 10.06 -4.77 15
        curveToRelative(
            dx1 = -1.17f,
            dy1 = 5.12f,
            dx2 = -2.97f,
            dy2 = 10.06f,
            dx3 = -4.77f,
            dy3 = 15.0f,
        )
        // a 30 30 0 0 0 -1.71 6.02
        arcToRelative(
            a = 30.0f,
            b = 30.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -1.71f,
            dy1 = 6.02f,
        )
        // a 10 10 0 0 0 0.89 6.11
        arcToRelative(
            a = 10.0f,
            b = 10.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.89f,
            dy1 = 6.11f,
        )
        // a 9.3 9.3 0 0 0 5.59 4.24
        arcToRelative(
            a = 9.3f,
            b = 9.3f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 5.59f,
            dy1 = 4.24f,
        )
        // a 13.4 13.4 0 0 0 7.02 0.09
        arcToRelative(
            a = 13.4f,
            b = 13.4f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 7.02f,
            dy1 = 0.09f,
        )
        // c 2.3 -0.57 6.17 -1.31 8.04 -2.77
        curveToRelative(
            dx1 = 2.3f,
            dy1 = -0.57f,
            dx2 = 6.17f,
            dy2 = -1.31f,
            dx3 = 8.04f,
            dy3 = -2.77f,
        )
        // c 4.75 -3.69 5.88 -10.1 7.01 -15.72
        curveToRelative(
            dx1 = 4.75f,
            dy1 = -3.69f,
            dx2 = 5.88f,
            dy2 = -10.1f,
            dx3 = 7.01f,
            dy3 = -15.72f,
        )
        // c 1.17 -5.87 0.6 -12.02 -0.43 -17.95
        curveToRelative(
            dx1 = 1.17f,
            dy1 = -5.87f,
            dx2 = 0.6f,
            dy2 = -12.02f,
            dx3 = -0.43f,
            dy3 = -17.95f,
        )
        // a 121 121 0 0 0 -6.79 -23.62
        arcToRelative(
            a = 121.0f,
            b = 121.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -6.79f,
            dy1 = -23.62f,
        )
        // a 82 82 0 0 0 -8.44 -15.96
        arcToRelative(
            a = 82.0f,
            b = 82.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -8.44f,
            dy1 = -15.96f,
        )
        // c -3.32 -4.89 -8.02 -8.7 -11.5 -13.48
        curveToRelative(
            dx1 = -3.32f,
            dy1 = -4.89f,
            dx2 = -8.02f,
            dy2 = -8.7f,
            dx3 = -11.5f,
            dy3 = -13.48f,
        )
        // c -1.21 -1.66 -2.66 -3.38 -3.84 -5.06
        curveToRelative(
            dx1 = -1.21f,
            dy1 = -1.66f,
            dx2 = -2.66f,
            dy2 = -3.38f,
            dx3 = -3.84f,
            dy3 = -5.06f,
        )
        // c -2.56 -3.62 -1.98 -2.94 -3.57 -5.29
        curveToRelative(
            dx1 = -2.56f,
            dy1 = -3.62f,
            dx2 = -1.98f,
            dy2 = -2.94f,
            dx3 = -3.57f,
            dy3 = -5.29f,
        )
        // c -1.15 -1.7 -2.97 -2.28 -4.88 -3.02
        curveToRelative(
            dx1 = -1.15f,
            dy1 = -1.7f,
            dx2 = -2.97f,
            dy2 = -2.28f,
            dx3 = -4.88f,
            dy3 = -3.02f,
        )
        // a 9.7 9.7 0 0 0 -6.04 -0.41
        arcToRelative(
            a = 9.7f,
            b = 9.7f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -6.04f,
            dy1 = -0.41f,
        )
        // a 9.5 9.5 0 0 0 -5.86 5.24
        arcToRelative(
            a = 9.5f,
            b = 9.5f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -5.86f,
            dy1 = 5.24f,
        )
        // a 13.8 13.8 0 0 0 -0.89 7.95
        arcToRelative(
            a = 13.8f,
            b = 13.8f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -0.89f,
            dy1 = 7.95f,
        )
        // c 0.57 3.44 2.14 6.64 3.92 9.64
        curveToRelative(
            dx1 = 0.57f,
            dy1 = 3.44f,
            dx2 = 2.14f,
            dy2 = 6.64f,
            dx3 = 3.92f,
            dy3 = 9.64f,
        )
        // c 2 3.39 4.32 6.66 7.35 9.18
        curveToRelative(
            dx1 = 2.0f,
            dy1 = 3.39f,
            dx2 = 4.32f,
            dy2 = 6.66f,
            dx3 = 7.35f,
            dy3 = 9.18f,
        )
        // c 3.16 2.63 6.98 4.37 10.19 6.95
        curveToRelative(
            dx1 = 3.16f,
            dy1 = 2.63f,
            dx2 = 6.98f,
            dy2 = 4.37f,
            dx3 = 10.19f,
            dy3 = 6.95f,
        )
    }
    // M150.42 118.99 a30 30 0 0 0 1.31 1.19 c3.22 2.63 4.93 5.58 8.2 8.16 5.34 4.22 10.75 11.5 11.8 18.15 .82 5.19 -.26 8.01 -1.58 14.12 -1.32 6.12 -5.06 14.78 -7.09 20.68 -.8 2.35 1.64 1.38 1.32 3.86 a15 15 0 0 0 -.03 3.67 q.02 -.35 .06 -.71 c.39 -3.38 1.42 -6.63 2.55 -9.82 2.17 -6.13 4.66 -12.15 6.38 -18.45 s1.53 -10.82 .63 -16.23 a27.4 27.4 0 0 0 -10.69 -17.24 c-3.97 -2.93 -8.64 -4.81 -12.86 -7.38
    path(
        fill = SolidColor(Color(0xFF838384)),
    ) {
        // M 150.42 118.99
        moveTo(x = 150.42f, y = 118.99f)
        // a 30 30 0 0 0 1.31 1.19
        arcToRelative(
            a = 30.0f,
            b = 30.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 1.31f,
            dy1 = 1.19f,
        )
        // c 3.22 2.63 4.93 5.58 8.2 8.16
        curveToRelative(
            dx1 = 3.22f,
            dy1 = 2.63f,
            dx2 = 4.93f,
            dy2 = 5.58f,
            dx3 = 8.2f,
            dy3 = 8.16f,
        )
        // c 5.34 4.22 10.75 11.5 11.8 18.15
        curveToRelative(
            dx1 = 5.34f,
            dy1 = 4.22f,
            dx2 = 10.75f,
            dy2 = 11.5f,
            dx3 = 11.8f,
            dy3 = 18.15f,
        )
        // c 0.82 5.19 -0.26 8.01 -1.58 14.12
        curveToRelative(
            dx1 = 0.82f,
            dy1 = 5.19f,
            dx2 = -0.26f,
            dy2 = 8.01f,
            dx3 = -1.58f,
            dy3 = 14.12f,
        )
        // c -1.32 6.12 -5.06 14.78 -7.09 20.68
        curveToRelative(
            dx1 = -1.32f,
            dy1 = 6.12f,
            dx2 = -5.06f,
            dy2 = 14.78f,
            dx3 = -7.09f,
            dy3 = 20.68f,
        )
        // c -0.8 2.35 1.64 1.38 1.32 3.86
        curveToRelative(
            dx1 = -0.8f,
            dy1 = 2.35f,
            dx2 = 1.64f,
            dy2 = 1.38f,
            dx3 = 1.32f,
            dy3 = 3.86f,
        )
        // a 15 15 0 0 0 -0.03 3.67
        arcToRelative(
            a = 15.0f,
            b = 15.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -0.03f,
            dy1 = 3.67f,
        )
        // q 0.02 -0.35 0.06 -0.71
        quadToRelative(
            dx1 = 0.02f,
            dy1 = -0.35f,
            dx2 = 0.06f,
            dy2 = -0.71f,
        )
        // c 0.39 -3.38 1.42 -6.63 2.55 -9.82
        curveToRelative(
            dx1 = 0.39f,
            dy1 = -3.38f,
            dx2 = 1.42f,
            dy2 = -6.63f,
            dx3 = 2.55f,
            dy3 = -9.82f,
        )
        // c 2.17 -6.13 4.66 -12.15 6.38 -18.45
        curveToRelative(
            dx1 = 2.17f,
            dy1 = -6.13f,
            dx2 = 4.66f,
            dy2 = -12.15f,
            dx3 = 6.38f,
            dy3 = -18.45f,
        )
        // s 1.53 -10.82 0.63 -16.23
        reflectiveCurveToRelative(
            dx1 = 1.53f,
            dy1 = -10.82f,
            dx2 = 0.63f,
            dy2 = -16.23f,
        )
        // a 27.4 27.4 0 0 0 -10.69 -17.24
        arcToRelative(
            a = 27.4f,
            b = 27.4f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -10.69f,
            dy1 = -17.24f,
        )
        // c -3.97 -2.93 -8.64 -4.81 -12.86 -7.38
        curveToRelative(
            dx1 = -3.97f,
            dy1 = -2.93f,
            dx2 = -8.64f,
            dy2 = -4.81f,
            dx3 = -12.86f,
            dy3 = -7.38f,
        )
    }
    // M34.98 175.33 a8 8 0 0 1 4.39 -.41 11 11 0 0 1 4.09 1.74 c2.47 1.68 4.3 4.12 6.05 6.54 4.03 5.54 7.9 11.2 11.42 17.08 2.85 4.78 5.46 9.71 8.76 14.18 2.15 2.93 4.57 5.64 6.73 8.55 s4.07 6.08 5.03 9.58 a19.2 19.2 0 0 1 -1.4 13.75 18 18 0 0 1 -6.7 7.19 A17 17 0 0 1 64.2 256 c-5.27 0 -10.42 -2.83 -15.32 -4.78 -9.98 -3.98 -20.82 -5.22 -31.11 -8.32 -3.16 -.95 -6.27 -2.08 -9.45 -2.95 -1.42 -.39 -2.85 -.73 -4.19 -1.34 a7 7 0 0 1 -3.33 -2.77 6.5 6.5 0 0 1 -.8 -3.26 10 10 0 0 1 .67 -3.32 c.77 -2.13 2.02 -4.06 2.86 -6.17 1.37 -3.44 1.62 -7.23 1.43 -10.93 -.18 -3.69 -.78 -7.36 -1.03 -11.05 a19 19 0 0 1 .16 -4.95 8 8 0 0 1 2.2 -4.35 8 8 0 0 1 4.05 -2 20 20 0 0 1 4.55 -.29 c1.52 .03 3.05 .12 4.57 -.01 a10 10 0 0 0 4.37 -1.22 10 10 0 0 0 3.14 -2.96 c.85 -1.17 1.54 -2.45 2.25 -3.72 a23 23 0 0 1 2.36 -3.64 9 9 0 0 1 3.4 -2.64
    path(
        fill = Brush.linearGradient(
            0.0f to Color(0xFFB98309),
            1.0f to Color(0xFF382605),
            start = Offset(x = 23.18f, y = 193.01f),
            end = Offset(x = 64.31f, y = 262.02f),
        ),
    ) {
        // M 34.98 175.33
        moveTo(x = 34.98f, y = 175.33f)
        // a 8 8 0 0 1 4.39 -0.41
        arcToRelative(
            a = 8.0f,
            b = 8.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 4.39f,
            dy1 = -0.41f,
        )
        // a 11 11 0 0 1 4.09 1.74
        arcToRelative(
            a = 11.0f,
            b = 11.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 4.09f,
            dy1 = 1.74f,
        )
        // c 2.47 1.68 4.3 4.12 6.05 6.54
        curveToRelative(
            dx1 = 2.47f,
            dy1 = 1.68f,
            dx2 = 4.3f,
            dy2 = 4.12f,
            dx3 = 6.05f,
            dy3 = 6.54f,
        )
        // c 4.03 5.54 7.9 11.2 11.42 17.08
        curveToRelative(
            dx1 = 4.03f,
            dy1 = 5.54f,
            dx2 = 7.9f,
            dy2 = 11.2f,
            dx3 = 11.42f,
            dy3 = 17.08f,
        )
        // c 2.85 4.78 5.46 9.71 8.76 14.18
        curveToRelative(
            dx1 = 2.85f,
            dy1 = 4.78f,
            dx2 = 5.46f,
            dy2 = 9.71f,
            dx3 = 8.76f,
            dy3 = 14.18f,
        )
        // c 2.15 2.93 4.57 5.64 6.73 8.55
        curveToRelative(
            dx1 = 2.15f,
            dy1 = 2.93f,
            dx2 = 4.57f,
            dy2 = 5.64f,
            dx3 = 6.73f,
            dy3 = 8.55f,
        )
        // s 4.07 6.08 5.03 9.58
        reflectiveCurveToRelative(
            dx1 = 4.07f,
            dy1 = 6.08f,
            dx2 = 5.03f,
            dy2 = 9.58f,
        )
        // a 19.2 19.2 0 0 1 -1.4 13.75
        arcToRelative(
            a = 19.2f,
            b = 19.2f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -1.4f,
            dy1 = 13.75f,
        )
        // a 18 18 0 0 1 -6.7 7.19
        arcToRelative(
            a = 18.0f,
            b = 18.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -6.7f,
            dy1 = 7.19f,
        )
        // A 17 17 0 0 1 64.2 256
        arcTo(
            horizontalEllipseRadius = 17.0f,
            verticalEllipseRadius = 17.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            x1 = 64.2f,
            y1 = 256.0f,
        )
        // c -5.27 0 -10.42 -2.83 -15.32 -4.78
        curveToRelative(
            dx1 = -5.27f,
            dy1 = 0.0f,
            dx2 = -10.42f,
            dy2 = -2.83f,
            dx3 = -15.32f,
            dy3 = -4.78f,
        )
        // c -9.98 -3.98 -20.82 -5.22 -31.11 -8.32
        curveToRelative(
            dx1 = -9.98f,
            dy1 = -3.98f,
            dx2 = -20.82f,
            dy2 = -5.22f,
            dx3 = -31.11f,
            dy3 = -8.32f,
        )
        // c -3.16 -0.95 -6.27 -2.08 -9.45 -2.95
        curveToRelative(
            dx1 = -3.16f,
            dy1 = -0.95f,
            dx2 = -6.27f,
            dy2 = -2.08f,
            dx3 = -9.45f,
            dy3 = -2.95f,
        )
        // c -1.42 -0.39 -2.85 -0.73 -4.19 -1.34
        curveToRelative(
            dx1 = -1.42f,
            dy1 = -0.39f,
            dx2 = -2.85f,
            dy2 = -0.73f,
            dx3 = -4.19f,
            dy3 = -1.34f,
        )
        // a 7 7 0 0 1 -3.33 -2.77
        arcToRelative(
            a = 7.0f,
            b = 7.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -3.33f,
            dy1 = -2.77f,
        )
        // a 6.5 6.5 0 0 1 -0.8 -3.26
        arcToRelative(
            a = 6.5f,
            b = 6.5f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -0.8f,
            dy1 = -3.26f,
        )
        // a 10 10 0 0 1 0.67 -3.32
        arcToRelative(
            a = 10.0f,
            b = 10.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 0.67f,
            dy1 = -3.32f,
        )
        // c 0.77 -2.13 2.02 -4.06 2.86 -6.17
        curveToRelative(
            dx1 = 0.77f,
            dy1 = -2.13f,
            dx2 = 2.02f,
            dy2 = -4.06f,
            dx3 = 2.86f,
            dy3 = -6.17f,
        )
        // c 1.37 -3.44 1.62 -7.23 1.43 -10.93
        curveToRelative(
            dx1 = 1.37f,
            dy1 = -3.44f,
            dx2 = 1.62f,
            dy2 = -7.23f,
            dx3 = 1.43f,
            dy3 = -10.93f,
        )
        // c -0.18 -3.69 -0.78 -7.36 -1.03 -11.05
        curveToRelative(
            dx1 = -0.18f,
            dy1 = -3.69f,
            dx2 = -0.78f,
            dy2 = -7.36f,
            dx3 = -1.03f,
            dy3 = -11.05f,
        )
        // a 19 19 0 0 1 0.16 -4.95
        arcToRelative(
            a = 19.0f,
            b = 19.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 0.16f,
            dy1 = -4.95f,
        )
        // a 8 8 0 0 1 2.2 -4.35
        arcToRelative(
            a = 8.0f,
            b = 8.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 2.2f,
            dy1 = -4.35f,
        )
        // a 8 8 0 0 1 4.05 -2
        arcToRelative(
            a = 8.0f,
            b = 8.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 4.05f,
            dy1 = -2.0f,
        )
        // a 20 20 0 0 1 4.55 -0.29
        arcToRelative(
            a = 20.0f,
            b = 20.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 4.55f,
            dy1 = -0.29f,
        )
        // c 1.52 0.03 3.05 0.12 4.57 -0.01
        curveToRelative(
            dx1 = 1.52f,
            dy1 = 0.03f,
            dx2 = 3.05f,
            dy2 = 0.12f,
            dx3 = 4.57f,
            dy3 = -0.01f,
        )
        // a 10 10 0 0 0 4.37 -1.22
        arcToRelative(
            a = 10.0f,
            b = 10.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 4.37f,
            dy1 = -1.22f,
        )
        // a 10 10 0 0 0 3.14 -2.96
        arcToRelative(
            a = 10.0f,
            b = 10.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 3.14f,
            dy1 = -2.96f,
        )
        // c 0.85 -1.17 1.54 -2.45 2.25 -3.72
        curveToRelative(
            dx1 = 0.85f,
            dy1 = -1.17f,
            dx2 = 1.54f,
            dy2 = -2.45f,
            dx3 = 2.25f,
            dy3 = -3.72f,
        )
        // a 23 23 0 0 1 2.36 -3.64
        arcToRelative(
            a = 23.0f,
            b = 23.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 2.36f,
            dy1 = -3.64f,
        )
        // a 9 9 0 0 1 3.4 -2.64
        arcToRelative(
            a = 9.0f,
            b = 9.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 3.4f,
            dy1 = -2.64f,
        )
    }
    // M37.16 177.7 a6.8 6.8 0 0 1 3.98 -.26 9 9 0 0 1 3.61 1.77 c2.14 1.65 3.62 3.97 5.05 6.26 a411 411 0 0 1 9.92 16.86 c2.4 4.31 4.68 8.7 7.62 12.65 1.95 2.62 4.18 5.03 6.17 7.62 s3.76 5.41 4.64 8.56 a16.8 16.8 0 0 1 -1.28 12.26 16.4 16.4 0 0 1 -6.2 6.48 16 16 0 0 1 -8.69 2.14 c-4.82 -.22 -9.23 -2.63 -13.77 -4.26 -8.71 -3.16 -18.14 -3.59 -27.08 -6.05 -3.2 -.87 -6.32 -2.03 -9.53 -2.84 -1.43 -.36 -2.88 -.66 -4.23 -1.23 a7 7 0 0 1 -3.36 -2.72 6 6 0 0 1 -.73 -3.15 10 10 0 0 1 .7 -3.19 c.78 -2.04 2 -3.88 2.78 -5.92 1.19 -3.08 1.34 -6.47 1.12 -9.76 s-.8 -6.56 -1 -9.85 a17 17 0 0 1 .2 -4.41 7.6 7.6 0 0 1 1.98 -3.89 7.7 7.7 0 0 1 4.29 -1.99 20 20 0 0 1 4.78 .01 c1.6 .14 3.2 .32 4.8 .23 a9 9 0 0 0 4.54 -1.39 9 9 0 0 0 2.79 -3.27 c.69 -1.27 1.18 -2.64 1.71 -3.98 a19 19 0 0 1 1.91 -3.89 7.6 7.6 0 0 1 3.28 -2.79
    path(
        fill = SolidColor(Color(0xFFD99A03)),
    ) {
        // M 37.16 177.7
        moveTo(x = 37.16f, y = 177.7f)
        // a 6.8 6.8 0 0 1 3.98 -0.26
        arcToRelative(
            a = 6.8f,
            b = 6.8f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 3.98f,
            dy1 = -0.26f,
        )
        // a 9 9 0 0 1 3.61 1.77
        arcToRelative(
            a = 9.0f,
            b = 9.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 3.61f,
            dy1 = 1.77f,
        )
        // c 2.14 1.65 3.62 3.97 5.05 6.26
        curveToRelative(
            dx1 = 2.14f,
            dy1 = 1.65f,
            dx2 = 3.62f,
            dy2 = 3.97f,
            dx3 = 5.05f,
            dy3 = 6.26f,
        )
        // a 411 411 0 0 1 9.92 16.86
        arcToRelative(
            a = 411.0f,
            b = 411.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 9.92f,
            dy1 = 16.86f,
        )
        // c 2.4 4.31 4.68 8.7 7.62 12.65
        curveToRelative(
            dx1 = 2.4f,
            dy1 = 4.31f,
            dx2 = 4.68f,
            dy2 = 8.7f,
            dx3 = 7.62f,
            dy3 = 12.65f,
        )
        // c 1.95 2.62 4.18 5.03 6.17 7.62
        curveToRelative(
            dx1 = 1.95f,
            dy1 = 2.62f,
            dx2 = 4.18f,
            dy2 = 5.03f,
            dx3 = 6.17f,
            dy3 = 7.62f,
        )
        // s 3.76 5.41 4.64 8.56
        reflectiveCurveToRelative(
            dx1 = 3.76f,
            dy1 = 5.41f,
            dx2 = 4.64f,
            dy2 = 8.56f,
        )
        // a 16.8 16.8 0 0 1 -1.28 12.26
        arcToRelative(
            a = 16.8f,
            b = 16.8f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -1.28f,
            dy1 = 12.26f,
        )
        // a 16.4 16.4 0 0 1 -6.2 6.48
        arcToRelative(
            a = 16.4f,
            b = 16.4f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -6.2f,
            dy1 = 6.48f,
        )
        // a 16 16 0 0 1 -8.69 2.14
        arcToRelative(
            a = 16.0f,
            b = 16.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -8.69f,
            dy1 = 2.14f,
        )
        // c -4.82 -0.22 -9.23 -2.63 -13.77 -4.26
        curveToRelative(
            dx1 = -4.82f,
            dy1 = -0.22f,
            dx2 = -9.23f,
            dy2 = -2.63f,
            dx3 = -13.77f,
            dy3 = -4.26f,
        )
        // c -8.71 -3.16 -18.14 -3.59 -27.08 -6.05
        curveToRelative(
            dx1 = -8.71f,
            dy1 = -3.16f,
            dx2 = -18.14f,
            dy2 = -3.59f,
            dx3 = -27.08f,
            dy3 = -6.05f,
        )
        // c -3.2 -0.87 -6.32 -2.03 -9.53 -2.84
        curveToRelative(
            dx1 = -3.2f,
            dy1 = -0.87f,
            dx2 = -6.32f,
            dy2 = -2.03f,
            dx3 = -9.53f,
            dy3 = -2.84f,
        )
        // c -1.43 -0.36 -2.88 -0.66 -4.23 -1.23
        curveToRelative(
            dx1 = -1.43f,
            dy1 = -0.36f,
            dx2 = -2.88f,
            dy2 = -0.66f,
            dx3 = -4.23f,
            dy3 = -1.23f,
        )
        // a 7 7 0 0 1 -3.36 -2.72
        arcToRelative(
            a = 7.0f,
            b = 7.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -3.36f,
            dy1 = -2.72f,
        )
        // a 6 6 0 0 1 -0.73 -3.15
        arcToRelative(
            a = 6.0f,
            b = 6.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -0.73f,
            dy1 = -3.15f,
        )
        // a 10 10 0 0 1 0.7 -3.19
        arcToRelative(
            a = 10.0f,
            b = 10.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 0.7f,
            dy1 = -3.19f,
        )
        // c 0.78 -2.04 2 -3.88 2.78 -5.92
        curveToRelative(
            dx1 = 0.78f,
            dy1 = -2.04f,
            dx2 = 2.0f,
            dy2 = -3.88f,
            dx3 = 2.78f,
            dy3 = -5.92f,
        )
        // c 1.19 -3.08 1.34 -6.47 1.12 -9.76
        curveToRelative(
            dx1 = 1.19f,
            dy1 = -3.08f,
            dx2 = 1.34f,
            dy2 = -6.47f,
            dx3 = 1.12f,
            dy3 = -9.76f,
        )
        // s -0.8 -6.56 -1 -9.85
        reflectiveCurveToRelative(
            dx1 = -0.8f,
            dy1 = -6.56f,
            dx2 = -1.0f,
            dy2 = -9.85f,
        )
        // a 17 17 0 0 1 0.2 -4.41
        arcToRelative(
            a = 17.0f,
            b = 17.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 0.2f,
            dy1 = -4.41f,
        )
        // a 7.6 7.6 0 0 1 1.98 -3.89
        arcToRelative(
            a = 7.6f,
            b = 7.6f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 1.98f,
            dy1 = -3.89f,
        )
        // a 7.7 7.7 0 0 1 4.29 -1.99
        arcToRelative(
            a = 7.7f,
            b = 7.7f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 4.29f,
            dy1 = -1.99f,
        )
        // a 20 20 0 0 1 4.78 0.01
        arcToRelative(
            a = 20.0f,
            b = 20.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 4.78f,
            dy1 = 0.01f,
        )
        // c 1.6 0.14 3.2 0.32 4.8 0.23
        curveToRelative(
            dx1 = 1.6f,
            dy1 = 0.14f,
            dx2 = 3.2f,
            dy2 = 0.32f,
            dx3 = 4.8f,
            dy3 = 0.23f,
        )
        // a 9 9 0 0 0 4.54 -1.39
        arcToRelative(
            a = 9.0f,
            b = 9.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 4.54f,
            dy1 = -1.39f,
        )
        // a 9 9 0 0 0 2.79 -3.27
        arcToRelative(
            a = 9.0f,
            b = 9.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 2.79f,
            dy1 = -3.27f,
        )
        // c 0.69 -1.27 1.18 -2.64 1.71 -3.98
        curveToRelative(
            dx1 = 0.69f,
            dy1 = -1.27f,
            dx2 = 1.18f,
            dy2 = -2.64f,
            dx3 = 1.71f,
            dy3 = -3.98f,
        )
        // a 19 19 0 0 1 1.91 -3.89
        arcToRelative(
            a = 19.0f,
            b = 19.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 1.91f,
            dy1 = -3.89f,
        )
        // a 7.6 7.6 0 0 1 3.28 -2.79
        arcToRelative(
            a = 7.6f,
            b = 7.6f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 3.28f,
            dy1 = -2.79f,
        )
    }
    // M35.99 174.57 a6.3 6.3 0 0 1 3.98 -.45 8.5 8.5 0 0 1 3.62 1.77 c2.09 1.7 3.43 4.13 4.67 6.51 2.84 5.46 5.5 11.04 8.9 16.19 2.48 3.73 5.33 7.2 7.83 10.92 3.39 5.03 6.15 10.57 7.29 16.5 .76 4 .74 8.31 -1.18 11.9 a13.2 13.2 0 0 1 -5.75 5.52 15 15 0 0 1 -7.92 1.47 c-4.27 -.37 -8.14 -2.47 -12.16 -3.94 -7.13 -2.59 -14.84 -3.22 -22.18 -5.18 -3.09 -.82 -6.13 -1.89 -9.26 -2.54 -1.39 -.29 -2.8 -.5 -4.12 -1 a6 6 0 0 1 -3.25 -2.55 5 5 0 0 1 -.56 -2.84 9 9 0 0 1 .74 -2.83 c.77 -1.8 1.9 -3.46 2.49 -5.32 .88 -2.75 .52 -5.72 -.14 -8.53 -.65 -2.8 -1.6 -5.55 -1.89 -8.41 a11 11 0 0 1 .17 -3.82 6.7 6.7 0 0 1 1.81 -3.34 7.6 7.6 0 0 1 4.56 -1.89 21 21 0 0 1 5.01 .3 c1.66 .24 3.34 .5 5.01 .42 a8 8 0 0 0 4.7 -1.54 8 8 0 0 0 2.59 -4.09 c.47 -1.57 .62 -3.2 .81 -4.82 a18 18 0 0 1 1.06 -4.77 7 7 0 0 1 3.17 -3.64
    path(
        fill = SolidColor(Color(0xFFF5BD0C)),
    ) {
        // M 35.99 174.57
        moveTo(x = 35.99f, y = 174.57f)
        // a 6.3 6.3 0 0 1 3.98 -0.45
        arcToRelative(
            a = 6.3f,
            b = 6.3f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 3.98f,
            dy1 = -0.45f,
        )
        // a 8.5 8.5 0 0 1 3.62 1.77
        arcToRelative(
            a = 8.5f,
            b = 8.5f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 3.62f,
            dy1 = 1.77f,
        )
        // c 2.09 1.7 3.43 4.13 4.67 6.51
        curveToRelative(
            dx1 = 2.09f,
            dy1 = 1.7f,
            dx2 = 3.43f,
            dy2 = 4.13f,
            dx3 = 4.67f,
            dy3 = 6.51f,
        )
        // c 2.84 5.46 5.5 11.04 8.9 16.19
        curveToRelative(
            dx1 = 2.84f,
            dy1 = 5.46f,
            dx2 = 5.5f,
            dy2 = 11.04f,
            dx3 = 8.9f,
            dy3 = 16.19f,
        )
        // c 2.48 3.73 5.33 7.2 7.83 10.92
        curveToRelative(
            dx1 = 2.48f,
            dy1 = 3.73f,
            dx2 = 5.33f,
            dy2 = 7.2f,
            dx3 = 7.83f,
            dy3 = 10.92f,
        )
        // c 3.39 5.03 6.15 10.57 7.29 16.5
        curveToRelative(
            dx1 = 3.39f,
            dy1 = 5.03f,
            dx2 = 6.15f,
            dy2 = 10.57f,
            dx3 = 7.29f,
            dy3 = 16.5f,
        )
        // c 0.76 4 0.74 8.31 -1.18 11.9
        curveToRelative(
            dx1 = 0.76f,
            dy1 = 4.0f,
            dx2 = 0.74f,
            dy2 = 8.31f,
            dx3 = -1.18f,
            dy3 = 11.9f,
        )
        // a 13.2 13.2 0 0 1 -5.75 5.52
        arcToRelative(
            a = 13.2f,
            b = 13.2f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -5.75f,
            dy1 = 5.52f,
        )
        // a 15 15 0 0 1 -7.92 1.47
        arcToRelative(
            a = 15.0f,
            b = 15.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -7.92f,
            dy1 = 1.47f,
        )
        // c -4.27 -0.37 -8.14 -2.47 -12.16 -3.94
        curveToRelative(
            dx1 = -4.27f,
            dy1 = -0.37f,
            dx2 = -8.14f,
            dy2 = -2.47f,
            dx3 = -12.16f,
            dy3 = -3.94f,
        )
        // c -7.13 -2.59 -14.84 -3.22 -22.18 -5.18
        curveToRelative(
            dx1 = -7.13f,
            dy1 = -2.59f,
            dx2 = -14.84f,
            dy2 = -3.22f,
            dx3 = -22.18f,
            dy3 = -5.18f,
        )
        // c -3.09 -0.82 -6.13 -1.89 -9.26 -2.54
        curveToRelative(
            dx1 = -3.09f,
            dy1 = -0.82f,
            dx2 = -6.13f,
            dy2 = -1.89f,
            dx3 = -9.26f,
            dy3 = -2.54f,
        )
        // c -1.39 -0.29 -2.8 -0.5 -4.12 -1
        curveToRelative(
            dx1 = -1.39f,
            dy1 = -0.29f,
            dx2 = -2.8f,
            dy2 = -0.5f,
            dx3 = -4.12f,
            dy3 = -1.0f,
        )
        // a 6 6 0 0 1 -3.25 -2.55
        arcToRelative(
            a = 6.0f,
            b = 6.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -3.25f,
            dy1 = -2.55f,
        )
        // a 5 5 0 0 1 -0.56 -2.84
        arcToRelative(
            a = 5.0f,
            b = 5.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -0.56f,
            dy1 = -2.84f,
        )
        // a 9 9 0 0 1 0.74 -2.83
        arcToRelative(
            a = 9.0f,
            b = 9.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 0.74f,
            dy1 = -2.83f,
        )
        // c 0.77 -1.8 1.9 -3.46 2.49 -5.32
        curveToRelative(
            dx1 = 0.77f,
            dy1 = -1.8f,
            dx2 = 1.9f,
            dy2 = -3.46f,
            dx3 = 2.49f,
            dy3 = -5.32f,
        )
        // c 0.88 -2.75 0.52 -5.72 -0.14 -8.53
        curveToRelative(
            dx1 = 0.88f,
            dy1 = -2.75f,
            dx2 = 0.52f,
            dy2 = -5.72f,
            dx3 = -0.14f,
            dy3 = -8.53f,
        )
        // c -0.65 -2.8 -1.6 -5.55 -1.89 -8.41
        curveToRelative(
            dx1 = -0.65f,
            dy1 = -2.8f,
            dx2 = -1.6f,
            dy2 = -5.55f,
            dx3 = -1.89f,
            dy3 = -8.41f,
        )
        // a 11 11 0 0 1 0.17 -3.82
        arcToRelative(
            a = 11.0f,
            b = 11.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 0.17f,
            dy1 = -3.82f,
        )
        // a 6.7 6.7 0 0 1 1.81 -3.34
        arcToRelative(
            a = 6.7f,
            b = 6.7f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 1.81f,
            dy1 = -3.34f,
        )
        // a 7.6 7.6 0 0 1 4.56 -1.89
        arcToRelative(
            a = 7.6f,
            b = 7.6f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 4.56f,
            dy1 = -1.89f,
        )
        // a 21 21 0 0 1 5.01 0.3
        arcToRelative(
            a = 21.0f,
            b = 21.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 5.01f,
            dy1 = 0.3f,
        )
        // c 1.66 0.24 3.34 0.5 5.01 0.42
        curveToRelative(
            dx1 = 1.66f,
            dy1 = 0.24f,
            dx2 = 3.34f,
            dy2 = 0.5f,
            dx3 = 5.01f,
            dy3 = 0.42f,
        )
        // a 8 8 0 0 0 4.7 -1.54
        arcToRelative(
            a = 8.0f,
            b = 8.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 4.7f,
            dy1 = -1.54f,
        )
        // a 8 8 0 0 0 2.59 -4.09
        arcToRelative(
            a = 8.0f,
            b = 8.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 2.59f,
            dy1 = -4.09f,
        )
        // c 0.47 -1.57 0.62 -3.2 0.81 -4.82
        curveToRelative(
            dx1 = 0.47f,
            dy1 = -1.57f,
            dx2 = 0.62f,
            dy2 = -3.2f,
            dx3 = 0.81f,
            dy3 = -4.82f,
        )
        // a 18 18 0 0 1 1.06 -4.77
        arcToRelative(
            a = 18.0f,
            b = 18.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 1.06f,
            dy1 = -4.77f,
        )
        // a 7 7 0 0 1 3.17 -3.64
        arcToRelative(
            a = 7.0f,
            b = 7.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 3.17f,
            dy1 = -3.64f,
        )
    }
    // M51.2 188.21 c2.25 4.06 3.62 8.72 5.85 12.82 2.05 3.77 4.38 7.65 6.46 11.12 .93 1.55 3.09 3.93 5.27 7.62 1.98 3.34 3.98 8.01 5.1 9.58 -.64 -1.84 -1.96 -6.77 -3.54 -10.28 -1.47 -3.28 -3.19 -5.15 -4.24 -6.92 -2.08 -3.47 -4.33 -6.6 -6.47 -9.91 -2.95 -4.57 -5.2 -9.68 -8.43 -14.03
    path(
        fill = Brush.linearGradient(
            0.0f to Color(0xFFEBC40C),
            1.0f to Color(0x00EBC40C),
            start = Offset(x = 64.47f, y = 210.83f),
            end = Offset(x = 77.41f, y = 235.21f),
        ),
    ) {
        // M 51.2 188.21
        moveTo(x = 51.2f, y = 188.21f)
        // c 2.25 4.06 3.62 8.72 5.85 12.82
        curveToRelative(
            dx1 = 2.25f,
            dy1 = 4.06f,
            dx2 = 3.62f,
            dy2 = 8.72f,
            dx3 = 5.85f,
            dy3 = 12.82f,
        )
        // c 2.05 3.77 4.38 7.65 6.46 11.12
        curveToRelative(
            dx1 = 2.05f,
            dy1 = 3.77f,
            dx2 = 4.38f,
            dy2 = 7.65f,
            dx3 = 6.46f,
            dy3 = 11.12f,
        )
        // c 0.93 1.55 3.09 3.93 5.27 7.62
        curveToRelative(
            dx1 = 0.93f,
            dy1 = 1.55f,
            dx2 = 3.09f,
            dy2 = 3.93f,
            dx3 = 5.27f,
            dy3 = 7.62f,
        )
        // c 1.98 3.34 3.98 8.01 5.1 9.58
        curveToRelative(
            dx1 = 1.98f,
            dy1 = 3.34f,
            dx2 = 3.98f,
            dy2 = 8.01f,
            dx3 = 5.1f,
            dy3 = 9.58f,
        )
        // c -0.64 -1.84 -1.96 -6.77 -3.54 -10.28
        curveToRelative(
            dx1 = -0.64f,
            dy1 = -1.84f,
            dx2 = -1.96f,
            dy2 = -6.77f,
            dx3 = -3.54f,
            dy3 = -10.28f,
        )
        // c -1.47 -3.28 -3.19 -5.15 -4.24 -6.92
        curveToRelative(
            dx1 = -1.47f,
            dy1 = -3.28f,
            dx2 = -3.19f,
            dy2 = -5.15f,
            dx3 = -4.24f,
            dy3 = -6.92f,
        )
        // c -2.08 -3.47 -4.33 -6.6 -6.47 -9.91
        curveToRelative(
            dx1 = -2.08f,
            dy1 = -3.47f,
            dx2 = -4.33f,
            dy2 = -6.6f,
            dx3 = -6.47f,
            dy3 = -9.91f,
        )
        // c -2.95 -4.57 -5.2 -9.68 -8.43 -14.03
        curveToRelative(
            dx1 = -2.95f,
            dy1 = -4.57f,
            dx2 = -5.2f,
            dy2 = -9.68f,
            dx3 = -8.43f,
            dy3 = -14.03f,
        )
    }
    // M198.7 215.61 a14 14 0 0 1 -1.81 3.8 c-1.75 2.59 -4.3 4.55 -6.84 6.35 -4.33 3.07 -8.85 5.89 -12.89 9.38 a65 65 0 0 0 -7.45 7.73 c-1.95 2.36 -3.79 4.84 -6.02 6.94 a21 21 0 0 1 -7.74 4.77 19 19 0 0 1 -10.47 .22 c-2.34 -.6 -4.63 -1.64 -6.08 -3.53 s-1.92 -4.44 -2.09 -6.94 c-.3 -4.42 .23 -8.93 .71 -13.42 .4 -3.73 .77 -7.46 .92 -11.18 a113 113 0 0 0 -1.09 -20.05 c-.16 -1.11 -.32 -2.22 -.23 -3.35 a5.4 5.4 0 0 1 1.27 -3.2 5.4 5.4 0 0 1 2.79 -1.52 c1.02 -.24 2.06 -.25 3.09 -.28 2.43 -.06 4.86 -.21 7.25 .01 1.51 .13 2.99 .41 4.49 .55 a20 20 0 0 0 7.64 -.62 c2.71 -.8 5.29 -2.29 8.05 -2.7 a12 12 0 0 1 3.36 .01 6 6 0 0 1 3.1 1.15 6 6 0 0 1 1.51 1.99 13 13 0 0 1 1.1 3.68 c.17 1.14 .24 2.31 .53 3.41 a10 10 0 0 0 2.89 4.6 29 29 0 0 0 4.39 3.22 50 50 0 0 0 4.7 2.73 c.77 .38 1.56 .72 2.29 1.15 a6 6 0 0 1 1.91 1.67 4.7 4.7 0 0 1 .72 3.43
    path(
        fill = Brush.linearGradient(
            0.0f to Color(0xFF000000),
            1.0f to Color(0x00000000),
            start = Offset(x = 146.93f, y = 211.96f),
            end = Offset(x = 150.2f, y = 235.73f),
        ),
        fillAlpha = 0.2f,
        strokeAlpha = 0.2f,
    ) {
        // M 198.7 215.61
        moveTo(x = 198.7f, y = 215.61f)
        // a 14 14 0 0 1 -1.81 3.8
        arcToRelative(
            a = 14.0f,
            b = 14.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -1.81f,
            dy1 = 3.8f,
        )
        // c -1.75 2.59 -4.3 4.55 -6.84 6.35
        curveToRelative(
            dx1 = -1.75f,
            dy1 = 2.59f,
            dx2 = -4.3f,
            dy2 = 4.55f,
            dx3 = -6.84f,
            dy3 = 6.35f,
        )
        // c -4.33 3.07 -8.85 5.89 -12.89 9.38
        curveToRelative(
            dx1 = -4.33f,
            dy1 = 3.07f,
            dx2 = -8.85f,
            dy2 = 5.89f,
            dx3 = -12.89f,
            dy3 = 9.38f,
        )
        // a 65 65 0 0 0 -7.45 7.73
        arcToRelative(
            a = 65.0f,
            b = 65.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -7.45f,
            dy1 = 7.73f,
        )
        // c -1.95 2.36 -3.79 4.84 -6.02 6.94
        curveToRelative(
            dx1 = -1.95f,
            dy1 = 2.36f,
            dx2 = -3.79f,
            dy2 = 4.84f,
            dx3 = -6.02f,
            dy3 = 6.94f,
        )
        // a 21 21 0 0 1 -7.74 4.77
        arcToRelative(
            a = 21.0f,
            b = 21.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -7.74f,
            dy1 = 4.77f,
        )
        // a 19 19 0 0 1 -10.47 0.22
        arcToRelative(
            a = 19.0f,
            b = 19.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -10.47f,
            dy1 = 0.22f,
        )
        // c -2.34 -0.6 -4.63 -1.64 -6.08 -3.53
        curveToRelative(
            dx1 = -2.34f,
            dy1 = -0.6f,
            dx2 = -4.63f,
            dy2 = -1.64f,
            dx3 = -6.08f,
            dy3 = -3.53f,
        )
        // s -1.92 -4.44 -2.09 -6.94
        reflectiveCurveToRelative(
            dx1 = -1.92f,
            dy1 = -4.44f,
            dx2 = -2.09f,
            dy2 = -6.94f,
        )
        // c -0.3 -4.42 0.23 -8.93 0.71 -13.42
        curveToRelative(
            dx1 = -0.3f,
            dy1 = -4.42f,
            dx2 = 0.23f,
            dy2 = -8.93f,
            dx3 = 0.71f,
            dy3 = -13.42f,
        )
        // c 0.4 -3.73 0.77 -7.46 0.92 -11.18
        curveToRelative(
            dx1 = 0.4f,
            dy1 = -3.73f,
            dx2 = 0.77f,
            dy2 = -7.46f,
            dx3 = 0.92f,
            dy3 = -11.18f,
        )
        // a 113 113 0 0 0 -1.09 -20.05
        arcToRelative(
            a = 113.0f,
            b = 113.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -1.09f,
            dy1 = -20.05f,
        )
        // c -0.16 -1.11 -0.32 -2.22 -0.23 -3.35
        curveToRelative(
            dx1 = -0.16f,
            dy1 = -1.11f,
            dx2 = -0.32f,
            dy2 = -2.22f,
            dx3 = -0.23f,
            dy3 = -3.35f,
        )
        // a 5.4 5.4 0 0 1 1.27 -3.2
        arcToRelative(
            a = 5.4f,
            b = 5.4f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 1.27f,
            dy1 = -3.2f,
        )
        // a 5.4 5.4 0 0 1 2.79 -1.52
        arcToRelative(
            a = 5.4f,
            b = 5.4f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 2.79f,
            dy1 = -1.52f,
        )
        // c 1.02 -0.24 2.06 -0.25 3.09 -0.28
        curveToRelative(
            dx1 = 1.02f,
            dy1 = -0.24f,
            dx2 = 2.06f,
            dy2 = -0.25f,
            dx3 = 3.09f,
            dy3 = -0.28f,
        )
        // c 2.43 -0.06 4.86 -0.21 7.25 0.01
        curveToRelative(
            dx1 = 2.43f,
            dy1 = -0.06f,
            dx2 = 4.86f,
            dy2 = -0.21f,
            dx3 = 7.25f,
            dy3 = 0.01f,
        )
        // c 1.51 0.13 2.99 0.41 4.49 0.55
        curveToRelative(
            dx1 = 1.51f,
            dy1 = 0.13f,
            dx2 = 2.99f,
            dy2 = 0.41f,
            dx3 = 4.49f,
            dy3 = 0.55f,
        )
        // a 20 20 0 0 0 7.64 -0.62
        arcToRelative(
            a = 20.0f,
            b = 20.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 7.64f,
            dy1 = -0.62f,
        )
        // c 2.71 -0.8 5.29 -2.29 8.05 -2.7
        curveToRelative(
            dx1 = 2.71f,
            dy1 = -0.8f,
            dx2 = 5.29f,
            dy2 = -2.29f,
            dx3 = 8.05f,
            dy3 = -2.7f,
        )
        // a 12 12 0 0 1 3.36 0.01
        arcToRelative(
            a = 12.0f,
            b = 12.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 3.36f,
            dy1 = 0.01f,
        )
        // a 6 6 0 0 1 3.1 1.15
        arcToRelative(
            a = 6.0f,
            b = 6.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 3.1f,
            dy1 = 1.15f,
        )
        // a 6 6 0 0 1 1.51 1.99
        arcToRelative(
            a = 6.0f,
            b = 6.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 1.51f,
            dy1 = 1.99f,
        )
        // a 13 13 0 0 1 1.1 3.68
        arcToRelative(
            a = 13.0f,
            b = 13.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 1.1f,
            dy1 = 3.68f,
        )
        // c 0.17 1.14 0.24 2.31 0.53 3.41
        curveToRelative(
            dx1 = 0.17f,
            dy1 = 1.14f,
            dx2 = 0.24f,
            dy2 = 2.31f,
            dx3 = 0.53f,
            dy3 = 3.41f,
        )
        // a 10 10 0 0 0 2.89 4.6
        arcToRelative(
            a = 10.0f,
            b = 10.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 2.89f,
            dy1 = 4.6f,
        )
        // a 29 29 0 0 0 4.39 3.22
        arcToRelative(
            a = 29.0f,
            b = 29.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 4.39f,
            dy1 = 3.22f,
        )
        // a 50 50 0 0 0 4.7 2.73
        arcToRelative(
            a = 50.0f,
            b = 50.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 4.7f,
            dy1 = 2.73f,
        )
        // c 0.77 0.38 1.56 0.72 2.29 1.15
        curveToRelative(
            dx1 = 0.77f,
            dy1 = 0.38f,
            dx2 = 1.56f,
            dy2 = 0.72f,
            dx3 = 2.29f,
            dy3 = 1.15f,
        )
        // a 6 6 0 0 1 1.91 1.67
        arcToRelative(
            a = 6.0f,
            b = 6.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 1.91f,
            dy1 = 1.67f,
        )
        // a 4.7 4.7 0 0 1 0.72 3.43
        arcToRelative(
            a = 4.7f,
            b = 4.7f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 0.72f,
            dy1 = 3.43f,
        )
    }
    // M213.47 222.92 c-2.26 2.68 -5.4 4.45 -8.53 6.05 -5.33 2.71 -10.86 5.1 -15.87 8.37 a65 65 0 0 0 -9.36 7.53 c-2.48 2.37 -4.83 4.9 -7.61 6.91 a22 22 0 0 1 -9.48 4.01 20 20 0 0 1 -2.86 .21 c-3.24 0 -6.48 -.78 -9.46 -2.08 -2.7 -1.17 -5.3 -2.86 -6.86 -5.36 -1.56 -2.52 -1.92 -5.59 -1.92 -8.56 -.01 -5.23 .96 -10.41 1.87 -15.57 .76 -4.29 1.48 -8.58 1.95 -12.91 a133 133 0 0 0 .28 -23.71 c-.1 -1.32 -.21 -2.65 -.01 -3.96 a5.7 5.7 0 0 1 1.74 -3.48 5.6 5.6 0 0 1 3.4 -1.22 c1.22 -.07 2.44 .12 3.65 .3 2.85 .42 5.73 .74 8.52 1.48 1.76 .46 3.48 1.08 5.23 1.56 a24 24 0 0 0 9.02 .82 c3.25 -.38 6.41 -1.6 9.68 -1.52 a14 14 0 0 1 3.95 .69 c1.3 .41 2.59 1 3.55 1.98 a8 8 0 0 1 1.62 2.64 16 16 0 0 1 1.01 4.52 c.11 1.37 .09 2.76 .35 4.11 a13.6 13.6 0 0 0 3.04 5.97 38 38 0 0 0 4.91 4.66 69 69 0 0 0 5.32 4.16 c.87 .6 1.77 1.16 2.6 1.81 a8 8 0 0 1 2.11 2.34 5.5 5.5 0 0 1 .46 4.21 12 12 0 0 1 -2.3 4.04
    path(
        fill = Brush.linearGradient(
            0.0f to Color(0xFF3E2A06),
            1.0f to Color(0xFFAD780A),
            start = Offset(x = 151.5f, y = 253.02f),
            end = Offset(x = 192.94f, y = 185.84f),
        ),
    ) {
        // M 213.47 222.92
        moveTo(x = 213.47f, y = 222.92f)
        // c -2.26 2.68 -5.4 4.45 -8.53 6.05
        curveToRelative(
            dx1 = -2.26f,
            dy1 = 2.68f,
            dx2 = -5.4f,
            dy2 = 4.45f,
            dx3 = -8.53f,
            dy3 = 6.05f,
        )
        // c -5.33 2.71 -10.86 5.1 -15.87 8.37
        curveToRelative(
            dx1 = -5.33f,
            dy1 = 2.71f,
            dx2 = -10.86f,
            dy2 = 5.1f,
            dx3 = -15.87f,
            dy3 = 8.37f,
        )
        // a 65 65 0 0 0 -9.36 7.53
        arcToRelative(
            a = 65.0f,
            b = 65.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -9.36f,
            dy1 = 7.53f,
        )
        // c -2.48 2.37 -4.83 4.9 -7.61 6.91
        curveToRelative(
            dx1 = -2.48f,
            dy1 = 2.37f,
            dx2 = -4.83f,
            dy2 = 4.9f,
            dx3 = -7.61f,
            dy3 = 6.91f,
        )
        // a 22 22 0 0 1 -9.48 4.01
        arcToRelative(
            a = 22.0f,
            b = 22.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -9.48f,
            dy1 = 4.01f,
        )
        // a 20 20 0 0 1 -2.86 0.21
        arcToRelative(
            a = 20.0f,
            b = 20.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -2.86f,
            dy1 = 0.21f,
        )
        // c -3.24 0 -6.48 -0.78 -9.46 -2.08
        curveToRelative(
            dx1 = -3.24f,
            dy1 = 0.0f,
            dx2 = -6.48f,
            dy2 = -0.78f,
            dx3 = -9.46f,
            dy3 = -2.08f,
        )
        // c -2.7 -1.17 -5.3 -2.86 -6.86 -5.36
        curveToRelative(
            dx1 = -2.7f,
            dy1 = -1.17f,
            dx2 = -5.3f,
            dy2 = -2.86f,
            dx3 = -6.86f,
            dy3 = -5.36f,
        )
        // c -1.56 -2.52 -1.92 -5.59 -1.92 -8.56
        curveToRelative(
            dx1 = -1.56f,
            dy1 = -2.52f,
            dx2 = -1.92f,
            dy2 = -5.59f,
            dx3 = -1.92f,
            dy3 = -8.56f,
        )
        // c -0.01 -5.23 0.96 -10.41 1.87 -15.57
        curveToRelative(
            dx1 = -0.01f,
            dy1 = -5.23f,
            dx2 = 0.96f,
            dy2 = -10.41f,
            dx3 = 1.87f,
            dy3 = -15.57f,
        )
        // c 0.76 -4.29 1.48 -8.58 1.95 -12.91
        curveToRelative(
            dx1 = 0.76f,
            dy1 = -4.29f,
            dx2 = 1.48f,
            dy2 = -8.58f,
            dx3 = 1.95f,
            dy3 = -12.91f,
        )
        // a 133 133 0 0 0 0.28 -23.71
        arcToRelative(
            a = 133.0f,
            b = 133.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.28f,
            dy1 = -23.71f,
        )
        // c -0.1 -1.32 -0.21 -2.65 -0.01 -3.96
        curveToRelative(
            dx1 = -0.1f,
            dy1 = -1.32f,
            dx2 = -0.21f,
            dy2 = -2.65f,
            dx3 = -0.01f,
            dy3 = -3.96f,
        )
        // a 5.7 5.7 0 0 1 1.74 -3.48
        arcToRelative(
            a = 5.7f,
            b = 5.7f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 1.74f,
            dy1 = -3.48f,
        )
        // a 5.6 5.6 0 0 1 3.4 -1.22
        arcToRelative(
            a = 5.6f,
            b = 5.6f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 3.4f,
            dy1 = -1.22f,
        )
        // c 1.22 -0.07 2.44 0.12 3.65 0.3
        curveToRelative(
            dx1 = 1.22f,
            dy1 = -0.07f,
            dx2 = 2.44f,
            dy2 = 0.12f,
            dx3 = 3.65f,
            dy3 = 0.3f,
        )
        // c 2.85 0.42 5.73 0.74 8.52 1.48
        curveToRelative(
            dx1 = 2.85f,
            dy1 = 0.42f,
            dx2 = 5.73f,
            dy2 = 0.74f,
            dx3 = 8.52f,
            dy3 = 1.48f,
        )
        // c 1.76 0.46 3.48 1.08 5.23 1.56
        curveToRelative(
            dx1 = 1.76f,
            dy1 = 0.46f,
            dx2 = 3.48f,
            dy2 = 1.08f,
            dx3 = 5.23f,
            dy3 = 1.56f,
        )
        // a 24 24 0 0 0 9.02 0.82
        arcToRelative(
            a = 24.0f,
            b = 24.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 9.02f,
            dy1 = 0.82f,
        )
        // c 3.25 -0.38 6.41 -1.6 9.68 -1.52
        curveToRelative(
            dx1 = 3.25f,
            dy1 = -0.38f,
            dx2 = 6.41f,
            dy2 = -1.6f,
            dx3 = 9.68f,
            dy3 = -1.52f,
        )
        // a 14 14 0 0 1 3.95 0.69
        arcToRelative(
            a = 14.0f,
            b = 14.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 3.95f,
            dy1 = 0.69f,
        )
        // c 1.3 0.41 2.59 1 3.55 1.98
        curveToRelative(
            dx1 = 1.3f,
            dy1 = 0.41f,
            dx2 = 2.59f,
            dy2 = 1.0f,
            dx3 = 3.55f,
            dy3 = 1.98f,
        )
        // a 8 8 0 0 1 1.62 2.64
        arcToRelative(
            a = 8.0f,
            b = 8.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 1.62f,
            dy1 = 2.64f,
        )
        // a 16 16 0 0 1 1.01 4.52
        arcToRelative(
            a = 16.0f,
            b = 16.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 1.01f,
            dy1 = 4.52f,
        )
        // c 0.11 1.37 0.09 2.76 0.35 4.11
        curveToRelative(
            dx1 = 0.11f,
            dy1 = 1.37f,
            dx2 = 0.09f,
            dy2 = 2.76f,
            dx3 = 0.35f,
            dy3 = 4.11f,
        )
        // a 13.6 13.6 0 0 0 3.04 5.97
        arcToRelative(
            a = 13.6f,
            b = 13.6f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 3.04f,
            dy1 = 5.97f,
        )
        // a 38 38 0 0 0 4.91 4.66
        arcToRelative(
            a = 38.0f,
            b = 38.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 4.91f,
            dy1 = 4.66f,
        )
        // a 69 69 0 0 0 5.32 4.16
        arcToRelative(
            a = 69.0f,
            b = 69.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 5.32f,
            dy1 = 4.16f,
        )
        // c 0.87 0.6 1.77 1.16 2.6 1.81
        curveToRelative(
            dx1 = 0.87f,
            dy1 = 0.6f,
            dx2 = 1.77f,
            dy2 = 1.16f,
            dx3 = 2.6f,
            dy3 = 1.81f,
        )
        // a 8 8 0 0 1 2.11 2.34
        arcToRelative(
            a = 8.0f,
            b = 8.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 2.11f,
            dy1 = 2.34f,
        )
        // a 5.5 5.5 0 0 1 0.46 4.21
        arcToRelative(
            a = 5.5f,
            b = 5.5f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 0.46f,
            dy1 = 4.21f,
        )
        // a 12 12 0 0 1 -2.3 4.04
        arcToRelative(
            a = 12.0f,
            b = 12.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -2.3f,
            dy1 = 4.04f,
        )
    }
    // M213.21 216.12 a13 13 0 0 1 -2.22 3.67 c-2.07 2.42 -4.93 4.01 -7.78 5.44 -4.88 2.44 -9.92 4.58 -14.5 7.52 a59 59 0 0 0 -8.55 6.78 c-2.26 2.13 -4.41 4.41 -6.95 6.21 a20 20 0 0 1 -8.65 3.6 21 21 0 0 1 -11.25 -1.67 c-2.46 -1.06 -4.84 -2.56 -6.27 -4.83 -1.42 -2.26 -1.75 -5.02 -1.75 -7.69 -.02 -4.71 .87 -9.37 1.71 -14 .7 -3.85 1.36 -7.71 1.78 -11.6 a122 122 0 0 0 .25 -21.32 c-.08 -1.19 -.17 -2.39 .01 -3.57 a5 5 0 0 1 1.57 -3.13 5 5 0 0 1 3.11 -1.1 c1.11 -.06 2.22 .12 3.33 .28 2.61 .38 5.23 .67 7.78 1.33 1.61 .42 3.18 .98 4.78 1.4 a22 22 0 0 0 8.24 .74 c2.97 -.34 5.85 -1.44 8.83 -1.37 a13 13 0 0 1 3.61 .62 8 8 0 0 1 3.25 1.78 8 8 0 0 1 1.48 2.38 11 11 0 0 1 .91 4.07 c.03 1.46 -.28 2.92 -.09 4.37 a8 8 0 0 0 1.3 3.28 c.63 1 1.4 1.91 2.17 2.81 1.48 1.75 2.96 3.53 4.82 4.87 2.11 1.53 4.62 2.43 6.8 3.85 a7 7 0 0 1 1.74 1.54 4.1 4.1 0 0 1 .54 3.74
    path(
        fill = SolidColor(Color(0xFFCD8907)),
    ) {
        // M 213.21 216.12
        moveTo(x = 213.21f, y = 216.12f)
        // a 13 13 0 0 1 -2.22 3.67
        arcToRelative(
            a = 13.0f,
            b = 13.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -2.22f,
            dy1 = 3.67f,
        )
        // c -2.07 2.42 -4.93 4.01 -7.78 5.44
        curveToRelative(
            dx1 = -2.07f,
            dy1 = 2.42f,
            dx2 = -4.93f,
            dy2 = 4.01f,
            dx3 = -7.78f,
            dy3 = 5.44f,
        )
        // c -4.88 2.44 -9.92 4.58 -14.5 7.52
        curveToRelative(
            dx1 = -4.88f,
            dy1 = 2.44f,
            dx2 = -9.92f,
            dy2 = 4.58f,
            dx3 = -14.5f,
            dy3 = 7.52f,
        )
        // a 59 59 0 0 0 -8.55 6.78
        arcToRelative(
            a = 59.0f,
            b = 59.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -8.55f,
            dy1 = 6.78f,
        )
        // c -2.26 2.13 -4.41 4.41 -6.95 6.21
        curveToRelative(
            dx1 = -2.26f,
            dy1 = 2.13f,
            dx2 = -4.41f,
            dy2 = 4.41f,
            dx3 = -6.95f,
            dy3 = 6.21f,
        )
        // a 20 20 0 0 1 -8.65 3.6
        arcToRelative(
            a = 20.0f,
            b = 20.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -8.65f,
            dy1 = 3.6f,
        )
        // a 21 21 0 0 1 -11.25 -1.67
        arcToRelative(
            a = 21.0f,
            b = 21.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -11.25f,
            dy1 = -1.67f,
        )
        // c -2.46 -1.06 -4.84 -2.56 -6.27 -4.83
        curveToRelative(
            dx1 = -2.46f,
            dy1 = -1.06f,
            dx2 = -4.84f,
            dy2 = -2.56f,
            dx3 = -6.27f,
            dy3 = -4.83f,
        )
        // c -1.42 -2.26 -1.75 -5.02 -1.75 -7.69
        curveToRelative(
            dx1 = -1.42f,
            dy1 = -2.26f,
            dx2 = -1.75f,
            dy2 = -5.02f,
            dx3 = -1.75f,
            dy3 = -7.69f,
        )
        // c -0.02 -4.71 0.87 -9.37 1.71 -14
        curveToRelative(
            dx1 = -0.02f,
            dy1 = -4.71f,
            dx2 = 0.87f,
            dy2 = -9.37f,
            dx3 = 1.71f,
            dy3 = -14.0f,
        )
        // c 0.7 -3.85 1.36 -7.71 1.78 -11.6
        curveToRelative(
            dx1 = 0.7f,
            dy1 = -3.85f,
            dx2 = 1.36f,
            dy2 = -7.71f,
            dx3 = 1.78f,
            dy3 = -11.6f,
        )
        // a 122 122 0 0 0 0.25 -21.32
        arcToRelative(
            a = 122.0f,
            b = 122.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.25f,
            dy1 = -21.32f,
        )
        // c -0.08 -1.19 -0.17 -2.39 0.01 -3.57
        curveToRelative(
            dx1 = -0.08f,
            dy1 = -1.19f,
            dx2 = -0.17f,
            dy2 = -2.39f,
            dx3 = 0.01f,
            dy3 = -3.57f,
        )
        // a 5 5 0 0 1 1.57 -3.13
        arcToRelative(
            a = 5.0f,
            b = 5.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 1.57f,
            dy1 = -3.13f,
        )
        // a 5 5 0 0 1 3.11 -1.1
        arcToRelative(
            a = 5.0f,
            b = 5.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 3.11f,
            dy1 = -1.1f,
        )
        // c 1.11 -0.06 2.22 0.12 3.33 0.28
        curveToRelative(
            dx1 = 1.11f,
            dy1 = -0.06f,
            dx2 = 2.22f,
            dy2 = 0.12f,
            dx3 = 3.33f,
            dy3 = 0.28f,
        )
        // c 2.61 0.38 5.23 0.67 7.78 1.33
        curveToRelative(
            dx1 = 2.61f,
            dy1 = 0.38f,
            dx2 = 5.23f,
            dy2 = 0.67f,
            dx3 = 7.78f,
            dy3 = 1.33f,
        )
        // c 1.61 0.42 3.18 0.98 4.78 1.4
        curveToRelative(
            dx1 = 1.61f,
            dy1 = 0.42f,
            dx2 = 3.18f,
            dy2 = 0.98f,
            dx3 = 4.78f,
            dy3 = 1.4f,
        )
        // a 22 22 0 0 0 8.24 0.74
        arcToRelative(
            a = 22.0f,
            b = 22.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 8.24f,
            dy1 = 0.74f,
        )
        // c 2.97 -0.34 5.85 -1.44 8.83 -1.37
        curveToRelative(
            dx1 = 2.97f,
            dy1 = -0.34f,
            dx2 = 5.85f,
            dy2 = -1.44f,
            dx3 = 8.83f,
            dy3 = -1.37f,
        )
        // a 13 13 0 0 1 3.61 0.62
        arcToRelative(
            a = 13.0f,
            b = 13.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 3.61f,
            dy1 = 0.62f,
        )
        // a 8 8 0 0 1 3.25 1.78
        arcToRelative(
            a = 8.0f,
            b = 8.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 3.25f,
            dy1 = 1.78f,
        )
        // a 8 8 0 0 1 1.48 2.38
        arcToRelative(
            a = 8.0f,
            b = 8.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 1.48f,
            dy1 = 2.38f,
        )
        // a 11 11 0 0 1 0.91 4.07
        arcToRelative(
            a = 11.0f,
            b = 11.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 0.91f,
            dy1 = 4.07f,
        )
        // c 0.03 1.46 -0.28 2.92 -0.09 4.37
        curveToRelative(
            dx1 = 0.03f,
            dy1 = 1.46f,
            dx2 = -0.28f,
            dy2 = 2.92f,
            dx3 = -0.09f,
            dy3 = 4.37f,
        )
        // a 8 8 0 0 0 1.3 3.28
        arcToRelative(
            a = 8.0f,
            b = 8.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 1.3f,
            dy1 = 3.28f,
        )
        // c 0.63 1 1.4 1.91 2.17 2.81
        curveToRelative(
            dx1 = 0.63f,
            dy1 = 1.0f,
            dx2 = 1.4f,
            dy2 = 1.91f,
            dx3 = 2.17f,
            dy3 = 2.81f,
        )
        // c 1.48 1.75 2.96 3.53 4.82 4.87
        curveToRelative(
            dx1 = 1.48f,
            dy1 = 1.75f,
            dx2 = 2.96f,
            dy2 = 3.53f,
            dx3 = 4.82f,
            dy3 = 4.87f,
        )
        // c 2.11 1.53 4.62 2.43 6.8 3.85
        curveToRelative(
            dx1 = 2.11f,
            dy1 = 1.53f,
            dx2 = 4.62f,
            dy2 = 2.43f,
            dx3 = 6.8f,
            dy3 = 3.85f,
        )
        // a 7 7 0 0 1 1.74 1.54
        arcToRelative(
            a = 7.0f,
            b = 7.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 1.74f,
            dy1 = 1.54f,
        )
        // a 4.1 4.1 0 0 1 0.54 3.74
        arcToRelative(
            a = 4.1f,
            b = 4.1f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 0.54f,
            dy1 = 3.74f,
        )
    }
    // M212.91 214.61 a16 16 0 0 1 -2.28 3.71 19 19 0 0 1 -8 5.49 c-4.97 1.88 -10.39 2.13 -15.26 4.27 -2.97 1.3 -5.65 3.26 -8.36 5.12 a42 42 0 0 1 -6.82 3.98 35 35 0 0 1 -8.5 2.32 27 27 0 0 1 -5.57 .41 12 12 0 0 1 -5.37 -1.49 7.5 7.5 0 0 1 -3.03 -3.1 c-.73 -1.49 -.86 -3.24 -.85 -4.94 .05 -4.5 1.02 -8.96 .99 -13.47 -.03 -3.93 -.81 -7.8 -1.03 -11.72 -.43 -7.54 1.19 -15.2 -.24 -22.59 -.22 -1.19 -.53 -2.37 -.52 -3.58 a5 5 0 0 1 .31 -1.77 3.5 3.5 0 0 1 1.01 -1.42 3 3 0 0 1 1.31 -.56 5 5 0 0 1 1.41 .01 c.93 .15 1.82 .51 2.73 .78 2.6 .78 5.35 .76 8 1.35 1.66 .36 3.26 .97 4.91 1.41 a22 22 0 0 0 8.46 .75 c3.04 -.36 6.01 -1.46 9.07 -1.38 a14 14 0 0 1 3.71 .62 7.6 7.6 0 0 1 3.34 1.8 8 8 0 0 1 1.51 2.4 10 10 0 0 1 .95 4.11 c-.01 .74 -.12 1.47 -.19 2.21 a7 7 0 0 0 .09 2.2 6 6 0 0 0 .97 1.96 c.42 .59 .9 1.12 1.34 1.7 1.22 1.61 2.1 3.49 3.05 5.3 s2.02 3.6 3.53 4.91 c2.05 1.77 4.7 2.48 6.99 3.89 a6 6 0 0 1 1.78 1.55 4.1 4.1 0 0 1 .56 3.77
    path(
        fill = SolidColor(Color(0xFFF5C021)),
    ) {
        // M 212.91 214.61
        moveTo(x = 212.91f, y = 214.61f)
        // a 16 16 0 0 1 -2.28 3.71
        arcToRelative(
            a = 16.0f,
            b = 16.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -2.28f,
            dy1 = 3.71f,
        )
        // a 19 19 0 0 1 -8 5.49
        arcToRelative(
            a = 19.0f,
            b = 19.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -8.0f,
            dy1 = 5.49f,
        )
        // c -4.97 1.88 -10.39 2.13 -15.26 4.27
        curveToRelative(
            dx1 = -4.97f,
            dy1 = 1.88f,
            dx2 = -10.39f,
            dy2 = 2.13f,
            dx3 = -15.26f,
            dy3 = 4.27f,
        )
        // c -2.97 1.3 -5.65 3.26 -8.36 5.12
        curveToRelative(
            dx1 = -2.97f,
            dy1 = 1.3f,
            dx2 = -5.65f,
            dy2 = 3.26f,
            dx3 = -8.36f,
            dy3 = 5.12f,
        )
        // a 42 42 0 0 1 -6.82 3.98
        arcToRelative(
            a = 42.0f,
            b = 42.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -6.82f,
            dy1 = 3.98f,
        )
        // a 35 35 0 0 1 -8.5 2.32
        arcToRelative(
            a = 35.0f,
            b = 35.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -8.5f,
            dy1 = 2.32f,
        )
        // a 27 27 0 0 1 -5.57 0.41
        arcToRelative(
            a = 27.0f,
            b = 27.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -5.57f,
            dy1 = 0.41f,
        )
        // a 12 12 0 0 1 -5.37 -1.49
        arcToRelative(
            a = 12.0f,
            b = 12.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -5.37f,
            dy1 = -1.49f,
        )
        // a 7.5 7.5 0 0 1 -3.03 -3.1
        arcToRelative(
            a = 7.5f,
            b = 7.5f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -3.03f,
            dy1 = -3.1f,
        )
        // c -0.73 -1.49 -0.86 -3.24 -0.85 -4.94
        curveToRelative(
            dx1 = -0.73f,
            dy1 = -1.49f,
            dx2 = -0.86f,
            dy2 = -3.24f,
            dx3 = -0.85f,
            dy3 = -4.94f,
        )
        // c 0.05 -4.5 1.02 -8.96 0.99 -13.47
        curveToRelative(
            dx1 = 0.05f,
            dy1 = -4.5f,
            dx2 = 1.02f,
            dy2 = -8.96f,
            dx3 = 0.99f,
            dy3 = -13.47f,
        )
        // c -0.03 -3.93 -0.81 -7.8 -1.03 -11.72
        curveToRelative(
            dx1 = -0.03f,
            dy1 = -3.93f,
            dx2 = -0.81f,
            dy2 = -7.8f,
            dx3 = -1.03f,
            dy3 = -11.72f,
        )
        // c -0.43 -7.54 1.19 -15.2 -0.24 -22.59
        curveToRelative(
            dx1 = -0.43f,
            dy1 = -7.54f,
            dx2 = 1.19f,
            dy2 = -15.2f,
            dx3 = -0.24f,
            dy3 = -22.59f,
        )
        // c -0.22 -1.19 -0.53 -2.37 -0.52 -3.58
        curveToRelative(
            dx1 = -0.22f,
            dy1 = -1.19f,
            dx2 = -0.53f,
            dy2 = -2.37f,
            dx3 = -0.52f,
            dy3 = -3.58f,
        )
        // a 5 5 0 0 1 0.31 -1.77
        arcToRelative(
            a = 5.0f,
            b = 5.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 0.31f,
            dy1 = -1.77f,
        )
        // a 3.5 3.5 0 0 1 1.01 -1.42
        arcToRelative(
            a = 3.5f,
            b = 3.5f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 1.01f,
            dy1 = -1.42f,
        )
        // a 3 3 0 0 1 1.31 -0.56
        arcToRelative(
            a = 3.0f,
            b = 3.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 1.31f,
            dy1 = -0.56f,
        )
        // a 5 5 0 0 1 1.41 0.01
        arcToRelative(
            a = 5.0f,
            b = 5.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 1.41f,
            dy1 = 0.01f,
        )
        // c 0.93 0.15 1.82 0.51 2.73 0.78
        curveToRelative(
            dx1 = 0.93f,
            dy1 = 0.15f,
            dx2 = 1.82f,
            dy2 = 0.51f,
            dx3 = 2.73f,
            dy3 = 0.78f,
        )
        // c 2.6 0.78 5.35 0.76 8 1.35
        curveToRelative(
            dx1 = 2.6f,
            dy1 = 0.78f,
            dx2 = 5.35f,
            dy2 = 0.76f,
            dx3 = 8.0f,
            dy3 = 1.35f,
        )
        // c 1.66 0.36 3.26 0.97 4.91 1.41
        curveToRelative(
            dx1 = 1.66f,
            dy1 = 0.36f,
            dx2 = 3.26f,
            dy2 = 0.97f,
            dx3 = 4.91f,
            dy3 = 1.41f,
        )
        // a 22 22 0 0 0 8.46 0.75
        arcToRelative(
            a = 22.0f,
            b = 22.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 8.46f,
            dy1 = 0.75f,
        )
        // c 3.04 -0.36 6.01 -1.46 9.07 -1.38
        curveToRelative(
            dx1 = 3.04f,
            dy1 = -0.36f,
            dx2 = 6.01f,
            dy2 = -1.46f,
            dx3 = 9.07f,
            dy3 = -1.38f,
        )
        // a 14 14 0 0 1 3.71 0.62
        arcToRelative(
            a = 14.0f,
            b = 14.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 3.71f,
            dy1 = 0.62f,
        )
        // a 7.6 7.6 0 0 1 3.34 1.8
        arcToRelative(
            a = 7.6f,
            b = 7.6f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 3.34f,
            dy1 = 1.8f,
        )
        // a 8 8 0 0 1 1.51 2.4
        arcToRelative(
            a = 8.0f,
            b = 8.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 1.51f,
            dy1 = 2.4f,
        )
        // a 10 10 0 0 1 0.95 4.11
        arcToRelative(
            a = 10.0f,
            b = 10.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 0.95f,
            dy1 = 4.11f,
        )
        // c -0.01 0.74 -0.12 1.47 -0.19 2.21
        curveToRelative(
            dx1 = -0.01f,
            dy1 = 0.74f,
            dx2 = -0.12f,
            dy2 = 1.47f,
            dx3 = -0.19f,
            dy3 = 2.21f,
        )
        // a 7 7 0 0 0 0.09 2.2
        arcToRelative(
            a = 7.0f,
            b = 7.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.09f,
            dy1 = 2.2f,
        )
        // a 6 6 0 0 0 0.97 1.96
        arcToRelative(
            a = 6.0f,
            b = 6.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.97f,
            dy1 = 1.96f,
        )
        // c 0.42 0.59 0.9 1.12 1.34 1.7
        curveToRelative(
            dx1 = 0.42f,
            dy1 = 0.59f,
            dx2 = 0.9f,
            dy2 = 1.12f,
            dx3 = 1.34f,
            dy3 = 1.7f,
        )
        // c 1.22 1.61 2.1 3.49 3.05 5.3
        curveToRelative(
            dx1 = 1.22f,
            dy1 = 1.61f,
            dx2 = 2.1f,
            dy2 = 3.49f,
            dx3 = 3.05f,
            dy3 = 5.3f,
        )
        // s 2.02 3.6 3.53 4.91
        reflectiveCurveToRelative(
            dx1 = 2.02f,
            dy1 = 3.6f,
            dx2 = 3.53f,
            dy2 = 4.91f,
        )
        // c 2.05 1.77 4.7 2.48 6.99 3.89
        curveToRelative(
            dx1 = 2.05f,
            dy1 = 1.77f,
            dx2 = 4.7f,
            dy2 = 2.48f,
            dx3 = 6.99f,
            dy3 = 3.89f,
        )
        // a 6 6 0 0 1 1.78 1.55
        arcToRelative(
            a = 6.0f,
            b = 6.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 1.78f,
            dy1 = 1.55f,
        )
        // a 4.1 4.1 0 0 1 0.56 3.77
        arcToRelative(
            a = 4.1f,
            b = 4.1f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 0.56f,
            dy1 = 3.77f,
        )
    }
    // M148.08 181.58 c2.82 -.76 5.22 1.38 7.27 2.99 1.32 1.13 3.24 .85 4.86 .9 2.69 -.09 5.36 .45 8.05 .12 5.3 -.45 10.49 -1.75 15.81 -1.97 2.54 -.16 5.4 -.31 7.59 1.17 .89 .62 2.2 3.23 3.07 2.25 -.36 -2.74 -2.39 -5.39 -5.11 -6.12 -2.14 -.34 -4.3 .25 -6.46 .06 -6.39 -.15 -12.75 -1.34 -19.16 -1 -4.46 .04 -8.91 -.17 -13.37 -.34 -1.75 -.36 -2.37 1.19 -3.32 1.79 .25 .19 .34 .25 .77 .15
    path(
        fill = Brush.linearGradient(
            0.0f to Color(0xFFF3CD0C),
            1.0f to Color(0x00F3CD0C),
            start = Offset(x = 162.81f, y = 180.67f),
            end = Offset(x = 161.59f, y = 191.64f),
        ),
    ) {
        // M 148.08 181.58
        moveTo(x = 148.08f, y = 181.58f)
        // c 2.82 -0.76 5.22 1.38 7.27 2.99
        curveToRelative(
            dx1 = 2.82f,
            dy1 = -0.76f,
            dx2 = 5.22f,
            dy2 = 1.38f,
            dx3 = 7.27f,
            dy3 = 2.99f,
        )
        // c 1.32 1.13 3.24 0.85 4.86 0.9
        curveToRelative(
            dx1 = 1.32f,
            dy1 = 1.13f,
            dx2 = 3.24f,
            dy2 = 0.85f,
            dx3 = 4.86f,
            dy3 = 0.9f,
        )
        // c 2.69 -0.09 5.36 0.45 8.05 0.12
        curveToRelative(
            dx1 = 2.69f,
            dy1 = -0.09f,
            dx2 = 5.36f,
            dy2 = 0.45f,
            dx3 = 8.05f,
            dy3 = 0.12f,
        )
        // c 5.3 -0.45 10.49 -1.75 15.81 -1.97
        curveToRelative(
            dx1 = 5.3f,
            dy1 = -0.45f,
            dx2 = 10.49f,
            dy2 = -1.75f,
            dx3 = 15.81f,
            dy3 = -1.97f,
        )
        // c 2.54 -0.16 5.4 -0.31 7.59 1.17
        curveToRelative(
            dx1 = 2.54f,
            dy1 = -0.16f,
            dx2 = 5.4f,
            dy2 = -0.31f,
            dx3 = 7.59f,
            dy3 = 1.17f,
        )
        // c 0.89 0.62 2.2 3.23 3.07 2.25
        curveToRelative(
            dx1 = 0.89f,
            dy1 = 0.62f,
            dx2 = 2.2f,
            dy2 = 3.23f,
            dx3 = 3.07f,
            dy3 = 2.25f,
        )
        // c -0.36 -2.74 -2.39 -5.39 -5.11 -6.12
        curveToRelative(
            dx1 = -0.36f,
            dy1 = -2.74f,
            dx2 = -2.39f,
            dy2 = -5.39f,
            dx3 = -5.11f,
            dy3 = -6.12f,
        )
        // c -2.14 -0.34 -4.3 0.25 -6.46 0.06
        curveToRelative(
            dx1 = -2.14f,
            dy1 = -0.34f,
            dx2 = -4.3f,
            dy2 = 0.25f,
            dx3 = -6.46f,
            dy3 = 0.06f,
        )
        // c -6.39 -0.15 -12.75 -1.34 -19.16 -1
        curveToRelative(
            dx1 = -6.39f,
            dy1 = -0.15f,
            dx2 = -12.75f,
            dy2 = -1.34f,
            dx3 = -19.16f,
            dy3 = -1.0f,
        )
        // c -4.46 0.04 -8.91 -0.17 -13.37 -0.34
        curveToRelative(
            dx1 = -4.46f,
            dy1 = 0.04f,
            dx2 = -8.91f,
            dy2 = -0.17f,
            dx3 = -13.37f,
            dy3 = -0.34f,
        )
        // c -1.75 -0.36 -2.37 1.19 -3.32 1.79
        curveToRelative(
            dx1 = -1.75f,
            dy1 = -0.36f,
            dx2 = -2.37f,
            dy2 = 1.19f,
            dx3 = -3.32f,
            dy3 = 1.79f,
        )
        // c 0.25 0.19 0.34 0.25 0.77 0.15
        curveToRelative(
            dx1 = 0.25f,
            dy1 = 0.19f,
            dx2 = 0.34f,
            dy2 = 0.25f,
            dx3 = 0.77f,
            dy3 = 0.15f,
        )
    }
    // M185.49 187.61 a4.5 4.5 0 0 0 -2.35 -2.07 8 8 0 0 0 -3.13 -.54 c-2.13 .02 -4.25 .57 -6.38 .39 -1.79 -.16 -3.49 -.83 -5.24 -1.26 a11 11 0 0 0 -5.52 -.12 8 8 0 0 0 -4.67 3.49 11 11 0 0 0 -1.52 4.98 29 29 0 0 0 .19 5.25 26 26 0 0 0 .57 3.75 10 10 0 0 0 1.52 3.46 9.3 9.3 0 0 0 4.37 3.17 12.2 12.2 0 0 0 8.73 -.15 24 24 0 0 0 11.52 -10.6 22 22 0 0 0 2.06 -5.31 10 10 0 0 0 .32 -2.25 4.6 4.6 0 0 0 -.47 -2.19
    path(
        fill = Brush.radialGradient(
            0.0f to Color(0xFF110800),
            0.59f to Color(0xCCA65A00),
            1.0f to Color(0x00FF921E),
            center = Offset(x = 169.71007f, y = 194.53f),
            radius = 20.155956f,
        ),
        fillAlpha = 0.35f,
        strokeAlpha = 0.35f,
    ) {
        // M 185.49 187.61
        moveTo(x = 185.49f, y = 187.61f)
        // a 4.5 4.5 0 0 0 -2.35 -2.07
        arcToRelative(
            a = 4.5f,
            b = 4.5f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -2.35f,
            dy1 = -2.07f,
        )
        // a 8 8 0 0 0 -3.13 -0.54
        arcToRelative(
            a = 8.0f,
            b = 8.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -3.13f,
            dy1 = -0.54f,
        )
        // c -2.13 0.02 -4.25 0.57 -6.38 0.39
        curveToRelative(
            dx1 = -2.13f,
            dy1 = 0.02f,
            dx2 = -4.25f,
            dy2 = 0.57f,
            dx3 = -6.38f,
            dy3 = 0.39f,
        )
        // c -1.79 -0.16 -3.49 -0.83 -5.24 -1.26
        curveToRelative(
            dx1 = -1.79f,
            dy1 = -0.16f,
            dx2 = -3.49f,
            dy2 = -0.83f,
            dx3 = -5.24f,
            dy3 = -1.26f,
        )
        // a 11 11 0 0 0 -5.52 -0.12
        arcToRelative(
            a = 11.0f,
            b = 11.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -5.52f,
            dy1 = -0.12f,
        )
        // a 8 8 0 0 0 -4.67 3.49
        arcToRelative(
            a = 8.0f,
            b = 8.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -4.67f,
            dy1 = 3.49f,
        )
        // a 11 11 0 0 0 -1.52 4.98
        arcToRelative(
            a = 11.0f,
            b = 11.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -1.52f,
            dy1 = 4.98f,
        )
        // a 29 29 0 0 0 0.19 5.25
        arcToRelative(
            a = 29.0f,
            b = 29.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.19f,
            dy1 = 5.25f,
        )
        // a 26 26 0 0 0 0.57 3.75
        arcToRelative(
            a = 26.0f,
            b = 26.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.57f,
            dy1 = 3.75f,
        )
        // a 10 10 0 0 0 1.52 3.46
        arcToRelative(
            a = 10.0f,
            b = 10.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 1.52f,
            dy1 = 3.46f,
        )
        // a 9.3 9.3 0 0 0 4.37 3.17
        arcToRelative(
            a = 9.3f,
            b = 9.3f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 4.37f,
            dy1 = 3.17f,
        )
        // a 12.2 12.2 0 0 0 8.73 -0.15
        arcToRelative(
            a = 12.2f,
            b = 12.2f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 8.73f,
            dy1 = -0.15f,
        )
        // a 24 24 0 0 0 11.52 -10.6
        arcToRelative(
            a = 24.0f,
            b = 24.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 11.52f,
            dy1 = -10.6f,
        )
        // a 22 22 0 0 0 2.06 -5.31
        arcToRelative(
            a = 22.0f,
            b = 22.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 2.06f,
            dy1 = -5.31f,
        )
        // a 10 10 0 0 0 0.32 -2.25
        arcToRelative(
            a = 10.0f,
            b = 10.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.32f,
            dy1 = -2.25f,
        )
        // a 4.6 4.6 0 0 0 -0.47 -2.19
        arcToRelative(
            a = 4.6f,
            b = 4.6f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -0.47f,
            dy1 = -2.19f,
        )
    }
    // M185.49 184.89 a4.4 4.4 0 0 0 -2.35 -1.5 11 11 0 0 0 -3.13 -.39 c-2.13 .02 -4.25 .42 -6.38 .28 -1.79 -.11 -3.49 -.6 -5.24 -.9 a15 15 0 0 0 -5.52 -.09 8.3 8.3 0 0 0 -4.67 2.52 6.4 6.4 0 0 0 -1.52 3.6 15 15 0 0 0 .19 3.79 14 14 0 0 0 .57 2.72 6.5 6.5 0 0 0 1.52 2.5 10 10 0 0 0 4.37 2.29 16.5 16.5 0 0 0 8.73 -.11 c4.88 -1.53 9.01 -4.28 11.52 -7.66 a14 14 0 0 0 2.06 -3.84 5 5 0 0 0 .32 -1.62 2.6 2.6 0 0 0 -.47 -1.59
    path(
        fill = Brush.radialGradient(
            0.0f to Color(0xFF110800),
            0.59f to Color(0xCCA65A00),
            1.0f to Color(0x00FF921E),
            center = Offset(x = 169.71f, y = 189.88998f),
            radius = 17.471222f,
        ),
        fillAlpha = 0.35f,
        strokeAlpha = 0.35f,
    ) {
        // M 185.49 184.89
        moveTo(x = 185.49f, y = 184.89f)
        // a 4.4 4.4 0 0 0 -2.35 -1.5
        arcToRelative(
            a = 4.4f,
            b = 4.4f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -2.35f,
            dy1 = -1.5f,
        )
        // a 11 11 0 0 0 -3.13 -0.39
        arcToRelative(
            a = 11.0f,
            b = 11.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -3.13f,
            dy1 = -0.39f,
        )
        // c -2.13 0.02 -4.25 0.42 -6.38 0.28
        curveToRelative(
            dx1 = -2.13f,
            dy1 = 0.02f,
            dx2 = -4.25f,
            dy2 = 0.42f,
            dx3 = -6.38f,
            dy3 = 0.28f,
        )
        // c -1.79 -0.11 -3.49 -0.6 -5.24 -0.9
        curveToRelative(
            dx1 = -1.79f,
            dy1 = -0.11f,
            dx2 = -3.49f,
            dy2 = -0.6f,
            dx3 = -5.24f,
            dy3 = -0.9f,
        )
        // a 15 15 0 0 0 -5.52 -0.09
        arcToRelative(
            a = 15.0f,
            b = 15.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -5.52f,
            dy1 = -0.09f,
        )
        // a 8.3 8.3 0 0 0 -4.67 2.52
        arcToRelative(
            a = 8.3f,
            b = 8.3f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -4.67f,
            dy1 = 2.52f,
        )
        // a 6.4 6.4 0 0 0 -1.52 3.6
        arcToRelative(
            a = 6.4f,
            b = 6.4f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -1.52f,
            dy1 = 3.6f,
        )
        // a 15 15 0 0 0 0.19 3.79
        arcToRelative(
            a = 15.0f,
            b = 15.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.19f,
            dy1 = 3.79f,
        )
        // a 14 14 0 0 0 0.57 2.72
        arcToRelative(
            a = 14.0f,
            b = 14.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.57f,
            dy1 = 2.72f,
        )
        // a 6.5 6.5 0 0 0 1.52 2.5
        arcToRelative(
            a = 6.5f,
            b = 6.5f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 1.52f,
            dy1 = 2.5f,
        )
        // a 10 10 0 0 0 4.37 2.29
        arcToRelative(
            a = 10.0f,
            b = 10.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 4.37f,
            dy1 = 2.29f,
        )
        // a 16.5 16.5 0 0 0 8.73 -0.11
        arcToRelative(
            a = 16.5f,
            b = 16.5f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 8.73f,
            dy1 = -0.11f,
        )
        // c 4.88 -1.53 9.01 -4.28 11.52 -7.66
        curveToRelative(
            dx1 = 4.88f,
            dy1 = -1.53f,
            dx2 = 9.01f,
            dy2 = -4.28f,
            dx3 = 11.52f,
            dy3 = -7.66f,
        )
        // a 14 14 0 0 0 2.06 -3.84
        arcToRelative(
            a = 14.0f,
            b = 14.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 2.06f,
            dy1 = -3.84f,
        )
        // a 5 5 0 0 0 0.32 -1.62
        arcToRelative(
            a = 5.0f,
            b = 5.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.32f,
            dy1 = -1.62f,
        )
        // a 2.6 2.6 0 0 0 -0.47 -1.59
        arcToRelative(
            a = 2.6f,
            b = 2.6f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -0.47f,
            dy1 = -1.59f,
        )
    }
}

private fun ImageVector.Builder.tuxChunk2() {
    // M189.55 178.72 a6.4 6.4 0 0 0 -1.72 -2.47 9 9 0 0 0 -2.57 -1.6 c-1.86 -.79 -3.89 -1.09 -5.89 -1.46 -1.87 -.35 -3.74 -.78 -5.62 -1.1 -1.96 -.33 -3.98 -.55 -5.92 -.11 a10 10 0 0 0 -4.54 2.43 13 13 0 0 0 -3 4.21 18 18 0 0 0 -1.43 8.97 c.18 2.27 .76 4.61 2.25 6.32 a10 10 0 0 0 4.68 2.78 16 16 0 0 0 9.36 -.13 24.7 24.7 0 0 0 12.35 -9.29 14 14 0 0 0 2.2 -4.66 7 7 0 0 0 -.15 -3.89
    path(
        fill = SolidColor(Color(0xFF020204)),
    ) {
        // M 189.55 178.72
        moveTo(x = 189.55f, y = 178.72f)
        // a 6.4 6.4 0 0 0 -1.72 -2.47
        arcToRelative(
            a = 6.4f,
            b = 6.4f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -1.72f,
            dy1 = -2.47f,
        )
        // a 9 9 0 0 0 -2.57 -1.6
        arcToRelative(
            a = 9.0f,
            b = 9.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -2.57f,
            dy1 = -1.6f,
        )
        // c -1.86 -0.79 -3.89 -1.09 -5.89 -1.46
        curveToRelative(
            dx1 = -1.86f,
            dy1 = -0.79f,
            dx2 = -3.89f,
            dy2 = -1.09f,
            dx3 = -5.89f,
            dy3 = -1.46f,
        )
        // c -1.87 -0.35 -3.74 -0.78 -5.62 -1.1
        curveToRelative(
            dx1 = -1.87f,
            dy1 = -0.35f,
            dx2 = -3.74f,
            dy2 = -0.78f,
            dx3 = -5.62f,
            dy3 = -1.1f,
        )
        // c -1.96 -0.33 -3.98 -0.55 -5.92 -0.11
        curveToRelative(
            dx1 = -1.96f,
            dy1 = -0.33f,
            dx2 = -3.98f,
            dy2 = -0.55f,
            dx3 = -5.92f,
            dy3 = -0.11f,
        )
        // a 10 10 0 0 0 -4.54 2.43
        arcToRelative(
            a = 10.0f,
            b = 10.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -4.54f,
            dy1 = 2.43f,
        )
        // a 13 13 0 0 0 -3 4.21
        arcToRelative(
            a = 13.0f,
            b = 13.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -3.0f,
            dy1 = 4.21f,
        )
        // a 18 18 0 0 0 -1.43 8.97
        arcToRelative(
            a = 18.0f,
            b = 18.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -1.43f,
            dy1 = 8.97f,
        )
        // c 0.18 2.27 0.76 4.61 2.25 6.32
        curveToRelative(
            dx1 = 0.18f,
            dy1 = 2.27f,
            dx2 = 0.76f,
            dy2 = 4.61f,
            dx3 = 2.25f,
            dy3 = 6.32f,
        )
        // a 10 10 0 0 0 4.68 2.78
        arcToRelative(
            a = 10.0f,
            b = 10.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 4.68f,
            dy1 = 2.78f,
        )
        // a 16 16 0 0 0 9.36 -0.13
        arcToRelative(
            a = 16.0f,
            b = 16.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 9.36f,
            dy1 = -0.13f,
        )
        // a 24.7 24.7 0 0 0 12.35 -9.29
        arcToRelative(
            a = 24.7f,
            b = 24.7f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 12.35f,
            dy1 = -9.29f,
        )
        // a 14 14 0 0 0 2.2 -4.66
        arcToRelative(
            a = 14.0f,
            b = 14.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 2.2f,
            dy1 = -4.66f,
        )
        // a 7 7 0 0 0 -0.15 -3.89
        arcToRelative(
            a = 7.0f,
            b = 7.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -0.15f,
            dy1 = -3.89f,
        )
    }
    group(
        // 
        clipPathData = PathData {
        
        },
    ) {
        // M168.89 171.07 a10.75 10.75 0 0 0 -8.67 5.2 10.7 10.7 0 0 0 -1.43 6.17 10 10 0 0 1 1.19 -4.28 8.8 8.8 0 0 1 5.98 -4.37 16 16 0 0 1 4.68 .08 c1.5 .19 3 .39 4.47 .7 a18 18 0 0 1 6.44 2.59 6 6 0 0 1 1.21 1.08 3 3 0 0 1 .73 1.42 3.4 3.4 0 0 1 -.46 2.29 9 9 0 0 1 -1.48 1.86 30 30 0 0 1 -1.43 1.32 c2.21 -.43 4.44 -1.03 6.28 -2.31 a6.4 6.4 0 0 0 1.94 -2.02 4 4 0 0 0 .43 -2.75 4 4 0 0 0 -.92 -1.67 8 8 0 0 0 -1.45 -1.24 17 17 0 0 0 -7.81 -2.99 91 91 0 0 0 -5.42 -.83 24 24 0 0 0 -4.28 -.25
        path(
            fill = Brush.radialGradient(
                0.0f to Color(0xFF7C7C7C),
                1.0f to Color(0x547C7C7C),
                center = Offset(x = 184.65015f, y = 176.62016f),
                radius = 5.4132524f,
            ),
        ) {
            // M 168.89 171.07
            moveTo(x = 168.89f, y = 171.07f)
            // a 10.75 10.75 0 0 0 -8.67 5.2
            arcToRelative(
                a = 10.75f,
                b = 10.75f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                dx1 = -8.67f,
                dy1 = 5.2f,
            )
            // a 10.7 10.7 0 0 0 -1.43 6.17
            arcToRelative(
                a = 10.7f,
                b = 10.7f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                dx1 = -1.43f,
                dy1 = 6.17f,
            )
            // a 10 10 0 0 1 1.19 -4.28
            arcToRelative(
                a = 10.0f,
                b = 10.0f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                dx1 = 1.19f,
                dy1 = -4.28f,
            )
            // a 8.8 8.8 0 0 1 5.98 -4.37
            arcToRelative(
                a = 8.8f,
                b = 8.8f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                dx1 = 5.98f,
                dy1 = -4.37f,
            )
            // a 16 16 0 0 1 4.68 0.08
            arcToRelative(
                a = 16.0f,
                b = 16.0f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                dx1 = 4.68f,
                dy1 = 0.08f,
            )
            // c 1.5 0.19 3 0.39 4.47 0.7
            curveToRelative(
                dx1 = 1.5f,
                dy1 = 0.19f,
                dx2 = 3.0f,
                dy2 = 0.39f,
                dx3 = 4.47f,
                dy3 = 0.7f,
            )
            // a 18 18 0 0 1 6.44 2.59
            arcToRelative(
                a = 18.0f,
                b = 18.0f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                dx1 = 6.44f,
                dy1 = 2.59f,
            )
            // a 6 6 0 0 1 1.21 1.08
            arcToRelative(
                a = 6.0f,
                b = 6.0f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                dx1 = 1.21f,
                dy1 = 1.08f,
            )
            // a 3 3 0 0 1 0.73 1.42
            arcToRelative(
                a = 3.0f,
                b = 3.0f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                dx1 = 0.73f,
                dy1 = 1.42f,
            )
            // a 3.4 3.4 0 0 1 -0.46 2.29
            arcToRelative(
                a = 3.4f,
                b = 3.4f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                dx1 = -0.46f,
                dy1 = 2.29f,
            )
            // a 9 9 0 0 1 -1.48 1.86
            arcToRelative(
                a = 9.0f,
                b = 9.0f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                dx1 = -1.48f,
                dy1 = 1.86f,
            )
            // a 30 30 0 0 1 -1.43 1.32
            arcToRelative(
                a = 30.0f,
                b = 30.0f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                dx1 = -1.43f,
                dy1 = 1.32f,
            )
            // c 2.21 -0.43 4.44 -1.03 6.28 -2.31
            curveToRelative(
                dx1 = 2.21f,
                dy1 = -0.43f,
                dx2 = 4.44f,
                dy2 = -1.03f,
                dx3 = 6.28f,
                dy3 = -2.31f,
            )
            // a 6.4 6.4 0 0 0 1.94 -2.02
            arcToRelative(
                a = 6.4f,
                b = 6.4f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                dx1 = 1.94f,
                dy1 = -2.02f,
            )
            // a 4 4 0 0 0 0.43 -2.75
            arcToRelative(
                a = 4.0f,
                b = 4.0f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                dx1 = 0.43f,
                dy1 = -2.75f,
            )
            // a 4 4 0 0 0 -0.92 -1.67
            arcToRelative(
                a = 4.0f,
                b = 4.0f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                dx1 = -0.92f,
                dy1 = -1.67f,
            )
            // a 8 8 0 0 0 -1.45 -1.24
            arcToRelative(
                a = 8.0f,
                b = 8.0f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                dx1 = -1.45f,
                dy1 = -1.24f,
            )
            // a 17 17 0 0 0 -7.81 -2.99
            arcToRelative(
                a = 17.0f,
                b = 17.0f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                dx1 = -7.81f,
                dy1 = -2.99f,
            )
            // a 91 91 0 0 0 -5.42 -0.83
            arcToRelative(
                a = 91.0f,
                b = 91.0f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                dx1 = -5.42f,
                dy1 = -0.83f,
            )
            // a 24 24 0 0 0 -4.28 -0.25
            arcToRelative(
                a = 24.0f,
                b = 24.0f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                dx1 = -4.28f,
                dy1 = -0.25f,
            )
        }
    }
    group(
        // 
        clipPathData = PathData {
        
        },
    ) {
        // M168.89 171.07 a10.75 10.75 0 0 0 -8.67 5.2 10.7 10.7 0 0 0 -1.43 6.17 10 10 0 0 1 1.19 -4.28 8.8 8.8 0 0 1 5.98 -4.37 16 16 0 0 1 4.68 .08 c1.5 .19 3 .39 4.47 .7 a18 18 0 0 1 6.44 2.59 6 6 0 0 1 1.21 1.08 3 3 0 0 1 .73 1.42 3.4 3.4 0 0 1 -.46 2.29 9 9 0 0 1 -1.48 1.86 30 30 0 0 1 -1.43 1.32 c2.21 -.43 4.44 -1.03 6.28 -2.31 a6.4 6.4 0 0 0 1.94 -2.02 4 4 0 0 0 .43 -2.75 4 4 0 0 0 -.92 -1.67 8 8 0 0 0 -1.45 -1.24 17 17 0 0 0 -7.81 -2.99 91 91 0 0 0 -5.42 -.83 24 24 0 0 0 -4.28 -.25
        path(
            fill = Brush.linearGradient(
                0.0f to Color(0xFF7C7C7C),
                1.0f to Color(0x547C7C7C),
                start = Offset(x = 165.69f, y = 173.58f),
                end = Offset(x = 168.27f, y = 173.47f),
            ),
        ) {
            // M 168.89 171.07
            moveTo(x = 168.89f, y = 171.07f)
            // a 10.75 10.75 0 0 0 -8.67 5.2
            arcToRelative(
                a = 10.75f,
                b = 10.75f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                dx1 = -8.67f,
                dy1 = 5.2f,
            )
            // a 10.7 10.7 0 0 0 -1.43 6.17
            arcToRelative(
                a = 10.7f,
                b = 10.7f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                dx1 = -1.43f,
                dy1 = 6.17f,
            )
            // a 10 10 0 0 1 1.19 -4.28
            arcToRelative(
                a = 10.0f,
                b = 10.0f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                dx1 = 1.19f,
                dy1 = -4.28f,
            )
            // a 8.8 8.8 0 0 1 5.98 -4.37
            arcToRelative(
                a = 8.8f,
                b = 8.8f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                dx1 = 5.98f,
                dy1 = -4.37f,
            )
            // a 16 16 0 0 1 4.68 0.08
            arcToRelative(
                a = 16.0f,
                b = 16.0f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                dx1 = 4.68f,
                dy1 = 0.08f,
            )
            // c 1.5 0.19 3 0.39 4.47 0.7
            curveToRelative(
                dx1 = 1.5f,
                dy1 = 0.19f,
                dx2 = 3.0f,
                dy2 = 0.39f,
                dx3 = 4.47f,
                dy3 = 0.7f,
            )
            // a 18 18 0 0 1 6.44 2.59
            arcToRelative(
                a = 18.0f,
                b = 18.0f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                dx1 = 6.44f,
                dy1 = 2.59f,
            )
            // a 6 6 0 0 1 1.21 1.08
            arcToRelative(
                a = 6.0f,
                b = 6.0f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                dx1 = 1.21f,
                dy1 = 1.08f,
            )
            // a 3 3 0 0 1 0.73 1.42
            arcToRelative(
                a = 3.0f,
                b = 3.0f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                dx1 = 0.73f,
                dy1 = 1.42f,
            )
            // a 3.4 3.4 0 0 1 -0.46 2.29
            arcToRelative(
                a = 3.4f,
                b = 3.4f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                dx1 = -0.46f,
                dy1 = 2.29f,
            )
            // a 9 9 0 0 1 -1.48 1.86
            arcToRelative(
                a = 9.0f,
                b = 9.0f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                dx1 = -1.48f,
                dy1 = 1.86f,
            )
            // a 30 30 0 0 1 -1.43 1.32
            arcToRelative(
                a = 30.0f,
                b = 30.0f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                dx1 = -1.43f,
                dy1 = 1.32f,
            )
            // c 2.21 -0.43 4.44 -1.03 6.28 -2.31
            curveToRelative(
                dx1 = 2.21f,
                dy1 = -0.43f,
                dx2 = 4.44f,
                dy2 = -1.03f,
                dx3 = 6.28f,
                dy3 = -2.31f,
            )
            // a 6.4 6.4 0 0 0 1.94 -2.02
            arcToRelative(
                a = 6.4f,
                b = 6.4f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                dx1 = 1.94f,
                dy1 = -2.02f,
            )
            // a 4 4 0 0 0 0.43 -2.75
            arcToRelative(
                a = 4.0f,
                b = 4.0f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                dx1 = 0.43f,
                dy1 = -2.75f,
            )
            // a 4 4 0 0 0 -0.92 -1.67
            arcToRelative(
                a = 4.0f,
                b = 4.0f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                dx1 = -0.92f,
                dy1 = -1.67f,
            )
            // a 8 8 0 0 0 -1.45 -1.24
            arcToRelative(
                a = 8.0f,
                b = 8.0f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                dx1 = -1.45f,
                dy1 = -1.24f,
            )
            // a 17 17 0 0 0 -7.81 -2.99
            arcToRelative(
                a = 17.0f,
                b = 17.0f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                dx1 = -7.81f,
                dy1 = -2.99f,
            )
            // a 91 91 0 0 0 -5.42 -0.83
            arcToRelative(
                a = 91.0f,
                b = 91.0f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                dx1 = -5.42f,
                dy1 = -0.83f,
            )
            // a 24 24 0 0 0 -4.28 -0.25
            arcToRelative(
                a = 24.0f,
                b = 24.0f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                dx1 = -4.28f,
                dy1 = -0.25f,
            )
        }
    }
    // M84.45 38.28 a6.6 6.6 0 0 0 -4.12 1.84 A9.4 9.4 0 0 0 77.92 44 c-.97 2.92 -.75 6.08 -.53 9.15 .2 2.77 .41 5.6 1.45 8.18 a11 11 0 0 0 2.22 3.51 7.13 7.13 0 0 0 7.41 1.96 8.6 8.6 0 0 0 3.32 -2.02 11 11 0 0 0 2.8 -4.9 19 19 0 0 0 .65 -5.66 24 24 0 0 0 -1.09 -7.03 16 16 0 0 0 -3.6 -6.11 10 10 0 0 0 -2.75 -2.06 7 7 0 0 0 -3.35 -.74
    path(
        fill = Brush.radialGradient(
            0.0f to Color(0xFFFEFEFC),
            0.75f to Color(0xFFFEFEFC),
            1.0f to Color(0xFFD4D4D4),
            center = Offset(x = 86.49f, y = 51.410004f),
            radius = 13.242356f,
        ),
    ) {
        // M 84.45 38.28
        moveTo(x = 84.45f, y = 38.28f)
        // a 6.6 6.6 0 0 0 -4.12 1.84
        arcToRelative(
            a = 6.6f,
            b = 6.6f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -4.12f,
            dy1 = 1.84f,
        )
        // A 9.4 9.4 0 0 0 77.92 44
        arcTo(
            horizontalEllipseRadius = 9.4f,
            verticalEllipseRadius = 9.4f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            x1 = 77.92f,
            y1 = 44.0f,
        )
        // c -0.97 2.92 -0.75 6.08 -0.53 9.15
        curveToRelative(
            dx1 = -0.97f,
            dy1 = 2.92f,
            dx2 = -0.75f,
            dy2 = 6.08f,
            dx3 = -0.53f,
            dy3 = 9.15f,
        )
        // c 0.2 2.77 0.41 5.6 1.45 8.18
        curveToRelative(
            dx1 = 0.2f,
            dy1 = 2.77f,
            dx2 = 0.41f,
            dy2 = 5.6f,
            dx3 = 1.45f,
            dy3 = 8.18f,
        )
        // a 11 11 0 0 0 2.22 3.51
        arcToRelative(
            a = 11.0f,
            b = 11.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 2.22f,
            dy1 = 3.51f,
        )
        // a 7.13 7.13 0 0 0 7.41 1.96
        arcToRelative(
            a = 7.13f,
            b = 7.13f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 7.41f,
            dy1 = 1.96f,
        )
        // a 8.6 8.6 0 0 0 3.32 -2.02
        arcToRelative(
            a = 8.6f,
            b = 8.6f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 3.32f,
            dy1 = -2.02f,
        )
        // a 11 11 0 0 0 2.8 -4.9
        arcToRelative(
            a = 11.0f,
            b = 11.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 2.8f,
            dy1 = -4.9f,
        )
        // a 19 19 0 0 0 0.65 -5.66
        arcToRelative(
            a = 19.0f,
            b = 19.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.65f,
            dy1 = -5.66f,
        )
        // a 24 24 0 0 0 -1.09 -7.03
        arcToRelative(
            a = 24.0f,
            b = 24.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -1.09f,
            dy1 = -7.03f,
        )
        // a 16 16 0 0 0 -3.6 -6.11
        arcToRelative(
            a = 16.0f,
            b = 16.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -3.6f,
            dy1 = -6.11f,
        )
        // a 10 10 0 0 0 -2.75 -2.06
        arcToRelative(
            a = 10.0f,
            b = 10.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -2.75f,
            dy1 = -2.06f,
        )
        // a 7 7 0 0 0 -3.35 -0.74
        arcToRelative(
            a = 7.0f,
            b = 7.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -3.35f,
            dy1 = -0.74f,
        )
    }
    // M80.75 50.99 a11.7 11.7 0 0 0 .33 5.81 10 10 0 0 0 2.05 3.28 7 7 0 0 0 1.99 1.55 3.7 3.7 0 0 0 2.48 .32 3.6 3.6 0 0 0 1.91 -1.29 6 6 0 0 0 1.05 -2.09 11.5 11.5 0 0 0 -.11 -6.83 9 9 0 0 0 -2.6 -4.24 5 5 0 0 0 -2.12 -1.11 3.7 3.7 0 0 0 -2.36 .19 4 4 0 0 0 -1.85 1.86 9 9 0 0 0 -.77 2.55
    path(
        fill = SolidColor(Color(0xFF020204)),
    ) {
        // M 80.75 50.99
        moveTo(x = 80.75f, y = 50.99f)
        // a 11.7 11.7 0 0 0 0.33 5.81
        arcToRelative(
            a = 11.7f,
            b = 11.7f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.33f,
            dy1 = 5.81f,
        )
        // a 10 10 0 0 0 2.05 3.28
        arcToRelative(
            a = 10.0f,
            b = 10.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 2.05f,
            dy1 = 3.28f,
        )
        // a 7 7 0 0 0 1.99 1.55
        arcToRelative(
            a = 7.0f,
            b = 7.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 1.99f,
            dy1 = 1.55f,
        )
        // a 3.7 3.7 0 0 0 2.48 0.32
        arcToRelative(
            a = 3.7f,
            b = 3.7f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 2.48f,
            dy1 = 0.32f,
        )
        // a 3.6 3.6 0 0 0 1.91 -1.29
        arcToRelative(
            a = 3.6f,
            b = 3.6f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 1.91f,
            dy1 = -1.29f,
        )
        // a 6 6 0 0 0 1.05 -2.09
        arcToRelative(
            a = 6.0f,
            b = 6.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 1.05f,
            dy1 = -2.09f,
        )
        // a 11.5 11.5 0 0 0 -0.11 -6.83
        arcToRelative(
            a = 11.5f,
            b = 11.5f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -0.11f,
            dy1 = -6.83f,
        )
        // a 9 9 0 0 0 -2.6 -4.24
        arcToRelative(
            a = 9.0f,
            b = 9.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -2.6f,
            dy1 = -4.24f,
        )
        // a 5 5 0 0 0 -2.12 -1.11
        arcToRelative(
            a = 5.0f,
            b = 5.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -2.12f,
            dy1 = -1.11f,
        )
        // a 3.7 3.7 0 0 0 -2.36 0.19
        arcToRelative(
            a = 3.7f,
            b = 3.7f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -2.36f,
            dy1 = 0.19f,
        )
        // a 4 4 0 0 0 -1.85 1.86
        arcToRelative(
            a = 4.0f,
            b = 4.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -1.85f,
            dy1 = 1.86f,
        )
        // a 9 9 0 0 0 -0.77 2.55
        arcToRelative(
            a = 9.0f,
            b = 9.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -0.77f,
            dy1 = 2.55f,
        )
    }
    // M84.84 49.59 c.21 .55 .91 .75 1.3 1.19 a5 5 0 0 1 .97 1.4 c.39 1.01 -.39 2.51 .43 3.23 .25 .22 .77 .23 1.02 0 .99 -.9 .77 -2.71 .38 -3.99 a4.9 4.9 0 0 0 -2.31 -2.8 c-.5 -.26 -1.25 -.47 -1.68 -.11 -.27 .24 -.24 .74 -.11 1.08
    path(
        fill = Brush.linearGradient(
            0.0f to Color(0x00757574),
            0.25f to Color(0xFF757574),
            0.5f to Color(0xFF757574),
            1.0f to Color(0x00757574),
            start = Offset(x = 84.29f, y = 46.64f),
            end = Offset(x = 89.32f, y = 55.63f),
        ),
    ) {
        // M 84.84 49.59
        moveTo(x = 84.84f, y = 49.59f)
        // c 0.21 0.55 0.91 0.75 1.3 1.19
        curveToRelative(
            dx1 = 0.21f,
            dy1 = 0.55f,
            dx2 = 0.91f,
            dy2 = 0.75f,
            dx3 = 1.3f,
            dy3 = 1.19f,
        )
        // a 5 5 0 0 1 0.97 1.4
        arcToRelative(
            a = 5.0f,
            b = 5.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 0.97f,
            dy1 = 1.4f,
        )
        // c 0.39 1.01 -0.39 2.51 0.43 3.23
        curveToRelative(
            dx1 = 0.39f,
            dy1 = 1.01f,
            dx2 = -0.39f,
            dy2 = 2.51f,
            dx3 = 0.43f,
            dy3 = 3.23f,
        )
        // c 0.25 0.22 0.77 0.23 1.02 0
        curveToRelative(
            dx1 = 0.25f,
            dy1 = 0.22f,
            dx2 = 0.77f,
            dy2 = 0.23f,
            dx3 = 1.02f,
            dy3 = 0.0f,
        )
        // c 0.99 -0.9 0.77 -2.71 0.38 -3.99
        curveToRelative(
            dx1 = 0.99f,
            dy1 = -0.9f,
            dx2 = 0.77f,
            dy2 = -2.71f,
            dx3 = 0.38f,
            dy3 = -3.99f,
        )
        // a 4.9 4.9 0 0 0 -2.31 -2.8
        arcToRelative(
            a = 4.9f,
            b = 4.9f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -2.31f,
            dy1 = -2.8f,
        )
        // c -0.5 -0.26 -1.25 -0.47 -1.68 -0.11
        curveToRelative(
            dx1 = -0.5f,
            dy1 = -0.26f,
            dx2 = -1.25f,
            dy2 = -0.47f,
            dx3 = -1.68f,
            dy3 = -0.11f,
        )
        // c -0.27 0.24 -0.24 0.74 -0.11 1.08
        curveToRelative(
            dx1 = -0.27f,
            dy1 = 0.24f,
            dx2 = -0.24f,
            dy2 = 0.74f,
            dx3 = -0.11f,
            dy3 = 1.08f,
        )
    }
    // M81.14 44.46 c2.32 -1.38 5.13 -1.7 7.82 -1.45 2.68 .26 5.27 1.04 7.87 1.75 1.91 .52 3.84 1 5.63 1.84 a10 10 0 0 1 4.43 3.8 q.22 .41 .46 .83 a3 3 0 0 0 .62 .71 1.4 1.4 0 0 0 .88 .3 1 1 0 0 0 .45 -.13 1 1 0 0 0 .33 -.34 1 1 0 0 0 .1 -.53 3 3 0 0 0 -.1 -.54 c-.65 -2.37 -2.19 -4.38 -3.35 -6.55 -.7 -1.3 -1.28 -2.66 -1.98 -3.96 -2.43 -4.45 -6.42 -7.94 -10.95 -10.21 a37.5 37.5 0 0 0 -14.65 -3.65 c-5.86 -.35 -11.73 .35 -17.51 1.37 -2.51 .44 -5.06 .96 -7.27 2.21 A10 10 0 0 0 51 32.33 a7 7 0 0 0 -1.55 3.44 7.5 7.5 0 0 0 .44 3.62 11 11 0 0 0 1.86 3.15 c1.54 1.91 3.53 3.39 5.36 5.03 1.83 1.63 3.52 3.44 5.57 4.79 a12 12 0 0 0 3.31 1.57 8 8 0 0 0 3.64 .17 8.4 8.4 0 0 0 3.41 -1.64 18 18 0 0 0 2.71 -2.66 c1.66 -1.93 3.21 -4.04 5.39 -5.34
    path(
        fill = Brush.radialGradient(
            0.0f to Color(0xFFC8C8C8),
            1.0f to Color(0xFF797978),
            center = Offset(x = 84.890045f, y = 43.74002f),
            radius = 6.01479f,
        ),
    ) {
        // M 81.14 44.46
        moveTo(x = 81.14f, y = 44.46f)
        // c 2.32 -1.38 5.13 -1.7 7.82 -1.45
        curveToRelative(
            dx1 = 2.32f,
            dy1 = -1.38f,
            dx2 = 5.13f,
            dy2 = -1.7f,
            dx3 = 7.82f,
            dy3 = -1.45f,
        )
        // c 2.68 0.26 5.27 1.04 7.87 1.75
        curveToRelative(
            dx1 = 2.68f,
            dy1 = 0.26f,
            dx2 = 5.27f,
            dy2 = 1.04f,
            dx3 = 7.87f,
            dy3 = 1.75f,
        )
        // c 1.91 0.52 3.84 1 5.63 1.84
        curveToRelative(
            dx1 = 1.91f,
            dy1 = 0.52f,
            dx2 = 3.84f,
            dy2 = 1.0f,
            dx3 = 5.63f,
            dy3 = 1.84f,
        )
        // a 10 10 0 0 1 4.43 3.8
        arcToRelative(
            a = 10.0f,
            b = 10.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 4.43f,
            dy1 = 3.8f,
        )
        // q 0.22 0.41 0.46 0.83
        quadToRelative(
            dx1 = 0.22f,
            dy1 = 0.41f,
            dx2 = 0.46f,
            dy2 = 0.83f,
        )
        // a 3 3 0 0 0 0.62 0.71
        arcToRelative(
            a = 3.0f,
            b = 3.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.62f,
            dy1 = 0.71f,
        )
        // a 1.4 1.4 0 0 0 0.88 0.3
        arcToRelative(
            a = 1.4f,
            b = 1.4f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.88f,
            dy1 = 0.3f,
        )
        // a 1 1 0 0 0 0.45 -0.13
        arcToRelative(
            a = 1.0f,
            b = 1.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.45f,
            dy1 = -0.13f,
        )
        // a 1 1 0 0 0 0.33 -0.34
        arcToRelative(
            a = 1.0f,
            b = 1.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.33f,
            dy1 = -0.34f,
        )
        // a 1 1 0 0 0 0.1 -0.53
        arcToRelative(
            a = 1.0f,
            b = 1.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.1f,
            dy1 = -0.53f,
        )
        // a 3 3 0 0 0 -0.1 -0.54
        arcToRelative(
            a = 3.0f,
            b = 3.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -0.1f,
            dy1 = -0.54f,
        )
        // c -0.65 -2.37 -2.19 -4.38 -3.35 -6.55
        curveToRelative(
            dx1 = -0.65f,
            dy1 = -2.37f,
            dx2 = -2.19f,
            dy2 = -4.38f,
            dx3 = -3.35f,
            dy3 = -6.55f,
        )
        // c -0.7 -1.3 -1.28 -2.66 -1.98 -3.96
        curveToRelative(
            dx1 = -0.7f,
            dy1 = -1.3f,
            dx2 = -1.28f,
            dy2 = -2.66f,
            dx3 = -1.98f,
            dy3 = -3.96f,
        )
        // c -2.43 -4.45 -6.42 -7.94 -10.95 -10.21
        curveToRelative(
            dx1 = -2.43f,
            dy1 = -4.45f,
            dx2 = -6.42f,
            dy2 = -7.94f,
            dx3 = -10.95f,
            dy3 = -10.21f,
        )
        // a 37.5 37.5 0 0 0 -14.65 -3.65
        arcToRelative(
            a = 37.5f,
            b = 37.5f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -14.65f,
            dy1 = -3.65f,
        )
        // c -5.86 -0.35 -11.73 0.35 -17.51 1.37
        curveToRelative(
            dx1 = -5.86f,
            dy1 = -0.35f,
            dx2 = -11.73f,
            dy2 = 0.35f,
            dx3 = -17.51f,
            dy3 = 1.37f,
        )
        // c -2.51 0.44 -5.06 0.96 -7.27 2.21
        curveToRelative(
            dx1 = -2.51f,
            dy1 = 0.44f,
            dx2 = -5.06f,
            dy2 = 0.96f,
            dx3 = -7.27f,
            dy3 = 2.21f,
        )
        // A 10 10 0 0 0 51 32.33
        arcTo(
            horizontalEllipseRadius = 10.0f,
            verticalEllipseRadius = 10.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            x1 = 51.0f,
            y1 = 32.33f,
        )
        // a 7 7 0 0 0 -1.55 3.44
        arcToRelative(
            a = 7.0f,
            b = 7.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -1.55f,
            dy1 = 3.44f,
        )
        // a 7.5 7.5 0 0 0 0.44 3.62
        arcToRelative(
            a = 7.5f,
            b = 7.5f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.44f,
            dy1 = 3.62f,
        )
        // a 11 11 0 0 0 1.86 3.15
        arcToRelative(
            a = 11.0f,
            b = 11.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 1.86f,
            dy1 = 3.15f,
        )
        // c 1.54 1.91 3.53 3.39 5.36 5.03
        curveToRelative(
            dx1 = 1.54f,
            dy1 = 1.91f,
            dx2 = 3.53f,
            dy2 = 3.39f,
            dx3 = 5.36f,
            dy3 = 5.03f,
        )
        // c 1.83 1.63 3.52 3.44 5.57 4.79
        curveToRelative(
            dx1 = 1.83f,
            dy1 = 1.63f,
            dx2 = 3.52f,
            dy2 = 3.44f,
            dx3 = 5.57f,
            dy3 = 4.79f,
        )
        // a 12 12 0 0 0 3.31 1.57
        arcToRelative(
            a = 12.0f,
            b = 12.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 3.31f,
            dy1 = 1.57f,
        )
        // a 8 8 0 0 0 3.64 0.17
        arcToRelative(
            a = 8.0f,
            b = 8.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 3.64f,
            dy1 = 0.17f,
        )
        // a 8.4 8.4 0 0 0 3.41 -1.64
        arcToRelative(
            a = 8.4f,
            b = 8.4f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 3.41f,
            dy1 = -1.64f,
        )
        // a 18 18 0 0 0 2.71 -2.66
        arcToRelative(
            a = 18.0f,
            b = 18.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 2.71f,
            dy1 = -2.66f,
        )
        // c 1.66 -1.93 3.21 -4.04 5.39 -5.34
        curveToRelative(
            dx1 = 1.66f,
            dy1 = -1.93f,
            dx2 = 3.21f,
            dy2 = -4.04f,
            dx3 = 5.39f,
            dy3 = -5.34f,
        )
    }
    // M90.77 36.57 a20.4 20.4 0 0 1 4.85 7.16 c-.48 -2.91 -1.23 -5.26 -3.13 -7.16 a15 15 0 0 0 -3.98 -2.72 11 11 0 0 0 -3.61 -.97 c-.83 -.02 -1.03 0 -1.2 .01 -.18 .01 -.31 .01 .23 .08 a12 12 0 0 1 3.05 .97 15 15 0 0 1 3.79 2.63
    path(
        fill = Brush.linearGradient(
            0.0f to Color(0x00646464),
            0.31f to Color(0x94646464),
            0.47f to Color(0xFF646464),
            0.73f to Color(0x42646464),
            1.0f to Color(0x00646464),
            start = Offset(x = 83.59f, y = 32.51f),
            end = Offset(x = 94.48f, y = 43.63f),
        ),
    ) {
        // M 90.77 36.57
        moveTo(x = 90.77f, y = 36.57f)
        // a 20.4 20.4 0 0 1 4.85 7.16
        arcToRelative(
            a = 20.4f,
            b = 20.4f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 4.85f,
            dy1 = 7.16f,
        )
        // c -0.48 -2.91 -1.23 -5.26 -3.13 -7.16
        curveToRelative(
            dx1 = -0.48f,
            dy1 = -2.91f,
            dx2 = -1.23f,
            dy2 = -5.26f,
            dx3 = -3.13f,
            dy3 = -7.16f,
        )
        // a 15 15 0 0 0 -3.98 -2.72
        arcToRelative(
            a = 15.0f,
            b = 15.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -3.98f,
            dy1 = -2.72f,
        )
        // a 11 11 0 0 0 -3.61 -0.97
        arcToRelative(
            a = 11.0f,
            b = 11.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -3.61f,
            dy1 = -0.97f,
        )
        // c -0.83 -0.02 -1.03 0 -1.2 0.01
        curveToRelative(
            dx1 = -0.83f,
            dy1 = -0.02f,
            dx2 = -1.03f,
            dy2 = 0.0f,
            dx3 = -1.2f,
            dy3 = 0.01f,
        )
        // c -0.18 0.01 -0.31 0.01 0.23 0.08
        curveToRelative(
            dx1 = -0.18f,
            dy1 = 0.01f,
            dx2 = -0.31f,
            dy2 = 0.01f,
            dx3 = 0.23f,
            dy3 = 0.08f,
        )
        // a 12 12 0 0 1 3.05 0.97
        arcToRelative(
            a = 12.0f,
            b = 12.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 3.05f,
            dy1 = 0.97f,
        )
        // a 15 15 0 0 1 3.79 2.63
        arcToRelative(
            a = 15.0f,
            b = 15.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 3.79f,
            dy1 = 2.63f,
        )
    }
    // M111.61 38.28 a14 14 0 0 0 -5.38 6.68 c-1.24 3.45 -.77 7.31 .43 10.77 1.22 3.55 3.27 6.93 6.36 9.06 a11.6 11.6 0 0 0 5.19 2.02 9.6 9.6 0 0 0 5.47 -.95 10.6 10.6 0 0 0 4.53 -4.98 18 18 0 0 0 1.5 -6.66 22 22 0 0 0 -1.08 -8.61 14.5 14.5 0 0 0 -5.58 -7.47 11 11 0 0 0 -4.4 -1.67 9.4 9.4 0 0 0 -7.04 1.81
    path(
        fill = Brush.radialGradient(
            0.0f to Color(0xFFFEFEFC),
            0.75f to Color(0xFFFEFEFC),
            1.0f to Color(0xFFD4D4D4),
            center = Offset(x = 118.05999f, y = 51.410015f),
            radius = 14.695441f,
        ),
    ) {
        // M 111.61 38.28
        moveTo(x = 111.61f, y = 38.28f)
        // a 14 14 0 0 0 -5.38 6.68
        arcToRelative(
            a = 14.0f,
            b = 14.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -5.38f,
            dy1 = 6.68f,
        )
        // c -1.24 3.45 -0.77 7.31 0.43 10.77
        curveToRelative(
            dx1 = -1.24f,
            dy1 = 3.45f,
            dx2 = -0.77f,
            dy2 = 7.31f,
            dx3 = 0.43f,
            dy3 = 10.77f,
        )
        // c 1.22 3.55 3.27 6.93 6.36 9.06
        curveToRelative(
            dx1 = 1.22f,
            dy1 = 3.55f,
            dx2 = 3.27f,
            dy2 = 6.93f,
            dx3 = 6.36f,
            dy3 = 9.06f,
        )
        // a 11.6 11.6 0 0 0 5.19 2.02
        arcToRelative(
            a = 11.6f,
            b = 11.6f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 5.19f,
            dy1 = 2.02f,
        )
        // a 9.6 9.6 0 0 0 5.47 -0.95
        arcToRelative(
            a = 9.6f,
            b = 9.6f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 5.47f,
            dy1 = -0.95f,
        )
        // a 10.6 10.6 0 0 0 4.53 -4.98
        arcToRelative(
            a = 10.6f,
            b = 10.6f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 4.53f,
            dy1 = -4.98f,
        )
        // a 18 18 0 0 0 1.5 -6.66
        arcToRelative(
            a = 18.0f,
            b = 18.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 1.5f,
            dy1 = -6.66f,
        )
        // a 22 22 0 0 0 -1.08 -8.61
        arcToRelative(
            a = 22.0f,
            b = 22.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -1.08f,
            dy1 = -8.61f,
        )
        // a 14.5 14.5 0 0 0 -5.58 -7.47
        arcToRelative(
            a = 14.5f,
            b = 14.5f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -5.58f,
            dy1 = -7.47f,
        )
        // a 11 11 0 0 0 -4.4 -1.67
        arcToRelative(
            a = 11.0f,
            b = 11.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -4.4f,
            dy1 = -1.67f,
        )
        // a 9.4 9.4 0 0 0 -7.04 1.81
        arcToRelative(
            a = 9.4f,
            b = 9.4f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -7.04f,
            dy1 = 1.81f,
        )
    }
    // M117.14 45.52 a5.6 5.6 0 0 0 -2.55 .85 7 7 0 0 0 -1.92 1.88 9.6 9.6 0 0 0 -1.55 5.12 11 11 0 0 0 .55 4 7.5 7.5 0 0 0 2.25 3.33 6.5 6.5 0 0 0 3.81 1.49 6.5 6.5 0 0 0 6.09 -3.61 9 9 0 0 0 .98 -3.15 9.7 9.7 0 0 0 -.93 -5.69 8 8 0 0 0 -4.24 -3.84 6 6 0 0 0 -2.49 -.38
    path(
        fill = SolidColor(Color(0xFF020204)),
    ) {
        // M 117.14 45.52
        moveTo(x = 117.14f, y = 45.52f)
        // a 5.6 5.6 0 0 0 -2.55 0.85
        arcToRelative(
            a = 5.6f,
            b = 5.6f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -2.55f,
            dy1 = 0.85f,
        )
        // a 7 7 0 0 0 -1.92 1.88
        arcToRelative(
            a = 7.0f,
            b = 7.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -1.92f,
            dy1 = 1.88f,
        )
        // a 9.6 9.6 0 0 0 -1.55 5.12
        arcToRelative(
            a = 9.6f,
            b = 9.6f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -1.55f,
            dy1 = 5.12f,
        )
        // a 11 11 0 0 0 0.55 4
        arcToRelative(
            a = 11.0f,
            b = 11.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.55f,
            dy1 = 4.0f,
        )
        // a 7.5 7.5 0 0 0 2.25 3.33
        arcToRelative(
            a = 7.5f,
            b = 7.5f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 2.25f,
            dy1 = 3.33f,
        )
        // a 6.5 6.5 0 0 0 3.81 1.49
        arcToRelative(
            a = 6.5f,
            b = 6.5f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 3.81f,
            dy1 = 1.49f,
        )
        // a 6.5 6.5 0 0 0 6.09 -3.61
        arcToRelative(
            a = 6.5f,
            b = 6.5f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 6.09f,
            dy1 = -3.61f,
        )
        // a 9 9 0 0 0 0.98 -3.15
        arcToRelative(
            a = 9.0f,
            b = 9.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.98f,
            dy1 = -3.15f,
        )
        // a 9.7 9.7 0 0 0 -0.93 -5.69
        arcToRelative(
            a = 9.7f,
            b = 9.7f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -0.93f,
            dy1 = -5.69f,
        )
        // a 8 8 0 0 0 -4.24 -3.84
        arcToRelative(
            a = 8.0f,
            b = 8.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -4.24f,
            dy1 = -3.84f,
        )
        // a 6 6 0 0 0 -2.49 -0.38
        arcToRelative(
            a = 6.0f,
            b = 6.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -2.49f,
            dy1 = -0.38f,
        )
    }
    // M122.71 53.36 c1 -1 -.71 -3.65 -2.05 -4.74 -.97 -.78 -3.78 -1.61 -3.66 -.75 .12 .85 1.39 1.95 2.23 2.79 1.05 1.03 3 3.18 3.48 2.7
    path(
        fill = Brush.linearGradient(
            0.0f to Color(0x63949494),
            0.5f to Color(0xFF949494),
            1.0f to Color(0x63949494),
            start = Offset(x = 117.87f, y = 47.25f),
            end = Offset(x = 123.66f, y = 54.11f),
        ),
    ) {
        // M 122.71 53.36
        moveTo(x = 122.71f, y = 53.36f)
        // c 1 -1 -0.71 -3.65 -2.05 -4.74
        curveToRelative(
            dx1 = 1.0f,
            dy1 = -1.0f,
            dx2 = -0.71f,
            dy2 = -3.65f,
            dx3 = -2.05f,
            dy3 = -4.74f,
        )
        // c -0.97 -0.78 -3.78 -1.61 -3.66 -0.75
        curveToRelative(
            dx1 = -0.97f,
            dy1 = -0.78f,
            dx2 = -3.78f,
            dy2 = -1.61f,
            dx3 = -3.66f,
            dy3 = -0.75f,
        )
        // c 0.12 0.85 1.39 1.95 2.23 2.79
        curveToRelative(
            dx1 = 0.12f,
            dy1 = 0.85f,
            dx2 = 1.39f,
            dy2 = 1.95f,
            dx3 = 2.23f,
            dy3 = 2.79f,
        )
        // c 1.05 1.03 3 3.18 3.48 2.7
        curveToRelative(
            dx1 = 1.05f,
            dy1 = 1.03f,
            dx2 = 3.0f,
            dy2 = 3.18f,
            dx3 = 3.48f,
            dy3 = 2.7f,
        )
    }
    // M102.56 47.01 a21 21 0 0 1 7 -3.8 19.3 19.3 0 0 1 15.84 1.97 c1.6 1.01 3.03 2.27 4.52 3.45 a17 17 0 0 0 4.85 2.9 7 7 0 0 0 3.02 .43 5.6 5.6 0 0 0 2.57 -.96 7 7 0 0 0 1.88 -2.02 8.8 8.8 0 0 0 1.1 -5.34 c-.33 -3.69 -2.41 -6.94 -4.15 -10.21 -.55 -1.02 -1.07 -2.06 -1.73 -3.01 -2.01 -2.93 -5.23 -4.86 -8.6 -5.99 s-6.93 -1.54 -10.46 -1.98 c-1.58 -.2 -3.17 -.41 -4.74 -.22 -1.81 .22 -3.51 .95 -5.28 1.4 -.84 .22 -1.69 .37 -2.52 .61 a7 7 0 0 0 -2.33 1.11 5.7 5.7 0 0 0 -1.87 3.21 10 10 0 0 0 -.01 3.77 c.39 2.5 1.33 4.93 1.24 7.46 -.06 1.73 -.61 3.44 -.54 5.17 a20 20 0 0 0 .21 2.05
    path(
        fill = Brush.linearGradient(
            0.0f to Color(0xFF747474),
            0.13f to Color(0xFF8C8C8C),
            0.25f to Color(0xFFA4A4A4),
            0.5f to Color(0xFFD4D4D4),
            0.62f to Color(0xFFD4D4D4),
            1.0f to Color(0xFF7C7C7C),
            start = Offset(x = 112.9f, y = 36.23f),
            end = Offset(x = 131.32f, y = 47.01f),
        ),
    ) {
        // M 102.56 47.01
        moveTo(x = 102.56f, y = 47.01f)
        // a 21 21 0 0 1 7 -3.8
        arcToRelative(
            a = 21.0f,
            b = 21.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 7.0f,
            dy1 = -3.8f,
        )
        // a 19.3 19.3 0 0 1 15.84 1.97
        arcToRelative(
            a = 19.3f,
            b = 19.3f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 15.84f,
            dy1 = 1.97f,
        )
        // c 1.6 1.01 3.03 2.27 4.52 3.45
        curveToRelative(
            dx1 = 1.6f,
            dy1 = 1.01f,
            dx2 = 3.03f,
            dy2 = 2.27f,
            dx3 = 4.52f,
            dy3 = 3.45f,
        )
        // a 17 17 0 0 0 4.85 2.9
        arcToRelative(
            a = 17.0f,
            b = 17.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 4.85f,
            dy1 = 2.9f,
        )
        // a 7 7 0 0 0 3.02 0.43
        arcToRelative(
            a = 7.0f,
            b = 7.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 3.02f,
            dy1 = 0.43f,
        )
        // a 5.6 5.6 0 0 0 2.57 -0.96
        arcToRelative(
            a = 5.6f,
            b = 5.6f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 2.57f,
            dy1 = -0.96f,
        )
        // a 7 7 0 0 0 1.88 -2.02
        arcToRelative(
            a = 7.0f,
            b = 7.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 1.88f,
            dy1 = -2.02f,
        )
        // a 8.8 8.8 0 0 0 1.1 -5.34
        arcToRelative(
            a = 8.8f,
            b = 8.8f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 1.1f,
            dy1 = -5.34f,
        )
        // c -0.33 -3.69 -2.41 -6.94 -4.15 -10.21
        curveToRelative(
            dx1 = -0.33f,
            dy1 = -3.69f,
            dx2 = -2.41f,
            dy2 = -6.94f,
            dx3 = -4.15f,
            dy3 = -10.21f,
        )
        // c -0.55 -1.02 -1.07 -2.06 -1.73 -3.01
        curveToRelative(
            dx1 = -0.55f,
            dy1 = -1.02f,
            dx2 = -1.07f,
            dy2 = -2.06f,
            dx3 = -1.73f,
            dy3 = -3.01f,
        )
        // c -2.01 -2.93 -5.23 -4.86 -8.6 -5.99
        curveToRelative(
            dx1 = -2.01f,
            dy1 = -2.93f,
            dx2 = -5.23f,
            dy2 = -4.86f,
            dx3 = -8.6f,
            dy3 = -5.99f,
        )
        // s -6.93 -1.54 -10.46 -1.98
        reflectiveCurveToRelative(
            dx1 = -6.93f,
            dy1 = -1.54f,
            dx2 = -10.46f,
            dy2 = -1.98f,
        )
        // c -1.58 -0.2 -3.17 -0.41 -4.74 -0.22
        curveToRelative(
            dx1 = -1.58f,
            dy1 = -0.2f,
            dx2 = -3.17f,
            dy2 = -0.41f,
            dx3 = -4.74f,
            dy3 = -0.22f,
        )
        // c -1.81 0.22 -3.51 0.95 -5.28 1.4
        curveToRelative(
            dx1 = -1.81f,
            dy1 = 0.22f,
            dx2 = -3.51f,
            dy2 = 0.95f,
            dx3 = -5.28f,
            dy3 = 1.4f,
        )
        // c -0.84 0.22 -1.69 0.37 -2.52 0.61
        curveToRelative(
            dx1 = -0.84f,
            dy1 = 0.22f,
            dx2 = -1.69f,
            dy2 = 0.37f,
            dx3 = -2.52f,
            dy3 = 0.61f,
        )
        // a 7 7 0 0 0 -2.33 1.11
        arcToRelative(
            a = 7.0f,
            b = 7.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -2.33f,
            dy1 = 1.11f,
        )
        // a 5.7 5.7 0 0 0 -1.87 3.21
        arcToRelative(
            a = 5.7f,
            b = 5.7f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -1.87f,
            dy1 = 3.21f,
        )
        // a 10 10 0 0 0 -0.01 3.77
        arcToRelative(
            a = 10.0f,
            b = 10.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -0.01f,
            dy1 = 3.77f,
        )
        // c 0.39 2.5 1.33 4.93 1.24 7.46
        curveToRelative(
            dx1 = 0.39f,
            dy1 = 2.5f,
            dx2 = 1.33f,
            dy2 = 4.93f,
            dx3 = 1.24f,
            dy3 = 7.46f,
        )
        // c -0.06 1.73 -0.61 3.44 -0.54 5.17
        curveToRelative(
            dx1 = -0.06f,
            dy1 = 1.73f,
            dx2 = -0.61f,
            dy2 = 3.44f,
            dx3 = -0.54f,
            dy3 = 5.17f,
        )
        // a 20 20 0 0 0 0.21 2.05
        arcToRelative(
            a = 20.0f,
            b = 20.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.21f,
            dy1 = 2.05f,
        )
    }
    // M119.93 31.18 a9 9 0 0 0 -1.07 1.7 15 15 0 0 1 5.19 2.21 21 21 0 0 1 7.01 8.29 9 9 0 0 0 1.02 -1.37 c-1.64 -3.44 -4 -6.55 -7.16 -8.65 -1.52 -1 -3.21 -1.77 -4.99 -2.18
    path(
        fill = Brush.linearGradient(
            0.0f to Color(0x00646464),
            0.31f to Color(0x94646464),
            0.47f to Color(0xFF646464),
            0.73f to Color(0x42646464),
            1.0f to Color(0x00646464),
            start = Offset(x = 119.16f, y = 31.56f),
            end = Offset(x = 131.42f, y = 43.14f),
        ),
    ) {
        // M 119.93 31.18
        moveTo(x = 119.93f, y = 31.18f)
        // a 9 9 0 0 0 -1.07 1.7
        arcToRelative(
            a = 9.0f,
            b = 9.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -1.07f,
            dy1 = 1.7f,
        )
        // a 15 15 0 0 1 5.19 2.21
        arcToRelative(
            a = 15.0f,
            b = 15.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 5.19f,
            dy1 = 2.21f,
        )
        // a 21 21 0 0 1 7.01 8.29
        arcToRelative(
            a = 21.0f,
            b = 21.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 7.01f,
            dy1 = 8.29f,
        )
        // a 9 9 0 0 0 1.02 -1.37
        arcToRelative(
            a = 9.0f,
            b = 9.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 1.02f,
            dy1 = -1.37f,
        )
        // c -1.64 -3.44 -4 -6.55 -7.16 -8.65
        curveToRelative(
            dx1 = -1.64f,
            dy1 = -3.44f,
            dx2 = -4.0f,
            dy2 = -6.55f,
            dx3 = -7.16f,
            dy3 = -8.65f,
        )
        // c -1.52 -1 -3.21 -1.77 -4.99 -2.18
        curveToRelative(
            dx1 = -1.52f,
            dy1 = -1.0f,
            dx2 = -3.21f,
            dy2 = -1.77f,
            dx3 = -4.99f,
            dy3 = -2.18f,
        )
    }
    // M81.12 89.33 c1.47 4.26 4.42 7.89 7.92 10.72 a17 17 0 0 0 3.76 2.43 9 9 0 0 0 4.36 .84 10 10 0 0 0 4.13 -1.42 c1.28 -.72 2.46 -1.59 3.7 -2.37 2.12 -1.35 4.39 -2.44 6.6 -3.64 a38 38 0 0 0 7.46 -5.14 c1.03 -.93 1.98 -1.95 3.11 -2.75 a6 6 0 0 1 3.87 -1.29 c1.04 .07 2.01 .51 3.03 .73 a4 4 0 0 0 1.55 .08 2.4 2.4 0 0 0 1.37 -.67 2.4 2.4 0 0 0 .61 -1.76 4 4 0 0 0 -.54 -1.81 c-.59 -1.13 -1.49 -2.1 -1.89 -3.31 -.36 -1.08 -.29 -2.24 -.26 -3.37 a7 7 0 0 0 -.51 -3.33 4.3 4.3 0 0 0 -1.83 -1.77 7 7 0 0 0 -2.48 -.7 c-1.72 -.16 -3.44 .18 -5.17 .27 -2.28 .13 -4.58 -.15 -6.87 -.02 -2.85 .18 -5.65 1 -8.51 1.01 -3.26 .01 -6.52 -1.06 -9.74 -.55 -1.39 .22 -2.71 .72 -4.03 1.16 a12 12 0 0 1 -4.1 .82 c-1.59 -.03 -3.13 -.58 -4.72 -.69 a5.5 5.5 0 0 0 -2.35 .28 3.3 3.3 0 0 0 -1.78 1.5 3 3 0 0 0 -.33 1.31 5 5 0 0 0 .15 1.36 c.22 .88 .63 1.71 .96 2.55 1.2 3.07 1.46 6.42 2.53 9.53
    path(
        fillAlpha = 0.259f,
        fill = SolidColor(Color(0xFF000000)),
    ) {
        // M 81.12 89.33
        moveTo(x = 81.12f, y = 89.33f)
        // c 1.47 4.26 4.42 7.89 7.92 10.72
        curveToRelative(
            dx1 = 1.47f,
            dy1 = 4.26f,
            dx2 = 4.42f,
            dy2 = 7.89f,
            dx3 = 7.92f,
            dy3 = 10.72f,
        )
        // a 17 17 0 0 0 3.76 2.43
        arcToRelative(
            a = 17.0f,
            b = 17.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 3.76f,
            dy1 = 2.43f,
        )
        // a 9 9 0 0 0 4.36 0.84
        arcToRelative(
            a = 9.0f,
            b = 9.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 4.36f,
            dy1 = 0.84f,
        )
        // a 10 10 0 0 0 4.13 -1.42
        arcToRelative(
            a = 10.0f,
            b = 10.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 4.13f,
            dy1 = -1.42f,
        )
        // c 1.28 -0.72 2.46 -1.59 3.7 -2.37
        curveToRelative(
            dx1 = 1.28f,
            dy1 = -0.72f,
            dx2 = 2.46f,
            dy2 = -1.59f,
            dx3 = 3.7f,
            dy3 = -2.37f,
        )
        // c 2.12 -1.35 4.39 -2.44 6.6 -3.64
        curveToRelative(
            dx1 = 2.12f,
            dy1 = -1.35f,
            dx2 = 4.39f,
            dy2 = -2.44f,
            dx3 = 6.6f,
            dy3 = -3.64f,
        )
        // a 38 38 0 0 0 7.46 -5.14
        arcToRelative(
            a = 38.0f,
            b = 38.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 7.46f,
            dy1 = -5.14f,
        )
        // c 1.03 -0.93 1.98 -1.95 3.11 -2.75
        curveToRelative(
            dx1 = 1.03f,
            dy1 = -0.93f,
            dx2 = 1.98f,
            dy2 = -1.95f,
            dx3 = 3.11f,
            dy3 = -2.75f,
        )
        // a 6 6 0 0 1 3.87 -1.29
        arcToRelative(
            a = 6.0f,
            b = 6.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 3.87f,
            dy1 = -1.29f,
        )
        // c 1.04 0.07 2.01 0.51 3.03 0.73
        curveToRelative(
            dx1 = 1.04f,
            dy1 = 0.07f,
            dx2 = 2.01f,
            dy2 = 0.51f,
            dx3 = 3.03f,
            dy3 = 0.73f,
        )
        // a 4 4 0 0 0 1.55 0.08
        arcToRelative(
            a = 4.0f,
            b = 4.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 1.55f,
            dy1 = 0.08f,
        )
        // a 2.4 2.4 0 0 0 1.37 -0.67
        arcToRelative(
            a = 2.4f,
            b = 2.4f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 1.37f,
            dy1 = -0.67f,
        )
        // a 2.4 2.4 0 0 0 0.61 -1.76
        arcToRelative(
            a = 2.4f,
            b = 2.4f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.61f,
            dy1 = -1.76f,
        )
        // a 4 4 0 0 0 -0.54 -1.81
        arcToRelative(
            a = 4.0f,
            b = 4.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -0.54f,
            dy1 = -1.81f,
        )
        // c -0.59 -1.13 -1.49 -2.1 -1.89 -3.31
        curveToRelative(
            dx1 = -0.59f,
            dy1 = -1.13f,
            dx2 = -1.49f,
            dy2 = -2.1f,
            dx3 = -1.89f,
            dy3 = -3.31f,
        )
        // c -0.36 -1.08 -0.29 -2.24 -0.26 -3.37
        curveToRelative(
            dx1 = -0.36f,
            dy1 = -1.08f,
            dx2 = -0.29f,
            dy2 = -2.24f,
            dx3 = -0.26f,
            dy3 = -3.37f,
        )
        // a 7 7 0 0 0 -0.51 -3.33
        arcToRelative(
            a = 7.0f,
            b = 7.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -0.51f,
            dy1 = -3.33f,
        )
        // a 4.3 4.3 0 0 0 -1.83 -1.77
        arcToRelative(
            a = 4.3f,
            b = 4.3f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -1.83f,
            dy1 = -1.77f,
        )
        // a 7 7 0 0 0 -2.48 -0.7
        arcToRelative(
            a = 7.0f,
            b = 7.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -2.48f,
            dy1 = -0.7f,
        )
        // c -1.72 -0.16 -3.44 0.18 -5.17 0.27
        curveToRelative(
            dx1 = -1.72f,
            dy1 = -0.16f,
            dx2 = -3.44f,
            dy2 = 0.18f,
            dx3 = -5.17f,
            dy3 = 0.27f,
        )
        // c -2.28 0.13 -4.58 -0.15 -6.87 -0.02
        curveToRelative(
            dx1 = -2.28f,
            dy1 = 0.13f,
            dx2 = -4.58f,
            dy2 = -0.15f,
            dx3 = -6.87f,
            dy3 = -0.02f,
        )
        // c -2.85 0.18 -5.65 1 -8.51 1.01
        curveToRelative(
            dx1 = -2.85f,
            dy1 = 0.18f,
            dx2 = -5.65f,
            dy2 = 1.0f,
            dx3 = -8.51f,
            dy3 = 1.01f,
        )
        // c -3.26 0.01 -6.52 -1.06 -9.74 -0.55
        curveToRelative(
            dx1 = -3.26f,
            dy1 = 0.01f,
            dx2 = -6.52f,
            dy2 = -1.06f,
            dx3 = -9.74f,
            dy3 = -0.55f,
        )
        // c -1.39 0.22 -2.71 0.72 -4.03 1.16
        curveToRelative(
            dx1 = -1.39f,
            dy1 = 0.22f,
            dx2 = -2.71f,
            dy2 = 0.72f,
            dx3 = -4.03f,
            dy3 = 1.16f,
        )
        // a 12 12 0 0 1 -4.1 0.82
        arcToRelative(
            a = 12.0f,
            b = 12.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -4.1f,
            dy1 = 0.82f,
        )
        // c -1.59 -0.03 -3.13 -0.58 -4.72 -0.69
        curveToRelative(
            dx1 = -1.59f,
            dy1 = -0.03f,
            dx2 = -3.13f,
            dy2 = -0.58f,
            dx3 = -4.72f,
            dy3 = -0.69f,
        )
        // a 5.5 5.5 0 0 0 -2.35 0.28
        arcToRelative(
            a = 5.5f,
            b = 5.5f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -2.35f,
            dy1 = 0.28f,
        )
        // a 3.3 3.3 0 0 0 -1.78 1.5
        arcToRelative(
            a = 3.3f,
            b = 3.3f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -1.78f,
            dy1 = 1.5f,
        )
        // a 3 3 0 0 0 -0.33 1.31
        arcToRelative(
            a = 3.0f,
            b = 3.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -0.33f,
            dy1 = 1.31f,
        )
        // a 5 5 0 0 0 0.15 1.36
        arcToRelative(
            a = 5.0f,
            b = 5.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.15f,
            dy1 = 1.36f,
        )
        // c 0.22 0.88 0.63 1.71 0.96 2.55
        curveToRelative(
            dx1 = 0.22f,
            dy1 = 0.88f,
            dx2 = 0.63f,
            dy2 = 1.71f,
            dx3 = 0.96f,
            dy3 = 2.55f,
        )
        // c 1.2 3.07 1.46 6.42 2.53 9.53
        curveToRelative(
            dx1 = 1.2f,
            dy1 = 3.07f,
            dx2 = 1.46f,
            dy2 = 6.42f,
            dx3 = 2.53f,
            dy3 = 9.53f,
        )
    }
    // M77.03 77.2 a33 33 0 0 1 7.56 6.39 c1.99 2.29 3.68 4.89 6.29 6.58 a14 14 0 0 0 6.28 2.08 22 22 0 0 0 7.83 -.84 29 29 0 0 0 6.7 -2.71 c3.97 -2.25 7.28 -5.55 11.65 -7.03 .95 -.33 1.94 -.56 2.86 -.96 a4.4 4.4 0 0 0 2.23 -1.83 c.42 -.82 .4 -1.75 .54 -2.64 .15 -.96 .48 -1.88 .66 -2.83 a4.4 4.4 0 0 0 -.24 -2.83 4 4 0 0 0 -1.81 -1.66 7 7 0 0 0 -2.51 -.56 c-1.72 -.08 -3.43 .33 -5.16 .47 -2.28 .19 -4.58 -.08 -6.87 -.01 -2.85 .08 -5.66 .67 -8.51 .8 -3.25 .14 -6.49 -.34 -9.74 -.44 a21 21 0 0 0 -4.21 .2 11 11 0 0 0 -3.92 1.37 c-1.14 .69 -2.07 1.64 -3.11 2.45 a9 9 0 0 1 -1.68 1.07 5 5 0 0 1 -1.96 .51 c-.35 .01 -.71 -.01 -1.05 .04 a2.3 2.3 0 0 0 -1.47 .83 2 2 0 0 0 -.36 1.55
    path(
        fillAlpha = 0.3f,
        strokeAlpha = 0.3f,
        fill = SolidColor(Color(0xFF000000)),
    ) {
        // M 77.03 77.2
        moveTo(x = 77.03f, y = 77.2f)
        // a 33 33 0 0 1 7.56 6.39
        arcToRelative(
            a = 33.0f,
            b = 33.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 7.56f,
            dy1 = 6.39f,
        )
        // c 1.99 2.29 3.68 4.89 6.29 6.58
        curveToRelative(
            dx1 = 1.99f,
            dy1 = 2.29f,
            dx2 = 3.68f,
            dy2 = 4.89f,
            dx3 = 6.29f,
            dy3 = 6.58f,
        )
        // a 14 14 0 0 0 6.28 2.08
        arcToRelative(
            a = 14.0f,
            b = 14.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 6.28f,
            dy1 = 2.08f,
        )
        // a 22 22 0 0 0 7.83 -0.84
        arcToRelative(
            a = 22.0f,
            b = 22.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 7.83f,
            dy1 = -0.84f,
        )
        // a 29 29 0 0 0 6.7 -2.71
        arcToRelative(
            a = 29.0f,
            b = 29.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 6.7f,
            dy1 = -2.71f,
        )
        // c 3.97 -2.25 7.28 -5.55 11.65 -7.03
        curveToRelative(
            dx1 = 3.97f,
            dy1 = -2.25f,
            dx2 = 7.28f,
            dy2 = -5.55f,
            dx3 = 11.65f,
            dy3 = -7.03f,
        )
        // c 0.95 -0.33 1.94 -0.56 2.86 -0.96
        curveToRelative(
            dx1 = 0.95f,
            dy1 = -0.33f,
            dx2 = 1.94f,
            dy2 = -0.56f,
            dx3 = 2.86f,
            dy3 = -0.96f,
        )
        // a 4.4 4.4 0 0 0 2.23 -1.83
        arcToRelative(
            a = 4.4f,
            b = 4.4f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 2.23f,
            dy1 = -1.83f,
        )
        // c 0.42 -0.82 0.4 -1.75 0.54 -2.64
        curveToRelative(
            dx1 = 0.42f,
            dy1 = -0.82f,
            dx2 = 0.4f,
            dy2 = -1.75f,
            dx3 = 0.54f,
            dy3 = -2.64f,
        )
        // c 0.15 -0.96 0.48 -1.88 0.66 -2.83
        curveToRelative(
            dx1 = 0.15f,
            dy1 = -0.96f,
            dx2 = 0.48f,
            dy2 = -1.88f,
            dx3 = 0.66f,
            dy3 = -2.83f,
        )
        // a 4.4 4.4 0 0 0 -0.24 -2.83
        arcToRelative(
            a = 4.4f,
            b = 4.4f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -0.24f,
            dy1 = -2.83f,
        )
        // a 4 4 0 0 0 -1.81 -1.66
        arcToRelative(
            a = 4.0f,
            b = 4.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -1.81f,
            dy1 = -1.66f,
        )
        // a 7 7 0 0 0 -2.51 -0.56
        arcToRelative(
            a = 7.0f,
            b = 7.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -2.51f,
            dy1 = -0.56f,
        )
        // c -1.72 -0.08 -3.43 0.33 -5.16 0.47
        curveToRelative(
            dx1 = -1.72f,
            dy1 = -0.08f,
            dx2 = -3.43f,
            dy2 = 0.33f,
            dx3 = -5.16f,
            dy3 = 0.47f,
        )
        // c -2.28 0.19 -4.58 -0.08 -6.87 -0.01
        curveToRelative(
            dx1 = -2.28f,
            dy1 = 0.19f,
            dx2 = -4.58f,
            dy2 = -0.08f,
            dx3 = -6.87f,
            dy3 = -0.01f,
        )
        // c -2.85 0.08 -5.66 0.67 -8.51 0.8
        curveToRelative(
            dx1 = -2.85f,
            dy1 = 0.08f,
            dx2 = -5.66f,
            dy2 = 0.67f,
            dx3 = -8.51f,
            dy3 = 0.8f,
        )
        // c -3.25 0.14 -6.49 -0.34 -9.74 -0.44
        curveToRelative(
            dx1 = -3.25f,
            dy1 = 0.14f,
            dx2 = -6.49f,
            dy2 = -0.34f,
            dx3 = -9.74f,
            dy3 = -0.44f,
        )
        // a 21 21 0 0 0 -4.21 0.2
        arcToRelative(
            a = 21.0f,
            b = 21.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -4.21f,
            dy1 = 0.2f,
        )
        // a 11 11 0 0 0 -3.92 1.37
        arcToRelative(
            a = 11.0f,
            b = 11.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -3.92f,
            dy1 = 1.37f,
        )
        // c -1.14 0.69 -2.07 1.64 -3.11 2.45
        curveToRelative(
            dx1 = -1.14f,
            dy1 = 0.69f,
            dx2 = -2.07f,
            dy2 = 1.64f,
            dx3 = -3.11f,
            dy3 = 2.45f,
        )
        // a 9 9 0 0 1 -1.68 1.07
        arcToRelative(
            a = 9.0f,
            b = 9.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -1.68f,
            dy1 = 1.07f,
        )
        // a 5 5 0 0 1 -1.96 0.51
        arcToRelative(
            a = 5.0f,
            b = 5.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -1.96f,
            dy1 = 0.51f,
        )
        // c -0.35 0.01 -0.71 -0.01 -1.05 0.04
        curveToRelative(
            dx1 = -0.35f,
            dy1 = 0.01f,
            dx2 = -0.71f,
            dy2 = -0.01f,
            dx3 = -1.05f,
            dy3 = 0.04f,
        )
        // a 2.3 2.3 0 0 0 -1.47 0.83
        arcToRelative(
            a = 2.3f,
            b = 2.3f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -1.47f,
            dy1 = 0.83f,
        )
        // a 2 2 0 0 0 -0.36 1.55
        arcToRelative(
            a = 2.0f,
            b = 2.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -0.36f,
            dy1 = 1.55f,
        )
    }
    // M91.66 58.53 c1.53 -1.71 2.57 -3.8 4.03 -5.56 a10 10 0 0 1 2.57 -2.26 5.7 5.7 0 0 1 3.29 -.79 5.8 5.8 0 0 1 3.39 1.61 10 10 0 0 1 2.17 3.12 c.53 1.11 .95 2.28 1.71 3.24 .81 1.02 1.94 1.71 2.97 2.52 a8 8 0 0 1 1.41 1.34 4 4 0 0 1 .86 1.74 3.7 3.7 0 0 1 -.16 1.95 4.4 4.4 0 0 1 -1.09 1.64 6.3 6.3 0 0 1 -3.56 1.6 c-2.62 .37 -5.27 -.41 -7.92 -.34 -2.67 .08 -5.29 1.02 -7.97 .93 a7 7 0 0 1 -3.79 -1.14 5 5 0 0 1 -1.38 -1.45 4 4 0 0 1 -.58 -1.9 A4 4 0 0 1 88 62.92 a7 7 0 0 1 1.01 -1.62 c.81 -.99 1.8 -1.81 2.65 -2.77
    path(
        fill = Brush.radialGradient(
            0.0f to Color(0xFF020204),
            0.73f to Color(0xFF020204),
            1.0f to Color(0xFF5C5C5C),
            center = Offset(x = 97.64026f, y = 60.11981f),
            radius = 10.922866f,
        ),
    ) {
        // M 91.66 58.53
        moveTo(x = 91.66f, y = 58.53f)
        // c 1.53 -1.71 2.57 -3.8 4.03 -5.56
        curveToRelative(
            dx1 = 1.53f,
            dy1 = -1.71f,
            dx2 = 2.57f,
            dy2 = -3.8f,
            dx3 = 4.03f,
            dy3 = -5.56f,
        )
        // a 10 10 0 0 1 2.57 -2.26
        arcToRelative(
            a = 10.0f,
            b = 10.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 2.57f,
            dy1 = -2.26f,
        )
        // a 5.7 5.7 0 0 1 3.29 -0.79
        arcToRelative(
            a = 5.7f,
            b = 5.7f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 3.29f,
            dy1 = -0.79f,
        )
        // a 5.8 5.8 0 0 1 3.39 1.61
        arcToRelative(
            a = 5.8f,
            b = 5.8f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 3.39f,
            dy1 = 1.61f,
        )
        // a 10 10 0 0 1 2.17 3.12
        arcToRelative(
            a = 10.0f,
            b = 10.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 2.17f,
            dy1 = 3.12f,
        )
        // c 0.53 1.11 0.95 2.28 1.71 3.24
        curveToRelative(
            dx1 = 0.53f,
            dy1 = 1.11f,
            dx2 = 0.95f,
            dy2 = 2.28f,
            dx3 = 1.71f,
            dy3 = 3.24f,
        )
        // c 0.81 1.02 1.94 1.71 2.97 2.52
        curveToRelative(
            dx1 = 0.81f,
            dy1 = 1.02f,
            dx2 = 1.94f,
            dy2 = 1.71f,
            dx3 = 2.97f,
            dy3 = 2.52f,
        )
        // a 8 8 0 0 1 1.41 1.34
        arcToRelative(
            a = 8.0f,
            b = 8.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 1.41f,
            dy1 = 1.34f,
        )
        // a 4 4 0 0 1 0.86 1.74
        arcToRelative(
            a = 4.0f,
            b = 4.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 0.86f,
            dy1 = 1.74f,
        )
        // a 3.7 3.7 0 0 1 -0.16 1.95
        arcToRelative(
            a = 3.7f,
            b = 3.7f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -0.16f,
            dy1 = 1.95f,
        )
        // a 4.4 4.4 0 0 1 -1.09 1.64
        arcToRelative(
            a = 4.4f,
            b = 4.4f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -1.09f,
            dy1 = 1.64f,
        )
        // a 6.3 6.3 0 0 1 -3.56 1.6
        arcToRelative(
            a = 6.3f,
            b = 6.3f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -3.56f,
            dy1 = 1.6f,
        )
        // c -2.62 0.37 -5.27 -0.41 -7.92 -0.34
        curveToRelative(
            dx1 = -2.62f,
            dy1 = 0.37f,
            dx2 = -5.27f,
            dy2 = -0.41f,
            dx3 = -7.92f,
            dy3 = -0.34f,
        )
        // c -2.67 0.08 -5.29 1.02 -7.97 0.93
        curveToRelative(
            dx1 = -2.67f,
            dy1 = 0.08f,
            dx2 = -5.29f,
            dy2 = 1.02f,
            dx3 = -7.97f,
            dy3 = 0.93f,
        )
        // a 7 7 0 0 1 -3.79 -1.14
        arcToRelative(
            a = 7.0f,
            b = 7.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -3.79f,
            dy1 = -1.14f,
        )
        // a 5 5 0 0 1 -1.38 -1.45
        arcToRelative(
            a = 5.0f,
            b = 5.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -1.38f,
            dy1 = -1.45f,
        )
        // a 4 4 0 0 1 -0.58 -1.9
        arcToRelative(
            a = 4.0f,
            b = 4.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -0.58f,
            dy1 = -1.9f,
        )
        // A 4 4 0 0 1 88 62.92
        arcTo(
            horizontalEllipseRadius = 4.0f,
            verticalEllipseRadius = 4.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            x1 = 88.0f,
            y1 = 62.92f,
        )
        // a 7 7 0 0 1 1.01 -1.62
        arcToRelative(
            a = 7.0f,
            b = 7.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 1.01f,
            dy1 = -1.62f,
        )
        // c 0.81 -0.99 1.8 -1.81 2.65 -2.77
        curveToRelative(
            dx1 = 0.81f,
            dy1 = -0.99f,
            dx2 = 1.8f,
            dy2 = -1.81f,
            dx3 = 2.65f,
            dy3 = -2.77f,
        )
    }
    // M77.14 75.05 a3 3 0 0 0 .28 .73 3 3 0 0 0 .93 .95 q.55 .39 1.13 .72 a21 21 0 0 1 5.11 4.92 c1.95 2.52 3.68 5.31 6.29 7.14 a13 13 0 0 0 6.28 2.26 21 21 0 0 0 7.83 -.91 27 27 0 0 0 6.7 -2.95 c3.97 -2.44 7.28 -6.02 11.65 -7.63 .95 -.35 1.94 -.6 2.86 -1.03 a4.5 4.5 0 0 0 2.23 -2 c.42 -.88 .4 -1.9 .54 -2.87 .15 -1.03 .48 -2.03 .66 -3.06 a5.2 5.2 0 0 0 -.24 -3.08 4 4 0 0 0 -1.81 -1.79 6 6 0 0 0 -2.51 -.62 c-1.72 -.08 -3.43 .36 -5.16 .52 -2.28 .21 -4.58 -.09 -6.87 -.02 -2.85 .09 -5.66 .73 -8.51 .87 -3.25 .15 -6.49 -.35 -9.74 -.48 a20 20 0 0 0 -4.22 .2 10 10 0 0 0 -3.91 1.51 c-1.13 .78 -2.03 1.84 -3.07 2.74 a8 8 0 0 1 -1.7 1.16 4 4 0 0 1 -1.98 .47 c-.35 -.01 -.72 -.06 -1.05 .04 a2 2 0 0 0 -.56 .35 8.36 8.36 0 0 0 -1.16 1.86
    path(
        fill = Brush.radialGradient(
            0.0f to Color(0xFFD2940A),
            0.75f to Color(0xFFD89C08),
            0.87f to Color(0xFFB67E07),
            1.0f to Color(0xFF946106),
            center = Offset(x = 109.770164f, y = 70.609856f),
            radius = 23.46047f,
        ),
    ) {
        // M 77.14 75.05
        moveTo(x = 77.14f, y = 75.05f)
        // a 3 3 0 0 0 0.28 0.73
        arcToRelative(
            a = 3.0f,
            b = 3.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.28f,
            dy1 = 0.73f,
        )
        // a 3 3 0 0 0 0.93 0.95
        arcToRelative(
            a = 3.0f,
            b = 3.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.93f,
            dy1 = 0.95f,
        )
        // q 0.55 0.39 1.13 0.72
        quadToRelative(
            dx1 = 0.55f,
            dy1 = 0.39f,
            dx2 = 1.13f,
            dy2 = 0.72f,
        )
        // a 21 21 0 0 1 5.11 4.92
        arcToRelative(
            a = 21.0f,
            b = 21.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 5.11f,
            dy1 = 4.92f,
        )
        // c 1.95 2.52 3.68 5.31 6.29 7.14
        curveToRelative(
            dx1 = 1.95f,
            dy1 = 2.52f,
            dx2 = 3.68f,
            dy2 = 5.31f,
            dx3 = 6.29f,
            dy3 = 7.14f,
        )
        // a 13 13 0 0 0 6.28 2.26
        arcToRelative(
            a = 13.0f,
            b = 13.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 6.28f,
            dy1 = 2.26f,
        )
        // a 21 21 0 0 0 7.83 -0.91
        arcToRelative(
            a = 21.0f,
            b = 21.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 7.83f,
            dy1 = -0.91f,
        )
        // a 27 27 0 0 0 6.7 -2.95
        arcToRelative(
            a = 27.0f,
            b = 27.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 6.7f,
            dy1 = -2.95f,
        )
        // c 3.97 -2.44 7.28 -6.02 11.65 -7.63
        curveToRelative(
            dx1 = 3.97f,
            dy1 = -2.44f,
            dx2 = 7.28f,
            dy2 = -6.02f,
            dx3 = 11.65f,
            dy3 = -7.63f,
        )
        // c 0.95 -0.35 1.94 -0.6 2.86 -1.03
        curveToRelative(
            dx1 = 0.95f,
            dy1 = -0.35f,
            dx2 = 1.94f,
            dy2 = -0.6f,
            dx3 = 2.86f,
            dy3 = -1.03f,
        )
        // a 4.5 4.5 0 0 0 2.23 -2
        arcToRelative(
            a = 4.5f,
            b = 4.5f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 2.23f,
            dy1 = -2.0f,
        )
        // c 0.42 -0.88 0.4 -1.9 0.54 -2.87
        curveToRelative(
            dx1 = 0.42f,
            dy1 = -0.88f,
            dx2 = 0.4f,
            dy2 = -1.9f,
            dx3 = 0.54f,
            dy3 = -2.87f,
        )
        // c 0.15 -1.03 0.48 -2.03 0.66 -3.06
        curveToRelative(
            dx1 = 0.15f,
            dy1 = -1.03f,
            dx2 = 0.48f,
            dy2 = -2.03f,
            dx3 = 0.66f,
            dy3 = -3.06f,
        )
        // a 5.2 5.2 0 0 0 -0.24 -3.08
        arcToRelative(
            a = 5.2f,
            b = 5.2f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -0.24f,
            dy1 = -3.08f,
        )
        // a 4 4 0 0 0 -1.81 -1.79
        arcToRelative(
            a = 4.0f,
            b = 4.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -1.81f,
            dy1 = -1.79f,
        )
        // a 6 6 0 0 0 -2.51 -0.62
        arcToRelative(
            a = 6.0f,
            b = 6.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -2.51f,
            dy1 = -0.62f,
        )
        // c -1.72 -0.08 -3.43 0.36 -5.16 0.52
        curveToRelative(
            dx1 = -1.72f,
            dy1 = -0.08f,
            dx2 = -3.43f,
            dy2 = 0.36f,
            dx3 = -5.16f,
            dy3 = 0.52f,
        )
        // c -2.28 0.21 -4.58 -0.09 -6.87 -0.02
        curveToRelative(
            dx1 = -2.28f,
            dy1 = 0.21f,
            dx2 = -4.58f,
            dy2 = -0.09f,
            dx3 = -6.87f,
            dy3 = -0.02f,
        )
        // c -2.85 0.09 -5.66 0.73 -8.51 0.87
        curveToRelative(
            dx1 = -2.85f,
            dy1 = 0.09f,
            dx2 = -5.66f,
            dy2 = 0.73f,
            dx3 = -8.51f,
            dy3 = 0.87f,
        )
        // c -3.25 0.15 -6.49 -0.35 -9.74 -0.48
        curveToRelative(
            dx1 = -3.25f,
            dy1 = 0.15f,
            dx2 = -6.49f,
            dy2 = -0.35f,
            dx3 = -9.74f,
            dy3 = -0.48f,
        )
        // a 20 20 0 0 0 -4.22 0.2
        arcToRelative(
            a = 20.0f,
            b = 20.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -4.22f,
            dy1 = 0.2f,
        )
        // a 10 10 0 0 0 -3.91 1.51
        arcToRelative(
            a = 10.0f,
            b = 10.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -3.91f,
            dy1 = 1.51f,
        )
        // c -1.13 0.78 -2.03 1.84 -3.07 2.74
        curveToRelative(
            dx1 = -1.13f,
            dy1 = 0.78f,
            dx2 = -2.03f,
            dy2 = 1.84f,
            dx3 = -3.07f,
            dy3 = 2.74f,
        )
        // a 8 8 0 0 1 -1.7 1.16
        arcToRelative(
            a = 8.0f,
            b = 8.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -1.7f,
            dy1 = 1.16f,
        )
        // a 4 4 0 0 1 -1.98 0.47
        arcToRelative(
            a = 4.0f,
            b = 4.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -1.98f,
            dy1 = 0.47f,
        )
        // c -0.35 -0.01 -0.72 -0.06 -1.05 0.04
        curveToRelative(
            dx1 = -0.35f,
            dy1 = -0.01f,
            dx2 = -0.72f,
            dy2 = -0.06f,
            dx3 = -1.05f,
            dy3 = 0.04f,
        )
        // a 2 2 0 0 0 -0.56 0.35
        arcToRelative(
            a = 2.0f,
            b = 2.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -0.56f,
            dy1 = 0.35f,
        )
        // a 8.36 8.36 0 0 0 -1.16 1.86
        arcToRelative(
            a = 8.36f,
            b = 8.36f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -1.16f,
            dy1 = 1.86f,
        )
    }
    // M89.9 78.56 a5.73 5.73 0 0 0 3.75 6.76 6.8 6.8 0 0 0 5.21 -.54 5.6 5.6 0 0 0 2.3 -2.06 4 4 0 0 0 .53 -1.46 3.4 3.4 0 0 0 -.16 -1.54 3.5 3.5 0 0 0 -.99 -1.37 5 5 0 0 0 -1.5 -.82 15 15 0 0 0 -3.91 -.68 c-2.02 -.04 -4.9 .34 -5.23 1.71
    path(
        fill = SolidColor(Color(0xFFD9B30D)),
    ) {
        // M 89.9 78.56
        moveTo(x = 89.9f, y = 78.56f)
        // a 5.73 5.73 0 0 0 3.75 6.76
        arcToRelative(
            a = 5.73f,
            b = 5.73f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 3.75f,
            dy1 = 6.76f,
        )
        // a 6.8 6.8 0 0 0 5.21 -0.54
        arcToRelative(
            a = 6.8f,
            b = 6.8f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 5.21f,
            dy1 = -0.54f,
        )
        // a 5.6 5.6 0 0 0 2.3 -2.06
        arcToRelative(
            a = 5.6f,
            b = 5.6f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 2.3f,
            dy1 = -2.06f,
        )
        // a 4 4 0 0 0 0.53 -1.46
        arcToRelative(
            a = 4.0f,
            b = 4.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.53f,
            dy1 = -1.46f,
        )
        // a 3.4 3.4 0 0 0 -0.16 -1.54
        arcToRelative(
            a = 3.4f,
            b = 3.4f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -0.16f,
            dy1 = -1.54f,
        )
        // a 3.5 3.5 0 0 0 -0.99 -1.37
        arcToRelative(
            a = 3.5f,
            b = 3.5f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -0.99f,
            dy1 = -1.37f,
        )
        // a 5 5 0 0 0 -1.5 -0.82
        arcToRelative(
            a = 5.0f,
            b = 5.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -1.5f,
            dy1 = -0.82f,
        )
        // a 15 15 0 0 0 -3.91 -0.68
        arcToRelative(
            a = 15.0f,
            b = 15.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -3.91f,
            dy1 = -0.68f,
        )
        // c -2.02 -0.04 -4.9 0.34 -5.23 1.71
        curveToRelative(
            dx1 = -2.02f,
            dy1 = -0.04f,
            dx2 = -4.9f,
            dy2 = 0.34f,
            dx3 = -5.23f,
            dy3 = 1.71f,
        )
    }
    // M84.31 67.86 a47 47 0 0 0 -3.36 2.2 4 4 0 0 0 -1.45 1.47 3.5 3.5 0 0 0 -.27 1.43 c0 .5 .03 .99 -.04 1.48 -.04 .33 -.13 .66 -.14 .99 a1.18 1.18 0 0 0 .28 .94 1 1 0 0 0 .56 .32 q.31 .08 .64 .14 a6 6 0 0 1 2.66 1.56 c.77 .69 1.47 1.48 2.28 2.13 2.18 1.78 5.07 2.52 7.89 2.56 s5.61 -.54 8.36 -1.16 a50 50 0 0 0 6.39 -1.76 28 28 0 0 0 8.72 -5.19 c1.17 -1.01 2.26 -2.12 3.57 -2.94 1.15 -.73 2.44 -1.21 3.62 -1.9 l.3 -.2 a1 1 0 0 0 .24 -.28 1 1 0 0 0 .03 -.62 2 2 0 0 0 -.31 -.55 5 5 0 0 0 -.49 -.5 c-1.23 -1.05 -2.89 -1.43 -4.51 -1.56 -1.61 -.12 -3.24 -.03 -4.83 -.3 -1.5 -.25 -2.92 -.81 -4.37 -1.27 a30.73 30.73 0 0 0 -15.83 -.86 33 33 0 0 0 -9.94 3.87
    path(
        fill = SolidColor(Color(0xFF604405)),
    ) {
        // M 84.31 67.86
        moveTo(x = 84.31f, y = 67.86f)
        // a 47 47 0 0 0 -3.36 2.2
        arcToRelative(
            a = 47.0f,
            b = 47.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -3.36f,
            dy1 = 2.2f,
        )
        // a 4 4 0 0 0 -1.45 1.47
        arcToRelative(
            a = 4.0f,
            b = 4.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -1.45f,
            dy1 = 1.47f,
        )
        // a 3.5 3.5 0 0 0 -0.27 1.43
        arcToRelative(
            a = 3.5f,
            b = 3.5f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -0.27f,
            dy1 = 1.43f,
        )
        // c 0 0.5 0.03 0.99 -0.04 1.48
        curveToRelative(
            dx1 = 0.0f,
            dy1 = 0.5f,
            dx2 = 0.03f,
            dy2 = 0.99f,
            dx3 = -0.04f,
            dy3 = 1.48f,
        )
        // c -0.04 0.33 -0.13 0.66 -0.14 0.99
        curveToRelative(
            dx1 = -0.04f,
            dy1 = 0.33f,
            dx2 = -0.13f,
            dy2 = 0.66f,
            dx3 = -0.14f,
            dy3 = 0.99f,
        )
        // a 1.18 1.18 0 0 0 0.28 0.94
        arcToRelative(
            a = 1.18f,
            b = 1.18f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.28f,
            dy1 = 0.94f,
        )
        // a 1 1 0 0 0 0.56 0.32
        arcToRelative(
            a = 1.0f,
            b = 1.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.56f,
            dy1 = 0.32f,
        )
        // q 0.31 0.08 0.64 0.14
        quadToRelative(
            dx1 = 0.31f,
            dy1 = 0.08f,
            dx2 = 0.64f,
            dy2 = 0.14f,
        )
        // a 6 6 0 0 1 2.66 1.56
        arcToRelative(
            a = 6.0f,
            b = 6.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 2.66f,
            dy1 = 1.56f,
        )
        // c 0.77 0.69 1.47 1.48 2.28 2.13
        curveToRelative(
            dx1 = 0.77f,
            dy1 = 0.69f,
            dx2 = 1.47f,
            dy2 = 1.48f,
            dx3 = 2.28f,
            dy3 = 2.13f,
        )
        // c 2.18 1.78 5.07 2.52 7.89 2.56
        curveToRelative(
            dx1 = 2.18f,
            dy1 = 1.78f,
            dx2 = 5.07f,
            dy2 = 2.52f,
            dx3 = 7.89f,
            dy3 = 2.56f,
        )
        // s 5.61 -0.54 8.36 -1.16
        reflectiveCurveToRelative(
            dx1 = 5.61f,
            dy1 = -0.54f,
            dx2 = 8.36f,
            dy2 = -1.16f,
        )
        // a 50 50 0 0 0 6.39 -1.76
        arcToRelative(
            a = 50.0f,
            b = 50.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 6.39f,
            dy1 = -1.76f,
        )
        // a 28 28 0 0 0 8.72 -5.19
        arcToRelative(
            a = 28.0f,
            b = 28.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 8.72f,
            dy1 = -5.19f,
        )
        // c 1.17 -1.01 2.26 -2.12 3.57 -2.94
        curveToRelative(
            dx1 = 1.17f,
            dy1 = -1.01f,
            dx2 = 2.26f,
            dy2 = -2.12f,
            dx3 = 3.57f,
            dy3 = -2.94f,
        )
        // c 1.15 -0.73 2.44 -1.21 3.62 -1.9
        curveToRelative(
            dx1 = 1.15f,
            dy1 = -0.73f,
            dx2 = 2.44f,
            dy2 = -1.21f,
            dx3 = 3.62f,
            dy3 = -1.9f,
        )
        // l 0.3 -0.2
        lineToRelative(dx = 0.3f, dy = -0.2f)
        // a 1 1 0 0 0 0.24 -0.28
        arcToRelative(
            a = 1.0f,
            b = 1.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.24f,
            dy1 = -0.28f,
        )
        // a 1 1 0 0 0 0.03 -0.62
        arcToRelative(
            a = 1.0f,
            b = 1.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.03f,
            dy1 = -0.62f,
        )
        // a 2 2 0 0 0 -0.31 -0.55
        arcToRelative(
            a = 2.0f,
            b = 2.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -0.31f,
            dy1 = -0.55f,
        )
        // a 5 5 0 0 0 -0.49 -0.5
        arcToRelative(
            a = 5.0f,
            b = 5.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -0.49f,
            dy1 = -0.5f,
        )
        // c -1.23 -1.05 -2.89 -1.43 -4.51 -1.56
        curveToRelative(
            dx1 = -1.23f,
            dy1 = -1.05f,
            dx2 = -2.89f,
            dy2 = -1.43f,
            dx3 = -4.51f,
            dy3 = -1.56f,
        )
        // c -1.61 -0.12 -3.24 -0.03 -4.83 -0.3
        curveToRelative(
            dx1 = -1.61f,
            dy1 = -0.12f,
            dx2 = -3.24f,
            dy2 = -0.03f,
            dx3 = -4.83f,
            dy3 = -0.3f,
        )
        // c -1.5 -0.25 -2.92 -0.81 -4.37 -1.27
        curveToRelative(
            dx1 = -1.5f,
            dy1 = -0.25f,
            dx2 = -2.92f,
            dy2 = -0.81f,
            dx3 = -4.37f,
            dy3 = -1.27f,
        )
        // a 30.73 30.73 0 0 0 -15.83 -0.86
        arcToRelative(
            a = 30.73f,
            b = 30.73f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -15.83f,
            dy1 = -0.86f,
        )
        // a 33 33 0 0 0 -9.94 3.87
        arcToRelative(
            a = 33.0f,
            b = 33.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -9.94f,
            dy1 = 3.87f,
        )
    }
    // M83.94 63.95 a21 21 0 0 0 -4.43 4.04 10 10 0 0 0 -1.74 2.94 c-.29 .86 -.39 1.76 -.57 2.65 -.07 .33 -.15 .66 -.14 1 a2 2 0 0 0 .07 .5 1 1 0 0 0 .25 .43 1.5 1.5 0 0 0 .74 .37 q.42 .06 .84 .09 a8 8 0 0 1 3.44 1.47 c1.04 .71 2 1.55 3.07 2.22 a16 16 0 0 0 7.95 2.26 35 35 0 0 0 8.3 -.86 37 37 0 0 0 6.39 -1.76 c3.16 -1.25 6.01 -3.16 8.72 -5.19 a36 36 0 0 0 3.57 -2.94 q.55 -.56 1.14 -1.08 a4 4 0 0 1 1.35 -.78 5 5 0 0 1 2.37 -.04 c.59 .1 1.18 .23 1.78 .21 a3 3 0 0 0 .88 -.18 2 2 0 0 0 .73 -.52 1.7 1.7 0 0 0 .38 -1.09 q-.01 -.61 -.32 -1.13 a4 4 0 0 0 -1.81 -1.46 c-.99 -.44 -2.06 -.65 -3.11 -.91 a44 44 0 0 1 -9.34 -3.41 c-1.48 -.73 -2.92 -1.54 -4.37 -2.32 a27 27 0 0 0 -4.64 -2.07 c-3.64 -1.1 -7.6 -.74 -11.19 .51 a24 24 0 0 0 -10.31 7.05
    path(
        fill = Brush.linearGradient(
            0.0f to Color(0xFFAD780A),
            0.12f to Color(0xFFD89E08),
            0.25f to Color(0xFFEDB80B),
            0.39f to Color(0xFFEBC80D),
            0.53f to Color(0xFFF5D838),
            0.77f to Color(0xFFF6D811),
            1.0f to Color(0xFFF5CD31),
            start = Offset(x = 78.09f, y = 69.26f),
            end = Offset(x = 126.77f, y = 68.88f),
        ),
    ) {
        // M 83.94 63.95
        moveTo(x = 83.94f, y = 63.95f)
        // a 21 21 0 0 0 -4.43 4.04
        arcToRelative(
            a = 21.0f,
            b = 21.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -4.43f,
            dy1 = 4.04f,
        )
        // a 10 10 0 0 0 -1.74 2.94
        arcToRelative(
            a = 10.0f,
            b = 10.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -1.74f,
            dy1 = 2.94f,
        )
        // c -0.29 0.86 -0.39 1.76 -0.57 2.65
        curveToRelative(
            dx1 = -0.29f,
            dy1 = 0.86f,
            dx2 = -0.39f,
            dy2 = 1.76f,
            dx3 = -0.57f,
            dy3 = 2.65f,
        )
        // c -0.07 0.33 -0.15 0.66 -0.14 1
        curveToRelative(
            dx1 = -0.07f,
            dy1 = 0.33f,
            dx2 = -0.15f,
            dy2 = 0.66f,
            dx3 = -0.14f,
            dy3 = 1.0f,
        )
        // a 2 2 0 0 0 0.07 0.5
        arcToRelative(
            a = 2.0f,
            b = 2.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.07f,
            dy1 = 0.5f,
        )
        // a 1 1 0 0 0 0.25 0.43
        arcToRelative(
            a = 1.0f,
            b = 1.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.25f,
            dy1 = 0.43f,
        )
        // a 1.5 1.5 0 0 0 0.74 0.37
        arcToRelative(
            a = 1.5f,
            b = 1.5f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.74f,
            dy1 = 0.37f,
        )
        // q 0.42 0.06 0.84 0.09
        quadToRelative(
            dx1 = 0.42f,
            dy1 = 0.06f,
            dx2 = 0.84f,
            dy2 = 0.09f,
        )
        // a 8 8 0 0 1 3.44 1.47
        arcToRelative(
            a = 8.0f,
            b = 8.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 3.44f,
            dy1 = 1.47f,
        )
        // c 1.04 0.71 2 1.55 3.07 2.22
        curveToRelative(
            dx1 = 1.04f,
            dy1 = 0.71f,
            dx2 = 2.0f,
            dy2 = 1.55f,
            dx3 = 3.07f,
            dy3 = 2.22f,
        )
        // a 16 16 0 0 0 7.95 2.26
        arcToRelative(
            a = 16.0f,
            b = 16.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 7.95f,
            dy1 = 2.26f,
        )
        // a 35 35 0 0 0 8.3 -0.86
        arcToRelative(
            a = 35.0f,
            b = 35.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 8.3f,
            dy1 = -0.86f,
        )
        // a 37 37 0 0 0 6.39 -1.76
        arcToRelative(
            a = 37.0f,
            b = 37.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 6.39f,
            dy1 = -1.76f,
        )
        // c 3.16 -1.25 6.01 -3.16 8.72 -5.19
        curveToRelative(
            dx1 = 3.16f,
            dy1 = -1.25f,
            dx2 = 6.01f,
            dy2 = -3.16f,
            dx3 = 8.72f,
            dy3 = -5.19f,
        )
        // a 36 36 0 0 0 3.57 -2.94
        arcToRelative(
            a = 36.0f,
            b = 36.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 3.57f,
            dy1 = -2.94f,
        )
        // q 0.55 -0.56 1.14 -1.08
        quadToRelative(
            dx1 = 0.55f,
            dy1 = -0.56f,
            dx2 = 1.14f,
            dy2 = -1.08f,
        )
        // a 4 4 0 0 1 1.35 -0.78
        arcToRelative(
            a = 4.0f,
            b = 4.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 1.35f,
            dy1 = -0.78f,
        )
        // a 5 5 0 0 1 2.37 -0.04
        arcToRelative(
            a = 5.0f,
            b = 5.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 2.37f,
            dy1 = -0.04f,
        )
        // c 0.59 0.1 1.18 0.23 1.78 0.21
        curveToRelative(
            dx1 = 0.59f,
            dy1 = 0.1f,
            dx2 = 1.18f,
            dy2 = 0.23f,
            dx3 = 1.78f,
            dy3 = 0.21f,
        )
        // a 3 3 0 0 0 0.88 -0.18
        arcToRelative(
            a = 3.0f,
            b = 3.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.88f,
            dy1 = -0.18f,
        )
        // a 2 2 0 0 0 0.73 -0.52
        arcToRelative(
            a = 2.0f,
            b = 2.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.73f,
            dy1 = -0.52f,
        )
        // a 1.7 1.7 0 0 0 0.38 -1.09
        arcToRelative(
            a = 1.7f,
            b = 1.7f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.38f,
            dy1 = -1.09f,
        )
        // q -0.01 -0.61 -0.32 -1.13
        quadToRelative(
            dx1 = -0.01f,
            dy1 = -0.61f,
            dx2 = -0.32f,
            dy2 = -1.13f,
        )
        // a 4 4 0 0 0 -1.81 -1.46
        arcToRelative(
            a = 4.0f,
            b = 4.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -1.81f,
            dy1 = -1.46f,
        )
        // c -0.99 -0.44 -2.06 -0.65 -3.11 -0.91
        curveToRelative(
            dx1 = -0.99f,
            dy1 = -0.44f,
            dx2 = -2.06f,
            dy2 = -0.65f,
            dx3 = -3.11f,
            dy3 = -0.91f,
        )
        // a 44 44 0 0 1 -9.34 -3.41
        arcToRelative(
            a = 44.0f,
            b = 44.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -9.34f,
            dy1 = -3.41f,
        )
        // c -1.48 -0.73 -2.92 -1.54 -4.37 -2.32
        curveToRelative(
            dx1 = -1.48f,
            dy1 = -0.73f,
            dx2 = -2.92f,
            dy2 = -1.54f,
            dx3 = -4.37f,
            dy3 = -2.32f,
        )
        // a 27 27 0 0 0 -4.64 -2.07
        arcToRelative(
            a = 27.0f,
            b = 27.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -4.64f,
            dy1 = -2.07f,
        )
        // c -3.64 -1.1 -7.6 -0.74 -11.19 0.51
        curveToRelative(
            dx1 = -3.64f,
            dy1 = -1.1f,
            dx2 = -7.6f,
            dy2 = -0.74f,
            dx3 = -11.19f,
            dy3 = 0.51f,
        )
        // a 24 24 0 0 0 -10.31 7.05
        arcToRelative(
            a = 24.0f,
            b = 24.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -10.31f,
            dy1 = 7.05f,
        )
    }
    // M109.45 64.75 a1.6 1.6 0 0 0 -.78 -.51 2 2 0 0 0 -.93 -.04 4.4 4.4 0 0 0 -1.7 .8 19 19 0 0 0 -3.91 3.64 14 14 0 0 0 -3.16 6.27 4 4 0 0 0 -.07 1.19 1.8 1.8 0 0 0 .49 1.07 1.6 1.6 0 0 0 .92 .45 2 2 0 0 0 1.04 -.11 5 5 0 0 0 1.74 -1.15 c2.87 -2.58 5.47 -5.66 6.51 -9.38 a4 4 0 0 0 .19 -1.14 q.01 -.6 -.34 -1.09
    path(
        fill = SolidColor(Color(0xFFF6DA4A)),
    ) {
        // M 109.45 64.75
        moveTo(x = 109.45f, y = 64.75f)
        // a 1.6 1.6 0 0 0 -0.78 -0.51
        arcToRelative(
            a = 1.6f,
            b = 1.6f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -0.78f,
            dy1 = -0.51f,
        )
        // a 2 2 0 0 0 -0.93 -0.04
        arcToRelative(
            a = 2.0f,
            b = 2.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -0.93f,
            dy1 = -0.04f,
        )
        // a 4.4 4.4 0 0 0 -1.7 0.8
        arcToRelative(
            a = 4.4f,
            b = 4.4f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -1.7f,
            dy1 = 0.8f,
        )
        // a 19 19 0 0 0 -3.91 3.64
        arcToRelative(
            a = 19.0f,
            b = 19.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -3.91f,
            dy1 = 3.64f,
        )
        // a 14 14 0 0 0 -3.16 6.27
        arcToRelative(
            a = 14.0f,
            b = 14.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -3.16f,
            dy1 = 6.27f,
        )
        // a 4 4 0 0 0 -0.07 1.19
        arcToRelative(
            a = 4.0f,
            b = 4.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -0.07f,
            dy1 = 1.19f,
        )
        // a 1.8 1.8 0 0 0 0.49 1.07
        arcToRelative(
            a = 1.8f,
            b = 1.8f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.49f,
            dy1 = 1.07f,
        )
        // a 1.6 1.6 0 0 0 0.92 0.45
        arcToRelative(
            a = 1.6f,
            b = 1.6f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.92f,
            dy1 = 0.45f,
        )
        // a 2 2 0 0 0 1.04 -0.11
        arcToRelative(
            a = 2.0f,
            b = 2.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 1.04f,
            dy1 = -0.11f,
        )
        // a 5 5 0 0 0 1.74 -1.15
        arcToRelative(
            a = 5.0f,
            b = 5.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 1.74f,
            dy1 = -1.15f,
        )
        // c 2.87 -2.58 5.47 -5.66 6.51 -9.38
        curveToRelative(
            dx1 = 2.87f,
            dy1 = -2.58f,
            dx2 = 5.47f,
            dy2 = -5.66f,
            dx3 = 6.51f,
            dy3 = -9.38f,
        )
        // a 4 4 0 0 0 0.19 -1.14
        arcToRelative(
            a = 4.0f,
            b = 4.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 0.19f,
            dy1 = -1.14f,
        )
        // q 0.01 -0.6 -0.34 -1.09
        quadToRelative(
            dx1 = 0.01f,
            dy1 = -0.6f,
            dx2 = -0.34f,
            dy2 = -1.09f,
        )
    }
    // M92.72 59.06 c-.77 -.25 -2.03 1.1 -1.62 1.79 .11 .19 .46 .43 .7 .3 .35 -.19 .64 -.89 1.02 -1.16 .25 -.18 .2 -.84 -.1 -.93
    path(
        fill = Brush.radialGradient(
            0.0f to Color(0xFF3A2903),
            0.55f to Color(0xFF735208),
            1.0f to Color(0xFFAC8C04),
            center = Offset(x = 92.11f, y = 59.88f),
            radius = 1.3709121f,
        ),
        fillAlpha = 0.8f,
        strokeAlpha = 0.8f,
    ) {
        // M 92.72 59.06
        moveTo(x = 92.72f, y = 59.06f)
        // c -0.77 -0.25 -2.03 1.1 -1.62 1.79
        curveToRelative(
            dx1 = -0.77f,
            dy1 = -0.25f,
            dx2 = -2.03f,
            dy2 = 1.1f,
            dx3 = -1.62f,
            dy3 = 1.79f,
        )
        // c 0.11 0.19 0.46 0.43 0.7 0.3
        curveToRelative(
            dx1 = 0.11f,
            dy1 = 0.19f,
            dx2 = 0.46f,
            dy2 = 0.43f,
            dx3 = 0.7f,
            dy3 = 0.3f,
        )
        // c 0.35 -0.19 0.64 -0.89 1.02 -1.16
        curveToRelative(
            dx1 = 0.35f,
            dy1 = -0.19f,
            dx2 = 0.64f,
            dy2 = -0.89f,
            dx3 = 1.02f,
            dy3 = -1.16f,
        )
        // c 0.25 -0.18 0.2 -0.84 -0.1 -0.93
        curveToRelative(
            dx1 = 0.25f,
            dy1 = -0.18f,
            dx2 = 0.2f,
            dy2 = -0.84f,
            dx3 = -0.1f,
            dy3 = -0.93f,
        )
    }
    // M102.56 59.42 c.2 .64 1.23 .53 1.83 .84 .52 .27 .94 .86 1.53 .88 .56 .01 1.44 -.2 1.51 -.76 .09 -.73 -.98 -1.2 -1.67 -1.47 -.89 -.34 -2.03 -.52 -2.86 -.06 -.19 .11 -.4 .36 -.34 .57
    path(
        fill = Brush.radialGradient(
            0.0f to Color(0xFF3A2903),
            0.55f to Color(0xFF735208),
            1.0f to Color(0xFFAC8C04),
            center = Offset(x = 104.65f, y = 59.7f),
            radius = 2.2751703f,
        ),
        fillAlpha = 0.8f,
        strokeAlpha = 0.8f,
    ) {
        // M 102.56 59.42
        moveTo(x = 102.56f, y = 59.42f)
        // c 0.2 0.64 1.23 0.53 1.83 0.84
        curveToRelative(
            dx1 = 0.2f,
            dy1 = 0.64f,
            dx2 = 1.23f,
            dy2 = 0.53f,
            dx3 = 1.83f,
            dy3 = 0.84f,
        )
        // c 0.52 0.27 0.94 0.86 1.53 0.88
        curveToRelative(
            dx1 = 0.52f,
            dy1 = 0.27f,
            dx2 = 0.94f,
            dy2 = 0.86f,
            dx3 = 1.53f,
            dy3 = 0.88f,
        )
        // c 0.56 0.01 1.44 -0.2 1.51 -0.76
        curveToRelative(
            dx1 = 0.56f,
            dy1 = 0.01f,
            dx2 = 1.44f,
            dy2 = -0.2f,
            dx3 = 1.51f,
            dy3 = -0.76f,
        )
        // c 0.09 -0.73 -0.98 -1.2 -1.67 -1.47
        curveToRelative(
            dx1 = 0.09f,
            dy1 = -0.73f,
            dx2 = -0.98f,
            dy2 = -1.2f,
            dx3 = -1.67f,
            dy3 = -1.47f,
        )
        // c -0.89 -0.34 -2.03 -0.52 -2.86 -0.06
        curveToRelative(
            dx1 = -0.89f,
            dy1 = -0.34f,
            dx2 = -2.03f,
            dy2 = -0.52f,
            dx3 = -2.86f,
            dy3 = -0.06f,
        )
        // c -0.19 0.11 -0.4 0.36 -0.34 0.57
        curveToRelative(
            dx1 = -0.19f,
            dy1 = 0.11f,
            dx2 = -0.4f,
            dy2 = 0.36f,
            dx3 = -0.34f,
            dy3 = 0.57f,
        )
    }
    // M129.27 69.15 a2.42 3.1 16.94 0 1 -2.81 3.04 2.42 3.1 16.94 0 1 -2.12 -3.04 2.42 3.1 16.94 0 1 2.81 -3.05 2.42 3.1 16.94 0 1 2.12 3.05
    path(
        fill = Brush.linearGradient(
            0.0f to Color(0xFFF5CE2D),
            1.0f to Color(0xFFD79B08),
            start = Offset(x = 126.74f, y = 67.49f),
            end = Offset(x = 126.74f, y = 71.09f),
        ),
    ) {
        // M 129.27 69.15
        moveTo(x = 129.27f, y = 69.15f)
        // a 2.42 3.1 16.94 0 1 -2.81 3.04
        arcToRelative(
            a = 2.42f,
            b = 3.1f,
            theta = 16.94f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -2.81f,
            dy1 = 3.04f,
        )
        // a 2.42 3.1 16.94 0 1 -2.12 -3.04
        arcToRelative(
            a = 2.42f,
            b = 3.1f,
            theta = 16.94f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = -2.12f,
            dy1 = -3.04f,
        )
        // a 2.42 3.1 16.94 0 1 2.81 -3.05
        arcToRelative(
            a = 2.42f,
            b = 3.1f,
            theta = 16.94f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 2.81f,
            dy1 = -3.05f,
        )
        // a 2.42 3.1 16.94 0 1 2.12 3.05
        arcToRelative(
            a = 2.42f,
            b = 3.1f,
            theta = 16.94f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            dx1 = 2.12f,
            dy1 = 3.05f,
        )
    }
}

@Suppress("ObjectPropertyName")
private var _tux: ImageVector? = null
