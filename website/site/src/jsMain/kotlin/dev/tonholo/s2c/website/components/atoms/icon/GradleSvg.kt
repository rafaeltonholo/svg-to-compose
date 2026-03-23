package dev.tonholo.s2c.website.components.atoms.icon

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.dom.svg.Path
import com.varabyte.kobweb.compose.dom.svg.SVGFillType
import com.varabyte.kobweb.compose.dom.svg.SVGStrokeLineJoin
import com.varabyte.kobweb.compose.dom.svg.Svg
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.toAttrs

@Composable
fun GradleSvg(
    color: Color = Color.rgb(value = 0x02303A),
    width: Int = 512,
    height: Int = 512,
    modifier: Modifier = Modifier
) {
    Svg(
        attrs = modifier.toAttrs {
            viewBox(x = 0, y = 0, width = 512, height = 512)
            fill(SVGFillType.None)
            width(value = width)
            height(value = height)
            attr("fill-rule", "evenodd")
            attr("clip-rule", "evenodd")
            strokeLineJoin(SVGStrokeLineJoin.Round)
            attr("stroke-miterlimit", "2")
        },
    ) {
        Path {
            fill(color)
            attr("fill-rule", "nonzero")
            d(
                "M478.825 95.63c-30.024-30.037-79.12-30.886-110.166-1.906" +
                    "a7.744 7.744 0 00-2.425 5.427 7.92 7.92 0 002.251 5.486" +
                    "l9.874 10.22c2.65 2.638 6.882 2.938 9.873.692" +
                    "a44.95 44.95 0 0127.196-9.065h.196" +
                    "c24.897 0 45.383 20.486 45.383 45.383" +
                    " 0 12.068-4.81 23.65-13.36 32.161" +
                    "-62.937 62.936-146.947-113.4-337.603-22.691" +
                    "a25.962 25.962 0 00-14.948 23.488" +
                    "c0 4.498 1.172 8.92 3.4 12.83" +
                    "l32.681 56.526c7.05 12.16 22.772 16.485 35.048 9.643" +
                    "l.808-.462-.635.462 14.493-8.141" +
                    "a332.489 332.489 0 0045.614-34.067" +
                    "c2.99-2.569 7.46-2.569 10.45 0" +
                    "a7.399 7.399 0 012.83 5.774 7.642 7.642 0 01-2.483 5.774" +
                    " 341.763 341.763 0 01-48.097 35.683h-.462" +
                    "l-14.493 8.084a40.441 40.441 0 01-20.093 5.254" +
                    "c-14.781 0-28.5-7.858-35.971-20.613" +
                    "l-30.89-53.409C27.938 250.082-8.09 330.86 11.251 433.346" +
                    "c.705 3.517 3.805 6.08 7.391 6.12h35.221" +
                    "c3.77 0 6.986-2.84 7.448-6.582" +
                    " 3.234-25.659 25.296-45.117 51.157-45.117" +
                    " 25.862 0 47.924 19.458 51.157 45.117" +
                    ".491 3.754 3.725 6.589 7.507 6.583h34.297" +
                    "c3.77 0 6.986-2.841 7.448-6.583" +
                    " 3.337-25.59 25.376-44.944 51.186-44.944" +
                    "s47.849 19.354 51.186 44.944" +
                    "c.462 3.742 3.678 6.583 7.448 6.583h33.893" +
                    "c4.077 0 7.443-3.314 7.506-7.39" +
                    ".809-47.751 13.685-102.604 50.407-130.088" +
                    " 127.2-95.154 93.768-176.74 64.321-206.36z" +
                    "m-129.74 143.943l-24.25-12.183v-.057" +
                    "c0-8.361 6.882-15.244 15.243-15.244" +
                    " 8.36 0 15.243 6.883 15.243 15.244" +
                    " 0 4.855-2.321 9.428-6.236 12.298v-.058z",
            )
        }
    }
}

@Composable
fun GradleSvg(color: Color = Color.rgb(value = 0x02303A), size: Int = 512, modifier: Modifier = Modifier) {
    GradleSvg(color = color, width = size, height = size, modifier = modifier)
}
