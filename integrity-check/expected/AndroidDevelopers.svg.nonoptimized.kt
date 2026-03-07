package dev.tonholo.s2c.integrity.icon.svg

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathData
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AndroidDevelopers: ImageVector
    get() {
        val current = _androidDevelopers
        if (current != null) return current

        return ImageVector.Builder(
            name = ".AndroidDevelopers",
            defaultWidth = 129.0.dp,
            defaultHeight = 20.0.dp,
            viewportWidth = 129.0f,
            viewportHeight = 20.0f,
        ).apply {
            group(
                // M25.473 13.317 a12.86 12.86 0 0 0 -2.031 -4.427 12.94 12.94 0 0 0 -2.096 -2.297 12.98 12.98 0 0 0 -1.61 -1.185 l.014 -.026 .78 -1.345 .763 -1.315 .547 -.944 A1.185 1.185 0 0 0 19.792 .59 l-.547 .944 -.763 1.314 -.78 1.346 -.086 .148 -.118 -.047 a12.928 12.928 0 0 0 -4.614 -.845 h-.133 a12.934 12.934 0 0 0 -4.574 .882 l-.08 -.137 -.78 -1.346 -.762 -1.315 L6.007 .59 A1.183 1.183 0 0 0 3.84 .88 a1.186 1.186 0 0 0 .12 .898 l.547 .945 .763 1.314 .78 1.346 .007 .011 a13.017 13.017 0 0 0 -4.67 5.03 12.89 12.89 0 0 0 -1.09 2.894 C.079 14.204 .796 15 1.709 15 h22.349 c.912 0 1.633 -.797 1.415 -1.683Z
                clipPathData = PathData {
                    // M 25.473 13.317
                    moveTo(x = 25.473f, y = 13.317f)
                    // a 12.86 12.86 0 0 0 -2.031 -4.427
                    arcToRelative(
                        a = 12.86f,
                        b = 12.86f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -2.031f,
                        dy1 = -4.427f,
                    )
                    // a 12.94 12.94 0 0 0 -2.096 -2.297
                    arcToRelative(
                        a = 12.94f,
                        b = 12.94f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -2.096f,
                        dy1 = -2.297f,
                    )
                    // a 12.98 12.98 0 0 0 -1.61 -1.185
                    arcToRelative(
                        a = 12.98f,
                        b = 12.98f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -1.61f,
                        dy1 = -1.185f,
                    )
                    // l 0.014 -0.026
                    lineToRelative(dx = 0.014f, dy = -0.026f)
                    // l 0.78 -1.345
                    lineToRelative(dx = 0.78f, dy = -1.345f)
                    // l 0.763 -1.315
                    lineToRelative(dx = 0.763f, dy = -1.315f)
                    // l 0.547 -0.944
                    lineToRelative(dx = 0.547f, dy = -0.944f)
                    // A 1.185 1.185 0 0 0 19.792 0.59
                    arcTo(
                        horizontalEllipseRadius = 1.185f,
                        verticalEllipseRadius = 1.185f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        x1 = 19.792f,
                        y1 = 0.59f,
                    )
                    // l -0.547 0.944
                    lineToRelative(dx = -0.547f, dy = 0.944f)
                    // l -0.763 1.314
                    lineToRelative(dx = -0.763f, dy = 1.314f)
                    // l -0.78 1.346
                    lineToRelative(dx = -0.78f, dy = 1.346f)
                    // l -0.086 0.148
                    lineToRelative(dx = -0.086f, dy = 0.148f)
                    // l -0.118 -0.047
                    lineToRelative(dx = -0.118f, dy = -0.047f)
                    // a 12.928 12.928 0 0 0 -4.614 -0.845
                    arcToRelative(
                        a = 12.928f,
                        b = 12.928f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -4.614f,
                        dy1 = -0.845f,
                    )
                    // h -0.133
                    horizontalLineToRelative(dx = -0.133f)
                    // a 12.934 12.934 0 0 0 -4.574 0.882
                    arcToRelative(
                        a = 12.934f,
                        b = 12.934f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -4.574f,
                        dy1 = 0.882f,
                    )
                    // l -0.08 -0.137
                    lineToRelative(dx = -0.08f, dy = -0.137f)
                    // l -0.78 -1.346
                    lineToRelative(dx = -0.78f, dy = -1.346f)
                    // l -0.762 -1.315
                    lineToRelative(dx = -0.762f, dy = -1.315f)
                    // L 6.007 0.59
                    lineTo(x = 6.007f, y = 0.59f)
                    // A 1.183 1.183 0 0 0 3.84 0.88
                    arcTo(
                        horizontalEllipseRadius = 1.183f,
                        verticalEllipseRadius = 1.183f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        x1 = 3.84f,
                        y1 = 0.88f,
                    )
                    // a 1.186 1.186 0 0 0 0.12 0.898
                    arcToRelative(
                        a = 1.186f,
                        b = 1.186f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = 0.12f,
                        dy1 = 0.898f,
                    )
                    // l 0.547 0.945
                    lineToRelative(dx = 0.547f, dy = 0.945f)
                    // l 0.763 1.314
                    lineToRelative(dx = 0.763f, dy = 1.314f)
                    // l 0.78 1.346
                    lineToRelative(dx = 0.78f, dy = 1.346f)
                    // l 0.007 0.011
                    lineToRelative(dx = 0.007f, dy = 0.011f)
                    // a 13.017 13.017 0 0 0 -4.67 5.03
                    arcToRelative(
                        a = 13.017f,
                        b = 13.017f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -4.67f,
                        dy1 = 5.03f,
                    )
                    // a 12.89 12.89 0 0 0 -1.09 2.894
                    arcToRelative(
                        a = 12.89f,
                        b = 12.89f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -1.09f,
                        dy1 = 2.894f,
                    )
                    // C 0.079 14.204 0.796 15 1.709 15
                    curveTo(
                        x1 = 0.079f,
                        y1 = 14.204f,
                        x2 = 0.796f,
                        y2 = 15.0f,
                        x3 = 1.709f,
                        y3 = 15.0f,
                    )
                    // h 22.349
                    horizontalLineToRelative(dx = 22.349f)
                    // c 0.912 0 1.633 -0.797 1.415 -1.683z
                    curveToRelative(
                        dx1 = 0.912f,
                        dy1 = 0.0f,
                        dx2 = 1.633f,
                        dy2 = -0.797f,
                        dx3 = 1.415f,
                        dy3 = -1.683f,
                    )
                    close()
                },
            ) {
                // M25.913 -.473 H-1.592 v15.476 h27.505 V-.474Z
                path(
                    fill = SolidColor(Color(0xFF4FAF53)),
                ) {
                    // M 25.913 -0.473
                    moveTo(x = 25.913f, y = -0.473f)
                    // H -1.592
                    horizontalLineTo(x = -1.592f)
                    // v 15.476
                    verticalLineToRelative(dy = 15.476f)
                    // h 27.505
                    horizontalLineToRelative(dx = 27.505f)
                    // V -0.474z
                    verticalLineTo(y = -0.474f)
                    close()
                }
                // M12.362 5.355 c2.608 2.07 -4.741 2.3 -7.582 6.815 -2.84 4.514 -.568 12.363 -3.176 10.294 -1.611 -2.477 -1.863 -7.514 .978 -12.03 2.84 -4.514 7.554 -5.998 9.77 -5.079 h.01Z
                path(
                    fill = Brush.linearGradient(
                        0.0f to Color(0xFFA8F0B9),
                        0.807f to Color(0x00ADEEBC),
                        start = Offset(x = 2.442f, y = 10.32f),
                        end = Offset(x = 6.361f, y = 12.778f),
                    ),
                    fillAlpha = 0.4f,
                ) {
                    // M 12.362 5.355
                    moveTo(x = 12.362f, y = 5.355f)
                    // c 2.608 2.07 -4.741 2.3 -7.582 6.815
                    curveToRelative(
                        dx1 = 2.608f,
                        dy1 = 2.07f,
                        dx2 = -4.741f,
                        dy2 = 2.3f,
                        dx3 = -7.582f,
                        dy3 = 6.815f,
                    )
                    // c -2.84 4.514 -0.568 12.363 -3.176 10.294
                    curveToRelative(
                        dx1 = -2.84f,
                        dy1 = 4.514f,
                        dx2 = -0.568f,
                        dy2 = 12.363f,
                        dx3 = -3.176f,
                        dy3 = 10.294f,
                    )
                    // c -1.611 -2.477 -1.863 -7.514 0.978 -12.03
                    curveToRelative(
                        dx1 = -1.611f,
                        dy1 = -2.477f,
                        dx2 = -1.863f,
                        dy2 = -7.514f,
                        dx3 = 0.978f,
                        dy3 = -12.03f,
                    )
                    // c 2.84 -4.514 7.554 -5.998 9.77 -5.079
                    curveToRelative(
                        dx1 = 2.84f,
                        dy1 = -4.514f,
                        dx2 = 7.554f,
                        dy2 = -5.998f,
                        dx3 = 9.77f,
                        dy3 = -5.079f,
                    )
                    // h 0.01z
                    horizontalLineToRelative(dx = 0.01f)
                    close()
                }
                // M13.49 5.341 c-2.74 1.956 4.983 2.173 7.968 6.44 2.985 4.266 .597 11.683 3.338 9.728 1.693 -2.341 1.957 -7.101 -1.028 -11.368 -2.985 -4.267 -7.938 -5.669 -10.268 -4.8 h-.01Z
                path(
                    fill = Brush.linearGradient(
                        0.292f to Color(0xFFA8F0B9),
                        0.828f to Color(0x00ADEEBC),
                        start = Offset(x = 23.915f, y = 10.032f),
                        end = Offset(x = 20.055f, y = 12.726f),
                    ),
                    fillAlpha = 0.4f,
                ) {
                    // M 13.49 5.341
                    moveTo(x = 13.49f, y = 5.341f)
                    // c -2.74 1.956 4.983 2.173 7.968 6.44
                    curveToRelative(
                        dx1 = -2.74f,
                        dy1 = 1.956f,
                        dx2 = 4.983f,
                        dy2 = 2.173f,
                        dx3 = 7.968f,
                        dy3 = 6.44f,
                    )
                    // c 2.985 4.266 0.597 11.683 3.338 9.728
                    curveToRelative(
                        dx1 = 2.985f,
                        dy1 = 4.266f,
                        dx2 = 0.597f,
                        dy2 = 11.683f,
                        dx3 = 3.338f,
                        dy3 = 9.728f,
                    )
                    // c 1.693 -2.341 1.957 -7.101 -1.028 -11.368
                    curveToRelative(
                        dx1 = 1.693f,
                        dy1 = -2.341f,
                        dx2 = 1.957f,
                        dy2 = -7.101f,
                        dx3 = -1.028f,
                        dy3 = -11.368f,
                    )
                    // c -2.985 -4.267 -7.938 -5.669 -10.268 -4.8
                    curveToRelative(
                        dx1 = -2.985f,
                        dy1 = -4.267f,
                        dx2 = -7.938f,
                        dy2 = -5.669f,
                        dx3 = -10.268f,
                        dy3 = -4.8f,
                    )
                    // h -0.01z
                    horizontalLineToRelative(dx = -0.01f)
                    close()
                }
                // M-1.386 11.668 c-1.047 3.295 -1.586 6.064 -1.204 6.185 .382 .121 1.54 -2.45 2.588 -5.745 1.047 -3.294 5.366 -6.886 4.984 -7.007 -.382 -.122 -5.32 3.273 -6.368 6.567Z
                path(
                    fill = SolidColor(Color(0xFF8BD8A0)),
                    fillAlpha = 0.6f,
                    strokeAlpha = 0.6f,
                ) {
                    // M -1.386 11.668
                    moveTo(x = -1.386f, y = 11.668f)
                    // c -1.047 3.295 -1.586 6.064 -1.204 6.185
                    curveToRelative(
                        dx1 = -1.047f,
                        dy1 = 3.295f,
                        dx2 = -1.586f,
                        dy2 = 6.064f,
                        dx3 = -1.204f,
                        dy3 = 6.185f,
                    )
                    // c 0.382 0.121 1.54 -2.45 2.588 -5.745
                    curveToRelative(
                        dx1 = 0.382f,
                        dy1 = 0.121f,
                        dx2 = 1.54f,
                        dy2 = -2.45f,
                        dx3 = 2.588f,
                        dy3 = -5.745f,
                    )
                    // c 1.047 -3.294 5.366 -6.886 4.984 -7.007
                    curveToRelative(
                        dx1 = 1.047f,
                        dy1 = -3.294f,
                        dx2 = 5.366f,
                        dy2 = -6.886f,
                        dx3 = 4.984f,
                        dy3 = -7.007f,
                    )
                    // c -0.382 -0.122 -5.32 3.273 -6.368 6.567z
                    curveToRelative(
                        dx1 = -0.382f,
                        dy1 = -0.122f,
                        dx2 = -5.32f,
                        dy2 = 3.273f,
                        dx3 = -6.368f,
                        dy3 = 6.567f,
                    )
                    close()
                }
                // M25.966 11.368 c1.047 3.294 1.586 6.063 1.204 6.184 -.382 .122 -1.54 -2.45 -2.587 -5.745 -1.047 -3.294 -5.367 -6.886 -4.985 -7.007 .382 -.121 5.32 3.274 6.368 6.568Z
                path(
                    fill = SolidColor(Color(0xFF8BD8A0)),
                    fillAlpha = 0.5f,
                    strokeAlpha = 0.5f,
                ) {
                    // M 25.966 11.368
                    moveTo(x = 25.966f, y = 11.368f)
                    // c 1.047 3.294 1.586 6.063 1.204 6.184
                    curveToRelative(
                        dx1 = 1.047f,
                        dy1 = 3.294f,
                        dx2 = 1.586f,
                        dy2 = 6.063f,
                        dx3 = 1.204f,
                        dy3 = 6.184f,
                    )
                    // c -0.382 0.122 -1.54 -2.45 -2.587 -5.745
                    curveToRelative(
                        dx1 = -0.382f,
                        dy1 = 0.122f,
                        dx2 = -1.54f,
                        dy2 = -2.45f,
                        dx3 = -2.587f,
                        dy3 = -5.745f,
                    )
                    // c -1.047 -3.294 -5.367 -6.886 -4.985 -7.007
                    curveToRelative(
                        dx1 = -1.047f,
                        dy1 = -3.294f,
                        dx2 = -5.367f,
                        dy2 = -6.886f,
                        dx3 = -4.985f,
                        dy3 = -7.007f,
                    )
                    // c 0.382 -0.121 5.32 3.274 6.368 6.568z
                    curveToRelative(
                        dx1 = 0.382f,
                        dy1 = -0.121f,
                        dx2 = 5.32f,
                        dy2 = 3.274f,
                        dx3 = 6.368f,
                        dy3 = 6.568f,
                    )
                    close()
                }
                // M4.904 2.584 c1.036 1.703 1.49 2.515 1.34 2.607 -.15 .091 -.849 -.572 -1.886 -2.275 -1.566 -1.377 .09 -3.241 .241 -3.333 .15 -.092 -1.237 .744 .305 3.001Z
                path(
                    fill = SolidColor(Color(0xFF0D652D)),
                    fillAlpha = 0.7f,
                    strokeAlpha = 0.7f,
                ) {
                    // M 4.904 2.584
                    moveTo(x = 4.904f, y = 2.584f)
                    // c 1.036 1.703 1.49 2.515 1.34 2.607
                    curveToRelative(
                        dx1 = 1.036f,
                        dy1 = 1.703f,
                        dx2 = 1.49f,
                        dy2 = 2.515f,
                        dx3 = 1.34f,
                        dy3 = 2.607f,
                    )
                    // c -0.15 0.091 -0.849 -0.572 -1.886 -2.275
                    curveToRelative(
                        dx1 = -0.15f,
                        dy1 = 0.091f,
                        dx2 = -0.849f,
                        dy2 = -0.572f,
                        dx3 = -1.886f,
                        dy3 = -2.275f,
                    )
                    // c -1.566 -1.377 0.09 -3.241 0.241 -3.333
                    curveToRelative(
                        dx1 = -1.566f,
                        dy1 = -1.377f,
                        dx2 = 0.09f,
                        dy2 = -3.241f,
                        dx3 = 0.241f,
                        dy3 = -3.333f,
                    )
                    // c 0.15 -0.092 -1.237 0.744 0.305 3.001z
                    curveToRelative(
                        dx1 = 0.15f,
                        dy1 = -0.092f,
                        dx2 = -1.237f,
                        dy2 = 0.744f,
                        dx3 = 0.305f,
                        dy3 = 3.001f,
                    )
                    close()
                }
                // M6.044 5.368 c.124 -.08 -.53 -1.302 -1.463 -2.727 C3.65 1.216 2.794 .126 2.67 .207 c-.124 .08 .53 1.302 1.463 2.727 C5.064 4.36 5.92 5.45 6.044 5.368Z
                path(
                    fill = SolidColor(Color(0xFF202124)),
                    fillAlpha = 0.1f,
                    strokeAlpha = 0.1f,
                ) {
                    // M 6.044 5.368
                    moveTo(x = 6.044f, y = 5.368f)
                    // c 0.124 -0.08 -0.53 -1.302 -1.463 -2.727
                    curveToRelative(
                        dx1 = 0.124f,
                        dy1 = -0.08f,
                        dx2 = -0.53f,
                        dy2 = -1.302f,
                        dx3 = -1.463f,
                        dy3 = -2.727f,
                    )
                    // C 3.65 1.216 2.794 0.126 2.67 0.207
                    curveTo(
                        x1 = 3.65f,
                        y1 = 1.216f,
                        x2 = 2.794f,
                        y2 = 0.126f,
                        x3 = 2.67f,
                        y3 = 0.207f,
                    )
                    // c -0.124 0.08 0.53 1.302 1.463 2.727
                    curveToRelative(
                        dx1 = -0.124f,
                        dy1 = 0.08f,
                        dx2 = 0.53f,
                        dy2 = 1.302f,
                        dx3 = 1.463f,
                        dy3 = 2.727f,
                    )
                    // C 5.064 4.36 5.92 5.45 6.044 5.368z
                    curveTo(
                        x1 = 5.064f,
                        y1 = 4.36f,
                        x2 = 5.92f,
                        y2 = 5.45f,
                        x3 = 6.044f,
                        y3 = 5.368f,
                    )
                    close()
                }
                // M6.194 1.715 c1.085 1.781 1.614 2.598 1.734 2.524 .12 -.073 -.213 -1.008 -1.298 -2.79 -1.046 -2.38 -2.932 -.96 -2.746 -.7 .362 .697 .923 -1.532 2.31 .966Z
                path(
                    fill = SolidColor(Color(0xFFFFFFFF)),
                    fillAlpha = 0.3f,
                    strokeAlpha = 0.3f,
                ) {
                    // M 6.194 1.715
                    moveTo(x = 6.194f, y = 1.715f)
                    // c 1.085 1.781 1.614 2.598 1.734 2.524
                    curveToRelative(
                        dx1 = 1.085f,
                        dy1 = 1.781f,
                        dx2 = 1.614f,
                        dy2 = 2.598f,
                        dx3 = 1.734f,
                        dy3 = 2.524f,
                    )
                    // c 0.12 -0.073 -0.213 -1.008 -1.298 -2.79
                    curveToRelative(
                        dx1 = 0.12f,
                        dy1 = -0.073f,
                        dx2 = -0.213f,
                        dy2 = -1.008f,
                        dx3 = -1.298f,
                        dy3 = -2.79f,
                    )
                    // c -1.046 -2.38 -2.932 -0.96 -2.746 -0.7
                    curveToRelative(
                        dx1 = -1.046f,
                        dy1 = -2.38f,
                        dx2 = -2.932f,
                        dy2 = -0.96f,
                        dx3 = -2.746f,
                        dy3 = -0.7f,
                    )
                    // c 0.362 0.697 0.923 -1.532 2.31 0.966z
                    curveToRelative(
                        dx1 = 0.362f,
                        dy1 = 0.697f,
                        dx2 = 0.923f,
                        dy2 = -1.532f,
                        dx3 = 2.31f,
                        dy3 = 0.966f,
                    )
                    close()
                }
                // M2.841 1.076 c.19 .658 .945 1.017 1.686 .802 .741 -.214 1.188 -.92 .997 -1.578 -.19 -.658 -.945 -1.017 -1.686 -.802 -.74 .214 -1.187 .92 -.997 1.578Z
                path(
                    fill = Brush.radialGradient(
                        0.0f to Color(0xFFFFFFFF),
                        0.948f to Color(0x00FFFFFF),
                        center = Offset.Zero,
                        radius = 1.0f,
                    ),
                    fillAlpha = 0.9f,
                    strokeAlpha = 0.15f,
                ) {
                    // M 2.841 1.076
                    moveTo(x = 2.841f, y = 1.076f)
                    // c 0.19 0.658 0.945 1.017 1.686 0.802
                    curveToRelative(
                        dx1 = 0.19f,
                        dy1 = 0.658f,
                        dx2 = 0.945f,
                        dy2 = 1.017f,
                        dx3 = 1.686f,
                        dy3 = 0.802f,
                    )
                    // c 0.741 -0.214 1.188 -0.92 0.997 -1.578
                    curveToRelative(
                        dx1 = 0.741f,
                        dy1 = -0.214f,
                        dx2 = 1.188f,
                        dy2 = -0.92f,
                        dx3 = 0.997f,
                        dy3 = -1.578f,
                    )
                    // c -0.19 -0.658 -0.945 -1.017 -1.686 -0.802
                    curveToRelative(
                        dx1 = -0.19f,
                        dy1 = -0.658f,
                        dx2 = -0.945f,
                        dy2 = -1.017f,
                        dx3 = -1.686f,
                        dy3 = -0.802f,
                    )
                    // c -0.74 0.214 -1.187 0.92 -0.997 1.578z
                    curveToRelative(
                        dx1 = -0.74f,
                        dy1 = 0.214f,
                        dx2 = -1.187f,
                        dy2 = 0.92f,
                        dx3 = -0.997f,
                        dy3 = 1.578f,
                    )
                    close()
                }
                // M20.127 .904 c.196 .676 1 1.038 1.795 .807 .796 -.23 1.282 -.965 1.087 -1.641 -.196 -.676 -1 -1.038 -1.795 -.807 -.796 .23 -1.282 .965 -1.087 1.641Z
                path(
                    fill = Brush.radialGradient(
                        0.0f to Color(0xFFFFFFFF),
                        0.948f to Color(0x00FFFFFF),
                        center = Offset.Zero,
                        radius = 1.0f,
                    ),
                    fillAlpha = 0.9f,
                    strokeAlpha = 0.15f,
                ) {
                    // M 20.127 0.904
                    moveTo(x = 20.127f, y = 0.904f)
                    // c 0.196 0.676 1 1.038 1.795 0.807
                    curveToRelative(
                        dx1 = 0.196f,
                        dy1 = 0.676f,
                        dx2 = 1.0f,
                        dy2 = 1.038f,
                        dx3 = 1.795f,
                        dy3 = 0.807f,
                    )
                    // c 0.796 -0.23 1.282 -0.965 1.087 -1.641
                    curveToRelative(
                        dx1 = 0.796f,
                        dy1 = -0.23f,
                        dx2 = 1.282f,
                        dy2 = -0.965f,
                        dx3 = 1.087f,
                        dy3 = -1.641f,
                    )
                    // c -0.196 -0.676 -1 -1.038 -1.795 -0.807
                    curveToRelative(
                        dx1 = -0.196f,
                        dy1 = -0.676f,
                        dx2 = -1.0f,
                        dy2 = -1.038f,
                        dx3 = -1.795f,
                        dy3 = -0.807f,
                    )
                    // c -0.796 0.23 -1.282 0.965 -1.087 1.641z
                    curveToRelative(
                        dx1 = -0.796f,
                        dy1 = 0.23f,
                        dx2 = -1.282f,
                        dy2 = 0.965f,
                        dx3 = -1.087f,
                        dy3 = 1.641f,
                    )
                    close()
                }
                // M19.659 5.461 c-.126 -.079 .51 -1.31 1.418 -2.75 .909 -1.44 1.747 -2.544 1.872 -2.465 .125 .079 -.51 1.31 -1.419 2.75 -.908 1.44 -1.746 2.544 -1.872 2.465Z
                path(
                    fill = SolidColor(Color(0xFF202124)),
                    fillAlpha = 0.1f,
                    strokeAlpha = 0.1f,
                ) {
                    // M 19.659 5.461
                    moveTo(x = 19.659f, y = 5.461f)
                    // c -0.126 -0.079 0.51 -1.31 1.418 -2.75
                    curveToRelative(
                        dx1 = -0.126f,
                        dy1 = -0.079f,
                        dx2 = 0.51f,
                        dy2 = -1.31f,
                        dx3 = 1.418f,
                        dy3 = -2.75f,
                    )
                    // c 0.909 -1.44 1.747 -2.544 1.872 -2.465
                    curveToRelative(
                        dx1 = 0.909f,
                        dy1 = -1.44f,
                        dx2 = 1.747f,
                        dy2 = -2.544f,
                        dx3 = 1.872f,
                        dy3 = -2.465f,
                    )
                    // c 0.125 0.079 -0.51 1.31 -1.419 2.75
                    curveToRelative(
                        dx1 = 0.125f,
                        dy1 = 0.079f,
                        dx2 = -0.51f,
                        dy2 = 1.31f,
                        dx3 = -1.419f,
                        dy3 = 2.75f,
                    )
                    // c -0.908 1.44 -1.746 2.544 -1.872 2.465z
                    curveToRelative(
                        dx1 = -0.908f,
                        dy1 = 1.44f,
                        dx2 = -1.746f,
                        dy2 = 2.544f,
                        dx3 = -1.872f,
                        dy3 = 2.465f,
                    )
                    close()
                }
                // M20.267 5.271 c-1.091 -.461 -2.023 -.725 -2.08 -.589 -.058 .137 .77 .52 1.861 .982 1.092 .461 2.034 .826 2.092 .69 .057 -.137 -.781 -.621 -1.873 -1.083Z
                path(
                    fill = SolidColor(Color(0xFF0D652D)),
                    fillAlpha = 0.3f,
                    strokeAlpha = 0.3f,
                ) {
                    // M 20.267 5.271
                    moveTo(x = 20.267f, y = 5.271f)
                    // c -1.091 -0.461 -2.023 -0.725 -2.08 -0.589
                    curveToRelative(
                        dx1 = -1.091f,
                        dy1 = -0.461f,
                        dx2 = -2.023f,
                        dy2 = -0.725f,
                        dx3 = -2.08f,
                        dy3 = -0.589f,
                    )
                    // c -0.058 0.137 0.77 0.52 1.861 0.982
                    curveToRelative(
                        dx1 = -0.058f,
                        dy1 = 0.137f,
                        dx2 = 0.77f,
                        dy2 = 0.52f,
                        dx3 = 1.861f,
                        dy3 = 0.982f,
                    )
                    // c 1.092 0.461 2.034 0.826 2.092 0.69
                    curveToRelative(
                        dx1 = 1.092f,
                        dy1 = 0.461f,
                        dx2 = 2.034f,
                        dy2 = 0.826f,
                        dx3 = 2.092f,
                        dy3 = 0.69f,
                    )
                    // c 0.057 -0.137 -0.781 -0.621 -1.873 -1.083z
                    curveToRelative(
                        dx1 = 0.057f,
                        dy1 = -0.137f,
                        dx2 = -0.781f,
                        dy2 = -0.621f,
                        dx3 = -1.873f,
                        dy3 = -1.083f,
                    )
                    close()
                }
                // M18.03 4.4 c-.304 -.149 -.575 -.222 -.604 -.163 -.029 .06 .2 .187 .503 .337 .304 .15 .57 .264 .599 .205 .029 -.06 -.194 -.23 -.498 -.379Z
                path(
                    fill = SolidColor(Color(0xFF81C995)),
                    fillAlpha = 0.3f,
                    strokeAlpha = 0.3f,
                ) {
                    // M 18.03 4.4
                    moveTo(x = 18.03f, y = 4.4f)
                    // c -0.304 -0.149 -0.575 -0.222 -0.604 -0.163
                    curveToRelative(
                        dx1 = -0.304f,
                        dy1 = -0.149f,
                        dx2 = -0.575f,
                        dy2 = -0.222f,
                        dx3 = -0.604f,
                        dy3 = -0.163f,
                    )
                    // c -0.029 0.06 0.2 0.187 0.503 0.337
                    curveToRelative(
                        dx1 = -0.029f,
                        dy1 = 0.06f,
                        dx2 = 0.2f,
                        dy2 = 0.187f,
                        dx3 = 0.503f,
                        dy3 = 0.337f,
                    )
                    // c 0.304 0.15 0.57 0.264 0.599 0.205
                    curveToRelative(
                        dx1 = 0.304f,
                        dy1 = 0.15f,
                        dx2 = 0.57f,
                        dy2 = 0.264f,
                        dx3 = 0.599f,
                        dy3 = 0.205f,
                    )
                    // c 0.029 -0.06 -0.194 -0.23 -0.498 -0.379z
                    curveToRelative(
                        dx1 = 0.029f,
                        dy1 = -0.06f,
                        dx2 = -0.194f,
                        dy2 = -0.23f,
                        dx3 = -0.498f,
                        dy3 = -0.379f,
                    )
                    close()
                }
                // M5.366 5.392 c1.082 -.484 2.008 -.767 2.069 -.632 .06 .135 -.76 .536 -1.842 1.02 s-2.016 .868 -2.076 .733 c-.06 -.135 .767 -.637 1.85 -1.121Z
                path(
                    fill = SolidColor(Color(0xFF0D652D)),
                    fillAlpha = 0.3f,
                    strokeAlpha = 0.3f,
                ) {
                    // M 5.366 5.392
                    moveTo(x = 5.366f, y = 5.392f)
                    // c 1.082 -0.484 2.008 -0.767 2.069 -0.632
                    curveToRelative(
                        dx1 = 1.082f,
                        dy1 = -0.484f,
                        dx2 = 2.008f,
                        dy2 = -0.767f,
                        dx3 = 2.069f,
                        dy3 = -0.632f,
                    )
                    // c 0.06 0.135 -0.76 0.536 -1.842 1.02
                    curveToRelative(
                        dx1 = 0.06f,
                        dy1 = 0.135f,
                        dx2 = -0.76f,
                        dy2 = 0.536f,
                        dx3 = -1.842f,
                        dy3 = 1.02f,
                    )
                    // s -2.016 0.868 -2.076 0.733
                    reflectiveCurveToRelative(
                        dx1 = -2.016f,
                        dy1 = 0.868f,
                        dx2 = -2.076f,
                        dy2 = 0.733f,
                    )
                    // c -0.06 -0.135 0.767 -0.637 1.85 -1.121z
                    curveToRelative(
                        dx1 = -0.06f,
                        dy1 = -0.135f,
                        dx2 = 0.767f,
                        dy2 = -0.637f,
                        dx3 = 1.85f,
                        dy3 = -1.121f,
                    )
                    close()
                }
                // M7.579 4.478 c.3 -.156 .57 -.234 .6 -.175 .03 .059 -.195 .19 -.496 .346 -.301 .156 -.564 .276 -.594 .217 -.03 -.058 .189 -.232 .49 -.388Z
                path(
                    fill = SolidColor(Color(0xFF81C995)),
                    fillAlpha = 0.3f,
                    strokeAlpha = 0.3f,
                ) {
                    // M 7.579 4.478
                    moveTo(x = 7.579f, y = 4.478f)
                    // c 0.3 -0.156 0.57 -0.234 0.6 -0.175
                    curveToRelative(
                        dx1 = 0.3f,
                        dy1 = -0.156f,
                        dx2 = 0.57f,
                        dy2 = -0.234f,
                        dx3 = 0.6f,
                        dy3 = -0.175f,
                    )
                    // c 0.03 0.059 -0.195 0.19 -0.496 0.346
                    curveToRelative(
                        dx1 = 0.03f,
                        dy1 = 0.059f,
                        dx2 = -0.195f,
                        dy2 = 0.19f,
                        dx3 = -0.496f,
                        dy3 = 0.346f,
                    )
                    // c -0.301 0.156 -0.564 0.276 -0.594 0.217
                    curveToRelative(
                        dx1 = -0.301f,
                        dy1 = 0.156f,
                        dx2 = -0.564f,
                        dy2 = 0.276f,
                        dx3 = -0.594f,
                        dy3 = 0.217f,
                    )
                    // c -0.03 -0.058 0.189 -0.232 0.49 -0.388z
                    curveToRelative(
                        dx1 = -0.03f,
                        dy1 = -0.058f,
                        dx2 = 0.189f,
                        dy2 = -0.232f,
                        dx3 = 0.49f,
                        dy3 = -0.388f,
                    )
                    close()
                }
                // M20.934 2.655 c-.997 1.726 -1.432 2.549 -1.28 2.637 .154 .088 .836 -.591 1.833 -2.318 1.534 -1.413 -.166 -3.239 -.319 -3.327 -.152 -.088 1.254 .716 -.234 3.008Z
                path(
                    fill = SolidColor(Color(0xFF0D652D)),
                    fillAlpha = 0.7f,
                    strokeAlpha = 0.7f,
                ) {
                    // M 20.934 2.655
                    moveTo(x = 20.934f, y = 2.655f)
                    // c -0.997 1.726 -1.432 2.549 -1.28 2.637
                    curveToRelative(
                        dx1 = -0.997f,
                        dy1 = 1.726f,
                        dx2 = -1.432f,
                        dy2 = 2.549f,
                        dx3 = -1.28f,
                        dy3 = 2.637f,
                    )
                    // c 0.154 0.088 0.836 -0.591 1.833 -2.318
                    curveToRelative(
                        dx1 = 0.154f,
                        dy1 = 0.088f,
                        dx2 = 0.836f,
                        dy2 = -0.591f,
                        dx3 = 1.833f,
                        dy3 = -2.318f,
                    )
                    // c 1.534 -1.413 -0.166 -3.239 -0.319 -3.327
                    curveToRelative(
                        dx1 = 1.534f,
                        dy1 = -1.413f,
                        dx2 = -0.166f,
                        dy2 = -3.239f,
                        dx3 = -0.319f,
                        dy3 = -3.327f,
                    )
                    // c -0.152 -0.088 1.254 0.716 -0.234 3.008z
                    curveToRelative(
                        dx1 = -0.152f,
                        dy1 = -0.088f,
                        dx2 = 1.254f,
                        dy2 = 0.716f,
                        dx3 = -0.234f,
                        dy3 = 3.008f,
                    )
                    close()
                }
                // M19.562 1.811 C18.547 3.57 18.049 4.374 17.92 4.3 c-.128 -.074 .162 -1 1.177 -2.757 1.156 -3.053 3.448 -2.65 3.072 -.98 -.296 .685 -1.324 -1.217 -2.608 1.248Z
                path(
                    fill = SolidColor(Color(0xFFFFFFFF)),
                    fillAlpha = 0.3f,
                    strokeAlpha = 0.3f,
                ) {
                    // M 19.562 1.811
                    moveTo(x = 19.562f, y = 1.811f)
                    // C 18.547 3.57 18.049 4.374 17.92 4.3
                    curveTo(
                        x1 = 18.547f,
                        y1 = 3.57f,
                        x2 = 18.049f,
                        y2 = 4.374f,
                        x3 = 17.92f,
                        y3 = 4.3f,
                    )
                    // c -0.128 -0.074 0.162 -1 1.177 -2.757
                    curveToRelative(
                        dx1 = -0.128f,
                        dy1 = -0.074f,
                        dx2 = 0.162f,
                        dy2 = -1.0f,
                        dx3 = 1.177f,
                        dy3 = -2.757f,
                    )
                    // c 1.156 -3.053 3.448 -2.65 3.072 -0.98
                    curveToRelative(
                        dx1 = 1.156f,
                        dy1 = -3.053f,
                        dx2 = 3.448f,
                        dy2 = -2.65f,
                        dx3 = 3.072f,
                        dy3 = -0.98f,
                    )
                    // c -0.296 0.685 -1.324 -1.217 -2.608 1.248z
                    curveToRelative(
                        dx1 = -0.296f,
                        dy1 = 0.685f,
                        dx2 = -1.324f,
                        dy2 = -1.217f,
                        dx3 = -2.608f,
                        dy3 = 1.248f,
                    )
                    close()
                }
                // M7.935 9.667 c.166 -.188 .026 -.587 -.313 -.892 -.34 -.305 -.749 -.4 -.915 -.211 -.166 .188 -.026 .587 .313 .892 .34 .305 .749 .4 .915 .211Z
                path(
                    fill = Brush.radialGradient(
                        0.0f to Color(0xFF93E19F),
                        1.0f to Color(0x0093E19F),
                        center = Offset.Zero,
                        radius = 1.0f,
                    ),
                    fillAlpha = 0.7f,
                ) {
                    // M 7.935 9.667
                    moveTo(x = 7.935f, y = 9.667f)
                    // c 0.166 -0.188 0.026 -0.587 -0.313 -0.892
                    curveToRelative(
                        dx1 = 0.166f,
                        dy1 = -0.188f,
                        dx2 = 0.026f,
                        dy2 = -0.587f,
                        dx3 = -0.313f,
                        dy3 = -0.892f,
                    )
                    // c -0.34 -0.305 -0.749 -0.4 -0.915 -0.211
                    curveToRelative(
                        dx1 = -0.34f,
                        dy1 = -0.305f,
                        dx2 = -0.749f,
                        dy2 = -0.4f,
                        dx3 = -0.915f,
                        dy3 = -0.211f,
                    )
                    // c -0.166 0.188 -0.026 0.587 0.313 0.892
                    curveToRelative(
                        dx1 = -0.166f,
                        dy1 = 0.188f,
                        dx2 = -0.026f,
                        dy2 = 0.587f,
                        dx3 = 0.313f,
                        dy3 = 0.892f,
                    )
                    // c 0.34 0.305 0.749 0.4 0.915 0.211z
                    curveToRelative(
                        dx1 = 0.34f,
                        dy1 = 0.305f,
                        dx2 = 0.749f,
                        dy2 = 0.4f,
                        dx3 = 0.915f,
                        dy3 = 0.211f,
                    )
                    close()
                }
                // M19.225 9.475 c.34 -.305 .48 -.704 .313 -.892 -.166 -.189 -.575 -.094 -.914 .21 -.34 .306 -.48 .705 -.313 .893 .166 .189 .575 .094 .914 -.21Z
                path(
                    fill = Brush.radialGradient(
                        0.0f to Color(0xFF93E19F),
                        1.0f to Color(0x0093E19F),
                        center = Offset.Zero,
                        radius = 1.0f,
                    ),
                    fillAlpha = 0.7f,
                ) {
                    // M 19.225 9.475
                    moveTo(x = 19.225f, y = 9.475f)
                    // c 0.34 -0.305 0.48 -0.704 0.313 -0.892
                    curveToRelative(
                        dx1 = 0.34f,
                        dy1 = -0.305f,
                        dx2 = 0.48f,
                        dy2 = -0.704f,
                        dx3 = 0.313f,
                        dy3 = -0.892f,
                    )
                    // c -0.166 -0.189 -0.575 -0.094 -0.914 0.21
                    curveToRelative(
                        dx1 = -0.166f,
                        dy1 = -0.189f,
                        dx2 = -0.575f,
                        dy2 = -0.094f,
                        dx3 = -0.914f,
                        dy3 = 0.21f,
                    )
                    // c -0.34 0.306 -0.48 0.705 -0.313 0.893
                    curveToRelative(
                        dx1 = -0.34f,
                        dy1 = 0.306f,
                        dx2 = -0.48f,
                        dy2 = 0.705f,
                        dx3 = -0.313f,
                        dy3 = 0.893f,
                    )
                    // c 0.166 0.189 0.575 0.094 0.914 -0.21z
                    curveToRelative(
                        dx1 = 0.166f,
                        dy1 = 0.189f,
                        dx2 = 0.575f,
                        dy2 = 0.094f,
                        dx3 = 0.914f,
                        dy3 = -0.21f,
                    )
                    close()
                }
                // M7.798 10.919 c.412 -1.027 -.02 -1.66 -.287 -1.856 -.771 -.954 -1.81 -.218 -2.117 .197 -.309 .404 -.761 1.13 -.319 2.126 .442 .995 2.21 .819 2.723 -.467Z M18.455 10.942 c-.411 -1.016 .02 -1.66 .288 -1.846 .77 -.954 1.808 -.218 2.117 .197 .308 .404 .76 1.13 .318 2.116 -.442 .985 -2.21 .819 -2.723 -.457 v-.01Z
                path(
                    fill = SolidColor(Color(0xFF011B04)),
                    fillAlpha = 0.09f,
                    strokeAlpha = 0.09f,
                ) {
                    // M 7.798 10.919
                    moveTo(x = 7.798f, y = 10.919f)
                    // c 0.412 -1.027 -0.02 -1.66 -0.287 -1.856
                    curveToRelative(
                        dx1 = 0.412f,
                        dy1 = -1.027f,
                        dx2 = -0.02f,
                        dy2 = -1.66f,
                        dx3 = -0.287f,
                        dy3 = -1.856f,
                    )
                    // c -0.771 -0.954 -1.81 -0.218 -2.117 0.197
                    curveToRelative(
                        dx1 = -0.771f,
                        dy1 = -0.954f,
                        dx2 = -1.81f,
                        dy2 = -0.218f,
                        dx3 = -2.117f,
                        dy3 = 0.197f,
                    )
                    // c -0.309 0.404 -0.761 1.13 -0.319 2.126
                    curveToRelative(
                        dx1 = -0.309f,
                        dy1 = 0.404f,
                        dx2 = -0.761f,
                        dy2 = 1.13f,
                        dx3 = -0.319f,
                        dy3 = 2.126f,
                    )
                    // c 0.442 0.995 2.21 0.819 2.723 -0.467z
                    curveToRelative(
                        dx1 = 0.442f,
                        dy1 = 0.995f,
                        dx2 = 2.21f,
                        dy2 = 0.819f,
                        dx3 = 2.723f,
                        dy3 = -0.467f,
                    )
                    close()
                    // M 18.455 10.942
                    moveTo(x = 18.455f, y = 10.942f)
                    // c -0.411 -1.016 0.02 -1.66 0.288 -1.846
                    curveToRelative(
                        dx1 = -0.411f,
                        dy1 = -1.016f,
                        dx2 = 0.02f,
                        dy2 = -1.66f,
                        dx3 = 0.288f,
                        dy3 = -1.846f,
                    )
                    // c 0.77 -0.954 1.808 -0.218 2.117 0.197
                    curveToRelative(
                        dx1 = 0.77f,
                        dy1 = -0.954f,
                        dx2 = 1.808f,
                        dy2 = -0.218f,
                        dx3 = 2.117f,
                        dy3 = 0.197f,
                    )
                    // c 0.308 0.404 0.76 1.13 0.318 2.116
                    curveToRelative(
                        dx1 = 0.308f,
                        dy1 = 0.404f,
                        dx2 = 0.76f,
                        dy2 = 1.13f,
                        dx3 = 0.318f,
                        dy3 = 2.116f,
                    )
                    // c -0.442 0.985 -2.21 0.819 -2.723 -0.457
                    curveToRelative(
                        dx1 = -0.442f,
                        dy1 = 0.985f,
                        dx2 = -2.21f,
                        dy2 = 0.819f,
                        dx3 = -2.723f,
                        dy3 = -0.457f,
                    )
                    // v -0.01z
                    verticalLineToRelative(dy = -0.01f)
                    close()
                }
                // M20.779 11.644 c.637 -.425 .74 -1.41 .226 -2.188 -.514 -.778 -1.45 -1.058 -2.086 -.633 -.637 .426 -.74 1.41 -.226 2.188 .513 .778 1.449 1.058 2.086 .633Z
                path(
                    fill = SolidColor(Color(0xFF202124)),
                ) {
                    // M 20.779 11.644
                    moveTo(x = 20.779f, y = 11.644f)
                    // c 0.637 -0.425 0.74 -1.41 0.226 -2.188
                    curveToRelative(
                        dx1 = 0.637f,
                        dy1 = -0.425f,
                        dx2 = 0.74f,
                        dy2 = -1.41f,
                        dx3 = 0.226f,
                        dy3 = -2.188f,
                    )
                    // c -0.514 -0.778 -1.45 -1.058 -2.086 -0.633
                    curveToRelative(
                        dx1 = -0.514f,
                        dy1 = -0.778f,
                        dx2 = -1.45f,
                        dy2 = -1.058f,
                        dx3 = -2.086f,
                        dy3 = -0.633f,
                    )
                    // c -0.637 0.426 -0.74 1.41 -0.226 2.188
                    curveToRelative(
                        dx1 = -0.637f,
                        dy1 = 0.426f,
                        dx2 = -0.74f,
                        dy2 = 1.41f,
                        dx3 = -0.226f,
                        dy3 = 2.188f,
                    )
                    // c 0.513 0.778 1.449 1.058 2.086 0.633z
                    curveToRelative(
                        dx1 = 0.513f,
                        dy1 = 0.778f,
                        dx2 = 1.449f,
                        dy2 = 1.058f,
                        dx3 = 2.086f,
                        dy3 = 0.633f,
                    )
                    close()
                }
                // M7.552 10.98 c.514 -.778 .422 -1.743 -.205 -2.168 -.627 -.414 -1.542 -.124 -2.056 .643 -.514 .778 -.421 1.742 .206 2.168 .627 .414 1.541 .124 2.055 -.643Z M18.694 10.991 c-.514 -.777 -.422 -1.742 .205 -2.167 .627 -.415 1.542 -.124 2.056 .643 .514 .778 .421 1.742 -.206 2.167 -.627 .415 -1.541 .125 -2.055 -.643Z
                path(
                    fill = SolidColor(Color(0xFF000000)),
                ) {
                    // M 7.552 10.98
                    moveTo(x = 7.552f, y = 10.98f)
                    // c 0.514 -0.778 0.422 -1.743 -0.205 -2.168
                    curveToRelative(
                        dx1 = 0.514f,
                        dy1 = -0.778f,
                        dx2 = 0.422f,
                        dy2 = -1.743f,
                        dx3 = -0.205f,
                        dy3 = -2.168f,
                    )
                    // c -0.627 -0.414 -1.542 -0.124 -2.056 0.643
                    curveToRelative(
                        dx1 = -0.627f,
                        dy1 = -0.414f,
                        dx2 = -1.542f,
                        dy2 = -0.124f,
                        dx3 = -2.056f,
                        dy3 = 0.643f,
                    )
                    // c -0.514 0.778 -0.421 1.742 0.206 2.168
                    curveToRelative(
                        dx1 = -0.514f,
                        dy1 = 0.778f,
                        dx2 = -0.421f,
                        dy2 = 1.742f,
                        dx3 = 0.206f,
                        dy3 = 2.168f,
                    )
                    // c 0.627 0.414 1.541 0.124 2.055 -0.643z
                    curveToRelative(
                        dx1 = 0.627f,
                        dy1 = 0.414f,
                        dx2 = 1.541f,
                        dy2 = 0.124f,
                        dx3 = 2.055f,
                        dy3 = -0.643f,
                    )
                    close()
                    // M 18.694 10.991
                    moveTo(x = 18.694f, y = 10.991f)
                    // c -0.514 -0.777 -0.422 -1.742 0.205 -2.167
                    curveToRelative(
                        dx1 = -0.514f,
                        dy1 = -0.777f,
                        dx2 = -0.422f,
                        dy2 = -1.742f,
                        dx3 = 0.205f,
                        dy3 = -2.167f,
                    )
                    // c 0.627 -0.415 1.542 -0.124 2.056 0.643
                    curveToRelative(
                        dx1 = 0.627f,
                        dy1 = -0.415f,
                        dx2 = 1.542f,
                        dy2 = -0.124f,
                        dx3 = 2.056f,
                        dy3 = 0.643f,
                    )
                    // c 0.514 0.778 0.421 1.742 -0.206 2.167
                    curveToRelative(
                        dx1 = 0.514f,
                        dy1 = 0.778f,
                        dx2 = 0.421f,
                        dy2 = 1.742f,
                        dx3 = -0.206f,
                        dy3 = 2.167f,
                    )
                    // c -0.627 0.415 -1.541 0.125 -2.055 -0.643z
                    curveToRelative(
                        dx1 = -0.627f,
                        dy1 = 0.415f,
                        dx2 = -1.541f,
                        dy2 = 0.125f,
                        dx3 = -2.055f,
                        dy3 = -0.643f,
                    )
                    close()
                }
                // M5.476 9.363 c-.493 .602 -.545 1.317 -.483 1.618 -.123 -.591 -.082 -1.162 .39 -1.742 .257 -.322 .658 -.498 .946 -.581 .041 -.01 .093 -.02 .123 -.031 a.688 .688 0 0 1 .175 -.031 c-.051 .01 -.113 .02 -.175 .03 -.04 0 -.082 .022 -.123 .032 -.206 .073 -.483 .228 -.863 .705 h.01Z
                path(
                    fill = SolidColor(Color(0xFF000000)),
                ) {
                    // M 5.476 9.363
                    moveTo(x = 5.476f, y = 9.363f)
                    // c -0.493 0.602 -0.545 1.317 -0.483 1.618
                    curveToRelative(
                        dx1 = -0.493f,
                        dy1 = 0.602f,
                        dx2 = -0.545f,
                        dy2 = 1.317f,
                        dx3 = -0.483f,
                        dy3 = 1.618f,
                    )
                    // c -0.123 -0.591 -0.082 -1.162 0.39 -1.742
                    curveToRelative(
                        dx1 = -0.123f,
                        dy1 = -0.591f,
                        dx2 = -0.082f,
                        dy2 = -1.162f,
                        dx3 = 0.39f,
                        dy3 = -1.742f,
                    )
                    // c 0.257 -0.322 0.658 -0.498 0.946 -0.581
                    curveToRelative(
                        dx1 = 0.257f,
                        dy1 = -0.322f,
                        dx2 = 0.658f,
                        dy2 = -0.498f,
                        dx3 = 0.946f,
                        dy3 = -0.581f,
                    )
                    // c 0.041 -0.01 0.093 -0.02 0.123 -0.031
                    curveToRelative(
                        dx1 = 0.041f,
                        dy1 = -0.01f,
                        dx2 = 0.093f,
                        dy2 = -0.02f,
                        dx3 = 0.123f,
                        dy3 = -0.031f,
                    )
                    // a 0.688 0.688 0 0 1 0.175 -0.031
                    arcToRelative(
                        a = 0.688f,
                        b = 0.688f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        dx1 = 0.175f,
                        dy1 = -0.031f,
                    )
                    // c -0.051 0.01 -0.113 0.02 -0.175 0.03
                    curveToRelative(
                        dx1 = -0.051f,
                        dy1 = 0.01f,
                        dx2 = -0.113f,
                        dy2 = 0.02f,
                        dx3 = -0.175f,
                        dy3 = 0.03f,
                    )
                    // c -0.04 0 -0.082 0.022 -0.123 0.032
                    curveToRelative(
                        dx1 = -0.04f,
                        dy1 = 0.0f,
                        dx2 = -0.082f,
                        dy2 = 0.022f,
                        dx3 = -0.123f,
                        dy3 = 0.032f,
                    )
                    // c -0.206 0.073 -0.483 0.228 -0.863 0.705
                    curveToRelative(
                        dx1 = -0.206f,
                        dy1 = 0.073f,
                        dx2 = -0.483f,
                        dy2 = 0.228f,
                        dx3 = -0.863f,
                        dy3 = 0.705f,
                    )
                    // h 0.01z
                    horizontalLineToRelative(dx = 0.01f)
                    close()
                }
                // M7.687 9.51 c-.205 -.861 -.894 -.944 -1.212 -.882 .277 -.125 .77 .03 .986 .28 .165 .197 .226 .487 .237 .612 l-.01 -.01Z
                path(
                    fill = Brush.linearGradient(
                        0.0f to Color(0xFFE2DDE2),
                        1.0f to Color(0x00E2DDE2),
                        start = Offset(x = 7.05f, y = 8.265f),
                        end = Offset(x = 7.102f, y = 9.478f),
                    ),
                    fillAlpha = 0.8f,
                    strokeAlpha = 0.8f,
                ) {
                    // M 7.687 9.51
                    moveTo(x = 7.687f, y = 9.51f)
                    // c -0.205 -0.861 -0.894 -0.944 -1.212 -0.882
                    curveToRelative(
                        dx1 = -0.205f,
                        dy1 = -0.861f,
                        dx2 = -0.894f,
                        dy2 = -0.944f,
                        dx3 = -1.212f,
                        dy3 = -0.882f,
                    )
                    // c 0.277 -0.125 0.77 0.03 0.986 0.28
                    curveToRelative(
                        dx1 = 0.277f,
                        dy1 = -0.125f,
                        dx2 = 0.77f,
                        dy2 = 0.03f,
                        dx3 = 0.986f,
                        dy3 = 0.28f,
                    )
                    // c 0.165 0.197 0.226 0.487 0.237 0.612
                    curveToRelative(
                        dx1 = 0.165f,
                        dy1 = 0.197f,
                        dx2 = 0.226f,
                        dy2 = 0.487f,
                        dx3 = 0.237f,
                        dy3 = 0.612f,
                    )
                    // l -0.01 -0.01z
                    lineToRelative(dx = -0.01f, dy = -0.01f)
                    close()
                }
                // M19.495 8.781 c-.73 .041 -.966 .622 -.997 .902 .072 -.176 .34 -.54 .802 -.632 .462 -.083 1.069 .445 1.315 .725 l.226 -.259 c-.144 -.27 -.616 -.788 -1.346 -.736Z
                path(
                    fill = Brush.linearGradient(
                        0.0f to Color(0xFF373637),
                        1.0f to Color(0x00373637),
                        start = Offset(x = 19.67f, y = 8.781f),
                        end = Offset(x = 19.67f, y = 9.776f),
                    ),
                ) {
                    // M 19.495 8.781
                    moveTo(x = 19.495f, y = 8.781f)
                    // c -0.73 0.041 -0.966 0.622 -0.997 0.902
                    curveToRelative(
                        dx1 = -0.73f,
                        dy1 = 0.041f,
                        dx2 = -0.966f,
                        dy2 = 0.622f,
                        dx3 = -0.997f,
                        dy3 = 0.902f,
                    )
                    // c 0.072 -0.176 0.34 -0.54 0.802 -0.632
                    curveToRelative(
                        dx1 = 0.072f,
                        dy1 = -0.176f,
                        dx2 = 0.34f,
                        dy2 = -0.54f,
                        dx3 = 0.802f,
                        dy3 = -0.632f,
                    )
                    // c 0.462 -0.083 1.069 0.445 1.315 0.725
                    curveToRelative(
                        dx1 = 0.462f,
                        dy1 = -0.083f,
                        dx2 = 1.069f,
                        dy2 = 0.445f,
                        dx3 = 1.315f,
                        dy3 = 0.725f,
                    )
                    // l 0.226 -0.259
                    lineToRelative(dx = 0.226f, dy = -0.259f)
                    // c -0.144 -0.27 -0.616 -0.788 -1.346 -0.736z
                    curveToRelative(
                        dx1 = -0.144f,
                        dy1 = -0.27f,
                        dx2 = -0.616f,
                        dy2 = -0.788f,
                        dx3 = -1.346f,
                        dy3 = -0.736f,
                    )
                    close()
                }
                // M20.768 9.383 c.494 .601 .545 1.317 .483 1.617 .124 -.59 .082 -1.161 -.39 -1.742 -.257 -.321 -.658 -.498 -.946 -.58 -.04 -.01 -.092 -.021 -.123 -.032 a.687 .687 0 0 0 -.175 -.03 c.052 .01 .113 .02 .175 .03 .041 0 .082 .021 .123 .031 .206 .073 .483 .229 .864 .706 h-.01Z
                path(
                    fill = SolidColor(Color(0xFF000000)),
                ) {
                    // M 20.768 9.383
                    moveTo(x = 20.768f, y = 9.383f)
                    // c 0.494 0.601 0.545 1.317 0.483 1.617
                    curveToRelative(
                        dx1 = 0.494f,
                        dy1 = 0.601f,
                        dx2 = 0.545f,
                        dy2 = 1.317f,
                        dx3 = 0.483f,
                        dy3 = 1.617f,
                    )
                    // c 0.124 -0.59 0.082 -1.161 -0.39 -1.742
                    curveToRelative(
                        dx1 = 0.124f,
                        dy1 = -0.59f,
                        dx2 = 0.082f,
                        dy2 = -1.161f,
                        dx3 = -0.39f,
                        dy3 = -1.742f,
                    )
                    // c -0.257 -0.321 -0.658 -0.498 -0.946 -0.58
                    curveToRelative(
                        dx1 = -0.257f,
                        dy1 = -0.321f,
                        dx2 = -0.658f,
                        dy2 = -0.498f,
                        dx3 = -0.946f,
                        dy3 = -0.58f,
                    )
                    // c -0.04 -0.01 -0.092 -0.021 -0.123 -0.032
                    curveToRelative(
                        dx1 = -0.04f,
                        dy1 = -0.01f,
                        dx2 = -0.092f,
                        dy2 = -0.021f,
                        dx3 = -0.123f,
                        dy3 = -0.032f,
                    )
                    // a 0.687 0.687 0 0 0 -0.175 -0.03
                    arcToRelative(
                        a = 0.687f,
                        b = 0.687f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -0.175f,
                        dy1 = -0.03f,
                    )
                    // c 0.052 0.01 0.113 0.02 0.175 0.03
                    curveToRelative(
                        dx1 = 0.052f,
                        dy1 = 0.01f,
                        dx2 = 0.113f,
                        dy2 = 0.02f,
                        dx3 = 0.175f,
                        dy3 = 0.03f,
                    )
                    // c 0.041 0 0.082 0.021 0.123 0.031
                    curveToRelative(
                        dx1 = 0.041f,
                        dy1 = 0.0f,
                        dx2 = 0.082f,
                        dy2 = 0.021f,
                        dx3 = 0.123f,
                        dy3 = 0.031f,
                    )
                    // c 0.206 0.073 0.483 0.229 0.864 0.706
                    curveToRelative(
                        dx1 = 0.206f,
                        dy1 = 0.073f,
                        dx2 = 0.483f,
                        dy2 = 0.229f,
                        dx3 = 0.864f,
                        dy3 = 0.706f,
                    )
                    // h -0.01z
                    horizontalLineToRelative(dx = -0.01f)
                    close()
                }
                // M6.122 9.139 c-.288 .062 -.504 .363 -.565 .508 l.144 .218 s.102 -.177 .215 -.29 c.113 -.115 .288 -.24 .36 -.291 l-.144 -.145 h-.01Z M20.132 9.146 c.278 .052 .493 .343 .565 .488 l-.143 .207 s-.103 -.166 -.216 -.28 c-.113 -.114 -.278 -.228 -.35 -.28 l.144 -.135Z
                path(
                    fill = SolidColor(Color(0xFFE2DCE1)),
                    fillAlpha = 0.8f,
                    strokeAlpha = 0.8f,
                ) {
                    // M 6.122 9.139
                    moveTo(x = 6.122f, y = 9.139f)
                    // c -0.288 0.062 -0.504 0.363 -0.565 0.508
                    curveToRelative(
                        dx1 = -0.288f,
                        dy1 = 0.062f,
                        dx2 = -0.504f,
                        dy2 = 0.363f,
                        dx3 = -0.565f,
                        dy3 = 0.508f,
                    )
                    // l 0.144 0.218
                    lineToRelative(dx = 0.144f, dy = 0.218f)
                    // s 0.102 -0.177 0.215 -0.29
                    reflectiveCurveToRelative(
                        dx1 = 0.102f,
                        dy1 = -0.177f,
                        dx2 = 0.215f,
                        dy2 = -0.29f,
                    )
                    // c 0.113 -0.115 0.288 -0.24 0.36 -0.291
                    curveToRelative(
                        dx1 = 0.113f,
                        dy1 = -0.115f,
                        dx2 = 0.288f,
                        dy2 = -0.24f,
                        dx3 = 0.36f,
                        dy3 = -0.291f,
                    )
                    // l -0.144 -0.145
                    lineToRelative(dx = -0.144f, dy = -0.145f)
                    // h -0.01z
                    horizontalLineToRelative(dx = -0.01f)
                    close()
                    // M 20.132 9.146
                    moveTo(x = 20.132f, y = 9.146f)
                    // c 0.278 0.052 0.493 0.343 0.565 0.488
                    curveToRelative(
                        dx1 = 0.278f,
                        dy1 = 0.052f,
                        dx2 = 0.493f,
                        dy2 = 0.343f,
                        dx3 = 0.565f,
                        dy3 = 0.488f,
                    )
                    // l -0.143 0.207
                    lineToRelative(dx = -0.143f, dy = 0.207f)
                    // s -0.103 -0.166 -0.216 -0.28
                    reflectiveCurveToRelative(
                        dx1 = -0.103f,
                        dy1 = -0.166f,
                        dx2 = -0.216f,
                        dy2 = -0.28f,
                    )
                    // c -0.113 -0.114 -0.278 -0.228 -0.35 -0.28
                    curveToRelative(
                        dx1 = -0.113f,
                        dy1 = -0.114f,
                        dx2 = -0.278f,
                        dy2 = -0.228f,
                        dx3 = -0.35f,
                        dy3 = -0.28f,
                    )
                    // l 0.144 -0.135z
                    lineToRelative(dx = 0.144f, dy = -0.135f)
                    close()
                }
                // M7.294 10.693 c-.134 .073 -.35 .177 -.442 .218 l-.082 .249 c.215 -.073 .37 -.197 .431 -.249 l.093 -.218Z M7.725 9.603 a1.633 1.633 0 0 0 -.082 -.218 v.093 c.04 .073 .072 .207 .082 .27 0 -.021 .02 -.083 0 -.156 v.01Z
                path(
                    fill = SolidColor(Color(0xFFE2DCE1)),
                    fillAlpha = 0.8f,
                    strokeAlpha = 0.8f,
                ) {
                    // M 7.294 10.693
                    moveTo(x = 7.294f, y = 10.693f)
                    // c -0.134 0.073 -0.35 0.177 -0.442 0.218
                    curveToRelative(
                        dx1 = -0.134f,
                        dy1 = 0.073f,
                        dx2 = -0.35f,
                        dy2 = 0.177f,
                        dx3 = -0.442f,
                        dy3 = 0.218f,
                    )
                    // l -0.082 0.249
                    lineToRelative(dx = -0.082f, dy = 0.249f)
                    // c 0.215 -0.073 0.37 -0.197 0.431 -0.249
                    curveToRelative(
                        dx1 = 0.215f,
                        dy1 = -0.073f,
                        dx2 = 0.37f,
                        dy2 = -0.197f,
                        dx3 = 0.431f,
                        dy3 = -0.249f,
                    )
                    // l 0.093 -0.218z
                    lineToRelative(dx = 0.093f, dy = -0.218f)
                    close()
                    // M 7.725 9.603
                    moveTo(x = 7.725f, y = 9.603f)
                    // a 1.633 1.633 0 0 0 -0.082 -0.218
                    arcToRelative(
                        a = 1.633f,
                        b = 1.633f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -0.082f,
                        dy1 = -0.218f,
                    )
                    // v 0.093
                    verticalLineToRelative(dy = 0.093f)
                    // c 0.04 0.073 0.072 0.207 0.082 0.27
                    curveToRelative(
                        dx1 = 0.04f,
                        dy1 = 0.073f,
                        dx2 = 0.072f,
                        dy2 = 0.207f,
                        dx3 = 0.082f,
                        dy3 = 0.27f,
                    )
                    // c 0 -0.021 0.02 -0.083 0 -0.156
                    curveToRelative(
                        dx1 = 0.0f,
                        dy1 = -0.021f,
                        dx2 = 0.02f,
                        dy2 = -0.083f,
                        dx3 = 0.0f,
                        dy3 = -0.156f,
                    )
                    // v 0.01z
                    verticalLineToRelative(dy = 0.01f)
                    close()
                }
                // M18.93 10.715 c.133 .072 .35 .176 .442 .218 l.082 .248 a1.346 1.346 0 0 1 -.432 -.248 l-.092 -.218Z M18.507 9.624 c.02 -.073 .062 -.176 .082 -.218 V9.5 a1.004 1.004 0 0 0 -.082 .27 c0 -.022 -.02 -.084 0 -.156 v.01Z
                path(
                    fill = SolidColor(Color(0xFFE2DCE1)),
                    fillAlpha = 0.8f,
                    strokeAlpha = 0.8f,
                ) {
                    // M 18.93 10.715
                    moveTo(x = 18.93f, y = 10.715f)
                    // c 0.133 0.072 0.35 0.176 0.442 0.218
                    curveToRelative(
                        dx1 = 0.133f,
                        dy1 = 0.072f,
                        dx2 = 0.35f,
                        dy2 = 0.176f,
                        dx3 = 0.442f,
                        dy3 = 0.218f,
                    )
                    // l 0.082 0.248
                    lineToRelative(dx = 0.082f, dy = 0.248f)
                    // a 1.346 1.346 0 0 1 -0.432 -0.248
                    arcToRelative(
                        a = 1.346f,
                        b = 1.346f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        dx1 = -0.432f,
                        dy1 = -0.248f,
                    )
                    // l -0.092 -0.218z
                    lineToRelative(dx = -0.092f, dy = -0.218f)
                    close()
                    // M 18.507 9.624
                    moveTo(x = 18.507f, y = 9.624f)
                    // c 0.02 -0.073 0.062 -0.176 0.082 -0.218
                    curveToRelative(
                        dx1 = 0.02f,
                        dy1 = -0.073f,
                        dx2 = 0.062f,
                        dy2 = -0.176f,
                        dx3 = 0.082f,
                        dy3 = -0.218f,
                    )
                    // V 9.5
                    verticalLineTo(y = 9.5f)
                    // a 1.004 1.004 0 0 0 -0.082 0.27
                    arcToRelative(
                        a = 1.004f,
                        b = 1.004f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -0.082f,
                        dy1 = 0.27f,
                    )
                    // c 0 -0.022 -0.02 -0.084 0 -0.156
                    curveToRelative(
                        dx1 = 0.0f,
                        dy1 = -0.022f,
                        dx2 = -0.02f,
                        dy2 = -0.084f,
                        dx3 = 0.0f,
                        dy3 = -0.156f,
                    )
                    // v 0.01z
                    verticalLineToRelative(dy = 0.01f)
                    close()
                }
            }
            // M35.876 0 c4.557 0 8.159 3.399 8.159 7.662 s-3.602 7.682 -8.159 7.682 h-4.108 V0 h4.108Z m-1.713 13.104 h1.713 c3.291 0 5.745 -2.338 5.745 -5.442 S39.166 2.24 35.876 2.24 h-1.713 v10.864Z m10.943 -2.672 c0 2.928 2.2 5.226 5.14 5.226 2.376 0 3.759 -1.473 4.323 -2.318 l-1.772 -1.198 c-.584 .883 -1.401 1.473 -2.55 1.473 -1.344 0 -2.493 -1.061 -2.785 -2.495 H54.9 c.04 -.236 .059 -.491 .059 -.727 0 -2.927 -1.947 -5.187 -4.849 -5.187 -2.901 0 -5.004 2.26 -5.004 5.226Z m2.395 -1.021 c.292 -1.277 1.207 -2.161 2.61 -2.161 1.401 0 2.317 .884 2.609 2.16 H47.5Z m7.419 -3.89 3.602 9.823 h2.629 l3.602 -9.823 h-2.336 l-2.532 7.347 h-.097 l-2.531 -7.347 H54.92Z m9.775 4.911 c0 2.928 2.2 5.226 5.14 5.226 2.376 0 3.759 -1.473 4.323 -2.318 l-1.772 -1.198 c-.584 .883 -1.402 1.473 -2.55 1.473 -1.344 0 -2.493 -1.061 -2.785 -2.495 h7.438 c.04 -.236 .059 -.491 .059 -.727 0 -2.927 -1.947 -5.187 -4.849 -5.187 -2.901 0 -5.004 2.26 -5.004 5.226Z m2.395 -1.021 c.292 -1.277 1.207 -2.161 2.61 -2.161 1.401 0 2.316 .884 2.608 2.16 H67.09Z m8.607 5.933 h2.239 V0 h-2.24 v15.344Z m3.388 -4.951 c0 2.947 2.297 5.265 5.218 5.265 s5.219 -2.318 5.219 -5.265 c0 -2.947 -2.259 -5.187 -5.219 -5.187 -2.96 0 -5.218 2.26 -5.218 5.187Z m2.278 0 c0 -1.768 1.285 -3.143 2.94 -3.143 1.656 0 2.94 1.375 2.94 3.143 0 1.768 -1.304 3.222 -2.94 3.222 -1.635 0 -2.94 -1.434 -2.94 -3.222Z M90.671 20 h2.239 v-5.462 h.097 c.584 .708 1.597 1.12 2.727 1.12 2.687 0 4.926 -2.377 4.926 -5.206 0 -2.83 -2.259 -5.246 -4.926 -5.246 -1.11 0 -2.123 .452 -2.727 1.199 h-.097 V5.52 h-2.24 V20Z m2.083 -9.548 c0 -1.827 1.188 -3.202 2.765 -3.202 1.578 0 2.863 1.414 2.863 3.202 s-1.266 3.163 -2.863 3.163 -2.765 -1.356 -2.765 -3.163Z m8.938 -.02 c0 2.928 2.2 5.226 5.141 5.226 2.375 0 3.758 -1.473 4.322 -2.318 l-1.772 -1.198 c-.584 .883 -1.402 1.473 -2.55 1.473 -1.344 0 -2.493 -1.061 -2.785 -2.495 h7.438 c.039 -.236 .059 -.491 .059 -.727 0 -2.927 -1.947 -5.187 -4.849 -5.187 -2.901 0 -5.004 2.26 -5.004 5.226Z m2.395 -1.021 c.292 -1.277 1.207 -2.161 2.609 -2.161 s2.317 .884 2.61 2.16 h-5.219Z m8.548 5.933 h2.24 v-5.147 c0 -2.044 1.071 -2.947 2.862 -2.947 .331 0 .584 .02 .818 .078 V5.344 c-.234 -.079 -.643 -.138 -1.091 -.138 -3.037 0 -4.829 1.926 -4.829 4.99 v5.148Z m6.309 -2.633 c.175 1.71 1.947 2.947 4.187 2.947 2.356 0 3.875 -1.218 3.875 -3.124 0 -1.591 -.916 -2.495 -2.96 -2.907 l-1.363 -.275 c-.818 -.158 -1.246 -.55 -1.246 -1.16 0 -.766 .603 -1.257 1.557 -1.257 1.071 0 1.772 .53 1.928 1.513 l2.006 -.472 c-.292 -1.67 -1.87 -2.77 -3.934 -2.77 -2.239 0 -3.758 1.238 -3.758 3.085 0 1.513 1.032 2.593 2.863 2.966 l1.363 .275 c.934 .197 1.363 .57 1.363 1.219 0 .726 -.643 1.198 -1.694 1.198 -1.208 0 -1.986 -.629 -2.181 -1.69 l-2.006 .452Z
            path(
                fill = SolidColor(Color(0xFF202124)),
            ) {
                // M 35.876 0
                moveTo(x = 35.876f, y = 0.0f)
                // c 4.557 0 8.159 3.399 8.159 7.662
                curveToRelative(
                    dx1 = 4.557f,
                    dy1 = 0.0f,
                    dx2 = 8.159f,
                    dy2 = 3.399f,
                    dx3 = 8.159f,
                    dy3 = 7.662f,
                )
                // s -3.602 7.682 -8.159 7.682
                reflectiveCurveToRelative(
                    dx1 = -3.602f,
                    dy1 = 7.682f,
                    dx2 = -8.159f,
                    dy2 = 7.682f,
                )
                // h -4.108
                horizontalLineToRelative(dx = -4.108f)
                // V 0
                verticalLineTo(y = 0.0f)
                // h 4.108z
                horizontalLineToRelative(dx = 4.108f)
                close()
                // m -1.713 13.104
                moveToRelative(dx = -1.713f, dy = 13.104f)
                // h 1.713
                horizontalLineToRelative(dx = 1.713f)
                // c 3.291 0 5.745 -2.338 5.745 -5.442
                curveToRelative(
                    dx1 = 3.291f,
                    dy1 = 0.0f,
                    dx2 = 5.745f,
                    dy2 = -2.338f,
                    dx3 = 5.745f,
                    dy3 = -5.442f,
                )
                // S 39.166 2.24 35.876 2.24
                reflectiveCurveTo(
                    x1 = 39.166f,
                    y1 = 2.24f,
                    x2 = 35.876f,
                    y2 = 2.24f,
                )
                // h -1.713
                horizontalLineToRelative(dx = -1.713f)
                // v 10.864z
                verticalLineToRelative(dy = 10.864f)
                close()
                // m 10.943 -2.672
                moveToRelative(dx = 10.943f, dy = -2.672f)
                // c 0 2.928 2.2 5.226 5.14 5.226
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = 2.928f,
                    dx2 = 2.2f,
                    dy2 = 5.226f,
                    dx3 = 5.14f,
                    dy3 = 5.226f,
                )
                // c 2.376 0 3.759 -1.473 4.323 -2.318
                curveToRelative(
                    dx1 = 2.376f,
                    dy1 = 0.0f,
                    dx2 = 3.759f,
                    dy2 = -1.473f,
                    dx3 = 4.323f,
                    dy3 = -2.318f,
                )
                // l -1.772 -1.198
                lineToRelative(dx = -1.772f, dy = -1.198f)
                // c -0.584 0.883 -1.401 1.473 -2.55 1.473
                curveToRelative(
                    dx1 = -0.584f,
                    dy1 = 0.883f,
                    dx2 = -1.401f,
                    dy2 = 1.473f,
                    dx3 = -2.55f,
                    dy3 = 1.473f,
                )
                // c -1.344 0 -2.493 -1.061 -2.785 -2.495
                curveToRelative(
                    dx1 = -1.344f,
                    dy1 = 0.0f,
                    dx2 = -2.493f,
                    dy2 = -1.061f,
                    dx3 = -2.785f,
                    dy3 = -2.495f,
                )
                // H 54.9
                horizontalLineTo(x = 54.9f)
                // c 0.04 -0.236 0.059 -0.491 0.059 -0.727
                curveToRelative(
                    dx1 = 0.04f,
                    dy1 = -0.236f,
                    dx2 = 0.059f,
                    dy2 = -0.491f,
                    dx3 = 0.059f,
                    dy3 = -0.727f,
                )
                // c 0 -2.927 -1.947 -5.187 -4.849 -5.187
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = -2.927f,
                    dx2 = -1.947f,
                    dy2 = -5.187f,
                    dx3 = -4.849f,
                    dy3 = -5.187f,
                )
                // c -2.901 0 -5.004 2.26 -5.004 5.226z
                curveToRelative(
                    dx1 = -2.901f,
                    dy1 = 0.0f,
                    dx2 = -5.004f,
                    dy2 = 2.26f,
                    dx3 = -5.004f,
                    dy3 = 5.226f,
                )
                close()
                // m 2.395 -1.021
                moveToRelative(dx = 2.395f, dy = -1.021f)
                // c 0.292 -1.277 1.207 -2.161 2.61 -2.161
                curveToRelative(
                    dx1 = 0.292f,
                    dy1 = -1.277f,
                    dx2 = 1.207f,
                    dy2 = -2.161f,
                    dx3 = 2.61f,
                    dy3 = -2.161f,
                )
                // c 1.401 0 2.317 0.884 2.609 2.16
                curveToRelative(
                    dx1 = 1.401f,
                    dy1 = 0.0f,
                    dx2 = 2.317f,
                    dy2 = 0.884f,
                    dx3 = 2.609f,
                    dy3 = 2.16f,
                )
                // H 47.5z
                horizontalLineTo(x = 47.5f)
                close()
                // m 7.419 -3.89
                moveToRelative(dx = 7.419f, dy = -3.89f)
                // l 3.602 9.823
                lineToRelative(dx = 3.602f, dy = 9.823f)
                // h 2.629
                horizontalLineToRelative(dx = 2.629f)
                // l 3.602 -9.823
                lineToRelative(dx = 3.602f, dy = -9.823f)
                // h -2.336
                horizontalLineToRelative(dx = -2.336f)
                // l -2.532 7.347
                lineToRelative(dx = -2.532f, dy = 7.347f)
                // h -0.097
                horizontalLineToRelative(dx = -0.097f)
                // l -2.531 -7.347
                lineToRelative(dx = -2.531f, dy = -7.347f)
                // H 54.92z
                horizontalLineTo(x = 54.92f)
                close()
                // m 9.775 4.911
                moveToRelative(dx = 9.775f, dy = 4.911f)
                // c 0 2.928 2.2 5.226 5.14 5.226
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = 2.928f,
                    dx2 = 2.2f,
                    dy2 = 5.226f,
                    dx3 = 5.14f,
                    dy3 = 5.226f,
                )
                // c 2.376 0 3.759 -1.473 4.323 -2.318
                curveToRelative(
                    dx1 = 2.376f,
                    dy1 = 0.0f,
                    dx2 = 3.759f,
                    dy2 = -1.473f,
                    dx3 = 4.323f,
                    dy3 = -2.318f,
                )
                // l -1.772 -1.198
                lineToRelative(dx = -1.772f, dy = -1.198f)
                // c -0.584 0.883 -1.402 1.473 -2.55 1.473
                curveToRelative(
                    dx1 = -0.584f,
                    dy1 = 0.883f,
                    dx2 = -1.402f,
                    dy2 = 1.473f,
                    dx3 = -2.55f,
                    dy3 = 1.473f,
                )
                // c -1.344 0 -2.493 -1.061 -2.785 -2.495
                curveToRelative(
                    dx1 = -1.344f,
                    dy1 = 0.0f,
                    dx2 = -2.493f,
                    dy2 = -1.061f,
                    dx3 = -2.785f,
                    dy3 = -2.495f,
                )
                // h 7.438
                horizontalLineToRelative(dx = 7.438f)
                // c 0.04 -0.236 0.059 -0.491 0.059 -0.727
                curveToRelative(
                    dx1 = 0.04f,
                    dy1 = -0.236f,
                    dx2 = 0.059f,
                    dy2 = -0.491f,
                    dx3 = 0.059f,
                    dy3 = -0.727f,
                )
                // c 0 -2.927 -1.947 -5.187 -4.849 -5.187
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = -2.927f,
                    dx2 = -1.947f,
                    dy2 = -5.187f,
                    dx3 = -4.849f,
                    dy3 = -5.187f,
                )
                // c -2.901 0 -5.004 2.26 -5.004 5.226z
                curveToRelative(
                    dx1 = -2.901f,
                    dy1 = 0.0f,
                    dx2 = -5.004f,
                    dy2 = 2.26f,
                    dx3 = -5.004f,
                    dy3 = 5.226f,
                )
                close()
                // m 2.395 -1.021
                moveToRelative(dx = 2.395f, dy = -1.021f)
                // c 0.292 -1.277 1.207 -2.161 2.61 -2.161
                curveToRelative(
                    dx1 = 0.292f,
                    dy1 = -1.277f,
                    dx2 = 1.207f,
                    dy2 = -2.161f,
                    dx3 = 2.61f,
                    dy3 = -2.161f,
                )
                // c 1.401 0 2.316 0.884 2.608 2.16
                curveToRelative(
                    dx1 = 1.401f,
                    dy1 = 0.0f,
                    dx2 = 2.316f,
                    dy2 = 0.884f,
                    dx3 = 2.608f,
                    dy3 = 2.16f,
                )
                // H 67.09z
                horizontalLineTo(x = 67.09f)
                close()
                // m 8.607 5.933
                moveToRelative(dx = 8.607f, dy = 5.933f)
                // h 2.239
                horizontalLineToRelative(dx = 2.239f)
                // V 0
                verticalLineTo(y = 0.0f)
                // h -2.24
                horizontalLineToRelative(dx = -2.24f)
                // v 15.344z
                verticalLineToRelative(dy = 15.344f)
                close()
                // m 3.388 -4.951
                moveToRelative(dx = 3.388f, dy = -4.951f)
                // c 0 2.947 2.297 5.265 5.218 5.265
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = 2.947f,
                    dx2 = 2.297f,
                    dy2 = 5.265f,
                    dx3 = 5.218f,
                    dy3 = 5.265f,
                )
                // s 5.219 -2.318 5.219 -5.265
                reflectiveCurveToRelative(
                    dx1 = 5.219f,
                    dy1 = -2.318f,
                    dx2 = 5.219f,
                    dy2 = -5.265f,
                )
                // c 0 -2.947 -2.259 -5.187 -5.219 -5.187
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = -2.947f,
                    dx2 = -2.259f,
                    dy2 = -5.187f,
                    dx3 = -5.219f,
                    dy3 = -5.187f,
                )
                // c -2.96 0 -5.218 2.26 -5.218 5.187z
                curveToRelative(
                    dx1 = -2.96f,
                    dy1 = 0.0f,
                    dx2 = -5.218f,
                    dy2 = 2.26f,
                    dx3 = -5.218f,
                    dy3 = 5.187f,
                )
                close()
                // m 2.278 0
                moveToRelative(dx = 2.278f, dy = 0.0f)
                // c 0 -1.768 1.285 -3.143 2.94 -3.143
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = -1.768f,
                    dx2 = 1.285f,
                    dy2 = -3.143f,
                    dx3 = 2.94f,
                    dy3 = -3.143f,
                )
                // c 1.656 0 2.94 1.375 2.94 3.143
                curveToRelative(
                    dx1 = 1.656f,
                    dy1 = 0.0f,
                    dx2 = 2.94f,
                    dy2 = 1.375f,
                    dx3 = 2.94f,
                    dy3 = 3.143f,
                )
                // c 0 1.768 -1.304 3.222 -2.94 3.222
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = 1.768f,
                    dx2 = -1.304f,
                    dy2 = 3.222f,
                    dx3 = -2.94f,
                    dy3 = 3.222f,
                )
                // c -1.635 0 -2.94 -1.434 -2.94 -3.222z
                curveToRelative(
                    dx1 = -1.635f,
                    dy1 = 0.0f,
                    dx2 = -2.94f,
                    dy2 = -1.434f,
                    dx3 = -2.94f,
                    dy3 = -3.222f,
                )
                close()
                // M 90.671 20
                moveTo(x = 90.671f, y = 20.0f)
                // h 2.239
                horizontalLineToRelative(dx = 2.239f)
                // v -5.462
                verticalLineToRelative(dy = -5.462f)
                // h 0.097
                horizontalLineToRelative(dx = 0.097f)
                // c 0.584 0.708 1.597 1.12 2.727 1.12
                curveToRelative(
                    dx1 = 0.584f,
                    dy1 = 0.708f,
                    dx2 = 1.597f,
                    dy2 = 1.12f,
                    dx3 = 2.727f,
                    dy3 = 1.12f,
                )
                // c 2.687 0 4.926 -2.377 4.926 -5.206
                curveToRelative(
                    dx1 = 2.687f,
                    dy1 = 0.0f,
                    dx2 = 4.926f,
                    dy2 = -2.377f,
                    dx3 = 4.926f,
                    dy3 = -5.206f,
                )
                // c 0 -2.83 -2.259 -5.246 -4.926 -5.246
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = -2.83f,
                    dx2 = -2.259f,
                    dy2 = -5.246f,
                    dx3 = -4.926f,
                    dy3 = -5.246f,
                )
                // c -1.11 0 -2.123 0.452 -2.727 1.199
                curveToRelative(
                    dx1 = -1.11f,
                    dy1 = 0.0f,
                    dx2 = -2.123f,
                    dy2 = 0.452f,
                    dx3 = -2.727f,
                    dy3 = 1.199f,
                )
                // h -0.097
                horizontalLineToRelative(dx = -0.097f)
                // V 5.52
                verticalLineTo(y = 5.52f)
                // h -2.24
                horizontalLineToRelative(dx = -2.24f)
                // V 20z
                verticalLineTo(y = 20.0f)
                close()
                // m 2.083 -9.548
                moveToRelative(dx = 2.083f, dy = -9.548f)
                // c 0 -1.827 1.188 -3.202 2.765 -3.202
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = -1.827f,
                    dx2 = 1.188f,
                    dy2 = -3.202f,
                    dx3 = 2.765f,
                    dy3 = -3.202f,
                )
                // c 1.578 0 2.863 1.414 2.863 3.202
                curveToRelative(
                    dx1 = 1.578f,
                    dy1 = 0.0f,
                    dx2 = 2.863f,
                    dy2 = 1.414f,
                    dx3 = 2.863f,
                    dy3 = 3.202f,
                )
                // s -1.266 3.163 -2.863 3.163
                reflectiveCurveToRelative(
                    dx1 = -1.266f,
                    dy1 = 3.163f,
                    dx2 = -2.863f,
                    dy2 = 3.163f,
                )
                // s -2.765 -1.356 -2.765 -3.163z
                reflectiveCurveToRelative(
                    dx1 = -2.765f,
                    dy1 = -1.356f,
                    dx2 = -2.765f,
                    dy2 = -3.163f,
                )
                close()
                // m 8.938 -0.02
                moveToRelative(dx = 8.938f, dy = -0.02f)
                // c 0 2.928 2.2 5.226 5.141 5.226
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = 2.928f,
                    dx2 = 2.2f,
                    dy2 = 5.226f,
                    dx3 = 5.141f,
                    dy3 = 5.226f,
                )
                // c 2.375 0 3.758 -1.473 4.322 -2.318
                curveToRelative(
                    dx1 = 2.375f,
                    dy1 = 0.0f,
                    dx2 = 3.758f,
                    dy2 = -1.473f,
                    dx3 = 4.322f,
                    dy3 = -2.318f,
                )
                // l -1.772 -1.198
                lineToRelative(dx = -1.772f, dy = -1.198f)
                // c -0.584 0.883 -1.402 1.473 -2.55 1.473
                curveToRelative(
                    dx1 = -0.584f,
                    dy1 = 0.883f,
                    dx2 = -1.402f,
                    dy2 = 1.473f,
                    dx3 = -2.55f,
                    dy3 = 1.473f,
                )
                // c -1.344 0 -2.493 -1.061 -2.785 -2.495
                curveToRelative(
                    dx1 = -1.344f,
                    dy1 = 0.0f,
                    dx2 = -2.493f,
                    dy2 = -1.061f,
                    dx3 = -2.785f,
                    dy3 = -2.495f,
                )
                // h 7.438
                horizontalLineToRelative(dx = 7.438f)
                // c 0.039 -0.236 0.059 -0.491 0.059 -0.727
                curveToRelative(
                    dx1 = 0.039f,
                    dy1 = -0.236f,
                    dx2 = 0.059f,
                    dy2 = -0.491f,
                    dx3 = 0.059f,
                    dy3 = -0.727f,
                )
                // c 0 -2.927 -1.947 -5.187 -4.849 -5.187
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = -2.927f,
                    dx2 = -1.947f,
                    dy2 = -5.187f,
                    dx3 = -4.849f,
                    dy3 = -5.187f,
                )
                // c -2.901 0 -5.004 2.26 -5.004 5.226z
                curveToRelative(
                    dx1 = -2.901f,
                    dy1 = 0.0f,
                    dx2 = -5.004f,
                    dy2 = 2.26f,
                    dx3 = -5.004f,
                    dy3 = 5.226f,
                )
                close()
                // m 2.395 -1.021
                moveToRelative(dx = 2.395f, dy = -1.021f)
                // c 0.292 -1.277 1.207 -2.161 2.609 -2.161
                curveToRelative(
                    dx1 = 0.292f,
                    dy1 = -1.277f,
                    dx2 = 1.207f,
                    dy2 = -2.161f,
                    dx3 = 2.609f,
                    dy3 = -2.161f,
                )
                // s 2.317 0.884 2.61 2.16
                reflectiveCurveToRelative(
                    dx1 = 2.317f,
                    dy1 = 0.884f,
                    dx2 = 2.61f,
                    dy2 = 2.16f,
                )
                // h -5.219z
                horizontalLineToRelative(dx = -5.219f)
                close()
                // m 8.548 5.933
                moveToRelative(dx = 8.548f, dy = 5.933f)
                // h 2.24
                horizontalLineToRelative(dx = 2.24f)
                // v -5.147
                verticalLineToRelative(dy = -5.147f)
                // c 0 -2.044 1.071 -2.947 2.862 -2.947
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = -2.044f,
                    dx2 = 1.071f,
                    dy2 = -2.947f,
                    dx3 = 2.862f,
                    dy3 = -2.947f,
                )
                // c 0.331 0 0.584 0.02 0.818 0.078
                curveToRelative(
                    dx1 = 0.331f,
                    dy1 = 0.0f,
                    dx2 = 0.584f,
                    dy2 = 0.02f,
                    dx3 = 0.818f,
                    dy3 = 0.078f,
                )
                // V 5.344
                verticalLineTo(y = 5.344f)
                // c -0.234 -0.079 -0.643 -0.138 -1.091 -0.138
                curveToRelative(
                    dx1 = -0.234f,
                    dy1 = -0.079f,
                    dx2 = -0.643f,
                    dy2 = -0.138f,
                    dx3 = -1.091f,
                    dy3 = -0.138f,
                )
                // c -3.037 0 -4.829 1.926 -4.829 4.99
                curveToRelative(
                    dx1 = -3.037f,
                    dy1 = 0.0f,
                    dx2 = -4.829f,
                    dy2 = 1.926f,
                    dx3 = -4.829f,
                    dy3 = 4.99f,
                )
                // v 5.148z
                verticalLineToRelative(dy = 5.148f)
                close()
                // m 6.309 -2.633
                moveToRelative(dx = 6.309f, dy = -2.633f)
                // c 0.175 1.71 1.947 2.947 4.187 2.947
                curveToRelative(
                    dx1 = 0.175f,
                    dy1 = 1.71f,
                    dx2 = 1.947f,
                    dy2 = 2.947f,
                    dx3 = 4.187f,
                    dy3 = 2.947f,
                )
                // c 2.356 0 3.875 -1.218 3.875 -3.124
                curveToRelative(
                    dx1 = 2.356f,
                    dy1 = 0.0f,
                    dx2 = 3.875f,
                    dy2 = -1.218f,
                    dx3 = 3.875f,
                    dy3 = -3.124f,
                )
                // c 0 -1.591 -0.916 -2.495 -2.96 -2.907
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = -1.591f,
                    dx2 = -0.916f,
                    dy2 = -2.495f,
                    dx3 = -2.96f,
                    dy3 = -2.907f,
                )
                // l -1.363 -0.275
                lineToRelative(dx = -1.363f, dy = -0.275f)
                // c -0.818 -0.158 -1.246 -0.55 -1.246 -1.16
                curveToRelative(
                    dx1 = -0.818f,
                    dy1 = -0.158f,
                    dx2 = -1.246f,
                    dy2 = -0.55f,
                    dx3 = -1.246f,
                    dy3 = -1.16f,
                )
                // c 0 -0.766 0.603 -1.257 1.557 -1.257
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = -0.766f,
                    dx2 = 0.603f,
                    dy2 = -1.257f,
                    dx3 = 1.557f,
                    dy3 = -1.257f,
                )
                // c 1.071 0 1.772 0.53 1.928 1.513
                curveToRelative(
                    dx1 = 1.071f,
                    dy1 = 0.0f,
                    dx2 = 1.772f,
                    dy2 = 0.53f,
                    dx3 = 1.928f,
                    dy3 = 1.513f,
                )
                // l 2.006 -0.472
                lineToRelative(dx = 2.006f, dy = -0.472f)
                // c -0.292 -1.67 -1.87 -2.77 -3.934 -2.77
                curveToRelative(
                    dx1 = -0.292f,
                    dy1 = -1.67f,
                    dx2 = -1.87f,
                    dy2 = -2.77f,
                    dx3 = -3.934f,
                    dy3 = -2.77f,
                )
                // c -2.239 0 -3.758 1.238 -3.758 3.085
                curveToRelative(
                    dx1 = -2.239f,
                    dy1 = 0.0f,
                    dx2 = -3.758f,
                    dy2 = 1.238f,
                    dx3 = -3.758f,
                    dy3 = 3.085f,
                )
                // c 0 1.513 1.032 2.593 2.863 2.966
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = 1.513f,
                    dx2 = 1.032f,
                    dy2 = 2.593f,
                    dx3 = 2.863f,
                    dy3 = 2.966f,
                )
                // l 1.363 0.275
                lineToRelative(dx = 1.363f, dy = 0.275f)
                // c 0.934 0.197 1.363 0.57 1.363 1.219
                curveToRelative(
                    dx1 = 0.934f,
                    dy1 = 0.197f,
                    dx2 = 1.363f,
                    dy2 = 0.57f,
                    dx3 = 1.363f,
                    dy3 = 1.219f,
                )
                // c 0 0.726 -0.643 1.198 -1.694 1.198
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = 0.726f,
                    dx2 = -0.643f,
                    dy2 = 1.198f,
                    dx3 = -1.694f,
                    dy3 = 1.198f,
                )
                // c -1.208 0 -1.986 -0.629 -2.181 -1.69
                curveToRelative(
                    dx1 = -1.208f,
                    dy1 = 0.0f,
                    dx2 = -1.986f,
                    dy2 = -0.629f,
                    dx3 = -2.181f,
                    dy3 = -1.69f,
                )
                // l -2.006 0.452z
                lineToRelative(dx = -2.006f, dy = 0.452f)
                close()
            }
        }.build().also { _androidDevelopers = it }
    }

@Suppress("ObjectPropertyName")
private var _androidDevelopers: ImageVector? = null
