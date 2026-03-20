package dev.tonholo.s2c.website.editor.mapper

import androidx.compose.ui.graphics.PathFillType
import dev.tonholo.s2c.domain.compose.PathFillType as S2cPathFillType

/** Converts a domain [PathFillType][dev.tonholo.s2c.domain.compose.PathFillType] to a Compose [PathFillType]. */
internal fun S2cPathFillType.toPathFillType(): PathFillType = when (value.lowercase()) {
    "evenodd" -> PathFillType.EvenOdd
    else -> PathFillType.NonZero
}
