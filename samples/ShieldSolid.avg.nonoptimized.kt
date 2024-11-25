package dev.tonholo.svg_to_compose.playground.ui.icon.avg

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
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val ShieldSolidAvgNonOptimized: ImageVector
    get() {
        val current = _shieldSolidAvgNonOptimized
        if (current != null) return current

        return ImageVector.Builder(
            name = "dev.tonholo.svg_to_compose.playground.ui.theme.SampleAppTheme.ShieldSolidAvgNonOptimized",
            defaultWidth = 16.0.dp,
            defaultHeight = 16.0.dp,
            viewportWidth = 512.0f,
            viewportHeight = 512.0f,
        ).apply {
            // M256 0 c4.6 0 9.2 1 13.4 2.9 L457.7 82.8 c22 9.3 38.4 31 38.3 57.2 -0.5 99.2 -41.3 280.7 -213.6 363.2 -16.7 8 -36.1 8 -52.8 0 C57.3 420.7 16.5 239.2 16 140 c-0.1 -26.2 16.3 -47.9 38.3 -57.2 L242.7 2.9 C246.8 1 251.4 0 256 0z m0 66.8 V444.8 C394 378 431.1 230.1 432 141.4 L256 66.8 l0 0z
            path(
                fill = SolidColor(Color(0xFF1E3050)),
                fillAlpha = 1.0f,
            ) {
                // M 256 0
                moveTo(x = 256.0f, y = 0.0f)
                // c 4.6 0 9.2 1 13.4 2.9
                curveToRelative(
                    dx1 = 4.6f,
                    dy1 = 0.0f,
                    dx2 = 9.2f,
                    dy2 = 1.0f,
                    dx3 = 13.4f,
                    dy3 = 2.9f,
                )
                // L 457.7 82.8
                lineTo(x = 457.7f, y = 82.8f)
                // c 22 9.3 38.4 31 38.3 57.2
                curveToRelative(
                    dx1 = 22.0f,
                    dy1 = 9.3f,
                    dx2 = 38.4f,
                    dy2 = 31.0f,
                    dx3 = 38.3f,
                    dy3 = 57.2f,
                )
                // c -0.5 99.2 -41.3 280.7 -213.6 363.2
                curveToRelative(
                    dx1 = -0.5f,
                    dy1 = 99.2f,
                    dx2 = -41.3f,
                    dy2 = 280.7f,
                    dx3 = -213.6f,
                    dy3 = 363.2f,
                )
                // c -16.7 8 -36.1 8 -52.8 0
                curveToRelative(
                    dx1 = -16.7f,
                    dy1 = 8.0f,
                    dx2 = -36.1f,
                    dy2 = 8.0f,
                    dx3 = -52.8f,
                    dy3 = 0.0f,
                )
                // C 57.3 420.7 16.5 239.2 16 140
                curveTo(
                    x1 = 57.3f,
                    y1 = 420.7f,
                    x2 = 16.5f,
                    y2 = 239.2f,
                    x3 = 16.0f,
                    y3 = 140.0f,
                )
                // c -0.1 -26.2 16.3 -47.9 38.3 -57.2
                curveToRelative(
                    dx1 = -0.1f,
                    dy1 = -26.2f,
                    dx2 = 16.3f,
                    dy2 = -47.9f,
                    dx3 = 38.3f,
                    dy3 = -57.2f,
                )
                // L 242.7 2.9
                lineTo(x = 242.7f, y = 2.9f)
                // C 246.8 1 251.4 0 256 0z
                curveTo(
                    x1 = 246.8f,
                    y1 = 1.0f,
                    x2 = 251.4f,
                    y2 = 0.0f,
                    x3 = 256.0f,
                    y3 = 0.0f,
                )
                close()
                // m 0 66.8
                moveToRelative(dx = 0.0f, dy = 66.8f)
                // V 444.8
                verticalLineTo(y = 444.8f)
                // C 394 378 431.1 230.1 432 141.4
                curveTo(
                    x1 = 394.0f,
                    y1 = 378.0f,
                    x2 = 431.1f,
                    y2 = 230.1f,
                    x3 = 432.0f,
                    y3 = 141.4f,
                )
                // L 256 66.8
                lineTo(x = 256.0f, y = 66.8f)
                // l 0 0z
                lineToRelative(dx = 0.0f, dy = 0.0f)
                close()
            }
        }.build().also { _shieldSolidAvgNonOptimized = it }
    }

@Preview
@Composable
private fun IconPreview() {
    dev.tonholo.svg_to_compose.playground.ui.theme.SampleAppTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                imageVector = ShieldSolidAvgNonOptimized,
                contentDescription = null,
                modifier = Modifier
                    .width((512.0).dp)
                    .height((512.0).dp),
            )
        }
    }
}

@Suppress("ObjectPropertyName")
private var _shieldSolidAvgNonOptimized: ImageVector? = null
