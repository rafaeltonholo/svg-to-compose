package dev.tonholo.s2c.integrity.icon.svg

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AttentionFilled: ImageVector
    get() {
        val current = _attentionFilled
        if (current != null) return current

        return ImageVector.Builder(
            name = ".AttentionFilled",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f,
        ).apply {
            // M21.5 12 C21.5 17.2467 17.2467 21.5 12 21.5 C6.75329 21.5 2.5 17.2467 2.5 12 C2.5 6.75329 6.75329 2.5 12 2.5 C17.2467 2.5 21.5 6.75329 21.5 12Z M12 7.25 C12.4142 7.25 12.75 7.58579 12.75 8 V13 C12.75 13.4142 12.4142 13.75 12 13.75 C11.5858 13.75 11.25 13.4142 11.25 13 V8 C11.25 7.58579 11.5858 7.25 12 7.25Z M12 17 C12.5523 17 13 16.5523 13 16 C13 15.4477 12.5523 15 12 15 C11.4477 15 11 15.4477 11 16 C11 16.5523 11.4477 17 12 17Z
            path(
                fill = SolidColor(Color(0xFF121212)),
                pathFillType = PathFillType.EvenOdd,
            ) {
                // M 21.5 12
                moveTo(x = 21.5f, y = 12.0f)
                // C 21.5 17.2467 17.2467 21.5 12 21.5
                curveTo(
                    x1 = 21.5f,
                    y1 = 17.2467f,
                    x2 = 17.2467f,
                    y2 = 21.5f,
                    x3 = 12.0f,
                    y3 = 21.5f,
                )
                // C 6.75329 21.5 2.5 17.2467 2.5 12
                curveTo(
                    x1 = 6.75329f,
                    y1 = 21.5f,
                    x2 = 2.5f,
                    y2 = 17.2467f,
                    x3 = 2.5f,
                    y3 = 12.0f,
                )
                // C 2.5 6.75329 6.75329 2.5 12 2.5
                curveTo(
                    x1 = 2.5f,
                    y1 = 6.75329f,
                    x2 = 6.75329f,
                    y2 = 2.5f,
                    x3 = 12.0f,
                    y3 = 2.5f,
                )
                // C 17.2467 2.5 21.5 6.75329 21.5 12z
                curveTo(
                    x1 = 17.2467f,
                    y1 = 2.5f,
                    x2 = 21.5f,
                    y2 = 6.75329f,
                    x3 = 21.5f,
                    y3 = 12.0f,
                )
                close()
                // M 12 7.25
                moveTo(x = 12.0f, y = 7.25f)
                // C 12.4142 7.25 12.75 7.58579 12.75 8
                curveTo(
                    x1 = 12.4142f,
                    y1 = 7.25f,
                    x2 = 12.75f,
                    y2 = 7.58579f,
                    x3 = 12.75f,
                    y3 = 8.0f,
                )
                // V 13
                verticalLineTo(y = 13.0f)
                // C 12.75 13.4142 12.4142 13.75 12 13.75
                curveTo(
                    x1 = 12.75f,
                    y1 = 13.4142f,
                    x2 = 12.4142f,
                    y2 = 13.75f,
                    x3 = 12.0f,
                    y3 = 13.75f,
                )
                // C 11.5858 13.75 11.25 13.4142 11.25 13
                curveTo(
                    x1 = 11.5858f,
                    y1 = 13.75f,
                    x2 = 11.25f,
                    y2 = 13.4142f,
                    x3 = 11.25f,
                    y3 = 13.0f,
                )
                // V 8
                verticalLineTo(y = 8.0f)
                // C 11.25 7.58579 11.5858 7.25 12 7.25z
                curveTo(
                    x1 = 11.25f,
                    y1 = 7.58579f,
                    x2 = 11.5858f,
                    y2 = 7.25f,
                    x3 = 12.0f,
                    y3 = 7.25f,
                )
                close()
                // M 12 17
                moveTo(x = 12.0f, y = 17.0f)
                // C 12.5523 17 13 16.5523 13 16
                curveTo(
                    x1 = 12.5523f,
                    y1 = 17.0f,
                    x2 = 13.0f,
                    y2 = 16.5523f,
                    x3 = 13.0f,
                    y3 = 16.0f,
                )
                // C 13 15.4477 12.5523 15 12 15
                curveTo(
                    x1 = 13.0f,
                    y1 = 15.4477f,
                    x2 = 12.5523f,
                    y2 = 15.0f,
                    x3 = 12.0f,
                    y3 = 15.0f,
                )
                // C 11.4477 15 11 15.4477 11 16
                curveTo(
                    x1 = 11.4477f,
                    y1 = 15.0f,
                    x2 = 11.0f,
                    y2 = 15.4477f,
                    x3 = 11.0f,
                    y3 = 16.0f,
                )
                // C 11 16.5523 11.4477 17 12 17z
                curveTo(
                    x1 = 11.0f,
                    y1 = 16.5523f,
                    x2 = 11.4477f,
                    y2 = 17.0f,
                    x3 = 12.0f,
                    y3 = 17.0f,
                )
                close()
            }
        }.build().also { _attentionFilled = it }
    }

@Suppress("ObjectPropertyName")
private var _attentionFilled: ImageVector? = null
