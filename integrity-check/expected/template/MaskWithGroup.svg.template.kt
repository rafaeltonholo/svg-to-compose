package dev.tonholo.s2c.integrity.icon.svg

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathData
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import dev.tonholo.svg_to_compose.playground.ui.icon.template.Icons
import dev.tonholo.svg_to_compose.playground.ui.icon.template.icon
import dev.tonholo.svg_to_compose.playground.ui.icon.template.iconGroup
import dev.tonholo.svg_to_compose.playground.ui.icon.template.iconPath
import dev.tonholo.svg_to_compose.playground.ui.theme.SampleAppTheme

val Icons.MaskWithGroup: ImageVector by lazy {
    icon(name = "MaskWithGroup", viewportWidth = 300.0f, viewportHeight = 150.0f) {
        iconGroup(
            clipPathData = PathData {
                // M 0 0
                moveTo(x = 0.0f, y = 0.0f)
                // h 100
                horizontalLineToRelative(dx = 100.0f)
                // v 100
                verticalLineToRelative(dy = 100.0f)
                // h -100z
                horizontalLineToRelative(dx = -100.0f)
                close()
                // M 50 50
                moveTo(x = 50.0f, y = 50.0f)
                // m -25 0
                moveToRelative(dx = -25.0f, dy = 0.0f)
                // a 25 25 0 1 1 50 0
                arcToRelative(
                    a = 25.0f,
                    b = 25.0f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = true,
                    dx1 = 50.0f,
                    dy1 = 0.0f,
                )
                // a 25 25 0 1 1 -50 0z
                arcToRelative(
                    a = 25.0f,
                    b = 25.0f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = true,
                    dx1 = -50.0f,
                    dy1 = 0.0f,
                )
                close()
            },
        ) {
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
    SampleAppTheme {
        Image(
            imageVector = Icons.MaskWithGroup,
            contentDescription = null,
        )
    }
}
