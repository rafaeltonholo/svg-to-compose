package dev.tonholo.s2c.integrity.icon.avg

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import dev.tonholo.svgtocompose.playground.ui.icon.template.Icons
import dev.tonholo.svgtocompose.playground.ui.icon.template.icon
import dev.tonholo.svgtocompose.playground.ui.icon.template.iconPath
import dev.tonholo.svgtocompose.playground.ui.theme.SampleAppTheme

val Icons.StrokeGradient: ImageVector by lazy {
    icon(name = "StrokeGradient", viewportWidth = 100.0f, viewportHeight = 100.0f) {
        iconPath() {
            // M 50 3
            moveTo(x = 50.0f, y = 3.0f)
            // a 47 47 0 1 1 0 94
            arcToRelative(
                a = 47.0f,
                b = 47.0f,
                theta = 0.0f,
                isMoreThanHalf = true,
                isPositiveArc = true,
                dx1 = 0.0f,
                dy1 = 94.0f,
            )
            // a 47 47 0 1 1 0 -94
            arcToRelative(
                a = 47.0f,
                b = 47.0f,
                theta = 0.0f,
                isMoreThanHalf = true,
                isPositiveArc = true,
                dx1 = 0.0f,
                dy1 = -94.0f,
            )
        }
    }
}

@Preview(name = "StrokeGradient", showBackground = true)
@Composable
private fun StrokeGradientPreview() {
    SampleAppTheme {
        Image(
            imageVector = Icons.StrokeGradient,
            contentDescription = null,
        )
    }
}
