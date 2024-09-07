package dev.tonholo.sampleApp.ui.icon.svg

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathData
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val IllustrationSvgOptimized: ImageVector
    get() {
        val current = _illustrationSvgOptimized
        if (current != null) return current

        return ImageVector.Builder(
            name = "dev.tonholo.sampleApp.ui.theme.SampleAppTheme.IllustrationSvgOptimized",
            defaultWidth = 116.0.dp,
            defaultHeight = 114.0.dp,
            viewportWidth = 116.0f,
            viewportHeight = 114.0f,
        ).apply {
            // M56.79 113.12 c-3.02 0 -6.07 -.25 -9.07 -.74 l-.48 -.07 -.18 -.04 a56 56 0 0 1 -27.15 -12.9 24 24 0 0 1 -5.7 -3.56 16 16 0 0 1 -5.2 -8.02 3 3 0 0 1 -.07 -1.35 L8 84.84 c-2.72 -1.15 -4.4 -4.23 -3.94 -7.43 l.13 -.66 a54 54 0 0 1 -1.94 -6.21 6.3 6.3 0 0 1 -1.39 -8.03 56 56 0 0 1 .58 -15.2 A56 56 0 0 1 66.4 1.73 a56.06 56.06 0 0 1 46.36 52.73 c2.8 2 3.85 5.64 2.45 8.5 a6.6 6.6 0 0 1 -3.12 3.02 l-.12 .7 a56 56 0 0 1 -.69 3.33 8 8 0 0 1 .5 1.87 7 7 0 0 1 -3.49 7.22 55 55 0 0 1 -5.2 9.49 53 53 0 0 1 2.47 .8 l.76 .27 a3.42 3.42 0 0 1 1.84 4.96 16 16 0 0 1 -7.21 6.28 27 27 0 0 1 -8.45 2.11 l-1.4 .13 a38 38 0 0 0 -2.97 .34 l-.95 .63 -.5 .32 -.07 .04 -1.42 .87 h-.02 l-.67 .4 -.15 .09 a57 57 0 0 1 -2.08 1.11 l-.17 .09 -1.26 .62 -.65 .3 -1.31 .6 -.59 .24 a57 57 0 0 1 -4.72 1.73 55 55 0 0 1 -16.78 2.6
            path(
                fill = SolidColor(Color(0xFFCCB0AB)),
            ) {
                // M 56.79 113.12
                moveTo(x = 56.79f, y = 113.12f)
                // c -3.02 0 -6.07 -0.25 -9.07 -0.74
                curveToRelative(
                    dx1 = -3.02f,
                    dy1 = 0.0f,
                    dx2 = -6.07f,
                    dy2 = -0.25f,
                    dx3 = -9.07f,
                    dy3 = -0.74f,
                )
                // l -0.48 -0.07
                lineToRelative(dx = -0.48f, dy = -0.07f)
                // l -0.18 -0.04
                lineToRelative(dx = -0.18f, dy = -0.04f)
                // a 56 56 0 0 1 -27.15 -12.9
                arcToRelative(
                    a = 56.0f,
                    b = 56.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -27.15f,
                    dy1 = -12.9f,
                )
                // a 24 24 0 0 1 -5.7 -3.56
                arcToRelative(
                    a = 24.0f,
                    b = 24.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -5.7f,
                    dy1 = -3.56f,
                )
                // a 16 16 0 0 1 -5.2 -8.02
                arcToRelative(
                    a = 16.0f,
                    b = 16.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -5.2f,
                    dy1 = -8.02f,
                )
                // a 3 3 0 0 1 -0.07 -1.35
                arcToRelative(
                    a = 3.0f,
                    b = 3.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.07f,
                    dy1 = -1.35f,
                )
                // L 8 84.84
                lineTo(x = 8.0f, y = 84.84f)
                // c -2.72 -1.15 -4.4 -4.23 -3.94 -7.43
                curveToRelative(
                    dx1 = -2.72f,
                    dy1 = -1.15f,
                    dx2 = -4.4f,
                    dy2 = -4.23f,
                    dx3 = -3.94f,
                    dy3 = -7.43f,
                )
                // l 0.13 -0.66
                lineToRelative(dx = 0.13f, dy = -0.66f)
                // a 54 54 0 0 1 -1.94 -6.21
                arcToRelative(
                    a = 54.0f,
                    b = 54.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.94f,
                    dy1 = -6.21f,
                )
                // a 6.3 6.3 0 0 1 -1.39 -8.03
                arcToRelative(
                    a = 6.3f,
                    b = 6.3f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.39f,
                    dy1 = -8.03f,
                )
                // a 56 56 0 0 1 0.58 -15.2
                arcToRelative(
                    a = 56.0f,
                    b = 56.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.58f,
                    dy1 = -15.2f,
                )
                // A 56 56 0 0 1 66.4 1.73
                arcTo(
                    horizontalEllipseRadius = 56.0f,
                    verticalEllipseRadius = 56.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 66.4f,
                    y1 = 1.73f,
                )
                // a 56.06 56.06 0 0 1 46.36 52.73
                arcToRelative(
                    a = 56.06f,
                    b = 56.06f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 46.36f,
                    dy1 = 52.73f,
                )
                // c 2.8 2 3.85 5.64 2.45 8.5
                curveToRelative(
                    dx1 = 2.8f,
                    dy1 = 2.0f,
                    dx2 = 3.85f,
                    dy2 = 5.64f,
                    dx3 = 2.45f,
                    dy3 = 8.5f,
                )
                // a 6.6 6.6 0 0 1 -3.12 3.02
                arcToRelative(
                    a = 6.6f,
                    b = 6.6f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -3.12f,
                    dy1 = 3.02f,
                )
                // l -0.12 0.7
                lineToRelative(dx = -0.12f, dy = 0.7f)
                // a 56 56 0 0 1 -0.69 3.33
                arcToRelative(
                    a = 56.0f,
                    b = 56.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.69f,
                    dy1 = 3.33f,
                )
                // a 8 8 0 0 1 0.5 1.87
                arcToRelative(
                    a = 8.0f,
                    b = 8.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.5f,
                    dy1 = 1.87f,
                )
                // a 7 7 0 0 1 -3.49 7.22
                arcToRelative(
                    a = 7.0f,
                    b = 7.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -3.49f,
                    dy1 = 7.22f,
                )
                // a 55 55 0 0 1 -5.2 9.49
                arcToRelative(
                    a = 55.0f,
                    b = 55.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -5.2f,
                    dy1 = 9.49f,
                )
                // a 53 53 0 0 1 2.47 0.8
                arcToRelative(
                    a = 53.0f,
                    b = 53.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 2.47f,
                    dy1 = 0.8f,
                )
                // l 0.76 0.27
                lineToRelative(dx = 0.76f, dy = 0.27f)
                // a 3.42 3.42 0 0 1 1.84 4.96
                arcToRelative(
                    a = 3.42f,
                    b = 3.42f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 1.84f,
                    dy1 = 4.96f,
                )
                // a 16 16 0 0 1 -7.21 6.28
                arcToRelative(
                    a = 16.0f,
                    b = 16.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -7.21f,
                    dy1 = 6.28f,
                )
                // a 27 27 0 0 1 -8.45 2.11
                arcToRelative(
                    a = 27.0f,
                    b = 27.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -8.45f,
                    dy1 = 2.11f,
                )
                // l -1.4 0.13
                lineToRelative(dx = -1.4f, dy = 0.13f)
                // a 38 38 0 0 0 -2.97 0.34
                arcToRelative(
                    a = 38.0f,
                    b = 38.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -2.97f,
                    dy1 = 0.34f,
                )
                // l -0.95 0.63
                lineToRelative(dx = -0.95f, dy = 0.63f)
                // l -0.5 0.32
                lineToRelative(dx = -0.5f, dy = 0.32f)
                // l -0.07 0.04
                lineToRelative(dx = -0.07f, dy = 0.04f)
                // l -1.42 0.87
                lineToRelative(dx = -1.42f, dy = 0.87f)
                // h -0.02
                horizontalLineToRelative(dx = -0.02f)
                // l -0.67 0.4
                lineToRelative(dx = -0.67f, dy = 0.4f)
                // l -0.15 0.09
                lineToRelative(dx = -0.15f, dy = 0.09f)
                // a 57 57 0 0 1 -2.08 1.11
                arcToRelative(
                    a = 57.0f,
                    b = 57.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -2.08f,
                    dy1 = 1.11f,
                )
                // l -0.17 0.09
                lineToRelative(dx = -0.17f, dy = 0.09f)
                // l -1.26 0.62
                lineToRelative(dx = -1.26f, dy = 0.62f)
                // l -0.65 0.3
                lineToRelative(dx = -0.65f, dy = 0.3f)
                // l -1.31 0.6
                lineToRelative(dx = -1.31f, dy = 0.6f)
                // l -0.59 0.24
                lineToRelative(dx = -0.59f, dy = 0.24f)
                // a 57 57 0 0 1 -4.72 1.73
                arcToRelative(
                    a = 57.0f,
                    b = 57.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -4.72f,
                    dy1 = 1.73f,
                )
                // a 55 55 0 0 1 -16.78 2.6
                arcToRelative(
                    a = 55.0f,
                    b = 55.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -16.78f,
                    dy1 = 2.6f,
                )
            }
            // M109.7 56.82 a52 52 0 0 1 -.8 9.55 52 52 0 0 1 -2.88 10.26 52 52 0 0 1 -9.13 15.09 53 53 0 0 1 -9.8 8.8 49 49 0 0 1 -3.97 2.52 53 53 0 0 1 -10.26 4.48 52 52 0 0 1 -24.33 1.75 l-.63 -.1 A52.5 52.5 0 0 1 22.1 96.73 h-.02 A53 53 0 0 1 8.1 76.88 a53 53 0 0 1 -2.54 -8.17 A52.7 52.7 0 0 1 5.1 48.17 a52.7 52.7 0 0 1 104.6 8.65
            path(
                fill = SolidColor(Color(0xFFFFCE86)),
            ) {
                // M 109.7 56.82
                moveTo(x = 109.7f, y = 56.82f)
                // a 52 52 0 0 1 -0.8 9.55
                arcToRelative(
                    a = 52.0f,
                    b = 52.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.8f,
                    dy1 = 9.55f,
                )
                // a 52 52 0 0 1 -2.88 10.26
                arcToRelative(
                    a = 52.0f,
                    b = 52.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -2.88f,
                    dy1 = 10.26f,
                )
                // a 52 52 0 0 1 -9.13 15.09
                arcToRelative(
                    a = 52.0f,
                    b = 52.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -9.13f,
                    dy1 = 15.09f,
                )
                // a 53 53 0 0 1 -9.8 8.8
                arcToRelative(
                    a = 53.0f,
                    b = 53.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -9.8f,
                    dy1 = 8.8f,
                )
                // a 49 49 0 0 1 -3.97 2.52
                arcToRelative(
                    a = 49.0f,
                    b = 49.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -3.97f,
                    dy1 = 2.52f,
                )
                // a 53 53 0 0 1 -10.26 4.48
                arcToRelative(
                    a = 53.0f,
                    b = 53.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -10.26f,
                    dy1 = 4.48f,
                )
                // a 52 52 0 0 1 -24.33 1.75
                arcToRelative(
                    a = 52.0f,
                    b = 52.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -24.33f,
                    dy1 = 1.75f,
                )
                // l -0.63 -0.1
                lineToRelative(dx = -0.63f, dy = -0.1f)
                // A 52.5 52.5 0 0 1 22.1 96.73
                arcTo(
                    horizontalEllipseRadius = 52.5f,
                    verticalEllipseRadius = 52.5f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 22.1f,
                    y1 = 96.73f,
                )
                // h -0.02
                horizontalLineToRelative(dx = -0.02f)
                // A 53 53 0 0 1 8.1 76.88
                arcTo(
                    horizontalEllipseRadius = 53.0f,
                    verticalEllipseRadius = 53.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 8.1f,
                    y1 = 76.88f,
                )
                // a 53 53 0 0 1 -2.54 -8.17
                arcToRelative(
                    a = 53.0f,
                    b = 53.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -2.54f,
                    dy1 = -8.17f,
                )
                // A 52.7 52.7 0 0 1 5.1 48.17
                arcTo(
                    horizontalEllipseRadius = 52.7f,
                    verticalEllipseRadius = 52.7f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 5.1f,
                    y1 = 48.17f,
                )
                // a 52.7 52.7 0 0 1 104.6 8.65
                arcToRelative(
                    a = 52.7f,
                    b = 52.7f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 104.6f,
                    dy1 = 8.65f,
                )
            }
            // M39.05 62.75 s6.84 -13.74 7.61 -15.4 c.78 -1.64 1.66 -3.24 3.02 -3.34 s2.62 .4 3.2 1.27 c.58 .88 1.2 1.76 1.2 1.76z
            path(
                fill = SolidColor(Color(0xFF7E5C63)),
            ) {
                // M 39.05 62.75
                moveTo(x = 39.05f, y = 62.75f)
                // s 6.84 -13.74 7.61 -15.4
                reflectiveCurveToRelative(
                    dx1 = 6.84f,
                    dy1 = -13.74f,
                    dx2 = 7.61f,
                    dy2 = -15.4f,
                )
                // c 0.78 -1.64 1.66 -3.24 3.02 -3.34
                curveToRelative(
                    dx1 = 0.78f,
                    dy1 = -1.64f,
                    dx2 = 1.66f,
                    dy2 = -3.24f,
                    dx3 = 3.02f,
                    dy3 = -3.34f,
                )
                // s 2.62 0.4 3.2 1.27
                reflectiveCurveToRelative(
                    dx1 = 2.62f,
                    dy1 = 0.4f,
                    dx2 = 3.2f,
                    dy2 = 1.27f,
                )
                // c 0.58 0.88 1.2 1.76 1.2 1.76z
                curveToRelative(
                    dx1 = 0.58f,
                    dy1 = 0.88f,
                    dx2 = 1.2f,
                    dy2 = 1.76f,
                    dx3 = 1.2f,
                    dy3 = 1.76f,
                )
                close()
            }
            // M87.28 100.4 a53 53 0 0 1 -14.42 7.12 52 52 0 0 1 -24.33 1.75 c-4.44 -3.73 -8.9 -8.33 -9.44 -12.18 l-.32 -2.33 a201 201 0 0 1 -1.25 -12.04 c-.36 -5.16 -.39 -10 .43 -12.13 v-.02 a3 3 0 0 1 .5 -.82 l.02 -.03 .19 -.14 -.01 -.06 -.15 -.68 v-.03 a17 17 0 0 1 -.36 -2.92 l.01 -.25 a4 4 0 0 1 .28 -1.61 l.02 -.04 a16 16 0 0 1 1.14 -1.8 76 76 0 0 1 1.93 -2.65 l.14 -.19 a94 94 0 0 1 4.8 -5.8 q.97 -1.08 1.93 -2.06 c2 -2 3.92 -3.6 5.43 -4.17 a3 3 0 0 1 1.4 -.23 l.2 .05 .21 .09 a2 2 0 0 1 .9 .8 l.02 .01 a3.3 3.3 0 0 1 .47 1.67 c.08 1.65 -.58 3.8 -1.49 5.83 l-.25 .53 a21 21 0 0 1 -3.3 5.13 l-.12 .11 c-2.1 2.17 -2.23 4.04 -2.24 4.27 v.02 s2 -.48 4.73 -1.02 l.11 -.02 c1.48 -.3 3.17 -.6 4.87 -.86 2.85 -.44 5.72 -.73 7.7 -.57 a8.3 8.3 0 0 1 7.1 5.37z
            path(
                fill = SolidColor(Color(0xFF7E5C63)),
            ) {
                // M 87.28 100.4
                moveTo(x = 87.28f, y = 100.4f)
                // a 53 53 0 0 1 -14.42 7.12
                arcToRelative(
                    a = 53.0f,
                    b = 53.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -14.42f,
                    dy1 = 7.12f,
                )
                // a 52 52 0 0 1 -24.33 1.75
                arcToRelative(
                    a = 52.0f,
                    b = 52.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -24.33f,
                    dy1 = 1.75f,
                )
                // c -4.44 -3.73 -8.9 -8.33 -9.44 -12.18
                curveToRelative(
                    dx1 = -4.44f,
                    dy1 = -3.73f,
                    dx2 = -8.9f,
                    dy2 = -8.33f,
                    dx3 = -9.44f,
                    dy3 = -12.18f,
                )
                // l -0.32 -2.33
                lineToRelative(dx = -0.32f, dy = -2.33f)
                // a 201 201 0 0 1 -1.25 -12.04
                arcToRelative(
                    a = 201.0f,
                    b = 201.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.25f,
                    dy1 = -12.04f,
                )
                // c -0.36 -5.16 -0.39 -10 0.43 -12.13
                curveToRelative(
                    dx1 = -0.36f,
                    dy1 = -5.16f,
                    dx2 = -0.39f,
                    dy2 = -10.0f,
                    dx3 = 0.43f,
                    dy3 = -12.13f,
                )
                // v -0.02
                verticalLineToRelative(dy = -0.02f)
                // a 3 3 0 0 1 0.5 -0.82
                arcToRelative(
                    a = 3.0f,
                    b = 3.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.5f,
                    dy1 = -0.82f,
                )
                // l 0.02 -0.03
                lineToRelative(dx = 0.02f, dy = -0.03f)
                // l 0.19 -0.14
                lineToRelative(dx = 0.19f, dy = -0.14f)
                // l -0.01 -0.06
                lineToRelative(dx = -0.01f, dy = -0.06f)
                // l -0.15 -0.68
                lineToRelative(dx = -0.15f, dy = -0.68f)
                // v -0.03
                verticalLineToRelative(dy = -0.03f)
                // a 17 17 0 0 1 -0.36 -2.92
                arcToRelative(
                    a = 17.0f,
                    b = 17.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.36f,
                    dy1 = -2.92f,
                )
                // l 0.01 -0.25
                lineToRelative(dx = 0.01f, dy = -0.25f)
                // a 4 4 0 0 1 0.28 -1.61
                arcToRelative(
                    a = 4.0f,
                    b = 4.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.28f,
                    dy1 = -1.61f,
                )
                // l 0.02 -0.04
                lineToRelative(dx = 0.02f, dy = -0.04f)
                // a 16 16 0 0 1 1.14 -1.8
                arcToRelative(
                    a = 16.0f,
                    b = 16.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 1.14f,
                    dy1 = -1.8f,
                )
                // a 76 76 0 0 1 1.93 -2.65
                arcToRelative(
                    a = 76.0f,
                    b = 76.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 1.93f,
                    dy1 = -2.65f,
                )
                // l 0.14 -0.19
                lineToRelative(dx = 0.14f, dy = -0.19f)
                // a 94 94 0 0 1 4.8 -5.8
                arcToRelative(
                    a = 94.0f,
                    b = 94.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 4.8f,
                    dy1 = -5.8f,
                )
                // q 0.97 -1.08 1.93 -2.06
                quadToRelative(
                    dx1 = 0.97f,
                    dy1 = -1.08f,
                    dx2 = 1.93f,
                    dy2 = -2.06f,
                )
                // c 2 -2 3.92 -3.6 5.43 -4.17
                curveToRelative(
                    dx1 = 2.0f,
                    dy1 = -2.0f,
                    dx2 = 3.92f,
                    dy2 = -3.6f,
                    dx3 = 5.43f,
                    dy3 = -4.17f,
                )
                // a 3 3 0 0 1 1.4 -0.23
                arcToRelative(
                    a = 3.0f,
                    b = 3.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 1.4f,
                    dy1 = -0.23f,
                )
                // l 0.2 0.05
                lineToRelative(dx = 0.2f, dy = 0.05f)
                // l 0.21 0.09
                lineToRelative(dx = 0.21f, dy = 0.09f)
                // a 2 2 0 0 1 0.9 0.8
                arcToRelative(
                    a = 2.0f,
                    b = 2.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.9f,
                    dy1 = 0.8f,
                )
                // l 0.02 0.01
                lineToRelative(dx = 0.02f, dy = 0.01f)
                // a 3.3 3.3 0 0 1 0.47 1.67
                arcToRelative(
                    a = 3.3f,
                    b = 3.3f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.47f,
                    dy1 = 1.67f,
                )
                // c 0.08 1.65 -0.58 3.8 -1.49 5.83
                curveToRelative(
                    dx1 = 0.08f,
                    dy1 = 1.65f,
                    dx2 = -0.58f,
                    dy2 = 3.8f,
                    dx3 = -1.49f,
                    dy3 = 5.83f,
                )
                // l -0.25 0.53
                lineToRelative(dx = -0.25f, dy = 0.53f)
                // a 21 21 0 0 1 -3.3 5.13
                arcToRelative(
                    a = 21.0f,
                    b = 21.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -3.3f,
                    dy1 = 5.13f,
                )
                // l -0.12 0.11
                lineToRelative(dx = -0.12f, dy = 0.11f)
                // c -2.1 2.17 -2.23 4.04 -2.24 4.27
                curveToRelative(
                    dx1 = -2.1f,
                    dy1 = 2.17f,
                    dx2 = -2.23f,
                    dy2 = 4.04f,
                    dx3 = -2.24f,
                    dy3 = 4.27f,
                )
                // v 0.02
                verticalLineToRelative(dy = 0.02f)
                // s 2 -0.48 4.73 -1.02
                reflectiveCurveToRelative(
                    dx1 = 2.0f,
                    dy1 = -0.48f,
                    dx2 = 4.73f,
                    dy2 = -1.02f,
                )
                // l 0.11 -0.02
                lineToRelative(dx = 0.11f, dy = -0.02f)
                // c 1.48 -0.3 3.17 -0.6 4.87 -0.86
                curveToRelative(
                    dx1 = 1.48f,
                    dy1 = -0.3f,
                    dx2 = 3.17f,
                    dy2 = -0.6f,
                    dx3 = 4.87f,
                    dy3 = -0.86f,
                )
                // c 2.85 -0.44 5.72 -0.73 7.7 -0.57
                curveToRelative(
                    dx1 = 2.85f,
                    dy1 = -0.44f,
                    dx2 = 5.72f,
                    dy2 = -0.73f,
                    dx3 = 7.7f,
                    dy3 = -0.57f,
                )
                // a 8.3 8.3 0 0 1 7.1 5.37z
                arcToRelative(
                    a = 8.3f,
                    b = 8.3f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 7.1f,
                    dy1 = 5.37f,
                )
                close()
            }
            // M55.47 48.66 a3 3 0 0 1 -.78 .86 36 36 0 0 1 -3.02 2.36 c-.86 .6 -1.74 1.1 -2.73 .4 a1.2 1.2 0 0 1 -.55 -.79 c2.67 -2.68 5.24 -4.65 6.82 -4.4 a1 1 0 0 1 .4 .46 1.3 1.3 0 0 1 -.14 1.11
            path(
                fill = SolidColor(Color(0xFFFF91B8)),
            ) {
                // M 55.47 48.66
                moveTo(x = 55.47f, y = 48.66f)
                // a 3 3 0 0 1 -0.78 0.86
                arcToRelative(
                    a = 3.0f,
                    b = 3.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.78f,
                    dy1 = 0.86f,
                )
                // a 36 36 0 0 1 -3.02 2.36
                arcToRelative(
                    a = 36.0f,
                    b = 36.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -3.02f,
                    dy1 = 2.36f,
                )
                // c -0.86 0.6 -1.74 1.1 -2.73 0.4
                curveToRelative(
                    dx1 = -0.86f,
                    dy1 = 0.6f,
                    dx2 = -1.74f,
                    dy2 = 1.1f,
                    dx3 = -2.73f,
                    dy3 = 0.4f,
                )
                // a 1.2 1.2 0 0 1 -0.55 -0.79
                arcToRelative(
                    a = 1.2f,
                    b = 1.2f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.55f,
                    dy1 = -0.79f,
                )
                // c 2.67 -2.68 5.24 -4.65 6.82 -4.4
                curveToRelative(
                    dx1 = 2.67f,
                    dy1 = -2.68f,
                    dx2 = 5.24f,
                    dy2 = -4.65f,
                    dx3 = 6.82f,
                    dy3 = -4.4f,
                )
                // a 1 1 0 0 1 0.4 0.46
                arcToRelative(
                    a = 1.0f,
                    b = 1.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.4f,
                    dy1 = 0.46f,
                )
                // a 1.3 1.3 0 0 1 -0.14 1.11
                arcToRelative(
                    a = 1.3f,
                    b = 1.3f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.14f,
                    dy1 = 1.11f,
                )
            }
            // m75.52 72.16 -12.04 6.33 -3 -13.21 s-5.06 -6.45 -5.06 -10.97 c0 -4.51 2.9 -5.9 4.51 -5.9 l.26 .03 c1.52 .45 3.02 4.77 4.04 6.2 l.32 .4 c1.42 1.64 5.1 4.68 6.77 7.55 1.83 3.12 4.2 9.57 4.2 9.57
            path(
                fill = SolidColor(Color(0xFF7E5C63)),
            ) {
                // M 75.52 72.16
                moveTo(x = 75.52f, y = 72.16f)
                // l -12.04 6.33
                lineToRelative(dx = -12.04f, dy = 6.33f)
                // l -3 -13.21
                lineToRelative(dx = -3.0f, dy = -13.21f)
                // s -5.06 -6.45 -5.06 -10.97
                reflectiveCurveToRelative(
                    dx1 = -5.06f,
                    dy1 = -6.45f,
                    dx2 = -5.06f,
                    dy2 = -10.97f,
                )
                // c 0 -4.51 2.9 -5.9 4.51 -5.9
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = -4.51f,
                    dx2 = 2.9f,
                    dy2 = -5.9f,
                    dx3 = 4.51f,
                    dy3 = -5.9f,
                )
                // l 0.26 0.03
                lineToRelative(dx = 0.26f, dy = 0.03f)
                // c 1.52 0.45 3.02 4.77 4.04 6.2
                curveToRelative(
                    dx1 = 1.52f,
                    dy1 = 0.45f,
                    dx2 = 3.02f,
                    dy2 = 4.77f,
                    dx3 = 4.04f,
                    dy3 = 6.2f,
                )
                // l 0.32 0.4
                lineToRelative(dx = 0.32f, dy = 0.4f)
                // c 1.42 1.64 5.1 4.68 6.77 7.55
                curveToRelative(
                    dx1 = 1.42f,
                    dy1 = 1.64f,
                    dx2 = 5.1f,
                    dy2 = 4.68f,
                    dx3 = 6.77f,
                    dy3 = 7.55f,
                )
                // c 1.83 3.12 4.2 9.57 4.2 9.57
                curveToRelative(
                    dx1 = 1.83f,
                    dy1 = 3.12f,
                    dx2 = 4.2f,
                    dy2 = 9.57f,
                    dx3 = 4.2f,
                    dy3 = 9.57f,
                )
            }
            // M68.14 65.27 c-.31 1.03 -1.5 1.5 -2.56 1.72 a15 15 0 0 1 -3.87 .25 c-.8 -.05 -1.63 -.19 -2.2 -.74 -.66 -.65 -.72 -1.66 -.47 -2.58 3.24 -.36 6.2 -.52 7.5 -.39 a2.3 2.3 0 0 1 1.64 .95 1.6 1.6 0 0 1 -.04 .79 m4.83 11.58 a2 2 0 0 1 -.81 1.09 4 4 0 0 1 -1.93 .61 13 13 0 0 1 -3.7 -.05 c-.47 -.07 -.95 -.19 -1.26 -.54 a1.7 1.7 0 0 1 -.35 -1.13 A4 4 0 0 1 65.39 75 c3.88 .18 6.99 .8 7.58 1.85
            path(
                fill = SolidColor(Color(0xFFFF91B8)),
            ) {
                // M 68.14 65.27
                moveTo(x = 68.14f, y = 65.27f)
                // c -0.31 1.03 -1.5 1.5 -2.56 1.72
                curveToRelative(
                    dx1 = -0.31f,
                    dy1 = 1.03f,
                    dx2 = -1.5f,
                    dy2 = 1.5f,
                    dx3 = -2.56f,
                    dy3 = 1.72f,
                )
                // a 15 15 0 0 1 -3.87 0.25
                arcToRelative(
                    a = 15.0f,
                    b = 15.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -3.87f,
                    dy1 = 0.25f,
                )
                // c -0.8 -0.05 -1.63 -0.19 -2.2 -0.74
                curveToRelative(
                    dx1 = -0.8f,
                    dy1 = -0.05f,
                    dx2 = -1.63f,
                    dy2 = -0.19f,
                    dx3 = -2.2f,
                    dy3 = -0.74f,
                )
                // c -0.66 -0.65 -0.72 -1.66 -0.47 -2.58
                curveToRelative(
                    dx1 = -0.66f,
                    dy1 = -0.65f,
                    dx2 = -0.72f,
                    dy2 = -1.66f,
                    dx3 = -0.47f,
                    dy3 = -2.58f,
                )
                // c 3.24 -0.36 6.2 -0.52 7.5 -0.39
                curveToRelative(
                    dx1 = 3.24f,
                    dy1 = -0.36f,
                    dx2 = 6.2f,
                    dy2 = -0.52f,
                    dx3 = 7.5f,
                    dy3 = -0.39f,
                )
                // a 2.3 2.3 0 0 1 1.64 0.95
                arcToRelative(
                    a = 2.3f,
                    b = 2.3f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 1.64f,
                    dy1 = 0.95f,
                )
                // a 1.6 1.6 0 0 1 -0.04 0.79
                arcToRelative(
                    a = 1.6f,
                    b = 1.6f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.04f,
                    dy1 = 0.79f,
                )
                // m 4.83 11.58
                moveToRelative(dx = 4.83f, dy = 11.58f)
                // a 2 2 0 0 1 -0.81 1.09
                arcToRelative(
                    a = 2.0f,
                    b = 2.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.81f,
                    dy1 = 1.09f,
                )
                // a 4 4 0 0 1 -1.93 0.61
                arcToRelative(
                    a = 4.0f,
                    b = 4.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.93f,
                    dy1 = 0.61f,
                )
                // a 13 13 0 0 1 -3.7 -0.05
                arcToRelative(
                    a = 13.0f,
                    b = 13.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -3.7f,
                    dy1 = -0.05f,
                )
                // c -0.47 -0.07 -0.95 -0.19 -1.26 -0.54
                curveToRelative(
                    dx1 = -0.47f,
                    dy1 = -0.07f,
                    dx2 = -0.95f,
                    dy2 = -0.19f,
                    dx3 = -1.26f,
                    dy3 = -0.54f,
                )
                // a 1.7 1.7 0 0 1 -0.35 -1.13
                arcToRelative(
                    a = 1.7f,
                    b = 1.7f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.35f,
                    dy1 = -1.13f,
                )
                // A 4 4 0 0 1 65.39 75
                arcTo(
                    horizontalEllipseRadius = 4.0f,
                    verticalEllipseRadius = 4.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 65.39f,
                    y1 = 75.0f,
                )
                // c 3.88 0.18 6.99 0.8 7.58 1.85
                curveToRelative(
                    dx1 = 3.88f,
                    dy1 = 0.18f,
                    dx2 = 6.99f,
                    dy2 = 0.8f,
                    dx3 = 7.58f,
                    dy3 = 1.85f,
                )
            }
            // m38.8 69.42 -.07 -.28 A13 13 0 0 1 38.3 66 a5 5 0 0 1 .57 -2.55 c1.27 -2.07 6.92 -9.25 10.05 -12.3 q2 -1.96 3.48 -3 1.5 -1.05 2.35 -1.05 a1 1 0 0 1 .5 .13 4 4 0 0 1 1.3 1.07 3 3 0 0 1 .6 1.92 5 5 0 0 1 -.11 1.03 c-.47 2.45 -2.65 6.4 -4.49 8.88 -.92 1.25 -1.65 2.14 -2.17 2.96 a5.3 5.3 0 0 0 -.92 2.5 .16 .16 0 1 0 .31 .02 5 5 0 0 1 .88 -2.35 c.5 -.8 1.23 -1.69 2.15 -2.94 1.87 -2.52 4.05 -6.46 4.55 -9 a6 6 0 0 0 .1 -1.1 3.4 3.4 0 0 0 -.65 -2.11 4.4 4.4 0 0 0 -1.38 -1.16 1.3 1.3 0 0 0 -.67 -.16 c-.69 0 -1.52 .4 -2.53 1.1 a30 30 0 0 0 -3.52 3.04 102 102 0 0 0 -10.1 12.36 A5 5 0 0 0 37.98 66 c0 1.8 .52 3.5 .52 3.51 a.16 .16 0 0 0 .3 -.09
            path(
                fill = SolidColor(Color(0xFF111528)),
            ) {
                // M 38.8 69.42
                moveTo(x = 38.8f, y = 69.42f)
                // l -0.07 -0.28
                lineToRelative(dx = -0.07f, dy = -0.28f)
                // A 13 13 0 0 1 38.3 66
                arcTo(
                    horizontalEllipseRadius = 13.0f,
                    verticalEllipseRadius = 13.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 38.3f,
                    y1 = 66.0f,
                )
                // a 5 5 0 0 1 0.57 -2.55
                arcToRelative(
                    a = 5.0f,
                    b = 5.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.57f,
                    dy1 = -2.55f,
                )
                // c 1.27 -2.07 6.92 -9.25 10.05 -12.3
                curveToRelative(
                    dx1 = 1.27f,
                    dy1 = -2.07f,
                    dx2 = 6.92f,
                    dy2 = -9.25f,
                    dx3 = 10.05f,
                    dy3 = -12.3f,
                )
                // q 2 -1.96 3.48 -3
                quadToRelative(
                    dx1 = 2.0f,
                    dy1 = -1.96f,
                    dx2 = 3.48f,
                    dy2 = -3.0f,
                )
                // q 1.5 -1.05 2.35 -1.05
                quadToRelative(
                    dx1 = 1.5f,
                    dy1 = -1.05f,
                    dx2 = 2.35f,
                    dy2 = -1.05f,
                )
                // a 1 1 0 0 1 0.5 0.13
                arcToRelative(
                    a = 1.0f,
                    b = 1.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.5f,
                    dy1 = 0.13f,
                )
                // a 4 4 0 0 1 1.3 1.07
                arcToRelative(
                    a = 4.0f,
                    b = 4.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 1.3f,
                    dy1 = 1.07f,
                )
                // a 3 3 0 0 1 0.6 1.92
                arcToRelative(
                    a = 3.0f,
                    b = 3.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.6f,
                    dy1 = 1.92f,
                )
                // a 5 5 0 0 1 -0.11 1.03
                arcToRelative(
                    a = 5.0f,
                    b = 5.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.11f,
                    dy1 = 1.03f,
                )
                // c -0.47 2.45 -2.65 6.4 -4.49 8.88
                curveToRelative(
                    dx1 = -0.47f,
                    dy1 = 2.45f,
                    dx2 = -2.65f,
                    dy2 = 6.4f,
                    dx3 = -4.49f,
                    dy3 = 8.88f,
                )
                // c -0.92 1.25 -1.65 2.14 -2.17 2.96
                curveToRelative(
                    dx1 = -0.92f,
                    dy1 = 1.25f,
                    dx2 = -1.65f,
                    dy2 = 2.14f,
                    dx3 = -2.17f,
                    dy3 = 2.96f,
                )
                // a 5.3 5.3 0 0 0 -0.92 2.5
                arcToRelative(
                    a = 5.3f,
                    b = 5.3f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -0.92f,
                    dy1 = 2.5f,
                )
                // a 0.16 0.16 0 1 0 0.31 0.02
                arcToRelative(
                    a = 0.16f,
                    b = 0.16f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = 0.31f,
                    dy1 = 0.02f,
                )
                // a 5 5 0 0 1 0.88 -2.35
                arcToRelative(
                    a = 5.0f,
                    b = 5.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.88f,
                    dy1 = -2.35f,
                )
                // c 0.5 -0.8 1.23 -1.69 2.15 -2.94
                curveToRelative(
                    dx1 = 0.5f,
                    dy1 = -0.8f,
                    dx2 = 1.23f,
                    dy2 = -1.69f,
                    dx3 = 2.15f,
                    dy3 = -2.94f,
                )
                // c 1.87 -2.52 4.05 -6.46 4.55 -9
                curveToRelative(
                    dx1 = 1.87f,
                    dy1 = -2.52f,
                    dx2 = 4.05f,
                    dy2 = -6.46f,
                    dx3 = 4.55f,
                    dy3 = -9.0f,
                )
                // a 6 6 0 0 0 0.1 -1.1
                arcToRelative(
                    a = 6.0f,
                    b = 6.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.1f,
                    dy1 = -1.1f,
                )
                // a 3.4 3.4 0 0 0 -0.65 -2.11
                arcToRelative(
                    a = 3.4f,
                    b = 3.4f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -0.65f,
                    dy1 = -2.11f,
                )
                // a 4.4 4.4 0 0 0 -1.38 -1.16
                arcToRelative(
                    a = 4.4f,
                    b = 4.4f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -1.38f,
                    dy1 = -1.16f,
                )
                // a 1.3 1.3 0 0 0 -0.67 -0.16
                arcToRelative(
                    a = 1.3f,
                    b = 1.3f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -0.67f,
                    dy1 = -0.16f,
                )
                // c -0.69 0 -1.52 0.4 -2.53 1.1
                curveToRelative(
                    dx1 = -0.69f,
                    dy1 = 0.0f,
                    dx2 = -1.52f,
                    dy2 = 0.4f,
                    dx3 = -2.53f,
                    dy3 = 1.1f,
                )
                // a 30 30 0 0 0 -3.52 3.04
                arcToRelative(
                    a = 30.0f,
                    b = 30.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -3.52f,
                    dy1 = 3.04f,
                )
                // a 102 102 0 0 0 -10.1 12.36
                arcToRelative(
                    a = 102.0f,
                    b = 102.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -10.1f,
                    dy1 = 12.36f,
                )
                // A 5 5 0 0 0 37.98 66
                arcTo(
                    horizontalEllipseRadius = 5.0f,
                    verticalEllipseRadius = 5.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 37.98f,
                    y1 = 66.0f,
                )
                // c 0 1.8 0.52 3.5 0.52 3.51
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = 1.8f,
                    dx2 = 0.52f,
                    dy2 = 3.5f,
                    dx3 = 0.52f,
                    dy3 = 3.51f,
                )
                // a 0.16 0.16 0 0 0 0.3 -0.09
                arcToRelative(
                    a = 0.16f,
                    b = 0.16f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.3f,
                    dy1 = -0.09f,
                )
            }
            // m50.25 76.66 -.15 -1.94 -.16 .01 .03 .16 .83 -.2 7.63 -1.73 c1.77 -.4 3.84 -.65 5.67 -1.06 1.82 -.42 3.4 -1 4.19 -2.16 a5 5 0 0 0 .02 -5.36 2.4 2.4 0 0 0 -1.75 -1 13 13 0 0 0 -1.2 -.05 63 63 0 0 0 -6.33 .43 72 72 0 0 0 -8 1.31 c-5.09 1.22 -9.29 2.59 -11.88 3.88 -.9 .46 -1.44 1.5 -1.76 2.8 a20 20 0 0 0 -.42 4.47 c0 1.73 .12 3.45 .24 4.81 .32 3.88 1.77 17.1 1.77 17.1 a.16 .16 0 1 0 .31 -.04 l-.23 -2.12 c-.42 -3.9 -1.3 -12.06 -1.54 -14.96 a59 59 0 0 1 -.24 -4.79 c0 -1.57 .1 -3.13 .42 -4.4 .3 -1.26 .82 -2.2 1.59 -2.58 2.56 -1.28 6.74 -2.65 11.82 -3.86 a72 72 0 0 1 7.95 -1.3 63 63 0 0 1 6.3 -.44 q.71 0 1.17 .05 a2.1 2.1 0 0 1 1.52 .88 4 4 0 0 1 .71 2.4 c0 .91 -.24 1.86 -.74 2.6 -.67 1.02 -2.19 1.61 -4 2.02 -1.8 .4 -3.87 .65 -5.66 1.06 l-8.46 1.93 a.2 .2 0 0 0 -.12 .17 l.16 1.93 a.16 .16 0 0 0 .17 .15 .16 .16 0 0 0 .14 -.17
            path(
                fill = SolidColor(Color(0xFF111528)),
            ) {
                // M 50.25 76.66
                moveTo(x = 50.25f, y = 76.66f)
                // l -0.15 -1.94
                lineToRelative(dx = -0.15f, dy = -1.94f)
                // l -0.16 0.01
                lineToRelative(dx = -0.16f, dy = 0.01f)
                // l 0.03 0.16
                lineToRelative(dx = 0.03f, dy = 0.16f)
                // l 0.83 -0.2
                lineToRelative(dx = 0.83f, dy = -0.2f)
                // l 7.63 -1.73
                lineToRelative(dx = 7.63f, dy = -1.73f)
                // c 1.77 -0.4 3.84 -0.65 5.67 -1.06
                curveToRelative(
                    dx1 = 1.77f,
                    dy1 = -0.4f,
                    dx2 = 3.84f,
                    dy2 = -0.65f,
                    dx3 = 5.67f,
                    dy3 = -1.06f,
                )
                // c 1.82 -0.42 3.4 -1 4.19 -2.16
                curveToRelative(
                    dx1 = 1.82f,
                    dy1 = -0.42f,
                    dx2 = 3.4f,
                    dy2 = -1.0f,
                    dx3 = 4.19f,
                    dy3 = -2.16f,
                )
                // a 5 5 0 0 0 0.02 -5.36
                arcToRelative(
                    a = 5.0f,
                    b = 5.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.02f,
                    dy1 = -5.36f,
                )
                // a 2.4 2.4 0 0 0 -1.75 -1
                arcToRelative(
                    a = 2.4f,
                    b = 2.4f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -1.75f,
                    dy1 = -1.0f,
                )
                // a 13 13 0 0 0 -1.2 -0.05
                arcToRelative(
                    a = 13.0f,
                    b = 13.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -1.2f,
                    dy1 = -0.05f,
                )
                // a 63 63 0 0 0 -6.33 0.43
                arcToRelative(
                    a = 63.0f,
                    b = 63.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -6.33f,
                    dy1 = 0.43f,
                )
                // a 72 72 0 0 0 -8 1.31
                arcToRelative(
                    a = 72.0f,
                    b = 72.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -8.0f,
                    dy1 = 1.31f,
                )
                // c -5.09 1.22 -9.29 2.59 -11.88 3.88
                curveToRelative(
                    dx1 = -5.09f,
                    dy1 = 1.22f,
                    dx2 = -9.29f,
                    dy2 = 2.59f,
                    dx3 = -11.88f,
                    dy3 = 3.88f,
                )
                // c -0.9 0.46 -1.44 1.5 -1.76 2.8
                curveToRelative(
                    dx1 = -0.9f,
                    dy1 = 0.46f,
                    dx2 = -1.44f,
                    dy2 = 1.5f,
                    dx3 = -1.76f,
                    dy3 = 2.8f,
                )
                // a 20 20 0 0 0 -0.42 4.47
                arcToRelative(
                    a = 20.0f,
                    b = 20.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -0.42f,
                    dy1 = 4.47f,
                )
                // c 0 1.73 0.12 3.45 0.24 4.81
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = 1.73f,
                    dx2 = 0.12f,
                    dy2 = 3.45f,
                    dx3 = 0.24f,
                    dy3 = 4.81f,
                )
                // c 0.32 3.88 1.77 17.1 1.77 17.1
                curveToRelative(
                    dx1 = 0.32f,
                    dy1 = 3.88f,
                    dx2 = 1.77f,
                    dy2 = 17.1f,
                    dx3 = 1.77f,
                    dy3 = 17.1f,
                )
                // a 0.16 0.16 0 1 0 0.31 -0.04
                arcToRelative(
                    a = 0.16f,
                    b = 0.16f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = 0.31f,
                    dy1 = -0.04f,
                )
                // l -0.23 -2.12
                lineToRelative(dx = -0.23f, dy = -2.12f)
                // c -0.42 -3.9 -1.3 -12.06 -1.54 -14.96
                curveToRelative(
                    dx1 = -0.42f,
                    dy1 = -3.9f,
                    dx2 = -1.3f,
                    dy2 = -12.06f,
                    dx3 = -1.54f,
                    dy3 = -14.96f,
                )
                // a 59 59 0 0 1 -0.24 -4.79
                arcToRelative(
                    a = 59.0f,
                    b = 59.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.24f,
                    dy1 = -4.79f,
                )
                // c 0 -1.57 0.1 -3.13 0.42 -4.4
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = -1.57f,
                    dx2 = 0.1f,
                    dy2 = -3.13f,
                    dx3 = 0.42f,
                    dy3 = -4.4f,
                )
                // c 0.3 -1.26 0.82 -2.2 1.59 -2.58
                curveToRelative(
                    dx1 = 0.3f,
                    dy1 = -1.26f,
                    dx2 = 0.82f,
                    dy2 = -2.2f,
                    dx3 = 1.59f,
                    dy3 = -2.58f,
                )
                // c 2.56 -1.28 6.74 -2.65 11.82 -3.86
                curveToRelative(
                    dx1 = 2.56f,
                    dy1 = -1.28f,
                    dx2 = 6.74f,
                    dy2 = -2.65f,
                    dx3 = 11.82f,
                    dy3 = -3.86f,
                )
                // a 72 72 0 0 1 7.95 -1.3
                arcToRelative(
                    a = 72.0f,
                    b = 72.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 7.95f,
                    dy1 = -1.3f,
                )
                // a 63 63 0 0 1 6.3 -0.44
                arcToRelative(
                    a = 63.0f,
                    b = 63.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 6.3f,
                    dy1 = -0.44f,
                )
                // q 0.71 0 1.17 0.05
                quadToRelative(
                    dx1 = 0.71f,
                    dy1 = 0.0f,
                    dx2 = 1.17f,
                    dy2 = 0.05f,
                )
                // a 2.1 2.1 0 0 1 1.52 0.88
                arcToRelative(
                    a = 2.1f,
                    b = 2.1f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 1.52f,
                    dy1 = 0.88f,
                )
                // a 4 4 0 0 1 0.71 2.4
                arcToRelative(
                    a = 4.0f,
                    b = 4.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.71f,
                    dy1 = 2.4f,
                )
                // c 0 0.91 -0.24 1.86 -0.74 2.6
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = 0.91f,
                    dx2 = -0.24f,
                    dy2 = 1.86f,
                    dx3 = -0.74f,
                    dy3 = 2.6f,
                )
                // c -0.67 1.02 -2.19 1.61 -4 2.02
                curveToRelative(
                    dx1 = -0.67f,
                    dy1 = 1.02f,
                    dx2 = -2.19f,
                    dy2 = 1.61f,
                    dx3 = -4.0f,
                    dy3 = 2.02f,
                )
                // c -1.8 0.4 -3.87 0.65 -5.66 1.06
                curveToRelative(
                    dx1 = -1.8f,
                    dy1 = 0.4f,
                    dx2 = -3.87f,
                    dy2 = 0.65f,
                    dx3 = -5.66f,
                    dy3 = 1.06f,
                )
                // l -8.46 1.93
                lineToRelative(dx = -8.46f, dy = 1.93f)
                // a 0.2 0.2 0 0 0 -0.12 0.17
                arcToRelative(
                    a = 0.2f,
                    b = 0.2f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -0.12f,
                    dy1 = 0.17f,
                )
                // l 0.16 1.93
                lineToRelative(dx = 0.16f, dy = 1.93f)
                // a 0.16 0.16 0 0 0 0.17 0.15
                arcToRelative(
                    a = 0.16f,
                    b = 0.16f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.17f,
                    dy1 = 0.15f,
                )
                // a 0.16 0.16 0 0 0 0.14 -0.17
                arcToRelative(
                    a = 0.16f,
                    b = 0.16f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.14f,
                    dy1 = -0.17f,
                )
            }
            // m53.96 91.33 2.2 -6.71 L56 84.57 l.01 .15 s3.25 -.34 6.71 -.77 a199 199 0 0 0 4.98 -.66 22 22 0 0 0 3.04 -.59 3.7 3.7 0 0 0 2.05 -2.05 7 7 0 0 0 .53 -2.6 4 4 0 0 0 -.14 -1.11 v-.02 l-.08 -.14 -.14 .07 h.16 l-.02 -.08 A2 2 0 0 0 72.15 76 c-1.35 -.64 -3.83 -1.02 -6.75 -1.15 a45 45 0 0 0 -3 -.06 c-5.37 .06 -9.15 .2 -12 1.68 -.74 .4 -1.6 1.36 -2.5 2.6 -2.69 3.7 -5.68 9.83 -5.68 9.84 a.16 .16 0 0 0 .29 .13 l.23 -.47 a100 100 0 0 1 3.41 -6.24 40 40 0 0 1 2.34 -3.52 c.76 -1 1.5 -1.76 2.06 -2.06 2.74 -1.43 6.48 -1.6 11.85 -1.65 a44 44 0 0 1 2.98 .06 29 29 0 0 1 5 .6 A8 8 0 0 1 72 76.28 a2 2 0 0 1 .82 .63 l.14 -.07 h-.16 l.02 .07 .07 .14 .14 -.07 -.15 .05 a3 3 0 0 1 .12 1 6 6 0 0 1 -.5 2.49 3.4 3.4 0 0 1 -1.87 1.88 22 22 0 0 1 -2.98 .57 255 255 0 0 1 -10.46 1.3 l-1.21 .13 a.2 .2 0 0 0 -.13 .1 l-2.2 6.73 a.16 .16 0 0 0 .3 .1
            path(
                fill = SolidColor(Color(0xFF111528)),
            ) {
                // M 53.96 91.33
                moveTo(x = 53.96f, y = 91.33f)
                // l 2.2 -6.71
                lineToRelative(dx = 2.2f, dy = -6.71f)
                // L 56 84.57
                lineTo(x = 56.0f, y = 84.57f)
                // l 0.01 0.15
                lineToRelative(dx = 0.01f, dy = 0.15f)
                // s 3.25 -0.34 6.71 -0.77
                reflectiveCurveToRelative(
                    dx1 = 3.25f,
                    dy1 = -0.34f,
                    dx2 = 6.71f,
                    dy2 = -0.77f,
                )
                // a 199 199 0 0 0 4.98 -0.66
                arcToRelative(
                    a = 199.0f,
                    b = 199.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 4.98f,
                    dy1 = -0.66f,
                )
                // a 22 22 0 0 0 3.04 -0.59
                arcToRelative(
                    a = 22.0f,
                    b = 22.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 3.04f,
                    dy1 = -0.59f,
                )
                // a 3.7 3.7 0 0 0 2.05 -2.05
                arcToRelative(
                    a = 3.7f,
                    b = 3.7f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 2.05f,
                    dy1 = -2.05f,
                )
                // a 7 7 0 0 0 0.53 -2.6
                arcToRelative(
                    a = 7.0f,
                    b = 7.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.53f,
                    dy1 = -2.6f,
                )
                // a 4 4 0 0 0 -0.14 -1.11
                arcToRelative(
                    a = 4.0f,
                    b = 4.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -0.14f,
                    dy1 = -1.11f,
                )
                // v -0.02
                verticalLineToRelative(dy = -0.02f)
                // l -0.08 -0.14
                lineToRelative(dx = -0.08f, dy = -0.14f)
                // l -0.14 0.07
                lineToRelative(dx = -0.14f, dy = 0.07f)
                // h 0.16
                horizontalLineToRelative(dx = 0.16f)
                // l -0.02 -0.08
                lineToRelative(dx = -0.02f, dy = -0.08f)
                // A 2 2 0 0 0 72.15 76
                arcTo(
                    horizontalEllipseRadius = 2.0f,
                    verticalEllipseRadius = 2.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 72.15f,
                    y1 = 76.0f,
                )
                // c -1.35 -0.64 -3.83 -1.02 -6.75 -1.15
                curveToRelative(
                    dx1 = -1.35f,
                    dy1 = -0.64f,
                    dx2 = -3.83f,
                    dy2 = -1.02f,
                    dx3 = -6.75f,
                    dy3 = -1.15f,
                )
                // a 45 45 0 0 0 -3 -0.06
                arcToRelative(
                    a = 45.0f,
                    b = 45.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -3.0f,
                    dy1 = -0.06f,
                )
                // c -5.37 0.06 -9.15 0.2 -12 1.68
                curveToRelative(
                    dx1 = -5.37f,
                    dy1 = 0.06f,
                    dx2 = -9.15f,
                    dy2 = 0.2f,
                    dx3 = -12.0f,
                    dy3 = 1.68f,
                )
                // c -0.74 0.4 -1.6 1.36 -2.5 2.6
                curveToRelative(
                    dx1 = -0.74f,
                    dy1 = 0.4f,
                    dx2 = -1.6f,
                    dy2 = 1.36f,
                    dx3 = -2.5f,
                    dy3 = 2.6f,
                )
                // c -2.69 3.7 -5.68 9.83 -5.68 9.84
                curveToRelative(
                    dx1 = -2.69f,
                    dy1 = 3.7f,
                    dx2 = -5.68f,
                    dy2 = 9.83f,
                    dx3 = -5.68f,
                    dy3 = 9.84f,
                )
                // a 0.16 0.16 0 0 0 0.29 0.13
                arcToRelative(
                    a = 0.16f,
                    b = 0.16f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.29f,
                    dy1 = 0.13f,
                )
                // l 0.23 -0.47
                lineToRelative(dx = 0.23f, dy = -0.47f)
                // a 100 100 0 0 1 3.41 -6.24
                arcToRelative(
                    a = 100.0f,
                    b = 100.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 3.41f,
                    dy1 = -6.24f,
                )
                // a 40 40 0 0 1 2.34 -3.52
                arcToRelative(
                    a = 40.0f,
                    b = 40.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 2.34f,
                    dy1 = -3.52f,
                )
                // c 0.76 -1 1.5 -1.76 2.06 -2.06
                curveToRelative(
                    dx1 = 0.76f,
                    dy1 = -1.0f,
                    dx2 = 1.5f,
                    dy2 = -1.76f,
                    dx3 = 2.06f,
                    dy3 = -2.06f,
                )
                // c 2.74 -1.43 6.48 -1.6 11.85 -1.65
                curveToRelative(
                    dx1 = 2.74f,
                    dy1 = -1.43f,
                    dx2 = 6.48f,
                    dy2 = -1.6f,
                    dx3 = 11.85f,
                    dy3 = -1.65f,
                )
                // a 44 44 0 0 1 2.98 0.06
                arcToRelative(
                    a = 44.0f,
                    b = 44.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 2.98f,
                    dy1 = 0.06f,
                )
                // a 29 29 0 0 1 5 0.6
                arcToRelative(
                    a = 29.0f,
                    b = 29.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 5.0f,
                    dy1 = 0.6f,
                )
                // A 8 8 0 0 1 72 76.28
                arcTo(
                    horizontalEllipseRadius = 8.0f,
                    verticalEllipseRadius = 8.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 72.0f,
                    y1 = 76.28f,
                )
                // a 2 2 0 0 1 0.82 0.63
                arcToRelative(
                    a = 2.0f,
                    b = 2.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.82f,
                    dy1 = 0.63f,
                )
                // l 0.14 -0.07
                lineToRelative(dx = 0.14f, dy = -0.07f)
                // h -0.16
                horizontalLineToRelative(dx = -0.16f)
                // l 0.02 0.07
                lineToRelative(dx = 0.02f, dy = 0.07f)
                // l 0.07 0.14
                lineToRelative(dx = 0.07f, dy = 0.14f)
                // l 0.14 -0.07
                lineToRelative(dx = 0.14f, dy = -0.07f)
                // l -0.15 0.05
                lineToRelative(dx = -0.15f, dy = 0.05f)
                // a 3 3 0 0 1 0.12 1
                arcToRelative(
                    a = 3.0f,
                    b = 3.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.12f,
                    dy1 = 1.0f,
                )
                // a 6 6 0 0 1 -0.5 2.49
                arcToRelative(
                    a = 6.0f,
                    b = 6.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.5f,
                    dy1 = 2.49f,
                )
                // a 3.4 3.4 0 0 1 -1.87 1.88
                arcToRelative(
                    a = 3.4f,
                    b = 3.4f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.87f,
                    dy1 = 1.88f,
                )
                // a 22 22 0 0 1 -2.98 0.57
                arcToRelative(
                    a = 22.0f,
                    b = 22.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -2.98f,
                    dy1 = 0.57f,
                )
                // a 255 255 0 0 1 -10.46 1.3
                arcToRelative(
                    a = 255.0f,
                    b = 255.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -10.46f,
                    dy1 = 1.3f,
                )
                // l -1.21 0.13
                lineToRelative(dx = -1.21f, dy = 0.13f)
                // a 0.2 0.2 0 0 0 -0.13 0.1
                arcToRelative(
                    a = 0.2f,
                    b = 0.2f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -0.13f,
                    dy1 = 0.1f,
                )
                // l -2.2 6.73
                lineToRelative(dx = -2.2f, dy = 6.73f)
                // a 0.16 0.16 0 0 0 0.3 0.1
                arcToRelative(
                    a = 0.16f,
                    b = 0.16f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.3f,
                    dy1 = 0.1f,
                )
            }
            // m54.3 89.5 2.29 2.43 a.16 .16 0 1 0 .23 -.21 l-2.3 -2.44 a.16 .16 0 1 0 -.23 .22
            path(
                fill = SolidColor(Color(0xFF111528)),
            ) {
                // M 54.3 89.5
                moveTo(x = 54.3f, y = 89.5f)
                // l 2.29 2.43
                lineToRelative(dx = 2.29f, dy = 2.43f)
                // a 0.16 0.16 0 1 0 0.23 -0.21
                arcToRelative(
                    a = 0.16f,
                    b = 0.16f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = 0.23f,
                    dy1 = -0.21f,
                )
                // l -2.3 -2.44
                lineToRelative(dx = -2.3f, dy = -2.44f)
                // a 0.16 0.16 0 1 0 -0.23 0.22
                arcToRelative(
                    a = 0.16f,
                    b = 0.16f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = -0.23f,
                    dy1 = 0.22f,
                )
            }
            // M64.55 55.04 a1.5 1.5 0 0 1 -1.08 .42 2.2 2.2 0 0 1 -1.27 -.48 5 5 0 0 1 -1.34 -1.79 13 13 0 0 1 -.8 -1.88 c-.3 -.92 -.43 -2.04 .13 -2.87 1.52 .45 3.02 4.77 4.04 6.2z m-17.86 -4.16 c.26 -.07 .45 -.28 .62 -.48 a17 17 0 0 0 2.33 -3.65 2 2 0 0 0 .27 -.9 c.02 -.68 -.5 -1.23 -1 -1.7 -.65 .77 -2.26 3.42 -2.5 3.76 -.4 .6 -.86 1.28 -1.09 1.98 -.14 .43 .04 .42 .4 .66 .3 .19 .6 .42 .97 .33
            path(
                fill = SolidColor(Color(0xFFFF91B8)),
            ) {
                // M 64.55 55.04
                moveTo(x = 64.55f, y = 55.04f)
                // a 1.5 1.5 0 0 1 -1.08 0.42
                arcToRelative(
                    a = 1.5f,
                    b = 1.5f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.08f,
                    dy1 = 0.42f,
                )
                // a 2.2 2.2 0 0 1 -1.27 -0.48
                arcToRelative(
                    a = 2.2f,
                    b = 2.2f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.27f,
                    dy1 = -0.48f,
                )
                // a 5 5 0 0 1 -1.34 -1.79
                arcToRelative(
                    a = 5.0f,
                    b = 5.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.34f,
                    dy1 = -1.79f,
                )
                // a 13 13 0 0 1 -0.8 -1.88
                arcToRelative(
                    a = 13.0f,
                    b = 13.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.8f,
                    dy1 = -1.88f,
                )
                // c -0.3 -0.92 -0.43 -2.04 0.13 -2.87
                curveToRelative(
                    dx1 = -0.3f,
                    dy1 = -0.92f,
                    dx2 = -0.43f,
                    dy2 = -2.04f,
                    dx3 = 0.13f,
                    dy3 = -2.87f,
                )
                // c 1.52 0.45 3.02 4.77 4.04 6.2z
                curveToRelative(
                    dx1 = 1.52f,
                    dy1 = 0.45f,
                    dx2 = 3.02f,
                    dy2 = 4.77f,
                    dx3 = 4.04f,
                    dy3 = 6.2f,
                )
                close()
                // m -17.86 -4.16
                moveToRelative(dx = -17.86f, dy = -4.16f)
                // c 0.26 -0.07 0.45 -0.28 0.62 -0.48
                curveToRelative(
                    dx1 = 0.26f,
                    dy1 = -0.07f,
                    dx2 = 0.45f,
                    dy2 = -0.28f,
                    dx3 = 0.62f,
                    dy3 = -0.48f,
                )
                // a 17 17 0 0 0 2.33 -3.65
                arcToRelative(
                    a = 17.0f,
                    b = 17.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 2.33f,
                    dy1 = -3.65f,
                )
                // a 2 2 0 0 0 0.27 -0.9
                arcToRelative(
                    a = 2.0f,
                    b = 2.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.27f,
                    dy1 = -0.9f,
                )
                // c 0.02 -0.68 -0.5 -1.23 -1 -1.7
                curveToRelative(
                    dx1 = 0.02f,
                    dy1 = -0.68f,
                    dx2 = -0.5f,
                    dy2 = -1.23f,
                    dx3 = -1.0f,
                    dy3 = -1.7f,
                )
                // c -0.65 0.77 -2.26 3.42 -2.5 3.76
                curveToRelative(
                    dx1 = -0.65f,
                    dy1 = 0.77f,
                    dx2 = -2.26f,
                    dy2 = 3.42f,
                    dx3 = -2.5f,
                    dy3 = 3.76f,
                )
                // c -0.4 0.6 -0.86 1.28 -1.09 1.98
                curveToRelative(
                    dx1 = -0.4f,
                    dy1 = 0.6f,
                    dx2 = -0.86f,
                    dy2 = 1.28f,
                    dx3 = -1.09f,
                    dy3 = 1.98f,
                )
                // c -0.14 0.43 0.04 0.42 0.4 0.66
                curveToRelative(
                    dx1 = -0.14f,
                    dy1 = 0.43f,
                    dx2 = 0.04f,
                    dy2 = 0.42f,
                    dx3 = 0.4f,
                    dy3 = 0.66f,
                )
                // c 0.3 0.19 0.6 0.42 0.97 0.33
                curveToRelative(
                    dx1 = 0.3f,
                    dy1 = 0.19f,
                    dx2 = 0.6f,
                    dy2 = 0.42f,
                    dx3 = 0.97f,
                    dy3 = 0.33f,
                )
            }
            // M57.67 43.65 a138 138 0 0 1 .07 3.87 46 46 0 0 1 -.37 5.95 c-.24 2.14 -.6 5.05 -1.08 10.52 a.2 .2 0 1 0 .38 .04 c.48 -5.48 .84 -8.37 1.09 -10.51 a46 46 0 0 0 .36 -6 c0 -1.05 -.02 -2.3 -.06 -3.88 a.2 .2 0 1 0 -.39 .01
            path(
                fill = SolidColor(Color(0xFF111528)),
            ) {
                // M 57.67 43.65
                moveTo(x = 57.67f, y = 43.65f)
                // a 138 138 0 0 1 0.07 3.87
                arcToRelative(
                    a = 138.0f,
                    b = 138.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.07f,
                    dy1 = 3.87f,
                )
                // a 46 46 0 0 1 -0.37 5.95
                arcToRelative(
                    a = 46.0f,
                    b = 46.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.37f,
                    dy1 = 5.95f,
                )
                // c -0.24 2.14 -0.6 5.05 -1.08 10.52
                curveToRelative(
                    dx1 = -0.24f,
                    dy1 = 2.14f,
                    dx2 = -0.6f,
                    dy2 = 5.05f,
                    dx3 = -1.08f,
                    dy3 = 10.52f,
                )
                // a 0.2 0.2 0 1 0 0.38 0.04
                arcToRelative(
                    a = 0.2f,
                    b = 0.2f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = 0.38f,
                    dy1 = 0.04f,
                )
                // c 0.48 -5.48 0.84 -8.37 1.09 -10.51
                curveToRelative(
                    dx1 = 0.48f,
                    dy1 = -5.48f,
                    dx2 = 0.84f,
                    dy2 = -8.37f,
                    dx3 = 1.09f,
                    dy3 = -10.51f,
                )
                // a 46 46 0 0 0 0.36 -6
                arcToRelative(
                    a = 46.0f,
                    b = 46.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.36f,
                    dy1 = -6.0f,
                )
                // c 0 -1.05 -0.02 -2.3 -0.06 -3.88
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = -1.05f,
                    dx2 = -0.02f,
                    dy2 = -2.3f,
                    dx3 = -0.06f,
                    dy3 = -3.88f,
                )
                // a 0.2 0.2 0 1 0 -0.39 0.01
                arcToRelative(
                    a = 0.2f,
                    b = 0.2f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = -0.39f,
                    dy1 = 0.01f,
                )
            }
            group(
                // M55.18 24.7 c-.6 0 -1.4 -.25 -1.77 -.33 -3.27 -.72 -6.23 -2.44 -8.87 -4.45 a10 10 0 0 1 -2.4 -2.38 4 4 0 0 1 -.57 -3.24 2.28 2.28 0 0 1 2.25 -1.7 c1.01 0 2.1 .56 2.98 1.15 a29 29 0 0 1 8.35 8.67 c.31 .5 1.25 1.57 .62 2.12 q-.21 .17 -.6 .16
                clipPathData = PathData {
                    // M 55.18 24.7
                    moveTo(x = 55.18f, y = 24.7f)
                    // c -0.6 0 -1.4 -0.25 -1.77 -0.33
                    curveToRelative(
                        dx1 = -0.6f,
                        dy1 = 0.0f,
                        dx2 = -1.4f,
                        dy2 = -0.25f,
                        dx3 = -1.77f,
                        dy3 = -0.33f,
                    )
                    // c -3.27 -0.72 -6.23 -2.44 -8.87 -4.45
                    curveToRelative(
                        dx1 = -3.27f,
                        dy1 = -0.72f,
                        dx2 = -6.23f,
                        dy2 = -2.44f,
                        dx3 = -8.87f,
                        dy3 = -4.45f,
                    )
                    // a 10 10 0 0 1 -2.4 -2.38
                    arcToRelative(
                        a = 10.0f,
                        b = 10.0f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        dx1 = -2.4f,
                        dy1 = -2.38f,
                    )
                    // a 4 4 0 0 1 -0.57 -3.24
                    arcToRelative(
                        a = 4.0f,
                        b = 4.0f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        dx1 = -0.57f,
                        dy1 = -3.24f,
                    )
                    // a 2.28 2.28 0 0 1 2.25 -1.7
                    arcToRelative(
                        a = 2.28f,
                        b = 2.28f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        dx1 = 2.25f,
                        dy1 = -1.7f,
                    )
                    // c 1.01 0 2.1 0.56 2.98 1.15
                    curveToRelative(
                        dx1 = 1.01f,
                        dy1 = 0.0f,
                        dx2 = 2.1f,
                        dy2 = 0.56f,
                        dx3 = 2.98f,
                        dy3 = 1.15f,
                    )
                    // a 29 29 0 0 1 8.35 8.67
                    arcToRelative(
                        a = 29.0f,
                        b = 29.0f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        dx1 = 8.35f,
                        dy1 = 8.67f,
                    )
                    // c 0.31 0.5 1.25 1.57 0.62 2.12
                    curveToRelative(
                        dx1 = 0.31f,
                        dy1 = 0.5f,
                        dx2 = 1.25f,
                        dy2 = 1.57f,
                        dx3 = 0.62f,
                        dy3 = 2.12f,
                    )
                    // q -0.21 0.17 -0.6 0.16
                    quadToRelative(
                        dx1 = -0.21f,
                        dy1 = 0.17f,
                        dx2 = -0.6f,
                        dy2 = 0.16f,
                    )
                },
            ) {
                // M41.23 24.7 H56.4 V12.6 H41.23z
                path(
                    fill = SolidColor(Color(0xFFFAB5D5)),
                ) {
                    // M 41.23 24.7
                    moveTo(x = 41.23f, y = 24.7f)
                    // H 56.4
                    horizontalLineTo(x = 56.4f)
                    // V 12.6
                    verticalLineTo(y = 12.6f)
                    // H 41.23z
                    horizontalLineTo(x = 41.23f)
                    close()
                }
            }
            // M41.15 32.67 a5 5 0 0 1 -1.96 -.37 c-.52 -.23 -1 -.63 -1.16 -1.17 -.27 -.87 .32 -1.76 .95 -2.41 a14.4 14.4 0 0 1 7.94 -3.97 21 21 0 0 1 3.53 -.29 28 28 0 0 1 3.03 .18 c.81 .09 2.4 .03 2.63 1.05 .18 .8 -.77 1.66 -1.44 2.18 l-.32 .23 a25 25 0 0 1 -13.2 4.57 m8.16 8.85 c-1.08 0 -2.17 -.48 -2.66 -1.43 -.66 -1.24 -.13 -2.77 .54 -4 a21 21 0 0 1 4.15 -5.32 c.44 -.41 1.77 -1.73 3.01 -2.67 l.03 -.02 .3 -.21 c.63 -.46 1.22 -.77 1.62 -.77 a1 1 0 0 1 .22 .04 c.45 .2 .54 .9 .48 1.77 a27 27 0 0 0 -.66 3.29 l-.1 .36 a19 19 0 0 1 -2.56 5.96 c-.8 1.2 -1.85 2.35 -3.22 2.81 a4 4 0 0 1 -1.15 .19
            path(
                fill = SolidColor(Color(0xFFFAB5D5)),
            ) {
                // M 41.15 32.67
                moveTo(x = 41.15f, y = 32.67f)
                // a 5 5 0 0 1 -1.96 -0.37
                arcToRelative(
                    a = 5.0f,
                    b = 5.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.96f,
                    dy1 = -0.37f,
                )
                // c -0.52 -0.23 -1 -0.63 -1.16 -1.17
                curveToRelative(
                    dx1 = -0.52f,
                    dy1 = -0.23f,
                    dx2 = -1.0f,
                    dy2 = -0.63f,
                    dx3 = -1.16f,
                    dy3 = -1.17f,
                )
                // c -0.27 -0.87 0.32 -1.76 0.95 -2.41
                curveToRelative(
                    dx1 = -0.27f,
                    dy1 = -0.87f,
                    dx2 = 0.32f,
                    dy2 = -1.76f,
                    dx3 = 0.95f,
                    dy3 = -2.41f,
                )
                // a 14.4 14.4 0 0 1 7.94 -3.97
                arcToRelative(
                    a = 14.4f,
                    b = 14.4f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 7.94f,
                    dy1 = -3.97f,
                )
                // a 21 21 0 0 1 3.53 -0.29
                arcToRelative(
                    a = 21.0f,
                    b = 21.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 3.53f,
                    dy1 = -0.29f,
                )
                // a 28 28 0 0 1 3.03 0.18
                arcToRelative(
                    a = 28.0f,
                    b = 28.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 3.03f,
                    dy1 = 0.18f,
                )
                // c 0.81 0.09 2.4 0.03 2.63 1.05
                curveToRelative(
                    dx1 = 0.81f,
                    dy1 = 0.09f,
                    dx2 = 2.4f,
                    dy2 = 0.03f,
                    dx3 = 2.63f,
                    dy3 = 1.05f,
                )
                // c 0.18 0.8 -0.77 1.66 -1.44 2.18
                curveToRelative(
                    dx1 = 0.18f,
                    dy1 = 0.8f,
                    dx2 = -0.77f,
                    dy2 = 1.66f,
                    dx3 = -1.44f,
                    dy3 = 2.18f,
                )
                // l -0.32 0.23
                lineToRelative(dx = -0.32f, dy = 0.23f)
                // a 25 25 0 0 1 -13.2 4.57
                arcToRelative(
                    a = 25.0f,
                    b = 25.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -13.2f,
                    dy1 = 4.57f,
                )
                // m 8.16 8.85
                moveToRelative(dx = 8.16f, dy = 8.85f)
                // c -1.08 0 -2.17 -0.48 -2.66 -1.43
                curveToRelative(
                    dx1 = -1.08f,
                    dy1 = 0.0f,
                    dx2 = -2.17f,
                    dy2 = -0.48f,
                    dx3 = -2.66f,
                    dy3 = -1.43f,
                )
                // c -0.66 -1.24 -0.13 -2.77 0.54 -4
                curveToRelative(
                    dx1 = -0.66f,
                    dy1 = -1.24f,
                    dx2 = -0.13f,
                    dy2 = -2.77f,
                    dx3 = 0.54f,
                    dy3 = -4.0f,
                )
                // a 21 21 0 0 1 4.15 -5.32
                arcToRelative(
                    a = 21.0f,
                    b = 21.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 4.15f,
                    dy1 = -5.32f,
                )
                // c 0.44 -0.41 1.77 -1.73 3.01 -2.67
                curveToRelative(
                    dx1 = 0.44f,
                    dy1 = -0.41f,
                    dx2 = 1.77f,
                    dy2 = -1.73f,
                    dx3 = 3.01f,
                    dy3 = -2.67f,
                )
                // l 0.03 -0.02
                lineToRelative(dx = 0.03f, dy = -0.02f)
                // l 0.3 -0.21
                lineToRelative(dx = 0.3f, dy = -0.21f)
                // c 0.63 -0.46 1.22 -0.77 1.62 -0.77
                curveToRelative(
                    dx1 = 0.63f,
                    dy1 = -0.46f,
                    dx2 = 1.22f,
                    dy2 = -0.77f,
                    dx3 = 1.62f,
                    dy3 = -0.77f,
                )
                // a 1 1 0 0 1 0.22 0.04
                arcToRelative(
                    a = 1.0f,
                    b = 1.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.22f,
                    dy1 = 0.04f,
                )
                // c 0.45 0.2 0.54 0.9 0.48 1.77
                curveToRelative(
                    dx1 = 0.45f,
                    dy1 = 0.2f,
                    dx2 = 0.54f,
                    dy2 = 0.9f,
                    dx3 = 0.48f,
                    dy3 = 1.77f,
                )
                // a 27 27 0 0 0 -0.66 3.29
                arcToRelative(
                    a = 27.0f,
                    b = 27.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -0.66f,
                    dy1 = 3.29f,
                )
                // l -0.1 0.36
                lineToRelative(dx = -0.1f, dy = 0.36f)
                // a 19 19 0 0 1 -2.56 5.96
                arcToRelative(
                    a = 19.0f,
                    b = 19.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -2.56f,
                    dy1 = 5.96f,
                )
                // c -0.8 1.2 -1.85 2.35 -3.22 2.81
                curveToRelative(
                    dx1 = -0.8f,
                    dy1 = 1.2f,
                    dx2 = -1.85f,
                    dy2 = 2.35f,
                    dx3 = -3.22f,
                    dy3 = 2.81f,
                )
                // a 4 4 0 0 1 -1.15 0.19
                arcToRelative(
                    a = 4.0f,
                    b = 4.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.15f,
                    dy1 = 0.19f,
                )
            }
            // m54.35 28.1 .03 -.02z
            path(
                fill = SolidColor(Color(0xFFFBC4DB)),
            ) {
                // M 54.35 28.1
                moveTo(x = 54.35f, y = 28.1f)
                // l 0.03 -0.02z
                lineToRelative(dx = 0.03f, dy = -0.02f)
                close()
            }
            // M63.25 26.4 c-1.78 0 -4.2 -.13 -4.52 -.84 -.52 -1.16 3.09 -3.31 3.86 -3.83 1.81 -1.2 3.8 -2.15 5.9 -2.67 a9 9 0 0 1 2.23 -.3 c.71 0 1.41 .1 2.06 .39 1.33 .58 2.35 2.08 1.98 3.48 -.36 1.36 -1.78 2.14 -3.1 2.6 A21 21 0 0 1 65 26.36 c-.3 0 -.97 .03 -1.75 .03 m-5.55 -1.1 c-1.25 0 -1.94 -4.07 -2.13 -4.98 a19 19 0 0 1 -.34 -6.47 c.19 -1.44 .61 -2.93 1.64 -3.96 a3.6 3.6 0 0 1 2.46 -1.04 2.5 2.5 0 0 1 1.5 .47 c1.14 .83 1.35 2.43 1.3 3.84 a21 21 0 0 1 -1.37 6.6 c-.36 .96 -1.91 5.5 -3.04 5.55z M70.9 37.1 a6 6 0 0 1 -2.7 -.81 c-2 -1.05 -3.8 -2.45 -5.38 -4.07 -.71 -.74 -4.13 -4.08 -3.7 -5.12 .16 -.39 .7 -.52 1.38 -.52 1.41 0 3.44 .57 4.05 .71 a19 19 0 0 1 5.99 2.48 c1.22 .79 2.38 1.81 2.86 3.18 .49 1.37 .1 3.14 -1.18 3.83 a3 3 0 0 1 -1.32 .32
            path(
                fill = SolidColor(Color(0xFFFAB5D5)),
            ) {
                // M 63.25 26.4
                moveTo(x = 63.25f, y = 26.4f)
                // c -1.78 0 -4.2 -0.13 -4.52 -0.84
                curveToRelative(
                    dx1 = -1.78f,
                    dy1 = 0.0f,
                    dx2 = -4.2f,
                    dy2 = -0.13f,
                    dx3 = -4.52f,
                    dy3 = -0.84f,
                )
                // c -0.52 -1.16 3.09 -3.31 3.86 -3.83
                curveToRelative(
                    dx1 = -0.52f,
                    dy1 = -1.16f,
                    dx2 = 3.09f,
                    dy2 = -3.31f,
                    dx3 = 3.86f,
                    dy3 = -3.83f,
                )
                // c 1.81 -1.2 3.8 -2.15 5.9 -2.67
                curveToRelative(
                    dx1 = 1.81f,
                    dy1 = -1.2f,
                    dx2 = 3.8f,
                    dy2 = -2.15f,
                    dx3 = 5.9f,
                    dy3 = -2.67f,
                )
                // a 9 9 0 0 1 2.23 -0.3
                arcToRelative(
                    a = 9.0f,
                    b = 9.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 2.23f,
                    dy1 = -0.3f,
                )
                // c 0.71 0 1.41 0.1 2.06 0.39
                curveToRelative(
                    dx1 = 0.71f,
                    dy1 = 0.0f,
                    dx2 = 1.41f,
                    dy2 = 0.1f,
                    dx3 = 2.06f,
                    dy3 = 0.39f,
                )
                // c 1.33 0.58 2.35 2.08 1.98 3.48
                curveToRelative(
                    dx1 = 1.33f,
                    dy1 = 0.58f,
                    dx2 = 2.35f,
                    dy2 = 2.08f,
                    dx3 = 1.98f,
                    dy3 = 3.48f,
                )
                // c -0.36 1.36 -1.78 2.14 -3.1 2.6
                curveToRelative(
                    dx1 = -0.36f,
                    dy1 = 1.36f,
                    dx2 = -1.78f,
                    dy2 = 2.14f,
                    dx3 = -3.1f,
                    dy3 = 2.6f,
                )
                // A 21 21 0 0 1 65 26.36
                arcTo(
                    horizontalEllipseRadius = 21.0f,
                    verticalEllipseRadius = 21.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 65.0f,
                    y1 = 26.36f,
                )
                // c -0.3 0 -0.97 0.03 -1.75 0.03
                curveToRelative(
                    dx1 = -0.3f,
                    dy1 = 0.0f,
                    dx2 = -0.97f,
                    dy2 = 0.03f,
                    dx3 = -1.75f,
                    dy3 = 0.03f,
                )
                // m -5.55 -1.1
                moveToRelative(dx = -5.55f, dy = -1.1f)
                // c -1.25 0 -1.94 -4.07 -2.13 -4.98
                curveToRelative(
                    dx1 = -1.25f,
                    dy1 = 0.0f,
                    dx2 = -1.94f,
                    dy2 = -4.07f,
                    dx3 = -2.13f,
                    dy3 = -4.98f,
                )
                // a 19 19 0 0 1 -0.34 -6.47
                arcToRelative(
                    a = 19.0f,
                    b = 19.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.34f,
                    dy1 = -6.47f,
                )
                // c 0.19 -1.44 0.61 -2.93 1.64 -3.96
                curveToRelative(
                    dx1 = 0.19f,
                    dy1 = -1.44f,
                    dx2 = 0.61f,
                    dy2 = -2.93f,
                    dx3 = 1.64f,
                    dy3 = -3.96f,
                )
                // a 3.6 3.6 0 0 1 2.46 -1.04
                arcToRelative(
                    a = 3.6f,
                    b = 3.6f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 2.46f,
                    dy1 = -1.04f,
                )
                // a 2.5 2.5 0 0 1 1.5 0.47
                arcToRelative(
                    a = 2.5f,
                    b = 2.5f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 1.5f,
                    dy1 = 0.47f,
                )
                // c 1.14 0.83 1.35 2.43 1.3 3.84
                curveToRelative(
                    dx1 = 1.14f,
                    dy1 = 0.83f,
                    dx2 = 1.35f,
                    dy2 = 2.43f,
                    dx3 = 1.3f,
                    dy3 = 3.84f,
                )
                // a 21 21 0 0 1 -1.37 6.6
                arcToRelative(
                    a = 21.0f,
                    b = 21.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.37f,
                    dy1 = 6.6f,
                )
                // c -0.36 0.96 -1.91 5.5 -3.04 5.55z
                curveToRelative(
                    dx1 = -0.36f,
                    dy1 = 0.96f,
                    dx2 = -1.91f,
                    dy2 = 5.5f,
                    dx3 = -3.04f,
                    dy3 = 5.55f,
                )
                close()
                // M 70.9 37.1
                moveTo(x = 70.9f, y = 37.1f)
                // a 6 6 0 0 1 -2.7 -0.81
                arcToRelative(
                    a = 6.0f,
                    b = 6.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -2.7f,
                    dy1 = -0.81f,
                )
                // c -2 -1.05 -3.8 -2.45 -5.38 -4.07
                curveToRelative(
                    dx1 = -2.0f,
                    dy1 = -1.05f,
                    dx2 = -3.8f,
                    dy2 = -2.45f,
                    dx3 = -5.38f,
                    dy3 = -4.07f,
                )
                // c -0.71 -0.74 -4.13 -4.08 -3.7 -5.12
                curveToRelative(
                    dx1 = -0.71f,
                    dy1 = -0.74f,
                    dx2 = -4.13f,
                    dy2 = -4.08f,
                    dx3 = -3.7f,
                    dy3 = -5.12f,
                )
                // c 0.16 -0.39 0.7 -0.52 1.38 -0.52
                curveToRelative(
                    dx1 = 0.16f,
                    dy1 = -0.39f,
                    dx2 = 0.7f,
                    dy2 = -0.52f,
                    dx3 = 1.38f,
                    dy3 = -0.52f,
                )
                // c 1.41 0 3.44 0.57 4.05 0.71
                curveToRelative(
                    dx1 = 1.41f,
                    dy1 = 0.0f,
                    dx2 = 3.44f,
                    dy2 = 0.57f,
                    dx3 = 4.05f,
                    dy3 = 0.71f,
                )
                // a 19 19 0 0 1 5.99 2.48
                arcToRelative(
                    a = 19.0f,
                    b = 19.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 5.99f,
                    dy1 = 2.48f,
                )
                // c 1.22 0.79 2.38 1.81 2.86 3.18
                curveToRelative(
                    dx1 = 1.22f,
                    dy1 = 0.79f,
                    dx2 = 2.38f,
                    dy2 = 1.81f,
                    dx3 = 2.86f,
                    dy3 = 3.18f,
                )
                // c 0.49 1.37 0.1 3.14 -1.18 3.83
                curveToRelative(
                    dx1 = 0.49f,
                    dy1 = 1.37f,
                    dx2 = 0.1f,
                    dy2 = 3.14f,
                    dx3 = -1.18f,
                    dy3 = 3.83f,
                )
                // a 3 3 0 0 1 -1.32 0.32
                arcToRelative(
                    a = 3.0f,
                    b = 3.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.32f,
                    dy1 = 0.32f,
                )
            }
            // M59.24 43.76 a3 3 0 0 1 -1.22 -.22 .2 .2 0 0 0 -.16 -.09 h-.03 a3.5 3.5 0 0 1 -1.48 -1.23 c-.8 -1.23 -.75 -2.8 -.62 -4.27 .05 -.65 .24 -3.26 .61 -5.75 A20 20 0 0 0 57 28.9 c.3 -1.09 .68 -1.82 1.13 -1.82 l.09 .01 c1.09 .22 2.5 4.32 2.86 5.26 a25 25 0 0 1 1.54 6.17 6 6 0 0 1 -.25 3.19 3.5 3.5 0 0 1 -3.13 2.04
            path(
                fill = SolidColor(Color(0xFFFAB5D5)),
            ) {
                // M 59.24 43.76
                moveTo(x = 59.24f, y = 43.76f)
                // a 3 3 0 0 1 -1.22 -0.22
                arcToRelative(
                    a = 3.0f,
                    b = 3.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.22f,
                    dy1 = -0.22f,
                )
                // a 0.2 0.2 0 0 0 -0.16 -0.09
                arcToRelative(
                    a = 0.2f,
                    b = 0.2f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -0.16f,
                    dy1 = -0.09f,
                )
                // h -0.03
                horizontalLineToRelative(dx = -0.03f)
                // a 3.5 3.5 0 0 1 -1.48 -1.23
                arcToRelative(
                    a = 3.5f,
                    b = 3.5f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.48f,
                    dy1 = -1.23f,
                )
                // c -0.8 -1.23 -0.75 -2.8 -0.62 -4.27
                curveToRelative(
                    dx1 = -0.8f,
                    dy1 = -1.23f,
                    dx2 = -0.75f,
                    dy2 = -2.8f,
                    dx3 = -0.62f,
                    dy3 = -4.27f,
                )
                // c 0.05 -0.65 0.24 -3.26 0.61 -5.75
                curveToRelative(
                    dx1 = 0.05f,
                    dy1 = -0.65f,
                    dx2 = 0.24f,
                    dy2 = -3.26f,
                    dx3 = 0.61f,
                    dy3 = -5.75f,
                )
                // A 20 20 0 0 0 57 28.9
                arcTo(
                    horizontalEllipseRadius = 20.0f,
                    verticalEllipseRadius = 20.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 57.0f,
                    y1 = 28.9f,
                )
                // c 0.3 -1.09 0.68 -1.82 1.13 -1.82
                curveToRelative(
                    dx1 = 0.3f,
                    dy1 = -1.09f,
                    dx2 = 0.68f,
                    dy2 = -1.82f,
                    dx3 = 1.13f,
                    dy3 = -1.82f,
                )
                // l 0.09 0.01
                lineToRelative(dx = 0.09f, dy = 0.01f)
                // c 1.09 0.22 2.5 4.32 2.86 5.26
                curveToRelative(
                    dx1 = 1.09f,
                    dy1 = 0.22f,
                    dx2 = 2.5f,
                    dy2 = 4.32f,
                    dx3 = 2.86f,
                    dy3 = 5.26f,
                )
                // a 25 25 0 0 1 1.54 6.17
                arcToRelative(
                    a = 25.0f,
                    b = 25.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 1.54f,
                    dy1 = 6.17f,
                )
                // a 6 6 0 0 1 -0.25 3.19
                arcToRelative(
                    a = 6.0f,
                    b = 6.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.25f,
                    dy1 = 3.19f,
                )
                // a 3.5 3.5 0 0 1 -3.13 2.04
                arcToRelative(
                    a = 3.5f,
                    b = 3.5f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -3.13f,
                    dy1 = 2.04f,
                )
            }
            // m58.02 43.54 -.16 -.09 a.2 .2 0 0 1 .16 .09
            path(
                fill = SolidColor(Color(0xFFA583A0)),
            ) {
                // M 58.02 43.54
                moveTo(x = 58.02f, y = 43.54f)
                // l -0.16 -0.09
                lineToRelative(dx = -0.16f, dy = -0.09f)
                // a 0.2 0.2 0 0 1 0.16 0.09
                arcToRelative(
                    a = 0.2f,
                    b = 0.2f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.16f,
                    dy1 = 0.09f,
                )
            }
            // M56.34 32.2 c.18 -1.2 .4 -2.37 .66 -3.29 a20 20 0 0 1 -.66 3.29
            path(
                fill = SolidColor(Color(0xFFFBC4DB)),
            ) {
                // M 56.34 32.2
                moveTo(x = 56.34f, y = 32.2f)
                // c 0.18 -1.2 0.4 -2.37 0.66 -3.29
                curveToRelative(
                    dx1 = 0.18f,
                    dy1 = -1.2f,
                    dx2 = 0.4f,
                    dy2 = -2.37f,
                    dx3 = 0.66f,
                    dy3 = -3.29f,
                )
                // a 20 20 0 0 1 -0.66 3.29
                arcToRelative(
                    a = 20.0f,
                    b = 20.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.66f,
                    dy1 = 3.29f,
                )
            }
            // M53.68 23.5 c-.44 .18 -.89 .42 -1.1 .85 -.37 .77 .27 1.65 .91 2.22 1.82 1.59 4.14 2.6 6.54 2.87 A4.3 4.3 0 0 0 62 29.29 c.62 -.23 1.18 -.76 1.28 -1.42 .28 -1.72 -2.15 -3.45 -3.52 -4.1 a8 8 0 0 0 -6.09 -.27
            path(
                fill = SolidColor(Color(0xFFF04140)),
            ) {
                // M 53.68 23.5
                moveTo(x = 53.68f, y = 23.5f)
                // c -0.44 0.18 -0.89 0.42 -1.1 0.85
                curveToRelative(
                    dx1 = -0.44f,
                    dy1 = 0.18f,
                    dx2 = -0.89f,
                    dy2 = 0.42f,
                    dx3 = -1.1f,
                    dy3 = 0.85f,
                )
                // c -0.37 0.77 0.27 1.65 0.91 2.22
                curveToRelative(
                    dx1 = -0.37f,
                    dy1 = 0.77f,
                    dx2 = 0.27f,
                    dy2 = 1.65f,
                    dx3 = 0.91f,
                    dy3 = 2.22f,
                )
                // c 1.82 1.59 4.14 2.6 6.54 2.87
                curveToRelative(
                    dx1 = 1.82f,
                    dy1 = 1.59f,
                    dx2 = 4.14f,
                    dy2 = 2.6f,
                    dx3 = 6.54f,
                    dy3 = 2.87f,
                )
                // A 4.3 4.3 0 0 0 62 29.29
                arcTo(
                    horizontalEllipseRadius = 4.3f,
                    verticalEllipseRadius = 4.3f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 62.0f,
                    y1 = 29.29f,
                )
                // c 0.62 -0.23 1.18 -0.76 1.28 -1.42
                curveToRelative(
                    dx1 = 0.62f,
                    dy1 = -0.23f,
                    dx2 = 1.18f,
                    dy2 = -0.76f,
                    dx3 = 1.28f,
                    dy3 = -1.42f,
                )
                // c 0.28 -1.72 -2.15 -3.45 -3.52 -4.1
                curveToRelative(
                    dx1 = 0.28f,
                    dy1 = -1.72f,
                    dx2 = -2.15f,
                    dy2 = -3.45f,
                    dx3 = -3.52f,
                    dy3 = -4.1f,
                )
                // a 8 8 0 0 0 -6.09 -0.27
                arcToRelative(
                    a = 8.0f,
                    b = 8.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -6.09f,
                    dy1 = -0.27f,
                )
            }
            // M93.35 61.33 q-.14 .76 -.29 1.62 l-.36 1.96 a443 443 0 0 1 -8.57 36.25 l-.16 .53 -.3 1.02 -.55 .33 .99 -3.39 .39 -1.37 a406 406 0 0 0 7.8 -33.59 l.25 -1.31 .37 -2.12 a.2 .2 0 0 1 .25 -.17 .2 .2 0 0 1 .17 .24
            path(
                fill = SolidColor(Color(0xFF111528)),
            ) {
                // M 93.35 61.33
                moveTo(x = 93.35f, y = 61.33f)
                // q -0.14 0.76 -0.29 1.62
                quadToRelative(
                    dx1 = -0.14f,
                    dy1 = 0.76f,
                    dx2 = -0.29f,
                    dy2 = 1.62f,
                )
                // l -0.36 1.96
                lineToRelative(dx = -0.36f, dy = 1.96f)
                // a 443 443 0 0 1 -8.57 36.25
                arcToRelative(
                    a = 443.0f,
                    b = 443.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -8.57f,
                    dy1 = 36.25f,
                )
                // l -0.16 0.53
                lineToRelative(dx = -0.16f, dy = 0.53f)
                // l -0.3 1.02
                lineToRelative(dx = -0.3f, dy = 1.02f)
                // l -0.55 0.33
                lineToRelative(dx = -0.55f, dy = 0.33f)
                // l 0.99 -3.39
                lineToRelative(dx = 0.99f, dy = -3.39f)
                // l 0.39 -1.37
                lineToRelative(dx = 0.39f, dy = -1.37f)
                // a 406 406 0 0 0 7.8 -33.59
                arcToRelative(
                    a = 406.0f,
                    b = 406.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 7.8f,
                    dy1 = -33.59f,
                )
                // l 0.25 -1.31
                lineToRelative(dx = 0.25f, dy = -1.31f)
                // l 0.37 -2.12
                lineToRelative(dx = 0.37f, dy = -2.12f)
                // a 0.2 0.2 0 0 1 0.25 -0.17
                arcToRelative(
                    a = 0.2f,
                    b = 0.2f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.25f,
                    dy1 = -0.17f,
                )
                // a 0.2 0.2 0 0 1 0.17 0.24
                arcToRelative(
                    a = 0.2f,
                    b = 0.2f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.17f,
                    dy1 = 0.24f,
                )
            }
            // M84 45.9 c-1.18 -1.21 -2.87 -2.41 -4.45 -1.78 a2.8 2.8 0 0 0 -1.35 1.23 4.4 4.4 0 0 0 -.09 3.64 11 11 0 0 0 2.08 3.12 c2.42 2.76 5.27 5.29 8.66 6.79 .54 .24 1.95 1.04 2.52 .71 .81 -.46 .03 -1.84 -.2 -2.45 a32 32 0 0 0 -7.16 -11.26
            path(
                fill = SolidColor(Color(0xFFFF91B8)),
            ) {
                // M 84 45.9
                moveTo(x = 84.0f, y = 45.9f)
                // c -1.18 -1.21 -2.87 -2.41 -4.45 -1.78
                curveToRelative(
                    dx1 = -1.18f,
                    dy1 = -1.21f,
                    dx2 = -2.87f,
                    dy2 = -2.41f,
                    dx3 = -4.45f,
                    dy3 = -1.78f,
                )
                // a 2.8 2.8 0 0 0 -1.35 1.23
                arcToRelative(
                    a = 2.8f,
                    b = 2.8f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -1.35f,
                    dy1 = 1.23f,
                )
                // a 4.4 4.4 0 0 0 -0.09 3.64
                arcToRelative(
                    a = 4.4f,
                    b = 4.4f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -0.09f,
                    dy1 = 3.64f,
                )
                // a 11 11 0 0 0 2.08 3.12
                arcToRelative(
                    a = 11.0f,
                    b = 11.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 2.08f,
                    dy1 = 3.12f,
                )
                // c 2.42 2.76 5.27 5.29 8.66 6.79
                curveToRelative(
                    dx1 = 2.42f,
                    dy1 = 2.76f,
                    dx2 = 5.27f,
                    dy2 = 5.29f,
                    dx3 = 8.66f,
                    dy3 = 6.79f,
                )
                // c 0.54 0.24 1.95 1.04 2.52 0.71
                curveToRelative(
                    dx1 = 0.54f,
                    dy1 = 0.24f,
                    dx2 = 1.95f,
                    dy2 = 1.04f,
                    dx3 = 2.52f,
                    dy3 = 0.71f,
                )
                // c 0.81 -0.46 0.03 -1.84 -0.2 -2.45
                curveToRelative(
                    dx1 = 0.81f,
                    dy1 = -0.46f,
                    dx2 = 0.03f,
                    dy2 = -1.84f,
                    dx3 = -0.2f,
                    dy3 = -2.45f,
                )
                // a 32 32 0 0 0 -7.16 -11.26
                arcToRelative(
                    a = 32.0f,
                    b = 32.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -7.16f,
                    dy1 = -11.26f,
                )
            }
            // M81.71 57.89 a16 16 0 0 0 -9.5 2.56 c-.83 .57 -1.66 1.4 -1.56 2.41 .06 .62 .49 1.16 1 1.53 a6.7 6.7 0 0 0 3.28 1.01 c4.81 .5 9.74 -.29 14.16 -2.25 .79 -.35 2.43 -1.16 2.41 -2.22 -.02 -1.15 -1.77 -1.45 -2.62 -1.72 a26 26 0 0 0 -7.17 -1.32
            path(
                fill = SolidColor(Color(0xFFFF91B8)),
            ) {
                // M 81.71 57.89
                moveTo(x = 81.71f, y = 57.89f)
                // a 16 16 0 0 0 -9.5 2.56
                arcToRelative(
                    a = 16.0f,
                    b = 16.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -9.5f,
                    dy1 = 2.56f,
                )
                // c -0.83 0.57 -1.66 1.4 -1.56 2.41
                curveToRelative(
                    dx1 = -0.83f,
                    dy1 = 0.57f,
                    dx2 = -1.66f,
                    dy2 = 1.4f,
                    dx3 = -1.56f,
                    dy3 = 2.41f,
                )
                // c 0.06 0.62 0.49 1.16 1 1.53
                curveToRelative(
                    dx1 = 0.06f,
                    dy1 = 0.62f,
                    dx2 = 0.49f,
                    dy2 = 1.16f,
                    dx3 = 1.0f,
                    dy3 = 1.53f,
                )
                // a 6.7 6.7 0 0 0 3.28 1.01
                arcToRelative(
                    a = 6.7f,
                    b = 6.7f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 3.28f,
                    dy1 = 1.01f,
                )
                // c 4.81 0.5 9.74 -0.29 14.16 -2.25
                curveToRelative(
                    dx1 = 4.81f,
                    dy1 = 0.5f,
                    dx2 = 9.74f,
                    dy2 = -0.29f,
                    dx3 = 14.16f,
                    dy3 = -2.25f,
                )
                // c 0.79 -0.35 2.43 -1.16 2.41 -2.22
                curveToRelative(
                    dx1 = 0.79f,
                    dy1 = -0.35f,
                    dx2 = 2.43f,
                    dy2 = -1.16f,
                    dx3 = 2.41f,
                    dy3 = -2.22f,
                )
                // c -0.02 -1.15 -1.77 -1.45 -2.62 -1.72
                curveToRelative(
                    dx1 = -0.02f,
                    dy1 = -1.15f,
                    dx2 = -1.77f,
                    dy2 = -1.45f,
                    dx3 = -2.62f,
                    dy3 = -1.72f,
                )
                // a 26 26 0 0 0 -7.17 -1.32
                arcToRelative(
                    a = 26.0f,
                    b = 26.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -7.17f,
                    dy1 = -1.32f,
                )
            }
            // M79.51 70.27 c-1 1.2 -1.9 2.74 -1.47 4.23 .45 1.55 2.26 2.4 3.86 2.2 1.6 -.21 2.99 -1.22 4.13 -2.36 a21 21 0 0 0 4.1 -5.9 c.44 -.94 2.64 -5.04 1.5 -5.84 -1.03 -.72 -5.47 2.2 -6.43 2.8 a24 24 0 0 0 -5.69 4.87 m28.98 -6.41 c1.55 -.21 3.26 -.74 3.95 -2.14 .71 -1.45 -.06 -3.3 -1.38 -4.22 -1.32 -.93 -3.02 -1.12 -4.64 -1.05 a21 21 0 0 0 -7 1.6 c-.96 .4 -5.35 1.95 -5.04 3.32 .28 1.22 5.52 2.05 6.64 2.25 2.46 .46 4.99 .57 7.47 .24 m-7.7 -15.22 c.37 -1.52 .5 -3.3 -.55 -4.46 -1.08 -1.19 -3.09 -1.14 -4.43 -.25 -1.35 .9 -2.14 2.41 -2.66 3.94 a21 21 0 0 0 -1.06 7.1 c.02 1.04 -.14 5.7 1.25 5.9 1.23 .2 3.92 -4.39 4.52 -5.35 a24 24 0 0 0 2.94 -6.88 m1.51 26.47 c1.21 .98 2.77 1.87 4.26 1.42 1.55 -.48 2.36 -2.3 2.14 -3.9 -.23 -1.6 -1.26 -2.97 -2.41 -4.1 -1.73 -1.69 -3.78 -3 -5.97 -4 -.94 -.44 -5.07 -2.58 -5.86 -1.42 -.7 1.04 2.28 5.43 2.9 6.39 1.35 2.1 3 4.03 4.94 5.6
            path(
                fill = SolidColor(Color(0xFFFF91B8)),
            ) {
                // M 79.51 70.27
                moveTo(x = 79.51f, y = 70.27f)
                // c -1 1.2 -1.9 2.74 -1.47 4.23
                curveToRelative(
                    dx1 = -1.0f,
                    dy1 = 1.2f,
                    dx2 = -1.9f,
                    dy2 = 2.74f,
                    dx3 = -1.47f,
                    dy3 = 4.23f,
                )
                // c 0.45 1.55 2.26 2.4 3.86 2.2
                curveToRelative(
                    dx1 = 0.45f,
                    dy1 = 1.55f,
                    dx2 = 2.26f,
                    dy2 = 2.4f,
                    dx3 = 3.86f,
                    dy3 = 2.2f,
                )
                // c 1.6 -0.21 2.99 -1.22 4.13 -2.36
                curveToRelative(
                    dx1 = 1.6f,
                    dy1 = -0.21f,
                    dx2 = 2.99f,
                    dy2 = -1.22f,
                    dx3 = 4.13f,
                    dy3 = -2.36f,
                )
                // a 21 21 0 0 0 4.1 -5.9
                arcToRelative(
                    a = 21.0f,
                    b = 21.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 4.1f,
                    dy1 = -5.9f,
                )
                // c 0.44 -0.94 2.64 -5.04 1.5 -5.84
                curveToRelative(
                    dx1 = 0.44f,
                    dy1 = -0.94f,
                    dx2 = 2.64f,
                    dy2 = -5.04f,
                    dx3 = 1.5f,
                    dy3 = -5.84f,
                )
                // c -1.03 -0.72 -5.47 2.2 -6.43 2.8
                curveToRelative(
                    dx1 = -1.03f,
                    dy1 = -0.72f,
                    dx2 = -5.47f,
                    dy2 = 2.2f,
                    dx3 = -6.43f,
                    dy3 = 2.8f,
                )
                // a 24 24 0 0 0 -5.69 4.87
                arcToRelative(
                    a = 24.0f,
                    b = 24.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -5.69f,
                    dy1 = 4.87f,
                )
                // m 28.98 -6.41
                moveToRelative(dx = 28.98f, dy = -6.41f)
                // c 1.55 -0.21 3.26 -0.74 3.95 -2.14
                curveToRelative(
                    dx1 = 1.55f,
                    dy1 = -0.21f,
                    dx2 = 3.26f,
                    dy2 = -0.74f,
                    dx3 = 3.95f,
                    dy3 = -2.14f,
                )
                // c 0.71 -1.45 -0.06 -3.3 -1.38 -4.22
                curveToRelative(
                    dx1 = 0.71f,
                    dy1 = -1.45f,
                    dx2 = -0.06f,
                    dy2 = -3.3f,
                    dx3 = -1.38f,
                    dy3 = -4.22f,
                )
                // c -1.32 -0.93 -3.02 -1.12 -4.64 -1.05
                curveToRelative(
                    dx1 = -1.32f,
                    dy1 = -0.93f,
                    dx2 = -3.02f,
                    dy2 = -1.12f,
                    dx3 = -4.64f,
                    dy3 = -1.05f,
                )
                // a 21 21 0 0 0 -7 1.6
                arcToRelative(
                    a = 21.0f,
                    b = 21.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -7.0f,
                    dy1 = 1.6f,
                )
                // c -0.96 0.4 -5.35 1.95 -5.04 3.32
                curveToRelative(
                    dx1 = -0.96f,
                    dy1 = 0.4f,
                    dx2 = -5.35f,
                    dy2 = 1.95f,
                    dx3 = -5.04f,
                    dy3 = 3.32f,
                )
                // c 0.28 1.22 5.52 2.05 6.64 2.25
                curveToRelative(
                    dx1 = 0.28f,
                    dy1 = 1.22f,
                    dx2 = 5.52f,
                    dy2 = 2.05f,
                    dx3 = 6.64f,
                    dy3 = 2.25f,
                )
                // c 2.46 0.46 4.99 0.57 7.47 0.24
                curveToRelative(
                    dx1 = 2.46f,
                    dy1 = 0.46f,
                    dx2 = 4.99f,
                    dy2 = 0.57f,
                    dx3 = 7.47f,
                    dy3 = 0.24f,
                )
                // m -7.7 -15.22
                moveToRelative(dx = -7.7f, dy = -15.22f)
                // c 0.37 -1.52 0.5 -3.3 -0.55 -4.46
                curveToRelative(
                    dx1 = 0.37f,
                    dy1 = -1.52f,
                    dx2 = 0.5f,
                    dy2 = -3.3f,
                    dx3 = -0.55f,
                    dy3 = -4.46f,
                )
                // c -1.08 -1.19 -3.09 -1.14 -4.43 -0.25
                curveToRelative(
                    dx1 = -1.08f,
                    dy1 = -1.19f,
                    dx2 = -3.09f,
                    dy2 = -1.14f,
                    dx3 = -4.43f,
                    dy3 = -0.25f,
                )
                // c -1.35 0.9 -2.14 2.41 -2.66 3.94
                curveToRelative(
                    dx1 = -1.35f,
                    dy1 = 0.9f,
                    dx2 = -2.14f,
                    dy2 = 2.41f,
                    dx3 = -2.66f,
                    dy3 = 3.94f,
                )
                // a 21 21 0 0 0 -1.06 7.1
                arcToRelative(
                    a = 21.0f,
                    b = 21.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -1.06f,
                    dy1 = 7.1f,
                )
                // c 0.02 1.04 -0.14 5.7 1.25 5.9
                curveToRelative(
                    dx1 = 0.02f,
                    dy1 = 1.04f,
                    dx2 = -0.14f,
                    dy2 = 5.7f,
                    dx3 = 1.25f,
                    dy3 = 5.9f,
                )
                // c 1.23 0.2 3.92 -4.39 4.52 -5.35
                curveToRelative(
                    dx1 = 1.23f,
                    dy1 = 0.2f,
                    dx2 = 3.92f,
                    dy2 = -4.39f,
                    dx3 = 4.52f,
                    dy3 = -5.35f,
                )
                // a 24 24 0 0 0 2.94 -6.88
                arcToRelative(
                    a = 24.0f,
                    b = 24.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 2.94f,
                    dy1 = -6.88f,
                )
                // m 1.51 26.47
                moveToRelative(dx = 1.51f, dy = 26.47f)
                // c 1.21 0.98 2.77 1.87 4.26 1.42
                curveToRelative(
                    dx1 = 1.21f,
                    dy1 = 0.98f,
                    dx2 = 2.77f,
                    dy2 = 1.87f,
                    dx3 = 4.26f,
                    dy3 = 1.42f,
                )
                // c 1.55 -0.48 2.36 -2.3 2.14 -3.9
                curveToRelative(
                    dx1 = 1.55f,
                    dy1 = -0.48f,
                    dx2 = 2.36f,
                    dy2 = -2.3f,
                    dx3 = 2.14f,
                    dy3 = -3.9f,
                )
                // c -0.23 -1.6 -1.26 -2.97 -2.41 -4.1
                curveToRelative(
                    dx1 = -0.23f,
                    dy1 = -1.6f,
                    dx2 = -1.26f,
                    dy2 = -2.97f,
                    dx3 = -2.41f,
                    dy3 = -4.1f,
                )
                // c -1.73 -1.69 -3.78 -3 -5.97 -4
                curveToRelative(
                    dx1 = -1.73f,
                    dy1 = -1.69f,
                    dx2 = -3.78f,
                    dy2 = -3.0f,
                    dx3 = -5.97f,
                    dy3 = -4.0f,
                )
                // c -0.94 -0.44 -5.07 -2.58 -5.86 -1.42
                curveToRelative(
                    dx1 = -0.94f,
                    dy1 = -0.44f,
                    dx2 = -5.07f,
                    dy2 = -2.58f,
                    dx3 = -5.86f,
                    dy3 = -1.42f,
                )
                // c -0.7 1.04 2.28 5.43 2.9 6.39
                curveToRelative(
                    dx1 = -0.7f,
                    dy1 = 1.04f,
                    dx2 = 2.28f,
                    dy2 = 5.43f,
                    dx3 = 2.9f,
                    dy3 = 6.39f,
                )
                // c 1.35 2.1 3 4.03 4.94 5.6
                curveToRelative(
                    dx1 = 1.35f,
                    dy1 = 2.1f,
                    dx2 = 3.0f,
                    dy2 = 4.03f,
                    dx3 = 4.94f,
                    dy3 = 5.6f,
                )
            }
            // M88.38 74.18 c-.46 1.56 -.87 3.26 -.26 4.77 a3.9 3.9 0 0 0 3.09 2.37 3.9 3.9 0 0 0 3.55 -1.58 7 7 0 0 0 .99 -3.42 28 28 0 0 0 -.32 -7.04 c-.2 -1.1 -.82 -5.88 -1.95 -6.35 -1.76 -.73 -4.67 9.8 -5.1 11.25
            path(
                fill = SolidColor(Color(0xFFFF91B8)),
            ) {
                // M 88.38 74.18
                moveTo(x = 88.38f, y = 74.18f)
                // c -0.46 1.56 -0.87 3.26 -0.26 4.77
                curveToRelative(
                    dx1 = -0.46f,
                    dy1 = 1.56f,
                    dx2 = -0.87f,
                    dy2 = 3.26f,
                    dx3 = -0.26f,
                    dy3 = 4.77f,
                )
                // a 3.9 3.9 0 0 0 3.09 2.37
                arcToRelative(
                    a = 3.9f,
                    b = 3.9f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 3.09f,
                    dy1 = 2.37f,
                )
                // a 3.9 3.9 0 0 0 3.55 -1.58
                arcToRelative(
                    a = 3.9f,
                    b = 3.9f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 3.55f,
                    dy1 = -1.58f,
                )
                // a 7 7 0 0 0 0.99 -3.42
                arcToRelative(
                    a = 7.0f,
                    b = 7.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.99f,
                    dy1 = -3.42f,
                )
                // a 28 28 0 0 0 -0.32 -7.04
                arcToRelative(
                    a = 28.0f,
                    b = 28.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -0.32f,
                    dy1 = -7.04f,
                )
                // c -0.2 -1.1 -0.82 -5.88 -1.95 -6.35
                curveToRelative(
                    dx1 = -0.2f,
                    dy1 = -1.1f,
                    dx2 = -0.82f,
                    dy2 = -5.88f,
                    dx3 = -1.95f,
                    dy3 = -6.35f,
                )
                // c -1.76 -0.73 -4.67 9.8 -5.1 11.25
                curveToRelative(
                    dx1 = -1.76f,
                    dy1 = -0.73f,
                    dx2 = -4.67f,
                    dy2 = 9.8f,
                    dx3 = -5.1f,
                    dy3 = 11.25f,
                )
            }
            // M89.34 58.02 c-.51 .1 -1.06 .26 -1.38 .68 -.57 .76 -.08 1.85 .5 2.61 a13.3 13.3 0 0 0 6.47 4.56 5 5 0 0 0 2.19 .28 c.73 -.12 1.44 -.57 1.7 -1.26 .69 -1.82 -1.57 -4.23 -2.92 -5.25 a8.8 8.8 0 0 0 -6.56 -1.62
            path(
                fill = SolidColor(Color(0xFFF04140)),
            ) {
                // M 89.34 58.02
                moveTo(x = 89.34f, y = 58.02f)
                // c -0.51 0.1 -1.06 0.26 -1.38 0.68
                curveToRelative(
                    dx1 = -0.51f,
                    dy1 = 0.1f,
                    dx2 = -1.06f,
                    dy2 = 0.26f,
                    dx3 = -1.38f,
                    dy3 = 0.68f,
                )
                // c -0.57 0.76 -0.08 1.85 0.5 2.61
                curveToRelative(
                    dx1 = -0.57f,
                    dy1 = 0.76f,
                    dx2 = -0.08f,
                    dy2 = 1.85f,
                    dx3 = 0.5f,
                    dy3 = 2.61f,
                )
                // a 13.3 13.3 0 0 0 6.47 4.56
                arcToRelative(
                    a = 13.3f,
                    b = 13.3f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 6.47f,
                    dy1 = 4.56f,
                )
                // a 5 5 0 0 0 2.19 0.28
                arcToRelative(
                    a = 5.0f,
                    b = 5.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 2.19f,
                    dy1 = 0.28f,
                )
                // c 0.73 -0.12 1.44 -0.57 1.7 -1.26
                curveToRelative(
                    dx1 = 0.73f,
                    dy1 = -0.12f,
                    dx2 = 1.44f,
                    dy2 = -0.57f,
                    dx3 = 1.7f,
                    dy3 = -1.26f,
                )
                // c 0.69 -1.82 -1.57 -4.23 -2.92 -5.25
                curveToRelative(
                    dx1 = 0.69f,
                    dy1 = -1.82f,
                    dx2 = -1.57f,
                    dy2 = -4.23f,
                    dx3 = -2.92f,
                    dy3 = -5.25f,
                )
                // a 8.8 8.8 0 0 0 -6.56 -1.62
                arcToRelative(
                    a = 8.8f,
                    b = 8.8f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -6.56f,
                    dy1 = -1.62f,
                )
            }
            // M84.25 100.28 c-.51 -3.08 -2.84 -5.47 -4.8 -7.9 a23 23 0 0 1 -3.88 -6.53 12.6 12.6 0 0 1 -.5 -7.49 c2.58 1.29 5.07 2.35 6.78 4.68 a13 13 0 0 1 1.9 4.15 c1.22 4.37 1.15 8.6 .5 13.1 m-.8 1.74 c2.56 -1.8 5.9 -1.82 9 -2.14 a23 23 0 0 0 7.38 -1.82 c2.3 -1.05 4.4 -2.7 5.68 -4.9 -2.73 -.93 -5.23 -1.96 -8.1 -1.54 a13 13 0 0 0 -4.28 1.56 c-3.97 2.2 -6.93 5.22 -9.67 8.84 m-51.57 1.58 -.53 -.3 -.6 -2.2 -.57 -2.14 a442.98 442.98 0 0 1 -6.44 -28.51 l-.36 -1.97 -.29 -1.62 a.2 .2 0 0 1 .17 -.25 .2 .2 0 0 1 .25 .18 l.62 3.43 a408 408 0 0 0 3.27 15.6 406 406 0 0 0 3.99 15.97 l.12 .46 .1 .34 q.13 .51 .27 1.01
            path(
                fill = SolidColor(Color(0xFF111528)),
            ) {
                // M 84.25 100.28
                moveTo(x = 84.25f, y = 100.28f)
                // c -0.51 -3.08 -2.84 -5.47 -4.8 -7.9
                curveToRelative(
                    dx1 = -0.51f,
                    dy1 = -3.08f,
                    dx2 = -2.84f,
                    dy2 = -5.47f,
                    dx3 = -4.8f,
                    dy3 = -7.9f,
                )
                // a 23 23 0 0 1 -3.88 -6.53
                arcToRelative(
                    a = 23.0f,
                    b = 23.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -3.88f,
                    dy1 = -6.53f,
                )
                // a 12.6 12.6 0 0 1 -0.5 -7.49
                arcToRelative(
                    a = 12.6f,
                    b = 12.6f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.5f,
                    dy1 = -7.49f,
                )
                // c 2.58 1.29 5.07 2.35 6.78 4.68
                curveToRelative(
                    dx1 = 2.58f,
                    dy1 = 1.29f,
                    dx2 = 5.07f,
                    dy2 = 2.35f,
                    dx3 = 6.78f,
                    dy3 = 4.68f,
                )
                // a 13 13 0 0 1 1.9 4.15
                arcToRelative(
                    a = 13.0f,
                    b = 13.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 1.9f,
                    dy1 = 4.15f,
                )
                // c 1.22 4.37 1.15 8.6 0.5 13.1
                curveToRelative(
                    dx1 = 1.22f,
                    dy1 = 4.37f,
                    dx2 = 1.15f,
                    dy2 = 8.6f,
                    dx3 = 0.5f,
                    dy3 = 13.1f,
                )
                // m -0.8 1.74
                moveToRelative(dx = -0.8f, dy = 1.74f)
                // c 2.56 -1.8 5.9 -1.82 9 -2.14
                curveToRelative(
                    dx1 = 2.56f,
                    dy1 = -1.8f,
                    dx2 = 5.9f,
                    dy2 = -1.82f,
                    dx3 = 9.0f,
                    dy3 = -2.14f,
                )
                // a 23 23 0 0 0 7.38 -1.82
                arcToRelative(
                    a = 23.0f,
                    b = 23.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 7.38f,
                    dy1 = -1.82f,
                )
                // c 2.3 -1.05 4.4 -2.7 5.68 -4.9
                curveToRelative(
                    dx1 = 2.3f,
                    dy1 = -1.05f,
                    dx2 = 4.4f,
                    dy2 = -2.7f,
                    dx3 = 5.68f,
                    dy3 = -4.9f,
                )
                // c -2.73 -0.93 -5.23 -1.96 -8.1 -1.54
                curveToRelative(
                    dx1 = -2.73f,
                    dy1 = -0.93f,
                    dx2 = -5.23f,
                    dy2 = -1.96f,
                    dx3 = -8.1f,
                    dy3 = -1.54f,
                )
                // a 13 13 0 0 0 -4.28 1.56
                arcToRelative(
                    a = 13.0f,
                    b = 13.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -4.28f,
                    dy1 = 1.56f,
                )
                // c -3.97 2.2 -6.93 5.22 -9.67 8.84
                curveToRelative(
                    dx1 = -3.97f,
                    dy1 = 2.2f,
                    dx2 = -6.93f,
                    dy2 = 5.22f,
                    dx3 = -9.67f,
                    dy3 = 8.84f,
                )
                // m -51.57 1.58
                moveToRelative(dx = -51.57f, dy = 1.58f)
                // l -0.53 -0.3
                lineToRelative(dx = -0.53f, dy = -0.3f)
                // l -0.6 -2.2
                lineToRelative(dx = -0.6f, dy = -2.2f)
                // l -0.57 -2.14
                lineToRelative(dx = -0.57f, dy = -2.14f)
                // a 442.98 442.98 0 0 1 -6.44 -28.51
                arcToRelative(
                    a = 442.98f,
                    b = 442.98f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -6.44f,
                    dy1 = -28.51f,
                )
                // l -0.36 -1.97
                lineToRelative(dx = -0.36f, dy = -1.97f)
                // l -0.29 -1.62
                lineToRelative(dx = -0.29f, dy = -1.62f)
                // a 0.2 0.2 0 0 1 0.17 -0.25
                arcToRelative(
                    a = 0.2f,
                    b = 0.2f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.17f,
                    dy1 = -0.25f,
                )
                // a 0.2 0.2 0 0 1 0.25 0.18
                arcToRelative(
                    a = 0.2f,
                    b = 0.2f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.25f,
                    dy1 = 0.18f,
                )
                // l 0.62 3.43
                lineToRelative(dx = 0.62f, dy = 3.43f)
                // a 408 408 0 0 0 3.27 15.6
                arcToRelative(
                    a = 408.0f,
                    b = 408.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 3.27f,
                    dy1 = 15.6f,
                )
                // a 406 406 0 0 0 3.99 15.97
                arcToRelative(
                    a = 406.0f,
                    b = 406.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 3.99f,
                    dy1 = 15.97f,
                )
                // l 0.12 0.46
                lineToRelative(dx = 0.12f, dy = 0.46f)
                // l 0.1 0.34
                lineToRelative(dx = 0.1f, dy = 0.34f)
                // q 0.13 0.51 0.27 1.01
                quadToRelative(
                    dx1 = 0.13f,
                    dy1 = 0.51f,
                    dx2 = 0.27f,
                    dy2 = 1.01f,
                )
            }
            // M32.43 51.43 c1.19 -1.21 2.87 -2.41 4.45 -1.79 a2.8 2.8 0 0 1 1.35 1.24 4.4 4.4 0 0 1 .1 3.64 11 11 0 0 1 -2.09 3.12 c-2.42 2.76 -5.26 5.29 -8.65 6.78 -.55 .25 -1.95 1.05 -2.53 .72 -.8 -.47 -.02 -1.84 .2 -2.45 a32 32 0 0 1 7.17 -11.26
            path(
                fill = SolidColor(Color(0xFFFF91B8)),
            ) {
                // M 32.43 51.43
                moveTo(x = 32.43f, y = 51.43f)
                // c 1.19 -1.21 2.87 -2.41 4.45 -1.79
                curveToRelative(
                    dx1 = 1.19f,
                    dy1 = -1.21f,
                    dx2 = 2.87f,
                    dy2 = -2.41f,
                    dx3 = 4.45f,
                    dy3 = -1.79f,
                )
                // a 2.8 2.8 0 0 1 1.35 1.24
                arcToRelative(
                    a = 2.8f,
                    b = 2.8f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 1.35f,
                    dy1 = 1.24f,
                )
                // a 4.4 4.4 0 0 1 0.1 3.64
                arcToRelative(
                    a = 4.4f,
                    b = 4.4f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.1f,
                    dy1 = 3.64f,
                )
                // a 11 11 0 0 1 -2.09 3.12
                arcToRelative(
                    a = 11.0f,
                    b = 11.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -2.09f,
                    dy1 = 3.12f,
                )
                // c -2.42 2.76 -5.26 5.29 -8.65 6.78
                curveToRelative(
                    dx1 = -2.42f,
                    dy1 = 2.76f,
                    dx2 = -5.26f,
                    dy2 = 5.29f,
                    dx3 = -8.65f,
                    dy3 = 6.78f,
                )
                // c -0.55 0.25 -1.95 1.05 -2.53 0.72
                curveToRelative(
                    dx1 = -0.55f,
                    dy1 = 0.25f,
                    dx2 = -1.95f,
                    dy2 = 1.05f,
                    dx3 = -2.53f,
                    dy3 = 0.72f,
                )
                // c -0.8 -0.47 -0.02 -1.84 0.2 -2.45
                curveToRelative(
                    dx1 = -0.8f,
                    dy1 = -0.47f,
                    dx2 = -0.02f,
                    dy2 = -1.84f,
                    dx3 = 0.2f,
                    dy3 = -2.45f,
                )
                // a 32 32 0 0 1 7.17 -11.26
                arcToRelative(
                    a = 32.0f,
                    b = 32.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 7.17f,
                    dy1 = -11.26f,
                )
            }
            // M34.72 63.41 a16 16 0 0 1 9.5 2.57 c.83 .57 1.67 1.4 1.57 2.4 -.06 .63 -.5 1.17 -1 1.54 a6.7 6.7 0 0 1 -3.29 1.01 28 28 0 0 1 -14.15 -2.25 c-.8 -.35 -2.44 -1.16 -2.41 -2.22 .02 -1.15 1.76 -1.45 2.62 -1.72 a26 26 0 0 1 7.16 -1.33
            path(
                fill = SolidColor(Color(0xFFFF91B8)),
            ) {
                // M 34.72 63.41
                moveTo(x = 34.72f, y = 63.41f)
                // a 16 16 0 0 1 9.5 2.57
                arcToRelative(
                    a = 16.0f,
                    b = 16.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 9.5f,
                    dy1 = 2.57f,
                )
                // c 0.83 0.57 1.67 1.4 1.57 2.4
                curveToRelative(
                    dx1 = 0.83f,
                    dy1 = 0.57f,
                    dx2 = 1.67f,
                    dy2 = 1.4f,
                    dx3 = 1.57f,
                    dy3 = 2.4f,
                )
                // c -0.06 0.63 -0.5 1.17 -1 1.54
                curveToRelative(
                    dx1 = -0.06f,
                    dy1 = 0.63f,
                    dx2 = -0.5f,
                    dy2 = 1.17f,
                    dx3 = -1.0f,
                    dy3 = 1.54f,
                )
                // a 6.7 6.7 0 0 1 -3.29 1.01
                arcToRelative(
                    a = 6.7f,
                    b = 6.7f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -3.29f,
                    dy1 = 1.01f,
                )
                // a 28 28 0 0 1 -14.15 -2.25
                arcToRelative(
                    a = 28.0f,
                    b = 28.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -14.15f,
                    dy1 = -2.25f,
                )
                // c -0.8 -0.35 -2.44 -1.16 -2.41 -2.22
                curveToRelative(
                    dx1 = -0.8f,
                    dy1 = -0.35f,
                    dx2 = -2.44f,
                    dy2 = -1.16f,
                    dx3 = -2.41f,
                    dy3 = -2.22f,
                )
                // c 0.02 -1.15 1.76 -1.45 2.62 -1.72
                curveToRelative(
                    dx1 = 0.02f,
                    dy1 = -1.15f,
                    dx2 = 1.76f,
                    dy2 = -1.45f,
                    dx3 = 2.62f,
                    dy3 = -1.72f,
                )
                // a 26 26 0 0 1 7.16 -1.33
                arcToRelative(
                    a = 26.0f,
                    b = 26.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 7.16f,
                    dy1 = -1.33f,
                )
            }
            // M36.92 75.8 c1 1.19 1.91 2.73 1.48 4.23 -.45 1.55 -2.27 2.4 -3.87 2.19 S31.55 81 30.4 79.87 a21 21 0 0 1 -4.1 -5.91 c-.44 -.93 -2.64 -5.03 -1.49 -5.84 1.03 -.71 5.46 2.2 6.43 2.8 a24 24 0 0 1 5.68 4.87 M7.94 69.38 C6.4 69.18 4.68 68.65 4 67.25 c-.7 -1.45 .07 -3.3 1.39 -4.22 C6.7 62.1 8.4 61.9 10 61.98 c2.41 .1 4.78 .7 7 1.6 .97 .4 5.36 1.94 5.05 3.31 -.28 1.22 -5.52 2.06 -6.64 2.26 a24 24 0 0 1 -7.48 .24 m7.7 -15.23 c-.37 -1.51 -.5 -3.3 .55 -4.45 1.09 -1.19 3.1 -1.14 4.44 -.25 s2.13 2.41 2.66 3.94 a21 21 0 0 1 1.05 7.1 c-.01 1.04 .15 5.7 -1.24 5.9 -1.24 .19 -3.92 -4.39 -4.52 -5.36 a24 24 0 0 1 -2.94 -6.88 m-1.5 26.48 c-1.22 .98 -2.77 1.87 -4.27 1.41 -1.54 -.47 -2.36 -2.3 -2.13 -3.9 s1.26 -2.96 2.4 -4.1 c1.73 -1.68 3.79 -3 5.98 -4 .94 -.43 5.07 -2.58 5.85 -1.41 .7 1.03 -2.28 5.43 -2.9 6.38 a24 24 0 0 1 -4.94 5.62
            path(
                fill = SolidColor(Color(0xFFFF91B8)),
            ) {
                // M 36.92 75.8
                moveTo(x = 36.92f, y = 75.8f)
                // c 1 1.19 1.91 2.73 1.48 4.23
                curveToRelative(
                    dx1 = 1.0f,
                    dy1 = 1.19f,
                    dx2 = 1.91f,
                    dy2 = 2.73f,
                    dx3 = 1.48f,
                    dy3 = 4.23f,
                )
                // c -0.45 1.55 -2.27 2.4 -3.87 2.19
                curveToRelative(
                    dx1 = -0.45f,
                    dy1 = 1.55f,
                    dx2 = -2.27f,
                    dy2 = 2.4f,
                    dx3 = -3.87f,
                    dy3 = 2.19f,
                )
                // S 31.55 81 30.4 79.87
                reflectiveCurveTo(
                    x1 = 31.55f,
                    y1 = 81.0f,
                    x2 = 30.4f,
                    y2 = 79.87f,
                )
                // a 21 21 0 0 1 -4.1 -5.91
                arcToRelative(
                    a = 21.0f,
                    b = 21.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -4.1f,
                    dy1 = -5.91f,
                )
                // c -0.44 -0.93 -2.64 -5.03 -1.49 -5.84
                curveToRelative(
                    dx1 = -0.44f,
                    dy1 = -0.93f,
                    dx2 = -2.64f,
                    dy2 = -5.03f,
                    dx3 = -1.49f,
                    dy3 = -5.84f,
                )
                // c 1.03 -0.71 5.46 2.2 6.43 2.8
                curveToRelative(
                    dx1 = 1.03f,
                    dy1 = -0.71f,
                    dx2 = 5.46f,
                    dy2 = 2.2f,
                    dx3 = 6.43f,
                    dy3 = 2.8f,
                )
                // a 24 24 0 0 1 5.68 4.87
                arcToRelative(
                    a = 24.0f,
                    b = 24.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 5.68f,
                    dy1 = 4.87f,
                )
                // M 7.94 69.38
                moveTo(x = 7.94f, y = 69.38f)
                // C 6.4 69.18 4.68 68.65 4 67.25
                curveTo(
                    x1 = 6.4f,
                    y1 = 69.18f,
                    x2 = 4.68f,
                    y2 = 68.65f,
                    x3 = 4.0f,
                    y3 = 67.25f,
                )
                // c -0.7 -1.45 0.07 -3.3 1.39 -4.22
                curveToRelative(
                    dx1 = -0.7f,
                    dy1 = -1.45f,
                    dx2 = 0.07f,
                    dy2 = -3.3f,
                    dx3 = 1.39f,
                    dy3 = -4.22f,
                )
                // C 6.7 62.1 8.4 61.9 10 61.98
                curveTo(
                    x1 = 6.7f,
                    y1 = 62.1f,
                    x2 = 8.4f,
                    y2 = 61.9f,
                    x3 = 10.0f,
                    y3 = 61.98f,
                )
                // c 2.41 0.1 4.78 0.7 7 1.6
                curveToRelative(
                    dx1 = 2.41f,
                    dy1 = 0.1f,
                    dx2 = 4.78f,
                    dy2 = 0.7f,
                    dx3 = 7.0f,
                    dy3 = 1.6f,
                )
                // c 0.97 0.4 5.36 1.94 5.05 3.31
                curveToRelative(
                    dx1 = 0.97f,
                    dy1 = 0.4f,
                    dx2 = 5.36f,
                    dy2 = 1.94f,
                    dx3 = 5.05f,
                    dy3 = 3.31f,
                )
                // c -0.28 1.22 -5.52 2.06 -6.64 2.26
                curveToRelative(
                    dx1 = -0.28f,
                    dy1 = 1.22f,
                    dx2 = -5.52f,
                    dy2 = 2.06f,
                    dx3 = -6.64f,
                    dy3 = 2.26f,
                )
                // a 24 24 0 0 1 -7.48 0.24
                arcToRelative(
                    a = 24.0f,
                    b = 24.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -7.48f,
                    dy1 = 0.24f,
                )
                // m 7.7 -15.23
                moveToRelative(dx = 7.7f, dy = -15.23f)
                // c -0.37 -1.51 -0.5 -3.3 0.55 -4.45
                curveToRelative(
                    dx1 = -0.37f,
                    dy1 = -1.51f,
                    dx2 = -0.5f,
                    dy2 = -3.3f,
                    dx3 = 0.55f,
                    dy3 = -4.45f,
                )
                // c 1.09 -1.19 3.1 -1.14 4.44 -0.25
                curveToRelative(
                    dx1 = 1.09f,
                    dy1 = -1.19f,
                    dx2 = 3.1f,
                    dy2 = -1.14f,
                    dx3 = 4.44f,
                    dy3 = -0.25f,
                )
                // s 2.13 2.41 2.66 3.94
                reflectiveCurveToRelative(
                    dx1 = 2.13f,
                    dy1 = 2.41f,
                    dx2 = 2.66f,
                    dy2 = 3.94f,
                )
                // a 21 21 0 0 1 1.05 7.1
                arcToRelative(
                    a = 21.0f,
                    b = 21.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 1.05f,
                    dy1 = 7.1f,
                )
                // c -0.01 1.04 0.15 5.7 -1.24 5.9
                curveToRelative(
                    dx1 = -0.01f,
                    dy1 = 1.04f,
                    dx2 = 0.15f,
                    dy2 = 5.7f,
                    dx3 = -1.24f,
                    dy3 = 5.9f,
                )
                // c -1.24 0.19 -3.92 -4.39 -4.52 -5.36
                curveToRelative(
                    dx1 = -1.24f,
                    dy1 = 0.19f,
                    dx2 = -3.92f,
                    dy2 = -4.39f,
                    dx3 = -4.52f,
                    dy3 = -5.36f,
                )
                // a 24 24 0 0 1 -2.94 -6.88
                arcToRelative(
                    a = 24.0f,
                    b = 24.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -2.94f,
                    dy1 = -6.88f,
                )
                // m -1.5 26.48
                moveToRelative(dx = -1.5f, dy = 26.48f)
                // c -1.22 0.98 -2.77 1.87 -4.27 1.41
                curveToRelative(
                    dx1 = -1.22f,
                    dy1 = 0.98f,
                    dx2 = -2.77f,
                    dy2 = 1.87f,
                    dx3 = -4.27f,
                    dy3 = 1.41f,
                )
                // c -1.54 -0.47 -2.36 -2.3 -2.13 -3.9
                curveToRelative(
                    dx1 = -1.54f,
                    dy1 = -0.47f,
                    dx2 = -2.36f,
                    dy2 = -2.3f,
                    dx3 = -2.13f,
                    dy3 = -3.9f,
                )
                // s 1.26 -2.96 2.4 -4.1
                reflectiveCurveToRelative(
                    dx1 = 1.26f,
                    dy1 = -2.96f,
                    dx2 = 2.4f,
                    dy2 = -4.1f,
                )
                // c 1.73 -1.68 3.79 -3 5.98 -4
                curveToRelative(
                    dx1 = 1.73f,
                    dy1 = -1.68f,
                    dx2 = 3.79f,
                    dy2 = -3.0f,
                    dx3 = 5.98f,
                    dy3 = -4.0f,
                )
                // c 0.94 -0.43 5.07 -2.58 5.85 -1.41
                curveToRelative(
                    dx1 = 0.94f,
                    dy1 = -0.43f,
                    dx2 = 5.07f,
                    dy2 = -2.58f,
                    dx3 = 5.85f,
                    dy3 = -1.41f,
                )
                // c 0.7 1.03 -2.28 5.43 -2.9 6.38
                curveToRelative(
                    dx1 = 0.7f,
                    dy1 = 1.03f,
                    dx2 = -2.28f,
                    dy2 = 5.43f,
                    dx3 = -2.9f,
                    dy3 = 6.38f,
                )
                // a 24 24 0 0 1 -4.94 5.62
                arcToRelative(
                    a = 24.0f,
                    b = 24.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -4.94f,
                    dy1 = 5.62f,
                )
            }
            // M28.06 79.7 c.46 1.56 .87 3.27 .26 4.78 a3.9 3.9 0 0 1 -3.09 2.36 3.9 3.9 0 0 1 -3.56 -1.58 7 7 0 0 1 -.98 -3.41 A28 28 0 0 1 21 74.81 c.19 -1.1 .81 -5.88 1.95 -6.35 1.75 -.73 4.67 9.8 5.1 11.25
            path(
                fill = SolidColor(Color(0xFFFF91B8)),
            ) {
                // M 28.06 79.7
                moveTo(x = 28.06f, y = 79.7f)
                // c 0.46 1.56 0.87 3.27 0.26 4.78
                curveToRelative(
                    dx1 = 0.46f,
                    dy1 = 1.56f,
                    dx2 = 0.87f,
                    dy2 = 3.27f,
                    dx3 = 0.26f,
                    dy3 = 4.78f,
                )
                // a 3.9 3.9 0 0 1 -3.09 2.36
                arcToRelative(
                    a = 3.9f,
                    b = 3.9f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -3.09f,
                    dy1 = 2.36f,
                )
                // a 3.9 3.9 0 0 1 -3.56 -1.58
                arcToRelative(
                    a = 3.9f,
                    b = 3.9f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -3.56f,
                    dy1 = -1.58f,
                )
                // a 7 7 0 0 1 -0.98 -3.41
                arcToRelative(
                    a = 7.0f,
                    b = 7.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.98f,
                    dy1 = -3.41f,
                )
                // A 28 28 0 0 1 21 74.81
                arcTo(
                    horizontalEllipseRadius = 28.0f,
                    verticalEllipseRadius = 28.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 21.0f,
                    y1 = 74.81f,
                )
                // c 0.19 -1.1 0.81 -5.88 1.95 -6.35
                curveToRelative(
                    dx1 = 0.19f,
                    dy1 = -1.1f,
                    dx2 = 0.81f,
                    dy2 = -5.88f,
                    dx3 = 1.95f,
                    dy3 = -6.35f,
                )
                // c 1.75 -0.73 4.67 9.8 5.1 11.25
                curveToRelative(
                    dx1 = 1.75f,
                    dy1 = -0.73f,
                    dx2 = 4.67f,
                    dy2 = 9.8f,
                    dx3 = 5.1f,
                    dy3 = 11.25f,
                )
            }
            // M27.1 63.55 c.51 .1 1.06 .26 1.37 .68 .58 .76 .09 1.85 -.5 2.6 a13.3 13.3 0 0 1 -6.47 4.57 5 5 0 0 1 -2.18 .27 c-.73 -.11 -1.45 -.56 -1.7 -1.25 -.69 -1.82 1.57 -4.23 2.91 -5.25 a8.8 8.8 0 0 1 6.56 -1.62
            path(
                fill = SolidColor(Color(0xFFF04140)),
            ) {
                // M 27.1 63.55
                moveTo(x = 27.1f, y = 63.55f)
                // c 0.51 0.1 1.06 0.26 1.37 0.68
                curveToRelative(
                    dx1 = 0.51f,
                    dy1 = 0.1f,
                    dx2 = 1.06f,
                    dy2 = 0.26f,
                    dx3 = 1.37f,
                    dy3 = 0.68f,
                )
                // c 0.58 0.76 0.09 1.85 -0.5 2.6
                curveToRelative(
                    dx1 = 0.58f,
                    dy1 = 0.76f,
                    dx2 = 0.09f,
                    dy2 = 1.85f,
                    dx3 = -0.5f,
                    dy3 = 2.6f,
                )
                // a 13.3 13.3 0 0 1 -6.47 4.57
                arcToRelative(
                    a = 13.3f,
                    b = 13.3f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -6.47f,
                    dy1 = 4.57f,
                )
                // a 5 5 0 0 1 -2.18 0.27
                arcToRelative(
                    a = 5.0f,
                    b = 5.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -2.18f,
                    dy1 = 0.27f,
                )
                // c -0.73 -0.11 -1.45 -0.56 -1.7 -1.25
                curveToRelative(
                    dx1 = -0.73f,
                    dy1 = -0.11f,
                    dx2 = -1.45f,
                    dy2 = -0.56f,
                    dx3 = -1.7f,
                    dy3 = -1.25f,
                )
                // c -0.69 -1.82 1.57 -4.23 2.91 -5.25
                curveToRelative(
                    dx1 = -0.69f,
                    dy1 = -1.82f,
                    dx2 = 1.57f,
                    dy2 = -4.23f,
                    dx3 = 2.91f,
                    dy3 = -5.25f,
                )
                // a 8.8 8.8 0 0 1 6.56 -1.62
                arcToRelative(
                    a = 8.8f,
                    b = 8.8f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 6.56f,
                    dy1 = -1.62f,
                )
            }
            // M31.39 101.8 c-1.96 -2.44 -5.17 -3.38 -8.06 -4.54 a23 23 0 0 1 -6.59 -3.78 12.6 12.6 0 0 1 -4.11 -6.27 c2.88 -.15 5.57 -.45 8.2 .74 1.4 .63 2.61 1.6 3.7 2.68 3.2 3.2 5.22 6.93 6.86 11.16 m.02 1.17 c1.34 -2.82 4.24 -4.49 6.79 -6.28 a23 23 0 0 0 5.52 -5.2 c1.5 -2.06 2.5 -4.52 2.55 -7.07 -2.84 .53 -5.52 .87 -7.8 2.63 a13 13 0 0 0 -2.98 3.46 c-2.38 3.87 -3.47 7.96 -4.08 12.45 m7.47 -39.53 .37 -.8 c.83 -1.78 2.94 -6.25 5.02 -10.33 1.05 -2.05 2.08 -4 2.95 -5.47 a23 23 0 0 1 1.16 -1.8 5 5 0 0 1 .43 -.53 1 1 0 0 1 .29 -.23 3 3 0 0 1 .95 -.17 c1.49 0 3.17 1.14 3.78 2.98 a.16 .16 0 1 0 .3 -.1 4.6 4.6 0 0 0 -4.08 -3.2 3 3 0 0 0 -1.07 .2 1.4 1.4 0 0 0 -.46 .36 26 26 0 0 0 -2.56 4.08 c-3.12 5.7 -7.37 14.87 -7.37 14.87 a.16 .16 0 0 0 .29 .14
            path(
                fill = SolidColor(Color(0xFF111528)),
            ) {
                // M 31.39 101.8
                moveTo(x = 31.39f, y = 101.8f)
                // c -1.96 -2.44 -5.17 -3.38 -8.06 -4.54
                curveToRelative(
                    dx1 = -1.96f,
                    dy1 = -2.44f,
                    dx2 = -5.17f,
                    dy2 = -3.38f,
                    dx3 = -8.06f,
                    dy3 = -4.54f,
                )
                // a 23 23 0 0 1 -6.59 -3.78
                arcToRelative(
                    a = 23.0f,
                    b = 23.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -6.59f,
                    dy1 = -3.78f,
                )
                // a 12.6 12.6 0 0 1 -4.11 -6.27
                arcToRelative(
                    a = 12.6f,
                    b = 12.6f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -4.11f,
                    dy1 = -6.27f,
                )
                // c 2.88 -0.15 5.57 -0.45 8.2 0.74
                curveToRelative(
                    dx1 = 2.88f,
                    dy1 = -0.15f,
                    dx2 = 5.57f,
                    dy2 = -0.45f,
                    dx3 = 8.2f,
                    dy3 = 0.74f,
                )
                // c 1.4 0.63 2.61 1.6 3.7 2.68
                curveToRelative(
                    dx1 = 1.4f,
                    dy1 = 0.63f,
                    dx2 = 2.61f,
                    dy2 = 1.6f,
                    dx3 = 3.7f,
                    dy3 = 2.68f,
                )
                // c 3.2 3.2 5.22 6.93 6.86 11.16
                curveToRelative(
                    dx1 = 3.2f,
                    dy1 = 3.2f,
                    dx2 = 5.22f,
                    dy2 = 6.93f,
                    dx3 = 6.86f,
                    dy3 = 11.16f,
                )
                // m 0.02 1.17
                moveToRelative(dx = 0.02f, dy = 1.17f)
                // c 1.34 -2.82 4.24 -4.49 6.79 -6.28
                curveToRelative(
                    dx1 = 1.34f,
                    dy1 = -2.82f,
                    dx2 = 4.24f,
                    dy2 = -4.49f,
                    dx3 = 6.79f,
                    dy3 = -6.28f,
                )
                // a 23 23 0 0 0 5.52 -5.2
                arcToRelative(
                    a = 23.0f,
                    b = 23.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 5.52f,
                    dy1 = -5.2f,
                )
                // c 1.5 -2.06 2.5 -4.52 2.55 -7.07
                curveToRelative(
                    dx1 = 1.5f,
                    dy1 = -2.06f,
                    dx2 = 2.5f,
                    dy2 = -4.52f,
                    dx3 = 2.55f,
                    dy3 = -7.07f,
                )
                // c -2.84 0.53 -5.52 0.87 -7.8 2.63
                curveToRelative(
                    dx1 = -2.84f,
                    dy1 = 0.53f,
                    dx2 = -5.52f,
                    dy2 = 0.87f,
                    dx3 = -7.8f,
                    dy3 = 2.63f,
                )
                // a 13 13 0 0 0 -2.98 3.46
                arcToRelative(
                    a = 13.0f,
                    b = 13.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -2.98f,
                    dy1 = 3.46f,
                )
                // c -2.38 3.87 -3.47 7.96 -4.08 12.45
                curveToRelative(
                    dx1 = -2.38f,
                    dy1 = 3.87f,
                    dx2 = -3.47f,
                    dy2 = 7.96f,
                    dx3 = -4.08f,
                    dy3 = 12.45f,
                )
                // m 7.47 -39.53
                moveToRelative(dx = 7.47f, dy = -39.53f)
                // l 0.37 -0.8
                lineToRelative(dx = 0.37f, dy = -0.8f)
                // c 0.83 -1.78 2.94 -6.25 5.02 -10.33
                curveToRelative(
                    dx1 = 0.83f,
                    dy1 = -1.78f,
                    dx2 = 2.94f,
                    dy2 = -6.25f,
                    dx3 = 5.02f,
                    dy3 = -10.33f,
                )
                // c 1.05 -2.05 2.08 -4 2.95 -5.47
                curveToRelative(
                    dx1 = 1.05f,
                    dy1 = -2.05f,
                    dx2 = 2.08f,
                    dy2 = -4.0f,
                    dx3 = 2.95f,
                    dy3 = -5.47f,
                )
                // a 23 23 0 0 1 1.16 -1.8
                arcToRelative(
                    a = 23.0f,
                    b = 23.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 1.16f,
                    dy1 = -1.8f,
                )
                // a 5 5 0 0 1 0.43 -0.53
                arcToRelative(
                    a = 5.0f,
                    b = 5.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.43f,
                    dy1 = -0.53f,
                )
                // a 1 1 0 0 1 0.29 -0.23
                arcToRelative(
                    a = 1.0f,
                    b = 1.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.29f,
                    dy1 = -0.23f,
                )
                // a 3 3 0 0 1 0.95 -0.17
                arcToRelative(
                    a = 3.0f,
                    b = 3.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.95f,
                    dy1 = -0.17f,
                )
                // c 1.49 0 3.17 1.14 3.78 2.98
                curveToRelative(
                    dx1 = 1.49f,
                    dy1 = 0.0f,
                    dx2 = 3.17f,
                    dy2 = 1.14f,
                    dx3 = 3.78f,
                    dy3 = 2.98f,
                )
                // a 0.16 0.16 0 1 0 0.3 -0.1
                arcToRelative(
                    a = 0.16f,
                    b = 0.16f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = 0.3f,
                    dy1 = -0.1f,
                )
                // a 4.6 4.6 0 0 0 -4.08 -3.2
                arcToRelative(
                    a = 4.6f,
                    b = 4.6f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -4.08f,
                    dy1 = -3.2f,
                )
                // a 3 3 0 0 0 -1.07 0.2
                arcToRelative(
                    a = 3.0f,
                    b = 3.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -1.07f,
                    dy1 = 0.2f,
                )
                // a 1.4 1.4 0 0 0 -0.46 0.36
                arcToRelative(
                    a = 1.4f,
                    b = 1.4f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -0.46f,
                    dy1 = 0.36f,
                )
                // a 26 26 0 0 0 -2.56 4.08
                arcToRelative(
                    a = 26.0f,
                    b = 26.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -2.56f,
                    dy1 = 4.08f,
                )
                // c -3.12 5.7 -7.37 14.87 -7.37 14.87
                curveToRelative(
                    dx1 = -3.12f,
                    dy1 = 5.7f,
                    dx2 = -7.37f,
                    dy2 = 14.87f,
                    dx3 = -7.37f,
                    dy3 = 14.87f,
                )
                // a 0.16 0.16 0 0 0 0.29 0.14
                arcToRelative(
                    a = 0.16f,
                    b = 0.16f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.29f,
                    dy1 = 0.14f,
                )
            }
            // m97.7 40.62 -.46 -7.64 -2.1 7.28 -6.68 -.58 5.48 3 -3.1 7.22 4.99 -5.56 2.56 7.75 -.14 -8.85 8.18 -.93z m-72.15 2.52 -.46 -7.64 -2.1 7.28 -6.69 -.58 5.5 3 -3.12 7.22 5 -5.56 2.56 7.75 -.14 -8.84 8.18 -.94z M59.4 96 l-.46 -7.64 -2.11 7.28 -6.68 -.58 5.49 3 -3.11 7.22 5 -5.56 2.56 7.75 -.14 -8.84 8.17 -.94z
            path(
                fill = SolidColor(Color(0xFFFBBF4C)),
            ) {
                // M 97.7 40.62
                moveTo(x = 97.7f, y = 40.62f)
                // l -0.46 -7.64
                lineToRelative(dx = -0.46f, dy = -7.64f)
                // l -2.1 7.28
                lineToRelative(dx = -2.1f, dy = 7.28f)
                // l -6.68 -0.58
                lineToRelative(dx = -6.68f, dy = -0.58f)
                // l 5.48 3
                lineToRelative(dx = 5.48f, dy = 3.0f)
                // l -3.1 7.22
                lineToRelative(dx = -3.1f, dy = 7.22f)
                // l 4.99 -5.56
                lineToRelative(dx = 4.99f, dy = -5.56f)
                // l 2.56 7.75
                lineToRelative(dx = 2.56f, dy = 7.75f)
                // l -0.14 -8.85
                lineToRelative(dx = -0.14f, dy = -8.85f)
                // l 8.18 -0.93z
                lineToRelative(dx = 8.18f, dy = -0.93f)
                close()
                // m -72.15 2.52
                moveToRelative(dx = -72.15f, dy = 2.52f)
                // l -0.46 -7.64
                lineToRelative(dx = -0.46f, dy = -7.64f)
                // l -2.1 7.28
                lineToRelative(dx = -2.1f, dy = 7.28f)
                // l -6.69 -0.58
                lineToRelative(dx = -6.69f, dy = -0.58f)
                // l 5.5 3
                lineToRelative(dx = 5.5f, dy = 3.0f)
                // l -3.12 7.22
                lineToRelative(dx = -3.12f, dy = 7.22f)
                // l 5 -5.56
                lineToRelative(dx = 5.0f, dy = -5.56f)
                // l 2.56 7.75
                lineToRelative(dx = 2.56f, dy = 7.75f)
                // l -0.14 -8.84
                lineToRelative(dx = -0.14f, dy = -8.84f)
                // l 8.18 -0.94z
                lineToRelative(dx = 8.18f, dy = -0.94f)
                close()
                // M 59.4 96
                moveTo(x = 59.4f, y = 96.0f)
                // l -0.46 -7.64
                lineToRelative(dx = -0.46f, dy = -7.64f)
                // l -2.11 7.28
                lineToRelative(dx = -2.11f, dy = 7.28f)
                // l -6.68 -0.58
                lineToRelative(dx = -6.68f, dy = -0.58f)
                // l 5.49 3
                lineToRelative(dx = 5.49f, dy = 3.0f)
                // l -3.11 7.22
                lineToRelative(dx = -3.11f, dy = 7.22f)
                // l 5 -5.56
                lineToRelative(dx = 5.0f, dy = -5.56f)
                // l 2.56 7.75
                lineToRelative(dx = 2.56f, dy = 7.75f)
                // l -0.14 -8.84
                lineToRelative(dx = -0.14f, dy = -8.84f)
                // l 8.17 -0.94z
                lineToRelative(dx = 8.17f, dy = -0.94f)
                close()
            }
            // M64.75 47.46 s3.85 -.64 4.34 -1.67 c.5 -1.04 .4 -5.04 .4 -5.04 s1.13 3.5 1.92 4.4 c.92 1.03 4.59 .98 4.59 .98 s-3.3 .62 -3.95 1.78 c-.44 .79 -.89 3.85 -.74 5.03 0 0 -1.04 -4.34 -2.12 -4.78 -1.09 -.45 -4.44 -.7 -4.44 -.7 m25.8 30.1 s3.84 .6 4.64 -.22 c.8 -.83 1.97 -4.65 1.97 -4.65 s-.03 3.68 .43 4.78 c.55 1.27 4.04 2.39 4.04 2.39 s-3.32 -.46 -4.3 .43 c-.68 .6 -2.07 3.37 -2.3 4.54 0 0 .4 -4.45 -.5 -5.21 -.88 -.77 -3.99 -2.07 -3.99 -2.07 m-75.46 4.46 s3.86 .6 4.65 -.22 1.97 -4.65 1.97 -4.65 -.03 3.69 .44 4.78 c.54 1.28 4.04 2.39 4.04 2.39 s-3.33 -.46 -4.31 .43 c-.67 .61 -2.06 3.37 -2.3 4.54 0 0 .4 -4.45 -.5 -5.21 -.88 -.77 -3.99 -2.06 -3.99 -2.06
            path(
                fill = SolidColor(Color(0xFFCCB0AB)),
            ) {
                // M 64.75 47.46
                moveTo(x = 64.75f, y = 47.46f)
                // s 3.85 -0.64 4.34 -1.67
                reflectiveCurveToRelative(
                    dx1 = 3.85f,
                    dy1 = -0.64f,
                    dx2 = 4.34f,
                    dy2 = -1.67f,
                )
                // c 0.5 -1.04 0.4 -5.04 0.4 -5.04
                curveToRelative(
                    dx1 = 0.5f,
                    dy1 = -1.04f,
                    dx2 = 0.4f,
                    dy2 = -5.04f,
                    dx3 = 0.4f,
                    dy3 = -5.04f,
                )
                // s 1.13 3.5 1.92 4.4
                reflectiveCurveToRelative(
                    dx1 = 1.13f,
                    dy1 = 3.5f,
                    dx2 = 1.92f,
                    dy2 = 4.4f,
                )
                // c 0.92 1.03 4.59 0.98 4.59 0.98
                curveToRelative(
                    dx1 = 0.92f,
                    dy1 = 1.03f,
                    dx2 = 4.59f,
                    dy2 = 0.98f,
                    dx3 = 4.59f,
                    dy3 = 0.98f,
                )
                // s -3.3 0.62 -3.95 1.78
                reflectiveCurveToRelative(
                    dx1 = -3.3f,
                    dy1 = 0.62f,
                    dx2 = -3.95f,
                    dy2 = 1.78f,
                )
                // c -0.44 0.79 -0.89 3.85 -0.74 5.03
                curveToRelative(
                    dx1 = -0.44f,
                    dy1 = 0.79f,
                    dx2 = -0.89f,
                    dy2 = 3.85f,
                    dx3 = -0.74f,
                    dy3 = 5.03f,
                )
                // c 0 0 -1.04 -4.34 -2.12 -4.78
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = 0.0f,
                    dx2 = -1.04f,
                    dy2 = -4.34f,
                    dx3 = -2.12f,
                    dy3 = -4.78f,
                )
                // c -1.09 -0.45 -4.44 -0.7 -4.44 -0.7
                curveToRelative(
                    dx1 = -1.09f,
                    dy1 = -0.45f,
                    dx2 = -4.44f,
                    dy2 = -0.7f,
                    dx3 = -4.44f,
                    dy3 = -0.7f,
                )
                // m 25.8 30.1
                moveToRelative(dx = 25.8f, dy = 30.1f)
                // s 3.84 0.6 4.64 -0.22
                reflectiveCurveToRelative(
                    dx1 = 3.84f,
                    dy1 = 0.6f,
                    dx2 = 4.64f,
                    dy2 = -0.22f,
                )
                // c 0.8 -0.83 1.97 -4.65 1.97 -4.65
                curveToRelative(
                    dx1 = 0.8f,
                    dy1 = -0.83f,
                    dx2 = 1.97f,
                    dy2 = -4.65f,
                    dx3 = 1.97f,
                    dy3 = -4.65f,
                )
                // s -0.03 3.68 0.43 4.78
                reflectiveCurveToRelative(
                    dx1 = -0.03f,
                    dy1 = 3.68f,
                    dx2 = 0.43f,
                    dy2 = 4.78f,
                )
                // c 0.55 1.27 4.04 2.39 4.04 2.39
                curveToRelative(
                    dx1 = 0.55f,
                    dy1 = 1.27f,
                    dx2 = 4.04f,
                    dy2 = 2.39f,
                    dx3 = 4.04f,
                    dy3 = 2.39f,
                )
                // s -3.32 -0.46 -4.3 0.43
                reflectiveCurveToRelative(
                    dx1 = -3.32f,
                    dy1 = -0.46f,
                    dx2 = -4.3f,
                    dy2 = 0.43f,
                )
                // c -0.68 0.6 -2.07 3.37 -2.3 4.54
                curveToRelative(
                    dx1 = -0.68f,
                    dy1 = 0.6f,
                    dx2 = -2.07f,
                    dy2 = 3.37f,
                    dx3 = -2.3f,
                    dy3 = 4.54f,
                )
                // c 0 0 0.4 -4.45 -0.5 -5.21
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = 0.0f,
                    dx2 = 0.4f,
                    dy2 = -4.45f,
                    dx3 = -0.5f,
                    dy3 = -5.21f,
                )
                // c -0.88 -0.77 -3.99 -2.07 -3.99 -2.07
                curveToRelative(
                    dx1 = -0.88f,
                    dy1 = -0.77f,
                    dx2 = -3.99f,
                    dy2 = -2.07f,
                    dx3 = -3.99f,
                    dy3 = -2.07f,
                )
                // m -75.46 4.46
                moveToRelative(dx = -75.46f, dy = 4.46f)
                // s 3.86 0.6 4.65 -0.22
                reflectiveCurveToRelative(
                    dx1 = 3.86f,
                    dy1 = 0.6f,
                    dx2 = 4.65f,
                    dy2 = -0.22f,
                )
                // s 1.97 -4.65 1.97 -4.65
                reflectiveCurveToRelative(
                    dx1 = 1.97f,
                    dy1 = -4.65f,
                    dx2 = 1.97f,
                    dy2 = -4.65f,
                )
                // s -0.03 3.69 0.44 4.78
                reflectiveCurveToRelative(
                    dx1 = -0.03f,
                    dy1 = 3.69f,
                    dx2 = 0.44f,
                    dy2 = 4.78f,
                )
                // c 0.54 1.28 4.04 2.39 4.04 2.39
                curveToRelative(
                    dx1 = 0.54f,
                    dy1 = 1.28f,
                    dx2 = 4.04f,
                    dy2 = 2.39f,
                    dx3 = 4.04f,
                    dy3 = 2.39f,
                )
                // s -3.33 -0.46 -4.31 0.43
                reflectiveCurveToRelative(
                    dx1 = -3.33f,
                    dy1 = -0.46f,
                    dx2 = -4.31f,
                    dy2 = 0.43f,
                )
                // c -0.67 0.61 -2.06 3.37 -2.3 4.54
                curveToRelative(
                    dx1 = -0.67f,
                    dy1 = 0.61f,
                    dx2 = -2.06f,
                    dy2 = 3.37f,
                    dx3 = -2.3f,
                    dy3 = 4.54f,
                )
                // c 0 0 0.4 -4.45 -0.5 -5.21
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = 0.0f,
                    dx2 = 0.4f,
                    dy2 = -4.45f,
                    dx3 = -0.5f,
                    dy3 = -5.21f,
                )
                // c -0.88 -0.77 -3.99 -2.06 -3.99 -2.06
                curveToRelative(
                    dx1 = -0.88f,
                    dy1 = -0.77f,
                    dx2 = -3.99f,
                    dy2 = -2.06f,
                    dx3 = -3.99f,
                    dy3 = -2.06f,
                )
            }
        }.build().also { _illustrationSvgOptimized = it }
    }

@Preview
@Composable
private fun IconPreview() {
    dev.tonholo.sampleApp.ui.theme.SampleAppTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                imageVector = IllustrationSvgOptimized,
                contentDescription = null,
                modifier = Modifier
                    .width((116.0).dp)
                    .height((114.0).dp),
            )
        }
    }
}

@Suppress("ObjectPropertyName")
private var _illustrationSvgOptimized: ImageVector? = null
