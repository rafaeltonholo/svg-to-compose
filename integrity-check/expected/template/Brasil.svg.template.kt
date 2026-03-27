package dev.tonholo.s2c.integrity.icon.svg

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
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

val Icons.Brasil: ImageVector by lazy {
    icon(name = "Brasil", viewportWidth = 4200.0f, viewportHeight = 2940.0f) {
        brasilPart1()
        brasilPart2()
    }
}

private fun ImageVector.Builder.brasilPart1() {
    iconPath(fill = SolidColor(Color(0xFF009B3A))) {
        // M 0 0
        moveTo(x = 0.0f, y = 0.0f)
        // l 4200 0
        lineToRelative(dx = 4200.0f, dy = 0.0f)
        // l 0 2940
        lineToRelative(dx = 0.0f, dy = 2940.0f)
        // l -4200 0z
        lineToRelative(dx = -4200.0f, dy = 0.0f)
        close()
    }
    iconPath(fill = SolidColor(Color(0xFFFEDF00))) {
        // M 357 1470
        moveTo(x = 357.0f, y = 1470.0f)
        // L 2100 2583
        lineTo(x = 2100.0f, y = 2583.0f)
        // L 3843 1470
        lineTo(x = 3843.0f, y = 1470.0f)
        // L 2100 357z
        lineTo(x = 2100.0f, y = 357.0f)
        close()
    }
    iconPath(fill = SolidColor(Color(0xFF002776))) {
        // M 2100 1470
        moveTo(x = 2100.0f, y = 1470.0f)
        // m -735 0
        moveToRelative(dx = -735.0f, dy = 0.0f)
        // a 735 735 0 1 1 1470 0
        arcToRelative(
            a = 735.0f,
            b = 735.0f,
            theta = 0.0f,
            isMoreThanHalf = true,
            isPositiveArc = true,
            dx1 = 1470.0f,
            dy1 = 0.0f,
        )
        // a 735 735 0 1 1 -1470 0z
        arcToRelative(
            a = 735.0f,
            b = 735.0f,
            theta = 0.0f,
            isMoreThanHalf = true,
            isPositiveArc = true,
            dx1 = -1470.0f,
            dy1 = 0.0f,
        )
        close()
    }
    iconGroup(
        clipPathData = PathData {
            // M 2100 1470
            moveTo(x = 2100.0f, y = 1470.0f)
            // m -735 0
            moveToRelative(dx = -735.0f, dy = 0.0f)
            // a 735 735 0 1 1 1470 0
            arcToRelative(
                a = 735.0f,
                b = 735.0f,
                theta = 0.0f,
                isMoreThanHalf = true,
                isPositiveArc = true,
                dx1 = 1470.0f,
                dy1 = 0.0f,
            )
            // a 735 735 0 1 1 -1470 0z
            arcToRelative(
                a = 735.0f,
                b = 735.0f,
                theta = 0.0f,
                isMoreThanHalf = true,
                isPositiveArc = true,
                dx1 = -1470.0f,
                dy1 = 0.0f,
            )
            close()
        },
    ) {
        iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
            // M -105 2940
            moveTo(x = -105.0f, y = 2940.0f)
            // a 1785 1785 0 0 1 3570 0
            arcToRelative(
                a = 1785.0f,
                b = 1785.0f,
                theta = 0.0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                dx1 = 3570.0f,
                dy1 = 0.0f,
            )
            // l -105 0
            lineToRelative(dx = -105.0f, dy = 0.0f)
            // a 1680 1680 0 1 0 -3360 0z
            arcToRelative(
                a = 1680.0f,
                b = 1680.0f,
                theta = 0.0f,
                isMoreThanHalf = true,
                isPositiveArc = false,
                dx1 = -3360.0f,
                dy1 = 0.0f,
            )
            close()
        }
    }
    iconPath(fill = SolidColor(Color(0xFF009B3A)), pathFillType = PathFillType.EvenOdd) {
        // M 1473.1268 1255.153
        moveTo(x = 1473.1268f, y = 1255.153f)
        // a 31.5 35 -7 0 0 -8.530854 -69.47823
        arcToRelative(
            a = 31.5f,
            b = 35.0f,
            theta = -7.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -8.530854f,
            dy1 = -69.47823f,
        )
        // A 31.5 35 -7 0 0 1473.1268 1255.153
        arcTo(
            horizontalEllipseRadius = 31.5f,
            verticalEllipseRadius = 35.0f,
            theta = -7.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            x1 = 1473.1268f,
            y1 = 1255.153f,
        )
        // m -1.5843015 -12.9031
        moveToRelative(dx = -1.5843015f, dy = -12.9031f)
        // a 18.5 22 -7 0 0 -5.3622513 -43.67203
        arcToRelative(
            a = 18.5f,
            b = 22.0f,
            theta = -7.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -5.3622513f,
            dy1 = -43.67203f,
        )
        // a 18.5 22 -7 0 0 5.3622513 43.67203
        arcToRelative(
            a = 18.5f,
            b = 22.0f,
            theta = -7.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 5.3622513f,
            dy1 = 43.67203f,
        )
    }
    iconPath(fill = SolidColor(Color(0xFF009B3A)), pathFillType = PathFillType.EvenOdd) {
        // M 1530.1652 1248.8324
        moveTo(x = 1530.1652f, y = 1248.8324f)
        // l 12.968332 -0.9068342
        lineToRelative(dx = 12.968332f, dy = -0.9068342f)
        // l -1.8136684 -25.936665
        lineToRelative(dx = -1.8136684f, dy = -25.936665f)
        // l 27.931793 -1.9531813
        lineToRelative(dx = 27.931793f, dy = -1.9531813f)
        // a 22 22 0 0 0 -3.069285 -43.89282
        arcToRelative(
            a = 22.0f,
            b = 22.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -3.069285f,
            dy1 = -43.89282f,
        )
        // l -39.90256 2.790259z
        lineToRelative(dx = -39.90256f, dy = 2.790259f)
        close()
        // m 10.24783 -39.811832
        moveToRelative(dx = 10.24783f, dy = -39.811832f)
        // l 26.934229 -1.8834248
        lineToRelative(dx = 26.934229f, dy = -1.8834248f)
        // a 9 9 0 0 0 -1.2556165 -17.956152
        arcToRelative(
            a = 9.0f,
            b = 9.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -1.2556165f,
            dy1 = -17.956152f,
        )
        // l -26.934229 1.8834248z
        lineToRelative(dx = -26.934229f, dy = 1.8834248f)
        close()
    }
    iconPath(fill = SolidColor(Color(0xFF009B3A))) {
        // M 1589.5201 1244.6819
        moveTo(x = 1589.5201f, y = 1244.6819f)
        // c -0.6975647 -9.97564 -2.232207 -31.92205 -17.195667 -30.875702
        curveToRelative(
            dx1 = -0.6975647f,
            dy1 = -9.97564f,
            dx2 = -2.232207f,
            dy2 = -31.92205f,
            dx3 = -17.195667f,
            dy3 = -30.875702f,
        )
        // L 1553.3708 1215.1315
        lineTo(x = 1553.3708f, y = 1215.1315f)
        // c 21.94641 -1.5346425 23.481052 20.411766 24.178616 30.387407
        curveToRelative(
            dx1 = 21.94641f,
            dy1 = -1.5346425f,
            dx2 = 23.481052f,
            dy2 = 20.411766f,
            dx3 = 24.178616f,
            dy3 = 30.387407f,
        )
    }
    iconPath(fill = SolidColor(Color(0xFF009B3A)), pathFillType = PathFillType.EvenOdd) {
        // M 1618.8794 1243.3082
        moveTo(x = 1618.8794f, y = 1243.3082f)
        // l 32.994972 -0.5759294
        lineToRelative(dx = 32.994972f, dy = -0.5759294f)
        // a 30 30 0 0 0 29.471859 -30.519003
        arcToRelative(
            a = 30.0f,
            b = 30.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 29.471859f,
            dy1 = -30.519003f,
        )
        // l -0.17452407 -9.998477
        lineToRelative(dx = -0.17452407f, dy = -9.998477f)
        // a 30 30 0 0 0 -30.519003 -29.471859
        arcToRelative(
            a = 30.0f,
            b = 30.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -30.519003f,
            dy1 = -29.471859f,
        )
        // l -32.994972 0.5759294z
        lineToRelative(dx = -32.994972f, dy = 0.5759294f)
        close()
        // m 12.771139 -13.224901
        moveToRelative(dx = 12.771139f, dy = -13.224901f)
        // l 18.997107 -0.33159572
        lineToRelative(dx = 18.997107f, dy = -0.33159572f)
        // a 19 19 0 0 0 18.66551 -19.328701
        arcToRelative(
            a = 19.0f,
            b = 19.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 18.66551f,
            dy1 = -19.328701f,
        )
        // l -0.10471444 -5.9990864
        lineToRelative(dx = -0.10471444f, dy = -5.9990864f)
        // a 19 19 0 0 0 -19.328701 -18.66551
        arcToRelative(
            a = 19.0f,
            b = 19.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -19.328701f,
            dy1 = -18.66551f,
        )
        // l -18.997107 0.33159572z
        lineToRelative(dx = -18.997107f, dy = 0.33159572f)
        close()
    }
    iconPath(fill = SolidColor(Color(0xFF009B3A))) {
        // M 1707.7611 1242.4347
        moveTo(x = 1707.7611f, y = 1242.4347f)
        // l 62.96162 2.1986682
        lineToRelative(dx = 62.96162f, dy = 2.1986682f)
        // l 0.45369345 -12.992081
        lineToRelative(dx = 0.45369345f, dy = -12.992081f)
        // L 1720.2075 1229.8615
        lineTo(x = 1720.2075f, y = 1229.8615f)
        // l 0.62819093 -17.989035
        lineToRelative(dx = 0.62819093f, dy = -17.989035f)
        // l 39.97563 1.3959799
        lineToRelative(dx = 39.97563f, dy = 1.3959799f)
        // l 0.41879395 -11.99269
        lineToRelative(dx = 0.41879395f, dy = -11.99269f)
        // L 1721.2544 1199.8798
        lineTo(x = 1721.2544f, y = 1199.8798f)
        // l 0.48859295 -13.991471
        lineToRelative(dx = 0.48859295f, dy = -13.991471f)
        // l 47.97076 1.6751758
        lineToRelative(dx = 47.97076f, dy = 1.6751758f)
        // l 0.45369345 -12.992081
        lineToRelative(dx = 0.45369345f, dy = -12.992081f)
        // L 1710.2041 1172.4774z
        lineTo(x = 1710.2041f, y = 1172.4774f)
        close()
    }
    iconPath(fill = SolidColor(Color(0xFF009B3A))) {
        // M 1796.5668 1246.2141
        moveTo(x = 1796.5668f, y = 1246.2141f)
        // l 11.954336 1.0458689
        lineToRelative(dx = 11.954336f, dy = 1.0458689f)
        // l 4.1834755 -47.817345
        lineToRelative(dx = 4.1834755f, dy = -47.817345f)
        // l 9.76325 49.037525
        lineToRelative(dx = 9.76325f, dy = 49.037525f)
        // l 10.958141 0.9587132
        lineToRelative(dx = 10.958141f, dy = 0.9587132f)
        // l 18.130201 -46.597164
        lineToRelative(dx = 18.130201f, dy = -46.597164f)
        // L 1847.3727 1250.659
        lineTo(x = 1847.3727f, y = 1250.659f)
        // l 11.954336 1.0458689
        lineToRelative(dx = 11.954336f, dy = 1.0458689f)
        // l 6.100902 -69.73363
        lineToRelative(dx = 6.100902f, dy = -69.73363f)
        // L 1847.9945 1180.446
        lineTo(x = 1847.9945f, y = 1180.446f)
        // L 1829.8643 1227.0432
        lineTo(x = 1829.8643f, y = 1227.0432f)
        // l -9.76325 -49.037525
        lineToRelative(dx = -9.76325f, dy = -49.037525f)
        // l -17.433407 -1.5252255z
        lineToRelative(dx = -17.433407f, dy = -1.5252255f)
        close()
    }
    iconPath(fill = SolidColor(Color(0xFF009B3A))) {
        // M 1941.6 1262.5731
        moveTo(x = 1941.6f, y = 1262.5731f)
        // l 51.74169 8.890849
        lineToRelative(dx = 51.74169f, dy = 8.890849f)
        // l 2.0321941 -11.826673
        lineToRelative(dx = 2.0321941f, dy = -11.826673f)
        // l -39.91502 -6.858655
        lineToRelative(dx = -39.91502f, dy = -6.858655f)
        // l 2.709592 -15.768897
        lineToRelative(dx = 2.709592f, dy = -15.768897f)
        // l 32.52335 5.5885334
        lineToRelative(dx = 32.52335f, dy = 5.5885334f)
        // l 2.0321941 -11.826673
        lineToRelative(dx = 2.0321941f, dy = -11.826673f)
        // l -32.52335 -5.5885334
        lineToRelative(dx = -32.52335f, dy = -5.5885334f)
        // l 1.8628446 -10.841117
        lineToRelative(dx = 1.8628446f, dy = -10.841117f)
        // L 2000.7465 1220.989
        lineTo(x = 2000.7465f, y = 1220.989f)
        // l 2.0321941 -11.826673
        lineToRelative(dx = 2.0321941f, dy = -11.826673f)
        // l -50.509747 -8.679162z
        lineToRelative(dx = -50.509747f, dy = -8.679162f)
        close()
    }
    iconPath(fill = SolidColor(Color(0xFF009B3A)), pathFillType = PathFillType.EvenOdd) {
        // M 2074.5234 1288.6824
        moveTo(x = 2074.5234f, y = 1288.6824f)
        // l 12.585919 3.25494
        lineToRelative(dx = 12.585919f, dy = 3.25494f)
        // l 6.50988 -25.171839
        lineToRelative(dx = 6.50988f, dy = -25.171839f)
        // l 27.108133 7.01064
        lineToRelative(dx = 27.108133f, dy = 7.01064f)
        // a 22 22 0 0 0 11.01672 -42.598495
        arcToRelative(
            a = 22.0f,
            b = 22.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 11.01672f,
            dy1 = -42.598495f,
        )
        // l -38.725906 -10.015201z
        lineToRelative(dx = -38.725906f, dy = -10.015201f)
        close()
        // m 22.35074 -34.50282
        moveToRelative(dx = 22.35074f, dy = -34.50282f)
        // l 26.139986 6.76026
        lineToRelative(dx = 26.139986f, dy = 6.76026f)
        // a 9 9 0 0 0 4.50684 -17.426657
        arcToRelative(
            a = 9.0f,
            b = 9.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 4.50684f,
            dy1 = -17.426657f,
        )
        // l -26.139986 -6.76026z
        lineToRelative(dx = -26.139986f, dy = -6.76026f)
        close()
    }
    iconPath(fill = SolidColor(Color(0xFF009B3A)), pathFillType = PathFillType.EvenOdd) {
        // M 2160.406 1311.5933
        moveTo(x = 2160.406f, y = 1311.5933f)
        // l 12.39832 3.9091754
        lineToRelative(dx = 12.39832f, dy = 3.9091754f)
        // l 7.818351 -24.79664
        lineToRelative(dx = 7.818351f, dy = -24.79664f)
        // l 26.704075 8.419763
        lineToRelative(dx = 26.704075f, dy = 8.419763f)
        // a 22 22 0 0 0 13.231055 -41.963547
        arcToRelative(
            a = 22.0f,
            b = 22.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 13.231055f,
            dy1 = -41.963547f,
        )
        // l -38.148678 -12.028232z
        lineToRelative(dx = -38.148678f, dy = -12.028232f)
        close()
        // m 24.125847 -33.285786
        moveToRelative(dx = 24.125847f, dy = -33.285786f)
        // l 25.750359 8.119057
        lineToRelative(dx = 25.750359f, dy = 8.119057f)
        // a 9 9 0 0 0 5.4127045 -17.166904
        arcToRelative(
            a = 9.0f,
            b = 9.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 5.4127045f,
            dy1 = -17.166904f,
        )
        // l -25.750359 -8.119057z
        lineToRelative(dx = -25.750359f, dy = -8.119057f)
        close()
    }
    iconPath(fill = SolidColor(Color(0xFF009B3A))) {
        // M 2217.152 1329.4852
        moveTo(x = 2217.152f, y = 1329.4852f)
        // c 3.007058 -9.537169 9.622585 -30.518942 -4.683169 -35.02953
        curveToRelative(
            dx1 = 3.007058f,
            dy1 = -9.537169f,
            dx2 = 9.622585f,
            dy2 = -30.518942f,
            dx3 = -4.683169f,
            dy3 = -35.02953f,
        )
        // L 2194.3484 1288.7423
        lineTo(x = 2194.3484f, y = 1288.7423f)
        // c 20.981773 6.6155276 14.366245 27.5973 11.359187 37.13447
        curveToRelative(
            dx1 = 20.981773f,
            dy1 = 6.6155276f,
            dx2 = 14.366245f,
            dy2 = 27.5973f,
            dx3 = 11.359187f,
            dy3 = 37.13447f,
        )
    }
    iconPath(fill = SolidColor(Color(0xFF009B3A)), pathFillType = PathFillType.EvenOdd) {
        // M 2274.477 1349.9989
        moveTo(x = 2274.477f, y = 1349.9989f)
        // a 31.5 35 20.5 0 0 24.514517 -65.567055
        arcToRelative(
            a = 31.5f,
            b = 35.0f,
            theta = 20.5f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 24.514517f,
            dy1 = -65.567055f,
        )
        // A 31.5 35 20.5 0 0 2274.477 1349.9989
        arcTo(
            horizontalEllipseRadius = 31.5f,
            verticalEllipseRadius = 35.0f,
            theta = 20.5f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            x1 = 2274.477f,
            y1 = 1349.9989f,
        )
        // m 4.5526958 -12.176739
        moveToRelative(dx = 4.5526958f, dy = -12.176739f)
        // a 18.5 22 20.5 0 0 15.409124 -41.213577
        arcToRelative(
            a = 18.5f,
            b = 22.0f,
            theta = 20.5f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 15.409124f,
            dy1 = -41.213577f,
        )
        // a 18.5 22 20.5 0 0 -15.409124 41.213577
        arcToRelative(
            a = 18.5f,
            b = 22.0f,
            theta = 20.5f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -15.409124f,
            dy1 = 41.213577f,
        )
    }
    iconGroup(
        clipPathData = PathData {
            moveTo(x = 2327.9893f, y = 1370.73f)
            lineToRelative(dx = 27.912436f, dy = -64.19421f)
            lineToRelative(dx = 57.774784f, dy = 25.121191f)
            lineTo(x = 2385.764f, y = 1395.8511f)
            close()
            moveTo(x = 2375.6177f, y = 1340.1887f)
            lineToRelative(dx = -4.784989f, dy = 11.004721f)
            lineToRelative(dx = 28.887392f, dy = 12.5605955f)
            lineToRelative(dx = 4.784989f, dy = -11.004721f)
            close()
        },
    ) {
        iconGroup(
            clipPathData = PathData {
                // M 2327.9893 1370.73
                moveTo(x = 2327.9893f, y = 1370.73f)
                // l 27.912436 -64.19421
                lineToRelative(dx = 27.912436f, dy = -64.19421f)
                // l 57.774784 25.121191
                lineToRelative(dx = 57.774784f, dy = 25.121191f)
                // L 2385.764 1395.8511z
                lineTo(x = 2385.764f, y = 1395.8511f)
                close()
                // M 2375.6177 1340.1887
                moveTo(x = 2375.6177f, y = 1340.1887f)
                // l -4.784989 11.004721
                lineToRelative(dx = -4.784989f, dy = 11.004721f)
                // l 28.887392 12.5605955
                lineToRelative(dx = 28.887392f, dy = 12.5605955f)
                // l 4.784989 -11.004721z
                lineToRelative(dx = 4.784989f, dy = -11.004721f)
                close()
            },
        ) {
            iconPath(fill = SolidColor(Color(0xFF009B3A)), pathFillType = PathFillType.EvenOdd) {
                // M 2356.8765 1383.2905
                moveTo(x = 2356.8765f, y = 1383.2905f)
                // a 31.5 35 23.5 0 0 27.912436 -64.19421
                arcToRelative(
                    a = 31.5f,
                    b = 35.0f,
                    theta = 23.5f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 27.912436f,
                    dy1 = -64.19421f,
                )
                // A 31.5 35 23.5 0 0 2356.8765 1383.2905
                arcTo(
                    horizontalEllipseRadius = 31.5f,
                    verticalEllipseRadius = 35.0f,
                    theta = 23.5f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 2356.8765f,
                    y1 = 1383.2905f,
                )
                // m 5.1837378 -11.921781
                moveToRelative(dx = 5.1837378f, dy = -11.921781f)
                // a 18.5 22 23.5 0 0 17.544958 -40.350643
                arcToRelative(
                    a = 18.5f,
                    b = 22.0f,
                    theta = 23.5f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 17.544958f,
                    dy1 = -40.350643f,
                )
                // a 18.5 22 23.5 0 0 -17.544958 40.350643
                arcToRelative(
                    a = 18.5f,
                    b = 22.0f,
                    theta = 23.5f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -17.544958f,
                    dy1 = 40.350643f,
                )
            }
        }
        iconPath(fill = SolidColor(Color(0xFF009B3A))) {
            // M 2375.418 1353.1871
            moveTo(x = 2375.418f, y = 1353.1871f)
            // l 24.302092 10.566851
            lineToRelative(dx = 24.302092f, dy = 10.566851f)
            // l -3.9874907 9.170601
            lineToRelative(dx = -3.9874907f, dy = 9.170601f)
            // L 2371.4307 1362.3578z
            lineTo(x = 2371.4307f, y = 1362.3578f)
            close()
        }
        iconPath(fill = SolidColor(Color(0xFF009B3A))) {
            // M 2390.5496 1359.7665
            moveTo(x = 2390.5496f, y = 1359.7665f)
            // l 9.170601 3.9874907
            lineToRelative(dx = 9.170601f, dy = 3.9874907f)
            // L 2385.764 1395.8511
            lineTo(x = 2385.764f, y = 1395.8511f)
            // l -9.170601 -3.9874907z
            lineToRelative(dx = -9.170601f, dy = -3.9874907f)
            close()
        }
    }
    iconPath(fill = SolidColor(Color(0xFF009B3A)), pathFillType = PathFillType.EvenOdd) {
        // M 2409.2305 1406.7937
        moveTo(x = 2409.2305f, y = 1406.7937f)
        // l 11.634147 5.8005714
        lineToRelative(dx = 11.634147f, dy = 5.8005714f)
        // l 11.601143 -23.268293
        lineToRelative(dx = 11.601143f, dy = -23.268293f)
        // l 25.058163 12.493539
        lineToRelative(dx = 25.058163f, dy = 12.493539f)
        // a 22 22 0 0 0 19.632704 -39.377113
        arcToRelative(
            a = 22.0f,
            b = 22.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 19.632704f,
            dy1 = -39.377113f,
        )
        // l -35.797375 -17.847912z
        lineToRelative(dx = -35.797375f, dy = -17.847912f)
        close()
        // m 29.035862 -29.101868
        moveToRelative(dx = 29.035862f, dy = -29.101868f)
        // l 24.163227 12.047341
        lineToRelative(dx = 24.163227f, dy = 12.047341f)
        // a 9 9 0 0 0 8.031561 -16.108818
        arcToRelative(
            a = 9.0f,
            b = 9.0f,
            theta = 0.0f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 8.031561f,
            dy1 = -16.108818f,
        )
        // l -24.163227 -12.047341z
        lineToRelative(dx = -24.163227f, dy = -12.047341f)
        close()
    }
    iconPath(fill = SolidColor(Color(0xFF009B3A))) {
        // M 2462.479 1433.3424
        moveTo(x = 2462.479f, y = 1433.3424f)
        // c 4.461978 -8.949344 14.27833 -28.6379 0.8543146 -35.330868
        curveToRelative(
            dx1 = 4.461978f,
            dy1 = -8.949344f,
            dx2 = 14.27833f,
            dy2 = -28.6379f,
            dx3 = 0.8543146f,
            dy3 = -35.330868f,
        )
        // L 2446.3296 1389.5338
        lineTo(x = 2446.3296f, y = 1389.5338f)
        // c 19.688557 9.816352 9.872204 29.504908 5.410226 38.45425
        curveToRelative(
            dx1 = 19.688557f,
            dy1 = 9.816352f,
            dx2 = 9.872204f,
            dy2 = 29.504908f,
            dx3 = 5.410226f,
            dy3 = 38.45425f,
        )
    }
    iconPath(fill = SolidColor(Color(0xFF009B3A))) {
        // M 2488.473 1447.0598
        moveTo(x = 2488.473f, y = 1447.0598f)
        // l 54.83241 31.022684
        lineToRelative(dx = 54.83241f, dy = 31.022684f)
        // l 6.4015064 -11.314624
        lineToRelative(dx = 6.4015064f, dy = -11.314624f)
        // L 2505.3186 1441.6543
        lineTo(x = 2505.3186f, y = 1441.6543f)
        // l 8.863624 -15.666403
        lineToRelative(dx = 8.863624f, dy = -15.666403f)
        // l 34.814228 19.696943
        lineToRelative(dx = 34.814228f, dy = 19.696943f)
        // l 5.909083 -10.444268
        lineToRelative(dx = 5.909083f, dy = -10.444268f)
        // L 2520.0913 1415.5437
        lineTo(x = 2520.0913f, y = 1415.5437f)
        // l 6.89393 -12.184979
        lineToRelative(dx = 6.89393f, dy = -12.184979f)
        // l 41.777073 23.636332
        lineToRelative(dx = 41.777073f, dy = 23.636332f)
        // l 6.4015064 -11.314624
        lineToRelative(dx = 6.4015064f, dy = -11.314624f)
        // L 2522.9424 1386.135z
        lineTo(x = 2522.9424f, y = 1386.135f)
        close()
    }
    iconPath(fill = SolidColor(Color(0xFF009B3A))) {
        // M 2590.6033 1481.3259
        moveTo(x = 2590.6033f, y = 1481.3259f)
        // C 2586.8423 1487.2297 2590.6545 1493.8083 2599.0884 1499.1813
        curveTo(
            x1 = 2586.8423f,
            y1 = 1487.2297f,
            x2 = 2590.6545f,
            y2 = 1493.8083f,
            x3 = 2599.0884f,
            y3 = 1499.1813f,
        )
        // s 14.177814 5.178774 16.604095 1.3888855
        reflectiveCurveToRelative(
            dx1 = 14.177814f,
            dy1 = 5.178774f,
            dx2 = 16.604095f,
            dy2 = 1.3888855f,
        )
        // c 7.6565194 -12.018328 -36.607727 -29.546562 -22.96439 -50.497025
        curveToRelative(
            dx1 = 7.6565194f,
            dy1 = -12.018328f,
            dx2 = -36.607727f,
            dy2 = -29.546562f,
            dx3 = -22.96439f,
            dy3 = -50.497025f,
        )
        // C 2604.491 1432.0746 2624.6167 1446.0818 2632.2073 1450.9175
        curveTo(
            x1 = 2604.491f,
            y1 = 1432.0746f,
            x2 = 2624.6167f,
            y2 = 1446.0818f,
            x3 = 2632.2073f,
            y3 = 1450.9175f,
        )
        // s 19.778978 17.343355 10.299713 31.757534
        reflectiveCurveToRelative(
            dx1 = 19.778978f,
            dy1 = 17.343355f,
            dx2 = 10.299713f,
            dy2 = 31.757534f,
        )
        // L 2629.6453 1474.4812
        lineTo(x = 2629.6453f, y = 1474.4812f)
        // c 4.029747 -6.3254356 -0.39641914 -12.40586 -7.143551 -16.704256
        curveToRelative(
            dx1 = 4.029747f,
            dy1 = -6.3254356f,
            dx2 = -0.39641914f,
            dy2 = -12.40586f,
            dx3 = -7.143551f,
            dy3 = -16.704256f,
        )
        // c -6.5362835 -4.164072 -11.846561 -6.0649805 -15.741983 0.049607478
        curveToRelative(
            dx1 = -6.5362835f,
            dy1 = -4.164072f,
            dx2 = -11.846561f,
            dy2 = -6.0649805f,
            dx3 = -15.741983f,
            dy3 = 0.049607478f,
        )
        // c -6.5241184 9.775524 36.857655 28.223673 23.55949 49.09761
        curveToRelative(
            dx1 = -6.5241184f,
            dy1 = 9.775524f,
            dx2 = 36.857655f,
            dy2 = 28.223673f,
            dx3 = 23.55949f,
            dy3 = 49.09761f,
        )
        // C 2620.5134 1522.316 2603.452 1515.5966 2592.0662 1508.343
        curveTo(
            x1 = 2620.5134f,
            y1 = 1522.316f,
            x2 = 2603.452f,
            y2 = 1515.5966f,
            x3 = 2592.0662f,
            y3 = 1508.343f,
        )
        // c -9.699001 -6.1789455 -24.191153 -20.747065 -14.746239 -35.47955z
        curveToRelative(
            dx1 = -9.699001f,
            dy1 = -6.1789455f,
            dx2 = -24.191153f,
            dy2 = -20.747065f,
            dx3 = -14.746239f,
            dy3 = -35.47955f,
        )
        close()
    }
    iconPath(fill = SolidColor(Color(0xFF009B3A))) {
        // M 2665.6965 1530.9823
        moveTo(x = 2665.6965f, y = 1530.9823f)
        // C 2661.6316 1536.6812 2665.0942 1543.4502 2673.2354 1549.2573
        curveTo(
            x1 = 2661.6316f,
            y1 = 1536.6812f,
            x2 = 2665.0942f,
            y2 = 1543.4502f,
            x3 = 2673.2354f,
            y3 = 1549.2573f,
        )
        // s 13.887347 5.9136863 16.508652 2.2559733
        reflectiveCurveToRelative(
            dx1 = 13.887347f,
            dy1 = 5.9136863f,
            dx2 = 16.508652f,
            dy2 = 2.2559733f,
        )
        // c 8.275017 -11.601146 -35.01121 -31.42197 -20.290108 -51.62968
        curveToRelative(
            dx1 = 8.275017f,
            dy1 = -11.601146f,
            dx2 = -35.01121f,
            dy2 = -31.42197f,
            dx3 = -20.290108f,
            dy3 = -51.62968f,
        )
        // C 2682.1426 1482.5253 2701.5078 1497.5667 2708.8347 1502.793
        curveTo(
            x1 = 2682.1426f,
            y1 = 1482.5253f,
            x2 = 2701.5078f,
            y2 = 1497.5667f,
            x3 = 2708.8347f,
            y3 = 1502.793f,
        )
        // s 18.844193 18.354738 8.623537 32.253056
        reflectiveCurveToRelative(
            dx1 = 18.844193f,
            dy1 = 18.354738f,
            dx2 = 8.623537f,
            dy2 = 32.253056f,
        )
        // L 2705.0432 1526.1903
        lineTo(x = 2705.0432f, y = 1526.1903f)
        // c 4.3552723 -6.1058664 0.25339666 -12.409605 -6.2595277 -17.05523
        curveToRelative(
            dx1 = 4.3552723f,
            dy1 = -6.1058664f,
            dx2 = 0.25339666f,
            dy2 = -12.409605f,
            dx3 = -6.2595277f,
            dy3 = -17.05523f,
        )
        // c -6.3093953 -4.5004478 -11.512909 -6.6766696 -15.723005 -0.7743323
        curveToRelative(
            dx1 = -6.3093953f,
            dy1 = -4.5004478f,
            dx2 = -11.512909f,
            dy2 = -6.6766696f,
            dx3 = -15.723005f,
            dy3 = -0.7743323f,
        )
        // c -7.0267887 9.420682 35.330032 30.113974 20.957632 50.263332
        curveToRelative(
            dx1 = -7.0267887f,
            dy1 = 9.420682f,
            dx2 = 35.330032f,
            dy2 = 30.113974f,
            dx3 = 20.957632f,
            dy3 = 50.263332f,
        )
        // C 2693.4204 1573.4817 2676.734 1565.8784 2665.7432 1558.039
        curveTo(
            x1 = 2693.4204f,
            y1 = 1573.4817f,
            x2 = 2676.734f,
            y2 = 1565.8784f,
            x3 = 2665.7432f,
            y3 = 1558.039f,
        )
        // c -9.362329 -6.678084 -23.072182 -21.984697 -12.869174 -36.202686z
        curveToRelative(
            dx1 = -9.362329f,
            dy1 = -6.678084f,
            dx2 = -23.072182f,
            dy2 = -21.984697f,
            dx3 = -12.869174f,
            dy3 = -36.202686f,
        )
        close()
    }
    iconPath(fill = SolidColor(Color(0xFF009B3A)), pathFillType = PathFillType.EvenOdd) {
        // M 2736.7185 1611.5227
        moveTo(x = 2736.7185f, y = 1611.5227f)
        // a 31.5 35 38.5 0 0 43.576023 -54.78257
        arcToRelative(
            a = 31.5f,
            b = 35.0f,
            theta = 38.5f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 43.576023f,
            dy1 = -54.78257f,
        )
        // A 31.5 35 38.5 0 0 2736.7185 1611.5227
        arcTo(
            horizontalEllipseRadius = 31.5f,
            verticalEllipseRadius = 35.0f,
            theta = 38.5f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            x1 = 2736.7185f,
            y1 = 1611.5227f,
        )
        // m 8.09269 -10.173906
        moveToRelative(dx = 8.09269f, dy = -10.173906f)
        // a 18.5 22 38.5 0 0 27.390644 -34.434757
        arcToRelative(
            a = 18.5f,
            b = 22.0f,
            theta = 38.5f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = 27.390644f,
            dy1 = -34.434757f,
        )
        // a 18.5 22 38.5 0 0 -27.390644 34.434757
        arcToRelative(
            a = 18.5f,
            b = 22.0f,
            theta = 38.5f,
            isMoreThanHalf = false,
            isPositiveArc = false,
            dx1 = -27.390644f,
            dy1 = 34.434757f,
        )
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1500 1306.5
        moveTo(x = 1500.0f, y = 1306.5f)
        // l -9.7340355 29.95828
        lineToRelative(dx = -9.7340355f, dy = 29.95828f)
        // l 14.97914 4.8670177
        lineToRelative(dx = 14.97914f, dy = 4.8670177f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1500 1306.5
        moveTo(x = 1500.0f, y = 1306.5f)
        // l 9.7340355 29.95828
        lineToRelative(dx = 9.7340355f, dy = 29.95828f)
        // l -14.97914 4.8670177
        lineToRelative(dx = -14.97914f, dy = 4.8670177f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1529.9583 1328.266
        moveTo(x = 1529.9583f, y = 1328.266f)
        // l -31.5 1.7763568E-15
        lineToRelative(dx = -31.5f, dy = 1.7763568E-15f)
        // l 8.881784E-16 15.75
        lineToRelative(dx = 8.881784E-16f, dy = 15.75f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1529.9583 1328.266
        moveTo(x = 1529.9583f, y = 1328.266f)
        // l -25.484035 18.515236
        lineToRelative(dx = -25.484035f, dy = 18.515236f)
        // l -9.257618 -12.742018
        lineToRelative(dx = -9.257618f, dy = -12.742018f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1470.0417 1328.266
        moveTo(x = 1470.0417f, y = 1328.266f)
        // l 25.484035 18.515236
        lineToRelative(dx = 25.484035f, dy = 18.515236f)
        // l 9.257618 -12.742018
        lineToRelative(dx = 9.257618f, dy = -12.742018f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1470.0417 1328.266
        moveTo(x = 1470.0417f, y = 1328.266f)
        // l 31.5 1.7763568E-15
        lineToRelative(dx = 31.5f, dy = 1.7763568E-15f)
        // l -8.881784E-16 15.75
        lineToRelative(dx = -8.881784E-16f, dy = 15.75f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1518.5153 1363.484
        moveTo(x = 1518.5153f, y = 1363.484f)
        // l -9.7340355 -29.95828
        lineToRelative(dx = -9.7340355f, dy = -29.95828f)
        // l -14.97914 4.8670177
        lineToRelative(dx = -14.97914f, dy = 4.8670177f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1518.5153 1363.484
        moveTo(x = 1518.5153f, y = 1363.484f)
        // l -25.484035 -18.515236
        lineToRelative(dx = -25.484035f, dy = -18.515236f)
        // l 9.257618 -12.742018
        lineToRelative(dx = 9.257618f, dy = -12.742018f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1481.4847 1363.484
        moveTo(x = 1481.4847f, y = 1363.484f)
        // l 25.484035 -18.515236
        lineToRelative(dx = 25.484035f, dy = -18.515236f)
        // l -9.257618 -12.742018
        lineToRelative(dx = -9.257618f, dy = -12.742018f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1481.4847 1363.484
        moveTo(x = 1481.4847f, y = 1363.484f)
        // l 9.7340355 -29.95828
        lineToRelative(dx = 9.7340355f, dy = -29.95828f)
        // l 14.97914 4.8670177
        lineToRelative(dx = 14.97914f, dy = 4.8670177f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1565 1615.5
        moveTo(x = 1565.0f, y = 1615.5f)
        // l -9.7340355 29.95828
        lineToRelative(dx = -9.7340355f, dy = 29.95828f)
        // l 14.97914 4.8670177
        lineToRelative(dx = 14.97914f, dy = 4.8670177f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1565 1615.5
        moveTo(x = 1565.0f, y = 1615.5f)
        // l 9.7340355 29.95828
        lineToRelative(dx = 9.7340355f, dy = 29.95828f)
        // l -14.97914 4.8670177
        lineToRelative(dx = -14.97914f, dy = 4.8670177f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1594.9583 1637.266
        moveTo(x = 1594.9583f, y = 1637.266f)
        // l -31.5 1.7763568E-15
        lineToRelative(dx = -31.5f, dy = 1.7763568E-15f)
        // l 8.881784E-16 15.75
        lineToRelative(dx = 8.881784E-16f, dy = 15.75f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1594.9583 1637.266
        moveTo(x = 1594.9583f, y = 1637.266f)
        // l -25.484035 18.515236
        lineToRelative(dx = -25.484035f, dy = 18.515236f)
        // l -9.257618 -12.742018
        lineToRelative(dx = -9.257618f, dy = -12.742018f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1535.0417 1637.266
        moveTo(x = 1535.0417f, y = 1637.266f)
        // l 25.484035 18.515236
        lineToRelative(dx = 25.484035f, dy = 18.515236f)
        // l 9.257618 -12.742018
        lineToRelative(dx = 9.257618f, dy = -12.742018f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1535.0417 1637.266
        moveTo(x = 1535.0417f, y = 1637.266f)
        // l 31.5 1.7763568E-15
        lineToRelative(dx = 31.5f, dy = 1.7763568E-15f)
        // l -8.881784E-16 15.75
        lineToRelative(dx = -8.881784E-16f, dy = 15.75f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1583.5153 1672.484
        moveTo(x = 1583.5153f, y = 1672.484f)
        // l -9.7340355 -29.95828
        lineToRelative(dx = -9.7340355f, dy = -29.95828f)
        // l -14.97914 4.8670177
        lineToRelative(dx = -14.97914f, dy = 4.8670177f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1583.5153 1672.484
        moveTo(x = 1583.5153f, y = 1672.484f)
        // l -25.484035 -18.515236
        lineToRelative(dx = -25.484035f, dy = -18.515236f)
        // l 9.257618 -12.742018
        lineToRelative(dx = 9.257618f, dy = -12.742018f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1546.4847 1672.484
        moveTo(x = 1546.4847f, y = 1672.484f)
        // l 25.484035 -18.515236
        lineToRelative(dx = 25.484035f, dy = -18.515236f)
        // l -9.257618 -12.742018
        lineToRelative(dx = -9.257618f, dy = -12.742018f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1546.4847 1672.484
        moveTo(x = 1546.4847f, y = 1672.484f)
        // l 9.7340355 -29.95828
        lineToRelative(dx = 9.7340355f, dy = -29.95828f)
        // l 14.97914 4.8670177
        lineToRelative(dx = 14.97914f, dy = 4.8670177f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1475 1686.75
        moveTo(x = 1475.0f, y = 1686.75f)
        // l -8.111696 24.965233
        lineToRelative(dx = -8.111696f, dy = 24.965233f)
        // l 12.482616 4.055848
        lineToRelative(dx = 12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1475 1686.75
        moveTo(x = 1475.0f, y = 1686.75f)
        // l 8.111696 24.965233
        lineToRelative(dx = 8.111696f, dy = 24.965233f)
        // l -12.482616 4.055848
        lineToRelative(dx = -12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1499.9652 1704.8883
        moveTo(x = 1499.9652f, y = 1704.8883f)
        // l -26.25 1.7763568E-15
        lineToRelative(dx = -26.25f, dy = 1.7763568E-15f)
        // l 8.881784E-16 13.125
        lineToRelative(dx = 8.881784E-16f, dy = 13.125f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1499.9652 1704.8883
        moveTo(x = 1499.9652f, y = 1704.8883f)
        // l -21.236696 15.429363
        lineToRelative(dx = -21.236696f, dy = 15.429363f)
        // l -7.7146816 -10.618348
        lineToRelative(dx = -7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1450.0348 1704.8883
        moveTo(x = 1450.0348f, y = 1704.8883f)
        // l 21.236696 15.429363
        lineToRelative(dx = 21.236696f, dy = 15.429363f)
        // l 7.7146816 -10.618348
        lineToRelative(dx = 7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1450.0348 1704.8883
        moveTo(x = 1450.0348f, y = 1704.8883f)
        // l 26.25 1.7763568E-15
        lineToRelative(dx = 26.25f, dy = 1.7763568E-15f)
        // l -8.881784E-16 13.125
        lineToRelative(dx = -8.881784E-16f, dy = 13.125f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1490.4293 1734.2367
        moveTo(x = 1490.4293f, y = 1734.2367f)
        // l -8.111696 -24.965233
        lineToRelative(dx = -8.111696f, dy = -24.965233f)
        // l -12.482616 4.055848
        lineToRelative(dx = -12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1490.4293 1734.2367
        moveTo(x = 1490.4293f, y = 1734.2367f)
        // l -21.236696 -15.429363
        lineToRelative(dx = -21.236696f, dy = -15.429363f)
        // l 7.7146816 -10.618348
        lineToRelative(dx = 7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1459.5707 1734.2367
        moveTo(x = 1459.5707f, y = 1734.2367f)
        // l 21.236696 -15.429363
        lineToRelative(dx = 21.236696f, dy = -15.429363f)
        // l -7.7146816 -10.618348
        lineToRelative(dx = -7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1459.5707 1734.2367
        moveTo(x = 1459.5707f, y = 1734.2367f)
        // l 8.111696 -24.965233
        lineToRelative(dx = 8.111696f, dy = -24.965233f)
        // l 12.482616 4.055848
        lineToRelative(dx = 12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1637 1587
        moveTo(x = 1637.0f, y = 1587.0f)
        // l -4.635255 14.265848
        lineToRelative(dx = -4.635255f, dy = 14.265848f)
        // l 7.132924 2.3176274
        lineToRelative(dx = 7.132924f, dy = 2.3176274f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1637 1587
        moveTo(x = 1637.0f, y = 1587.0f)
        // l 4.635255 14.265848
        lineToRelative(dx = 4.635255f, dy = 14.265848f)
        // l -7.132924 2.3176274
        lineToRelative(dx = -7.132924f, dy = 2.3176274f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1651.2659 1597.3647
        moveTo(x = 1651.2659f, y = 1597.3647f)
        // l -15 8.881784E-16
        lineToRelative(dx = -15.0f, dy = 8.881784E-16f)
        // l 4.440892E-16 7.5
        lineToRelative(dx = 4.440892E-16f, dy = 7.5f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1651.2659 1597.3647
        moveTo(x = 1651.2659f, y = 1597.3647f)
        // l -12.135255 8.816779
        lineToRelative(dx = -12.135255f, dy = 8.816779f)
        // l -4.4083896 -6.0676274
        lineToRelative(dx = -4.4083896f, dy = -6.0676274f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1622.7341 1597.3647
        moveTo(x = 1622.7341f, y = 1597.3647f)
        // l 12.135255 8.816779
        lineToRelative(dx = 12.135255f, dy = 8.816779f)
        // l 4.4083896 -6.0676274
        lineToRelative(dx = 4.4083896f, dy = -6.0676274f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1622.7341 1597.3647
        moveTo(x = 1622.7341f, y = 1597.3647f)
        // l 15 8.881784E-16
        lineToRelative(dx = 15.0f, dy = 8.881784E-16f)
        // l -4.440892E-16 7.5
        lineToRelative(dx = -4.440892E-16f, dy = 7.5f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1645.8168 1614.1353
        moveTo(x = 1645.8168f, y = 1614.1353f)
        // l -4.635255 -14.265848
        lineToRelative(dx = -4.635255f, dy = -14.265848f)
        // l -7.132924 2.3176274
        lineToRelative(dx = -7.132924f, dy = 2.3176274f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1645.8168 1614.1353
        moveTo(x = 1645.8168f, y = 1614.1353f)
        // l -12.135255 -8.816779
        lineToRelative(dx = -12.135255f, dy = -8.816779f)
        // l 4.4083896 -6.0676274
        lineToRelative(dx = 4.4083896f, dy = -6.0676274f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1628.1832 1614.1353
        moveTo(x = 1628.1832f, y = 1614.1353f)
        // l 12.135255 -8.816779
        lineToRelative(dx = 12.135255f, dy = -8.816779f)
        // l -4.4083896 -6.0676274
        lineToRelative(dx = -4.4083896f, dy = -6.0676274f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1628.1832 1614.1353
        moveTo(x = 1628.1832f, y = 1614.1353f)
        // l 4.635255 -14.265848
        lineToRelative(dx = 4.635255f, dy = -14.265848f)
        // l 7.132924 2.3176274
        lineToRelative(dx = 7.132924f, dy = 2.3176274f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1718 1693.75
        moveTo(x = 1718.0f, y = 1693.75f)
        // l -8.111696 24.965233
        lineToRelative(dx = -8.111696f, dy = 24.965233f)
        // l 12.482616 4.055848
        lineToRelative(dx = 12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1718 1693.75
        moveTo(x = 1718.0f, y = 1693.75f)
        // l 8.111696 24.965233
        lineToRelative(dx = 8.111696f, dy = 24.965233f)
        // l -12.482616 4.055848
        lineToRelative(dx = -12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1742.9652 1711.8883
        moveTo(x = 1742.9652f, y = 1711.8883f)
        // l -26.25 1.7763568E-15
        lineToRelative(dx = -26.25f, dy = 1.7763568E-15f)
        // l 8.881784E-16 13.125
        lineToRelative(dx = 8.881784E-16f, dy = 13.125f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1742.9652 1711.8883
        moveTo(x = 1742.9652f, y = 1711.8883f)
        // l -21.236696 15.429363
        lineToRelative(dx = -21.236696f, dy = 15.429363f)
        // l -7.7146816 -10.618348
        lineToRelative(dx = -7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1693.0348 1711.8883
        moveTo(x = 1693.0348f, y = 1711.8883f)
        // l 21.236696 15.429363
        lineToRelative(dx = 21.236696f, dy = 15.429363f)
        // l 7.7146816 -10.618348
        lineToRelative(dx = 7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1693.0348 1711.8883
        moveTo(x = 1693.0348f, y = 1711.8883f)
        // l 26.25 1.7763568E-15
        lineToRelative(dx = 26.25f, dy = 1.7763568E-15f)
        // l -8.881784E-16 13.125
        lineToRelative(dx = -8.881784E-16f, dy = 13.125f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1733.4293 1741.2367
        moveTo(x = 1733.4293f, y = 1741.2367f)
        // l -8.111696 -24.965233
        lineToRelative(dx = -8.111696f, dy = -24.965233f)
        // l -12.482616 4.055848
        lineToRelative(dx = -12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1733.4293 1741.2367
        moveTo(x = 1733.4293f, y = 1741.2367f)
        // l -21.236696 -15.429363
        lineToRelative(dx = -21.236696f, dy = -15.429363f)
        // l 7.7146816 -10.618348
        lineToRelative(dx = 7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1702.5707 1741.2367
        moveTo(x = 1702.5707f, y = 1741.2367f)
        // l 21.236696 -15.429363
        lineToRelative(dx = 21.236696f, dy = -15.429363f)
        // l -7.7146816 -10.618348
        lineToRelative(dx = -7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1702.5707 1741.2367
        moveTo(x = 1702.5707f, y = 1741.2367f)
        // l 8.111696 -24.965233
        lineToRelative(dx = 8.111696f, dy = -24.965233f)
        // l 12.482616 4.055848
        lineToRelative(dx = 12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1696 1772
        moveTo(x = 1696.0f, y = 1772.0f)
        // l -6.489357 19.972187
        lineToRelative(dx = -6.489357f, dy = 19.972187f)
        // l 9.9860935 3.2446785
        lineToRelative(dx = 9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1696 1772
        moveTo(x = 1696.0f, y = 1772.0f)
        // l 6.489357 19.972187
        lineToRelative(dx = 6.489357f, dy = 19.972187f)
        // l -9.9860935 3.2446785
        lineToRelative(dx = -9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1715.9722 1786.5106
        moveTo(x = 1715.9722f, y = 1786.5106f)
        // l -21 1.7763568E-15
        lineToRelative(dx = -21.0f, dy = 1.7763568E-15f)
        // l 8.881784E-16 10.5
        lineToRelative(dx = 8.881784E-16f, dy = 10.5f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1715.9722 1786.5106
        moveTo(x = 1715.9722f, y = 1786.5106f)
        // l -16.989357 12.343491
        lineToRelative(dx = -16.989357f, dy = 12.343491f)
        // l -6.1717453 -8.4946785
        lineToRelative(dx = -6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1676.0278 1786.5106
        moveTo(x = 1676.0278f, y = 1786.5106f)
        // l 16.989357 12.343491
        lineToRelative(dx = 16.989357f, dy = 12.343491f)
        // l 6.1717453 -8.4946785
        lineToRelative(dx = 6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1676.0278 1786.5106
        moveTo(x = 1676.0278f, y = 1786.5106f)
        // l 21 1.7763568E-15
        lineToRelative(dx = 21.0f, dy = 1.7763568E-15f)
        // l -8.881784E-16 10.5
        lineToRelative(dx = -8.881784E-16f, dy = 10.5f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1708.3435 1809.9894
        moveTo(x = 1708.3435f, y = 1809.9894f)
        // l -6.489357 -19.972187
        lineToRelative(dx = -6.489357f, dy = -19.972187f)
        // l -9.9860935 3.2446785
        lineToRelative(dx = -9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1708.3435 1809.9894
        moveTo(x = 1708.3435f, y = 1809.9894f)
        // l -16.989357 -12.343491
        lineToRelative(dx = -16.989357f, dy = -12.343491f)
        // l 6.1717453 -8.4946785
        lineToRelative(dx = 6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1683.6565 1809.9894
        moveTo(x = 1683.6565f, y = 1809.9894f)
        // l 16.989357 -12.343491
        lineToRelative(dx = 16.989357f, dy = -12.343491f)
        // l -6.1717453 -8.4946785
        lineToRelative(dx = -6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1683.6565 1809.9894
        moveTo(x = 1683.6565f, y = 1809.9894f)
        // l 6.489357 -19.972187
        lineToRelative(dx = 6.489357f, dy = -19.972187f)
        // l 9.9860935 3.2446785
        lineToRelative(dx = 9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2328 1210.5
        moveTo(x = 2328.0f, y = 1210.5f)
        // l -9.7340355 29.95828
        lineToRelative(dx = -9.7340355f, dy = 29.95828f)
        // l 14.97914 4.8670177
        lineToRelative(dx = 14.97914f, dy = 4.8670177f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2328 1210.5
        moveTo(x = 2328.0f, y = 1210.5f)
        // l 9.7340355 29.95828
        lineToRelative(dx = 9.7340355f, dy = 29.95828f)
        // l -14.97914 4.8670177
        lineToRelative(dx = -14.97914f, dy = 4.8670177f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2357.9583 1232.266
        moveTo(x = 2357.9583f, y = 1232.266f)
        // l -31.5 1.7763568E-15
        lineToRelative(dx = -31.5f, dy = 1.7763568E-15f)
        // l 8.881784E-16 15.75
        lineToRelative(dx = 8.881784E-16f, dy = 15.75f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2357.9583 1232.266
        moveTo(x = 2357.9583f, y = 1232.266f)
        // l -25.484035 18.515236
        lineToRelative(dx = -25.484035f, dy = 18.515236f)
        // l -9.257618 -12.742018
        lineToRelative(dx = -9.257618f, dy = -12.742018f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2298.0417 1232.266
        moveTo(x = 2298.0417f, y = 1232.266f)
        // l 25.484035 18.515236
        lineToRelative(dx = 25.484035f, dy = 18.515236f)
        // l 9.257618 -12.742018
        lineToRelative(dx = 9.257618f, dy = -12.742018f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2298.0417 1232.266
        moveTo(x = 2298.0417f, y = 1232.266f)
        // l 31.5 1.7763568E-15
        lineToRelative(dx = 31.5f, dy = 1.7763568E-15f)
        // l -8.881784E-16 15.75
        lineToRelative(dx = -8.881784E-16f, dy = 15.75f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2346.5151 1267.484
        moveTo(x = 2346.5151f, y = 1267.484f)
        // l -9.7340355 -29.95828
        lineToRelative(dx = -9.7340355f, dy = -29.95828f)
        // l -14.97914 4.8670177
        lineToRelative(dx = -14.97914f, dy = 4.8670177f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2346.5151 1267.484
        moveTo(x = 2346.5151f, y = 1267.484f)
        // l -25.484035 -18.515236
        lineToRelative(dx = -25.484035f, dy = -18.515236f)
        // l 9.257618 -12.742018
        lineToRelative(dx = 9.257618f, dy = -12.742018f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2309.4849 1267.484
        moveTo(x = 2309.4849f, y = 1267.484f)
        // l 25.484035 -18.515236
        lineToRelative(dx = 25.484035f, dy = -18.515236f)
        // l -9.257618 -12.742018
        lineToRelative(dx = -9.257618f, dy = -12.742018f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2309.4849 1267.484
        moveTo(x = 2309.4849f, y = 1267.484f)
        // l 9.7340355 -29.95828
        lineToRelative(dx = 9.7340355f, dy = -29.95828f)
        // l 14.97914 4.8670177
        lineToRelative(dx = 14.97914f, dy = 4.8670177f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2615 1696.5
        moveTo(x = 2615.0f, y = 1696.5f)
        // l -9.7340355 29.95828
        lineToRelative(dx = -9.7340355f, dy = 29.95828f)
        // l 14.97914 4.8670177
        lineToRelative(dx = 14.97914f, dy = 4.8670177f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2615 1696.5
        moveTo(x = 2615.0f, y = 1696.5f)
        // l 9.7340355 29.95828
        lineToRelative(dx = 9.7340355f, dy = 29.95828f)
        // l -14.97914 4.8670177
        lineToRelative(dx = -14.97914f, dy = 4.8670177f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2644.9583 1718.266
        moveTo(x = 2644.9583f, y = 1718.266f)
        // l -31.5 1.7763568E-15
        lineToRelative(dx = -31.5f, dy = 1.7763568E-15f)
        // l 8.881784E-16 15.75
        lineToRelative(dx = 8.881784E-16f, dy = 15.75f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2644.9583 1718.266
        moveTo(x = 2644.9583f, y = 1718.266f)
        // l -25.484035 18.515236
        lineToRelative(dx = -25.484035f, dy = 18.515236f)
        // l -9.257618 -12.742018
        lineToRelative(dx = -9.257618f, dy = -12.742018f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2585.0417 1718.266
        moveTo(x = 2585.0417f, y = 1718.266f)
        // l 25.484035 18.515236
        lineToRelative(dx = 25.484035f, dy = 18.515236f)
        // l 9.257618 -12.742018
        lineToRelative(dx = 9.257618f, dy = -12.742018f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2585.0417 1718.266
        moveTo(x = 2585.0417f, y = 1718.266f)
        // l 31.5 1.7763568E-15
        lineToRelative(dx = 31.5f, dy = 1.7763568E-15f)
        // l -8.881784E-16 15.75
        lineToRelative(dx = -8.881784E-16f, dy = 15.75f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2633.5151 1753.484
        moveTo(x = 2633.5151f, y = 1753.484f)
        // l -9.7340355 -29.95828
        lineToRelative(dx = -9.7340355f, dy = -29.95828f)
        // l -14.97914 4.8670177
        lineToRelative(dx = -14.97914f, dy = 4.8670177f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2633.5151 1753.484
        moveTo(x = 2633.5151f, y = 1753.484f)
        // l -25.484035 -18.515236
        lineToRelative(dx = -25.484035f, dy = -18.515236f)
        // l 9.257618 -12.742018
        lineToRelative(dx = 9.257618f, dy = -12.742018f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2596.4849 1753.484
        moveTo(x = 2596.4849f, y = 1753.484f)
        // l 25.484035 -18.515236
        lineToRelative(dx = 25.484035f, dy = -18.515236f)
        // l -9.257618 -12.742018
        lineToRelative(dx = -9.257618f, dy = -12.742018f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2596.4849 1753.484
        moveTo(x = 2596.4849f, y = 1753.484f)
        // l 9.7340355 -29.95828
        lineToRelative(dx = 9.7340355f, dy = -29.95828f)
        // l 14.97914 4.8670177
        lineToRelative(dx = 14.97914f, dy = 4.8670177f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2717 1714
        moveTo(x = 2717.0f, y = 1714.0f)
        // l -6.489357 19.972187
        lineToRelative(dx = -6.489357f, dy = 19.972187f)
        // l 9.9860935 3.2446785
        lineToRelative(dx = 9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2717 1714
        moveTo(x = 2717.0f, y = 1714.0f)
        // l 6.489357 19.972187
        lineToRelative(dx = 6.489357f, dy = 19.972187f)
        // l -9.9860935 3.2446785
        lineToRelative(dx = -9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2736.9722 1728.5106
        moveTo(x = 2736.9722f, y = 1728.5106f)
        // l -21 1.7763568E-15
        lineToRelative(dx = -21.0f, dy = 1.7763568E-15f)
        // l 8.881784E-16 10.5
        lineToRelative(dx = 8.881784E-16f, dy = 10.5f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2736.9722 1728.5106
        moveTo(x = 2736.9722f, y = 1728.5106f)
        // l -16.989357 12.343491
        lineToRelative(dx = -16.989357f, dy = 12.343491f)
        // l -6.1717453 -8.4946785
        lineToRelative(dx = -6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2697.0278 1728.5106
        moveTo(x = 2697.0278f, y = 1728.5106f)
        // l 16.989357 12.343491
        lineToRelative(dx = 16.989357f, dy = 12.343491f)
        // l 6.1717453 -8.4946785
        lineToRelative(dx = 6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2697.0278 1728.5106
        moveTo(x = 2697.0278f, y = 1728.5106f)
        // l 21 1.7763568E-15
        lineToRelative(dx = 21.0f, dy = 1.7763568E-15f)
        // l -8.881784E-16 10.5
        lineToRelative(dx = -8.881784E-16f, dy = 10.5f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2729.3435 1751.9894
        moveTo(x = 2729.3435f, y = 1751.9894f)
        // l -6.489357 -19.972187
        lineToRelative(dx = -6.489357f, dy = -19.972187f)
        // l -9.9860935 3.2446785
        lineToRelative(dx = -9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2729.3435 1751.9894
        moveTo(x = 2729.3435f, y = 1751.9894f)
        // l -16.989357 -12.343491
        lineToRelative(dx = -16.989357f, dy = -12.343491f)
        // l 6.1717453 -8.4946785
        lineToRelative(dx = 6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2704.6565 1751.9894
        moveTo(x = 2704.6565f, y = 1751.9894f)
        // l 16.989357 -12.343491
        lineToRelative(dx = 16.989357f, dy = -12.343491f)
        // l -6.1717453 -8.4946785
        lineToRelative(dx = -6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2704.6565 1751.9894
        moveTo(x = 2704.6565f, y = 1751.9894f)
        // l 6.489357 -19.972187
        lineToRelative(dx = 6.489357f, dy = -19.972187f)
        // l 9.9860935 3.2446785
        lineToRelative(dx = 9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2645 1766.75
        moveTo(x = 2645.0f, y = 1766.75f)
        // l -8.111696 24.965233
        lineToRelative(dx = -8.111696f, dy = 24.965233f)
        // l 12.482616 4.055848
        lineToRelative(dx = 12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2645 1766.75
        moveTo(x = 2645.0f, y = 1766.75f)
        // l 8.111696 24.965233
        lineToRelative(dx = 8.111696f, dy = 24.965233f)
        // l -12.482616 4.055848
        lineToRelative(dx = -12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2669.9653 1784.8883
        moveTo(x = 2669.9653f, y = 1784.8883f)
        // l -26.25 1.7763568E-15
        lineToRelative(dx = -26.25f, dy = 1.7763568E-15f)
        // l 8.881784E-16 13.125
        lineToRelative(dx = 8.881784E-16f, dy = 13.125f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2669.9653 1784.8883
        moveTo(x = 2669.9653f, y = 1784.8883f)
        // l -21.236696 15.429363
        lineToRelative(dx = -21.236696f, dy = 15.429363f)
        // l -7.7146816 -10.618348
        lineToRelative(dx = -7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2620.0347 1784.8883
        moveTo(x = 2620.0347f, y = 1784.8883f)
        // l 21.236696 15.429363
        lineToRelative(dx = 21.236696f, dy = 15.429363f)
        // l 7.7146816 -10.618348
        lineToRelative(dx = 7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2620.0347 1784.8883
        moveTo(x = 2620.0347f, y = 1784.8883f)
        // l 26.25 1.7763568E-15
        lineToRelative(dx = 26.25f, dy = 1.7763568E-15f)
        // l -8.881784E-16 13.125
        lineToRelative(dx = -8.881784E-16f, dy = 13.125f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2660.4294 1814.2367
        moveTo(x = 2660.4294f, y = 1814.2367f)
        // l -8.111696 -24.965233
        lineToRelative(dx = -8.111696f, dy = -24.965233f)
        // l -12.482616 4.055848
        lineToRelative(dx = -12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2660.4294 1814.2367
        moveTo(x = 2660.4294f, y = 1814.2367f)
        // l -21.236696 -15.429363
        lineToRelative(dx = -21.236696f, dy = -15.429363f)
        // l 7.7146816 -10.618348
        lineToRelative(dx = 7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2629.5706 1814.2367
        moveTo(x = 2629.5706f, y = 1814.2367f)
        // l 21.236696 -15.429363
        lineToRelative(dx = 21.236696f, dy = -15.429363f)
        // l -7.7146816 -10.618348
        lineToRelative(dx = -7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2629.5706 1814.2367
        moveTo(x = 2629.5706f, y = 1814.2367f)
        // l 8.111696 -24.965233
        lineToRelative(dx = 8.111696f, dy = -24.965233f)
        // l 12.482616 4.055848
        lineToRelative(dx = 12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2468 1920.75
        moveTo(x = 2468.0f, y = 1920.75f)
        // l -8.111696 24.965233
        lineToRelative(dx = -8.111696f, dy = 24.965233f)
        // l 12.482616 4.055848
        lineToRelative(dx = 12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2468 1920.75
        moveTo(x = 2468.0f, y = 1920.75f)
        // l 8.111696 24.965233
        lineToRelative(dx = 8.111696f, dy = 24.965233f)
        // l -12.482616 4.055848
        lineToRelative(dx = -12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2492.9653 1938.8883
        moveTo(x = 2492.9653f, y = 1938.8883f)
        // l -26.25 1.7763568E-15
        lineToRelative(dx = -26.25f, dy = 1.7763568E-15f)
        // l 8.881784E-16 13.125
        lineToRelative(dx = 8.881784E-16f, dy = 13.125f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2492.9653 1938.8883
        moveTo(x = 2492.9653f, y = 1938.8883f)
        // l -21.236696 15.429363
        lineToRelative(dx = -21.236696f, dy = 15.429363f)
        // l -7.7146816 -10.618348
        lineToRelative(dx = -7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2443.0347 1938.8883
        moveTo(x = 2443.0347f, y = 1938.8883f)
        // l 21.236696 15.429363
        lineToRelative(dx = 21.236696f, dy = 15.429363f)
        // l 7.7146816 -10.618348
        lineToRelative(dx = 7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2443.0347 1938.8883
        moveTo(x = 2443.0347f, y = 1938.8883f)
        // l 26.25 1.7763568E-15
        lineToRelative(dx = 26.25f, dy = 1.7763568E-15f)
        // l -8.881784E-16 13.125
        lineToRelative(dx = -8.881784E-16f, dy = 13.125f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2483.4294 1968.2367
        moveTo(x = 2483.4294f, y = 1968.2367f)
        // l -8.111696 -24.965233
        lineToRelative(dx = -8.111696f, dy = -24.965233f)
        // l -12.482616 4.055848
        lineToRelative(dx = -12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2483.4294 1968.2367
        moveTo(x = 2483.4294f, y = 1968.2367f)
        // l -21.236696 -15.429363
        lineToRelative(dx = -21.236696f, dy = -15.429363f)
        // l 7.7146816 -10.618348
        lineToRelative(dx = 7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2452.5706 1968.2367
        moveTo(x = 2452.5706f, y = 1968.2367f)
        // l 21.236696 -15.429363
        lineToRelative(dx = 21.236696f, dy = -15.429363f)
        // l -7.7146816 -10.618348
        lineToRelative(dx = -7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2452.5706 1968.2367
        moveTo(x = 2452.5706f, y = 1968.2367f)
        // l 8.111696 -24.965233
        lineToRelative(dx = 8.111696f, dy = -24.965233f)
        // l 12.482616 4.055848
        lineToRelative(dx = 12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2467 2000
        moveTo(x = 2467.0f, y = 2000.0f)
        // l -6.489357 19.972187
        lineToRelative(dx = -6.489357f, dy = 19.972187f)
        // l 9.9860935 3.2446785
        lineToRelative(dx = 9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2467 2000
        moveTo(x = 2467.0f, y = 2000.0f)
        // l 6.489357 19.972187
        lineToRelative(dx = 6.489357f, dy = 19.972187f)
        // l -9.9860935 3.2446785
        lineToRelative(dx = -9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2486.9722 2014.5106
        moveTo(x = 2486.9722f, y = 2014.5106f)
        // l -21 1.7763568E-15
        lineToRelative(dx = -21.0f, dy = 1.7763568E-15f)
        // l 8.881784E-16 10.5
        lineToRelative(dx = 8.881784E-16f, dy = 10.5f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2486.9722 2014.5106
        moveTo(x = 2486.9722f, y = 2014.5106f)
        // l -16.989357 12.343491
        lineToRelative(dx = -16.989357f, dy = 12.343491f)
        // l -6.1717453 -8.4946785
        lineToRelative(dx = -6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2447.0278 2014.5106
        moveTo(x = 2447.0278f, y = 2014.5106f)
        // l 16.989357 12.343491
        lineToRelative(dx = 16.989357f, dy = 12.343491f)
        // l 6.1717453 -8.4946785
        lineToRelative(dx = 6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2447.0278 2014.5106
        moveTo(x = 2447.0278f, y = 2014.5106f)
        // l 21 1.7763568E-15
        lineToRelative(dx = 21.0f, dy = 1.7763568E-15f)
        // l -8.881784E-16 10.5
        lineToRelative(dx = -8.881784E-16f, dy = 10.5f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2479.3435 2037.9894
        moveTo(x = 2479.3435f, y = 2037.9894f)
        // l -6.489357 -19.972187
        lineToRelative(dx = -6.489357f, dy = -19.972187f)
        // l -9.9860935 3.2446785
        lineToRelative(dx = -9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2479.3435 2037.9894
        moveTo(x = 2479.3435f, y = 2037.9894f)
        // l -16.989357 -12.343491
        lineToRelative(dx = -16.989357f, dy = -12.343491f)
        // l 6.1717453 -8.4946785
        lineToRelative(dx = 6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2454.6565 2037.9894
        moveTo(x = 2454.6565f, y = 2037.9894f)
        // l 16.989357 -12.343491
        lineToRelative(dx = 16.989357f, dy = -12.343491f)
        // l -6.1717453 -8.4946785
        lineToRelative(dx = -6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2454.6565 2037.9894
        moveTo(x = 2454.6565f, y = 2037.9894f)
        // l 6.489357 -19.972187
        lineToRelative(dx = 6.489357f, dy = -19.972187f)
        // l 9.9860935 3.2446785
        lineToRelative(dx = 9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2541 1868
        moveTo(x = 2541.0f, y = 1868.0f)
        // l -6.489357 19.972187
        lineToRelative(dx = -6.489357f, dy = 19.972187f)
        // l 9.9860935 3.2446785
        lineToRelative(dx = 9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2541 1868
        moveTo(x = 2541.0f, y = 1868.0f)
        // l 6.489357 19.972187
        lineToRelative(dx = 6.489357f, dy = 19.972187f)
        // l -9.9860935 3.2446785
        lineToRelative(dx = -9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2560.9722 1882.5106
        moveTo(x = 2560.9722f, y = 1882.5106f)
        // l -21 1.7763568E-15
        lineToRelative(dx = -21.0f, dy = 1.7763568E-15f)
        // l 8.881784E-16 10.5
        lineToRelative(dx = 8.881784E-16f, dy = 10.5f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2560.9722 1882.5106
        moveTo(x = 2560.9722f, y = 1882.5106f)
        // l -16.989357 12.343491
        lineToRelative(dx = -16.989357f, dy = 12.343491f)
        // l -6.1717453 -8.4946785
        lineToRelative(dx = -6.1717453f, dy = -8.4946785f)
    }
}

private fun ImageVector.Builder.brasilPart2() {
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2521.0278 1882.5106
        moveTo(x = 2521.0278f, y = 1882.5106f)
        // l 16.989357 12.343491
        lineToRelative(dx = 16.989357f, dy = 12.343491f)
        // l 6.1717453 -8.4946785
        lineToRelative(dx = 6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2521.0278 1882.5106
        moveTo(x = 2521.0278f, y = 1882.5106f)
        // l 21 1.7763568E-15
        lineToRelative(dx = 21.0f, dy = 1.7763568E-15f)
        // l -8.881784E-16 10.5
        lineToRelative(dx = -8.881784E-16f, dy = 10.5f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2553.3435 1905.9894
        moveTo(x = 2553.3435f, y = 1905.9894f)
        // l -6.489357 -19.972187
        lineToRelative(dx = -6.489357f, dy = -19.972187f)
        // l -9.9860935 3.2446785
        lineToRelative(dx = -9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2553.3435 1905.9894
        moveTo(x = 2553.3435f, y = 1905.9894f)
        // l -16.989357 -12.343491
        lineToRelative(dx = -16.989357f, dy = -12.343491f)
        // l 6.1717453 -8.4946785
        lineToRelative(dx = 6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2528.6565 1905.9894
        moveTo(x = 2528.6565f, y = 1905.9894f)
        // l 16.989357 -12.343491
        lineToRelative(dx = 16.989357f, dy = -12.343491f)
        // l -6.1717453 -8.4946785
        lineToRelative(dx = -6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2528.6565 1905.9894
        moveTo(x = 2528.6565f, y = 1905.9894f)
        // l 6.489357 -19.972187
        lineToRelative(dx = 6.489357f, dy = -19.972187f)
        // l 9.9860935 3.2446785
        lineToRelative(dx = 9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2600 1825.75
        moveTo(x = 2600.0f, y = 1825.75f)
        // l -8.111696 24.965233
        lineToRelative(dx = -8.111696f, dy = 24.965233f)
        // l 12.482616 4.055848
        lineToRelative(dx = 12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2600 1825.75
        moveTo(x = 2600.0f, y = 1825.75f)
        // l 8.111696 24.965233
        lineToRelative(dx = 8.111696f, dy = 24.965233f)
        // l -12.482616 4.055848
        lineToRelative(dx = -12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2624.9653 1843.8883
        moveTo(x = 2624.9653f, y = 1843.8883f)
        // l -26.25 1.7763568E-15
        lineToRelative(dx = -26.25f, dy = 1.7763568E-15f)
        // l 8.881784E-16 13.125
        lineToRelative(dx = 8.881784E-16f, dy = 13.125f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2624.9653 1843.8883
        moveTo(x = 2624.9653f, y = 1843.8883f)
        // l -21.236696 15.429363
        lineToRelative(dx = -21.236696f, dy = 15.429363f)
        // l -7.7146816 -10.618348
        lineToRelative(dx = -7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2575.0347 1843.8883
        moveTo(x = 2575.0347f, y = 1843.8883f)
        // l 21.236696 15.429363
        lineToRelative(dx = 21.236696f, dy = 15.429363f)
        // l 7.7146816 -10.618348
        lineToRelative(dx = 7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2575.0347 1843.8883
        moveTo(x = 2575.0347f, y = 1843.8883f)
        // l 26.25 1.7763568E-15
        lineToRelative(dx = 26.25f, dy = 1.7763568E-15f)
        // l -8.881784E-16 13.125
        lineToRelative(dx = -8.881784E-16f, dy = 13.125f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2615.4294 1873.2367
        moveTo(x = 2615.4294f, y = 1873.2367f)
        // l -8.111696 -24.965233
        lineToRelative(dx = -8.111696f, dy = -24.965233f)
        // l -12.482616 4.055848
        lineToRelative(dx = -12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2615.4294 1873.2367
        moveTo(x = 2615.4294f, y = 1873.2367f)
        // l -21.236696 -15.429363
        lineToRelative(dx = -21.236696f, dy = -15.429363f)
        // l 7.7146816 -10.618348
        lineToRelative(dx = 7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2584.5706 1873.2367
        moveTo(x = 2584.5706f, y = 1873.2367f)
        // l 21.236696 -15.429363
        lineToRelative(dx = 21.236696f, dy = -15.429363f)
        // l -7.7146816 -10.618348
        lineToRelative(dx = -7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2584.5706 1873.2367
        moveTo(x = 2584.5706f, y = 1873.2367f)
        // l 8.111696 -24.965233
        lineToRelative(dx = 8.111696f, dy = -24.965233f)
        // l 12.482616 4.055848
        lineToRelative(dx = 12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2465 1854
        moveTo(x = 2465.0f, y = 1854.0f)
        // l -6.489357 19.972187
        lineToRelative(dx = -6.489357f, dy = 19.972187f)
        // l 9.9860935 3.2446785
        lineToRelative(dx = 9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2465 1854
        moveTo(x = 2465.0f, y = 1854.0f)
        // l 6.489357 19.972187
        lineToRelative(dx = 6.489357f, dy = 19.972187f)
        // l -9.9860935 3.2446785
        lineToRelative(dx = -9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2484.9722 1868.5106
        moveTo(x = 2484.9722f, y = 1868.5106f)
        // l -21 1.7763568E-15
        lineToRelative(dx = -21.0f, dy = 1.7763568E-15f)
        // l 8.881784E-16 10.5
        lineToRelative(dx = 8.881784E-16f, dy = 10.5f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2484.9722 1868.5106
        moveTo(x = 2484.9722f, y = 1868.5106f)
        // l -16.989357 12.343491
        lineToRelative(dx = -16.989357f, dy = 12.343491f)
        // l -6.1717453 -8.4946785
        lineToRelative(dx = -6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2445.0278 1868.5106
        moveTo(x = 2445.0278f, y = 1868.5106f)
        // l 16.989357 12.343491
        lineToRelative(dx = 16.989357f, dy = 12.343491f)
        // l 6.1717453 -8.4946785
        lineToRelative(dx = 6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2445.0278 1868.5106
        moveTo(x = 2445.0278f, y = 1868.5106f)
        // l 21 1.7763568E-15
        lineToRelative(dx = 21.0f, dy = 1.7763568E-15f)
        // l -8.881784E-16 10.5
        lineToRelative(dx = -8.881784E-16f, dy = 10.5f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2477.3435 1891.9894
        moveTo(x = 2477.3435f, y = 1891.9894f)
        // l -6.489357 -19.972187
        lineToRelative(dx = -6.489357f, dy = -19.972187f)
        // l -9.9860935 3.2446785
        lineToRelative(dx = -9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2477.3435 1891.9894
        moveTo(x = 2477.3435f, y = 1891.9894f)
        // l -16.989357 -12.343491
        lineToRelative(dx = -16.989357f, dy = -12.343491f)
        // l 6.1717453 -8.4946785
        lineToRelative(dx = 6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2452.6565 1891.9894
        moveTo(x = 2452.6565f, y = 1891.9894f)
        // l 16.989357 -12.343491
        lineToRelative(dx = 16.989357f, dy = -12.343491f)
        // l -6.1717453 -8.4946785
        lineToRelative(dx = -6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2452.6565 1891.9894
        moveTo(x = 2452.6565f, y = 1891.9894f)
        // l 6.489357 -19.972187
        lineToRelative(dx = 6.489357f, dy = -19.972187f)
        // l 9.9860935 3.2446785
        lineToRelative(dx = 9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1820 1473.75
        moveTo(x = 1820.0f, y = 1473.75f)
        // l -8.111696 24.965233
        lineToRelative(dx = -8.111696f, dy = 24.965233f)
        // l 12.482616 4.055848
        lineToRelative(dx = 12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1820 1473.75
        moveTo(x = 1820.0f, y = 1473.75f)
        // l 8.111696 24.965233
        lineToRelative(dx = 8.111696f, dy = 24.965233f)
        // l -12.482616 4.055848
        lineToRelative(dx = -12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1844.9652 1491.8883
        moveTo(x = 1844.9652f, y = 1491.8883f)
        // l -26.25 1.7763568E-15
        lineToRelative(dx = -26.25f, dy = 1.7763568E-15f)
        // l 8.881784E-16 13.125
        lineToRelative(dx = 8.881784E-16f, dy = 13.125f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1844.9652 1491.8883
        moveTo(x = 1844.9652f, y = 1491.8883f)
        // l -21.236696 15.429363
        lineToRelative(dx = -21.236696f, dy = 15.429363f)
        // l -7.7146816 -10.618348
        lineToRelative(dx = -7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1795.0348 1491.8883
        moveTo(x = 1795.0348f, y = 1491.8883f)
        // l 21.236696 15.429363
        lineToRelative(dx = 21.236696f, dy = 15.429363f)
        // l 7.7146816 -10.618348
        lineToRelative(dx = 7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1795.0348 1491.8883
        moveTo(x = 1795.0348f, y = 1491.8883f)
        // l 26.25 1.7763568E-15
        lineToRelative(dx = 26.25f, dy = 1.7763568E-15f)
        // l -8.881784E-16 13.125
        lineToRelative(dx = -8.881784E-16f, dy = 13.125f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1835.4293 1521.2367
        moveTo(x = 1835.4293f, y = 1521.2367f)
        // l -8.111696 -24.965233
        lineToRelative(dx = -8.111696f, dy = -24.965233f)
        // l -12.482616 4.055848
        lineToRelative(dx = -12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1835.4293 1521.2367
        moveTo(x = 1835.4293f, y = 1521.2367f)
        // l -21.236696 -15.429363
        lineToRelative(dx = -21.236696f, dy = -15.429363f)
        // l 7.7146816 -10.618348
        lineToRelative(dx = 7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1804.5707 1521.2367
        moveTo(x = 1804.5707f, y = 1521.2367f)
        // l 21.236696 -15.429363
        lineToRelative(dx = 21.236696f, dy = -15.429363f)
        // l -7.7146816 -10.618348
        lineToRelative(dx = -7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1804.5707 1521.2367
        moveTo(x = 1804.5707f, y = 1521.2367f)
        // l 8.111696 -24.965233
        lineToRelative(dx = 8.111696f, dy = -24.965233f)
        // l 12.482616 4.055848
        lineToRelative(dx = 12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2300 1412
        moveTo(x = 2300.0f, y = 1412.0f)
        // l -6.489357 19.972187
        lineToRelative(dx = -6.489357f, dy = 19.972187f)
        // l 9.9860935 3.2446785
        lineToRelative(dx = 9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2300 1412
        moveTo(x = 2300.0f, y = 1412.0f)
        // l 6.489357 19.972187
        lineToRelative(dx = 6.489357f, dy = 19.972187f)
        // l -9.9860935 3.2446785
        lineToRelative(dx = -9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2319.9722 1426.5106
        moveTo(x = 2319.9722f, y = 1426.5106f)
        // l -21 1.7763568E-15
        lineToRelative(dx = -21.0f, dy = 1.7763568E-15f)
        // l 8.881784E-16 10.5
        lineToRelative(dx = 8.881784E-16f, dy = 10.5f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2319.9722 1426.5106
        moveTo(x = 2319.9722f, y = 1426.5106f)
        // l -16.989357 12.343491
        lineToRelative(dx = -16.989357f, dy = 12.343491f)
        // l -6.1717453 -8.4946785
        lineToRelative(dx = -6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2280.0278 1426.5106
        moveTo(x = 2280.0278f, y = 1426.5106f)
        // l 16.989357 12.343491
        lineToRelative(dx = 16.989357f, dy = 12.343491f)
        // l 6.1717453 -8.4946785
        lineToRelative(dx = 6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2280.0278 1426.5106
        moveTo(x = 2280.0278f, y = 1426.5106f)
        // l 21 1.7763568E-15
        lineToRelative(dx = 21.0f, dy = 1.7763568E-15f)
        // l -8.881784E-16 10.5
        lineToRelative(dx = -8.881784E-16f, dy = 10.5f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2312.3435 1449.9894
        moveTo(x = 2312.3435f, y = 1449.9894f)
        // l -6.489357 -19.972187
        lineToRelative(dx = -6.489357f, dy = -19.972187f)
        // l -9.9860935 3.2446785
        lineToRelative(dx = -9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2312.3435 1449.9894
        moveTo(x = 2312.3435f, y = 1449.9894f)
        // l -16.989357 -12.343491
        lineToRelative(dx = -16.989357f, dy = -12.343491f)
        // l 6.1717453 -8.4946785
        lineToRelative(dx = 6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2287.6565 1449.9894
        moveTo(x = 2287.6565f, y = 1449.9894f)
        // l 16.989357 -12.343491
        lineToRelative(dx = 16.989357f, dy = -12.343491f)
        // l -6.1717453 -8.4946785
        lineToRelative(dx = -6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2287.6565 1449.9894
        moveTo(x = 2287.6565f, y = 1449.9894f)
        // l 6.489357 -19.972187
        lineToRelative(dx = 6.489357f, dy = -19.972187f)
        // l 9.9860935 3.2446785
        lineToRelative(dx = 9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2100 1768.5
        moveTo(x = 2100.0f, y = 1768.5f)
        // l -9.7340355 29.95828
        lineToRelative(dx = -9.7340355f, dy = 29.95828f)
        // l 14.97914 4.8670177
        lineToRelative(dx = 14.97914f, dy = 4.8670177f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2100 1768.5
        moveTo(x = 2100.0f, y = 1768.5f)
        // l 9.7340355 29.95828
        lineToRelative(dx = 9.7340355f, dy = 29.95828f)
        // l -14.97914 4.8670177
        lineToRelative(dx = -14.97914f, dy = 4.8670177f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2129.9583 1790.266
        moveTo(x = 2129.9583f, y = 1790.266f)
        // l -31.5 1.7763568E-15
        lineToRelative(dx = -31.5f, dy = 1.7763568E-15f)
        // l 8.881784E-16 15.75
        lineToRelative(dx = 8.881784E-16f, dy = 15.75f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2129.9583 1790.266
        moveTo(x = 2129.9583f, y = 1790.266f)
        // l -25.484035 18.515236
        lineToRelative(dx = -25.484035f, dy = 18.515236f)
        // l -9.257618 -12.742018
        lineToRelative(dx = -9.257618f, dy = -12.742018f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2070.0417 1790.266
        moveTo(x = 2070.0417f, y = 1790.266f)
        // l 25.484035 18.515236
        lineToRelative(dx = 25.484035f, dy = 18.515236f)
        // l 9.257618 -12.742018
        lineToRelative(dx = 9.257618f, dy = -12.742018f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2070.0417 1790.266
        moveTo(x = 2070.0417f, y = 1790.266f)
        // l 31.5 1.7763568E-15
        lineToRelative(dx = 31.5f, dy = 1.7763568E-15f)
        // l -8.881784E-16 15.75
        lineToRelative(dx = -8.881784E-16f, dy = 15.75f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2118.5151 1825.484
        moveTo(x = 2118.5151f, y = 1825.484f)
        // l -9.7340355 -29.95828
        lineToRelative(dx = -9.7340355f, dy = -29.95828f)
        // l -14.97914 4.8670177
        lineToRelative(dx = -14.97914f, dy = 4.8670177f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2118.5151 1825.484
        moveTo(x = 2118.5151f, y = 1825.484f)
        // l -25.484035 -18.515236
        lineToRelative(dx = -25.484035f, dy = -18.515236f)
        // l 9.257618 -12.742018
        lineToRelative(dx = 9.257618f, dy = -12.742018f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2081.4849 1825.484
        moveTo(x = 2081.4849f, y = 1825.484f)
        // l 25.484035 -18.515236
        lineToRelative(dx = 25.484035f, dy = -18.515236f)
        // l -9.257618 -12.742018
        lineToRelative(dx = -9.257618f, dy = -12.742018f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2081.4849 1825.484
        moveTo(x = 2081.4849f, y = 1825.484f)
        // l 9.7340355 -29.95828
        lineToRelative(dx = 9.7340355f, dy = -29.95828f)
        // l 14.97914 4.8670177
        lineToRelative(dx = 14.97914f, dy = 4.8670177f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2185 1627.75
        moveTo(x = 2185.0f, y = 1627.75f)
        // l -8.111696 24.965233
        lineToRelative(dx = -8.111696f, dy = 24.965233f)
        // l 12.482616 4.055848
        lineToRelative(dx = 12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2185 1627.75
        moveTo(x = 2185.0f, y = 1627.75f)
        // l 8.111696 24.965233
        lineToRelative(dx = 8.111696f, dy = 24.965233f)
        // l -12.482616 4.055848
        lineToRelative(dx = -12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2209.9653 1645.8883
        moveTo(x = 2209.9653f, y = 1645.8883f)
        // l -26.25 1.7763568E-15
        lineToRelative(dx = -26.25f, dy = 1.7763568E-15f)
        // l 8.881784E-16 13.125
        lineToRelative(dx = 8.881784E-16f, dy = 13.125f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2209.9653 1645.8883
        moveTo(x = 2209.9653f, y = 1645.8883f)
        // l -21.236696 15.429363
        lineToRelative(dx = -21.236696f, dy = 15.429363f)
        // l -7.7146816 -10.618348
        lineToRelative(dx = -7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2160.0347 1645.8883
        moveTo(x = 2160.0347f, y = 1645.8883f)
        // l 21.236696 15.429363
        lineToRelative(dx = 21.236696f, dy = 15.429363f)
        // l 7.7146816 -10.618348
        lineToRelative(dx = 7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2160.0347 1645.8883
        moveTo(x = 2160.0347f, y = 1645.8883f)
        // l 26.25 1.7763568E-15
        lineToRelative(dx = 26.25f, dy = 1.7763568E-15f)
        // l -8.881784E-16 13.125
        lineToRelative(dx = -8.881784E-16f, dy = 13.125f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2200.4294 1675.2367
        moveTo(x = 2200.4294f, y = 1675.2367f)
        // l -8.111696 -24.965233
        lineToRelative(dx = -8.111696f, dy = -24.965233f)
        // l -12.482616 4.055848
        lineToRelative(dx = -12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2200.4294 1675.2367
        moveTo(x = 2200.4294f, y = 1675.2367f)
        // l -21.236696 -15.429363
        lineToRelative(dx = -21.236696f, dy = -15.429363f)
        // l 7.7146816 -10.618348
        lineToRelative(dx = 7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2169.5706 1675.2367
        moveTo(x = 2169.5706f, y = 1675.2367f)
        // l 21.236696 -15.429363
        lineToRelative(dx = 21.236696f, dy = -15.429363f)
        // l -7.7146816 -10.618348
        lineToRelative(dx = -7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2169.5706 1675.2367
        moveTo(x = 2169.5706f, y = 1675.2367f)
        // l 8.111696 -24.965233
        lineToRelative(dx = 8.111696f, dy = -24.965233f)
        // l 12.482616 4.055848
        lineToRelative(dx = 12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2100 1561.75
        moveTo(x = 2100.0f, y = 1561.75f)
        // l -8.111696 24.965233
        lineToRelative(dx = -8.111696f, dy = 24.965233f)
        // l 12.482616 4.055848
        lineToRelative(dx = 12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2100 1561.75
        moveTo(x = 2100.0f, y = 1561.75f)
        // l 8.111696 24.965233
        lineToRelative(dx = 8.111696f, dy = 24.965233f)
        // l -12.482616 4.055848
        lineToRelative(dx = -12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2124.9653 1579.8883
        moveTo(x = 2124.9653f, y = 1579.8883f)
        // l -26.25 1.7763568E-15
        lineToRelative(dx = -26.25f, dy = 1.7763568E-15f)
        // l 8.881784E-16 13.125
        lineToRelative(dx = 8.881784E-16f, dy = 13.125f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2124.9653 1579.8883
        moveTo(x = 2124.9653f, y = 1579.8883f)
        // l -21.236696 15.429363
        lineToRelative(dx = -21.236696f, dy = 15.429363f)
        // l -7.7146816 -10.618348
        lineToRelative(dx = -7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2075.0347 1579.8883
        moveTo(x = 2075.0347f, y = 1579.8883f)
        // l 21.236696 15.429363
        lineToRelative(dx = 21.236696f, dy = 15.429363f)
        // l 7.7146816 -10.618348
        lineToRelative(dx = 7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2075.0347 1579.8883
        moveTo(x = 2075.0347f, y = 1579.8883f)
        // l 26.25 1.7763568E-15
        lineToRelative(dx = 26.25f, dy = 1.7763568E-15f)
        // l -8.881784E-16 13.125
        lineToRelative(dx = -8.881784E-16f, dy = 13.125f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2115.4294 1609.2367
        moveTo(x = 2115.4294f, y = 1609.2367f)
        // l -8.111696 -24.965233
        lineToRelative(dx = -8.111696f, dy = -24.965233f)
        // l -12.482616 4.055848
        lineToRelative(dx = -12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2115.4294 1609.2367
        moveTo(x = 2115.4294f, y = 1609.2367f)
        // l -21.236696 -15.429363
        lineToRelative(dx = -21.236696f, dy = -15.429363f)
        // l 7.7146816 -10.618348
        lineToRelative(dx = 7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2084.5706 1609.2367
        moveTo(x = 2084.5706f, y = 1609.2367f)
        // l 21.236696 -15.429363
        lineToRelative(dx = 21.236696f, dy = -15.429363f)
        // l -7.7146816 -10.618348
        lineToRelative(dx = -7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2084.5706 1609.2367
        moveTo(x = 2084.5706f, y = 1609.2367f)
        // l 8.111696 -24.965233
        lineToRelative(dx = 8.111696f, dy = -24.965233f)
        // l 12.482616 4.055848
        lineToRelative(dx = 12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2026 1633
        moveTo(x = 2026.0f, y = 1633.0f)
        // l -6.489357 19.972187
        lineToRelative(dx = -6.489357f, dy = 19.972187f)
        // l 9.9860935 3.2446785
        lineToRelative(dx = 9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2026 1633
        moveTo(x = 2026.0f, y = 1633.0f)
        // l 6.489357 19.972187
        lineToRelative(dx = 6.489357f, dy = 19.972187f)
        // l -9.9860935 3.2446785
        lineToRelative(dx = -9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2045.9722 1647.5106
        moveTo(x = 2045.9722f, y = 1647.5106f)
        // l -21 1.7763568E-15
        lineToRelative(dx = -21.0f, dy = 1.7763568E-15f)
        // l 8.881784E-16 10.5
        lineToRelative(dx = 8.881784E-16f, dy = 10.5f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2045.9722 1647.5106
        moveTo(x = 2045.9722f, y = 1647.5106f)
        // l -16.989357 12.343491
        lineToRelative(dx = -16.989357f, dy = 12.343491f)
        // l -6.1717453 -8.4946785
        lineToRelative(dx = -6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2006.0278 1647.5106
        moveTo(x = 2006.0278f, y = 1647.5106f)
        // l 16.989357 12.343491
        lineToRelative(dx = 16.989357f, dy = 12.343491f)
        // l 6.1717453 -8.4946785
        lineToRelative(dx = 6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2006.0278 1647.5106
        moveTo(x = 2006.0278f, y = 1647.5106f)
        // l 21 1.7763568E-15
        lineToRelative(dx = 21.0f, dy = 1.7763568E-15f)
        // l -8.881784E-16 10.5
        lineToRelative(dx = -8.881784E-16f, dy = 10.5f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2038.3435 1670.9894
        moveTo(x = 2038.3435f, y = 1670.9894f)
        // l -6.489357 -19.972187
        lineToRelative(dx = -6.489357f, dy = -19.972187f)
        // l -9.9860935 3.2446785
        lineToRelative(dx = -9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2038.3435 1670.9894
        moveTo(x = 2038.3435f, y = 1670.9894f)
        // l -16.989357 -12.343491
        lineToRelative(dx = -16.989357f, dy = -12.343491f)
        // l 6.1717453 -8.4946785
        lineToRelative(dx = 6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2013.6565 1670.9894
        moveTo(x = 2013.6565f, y = 1670.9894f)
        // l 16.989357 -12.343491
        lineToRelative(dx = 16.989357f, dy = -12.343491f)
        // l -6.1717453 -8.4946785
        lineToRelative(dx = -6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2013.6565 1670.9894
        moveTo(x = 2013.6565f, y = 1670.9894f)
        // l 6.489357 -19.972187
        lineToRelative(dx = 6.489357f, dy = -19.972187f)
        // l 9.9860935 3.2446785
        lineToRelative(dx = 9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2063 1690
        moveTo(x = 2063.0f, y = 1690.0f)
        // l -4.635255 14.265848
        lineToRelative(dx = -4.635255f, dy = 14.265848f)
        // l 7.132924 2.3176274
        lineToRelative(dx = 7.132924f, dy = 2.3176274f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2063 1690
        moveTo(x = 2063.0f, y = 1690.0f)
        // l 4.635255 14.265848
        lineToRelative(dx = 4.635255f, dy = 14.265848f)
        // l -7.132924 2.3176274
        lineToRelative(dx = -7.132924f, dy = 2.3176274f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2077.2659 1700.3647
        moveTo(x = 2077.2659f, y = 1700.3647f)
        // l -15 8.881784E-16
        lineToRelative(dx = -15.0f, dy = 8.881784E-16f)
        // l 4.440892E-16 7.5
        lineToRelative(dx = 4.440892E-16f, dy = 7.5f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2077.2659 1700.3647
        moveTo(x = 2077.2659f, y = 1700.3647f)
        // l -12.135255 8.816779
        lineToRelative(dx = -12.135255f, dy = 8.816779f)
        // l -4.4083896 -6.0676274
        lineToRelative(dx = -4.4083896f, dy = -6.0676274f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2048.7341 1700.3647
        moveTo(x = 2048.7341f, y = 1700.3647f)
        // l 12.135255 8.816779
        lineToRelative(dx = 12.135255f, dy = 8.816779f)
        // l 4.4083896 -6.0676274
        lineToRelative(dx = 4.4083896f, dy = -6.0676274f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2048.7341 1700.3647
        moveTo(x = 2048.7341f, y = 1700.3647f)
        // l 15 8.881784E-16
        lineToRelative(dx = 15.0f, dy = 8.881784E-16f)
        // l -4.440892E-16 7.5
        lineToRelative(dx = -4.440892E-16f, dy = 7.5f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2071.817 1717.1353
        moveTo(x = 2071.817f, y = 1717.1353f)
        // l -4.635255 -14.265848
        lineToRelative(dx = -4.635255f, dy = -14.265848f)
        // l -7.132924 2.3176274
        lineToRelative(dx = -7.132924f, dy = 2.3176274f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2071.817 1717.1353
        moveTo(x = 2071.817f, y = 1717.1353f)
        // l -12.135255 -8.816779
        lineToRelative(dx = -12.135255f, dy = -8.816779f)
        // l 4.4083896 -6.0676274
        lineToRelative(dx = 4.4083896f, dy = -6.0676274f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2054.183 1717.1353
        moveTo(x = 2054.183f, y = 1717.1353f)
        // l 12.135255 -8.816779
        lineToRelative(dx = 12.135255f, dy = -8.816779f)
        // l -4.4083896 -6.0676274
        lineToRelative(dx = -4.4083896f, dy = -6.0676274f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2054.183 1717.1353
        moveTo(x = 2054.183f, y = 1717.1353f)
        // l 4.635255 -14.265848
        lineToRelative(dx = 4.635255f, dy = -14.265848f)
        // l 7.132924 2.3176274
        lineToRelative(dx = 7.132924f, dy = 2.3176274f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2320 1938.75
        moveTo(x = 2320.0f, y = 1938.75f)
        // l -8.111696 24.965233
        lineToRelative(dx = -8.111696f, dy = 24.965233f)
        // l 12.482616 4.055848
        lineToRelative(dx = 12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2320 1938.75
        moveTo(x = 2320.0f, y = 1938.75f)
        // l 8.111696 24.965233
        lineToRelative(dx = 8.111696f, dy = 24.965233f)
        // l -12.482616 4.055848
        lineToRelative(dx = -12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2344.9653 1956.8883
        moveTo(x = 2344.9653f, y = 1956.8883f)
        // l -26.25 1.7763568E-15
        lineToRelative(dx = -26.25f, dy = 1.7763568E-15f)
        // l 8.881784E-16 13.125
        lineToRelative(dx = 8.881784E-16f, dy = 13.125f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2344.9653 1956.8883
        moveTo(x = 2344.9653f, y = 1956.8883f)
        // l -21.236696 15.429363
        lineToRelative(dx = -21.236696f, dy = 15.429363f)
        // l -7.7146816 -10.618348
        lineToRelative(dx = -7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2295.0347 1956.8883
        moveTo(x = 2295.0347f, y = 1956.8883f)
        // l 21.236696 15.429363
        lineToRelative(dx = 21.236696f, dy = 15.429363f)
        // l 7.7146816 -10.618348
        lineToRelative(dx = 7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2295.0347 1956.8883
        moveTo(x = 2295.0347f, y = 1956.8883f)
        // l 26.25 1.7763568E-15
        lineToRelative(dx = 26.25f, dy = 1.7763568E-15f)
        // l -8.881784E-16 13.125
        lineToRelative(dx = -8.881784E-16f, dy = 13.125f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2335.4294 1986.2367
        moveTo(x = 2335.4294f, y = 1986.2367f)
        // l -8.111696 -24.965233
        lineToRelative(dx = -8.111696f, dy = -24.965233f)
        // l -12.482616 4.055848
        lineToRelative(dx = -12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2335.4294 1986.2367
        moveTo(x = 2335.4294f, y = 1986.2367f)
        // l -21.236696 -15.429363
        lineToRelative(dx = -21.236696f, dy = -15.429363f)
        // l 7.7146816 -10.618348
        lineToRelative(dx = 7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2304.5706 1986.2367
        moveTo(x = 2304.5706f, y = 1986.2367f)
        // l 21.236696 -15.429363
        lineToRelative(dx = 21.236696f, dy = -15.429363f)
        // l -7.7146816 -10.618348
        lineToRelative(dx = -7.7146816f, dy = -10.618348f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2304.5706 1986.2367
        moveTo(x = 2304.5706f, y = 1986.2367f)
        // l 8.111696 -24.965233
        lineToRelative(dx = 8.111696f, dy = -24.965233f)
        // l 12.482616 4.055848
        lineToRelative(dx = 12.482616f, dy = 4.055848f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2383 1879
        moveTo(x = 2383.0f, y = 1879.0f)
        // l -6.489357 19.972187
        lineToRelative(dx = -6.489357f, dy = 19.972187f)
        // l 9.9860935 3.2446785
        lineToRelative(dx = 9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2383 1879
        moveTo(x = 2383.0f, y = 1879.0f)
        // l 6.489357 19.972187
        lineToRelative(dx = 6.489357f, dy = 19.972187f)
        // l -9.9860935 3.2446785
        lineToRelative(dx = -9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2402.9722 1893.5106
        moveTo(x = 2402.9722f, y = 1893.5106f)
        // l -21 1.7763568E-15
        lineToRelative(dx = -21.0f, dy = 1.7763568E-15f)
        // l 8.881784E-16 10.5
        lineToRelative(dx = 8.881784E-16f, dy = 10.5f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2402.9722 1893.5106
        moveTo(x = 2402.9722f, y = 1893.5106f)
        // l -16.989357 12.343491
        lineToRelative(dx = -16.989357f, dy = 12.343491f)
        // l -6.1717453 -8.4946785
        lineToRelative(dx = -6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2363.0278 1893.5106
        moveTo(x = 2363.0278f, y = 1893.5106f)
        // l 16.989357 12.343491
        lineToRelative(dx = 16.989357f, dy = 12.343491f)
        // l 6.1717453 -8.4946785
        lineToRelative(dx = 6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2363.0278 1893.5106
        moveTo(x = 2363.0278f, y = 1893.5106f)
        // l 21 1.7763568E-15
        lineToRelative(dx = 21.0f, dy = 1.7763568E-15f)
        // l -8.881784E-16 10.5
        lineToRelative(dx = -8.881784E-16f, dy = 10.5f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2395.3435 1916.9894
        moveTo(x = 2395.3435f, y = 1916.9894f)
        // l -6.489357 -19.972187
        lineToRelative(dx = -6.489357f, dy = -19.972187f)
        // l -9.9860935 3.2446785
        lineToRelative(dx = -9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2395.3435 1916.9894
        moveTo(x = 2395.3435f, y = 1916.9894f)
        // l -16.989357 -12.343491
        lineToRelative(dx = -16.989357f, dy = -12.343491f)
        // l 6.1717453 -8.4946785
        lineToRelative(dx = 6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2370.6565 1916.9894
        moveTo(x = 2370.6565f, y = 1916.9894f)
        // l 16.989357 -12.343491
        lineToRelative(dx = 16.989357f, dy = -12.343491f)
        // l -6.1717453 -8.4946785
        lineToRelative(dx = -6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2370.6565 1916.9894
        moveTo(x = 2370.6565f, y = 1916.9894f)
        // l 6.489357 -19.972187
        lineToRelative(dx = 6.489357f, dy = -19.972187f)
        // l 9.9860935 3.2446785
        lineToRelative(dx = 9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2262 1861
        moveTo(x = 2262.0f, y = 1861.0f)
        // l -6.489357 19.972187
        lineToRelative(dx = -6.489357f, dy = 19.972187f)
        // l 9.9860935 3.2446785
        lineToRelative(dx = 9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2262 1861
        moveTo(x = 2262.0f, y = 1861.0f)
        // l 6.489357 19.972187
        lineToRelative(dx = 6.489357f, dy = 19.972187f)
        // l -9.9860935 3.2446785
        lineToRelative(dx = -9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2281.9722 1875.5106
        moveTo(x = 2281.9722f, y = 1875.5106f)
        // l -21 1.7763568E-15
        lineToRelative(dx = -21.0f, dy = 1.7763568E-15f)
        // l 8.881784E-16 10.5
        lineToRelative(dx = 8.881784E-16f, dy = 10.5f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2281.9722 1875.5106
        moveTo(x = 2281.9722f, y = 1875.5106f)
        // l -16.989357 12.343491
        lineToRelative(dx = -16.989357f, dy = 12.343491f)
        // l -6.1717453 -8.4946785
        lineToRelative(dx = -6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2242.0278 1875.5106
        moveTo(x = 2242.0278f, y = 1875.5106f)
        // l 16.989357 12.343491
        lineToRelative(dx = 16.989357f, dy = 12.343491f)
        // l 6.1717453 -8.4946785
        lineToRelative(dx = 6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2242.0278 1875.5106
        moveTo(x = 2242.0278f, y = 1875.5106f)
        // l 21 1.7763568E-15
        lineToRelative(dx = 21.0f, dy = 1.7763568E-15f)
        // l -8.881784E-16 10.5
        lineToRelative(dx = -8.881784E-16f, dy = 10.5f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2274.3435 1898.9894
        moveTo(x = 2274.3435f, y = 1898.9894f)
        // l -6.489357 -19.972187
        lineToRelative(dx = -6.489357f, dy = -19.972187f)
        // l -9.9860935 3.2446785
        lineToRelative(dx = -9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2274.3435 1898.9894
        moveTo(x = 2274.3435f, y = 1898.9894f)
        // l -16.989357 -12.343491
        lineToRelative(dx = -16.989357f, dy = -12.343491f)
        // l 6.1717453 -8.4946785
        lineToRelative(dx = 6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2249.6565 1898.9894
        moveTo(x = 2249.6565f, y = 1898.9894f)
        // l 16.989357 -12.343491
        lineToRelative(dx = 16.989357f, dy = -12.343491f)
        // l -6.1717453 -8.4946785
        lineToRelative(dx = -6.1717453f, dy = -8.4946785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2249.6565 1898.9894
        moveTo(x = 2249.6565f, y = 1898.9894f)
        // l 6.489357 -19.972187
        lineToRelative(dx = 6.489357f, dy = -19.972187f)
        // l 9.9860935 3.2446785
        lineToRelative(dx = 9.9860935f, dy = 3.2446785f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1805 1828.5
        moveTo(x = 1805.0f, y = 1828.5f)
        // l -9.7340355 29.95828
        lineToRelative(dx = -9.7340355f, dy = 29.95828f)
        // l 14.97914 4.8670177
        lineToRelative(dx = 14.97914f, dy = 4.8670177f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1805 1828.5
        moveTo(x = 1805.0f, y = 1828.5f)
        // l 9.7340355 29.95828
        lineToRelative(dx = 9.7340355f, dy = 29.95828f)
        // l -14.97914 4.8670177
        lineToRelative(dx = -14.97914f, dy = 4.8670177f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1834.9583 1850.266
        moveTo(x = 1834.9583f, y = 1850.266f)
        // l -31.5 1.7763568E-15
        lineToRelative(dx = -31.5f, dy = 1.7763568E-15f)
        // l 8.881784E-16 15.75
        lineToRelative(dx = 8.881784E-16f, dy = 15.75f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1834.9583 1850.266
        moveTo(x = 1834.9583f, y = 1850.266f)
        // l -25.484035 18.515236
        lineToRelative(dx = -25.484035f, dy = 18.515236f)
        // l -9.257618 -12.742018
        lineToRelative(dx = -9.257618f, dy = -12.742018f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1775.0417 1850.266
        moveTo(x = 1775.0417f, y = 1850.266f)
        // l 25.484035 18.515236
        lineToRelative(dx = 25.484035f, dy = 18.515236f)
        // l 9.257618 -12.742018
        lineToRelative(dx = 9.257618f, dy = -12.742018f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1775.0417 1850.266
        moveTo(x = 1775.0417f, y = 1850.266f)
        // l 31.5 1.7763568E-15
        lineToRelative(dx = 31.5f, dy = 1.7763568E-15f)
        // l -8.881784E-16 15.75
        lineToRelative(dx = -8.881784E-16f, dy = 15.75f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1823.5153 1885.484
        moveTo(x = 1823.5153f, y = 1885.484f)
        // l -9.7340355 -29.95828
        lineToRelative(dx = -9.7340355f, dy = -29.95828f)
        // l -14.97914 4.8670177
        lineToRelative(dx = -14.97914f, dy = 4.8670177f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1823.5153 1885.484
        moveTo(x = 1823.5153f, y = 1885.484f)
        // l -25.484035 -18.515236
        lineToRelative(dx = -25.484035f, dy = -18.515236f)
        // l 9.257618 -12.742018
        lineToRelative(dx = 9.257618f, dy = -12.742018f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1786.4847 1885.484
        moveTo(x = 1786.4847f, y = 1885.484f)
        // l 25.484035 -18.515236
        lineToRelative(dx = 25.484035f, dy = -18.515236f)
        // l -9.257618 -12.742018
        lineToRelative(dx = -9.257618f, dy = -12.742018f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 1786.4847 1885.484
        moveTo(x = 1786.4847f, y = 1885.484f)
        // l 9.7340355 -29.95828
        lineToRelative(dx = 9.7340355f, dy = -29.95828f)
        // l 14.97914 4.8670177
        lineToRelative(dx = 14.97914f, dy = 4.8670177f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2100 2034.5
        moveTo(x = 2100.0f, y = 2034.5f)
        // l -3.2446785 9.9860935
        lineToRelative(dx = -3.2446785f, dy = 9.9860935f)
        // l 4.9930468 1.6223392
        lineToRelative(dx = 4.9930468f, dy = 1.6223392f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2100 2034.5
        moveTo(x = 2100.0f, y = 2034.5f)
        // l 3.2446785 9.9860935
        lineToRelative(dx = 3.2446785f, dy = 9.9860935f)
        // l -4.9930468 1.6223392
        lineToRelative(dx = -4.9930468f, dy = 1.6223392f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2109.986 2041.7554
        moveTo(x = 2109.986f, y = 2041.7554f)
        // l -10.5 8.881784E-16
        lineToRelative(dx = -10.5f, dy = 8.881784E-16f)
        // l 4.440892E-16 5.25
        lineToRelative(dx = 4.440892E-16f, dy = 5.25f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2109.986 2041.7554
        moveTo(x = 2109.986f, y = 2041.7554f)
        // l -8.4946785 6.1717453
        lineToRelative(dx = -8.4946785f, dy = 6.1717453f)
        // l -3.0858727 -4.2473392
        lineToRelative(dx = -3.0858727f, dy = -4.2473392f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2090.014 2041.7554
        moveTo(x = 2090.014f, y = 2041.7554f)
        // l 8.4946785 6.1717453
        lineToRelative(dx = 8.4946785f, dy = 6.1717453f)
        // l 3.0858727 -4.2473392
        lineToRelative(dx = 3.0858727f, dy = -4.2473392f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2090.014 2041.7554
        moveTo(x = 2090.014f, y = 2041.7554f)
        // l 10.5 8.881784E-16
        lineToRelative(dx = 10.5f, dy = 8.881784E-16f)
        // l -4.440892E-16 5.25
        lineToRelative(dx = -4.440892E-16f, dy = 5.25f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2106.1716 2053.4946
        moveTo(x = 2106.1716f, y = 2053.4946f)
        // l -3.2446785 -9.9860935
        lineToRelative(dx = -3.2446785f, dy = -9.9860935f)
        // l -4.9930468 1.6223392
        lineToRelative(dx = -4.9930468f, dy = 1.6223392f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2106.1716 2053.4946
        moveTo(x = 2106.1716f, y = 2053.4946f)
        // l -8.4946785 -6.1717453
        lineToRelative(dx = -8.4946785f, dy = -6.1717453f)
        // l 3.0858727 -4.2473392
        lineToRelative(dx = 3.0858727f, dy = -4.2473392f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2093.8284 2053.4946
        moveTo(x = 2093.8284f, y = 2053.4946f)
        // l 8.4946785 -6.1717453
        lineToRelative(dx = 8.4946785f, dy = -6.1717453f)
        // l -3.0858727 -4.2473392
        lineToRelative(dx = -3.0858727f, dy = -4.2473392f)
    }
    iconPath(fill = SolidColor(Color(0xFFFFFFFF))) {
        // M 2093.8284 2053.4946
        moveTo(x = 2093.8284f, y = 2053.4946f)
        // l 3.2446785 -9.9860935
        lineToRelative(dx = 3.2446785f, dy = -9.9860935f)
        // l 4.9930468 1.6223392
        lineToRelative(dx = 4.9930468f, dy = 1.6223392f)
    }
}

@Preview(name = "Brasil", showBackground = true)
@Composable
private fun BrasilPreview() {
    SampleAppTheme {
        Image(
            imageVector = Icons.Brasil,
            contentDescription = null,
        )
    }
}
