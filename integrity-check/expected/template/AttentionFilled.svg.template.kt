package dev.tonholo.s2c.integrity.icon.svg

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import my.custom.app.designsystem.atom.icon.Icons
import my.custom.app.designsystem.atom.icon.icon
import my.custom.app.designsystem.atom.icon.iconPath
import my.custom.app.designsystem.theme.AppTheme

val Icons.AttentionFilled: ImageVector by lazy {
    icon(name = AttentionFilled, viewportWidth = 24.0f, viewportHeight = 24.0f) {
                iconPath(fill = SolidColor(Color(0xFF121212)), pathFillType = PathFillType.EvenOdd) {
            // M 21.5 12
                moveTo(x = 21.5f, y = 12.0f)
                // a 9.5 9.5 0 1 1 -19 0
                arcToRelative(
                    a = 9.5f,
                    b = 9.5f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = true,
                    dx1 = -19.0f,
                    dy1 = 0.0f,
                )
                // a 9.5 9.5 0 0 1 19 0
                arcToRelative(
                    a = 9.5f,
                    b = 9.5f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 19.0f,
                    dy1 = 0.0f,
                )
                // M 12 7.25
                moveTo(x = 12.0f, y = 7.25f)
                // A 0.75 0.75 0 0 1 12.75 8
                arcTo(
                    horizontalEllipseRadius = 0.75f,
                    verticalEllipseRadius = 0.75f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 12.75f,
                    y1 = 8.0f,
                )
                // v 5
                verticalLineToRelative(dy = 5.0f)
                // a 0.75 0.75 0 0 1 -1.5 0
                arcToRelative(
                    a = 0.75f,
                    b = 0.75f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.5f,
                    dy1 = 0.0f,
                )
                // V 8
                verticalLineTo(y = 8.0f)
                // A 0.75 0.75 0 0 1 12 7.25
                arcTo(
                    horizontalEllipseRadius = 0.75f,
                    verticalEllipseRadius = 0.75f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 12.0f,
                    y1 = 7.25f,
                )
                // M 12 17
                moveTo(x = 12.0f, y = 17.0f)
                // a 1 1 0 1 0 0 -2
                arcToRelative(
                    a = 1.0f,
                    b = 1.0f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = 0.0f,
                    dy1 = -2.0f,
                )
                // a 1 1 0 0 0 0 2
                arcToRelative(
                    a = 1.0f,
                    b = 1.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.0f,
                    dy1 = 2.0f,
                )
        }
    }
}

@Preview(name = "AttentionFilled", showBackground = true)
@Composable
private fun AttentionFilledPreview() {
    AppTheme {
        Image(
            imageVector = AttentionFilled,
            contentDescription = null,
        )
    }
}
