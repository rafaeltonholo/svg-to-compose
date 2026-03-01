package dev.tonholo.s2c.integrity.icon.svg

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Uk: ImageVector
    get() {
        val current = _uk
        if (current != null) return current

        return ImageVector.Builder(
            name = ".Uk",
            defaultWidth = 300.0.dp,
            defaultHeight = 300.0.dp,
            viewportWidth = 300.0f,
            viewportHeight = 300.0f,
        ).apply {
            // <rect width="300" height="300" fill="#00247d" />
            path(
                fill = SolidColor(Color(0xFF00247D)),
            ) {
                // M 0 0
                moveTo(x = 0.0f, y = 0.0f)
                // h 300
                horizontalLineToRelative(dx = 300.0f)
                // v 300
                verticalLineToRelative(dy = 300.0f)
                // h -300z
                horizontalLineToRelative(dx = -300.0f)
                close()
            }
            // m0 0 300 300 m0 -300 L0 300
            path(
                stroke = SolidColor(Color(0xFFFFFFFF)),
                strokeLineWidth = 60.0f,
            ) {
                // M 0 0
                moveTo(x = 0.0f, y = 0.0f)
                // l 300 300
                lineToRelative(dx = 300.0f, dy = 300.0f)
                // m 0 -300
                moveToRelative(dx = 0.0f, dy = -300.0f)
                // L 0 300
                lineTo(x = 0.0f, y = 300.0f)
            }
            // m0 0 300 300 m0 -300 L0 300
            path(
                stroke = SolidColor(Color(0xFFCF142B)),
                strokeLineWidth = 40.0f,
            ) {
                // M 0 0
                moveTo(x = 0.0f, y = 0.0f)
                // l 300 300
                lineToRelative(dx = 300.0f, dy = 300.0f)
                // m 0 -300
                moveToRelative(dx = 0.0f, dy = -300.0f)
                // L 0 300
                lineTo(x = 0.0f, y = 300.0f)
            }
            // M150 150 v-30 L30 0 H0z h30 L300 30 V0z v30 l120 120 h30z h-30 L0 270 v30z
            path(
                fill = SolidColor(Color(0xFFFFFFFF)),
            ) {
                // M 150 150
                moveTo(x = 150.0f, y = 150.0f)
                // v -30
                verticalLineToRelative(dy = -30.0f)
                // L 30 0
                lineTo(x = 30.0f, y = 0.0f)
                // H 0z
                horizontalLineTo(x = 0.0f)
                close()
                // h 30
                horizontalLineToRelative(dx = 30.0f)
                // L 300 30
                lineTo(x = 300.0f, y = 30.0f)
                // V 0z
                verticalLineTo(y = 0.0f)
                close()
                // v 30
                verticalLineToRelative(dy = 30.0f)
                // l 120 120
                lineToRelative(dx = 120.0f, dy = 120.0f)
                // h 30z
                horizontalLineToRelative(dx = 30.0f)
                close()
                // h -30
                horizontalLineToRelative(dx = -30.0f)
                // L 0 270
                lineTo(x = 0.0f, y = 270.0f)
                // v 30z
                verticalLineToRelative(dy = 30.0f)
                close()
            }
            // M150 0 v300 M0 150 h300
            path(
                stroke = SolidColor(Color(0xFFFFFFFF)),
                strokeLineWidth = 100.0f,
            ) {
                // M 150 0
                moveTo(x = 150.0f, y = 0.0f)
                // v 300
                verticalLineToRelative(dy = 300.0f)
                // M 0 150
                moveTo(x = 0.0f, y = 150.0f)
                // h 300
                horizontalLineToRelative(dx = 300.0f)
            }
            // M150 0 v300 M0 150 h300
            path(
                stroke = SolidColor(Color(0xFFCF142B)),
                strokeLineWidth = 60.0f,
            ) {
                // M 150 0
                moveTo(x = 150.0f, y = 0.0f)
                // v 300
                verticalLineToRelative(dy = 300.0f)
                // M 0 150
                moveTo(x = 0.0f, y = 150.0f)
                // h 300
                horizontalLineToRelative(dx = 300.0f)
            }
        }.build().also { _uk = it }
    }

@Suppress("ObjectPropertyName")
private var _uk: ImageVector? = null
