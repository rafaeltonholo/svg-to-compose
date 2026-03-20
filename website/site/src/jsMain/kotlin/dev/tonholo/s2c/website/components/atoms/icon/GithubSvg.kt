package dev.tonholo.s2c.website.components.atoms.icon

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.dom.GenericTag
import com.varabyte.kobweb.compose.dom.svg.Defs
import com.varabyte.kobweb.compose.dom.svg.Group
import com.varabyte.kobweb.compose.dom.svg.Path
import com.varabyte.kobweb.compose.dom.svg.SVGContainerElementAttrsScope
import com.varabyte.kobweb.compose.dom.svg.SVGFillType
import com.varabyte.kobweb.compose.dom.svg.SVGStrokeLineCap
import com.varabyte.kobweb.compose.dom.svg.SVGStrokeLineJoin
import com.varabyte.kobweb.compose.dom.svg.Svg
import com.varabyte.kobweb.compose.ui.graphics.Color
import org.jetbrains.compose.web.attributes.AttrsScope
import org.w3c.dom.svg.SVGGElement

@Composable
fun GithubSvg(
    color: Color = Color.rgb(value = 0x78716C),
) {
    Svg(
        attrs = {
            viewBox(x = 0, y = 0, width = 13, height = 13)
            fill(SVGFillType.None)
            width(value = 13)
            height(value = 13)
        },
    ) {
        Group(
            attrs = {
                stroke(color)
                strokeLineCap(SVGStrokeLineCap.Round)
                strokeLineJoin(SVGStrokeLineJoin.Round)
                strokeWidth(value = 1.08)
                attr("clip-path", "url(#a)")
            },
        ) {
            Path {
                d(
                    "M8.12 11.92V9.75a2.6 2.6 0 0 0-.54-1.9c1.63 0 3.25-1.08 3.25-2.97" +
                        "a3 3 0 0 0-.54-1.9 4 4 0 0 0 0-1.9s-.54 0-1.62.82q-2.16-.4-4.34 0" +
                        "c-1.08-.82-1.62-.82-1.62-.82a4 4 0 0 0 0 1.9" +
                        "c-.4.55-.59 1.22-.54 1.9 0 1.9 1.62 2.97 3.25 2.97a2.6 2.6 0 0 0-.55 1.9v2.17",
                )
            }
            Path {
                d("M4.87 9.75c-2.44 1.08-2.7-1.08-3.79-1.08")
            }
        }

        Defs {
            GenericTag(
                name = "clip-path",
                namespace = "http://www.w3.org/2000/svg",
                attrs = {
                    SVGClipPathAttrsScope(this).apply {
                        attr("id", "a")
                    }
                },
            ) {
                Path {
                    fill(Color.rgb(value = 0xFFFFFF))
                    d("M0 0h13v13H0z")
                }
            }
        }
    }
}

class SVGClipPathAttrsScope(attrs: AttrsScope<SVGGElement>) : SVGContainerElementAttrsScope<SVGGElement>(attrs)
