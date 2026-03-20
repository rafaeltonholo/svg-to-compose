package dev.tonholo.s2c.integrity.icon.svg

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Kotlin: ImageVector
    get() {
        val current = _kotlin
        if (current != null) return current

        return ImageVector.Builder(
            name = ".Kotlin",
            defaultWidth = 22.0.dp,
            defaultHeight = 22.0.dp,
            viewportWidth = 22.0f,
            viewportHeight = 22.0f,
        ).apply {
            // M20 21 H0 V1 h20 L9.8 10.86z
            path(
                fill = Brush.radialGradient(
                    0.003f to Color(0xFFEF4857),
                    0.469f to Color(0xFFD211EC),
                    1.0f to Color(0xFF7F52FF),
                    center = Offset(x = 19.335f, y = 1.822f),
                    radius = 22.9097f,
                ),
            ) {
                // M 21 21
                moveTo(x = 21.0f, y = 21.0f)
                // L 1 21
                lineTo(x = 1.0f, y = 21.0f)
                // L 1 1
                lineTo(x = 1.0f, y = 1.0f)
                // l 20 0
                lineToRelative(dx = 20.0f, dy = 0.0f)
                // L 10.8 10.86z
                lineTo(x = 10.8f, y = 10.86f)
                close()
            }
        }.build().also { _kotlin = it }
    }

@Suppress("ObjectPropertyName")
private var _kotlin: ImageVector? = null
