package dev.tonholo.s2c.integrity.icon.svg

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import dev.tonholo.svgtocompose.playground.ui.icon.template.Icons
import dev.tonholo.svgtocompose.playground.ui.icon.template.icon
import dev.tonholo.svgtocompose.playground.ui.icon.template.iconPath
import dev.tonholo.svgtocompose.playground.ui.theme.SampleAppTheme

val Icons.LinearGradient01: ImageVector by lazy {
    icon(name = "LinearGradient01", viewportWidth = 800.0f, viewportHeight = 400.0f) {
        iconPath() {
            // M 1 1
            moveTo(x = 1.0f, y = 1.0f)
            // h 798
            horizontalLineToRelative(dx = 798.0f)
            // v 398
            verticalLineToRelative(dy = 398.0f)
            // h -798z
            horizontalLineToRelative(dx = -798.0f)
            close()
        }
        iconPath(
            fill = Brush.linearGradient(
                0.05f to Color(0xFFFF6600),
                0.95f to Color(0xFFFFFF66),
                start = Offset(x = 100.0f, y = 100.0f),
                end = Offset(x = 700.0f, y = 300.0f),
            ),
        ) {
            // M 100 100
            moveTo(x = 100.0f, y = 100.0f)
            // h 600
            horizontalLineToRelative(dx = 600.0f)
            // v 200
            verticalLineToRelative(dy = 200.0f)
            // h -600z
            horizontalLineToRelative(dx = -600.0f)
            close()
        }
    }
}

@Preview(name = "LinearGradient01", showBackground = true)
@Composable
private fun LinearGradient01Preview() {
    SampleAppTheme {
        Image(
            imageVector = Icons.LinearGradient01,
            contentDescription = null,
        )
    }
}
