package dev.tonholo.s2c.website.editor.mapper

import androidx.compose.ui.graphics.StrokeCap
import dev.tonholo.s2c.domain.compose.StrokeCap as S2cStrokeCap

/** Converts a domain [StrokeCap][dev.tonholo.s2c.domain.compose.StrokeCap] to a Compose [StrokeCap]. */
internal fun S2cStrokeCap.toStrokeCap(): StrokeCap =
    when (value.lowercase()) {
        "round" -> StrokeCap.Round
        "square" -> StrokeCap.Square
        else -> StrokeCap.Butt
    }
