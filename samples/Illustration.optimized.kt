package dev.tonholo.composeicons.ui.icon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
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

val IllustrationOptimized: ImageVector
    get() {
        val current = _illustrationOptimized
        if (current != null) return current

        return ImageVector.Builder(
            name = "dev.tonholo.composeicons.ui.theme.ComposeIconsTheme.IllustrationOptimized",
            defaultWidth = 116.0.dp,
            defaultHeight = 114.0.dp,
            viewportWidth = 116.0f,
            viewportHeight = 114.0f,
        ).apply {
            // M56.78 113.12 c-3.01 0 -6.06 -0.25 -9.06 -0.74 l-0.48 -0.07 -0.18 -0.04 a56.14 56.14 0 0 1 -27.15 -12.9 24.3 24.3 0 0 1 -5.7 -3.56 15.86 15.86 0 0 1 -5.2 -8.02 3.42 3.42 0 0 1 -0.07 -1.35 A55.4 55.4 0 0 1 8 84.84 c-2.72 -1.15 -4.4 -4.23 -3.94 -7.43 a7.8 7.8 0 0 1 0.13 -0.66 53.45 53.45 0 0 1 -1.94 -6.21 6.27 6.27 0 0 1 -1.39 -8.03 56.28 56.28 0 0 1 0.58 -15.2 A56 56 0 0 1 66.4 1.73 a56.06 56.06 0 0 1 46.36 52.73 c2.8 2 3.85 5.64 2.45 8.5 a6.64 6.64 0 0 1 -3.12 3.02 l-0.12 0.7 a55.56 55.56 0 0 1 -0.69 3.33 8.37 8.37 0 0 1 0.5 1.87 7.03 7.03 0 0 1 -3.49 7.22 55.44 55.44 0 0 1 -5.2 9.49 52.76 52.76 0 0 1 2.47 0.8 l0.76 0.27 a3.42 3.42 0 0 1 1.84 4.96 15.85 15.85 0 0 1 -7.21 6.28 26.5 26.5 0 0 1 -8.45 2.11 98.96 98.96 0 0 1 -1.4 0.13 37.99 37.99 0 0 0 -2.97 0.34 50.1 50.1 0 0 1 -0.95 0.63 l-0.5 0.32 -0.07 0.04 a54.25 54.25 0 0 1 -1.42 0.87 h-0.02 a19.15 19.15 0 0 1 -0.67 0.4 l-0.15 0.09 a57.34 57.34 0 0 1 -2.08 1.11 l-0.17 0.09 a53.86 53.86 0 0 1 -1.26 0.62 l-0.65 0.3 a56.54 56.54 0 0 1 -1.31 0.6 l-0.59 0.24 a57.08 57.08 0 0 1 -4.72 1.73 55.31 55.31 0 0 1 -16.78 2.6z
            path(
                fill = SolidColor(Color(0xFFCCB0AB)),
            ) {
                // M 56.78 113.12
                moveTo(x = 56.78f, y = 113.12f)
                // c -3.01 0.0 -6.06 -0.25 -9.06 -0.74
                curveToRelative(
                    dx1 = -3.01f,
                    dy1 = 0.0f,
                    dx2 = -6.06f,
                    dy2 = -0.25f,
                    dx3 = -9.06f,
                    dy3 = -0.74f,
                )
                // l -0.48 -0.07
                lineToRelative(dx = -0.48f, dy = -0.07f)
                // l -0.18 -0.04
                lineToRelative(dx = -0.18f, dy = -0.04f)
                // a 56.14 56.14 0.0 0 1 -27.15 -12.9
                arcToRelative(
                    a = 56.14f,
                    b = 56.14f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -27.15f,
                    dy1 = -12.9f,
                )
                // a 24.3 24.3 0.0 0 1 -5.7 -3.56
                arcToRelative(
                    a = 24.3f,
                    b = 24.3f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -5.7f,
                    dy1 = -3.56f,
                )
                // a 15.86 15.86 0.0 0 1 -5.2 -8.02
                arcToRelative(
                    a = 15.86f,
                    b = 15.86f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -5.2f,
                    dy1 = -8.02f,
                )
                // a 3.42 3.42 0.0 0 1 -0.07 -1.35
                arcToRelative(
                    a = 3.42f,
                    b = 3.42f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.07f,
                    dy1 = -1.35f,
                )
                // A 55.4 55.4 0.0 0 1 8.0 84.84
                arcTo(
                    horizontalEllipseRadius = 55.4f,
                    verticalEllipseRadius = 55.4f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 8.0f,
                    y1 = 84.84f,
                )
                // c -2.72 -1.15 -4.4 -4.23 -3.94 -7.43
                curveToRelative(
                    dx1 = -2.72f,
                    dy1 = -1.15f,
                    dx2 = -4.4f,
                    dy2 = -4.23f,
                    dx3 = -3.94f,
                    dy3 = -7.43f,
                )
                // a 7.8 7.8 0.0 0 1 0.13 -0.66
                arcToRelative(
                    a = 7.8f,
                    b = 7.8f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.13f,
                    dy1 = -0.66f,
                )
                // a 53.45 53.45 0.0 0 1 -1.94 -6.21
                arcToRelative(
                    a = 53.45f,
                    b = 53.45f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.94f,
                    dy1 = -6.21f,
                )
                // a 6.27 6.27 0.0 0 1 -1.39 -8.03
                arcToRelative(
                    a = 6.27f,
                    b = 6.27f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.39f,
                    dy1 = -8.03f,
                )
                // a 56.28 56.28 0.0 0 1 0.58 -15.2
                arcToRelative(
                    a = 56.28f,
                    b = 56.28f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.58f,
                    dy1 = -15.2f,
                )
                // A 56.0 56.0 0.0 0 1 66.4 1.73
                arcTo(
                    horizontalEllipseRadius = 56.0f,
                    verticalEllipseRadius = 56.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 66.4f,
                    y1 = 1.73f,
                )
                // a 56.06 56.06 0.0 0 1 46.36 52.73
                arcToRelative(
                    a = 56.06f,
                    b = 56.06f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 46.36f,
                    dy1 = 52.73f,
                )
                // c 2.8 2.0 3.85 5.64 2.45 8.5
                curveToRelative(
                    dx1 = 2.8f,
                    dy1 = 2.0f,
                    dx2 = 3.85f,
                    dy2 = 5.64f,
                    dx3 = 2.45f,
                    dy3 = 8.5f,
                )
                // a 6.64 6.64 0.0 0 1 -3.12 3.02
                arcToRelative(
                    a = 6.64f,
                    b = 6.64f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -3.12f,
                    dy1 = 3.02f,
                )
                // l -0.12 0.7
                lineToRelative(dx = -0.12f, dy = 0.7f)
                // a 55.56 55.56 0.0 0 1 -0.69 3.33
                arcToRelative(
                    a = 55.56f,
                    b = 55.56f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.69f,
                    dy1 = 3.33f,
                )
                // a 8.37 8.37 0.0 0 1 0.5 1.87
                arcToRelative(
                    a = 8.37f,
                    b = 8.37f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.5f,
                    dy1 = 1.87f,
                )
                // a 7.03 7.03 0.0 0 1 -3.49 7.22
                arcToRelative(
                    a = 7.03f,
                    b = 7.03f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -3.49f,
                    dy1 = 7.22f,
                )
                // a 55.44 55.44 0.0 0 1 -5.2 9.49
                arcToRelative(
                    a = 55.44f,
                    b = 55.44f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -5.2f,
                    dy1 = 9.49f,
                )
                // a 52.76 52.76 0.0 0 1 2.47 0.8
                arcToRelative(
                    a = 52.76f,
                    b = 52.76f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 2.47f,
                    dy1 = 0.8f,
                )
                // l 0.76 0.27
                lineToRelative(dx = 0.76f, dy = 0.27f)
                // a 3.42 3.42 0.0 0 1 1.84 4.96
                arcToRelative(
                    a = 3.42f,
                    b = 3.42f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 1.84f,
                    dy1 = 4.96f,
                )
                // a 15.85 15.85 0.0 0 1 -7.21 6.28
                arcToRelative(
                    a = 15.85f,
                    b = 15.85f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -7.21f,
                    dy1 = 6.28f,
                )
                // a 26.5 26.5 0.0 0 1 -8.45 2.11
                arcToRelative(
                    a = 26.5f,
                    b = 26.5f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -8.45f,
                    dy1 = 2.11f,
                )
                // a 98.96 98.96 0.0 0 1 -1.4 0.13
                arcToRelative(
                    a = 98.96f,
                    b = 98.96f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.4f,
                    dy1 = 0.13f,
                )
                // a 37.99 37.99 0.0 0 0 -2.97 0.34
                arcToRelative(
                    a = 37.99f,
                    b = 37.99f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -2.97f,
                    dy1 = 0.34f,
                )
                // a 50.1 50.1 0.0 0 1 -0.95 0.63
                arcToRelative(
                    a = 50.1f,
                    b = 50.1f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.95f,
                    dy1 = 0.63f,
                )
                // l -0.5 0.32
                lineToRelative(dx = -0.5f, dy = 0.32f)
                // l -0.07 0.04
                lineToRelative(dx = -0.07f, dy = 0.04f)
                // a 54.25 54.25 0.0 0 1 -1.42 0.87
                arcToRelative(
                    a = 54.25f,
                    b = 54.25f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.42f,
                    dy1 = 0.87f,
                )
                // h -0.02
                horizontalLineToRelative(dx = -0.02f)
                // a 19.15 19.15 0.0 0 1 -0.67 0.4
                arcToRelative(
                    a = 19.15f,
                    b = 19.15f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.67f,
                    dy1 = 0.4f,
                )
                // l -0.15 0.09
                lineToRelative(dx = -0.15f, dy = 0.09f)
                // a 57.34 57.34 0.0 0 1 -2.08 1.11
                arcToRelative(
                    a = 57.34f,
                    b = 57.34f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -2.08f,
                    dy1 = 1.11f,
                )
                // l -0.17 0.09
                lineToRelative(dx = -0.17f, dy = 0.09f)
                // a 53.86 53.86 0.0 0 1 -1.26 0.62
                arcToRelative(
                    a = 53.86f,
                    b = 53.86f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.26f,
                    dy1 = 0.62f,
                )
                // l -0.65 0.3
                lineToRelative(dx = -0.65f, dy = 0.3f)
                // a 56.54 56.54 0.0 0 1 -1.31 0.6
                arcToRelative(
                    a = 56.54f,
                    b = 56.54f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.31f,
                    dy1 = 0.6f,
                )
                // l -0.59 0.24
                lineToRelative(dx = -0.59f, dy = 0.24f)
                // a 57.08 57.08 0.0 0 1 -4.72 1.73
                arcToRelative(
                    a = 57.08f,
                    b = 57.08f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -4.72f,
                    dy1 = 1.73f,
                )
                // a 55.31 55.31 0.0 0 1 -16.78 2.6
                arcToRelative(
                    a = 55.31f,
                    b = 55.31f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -16.78f,
                    dy1 = 2.6f,
                )
                close()
            }
            // M109.7 56.82 a52.08 52.08 0 0 1 -0.8 9.55 51.73 51.73 0 0 1 -2.88 10.26 52.15 52.15 0 0 1 -9.13 15.09 52.65 52.65 0 0 1 -9.8 8.8 48.85 48.85 0 0 1 -3.97 2.52 53.06 53.06 0 0 1 -10.26 4.48 52.12 52.12 0 0 1 -24.33 1.75 l-0.63 -0.1 a52.48 52.48 0 0 1 -25.8 -12.44 h-0.02 A52.7 52.7 0 0 1 8.1 76.88 a52.6 52.6 0 0 1 -2.54 -8.17 A52.7 52.7 0 0 1 5.1 48.17 a52.7 52.7 0 0 1 104.6 8.65z
            path(
                fill = SolidColor(Color(0xFFFFCE86)),
            ) {
                // M 109.7 56.82
                moveTo(x = 109.7f, y = 56.82f)
                // a 52.08 52.08 0.0 0 1 -0.8 9.55
                arcToRelative(
                    a = 52.08f,
                    b = 52.08f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.8f,
                    dy1 = 9.55f,
                )
                // a 51.73 51.73 0.0 0 1 -2.88 10.26
                arcToRelative(
                    a = 51.73f,
                    b = 51.73f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -2.88f,
                    dy1 = 10.26f,
                )
                // a 52.15 52.15 0.0 0 1 -9.13 15.09
                arcToRelative(
                    a = 52.15f,
                    b = 52.15f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -9.13f,
                    dy1 = 15.09f,
                )
                // a 52.65 52.65 0.0 0 1 -9.8 8.8
                arcToRelative(
                    a = 52.65f,
                    b = 52.65f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -9.8f,
                    dy1 = 8.8f,
                )
                // a 48.85 48.85 0.0 0 1 -3.97 2.52
                arcToRelative(
                    a = 48.85f,
                    b = 48.85f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -3.97f,
                    dy1 = 2.52f,
                )
                // a 53.06 53.06 0.0 0 1 -10.26 4.48
                arcToRelative(
                    a = 53.06f,
                    b = 53.06f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -10.26f,
                    dy1 = 4.48f,
                )
                // a 52.12 52.12 0.0 0 1 -24.33 1.75
                arcToRelative(
                    a = 52.12f,
                    b = 52.12f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -24.33f,
                    dy1 = 1.75f,
                )
                // l -0.63 -0.1
                lineToRelative(dx = -0.63f, dy = -0.1f)
                // a 52.48 52.48 0.0 0 1 -25.8 -12.44
                arcToRelative(
                    a = 52.48f,
                    b = 52.48f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -25.8f,
                    dy1 = -12.44f,
                )
                // h -0.02
                horizontalLineToRelative(dx = -0.02f)
                // A 52.7 52.7 0.0 0 1 8.1 76.88
                arcTo(
                    horizontalEllipseRadius = 52.7f,
                    verticalEllipseRadius = 52.7f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 8.1f,
                    y1 = 76.88f,
                )
                // a 52.6 52.6 0.0 0 1 -2.54 -8.17
                arcToRelative(
                    a = 52.6f,
                    b = 52.6f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -2.54f,
                    dy1 = -8.17f,
                )
                // A 52.7 52.7 0.0 0 1 5.1 48.17
                arcTo(
                    horizontalEllipseRadius = 52.7f,
                    verticalEllipseRadius = 52.7f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 5.1f,
                    y1 = 48.17f,
                )
                // a 52.7 52.7 0.0 0 1 104.6 8.65
                arcToRelative(
                    a = 52.7f,
                    b = 52.7f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 104.6f,
                    dy1 = 8.65f,
                )
                close()
            }
            // M39.05 62.75 s6.84 -13.74 7.61 -15.4 c0.78 -1.64 1.66 -3.24 3.02 -3.34 1.36 -0.1 2.62 0.4 3.2 1.27 0.58 0.88 1.2 1.76 1.2 1.76 L39.05 62.75z
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
                // c 1.36 -0.1 2.62 0.4 3.2 1.27
                curveToRelative(
                    dx1 = 1.36f,
                    dy1 = -0.1f,
                    dx2 = 2.62f,
                    dy2 = 0.4f,
                    dx3 = 3.2f,
                    dy3 = 1.27f,
                )
                // c 0.58 0.88 1.2 1.76 1.2 1.76
                curveToRelative(
                    dx1 = 0.58f,
                    dy1 = 0.88f,
                    dx2 = 1.2f,
                    dy2 = 1.76f,
                    dx3 = 1.2f,
                    dy3 = 1.76f,
                )
                // L 39.05 62.75
                lineTo(x = 39.05f, y = 62.75f)
                close()
            }
            // M87.28 100.4 a52.83 52.83 0 0 1 -14.42 7.12 52.12 52.12 0 0 1 -24.33 1.75 c-4.44 -3.73 -8.9 -8.33 -9.44 -12.18 a200.81 200.81 0 0 1 -1.57 -14.37 c-0.36 -5.16 -0.39 -10 0.43 -12.13 v-0.02 a2.46 2.46 0 0 1 0.5 -0.82 l0.02 -0.03 a1.02 1.02 0 0 1 0.19 -0.14 l-0.01 -0.06 a9.83 9.83 0 0 1 -0.15 -0.68 v-0.03 a16.45 16.45 0 0 1 -0.36 -2.92 l0.01 -0.25 a4.16 4.16 0 0 1 0.28 -1.61 l0.02 -0.04 a16.27 16.27 0 0 1 1.14 -1.8 76.22 76.22 0 0 1 1.93 -2.65 l0.14 -0.19 a94.13 94.13 0 0 1 4.8 -5.8 c0.64 -0.72 1.29 -1.4 1.93 -2.06 2 -2 3.92 -3.6 5.43 -4.17 a2.7 2.7 0 0 1 1.4 -0.23 0.88 0.88 0 0 1 0.2 0.05 2.17 2.17 0 0 1 1.11 0.88 l0.02 0.02 a3.3 3.3 0 0 1 0.47 1.67 c0.08 1.65 -0.58 3.8 -1.49 5.83 l-0.25 0.53 a20.93 20.93 0 0 1 -3.3 5.13 l-0.12 0.11 c-2.1 2.17 -2.23 4.04 -2.24 4.27 v0.02 s2 -0.48 4.73 -1.02 l0.11 -0.02 c1.48 -0.3 3.17 -0.6 4.87 -0.86 2.85 -0.44 5.72 -0.73 7.7 -0.57 a8.32 8.32 0 0 1 7.1 5.37 l13.15 31.9z
            path(
                fill = SolidColor(Color(0xFF7E5C63)),
            ) {
                // M 87.28 100.4
                moveTo(x = 87.28f, y = 100.4f)
                // a 52.83 52.83 0.0 0 1 -14.42 7.12
                arcToRelative(
                    a = 52.83f,
                    b = 52.83f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -14.42f,
                    dy1 = 7.12f,
                )
                // a 52.12 52.12 0.0 0 1 -24.33 1.75
                arcToRelative(
                    a = 52.12f,
                    b = 52.12f,
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
                // a 200.81 200.81 0.0 0 1 -1.57 -14.37
                arcToRelative(
                    a = 200.81f,
                    b = 200.81f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.57f,
                    dy1 = -14.37f,
                )
                // c -0.36 -5.16 -0.39 -10.0 0.43 -12.13
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
                // a 2.46 2.46 0.0 0 1 0.5 -0.82
                arcToRelative(
                    a = 2.46f,
                    b = 2.46f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.5f,
                    dy1 = -0.82f,
                )
                // l 0.02 -0.03
                lineToRelative(dx = 0.02f, dy = -0.03f)
                // a 1.02 1.02 0.0 0 1 0.19 -0.14
                arcToRelative(
                    a = 1.02f,
                    b = 1.02f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.19f,
                    dy1 = -0.14f,
                )
                // l -0.01 -0.06
                lineToRelative(dx = -0.01f, dy = -0.06f)
                // a 9.83 9.83 0.0 0 1 -0.15 -0.68
                arcToRelative(
                    a = 9.83f,
                    b = 9.83f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.15f,
                    dy1 = -0.68f,
                )
                // v -0.03
                verticalLineToRelative(dy = -0.03f)
                // a 16.45 16.45 0.0 0 1 -0.36 -2.92
                arcToRelative(
                    a = 16.45f,
                    b = 16.45f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.36f,
                    dy1 = -2.92f,
                )
                // l 0.01 -0.25
                lineToRelative(dx = 0.01f, dy = -0.25f)
                // a 4.16 4.16 0.0 0 1 0.28 -1.61
                arcToRelative(
                    a = 4.16f,
                    b = 4.16f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.28f,
                    dy1 = -1.61f,
                )
                // l 0.02 -0.04
                lineToRelative(dx = 0.02f, dy = -0.04f)
                // a 16.27 16.27 0.0 0 1 1.14 -1.8
                arcToRelative(
                    a = 16.27f,
                    b = 16.27f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 1.14f,
                    dy1 = -1.8f,
                )
                // a 76.22 76.22 0.0 0 1 1.93 -2.65
                arcToRelative(
                    a = 76.22f,
                    b = 76.22f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 1.93f,
                    dy1 = -2.65f,
                )
                // l 0.14 -0.19
                lineToRelative(dx = 0.14f, dy = -0.19f)
                // a 94.13 94.13 0.0 0 1 4.8 -5.8
                arcToRelative(
                    a = 94.13f,
                    b = 94.13f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 4.8f,
                    dy1 = -5.8f,
                )
                // c 0.64 -0.72 1.29 -1.4 1.93 -2.06
                curveToRelative(
                    dx1 = 0.64f,
                    dy1 = -0.72f,
                    dx2 = 1.29f,
                    dy2 = -1.4f,
                    dx3 = 1.93f,
                    dy3 = -2.06f,
                )
                // c 2.0 -2.0 3.92 -3.6 5.43 -4.17
                curveToRelative(
                    dx1 = 2.0f,
                    dy1 = -2.0f,
                    dx2 = 3.92f,
                    dy2 = -3.6f,
                    dx3 = 5.43f,
                    dy3 = -4.17f,
                )
                // a 2.7 2.7 0.0 0 1 1.4 -0.23
                arcToRelative(
                    a = 2.7f,
                    b = 2.7f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 1.4f,
                    dy1 = -0.23f,
                )
                // a 0.88 0.88 0.0 0 1 0.2 0.05
                arcToRelative(
                    a = 0.88f,
                    b = 0.88f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.2f,
                    dy1 = 0.05f,
                )
                // a 2.17 2.17 0.0 0 1 1.11 0.88
                arcToRelative(
                    a = 2.17f,
                    b = 2.17f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 1.11f,
                    dy1 = 0.88f,
                )
                // l 0.02 0.02
                lineToRelative(dx = 0.02f, dy = 0.02f)
                // a 3.3 3.3 0.0 0 1 0.47 1.67
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
                // a 20.93 20.93 0.0 0 1 -3.3 5.13
                arcToRelative(
                    a = 20.93f,
                    b = 20.93f,
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
                // s 2.0 -0.48 4.73 -1.02
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
                // a 8.32 8.32 0.0 0 1 7.1 5.37
                arcToRelative(
                    a = 8.32f,
                    b = 8.32f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 7.1f,
                    dy1 = 5.37f,
                )
                // l 13.15 31.9
                lineToRelative(dx = 13.15f, dy = 31.9f)
                close()
            }
            // M55.47 48.66 a3.44 3.44 0 0 1 -0.78 0.86 36.32 36.32 0 0 1 -3.02 2.36 c-0.86 0.6 -1.74 1.1 -2.73 0.4 a1.2 1.2 0 0 1 -0.55 -0.79 c2.67 -2.68 5.24 -4.65 6.82 -4.4 a1.07 1.07 0 0 1 0.4 0.46 1.29 1.29 0 0 1 -0.14 1.11z
            path(
                fill = SolidColor(Color(0xFFFF91B8)),
            ) {
                // M 55.47 48.66
                moveTo(x = 55.47f, y = 48.66f)
                // a 3.44 3.44 0.0 0 1 -0.78 0.86
                arcToRelative(
                    a = 3.44f,
                    b = 3.44f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.78f,
                    dy1 = 0.86f,
                )
                // a 36.32 36.32 0.0 0 1 -3.02 2.36
                arcToRelative(
                    a = 36.32f,
                    b = 36.32f,
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
                // a 1.2 1.2 0.0 0 1 -0.55 -0.79
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
                // a 1.07 1.07 0.0 0 1 0.4 0.46
                arcToRelative(
                    a = 1.07f,
                    b = 1.07f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.4f,
                    dy1 = 0.46f,
                )
                // a 1.29 1.29 0.0 0 1 -0.14 1.11
                arcToRelative(
                    a = 1.29f,
                    b = 1.29f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.14f,
                    dy1 = 1.11f,
                )
                close()
            }
            // M75.52 72.16 l-12.04 6.33 -3 -13.21 s-5.06 -6.45 -5.06 -10.97 c0 -4.51 2.9 -5.9 4.51 -5.9 a0.9 0.9 0 0 1 0.26 0.03 c1.52 0.45 3.02 4.77 4.04 6.2 a9.31 9.31 0 0 0 0.32 0.4 c1.42 1.64 5.1 4.68 6.77 7.55 1.83 3.12 4.2 9.57 4.2 9.57z
            path(
                fill = SolidColor(Color(0xFF7E5C63)),
            ) {
                // M 75.52 72.16
                moveTo(x = 75.52f, y = 72.16f)
                // l -12.04 6.33
                lineToRelative(dx = -12.04f, dy = 6.33f)
                // l -3.0 -13.21
                lineToRelative(dx = -3.0f, dy = -13.21f)
                // s -5.06 -6.45 -5.06 -10.97
                reflectiveCurveToRelative(
                    dx1 = -5.06f,
                    dy1 = -6.45f,
                    dx2 = -5.06f,
                    dy2 = -10.97f,
                )
                // c 0.0 -4.51 2.9 -5.9 4.51 -5.9
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = -4.51f,
                    dx2 = 2.9f,
                    dy2 = -5.9f,
                    dx3 = 4.51f,
                    dy3 = -5.9f,
                )
                // a 0.9 0.9 0.0 0 1 0.26 0.03
                arcToRelative(
                    a = 0.9f,
                    b = 0.9f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.26f,
                    dy1 = 0.03f,
                )
                // c 1.52 0.45 3.02 4.77 4.04 6.2
                curveToRelative(
                    dx1 = 1.52f,
                    dy1 = 0.45f,
                    dx2 = 3.02f,
                    dy2 = 4.77f,
                    dx3 = 4.04f,
                    dy3 = 6.2f,
                )
                // a 9.31 9.31 0.0 0 0 0.32 0.4
                arcToRelative(
                    a = 9.31f,
                    b = 9.31f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.32f,
                    dy1 = 0.4f,
                )
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
                close()
            }
            // M68.14 65.27 c-0.31 1.03 -1.5 1.5 -2.56 1.72 a14.54 14.54 0 0 1 -3.87 0.25 c-0.8 -0.05 -1.63 -0.19 -2.2 -0.74 -0.66 -0.65 -0.72 -1.66 -0.47 -2.58 3.24 -0.36 6.2 -0.52 7.5 -0.39 a2.28 2.28 0 0 1 1.64 0.95 1.6 1.6 0 0 1 -0.04 0.79z m4.83 11.58 a2.05 2.05 0 0 1 -0.81 1.09 4.27 4.27 0 0 1 -1.93 0.61 13.24 13.24 0 0 1 -3.7 -0.05 c-0.47 -0.07 -0.95 -0.19 -1.26 -0.54 a1.68 1.68 0 0 1 -0.35 -1.13 A3.78 3.78 0 0 1 65.39 75 c3.88 0.18 6.99 0.8 7.58 1.85z
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
                // a 14.54 14.54 0.0 0 1 -3.87 0.25
                arcToRelative(
                    a = 14.54f,
                    b = 14.54f,
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
                // a 2.28 2.28 0.0 0 1 1.64 0.95
                arcToRelative(
                    a = 2.28f,
                    b = 2.28f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 1.64f,
                    dy1 = 0.95f,
                )
                // a 1.6 1.6 0.0 0 1 -0.04 0.79
                arcToRelative(
                    a = 1.6f,
                    b = 1.6f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.04f,
                    dy1 = 0.79f,
                )
                close()
                // m 4.83 11.58
                moveToRelative(dx = 4.83f, dy = 11.58f)
                // a 2.05 2.05 0.0 0 1 -0.81 1.09
                arcToRelative(
                    a = 2.05f,
                    b = 2.05f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.81f,
                    dy1 = 1.09f,
                )
                // a 4.27 4.27 0.0 0 1 -1.93 0.61
                arcToRelative(
                    a = 4.27f,
                    b = 4.27f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.93f,
                    dy1 = 0.61f,
                )
                // a 13.24 13.24 0.0 0 1 -3.7 -0.05
                arcToRelative(
                    a = 13.24f,
                    b = 13.24f,
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
                // a 1.68 1.68 0.0 0 1 -0.35 -1.13
                arcToRelative(
                    a = 1.68f,
                    b = 1.68f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.35f,
                    dy1 = -1.13f,
                )
                // A 3.78 3.78 0.0 0 1 65.39 75.0
                arcTo(
                    horizontalEllipseRadius = 3.78f,
                    verticalEllipseRadius = 3.78f,
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
                close()
            }
            // M38.8 69.42 l-0.07 -0.28 A13.36 13.36 0 0 1 38.3 66 a4.94 4.94 0 0 1 0.57 -2.55 c1.27 -2.07 6.92 -9.25 10.05 -12.3 1.33 -1.3 2.5 -2.31 3.48 -3 0.99 -0.7 1.79 -1.05 2.35 -1.05 a1 1 0 0 1 0.5 0.13 4.11 4.11 0 0 1 1.3 1.07 3.08 3.08 0 0 1 0.6 1.92 5.42 5.42 0 0 1 -0.11 1.03 c-0.47 2.45 -2.65 6.4 -4.49 8.88 -0.92 1.25 -1.65 2.14 -2.17 2.96 a5.31 5.31 0 0 0 -0.92 2.5 0.16 0.16 0 1 0 0.31 0.02 5 5 0 0 1 0.88 -2.35 c0.5 -0.8 1.23 -1.69 2.15 -2.94 1.87 -2.52 4.05 -6.46 4.55 -9 a5.78 5.78 0 0 0 0.1 -1.1 3.4 3.4 0 0 0 -0.65 -2.11 4.43 4.43 0 0 0 -1.38 -1.16 1.32 1.32 0 0 0 -0.67 -0.16 c-0.69 0 -1.52 0.4 -2.53 1.1 a30.3 30.3 0 0 0 -3.52 3.04 101.9 101.9 0 0 0 -10.1 12.36 A5.24 5.24 0 0 0 37.98 66 c0 1.8 0.52 3.5 0.52 3.51 a0.16 0.16 0 0 0 0.3 -0.09z
            path(
                fill = SolidColor(Color(0xFF111528)),
            ) {
                // M 38.8 69.42
                moveTo(x = 38.8f, y = 69.42f)
                // l -0.07 -0.28
                lineToRelative(dx = -0.07f, dy = -0.28f)
                // A 13.36 13.36 0.0 0 1 38.3 66.0
                arcTo(
                    horizontalEllipseRadius = 13.36f,
                    verticalEllipseRadius = 13.36f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 38.3f,
                    y1 = 66.0f,
                )
                // a 4.94 4.94 0.0 0 1 0.57 -2.55
                arcToRelative(
                    a = 4.94f,
                    b = 4.94f,
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
                // c 1.33 -1.3 2.5 -2.31 3.48 -3.0
                curveToRelative(
                    dx1 = 1.33f,
                    dy1 = -1.3f,
                    dx2 = 2.5f,
                    dy2 = -2.31f,
                    dx3 = 3.48f,
                    dy3 = -3.0f,
                )
                // c 0.99 -0.7 1.79 -1.05 2.35 -1.05
                curveToRelative(
                    dx1 = 0.99f,
                    dy1 = -0.7f,
                    dx2 = 1.79f,
                    dy2 = -1.05f,
                    dx3 = 2.35f,
                    dy3 = -1.05f,
                )
                // a 1.0 1.0 0.0 0 1 0.5 0.13
                arcToRelative(
                    a = 1.0f,
                    b = 1.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.5f,
                    dy1 = 0.13f,
                )
                // a 4.11 4.11 0.0 0 1 1.3 1.07
                arcToRelative(
                    a = 4.11f,
                    b = 4.11f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 1.3f,
                    dy1 = 1.07f,
                )
                // a 3.08 3.08 0.0 0 1 0.6 1.92
                arcToRelative(
                    a = 3.08f,
                    b = 3.08f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.6f,
                    dy1 = 1.92f,
                )
                // a 5.42 5.42 0.0 0 1 -0.11 1.03
                arcToRelative(
                    a = 5.42f,
                    b = 5.42f,
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
                // a 5.31 5.31 0.0 0 0 -0.92 2.5
                arcToRelative(
                    a = 5.31f,
                    b = 5.31f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -0.92f,
                    dy1 = 2.5f,
                )
                // a 0.16 0.16 0.0 1 0 0.31 0.02
                arcToRelative(
                    a = 0.16f,
                    b = 0.16f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = 0.31f,
                    dy1 = 0.02f,
                )
                // a 5.0 5.0 0.0 0 1 0.88 -2.35
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
                // c 1.87 -2.52 4.05 -6.46 4.55 -9.0
                curveToRelative(
                    dx1 = 1.87f,
                    dy1 = -2.52f,
                    dx2 = 4.05f,
                    dy2 = -6.46f,
                    dx3 = 4.55f,
                    dy3 = -9.0f,
                )
                // a 5.78 5.78 0.0 0 0 0.1 -1.1
                arcToRelative(
                    a = 5.78f,
                    b = 5.78f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.1f,
                    dy1 = -1.1f,
                )
                // a 3.4 3.4 0.0 0 0 -0.65 -2.11
                arcToRelative(
                    a = 3.4f,
                    b = 3.4f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -0.65f,
                    dy1 = -2.11f,
                )
                // a 4.43 4.43 0.0 0 0 -1.38 -1.16
                arcToRelative(
                    a = 4.43f,
                    b = 4.43f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -1.38f,
                    dy1 = -1.16f,
                )
                // a 1.32 1.32 0.0 0 0 -0.67 -0.16
                arcToRelative(
                    a = 1.32f,
                    b = 1.32f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -0.67f,
                    dy1 = -0.16f,
                )
                // c -0.69 0.0 -1.52 0.4 -2.53 1.1
                curveToRelative(
                    dx1 = -0.69f,
                    dy1 = 0.0f,
                    dx2 = -1.52f,
                    dy2 = 0.4f,
                    dx3 = -2.53f,
                    dy3 = 1.1f,
                )
                // a 30.3 30.3 0.0 0 0 -3.52 3.04
                arcToRelative(
                    a = 30.3f,
                    b = 30.3f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -3.52f,
                    dy1 = 3.04f,
                )
                // a 101.9 101.9 0.0 0 0 -10.1 12.36
                arcToRelative(
                    a = 101.9f,
                    b = 101.9f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -10.1f,
                    dy1 = 12.36f,
                )
                // A 5.24 5.24 0.0 0 0 37.98 66.0
                arcTo(
                    horizontalEllipseRadius = 5.24f,
                    verticalEllipseRadius = 5.24f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 37.98f,
                    y1 = 66.0f,
                )
                // c 0.0 1.8 0.52 3.5 0.52 3.51
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = 1.8f,
                    dx2 = 0.52f,
                    dy2 = 3.5f,
                    dx3 = 0.52f,
                    dy3 = 3.51f,
                )
                // a 0.16 0.16 0.0 0 0 0.3 -0.09
                arcToRelative(
                    a = 0.16f,
                    b = 0.16f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.3f,
                    dy1 = -0.09f,
                )
                close()
            }
            // M50.25 76.66 l-0.15 -1.94 -0.16 0.01 0.03 0.16 0.83 -0.2 7.63 -1.73 c1.77 -0.4 3.84 -0.65 5.67 -1.06 1.82 -0.42 3.4 -1 4.19 -2.16 a5.04 5.04 0 0 0 0.02 -5.36 2.44 2.44 0 0 0 -1.75 -1 12.45 12.45 0 0 0 -1.2 -0.05 62.9 62.9 0 0 0 -6.33 0.43 72.1 72.1 0 0 0 -8 1.31 c-5.09 1.22 -9.29 2.59 -11.88 3.88 -0.9 0.46 -1.44 1.5 -1.76 2.8 a19.6 19.6 0 0 0 -0.42 4.47 c0 1.73 0.12 3.45 0.24 4.81 0.32 3.88 1.77 17.1 1.77 17.1 a0.16 0.16 0 1 0 0.31 -0.04 l-0.23 -2.12 c-0.42 -3.9 -1.3 -12.06 -1.54 -14.96 a58.66 58.66 0 0 1 -0.24 -4.79 c0 -1.57 0.1 -3.13 0.42 -4.4 0.3 -1.26 0.82 -2.2 1.59 -2.58 2.56 -1.28 6.74 -2.65 11.82 -3.86 a71.9 71.9 0 0 1 7.95 -1.3 62.72 62.72 0 0 1 6.3 -0.44 c0.47 0 0.87 0.02 1.17 0.05 a2.12 2.12 0 0 1 1.52 0.88 4.03 4.03 0 0 1 0.71 2.4 c0 0.91 -0.24 1.86 -0.74 2.6 -0.67 1.02 -2.19 1.61 -4 2.02 -1.8 0.4 -3.87 0.65 -5.66 1.06 l-8.46 1.93 a0.16 0.16 0 0 0 -0.12 0.17 l0.16 1.93 a0.16 0.16 0 0 0 0.17 0.15 0.16 0.16 0 0 0 0.14 -0.17z
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
                // c 1.82 -0.42 3.4 -1.0 4.19 -2.16
                curveToRelative(
                    dx1 = 1.82f,
                    dy1 = -0.42f,
                    dx2 = 3.4f,
                    dy2 = -1.0f,
                    dx3 = 4.19f,
                    dy3 = -2.16f,
                )
                // a 5.04 5.04 0.0 0 0 0.02 -5.36
                arcToRelative(
                    a = 5.04f,
                    b = 5.04f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.02f,
                    dy1 = -5.36f,
                )
                // a 2.44 2.44 0.0 0 0 -1.75 -1.0
                arcToRelative(
                    a = 2.44f,
                    b = 2.44f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -1.75f,
                    dy1 = -1.0f,
                )
                // a 12.45 12.45 0.0 0 0 -1.2 -0.05
                arcToRelative(
                    a = 12.45f,
                    b = 12.45f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -1.2f,
                    dy1 = -0.05f,
                )
                // a 62.9 62.9 0.0 0 0 -6.33 0.43
                arcToRelative(
                    a = 62.9f,
                    b = 62.9f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -6.33f,
                    dy1 = 0.43f,
                )
                // a 72.1 72.1 0.0 0 0 -8.0 1.31
                arcToRelative(
                    a = 72.1f,
                    b = 72.1f,
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
                // a 19.6 19.6 0.0 0 0 -0.42 4.47
                arcToRelative(
                    a = 19.6f,
                    b = 19.6f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -0.42f,
                    dy1 = 4.47f,
                )
                // c 0.0 1.73 0.12 3.45 0.24 4.81
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
                // a 0.16 0.16 0.0 1 0 0.31 -0.04
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
                // a 58.66 58.66 0.0 0 1 -0.24 -4.79
                arcToRelative(
                    a = 58.66f,
                    b = 58.66f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.24f,
                    dy1 = -4.79f,
                )
                // c 0.0 -1.57 0.1 -3.13 0.42 -4.4
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
                // a 71.9 71.9 0.0 0 1 7.95 -1.3
                arcToRelative(
                    a = 71.9f,
                    b = 71.9f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 7.95f,
                    dy1 = -1.3f,
                )
                // a 62.72 62.72 0.0 0 1 6.3 -0.44
                arcToRelative(
                    a = 62.72f,
                    b = 62.72f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 6.3f,
                    dy1 = -0.44f,
                )
                // c 0.47 0.0 0.87 0.02 1.17 0.05
                curveToRelative(
                    dx1 = 0.47f,
                    dy1 = 0.0f,
                    dx2 = 0.87f,
                    dy2 = 0.02f,
                    dx3 = 1.17f,
                    dy3 = 0.05f,
                )
                // a 2.12 2.12 0.0 0 1 1.52 0.88
                arcToRelative(
                    a = 2.12f,
                    b = 2.12f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 1.52f,
                    dy1 = 0.88f,
                )
                // a 4.03 4.03 0.0 0 1 0.71 2.4
                arcToRelative(
                    a = 4.03f,
                    b = 4.03f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.71f,
                    dy1 = 2.4f,
                )
                // c 0.0 0.91 -0.24 1.86 -0.74 2.6
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = 0.91f,
                    dx2 = -0.24f,
                    dy2 = 1.86f,
                    dx3 = -0.74f,
                    dy3 = 2.6f,
                )
                // c -0.67 1.02 -2.19 1.61 -4.0 2.02
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
                // a 0.16 0.16 0.0 0 0 -0.12 0.17
                arcToRelative(
                    a = 0.16f,
                    b = 0.16f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -0.12f,
                    dy1 = 0.17f,
                )
                // l 0.16 1.93
                lineToRelative(dx = 0.16f, dy = 1.93f)
                // a 0.16 0.16 0.0 0 0 0.17 0.15
                arcToRelative(
                    a = 0.16f,
                    b = 0.16f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.17f,
                    dy1 = 0.15f,
                )
                // a 0.16 0.16 0.0 0 0 0.14 -0.17
                arcToRelative(
                    a = 0.16f,
                    b = 0.16f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.14f,
                    dy1 = -0.17f,
                )
                close()
            }
            // M53.96 91.33 l2.2 -6.71 L56 84.57 l0.01 0.15 s3.25 -0.34 6.71 -0.77 a199.3 199.3 0 0 0 4.98 -0.66 21.81 21.81 0 0 0 3.04 -0.59 3.67 3.67 0 0 0 2.05 -2.05 6.6 6.6 0 0 0 0.53 -2.6 3.61 3.61 0 0 0 -0.14 -1.11 v-0.02 l-0.08 -0.14 -0.14 0.07 h0.16 l-0.02 -0.08 A2.1 2.1 0 0 0 72.15 76 c-1.35 -0.64 -3.83 -1.02 -6.75 -1.15 a44.61 44.61 0 0 0 -3 -0.06 c-5.37 0.06 -9.15 0.2 -12 1.68 -0.74 0.4 -1.6 1.36 -2.5 2.6 -2.69 3.7 -5.68 9.83 -5.68 9.84 a0.16 0.16 0 0 0 0.29 0.13 l0.23 -0.47 a99.45 99.45 0 0 1 3.41 -6.24 39.54 39.54 0 0 1 2.34 -3.52 c0.76 -1 1.5 -1.76 2.06 -2.06 2.74 -1.43 6.48 -1.6 11.85 -1.65 a44.4 44.4 0 0 1 2.98 0.06 29.1 29.1 0 0 1 5 0.6 A8.16 8.16 0 0 1 72 76.28 a1.82 1.82 0 0 1 0.82 0.63 l0.14 -0.07 H72.8 l0.02 0.07 0.07 0.14 0.14 -0.07 -0.15 0.05 a3.35 3.35 0 0 1 0.12 1 6.3 6.3 0 0 1 -0.5 2.49 3.36 3.36 0 0 1 -1.87 1.88 21.87 21.87 0 0 1 -2.98 0.57 254.91 254.91 0 0 1 -10.46 1.3 l-1.21 0.13 a0.16 0.16 0 0 0 -0.13 0.1 l-2.2 6.73 a0.16 0.16 0 0 0 0.3 0.1z
            path(
                fill = SolidColor(Color(0xFF111528)),
            ) {
                // M 53.96 91.33
                moveTo(x = 53.96f, y = 91.33f)
                // l 2.2 -6.71
                lineToRelative(dx = 2.2f, dy = -6.71f)
                // L 56.0 84.57
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
                // a 199.3 199.3 0.0 0 0 4.98 -0.66
                arcToRelative(
                    a = 199.3f,
                    b = 199.3f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 4.98f,
                    dy1 = -0.66f,
                )
                // a 21.81 21.81 0.0 0 0 3.04 -0.59
                arcToRelative(
                    a = 21.81f,
                    b = 21.81f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 3.04f,
                    dy1 = -0.59f,
                )
                // a 3.67 3.67 0.0 0 0 2.05 -2.05
                arcToRelative(
                    a = 3.67f,
                    b = 3.67f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 2.05f,
                    dy1 = -2.05f,
                )
                // a 6.6 6.6 0.0 0 0 0.53 -2.6
                arcToRelative(
                    a = 6.6f,
                    b = 6.6f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.53f,
                    dy1 = -2.6f,
                )
                // a 3.61 3.61 0.0 0 0 -0.14 -1.11
                arcToRelative(
                    a = 3.61f,
                    b = 3.61f,
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
                // A 2.1 2.1 0.0 0 0 72.15 76.0
                arcTo(
                    horizontalEllipseRadius = 2.1f,
                    verticalEllipseRadius = 2.1f,
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
                // a 44.61 44.61 0.0 0 0 -3.0 -0.06
                arcToRelative(
                    a = 44.61f,
                    b = 44.61f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -3.0f,
                    dy1 = -0.06f,
                )
                // c -5.37 0.06 -9.15 0.2 -12.0 1.68
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
                // a 0.16 0.16 0.0 0 0 0.29 0.13
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
                // a 99.45 99.45 0.0 0 1 3.41 -6.24
                arcToRelative(
                    a = 99.45f,
                    b = 99.45f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 3.41f,
                    dy1 = -6.24f,
                )
                // a 39.54 39.54 0.0 0 1 2.34 -3.52
                arcToRelative(
                    a = 39.54f,
                    b = 39.54f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 2.34f,
                    dy1 = -3.52f,
                )
                // c 0.76 -1.0 1.5 -1.76 2.06 -2.06
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
                // a 44.4 44.4 0.0 0 1 2.98 0.06
                arcToRelative(
                    a = 44.4f,
                    b = 44.4f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 2.98f,
                    dy1 = 0.06f,
                )
                // a 29.1 29.1 0.0 0 1 5.0 0.6
                arcToRelative(
                    a = 29.1f,
                    b = 29.1f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 5.0f,
                    dy1 = 0.6f,
                )
                // A 8.16 8.16 0.0 0 1 72.0 76.28
                arcTo(
                    horizontalEllipseRadius = 8.16f,
                    verticalEllipseRadius = 8.16f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 72.0f,
                    y1 = 76.28f,
                )
                // a 1.82 1.82 0.0 0 1 0.82 0.63
                arcToRelative(
                    a = 1.82f,
                    b = 1.82f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.82f,
                    dy1 = 0.63f,
                )
                // l 0.14 -0.07
                lineToRelative(dx = 0.14f, dy = -0.07f)
                // H 72.8
                horizontalLineTo(x = 72.8f)
                // l 0.02 0.07
                lineToRelative(dx = 0.02f, dy = 0.07f)
                // l 0.07 0.14
                lineToRelative(dx = 0.07f, dy = 0.14f)
                // l 0.14 -0.07
                lineToRelative(dx = 0.14f, dy = -0.07f)
                // l -0.15 0.05
                lineToRelative(dx = -0.15f, dy = 0.05f)
                // a 3.35 3.35 0.0 0 1 0.12 1.0
                arcToRelative(
                    a = 3.35f,
                    b = 3.35f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.12f,
                    dy1 = 1.0f,
                )
                // a 6.3 6.3 0.0 0 1 -0.5 2.49
                arcToRelative(
                    a = 6.3f,
                    b = 6.3f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.5f,
                    dy1 = 2.49f,
                )
                // a 3.36 3.36 0.0 0 1 -1.87 1.88
                arcToRelative(
                    a = 3.36f,
                    b = 3.36f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.87f,
                    dy1 = 1.88f,
                )
                // a 21.87 21.87 0.0 0 1 -2.98 0.57
                arcToRelative(
                    a = 21.87f,
                    b = 21.87f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -2.98f,
                    dy1 = 0.57f,
                )
                // a 254.91 254.91 0.0 0 1 -10.46 1.3
                arcToRelative(
                    a = 254.91f,
                    b = 254.91f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -10.46f,
                    dy1 = 1.3f,
                )
                // l -1.21 0.13
                lineToRelative(dx = -1.21f, dy = 0.13f)
                // a 0.16 0.16 0.0 0 0 -0.13 0.1
                arcToRelative(
                    a = 0.16f,
                    b = 0.16f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -0.13f,
                    dy1 = 0.1f,
                )
                // l -2.2 6.73
                lineToRelative(dx = -2.2f, dy = 6.73f)
                // a 0.16 0.16 0.0 0 0 0.3 0.1
                arcToRelative(
                    a = 0.16f,
                    b = 0.16f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.3f,
                    dy1 = 0.1f,
                )
                close()
            }
            // M54.3 89.5 l2.29 2.43 a0.16 0.16 0 1 0 0.23 -0.21 l-2.3 -2.44 a0.16 0.16 0 1 0 -0.23 0.22z
            path(
                fill = SolidColor(Color(0xFF111528)),
            ) {
                // M 54.3 89.5
                moveTo(x = 54.3f, y = 89.5f)
                // l 2.29 2.43
                lineToRelative(dx = 2.29f, dy = 2.43f)
                // a 0.16 0.16 0.0 1 0 0.23 -0.21
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
                // a 0.16 0.16 0.0 1 0 -0.23 0.22
                arcToRelative(
                    a = 0.16f,
                    b = 0.16f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = -0.23f,
                    dy1 = 0.22f,
                )
                close()
            }
            // M64.55 55.04 a1.47 1.47 0 0 1 -1.08 0.42 2.24 2.24 0 0 1 -1.27 -0.48 4.9 4.9 0 0 1 -1.34 -1.79 13.33 13.33 0 0 1 -0.8 -1.88 c-0.3 -0.92 -0.43 -2.04 0.13 -2.87 1.52 0.45 3.02 4.77 4.04 6.2 a9.26 9.26 0 0 0 0.32 0.4z M46.7 50.88 c0.25 -0.07 0.44 -0.28 0.6 -0.48 a17 17 0 0 0 2.34 -3.65 2.3 2.3 0 0 0 0.27 -0.9 c0.02 -0.68 -0.5 -1.23 -1 -1.7 -0.65 0.77 -2.26 3.42 -2.5 3.76 -0.4 0.6 -0.86 1.28 -1.09 1.98 -0.14 0.43 0.04 0.42 0.4 0.66 0.3 0.19 0.6 0.42 0.97 0.33z
            path(
                fill = SolidColor(Color(0xFFFF91B8)),
            ) {
                // M 64.55 55.04
                moveTo(x = 64.55f, y = 55.04f)
                // a 1.47 1.47 0.0 0 1 -1.08 0.42
                arcToRelative(
                    a = 1.47f,
                    b = 1.47f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.08f,
                    dy1 = 0.42f,
                )
                // a 2.24 2.24 0.0 0 1 -1.27 -0.48
                arcToRelative(
                    a = 2.24f,
                    b = 2.24f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.27f,
                    dy1 = -0.48f,
                )
                // a 4.9 4.9 0.0 0 1 -1.34 -1.79
                arcToRelative(
                    a = 4.9f,
                    b = 4.9f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.34f,
                    dy1 = -1.79f,
                )
                // a 13.33 13.33 0.0 0 1 -0.8 -1.88
                arcToRelative(
                    a = 13.33f,
                    b = 13.33f,
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
                // c 1.52 0.45 3.02 4.77 4.04 6.2
                curveToRelative(
                    dx1 = 1.52f,
                    dy1 = 0.45f,
                    dx2 = 3.02f,
                    dy2 = 4.77f,
                    dx3 = 4.04f,
                    dy3 = 6.2f,
                )
                // a 9.26 9.26 0.0 0 0 0.32 0.4
                arcToRelative(
                    a = 9.26f,
                    b = 9.26f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.32f,
                    dy1 = 0.4f,
                )
                close()
                // M 46.7 50.88
                moveTo(x = 46.7f, y = 50.88f)
                // c 0.25 -0.07 0.44 -0.28 0.6 -0.48
                curveToRelative(
                    dx1 = 0.25f,
                    dy1 = -0.07f,
                    dx2 = 0.44f,
                    dy2 = -0.28f,
                    dx3 = 0.6f,
                    dy3 = -0.48f,
                )
                // a 17.0 17.0 0.0 0 0 2.34 -3.65
                arcToRelative(
                    a = 17.0f,
                    b = 17.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 2.34f,
                    dy1 = -3.65f,
                )
                // a 2.3 2.3 0.0 0 0 0.27 -0.9
                arcToRelative(
                    a = 2.3f,
                    b = 2.3f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.27f,
                    dy1 = -0.9f,
                )
                // c 0.02 -0.68 -0.5 -1.23 -1.0 -1.7
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
                close()
            }
            // M57.67 43.65 a138.4 138.4 0 0 1 0.07 3.87 45.6 45.6 0 0 1 -0.37 5.95 c-0.24 2.14 -0.6 5.05 -1.08 10.52 a0.2 0.2 0 1 0 0.38 0.04 c0.48 -5.48 0.84 -8.37 1.09 -10.51 a46.07 46.07 0 0 0 0.36 -6 c0 -1.05 -0.02 -2.3 -0.06 -3.88 a0.2 0.2 0 1 0 -0.39 0.01z
            path(
                fill = SolidColor(Color(0xFF111528)),
            ) {
                // M 57.67 43.65
                moveTo(x = 57.67f, y = 43.65f)
                // a 138.4 138.4 0.0 0 1 0.07 3.87
                arcToRelative(
                    a = 138.4f,
                    b = 138.4f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.07f,
                    dy1 = 3.87f,
                )
                // a 45.6 45.6 0.0 0 1 -0.37 5.95
                arcToRelative(
                    a = 45.6f,
                    b = 45.6f,
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
                // a 0.2 0.2 0.0 1 0 0.38 0.04
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
                // a 46.07 46.07 0.0 0 0 0.36 -6.0
                arcToRelative(
                    a = 46.07f,
                    b = 46.07f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.36f,
                    dy1 = -6.0f,
                )
                // c 0.0 -1.05 -0.02 -2.3 -0.06 -3.88
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = -1.05f,
                    dx2 = -0.02f,
                    dy2 = -2.3f,
                    dx3 = -0.06f,
                    dy3 = -3.88f,
                )
                // a 0.2 0.2 0.0 1 0 -0.39 0.01
                arcToRelative(
                    a = 0.2f,
                    b = 0.2f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = -0.39f,
                    dy1 = 0.01f,
                )
                close()
            }
            group(
                // M55.18 24.7 c-0.6 0 -1.4 -0.25 -1.77 -0.33 -3.27 -0.72 -6.23 -2.44 -8.87 -4.45 a9.78 9.78 0 0 1 -2.4 -2.38 3.92 3.92 0 0 1 -0.57 -3.24 2.28 2.28 0 0 1 2.25 -1.7 c1.01 0 2.1 0.56 2.98 1.15 a28.8 28.8 0 0 1 8.35 8.67 c0.31 0.5 1.25 1.57 0.62 2.12 -0.13 0.12 -0.35 0.16 -0.6 0.16z
                clipPathData = PathData {
                    // M 55.18 24.7
                    moveTo(x = 55.18f, y = 24.7f)
                    // c -0.6 0.0 -1.4 -0.25 -1.77 -0.33
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
                    // a 9.78 9.78 0.0 0 1 -2.4 -2.38
                    arcToRelative(
                        a = 9.78f,
                        b = 9.78f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        dx1 = -2.4f,
                        dy1 = -2.38f,
                    )
                    // a 3.92 3.92 0.0 0 1 -0.57 -3.24
                    arcToRelative(
                        a = 3.92f,
                        b = 3.92f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        dx1 = -0.57f,
                        dy1 = -3.24f,
                    )
                    // a 2.28 2.28 0.0 0 1 2.25 -1.7
                    arcToRelative(
                        a = 2.28f,
                        b = 2.28f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        dx1 = 2.25f,
                        dy1 = -1.7f,
                    )
                    // c 1.01 0.0 2.1 0.56 2.98 1.15
                    curveToRelative(
                        dx1 = 1.01f,
                        dy1 = 0.0f,
                        dx2 = 2.1f,
                        dy2 = 0.56f,
                        dx3 = 2.98f,
                        dy3 = 1.15f,
                    )
                    // a 28.8 28.8 0.0 0 1 8.35 8.67
                    arcToRelative(
                        a = 28.8f,
                        b = 28.8f,
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
                    // c -0.13 0.12 -0.35 0.16 -0.6 0.16
                    curveToRelative(
                        dx1 = -0.13f,
                        dy1 = 0.12f,
                        dx2 = -0.35f,
                        dy2 = 0.16f,
                        dx3 = -0.6f,
                        dy3 = 0.16f,
                    )
                    close()
                },
            ) {
                // M41.23 24.7 H56.4 V12.6 H41.23 v12.1z
                path(
                    fill = SolidColor(Color(0xFFFAB5D5)),
                ) {
                    // M 41.23 24.7
                    moveTo(x = 41.23f, y = 24.7f)
                    // H 56.4
                    horizontalLineTo(x = 56.4f)
                    // V 12.6
                    verticalLineTo(y = 12.6f)
                    // H 41.23
                    horizontalLineTo(x = 41.23f)
                    // v 12.1
                    verticalLineToRelative(dy = 12.1f)
                    close()
                }
            }
            // M41.15 32.67 a4.7 4.7 0 0 1 -1.96 -0.37 c-0.52 -0.23 -1 -0.63 -1.16 -1.17 -0.27 -0.87 0.32 -1.76 0.95 -2.41 a14.42 14.42 0 0 1 7.94 -3.97 20.63 20.63 0 0 1 3.53 -0.29 27.6 27.6 0 0 1 3.03 0.18 c0.81 0.09 2.4 0.03 2.63 1.05 0.18 0.8 -0.77 1.66 -1.44 2.18 a25.24 25.24 0 0 1 -13.52 4.8z
            path(
                fill = SolidColor(Color(0xFFFAB5D5)),
            ) {
                // M 41.15 32.67
                moveTo(x = 41.15f, y = 32.67f)
                // a 4.7 4.7 0.0 0 1 -1.96 -0.37
                arcToRelative(
                    a = 4.7f,
                    b = 4.7f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.96f,
                    dy1 = -0.37f,
                )
                // c -0.52 -0.23 -1.0 -0.63 -1.16 -1.17
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
                // a 14.42 14.42 0.0 0 1 7.94 -3.97
                arcToRelative(
                    a = 14.42f,
                    b = 14.42f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 7.94f,
                    dy1 = -3.97f,
                )
                // a 20.63 20.63 0.0 0 1 3.53 -0.29
                arcToRelative(
                    a = 20.63f,
                    b = 20.63f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 3.53f,
                    dy1 = -0.29f,
                )
                // a 27.6 27.6 0.0 0 1 3.03 0.18
                arcToRelative(
                    a = 27.6f,
                    b = 27.6f,
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
                // a 25.24 25.24 0.0 0 1 -13.52 4.8
                arcToRelative(
                    a = 25.24f,
                    b = 25.24f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -13.52f,
                    dy1 = 4.8f,
                )
                close()
            }
            // M49.31 41.52 c-1.08 0 -2.17 -0.48 -2.66 -1.43 -0.66 -1.24 -0.13 -2.77 0.54 -4 a21.4 21.4 0 0 1 4.15 -5.32 c0.44 -0.41 1.77 -1.73 3.01 -2.67 l0.03 -0.02 a11.53 11.53 0 0 0 0.3 -0.21 c0.63 -0.46 1.22 -0.77 1.62 -0.77 a0.55 0.55 0 0 1 0.22 0.04 c0.45 0.2 0.54 0.9 0.48 1.77 a26.69 26.69 0 0 0 -0.66 3.29 l-0.1 0.36 a18.87 18.87 0 0 1 -2.56 5.96 c-0.8 1.2 -1.85 2.35 -3.22 2.81 a3.57 3.57 0 0 1 -1.15 0.19z
            path(
                fill = SolidColor(Color(0xFFFAB5D5)),
            ) {
                // M 49.31 41.52
                moveTo(x = 49.31f, y = 41.52f)
                // c -1.08 0.0 -2.17 -0.48 -2.66 -1.43
                curveToRelative(
                    dx1 = -1.08f,
                    dy1 = 0.0f,
                    dx2 = -2.17f,
                    dy2 = -0.48f,
                    dx3 = -2.66f,
                    dy3 = -1.43f,
                )
                // c -0.66 -1.24 -0.13 -2.77 0.54 -4.0
                curveToRelative(
                    dx1 = -0.66f,
                    dy1 = -1.24f,
                    dx2 = -0.13f,
                    dy2 = -2.77f,
                    dx3 = 0.54f,
                    dy3 = -4.0f,
                )
                // a 21.4 21.4 0.0 0 1 4.15 -5.32
                arcToRelative(
                    a = 21.4f,
                    b = 21.4f,
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
                // a 11.53 11.53 0.0 0 0 0.3 -0.21
                arcToRelative(
                    a = 11.53f,
                    b = 11.53f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.3f,
                    dy1 = -0.21f,
                )
                // c 0.63 -0.46 1.22 -0.77 1.62 -0.77
                curveToRelative(
                    dx1 = 0.63f,
                    dy1 = -0.46f,
                    dx2 = 1.22f,
                    dy2 = -0.77f,
                    dx3 = 1.62f,
                    dy3 = -0.77f,
                )
                // a 0.55 0.55 0.0 0 1 0.22 0.04
                arcToRelative(
                    a = 0.55f,
                    b = 0.55f,
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
                // a 26.69 26.69 0.0 0 0 -0.66 3.29
                arcToRelative(
                    a = 26.69f,
                    b = 26.69f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -0.66f,
                    dy1 = 3.29f,
                )
                // l -0.1 0.36
                lineToRelative(dx = -0.1f, dy = 0.36f)
                // a 18.87 18.87 0.0 0 1 -2.56 5.96
                arcToRelative(
                    a = 18.87f,
                    b = 18.87f,
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
                // a 3.57 3.57 0.0 0 1 -1.15 0.19
                arcToRelative(
                    a = 3.57f,
                    b = 3.57f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.15f,
                    dy1 = 0.19f,
                )
                close()
            }
            // M54.35 28.1 a12.2 12.2 0 0 1 0.03 -0.02 l-0.03 0.02z
            path(
                fill = SolidColor(Color(0xFFFBC4DB)),
            ) {
                // M 54.35 28.1
                moveTo(x = 54.35f, y = 28.1f)
                // a 12.2 12.2 0.0 0 1 0.03 -0.02
                arcToRelative(
                    a = 12.2f,
                    b = 12.2f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.03f,
                    dy1 = -0.02f,
                )
                // l -0.03 0.02
                lineToRelative(dx = -0.03f, dy = 0.02f)
                close()
            }
            // M63.25 26.4 c-1.78 0 -4.2 -0.13 -4.52 -0.84 -0.52 -1.16 3.09 -3.31 3.86 -3.83 1.81 -1.2 3.8 -2.15 5.9 -2.67 a9.4 9.4 0 0 1 2.23 -0.3 c0.71 0 1.41 0.1 2.06 0.39 1.33 0.58 2.35 2.08 1.98 3.48 -0.36 1.36 -1.78 2.14 -3.1 2.6 A21.37 21.37 0 0 1 65 26.36 c-0.3 0 -0.97 0.03 -1.75 0.03z m-5.55 -1.1 c-1.25 0 -1.94 -4.07 -2.13 -4.98 a18.88 18.88 0 0 1 -0.34 -6.47 c0.19 -1.44 0.61 -2.93 1.64 -3.96 a3.58 3.58 0 0 1 2.46 -1.04 2.52 2.52 0 0 1 1.5 0.47 c1.14 0.83 1.35 2.43 1.3 3.84 a21.37 21.37 0 0 1 -1.37 6.6 c-0.36 0.96 -1.91 5.5 -3.04 5.55 H57.7z m13.2 11.79 a6.1 6.1 0 0 1 -2.7 -0.81 c-2 -1.05 -3.8 -2.45 -5.38 -4.07 -0.71 -0.74 -4.13 -4.08 -3.7 -5.12 0.16 -0.39 0.7 -0.52 1.38 -0.52 1.41 0 3.44 0.57 4.05 0.71 a18.9 18.9 0 0 1 5.99 2.48 c1.22 0.79 2.38 1.81 2.86 3.18 0.49 1.37 0.1 3.14 -1.18 3.83 a2.72 2.72 0 0 1 -1.32 0.32z
            path(
                fill = SolidColor(Color(0xFFFAB5D5)),
            ) {
                // M 63.25 26.4
                moveTo(x = 63.25f, y = 26.4f)
                // c -1.78 0.0 -4.2 -0.13 -4.52 -0.84
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
                // a 9.4 9.4 0.0 0 1 2.23 -0.3
                arcToRelative(
                    a = 9.4f,
                    b = 9.4f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 2.23f,
                    dy1 = -0.3f,
                )
                // c 0.71 0.0 1.41 0.1 2.06 0.39
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
                // A 21.37 21.37 0.0 0 1 65.0 26.36
                arcTo(
                    horizontalEllipseRadius = 21.37f,
                    verticalEllipseRadius = 21.37f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 65.0f,
                    y1 = 26.36f,
                )
                // c -0.3 0.0 -0.97 0.03 -1.75 0.03
                curveToRelative(
                    dx1 = -0.3f,
                    dy1 = 0.0f,
                    dx2 = -0.97f,
                    dy2 = 0.03f,
                    dx3 = -1.75f,
                    dy3 = 0.03f,
                )
                close()
                // m -5.55 -1.1
                moveToRelative(dx = -5.55f, dy = -1.1f)
                // c -1.25 0.0 -1.94 -4.07 -2.13 -4.98
                curveToRelative(
                    dx1 = -1.25f,
                    dy1 = 0.0f,
                    dx2 = -1.94f,
                    dy2 = -4.07f,
                    dx3 = -2.13f,
                    dy3 = -4.98f,
                )
                // a 18.88 18.88 0.0 0 1 -0.34 -6.47
                arcToRelative(
                    a = 18.88f,
                    b = 18.88f,
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
                // a 3.58 3.58 0.0 0 1 2.46 -1.04
                arcToRelative(
                    a = 3.58f,
                    b = 3.58f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 2.46f,
                    dy1 = -1.04f,
                )
                // a 2.52 2.52 0.0 0 1 1.5 0.47
                arcToRelative(
                    a = 2.52f,
                    b = 2.52f,
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
                // a 21.37 21.37 0.0 0 1 -1.37 6.6
                arcToRelative(
                    a = 21.37f,
                    b = 21.37f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.37f,
                    dy1 = 6.6f,
                )
                // c -0.36 0.96 -1.91 5.5 -3.04 5.55
                curveToRelative(
                    dx1 = -0.36f,
                    dy1 = 0.96f,
                    dx2 = -1.91f,
                    dy2 = 5.5f,
                    dx3 = -3.04f,
                    dy3 = 5.55f,
                )
                // H 57.7
                horizontalLineTo(x = 57.7f)
                close()
                // m 13.2 11.79
                moveToRelative(dx = 13.2f, dy = 11.79f)
                // a 6.1 6.1 0.0 0 1 -2.7 -0.81
                arcToRelative(
                    a = 6.1f,
                    b = 6.1f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -2.7f,
                    dy1 = -0.81f,
                )
                // c -2.0 -1.05 -3.8 -2.45 -5.38 -4.07
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
                // c 1.41 0.0 3.44 0.57 4.05 0.71
                curveToRelative(
                    dx1 = 1.41f,
                    dy1 = 0.0f,
                    dx2 = 3.44f,
                    dy2 = 0.57f,
                    dx3 = 4.05f,
                    dy3 = 0.71f,
                )
                // a 18.9 18.9 0.0 0 1 5.99 2.48
                arcToRelative(
                    a = 18.9f,
                    b = 18.9f,
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
                // a 2.72 2.72 0.0 0 1 -1.32 0.32
                arcToRelative(
                    a = 2.72f,
                    b = 2.72f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.32f,
                    dy1 = 0.32f,
                )
                close()
            }
            // M59.24 43.76 a3.44 3.44 0 0 1 -1.22 -0.22 0.2 0.2 0 0 0 -0.16 -0.09 h-0.03 a3.46 3.46 0 0 1 -1.48 -1.23 c-0.8 -1.23 -0.75 -2.8 -0.62 -4.27 0.05 -0.65 0.24 -3.26 0.61 -5.75 A19.82 19.82 0 0 0 57 28.9 c0.3 -1.09 0.68 -1.82 1.13 -1.82 l0.09 0.01 c1.09 0.22 2.5 4.32 2.86 5.26 a25.36 25.36 0 0 1 1.54 6.17 6.1 6.1 0 0 1 -0.25 3.19 3.51 3.51 0 0 1 -3.13 2.04z
            path(
                fill = SolidColor(Color(0xFFFAB5D5)),
            ) {
                // M 59.24 43.76
                moveTo(x = 59.24f, y = 43.76f)
                // a 3.44 3.44 0.0 0 1 -1.22 -0.22
                arcToRelative(
                    a = 3.44f,
                    b = 3.44f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.22f,
                    dy1 = -0.22f,
                )
                // a 0.2 0.2 0.0 0 0 -0.16 -0.09
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
                // a 3.46 3.46 0.0 0 1 -1.48 -1.23
                arcToRelative(
                    a = 3.46f,
                    b = 3.46f,
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
                // A 19.82 19.82 0.0 0 0 57.0 28.9
                arcTo(
                    horizontalEllipseRadius = 19.82f,
                    verticalEllipseRadius = 19.82f,
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
                // a 25.36 25.36 0.0 0 1 1.54 6.17
                arcToRelative(
                    a = 25.36f,
                    b = 25.36f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 1.54f,
                    dy1 = 6.17f,
                )
                // a 6.1 6.1 0.0 0 1 -0.25 3.19
                arcToRelative(
                    a = 6.1f,
                    b = 6.1f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.25f,
                    dy1 = 3.19f,
                )
                // a 3.51 3.51 0.0 0 1 -3.13 2.04
                arcToRelative(
                    a = 3.51f,
                    b = 3.51f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -3.13f,
                    dy1 = 2.04f,
                )
                close()
            }
            // M58.02 43.54 a3.48 3.48 0 0 1 -0.16 -0.09 0.2 0.2 0 0 1 0.16 0.09z
            path(
                fill = SolidColor(Color(0xFFA583A0)),
            ) {
                // M 58.02 43.54
                moveTo(x = 58.02f, y = 43.54f)
                // a 3.48 3.48 0.0 0 1 -0.16 -0.09
                arcToRelative(
                    a = 3.48f,
                    b = 3.48f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.16f,
                    dy1 = -0.09f,
                )
                // a 0.2 0.2 0.0 0 1 0.16 0.09
                arcToRelative(
                    a = 0.2f,
                    b = 0.2f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.16f,
                    dy1 = 0.09f,
                )
                close()
            }
            // M56.34 32.2 c0.18 -1.2 0.4 -2.37 0.66 -3.29 a19.82 19.82 0 0 1 -0.66 3.29z
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
                // a 19.82 19.82 0.0 0 1 -0.66 3.29
                arcToRelative(
                    a = 19.82f,
                    b = 19.82f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.66f,
                    dy1 = 3.29f,
                )
                close()
            }
            // M53.68 23.5 c-0.44 0.18 -0.89 0.42 -1.1 0.85 -0.37 0.77 0.27 1.65 0.91 2.22 1.82 1.59 4.14 2.6 6.54 2.87 A4.34 4.34 0 0 0 62 29.29 c0.62 -0.23 1.18 -0.76 1.28 -1.42 0.28 -1.72 -2.15 -3.45 -3.52 -4.1 a7.89 7.89 0 0 0 -6.09 -0.27z
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
                // A 4.34 4.34 0.0 0 0 62.0 29.29
                arcTo(
                    horizontalEllipseRadius = 4.34f,
                    verticalEllipseRadius = 4.34f,
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
                // a 7.89 7.89 0.0 0 0 -6.09 -0.27
                arcToRelative(
                    a = 7.89f,
                    b = 7.89f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -6.09f,
                    dy1 = -0.27f,
                )
                close()
            }
            // M93.34 61.33 l-0.28 1.62 -0.36 1.96 a442.79 442.79 0 0 1 -8.57 36.25 l-0.16 0.53 a51.25 51.25 0 0 1 -0.3 1.02 13.45 13.45 0 0 1 -0.55 0.33 l0.99 -3.39 0.39 -1.37 a406.31 406.31 0 0 0 7.8 -33.59 l0.25 -1.31 0.37 -2.12 a0.22 0.22 0 0 1 0.25 -0.17 0.21 0.21 0 0 1 0.17 0.24z
            path(
                fill = SolidColor(Color(0xFF111528)),
            ) {
                // M 93.34 61.33
                moveTo(x = 93.34f, y = 61.33f)
                // l -0.28 1.62
                lineToRelative(dx = -0.28f, dy = 1.62f)
                // l -0.36 1.96
                lineToRelative(dx = -0.36f, dy = 1.96f)
                // a 442.79 442.79 0.0 0 1 -8.57 36.25
                arcToRelative(
                    a = 442.79f,
                    b = 442.79f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -8.57f,
                    dy1 = 36.25f,
                )
                // l -0.16 0.53
                lineToRelative(dx = -0.16f, dy = 0.53f)
                // a 51.25 51.25 0.0 0 1 -0.3 1.02
                arcToRelative(
                    a = 51.25f,
                    b = 51.25f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.3f,
                    dy1 = 1.02f,
                )
                // a 13.45 13.45 0.0 0 1 -0.55 0.33
                arcToRelative(
                    a = 13.45f,
                    b = 13.45f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.55f,
                    dy1 = 0.33f,
                )
                // l 0.99 -3.39
                lineToRelative(dx = 0.99f, dy = -3.39f)
                // l 0.39 -1.37
                lineToRelative(dx = 0.39f, dy = -1.37f)
                // a 406.31 406.31 0.0 0 0 7.8 -33.59
                arcToRelative(
                    a = 406.31f,
                    b = 406.31f,
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
                // a 0.22 0.22 0.0 0 1 0.25 -0.17
                arcToRelative(
                    a = 0.22f,
                    b = 0.22f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.25f,
                    dy1 = -0.17f,
                )
                // a 0.21 0.21 0.0 0 1 0.17 0.24
                arcToRelative(
                    a = 0.21f,
                    b = 0.21f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.17f,
                    dy1 = 0.24f,
                )
                close()
            }
            // M84 45.9 c-1.18 -1.21 -2.87 -2.41 -4.45 -1.78 a2.75 2.75 0 0 0 -1.35 1.23 4.35 4.35 0 0 0 -0.09 3.64 10.84 10.84 0 0 0 2.08 3.12 c2.42 2.76 5.27 5.29 8.66 6.79 0.54 0.24 1.95 1.04 2.52 0.71 0.81 -0.46 0.03 -1.84 -0.2 -2.45 a32.2 32.2 0 0 0 -7.16 -11.26z
            path(
                fill = SolidColor(Color(0xFFFF91B8)),
            ) {
                // M 84.0 45.9
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
                // a 2.75 2.75 0.0 0 0 -1.35 1.23
                arcToRelative(
                    a = 2.75f,
                    b = 2.75f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -1.35f,
                    dy1 = 1.23f,
                )
                // a 4.35 4.35 0.0 0 0 -0.09 3.64
                arcToRelative(
                    a = 4.35f,
                    b = 4.35f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -0.09f,
                    dy1 = 3.64f,
                )
                // a 10.84 10.84 0.0 0 0 2.08 3.12
                arcToRelative(
                    a = 10.84f,
                    b = 10.84f,
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
                // a 32.2 32.2 0.0 0 0 -7.16 -11.26
                arcToRelative(
                    a = 32.2f,
                    b = 32.2f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -7.16f,
                    dy1 = -11.26f,
                )
                close()
            }
            // M81.71 57.89 a15.98 15.98 0 0 0 -9.5 2.56 c-0.83 0.57 -1.66 1.4 -1.56 2.41 0.06 0.62 0.49 1.16 1 1.53 a6.67 6.67 0 0 0 3.28 1.01 c4.81 0.5 9.74 -0.29 14.16 -2.25 0.79 -0.35 2.43 -1.16 2.41 -2.22 -0.02 -1.15 -1.77 -1.45 -2.62 -1.72 a25.92 25.92 0 0 0 -7.17 -1.32z
            path(
                fill = SolidColor(Color(0xFFFF91B8)),
            ) {
                // M 81.71 57.89
                moveTo(x = 81.71f, y = 57.89f)
                // a 15.98 15.98 0.0 0 0 -9.5 2.56
                arcToRelative(
                    a = 15.98f,
                    b = 15.98f,
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
                // c 0.06 0.62 0.49 1.16 1.0 1.53
                curveToRelative(
                    dx1 = 0.06f,
                    dy1 = 0.62f,
                    dx2 = 0.49f,
                    dy2 = 1.16f,
                    dx3 = 1.0f,
                    dy3 = 1.53f,
                )
                // a 6.67 6.67 0.0 0 0 3.28 1.01
                arcToRelative(
                    a = 6.67f,
                    b = 6.67f,
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
                // a 25.92 25.92 0.0 0 0 -7.17 -1.32
                arcToRelative(
                    a = 25.92f,
                    b = 25.92f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -7.17f,
                    dy1 = -1.32f,
                )
                close()
            }
            // M79.51 70.27 c-1 1.2 -1.9 2.74 -1.47 4.23 0.45 1.55 2.26 2.4 3.86 2.2 1.6 -0.21 2.99 -1.22 4.13 -2.36 a20.95 20.95 0 0 0 4.1 -5.9 c0.44 -0.94 2.64 -5.04 1.5 -5.84 -1.03 -0.72 -5.47 2.2 -6.43 2.8 a23.72 23.72 0 0 0 -5.69 4.87z m28.99 -6.41 c1.54 -0.21 3.25 -0.74 3.94 -2.14 0.71 -1.45 -0.06 -3.3 -1.38 -4.22 -1.32 -0.93 -3.02 -1.12 -4.64 -1.05 a20.94 20.94 0 0 0 -7 1.6 c-0.96 0.4 -5.35 1.95 -5.04 3.32 0.28 1.22 5.52 2.05 6.64 2.25 2.46 0.46 4.99 0.57 7.47 0.24z m-7.7 -15.22 c0.36 -1.52 0.5 -3.3 -0.55 -4.46 -1.1 -1.19 -3.1 -1.14 -4.44 -0.25 -1.35 0.9 -2.14 2.41 -2.66 3.94 a20.94 20.94 0 0 0 -1.06 7.1 c0.02 1.04 -0.14 5.7 1.25 5.9 1.23 0.2 3.92 -4.39 4.52 -5.35 a23.75 23.75 0 0 0 2.94 -6.88z m1.5 26.46 c1.21 1 2.77 1.88 4.26 1.43 1.55 -0.48 2.36 -2.3 2.14 -3.9 -0.23 -1.6 -1.26 -2.97 -2.41 -4.1 -1.73 -1.69 -3.78 -3 -5.97 -4 -0.94 -0.44 -5.07 -2.58 -5.86 -1.42 -0.7 1.04 2.28 5.43 2.9 6.39 1.35 2.1 3 4.03 4.94 5.6z
            path(
                fill = SolidColor(Color(0xFFFF91B8)),
            ) {
                // M 79.51 70.27
                moveTo(x = 79.51f, y = 70.27f)
                // c -1.0 1.2 -1.9 2.74 -1.47 4.23
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
                // a 20.95 20.95 0.0 0 0 4.1 -5.9
                arcToRelative(
                    a = 20.95f,
                    b = 20.95f,
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
                // a 23.72 23.72 0.0 0 0 -5.69 4.87
                arcToRelative(
                    a = 23.72f,
                    b = 23.72f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -5.69f,
                    dy1 = 4.87f,
                )
                close()
                // m 28.99 -6.41
                moveToRelative(dx = 28.99f, dy = -6.41f)
                // c 1.54 -0.21 3.25 -0.74 3.94 -2.14
                curveToRelative(
                    dx1 = 1.54f,
                    dy1 = -0.21f,
                    dx2 = 3.25f,
                    dy2 = -0.74f,
                    dx3 = 3.94f,
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
                // a 20.94 20.94 0.0 0 0 -7.0 1.6
                arcToRelative(
                    a = 20.94f,
                    b = 20.94f,
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
                close()
                // m -7.7 -15.22
                moveToRelative(dx = -7.7f, dy = -15.22f)
                // c 0.36 -1.52 0.5 -3.3 -0.55 -4.46
                curveToRelative(
                    dx1 = 0.36f,
                    dy1 = -1.52f,
                    dx2 = 0.5f,
                    dy2 = -3.3f,
                    dx3 = -0.55f,
                    dy3 = -4.46f,
                )
                // c -1.1 -1.19 -3.1 -1.14 -4.44 -0.25
                curveToRelative(
                    dx1 = -1.1f,
                    dy1 = -1.19f,
                    dx2 = -3.1f,
                    dy2 = -1.14f,
                    dx3 = -4.44f,
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
                // a 20.94 20.94 0.0 0 0 -1.06 7.1
                arcToRelative(
                    a = 20.94f,
                    b = 20.94f,
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
                // a 23.75 23.75 0.0 0 0 2.94 -6.88
                arcToRelative(
                    a = 23.75f,
                    b = 23.75f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 2.94f,
                    dy1 = -6.88f,
                )
                close()
                // m 1.5 26.46
                moveToRelative(dx = 1.5f, dy = 26.46f)
                // c 1.21 1.0 2.77 1.88 4.26 1.43
                curveToRelative(
                    dx1 = 1.21f,
                    dy1 = 1.0f,
                    dx2 = 2.77f,
                    dy2 = 1.88f,
                    dx3 = 4.26f,
                    dy3 = 1.43f,
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
                // c -1.73 -1.69 -3.78 -3.0 -5.97 -4.0
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
                // c 1.35 2.1 3.0 4.03 4.94 5.6
                curveToRelative(
                    dx1 = 1.35f,
                    dy1 = 2.1f,
                    dx2 = 3.0f,
                    dy2 = 4.03f,
                    dx3 = 4.94f,
                    dy3 = 5.6f,
                )
                close()
            }
            // M88.38 74.18 c-0.46 1.56 -0.87 3.26 -0.26 4.77 a3.9 3.9 0 0 0 3.09 2.37 3.9 3.9 0 0 0 3.55 -1.58 6.76 6.76 0 0 0 0.99 -3.42 28.1 28.1 0 0 0 -0.32 -7.04 c-0.2 -1.1 -0.82 -5.88 -1.95 -6.35 -1.76 -0.73 -4.67 9.8 -5.1 11.25z
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
                // a 3.9 3.9 0.0 0 0 3.09 2.37
                arcToRelative(
                    a = 3.9f,
                    b = 3.9f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 3.09f,
                    dy1 = 2.37f,
                )
                // a 3.9 3.9 0.0 0 0 3.55 -1.58
                arcToRelative(
                    a = 3.9f,
                    b = 3.9f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 3.55f,
                    dy1 = -1.58f,
                )
                // a 6.76 6.76 0.0 0 0 0.99 -3.42
                arcToRelative(
                    a = 6.76f,
                    b = 6.76f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.99f,
                    dy1 = -3.42f,
                )
                // a 28.1 28.1 0.0 0 0 -0.32 -7.04
                arcToRelative(
                    a = 28.1f,
                    b = 28.1f,
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
                close()
            }
            // M89.34 58.02 c-0.51 0.1 -1.06 0.26 -1.38 0.68 -0.57 0.76 -0.08 1.85 0.5 2.61 a13.27 13.27 0 0 0 6.47 4.56 4.81 4.81 0 0 0 2.19 0.28 c0.73 -0.12 1.44 -0.57 1.7 -1.26 0.69 -1.82 -1.57 -4.23 -2.92 -5.25 a8.75 8.75 0 0 0 -6.56 -1.62z
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
                // a 13.27 13.27 0.0 0 0 6.47 4.56
                arcToRelative(
                    a = 13.27f,
                    b = 13.27f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 6.47f,
                    dy1 = 4.56f,
                )
                // a 4.81 4.81 0.0 0 0 2.19 0.28
                arcToRelative(
                    a = 4.81f,
                    b = 4.81f,
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
                // a 8.75 8.75 0.0 0 0 -6.56 -1.62
                arcToRelative(
                    a = 8.75f,
                    b = 8.75f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -6.56f,
                    dy1 = -1.62f,
                )
                close()
            }
            // M84.25 100.28 c-0.51 -3.08 -2.84 -5.47 -4.8 -7.9 a23.28 23.28 0 0 1 -3.88 -6.53 12.57 12.57 0 0 1 -0.5 -7.49 c2.58 1.29 5.07 2.35 6.78 4.68 a12.74 12.74 0 0 1 1.9 4.15 c1.22 4.37 1.15 8.6 0.5 13.1z m-0.79 1.74 c2.55 -1.8 5.9 -1.82 9 -2.14 a23.29 23.29 0 0 0 7.37 -1.82 c2.3 -1.05 4.4 -2.7 5.68 -4.9 -2.73 -0.93 -5.23 -1.96 -8.1 -1.54 a12.72 12.72 0 0 0 -4.28 1.56 c-3.97 2.2 -6.93 5.22 -9.67 8.84z m-51.58 1.58 a12.9 12.9 0 0 1 -0.53 -0.3 l-0.6 -2.2 -0.57 -2.14 a442.98 442.98 0 0 1 -6.44 -28.51 l-0.36 -1.97 a152.6 152.6 0 0 1 -0.29 -1.62 0.21 0.21 0 0 1 0.17 -0.25 0.22 0.22 0 0 1 0.25 0.18 582.88 582.88 0 0 0 0.62 3.43 408.4 408.4 0 0 0 3.27 15.6 405.8 405.8 0 0 0 3.99 15.97 9.84 9.84 0 0 0 0.12 0.46 l0.1 0.34 c0.08 0.34 0.18 0.68 0.27 1.01z
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
                // a 23.28 23.28 0.0 0 1 -3.88 -6.53
                arcToRelative(
                    a = 23.28f,
                    b = 23.28f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -3.88f,
                    dy1 = -6.53f,
                )
                // a 12.57 12.57 0.0 0 1 -0.5 -7.49
                arcToRelative(
                    a = 12.57f,
                    b = 12.57f,
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
                // a 12.74 12.74 0.0 0 1 1.9 4.15
                arcToRelative(
                    a = 12.74f,
                    b = 12.74f,
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
                close()
                // m -0.79 1.74
                moveToRelative(dx = -0.79f, dy = 1.74f)
                // c 2.55 -1.8 5.9 -1.82 9.0 -2.14
                curveToRelative(
                    dx1 = 2.55f,
                    dy1 = -1.8f,
                    dx2 = 5.9f,
                    dy2 = -1.82f,
                    dx3 = 9.0f,
                    dy3 = -2.14f,
                )
                // a 23.29 23.29 0.0 0 0 7.37 -1.82
                arcToRelative(
                    a = 23.29f,
                    b = 23.29f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 7.37f,
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
                // a 12.72 12.72 0.0 0 0 -4.28 1.56
                arcToRelative(
                    a = 12.72f,
                    b = 12.72f,
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
                close()
                // m -51.58 1.58
                moveToRelative(dx = -51.58f, dy = 1.58f)
                // a 12.9 12.9 0.0 0 1 -0.53 -0.3
                arcToRelative(
                    a = 12.9f,
                    b = 12.9f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.53f,
                    dy1 = -0.3f,
                )
                // l -0.6 -2.2
                lineToRelative(dx = -0.6f, dy = -2.2f)
                // l -0.57 -2.14
                lineToRelative(dx = -0.57f, dy = -2.14f)
                // a 442.98 442.98 0.0 0 1 -6.44 -28.51
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
                // a 152.6 152.6 0.0 0 1 -0.29 -1.62
                arcToRelative(
                    a = 152.6f,
                    b = 152.6f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.29f,
                    dy1 = -1.62f,
                )
                // a 0.21 0.21 0.0 0 1 0.17 -0.25
                arcToRelative(
                    a = 0.21f,
                    b = 0.21f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.17f,
                    dy1 = -0.25f,
                )
                // a 0.22 0.22 0.0 0 1 0.25 0.18
                arcToRelative(
                    a = 0.22f,
                    b = 0.22f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.25f,
                    dy1 = 0.18f,
                )
                // a 582.88 582.88 0.0 0 0 0.62 3.43
                arcToRelative(
                    a = 582.88f,
                    b = 582.88f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.62f,
                    dy1 = 3.43f,
                )
                // a 408.4 408.4 0.0 0 0 3.27 15.6
                arcToRelative(
                    a = 408.4f,
                    b = 408.4f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 3.27f,
                    dy1 = 15.6f,
                )
                // a 405.8 405.8 0.0 0 0 3.99 15.97
                arcToRelative(
                    a = 405.8f,
                    b = 405.8f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 3.99f,
                    dy1 = 15.97f,
                )
                // a 9.84 9.84 0.0 0 0 0.12 0.46
                arcToRelative(
                    a = 9.84f,
                    b = 9.84f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.12f,
                    dy1 = 0.46f,
                )
                // l 0.1 0.34
                lineToRelative(dx = 0.1f, dy = 0.34f)
                // c 0.08 0.34 0.18 0.68 0.27 1.01
                curveToRelative(
                    dx1 = 0.08f,
                    dy1 = 0.34f,
                    dx2 = 0.18f,
                    dy2 = 0.68f,
                    dx3 = 0.27f,
                    dy3 = 1.01f,
                )
                close()
            }
            // M32.43 51.43 c1.19 -1.21 2.87 -2.41 4.45 -1.79 a2.76 2.76 0 0 1 1.35 1.24 4.35 4.35 0 0 1 0.1 3.64 10.84 10.84 0 0 1 -2.09 3.12 c-2.42 2.76 -5.26 5.29 -8.65 6.78 -0.55 0.25 -1.95 1.05 -2.53 0.72 -0.8 -0.47 -0.02 -1.84 0.2 -2.45 a32.17 32.17 0 0 1 7.17 -11.26z
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
                // a 2.76 2.76 0.0 0 1 1.35 1.24
                arcToRelative(
                    a = 2.76f,
                    b = 2.76f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 1.35f,
                    dy1 = 1.24f,
                )
                // a 4.35 4.35 0.0 0 1 0.1 3.64
                arcToRelative(
                    a = 4.35f,
                    b = 4.35f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.1f,
                    dy1 = 3.64f,
                )
                // a 10.84 10.84 0.0 0 1 -2.09 3.12
                arcToRelative(
                    a = 10.84f,
                    b = 10.84f,
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
                // a 32.17 32.17 0.0 0 1 7.17 -11.26
                arcToRelative(
                    a = 32.17f,
                    b = 32.17f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 7.17f,
                    dy1 = -11.26f,
                )
                close()
            }
            // M34.72 63.41 a15.98 15.98 0 0 1 9.5 2.57 c0.83 0.57 1.67 1.4 1.57 2.4 -0.06 0.63 -0.5 1.17 -1 1.54 a6.68 6.68 0 0 1 -3.29 1.01 27.98 27.98 0 0 1 -14.15 -2.25 c-0.8 -0.35 -2.44 -1.16 -2.41 -2.22 0.02 -1.15 1.76 -1.45 2.62 -1.72 a25.93 25.93 0 0 1 7.16 -1.33z
            path(
                fill = SolidColor(Color(0xFFFF91B8)),
            ) {
                // M 34.72 63.41
                moveTo(x = 34.72f, y = 63.41f)
                // a 15.98 15.98 0.0 0 1 9.5 2.57
                arcToRelative(
                    a = 15.98f,
                    b = 15.98f,
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
                // c -0.06 0.63 -0.5 1.17 -1.0 1.54
                curveToRelative(
                    dx1 = -0.06f,
                    dy1 = 0.63f,
                    dx2 = -0.5f,
                    dy2 = 1.17f,
                    dx3 = -1.0f,
                    dy3 = 1.54f,
                )
                // a 6.68 6.68 0.0 0 1 -3.29 1.01
                arcToRelative(
                    a = 6.68f,
                    b = 6.68f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -3.29f,
                    dy1 = 1.01f,
                )
                // a 27.98 27.98 0.0 0 1 -14.15 -2.25
                arcToRelative(
                    a = 27.98f,
                    b = 27.98f,
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
                // a 25.93 25.93 0.0 0 1 7.16 -1.33
                arcToRelative(
                    a = 25.93f,
                    b = 25.93f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 7.16f,
                    dy1 = -1.33f,
                )
                close()
            }
            // M36.92 75.8 c1 1.19 1.91 2.73 1.48 4.23 -0.45 1.55 -2.27 2.4 -3.87 2.19 -1.6 -0.2 -2.98 -1.22 -4.13 -2.35 a20.94 20.94 0 0 1 -4.1 -5.91 c-0.44 -0.93 -2.64 -5.03 -1.49 -5.84 1.03 -0.71 5.46 2.2 6.43 2.8 a23.7 23.7 0 0 1 5.68 4.87z M7.94 69.39 C6.4 69.18 4.68 68.65 4 67.25 c-0.7 -1.45 0.07 -3.3 1.39 -4.22 C6.7 62.1 8.4 61.9 10 61.98 c2.41 0.1 4.78 0.7 7 1.6 0.97 0.4 5.36 1.94 5.05 3.31 -0.28 1.22 -5.52 2.06 -6.64 2.26 a23.7 23.7 0 0 1 -7.48 0.24z m7.7 -15.23 c-0.37 -1.51 -0.5 -3.3 0.55 -4.45 1.09 -1.19 3.1 -1.14 4.44 -0.25 1.34 0.89 2.13 2.41 2.66 3.94 a20.93 20.93 0 0 1 1.05 7.1 c-0.01 1.04 0.15 5.7 -1.24 5.9 -1.24 0.19 -3.92 -4.39 -4.52 -5.36 a23.71 23.71 0 0 1 -2.94 -6.88z m-1.51 26.48 c-1.2 0.98 -2.76 1.87 -4.26 1.41 -1.54 -0.47 -2.36 -2.3 -2.13 -3.9 0.23 -1.6 1.26 -2.96 2.4 -4.1 1.73 -1.68 3.79 -3 5.98 -4 0.94 -0.43 5.07 -2.58 5.85 -1.41 0.7 1.03 -2.28 5.43 -2.9 6.38 a23.7 23.7 0 0 1 -4.94 5.62z
            path(
                fill = SolidColor(Color(0xFFFF91B8)),
            ) {
                // M 36.92 75.8
                moveTo(x = 36.92f, y = 75.8f)
                // c 1.0 1.19 1.91 2.73 1.48 4.23
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
                // c -1.6 -0.2 -2.98 -1.22 -4.13 -2.35
                curveToRelative(
                    dx1 = -1.6f,
                    dy1 = -0.2f,
                    dx2 = -2.98f,
                    dy2 = -1.22f,
                    dx3 = -4.13f,
                    dy3 = -2.35f,
                )
                // a 20.94 20.94 0.0 0 1 -4.1 -5.91
                arcToRelative(
                    a = 20.94f,
                    b = 20.94f,
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
                // a 23.7 23.7 0.0 0 1 5.68 4.87
                arcToRelative(
                    a = 23.7f,
                    b = 23.7f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 5.68f,
                    dy1 = 4.87f,
                )
                close()
                // M 7.94 69.39
                moveTo(x = 7.94f, y = 69.39f)
                // C 6.4 69.18 4.68 68.65 4.0 67.25
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
                // C 6.7 62.1 8.4 61.9 10.0 61.98
                curveTo(
                    x1 = 6.7f,
                    y1 = 62.1f,
                    x2 = 8.4f,
                    y2 = 61.9f,
                    x3 = 10.0f,
                    y3 = 61.98f,
                )
                // c 2.41 0.1 4.78 0.7 7.0 1.6
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
                // a 23.7 23.7 0.0 0 1 -7.48 0.24
                arcToRelative(
                    a = 23.7f,
                    b = 23.7f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -7.48f,
                    dy1 = 0.24f,
                )
                close()
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
                // c 1.34 0.89 2.13 2.41 2.66 3.94
                curveToRelative(
                    dx1 = 1.34f,
                    dy1 = 0.89f,
                    dx2 = 2.13f,
                    dy2 = 2.41f,
                    dx3 = 2.66f,
                    dy3 = 3.94f,
                )
                // a 20.93 20.93 0.0 0 1 1.05 7.1
                arcToRelative(
                    a = 20.93f,
                    b = 20.93f,
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
                // a 23.71 23.71 0.0 0 1 -2.94 -6.88
                arcToRelative(
                    a = 23.71f,
                    b = 23.71f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -2.94f,
                    dy1 = -6.88f,
                )
                close()
                // m -1.51 26.48
                moveToRelative(dx = -1.51f, dy = 26.48f)
                // c -1.2 0.98 -2.76 1.87 -4.26 1.41
                curveToRelative(
                    dx1 = -1.2f,
                    dy1 = 0.98f,
                    dx2 = -2.76f,
                    dy2 = 1.87f,
                    dx3 = -4.26f,
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
                // c 0.23 -1.6 1.26 -2.96 2.4 -4.1
                curveToRelative(
                    dx1 = 0.23f,
                    dy1 = -1.6f,
                    dx2 = 1.26f,
                    dy2 = -2.96f,
                    dx3 = 2.4f,
                    dy3 = -4.1f,
                )
                // c 1.73 -1.68 3.79 -3.0 5.98 -4.0
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
                // a 23.7 23.7 0.0 0 1 -4.94 5.62
                arcToRelative(
                    a = 23.7f,
                    b = 23.7f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -4.94f,
                    dy1 = 5.62f,
                )
                close()
            }
            // M28.06 79.7 c0.46 1.56 0.87 3.27 0.26 4.78 a3.9 3.9 0 0 1 -3.09 2.36 3.9 3.9 0 0 1 -3.56 -1.58 6.77 6.77 0 0 1 -0.98 -3.41 A28.09 28.09 0 0 1 21 74.81 c0.19 -1.1 0.81 -5.88 1.95 -6.35 1.75 -0.73 4.67 9.8 5.1 11.25z
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
                // a 3.9 3.9 0.0 0 1 -3.09 2.36
                arcToRelative(
                    a = 3.9f,
                    b = 3.9f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -3.09f,
                    dy1 = 2.36f,
                )
                // a 3.9 3.9 0.0 0 1 -3.56 -1.58
                arcToRelative(
                    a = 3.9f,
                    b = 3.9f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -3.56f,
                    dy1 = -1.58f,
                )
                // a 6.77 6.77 0.0 0 1 -0.98 -3.41
                arcToRelative(
                    a = 6.77f,
                    b = 6.77f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.98f,
                    dy1 = -3.41f,
                )
                // A 28.09 28.09 0.0 0 1 21.0 74.81
                arcTo(
                    horizontalEllipseRadius = 28.09f,
                    verticalEllipseRadius = 28.09f,
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
                close()
            }
            // M27.1 63.55 c0.51 0.1 1.06 0.26 1.37 0.68 0.58 0.76 0.09 1.85 -0.5 2.6 a13.27 13.27 0 0 1 -6.47 4.57 4.81 4.81 0 0 1 -2.18 0.27 c-0.73 -0.11 -1.45 -0.56 -1.7 -1.25 -0.69 -1.82 1.57 -4.23 2.91 -5.25 a8.75 8.75 0 0 1 6.56 -1.62z
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
                // a 13.27 13.27 0.0 0 1 -6.47 4.57
                arcToRelative(
                    a = 13.27f,
                    b = 13.27f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -6.47f,
                    dy1 = 4.57f,
                )
                // a 4.81 4.81 0.0 0 1 -2.18 0.27
                arcToRelative(
                    a = 4.81f,
                    b = 4.81f,
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
                // a 8.75 8.75 0.0 0 1 6.56 -1.62
                arcToRelative(
                    a = 8.75f,
                    b = 8.75f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 6.56f,
                    dy1 = -1.62f,
                )
                close()
            }
            // M31.39 101.8 c-1.96 -2.44 -5.17 -3.38 -8.06 -4.54 a23.27 23.27 0 0 1 -6.59 -3.78 12.57 12.57 0 0 1 -4.11 -6.27 c2.88 -0.15 5.57 -0.45 8.2 0.74 1.4 0.63 2.61 1.6 3.7 2.68 3.2 3.2 5.22 6.93 6.86 11.16z m0.01 1.17 c1.35 -2.82 4.25 -4.49 6.8 -6.28 a23.27 23.27 0 0 0 5.52 -5.2 c1.5 -2.06 2.5 -4.52 2.55 -7.07 -2.84 0.53 -5.52 0.87 -7.8 2.63 a12.73 12.73 0 0 0 -2.98 3.46 c-2.38 3.87 -3.47 7.96 -4.08 12.45z m7.48 -39.53 l0.37 -0.8 c0.83 -1.78 2.94 -6.25 5.02 -10.33 1.05 -2.05 2.08 -4 2.95 -5.47 a22.7 22.7 0 0 1 1.16 -1.8 5.25 5.25 0 0 1 0.43 -0.53 0.96 0.96 0 0 1 0.29 -0.23 2.61 2.61 0 0 1 0.95 -0.17 c1.49 0 3.17 1.14 3.78 2.98 a0.16 0.16 0 1 0 0.3 -0.1 4.56 4.56 0 0 0 -4.08 -3.2 2.93 2.93 0 0 0 -1.07 0.2 1.37 1.37 0 0 0 -0.46 0.36 25.87 25.87 0 0 0 -2.56 4.08 c-3.12 5.7 -7.37 14.87 -7.37 14.87 a0.16 0.16 0 0 0 0.29 0.14z
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
                // a 23.27 23.27 0.0 0 1 -6.59 -3.78
                arcToRelative(
                    a = 23.27f,
                    b = 23.27f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -6.59f,
                    dy1 = -3.78f,
                )
                // a 12.57 12.57 0.0 0 1 -4.11 -6.27
                arcToRelative(
                    a = 12.57f,
                    b = 12.57f,
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
                close()
                // m 0.01 1.17
                moveToRelative(dx = 0.01f, dy = 1.17f)
                // c 1.35 -2.82 4.25 -4.49 6.8 -6.28
                curveToRelative(
                    dx1 = 1.35f,
                    dy1 = -2.82f,
                    dx2 = 4.25f,
                    dy2 = -4.49f,
                    dx3 = 6.8f,
                    dy3 = -6.28f,
                )
                // a 23.27 23.27 0.0 0 0 5.52 -5.2
                arcToRelative(
                    a = 23.27f,
                    b = 23.27f,
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
                // a 12.73 12.73 0.0 0 0 -2.98 3.46
                arcToRelative(
                    a = 12.73f,
                    b = 12.73f,
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
                close()
                // m 7.48 -39.53
                moveToRelative(dx = 7.48f, dy = -39.53f)
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
                // c 1.05 -2.05 2.08 -4.0 2.95 -5.47
                curveToRelative(
                    dx1 = 1.05f,
                    dy1 = -2.05f,
                    dx2 = 2.08f,
                    dy2 = -4.0f,
                    dx3 = 2.95f,
                    dy3 = -5.47f,
                )
                // a 22.7 22.7 0.0 0 1 1.16 -1.8
                arcToRelative(
                    a = 22.7f,
                    b = 22.7f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 1.16f,
                    dy1 = -1.8f,
                )
                // a 5.25 5.25 0.0 0 1 0.43 -0.53
                arcToRelative(
                    a = 5.25f,
                    b = 5.25f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.43f,
                    dy1 = -0.53f,
                )
                // a 0.96 0.96 0.0 0 1 0.29 -0.23
                arcToRelative(
                    a = 0.96f,
                    b = 0.96f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.29f,
                    dy1 = -0.23f,
                )
                // a 2.61 2.61 0.0 0 1 0.95 -0.17
                arcToRelative(
                    a = 2.61f,
                    b = 2.61f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.95f,
                    dy1 = -0.17f,
                )
                // c 1.49 0.0 3.17 1.14 3.78 2.98
                curveToRelative(
                    dx1 = 1.49f,
                    dy1 = 0.0f,
                    dx2 = 3.17f,
                    dy2 = 1.14f,
                    dx3 = 3.78f,
                    dy3 = 2.98f,
                )
                // a 0.16 0.16 0.0 1 0 0.3 -0.1
                arcToRelative(
                    a = 0.16f,
                    b = 0.16f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = 0.3f,
                    dy1 = -0.1f,
                )
                // a 4.56 4.56 0.0 0 0 -4.08 -3.2
                arcToRelative(
                    a = 4.56f,
                    b = 4.56f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -4.08f,
                    dy1 = -3.2f,
                )
                // a 2.93 2.93 0.0 0 0 -1.07 0.2
                arcToRelative(
                    a = 2.93f,
                    b = 2.93f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -1.07f,
                    dy1 = 0.2f,
                )
                // a 1.37 1.37 0.0 0 0 -0.46 0.36
                arcToRelative(
                    a = 1.37f,
                    b = 1.37f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -0.46f,
                    dy1 = 0.36f,
                )
                // a 25.87 25.87 0.0 0 0 -2.56 4.08
                arcToRelative(
                    a = 25.87f,
                    b = 25.87f,
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
                // a 0.16 0.16 0.0 0 0 0.29 0.14
                arcToRelative(
                    a = 0.16f,
                    b = 0.16f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.29f,
                    dy1 = 0.14f,
                )
                close()
            }
            // M97.7 40.62 l-0.46 -7.64 -2.1 7.28 -6.68 -0.58 5.48 3 -3.1 7.22 4.99 -5.56 2.56 7.75 -0.14 -8.85 8.18 -0.93 -8.73 -1.69z m-72.15 2.52 l-0.46 -7.64 -2.1 7.28 -6.69 -0.58 5.5 3 -3.12 7.22 5 -5.56 2.56 7.75 -0.14 -8.84 8.18 -0.94 -8.73 -1.69z M59.4 96 l-0.46 -7.65 -2.11 7.28 -6.68 -0.58 5.49 3 -3.11 7.22 5 -5.56 2.56 7.75 -0.14 -8.84 8.17 -0.94 L59.4 96z
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
                // l 5.48 3.0
                lineToRelative(dx = 5.48f, dy = 3.0f)
                // l -3.1 7.22
                lineToRelative(dx = -3.1f, dy = 7.22f)
                // l 4.99 -5.56
                lineToRelative(dx = 4.99f, dy = -5.56f)
                // l 2.56 7.75
                lineToRelative(dx = 2.56f, dy = 7.75f)
                // l -0.14 -8.85
                lineToRelative(dx = -0.14f, dy = -8.85f)
                // l 8.18 -0.93
                lineToRelative(dx = 8.18f, dy = -0.93f)
                // l -8.73 -1.69
                lineToRelative(dx = -8.73f, dy = -1.69f)
                close()
                // m -72.15 2.52
                moveToRelative(dx = -72.15f, dy = 2.52f)
                // l -0.46 -7.64
                lineToRelative(dx = -0.46f, dy = -7.64f)
                // l -2.1 7.28
                lineToRelative(dx = -2.1f, dy = 7.28f)
                // l -6.69 -0.58
                lineToRelative(dx = -6.69f, dy = -0.58f)
                // l 5.5 3.0
                lineToRelative(dx = 5.5f, dy = 3.0f)
                // l -3.12 7.22
                lineToRelative(dx = -3.12f, dy = 7.22f)
                // l 5.0 -5.56
                lineToRelative(dx = 5.0f, dy = -5.56f)
                // l 2.56 7.75
                lineToRelative(dx = 2.56f, dy = 7.75f)
                // l -0.14 -8.84
                lineToRelative(dx = -0.14f, dy = -8.84f)
                // l 8.18 -0.94
                lineToRelative(dx = 8.18f, dy = -0.94f)
                // l -8.73 -1.69
                lineToRelative(dx = -8.73f, dy = -1.69f)
                close()
                // M 59.4 96.0
                moveTo(x = 59.4f, y = 96.0f)
                // l -0.46 -7.65
                lineToRelative(dx = -0.46f, dy = -7.65f)
                // l -2.11 7.28
                lineToRelative(dx = -2.11f, dy = 7.28f)
                // l -6.68 -0.58
                lineToRelative(dx = -6.68f, dy = -0.58f)
                // l 5.49 3.0
                lineToRelative(dx = 5.49f, dy = 3.0f)
                // l -3.11 7.22
                lineToRelative(dx = -3.11f, dy = 7.22f)
                // l 5.0 -5.56
                lineToRelative(dx = 5.0f, dy = -5.56f)
                // l 2.56 7.75
                lineToRelative(dx = 2.56f, dy = 7.75f)
                // l -0.14 -8.84
                lineToRelative(dx = -0.14f, dy = -8.84f)
                // l 8.17 -0.94
                lineToRelative(dx = 8.17f, dy = -0.94f)
                // L 59.4 96.0
                lineTo(x = 59.4f, y = 96.0f)
                close()
            }
            // M64.75 47.46 s3.85 -0.64 4.34 -1.67 c0.5 -1.04 0.4 -5.04 0.4 -5.04 s1.13 3.5 1.92 4.4 c0.92 1.03 4.59 0.98 4.59 0.98 s-3.3 0.62 -3.95 1.78 c-0.44 0.79 -0.89 3.85 -0.74 5.03 0 0 -1.04 -4.34 -2.12 -4.78 -1.09 -0.45 -4.44 -0.7 -4.44 -0.7z m25.79 30.09 s3.85 0.62 4.65 -0.21 c0.8 -0.83 1.97 -4.65 1.97 -4.65 s-0.03 3.68 0.43 4.78 c0.55 1.27 4.04 2.39 4.04 2.39 s-3.32 -0.46 -4.3 0.43 c-0.68 0.6 -2.07 3.37 -2.3 4.54 0 0 0.4 -4.45 -0.5 -5.21 -0.88 -0.77 -3.99 -2.07 -3.99 -2.07z M15.1 82.02 s3.85 0.6 4.64 -0.22 c0.8 -0.82 1.97 -4.65 1.97 -4.65 s-0.03 3.69 0.44 4.78 c0.54 1.28 4.04 2.39 4.04 2.39 s-3.33 -0.46 -4.31 0.43 c-0.67 0.61 -2.06 3.37 -2.3 4.54 0 0 0.4 -4.45 -0.5 -5.21 -0.88 -0.77 -3.99 -2.06 -3.99 -2.06z
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
                // c 0.0 0.0 -1.04 -4.34 -2.12 -4.78
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
                close()
                // m 25.79 30.09
                moveToRelative(dx = 25.79f, dy = 30.09f)
                // s 3.85 0.62 4.65 -0.21
                reflectiveCurveToRelative(
                    dx1 = 3.85f,
                    dy1 = 0.62f,
                    dx2 = 4.65f,
                    dy2 = -0.21f,
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
                // c 0.0 0.0 0.4 -4.45 -0.5 -5.21
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
                close()
                // M 15.1 82.02
                moveTo(x = 15.1f, y = 82.02f)
                // s 3.85 0.6 4.64 -0.22
                reflectiveCurveToRelative(
                    dx1 = 3.85f,
                    dy1 = 0.6f,
                    dx2 = 4.64f,
                    dy2 = -0.22f,
                )
                // c 0.8 -0.82 1.97 -4.65 1.97 -4.65
                curveToRelative(
                    dx1 = 0.8f,
                    dy1 = -0.82f,
                    dx2 = 1.97f,
                    dy2 = -4.65f,
                    dx3 = 1.97f,
                    dy3 = -4.65f,
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
                // c 0.0 0.0 0.4 -4.45 -0.5 -5.21
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
                close()
            }
        }.build().also { _illustrationOptimized = it }
    }

@Preview
@Composable
private fun IconPreview() {
    dev.tonholo.composeicons.ui.theme.ComposeIconsTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                imageVector = IllustrationOptimized,
                contentDescription = null,
                modifier = Modifier.size(100.dp),
            )
        }
    }
}

@Suppress("ObjectPropertyName")
private var _illustrationOptimized: ImageVector? = null
