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

val Android: ImageVector
    get() {
        val current = _android
        if (current != null) return current

        return ImageVector.Builder(
            name = ".Android",
            defaultWidth = 95.0.dp,
            defaultHeight = 95.0.dp,
            viewportWidth = 95.0f,
            viewportHeight = 95.0f,
        ).apply {
            group(
                // M85.122 64.795 a38.463 38.463 0 0 0 -3.26 -8.647 38.68 38.68 0 0 0 -9.074 -11.447 38.79 38.79 0 0 0 -4.813 -3.542 c.016 -.024 .029 -.05 .044 -.075 .776 -1.341 1.555 -2.68 2.331 -4.022 l2.28 -3.928 1.636 -2.822 a3.543 3.543 0 0 0 -1.251 -4.816 3.569 3.569 0 0 0 -2.173 -.478 3.535 3.535 0 0 0 -2.698 1.743 l-1.636 2.822 -2.279 3.928 c-.776 1.341 -1.555 2.68 -2.332 4.022 l-.255 .44 c-.118 -.046 -.234 -.093 -.353 -.138 a38.633 38.633 0 0 0 -13.786 -2.525 c-.133 0 -.265 0 -.398 .002 a38.652 38.652 0 0 0 -12.338 2.14 c-.449 .156 -.891 .321 -1.33 .494 l-.239 -.411 c-.776 -1.341 -1.555 -2.68 -2.331 -4.022 a136999.438 136999.438 0 0 0 -3.916 -6.75 3.534 3.534 0 0 0 -3.364 -1.748 3.517 3.517 0 0 0 -1.506 .484 3.526 3.526 0 0 0 -1.61 2.13 3.542 3.542 0 0 0 .359 2.685 l1.636 2.822 2.28 3.928 c.776 1.341 1.554 2.68 2.331 4.022 l.019 .034 a38.894 38.894 0 0 0 -11.142 10.451 38.38 38.38 0 0 0 -2.81 4.581 38.494 38.494 0 0 0 -3.26 8.647 c-.65 2.647 1.496 5.025 4.223 5.025 H80.89 c2.727 0 4.88 -2.38 4.231 -5.03Z
                clipPathData = PathData {
                    // M 85.122 64.795
                    moveTo(x = 85.122f, y = 64.795f)
                    // a 38.463 38.463 0 0 0 -3.26 -8.647
                    arcToRelative(
                        a = 38.463f,
                        b = 38.463f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -3.26f,
                        dy1 = -8.647f,
                    )
                    // a 38.68 38.68 0 0 0 -9.074 -11.447
                    arcToRelative(
                        a = 38.68f,
                        b = 38.68f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -9.074f,
                        dy1 = -11.447f,
                    )
                    // a 38.79 38.79 0 0 0 -4.813 -3.542
                    arcToRelative(
                        a = 38.79f,
                        b = 38.79f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -4.813f,
                        dy1 = -3.542f,
                    )
                    // c 0.016 -0.024 0.029 -0.05 0.044 -0.075
                    curveToRelative(
                        dx1 = 0.016f,
                        dy1 = -0.024f,
                        dx2 = 0.029f,
                        dy2 = -0.05f,
                        dx3 = 0.044f,
                        dy3 = -0.075f,
                    )
                    // c 0.776 -1.341 1.555 -2.68 2.331 -4.022
                    curveToRelative(
                        dx1 = 0.776f,
                        dy1 = -1.341f,
                        dx2 = 1.555f,
                        dy2 = -2.68f,
                        dx3 = 2.331f,
                        dy3 = -4.022f,
                    )
                    // l 2.28 -3.928
                    lineToRelative(dx = 2.28f, dy = -3.928f)
                    // l 1.636 -2.822
                    lineToRelative(dx = 1.636f, dy = -2.822f)
                    // a 3.543 3.543 0 0 0 -1.251 -4.816
                    arcToRelative(
                        a = 3.543f,
                        b = 3.543f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -1.251f,
                        dy1 = -4.816f,
                    )
                    // a 3.569 3.569 0 0 0 -2.173 -0.478
                    arcToRelative(
                        a = 3.569f,
                        b = 3.569f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -2.173f,
                        dy1 = -0.478f,
                    )
                    // a 3.535 3.535 0 0 0 -2.698 1.743
                    arcToRelative(
                        a = 3.535f,
                        b = 3.535f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -2.698f,
                        dy1 = 1.743f,
                    )
                    // l -1.636 2.822
                    lineToRelative(dx = -1.636f, dy = 2.822f)
                    // l -2.279 3.928
                    lineToRelative(dx = -2.279f, dy = 3.928f)
                    // c -0.776 1.341 -1.555 2.68 -2.332 4.022
                    curveToRelative(
                        dx1 = -0.776f,
                        dy1 = 1.341f,
                        dx2 = -1.555f,
                        dy2 = 2.68f,
                        dx3 = -2.332f,
                        dy3 = 4.022f,
                    )
                    // l -0.255 0.44
                    lineToRelative(dx = -0.255f, dy = 0.44f)
                    // c -0.118 -0.046 -0.234 -0.093 -0.353 -0.138
                    curveToRelative(
                        dx1 = -0.118f,
                        dy1 = -0.046f,
                        dx2 = -0.234f,
                        dy2 = -0.093f,
                        dx3 = -0.353f,
                        dy3 = -0.138f,
                    )
                    // a 38.633 38.633 0 0 0 -13.786 -2.525
                    arcToRelative(
                        a = 38.633f,
                        b = 38.633f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -13.786f,
                        dy1 = -2.525f,
                    )
                    // c -0.133 0 -0.265 0 -0.398 0.002
                    curveToRelative(
                        dx1 = -0.133f,
                        dy1 = 0.0f,
                        dx2 = -0.265f,
                        dy2 = 0.0f,
                        dx3 = -0.398f,
                        dy3 = 0.002f,
                    )
                    // a 38.652 38.652 0 0 0 -12.338 2.14
                    arcToRelative(
                        a = 38.652f,
                        b = 38.652f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -12.338f,
                        dy1 = 2.14f,
                    )
                    // c -0.449 0.156 -0.891 0.321 -1.33 0.494
                    curveToRelative(
                        dx1 = -0.449f,
                        dy1 = 0.156f,
                        dx2 = -0.891f,
                        dy2 = 0.321f,
                        dx3 = -1.33f,
                        dy3 = 0.494f,
                    )
                    // l -0.239 -0.411
                    lineToRelative(dx = -0.239f, dy = -0.411f)
                    // c -0.776 -1.341 -1.555 -2.68 -2.331 -4.022
                    curveToRelative(
                        dx1 = -0.776f,
                        dy1 = -1.341f,
                        dx2 = -1.555f,
                        dy2 = -2.68f,
                        dx3 = -2.331f,
                        dy3 = -4.022f,
                    )
                    // a 136999.44 136999.44 0 0 0 -3.916 -6.75
                    arcToRelative(
                        a = 136999.44f,
                        b = 136999.44f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -3.916f,
                        dy1 = -6.75f,
                    )
                    // a 3.534 3.534 0 0 0 -3.364 -1.748
                    arcToRelative(
                        a = 3.534f,
                        b = 3.534f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -3.364f,
                        dy1 = -1.748f,
                    )
                    // a 3.517 3.517 0 0 0 -1.506 0.484
                    arcToRelative(
                        a = 3.517f,
                        b = 3.517f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -1.506f,
                        dy1 = 0.484f,
                    )
                    // a 3.526 3.526 0 0 0 -1.61 2.13
                    arcToRelative(
                        a = 3.526f,
                        b = 3.526f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -1.61f,
                        dy1 = 2.13f,
                    )
                    // a 3.542 3.542 0 0 0 0.359 2.685
                    arcToRelative(
                        a = 3.542f,
                        b = 3.542f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = 0.359f,
                        dy1 = 2.685f,
                    )
                    // l 1.636 2.822
                    lineToRelative(dx = 1.636f, dy = 2.822f)
                    // l 2.28 3.928
                    lineToRelative(dx = 2.28f, dy = 3.928f)
                    // c 0.776 1.341 1.554 2.68 2.331 4.022
                    curveToRelative(
                        dx1 = 0.776f,
                        dy1 = 1.341f,
                        dx2 = 1.554f,
                        dy2 = 2.68f,
                        dx3 = 2.331f,
                        dy3 = 4.022f,
                    )
                    // l 0.019 0.034
                    lineToRelative(dx = 0.019f, dy = 0.034f)
                    // a 38.894 38.894 0 0 0 -11.142 10.451
                    arcToRelative(
                        a = 38.894f,
                        b = 38.894f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -11.142f,
                        dy1 = 10.451f,
                    )
                    // a 38.38 38.38 0 0 0 -2.81 4.581
                    arcToRelative(
                        a = 38.38f,
                        b = 38.38f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -2.81f,
                        dy1 = 4.581f,
                    )
                    // a 38.494 38.494 0 0 0 -3.26 8.647
                    arcToRelative(
                        a = 38.494f,
                        b = 38.494f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -3.26f,
                        dy1 = 8.647f,
                    )
                    // c -0.65 2.647 1.496 5.025 4.223 5.025
                    curveToRelative(
                        dx1 = -0.65f,
                        dy1 = 2.647f,
                        dx2 = 1.496f,
                        dy2 = 5.025f,
                        dx3 = 4.223f,
                        dy3 = 5.025f,
                    )
                    // H 80.89
                    horizontalLineTo(x = 80.89f)
                    // c 2.727 0 4.88 -2.38 4.231 -5.03z
                    curveToRelative(
                        dx1 = 2.727f,
                        dy1 = 0.0f,
                        dx2 = 4.88f,
                        dy2 = -2.38f,
                        dx3 = 4.231f,
                        dy3 = -5.03f,
                    )
                    close()
                },
            ) {
                // M86.42 23.578 H4.227 v46.245 H86.42 V23.578Z
                path(
                    fill = Brush.radialGradient(
                        0.307f to Color(0xFF4FAF53),
                        1.0f to Color(0xFF118016),
                        1.0f to Color(0x004FAF53),
                        center = Offset(x = 47.092957f, y = 55.79932f),
                        radius = 46.25078f,
                    ),
                ) {
                    // M 86.42 23.578
                    moveTo(x = 86.42f, y = 23.578f)
                    // H 4.227
                    horizontalLineTo(x = 4.227f)
                    // v 46.245
                    verticalLineToRelative(dy = 46.245f)
                    // H 86.42
                    horizontalLineTo(x = 86.42f)
                    // V 23.578z
                    verticalLineTo(y = 23.578f)
                    close()
                }
                // M50.316 38.42 c8.147 5.746 -14.782 6.376 -23.639 18.934 s-1.758 34.368 -9.904 28.623 c-5.03 -6.898 -5.795 -20.891 3.062 -33.449 8.857 -12.557 23.572 -16.683 30.481 -14.108Z
                path(
                    fill = Brush.linearGradient(
                        0.0f to Color(0xFFA8F0B9),
                        1.0f to Color(0x00ADEEBC),
                        start = Offset(x = 19.398f, y = 52.22f),
                        end = Offset(x = 30.779f, y = 60.247f),
                    ),
                    fillAlpha = 0.4f,
                    strokeAlpha = 0.8f,
                ) {
                    // M 50.316 38.42
                    moveTo(x = 50.316f, y = 38.42f)
                    // c 8.147 5.746 -14.782 6.376 -23.639 18.934
                    curveToRelative(
                        dx1 = 8.147f,
                        dy1 = 5.746f,
                        dx2 = -14.782f,
                        dy2 = 6.376f,
                        dx3 = -23.639f,
                        dy3 = 18.934f,
                    )
                    // s -1.758 34.368 -9.904 28.623
                    reflectiveCurveToRelative(
                        dx1 = -1.758f,
                        dy1 = 34.368f,
                        dx2 = -9.904f,
                        dy2 = 28.623f,
                    )
                    // c -5.03 -6.898 -5.795 -20.891 3.062 -33.449
                    curveToRelative(
                        dx1 = -5.03f,
                        dy1 = -6.898f,
                        dx2 = -5.795f,
                        dy2 = -20.891f,
                        dx3 = 3.062f,
                        dy3 = -33.449f,
                    )
                    // c 8.857 -12.557 23.572 -16.683 30.481 -14.108z
                    curveToRelative(
                        dx1 = 8.857f,
                        dy1 = -12.557f,
                        dx2 = 23.572f,
                        dy2 = -16.683f,
                        dx3 = 30.481f,
                        dy3 = -14.108f,
                    )
                    close()
                }
                // M47.858 38.652 c-7.453 5.392 13.526 5.984 21.63 17.768 s1.608 32.252 9.061 26.86 c4.602 -6.473 5.303 -19.604 -2.801 -31.388 -8.104 -11.785 -21.567 -15.656 -27.89 -13.24Z
                path(
                    fill = Brush.linearGradient(
                        0.0f to Color(0xFFA8F0B9),
                        1.0f to Color(0x00ADEEBC),
                        start = Offset(x = 74.868f, y = 53.06f),
                        end = Offset(x = 65.599f, y = 58.921f),
                    ),
                    fillAlpha = 0.4f,
                    strokeAlpha = 0.7f,
                ) {
                    // M 47.858 38.652
                    moveTo(x = 47.858f, y = 38.652f)
                    // c -7.453 5.392 13.526 5.984 21.63 17.768
                    curveToRelative(
                        dx1 = -7.453f,
                        dy1 = 5.392f,
                        dx2 = 13.526f,
                        dy2 = 5.984f,
                        dx3 = 21.63f,
                        dy3 = 17.768f,
                    )
                    // s 1.608 32.252 9.061 26.86
                    reflectiveCurveToRelative(
                        dx1 = 1.608f,
                        dy1 = 32.252f,
                        dx2 = 9.061f,
                        dy2 = 26.86f,
                    )
                    // c 4.602 -6.473 5.303 -19.604 -2.801 -31.388
                    curveToRelative(
                        dx1 = 4.602f,
                        dy1 = -6.473f,
                        dx2 = 5.303f,
                        dy2 = -19.604f,
                        dx3 = -2.801f,
                        dy3 = -31.388f,
                    )
                    // c -8.104 -11.785 -21.567 -15.656 -27.89 -13.24z
                    curveToRelative(
                        dx1 = -8.104f,
                        dy1 = -11.785f,
                        dx2 = -21.567f,
                        dy2 = -15.656f,
                        dx3 = -27.89f,
                        dy3 = -13.24f,
                    )
                    close()
                }
                // M7.96 58.972 c-3.13 9.844 -4.74 18.118 -3.599 18.48 1.142 .364 4.604 -7.322 7.733 -17.166 3.13 -9.844 16.037 -20.578 14.896 -20.94 -1.142 -.364 -15.9 9.782 -19.03 19.626Z
                path(
                    fill = SolidColor(Color(0xFF8BD8A0)),
                    fillAlpha = 0.6f,
                    strokeAlpha = 0.6f,
                ) {
                    // M 7.96 58.972
                    moveTo(x = 7.96f, y = 58.972f)
                    // c -3.13 9.844 -4.74 18.118 -3.599 18.48
                    curveToRelative(
                        dx1 = -3.13f,
                        dy1 = 9.844f,
                        dx2 = -4.74f,
                        dy2 = 18.118f,
                        dx3 = -3.599f,
                        dy3 = 18.48f,
                    )
                    // c 1.142 0.364 4.604 -7.322 7.733 -17.166
                    curveToRelative(
                        dx1 = 1.142f,
                        dy1 = 0.364f,
                        dx2 = 4.604f,
                        dy2 = -7.322f,
                        dx3 = 7.733f,
                        dy3 = -17.166f,
                    )
                    // c 3.13 -9.844 16.037 -20.578 14.896 -20.94
                    curveToRelative(
                        dx1 = 3.13f,
                        dy1 = -9.844f,
                        dx2 = 16.037f,
                        dy2 = -20.578f,
                        dx3 = 14.896f,
                        dy3 = -20.94f,
                    )
                    // c -1.142 -0.364 -15.9 9.782 -19.03 19.626z
                    curveToRelative(
                        dx1 = -1.142f,
                        dy1 = -0.364f,
                        dx2 = -15.9f,
                        dy2 = 9.782f,
                        dx3 = -19.03f,
                        dy3 = 19.626f,
                    )
                    close()
                }
                // M86.587 58.972 c3.13 9.844 4.74 18.118 3.6 18.48 -1.143 .364 -4.605 -7.322 -7.734 -17.166 -3.13 -9.844 -16.037 -20.578 -14.895 -20.94 1.141 -.364 15.9 9.782 19.029 19.626Z
                path(
                    fill = SolidColor(Color(0xFF8BD8A0)),
                    fillAlpha = 0.5f,
                    strokeAlpha = 0.5f,
                ) {
                    // M 86.587 58.972
                    moveTo(x = 86.587f, y = 58.972f)
                    // c 3.13 9.844 4.74 18.118 3.6 18.48
                    curveToRelative(
                        dx1 = 3.13f,
                        dy1 = 9.844f,
                        dx2 = 4.74f,
                        dy2 = 18.118f,
                        dx3 = 3.6f,
                        dy3 = 18.48f,
                    )
                    // c -1.143 0.364 -4.605 -7.322 -7.734 -17.166
                    curveToRelative(
                        dx1 = -1.143f,
                        dy1 = 0.364f,
                        dx2 = -4.605f,
                        dy2 = -7.322f,
                        dx3 = -7.734f,
                        dy3 = -17.166f,
                    )
                    // c -3.13 -9.844 -16.037 -20.578 -14.895 -20.94
                    curveToRelative(
                        dx1 = -3.13f,
                        dy1 = -9.844f,
                        dx2 = -16.037f,
                        dy2 = -20.578f,
                        dx3 = -14.895f,
                        dy3 = -20.94f,
                    )
                    // c 1.141 -0.364 15.9 9.782 19.029 19.626z
                    curveToRelative(
                        dx1 = 1.141f,
                        dy1 = -0.364f,
                        dx2 = 15.9f,
                        dy2 = 9.782f,
                        dx3 = 19.029f,
                        dy3 = 19.626f,
                    )
                    close()
                }
                // M28.66 41.056 c.37 -.242 -1.586 -3.891 -4.37 -8.15 -2.785 -4.26 -5.343 -7.516 -5.713 -7.274 -.37 .242 1.587 3.891 4.371 8.15 2.785 4.26 5.342 7.516 5.713 7.274Z
                path(
                    fill = SolidColor(Color(0xFF202124)),
                    fillAlpha = 0.1f,
                    strokeAlpha = 0.1f,
                ) {
                    // M 28.66 41.056
                    moveTo(x = 28.66f, y = 41.056f)
                    // c 0.37 -0.242 -1.586 -3.891 -4.37 -8.15
                    curveToRelative(
                        dx1 = 0.37f,
                        dy1 = -0.242f,
                        dx2 = -1.586f,
                        dy2 = -3.891f,
                        dx3 = -4.37f,
                        dy3 = -8.15f,
                    )
                    // c -2.785 -4.26 -5.343 -7.516 -5.713 -7.274
                    curveToRelative(
                        dx1 = -2.785f,
                        dy1 = -4.26f,
                        dx2 = -5.343f,
                        dy2 = -7.516f,
                        dx3 = -5.713f,
                        dy3 = -7.274f,
                    )
                    // c -0.37 0.242 1.587 3.891 4.371 8.15
                    curveToRelative(
                        dx1 = -0.37f,
                        dy1 = 0.242f,
                        dx2 = 1.587f,
                        dy2 = 3.891f,
                        dx3 = 4.371f,
                        dy3 = 8.15f,
                    )
                    // c 2.785 4.26 5.342 7.516 5.713 7.274z
                    curveToRelative(
                        dx1 = 2.785f,
                        dy1 = 4.26f,
                        dx2 = 5.342f,
                        dy2 = 7.516f,
                        dx3 = 5.713f,
                        dy3 = 7.274f,
                    )
                    close()
                }
                // M27.507 30.127 c3.242 5.324 4.823 7.764 5.183 7.544 .36 -.219 -.637 -3.014 -3.88 -8.338 -3.124 -7.111 -8.762 -2.87 -8.205 -2.093 1.083 2.085 2.757 -4.578 6.902 2.887Z
                path(
                    fill = SolidColor(Color(0xFFFFFFFF)),
                    fillAlpha = 0.3f,
                    strokeAlpha = 0.3f,
                ) {
                    // M 27.507 30.127
                    moveTo(x = 27.507f, y = 30.127f)
                    // c 3.242 5.324 4.823 7.764 5.183 7.544
                    curveToRelative(
                        dx1 = 3.242f,
                        dy1 = 5.324f,
                        dx2 = 4.823f,
                        dy2 = 7.764f,
                        dx3 = 5.183f,
                        dy3 = 7.544f,
                    )
                    // c 0.36 -0.219 -0.637 -3.014 -3.88 -8.338
                    curveToRelative(
                        dx1 = 0.36f,
                        dy1 = -0.219f,
                        dx2 = -0.637f,
                        dy2 = -3.014f,
                        dx3 = -3.88f,
                        dy3 = -8.338f,
                    )
                    // c -3.124 -7.111 -8.762 -2.87 -8.205 -2.093
                    curveToRelative(
                        dx1 = -3.124f,
                        dy1 = -7.111f,
                        dx2 = -8.762f,
                        dy2 = -2.87f,
                        dx3 = -8.205f,
                        dy3 = -2.093f,
                    )
                    // c 1.083 2.085 2.757 -4.578 6.902 2.887z
                    curveToRelative(
                        dx1 = 1.083f,
                        dy1 = 2.085f,
                        dx2 = 2.757f,
                        dy2 = -4.578f,
                        dx3 = 6.902f,
                        dy3 = 2.887f,
                    )
                    close()
                }
                // M18.478 28.284 c.585 2.02 2.987 3.1 5.364 2.412 2.378 -.688 3.831 -2.884 3.246 -4.905 -.585 -2.02 -2.986 -3.1 -5.364 -2.412 -2.377 .688 -3.83 2.884 -3.246 4.904Z
                path(
                    fill = Brush.radialGradient(
                        0.0f to Color(0xFFFFFFFF),
                        0.948f to Color(0x00FFFFFF),
                        center = Offset(x = 22.7829f, y = 27.03815f),
                        radius = 2.4601667f,
                    ),
                    fillAlpha = 0.9f,
                    strokeAlpha = 0.15f,
                ) {
                    // M 18.478 28.284
                    moveTo(x = 18.478f, y = 28.284f)
                    // c 0.585 2.02 2.987 3.1 5.364 2.412
                    curveToRelative(
                        dx1 = 0.585f,
                        dy1 = 2.02f,
                        dx2 = 2.987f,
                        dy2 = 3.1f,
                        dx3 = 5.364f,
                        dy3 = 2.412f,
                    )
                    // c 2.378 -0.688 3.831 -2.884 3.246 -4.905
                    curveToRelative(
                        dx1 = 2.378f,
                        dy1 = -0.688f,
                        dx2 = 3.831f,
                        dy2 = -2.884f,
                        dx3 = 3.246f,
                        dy3 = -4.905f,
                    )
                    // c -0.585 -2.02 -2.986 -3.1 -5.364 -2.412
                    curveToRelative(
                        dx1 = -0.585f,
                        dy1 = -2.02f,
                        dx2 = -2.986f,
                        dy2 = -3.1f,
                        dx3 = -5.364f,
                        dy3 = -2.412f,
                    )
                    // c -2.377 0.688 -3.83 2.884 -3.246 4.904z
                    curveToRelative(
                        dx1 = -2.377f,
                        dy1 = 0.688f,
                        dx2 = -3.83f,
                        dy2 = 2.884f,
                        dx3 = -3.246f,
                        dy3 = 4.904f,
                    )
                    close()
                }
                // M67.644 28.508 c.585 2.02 2.987 3.1 5.364 2.413 2.378 -.688 3.831 -2.884 3.246 -4.905 -.585 -2.02 -2.986 -3.1 -5.364 -2.412 -2.377 .688 -3.83 2.884 -3.246 4.904Z
                path(
                    fill = Brush.radialGradient(
                        0.0f to Color(0xFFFFFFFF),
                        0.948f to Color(0x00FFFFFF),
                        center = Offset(x = 71.95f, y = 27.262f),
                        radius = 2.4601676f,
                    ),
                    fillAlpha = 0.9f,
                    strokeAlpha = 0.15f,
                ) {
                    // M 67.644 28.508
                    moveTo(x = 67.644f, y = 28.508f)
                    // c 0.585 2.02 2.987 3.1 5.364 2.413
                    curveToRelative(
                        dx1 = 0.585f,
                        dy1 = 2.02f,
                        dx2 = 2.987f,
                        dy2 = 3.1f,
                        dx3 = 5.364f,
                        dy3 = 2.413f,
                    )
                    // c 2.378 -0.688 3.831 -2.884 3.246 -4.905
                    curveToRelative(
                        dx1 = 2.378f,
                        dy1 = -0.688f,
                        dx2 = 3.831f,
                        dy2 = -2.884f,
                        dx3 = 3.246f,
                        dy3 = -4.905f,
                    )
                    // c -0.585 -2.02 -2.986 -3.1 -5.364 -2.412
                    curveToRelative(
                        dx1 = -0.585f,
                        dy1 = -2.02f,
                        dx2 = -2.986f,
                        dy2 = -3.1f,
                        dx3 = -5.364f,
                        dy3 = -2.412f,
                    )
                    // c -2.377 0.688 -3.83 2.884 -3.246 4.904z
                    curveToRelative(
                        dx1 = -2.377f,
                        dy1 = 0.688f,
                        dx2 = -3.83f,
                        dy2 = 2.884f,
                        dx3 = -3.246f,
                        dy3 = 4.904f,
                    )
                    close()
                }
                // M66.116 41.32 c-.374 -.236 1.524 -3.917 4.24 -8.22 2.715 -4.304 5.22 -7.601 5.594 -7.365 .374 .236 -1.524 3.916 -4.24 8.22 -2.715 4.303 -5.22 7.6 -5.594 7.365Z
                path(
                    fill = SolidColor(Color(0xFF202124)),
                    fillAlpha = 0.1f,
                    strokeAlpha = 0.1f,
                ) {
                    // M 66.116 41.32
                    moveTo(x = 66.116f, y = 41.32f)
                    // c -0.374 -0.236 1.524 -3.917 4.24 -8.22
                    curveToRelative(
                        dx1 = -0.374f,
                        dy1 = -0.236f,
                        dx2 = 1.524f,
                        dy2 = -3.917f,
                        dx3 = 4.24f,
                        dy3 = -8.22f,
                    )
                    // c 2.715 -4.304 5.22 -7.601 5.594 -7.365
                    curveToRelative(
                        dx1 = 2.715f,
                        dy1 = -4.304f,
                        dx2 = 5.22f,
                        dy2 = -7.601f,
                        dx3 = 5.594f,
                        dy3 = -7.365f,
                    )
                    // c 0.374 0.236 -1.524 3.916 -4.24 8.22
                    curveToRelative(
                        dx1 = 0.374f,
                        dy1 = 0.236f,
                        dx2 = -1.524f,
                        dy2 = 3.916f,
                        dx3 = -4.24f,
                        dy3 = 8.22f,
                    )
                    // c -2.715 4.303 -5.22 7.6 -5.594 7.365z
                    curveToRelative(
                        dx1 = -2.715f,
                        dy1 = 4.303f,
                        dx2 = -5.22f,
                        dy2 = 7.6f,
                        dx3 = -5.594f,
                        dy3 = 7.365f,
                    )
                    close()
                }
                // M62.868 38.127 c-.91 -.447 -1.717 -.665 -1.804 -.487 -.087 .177 .594 .558 1.503 1.004 .909 .447 1.701 .79 1.788 .613 .087 -.177 -.579 -.683 -1.487 -1.13Z
                path(
                    fill = SolidColor(Color(0xFF81C995)),
                    fillAlpha = 0.3f,
                    strokeAlpha = 0.3f,
                ) {
                    // M 62.868 38.127
                    moveTo(x = 62.868f, y = 38.127f)
                    // c -0.91 -0.447 -1.717 -0.665 -1.804 -0.487
                    curveToRelative(
                        dx1 = -0.91f,
                        dy1 = -0.447f,
                        dx2 = -1.717f,
                        dy2 = -0.665f,
                        dx3 = -1.804f,
                        dy3 = -0.487f,
                    )
                    // c -0.087 0.177 0.594 0.558 1.503 1.004
                    curveToRelative(
                        dx1 = -0.087f,
                        dy1 = 0.177f,
                        dx2 = 0.594f,
                        dy2 = 0.558f,
                        dx3 = 1.503f,
                        dy3 = 1.004f,
                    )
                    // c 0.909 0.447 1.701 0.79 1.788 0.613
                    curveToRelative(
                        dx1 = 0.909f,
                        dy1 = 0.447f,
                        dx2 = 1.701f,
                        dy2 = 0.79f,
                        dx3 = 1.788f,
                        dy3 = 0.613f,
                    )
                    // c 0.087 -0.177 -0.579 -0.683 -1.487 -1.13z
                    curveToRelative(
                        dx1 = 0.087f,
                        dy1 = -0.177f,
                        dx2 = -0.579f,
                        dy2 = -0.683f,
                        dx3 = -1.487f,
                        dy3 = -1.13f,
                    )
                    close()
                }
                // M31.637 38.373 c.899 -.465 1.702 -.7 1.793 -.524 .09 .176 -.583 .57 -1.482 1.036 -.9 .465 -1.685 .825 -1.776 .649 -.09 -.176 .565 -.695 1.465 -1.16Z
                path(
                    fill = SolidColor(Color(0xFF81C995)),
                    fillAlpha = 0.3f,
                    strokeAlpha = 0.3f,
                ) {
                    // M 31.637 38.373
                    moveTo(x = 31.637f, y = 38.373f)
                    // c 0.899 -0.465 1.702 -0.7 1.793 -0.524
                    curveToRelative(
                        dx1 = 0.899f,
                        dy1 = -0.465f,
                        dx2 = 1.702f,
                        dy2 = -0.7f,
                        dx3 = 1.793f,
                        dy3 = -0.524f,
                    )
                    // c 0.09 0.176 -0.583 0.57 -1.482 1.036
                    curveToRelative(
                        dx1 = 0.09f,
                        dy1 = 0.176f,
                        dx2 = -0.583f,
                        dy2 = 0.57f,
                        dx3 = -1.482f,
                        dy3 = 1.036f,
                    )
                    // c -0.9 0.465 -1.685 0.825 -1.776 0.649
                    curveToRelative(
                        dx1 = -0.9f,
                        dy1 = 0.465f,
                        dx2 = -1.685f,
                        dy2 = 0.825f,
                        dx3 = -1.776f,
                        dy3 = 0.649f,
                    )
                    // c -0.09 -0.176 0.565 -0.695 1.465 -1.16z
                    curveToRelative(
                        dx1 = -0.09f,
                        dy1 = -0.176f,
                        dx2 = 0.565f,
                        dy2 = -0.695f,
                        dx3 = 1.465f,
                        dy3 = -1.16f,
                    )
                    close()
                }
                // M67.451 30.41 c-3.032 5.252 -4.52 7.657 -4.903 7.436 -.383 -.221 .484 -2.985 3.516 -8.237 3.456 -9.124 10.306 -7.918 9.182 -2.931 -.886 2.05 -3.956 -3.634 -7.795 3.732Z
                path(
                    fill = SolidColor(Color(0xFFFFFFFF)),
                    fillAlpha = 0.3f,
                    strokeAlpha = 0.3f,
                ) {
                    // M 67.451 30.41
                    moveTo(x = 67.451f, y = 30.41f)
                    // c -3.032 5.252 -4.52 7.657 -4.903 7.436
                    curveToRelative(
                        dx1 = -3.032f,
                        dy1 = 5.252f,
                        dx2 = -4.52f,
                        dy2 = 7.657f,
                        dx3 = -4.903f,
                        dy3 = 7.436f,
                    )
                    // c -0.383 -0.221 0.484 -2.985 3.516 -8.237
                    curveToRelative(
                        dx1 = -0.383f,
                        dy1 = -0.221f,
                        dx2 = 0.484f,
                        dy2 = -2.985f,
                        dx3 = 3.516f,
                        dy3 = -8.237f,
                    )
                    // c 3.456 -9.124 10.306 -7.918 9.182 -2.931
                    curveToRelative(
                        dx1 = 3.456f,
                        dy1 = -9.124f,
                        dx2 = 10.306f,
                        dy2 = -7.918f,
                        dx3 = 9.182f,
                        dy3 = -2.931f,
                    )
                    // c -0.886 2.05 -3.956 -3.634 -7.795 3.732z
                    curveToRelative(
                        dx1 = -0.886f,
                        dy1 = 2.05f,
                        dx2 = -3.956f,
                        dy2 = -3.634f,
                        dx3 = -7.795f,
                        dy3 = 3.732f,
                    )
                    close()
                }
            }
            // M31.736 55.797 c.466 -.528 .073 -1.649 -.878 -2.504 -.95 -.855 -2.1 -1.12 -2.565 -.592 -.466 .527 -.073 1.648 .878 2.503 .95 .855 2.1 1.12 2.566 .593Z
            path(
                fill = Brush.radialGradient(
                    0.0f to Color(0xFF93E19F),
                    1.0f to Color(0x0093E19F),
                    center = Offset(x = 31.97f, y = 54.58f),
                    radius = 1.4572628f,
                ),
                fillAlpha = 0.7f,
            ) {
                // M 31.736 55.797
                moveTo(x = 31.736f, y = 55.797f)
                // c 0.466 -0.528 0.073 -1.649 -0.878 -2.504
                curveToRelative(
                    dx1 = 0.466f,
                    dy1 = -0.528f,
                    dx2 = 0.073f,
                    dy2 = -1.649f,
                    dx3 = -0.878f,
                    dy3 = -2.504f,
                )
                // c -0.95 -0.855 -2.1 -1.12 -2.565 -0.592
                curveToRelative(
                    dx1 = -0.95f,
                    dy1 = -0.855f,
                    dx2 = -2.1f,
                    dy2 = -1.12f,
                    dx3 = -2.565f,
                    dy3 = -0.592f,
                )
                // c -0.466 0.527 -0.073 1.648 0.878 2.503
                curveToRelative(
                    dx1 = -0.466f,
                    dy1 = 0.527f,
                    dx2 = -0.073f,
                    dy2 = 1.648f,
                    dx3 = 0.878f,
                    dy3 = 2.503f,
                )
                // c 0.95 0.855 2.1 1.12 2.566 0.593z
                curveToRelative(
                    dx1 = 0.95f,
                    dy1 = 0.855f,
                    dx2 = 2.1f,
                    dy2 = 1.12f,
                    dx3 = 2.566f,
                    dy3 = 0.593f,
                )
                close()
            }
            // M64.413 54.447 c.951 -.855 1.344 -1.976 .878 -2.503 -.466 -.528 -1.614 -.263 -2.565 .592 -.951 .855 -1.344 1.976 -.878 2.503 .466 .528 1.614 .263 2.565 -.592Z
            path(
                fill = Brush.radialGradient(
                    0.0f to Color(0xFF93E19F),
                    1.0f to Color(0x0093E19F),
                    center = Offset(x = 64.02f, y = 53.387f),
                    radius = 1.456427f,
                ),
                fillAlpha = 0.7f,
            ) {
                // M 64.413 54.447
                moveTo(x = 64.413f, y = 54.447f)
                // c 0.951 -0.855 1.344 -1.976 0.878 -2.503
                curveToRelative(
                    dx1 = 0.951f,
                    dy1 = -0.855f,
                    dx2 = 1.344f,
                    dy2 = -1.976f,
                    dx3 = 0.878f,
                    dy3 = -2.503f,
                )
                // c -0.466 -0.528 -1.614 -0.263 -2.565 0.592
                curveToRelative(
                    dx1 = -0.466f,
                    dy1 = -0.528f,
                    dx2 = -1.614f,
                    dy2 = -0.263f,
                    dx3 = -2.565f,
                    dy3 = 0.592f,
                )
                // c -0.951 0.855 -1.344 1.976 -0.878 2.503
                curveToRelative(
                    dx1 = -0.951f,
                    dy1 = 0.855f,
                    dx2 = -1.344f,
                    dy2 = 1.976f,
                    dx3 = -0.878f,
                    dy3 = 2.503f,
                )
                // c 0.466 0.528 1.614 0.263 2.565 -0.592z
                curveToRelative(
                    dx1 = 0.466f,
                    dy1 = 0.528f,
                    dx2 = 1.614f,
                    dy2 = 0.263f,
                    dx3 = 2.565f,
                    dy3 = -0.592f,
                )
                close()
            }
            // M32.363 58.497 c1.153 -2.88 -.058 -4.654 -.807 -5.207 -2.162 -2.675 -5.074 -.61 -5.938 .553 -.865 1.134 -2.133 3.17 -.894 5.962 1.24 2.793 6.198 2.298 7.639 -1.308Z M62.253 58.561 c-1.153 -2.85 .058 -4.653 .807 -5.177 2.162 -2.676 5.073 -.61 5.938 .553 .865 1.134 2.133 3.17 .894 5.933 -1.24 2.763 -6.197 2.298 -7.639 -1.28 v-.029Z
            path(
                fill = SolidColor(Color(0xFF011B04)),
                fillAlpha = 0.09f,
                strokeAlpha = 0.09f,
            ) {
                // M 32.363 58.497
                moveTo(x = 32.363f, y = 58.497f)
                // c 1.153 -2.88 -0.058 -4.654 -0.807 -5.207
                curveToRelative(
                    dx1 = 1.153f,
                    dy1 = -2.88f,
                    dx2 = -0.058f,
                    dy2 = -4.654f,
                    dx3 = -0.807f,
                    dy3 = -5.207f,
                )
                // c -2.162 -2.675 -5.074 -0.61 -5.938 0.553
                curveToRelative(
                    dx1 = -2.162f,
                    dy1 = -2.675f,
                    dx2 = -5.074f,
                    dy2 = -0.61f,
                    dx3 = -5.938f,
                    dy3 = 0.553f,
                )
                // c -0.865 1.134 -2.133 3.17 -0.894 5.962
                curveToRelative(
                    dx1 = -0.865f,
                    dy1 = 1.134f,
                    dx2 = -2.133f,
                    dy2 = 3.17f,
                    dx3 = -0.894f,
                    dy3 = 5.962f,
                )
                // c 1.24 2.793 6.198 2.298 7.639 -1.308z
                curveToRelative(
                    dx1 = 1.24f,
                    dy1 = 2.793f,
                    dx2 = 6.198f,
                    dy2 = 2.298f,
                    dx3 = 7.639f,
                    dy3 = -1.308f,
                )
                close()
                // M 62.253 58.561
                moveTo(x = 62.253f, y = 58.561f)
                // c -1.153 -2.85 0.058 -4.653 0.807 -5.177
                curveToRelative(
                    dx1 = -1.153f,
                    dy1 = -2.85f,
                    dx2 = 0.058f,
                    dy2 = -4.653f,
                    dx3 = 0.807f,
                    dy3 = -5.177f,
                )
                // c 2.162 -2.676 5.073 -0.61 5.938 0.553
                curveToRelative(
                    dx1 = 2.162f,
                    dy1 = -2.676f,
                    dx2 = 5.073f,
                    dy2 = -0.61f,
                    dx3 = 5.938f,
                    dy3 = 0.553f,
                )
                // c 0.865 1.134 2.133 3.17 0.894 5.933
                curveToRelative(
                    dx1 = 0.865f,
                    dy1 = 1.134f,
                    dx2 = 2.133f,
                    dy2 = 3.17f,
                    dx3 = 0.894f,
                    dy3 = 5.933f,
                )
                // c -1.24 2.763 -6.197 2.298 -7.639 -1.28
                curveToRelative(
                    dx1 = -1.24f,
                    dy1 = 2.763f,
                    dx2 = -6.197f,
                    dy2 = 2.298f,
                    dx3 = -7.639f,
                    dy3 = -1.28f,
                )
                // v -0.029z
                verticalLineToRelative(dy = -0.029f)
                close()
            }
            // M68.77 60.532 c1.787 -1.192 2.075 -3.956 .634 -6.137 -1.441 -2.181 -4.064 -2.967 -5.852 -1.774 -1.787 1.192 -2.075 3.955 -.634 6.137 1.441 2.181 4.065 2.967 5.852 1.774Z
            path(
                fill = SolidColor(Color(0xFF202124)),
            ) {
                // M 68.77 60.532
                moveTo(x = 68.77f, y = 60.532f)
                // c 1.787 -1.192 2.075 -3.956 0.634 -6.137
                curveToRelative(
                    dx1 = 1.787f,
                    dy1 = -1.192f,
                    dx2 = 2.075f,
                    dy2 = -3.956f,
                    dx3 = 0.634f,
                    dy3 = -6.137f,
                )
                // c -1.441 -2.181 -4.064 -2.967 -5.852 -1.774
                curveToRelative(
                    dx1 = -1.441f,
                    dy1 = -2.181f,
                    dx2 = -4.064f,
                    dy2 = -2.967f,
                    dx3 = -5.852f,
                    dy3 = -1.774f,
                )
                // c -1.787 1.192 -2.075 3.955 -0.634 6.137
                curveToRelative(
                    dx1 = -1.787f,
                    dy1 = 1.192f,
                    dx2 = -2.075f,
                    dy2 = 3.955f,
                    dx3 = -0.634f,
                    dy3 = 6.137f,
                )
                // c 1.441 2.181 4.065 2.967 5.852 1.774z
                curveToRelative(
                    dx1 = 1.441f,
                    dy1 = 2.181f,
                    dx2 = 4.065f,
                    dy2 = 2.967f,
                    dx3 = 5.852f,
                    dy3 = 1.774f,
                )
                close()
            }
            // M31.674 58.666 c1.441 -2.181 1.182 -4.886 -.577 -6.078 -1.758 -1.164 -4.323 -.35 -5.765 1.803 -1.44 2.181 -1.181 4.886 .577 6.079 1.758 1.163 4.324 .349 5.765 -1.804Z M62.922 58.702 c-1.441 -2.182 -1.182 -4.887 .577 -6.08 1.758 -1.163 4.324 -.348 5.765 1.804 1.441 2.181 1.182 4.886 -.577 6.079 -1.758 1.163 -4.323 .349 -5.765 -1.803Z
            path(
                fill = SolidColor(Color(0xFF000000)),
            ) {
                // M 31.674 58.666
                moveTo(x = 31.674f, y = 58.666f)
                // c 1.441 -2.181 1.182 -4.886 -0.577 -6.078
                curveToRelative(
                    dx1 = 1.441f,
                    dy1 = -2.181f,
                    dx2 = 1.182f,
                    dy2 = -4.886f,
                    dx3 = -0.577f,
                    dy3 = -6.078f,
                )
                // c -1.758 -1.164 -4.323 -0.35 -5.765 1.803
                curveToRelative(
                    dx1 = -1.758f,
                    dy1 = -1.164f,
                    dx2 = -4.323f,
                    dy2 = -0.35f,
                    dx3 = -5.765f,
                    dy3 = 1.803f,
                )
                // c -1.44 2.181 -1.181 4.886 0.577 6.079
                curveToRelative(
                    dx1 = -1.44f,
                    dy1 = 2.181f,
                    dx2 = -1.181f,
                    dy2 = 4.886f,
                    dx3 = 0.577f,
                    dy3 = 6.079f,
                )
                // c 1.758 1.163 4.324 0.349 5.765 -1.804z
                curveToRelative(
                    dx1 = 1.758f,
                    dy1 = 1.163f,
                    dx2 = 4.324f,
                    dy2 = 0.349f,
                    dx3 = 5.765f,
                    dy3 = -1.804f,
                )
                close()
                // M 62.922 58.702
                moveTo(x = 62.922f, y = 58.702f)
                // c -1.441 -2.182 -1.182 -4.887 0.577 -6.08
                curveToRelative(
                    dx1 = -1.441f,
                    dy1 = -2.182f,
                    dx2 = -1.182f,
                    dy2 = -4.887f,
                    dx3 = 0.577f,
                    dy3 = -6.08f,
                )
                // c 1.758 -1.163 4.324 -0.348 5.765 1.804
                curveToRelative(
                    dx1 = 1.758f,
                    dy1 = -1.163f,
                    dx2 = 4.324f,
                    dy2 = -0.348f,
                    dx3 = 5.765f,
                    dy3 = 1.804f,
                )
                // c 1.441 2.181 1.182 4.886 -0.577 6.079
                curveToRelative(
                    dx1 = 1.441f,
                    dy1 = 2.181f,
                    dx2 = 1.182f,
                    dy2 = 4.886f,
                    dx3 = -0.577f,
                    dy3 = 6.079f,
                )
                // c -1.758 1.163 -4.323 0.349 -5.765 -1.803z
                curveToRelative(
                    dx1 = -1.758f,
                    dy1 = 1.163f,
                    dx2 = -4.323f,
                    dy2 = 0.349f,
                    dx3 = -5.765f,
                    dy3 = -1.803f,
                )
                close()
            }
            // M32.18 54.41 c-.83 -2.338 -2.776 -2.363 -3.645 -2.094 .736 -.43 2.159 -.144 2.835 .485 .517 .5 .776 1.292 .842 1.636 l-.032 -.026Z
            path(
                fill = Brush.linearGradient(
                    0.0f to Color(0xFFE2DDE2),
                    1.0f to Color(0x00E2DDE2),
                    start = Offset(x = 30.031f, y = 51.131f),
                    end = Offset(x = 30.54f, y = 54.499f),
                ),
                fillAlpha = 0.8f,
                strokeAlpha = 0.8f,
            ) {
                // M 32.18 54.41
                moveTo(x = 32.18f, y = 54.41f)
                // c -0.83 -2.338 -2.776 -2.363 -3.645 -2.094
                curveToRelative(
                    dx1 = -0.83f,
                    dy1 = -2.338f,
                    dx2 = -2.776f,
                    dy2 = -2.363f,
                    dx3 = -3.645f,
                    dy3 = -2.094f,
                )
                // c 0.736 -0.43 2.159 -0.144 2.835 0.485
                curveToRelative(
                    dx1 = 0.736f,
                    dy1 = -0.43f,
                    dx2 = 2.159f,
                    dy2 = -0.144f,
                    dx3 = 2.835f,
                    dy3 = 0.485f,
                )
                // c 0.517 0.5 0.776 1.292 0.842 1.636
                curveToRelative(
                    dx1 = 0.517f,
                    dy1 = 0.5f,
                    dx2 = 0.776f,
                    dy2 = 1.292f,
                    dx3 = 0.842f,
                    dy3 = 1.636f,
                )
                // l -0.032 -0.026z
                lineToRelative(dx = -0.032f, dy = -0.026f)
                close()
            }
            // M65.173 52.508 c-2.047 .116 -2.71 1.745 -2.796 2.53 .202 -.494 .951 -1.512 2.248 -1.774 1.297 -.232 2.998 1.251 3.69 2.036 l.634 -.727 c-.403 -.756 -1.73 -2.21 -3.776 -2.065Z
            path(
                fill = Brush.linearGradient(
                    0.0f to Color(0xFF373637),
                    1.0f to Color(0x00373637),
                    start = Offset(x = 65.663f, y = 52.508f),
                    end = Offset(x = 65.663f, y = 55.3f),
                ),
            ) {
                // M 65.173 52.508
                moveTo(x = 65.173f, y = 52.508f)
                // c -2.047 0.116 -2.71 1.745 -2.796 2.53
                curveToRelative(
                    dx1 = -2.047f,
                    dy1 = 0.116f,
                    dx2 = -2.71f,
                    dy2 = 1.745f,
                    dx3 = -2.796f,
                    dy3 = 2.53f,
                )
                // c 0.202 -0.494 0.951 -1.512 2.248 -1.774
                curveToRelative(
                    dx1 = 0.202f,
                    dy1 = -0.494f,
                    dx2 = 0.951f,
                    dy2 = -1.512f,
                    dx3 = 2.248f,
                    dy3 = -1.774f,
                )
                // c 1.297 -0.232 2.998 1.251 3.69 2.036
                curveToRelative(
                    dx1 = 1.297f,
                    dy1 = -0.232f,
                    dx2 = 2.998f,
                    dy2 = 1.251f,
                    dx3 = 3.69f,
                    dy3 = 2.036f,
                )
                // l 0.634 -0.727
                lineToRelative(dx = 0.634f, dy = -0.727f)
                // c -0.403 -0.756 -1.73 -2.21 -3.776 -2.065z
                curveToRelative(
                    dx1 = -0.403f,
                    dy1 = -0.756f,
                    dx2 = -1.73f,
                    dy2 = -2.21f,
                    dx3 = -3.776f,
                    dy3 = -2.065f,
                )
                close()
            }
            // M68.744 54.193 c1.384 1.687 1.528 3.694 1.355 4.538 .346 -1.658 .23 -3.258 -1.096 -4.887 -.72 -.901 -1.844 -1.396 -2.651 -1.629 -.116 -.029 -.26 -.058 -.346 -.087 a1.929 1.929 0 0 0 -.49 -.087 c.144 .03 .317 .058 .49 .087 .115 0 .23 .058 .346 .087 .576 .204 1.354 .64 2.42 1.978 h-.028Z
            path(
                fill = SolidColor(Color(0xFF000000)),
            ) {
                // M 68.744 54.193
                moveTo(x = 68.744f, y = 54.193f)
                // c 1.384 1.687 1.528 3.694 1.355 4.538
                curveToRelative(
                    dx1 = 1.384f,
                    dy1 = 1.687f,
                    dx2 = 1.528f,
                    dy2 = 3.694f,
                    dx3 = 1.355f,
                    dy3 = 4.538f,
                )
                // c 0.346 -1.658 0.23 -3.258 -1.096 -4.887
                curveToRelative(
                    dx1 = 0.346f,
                    dy1 = -1.658f,
                    dx2 = 0.23f,
                    dy2 = -3.258f,
                    dx3 = -1.096f,
                    dy3 = -4.887f,
                )
                // c -0.72 -0.901 -1.844 -1.396 -2.651 -1.629
                curveToRelative(
                    dx1 = -0.72f,
                    dy1 = -0.901f,
                    dx2 = -1.844f,
                    dy2 = -1.396f,
                    dx3 = -2.651f,
                    dy3 = -1.629f,
                )
                // c -0.116 -0.029 -0.26 -0.058 -0.346 -0.087
                curveToRelative(
                    dx1 = -0.116f,
                    dy1 = -0.029f,
                    dx2 = -0.26f,
                    dy2 = -0.058f,
                    dx3 = -0.346f,
                    dy3 = -0.087f,
                )
                // a 1.929 1.929 0 0 0 -0.49 -0.087
                arcToRelative(
                    a = 1.929f,
                    b = 1.929f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -0.49f,
                    dy1 = -0.087f,
                )
                // c 0.144 0.03 0.317 0.058 0.49 0.087
                curveToRelative(
                    dx1 = 0.144f,
                    dy1 = 0.03f,
                    dx2 = 0.317f,
                    dy2 = 0.058f,
                    dx3 = 0.49f,
                    dy3 = 0.087f,
                )
                // c 0.115 0 0.23 0.058 0.346 0.087
                curveToRelative(
                    dx1 = 0.115f,
                    dy1 = 0.0f,
                    dx2 = 0.23f,
                    dy2 = 0.058f,
                    dx3 = 0.346f,
                    dy3 = 0.087f,
                )
                // c 0.576 0.204 1.354 0.64 2.42 1.978
                curveToRelative(
                    dx1 = 0.576f,
                    dy1 = 0.204f,
                    dx2 = 1.354f,
                    dy2 = 0.64f,
                    dx3 = 2.42f,
                    dy3 = 1.978f,
                )
                // h -0.028z
                horizontalLineToRelative(dx = -0.028f)
                close()
            }
            // M27.658 53.506 c-.808 .174 -1.413 1.018 -1.586 1.425 l.404 .61 s.288 -.494 .605 -.814 c.317 -.32 .807 -.668 1.009 -.814 l-.404 -.407 h-.028Z M66.956 53.525 c.779 .146 1.384 .96 1.586 1.367 l-.404 .582 s-.288 -.465 -.605 -.785 -.779 -.64 -.98 -.785 l.403 -.379Z
            path(
                fill = SolidColor(Color(0xFFE2DCE1)),
                fillAlpha = 0.8f,
                strokeAlpha = 0.8f,
            ) {
                // M 27.658 53.506
                moveTo(x = 27.658f, y = 53.506f)
                // c -0.808 0.174 -1.413 1.018 -1.586 1.425
                curveToRelative(
                    dx1 = -0.808f,
                    dy1 = 0.174f,
                    dx2 = -1.413f,
                    dy2 = 1.018f,
                    dx3 = -1.586f,
                    dy3 = 1.425f,
                )
                // l 0.404 0.61
                lineToRelative(dx = 0.404f, dy = 0.61f)
                // s 0.288 -0.494 0.605 -0.814
                reflectiveCurveToRelative(
                    dx1 = 0.288f,
                    dy1 = -0.494f,
                    dx2 = 0.605f,
                    dy2 = -0.814f,
                )
                // c 0.317 -0.32 0.807 -0.668 1.009 -0.814
                curveToRelative(
                    dx1 = 0.317f,
                    dy1 = -0.32f,
                    dx2 = 0.807f,
                    dy2 = -0.668f,
                    dx3 = 1.009f,
                    dy3 = -0.814f,
                )
                // l -0.404 -0.407
                lineToRelative(dx = -0.404f, dy = -0.407f)
                // h -0.028z
                horizontalLineToRelative(dx = -0.028f)
                close()
                // M 66.956 53.525
                moveTo(x = 66.956f, y = 53.525f)
                // c 0.779 0.146 1.384 0.96 1.586 1.367
                curveToRelative(
                    dx1 = 0.779f,
                    dy1 = 0.146f,
                    dx2 = 1.384f,
                    dy2 = 0.96f,
                    dx3 = 1.586f,
                    dy3 = 1.367f,
                )
                // l -0.404 0.582
                lineToRelative(dx = -0.404f, dy = 0.582f)
                // s -0.288 -0.465 -0.605 -0.785
                reflectiveCurveToRelative(
                    dx1 = -0.288f,
                    dy1 = -0.465f,
                    dx2 = -0.605f,
                    dy2 = -0.785f,
                )
                // s -0.779 -0.64 -0.98 -0.785
                reflectiveCurveToRelative(
                    dx1 = -0.779f,
                    dy1 = -0.64f,
                    dx2 = -0.98f,
                    dy2 = -0.785f,
                )
                // l 0.403 -0.379z
                lineToRelative(dx = 0.403f, dy = -0.379f)
                close()
            }
            // M30.947 57.86 c-.375 .203 -.98 .494 -1.24 .61 l-.23 .698 a3.781 3.781 0 0 0 1.21 -.698 l.26 -.61Z M32.154 54.802 a4.56 4.56 0 0 0 -.23 -.61 v.261 c.115 .204 .202 .582 .23 .756 0 -.058 .058 -.232 0 -.436 v.03Z
            path(
                fill = SolidColor(Color(0xFFE2DCE1)),
                fillAlpha = 0.8f,
                strokeAlpha = 0.8f,
            ) {
                // M 30.947 57.86
                moveTo(x = 30.947f, y = 57.86f)
                // c -0.375 0.203 -0.98 0.494 -1.24 0.61
                curveToRelative(
                    dx1 = -0.375f,
                    dy1 = 0.203f,
                    dx2 = -0.98f,
                    dy2 = 0.494f,
                    dx3 = -1.24f,
                    dy3 = 0.61f,
                )
                // l -0.23 0.698
                lineToRelative(dx = -0.23f, dy = 0.698f)
                // a 3.781 3.781 0 0 0 1.21 -0.698
                arcToRelative(
                    a = 3.781f,
                    b = 3.781f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 1.21f,
                    dy1 = -0.698f,
                )
                // l 0.26 -0.61z
                lineToRelative(dx = 0.26f, dy = -0.61f)
                close()
                // M 32.154 54.802
                moveTo(x = 32.154f, y = 54.802f)
                // a 4.56 4.56 0 0 0 -0.23 -0.61
                arcToRelative(
                    a = 4.56f,
                    b = 4.56f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -0.23f,
                    dy1 = -0.61f,
                )
                // v 0.261
                verticalLineToRelative(dy = 0.261f)
                // c 0.115 0.204 0.202 0.582 0.23 0.756
                curveToRelative(
                    dx1 = 0.115f,
                    dy1 = 0.204f,
                    dx2 = 0.202f,
                    dy2 = 0.582f,
                    dx3 = 0.23f,
                    dy3 = 0.756f,
                )
                // c 0 -0.058 0.058 -0.232 0 -0.436
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = -0.058f,
                    dx2 = 0.058f,
                    dy2 = -0.232f,
                    dx3 = 0.0f,
                    dy3 = -0.436f,
                )
                // v 0.03z
                verticalLineToRelative(dy = 0.03f)
                close()
            }
            // M63.588 57.922 c.375 .204 .98 .494 1.24 .61 l.23 .699 a3.782 3.782 0 0 1 -1.21 -.698 l-.26 -.611Z M62.403 54.865 a4.56 4.56 0 0 1 .23 -.611 v.262 c-.115 .203 -.202 .581 -.23 .756 0 -.058 -.058 -.233 0 -.436 v.029Z
            path(
                fill = SolidColor(Color(0xFFE2DCE1)),
                fillAlpha = 0.8f,
                strokeAlpha = 0.8f,
            ) {
                // M 63.588 57.922
                moveTo(x = 63.588f, y = 57.922f)
                // c 0.375 0.204 0.98 0.494 1.24 0.61
                curveToRelative(
                    dx1 = 0.375f,
                    dy1 = 0.204f,
                    dx2 = 0.98f,
                    dy2 = 0.494f,
                    dx3 = 1.24f,
                    dy3 = 0.61f,
                )
                // l 0.23 0.699
                lineToRelative(dx = 0.23f, dy = 0.699f)
                // a 3.782 3.782 0 0 1 -1.21 -0.698
                arcToRelative(
                    a = 3.782f,
                    b = 3.782f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -1.21f,
                    dy1 = -0.698f,
                )
                // l -0.26 -0.611z
                lineToRelative(dx = -0.26f, dy = -0.611f)
                close()
                // M 62.403 54.865
                moveTo(x = 62.403f, y = 54.865f)
                // a 4.56 4.56 0 0 1 0.23 -0.611
                arcToRelative(
                    a = 4.56f,
                    b = 4.56f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.23f,
                    dy1 = -0.611f,
                )
                // v 0.262
                verticalLineToRelative(dy = 0.262f)
                // c -0.115 0.203 -0.202 0.581 -0.23 0.756
                curveToRelative(
                    dx1 = -0.115f,
                    dy1 = 0.203f,
                    dx2 = -0.202f,
                    dy2 = 0.581f,
                    dx3 = -0.23f,
                    dy3 = 0.756f,
                )
                // c 0 -0.058 -0.058 -0.233 0 -0.436
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = -0.058f,
                    dx2 = -0.058f,
                    dy2 = -0.233f,
                    dx3 = 0.0f,
                    dy3 = -0.436f,
                )
                // v 0.029z
                verticalLineToRelative(dy = 0.029f)
                close()
            }
        }.build().also { _android = it }
    }

@Suppress("ObjectPropertyName")
private var _android: ImageVector? = null
