package dev.tonholo.s2c.integrity.icon.svg

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathData
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import my.custom.app.designsystem.atom.icon.Icons
import my.custom.app.designsystem.atom.icon.icon
import my.custom.app.designsystem.atom.icon.iconPath
import my.custom.app.designsystem.theme.AppTheme

val Icons.MaskWithGroup: ImageVector by lazy {
    icon(name = MaskWithGroup, viewportWidth = 300.0f, viewportHeight = 150.0f) {
        group() {
            iconPath(fill = SolidColor(Color(0xFFFF0000))) {
                // M 0 0
                moveTo(x = 0.0f, y = 0.0f)
                // h 100
                horizontalLineToRelative(dx = 100.0f)
                // v 100
                verticalLineToRelative(dy = 100.0f)
                // h -100z
                horizontalLineToRelative(dx = -100.0f)
                close()
            }
        }
    }
}

@Preview(name = "MaskWithGroup", showBackground = true)
@Composable
private fun MaskWithGroupPreview() {
    AppTheme {
        Image(
            imageVector = MaskWithGroup,
            contentDescription = null,
        )
    }
}
