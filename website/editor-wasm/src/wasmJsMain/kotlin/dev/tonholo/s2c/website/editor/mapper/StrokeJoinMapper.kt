package dev.tonholo.s2c.website.editor.mapper

import androidx.compose.ui.graphics.StrokeJoin
import dev.tonholo.s2c.domain.compose.StrokeJoin as S2cStrokeJoin

/** Converts a domain [StrokeJoin][dev.tonholo.s2c.domain.compose.StrokeJoin] to a Compose [StrokeJoin]. */
internal fun S2cStrokeJoin.toStrokeJoin(): StrokeJoin = when (value.lowercase()) {
    "round" -> StrokeJoin.Round
    "bevel" -> StrokeJoin.Bevel
    else -> StrokeJoin.Miter
}
