package dev.tonholo.s2c.website.components.atoms

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.animation
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.animation.toAnimation
import dev.tonholo.s2c.website.FadeInUpKeyframes
import org.jetbrains.compose.web.css.AnimationFillMode
import org.jetbrains.compose.web.css.AnimationTimingFunction
import org.jetbrains.compose.web.css.CSSSizeValue
import org.jetbrains.compose.web.css.CSSUnit
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.dom.Div

external class IntersectionObserver(
    callback: (entries: Array<dynamic>, observer: IntersectionObserver) -> Unit,
    options: dynamic = definedExternally,
) {
    fun observe(target: org.w3c.dom.Element)
    fun unobserve(target: org.w3c.dom.Element)
    fun disconnect()
}

@Composable
fun AnimateOnScroll(
    modifier: Modifier = Modifier,
    keyframes: Keyframes = FadeInUpKeyframes,
    duration: CSSSizeValue<CSSUnit.ms> = 600.ms,
    delay: CSSSizeValue<CSSUnit.ms> = 0.ms,
    timingFunction: AnimationTimingFunction = AnimationTimingFunction.EaseOut,
    threshold: Double = 0.1,
    once: Boolean = true,
    content: @Composable () -> Unit,
) {
    var isVisible by remember { mutableStateOf(false) }

    val animationModifier = modifier.then(
        if (isVisible) {
            Modifier.animation(
                keyframes.toAnimation(
                    duration = duration,
                    delay = delay,
                    timingFunction = timingFunction,
                    fillMode = AnimationFillMode.Forwards,
                ),
            )
        } else {
            Modifier.opacity(0)
        }
    )

    Div(
        attrs = animationModifier.toAttrs {
            ref { element ->
                val options = js("({})")
                options.threshold = threshold

                val observer = IntersectionObserver(
                    callback = { entries, obs ->
                        entries.forEach { entry ->
                            val isIntersecting = entry.isIntersecting as Boolean
                            if (isIntersecting) {
                                isVisible = true
                                if (once) {
                                    obs.unobserve(element)
                                    obs.disconnect()
                                }
                            } else if (!once) {
                                isVisible = false
                            }
                        }
                    },
                    options = options,
                )
                observer.observe(element)
                onDispose {
                    observer.disconnect()
                }
            }
        },
    ) {
        content()
    }
}
